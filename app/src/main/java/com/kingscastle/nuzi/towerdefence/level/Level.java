package com.kingscastle.nuzi.towerdefence.level;


import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;

import com.kingscastle.nuzi.towerdefence.TowerDefenceGame;
import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.framework.Settings;
import com.kingscastle.nuzi.towerdefence.framework.SoundController;
import com.kingscastle.nuzi.towerdefence.framework.WtfException;
import com.kingscastle.nuzi.towerdefence.framework.implementation.GameMusic;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameElements.movement.Inter;
import com.kingscastle.nuzi.towerdefence.gameElements.movement.pathing.AStarPathFinder;
import com.kingscastle.nuzi.towerdefence.gameElements.movement.pathing.Grid;
import com.kingscastle.nuzi.towerdefence.gameUtils.CoordConverter;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.teams.HumanPlayer;
import com.kingscastle.nuzi.towerdefence.ui.UI;
import com.kingscastle.nuzi.towerdefence.ui.buttons.Zoomer;

import java.util.ArrayList;
import java.util.HashMap;


public abstract class Level implements CoordConverter,SoundController
{
	private static final String TAG = "Level";

	public enum GameState {
		Created, Started, Resumed, LoadingScreen, InGamePlay, Paused, Stopped, GameOver, PausedInGame;
	}

	private boolean onCreateCalled;
	private boolean onStartCalled;

	protected TowerDefenceGame tdg;
	private GameThread gt;

	protected long timeout;

	protected RectF onScreenArea;

	protected HumanPlayer hPlayer;

	protected MM mm = new MM(this);
	protected UI ui;

	private int width = 50;
	private int height = 25;

	protected final Background background;
	private final Grid grid;

	protected HashMap<String, String> extras;

	protected float dp = Rpg.getDp();
	private boolean firstTimeCreate = true;
	private volatile boolean ready;
	protected boolean fromSavedState = false;
	protected boolean hasFinallyInited = false;
	protected boolean hasSetLevelParams = false;

	protected boolean paused = true;
	private boolean gameOver = false;

	//private final Music inGameMusic;

	private String saveGameName;


	public Level()
	{
		setWidth(50);
		setHeight(30);

		gt = new GameThread();

		int normalGridSize = (int) Rpg.sixTeenDp;
		grid = new Grid( getWidth() , getHeight() , normalGridSize );

		//FIXME bad coding, class that's accessed non-statically but must have the grid set statically b4 you try to use it....
		//Maybe when I create legs i can pass it the grid since ill have access to it through mm
		Inter.setGrid(grid);

		AStarPathFinder.initializeGrids(getWidth(), getHeight());
		//Log.d( TAG , "Rpg.sixTeenDp = " + Rpg.sixTeenDp );

		//Log.d( TAG , "tileGridSize = " + tileGridSize );
		background = new Background( getWidth() , getHeight() , new Background.ScreenWidthProvider() {
			@Override
			public int getScreenWidth() {
				if(ui != null)
					return ui.getScreenWidth();
				return 0;
			}

			@Override
			public int getScreenHeight() {
					if(ui != null)
						return ui.getScreenHeight();
				return 0;
			}
		}, Rpg.getWidth(), Rpg.getHeight());


		Zoomer.clearZlcls();
		Zoomer.addZlcl(new Zoomer.onZoomLevelChangedListener() {
			@Override
			public void onZoomLevelChanged(float xScale, float yScale) {
				background.adjustCenteredOn();
				background.adjustScreenArea();
			}
		});

		hasSetLevelParams = true;

		//inGameMusic = loadMusic();

	}

	private long lastTime = System.currentTimeMillis();

	/** */
	public final void act()
	{
		mm.act();

		if( !paused ){
			GameTime.incTime(System.currentTimeMillis()-lastTime);
			lastTime = System.currentTimeMillis();
		}

		subAct();
	}

	/** Override if you want to do something every time the level gets a chance to update */
	protected void subAct() {
	}

