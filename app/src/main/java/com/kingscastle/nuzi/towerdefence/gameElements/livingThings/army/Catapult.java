package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army;

import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.framework.Rpg.Direction;
import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.gameElements.Cost;
import com.kingscastle.nuzi.towerdefence.gameElements.ImageFormatInfo;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.Animator;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingQualities;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LookDirectionFinder;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.AdvancedRangedSoldier;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks.ProjectileAttack;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameElements.projectiles.Projectile;
import com.kingscastle.nuzi.towerdefence.gameElements.projectiles.Stone;
import com.kingscastle.nuzi.towerdefence.gameUtils.Age;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.teams.Team;
import com.kingscastle.nuzi.towerdefence.teams.Teams;
import com.kingscastle.nuzi.towerdefence.teams.races.Races;


public class Catapult extends AdvancedRangedSoldier
{
	private static final String TAG = "Catapult";
	private static final String NAME = TAG;

	public static Image[] northImages;
	public static Image[] eastImages ;
	public static Image[] southImages;
	public static Image[] westImages ;

	public static final Image[] movingImages;
	public static final long animsFramePeriod = 20;

	private static ImageFormatInfo imageFormatInfo;

	//private static final int imageId = R.drawable.catapult;

	private static final LivingQualities staticLivingQualities  ; @Override
	protected LivingQualities getStaticLQ() { return staticLivingQualities; }
	private static final AttackerQualities staticAttackerQualities; @Override
	protected AttackerQualities getStaticAQ() { return staticAttackerQualities; }

	private static Cost cost = new Cost( 2000 , 2000 , 2000 , 3 );


	static
	{
		float dp = Rpg.getDp();
		imageFormatInfo = new ImageFormatInfo(0 , 0,
				0 , 0 , 4 , 2);

		northImages = new Image[12];
		eastImages  = new Image[12];
		southImages = new Image[12];
		westImages  = new Image[12];


		northImages = Assets.loadAnimationImages(R.drawable.catapult_north, 12).toArray(northImages);
		eastImages  = Assets.loadAnimationImages( R.drawable.catapult_east  , 12 ).toArray(eastImages);
		southImages = Assets.loadAnimationImages( R.drawable.catapult_south , 12 ).toArray(southImages);
		westImages  = Assets.loadAnimationImages( R.drawable.catapult_west  , 12 ).toArray(westImages);


		movingImages = new Image[12];

		int i = 0;
		for( ; i < 3 ; ++i )
			movingImages[i] = southImages[0];

		for( ; i < 6 ; ++i )
			movingImages[i] = westImages[0];

		for( ; i < 9 ; ++i )
			movingImages[i] = eastImages[0];

		for( ; i < 12 ; ++i )
			movingImages[i] = northImages[0];



		staticAttackerQualities = new AttackerQualities();

		staticAttackerQualities.setStaysAtDistanceSquared(20000 * dp * dp);
		staticAttackerQualities.setFocusRangeSquared(5000*dp*dp);
		staticAttackerQualities.setAttackRangeSquared(30000 * dp * dp);
		staticAttackerQualities.setDamage( 50 );  staticAttackerQualities.setdDamageAge( 0 ); staticAttackerQualities.setdDamageLvl( 20 );
		staticAttackerQualities.setROF(2000);

		staticLivingQualities = new LivingQualities(); staticLivingQualities.setRequiresBLvl(11); staticLivingQualities.setRequiresAge(Age.IRON); staticLivingQualities.setRequiresTcLvl(11);
		staticLivingQualities.setRangeOfSight( 300 );
		staticLivingQualities.setLevel( 1 );
		staticLivingQualities.setFullHealth(400);
		staticLivingQualities.setHealth( 400 ); staticLivingQualities.setdHealthAge( 0 ); staticLivingQualities.setdHealthLvl( 20 );
		staticLivingQualities.setFullMana(0);
		staticLivingQualities.setMana(0);
		staticLivingQualities.setHpRegenAmount( 1 );
		staticLivingQualities.setRegenRate( 1000 );
		staticLivingQualities.setArmor( 10 );  staticLivingQualities.setdArmorAge( 0 ); staticLivingQualities.setdArmorLvl( 2 );
		staticLivingQualities.setSpeed( 1.5f * dp );
	}

	private boolean firing = false;

	{
		setAQ( new AttackerQualities(staticAttackerQualities,getLQ().getBonuses()));
	}


