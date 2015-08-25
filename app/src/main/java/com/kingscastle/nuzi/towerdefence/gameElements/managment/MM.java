package com.kingscastle.nuzi.towerdefence.gameElements.managment;

import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.util.Log;

import com.kingscastle.nuzi.towerdefence.TowerDefenceGame;
import com.kingscastle.nuzi.towerdefence.effects.EffectsManager;
import com.kingscastle.nuzi.towerdefence.effects.RisingTextManager;
import com.kingscastle.nuzi.towerdefence.effects.ShouldDrawAnimCalcer;
import com.kingscastle.nuzi.towerdefence.framework.DontUseThisMethodException;
import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.framework.WtfException;
import com.kingscastle.nuzi.towerdefence.gameElements.CD;
import com.kingscastle.nuzi.towerdefence.gameElements.GameElement;
import com.kingscastle.nuzi.towerdefence.gameElements.MapBorderObject;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.Humanoid;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.abilities.Ability;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Building;
import com.kingscastle.nuzi.towerdefence.gameElements.projectiles.Projectile;
import com.kingscastle.nuzi.towerdefence.gameElements.spells.Spell;
import com.kingscastle.nuzi.towerdefence.gameElements.targeting.TargetFinder;
import com.kingscastle.nuzi.towerdefence.gameUtils.CoordConverter;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.level.GridUtil;
import com.kingscastle.nuzi.towerdefence.level.Level;
import com.kingscastle.nuzi.towerdefence.teams.HumanTeam;
import com.kingscastle.nuzi.towerdefence.teams.Player;
import com.kingscastle.nuzi.towerdefence.teams.Team;
import com.kingscastle.nuzi.towerdefence.teams.TeamManager;
import com.kingscastle.nuzi.towerdefence.teams.Teams;
import com.kingscastle.nuzi.towerdefence.teams.races.Races;
import com.kingscastle.nuzi.towerdefence.ui.UI;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Manages the managers and other useful tools like the collision detector, the ShouldDrawAnimationCalculator
 * and the grid utility classes.
 * A new one of these should be created for each level.
 */
public class MM
{
	private static final String TAG = "ManagerManager" ;


	private static MM managerManager;

	@NotNull
	private final TeamManager tm;
	@NotNull
	private final TargetFinder targetFinder;
	private CD cd;
	@NotNull
	private final EffectsManager em;
	@NotNull
	private final RisingTextManager rtm;
	@NotNull
	private final GemManager gem;
	private ShouldDrawAnimCalcer sdac;
	private GridUtil gridUtil;


	private long timeForGemsToAct;

	private final Level level;
	private UI ui;



	public MM(Level level){
		this.level = level;
		tm = new TeamManager( this );
		targetFinder = new TargetFinder(tm);
		em = new EffectsManager(this);
		rtm = new RisingTextManager(this);
		gem = new GemManager(this);
	}

	public void addBorderElements( int lvlWidth , int lvlHeight )
	{
		float dpTimes10 = Rpg.getDp()*10;

		RectF topBorder = new RectF( -100000 , -100000 , lvlWidth + 100000 , 0 );
		RectF leftBorder = new RectF( -100000 , -100000 , 0 , lvlHeight + 100000 );
		RectF rightBorder = new RectF( lvlWidth - 0 , -1000000 , lvlWidth + 100000  , lvlHeight + 100000 );
		RectF bottomBorder = new RectF( -100000 , lvlHeight - 0 , lvlWidth + 100000  , lvlHeight + 100000 );


		MapBorderObject top = new MapBorderObject( topBorder );
		MapBorderObject left = new MapBorderObject( leftBorder );
		MapBorderObject right = new MapBorderObject( rightBorder );
		MapBorderObject bottom = new MapBorderObject( bottomBorder );

		gem.add(top); gem.add(left); gem.add(right); gem.add(bottom);

	}





	@NotNull
	public static MM get() throws DontUseThisMethodException {
		throw new DontUseThisMethodException("Cannot get MM from this static method anymore!");
		//if ( managerManager == null )
		//	managerManager = new MM();

		//return managerManager;
	}






	public boolean add( @NotNull GameElement ge )
	{
		//if( ge == null )
		//	throw new IllegalArgumentException( " Trying to add a null ge to a manager.");

		if( ge.getTeamName() == null ) {
			if( TowerDefenceGame.testingVersion )
				throw new IllegalArgumentException(ge + "'s team is null and trying to add to a manager.");
			else
				return false;
		}

		for( Team t : tm.getTeams() )
		{
			if( t.getTeamName() == ge.getTeamName() )
			{
				if( ge instanceof Building )
				{
					Building b = (Building) ge ;
					if( t.getBm().add( b )){
						return true;
					}
					////Log.d( TAG , "Adding " + ge + " on team " + t.getTeamName() + " to BM ");
				}
				else if ( ge instanceof Projectile )
				{
					////Log.d( TAG , "Adding " + ge + " on team " + t.getTeamName() + " to BM ");
					return t.getPm().add( (Projectile) ge );
				}
				else if ( ge instanceof Spell)
				{
					////Log.d( TAG , "Adding " + ge + " on team " + t.getTeamName() + " to BM ");
					return t.getSm().add( (Spell) ge );
				}
				else if ( ge instanceof Humanoid)
				{
					Humanoid lt = (Humanoid) ge;
					////Log.d( TAG , "Adding " + ge + " on team " + t.getTeamName() + " to AM ");
					if( t.getAm().add( lt ) ){
						t.onUnitCreated( lt );
						return true;
					}
				}
				else
					return false;

			}
		}

		return false;
	}


