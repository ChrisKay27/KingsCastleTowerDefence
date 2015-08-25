package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings;

import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.Cost;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingQualities;
import com.kingscastle.nuzi.towerdefence.gameUtils.Age;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.teams.Teams;

public class UndeadChurch extends Building
{

	private static final String TAG = "UndeadChurch";
	private static final String NAME = "Church";

	public static final Buildings name = Buildings.UndeadChurch;

	private static  RectF staticPerceivedArea; // this includes the offset from the mapLocation


	private static Image deadImage;


	private static Cost cost = new Cost( 700 , 0 , 700 , 0 );

	private static final LivingQualities staticLivingQualities;




	@Override
	protected LivingQualities   getStaticLQ() { return staticLivingQualities;   }

	static
	{
		//int ironAgeID = R.drawable.evil_church_iron_age;
		//int bronzeAgeID = R.drawable.evil_church_bronze_age;
		//int stoneAgeID = R.drawable.evil_church_stone_age;



		staticLivingQualities = new LivingQualities();  staticLivingQualities.setRequiresAge(Age.STONE); staticLivingQualities.setRequiresTcLvl(1);
		staticLivingQualities.setRangeOfSight( 250 );
		staticLivingQualities.setLevel( 1 ); //5);
		staticLivingQualities.setFullHealth(200);
		staticLivingQualities.setHealth(200);
		staticLivingQualities.setHpRegenAmount(2);
		staticLivingQualities.setRegenRate( 4000 );

		staticPerceivedArea = Rpg.fourByFourArea;
	}


	private RectF perceivedArea;

	private BuildableUnits buildableUnits ;



	public UndeadChurch( vector v , Teams t )
	{
		this();
		setLoc( v );
		setTeam( t );

	}


	private UndeadChurch()
	{
		super( name );
		setBuildingsName( name );
		loadImages();

		loadPerceivedArea();
		setAttributes();
	}







	@Override
	public void setSelected ( boolean b )
	{
		super.setSelected( b );

	}



	@Override
	public Image getDeadImage()
	{
		loadImages();
		return deadImage;
	}


	@Override
	public void loadImages()
	{
		deadImage = Assets.genericDestroyedBuilding;
	}




	/**
	 * returns a rectangle to be placed with its center on the mapLocation of the tower
	 */
	@Override
	public RectF getPerceivedArea()
	{
		loadPerceivedArea();

		if( perceivedArea == null )
		{
			perceivedArea = staticPerceivedArea;
		}
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
		UndeadChurch.cost = cost;
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
		return NAME;
	}







}
