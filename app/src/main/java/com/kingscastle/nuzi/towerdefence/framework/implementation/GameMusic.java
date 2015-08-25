package com.kingscastle.nuzi.towerdefence.framework.implementation;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.util.Log;

import com.kingscastle.nuzi.towerdefence.framework.Music;
import com.kingscastle.nuzi.towerdefence.framework.Settings;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Chris on 7/15/2015 for Tower Defence
 */
public class GameMusic {

    private static final String TAG = "GameMusic";
    private static Music epicManShoutContest , castleCall, medievalShort;
    private static AndroidAudio audio;

    public static void loadMusic(@NotNull final Activity a, @Nullable final OnMusicLoadedListener omll ){
        if( audio != null ) {
            Log.w( TAG , "Audio already loaded");
            return;
        }
        new Thread(){
            @Override
            public void run() {
                audio = new AndroidAudio(a);
                try
                {
                    epicManShoutContest = audio.createMusic( "epic_man_shout_contest.mp3" );
                    epicManShoutContest.setLooping(true);
                    //epicManShoutContest.setVolume(0.5f);

                    castleCall = audio.createMusic( "castlecall.mp3" );
                    castleCall.setLooping( true );

                    medievalShort = audio.createMusic( "medieval_short.mp3" );
                    medievalShort.setLooping( true );
                }
                catch( Exception e ){
                    e.printStackTrace();
                }
                if( omll != null )
                    omll.onMusicLoaded();
            }
        }.start();
    }

    public static AndroidAudio getAudio() {
        return audio;
    }


    public static void playEpicManShoutContest() {
        if( !Settings.muteMusic )
            if( epicManShoutContest != null )
                if( !epicManShoutContest.isPlaying() )
                    epicManShoutContest.play();
    }
    public static void playCastleCall() {
        if( !Settings.muteMusic )
            if( castleCall != null )
                if( !castleCall.isPlaying() )
                    castleCall.play();
    }
    public static void playMedievalShort() {
        if( !Settings.muteMusic )
            if( medievalShort != null )
                if( !medievalShort.isPlaying() )
                    medievalShort.play();
    }
    public static void stopEpicManShoutContest() {
        if( epicManShoutContest != null )
            if( epicManShoutContest.isPlaying() )
                epicManShoutContest.stop();
    }
    public static void stopCastleCall() {
        if( castleCall != null )
            if( castleCall.isPlaying() )
                castleCall.stop();
    }
    public static void stopMedievalShort() {
        if( medievalShort != null )
            if( medievalShort.isPlaying() )
                medievalShort.stop();
    }


    public interface OnMusicLoadedListener{
        void onMusicLoaded();
    }
}
