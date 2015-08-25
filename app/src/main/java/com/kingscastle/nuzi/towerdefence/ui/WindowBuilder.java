package com.kingscastle.nuzi.towerdefence.ui;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;

import java.util.ArrayList;


public class WindowBuilder{

	private static ArrayList<View> popups = new ArrayList<View>();


	private final Activity a;

	private String posBText = "";
	private String negBText = "";

	private int posButBkrndResource = -1;
	private int negButBkrndResource = -1;

	private OnClickListener pl;
	private OnClickListener nl;
	private OnClickListener closeListener;

	private String title="";
	private View content;

	private boolean cancelable = true;


	private View mWindow;


	private OnGlobalLayoutListener globalLayoutListener;



	/**
	 * Any operations can happen on any thread.
	 * @param a
	 */
	public WindowBuilder(Activity a){
		this.a = a;
	}

	/**
	 * @param title Title to be displayed
	 * @return this to allow streamlining calls
	 */
	public WindowBuilder setTitle( String title ){
		this.title = title;
		return this;
	}

	/**
	 * @return this to allow streamlining calls
	 */
	public WindowBuilder setContent( View content ){
		this.content = content;
		return this;
	}

	/**
	 * @param ocl listener to use
	 * @return this to allow streamlining calls
	 */
	public WindowBuilder setCloseButtonListener( OnClickListener ocl ){
		closeListener = ocl;
		return this;
	}
	/**
	 * @param gll
	 * @param gll listener to use
	 * @return this to allow streamlining calls
	 */
	public WindowBuilder setOnGlobalLayoutListener( OnGlobalLayoutListener gll ){
		globalLayoutListener = gll;
		return this;
	}


	/**
	 * @param text Either Dia//Log.SURE(0), Dia//Log.SURE(1)
	 * @param ocl listener to use
	 * @return this to allow streamlining calls
	 */
	public WindowBuilder setPositiveButton( String text , int bkrndResource , OnClickListener ocl ){

		posBText = text;
		posButBkrndResource = bkrndResource;
		pl = ocl;

		return this;
	}


	/**
	 * @param text Either Dia//Log.NO_THANKS(0), Dia//Log.BACK(1)
	 * @param ocl listener to use
	 * @return this to allow streamlining calls
	 */
	public WindowBuilder setNegativeButton( String text , int bkrndResource , OnClickListener ocl ){

		negBText = text;
		negButBkrndResource = bkrndResource;
		nl = ocl;

		return this;
	}


	/**
	 * Default is false;
	 */
	public boolean isCancelable() 							{	return cancelable;				}
	/**
	 * Default is false;
	 * @param cancelable
	 * @return this to allow chaining calls
	 */
	public WindowBuilder setCancelable(boolean cancelable) 	{	this.cancelable = cancelable; return this;	}




