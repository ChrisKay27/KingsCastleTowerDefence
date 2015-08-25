package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings;

import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.effects.EffectsManager;
import com.kingscastle.nuzi.towerdefence.effects.animations.Anim;
import com.kingscastle.nuzi.towerdefence.effects.animations.Backing;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.gameElements.Cost;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingQualities;
import com.kingscastle.nuzi.towerdefence.gameUtils.Age;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.teams.Teams;

public class Farm extends Building
{

	private static final String TAG = "Farm";

	public static final Buildings name = Buildings.Farm;

	private static  RectF staticPerceivedArea; // this includes the offset from the mapLocation

	private static Image image = Assets.loadImage(R.drawable.farm),deadImage;

	private static Cost cost = new Cost( 10000 , 0 , 10000 , 0 );

	private static final LivingQualities staticLivingQualities;



	@Override
	protected LivingQualities getStaticLQ() { return staticLivingQualities;   }

	private RectF perceivedArea;


	static
	{

		staticLivingQualities = new LivingQualities(); staticLivingQualities.setRequiresAge(Age.STONE); staticLivingQualities.setRequiresTcLvl(4);
		staticLivingQualities.setRangeOfSight( 250 );
		staticLivingQualities.setLevel( 1 );
		staticLivingQualities.setFullHealth(250);
		staticLivingQualities.setHealth(250); staticLivingQualities.setdHealthLvl(30);
		staticLivingQualities.setHpRegenAmount(2);
		staticLivingQualities.setRegenRate( 4000 );

		staticPerceivedArea = Rpg.guardTowerArea;
	}

	{
		deadImage = Assets.genericDestroyedBuilding;
	}

	public Farm(){
		super(name);
	}


	public Farm(vector v, Teams t)
	{
		super( name , t );

		setLoc( v );
		setTeam( t );

		setBuildingsName( name );
		loadImages();

		loadPerceivedArea();
		setAttributes();
	}




	@Override
	protected void addAnimationToEm(Anim a, boolean sorted, EffectsManager em)
	{
		em.add( a , true);
		backing.setSize(Backing.MEDIUM);
		em.add(backing, EffectsManager.Position.Behind);
	}


	@Override
	public void setSelected ( boolean b )
	{
		super.setSelected(b);
	}


	@Override
	public Image getImage() {
		return image;
	}

	@Override
	public Image getDeadImage()
	{
		loadImages();
		return deadImage;
	}

	@Override
	public void loadImages(){
	}



	/**
	 * returns a rectangle to be placed with its center on the mapLocation of the tower
	 */
	@Override
	public RectF getPerceivedArea()
	{
		loadPerceivedArea();

		if( perceivedArea == null )
			perceivedArea = staticPerceivedArea;

		return perceivedArea;
	}



	public void setPerceivedSpriteArea( RectF perceivedSpriteArea2 )
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
	public Cost getCosts() {
		return cost;
	}

	public static void setCost(Cost cost) {
		Farm.cost = cost;
	}

	@Override
	public LivingQualities getNewLivingQualities()
	{
		return new LivingQualities( staticLivingQualities );
	}


	@Override
	public String toString() {
		return TAG;
	}

	@Override
	public String getName() {
		return TAG;
	}





}
