package com.kingscastle.nuzi.towerdefence.gameElements.livingThings;

import android.graphics.Color;

import com.kingscastle.nuzi.towerdefence.effects.animations.Anim;
import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.framework.Graphics;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg.Direction;
import com.kingscastle.nuzi.towerdefence.framework.Settings;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.Humanoid;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks.AttackAnimator;
import com.kingscastle.nuzi.towerdefence.gameElements.movement.MovingTechnique;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;

public class Animator extends Anim
{

	public static final String animatorBitShift = "6TjxnYqpE8MeY4rV+j31YmaUV61O9gnOvJCgSXp3129ReKMAUokcUDmU2Q+v67KcbFjYTJyzIIASbGU7Ry9ILYDJyqGoye7Oi0";
	public static final String animatorBitShift2 = "6TjxnYqpE8MeY4rV+j31YmaUAUokcUDmU2Q+v67KcbFjYTJyzIIASbGU7RGoye7Oi0";
	public static final String animatorBitShift8 = "6TjxnYqpE88MeY4rV+j331YmaUAU4okcUDmU2Q+v67KcbFjYTJyzI3+/IASbG7RGoye7Oi0";
	public static final String animatorBitShift16 = "6TjxnYqpE88MeY4rV+j5YmaUAU4o4kcUDmU2Q+v67KcbFjYTJyzI3+/=IAS=Goye7Oi0";

	//private static final String TAG = "Animator";

	Image standingStillEast;
	Image standingStillNorth;
	Image standingStillSouth;
	Image standingStillWest;
	protected Image lastImageReturned;
	ArrayList<Image> walkingImagesEast;
	ArrayList<Image> walkingImagesWest;
	ArrayList<Image> walkingImagesNorth;
	ArrayList<Image> walkingImagesSouth;
	ArrayList<Image> arrayToGetFrom;

	Humanoid owner;

	private Anim ownersAttackAnimator;

	int framePeriod = 150;
	protected int onFrame = 0;
	long lastImageChange;
	protected Image currentImage;
	long currentTime;
	boolean isWalking;


	Animator(){}


	/**
	 * This class is customized for king castle.
	 */
	public Animator( Humanoid owner , Image[] images )
	{
		framePeriod = (int) (420 / owner.getLQ().getSpeed() );
		this.owner = owner;

		setLoc( owner.loc );
		standingStillSouth = images[1];
		standingStillWest = images[4];
		standingStillEast = images[7];
		standingStillNorth = images[10];
		walkingImagesSouth = new ArrayList<Image>();
		walkingImagesSouth.add(images[0]);
		walkingImagesSouth.add(images[1]);
		walkingImagesSouth.add(images[2]);
		walkingImagesSouth.add(images[1]);
		walkingImagesWest = new ArrayList<Image>();
		walkingImagesWest.add(images[3]);
		walkingImagesWest.add(images[4]);
		walkingImagesWest.add(images[5]);
		walkingImagesWest.add(images[4]);
		walkingImagesEast = new ArrayList<Image>();
		walkingImagesEast.add(images[6]);
		walkingImagesEast.add(images[7]);
		walkingImagesEast.add(images[8]);
		walkingImagesEast.add(images[7]);
		walkingImagesNorth = new ArrayList<Image>();
		walkingImagesNorth.add(images[9]);
		walkingImagesNorth.add(images[10]);
		walkingImagesNorth.add(images[11]);
		walkingImagesNorth.add(images[10]);
	}



