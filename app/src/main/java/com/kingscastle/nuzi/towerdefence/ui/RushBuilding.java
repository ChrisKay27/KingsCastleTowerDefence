package com.kingscastle.nuzi.towerdefence.ui;


import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.gameElements.GameElement;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Building;
import com.kingscastle.nuzi.towerdefence.gameUtils.Queueable;

import java.util.ArrayList;


public class RushBuilding {

	private static final String TAG = "RushBuilding";
	static final float FINISH_NOW_TRANSLATION_Y_OFFSCREEN = 100* Rpg.getDp();

	static View rushWindow;
	static ImageButton hurryButton;
	static TextView msg;

	private Building selectedBuilding;
	private Building buildingToRushBuild;
	private ArrayList<? extends GameElement> buildingsToRushBuild;


	private int magicDustToComplete=1;
	private Queueable q;
	private final ShowFinishNowRunnable showFinishNowRunnable = new ShowFinishNowRunnable();

	private final SelectedUI selUI;




	RushBuilding(SelectedUI selUI){
		this.selUI = selUI;
	}




	/**
	 * Call only from UI thread
	 * @param ge
	 */
	public void showFinishNowButtonIfNeeded( GameElement ge ){
		if( true )
			return;

		buildingsToRushBuild = null;
		selectedBuilding = null;
		buildingToRushBuild = null;
		//Log.d( "RefreshUITrace" , "showFinishNowButtonIfNeeded("+ge+")");
		if( ge == null ) return;

		if( !(ge instanceof Building) ){
			hurryButton.setTranslationY( FINISH_NOW_TRANSLATION_Y_OFFSCREEN );
			return;
		}
		selectedBuilding = (Building) ge;

		final ImageButton finish = hurryButton;


//		if( ge instanceof Building ){
//			buildingToRushBuild = (Building) ge;
//			BuildQueue bq =  buildingToRushBuild.getBuildQueue();
//			q = bq.currentlyBuilding;
//
//
//			if( bq.isEmpty() ){
//				//Log.d( "RefreshUITrace" , "bq.isEmpty()");
//				if( finish != null )
//					if( finish.getTranslationY() == 0f ){
//						//Log.d( "RefreshUITrace" , "finish.getVisibility() == View.VISIBLE, Should act sliding down now");
//						ValueAnimator animation = ValueAnimator.ofFloat( 0f , FINISH_NOW_TRANSLATION_Y_OFFSCREEN );
//						animation.addUpdateListener(new AnimatorUpdateListener() {
//							@Override
//							public void onAnimationUpdate(ValueAnimator animation) {
//								finish.setTranslationY((Float) animation.getAnimatedValue());
//							}
//						});
//						animation.setDuration(150);
//						animation.start();
//						//hurryButton.setVisibility( View.INVISIBLE );
//					}
//			}else{
//				//Log.d( "RefreshUITrace" , "bq.is not Empty");
//				if( finish != null )
//					if( finish.getTranslationY() != 0f ){
//						ValueAnimator animation = ValueAnimator.ofFloat( FINISH_NOW_TRANSLATION_Y_OFFSCREEN , 0f );
//						animation.addUpdateListener(new AnimatorUpdateListener() {
//							@Override
//							public void onAnimationUpdate(ValueAnimator animation) {
//								finish.setTranslationY((Float) animation.getAnimatedValue());
//							}
//						});
//						animation.setDuration(150);
//						animation.start();
//					}
//			}
//		}
//		else{
//			if( finish != null )
//				finish.setTranslationY( FINISH_NOW_TRANSLATION_Y_OFFSCREEN );
//		}
	}


	public void showFinishNowButtonIfNeededTSafe( GameElement ge ) {
		//Log.d( "RefreshUITrace" , "showFinishNowButtonIfNeededTSafe("+ge+")");
		if( ge == null ) return;

		showFinishNowRunnable.ge = ge ;
		Rpg.getGame().getActivity().runOnUiThread(showFinishNowRunnable);
	}