	public boolean add( @Nullable Ability ability )
	{
		if( ability == null )
			throw new IllegalArgumentException( " Trying to add a null ability to a manager.");

		for ( Team t : tm.getTeams() )
			if ( t.getTeamName() == ability.getTarget().getTeamName() )
				return t.getAbm().add( ability );

		return false;
	}



	public boolean addGameElement( @NotNull GameElement ge )
	{
		//////Log.d( TAG , ge + " being added to gem");
		vector loc = ge.loc;

		if( loc == null )
			throw new IllegalArgumentException( "Trying to add a " + ge + " and its loc is null." );


		if( level.getLevelWidthInPx() == 0 || level.getLevelHeightInPx()  == 0 )
			throw new IllegalArgumentException( "Map dimensions in Rpg have not been set yet." );

		int height = level.getLevelHeightInPx();
		int width = level.getLevelWidthInPx();
		int heightDiv2 = level.getLevelHeightInPx()/2;
		int widthDiv2 = level.getLevelWidthInPx()/2;

		float x = loc.x;
		float y = loc.y;

		if( x < 0 || y < 0 || x >= width || y >= height ){
			if( TowerDefenceGame.testingVersion ){
				Log.e( TAG , "Trying to add a ge outside the map");
			}
			return false;
		}


		boolean success = gem.add( ge );
		if( !success ){
			if( TowerDefenceGame.testingVersion ){
				Log.e( TAG , ge + "Could not be added to gem");
			}
			return false;
		}
		return true;
		/*
		float overlap = Rpg.twoHundDp;

		boolean left = x < widthDiv2 - overlap ;
		boolean top = y < heightDiv2 - overlap ;

		if( left && top )
		{
			success = gem.add( ge );
			//Log.d( TAG , ge + " added to top left." + success );
			return success;
			//return gem.add( ge );
		}


		boolean right = x > widthDiv2 + overlap;


		if( right && top )
		{
			success = gem2.add( ge );
			////////////Log.d( TAG , ge + " added to top right." + success );
			return success;

			//	return gem2.add( ge );
		}


		boolean bottom = y > heightDiv2 + overlap;


		if( right && bottom )
		{
			success = gem4.add( ge );
			////////////Log.d( TAG , ge + " added to bottom right." + success );;
			return success;
			//return gem4.add( ge );
		}

		if( left && bottom )
		{
			success = gem3.add( ge );
			////////////Log.d( TAG , ge + " added to bottom left." + success );
			return success;
			//return gem3.add( ge );
		}

		if( left )
		{
			success = gem.add( ge ) && gem3.add( ge );
			////////////Log.d( TAG , ge + " added to both the whole left side." + success );
			return success;
			//return gem.add( ge ) && gem3.add( ge );
		}

		if( top )
		{
			success = gem.add( ge ) && gem2.add( ge );
			////////////Log.d( TAG , ge + " added to both the whole top half." + success );
			return success;
			//return gem.add( ge ) && gem2.add( ge );
		}

		if( bottom )
		{
			success = gem3.add( ge ) && gem4.add( ge );
			////////////Log.d( TAG , ge + " added to both the whole bottom half." + success );
			return success;
			//return gem3.add( ge ) && gem4.add( ge );
		}

		if( right )
		{
			success = gem2.add( ge ) && gem4.add( ge );
			////////////Log.d( TAG , ge + " added to both the whole right side." + success );
			return success;
			//return gem2.add( ge ) && gem4.add( ge );
		}

		success = gem.add( ge ) && gem2.add( ge ) && gem3.add( ge ) && gem4.add( ge );

		//Log.d( TAG , ge + " added to all sections: " + success );
		return success;*/

	}



	public void act()
	{
		if( timeForGemsToAct < GameTime.getTime() )
		{
			timeForGemsToAct = GameTime.getTime() + 500;
			gem.act();
		}
		rtm.act();
		em.act();
	}


	@Nullable
	public ListPkg<Building> getBuildingsOnTeam( @NonNull Teams team )
	{
		if( team == null )
			throw new IllegalArgumentException("team == null");

		//Log.d( TAG , "getBuildingsOnTeam(" + team + ")");

		for ( Team t : tm.getTeams() )
			if ( t.getTeamName() == team )
				return t.getBm().getBuildings();

		//Log.d( TAG , "getBuildingsOnTeam("+team+"), Did not find a team with the correct name");
		return null;
	}



	@Nullable
	public Player getPlayer( Teams team )
	{
		for ( Team t : tm.getTeams() )
			if ( t.getTeamName() == team )
				return t.getPlayer();

		return null;
	}