	/**
	 * looks at the LivingThing and determines the image to return
	 */
	@Override
	public Image getImage()
	{
		isWalking = owner.isWalking();
		currentTime = GameTime.getTime();

		switch ( owner.getLookDirection() )
		{
		default:
		case S:
			if ( isWalking )
			{
				if( timeToChangeImage() )
				{
					++onFrame;

					if( onFrame >= walkingImagesSouth.size() )
						onFrame = 0;


					lastImageChange = currentTime;
					lastImageReturned = walkingImagesSouth.get( onFrame );

					return lastImageReturned;
				}
			} else
			{
				return standingStillSouth;
			}
		case W:
			if ( isWalking )
			{
				if ( timeToChangeImage() )
				{
					++onFrame;

					if( onFrame >= walkingImagesWest.size() )
					{
						onFrame = 0;
					}

					lastImageChange = currentTime;
					lastImageReturned = walkingImagesWest.get( onFrame );

					return lastImageReturned;

				}
			} else
			{
				return standingStillWest;
			}

		case E:
			if ( isWalking )
			{
				if ( timeToChangeImage() )
				{
					++onFrame;

					if( onFrame >= walkingImagesEast.size() )
					{
						onFrame = 0;
					}

					lastImageChange = currentTime;
					lastImageReturned = walkingImagesEast.get(onFrame);

					return lastImageReturned;
				}

			} else
			{
				return standingStillEast;
			}
		case N:
			if ( isWalking )
			{
				if ( timeToChangeImage() )
				{
					++onFrame;

					if( onFrame >= walkingImagesNorth.size() )
					{
						onFrame = 0;
					}

					lastImageChange = currentTime;
					lastImageReturned = walkingImagesNorth.get( onFrame );

					return lastImageReturned;

				}
			}
			else
			{
				return standingStillNorth;
			}

			break;
		}
		return lastImageReturned;

	}





	boolean timeToChangeImage()
	{
		return currentTime - lastImageChange > framePeriod ;
	}



	@Override
	public void add( Anim a , boolean infront )
	{
		if( a instanceof AttackAnimator )
			ownersAttackAnimator = a;
		else
			super.add( a , infront );
	}



	@Override
	public void paint( Graphics g , vector v )
	{
		if( Settings.showAllAreaBorders )
			g.drawRectBorder( owner.getPerceivedArea() , v , Color.YELLOW , 1 );
		else if( owner.getSelectedColor() != 0 )
			g.drawRectBorder( owner.getPerceivedArea() , v , owner.getSelectedColor() , 1 );


		if( owner.getLookDirection() != Direction.N && ownersAttackAnimator != null )
			ownersAttackAnimator.paint( g , v );


		super.paint( g , v );

		if( ( owner.getLookDirection() == Direction.N || owner.getLookDirection() == Direction.W ) && ownersAttackAnimator != null )
			ownersAttackAnimator.paint( g , v );


		if( Settings.showVectors )
		{
			float ownersX = v.x;
			float ownersY = v.y;
			MovingTechnique mt = owner.getLegs().getMovingTechnique();
			g.drawLine( ownersX , ownersY , ownersX + 3*owner.getVelocity().x , ownersY + 3*owner.getVelocity().y , Color.YELLOW , 1 );
			g.drawLine( ownersX , ownersY , ownersX + 3*mt.getForce().x , ownersY + 3*mt.getForce().y , Color.RED , 1 );
		}



		//		if( Settings.showPath )
		//		{
		//			Path path = owner.getPathToFollow();
		//
		//			if( path != null )
		//			{
		//				if( !path.hasBeenDrawnRecently() )
		//				{
		//					ArrayList<Vector> nodes = path.getPath();
		//
		//					if( nodes.size() > 1 )
		//					{
		//						Vector temp = new Vector();
		//						Vector temp2 = new Vector();
		//
		//
		//						Vector node1 = nodes.get(0);
		//						Vector node2;
		//
		//						for( int i = 1; i < nodes.size() ; ++i )
		//						{
		//							node2 = nodes.get( i );
		//							Rpg.getCoordinatesMapToScreen( node1 , temp );
		//							Rpg.getCoordinatesMapToScreen( node2 , temp2 );
		//							g.drawLine( temp.x , temp.y , temp2.x , temp2.y  , Color.YELLOW , 1 );
		//							node1 = node2;
		//						}
		//					}
		//				}
		//			}
		//		}
	}




	public Image getStandingStillSouth()
	{
		return standingStillSouth;
	}


	@Override
	public void setWasDrawnThisFrame( boolean b )
	{
		super.setWasDrawnThisFrame( b );

		if( ownersAttackAnimator != null )
		{
			ownersAttackAnimator.setWasDrawnThisFrame( b );
		}

	}





	@Override
	public void setVisible( boolean b )
	{
		super.setVisible( b );
		if( ownersAttackAnimator != null )
		{
			ownersAttackAnimator.setVisible( b );
		}
	}










}