	/**
	 * Call only from UI thread
	 * @param ges
	 */
	public void showFinishNowButtonIfNeeded( ArrayList<? extends GameElement> ges ){
		Log.d( TAG , "showFinishNowButtonIfNeeded("+ges+")");
		selectedBuilding = null;
		buildingToRushBuild = null;
		buildingsToRushBuild = ges;

		if( ges == null || ges.isEmpty() ){
			hurryButton.setTranslationY( FINISH_NOW_TRANSLATION_Y_OFFSCREEN );
			return;
		}



			hurryButton.setTranslationY( FINISH_NOW_TRANSLATION_Y_OFFSCREEN );
			return;


	}


	private void slideButtonUp(){
		Log.d( TAG , "slideButtonUp()");
		final ImageButton finish = hurryButton;

		if( finish != null ){
			ValueAnimator animation = ValueAnimator.ofFloat( FINISH_NOW_TRANSLATION_Y_OFFSCREEN , 0f );
			animation.addUpdateListener(new AnimatorUpdateListener() {
				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					finish.setTranslationY((Float) animation.getAnimatedValue());
				}
			});
			animation.setDuration(150);
			animation.start();
		}
	}

	private void slideButtonDown(){
		final ImageButton finish = hurryButton;

		if( finish != null ){
			ValueAnimator animation = ValueAnimator.ofFloat( 0f , FINISH_NOW_TRANSLATION_Y_OFFSCREEN );
			animation.addUpdateListener(new AnimatorUpdateListener() {
				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					finish.setTranslationY((Float) animation.getAnimatedValue());
				}
			});
			animation.setDuration(150);
			animation.start();
		}
	}



	protected void userAcceptedRushBuild() {
//		if( buildingToRushBuild != null || (buildingsToRushBuild != null && !buildingsToRushBuild.isEmpty()))
//		{
//			PR pr = Rpg.getGame().getPlayer().getPR();
//			if( pr.canAfford( RT.MAGIC_DUST , magicDustToComplete ))
//			{
//				if( buildingsToRushBuild != null )
//				{
//					if( pr.spend( RT.MAGIC_DUST , magicDustToComplete ))
//						for( GameElement ge : buildingsToRushBuild ){
//
//						}
//					buildingsToRushBuild = null;
//					return;
//				}
//
//				if( q != buildingToRushBuild.getBuildQueue().currentlyBuilding ) return;
//
//				if( pr.spend( RT.MAGIC_DUST , magicDustToComplete ))
//					buildingToRushBuild.getBuildQueue().setFinishThisOneNow( true );
//
//			}
//			else{
//				CannotAfford.showCannotAffordMdMessage(Rpg.getGame().getActivity(), magicDustToComplete );
//			}
//		}

	}

	protected void userDeniedRushBuild() {


	}





	public void setHurryButtonAndSetup(final ImageButton finish) {

		RushBuilding.hurryButton = finish;
		finish.setOnClickListener(hurryButtonListener);
		finish.setTranslationY(FINISH_NOW_TRANSLATION_Y_OFFSCREEN );


		rushWindow = Rpg.getGame().getActivity().getLayoutInflater().inflate(R.layout.spend_to_research, null);
		rushWindow.setVisibility(View.INVISIBLE);
		//Log.d( "RushBuilding" , rushWindow.toString() );

		msg = (TextView) rushWindow.findViewById( R.id.textViewSpendToResearch );
		msg.setTypeface(Rpg.getImpact());

		View sthstrh = rushWindow.findViewById( R.id.imageButtonSure );
		//Log.d( "RushBuilding" , "should be sureButton " + sthstrh.toString() );

		if(sthstrh instanceof ImageButton ){
			ImageButton ib = (ImageButton) sthstrh;
			ib.setOnClickListener(sureButtonList);
		}

		sthstrh = rushWindow.findViewById( R.id.imageButtonNo );
		//Log.d( "RushBuilding" ,"should be noButton "+ sthstrh.toString() );
		if(sthstrh instanceof ImageButton ){
			ImageButton ib = (ImageButton) sthstrh;
			ib.setOnClickListener(noButtonList);
		}

		Paint p = new Paint();
		p.setTextSize(msg.getTextSize());

		int width = (int) p.measureText("   Do you want to spend 10000 magic  ");
		Rpg.getGame().getActivity().addContentView( rushWindow , new ViewGroup.LayoutParams( width , ViewGroup.LayoutParams.WRAP_CONTENT));


		final ViewTreeObserver observer = rushWindow.getViewTreeObserver();
		observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {

				rushWindow.setX( (Rpg.getWidth()-rushWindow.getWidth())/2 );
				rushWindow.setY( (Rpg.getHeight()-rushWindow.getHeight())/2);
				rushWindow.setVisibility(View.GONE);
				//				{
				//					ValueAnimator animation = ValueAnimator.ofFloat( 75*Rpg.getDp() , 0f );
				//					animation.addUpdateListener(new AnimatorUpdateListener() {
				//						@Override
				//						public void onAnimationUpdate(ValueAnimator animation) {
				//							finish.setTranslationY((Float) animation.getAnimatedValue());
				//						}
				//					});
				//					animation.setDuration(150);
				//					animation.act();
				//				}


				observer.removeGlobalOnLayoutListener(this);
			}
		});
	}





	private final OnClickListener sureButtonList = new OnClickListener() {
		@Override
		public void onClick(View v) {
			userAcceptedRushBuild();

			rushWindow.setVisibility( View.GONE );

			Building b = selectedBuilding;
			if( b != null )
				showFinishNowButtonIfNeeded(b);
		}
	};

	private final OnClickListener noButtonList = new OnClickListener() {
		@Override
		public void onClick(View v) {
			userDeniedRushBuild();
			rushWindow.setVisibility( View.GONE );
			Building b = selectedBuilding;
			if( b != null )
				showFinishNowButtonIfNeeded(b);
		}
	};

	private final OnClickListener hurryButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Building b = buildingToRushBuild;
			ArrayList<? extends GameElement> ges = buildingsToRushBuild;

