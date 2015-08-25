package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army;

import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.Cost;
import com.kingscastle.nuzi.towerdefence.gameElements.ImageFormatInfo;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.Animator;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingQualities;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.AdvancedMageSoldier;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks.SpellAttack;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameElements.spells.EarthQuake;
import com.kingscastle.nuzi.towerdefence.gameUtils.Age;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.teams.Teams;


public class ButterFlyFairy extends AdvancedMageSoldier
{

	private static final String TAG = "ButterFlyFairy";
	private static final String NAME = "ButterFly Fairy";


	private static ImageFormatInfo imageFormatInfo;
	private static Image[] redImages , blueImages , greenImages , orangeImages , whiteImages ;



	private static final AttackerQualities staticAttackerQualities; @Override
	protected AttackerQualities getStaticAQ() { return staticAttackerQualities; }
	private static final LivingQualities staticLivingQualities; @Override
	protected LivingQualities getStaticLQ() { return staticLivingQualities; }

	private static Cost cost = new Cost( 10000 , 10000 , 10000 , 4 );

	static{

		float dp = Rpg.getDp();
		imageFormatInfo = new ImageFormatInfo( 0 , 0 ,
				0 , 0 , 1 , 1);
		//imageFormatInfo.setID(R.drawable.)
		//		imageFormatInfo.setOrangeId( R.drawable.butter_deflower_orange );
		//		imageFormatInfo.setRedId( R.drawable.butter_deflower_red );
		//		imageFormatInfo.setBlueId( R.drawable.butter_deflower_blue );
		//		imageFormatInfo.setGreenId( R.drawable.butter_deflower_green );
		//		imageFormatInfo.setWhiteId( R.drawable.butter_deflower_white );

		staticAttackerQualities= new AttackerQualities();

		staticAttackerQualities.setStaysAtDistanceSquared( 10000 * dp * dp );
		staticAttackerQualities.setFocusRangeSquared(5000*dp*dp);
		staticAttackerQualities.setAttackRangeSquared(22500 * dp * dp); staticAttackerQualities.setdRangeSquaredAge(1500 * dp * dp); staticAttackerQualities.setdRangeSquaredLvl(500 * dp * dp);
		staticAttackerQualities.setDamage( 100 );  staticAttackerQualities.setdDamageAge( 0 ); staticAttackerQualities.setdDamageLvl( 20 );
		staticAttackerQualities.setROF( 1000 );

		staticLivingQualities = new LivingQualities(); staticLivingQualities.setRequiresCLvl( 1 );  staticLivingQualities.setRequiresAge(Age.STONE); staticLivingQualities.setRequiresTcLvl(1);
		staticLivingQualities.setLevel( 1 );
		staticLivingQualities.setFullHealth( 2000 );
		staticLivingQualities.setHealth( 2000 ); staticLivingQualities.setdHealthAge( 0 ); staticLivingQualities.setdHealthLvl( 30 ); //350 );
		staticLivingQualities.setFullMana( 200 );
		staticLivingQualities.setMana( 200 );
		staticLivingQualities.setHpRegenAmount( 1 );
		staticLivingQualities.setRegenRate( 1000 );
		staticLivingQualities.setSpeed( 1.8f * dp );
	}


	{
		setAQ(new AttackerQualities(staticAttackerQualities , lq.getBonuses() ));

	}


	public ButterFlyFairy(vector loc, Teams team){
		super(team);
		setLoc(loc);

		setTeam(team);
	}

	public ButterFlyFairy() {
	}

	@Override
	protected void upgrade(){
		super.upgrade();

	}

