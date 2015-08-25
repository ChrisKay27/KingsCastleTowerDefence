package com.kingscastle.nuzi.towerdefence.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;


public class LoadingScreen {

	private static final String TAG = "LoadingScreen";

	private static View loadingScreen;



	/**
	 * Only call from UI thread
	 */
	public static ProgressBar show( final Activity a ){

		final View v = loadingScreen;
		if( v != null ){
			a.runOnUiThread(new Runnable(){
				@Override
				public void run() {
					ViewGroup vg = (ViewGroup) v.getParent();
					if( vg != null )
						vg.removeView( v );
				}
			});
		}


		View loadingScreen = a.getLayoutInflater().inflate(R.layout.loading, null);

		TextView loading = (TextView) loadingScreen.findViewById(R.id.textViewLoading);
		//int color = loading.getPaint().getColor();
		UIUtil.applyKcStyle(loading);
		//loading.setTextColor(color);


		MProgressBar pb = (MProgressBar) loadingScreen.findViewById(R.id.progressBarLoading);


		a.addContentView( loadingScreen , new LayoutParams( LayoutParams.MATCH_PARENT , LayoutParams.MATCH_PARENT ));
		LoadingScreen.loadingScreen = loadingScreen;

		return pb;
	}








	public static void hide() {

		//		new Thread(){
		//			@Override
		//			public void run() {
		//				try {
		//					Thread.sleep(1000);
		//				} catch (InterruptedException e) {
		//					e.printStackTrace();
		//				}
		Rpg.getGame().getActivity().runOnUiThread(hide);
		//			}
		//		}.act();

		//		if( KingsCastle.uiThreadName.equals(Thread.currentThread().getName()) ){
		//			//Log.d(TAG , "Synchronious hiding of UI!");
		//			hide.run();
		//		}else{
		//			//Log.d(TAG , "ASynch hiding of UI!");
		//			Rpg.getGame().runOnUiThread(hide );
		//		}

	}




	private static Runnable hide = new Runnable(){
		@Override
		public void run() {
			final View v = loadingScreen;
			if( v != null )
			{
				if( v.getVisibility() == View.VISIBLE )
				{
					ValueAnimator animation = ValueAnimator.ofFloat( 1f , 0f );
					animation.addUpdateListener(new AnimatorUpdateListener() {
						@Override
						public void onAnimationUpdate(ValueAnimator animation) {
							v.setAlpha( (Float) animation.getAnimatedValue() );
						}
					});
					animation.setDuration(2000);
					animation.addListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							ViewGroup vg = (ViewGroup) v.getParent();
							if( vg != null )
								vg.removeView( v );
						}
					});
					animation.start();
				}
			}


		}
	};





	public static void bringToFront() {
		Rpg.getGame().getActivity().runOnUiThread(new Runnable(){
			@Override
			public void run() {
				final View v = loadingScreen;
				if( v != null )
					v.bringToFront();
			}
		});

	}








}
