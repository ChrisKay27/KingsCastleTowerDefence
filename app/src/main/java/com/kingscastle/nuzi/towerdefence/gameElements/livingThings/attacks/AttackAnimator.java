package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks;

import android.graphics.Paint;

import com.kingscastle.nuzi.towerdefence.effects.animations.Anim;
import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.framework.Graphics;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.Humanoid;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;

public abstract class AttackAnimator extends Anim
{
	private static final Paint dstOverPaint = Rpg.getDstOverPaint();

	private final Humanoid owner;
	private int framePeriod;
	private final int attackAtFrame;
	private int currentROA;
	private int onFrame = 0;


	private long lastImageChange;
	Image standingStillEast;
	Image standingStillNorth;
	Image standingStillSouth;
	Image standingStillWest;
	private Image currentImage;

	ArrayList<Image> imagesEast;
	ArrayList<Image> imagesWest;
	ArrayList<Image> imagesSouth;
	ArrayList<Image> imagesNorth;

	private vector attackingInDirectionUnitVector;
	private vector offset = new vector();

	private vector EOffset = new vector();
	private vector WOffset = new vector();
	private vector SOffset = new vector();
	private vector NOffset = new vector();


	private int timeFromAttackStartTillDoAttack;

	//public abstract void doAttack();
	public abstract void playSound();
	protected abstract void loadSounds();


	/**
	 * Sets the owner of this attackAnimator to the param owner, sets the location of this anim
	 * to the owners loc, sets the attack reference to the ownersAttack param and calculates the framePeriod based on the owners getROA().
	 * @param owner LivingThing which this anim is paired to.
	 * @param attackAtFrame frame (starting at 0) when doAttack() is called;
	 */
	AttackAnimator(Humanoid owner, int attackAtFrame)
	{
		loadSounds();
		this.owner = owner;
		setLoc( owner.loc );

		framePeriod = owner.getAQ().getROF() / 20;
		currentROA = owner.getAQ().getROF();

		timeFromAttackStartTillDoAttack = framePeriod * attackAtFrame;

		this.attackAtFrame = attackAtFrame;
	}



	/**
	 * Tells the animator to attack in the direction of inDirectionUnitVector.
	 * @param inDirectionUnitVector MUST be a vector with magnitude of 1 (ie. A unit vector).
	 */
	public void attackFromUnitVector( vector inDirectionUnitVector )
	{
		if( currentROA != owner.getAQ().getROF() )
		{
			framePeriod = owner.getAQ().getROF()/20;
			currentROA = owner.getAQ().getROF();
			timeFromAttackStartTillDoAttack = framePeriod * attackAtFrame;
		}

		++onFrame;
		attackingInDirectionUnitVector = inDirectionUnitVector;
		owner.lockLookDirectionFromUnitVector( inDirectionUnitVector );
	}

	private final vector tempUnitVector = new vector();
	/**
	 * Tells the animator to attack in the direction of inDirection.
	 * @param inDirection Does not have to be a unit vector, you should use attackFromUnitVector(...) if you have already computed the unit vector
	 * to save on unnecessary repeated calculations.
	 */
	public void attack( vector inDirection )
	{
		tempUnitVector.set( inDirection );
		tempUnitVector.turnIntoUnitVector();
		this.attackFromUnitVector( tempUnitVector );
	}


	/**
	 * Tells the animator to attack, if the owner of this animator's target is null the method returns quickly.
	 */
	public void attack()
	{
		LivingThing target = owner.getTarget();
		if( target == null ){
			owner.unlockLookDirection();
			return;
		}else
		{
			tempUnitVector.set( target.loc.x - owner.loc.x ,  target.loc.y - owner.loc.y );
			tempUnitVector.turnIntoUnitVector();
			this.attackFromUnitVector( tempUnitVector );
		}

	}


