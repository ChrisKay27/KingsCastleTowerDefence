package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army;

import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.Cost;
import com.kingscastle.nuzi.towerdefence.gameElements.ImageFormatInfo;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingQualities;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.MediumMeleeSoldier;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameUtils.Age;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.teams.Teams;


public class Saruman extends MediumMeleeSoldier
{

	private static final String TAG = "Saruman";
	private static final String NAME = "";

	//private static Image[] redImages , blueImages , greenImages , orangeImages , whiteImages ;
	//private static ImageFormatInfo imageFormatInfo;


	private static final AttackerQualities staticAttackerQualities; @Override
	protected AttackerQualities getStaticAQ() { return staticAttackerQualities; }
	private static final LivingQualities staticLivingQualities; @Override
	protected LivingQualities getStaticLQ() { return staticLivingQualities; }

	private static Cost cost = new Cost( 0 , 0 , 0 , 1 );

	static
	{
		float dp = Rpg.getDp();

		staticAttackerQualities= new AttackerQualities();

		staticAttackerQualities.setStaysAtDistanceSquared( 0 );

		staticLivingQualities = new LivingQualities();   staticLivingQualities.setRequiresAge(Age.STONE); staticLivingQualities.setRequiresTcLvl(1);
		staticLivingQualities.setRangeOfSight(150*dp);
		staticLivingQualities.setHealth(100000);
		staticLivingQualities.setFullHealth(100000);
	}

	{
		setAQ( new AttackerQualities(staticAttackerQualities,getLQ().getBonuses()) );
	}

	public Saruman()
	{
	}


	public Saruman( vector loc , Teams team )
	{
		super(team);
		setLoc( loc );

		this.team = team;
	}


	@Override
	public void finalInit( MM mm )
	{

	}

	@Override
	public void loadAnimation( MM mm )
	{
	}

	@Override
	public Image[] getImages()
	{
		return null;
	}

	@Override
	public void loadImages()
	{
	}



	@Override
	public boolean act(){
		return false;
	}

	@Override
	public void die(){

	}

	@Override
	public ImageFormatInfo getImageFormatInfo()
	{
		return null;
	}



	public void setImageFormatInfo( ImageFormatInfo imageFormatInfo2 )
	{

	}




	@Override
	public RectF getStaticPerceivedArea()
	{
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
		Saruman.cost = cost;
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
