package com.kingscastle.nuzi.towerdefence.gameElements.movement;


import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.Humanoid;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Building;
import com.kingscastle.nuzi.towerdefence.gameElements.movement.pathing.Path;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.teams.Teams;

import java.util.Random;


public class MovingTechnique
{
	private static final String TAG = "MovingTechnique";

	private static float speedCheatX = 1;
	private static float speedCheatY = 1;

	private final Random rand = new Random();
	private Humanoid driver;
	private Teams team;
	private vector position;
	private vector velocity = new vector();
	private vector end = new vector();
	private final vector seekForce = new vector();
	private final vector separationForce = new vector();
	private final vector normal = new vector();
	private final vector temp = new vector();
	private vector pathForce = new vector();
	private final vector desiredVelocity = new vector();

	private vector force = new vector();
	private final Line whisker = new Line();
	private Rpg.Direction d;
	private Inter inter = new Inter();
	private long lastPlayedWalkingSound;



	public MovingTechnique( Humanoid m )
	{
		//name = m.toString();
		driver = m;
		team = driver.getTeamName();
	}

	//private final String name;

	public void act( MovementTechniqueParams params )
	{

		position = driver.loc;
		float maxSpeed = params.getSpeed()*1.5f;
		float maxForce = params.getMaxForce();

		if( position == null )
			return;


		vector loc = params.getLocationOfInterest();

		//Avoid Obstacles/FollowPath
		end.set( velocity ).times( 2 ).add( position );
		pathForce.set( 0 , 0 );
		//


		switch( params.getMovementType() )
		{
		case FOLLOW_PATH:
		{
			Path path = params.getPathToFollow();

			if( path.size() == 1 )
			{
				loc = path.get(0);
				//driver.setPathToFollow( null );
				driver.pathDestinationReached();
				driver.setStayHere( loc );
				desiredVelocity.set( loc.x - position.x , loc.y - position.y );

				break;
			}

			vector lastNode;
			vector nextNode;

			int i = path.getIndexOfNextNode();

			if( i == path.size()-1 ) //at last node
			{
				if( path.getFormationPositions() != null ) //if there are formation positions
				{
					lastNode = path.get( i );			   //last node
					nextNode = path.findMyFormationPosition();  //a formation
				}
				else
				{
					lastNode = path.get( i-1 );
					nextNode = path.get( i );
				}
			}
			else
			{
				lastNode = path.get( i-1 );
				nextNode = path.get( i );
			}


			////Log.d ( name , "lastNode == " + lastNode );
			////Log.d ( name , "nextNode == " + nextNode );


			if( nextNode == null )
			{
				lastNode = path.get( i-1 );
				nextNode = path.get( i );
			}

			boolean goingToFormationPosition = path.getMyFormationPosition() == nextNode;
			float dist = goingToFormationPosition ? Rpg.tenDpSquared : Rpg.fifteenDpSquared ;


			if( position.distanceSquared( nextNode ) < dist )
			{

				vector temp = path.getNext();

				////Log.d ( name ,"Got to nextNode , path.getNext()=" + temp );

				if( temp == null || goingToFormationPosition )
				{
					if( path.getFormationPositions() != null )
					{
						nextNode = path.findMyFormationPosition();
					}

					////Log.d ( name ,"After trying to get formation position nextNode is " + nextNode );

					//driver.setPathToFollow( null );
					driver.pathDestinationReached();
					driver.setStayHere( nextNode );
					desiredVelocity.set( nextNode.x - position.x , nextNode.y - position.y );

					break;
				}
				else
				{
					////Log.d ( name ,"Got a next node location" );

					lastNode = nextNode;
					nextNode = temp;

					////Log.d ( name , "lastNode == " + lastNode );
					////Log.d ( name , "nextNode == " + nextNode );
				}

				//				if( driver.getTeamName() == Teams.BLUE )
				//				{
				//					//Log.d ( name , "i+1 == path.size()" );
				//				}
			}

			if( lastNode != nextNode )
			{
				vector pathNorm = new vector( lastNode , nextNode );
				pathNorm.rotate( 90 );
				vector nextNodeDirection = new vector( end , nextNode );
				if( pathNorm.x != 0 || pathNorm.y != 0 )
				{
					pathForce = nextNodeDirection.proj( pathNorm );
				}
			}



			desiredVelocity.set( nextNode.x - position.x , nextNode.y - position.y );
			break;
		}
		default:
		case SEEK:
		{
			float dist = position.distanceSquared( loc );
			if( dist < Rpg.fiveDpSquared )
			{
				position.set( loc );
				return;
			}
			else if( dist < Rpg.thirtyDpSquared )
			{
				maxSpeed /= 2;
				maxForce /= 2;
			}
			//else if( dist < Rpg.twentyDp )
			desiredVelocity.set( loc.x - position.x , loc.y - position.y );
			break;
		}
		case AVOID:
			desiredVelocity.set( position.x - loc.x , position.y - loc.y );
			break;

		case DPAD:
			desiredVelocity.set( params.getInDirection() );
			break;
		}



		//Seek
		desiredVelocity.normalize().times( maxSpeed );
		seekForce.set( desiredVelocity );
		seekForce.minus( velocity );



		//Avoid Obstacles
		whisker.set( position , end ) ;
		//normal.set( 0 , 0 );
		inter.checkForCollision2( position , normal );
		//normal.times( 150 );



		//Separation
		//andHere.set(velocity).normalize().times(Rpg.eightDp).add(position);
		separationForce.set( 0 , 0 );

		//THIS SHOULD BE IN HERE FOR ANYTHING BUT TOWER DEFENCE!
		LivingThing target = inter.checkForSeparation( params.getMM() ,position , separationForce , temp , team , driver.getTarget() );

		separationForce.times(1);

		if( target != null && !(target instanceof Building) && driver.getTarget() == null )
			driver.setTarget( target );



		float pathDistance = Rpg.thirtyDpSquared;

		truncate( separationForce    , maxForce );
		//truncate( normal , maxForce );
		truncate( seekForce          , maxForce );


		//if( normal.x != 0 && normal.y != 0 )
		//{
		//			pathDistance = Rpg.sixTeenDp;
		//			seekForce.times(          0.1f  );
		//			pathForce.times(          0.1f  );
		//			separationForce.times(    0.3f );
		//obstAvoidanceForce.times( 4f );
		//}


		if( pathForce.magnitudeSquared() > pathDistance ){
			truncate( pathForce , maxForce );
		}
		else{
			pathForce.set( 0 , 0 );
		}

		//if( driver.getTeamName() == Teams.BLUE )
		//{
		////Log.d ( name , "desiredVelocity:    " + desiredVelocity    );
		////Log.d ( name , "velocity:           " + velocity           );
		////Log.d ( name , "obstAvoidanceForce: " + obstAvoidanceForce );
		////Log.d ( name , "pathForce:          " + pathForce          );
		//}

		force.add( seekForce );
		force.add( pathForce );
		force.add( separationForce );
		//force.add( normal );

		////Log.d ( name , "force:              " + force              );

		truncate( force , maxForce );
		velocity.add( force );
		removeVectorsCompInDirOfNormalAndCompensate( velocity , normal );
		truncate( velocity , maxSpeed );



		walk();
	}






