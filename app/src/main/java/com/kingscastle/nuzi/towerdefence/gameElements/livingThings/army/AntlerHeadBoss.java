package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army;

import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.gameElements.Cost;
import com.kingscastle.nuzi.towerdefence.gameElements.ImageFormatInfo;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingQualities;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.MeleeSoldier;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameUtils.Age;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.teams.Teams;

import org.jetbrains.annotations.NotNull;


public class AntlerHeadBoss extends MeleeSoldier {

	private static ImageFormatInfo imageFormatInfo;
	private static Image[] images = Assets.loadImages( R.drawable.antler_head , 0 , 0 , 1 , 1 );


	private static final AttackerQualities staticAttackerQualities; @Override
	protected AttackerQualities getStaticAQ() { return staticAttackerQualities; }
	private static final LivingQualities staticLivingQualities; @Override
	protected LivingQualities getStaticLQ() { return staticLivingQualities; }

	private static Cost cost = new Cost( 4000 , 4000 , 0 , 2 );

	static
	{
		float dp = Rpg.getDp();
		imageFormatInfo = new ImageFormatInfo( 0, 0, 0, 0, 1, 1);

		staticAttackerQualities= new AttackerQualities();

		staticAttackerQualities.setStaysAtDistanceSquared(0);
		staticAttackerQualities.setFocusRangeSquared(15000*dp*dp);
		staticAttackerQualities.setAttackRangeSquared( Rpg.getMeleeAttackRangeSquared() );
		staticAttackerQualities.setDamage( 100 );  staticAttackerQualities.setdDamageAge( 0 ); staticAttackerQualities.setdDamageLvl( 5 );
		staticAttackerQualities.setROF(1000);

		staticLivingQualities = new LivingQualities(); staticLivingQualities.setRequiresBLvl(10); staticLivingQualities.setRequiresAge(Age.STEEL); staticLivingQualities.setRequiresTcLvl(16);
		staticLivingQualities.setLevel( 1 );
		staticLivingQualities.setFullHealth( 20000 );
		staticLivingQualities.setHealth( 20000 ); staticLivingQualities.setdHealthAge( 0 ); staticLivingQualities.setdHealthLvl( 30 );
		staticLivingQualities.setFullMana(0);
		staticLivingQualities.setMana(0);
		staticLivingQualities.setHpRegenAmount(1);
		staticLivingQualities.setRegenRate(1000);
		staticLivingQualities.setArmor( 10 );  staticLivingQualities.setdArmorAge( 0 ); staticLivingQualities.setdArmorLvl( 2 );
		staticLivingQualities.setSpeed(.5f * dp);
	}

	{
		setAQ(new AttackerQualities(staticAttackerQualities,getLQ().getBonuses()));
		setCostsLives(10);
		setGoldDropped(300);
	}

	public AntlerHeadBoss(vector loc, Teams team){
		super(team);
		setLoc(loc);
	}


	public AntlerHeadBoss(){
	}




	@Override
	public boolean create(@NotNull MM mm) {
		boolean superCreate =  super.create(mm);
		getAnim().setScale(1.5f);
		return superCreate;
	}




	@Override
	public Image[] getImages()
	{
		loadImages();

		Teams teamName = getTeamName();
		if( teamName == null )
			teamName = Teams.BLUE;

		return images;
	}

	@Override
	public void loadImages()
	{
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
		AntlerHeadBoss.imageFormatInfo = imageFormatInfo;
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
	public void setStaticPerceivedArea(RectF staticPercArea) {
	}
	@Override
	public LivingQualities getNewLivingQualities() {
		return new LivingQualities(staticLivingQualities);
	}



	@Override
	public Cost getCosts() {
		return cost;
	}
	public static void setCost(Cost cost) {
		AntlerHeadBoss.cost = cost;
	}



	private static final String TAG = "Knight";

	@Override
	public String toString() {
		return TAG;
	}
	@Override
	public String getName() {
		return TAG;
	}
}