	protected void setupSpells(){

		EarthQuake eq = new EarthQuake();
		eq.setDamage(getAQ().getDamage());
		eq.setCaster(this);
		SpellAttack fireBallAttack = new SpellAttack(getMM(), this , eq  );
		getAQ().addAttack( fireBallAttack ) ;


		//		Eruption erup = new Eruption();
		//		erup.setDamage(getAQ().getDamage());
		//		erup.setCaster(this);
		//		SpellAttack erupAttack = new SpellAttack( this , erup );
		//		getAQ().addAttack( erupAttack ) ;


		//		Laser lazer = new Laser();
		//		lazer.setDamage(getAQ().getDamage()/5);
		//		lazer.setCaster(this);
		//		SpellAttack lazerAttack = new SpellAttack( this , lazer );
		//		getAQ().addAttack( lazerAttack ) ;
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
		{
			redImages = Assets.loadImages(imageFormatInfo.getRedId(), 0, 0, 1, 1);
		}
		if( orangeImages == null )
		{
			orangeImages = Assets.loadImages( imageFormatInfo.getOrangeId()  , 0 , 0 , 1 , 1 );
		}
		if( blueImages == null )
		{
			blueImages = Assets.loadImages( imageFormatInfo.getBlueId()  , 0 , 0 , 1 , 1 );
		}
		if( greenImages == null )
		{
			greenImages = Assets.loadImages( imageFormatInfo.getGreenId()  , 0 , 0 , 1 , 1 );
		}
		if( whiteImages == null )
		{
			whiteImages = Assets.loadImages( imageFormatInfo.getWhiteId()  , 0 , 0 , 1 , 1 );
		}
	}


	@Override
	public void loadAnimation( MM mm )
	{
		super.loadAnimation(mm);
		Animator anim = getAnim();
		if( anim != null )
			anim.setScale(2f);
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
		ButterFlyFairy.imageFormatInfo = imageFormatInfo;
	}






	@Override
	public RectF getStaticPerceivedArea(){
		return Rpg.getNormalPerceivedArea();
	}



	@Override
	public void setStaticPerceivedArea(RectF staticPercArea2) {

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

	public static Image[] getRedImages()
	{
		if ( redImages == null )
			redImages = Assets.loadImages( imageFormatInfo.getRedId()  , 3 , 4, 0 , 0 , 1 , 1 );

		return redImages;
	}

	public static void setRedImages(Image[] redImages) {
		ButterFlyFairy.redImages = redImages;
	}

	public static Image[] getBlueImages()
	{
		if ( blueImages == null )
			blueImages = Assets.loadImages( imageFormatInfo.getBlueId()  , 3 , 4, 0 , 0 , 1 , 1 );

		return blueImages;
	}

	public static void setBlueImages(Image[] blueImages) {
		ButterFlyFairy.blueImages = blueImages;
	}

	public static Image[] getGreenImages()
	{
		if ( greenImages == null )
			greenImages = Assets.loadImages( imageFormatInfo.getGreenId()  , 3 , 4 , 0 , 0 , 1 , 1 );

		return greenImages;
	}

	public static void setGreenImages(Image[] greenImages) {
		ButterFlyFairy.greenImages = greenImages;
	}

	public static Image[] getOrangeImages()
	{
		if ( orangeImages == null )
			orangeImages = Assets.loadImages( imageFormatInfo.getOrangeId()  , 3 , 4 , 0 , 0 , 1 , 1 );

		return orangeImages;
	}

	public static void setOrangeImages(Image[] orangeImages) {
		ButterFlyFairy.orangeImages = orangeImages;
	}

	public static Image[] getWhiteImages()
	{
		if ( whiteImages == null )
			whiteImages = Assets.loadImages( imageFormatInfo.getWhiteId()  , 3 , 4 , 0 , 0 , 1 , 1 );

		return whiteImages;
	}

	public static void setWhiteImages(Image[] whiteImages) {
		ButterFlyFairy.whiteImages = whiteImages;
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
	public LivingQualities getNewLivingQualities()
	{
		return new LivingQualities(staticLivingQualities);
	}

	@Override
	public Cost getCosts() {
		return cost;
	}


	public static void setCost(Cost cost) {
		ButterFlyFairy.cost = cost;
	}


	@Override
	public String getAbilityMessage()
	{
		return buffMessage;
	}




















}
