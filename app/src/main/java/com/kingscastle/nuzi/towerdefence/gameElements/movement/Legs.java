package com.kingscastle.nuzi.towerdefence.gameElements.movement;


import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.GameElement;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.Humanoid;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.orders.Stance;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameElements.movement.MovementTechniqueParams.MovementType;
import com.kingscastle.nuzi.towerdefence.gameElements.movement.pathing.Path;
import com.kingscastle.nuzi.towerdefence.gameUtils.VectorMutator;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.teams.Teams;

import java.util.Random;

public class Legs
{
	private static final String TAG = null;
	private static final Random rand = new Random();
	private static final float smallDistance = 10*10* Rpg.getDp()*Rpg.getDp();


	private Humanoid driver;
	private float max_speed;
	private float max_force;
	private boolean walking;
	private MovingTechnique technique;
	//private Avoid avoid;
	//private DpadMove dpadMove;
	private final MovementTechniqueParams params;

	private long lastMoved;
	private float targetDistSquared;

	private long lastCheckedLocation;
	private final long timeBetweenLocationChecks = 1000;
	private long nextDistToDestCheckAt;
	private long stopWanderingAt;
	private long startWanderingAt;

	private boolean wandering = false;
	private MM mm;

	public Legs( Humanoid m )
	{
		driver = m;
		technique = new MovingTechnique( driver );
		params = new MovementTechniqueParams();
		params.setSpeed( m.getLQ().getSpeed() );
		params.setForce( m.getLQ().getForce() );
	}


	//	public boolean act( MovementTechniqueParams targetingParams )
	//	{
	//
	//		if( targetingParams == null || ( targetingParams.getLocationOfInterest() == null && targetingParams.getInDirection() == null ) || driver instanceof Building )
	//		{
	//			return false;
	//		}
	//
	//		boolean legsHaveActed = false;
	//
	//		if( targetingParams.getLocationOfInterest() != null )
	//		{
	//			Vector loc = targetingParams.getLocationOfInterest();
	//
	//			if( targetingParams.getDirection() == Direction.TOWARDS_LOCATION  )
	//			{
	//
	//				if( nextDistToDestCheckAt < GameTime.getTime() )
	//				{
	//					if( loc.distanceSquared( driver.loc ) <= smallDistance )
	//					{
	//						driver.loc.set( loc.getX() , loc.getY() );
	//						driver.updateArea();
	//						driver.setDestination( null );
	//						driver.setMovingIntoFormation( false );
	//						technique.getVelocity().set( 0 , 0 );
	//						walking = false;
	//
	//					}
	//					nextDistToDestCheckAt = GameTime.getTime() + 150;
	//				}
	//				else
	//				{
	//					moveTowards( this , loc , targetingParams , technique );
	//					legsHaveActed = true;
	//				}
	//
	//			}
	//			else if ( targetingParams.getDirection() == Direction.TOWARDS_ENEMY  )
	//			{
	//
	//				targetDistSquared = driver.loc.distanceSquared( loc );
	//
	//
	//				if( targetDistSquared < driver.getAQ().getStaysAtDistanceSquared() )
	//				{
	//					//	System.out.println(driver + ": target distance lower then stayAtDistance, moving away");
	//					//					if( !( driver instanceof Healer && target.getTeam() == driver.getTeam() ) )
	//					//					{
	//					//						moveAwayFrom(this,target.loc,targetingParams,technique);
	//					//						legsHaveActed = true;
	//					//					}
	//				}
	//				else if( targetDistSquared >= driver.getAQ().getAttackRangeSquared() )
	//				{
	//					//	System.out.println(driver + ": target outside attack range, moving towards target");
	//					//						if( !( driver instanceof Healer && target.getTeam() != driver.getTeam() ) )
	//					//						{
	//					//							moveTowards(this,target.loc,targetingParams,technique);
	//					//							legsHaveActed = true;
	//					//						}
	//				}
	//				lastCheckedLocation=GameTime.getTime();
	//			}
	//			else
	//			{
	//				if(itIsTimeToCheckLocation()
	//						&& driver.getStayHere() !=null && !driver.loc.equals(driver.getStayHere()) )
	//				{
	//					driver.setDestination(driver.getStayHere());
	//					lastCheckedLocation=GameTime.getTime();
	//				}
	//				else
	//				{
	//
	//					if( wandering )
	//					{
	//						wander(this , targetingParams , technique);
	//						legsHaveActed = true;
	//
	//						if( lastStartedWandering + 7000 < GameTime.getTime() )
	//						{
	//							targetingParams.setInDirection( null );
	//							lastStoppedWandering = GameTime.getTime();
	//							wandering = false;
	//						}
	//
	//					}
	//					else
	//					{
	//						if( lastStoppedWandering + 5000 < GameTime.getTime() )
	//						{
	//							wandering = true;
	//							lastStartedWandering = GameTime.getTime();
	//						}
	//					}
	//
	//				}
	//				targetDistSquared=0;
	//			}
	//
	//		}
	//
	//
	//		return legsHaveActed;
	//	}
	//