	/**
	 * Creates the players, teams, adds map border objects, no build zones, adds start and end
	 * location animations, tree border, creates the first round object and centers the map
	 */
	public final void onCreate(UI ui, TowerDefenceGame tdg){
		Log.v(TAG,"onCreate()");
		onCreateCalled = true;

		this.ui = ui;
		this.tdg = tdg;
		this.onScreenArea =  ui.getOnScreenArea();
		mm.setUI(ui);
		getBackground().setCenteredOn(new vector(getBackground().getWidthInPx() / 2, getBackground().getHeightInPx() / 2));
		mm.addBorderElements(getLevelWidthInPx(), getLevelHeightInPx());

		getBackground().scrollBy(5, 5);

		subOnCreate();
	}



	/**
	 * Should be called by the game when it receives its onStart call from the game activities onStart call
	 */
	public final void onStart()
	{
		Log.v(TAG,"onStart()");
		if( !onCreateCalled )
			throw new WtfException("You must call onCreate before onStart!");
		onStartCalled = true;

		timeout = GameTime.getTime() + 4000;

		//Let the subclass do stuff on start
		subOnStart();
	}


	/**
	 * Should called when your activity has its onResume called in order to restart the game.
	 */
	public final void onResume()
	{
		Log.v(TAG,"onResume()");
		if( !onStartCalled )
			throw new WtfException("You must call onStart before onResume!");

		//Team humanTeam = mm.getTeam(Teams.BLUE);

		gt.resume(this);
		mm.onResume();
		if( !Settings.muteMusic )
			startMusic();
		//Let the subclass do stuff on resume
		subOnResume();
	}

	/** Use this to start the team-threads when the user presses the unpause button*/
	public void unPause(){
		Log.v(TAG,"unPause()");
		paused = false;
		lastTime = System.currentTimeMillis();
		//mm.onResume();
	}
	/** Use this to pause the team-threads when the user presses the pause button */
	public void pause(){
		Log.v(TAG,"pause()");
		paused = true;
		//mm.onPause();
	}

	/**
	 * Should be called when your activity has its onPause called so the game can be paused.
	 */
	public final void onPause()
	{
		Log.v(TAG,"onPause()");
		paused = true;
		gt.pause();
		mm.onPause();

//		UI ui = mm.getUI();
//		if( ui != null )
//			ui.onPause();

		stopMusic();

		//Let the subclass do stuff on pause
		subOnPause();
	}

	public void onStop(){
		Log.v(TAG,"onStop()");
		//Let the subclass do stuff on pause
		subOnStop();
	}

	/** Override if needed */
	protected void subOnCreate() {
	}
	/** Override if needed */
	protected void subOnStart() {
	}
	/** Override if needed */
	protected void subOnResume() {
	}
	/** Override if needed */
	protected void subOnPause() {
	}
	/** Override if needed */
	protected void subOnStop() {
	}

	//**********         End Activity-like methods         ************//



	/**
	 * Must be called after the onCreate(UI ui, RectF onScreenArea)
	 * or at least at the end of the method.
	 */
	protected abstract void createBorder();




	public void finalInit()
	{
		loadLevel();
		mm.finalInit();
	}


	public boolean isPaused() {
		return paused;
	}




	//***********************   Music Methods    ***********************///

	@Override
	public void startMusic()
	{
		GameMusic.playMedievalShort();
		//if( inGameMusic != null )
		//	inGameMusic.play();
	}

//	protected Music loadMusic()
//	{
//		try
//		{
//			Music music = Rpg.getGame().getAudio().createMusic( "castlecall.mp3" );
//			music.setLooping( true );
//			music.setVolume(0.5f);
//			return music;
//		}
//		catch( Exception e ){
//			e.printStackTrace();
//		}
//		return null;
//	}

	@Override
	public void stopMusic()
	{
		GameMusic.stopMedievalShort();
//		if( inGameMusic != null )
//			inGameMusic.pause();
	}

	//*********************   End Music Methods    *********************///


	private boolean playerHasWonOrLost;

