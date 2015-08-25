package com.kingscastle.nuzi.towerdefence;

import android.animation.ValueAnimator;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.framework.implementation.AndroidFastRenderView;
import com.kingscastle.nuzi.towerdefence.gameUtils.Difficulty;
import com.kingscastle.nuzi.towerdefence.level.Forest;
import com.kingscastle.nuzi.towerdefence.util.Strings;
import com.kingscastle.nuzi.towerdefence.util.SystemUiHider;

import org.jetbrains.annotations.NotNull;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class GameActivity extends Activity {//} implements GoogleApiClient.ConnectionCallbacks,        GoogleApiClient.OnConnectionFailedListener, PlayerAchievements.UnlockAchievementListener {

    private PowerManager.WakeLock wakeLock;

    private static TowerDefenceGame tdg;
    private Handler uiHandler;
    //private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFontsAndTextSizes();

        uiHandler = new Handler();

        // Create the Google Api Client with access to the Play Games services
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
//                        // add other APIs and scopes here as needed
//                .build();

        boolean createNewGame = false;
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            createNewGame = extras.getBoolean(Strings.CreateNewGame, false);

        String levelClassName = Forest.class.getSimpleName();
        if (extras != null)
            levelClassName = extras.getString(Strings.LevelClassName, Forest.class.getSimpleName());

        TowerDefenceGame oldTdg = tdg;
        if (createNewGame || (oldTdg != null && oldTdg.isOver()))
            tdg = null;

        Difficulty diff = Difficulty.Medium;
        if (extras != null)
            diff = Difficulty.valueOf(extras.getString(Strings.Difficulty));

        // If the Android version is lower than Jellybean, use this call to hide
        // the status bar.
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            decorView.setOnSystemUiVisibilityChangeListener
                    (new View.OnSystemUiVisibilityChangeListener() {
                        @Override
                        public void onSystemUiVisibilityChange(int visibility) {
                            // Note that system bars will only be "visible" if none of the
                            // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
                            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                                uiHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                                        getWindow().getDecorView().setSystemUiVisibility(uiOptions);

                                        ActionBar actionBar = getActionBar();
                                        if (actionBar != null)
                                            actionBar.hide();
                                    }
                                }, 3000);
                            } else {
                                // TODO: The system bars are NOT visible. Make any desired
                                // adjustments to your UI, such as hiding the action bar or
                                // other navigational controls.
                            }
                        }
                    });
            // Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(uiOptions);
            // Remember that you should never show the action bar if the
            // status bar is hidden, so hide that too if necessary.
            ActionBar actionBar = getActionBar();
            if (actionBar != null)
                actionBar.hide();
        }

        setContentView(com.kingscastle.nuzi.towerdefence.R.layout.activity_game);

        if (tdg == null)
            tdg = new TowerDefenceGame(this, (AndroidFastRenderView) findViewById(com.kingscastle.nuzi.towerdefence.R.id.surfaceView), levelClassName, diff);

        tdg.onCreate(this, (AndroidFastRenderView) findViewById(com.kingscastle.nuzi.towerdefence.R.id.surfaceView));


        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "KingsCastle");

        //signInClicked();
    }


    @Override
    public void onStart() {
        super.onStart();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String uiThreadName2 = Thread.currentThread().getName();
                if (uiThreadName2 != null)
                    tdg.uiThreadName = uiThreadName2;
            }
        });
        tdg.onStart(this);
       //  mGoogleApiClient.connect();
    }

    @Override
    public void onResume() {
        super.onResume();
        wakeLock.acquire();
        tdg.onResume(this);

        final View startButton = findViewById(R.id.start_button);
        final ValueAnimator a = ValueAnimator.ofFloat(0.8f, 1.2f, 0.8f);
        a.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NotNull ValueAnimator animation) {
                //Log.d( TAG , "sl.getX()= " + scrollerLayout.getX() + " sl.getY()= " + scrollerLayout.getY() );
                startButton.setScaleX((Float) animation.getAnimatedValue());
                startButton.setScaleY((Float) animation.getAnimatedValue());
            }
        });
//        a.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//
//            }
//        });
        a.setDuration(1000);
        a.setRepeatCount(10);
        a.start();
    }


    @Override
    public void onPause() {
        super.onPause();
        wakeLock.release();
        tdg.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        tdg.onStop();
       // mGoogleApiClient.disconnect();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            // TODO: If Settings has multiple levels, Up should navigate up
            // that hierarchy.
            //NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


//    @Override
//    public void unlockAchievement(int ID) {
//        String achieveID = getString(ID);
////        if(mGoogleApiClient != null && mGoogleApiClient.isConnected())
////            Games.Achievements.unlock(mGoogleApiClient, achieveID);
//    }


//    @Override
//    public void onConnected(Bundle bundle) {
//
//    }

    private static int RC_SIGN_IN = 9001;

    private boolean mResolvingConnectionFailure = false;
    private boolean mAutoStartSignInFlow = true;
    private boolean mSignInClicked = false;

//    @Override
//    public void onConnectionFailed(ConnectionResult connectionResult) {
//        if (mResolvingConnectionFailure) {
//            // already resolving
//            return;
//        }
//
//        // if the sign-in button was clicked or if auto sign-in is enabled,
//        // launch the sign-in flow
//        if (mSignInClicked || mAutoStartSignInFlow) {
//            mAutoStartSignInFlow = false;
//            mSignInClicked = false;
//            mResolvingConnectionFailure = true;
//
//            // Attempt to resolve the connection failure using BaseGameUtils.
//            // The R.string.signin_other_error value should reference a generic
//            // error string in your strings.xml file, such as "There was
//            // an issue with sign-in, please try again later."
//            if (!BaseGameUtils.resolveConnectionFailure(this,
//                    mGoogleApiClient, connectionResult,
//                    RC_SIGN_IN, getString(R.string.signin_other_error))) {
//                mResolvingConnectionFailure = false;
//            }
//        }
//
//        // Put code here to display the sign-in button
//    }

//    @Override
//    public void onConnectionSuspended(int i) {
//        // Attempt to reconnect
//        mGoogleApiClient.connect();
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == RC_SIGN_IN) {
//            mSignInClicked = false;
//            mResolvingConnectionFailure = false;
//            if (resultCode == RESULT_OK) {
//                mGoogleApiClient.connect();
//            } else {
//                // Bring up an error dialog to alert the user that sign-in
//                // failed. The R.string.signin_failure should reference an error
//                // string in your strings.xml file that tells the user they
//                // could not be signed in, such as "Unable to sign in."
//                BaseGameUtils.showActivityResultError(this,
//                        requestCode, resultCode, R.string.signin_failure);
//            }
//        }
//    }

//    // Call when the sign-in button is clicked
//    private void signInClicked() {
//        mSignInClicked = true;
//        mGoogleApiClient.connect();
//    }

    // Call when the sign-out button is clicked
//    private void signOutclicked() {
//        mSignInClicked = false;
//        Games.signOut(mGoogleApiClient);
//    }

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

        Rpg.setLargeTitleTextSize(getResources().getDimension(R.dimen.splash_text_size) );
        Rpg.setTextSize(smallTextSize);
        Rpg.setSmallestTextSize( smallestTextSize );

        Rpg.setDemonicTale(Typeface.createFromAsset(getAssets(), "MB-Demonic_Tale.ttf"));
        Rpg.setImpact(Typeface.createFromAsset(getAssets() , "impact.ttf"));
        Rpg.setCooperBlack(Typeface.createFromAsset(getAssets(), "cooperblack.ttf"));
    }
}
