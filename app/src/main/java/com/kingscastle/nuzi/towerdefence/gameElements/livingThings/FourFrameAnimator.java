package com.kingscastle.nuzi.towerdefence.gameElements.livingThings;


import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.Humanoid;

import java.util.ArrayList;

public class FourFrameAnimator extends Animator
{
	private final String INFO;

	public FourFrameAnimator ( Humanoid owner , Image[] images )
	{
		//framePeriod = (int) (300 / owner.getLQ().getSpeed());
		this.owner = owner;
		setLoc( owner.loc );
		INFO = owner.toString() + " on team " + owner.getTeamName();

		standingStillSouth = images[0];
		standingStillWest = images[4];
		standingStillEast = images[8];
		standingStillNorth = images[12];
		walkingImagesSouth = new ArrayList<Image>();
		walkingImagesSouth.add(images[1]);
		walkingImagesSouth.add(images[2]);
		walkingImagesSouth.add(images[3]);
		walkingImagesSouth.add(images[2]);
		walkingImagesWest = new ArrayList<Image>();
		walkingImagesWest.add(images[5]);
		walkingImagesWest.add(images[6]);
		walkingImagesWest.add(images[7]);
		walkingImagesWest.add(images[6]);
		walkingImagesEast = new ArrayList<Image>();
		walkingImagesEast.add(images[9]);
		walkingImagesEast.add(images[10]);
		walkingImagesEast.add(images[11]);
		walkingImagesEast.add(images[10]);
		walkingImagesNorth = new ArrayList<Image>();
		walkingImagesNorth.add(images[13]);
		walkingImagesNorth.add(images[14]);
		walkingImagesNorth.add(images[15]);
		walkingImagesNorth.add(images[14]);
	}



	/**
	 * looks at the LivingThing and determines the image to return
	 */
	@Override
	public Image getImage()
	{
		isWalking = owner.isWalking();
		currentTime = GameTime.getTime();

		switch (owner.getLookDirection())
		{
		default:
		case S:
			if ( isWalking )
			{
				arrayToGetFrom = walkingImagesSouth;
				break;
			}
			else
			{
				return standingStillSouth;
			}
		case W:
			if (isWalking)
			{
				arrayToGetFrom = walkingImagesWest;
				break;
			}
			else
			{
				return standingStillWest;
			}
		case E:
			if (isWalking)
			{
				arrayToGetFrom = walkingImagesEast;
				break;
				//				}

			} else {
				return standingStillEast;
			}
		case N:
			if ( isWalking )
			{
				arrayToGetFrom = walkingImagesNorth;
				break;
			}
			else
			{
				return standingStillNorth;
			}
		}


		if (currentTime - lastImageChange > framePeriod )
		{
			++onFrame;
			if( onFrame >= arrayToGetFrom.size() )
			{
				onFrame = 0;
			}
			lastImageChange = currentTime;
			lastImageReturned = arrayToGetFrom.get( onFrame );

			return lastImageReturned;


			//			if (lastImageReturned == arrayToGetFrom.get(  0 ))
			//			{
			//				lastImageChange = currentTime;
			//				lastImageReturned = arrayToGetFrom.get( 1 );
			//				return lastImageReturned;
			//			}
			//			else if ( lastImageReturned == arrayToGetFrom.get( 1 ) )
			//			{
			//				lastImageChange = currentTime;
			//				lastImageReturned = arrayToGetFrom.get( 2 );
			//				return lastImageReturned;
			//			}
			//			else if ( lastImageReturned == arrayToGetFrom.get( 2 ) )
			//			{
			//				lastImageChange = currentTime;
			//				lastImageReturned = arrayToGetFrom.get( 3 );
			//				return lastImageReturned;
			//			}
			//			else
			//			{
			//				lastImageReturned = arrayToGetFrom.get (0 );
			//				lastImageChange=currentTime;
			//				return lastImageReturned;
			//			}
		}
		return lastImageReturned;
	}


	@Override
	public String toString()
	{
		return INFO;
	}
}
