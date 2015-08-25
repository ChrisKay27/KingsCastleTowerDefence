package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks;


import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.framework.Sound;
import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.Humanoid;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;

public class MeleeAnimator extends AttackAnimator
{

	public enum MeleeTypes
	{
		LongSword , Sabre , Mace , Hands ,  PickAxe , Hammer , Axe, Hoe, RESOURCES
	}

	private static ArrayList<Image> staticLongSwordImagesEast;
	private static ArrayList<Image> staticLongSwordImagesWest;
	private static ArrayList<Image> staticLongSwordImagesSouth;
	private static ArrayList<Image> staticLongSwordImagesNorth;
	private static ArrayList<Image> staticSabreImagesEast;
	private static ArrayList<Image> staticSabreImagesWest;
	private static ArrayList<Image> staticSabreImagesSouth;
	private static ArrayList<Image> staticSabreImagesNorth;
	private static ArrayList<Image> staticMaceImagesEast;
	private static ArrayList<Image> staticMaceImagesWest;
	private static ArrayList<Image> staticMaceImagesSouth;
	private static ArrayList<Image> staticMaceImagesNorth;
	private static ArrayList<Image> staticHandImagesEast;
	private static ArrayList<Image> staticHandImagesWest;
	private static ArrayList<Image> staticHandImagesSouth;
	private static ArrayList<Image> staticHandImagesNorth;

	private static ArrayList<Image> staticLongSwordImages;
	private static ArrayList<Image> staticSabreImages;
	private static ArrayList<Image> staticMaceImages;
	private static ArrayList<Image> staticHandImages;



	protected static ArrayList<Sound> longSwordSounds,sabreSounds,maceSounds;
	//private final int nextSound = 0;
	//private final Attack ownersAttack;
	private final MeleeTypes meleeType;
	private static final vector EOffset;
	private static final vector WOffset;
	private static final vector SOffset;
	private static final vector NOffset ;



	static
	{
		EOffset = new vector( 0 , 3 * Rpg.getDp() );
		WOffset = EOffset;
		NOffset = new vector();// -3 * Rpg.getDp() , 3 * Rpg.getDp() );
		SOffset = new vector( 5 * Rpg.getDp() , 6 * Rpg.getDp() );
	}


	{
		setEOffset( EOffset );
		setWOffset( WOffset );
		setNOffset( NOffset );
		setSOffset( SOffset );
	}


	public MeleeAnimator( Humanoid owner , MeleeTypes weaponType , MeleeAttack ownersAttack )
	{
		super( owner , 4 );
		//this.ownersAttack=ownersAttack;
		meleeType = weaponType;
		loadImages(weaponType);
	}



	public MeleeAnimator( Humanoid owner , MeleeAttack ownersAttack ) {
		this(owner,MeleeTypes.LongSword,ownersAttack);
	}



