package com.kingscastle.nuzi.towerdefence;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.kingscastle.nuzi.towerdefence.effects.SpecialEffects;
import com.kingscastle.nuzi.towerdefence.framework.Audio;
import com.kingscastle.nuzi.towerdefence.framework.Console;
import com.kingscastle.nuzi.towerdefence.framework.Graphics;
import com.kingscastle.nuzi.towerdefence.framework.Input;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.framework.Screen;
import com.kingscastle.nuzi.towerdefence.framework.Settings;
import com.kingscastle.nuzi.towerdefence.framework.implementation.AndroidFastRenderView;
import com.kingscastle.nuzi.towerdefence.framework.implementation.AndroidFileIO;
import com.kingscastle.nuzi.towerdefence.framework.implementation.AndroidGraphics;
import com.kingscastle.nuzi.towerdefence.framework.implementation.AndroidInput;
import com.kingscastle.nuzi.towerdefence.framework.implementation.GameMusic;
import com.kingscastle.nuzi.towerdefence.GameActivity;
import com.kingscastle.nuzi.towerdefence.GameOverActivity;
import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Building;
import com.kingscastle.nuzi.towerdefence.gameUtils.Difficulty;
import com.kingscastle.nuzi.towerdefence.level.Forest;
import com.kingscastle.nuzi.towerdefence.level.Level.GameState;
import com.kingscastle.nuzi.towerdefence.level.PR;
import com.kingscastle.nuzi.towerdefence.level.TowerDefenceLevel;
import com.kingscastle.nuzi.towerdefence.level.TowerDefenceLevel.RoundOverListener;
import com.kingscastle.nuzi.towerdefence.level.TowerDefenceLevelSaverLoader;
import com.kingscastle.nuzi.towerdefence.level.TowerDefenceLevels;
import com.kingscastle.nuzi.towerdefence.teams.HumanPlayer;
import com.kingscastle.nuzi.towerdefence.teams.Player;
import com.kingscastle.nuzi.towerdefence.teams.Team;
import com.kingscastle.nuzi.towerdefence.teams.Teams;
import com.kingscastle.nuzi.towerdefence.ui.BuildingBuilder;
import com.kingscastle.nuzi.towerdefence.ui.GameScreen;
import com.kingscastle.nuzi.towerdefence.ui.UI;
import com.kingscastle.nuzi.towerdefence.ui.ViewAnimatorHelper;
import com.kingscastle.nuzi.towerdefence.ui.buttons.Zoomer;
import com.kingscastle.nuzi.towerdefence.util.Strings;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Main game class
 */
public class TowerDefenceGame implements View.OnClickListener, RoundOverListener, BuildingBuilder.OnPendingBuildingSet, TowerDefenceLevel.OnGameEndListener {

    private static final String TAG = "TowerDefenceGame";
    public static final String FILENAME = "savedGameFile";
    public static boolean testingVersion = false;
    private final float scaleX;
    private final float scaleY;

    private boolean paused;

    public String uiThreadName = "STHSDRTHDRTHSDRthdr";
    private boolean over;


    public enum ScreenSize {
        Small , Normal , Large , XLarge
    }
    public static final String savedGameName = "c0krR1IClt7c";

    private GameActivity gameActivity;

    protected AndroidFastRenderView renderView;
    private Graphics graphics;
    private Audio audio;
    private Input input;
    private AndroidFileIO fileIO;

    private Screen screen;

    private final TowerDefenceLevel level;
    private UI ui;
    private GameScreen gameScreen;

    private int screenWidth;
    private int screenHeight;
    private float dp;

    private ScreenSize size;


    public float origScaleX, origScaleY;

    private GameState state;

    private Handler uiHandler;