	public Catapult( vector loc, Teams team ){
		super(team);
		setLoc(loc);
		setTeam(team);
	}

	public Catapult() {
	}



	@Override
	protected void legsAct(){
		if( !firing )
			super.legsAct();
	}

	@Override
	protected boolean armsAct()
	{
		if( getPathToFollow() == null && destination == null )
		{
			if( super.armsAct() )
			{
				firing = true;
				////Log.d( TAG , "attacking");
				return true;
			}
		}
		return false;
	}

	@Override
	public void loadAnimation( MM mm )
	{
		if ( aliveAnim == null )
		{
			aliveAnim = new Animator( this , getImages() )
			{
				private long changeImageAt;
				@Override
				public Image getImage()
				{
					Image[] images = getImagesForDir( lookDir );
					if( firing )
					{
						if( changeImageAt < GameTime.getTime() )
						{
							////Log.d( TAG , "time to change image, onFrame=" + onFrame );
							++onFrame;

							if( onFrame >= images.length )
							{
								onFrame = 0;
								firing = false;
								////Log.d( TAG , "done attacking animation");
							}

							changeImageAt = GameTime.getTime() + animsFramePeriod;
							lastImageReturned = images[ onFrame ];

							return lastImageReturned;
						}
						else
						{
							return lastImageReturned;
						}
					}
					else
					{
						return images[0];
					}
				}

				private Image[] getImagesForDir( Direction lookDir )
				{

					switch( lookDir )
					{
					default:
					case E:
						return eastImages;
					case N:
						return northImages;
					case S:
						return southImages;
					case W:
						return westImages;
					}
				}
			};

			mm.getEm().add( aliveAnim , true );

			Races race = Races.HUMAN;
			Team t = mm.getTeam( team );
			if( t != null )
				race = t.getRace().getRace();

			addHealthBarToAnim( race );

		}
		else if( aliveAnim.isOver() )
		{

			aliveAnim.setOver( false );
			healthBar = null;

			Races race = Races.HUMAN;
			Team t = mm.getTeam( team );
			if( t != null )
				race = t.getRace().getRace();

			addHealthBarToAnim( race );
			mm.getEm().add( aliveAnim );
		}
	}



	@Override
	public void finalInit( MM mm )
	{
		super.finalInit( mm );

		if( hasFinalInited )
			return;

		getAQ().setCurrentAttack( new ProjectileAttack( mm ,this , new Stone() ){
			private long doAttackAt;
			private LivingThing target;
			@Override
			public void act()
			{
				if( doAttackAt < GameTime.getTime() )
				{
					doAttack();
					doAttackAt = Long.MAX_VALUE;
				}
			}
			@Override
			public boolean attack( LivingThing target )
			{
				doAttackAt = GameTime.getTime() + animsFramePeriod*8;
				this.target = target;
				lookDir = LookDirectionFinder.getDir(loc, target.loc);
				return true;
			}
			private void doAttack()
			{
				Projectile p = proj.newInstance( owner , null , target );
				p.loc.add( 0 , -Rpg.sixTeenDp );
				mm.add( p );
			}

		});

		hasFinalInited = true;
	}



	@Override
	public Image[] getImages()
	{
		return movingImages;
	}


	@Override
	public void loadImages()
	{
	}


	/**
	 * DO NOT LOAD THE IMAGES HERE, USE GETIMAGES() to make sure they are not null.
	 * @return the staticImages
	 */
	@Override
	public Image[] getStaticImages() {
		return movingImages;
	}

	/**
	 * @param staticImages the staticImages to set
	 */
	@Override
	public void setStaticImages(Image[] staticImages) {
	}




	@Override
	public ImageFormatInfo getImageFormatInfo(){
		return imageFormatInfo;
	}

	public void setImageFormatInfo(ImageFormatInfo imageFormatInfo2){
		imageFormatInfo=imageFormatInfo2;
	}



	@Override
	public RectF getStaticPerceivedArea(){
		return Rpg.getNormalPerceivedArea();
	}



	@Override
	public void setStaticPerceivedArea(RectF staticPercArea2) {

	}




	@Override
	public LivingQualities getNewLivingQualities()
	{
		return new LivingQualities(staticLivingQualities);
	}


	@Override
	public Cost getCosts()
	{
		return cost;
	}

	public static void setCost(Cost cost) {
		Catapult.cost = cost;
	}

	@Override
	public String toString() {
		return TAG;
	}
	@Override
	public String getName() {
		return NAME;
	}
}