	private void truncate( vector v , float maxLength )
	{
		if( v.distanceSquared( 0 , 0 ) > maxLength*maxLength )
		{
			v.normalize().times( maxLength );
		}
		else
		{
			return;
		}

	}


	public void stop()
	{
	}

	public void updateVectorTip()
	{

	}

	public vector getVelocity()
	{
		return velocity;
	}

	public void setVelocity(vector v)
	{
		velocity=v;

	}




	/**
	 * Removes the component of AVector which is in the direction of the normal vector.
	 * @param normal This vector MUST only be one of the vectors [1,0] or [0,1].
	 * @return The vector AVector, possibly not modified.
	 */
	protected vector removeComponentInDirOfNormal( vector aVector , vector normal )
	{
		return aVector.removeComponentInDir(normal);
	}





	void removeVectorsCompInDirOfNormalAndCompensate(vector vector, vector normal)
	{

		float normX = normal.x;
		float normY = normal.y;
		float vX = vector.x;
		float vY = vector.y;


		if( normX != 0 && normY != 0 )
		{
			if( ( normX < 0 && vX < 0 ) || ( normX > 0 && vX > 0 ) )
			{
				if( ( normY < 0 && vY < 0 ) || ( normY > 0 && vY > 0 ) )
				{
					if( rand.nextBoolean() ) // both x and y are going towards boundary
					{
						vector.x = 0;
						vector.y *= -20f;
					}
					else
					{
						vector.x *= -20f;
						vector.y = 0;
					}
				}
				else	// only x is going towards boundary
				{
					vector.x = 0;
					vector.y *= 20f;
				}
			}
			else if( ( normY < 0 && vY < 0 ) || ( normY > 0 && vY > 0 ) ) // only y is going towards boundry
			{
				vector.x *= 20f;
				vector.y = 0;
			}
			return;
		}


		if( normX < 0 )
		{
			if( vX < 0 )
			{
				vector.x = 0;
				vector.y *= 20;
			}
		}
		else if( normX > 0 )
		{
			if( vX > 0 )
			{
				vector.x = 0;
				vector.y *= 20;
			}
		}
		else if( normY < 0 )
		{
			if( vY < 0 )
			{
				vector.y = 0;
				vector.x *= 20;
			}
		}
		else if( normY > 0 )
		{
			if( vY > 0 )
			{
				vector.y = 0;
				vector.x *= 20;
			}
		}

		return;
	}