	public boolean act()
	{
		return act( driver.getDestination() , driver.getTarget() );
	}


	public boolean act( LivingThing target )
	{
		return act( null , target );
	}


	public boolean act( vector loc )
	{
		return act( loc , null );
	}

	public boolean act( vector loc , LivingThing target , Stance stance )
	{
		switch( stance )
		{


		case HOLD_POSITION:
			if( itIsTimeToCheckLocation()
					&& driver.getHoldThisPosition() != null && driver.loc.distanceSquared( driver.getHoldThisPosition() ) > driver.getAQ().getFocusRangeSquared() )
			{
				driver.setDestination( driver.getHoldThisPosition() );
				lastCheckedLocation = GameTime.getTime();
			}
		default:
		case FREE:
			return act( loc , target );

		case PLAYING_THE_OBJECTIVE:
			return act( loc , target );

		}
	}


	boolean act(vector loc, LivingThing target)
	{
		boolean legsHaveActed = false;

		if( loc != null )
		{
			if( nextDistToDestCheckAt < GameTime.getTime() )
			{
				if( loc.distanceSquared( driver.loc ) < smallDistance )
				{
					driver.loc.set( loc.x , loc.y );
					driver.updateArea();
					driver.setDestination( null );
					driver.setCasualDestination( null );
					driver.setMovingIntoFormation( false );
					technique.getVelocity().set( 0 , 0 );
					walking = false;
				}

				nextDistToDestCheckAt = GameTime.getTime() + 150;
			}
			else
			{
				moveTowards( this , loc , params , technique );
				legsHaveActed = true;
			}
		}
		else
		{
			if( target != null )
			{

				targetDistSquared = driver.loc.distanceSquared( target.area );


				if( targetDistSquared < driver.getAQ().getStaysAtDistanceSquared() )
				{
					//	System.out.println(driver + ": target distance lower then stayAtDistance, moving away");
					//if( !( driver instanceof Healer && target.getTeamName() == driver.getTeamName() ) )
					//{
					moveAwayFrom( this , target.loc , params , technique );
					legsHaveActed = true;
					//}
				}
				else if( targetDistSquared >= driver.getAQ().getAttackRangeSquared() )
				{
					//	System.out.println(driver + ": target outside attack range, moving towards target");
					//if( !( driver instanceof Healer && target.getTeamName() != driver.getTeamName() ) )
					//{
					moveTowards( this , target.loc , params , technique );
					legsHaveActed = true;
					//}
				}
				lastCheckedLocation = GameTime.getTime();
			}
			else
			{
				if( itIsTimeToCheckLocation()
						&& driver.getStayHere() != null && !driver.loc.equals( driver.getStayHere() ) )
				{
					driver.setCasualDestination( driver.getStayHere() );
					lastCheckedLocation = GameTime.getTime();
				}
				else
				{

					if( wandering )
					{
						wander( this , params , technique );
						legsHaveActed = true;

						if( stopWanderingAt < GameTime.getTime() )
						{
							params.setInDirection( null );
							startWanderingAt = GameTime.getTime() + (int) (Math.random() * 5000);
							wandering = false;
						}

					}
					else
					{
						if( startWanderingAt < GameTime.getTime() )
						{
							wandering = true;
							stopWanderingAt = GameTime.getTime() + (int) (Math.random() * 5000);
						}
					}


				}
				targetDistSquared = 0;
			}
		}

		return legsHaveActed;

	}





	public boolean stayAtLeastAsFarAway( GameElement target , float f )
	{
		if( target == null )
			return false;


		boolean legsHaveActed = false;

		targetDistSquared = driver.loc.distanceSquared( target.area );

		if( targetDistSquared < f )
		{
			moveAwayFrom( this , target.loc , params , technique );
			legsHaveActed = true;
		}

		lastCheckedLocation = GameTime.getTime();
		return legsHaveActed;
	}





	public boolean stayAtADistanceFrom( GameElement target , int rangeSquared )
	{
		if( target == null )
			return false;

		boolean legsHaveActed = false;

		targetDistSquared = driver.loc.distanceSquared( target.area );

		if( targetDistSquared < rangeSquared )
		{
			moveAwayFrom( this , target.loc , params , technique );
			legsHaveActed = true;
		}
		else if( targetDistSquared >= rangeSquared )
		{
			moveTowards(this,target.loc,params,technique);
			legsHaveActed = true;
		}

		lastCheckedLocation=GameTime.getTime();
		return legsHaveActed;
	}





	public boolean stayWithinADistanceof( GameElement target , float f )
	{
		if ( target == null)
			return false;


		boolean legsHaveActed = false;

		targetDistSquared = driver.loc.distanceSquared( target.area );

		if( targetDistSquared >= f )
		{
			moveTowards( this , target.loc , params , technique );
			legsHaveActed = true;
		}

		lastCheckedLocation= GameTime.getTime();
		return legsHaveActed;
	}






	private boolean itIsTimeToCheckLocation(){
		return lastCheckedLocation + timeBetweenLocationChecks < GameTime.getTime();
	}


