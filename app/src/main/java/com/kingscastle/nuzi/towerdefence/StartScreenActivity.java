package com.kingscastle.nuzi.towerdefence;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.kingscastle.nuzi.towerdefence.framework.Music;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.framework.Settings;
import com.kingscastle.nuzi.towerdefence.framework.implementation.GameMusic;
import com.kingscastle.nuzi.towerdefence.util.Strings;

import java.io.File;


/**
 * Main Activity that is used to start the game activity
 */
public class StartScreenActivity extends Activity implements View.OnClickListener{

    private static final String TAG = "StartScreenActivity";

    private static Music startScreenMusic;
    private static View continueButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadFontsAndTextSizes();

        setContentView(R.layout.activity_start_screen);

        continueButton = findViewById(R.id.continue_button);
        continueButton.setOnClickListener(this);
        findViewById(R.id.new_game_button).setOnClickListener(this);
        findViewById(R.id.settings_button).setOnClickListener(this);

        Settings.loadFromPreferences(getSharedPreferences("Settings", MODE_PRIVATE));

        GameMusic.loadMusic(this, new GameMusic.OnMusicLoadedListener() {
            @Override
            public void onMusicLoaded() {
                GameMusic.playEpicManShoutContest();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Settings.muteMusic)
            GameMusic.stopEpicManShoutContest();
        else
            GameMusic.playEpicManShoutContest();

        if( !Settings.savingLevel ) {
            findViewById(R.id.continue_button).setVisibility(View.INVISIBLE);
            for (File f : getFilesDir().listFiles())
                if (f.getName().equals(TowerDefenceGame.FILENAME)) {
                    findViewById(R.id.continue_button).setVisibility(View.VISIBLE);
                    break;
                }
        }
        else{
            findViewById(R.id.continue_button).setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        GameMusic.stopEpicManShoutContest();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Settings.saveToPreferences(getSharedPreferences("Settings", MODE_PRIVATE));
    }

    private long lastClicked;

    @Override
    public void onClick(View v) {
        if( lastClicked + 500 > System.currentTimeMillis() )
            return;
        lastClicked = System.currentTimeMillis();

        switch(v.getId()){
            case R.id.continue_button:{
                startActivity(new Intent(this, GameActivity.class));
                break;
            }
            case R.id.new_game_button:{

                Intent newGameIntent = new Intent(this, ChooseLevelActivity.class);
                newGameIntent.putExtra(Strings.CreateNewGame,true);
                startActivityForResult(newGameIntent, 666);

                break;
            }
            case R.id.settings_button:{
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                break;
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG,"onActivityResult("+requestCode+","+resultCode+","+data);

        if( requestCode == 666 && resultCode == RESULT_OK ){
            Bundle extras = data.getExtras();
            if (extras != null) {
                String levelClassName = extras.getString(Strings.LevelClassName);
                String difficulty = extras.getString(Strings.Difficulty);
                Intent newGameIntent = new Intent(this, GameActivity.class);
                newGameIntent.putExtra(Strings.CreateNewGame, true);
                newGameIntent.putExtra(Strings.LevelClassName,levelClassName);
                newGameIntent.putExtra(Strings.Difficulty,difficulty);

                deleteFile(TowerDefenceGame.FILENAME);

                startActivity(newGameIntent);
            }
        }

    }


    private void loadFontsAndTextSizes() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        float smallTextSize = getResources().getDimension( R.dimen.game_info );

        float smallestTextSize = smallTextSize/2 ;

        if( displayMetrics.densityDpi == DisplayMetrics.DENSITY_HIGH )
            smallestTextSize *= 1.5;
        else if( displayMetrics.densityDpi == DisplayMetrics.DENSITY_MEDIUM )
            smallestTextSize *= 2;
        else if( displayMetrics.densityDpi == DisplayMetrics.DENSITY_LOW )
            smallestTextSize *= 2.5;

        Rpg.setTextSize(smallTextSize);
        Rpg.setSmallestTextSize( smallestTextSize );

        Rpg.setDemonicTale(Typeface.createFromAsset(getAssets(), "MB-Demonic_Tale.ttf"));
        Rpg.setImpact(Typeface.createFromAsset(getAssets() , "impact.ttf"));
        Rpg.setCooperBlack(Typeface.createFromAsset(getAssets(), "cooperblack.ttf"));
    }




}