	private void loadImages( MeleeTypes meleeType )
	{
		float dp = Rpg.getDp();
		switch(meleeType)
		{
		case Hands:
			if( staticHandImages == null )
			{
				staticHandImages= Assets.loadAnimationImages(R.drawable.hand_with_reverse, 6, 2);
				staticHandImagesSouth = new ArrayList<Image>();
				staticHandImagesSouth.add(staticHandImages.get(1));
				staticHandImagesSouth.add(staticHandImages.get(2));
				staticHandImagesSouth.add(staticHandImages.get(3));
				staticHandImagesSouth.add(staticHandImages.get(4));
				staticHandImagesSouth.add(staticHandImages.get(5));
				staticHandImagesWest = staticHandImagesSouth;
				staticHandImagesEast = new ArrayList<Image>();
				staticHandImagesEast.add(staticHandImages.get(10));
				staticHandImagesEast.add(staticHandImages.get(9));
				staticHandImagesEast.add(staticHandImages.get(8));
				staticHandImagesEast.add(staticHandImages.get(7));
				staticHandImagesEast.add(staticHandImages.get(6));
				staticHandImagesNorth=staticHandImagesEast;

			}
			standingStillSouth = staticHandImages.get(0);
			standingStillWest = standingStillSouth;
			standingStillEast = staticHandImages.get(11);
			standingStillNorth = standingStillEast;
			imagesEast  = staticHandImagesEast  ;
			imagesNorth = staticHandImagesNorth ;
			imagesWest  = staticHandImagesSouth ;
			imagesSouth = staticHandImagesSouth ;

			break;

		default:
		case LongSword:
			if( staticLongSwordImages == null )
			{
				staticLongSwordImages = Assets.loadAnimationImages(R.drawable.longsword_full,6,4);
				staticLongSwordImagesNorth = new ArrayList<Image>(5);
				staticLongSwordImagesEast  = new ArrayList<Image>(5);
				staticLongSwordImagesWest  = new ArrayList<Image>(5);
				staticLongSwordImagesSouth = new ArrayList<Image>(5);


				standingStillNorth = staticLongSwordImages.get(0);
				standingStillWest  = staticLongSwordImages.get(6);
				standingStillEast  = staticLongSwordImages.get(17);
				standingStillSouth = staticLongSwordImages.get(18);


				for( int i = 1 ; i < 6 ; ++i )
					staticLongSwordImagesNorth.add( staticLongSwordImages.get(i) );

				for( int i = 7 ; i < 12 ; ++i )
					staticLongSwordImagesWest.add( staticLongSwordImages.get(i) );

				for( int i = 16 ; i > 11 ; --i )
					staticLongSwordImagesEast.add( staticLongSwordImages.get(i) );

				for( int i = 19 ; i < 24 ; ++i )
					staticLongSwordImagesSouth.add( staticLongSwordImages.get(i) );



			}
			standingStillNorth = staticLongSwordImages.get(0);
			standingStillWest  = staticLongSwordImages.get(6);
			standingStillEast  = staticLongSwordImages.get(17);
			standingStillSouth = staticLongSwordImages.get(18);

			imagesEast =staticLongSwordImagesEast;
			imagesNorth=staticLongSwordImagesNorth;
			imagesWest =staticLongSwordImagesWest;
			imagesSouth=staticLongSwordImagesSouth;

			setSOffset( new vector(getSOffset()) ); getSOffset().add( -dp , 2*dp );
			setNOffset( new vector(getNOffset()) ); getNOffset().add( 2*dp , -dp );
			break;

		case Sabre:
			if( staticSabreImages == null )
			{
				staticSabreImages=Assets.loadAnimationImages(R.drawable.sabre_with_reverse,6,2);
				staticSabreImagesSouth = new ArrayList<Image>();
				staticSabreImagesSouth.add(staticSabreImages.get(1));
				staticSabreImagesSouth.add(staticSabreImages.get(2));
				staticSabreImagesSouth.add(staticSabreImages.get(3));
				staticSabreImagesSouth.add(staticSabreImages.get(4));
				staticSabreImagesSouth.add(staticSabreImages.get(5));
				staticSabreImagesWest = staticSabreImagesSouth;
				staticSabreImagesEast = new ArrayList<Image>();
				staticSabreImagesEast.add(staticSabreImages.get(10));
				staticSabreImagesEast.add(staticSabreImages.get(9));
				staticSabreImagesEast.add(staticSabreImages.get(8));
				staticSabreImagesEast.add(staticSabreImages.get(7));
				staticSabreImagesEast.add(staticSabreImages.get(6));
				staticSabreImagesNorth=staticSabreImagesEast;
			}
			standingStillSouth = staticSabreImages.get(0);
			standingStillWest = standingStillSouth;
			standingStillEast =staticSabreImages.get(11);
			standingStillNorth = standingStillEast;
			imagesEast =staticSabreImagesEast;
			imagesNorth=staticSabreImagesNorth;
			imagesWest =staticSabreImagesWest;
			imagesSouth=staticSabreImagesSouth;


			setSOffset( new vector(getSOffset()) ); getSOffset().add( -2*dp , -2*dp );


			break;

		case Mace:

			if( staticMaceImages == null )
			{
				staticMaceImages=Assets.loadAnimationImages( R.drawable.mace_with_reverse , 6 , 2 );
				staticMaceImagesSouth = new ArrayList<Image>();
				staticMaceImagesSouth.add(staticMaceImages.get( 1 ));
				staticMaceImagesSouth.add(staticMaceImages.get(2));
				staticMaceImagesSouth.add(staticMaceImages.get(3));
				staticMaceImagesSouth.add(staticMaceImages.get(4));
				staticMaceImagesSouth.add(staticMaceImages.get(5));
				staticMaceImagesWest = staticMaceImagesSouth;
				staticMaceImagesEast = new ArrayList<Image>();
				staticMaceImagesEast.add(staticMaceImages.get(10));
				staticMaceImagesEast.add(staticMaceImages.get(9));
				staticMaceImagesEast.add(staticMaceImages.get(8));
				staticMaceImagesEast.add(staticMaceImages.get(7));
				staticMaceImagesEast.add(staticMaceImages.get(6));
				staticMaceImagesNorth=staticMaceImagesEast;
			}

			standingStillSouth = staticMaceImages.get(0);
			standingStillWest = standingStillSouth;
			standingStillEast = standingStillNorth = staticMaceImages.get(11);

			imagesEast = staticMaceImagesEast;
			imagesNorth = staticMaceImagesNorth;
			imagesWest = staticMaceImagesWest;
			imagesSouth = staticMaceImagesSouth;

			break;

		}

	}


	//	@Override
	//	public void doAttack()
	//	{
	//		ownersAttack.checkHit( attackingInDirectionUnitVector );
	//	}

	@Override
	public void playSound()
	{
//				if( wasDrawnThisFrame() )
//				{
//
//					longSwordSounds.get( nextSound ).play( 0.5f );
//
//					++nextSound;
//
//					if( nextSound > 2 )
//					{
//						nextSound = 0;
//					}
//
//				}

	}



	@Override
	public void loadSounds()
	{
		//		if( longSwordSounds == null )
		//		{
		//			longSwordSounds=new ArrayList<Sound>();
		//
		//			longSwordSounds.add( Rpg.getGame().getAudio().createSound( "attackSounds/melee_sound.wav" ) );
		//			longSwordSounds.add( Rpg.getGame().getAudio().createSound( "attackSounds/melee_sound2.wav" ) );
		//			longSwordSounds.add( Rpg.getGame().getAudio().createSound( "attackSounds/sword_sound.wav" ) );
		//
		//			sabreSounds = maceSounds = longSwordSounds;
		//		}
	}



	protected boolean thereAreNoImagesYet()
	{
		if( meleeType == MeleeTypes.Hands )
			return true;
		else
			return false;
	}






}
