package com.kingscastle.nuzi.towerdefence.ui;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.framework.SoundController;


public class Menu {

	private static final String TAG = "Menu";

	private static MenuBuilder mBuilder = new MenuBuilder();
	private static WindowBuilder menuView;

	public static class MenuBuilder{

		public WindowBuilder showMenu(final SoundController sc){
//			final KingsCastle kc = Rpg.getGame();
//
//
//
//			WindowBuilder wb = new WindowBuilder(kc);
//			wb.setCancelable(true);
//
//			final View menuView = kc.getLayoutInflater().inflate(R.layout.menu,null);
//
//
//			final LinearLayout buttonLayout = (LinearLayout) menuView.findViewById(R.id.linearLayoutMenuButtons);
//
//			final ScrollView settingsButtonsScrollView = (ScrollView) menuView.findViewById(R.id.scrollViewSettingsButtons);
//			final LinearLayout settingsButtonsLayout = (LinearLayout) menuView.findViewById(R.id.linearLayoutSettingsButtons);
//
//
//
//			final Button settings = (Button) menuView.findViewById(R.id.buttonMenuSettings);
//			settings.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//
//					settingsButtonsLayout.setVisibility( View.VISIBLE );
//					settingsButtonsScrollView.setVisibility( View.VISIBLE );
//					{
//						ValueAnimator animation = ValueAnimator.ofFloat( 0f , -500*Rpg.getDp() );
//						animation.addUpdateListener(new AnimatorUpdateListener() {
//							@Override
//							public void onAnimationUpdate(ValueAnimator animation) {
//								buttonLayout.setY( (Float) animation.getAnimatedValue());
//							}
//						});
//						animation.addListener(new AnimatorListenerAdapter() {
//							@Override
//							public void onAnimationEnd(Animator animation) {
//								buttonLayout.setVisibility( View.GONE );
//							}
//						});
//						animation.setDuration(300);
//						animation.act();
//					}
//					{
//						ValueAnimator animation = ValueAnimator.ofFloat( -500*Rpg.getDp() , 0f );
//						animation.addUpdateListener(new AnimatorUpdateListener() {
//							@Override
//							public void onAnimationUpdate(ValueAnimator animation) {
//								settingsButtonsLayout.setY( (Float) animation.getAnimatedValue());
//							}
//						});
//						animation.setDuration(300);
//						animation.act();
//					}
//				}
//			});
//
//
//
//
//
//			final Button credits = (Button) menuView.findViewById(R.id.buttonMenuCredits);
//			credits.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					final View creditsView = kc.getLayoutInflater().inflate(R.layout.credits_view,(ViewGroup) menuView);
//					final ImageButton creditsBack = (ImageButton) creditsView.findViewById(R.id.imageButtonCreditsBack);
//					creditsBack.setOnClickListener(new OnClickListener() {
//						@Override
//						public void onClick(View v) {
//							ViewParent vp = creditsView.getParent();
//							if( vp != null && vp instanceof ViewGroup )
//								((ViewGroup)vp).removeView( creditsView );
//						}
//
//					});
//				}
//			});
//
//
//			final Button close = (Button) menuView.findViewById(R.id.buttonMenuClose);
//			close.setVisibility(View.GONE);
//
//
//			wb.setCloseButtonListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					hide();
//				}
//			});
//
//
//
//
//			final Button back = (Button) menuView.findViewById(R.id.ButtonMenuSettingsBack);
//			back.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					{
//						ValueAnimator animation = ValueAnimator.ofFloat( 0f , -500*Rpg.getDp() );
//						animation.addUpdateListener(new AnimatorUpdateListener() {
//							@Override
//							public void onAnimationUpdate(ValueAnimator animation) {
//								settingsButtonsLayout.setTranslationY( (Float) animation.getAnimatedValue());
//							}
//						});
//						animation.addListener(new AnimatorListenerAdapter() {
//							@Override
//							public void onAnimationEnd(Animator animation) {
//								settingsButtonsLayout.setVisibility(View.GONE);
//								settingsButtonsScrollView.setVisibility(View.GONE);
//							}
//						});
//						animation.setDuration(300);
//						animation.act();
//					}
//					{
//						buttonLayout.setVisibility(View.VISIBLE);
//						ValueAnimator animation = ValueAnimator.ofFloat( -500*Rpg.getDp() , 0f );
//						animation.addUpdateListener(new AnimatorUpdateListener() {
//							@Override
//							public void onAnimationUpdate(ValueAnimator animation) {
//								buttonLayout.setTranslationY( (Float) animation.getAnimatedValue());
//							}
//						});
//						animation.addListener(new AnimatorListenerAdapter() {
//							@Override
//							public void onAnimationEnd(Animator animation) {
//								menuView.requestLayout();
//							}
//						});
//						animation.setDuration(300);
//						animation.act();
//					}
//				}
//			});



//
//			final Button muteSounds = (Button) menuView.findViewById(R.id.ButtonMenuMuteSounds);
//			muteSounds.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					Settings.muteSounds = !Settings.muteSounds;
//
//					if( Settings.muteSounds )
//						muteSounds.setLayerType(View.LAYER_TYPE_HARDWARE, grey);
//					else
//						muteSounds.setLayerType(View.LAYER_TYPE_HARDWARE, normal);
//				}
//			});
//
//
//
//			final Button muteMusic = (Button) menuView.findViewById(R.id.ButtonMenuMuteMusic);
//			muteMusic.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					Settings.muteMusic = !Settings.muteMusic;
//
//					if( Settings.muteMusic ){
//						muteMusic.setLayerType(View.LAYER_TYPE_HARDWARE, grey);
//						sc.stopMusic();
//					}else{
//						muteMusic.setLayerType(View.LAYER_TYPE_HARDWARE, normal);
//						sc.startMusic();
//					}
//
//				}
//			});


			//wb.setContent(menuView);
			//wb.show();


			return null;
		}

	}

	private static final Paint normal = new Paint();
	private static final Paint grey = new Paint();{
		ColorMatrix cm;
		cm = new ColorMatrix();
		cm.setSaturation(0);
		grey.setColorFilter(new ColorMatrixColorFilter(cm));
	}



	public static void showMenu(final SoundController sc){

		WindowBuilder v = menuView;
		if( v != null )
			hide();

		if( Rpg.getGame().uiThreadName.equals(Thread.currentThread().getName()) ){
			//Log.d(TAG , "Synchronious showing of menu!");
			Menu.menuView = mBuilder.showMenu(sc);
		}else{
			//Log.d(TAG , "ASynch showing of menu!");
			Rpg.getGame().getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Menu.menuView = mBuilder.showMenu(sc);
				}
			});
		}
	}


	public static void setMenuBuilder(MenuBuilder mBuilder_ ){
		if( mBuilder_ != null )
			mBuilder = mBuilder_;
	}









	public static void hide() {
		if( Rpg.getGame().uiThreadName.equals(Thread.currentThread().getName()) ){
			//Log.d(TAG , "Synchronious hiding of menu!");
			hide.run();
		}else{
			//Log.d(TAG , "ASynch hiding of menu!");
			Rpg.getGame().getActivity().runOnUiThread(hide);
		}
	}




	private static Runnable hide = new Runnable(){
		@Override
		public void run() {
			final WindowBuilder v = menuView;
			if( v != null ){
				v.hide();
				menuView = null;
			}
		}
	};





}