	public void show(){
		a.runOnUiThread(new Runnable(){

			@Override
			public void run() {

				final View d2 = a.getLayoutInflater().inflate( R.layout.window , null );
				final ScrollView contentView = (ScrollView) d2.findViewById(R.id.windowContent);
				final View window = d2.findViewById(R.id.windowWindow);


				final CTextView2 titleView = (CTextView2) d2.findViewById(R.id.windowTitle);
				titleView.setText(title);


				if( content != null )
					contentView.addView(content);




				final OnClickListener cl = new OnClickListener() {
					@Override
					public void onClick(View view) {
						if( closeListener != null )
							closeListener.onClick(view);
						hide();
					}
				};
				final View closeButton = d2.findViewById(R.id.windowCloseButton);
				closeButton.setOnClickListener(cl);

				if( !cancelable )
					closeButton.setVisibility(View.INVISIBLE);



				final CTextView2 posB = (CTextView2) d2.findViewById(R.id.windowPosText);
				if( posButBkrndResource != -1 ){
					posB.setVisibility(View.VISIBLE);
					posB.setText(posBText);
					posB.setOnClickListener( new mOnClickListener(d2,pl) );
					posB.setBackgroundResource(posButBkrndResource);
				}
				else{
					posB.setVisibility(View.INVISIBLE);
				}




				final CTextView2 negB = (CTextView2) d2.findViewById(R.id.windowNegText);
				if( negButBkrndResource != -1 ){
					negB.setVisibility(View.VISIBLE);
					negB.setText(negBText);
					negB.setOnClickListener( new mOnClickListener(d2,nl) );
					negB.setBackgroundResource(negButBkrndResource);
				}
				else{
					negB.setVisibility(View.INVISIBLE);
				}





				final float threeQuarters = (Rpg.getWidth()*3)/4;


				window.setLayoutParams(new RelativeLayout.LayoutParams( LayoutParams.WRAP_CONTENT , LayoutParams.WRAP_CONTENT ));
				contentView.setLayoutParams(new LinearLayout.LayoutParams( LayoutParams.WRAP_CONTENT , LayoutParams.WRAP_CONTENT ));
				a.addContentView( d2 , new LayoutParams( LayoutParams.FILL_PARENT , LayoutParams.FILL_PARENT ));
				mWindow = d2;
				contentView.requestLayout();
				synchronized( popups ){
					popups.add( d2 );
				}



				final ValueAnimator a = ValueAnimator.ofFloat(0f,1f);
				a.setDuration(150);
				a.addUpdateListener(new AnimatorUpdateListener() {
					@Override
					public void onAnimationUpdate(ValueAnimator animation) {
						window.setScaleX((Float) animation.getAnimatedValue() );
						window.setScaleY((Float) animation.getAnimatedValue() );
						window.setAlpha((Float) animation.getAnimatedValue() );
					}
				});
				a.start();



				final ViewTreeObserver observer= window.getViewTreeObserver();
				observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {

						boolean widthOrHeightChanged = false;

						int newWidth = LayoutParams.WRAP_CONTENT;
						int newHeight = LayoutParams.WRAP_CONTENT;

						if( contentView.getHeight() > Rpg.getHeight() - 100*Rpg.getDp() ){
							newHeight = (int) (Rpg.getHeight() - 100*Rpg.getDp());
							contentView.setLayoutParams(new LinearLayout.LayoutParams( LayoutParams.WRAP_CONTENT , newHeight ));
							widthOrHeightChanged = true;
							contentView.requestLayout();
						}
						if( window.getWidth() > threeQuarters ){
							newWidth = (int) threeQuarters;
							window.setLayoutParams(new RelativeLayout.LayoutParams( newWidth , LayoutParams.WRAP_CONTENT ));
							widthOrHeightChanged = true;
							window.requestLayout();
						}

						if( widthOrHeightChanged ){
							return;
						}


						//window.requestLayout();

						float left = (Rpg.getWidth()-window.getWidth())/2;
						float top = (Rpg.getHeight()-window.getHeight())/2;
						float right = left + window.getWidth();
						float bottom = top + window.getHeight();

						window.setX( left );
						window.setY( top );

						closeButton.setX(window.getWidth() - closeButton.getWidth()*2f);


						final RectF wArea = new RectF(left, top, right, bottom);

						contentView.requestFocus();

						d2.setOnTouchListener(new OnTouchListener() {
							@Override
							public boolean onTouch(View v, MotionEvent event) {
								if( cancelable )
									if( !wArea.contains( event.getX(), event.getY()) ){
										d2.setOnTouchListener(null);
										hide();
										cl.onClick(v);

										//										ViewGroup vg = (ViewGroup)(d2.getParent());
										//										if( vg != null )
										//											vg.removeView(d2);

										//										synchronized( popups ){
										//											popups.remove( d2 );
										//										}
										return true;
									}

								return true;
							}
						});

						OnGlobalLayoutListener globalLayoutListener = WindowBuilder.this.globalLayoutListener;
						if( globalLayoutListener != null )
							globalLayoutListener.onGlobalLayout();

						observer.removeGlobalOnLayoutListener(this);
					}
				});
			}

		});
	}


	public void hide() {
		final View mWindow = WindowBuilder.this.mWindow;
		if( mWindow != null ){
			final ValueAnimator a = ValueAnimator.ofFloat(1f,0f);
			a.setDuration(150);
			a.addUpdateListener(new AnimatorUpdateListener() {
				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					float value = (Float) animation.getAnimatedValue();
					mWindow.setScaleX(value);
					mWindow.setScaleY(value);
					mWindow.setAlpha (value);
				}
			});
			a.addListener(new AnimatorListenerAdapter(){
				@Override
				public void onAnimationEnd(Animator animation) {
					ViewGroup vg = (ViewGroup)(mWindow.getParent());
					if( vg != null )
						vg.removeView(mWindow);
					synchronized( popups ){
						popups.remove( mWindow );
					}
				}
			});
			a.start();
		}
	}

	private static class mOnClickListener implements OnClickListener{
		final OnClickListener ocl;
		final View dialog;
		public mOnClickListener(View d, OnClickListener ocl_){
			ocl = ocl_; dialog = d;
		}

		@Override
		public void onClick(View v) {
			{
				if( dialog.getVisibility() == View.VISIBLE )
				{
					ValueAnimator animation = ValueAnimator.ofFloat( 1f , 0f );
					animation.addUpdateListener(new AnimatorUpdateListener() {
						@Override
						public void onAnimationUpdate(ValueAnimator animation) {
							dialog.setScaleX((Float) animation.getAnimatedValue());
							dialog.setScaleY((Float) animation.getAnimatedValue());
							dialog.setAlpha((Float) animation.getAnimatedValue());
						}
					});
					animation.setDuration(150);
					animation.addListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							ViewParent vp = dialog.getParent();
							if( vp != null && vp instanceof ViewGroup)
								((ViewGroup) vp).removeView(dialog);

							synchronized( popups ){
								popups.remove( dialog );
							}

						}
					});
					animation.start();
				}
			}
			if( ocl != null )
				ocl.onClick(v);
		}

	}







	public static void hidePopUps() {
		Rpg.getGame().getActivity().runOnUiThread(new Runnable(){
			@Override
			public void run() {
				synchronized( popups ){
					for( View v : popups ){

						ViewParent vp = v.getParent();
						if( vp instanceof ViewGroup )
							((ViewGroup)vp).removeView(v);

						popups.remove( v );
					}
				}
			}
		});
	}







}