	/**
	 * @return the rtm
	 */
	@NotNull
	public RisingTextManager getRtm() {
		return rtm;
	}



	/**
	 * @return the em
	 */
	@NotNull
	public EffectsManager getEm() {
		return em;
	}



	//
	//	public void setGem( GemManager gem2 )
	//	{
	//		gem = gem2;
	//	}



	@NotNull
	public GemManager getGem( float x , float y )
	{
		return gem;
		
//		if( x > level.getLevelWidthInPx()/2 )
//		{
//			if( y > level.getLevelHeightInPx()/2 )
//				return gem4;
//			else
//				return gem2;
//		}
//		else
//		{
//			if( y > level.getLevelHeightInPx()/2 )
//				return gem3;
//			else
//				return gem;
//		}
	}


	/**
	 * Won't be null.
	 */
	@NotNull
	public GemManager getGem(){
		return gem;
	}


	/**
	 * Won't be null.
	 */
	@NotNull
	public TeamManager getTM()	{
		return tm;
	}

	/**
	 * Won't be null.
	 */
	@NotNull
	public final TargetFinder getTargetFinder() {
		return targetFinder;
	}




	public void saveGems( BufferedWriter bw ) throws IOException {
		gem.saveYourself( bw );
	}


	/**
	 * Call after the level, ui and other objects have been created.
	 */
	public void finalInit() {
		tm.finalInit();
	}


	/**
	 * Call when you want the team and game element manager threads to stop running.
	 */
	public void onPause()
	{
		gem.pause();
		//Log.d( TAG , "before teamManager.pauseThreads()" );
		tm.pause();//pauseThreadsAndQueues();
		//Log.d( TAG , "after teamManager.pauseThreads()" );
	}

	/**
	 * Call when you want the team and game element manager threads to start running again.
	 */
	public void onResume()
	{
		gem.resume();
		////Log.d( TAG , "onResume()");
		tm.resume();
		////Log.d( TAG , "after teamManager.startThreadsAndResumeQueues()" );
	}


	//	public void onTownCenterDestroyed() {
	//		tm.onTownCenterDestroyed();
	//	}




	//	public void reset()
	//	{
	//		nullify();
	//
	//		tm = new TeamManager(this);
	//		tm.reset();
	//
	//		em = new EffectsManager(this);
	//		rtm = new RisingTextManager(this);
	//
	//		gem  = new GemManager   (this);
	//		gem = new SubGemManager(this);
	//		gem2 = new SubGemManager(this);
	//		gem3 = new SubGemManager(this);
	//		gem4 = new SubGemManager(this);
	//	}



	//	void nullify()
	//	{
	//
	//		if( tm != null )
	//		{
	//			tm.pause();
	//			tm.nullify();
	//		}
	//		if( em != null )
	//		{
	//			em.nullify();
	//			em = null;
	//		}
	//		if( rtm != null )
	//		{
	//			rtm.nullify();
	//			rtm = null;
	//		}
	//		if( gem != null )
	//		{
	//			gem.nullify();
	//			gem = null;
	//		}
	//		if( gem != null )
	//		{
	//			gem.nullify();
	//			gem = null;
	//		}
	//		if( gem2 != null )
	//		{
	//			gem2.nullify();
	//			gem2 = null;
	//		}
	//		if( gem3 != null )
	//		{
	//			gem3.nullify();
	//			gem3 = null;
	//		}
	//		if( gem4 != null )
	//		{
	//			gem4.nullify();
	//			gem4 = null;
	//		}
	//	}


	public void remove(@Nullable Team team) {
		if( tm != null && team != null )
			tm.remove(team);
	}


	public void add(@Nullable Team team) {
		if( tm != null && team != null )
			tm.add(team);
	}

	/**
	 * May return null
	 */
	public Team getTeam(Teams team) {
		return tm.getTeam(team);
	}


	@Nullable
	public Races getRace(Teams team) {
		Team t = getTeam( team );
		if( t != null )
			return t.getRace().getRace();
		return null;
	}


	@NotNull
	public Level getLevel() {
		return level;
	}

	public void setUI(@NotNull UI ui) {
		this.ui = ui;
		if( sdac == null )
			sdac = new ShouldDrawAnimCalcer(level, ui.getStillDrawArea(), tm);
		if( cd == null )
			cd = new CD( this , level.getGrid() );
		if( gridUtil == null )
			gridUtil = new GridUtil( this , level.getGrid() , level.getMapWidth() , level.getMapHeight() );
	}

	@NotNull
	public CoordConverter getCc(){
		return level;
	}



	public UI getUI() {
		return ui;
	}


	public boolean isHumanTeam(Teams team) {
		Team t = getTeam( team );

		return t instanceof HumanTeam;
	}


	@NonNull
	public CD getCD() {
		if( cd == null )
			throw new WtfException("Getting cd before it has been created");
		return cd;
	}

	public ShouldDrawAnimCalcer getSdac() {
		return sdac;
	}

	public GridUtil getGridUtil() {
		if( gridUtil == null )
			throw new WtfException("Getting gridUtil before it has been created");
		return gridUtil;
	}



}
