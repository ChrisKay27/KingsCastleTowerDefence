package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings;

import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.TowerDefenceGame;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.Cost;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingQualities;
import com.kingscastle.nuzi.towerdefence.gameUtils.Age;


public class StorageDepot extends Building
{

	private static final String TAG = "StorageDepot";
	private static final String NAME = "Storage Depot";

	public static final Buildings name = Buildings.StorageDepot;

	private static RectF staticPerceivedArea; // this includes the offset from the mapLocation


	private static Image image , deadImage;  //, damagedImage ;


	private static final Cost costs = new Cost( 100 , 0 , 100 , 0 );

	private static final LivingQualities staticLivingQualities;

	@Override
	protected LivingQualities getStaticLQ() {
		return staticLivingQualities;
	}

	static
	{
		staticLivingQualities = new LivingQualities(); staticLivingQualities.setRequiresAge(Age.STONE); staticLivingQualities.setRequiresTcLvl(2);

		staticLivingQualities.setLevel( 1 ); //1);
		staticLivingQualities.setFullHealth(300);
		staticLivingQualities.setHealth(300);
		staticLivingQualities.setHpRegenAmount(1);
		staticLivingQualities.setRegenRate(1000);

		staticLivingQualities.setdHealthLvl(30);
		staticLivingQualities.setdArmorLvl(1.5f);

		staticPerceivedArea = Rpg.fourByTwoArea;
	}



	public StorageDepot()
	{
		super( name );

		loadImages();
		loadPerceivedArea();
		setBuildingsName(name);
		setAttributes();
	}


	public StorageDepot( TowerDefenceGame UseThisConstructorForAllowedBuildings )
	{
		super( name );
	}


	StorageDepot(Buildings name2) {
		super( name2 );
	}


	@Override
	public void loadImages()
	{
		if (image == null)
		{
			int aliveId = R.drawable.lumber_mill;
			image = Assets.loadImage(aliveId);
			//damagedImage = image;
			deadImage = Assets.genericDestroyedBuilding;
		}

	}



	@Override
	public boolean canLevelUp() {
		return false;
	}

	@Override
	public Image getImage()
	{
		if( image == null )
		{
			loadImages();
		}

		return image;
	}


	@Override
	public Image getDamagedImage() {
		loadImages();
		return image;
	}
	@Override
	public Image getDeadImage() {
		loadImages();
		return deadImage;
	}

	@Override
	public Cost getCosts() {
		return costs;
	}


	@Override
	public String toString() {
		return NAME;
	}
	@Override
	public String getName() {
		return NAME;
	}

	/**
	 * returns a rectangle to be placed with its center on the mapLocation of the tower
	 */
	@Override
	public RectF getPerceivedArea()
	{
		loadPerceivedArea();
		return staticPerceivedArea;
	}

	public void setPerceivedSpriteArea(RectF perceivedSpriteArea2) {
		staticPerceivedArea = perceivedSpriteArea2;
	}

	@Override
	public RectF getStaticPerceivedArea() {
		return staticPerceivedArea;
	}

	@Override
	public void setStaticPerceivedArea(RectF staticPercArea) {
		staticPerceivedArea = staticPercArea;

	}





	@Override
	public LivingQualities getNewLivingQualities()
	{
		return new LivingQualities(staticLivingQualities);
	}





}