    public TowerDefenceGame(GameActivity gameActivity, AndroidFastRenderView surface, String levelClassName , Difficulty difficulty){
        Log.d(TAG, "new TowerDefenceGame()");
        this.gameActivity = gameActivity;

        dp = gameActivity.getResources().getDimension(R.dimen.onedp);

        DisplayMetrics displayMetrics = gameActivity.getResources().getDisplayMetrics();

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;


        float dpHeight = displayMetrics.ydpi;
        float dpWidth  = displayMetrics.xdpi;

        screenWidth = width;
        screenHeight = height;

        boolean isPortrait = gameActivity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        setSize( determineScreenSize( dpWidth , dpHeight ));

        int frameBufferWidth = isPortrait ? height: width;
        int frameBufferHeight = isPortrait ? width: height;


        scaleX = (float) frameBufferWidth
                / width;
        scaleY = (float) frameBufferHeight
                / height;

        origScaleX = scaleX;
        origScaleY = scaleY;


        renderView = surface;
        graphics = new AndroidGraphics( gameActivity.getAssets() , gameActivity.getResources() , frameBufferWidth , frameBufferHeight ); //, frameBuffer);
        //audio = new AndroidAudio(gameActivity);
        fileIO = new AndroidFileIO(gameActivity);
        input = new AndroidInput(gameActivity, surface, scaleX, scaleY);

        //@FIXME Probably shouldn't add a static accessor... bad design
        Rpg.holdThis(this);


        String levelClass = levelClassName;
        ObjectInputStream ois = null;
        try {
            FileInputStream fis = gameActivity.openFileInput(FILENAME);
            ois = new ObjectInputStream(fis);
            levelClass = (String) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            levelClass = Forest.class.getSimpleName(); //Guarantees there's no spelling issues
        }

        level = TowerDefenceLevels.get(levelClass);
        level.setDifficulty(difficulty);
        ui = new UI(this, level);

        gameScreen = new GameScreen(this,level,ui);

        SpecialEffects.setup(ui.getStillDrawArea(), level.getMM());

        level.onCreate(ui, this);

        setScreen(gameScreen);

        if( ois != null ) {
            try {
                TowerDefenceLevelSaverLoader.loadGame(ois, level);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        TowerDefenceLevel tdl = level;

        tdl.addROL(this);
        tdl.addPWL(this);
        level.getMM().getTeam(Teams.BLUE).addBdl(new Team.OnBuildingDestroyedListener() {
            @Override
            public void onBuildingDestroyed(Building b) {
                if (b.isSelected()) ui.clearSelected();
            }
        });
    }



    /**
     * This is called with a new GameActivity instance when the user has pressed the back button to go to the main menu but then
     * clicks the continue game button. This is used to set the use the new renderview created by the new Activity instance.
     * This method also adds listeners connecting the game to the new views so they can be updated when the game's states change.
     */
    public void onCreate(GameActivity gameActivity, AndroidFastRenderView surface) {
        Log.d(TAG, "onCreate()");
        state = GameState.Created;
        this.gameActivity = gameActivity;

        //If there is a previous renderView alive ensure its render thread has stopped.
        if( renderView != null )
            renderView.onPause();
        renderView = surface;

        input = new AndroidInput(gameActivity, surface, scaleX, scaleY);


        //Add new listeners connecting this new activity instances views to the game.
        gameActivity.findViewById(R.id.build_button).setOnClickListener(this);
        gameActivity.findViewById(R.id.build_now_button).setOnClickListener(this);
        gameActivity.findViewById(R.id.cancel_button).setOnClickListener(this);
        gameActivity.findViewById(R.id.pause_button).setOnClickListener(this);
        gameActivity.findViewById(R.id.start_button).setOnClickListener(this);


        ui.bb.addPBSL(this);

        final TextView goldDisplay = (TextView) gameActivity.findViewById(R.id.gold_textView);
        goldDisplay.setText("Gold: " + level.getHumanPlayer().getPR().getGold());
        level.getHumanPlayer().getPR().addRVCL(new PR.OnResourceValuesChangedListener() {
            int lastVal = -82457834;
            @Override
            public void onGoldValueChanged(int newGoldValue) {
                if (newGoldValue != lastVal) {
                    setText(goldDisplay, "Gold: " + newGoldValue);
                    lastVal = newGoldValue;
                }
            }
        });

        final TextView livesDisplay = (TextView) gameActivity.findViewById(R.id.lives_textView);
        livesDisplay.setText("Lives: " + level.getHumanPlayer().getLives());
        level.getHumanPlayer().addLVCL(new HumanPlayer.onLivesValueChangedListener() {
            int lastVal = -25475782;
            @Override
            public void onLivesValueChanged(int newLivesValue) {
                if (newLivesValue != lastVal) {
                    setText(livesDisplay, "Lives: " + newLivesValue);
                    lastVal = newLivesValue;
                }
            }
        });

        final TextView roundDisplay = (TextView) gameActivity.findViewById(R.id.round_textview);
        roundDisplay.setText("Round: " + level.getRound().getRoundNum());
        setupConsoleInput(gameActivity);
    }

    public void onStart(final GameActivity gameActivity)
    {
        Log.d(TAG, "onStart()");
        state = GameState.Started;
        this.gameActivity = gameActivity;
        uiHandler = new Handler();

        showGameStartingText();
        gameActivity.findViewById(R.id.pause_button).setVisibility(View.INVISIBLE);
        gameActivity.findViewById(R.id.start_button).setVisibility(View.VISIBLE);

        level.onStart();
    }

    public void onResume(final GameActivity gameActivity)
    {
        Log.d(TAG,"onResume()");

        state = GameState.Resumed;

        Screen s = getScreen();
        if( s != null )
            s.resume();

        ui.onResume();

        level.onResume();
        renderView.onResume(TowerDefenceGame.this);

        Zoomer.setup(gameActivity, (ZoomControls) gameActivity.findViewById(R.id.zoomControls), renderView);


        int lvlWidthPx = level.getLevelWidthInPx();
        int lvlHeightPx = level.getLevelHeightInPx();
        float scaleX = 1, scaleY = 1;


        if( lvlWidthPx < screenWidth )
            scaleX = (float) screenWidth / lvlWidthPx;

        if( lvlHeightPx < screenHeight )
            scaleY = (float) screenHeight / lvlHeightPx;

        float minScale = Math.max(scaleX, scaleY);
        Zoomer.setMinScale(minScale);


        state = GameState.InGamePlay;

        //Cause the loaded monsters to find their path to the objective AFTER the grid has been worked
        //by the game thread so it updates the unwalkable tiles
//        gt.addSucl(new GameThread.OnLevelUpdateCompleteListener() {
//            @Override
//            public boolean onScreenUpdateComplete() {
//                ArmyManager am = level.getMM().getTeam(Teams.RED).getAm();
//                for (final LivingThing lt : am.getArmy()) {
//                    PathFinderParams pfp = new PathFinderParams();
//                    pfp.grid = level.getGrid();
//                    pfp.fromHere = lt.getLoc();
//                    pfp.toHere = level.getEndLoc();
//                    pfp.mapWidthInPx = level.getLevelWidthInPx();
//                    pfp.mapHeightInPx = level.getLevelHeightInPx();
//                    pfp.pathFoundListener = new PathFoundListener() {
//                        @Override
//                        public void onPathFound(Path path) {
//                            lt.setPathToFollow(path);
//                        }
//
//                        @Override
//                        public void cannotPathToThatLocation(String reason) {
//                            Log.e(TAG, "Cannot path to destination! " + reason);
//                        }
//                    };
//
//                    PathFinder.findPath(pfp);
//                }
//                return true;
//            }
//        });
    }

    public void onPause()
    {
        Log.d(TAG, "onPause()");
        state = GameState.Paused;
        renderView.onPause();
        Screen s = getScreen();
        if( s != null ){
            s.pause();
            if ( gameActivity.isFinishing() )
                s.dispose();
        }

        level.onPause();
        ui.onPause();
    }

    public void onStop()
    {
        Log.d(TAG, "onStop()");
        state = GameState.Stopped;

        level.onStop();
        ui.onStop();

        if( !level.playerHasWonOrLost() ){
            new Thread(){
                @Override
                public void run() {
                    Settings.savingLevel = true;
                    try {
                        TowerDefenceLevelSaverLoader.saveLevel(getActivity().openFileOutput(FILENAME, Context.MODE_PRIVATE), level);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally{
                        Settings.savingLevel = false;
                    }

                }
            }.start();
        }
        else
            gameActivity.finish();
    }


    //End Android Activity Life Cycle Activities


    public void onGameOver(){
        Log.v(TAG, "On Game Over");
//        if( testingVersion )
//            return;
        over = true;
        new Thread(){
            @Override
            public void run() {
              //  level.onPause();
                getActivity().deleteFile(FILENAME);
                Intent i = new Intent(getActivity(), GameOverActivity.class);
                i.putExtra("RoundNum", level.getRound().getRoundNum());
                getActivity().finish();
                getActivity().startActivity(i);
                Log.v(TAG, "Starting game over activity!");
            }
        }.start();
    }



    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.build_button:{
                v.setVisibility(View.INVISIBLE);

                ui.bb.showScroller(this, (ScrollView) gameActivity.findViewById(R.id.towers_scroll_view) ,(LinearLayout) gameActivity.findViewById(R.id.building_buttons));

                //gameActivity.findViewById(R.id.towers_scroll_view).setVisibility(View.VISIBLE);
                gameActivity.findViewById(R.id.cancel_button).setVisibility(View.VISIBLE);
                break;
            }
            case R.id.build_now_button:{
                if(ui.bb.buildPendingBuilding(getActivity())) {
                    v.setVisibility(View.INVISIBLE);
                    gameActivity.findViewById(R.id.build_button).setVisibility(View.VISIBLE);
                    gameActivity.findViewById(R.id.cancel_button).setVisibility(View.INVISIBLE);
                }
                break;
            }
            case R.id.cancel_button:{
                ui.bb.setPendingBuilding(null);
                ui.bb.clearScrollerButtons(getActivity(), (ScrollView) gameActivity.findViewById(R.id.towers_scroll_view), (LinearLayout) gameActivity.findViewById(R.id.building_buttons));
                v.setVisibility(View.INVISIBLE);
                gameActivity.findViewById(R.id.build_now_button).setVisibility(View.INVISIBLE);
                gameActivity.findViewById(R.id.build_button).setVisibility(View.VISIBLE);
                Settings.showAllAreaBorders = false;
                break;
            }
            case R.id.pause_button:{
                level.pause();
                ViewAnimatorHelper.shinkAway(v, 400, new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ViewAnimatorHelper.grow(gameActivity.findViewById(R.id.start_button), 400, null);
                    }
                });

                break;
            }
            case R.id.start_button:{
                onResume(gameActivity);
                ViewAnimatorHelper.shinkAway(v, 400, new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ViewAnimatorHelper.grow(gameActivity.findViewById(R.id.pause_button), 400, null);
                    }
                });

