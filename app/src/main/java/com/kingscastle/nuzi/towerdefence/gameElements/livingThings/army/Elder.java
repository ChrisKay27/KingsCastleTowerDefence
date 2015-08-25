package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.effects.EffectsManager;
import com.kingscastle.nuzi.towerdefence.effects.animations.Anim;
import com.kingscastle.nuzi.towerdefence.effects.animations.FreezeAnim;
import com.kingscastle.nuzi.towerdefence.effects.animations.LightEffect2;
import com.kingscastle.nuzi.towerdefence.effects.animations.TapAnim;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.gameElements.CD;
import com.kingscastle.nuzi.towerdefence.gameElements.Cost;
import com.kingscastle.nuzi.towerdefence.gameElements.GameElement;
import com.kingscastle.nuzi.towerdefence.gameElements.ImageFormatInfo;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingQualities;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.AdvancedMageSoldier;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks.SpellAttack;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Building;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.ListPkg;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameElements.spells.LightningBolts;
import com.kingscastle.nuzi.towerdefence.gameElements.spells.SummoningPortal;
import com.kingscastle.nuzi.towerdefence.gameUtils.Age;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.level.Level;
import com.kingscastle.nuzi.towerdefence.teams.Teams;


public class Elder extends AdvancedMageSoldier
{

	private static final String TAG = Elder.class.getSimpleName();
	private static final String NAME = TAG;

	private static ImageFormatInfo imageFormatInfo;
	private static Image[] redImages , blueImages , greenImages , orangeImages , whiteImages ;

	private static final AttackerQualities staticAttackerQualities;
	private static final LivingQualities staticLivingQualities;

	private static Cost cost = new Cost( 2500 , 2500 , 2000 , 3 );

	static
	{
		int dp = (int) Rpg.getDp();

		imageFormatInfo = new ImageFormatInfo( 0  , 0 ,
				0 , 0 , 1 , 1);
		imageFormatInfo.setID( R.drawable.elder );

		staticAttackerQualities= new AttackerQualities();

		staticAttackerQualities.setStaysAtDistanceSquared(0);
		staticAttackerQualities.setFocusRangeSquared(15000*dp*dp);
		staticAttackerQualities.setAttackRangeSquared(22500 * dp * dp);
		staticAttackerQualities.setDamage( 100 );
		staticAttackerQualities.setROF(300);

		staticLivingQualities = new LivingQualities();
		staticLivingQualities.setRequiresBLvl(1);
		staticLivingQualities.setRequiresAge(Age.STONE);
		staticLivingQualities.setRequiresTcLvl(1);
		staticLivingQualities.setLevel( 1 );
		staticLivingQualities.setFullHealth( 80000 );
		staticLivingQualities.setHealth( 80000 );
		staticLivingQualities.setFullMana(0);
		staticLivingQualities.setMana(0);
		staticLivingQualities.setHpRegenAmount(1);
		staticLivingQualities.setRegenRate(1000);
		staticLivingQualities.setArmor( 15 );
		staticLivingQualities.setSpeed(0.4f * dp);
	}

	private long summonPortalAt;

	{
		setAQ(new AttackerQualities(staticAttackerQualities,getLQ().getBonuses()));
		setGoldDropped(300);
		setCostsLives(10);
	}

	public Elder(vector loc, Teams team){
		super(team);
		setLoc(loc);
	}

	public Elder(){
	}

	private long freezeBuildingAt = 0;
	private Anim tapThis,glow;

	@Override
	public boolean act() {

		if( freezeBuildingAt < GameTime.getTime() ){

			ListPkg<Building> goodBuildings = getMM().getBuildingsOnTeam(Teams.BLUE);
			if( goodBuildings.size != 0 ) {

				int bIndex = (int) (Math.random() * goodBuildings.size);
				final Building b = goodBuildings.list[bIndex];

				if( !b.isStunned() ){
					freezeBuildingAt = GameTime.getTime() + 2500 + (int)(Math.random()*2500);
					b.setStunnedUntil(GameTime.getTime() + 60000);

					final Anim glow = new LightEffect2(b.loc);
					final TapAnim tapThis = new TapAnim(b.loc);
					final FreezeAnim freeze = new FreezeAnim(b.loc, 60000);
					freeze.setRequiredPosition(EffectsManager.Position.InFront);
					freeze.setScale(2);
					tapThis.setAliveTime(4000);

					getMM().getUI().addTappable(b, new Runnable() {
						@Override
						public void run() {
							b.setStunnedUntil(0);
							freeze.setOver(true);
							tapThis.setOver(true);
							glow.setOver(true);
						}
					});
					this.tapThis = tapThis;
					this.glow = glow;
					getMM().getEm().add(glow);
					getMM().getEm().add(tapThis);
					getMM().getEm().add(freeze);
				}
			}
		}


		if( summonPortalAt < GameTime.getTime() ) {
			summonPortalAt = GameTime.getTime() + 10000;
			Level lvl = getMM().getLevel();

			CD cd = getMM().getUI().getCD();

			vector loc = new vector();
			int attempts=0;
			do{
				loc.set(Math.random()*lvl.getLevelWidthInPx(), Math.random()*lvl.getLevelHeightInPx());
				if( cd.checkPlaceable(loc) )
					break;
				attempts++;
			}while( attempts < 20 );
			if( attempts < 20 ){
				if( Math.random() < 0.5 ) {
					final GameElement sPortal = new SummoningPortal(loc, this, 1000, 10000, null, KratosHeavyArmor.class);
					getMM().add(sPortal);
					getMM().getUI().addTappable(sPortal, new Runnable() {
						@Override
						public void run() {
							sPortal.die();
						}
					});
				}
				else{
					final GameElement sPortal = new SummoningPortal(loc, this, 300, 10000, null, Spider.class, SpiderLarge.class);
					getMM().add(sPortal);
					getMM().getUI().addTappable(sPortal, new Runnable() {
						@Override
						public void run() {
							sPortal.die();
						}
					});
				}

			}
		}


		return super.act();
	}

