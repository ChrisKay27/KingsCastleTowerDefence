package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import android.util.Log;

import com.kingscastle.nuzi.towerdefence.effects.EffectsManager;
import com.kingscastle.nuzi.towerdefence.effects.Palette;
import com.kingscastle.nuzi.towerdefence.effects.animations.Anim;
import com.kingscastle.nuzi.towerdefence.effects.animations.Backing;
import com.kingscastle.nuzi.towerdefence.effects.animations.DestroyedBuildingAnim;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.Cost;
import com.kingscastle.nuzi.towerdefence.gameElements.ImageFormatInfo;
import com.kingscastle.nuzi.towerdefence.effects.animations.Bar;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameElements.movement.pathing.GridWorker;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.teams.Player;
import com.kingscastle.nuzi.towerdefence.teams.Team;
import com.kingscastle.nuzi.towerdefence.teams.Teams;
import com.kingscastle.nuzi.towerdefence.teams.races.Races;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Building extends LivingThing
{
	private static final String TAG = "Building";
	private static double regenRateBonus;

	protected boolean visible;

	private Buildings buildingsName;

	protected boolean constructed = false;
	private boolean buildByPlayer;
	protected long waitForIt = 0;

	protected transient BuildingAnim buildingAnim;




	protected Building( vector loc , Buildings name, Teams team ){
		super(team);
		setLoc(loc);
		this.buildingsName = name;
		updateArea();
	}

	protected Building( Buildings name, Teams team ){
		super(team);
		this.buildingsName = name;
		updateArea();
	}

	protected Building(){
		try {
			buildingsName = (Buildings) getClass().getField("name").get(null);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	protected Building(Buildings buildingsName)
	{
		this.buildingsName = buildingsName;
		updateArea();
	}

	public Building(vector v,Teams t)
	{
		setLoc(v);
		setTeam(t);
	}


	@Override
	public boolean act()
	{
		if( getLQ().getHealth() <= 0 )
		{
			die();
			return true;
		}
		else
			regen();

		return isDead();
	}



	@Override
	public boolean create( MM mm )
	{
		boolean superCreate = super.create(mm);

		loadImages();
		loadAnimation(mm);

		adjustAnimForLevel(lq.getLevel());

		final Team t = mm.getTeam( team );
		if( t != null ){
		}
		else{
			//Log.e( TAG , "Creating a building and mm.getTeam(myTeam) returned null!");
		}
		created = true;
		return superCreate;
	}


	/** Override this if you need to */
	protected void adjustAnimForLevel(int level) {
	}




	public void construct( MM mm )	{
		//Log.d( TAG , "constructing building " + this + " builtByTeam=" + team );

		Player p = mm.getPlayer( team );

		if ( p != null )
		{
			updateArea();

			addHealthBarToAnim(mm.getRace(team));
			addFlagToAnim();


			addAnimationToEm(buildingAnim, true, mm.getEm());
			setAttributes();

		}
		else{
			//Log.e( TAG , "Player Null in construct()" );
		}
	}

	@Override
	public void finalInit( MM mm ){

	}

	@Override
	public void loadAnimation( MM mm )
	{
		if( buildingAnim == null )
		{
			//System.out.println("adding building animation");

			buildingAnim = new BuildingAnim( this );
		}
	}

	@Override
	public void addHealthBarToAnim( Races race )
	{
		//		if( Settings.yourBaseMode )
		//			return;

		if ( buildingAnim != null )
		{
			if( getTeamName() == Teams.BLUE )
			{
				healthBar = new Bar( getLQ().getHealthObj() );
				buildingAnim.add( healthBar , true );
				Paint nonDistOverPaint = Palette.getPaint(Align.CENTER, Color.RED, Rpg.getTextSize());
				healthBar.setBarPaint( nonDistOverPaint );
				healthBar.setTextPaint( nonDistOverPaint );
			}
			else
			{
				float yOffs = 12 * Rpg.getDp();

				Image img = getImage();
				if( img != null )
					yOffs = img.getHeightDiv2() + Rpg.twoDp;


				healthBar = new Bar( getLQ().getHealthObj() , -8 * Rpg.getDp() , - yOffs );
				buildingAnim.add( healthBar , true );
				Paint nonDistOverPaint = Palette.getPaint( Align.CENTER , Color.RED , Rpg.getTextSize() );
				healthBar.setBarPaint( nonDistOverPaint );
			}

			healthBar.setShowCurrentAndMax( false );

			if( race == Races.UNDEAD )
				healthBar.setColor( Color.BLACK );
		}
	}

	void addFlagToAnim()
	{
		if ( buildingAnim != null ) {
		}
	}

	//Needs to stay final, Assummed its not null
	protected final Backing backing = new Backing( Backing.MEDIUM , Backing.DIRT , loc );


	/**
	 * Here only so this function can be overridden.
	 */
	protected void addAnimationToEm(Anim a, boolean sorted, EffectsManager em)
	{
		em.add( a , sorted );
		em.add( backing , EffectsManager.Position.Behind );
		em.sort();
	}


	public Cost getLvlUpCost() {
		Cost lvlUpCost = new Cost(getCosts());
		lvlUpCost.times(lq.getLevel() + 0.2f);
		return lvlUpCost;
	}





	public Image getImage()
	{
		loadImages();
		return null;
	}

	public Image getDamagedImage(){
		return null;
	}

	public Image getDeadImage()
	{
		return Assets.genericDestroyedBuilding;
	}




	@Override
	public void takeDamage( int dam , LivingThing enemy , DamageTypes damageType )
	{
		takeDamage( dam , enemy , damageType , getMM());
	}

	public void takeDamage( int dam , LivingThing enemy , DamageTypes damageType , MM mm )
	{
		//Towers don't die in tower defence mode
		if(true) return;

		BuildingAnim bAnim = buildingAnim;
		if( bAnim == null )
			return;



		float prevHealthPerc = getLQ().getHealthPercent();


		super.takeDamage( dam , enemy , damageType );


		float HealthPerc = getLQ().getHealthPercent();


		if( prevHealthPerc > 0.90 && HealthPerc <= 0.90 )
		
			bAnim.addDamagedEffect( 1 , mm );
		
		if( prevHealthPerc > 0.75 && HealthPerc <= 0.75 )
		
			bAnim.addDamagedEffect( 1 , mm );
		
		if( prevHealthPerc > 0.6 && HealthPerc <= 0.6 )
		
			bAnim.addDamagedEffect( 1 , mm );
		
		if(prevHealthPerc > 0.5 && HealthPerc <= 0.5)
		
			bAnim.addDamagedEffect( 2 , mm );
		
		if(prevHealthPerc > 0.35 && HealthPerc <= 0.35)
		
			bAnim.addDamagedEffect( 3 , mm );
		
		if(prevHealthPerc > 0.25 && HealthPerc <= 0.25)
		
			bAnim.addDamagedEffect( 3 , mm );
		
		if(prevHealthPerc > 0.15 && HealthPerc <= 0.15)
		
			bAnim.addDamagedEffect( 3 , mm );
		
		if(prevHealthPerc > 0 && HealthPerc <= 0)
		
			bAnim.addDamagedEffect( 4 , mm );
		

	}

	@Override
	public void takeHealing( int healAmount )
	{
		float prevHealthPerc = getLQ().getHealthPercent();

		getLQ().addHealth( healAmount * 5 );

		if( buildingAnim == null )
			return;


		float healthPerc = getLQ().getHealthPercent();



		if( healthPerc == 1 )
		
			buildingAnim.removeAllDamagedEffects();
		
		if( prevHealthPerc < 0.90 && healthPerc >= 0.90 )
		
			buildingAnim.removeADamagedEffect();
		
		if( prevHealthPerc < 0.75 && healthPerc >= 0.75 )
		
			buildingAnim.removeADamagedEffect();
		
		if( prevHealthPerc < 0.6 && healthPerc >= 0.6 )
		
			buildingAnim.removeADamagedEffect();
		
		if( prevHealthPerc < 0.5 &&healthPerc >= 0.5 )
		
			buildingAnim.removeADamagedEffect();
		
		if( prevHealthPerc < 0.35 && healthPerc >= 0.35 )
		
			buildingAnim.removeADamagedEffect();
		
		if( prevHealthPerc < 0.25 && healthPerc >= 0.25 )
		
			buildingAnim.removeADamagedEffect();
		
		if( prevHealthPerc < 0.15 && healthPerc >= 0.15 )
		
			buildingAnim.removeADamagedEffect();
		
		if( prevHealthPerc < 0 && healthPerc >= 0 )
		
			buildingAnim.removeADamagedEffect();
		
	}

	@Override
	public void setSelected( boolean b ){
		super.setSelected(b);
	}


	@Override
	public void die()
	{
		if( !isDead() )
		{
			getMM().getEm().add(new DestroyedBuildingAnim(loc));
			GridWorker.addToGridNow( area , true , getMM().getLevel().getGrid() );

			BuildingAnim bAnim = buildingAnim;
			if( bAnim != null )
				bAnim.setOver(true);//changeToDestroyedBuilding();


			if( healthBar != null )
				healthBar.setOver( true );

			setDead( true );
		}
	}




	RectF getAreaFromLoc(vector v)
	{
		RectF r = new RectF( getPerceivedArea() );

		r.offset( v.x , v.y );

		return r;
	}

	@Override
	public void updateArea()
	{
		area.set( getAreaFromLoc( loc ) ) ; //CHANGED jan 12
	}



	private static final ArrayList<String> packagesToLookForBuildings = new ArrayList<String>();
	static{
		addPackageToFindBuildings("com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.");
	}
	public static void addPackageToFindBuildings(String pkg){
		synchronized(packagesToLookForBuildings){
			packagesToLookForBuildings.add(pkg);
		}
	}

	public static Building newInstance( String className )
	{
		boolean failed = false;
		synchronized( packagesToLookForBuildings ){
			for( String s : packagesToLookForBuildings ){
				try
				{
					Class<?> aBuilding = Class.forName(s + className );

					Building building = (Building) aBuilding.newInstance();

					if( failed && building != null ){
						Log.e(TAG, "Eventual success for a " + className );
					}

					return building;
				}
				catch( NoClassDefFoundError e )
				{
					failed = true;

											Log.v( TAG ,  "Unable to load building from building package: " + s + "  Building:"+ className );
											e.printStackTrace();

				}
				catch( Exception e )
				{
					failed = true;

											Log.v( TAG ,  "Unable to load building from building package: " + s + "  Building:"+ className );
											e.printStackTrace();

				}
			}
		}
		return null;
	}

	public static Building getFromString( String name , Teams team, vector vector ){

		Building b = newInstance( name );
		b.setLoc( vector );
		b.updateArea();
		b.setTeam( team );
		return b;
	}



	/**
	 * @param percentIncrease add 0.10 for +10% increase
	 */
	public static void addTechRegenRateBonus(double percentIncrease) {
		regenRateBonus += percentIncrease;
	}



	public static double getRegenRateBonus() {
		return regenRateBonus;
	}

	public static void setRegenRateBonus(double regenRateBonus) {
		Building.regenRateBonus = regenRateBonus;
	}




	public Buildings getBuildingsName() {
		return buildingsName;
	}
	protected void setBuildingsName(Buildings name)
	{
		if( name != null )
			buildingsName = name;
	}


	@Override
	public Image[] getImages(){
		return null;
	}



	@Override
	public Anim getDyingAnimation() {
		return null;
	}


	public ArrayList<vector> getDamageOffsets(){
		return null;
	}


	public boolean isBuildByPlayer()
	{
		return buildByPlayer;
	}
	public void setBuildByPlayer(boolean buildByPlayer)
	{
		this.buildByPlayer = buildByPlayer;
	}


	protected void loadPerceivedArea()
	{
		if( getStaticPerceivedArea() == null )
			setStaticPerceivedArea( loadPerceivedAreaFromImage( getImage() ) );
	}


	@Override
	public ImageFormatInfo getImageFormatInfo() {
		return null;
	}



	/**
	 * NOT BEING USED, ONLY HERE CAUSE OF BAD INHERITANCE
	 */
	@Override
	public Image[] getStaticImages() {
		return null;
	}
	/**
	 * NOT BEING USED, ONLY HERE CAUSE OF BAD INHERITANCE
	 */
	@Override
	public void setStaticImages(Image[] staticImages) {
	}


	public Image getIconImage(){
		return null;
	}


	public BuildingAnim getBuildingAnim(){
		return buildingAnim;
	}

	/**
	 * The dirt backing behind a building
	 * @return
	 */
	public Anim getBacking(){
		return backing;
	}


	@Override
	public void saveYourself( BufferedWriter b ) throws IOException
	{

	}



	@Override
	public void setLoc( vector v )
	{
		super.setLoc(v);
		updateArea();
		//	deployLoc.set(v);
		//deployLoc.add( 0 , area.height()/2 + Rpg.eightDp);
	}


}
