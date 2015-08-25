package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army;

import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.effects.animations.Anim;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.Cost;
import com.kingscastle.nuzi.towerdefence.gameElements.ImageFormatInfo;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingQualities;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.MediumMage;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks.SpellAttack;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameElements.spells.Icicle;
import com.kingscastle.nuzi.towerdefence.gameElements.spells.LightningBolts;
import com.kingscastle.nuzi.towerdefence.gameUtils.Age;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.teams.Teams;


public class UndeadDullahan extends MediumMage
{

	private static final String TAG = "UndeadDullahan";
	private static final String NAME = "Dullahan";

	private static ImageFormatInfo imageFormatInfo;
	private static Image[] redImages , blueImages , greenImages , orangeImages , whiteImages ;

	private static final AttackerQualities staticAttackerQualities; @Override
	protected AttackerQualities getStaticAQ() { return staticAttackerQualities; }
	private static final LivingQualities staticLivingQualities; @Override
	protected LivingQualities getStaticLQ() { return staticLivingQualities; }

	private static Cost cost = new Cost( 1000 , 1000 , 1000 , 3 );

	static
	{
		float dp = Rpg.getDp();
		imageFormatInfo = new ImageFormatInfo( 0 , 0 ,
				0 , 0 , 1 , 1 );
		imageFormatInfo.setOrangeId( R.drawable.bonedullahan_a_red );
		imageFormatInfo.setRedId( R.drawable.bonedullahan_a_red );

		staticAttackerQualities= new AttackerQualities();

		staticAttackerQualities.setStaysAtDistanceSquared(12000 * dp * dp);
		staticAttackerQualities.setFocusRangeSquared(5000*dp*dp);
		staticAttackerQualities.setAttackRangeSquared(5000 * dp * dp);staticAttackerQualities.setdRangeSquaredAge(1500 * dp * dp); staticAttackerQualities.setdRangeSquaredLvl(500 * dp * dp);
		staticAttackerQualities.setROF(1000);
		staticAttackerQualities.setDamage(25); staticAttackerQualities.setdDamageLvl(5);

		staticLivingQualities = new LivingQualities(); staticLivingQualities.setRequiresCLvl( 1 );  staticLivingQualities.setRequiresAge(Age.STONE); staticLivingQualities.setRequiresTcLvl(1);
		staticLivingQualities.setRangeOfSight( 275 );
		staticLivingQualities.setLevel( 1 );
		staticLivingQualities.setFullHealth( 200 );
		staticLivingQualities.setHealth( 200 ); staticLivingQualities.setdHealthLvl( 10 );
		staticLivingQualities.setFullMana( 100 );
		staticLivingQualities.setMana( 100 );
		staticLivingQualities.setHpRegenAmount( 2 );
		staticLivingQualities.setRegenRate( 1000 );
		staticLivingQualities.setSpeed( 1f * dp );
	}



	{
		setAQ(new AttackerQualities(staticAttackerQualities , lq.getBonuses() ));


	}


	public UndeadDullahan(vector loc,Teams team)
	{
		super(team);
		setLoc(loc);
		super.team = team;
	}

	public UndeadDullahan() {

	}


	@Override
	protected void upgrade(){
		super.upgrade();

	}

	protected void setupSpells(){
		LightningBolts bolt = new LightningBolts();
		bolt.setDamage(getAQ().getDamage());
		bolt.setCaster(this);
		SpellAttack lBoltAttack = new SpellAttack(getMM(), this , bolt  );
		getAQ().addAttack( lBoltAttack );


		Icicle icicle = new Icicle(aq.getDamage(), 0.5f);
		icicle.setDamage(getAQ().getDamage());
		icicle.setCaster(this);
		SpellAttack icicleAttack = new SpellAttack(getMM(), this , icicle  );
		getAQ().addAttack(icicleAttack);
	}




	@Override
	public void finalInit( MM mm )
	{
		loadAnimation(mm);

		hasFinalInited = true;
	}




	@Override
	public Image[] getImages()
	{
		loadImages();

		Teams teamName = getTeamName();
		if( teamName == null )
		{
			teamName = Teams.BLUE;
		}

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
			redImages = Assets.loadImages(imageFormatInfo.getRedId(), 3, 4, 0, 0, 1, 1);
		}
		if( orangeImages == null )
		{
			orangeImages = Assets.loadImages( imageFormatInfo.getOrangeId() , 3 , 4 , 0 , 0 , 1 , 1 );
		}
		if( blueImages == null )
		{
			blueImages = Assets.loadImages( imageFormatInfo.getBlueId() , 3 , 4 , 0 , 0 , 1 , 1 );
		}
		if( greenImages == null )
		{
			greenImages = Assets.loadImages( imageFormatInfo.getGreenId() , 3 , 4 , 0 , 0 , 1 , 1 );
		}
		if( whiteImages == null )
		{
			whiteImages = Assets.loadImages( imageFormatInfo.getWhiteId() , 3 , 4 , 0 , 0 , 1 , 1 );
		}
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
		UndeadDullahan.imageFormatInfo = imageFormatInfo;
	}
	@Override
	public Anim getDyingAnimation(){
		return Assets.deadSkeletonAnim;
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
	public Image[] getStaticImages()
	{
		return null;
	}


	/**
	 * @param staticImages2 the staticImages to set
	 */
	@Override
	public void setStaticImages(Image[] staticImages2) {

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
		UndeadDullahan.cost = cost;
	}




	public static Image[] getRedImages()
	{
		return redImages;
	}

	public static void setRedImages(Image[] redImages) {
		UndeadDullahan.redImages = redImages;
	}

	public static Image[] getBlueImages()
	{
		return blueImages;
	}

	public static void setBlueImages(Image[] blueImages) {
		UndeadDullahan.blueImages = blueImages;
	}

	public static Image[] getGreenImages()
	{
		return greenImages;
	}

	public static void setGreenImages(Image[] greenImages) {
		UndeadDullahan.greenImages = greenImages;
	}

	public static Image[] getOrangeImages()
	{
		return orangeImages;
	}

	public static void setOrangeImages(Image[] orangeImages) {
		UndeadDullahan.orangeImages = orangeImages;
	}

	public static Image[] getWhiteImages()
	{
		return whiteImages;
	}

	public static void setWhiteImages(Image[] whiteImages) {
		UndeadDullahan.whiteImages = whiteImages;
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
	public String getAbilityMessage() {
		return buffMessage;
	}

}