//			if( b != null ){
//				{
//					BuildQueue bq =  b.getBuildQueue();
//					int ms = bq.getValue();
//					Queueable currBuilding = bq.getCurrentlyBuilding();
//					if( currBuilding == null ){
//						selUI.refreshUI();
//						return;
//					}
//					magicDustToComplete = (int) Math.ceil(ms/(1000f*60f*15f));
//					if( magicDustToComplete == 1 )
//						msg.setText("Do you want to spend "+magicDustToComplete+" magic\ndust to finish a " + currBuilding.getName() +" now?" );
//					else
//						msg.setText("Do you want to spend "+magicDustToComplete+" magic\ndusts to finish a " + currBuilding.getName() +" now?" );
//				}
//				rushWindow.setVisibility(View.VISIBLE);
//			}

		}
	};






	public View getHurryWindow() {
		return rushWindow;
	}

	public void setHurryWindow(View hurryWindow) {
		RushBuilding.rushWindow = hurryWindow;
	}

	public ImageButton getHurryButton() {
		return hurryButton;
	}

	public void setHurryButton(ImageButton hurryButton) {
		RushBuilding.hurryButton = hurryButton;
	}






	private class ShowFinishNowRunnable implements Runnable{
		private GameElement ge;
		@Override
		public synchronized void run() {
			showFinishNowButtonIfNeeded( ge );
		}

	}





	/**
	 * Only call from UI thread
	 */
	public void onSelUIHiden() {
		final View v = rushWindow;
		if( v == null ) return;

		ViewGroup vg = (ViewGroup) v.getParent();
		if( vg != null )
			vg.removeView( v );



	}






}
