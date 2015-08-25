package com.kingscastle.nuzi.towerdefence.framework;

/**
 * Created by Chris on 8/4/2015 for Tower Defence
 * Used to get the current game time. This time does not increase when the game is paused.
 */
public class GameTime {

    private static final String TAG = GameTime.class.getSimpleName();
    private static long time;

    public static void incTime(long dt){
        if( dt < 0 )
            throw new IllegalArgumentException("Cannot increment time by a negative number!");
        time += dt;
       // Log.v(TAG, "game time=" + time);
    }

    public static long getTime(){
        return time;
    }
}