	@Override
	public boolean create(MM mm) {
		boolean superCreate =  super.create(mm);
		getAnim().setScale(1.5f);
		final LightEffect2 le = new LightEffect2(loc);
		le.setScale(2);
		mm.getEm().add(le);
		getAnim().addAnimationListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				le.setOver(true);
			}
		});
		return superCreate;
	}



	@Override
	protected void setupSpells(){
		LightningBolts bolt = new LightningBolts();
		bolt.setDamage(getAQ().getDamage());
		bolt.setCaster(this);
		SpellAttack lBoltAttack = new SpellAttack(getMM(), this , bolt  );
		getAQ().addAttack(lBoltAttack) ;
	}

	@Override
	public Image[] getImages()
	{
		loadImages();

		Teams teamName = getTeamName();
		if( teamName == null )
			teamName = Teams.BLUE;

		switch( teamName )
		{
		default:
		case RED:
			return redImages;
		case GREEN:
			return greenImages;
		case BLUE:
			return blueImages;
		case ORANGE:
			return orangeImages;
		case WHITE:
			return whiteImages;
		}
	}

	@Override
	public void loadImages()
	{
		if( redImages == null )
			redImages = Assets.loadImages(imageFormatInfo.getRedId(), 0, 0, 1, 1);
		if( orangeImages == null )
			orangeImages = Assets.loadImages( imageFormatInfo.getOrangeId()  , 0 , 0 , 1 , 1 );
		if( blueImages == null )
			blueImages = Assets.loadImages( imageFormatInfo.getBlueId()  , 0 , 0 , 1 , 1 );
		if( greenImages == null )
			greenImages = Assets.loadImages( imageFormatInfo.getGreenId()  , 0 , 0 , 1 , 1 );
		if( whiteImages == null )
			whiteImages = Assets.loadImages( imageFormatInfo.getWhiteId()  , 0 , 0 , 1 , 1 );
	}


	/**
	 * @return the imageFormatInfo
	 */
	@Override
	public ImageFormatInfo getImageFormatInfo() {
		return imageFormatInfo;
	}


	/**
	 * @param imageFormatInfo the imageFormatInfo to set
	 */
	public void setImageFormatInfo(ImageFormatInfo imageFormatInfo) {
		Elder.imageFormatInfo = imageFormatInfo;
	}


	/**
	 * DO NOT LOAD THE IMAGES, USE GETIMAGES() to make sure they are not null.
	 * @return the staticImages
	 */
	@Override
	public Image[] getStaticImages() {
		return null;
	}


	/**
	 * @param staticImages the staticImages to set
	 */
	@Override
	public void setStaticImages(Image[] staticImages) {

	}




	@Override
	public RectF getStaticPerceivedArea() {
		return Rpg.getNormalPerceivedArea();
	}

	@Override
	public LivingQualities getNewLivingQualities()
	{
		return new LivingQualities(staticLivingQualities);
	}

	@Override
	public void setStaticPerceivedArea(RectF staticPercArea) {

	}


	@Override
	public Cost getCosts() {
		return cost;
	}


	public static void setCost(Cost cost) {
		Elder.cost = cost;
	}

	public static Image[] getRedImages()
	{
		if ( redImages == null )
		{
			redImages = Assets.loadImages( imageFormatInfo.getRedId()  , 3 , 4, 0 , 0 , 1 , 1 );
		}
		return redImages;
	}

	public static void setRedImages(Image[] redImages) {
		Elder.redImages = redImages;
	}

	public static Image[] getBlueImages()
	{
		if ( blueImages == null )
		{
			blueImages = Assets.loadImages( imageFormatInfo.getBlueId()  , 3 , 4, 0 , 0 , 1 , 1 );
		}
		return blueImages;
	}

	public static void setBlueImages(Image[] blueImages) {
		Elder.blueImages = blueImages;
	}

	public static Image[] getGreenImages()
	{
		if ( greenImages == null )
		{
			greenImages = Assets.loadImages( imageFormatInfo.getGreenId()  , 3 , 4 , 0 , 0 , 1 , 1 );
		}
		return greenImages;
	}

	public static void setGreenImages(Image[] greenImages) {
		Elder.greenImages = greenImages;
	}

	public static Image[] getOrangeImages()
	{
		if ( orangeImages == null )
		{
			orangeImages = Assets.loadImages( imageFormatInfo.getOrangeId()  , 3 , 4 , 0 , 0 , 1 , 1 );
		}
		return orangeImages;
	}

	public static void setOrangeImages(Image[] orangeImages) {
		Elder.orangeImages = orangeImages;
	}

	public static Image[] getWhiteImages()
	{
		if ( whiteImages == null )
		{
			whiteImages = Assets.loadImages( imageFormatInfo.getWhiteId()  , 3 , 4 , 0 , 0 , 1 , 1 );
		}
		return whiteImages;
	}

	public static void setWhiteImages(Image[] whiteImages) {
		Elder.whiteImages = whiteImages;
	}

	@Override
	public Anim getDyingAnimation(){
		return Assets.deadSkeletonAnim;
	}
	@Override
	public String toString() {
		return TAG;
	}
	@Override
	public String getName() {
		return NAME;
	}

	@Override
	protected AttackerQualities getStaticAQ() { return staticAttackerQualities; }


	@Override
	protected LivingQualities getStaticLQ() { return staticLivingQualities; }
}