	public void act( vector inDirection , boolean causedByDpad ){
		if( lastMoved + 20 < GameTime.getTime() )
			dPadMove( this , inDirection , params , technique );
	}



	public boolean wander()
	{
		boolean legsHaveActed = false;

		if( wandering )
		{
			wander( this , params , technique );
			legsHaveActed = true;

			if( stopWanderingAt < GameTime.getTime() ){
				params.setInDirection( null );
				startWanderingAt = GameTime.getTime() + 10000 + (int) (Math.random() * 10000);
				wandering = false;
			}

		}
		else
		{
			if( startWanderingAt < GameTime.getTime() ){
				wandering = true;
				stopWanderingAt = GameTime.getTime() + (int) (Math.random() * 2000);
			}
		}

		return legsHaveActed;
	}



	private static void wander( Legs legs, MovementTechniqueParams params , MovingTechnique technique )
	{
		if( legs.getDriver().getTeamName() == Teams.BLUE )
			return;

		float prevSpeed = legs.driver.lq.getSpeed();

		try{
			legs.driver.lq.setSpeed(1f);

			if( params.getInDirection() == null)
				params.setInDirection( new vector( - 10 +  rand.nextInt( 20 ) , - 10 +  rand.nextInt( 20 ) ));

			else
				VectorMutator.randomlyRotate(params.getInDirection(), 5, 5);


			params.setMovementType( MovementType.DPAD );

			dPadMove( legs , params.getInDirection() , params , technique );
		}
		finally{
			legs.driver.lq.setSpeed( prevSpeed );
		}

	}


	public void moveTowards( vector loc ){
		moveTowards( this , loc , params , technique );
	}


	private static void moveTowards(Legs legs, vector loc, MovementTechniqueParams params, MovingTechnique technique){

		params.setMovementType(MovementType.SEEK);
		params.setLocationOfInterest(loc);

		doYourThing(legs,technique,params);

	}

	public void moveAwayFrom( vector loc ){
		moveAwayFrom( this , loc , params , technique );
	}



	public void followPath()
	{
		followPath( this , params , technique );
		//		if( driver.getTeamName() == Teams.BLUE )
		//		{
		//			//Log.d ( TAG , "followPath()");
		//		}
	}

	void followPath(Legs legs, MovementTechniqueParams params, MovingTechnique technique)
	{
		params.setMovementType( MovementType.FOLLOW_PATH );
		doYourThing( legs , technique , params ) ;
	}


	private static void moveAwayFrom(Legs legs, vector loc, MovementTechniqueParams params, MovingTechnique technique)
	{
		params.setMovementType( MovementType.AVOID );
		params.setLocationOfInterest( loc );
		doYourThing( legs , technique , params ) ;
	}


	private static void dPadMove(Legs legs, vector inDirection, MovementTechniqueParams params, MovingTechnique technique)
	{
		params.setMovementType( MovementType.DPAD );
		params.setInDirection( inDirection );
		doYourThing( legs , technique , params );
	}


	private static void doYourThing( Legs legs , MovingTechnique technique , MovementTechniqueParams params )
	{
		params.setSpeed( legs.driver.lq.getSpeed() );
		params.setForce( legs.driver.lq.getForce() );
		technique.act( params );
		legs.lastMoved = GameTime.getTime();
		legs.walking = true;
		legs.driver.updateArea();
	}


	//	private static boolean amICloseEnoughTo(Vector driversLocation , Vector destination )
	//	{
	//		if( destination.distanceSquared( driversLocation ) <= smallDistance )
	//			return true;
	//		else
	//			return false;
	//	}


	public vector getVelocity(){
		return technique.getVelocity();
	}

	public boolean areYouStillWalking()
	{
		if( lastMoved + 200 < GameTime.getTime() )
		{
			technique.getVelocity().set( 0 , 0 );
			return walking = false;
		}
		else
			return true;
	}

	public long getLastMoved()
	{
		return lastMoved;
	}

	public void setLastMoved(long lastMoved) {
		this.lastMoved = lastMoved;
	}

	public float getTargetDistSquared() {
		return targetDistSquared;
	}
	public MovingTechnique getMovingTechnique(){
		return technique;
	}


	LivingThing getDriver() {
		return driver;
	}
	public void setDriver(Humanoid driver) {
		this.driver = driver;
	}

	public float getMax_speed() {
		return max_speed;
	}
	public void setMax_speed(float max_speed) {
		this.max_speed = max_speed;
	}

	public float getMax_force() {
		return max_force;
	}
	public void setMax_force(float max_force) {
		this.max_force = max_force;
	}



	public Path getPathToFollow(){
		return params.getPathToFollow();
	}

	public void setPathToFollow( Path pathToFollow ) {
		params.setPathToFollow( pathToFollow );
		if( pathToFollow != null )
			params.setMovementType( MovementType.FOLLOW_PATH );
	}



	public void nullify()
	{
		driver=null;
		technique=null;
	}


	public void setMm(MM mm) {
		this.mm = mm;
		params.setMM(mm);
	}
}