	protected void playerHasWon(){
		Log.v(TAG, "On Player Won");
		playerHasWonOrLost = true;
		pause();
		synchronized (pwls){
			for (OnGameEndListener gol :pwls){
				gol.onPlayerWon();
			}
		}
	}

	protected void playerHasLost(){
		Log.v(TAG, "On Game Over");
		playerHasWonOrLost = true;
		pause();
		setGameOver(true);
		synchronized (pwls){
			for (OnGameEndListener gol :pwls){
				gol.onPlayerLost();
			}
		}
	}

	public boolean playerHasWonOrLost() {
		return playerHasWonOrLost;
	}



	protected void loadLevel(){

	}


	public void nullify()
	{
		background.nullify();
	}



	public void setFromSavedState(boolean b) {
		fromSavedState = b;
	}


	public void loadExtras( Bundle b )
	{
	}



	//***************   Background Methods   ****************//

	public Background getBackground()
	{
		return background;
	}

	public int getLevelWidthInPx() {
		return getBackground().getWidthInPx();
	}
	public int getLevelHeightInPx() {
		return getBackground().getHeightInPx();
	}

	@Override
	public int getMapWidth() {
		return getBackground().getWidthInPx();
	}
	@Override
	public int getMapHeight() {
		return getBackground().getHeightInPx();
	}


	protected int getWidth()
	{
		return width;
	}
	protected int getHeight()
	{
		return height;
	}


	void setWidth(int width) {
		this.width = width;
	}
	void setHeight(int height) {
		this.height = height;
	}


	public void setNightTime(boolean nightTime){
		tdg.getGameScreen().setNightTime(nightTime);
	}







	public boolean isGameOver() {
		return gameOver;
	}
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}




	/**
	 * Not Null
	 * @return The grid
	 */
	public Grid getGrid(){
		return grid;
	}




	//**************************  Coordinate Conversion Methods   ********************************//


	public vector getCoordinatesMapToScreen(float x, float y) {
		return getBackground().getScrollingBackground().getCoordinatesMapToScreen(
				x, y);
	}

	public vector getCoordinatesScreenToMap(float x, float y) {
		return getBackground().getScrollingBackground().getCoordinatesScreenToMap(
				x, y);
	}


	@Override
	public vector getCoordsScreenToMap(float x, float y, vector intoThis) {
		return getBackground().getScrollingBackground().getCoordinatesScreenToMap(
				x, y, intoThis);
	}

	@Override
	public vector getCoordsScreenToMap(vector v, vector intoThis) {
		return getBackground().getScrollingBackground().getCoordinatesScreenToMap(
				v.x, v.y, intoThis);
	}

	@Override
	public vector getCoordsMapToScreen(float x, float y, vector intoThis) {
		return getBackground().getScrollingBackground().getCoordinatesMapToScreen(
				x, y, intoThis);
	}

	@Override
	public vector getCoordsMapToScreen(vector v, vector intoThis) {
		return getBackground().getScrollingBackground().getCoordinatesMapToScreen(
				v.x, v.y, intoThis);
	}

	//************************  End Coordinate Conversion Methods   ******************************//





	public void setSaveGameName( String saveFileName ){
		saveGameName = saveFileName;
	}




	public MM getMM() {
		return mm;
	}


	public HumanPlayer getHumanPlayer() {
		return hPlayer;
	}




	/** Sets the next round to start in timeout+current time ms in the future */
	public void setTimeout(int timeout) {
		this.timeout = timeout + GameTime.getTime();
	}






	//************************  Listener Methods And Interfaces   ********************************//

	//Player Won Listener
	protected final ArrayList<OnGameEndListener> pwls = new ArrayList<>();

	public interface OnGameEndListener {
		void onPlayerWon();
		void onPlayerLost();
	}

	public void addPWL(OnGameEndListener l)		   		{	synchronized( pwls ){	pwls.add( l );			}  	}
	public boolean removePWL(OnGameEndListener l)		{	synchronized( pwls ){	return pwls.remove( l );		}	}

}
