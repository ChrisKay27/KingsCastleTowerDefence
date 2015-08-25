package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings;

import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.Cost;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingQualities;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameUtils.Age;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.teams.Teams;

import java.util.ArrayList;

public class UndeadWatchTower extends AttackingBuilding
{

	private static final String TAG = "UndeadWatchTower";
	private static final String NAME = "Watch Tower";

	public static final Buildings name = Buildings.UndeadWatchTower;

	private static RectF staticPerceivedArea;

	private static Image image , damagedImage , deadImage , iconImage;


	private static final Cost cost = new Cost( 500 , 0 , 500 , 0 );

	private static final AttackerQualities staticAttackerQualities;
	private static final LivingQualities staticLivingQualities;

	private static ArrayList<vector> staticDamageOffsets;

	@Override
	protected AttackerQualities getStaticAQ() {
		return staticAttackerQualities;
	}
	@Override
	protected LivingQualities getStaticLQ() {
		return staticLivingQualities;
	}
	static
	{
		float dpSquared = Rpg.getDpSquared();

		staticAttackerQualities = new AttackerQualities();

		staticAttackerQualities.setFocusRangeSquared(30000 * dpSquared);
		staticAttackerQualities.setAttackRangeSquared(30000 * dpSquared);
		staticAttackerQualities.setDamage(15);
		staticAttackerQualities.setROF(800);

		staticLivingQualities = new LivingQualities();  staticLivingQualities.setRequiresAge(Age.STONE); staticLivingQualities.setRequiresTcLvl(1);
		staticLivingQualities.setRangeOfSight(250);
		staticLivingQualities.setLevel( 1 ); //1);
		staticLivingQualities.setFullHealth(250);
		staticLivingQualities.setHealth(250);
		staticLivingQualities.setFullMana(125);
		staticLivingQualities.setMana(125);
		staticLivingQualities.setHpRegenAmount(1);
		staticLivingQualities.setRegenRate(4000);

		staticAttackerQualities.setdDamageAge(4);
		staticAttackerQualities.setdDamageLvl(1);
		staticAttackerQualities.setdROFAge(-100);
		staticAttackerQualities.setdROFLvl(-20);
		staticAttackerQualities.setdRangeSquaredAge(-3000 * dpSquared);
		staticAttackerQualities.setdRangeSquaredLvl(-100 * dpSquared);
		staticLivingQualities.setAge( Age.STONE );
		staticLivingQualities.setdHealthAge(300);
		staticLivingQualities.setdHealthLvl(50);
		staticLivingQualities.setdRegenRateAge( -100 );
		staticLivingQualities.setdRegenRateLvl(-20);
		staticPerceivedArea = Rpg.guardTowerArea;

	}

	{
		setAQ( new AttackerQualities(staticAttackerQualities,getLQ().getBonuses()) );
	}



	public UndeadWatchTower()
	{
		super( name , null );
		loadPerceivedArea();
	}

	public UndeadWatchTower(vector v, Teams t)
	{
		super( name , t );
		setLoc(v);
		setTeam(t);
	}

	@Override
	protected void setupAttack() {

	}

	@Override
	public void loadAnimation( MM mm )
	{
		boolean justCreatedAnim = buildingAnim == null;

		super.loadAnimation( mm );

		if( buildingAnim != null && justCreatedAnim )

			buildingAnim.setOffs( 0 , -Rpg.sixTeenDp );
	}



	void loadDamageOffsets()
	{
		float dp = Rpg.getDp();

		staticDamageOffsets = new ArrayList<vector>();
		staticDamageOffsets.add( new vector( Math.random()*-5*dp , -15*dp + Math.random()*30*dp ) );
		staticDamageOffsets.add( new vector( Math.random()*-5*dp , -15*dp + Math.random()*30*dp ) );
		staticDamageOffsets.add( new vector( Math.random()*5*dp , -15*dp + Math.random()*30*dp ) );
		staticDamageOffsets.add( new vector( Math.random()*5*dp , -15*dp + Math.random()*30*dp ) );
	}

	@Override
	public ArrayList<vector> getDamageOffsets()
	{
		if( staticDamageOffsets == null )
		{
			loadDamageOffsets();
		}
		return staticDamageOffsets;
	}


	@Override
	public Image getImage() {
		loadImages();
		return image;
	}
	@Override
	public Image getDamagedImage() {
		loadImages();
		return damagedImage;
	}

	@Override
	public Image getDeadImage() {
		loadImages();
		return deadImage;
	}
	@Override
	public Image getIconImage() {
		loadImages();
		return iconImage;
	}
	@Override
	public void loadImages()
	{
		if (image == null)
		{
			int aliveId = 0;//R.drawable.evil_watch_tower;
			image = Assets.loadImage(aliveId);
			damagedImage = image;
			deadImage = Assets.smallDestroyedBuilding;
		}
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

	public void setPerceivedSpriteArea(RectF perceivedSpriteArea2)
	{
		staticPerceivedArea = perceivedSpriteArea2;
	}

	@Override
	public RectF getStaticPerceivedArea()
	{
		return staticPerceivedArea;
	}

	@Override
	public void setStaticPerceivedArea(RectF staticPercArea)
	{
		staticPerceivedArea = staticPercArea;
	}



	@Override
	public String toString()
	{
		return TAG;
	}
	@Override
	public String getName()
	{
		return NAME;
	}


	@Override
	public Cost getCosts() {
		return cost;
	}


	@Override
	public LivingQualities getNewLivingQualities()
	{
		return new LivingQualities(staticLivingQualities);
	}



}
