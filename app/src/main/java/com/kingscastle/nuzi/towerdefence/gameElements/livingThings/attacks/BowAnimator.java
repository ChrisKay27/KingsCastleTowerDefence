package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks;


import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.Humanoid;
import com.kingscastle.nuzi.towerdefence.gameElements.projectiles.Projectile;
import com.kingscastle.nuzi.towerdefence.gameElements.projectiles.ProjectileParams;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;

public class BowAnimator extends AttackAnimator
{

	public enum BowTypes
	{
		Bow , GreatBow , RecurveBow , CrossBow
	}

	private static ArrayList<Image> shootingBowImagesEast;
    private static ArrayList<Image> shootingBowImagesWest;
    private static ArrayList<Image> shootingBowImagesSouth;
    private static ArrayList<Image> shootingBowImagesNorth;
	private static ArrayList<Image> shootingGreatBowImagesEast;
    private static ArrayList<Image> shootingGreatBowImagesWest;
    private static ArrayList<Image> shootingGreatBowImagesSouth;
    private static ArrayList<Image> shootingGreatBowImagesNorth;
	private static ArrayList<Image> shootingRecurveBowImagesEast;
    private static ArrayList<Image> shootingRecurveBowImagesWest;
    private static ArrayList<Image> shootingRecurveBowImagesSouth;
    private static ArrayList<Image> shootingRecurveBowImagesNorth;
	private static ArrayList<Image> shootingCrossBowImagesEast;
    private static ArrayList<Image> shootingCrossBowImagesWest;
    private static ArrayList<Image> shootingCrossBowImagesSouth;
    protected static ArrayList<Image> shootingCrossBowImagesNorth;

	private static ArrayList<Image> staticBowImages;
    private static ArrayList<Image> staticGreatBowImages;
    private static ArrayList<Image> staticRecurveBowImages;
    private static ArrayList<Image> staticCrossBowImages;


	//protected static Sound bowSound;

	private Projectile nextArrow;

	private final ProjectileParams nextArrowParams;


	public BowAnimator(Humanoid owner,BowTypes bowType)
	{
		super(owner,9);
		loadImages( bowType );
		nextArrowParams = new ProjectileParams();
		nextArrowParams.setShooter(owner);
	}

	public BowAnimator(Humanoid owner)
	{
		this( owner , BowTypes.Bow );
		loadImages( BowTypes.Bow );
	}

	public void attack(vector inDirection, Projectile arrow)
	{
		nextArrow=arrow;
		super.attack(inDirection);
	}

	public void attackFromUnitVector(
			vector unitVector, Projectile arrow)
	{
		nextArrow=arrow;
		super.attackFromUnitVector(unitVector);
	}

	public void attack(Projectile arrow)
	{
		nextArrow=arrow;
		super.attack();
	}

	//	@Override
	//	public void doAttack()
	//	{
	//
	//		Rpg.getMM().add( getNewArrow() );
	//
	//	}

	//	private Projectile getNewArrow()
	//	{
	//
	//		if( attackingInDirectionUnitVector != null )
	//		{
	//			Projectile p = nextArrow.newInstance( owner , attackingInDirectionUnitVector );
	//			attackingInDirectionUnitVector = null ;
	//			return p;
	//		}
	//		else
	//		{
	//			return nextArrow.newInstance( owner , null , owner.getTarget() );
	//		}
	//	}

	//	public void paint(Graphics g, Vector v) {
	//		if(getImage()!=null){
	//			switch (owner.getLookDirection()){
	//			case N:
	//				g.drawImage(getImage(), v.getIntX()-getImage().getWidthDiv2(), v.getIntY()-getImage().getHeightDiv2());
	//				break;
	//			case W:
	//				g.drawImage(getImage(), v.getIntX()-getImage().getWidthDiv2(), v.getIntY()-getImage().getHeightDiv2());
	//				break;
	//			case S:
	//				g.drawImage(getImage(), v.getIntX()-getImage().getWidthDiv2(), v.getIntY()-getImage().getHeightDiv2());
	//				break;
	//			case E:
	//				g.drawImage(getImage(), v.getIntX()-getImage().getWidthDiv2(), v.getIntY()-getImage().getHeightDiv2());
	//				break;
	//			}
	//		}
	//	}


	@Override
	public boolean isOver()
	{
		return false;
	}



