package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings;

import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.Cost;
import com.kingscastle.nuzi.towerdefence.gameElements.ImageFormatInfo;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingQualities;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks.ProjectileAttack;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameElements.projectiles.Arrow;
import com.kingscastle.nuzi.towerdefence.gameUtils.Age;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.teams.Teams;

public class UndeadBarracks extends AttackingBuilding
{

	private static final String TAG = "UndeadBarracks";
	private static final String NAME = "Barracks";

	public static final Buildings name = Buildings.UndeadBarracks;


	private static Image deadImage;
	private static ImageFormatInfo imageFormatInfo;

	private static RectF staticPerceivedArea; // this is only the offset from the mapLocation.

	private static final AttackerQualities staticAttackerQualities;
	private static final LivingQualities staticLivingQualities;

	private static final Cost cost = new Cost( 500 , 0 , 500 , 0 );



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

		//int ironAgeID = R.drawable.evil_barracks_iron_age;
		//int bronzeAgeID = R.drawable.evil_barracks_bronze_age;
		int stoneAgeID = R.drawable.evil_barracks_stone_age;


		float dp = Rpg.getDp();

		staticAttackerQualities= new AttackerQualities();

		staticAttackerQualities.setFocusRangeSquared(  22500 * dp * dp );
		staticAttackerQualities.setAttackRangeSquared( 27000 * dp * dp );
		staticAttackerQualities.setDamage( 20 );
		staticAttackerQualities.setROF( 800 );

		staticLivingQualities = new LivingQualities();  staticLivingQualities.setRequiresAge(Age.STONE); staticLivingQualities.setRequiresTcLvl(1);
		staticLivingQualities.setRangeOfSight( 250 );
		staticLivingQualities.setFullHealth( 300 );
		staticLivingQualities.setHealth( 300 );
		staticLivingQualities.setFullMana( 0 );
		staticLivingQualities.setMana( 0 );
		staticLivingQualities.setHpRegenAmount( 1 );
		staticLivingQualities.setRegenRate( 4000 );

		staticPerceivedArea = Rpg.fourByFourArea;
	}

	private RectF perceivedArea;



	{
		setAQ(new AttackerQualities(staticAttackerQualities, getLQ().getBonuses()));

	}


	public UndeadBarracks()
	{
		super( name , null );
		setBuildingsName(name);
		loadImages();
		loadPerceivedArea();
		setAttributes();
	}

	public UndeadBarracks( vector v, Teams t )
	{
		super(name, t);
		setTeam(t);
		setLoc(v);
	}


	@Override
	public boolean create(MM mm) {
		getAQ().setCurrentAttack(new ProjectileAttack(mm, this, new Arrow())) ;
		return super.create(mm);
	}
	@Override
	protected void setupAttack() {

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







	@Override
	public String toString() {
		return TAG;
	}
	@Override
	public String getName() {
		return NAME;
	}








	@Override
	public Cost getCosts() {
		return cost;
	}










	@Override
	public ImageFormatInfo getImageFormatInfo(){
		return imageFormatInfo;
	}

	public void setImageFormatInfo(ImageFormatInfo imageFormatInfo2){
		imageFormatInfo=imageFormatInfo2;
	}







	/**
	 * returns a rectangle to be placed with its center on the mapLocation of the tower
	 */
	@Override
	public RectF getPerceivedArea()
	{

		//////Log.i ( TAG , "getting perc area for a barracks, perc area = " + perceivedArea );
		if( perceivedArea == null )
		{
			perceivedArea = getStaticPerceivedArea();
			//
			//			Image image = getImage();
			//			if( image == null )
			//			{
			//				throw new IllegalStateException("image == null when trying to load perc area.");
			//			}
			//
			//			int widthDiv2 = image.getWidthDiv2();
			//			int heightDiv2 = image.getHeightDiv2();
			//
			//			perceivedArea = new RectF( - widthDiv2 , - heightDiv2 + 5 * dp  , widthDiv2 , heightDiv2 - 5 * dp );

		}
		//////Log.i ( TAG , "perc area is now: " + perceivedArea );
		return perceivedArea;
	}


	public void setPerceivedSpriteArea(RectF perceivedSpriteArea2)
	{
		staticPerceivedArea = perceivedSpriteArea2;
	}

	@Override
	public RectF getStaticPerceivedArea()
	{
		if( staticPerceivedArea == null )
		{
			staticPerceivedArea = new Barracks().getPerceivedArea();
		}
		return staticPerceivedArea;
	}

	@Override
	public void setStaticPerceivedArea(RectF staticPercArea)
	{
		staticPerceivedArea = staticPercArea;
	}






	@Override
	public LivingQualities getNewLivingQualities()
	{
		return new LivingQualities( staticLivingQualities );
	}





















}

