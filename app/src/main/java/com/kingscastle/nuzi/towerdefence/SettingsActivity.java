package com.kingscastle.nuzi.towerdefence;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.kingscastle.nuzi.towerdefence.framework.Settings;


public class SettingsActivity extends Activity {

    private static final String TAG = "SettingsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Switch musicSwitch = (Switch)  findViewById(R.id.music_switch);
        musicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings.muteMusic = !isChecked;
                Log.d(TAG, "muteMusic="+Settings.muteMusic);
            }
        });
        musicSwitch.setChecked(!Settings.muteMusic);

        Switch soundsSwitch = (Switch)  findViewById(R.id.sounds_switch);
        soundsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings.muteSounds = !isChecked;
                Log.d(TAG, "muteSounds="+Settings.muteSounds);
            }
        });
        soundsSwitch.setChecked(!Settings.muteSounds);

//        Switch showBordersSwitch = (Switch)  findViewById(R.id.showborders_switch);
//        showBordersSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                Settings.alwaysShowAreaBorders = isChecked;
//            }
//        });

        final Switch pathsSwitch = (Switch)  findViewById(R.id.show_paths_switch);
        pathsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings.showPath = isChecked;
            }
        });

        Switch devSwitch = (Switch)  findViewById(R.id.dev_switch);
        devSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if( isChecked)
                    pathsSwitch.setVisibility(View.VISIBLE);
                else
                    pathsSwitch.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Settings.saveToPreferences(getSharedPreferences("Settings", MODE_PRIVATE));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

}