	private void loadImages( BowTypes bowType )
	{

		switch(bowType)
		{
		default:
		case Bow:

			if(staticBowImages==null)
			{
				staticBowImages= Assets.loadAnimationImages(R.drawable.bow_with_reverse, 10, 2);
				shootingBowImagesSouth = new ArrayList<Image>();
				shootingBowImagesSouth.add(staticBowImages.get(1));
				shootingBowImagesSouth.add(staticBowImages.get(2));
				shootingBowImagesSouth.add(staticBowImages.get(3));
				shootingBowImagesSouth.add(staticBowImages.get(4));
				shootingBowImagesSouth.add(staticBowImages.get(5));
				shootingBowImagesSouth.add(staticBowImages.get(6));
				shootingBowImagesSouth.add(staticBowImages.get(7));
				shootingBowImagesSouth.add(staticBowImages.get(8));
				shootingBowImagesSouth.add(staticBowImages.get(9));
				shootingBowImagesWest = shootingBowImagesSouth;
				shootingBowImagesEast = new ArrayList<Image>();
				shootingBowImagesEast.add(staticBowImages.get(19));
				shootingBowImagesEast.add(staticBowImages.get(18));
				shootingBowImagesEast.add(staticBowImages.get(17));
				shootingBowImagesEast.add(staticBowImages.get(16));
				shootingBowImagesEast.add(staticBowImages.get(15));
				shootingBowImagesEast.add(staticBowImages.get(14));
				shootingBowImagesEast.add(staticBowImages.get(13));
				shootingBowImagesEast.add(staticBowImages.get(12));
				shootingBowImagesEast.add(staticBowImages.get(11));
				shootingBowImagesNorth=shootingBowImagesEast;
			}
			imagesEast = shootingBowImagesEast;
			imagesWest = shootingBowImagesWest;
			imagesSouth = shootingBowImagesSouth;
			imagesNorth = shootingBowImagesSouth;
			standingStillSouth = staticBowImages.get(0);
			standingStillWest = standingStillSouth;
			standingStillEast = imagesEast.get(0);
			standingStillNorth=standingStillEast;
			break;

		case GreatBow:
			if( staticGreatBowImages == null )
			{
				staticGreatBowImages=Assets.loadAnimationImages(R.drawable.bow_with_reverse,10,2);
				shootingGreatBowImagesSouth = new ArrayList<Image>();
				shootingGreatBowImagesSouth.add(staticGreatBowImages.get(1));
				shootingGreatBowImagesSouth.add(staticGreatBowImages.get(2));
				shootingGreatBowImagesSouth.add(staticGreatBowImages.get(3));
				shootingGreatBowImagesSouth.add(staticGreatBowImages.get(4));
				shootingGreatBowImagesSouth.add(staticGreatBowImages.get(5));
				shootingGreatBowImagesSouth.add(staticGreatBowImages.get(6));
				shootingGreatBowImagesSouth.add(staticGreatBowImages.get(7));
				shootingGreatBowImagesSouth.add(staticGreatBowImages.get(8));
				shootingGreatBowImagesSouth.add(staticGreatBowImages.get(9));
				shootingGreatBowImagesWest = shootingGreatBowImagesSouth;
				shootingGreatBowImagesEast = new ArrayList<Image>();
				shootingGreatBowImagesEast.add(staticGreatBowImages.get(19));
				shootingGreatBowImagesEast.add(staticGreatBowImages.get(18));
				shootingGreatBowImagesEast.add(staticGreatBowImages.get(17));
				shootingGreatBowImagesEast.add(staticGreatBowImages.get(16));
				shootingGreatBowImagesEast.add(staticGreatBowImages.get(15));
				shootingGreatBowImagesEast.add(staticGreatBowImages.get(14));
				shootingGreatBowImagesEast.add(staticGreatBowImages.get(13));
				shootingGreatBowImagesEast.add(staticGreatBowImages.get(12));
				shootingGreatBowImagesEast.add(staticGreatBowImages.get(11));
				shootingGreatBowImagesNorth=shootingGreatBowImagesEast;
			}
			imagesEast = shootingGreatBowImagesEast;
			imagesWest = shootingGreatBowImagesWest;
			imagesSouth = shootingGreatBowImagesSouth;
			imagesNorth = shootingGreatBowImagesNorth;
			standingStillSouth = staticGreatBowImages.get(0);
			standingStillWest = standingStillSouth;
			standingStillEast = imagesEast.get(0);
			standingStillNorth=standingStillEast;

			break;
		case RecurveBow:
			if( staticRecurveBowImages == null )
			{
				staticRecurveBowImages=Assets.loadAnimationImages(R.drawable.recurvebow_with_reverse,10,2);
				shootingRecurveBowImagesSouth = new ArrayList<Image>();
				shootingRecurveBowImagesSouth.add(staticRecurveBowImages.get(1));
				shootingRecurveBowImagesSouth.add(staticRecurveBowImages.get(2));
				shootingRecurveBowImagesSouth.add(staticRecurveBowImages.get(3));
				shootingRecurveBowImagesSouth.add(staticRecurveBowImages.get(4));
				shootingRecurveBowImagesSouth.add(staticRecurveBowImages.get(5));
				shootingRecurveBowImagesSouth.add(staticRecurveBowImages.get(6));
				shootingRecurveBowImagesSouth.add(staticRecurveBowImages.get(7));
				shootingRecurveBowImagesSouth.add(staticRecurveBowImages.get(8));
				shootingRecurveBowImagesSouth.add(staticRecurveBowImages.get(9));
				shootingRecurveBowImagesWest = shootingRecurveBowImagesSouth;
				shootingRecurveBowImagesEast = new ArrayList<Image>();
				shootingRecurveBowImagesEast.add(staticRecurveBowImages.get(19));
				shootingRecurveBowImagesEast.add(staticRecurveBowImages.get(18));
				shootingRecurveBowImagesEast.add(staticRecurveBowImages.get(17));
				shootingRecurveBowImagesEast.add(staticRecurveBowImages.get(16));
				shootingRecurveBowImagesEast.add(staticRecurveBowImages.get(15));
				shootingRecurveBowImagesEast.add(staticRecurveBowImages.get(14));
				shootingRecurveBowImagesEast.add(staticRecurveBowImages.get(13));
				shootingRecurveBowImagesEast.add(staticRecurveBowImages.get(12));
				shootingRecurveBowImagesEast.add(staticRecurveBowImages.get(11));
				shootingRecurveBowImagesNorth=shootingRecurveBowImagesEast;
			}

			imagesEast = shootingRecurveBowImagesEast;
			imagesWest = shootingRecurveBowImagesWest;
			imagesSouth = shootingRecurveBowImagesSouth;
			imagesNorth = shootingRecurveBowImagesSouth;

			standingStillSouth = staticRecurveBowImages.get(0);
			standingStillWest = standingStillSouth;
			standingStillEast = imagesEast.get(0);
			standingStillNorth = standingStillEast;

			break;


		case CrossBow:

			if( staticCrossBowImages == null )
			{
				staticCrossBowImages = Assets.loadAnimationImages( R.drawable.xbow_with_reverse , 10 , 2 );

				shootingCrossBowImagesSouth = new ArrayList<Image>();
				shootingCrossBowImagesSouth.add(staticCrossBowImages.get(1));
				shootingCrossBowImagesSouth.add(staticCrossBowImages.get(2));
				shootingCrossBowImagesSouth.add(staticCrossBowImages.get(3));
				shootingCrossBowImagesSouth.add(staticCrossBowImages.get(4));
				shootingCrossBowImagesSouth.add(staticCrossBowImages.get(5));
				shootingCrossBowImagesSouth.add(staticCrossBowImages.get(6));
				shootingCrossBowImagesSouth.add(staticCrossBowImages.get(7));
				shootingCrossBowImagesSouth.add(staticCrossBowImages.get(8));
				shootingCrossBowImagesSouth.add(staticCrossBowImages.get(9));
				shootingCrossBowImagesWest = shootingCrossBowImagesSouth;
				shootingCrossBowImagesEast = new ArrayList<Image>();
				shootingCrossBowImagesEast.add(staticCrossBowImages.get(19));
				shootingCrossBowImagesEast.add(staticCrossBowImages.get(18));
				shootingCrossBowImagesEast.add(staticCrossBowImages.get(17));
				shootingCrossBowImagesEast.add(staticCrossBowImages.get(16));
				shootingCrossBowImagesEast.add(staticCrossBowImages.get(15));
				shootingCrossBowImagesEast.add(staticCrossBowImages.get(14));
				shootingCrossBowImagesEast.add(staticCrossBowImages.get(13));
				shootingCrossBowImagesEast.add(staticCrossBowImages.get(12));
				shootingCrossBowImagesEast.add(staticCrossBowImages.get(11));
				shootingCrossBowImagesSouth = shootingCrossBowImagesEast;
			}
			imagesEast = shootingCrossBowImagesEast;
			imagesWest = shootingCrossBowImagesWest;
			imagesSouth = shootingCrossBowImagesSouth;
			imagesNorth = shootingCrossBowImagesSouth;

			standingStillSouth = staticCrossBowImages.get(0);
			standingStillWest = standingStillSouth;
			standingStillEast = imagesEast.get(0);
			standingStillNorth=standingStillEast;

			getEOffset().add( Rpg.fiveDp , 0 );
			getWOffset().add( -Rpg.fiveDp , 0 );
			break;
		}

	}



	@Override
	public void playSound() {
		//float rate = ((float)1000)/(currentROA);
		//bowSound.play(0.05f,rate);
	}

	//	@Override
	@Override
	public void loadSounds() {
		//		bowSound=Rpg.getGame().getAudio().createSound("attackSounds/attack_ranged.mp3");
		//
	}




}