	/**
	 * looks at the LivingThing and determines the image to return
	 */
	@Override
	public Image getImage()
	{
		switch ( owner.getLookDirection() )
		{
		default:
		case W:
			if( onFrame == 0 || imagesWest == null )
				return standingStillWest;

			if ( onFrame > 0 )
			{
				if ( timeToChangeImage() )
				{
					++onFrame;
					//					if( onFrame == attackAtFrame )
					//					{
					//						doAttack();
					//					}
					if( onFrame >= imagesWest.size() )
					{
						onFrame -= imagesWest.size();
						owner.unlockLookDirection();
						return standingStillWest;
					}
					lastImageChange = GameTime.getTime();
				}
			}
			return imagesWest.get(onFrame>imagesWest.size()-1?imagesWest.size()-1:onFrame-1);


		case S:
			if( onFrame == 0 || imagesSouth == null )
				return standingStillSouth;

			if ( onFrame > 0 )
			{
				if ( timeToChangeImage() )
				{
					++onFrame;
					//					if( onFrame == attackAtFrame )
					//					{
					//						doAttack();
					//					}
					if( onFrame >= imagesSouth.size())
					{
						onFrame -= imagesSouth.size();
						owner.unlockLookDirection();
						return standingStillSouth;
					}
					lastImageChange = GameTime.getTime();
				}
			}
			return imagesSouth.get( onFrame > imagesSouth.size() - 1 ? imagesSouth.size() - 1 : onFrame - 1 );


		case N:
			if( onFrame == 0 || imagesNorth == null )
				return standingStillNorth;

			if ( onFrame > 0 )
			{
				if ( timeToChangeImage() )
				{
					++onFrame;
					//					if( onFrame == attackAtFrame )
					//					{
					//						doAttack();
					//					}
					if( onFrame >= imagesNorth.size() )
					{
						onFrame-=imagesNorth.size();
						owner.unlockLookDirection();
						return standingStillNorth;
					}
					lastImageChange=GameTime.getTime();
				}
			}
			return imagesNorth.get( onFrame > imagesNorth.size() - 1 ? imagesNorth.size()-1 : onFrame-1 );

		case E:
			if( onFrame == 0 || imagesEast == null )
				return standingStillEast;

			if ( onFrame > 0 )
			{
				if ( timeToChangeImage() )
				{
					++onFrame;
					//					if( onFrame == attackAtFrame )
					//					{
					//						doAttack();
					//					}
					if( onFrame >= imagesEast.size() )
					{
						onFrame -= imagesEast.size();
						owner.unlockLookDirection();
						return standingStillEast;
					}
					lastImageChange = GameTime.getTime();
				}
			}
			return imagesEast.get( onFrame > imagesEast.size() - 1 ? imagesEast.size() - 1 : onFrame - 1) ;
		}
	}



	private boolean timeToChangeImage()
	{
		return GameTime.getTime() - lastImageChange > framePeriod ; //FreakButton.tweakThisTheFuckkOut( framePeriod ) ;
	}




	@Override
	public void paint( Graphics g , vector v )
	{
		if( !isVisible() )
			return;

		currentImage = getImage();

		if( currentImage != null )
		{
			switch( owner.getLookDirection() )
			{
			default:
			case E: offset=EOffset;
			break;
			case N: offset=NOffset;
			break;
			case S: offset=SOffset;
			break;
			case W: offset=WOffset;
			break;
			}
			g.drawImage( currentImage , v.x - currentImage.getWidthDiv2() , v.y - currentImage.getHeightDiv2() , offset , dstOverPaint );
		}


	}




	@Override
	public boolean isOver()
	{
		return owner.isDead();
	}

	@Override
	public boolean act()
	{
		return isOver();
	}



	/**
	 * @return the eOffset
	 */
	vector getEOffset() {
		return EOffset;
	}
	/**
	 * @param eOffset the eOffset to set
	 */
	public void setEOffset(vector eOffset) {
		EOffset = eOffset;
	}
	/**
	 * @return the wOffset
	 */
	vector getWOffset() {
		return WOffset;
	}
	/**
	 * @param wOffset the wOffset to set
	 */
	public void setWOffset(vector wOffset) {
		WOffset = wOffset;
	}
	/**
	 * @return the sOffset
	 */
	vector getSOffset() {
		return SOffset;
	}
	/**
	 * @param sOffset the sOffset to set
	 */
	public void setSOffset(vector sOffset) {
		SOffset = sOffset;
	}
	/**
	 * @return the nOffset
	 */
	vector getNOffset() {
		return NOffset;
	}
	/**
	 * @param nOffset the nOffset to set
	 */
	public void setNOffset(vector nOffset) {
		NOffset = nOffset;
	}
	public int getTimeFromAttackStartTillDoAttack() {
		return timeFromAttackStartTillDoAttack;
	}
	public void setTimeFromAttackStartTillDoAttack(
			int timeFromAttackStartTillDoAttack) {
		this.timeFromAttackStartTillDoAttack = timeFromAttackStartTillDoAttack;
	}

	public vector getAttackingInDirectionUnitVector() {
		return attackingInDirectionUnitVector;
	}


}