                level.unPause();
                hideGameStartingText();
                break;
            }
        }
    }



    private void hideGameStartingText(){
        final View gameStartingText = gameActivity.findViewById(R.id.game_starting);
        ValueAnimator a = ValueAnimator.ofFloat(0f,gameStartingText.getHeight());
        a.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                gameStartingText.setTranslationY(gameStartingText.getTranslationY() + (Float) animation.getAnimatedValue());
            }
        });
        a.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                gameStartingText.setVisibility(View.INVISIBLE);
            }
        });
        a.setDuration(200);
        a.start();
    }

    private void showGameStartingText(){

        final View gameStartingText = gameActivity.findViewById(R.id.game_starting);
        ViewAnimatorHelper.grow(gameStartingText, 500, null);
        uiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ViewAnimatorHelper.slideToBottomOfScreen(gameStartingText, screenHeight, 500, null);
            }
        }, 2000);

    }




    @Override
    public void onPendingBuildingSet(Building b) {
        if( b != null ){
            gameActivity.findViewById(R.id.build_now_button).setVisibility(View.VISIBLE);
            ui.bb.clearScrollerButtons(getActivity(), (ScrollView) gameActivity.findViewById(R.id.towers_scroll_view), (LinearLayout) gameActivity.findViewById(R.id.building_buttons));
        }
    }

    private boolean gameIsOver = false;

    @Override
    public void onRoundOver(final int roundNum) {
        Log.v(TAG, "On Round Over");
        if( gameIsOver )
            return;

        level.getHumanPlayer().getPR().add((int) ((roundNum*20)/level.getDifficulty().multiplier),0,0);

        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                TextView v = (TextView) gameActivity.findViewById(R.id.round_over);
                ViewAnimatorHelper.grow(v, 500, null);
                final TextView roundOverTV = ((TextView) gameActivity.findViewById(R.id.round_textview));
                ViewAnimatorHelper.shinkAway(roundOverTV, 300, new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        roundOverTV.setText("Round: " + (roundNum + 1));
                        ViewAnimatorHelper.grow(roundOverTV, 300, null);
                    }
                });