	/**
	 * Removes the component of unitVector which is in the direction of the normal vector and sets the length of the unit to 1.
	 * @param unitVector Any vector, should be a unit vector because it will end up a unit vector in the end;
	 * @param normal This vector MUST only be one of the vectors [1,0] or [0,1] or [-1,0] or [1,0].
	 * @return  The vector unitVector with only one component.
	 */
	protected vector removeUnitVectorsCompInDirOfNormal( vector unitVector , vector normal )
	{
		if( normal == null )
		{
			return unitVector;
		}

		if( normal.x != 0 )
		{
			if( unitVector.y > 0 )
			{
				unitVector.set( 0 , 1 );
			}
			else
			{
				unitVector.set( 0 , -1 );
			}
		}
		else
		{
			if( unitVector.x > 0 )
			{
				unitVector.set( 1 , 0 );
			}
			else
			{
				unitVector.set( -1 , 0 );
			}
		}
		return unitVector;
	}



	private final vector dirVector = new vector();
	void walk()
	{
		if( !driver.isLookDirectionLocked() )
		{
			if ( d != null )
				driver.setLookDirection( d );
			else
				driver.setLookDirection( vector.getDirection4(dirVector.set(velocity).normalize()) );
			//driver.setLookDirection( Vector.getDirection4( new Vector(velocity).normalize() ) );

		}
		if( lastPlayedWalkingSound + 300 < GameTime.getTime() )
		{
			//Sounds.playWalkingSound();
			lastPlayedWalkingSound = GameTime.getTime();
		}


		position.add( velocity.x * speedCheatX , velocity.y * speedCheatY );

		driver.checkBounds();
	}




	public static float getSpeedCheatX()
	{
		return speedCheatX;
	}


	public static void setSpeedCheatX( float speedCheatX )
	{
		if( speedCheatX > 0 && speedCheatX < 10 )
		{
			MovingTechnique.speedCheatX = speedCheatX;
		}

	}


	public static float getSpeedCheatY()
	{
		return speedCheatY;
	}


	public static void setSpeedCheatY(float speedCheatY)
	{
		if( speedCheatY > 0 && speedCheatY < 10 )
		{
			MovingTechnique.speedCheatY = speedCheatY;
		}

	}




	public vector getForce() {
		return force;
	}





	public void setForce(vector force) {
		this.force = force;
	}





	public void nullify()
	{
		driver = null;
		position=null;
		velocity=null;
		inter=null;
		end=null;
	}

	public void setTeam(Teams team2) {
		team = team2;
	}



}
