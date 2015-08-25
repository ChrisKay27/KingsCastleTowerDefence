package com.kingscastle.nuzi.towerdefence.tutorials;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;

import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.ui.CTextView;
import com.kingscastle.nuzi.towerdefence.ui.WindowBuilder;
import com.kingscastle.nuzi.towerdefence.util.Strings;

import java.lang.reflect.InvocationTargetException;


/**
 * Created by Chris on 7/30/2015 for Tower Defence Free
 */
public class Tutorial {
    public static final String TUTORIAL = "Tutorial";
    public static final String NEXT_TUTORIAL = "NextTutorial";
    private final Activity a;
    private final SharedPreferences sp;

    public Tutorial(Activity a){
        this.a = a;
        this.sp =  a.getSharedPreferences(TUTORIAL,Activity.MODE_PRIVATE);
    }

    public void showTutorial(){
        String methodName = sp.getString(NEXT_TUTORIAL,"showFirstTutorial");
        try {
            getClass().getMethod(methodName,new Class[]{null}).invoke(this);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void showFirstTutorial(){
        new WindowBuilder(a).setCancelable(false).setContent(new CTextView(a).setText(a.getString(R.string.welcome)))
            .setNegativeButton("Skip", R.drawable.red_150x38, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sp.edit().putBoolean(Strings.TutorialComplete,true).apply();
                }
            }).setPositiveButton("Ok", R.drawable.blue_150x38, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.edit().putString(NEXT_TUTORIAL,"showSecondTutorial").apply();
                showSecondTutorial();
            }
        }).show();

    }


    private void showSecondTutorial(){
        new WindowBuilder(a).setCancelable(false).setContent(new CTextView(a).setText(a.getString(R.string.objective)))
                .setNegativeButton("Skip", R.drawable.red_150x38, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sp.edit().putBoolean(Strings.TutorialComplete,true).apply();
                    }
                }).setPositiveButton("Ok", R.drawable.blue_150x38, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.edit().putString(NEXT_TUTORIAL, "showFirstTutorial").apply();
                showSecondTutorial();
            }
        }).show();
    }


}
