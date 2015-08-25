package com.kingscastle.nuzi.towerdefence;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.kingscastle.nuzi.towerdefence.level.CastleLevel;
import com.kingscastle.nuzi.towerdefence.level.Desert2;
import com.kingscastle.nuzi.towerdefence.level.Desert3;
import com.kingscastle.nuzi.towerdefence.level.Desert4;
import com.kingscastle.nuzi.towerdefence.level.DesertLevel;
import com.kingscastle.nuzi.towerdefence.level.Forest;
import com.kingscastle.nuzi.towerdefence.level.ForestStrip;
import com.kingscastle.nuzi.towerdefence.level.Valley;
import com.kingscastle.nuzi.towerdefence.util.Strings;

import java.util.HashMap;
import java.util.Map;


public class ChooseLevelActivity extends Activity implements View.OnClickListener {

    private SharedPreferences sp;
    private Map<String,Boolean> unlockedLevels = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.kingscastle.nuzi.towerdefence.R.layout.activity_choose_level);

        sp = getSharedPreferences("LevelsComplete",MODE_PRIVATE);

        unlockedLevels.put(Forest.class.getSimpleName(),sp.getBoolean(Forest.class.getSimpleName(), false));
        unlockedLevels.put(Valley.class.getSimpleName(),sp.getBoolean(Valley.class.getSimpleName(), false));
        unlockedLevels.put(ForestStrip.class.getSimpleName(), sp.getBoolean(ForestStrip.class.getSimpleName(), false));
        unlockedLevels.put(DesertLevel.class.getSimpleName(),sp.getBoolean(DesertLevel.class.getSimpleName(), false));
        unlockedLevels.put(Desert2.class.getSimpleName(),sp.getBoolean(Desert2.class.getSimpleName(), false));
        unlockedLevels.put(Desert3.class.getSimpleName(),sp.getBoolean(Desert3.class.getSimpleName(), false));
        unlockedLevels.put(Desert4.class.getSimpleName(),sp.getBoolean(Desert4.class.getSimpleName(), false));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.kingscastle.nuzi.towerdefence.R.menu.menu_choose_level, menu);
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

    @Override
    public void onClick(View v) {
        final Intent intent = new Intent();

        String levelClassName = ((Button)v).getText().toString().replace(" ","");

        switch( v.getId() ){
            case R.id.forest_button:{
                levelClassName = Forest.class.getSimpleName();
                break;
            }
            case R.id.forest2_button: {
                levelClassName = Valley.class.getSimpleName();
                break;
            }
            case R.id.desert_button: {
                levelClassName = DesertLevel.class.getSimpleName();
                break;
            }
            case R.id.desert_2_button: {
                levelClassName = Desert2.class.getSimpleName();
                break;
            }
            case R.id.castle_level_button: {
                levelClassName = CastleLevel.class.getSimpleName();
                break;
            }
        }

        intent.putExtra(Strings.LevelClassName, levelClassName);

        String locked = (unlockedLevels.get(levelClassName)?"Unlocked":"Locked");

        Intent levelDetailsIntent = new Intent(this, LevelDetailsActivity.class);
        levelDetailsIntent.putExtra("levelClassName",levelClassName);

        startActivityForResult(levelDetailsIntent, 555);



//        WindowBuilder wb = new WindowBuilder(this);
//        wb.setTitle(levelClassName)
//                .setContent(new CTextView(this).setText( + "\nTop Score: " + score))
//                .setPositiveButton("Start Level",R.drawable.blue_300x75, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //close this Activity...
//                        finish();
//                    }
//                }).setNegativeButton("Back",R.drawable.red_150x38,null).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( requestCode == 555 && resultCode == RESULT_OK ){
            Bundle extras = data.getExtras();
            if (extras != null) {
                String levelClassName = extras.getString(Strings.LevelClassName);
                String difficulty = extras.getString(Strings.Difficulty);

                Intent result = new Intent();
                result.putExtra(Strings.LevelClassName,levelClassName);
                result.putExtra(Strings.Difficulty,difficulty);
                setResult(RESULT_OK, result);
                finish();
            }
        }

    }
}