//                TextView goldEarned = (TextView) gameActivity.findViewById(R.id.gold_earned);
//                ViewAnimatorHelper.grow(goldEarned, 500, null);

            }
        });
        uiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                View v = gameActivity.findViewById(R.id.round_over);
                v.setVisibility(View.INVISIBLE);
            }
        }, 3000);

    }

    @Override
    public void onPlayerWon() {
        Log.v(TAG, "On Player Won");
        gameIsOver = true;
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                TextView v = (TextView) gameActivity.findViewById(R.id.level_won);
                v.setText("You Won!\nClick the back button\nto try another level!");
                ViewAnimatorHelper.grow(v, 500, null);
            }
        });
        getActivity().deleteFile(FILENAME);

        SharedPreferences sp = gameActivity.getSharedPreferences(Strings.LevelDetails, Context.MODE_PRIVATE);
        Editor e = sp.edit();
        String levelClassName = level.getClass().getSimpleName();

        if(sp.getLong(levelClassName + "Score",0) < level.getPlayersScore() )
            e.putLong(levelClassName + "Score", level.getPlayersScore());

        e.putBoolean(levelClassName + level.getDifficulty().name(), true);
        e.apply();
    }

    @Override
    public void onPlayerLost() {
        Log.v(TAG, "On Player Lost");
        gameIsOver = true;
        onGameOver();
//        uiHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                TextView v = (TextView) gameActivity.findViewById(R.id.level_won);
//                v.setText("You Lost!\nClick the back button\nto try again!");
//                ViewAnimatorHelper.grow(v, 500, null);
//                gameActivity.findViewById(R.id.pause_button).setVisibility(View.INVISIBLE);
//                gameActivity.findViewById(R.id.start_button).setVisibility(View.INVISIBLE);
//            }
//        });
        String levelClassName = level.getClass().getSimpleName();
        SharedPreferences sp = gameActivity.getSharedPreferences(Strings.LevelDetails, Context.MODE_PRIVATE);
        if(sp.getLong(levelClassName + "Score",0) < level.getPlayersScore() ) {
            Editor e = sp.edit();
            e.putLong(levelClassName + "Score", level.getPlayersScore());
            e.apply();
        }

//        for( File f : gameActivity.getFilesDir().listFiles()){
//            if( f.getName().equals(TowerDefenceGame.FILENAME)) {
//                Log.d(TAG, "Deleting " + f);
//                f.delete();
//            }
//        }
    }

    /**
     * Only shows console in testing version. Sets up a listener on the edit text to be used to debug
     * The console commands are setup in the Console class.
     */
    private void setupConsoleInput(GameActivity gameActivity) {

        //Only used in testing version
        final EditText text = (EditText) gameActivity.findViewById(R.id.console_edittext);

        if( TowerDefenceGame.testingVersion ){
            Console.tdg = this;
            text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                private static final String hideThis = "hidethis";
                @Override
                public boolean onEditorAction(TextView v_, int actionId_, KeyEvent event_) {
                    try{
                        String input = text.getText().toString();
                        if( hideThis.equals( input ) )
                            text.setVisibility(View.INVISIBLE);
                        Console.cmd(input);
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                    text.setText("");
                    return false;
                }
            });
        }else
            text.setVisibility(View.GONE);
    }


    public void setText(final TextView tv , final String txt){
        if( uiHandler != null )
            uiHandler.post(new Runnable() {
                @Override
                public void run() {
                    tv.setText(txt);
                }
            });
    }



    public void backButton() {
        onPause();
    }

    public Input getInput() {
        return input;
    }


    public Graphics getGraphics()
    {
        return graphics;
    }


    public Audio getAudio() {
        return GameMusic.getAudio();
    }

    public void setScreen( Screen screen )
    {

        if( this.screen != null )
        {
            this.screen.pause();
            this.screen.dispose();
        }
        if( screen != null ) {
            screen.resume();
            //screen.update();
        }

        this.screen = screen;
    }



    public AndroidFastRenderView getRenderView(){
        return renderView;
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public AndroidFileIO getFileIO() {
        return fileIO;
    }

    public void setFileIO(AndroidFileIO fileIO)
    {
        this.fileIO = fileIO;
    }


    protected void nullify(){

    }


    ScreenSize getSize()
    {
        return size;
    }
    void setSize(ScreenSize size)
    {
        this.size = size;
    }



    protected Screen getScreen() {
        return screen;
    }
    public Screen getCurrentScreen() {
        return getScreen();
    }

    public Activity getActivity(){
        return gameActivity;
    }

    public void setActivity(GameActivity activity) {
        this.gameActivity = activity;
    }

    public TowerDefenceLevel getLevel(){
        return level;
    }

    public UI getUI(){
        return ui;
    }



    public boolean isPaused(){
        return paused;
    }


    public GameState getState() {
        return state;
    }

    public void setState(GameState st){
        state = st;
    }


    public Player getPlayer() {
        return level.getHumanPlayer();
    }

    public void alert(String s, int lengthShort) {
    }

    public boolean isOver() {
        return gameIsOver;
    }






    private ScreenSize determineScreenSize( float dpWidth , float dpHeight )
    {
        if( dpWidth >= 960 || dpHeight >= 720 )
        {
            return ScreenSize.XLarge;
        }
        else if(dpWidth>=640||dpHeight>=480)
        {
            return ScreenSize.Large;
        }
        else if(dpWidth>=470||dpHeight>=320)
        {
            return ScreenSize.Normal;
        }
        else{
            return ScreenSize.Small;
        }
    }

    public float getDp()
    {
        if( dp == 0 )
            dp = gameActivity.getResources().getDimension( R.dimen.onedp );

        return dp;
    }
}
