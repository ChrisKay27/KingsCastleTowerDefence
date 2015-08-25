package com.kingscastle.nuzi.towerdefence.ui;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;

import java.util.ArrayList;


public class DialogBuilder{

	private static ArrayList<View> popups = new ArrayList<View>();


	public static final int OK = 0;
	public static final int SURE = 1;
	public static final int NO_THANKS = 0;
	public static final int BACK = 1;



	private final Activity a;

	private int pos = -1;
	private int neg = -1;

	private OnClickListener pl;
	private OnClickListener nl;

	private String text;

	private boolean cancelable = false;


	/**
	 * Any operations can happen on any thread.
	 * @param a
	 */
	public DialogBuilder(Activity a){
		this.a = a;
	}


	/**
	 * @param text Message to be displayed
	 * @return this to allow streamlining calls
	 */
	public DialogBuilder setText( String text ){

		this.text = text;
		return this;
	}


	/**
	 * @param type Either Dia//Log.SURE(0), Dia//Log.SURE(1)
	 * @param ocl listener to use
	 * @return this to allow streamlining calls
	 */
	public DialogBuilder setPositiveButton( int type , OnClickListener ocl ){
		if( type != 0 || type != 1 )
			type = 0;

		pos = type;
		pl = ocl;

		return this;
	}


	/**
	 * @param type Either Dia//Log.NO_THANKS(0), Dia//Log.BACK(1)
	 * @param ocl listener to use
	 * @return this to allow streamlining calls
	 */
	public DialogBuilder setNegativeButton( int type , OnClickListener ocl ){
		if( type != 0 || type != 1 )
			type = 0;

		neg = type;
		nl = ocl;

		return this;
	}


	/**
	 * Default is false;
	 */
	public boolean isCancelable() 					{	return cancelable;				}
	/**
	 * Default is false;
	 * @param cancelable
	 * @return this to allow chaining calls
	 */
	public DialogBuilder setCancelable(boolean cancelable) 	{	this.cancelable = cancelable; return this;	}




	public void show(){
		a.runOnUiThread(new Runnable(){
			@Override
			public void run() {

				final View scrollView = a.getLayoutInflater().inflate( R.layout.warning_message , null );
				final View d = scrollView.findViewById(R.id.relativeLayoutWarning);

				WindowBuilder wb = new WindowBuilder(a);

				switch( pos ){
				default:break;
				case 0: wb.setPositiveButton("", R.drawable.ok_button , pl);   break;
				case 1: wb.setPositiveButton("", R.drawable.sure_button , pl); break;
				}

				//				ImageButton posB = (ImageButton) d2.findViewById(R.id.imageButtonPosButton);
				//
				//				switch( pos ){
				//				default:break;
				//				case 0: posB.setVisibility(View.VISIBLE); posB.setOnClickListener( new mOnClickListener(d2,pl) ); break;
				//				case 1: posB.setVisibility(View.VISIBLE); posB.setOnClickListener( new mOnClickListener(d2,pl) ); posB.setBackgroundResource(R.drawable.sure_button); break;
				//				}

				switch( neg ){
				default:break;
				case 0: wb.setNegativeButton("", R.drawable.back_button, nl); break;
				case 1: wb.setNegativeButton("", R.drawable.no_button, nl); break;
				}

				//				ImageButton negB = (ImageButton) d2.findViewById(R.id.imageButtonNegButton);
				//
				//				switch( neg ){
				//				default:break;
				//				case 0: negB.setVisibility(View.VISIBLE); negB.setOnClickListener( new mOnClickListener(d2,nl) ); break;
				//				case 1: negB.setVisibility(View.VISIBLE); negB.setOnClickListener( new mOnClickListener(d2,nl) ); posB.setBackgroundResource(R.drawable.back_button); break;
				//				}


				CTextView2 tv = (CTextView2) scrollView.findViewById(R.id.textViewWarningMessage);
				//				TextView tv2 = (TextView) d2.findViewById(R.id.textViewWarningMessage2);
				//				TextView tv3 = (TextView) d2.findViewById(R.id.textViewWarningMessage3);
				//				TextView[] tvs = { tv , tv2 , tv3 };
				//				UIUtil.applyCooperBlack(tvs);
				//				UIUtil.setUpForBacking( 2 , tvs );
				//				UIUtil.setText( text , tvs );
				tv.setText(text);



				wb.setCancelable(cancelable);


				//final float threeQuarters = (Rpg.getWidth()*3)/4;


				d.setLayoutParams(new ScrollView.LayoutParams( LayoutParams.WRAP_CONTENT , LayoutParams.WRAP_CONTENT ));
				scrollView.setLayoutParams(new RelativeLayout.LayoutParams( LayoutParams.WRAP_CONTENT , LayoutParams.WRAP_CONTENT ));

				wb.setContent(scrollView);
				wb.show();
				//				kc.addContentView( d2 , new ViewGroup.LayoutParams( LayoutParams.FILL_PARENT , LayoutParams.FILL_PARENT ));
				//				scrollView.requestLayout();
				//
				//				synchronized( popups ){
				//					popups.add( d2 );
				//				}



				//				final ValueAnimator a = ValueAnimator.ofFloat(0f,1f);
				//				a.setDuration(150);
				//				a.addUpdateListener(new AnimatorUpdateListener() {
				//					@Override
				//					public void onAnimationUpdate(ValueAnimator animation) {
				//						d.setScaleX((Float) animation.getAnimatedValue() );
				//						d.setScaleY((Float) animation.getAnimatedValue() );
				//						d.setAlpha((Float) animation.getAnimatedValue() );
				//					}
				//				});
				//				a.act();



				//				final ViewTreeObserver observer= d.getViewTreeObserver();
				//				observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
				//					@Override
				//					public void onGlobalLayout() {
				//
				//						if( scrollView.getWidth() > threeQuarters ){
				//							scrollView.setLayoutParams(new RelativeLayout.LayoutParams( (int) threeQuarters , LayoutParams.WRAP_CONTENT ));
				//							scrollView.requestLayout();
				//							return;
				//						}
				//
				//
				//						float left = (Rpg.getWidth()-scrollView.getWidth())/2;
				//						float top = (Rpg.getHeight()-scrollView.getHeight())/2;
				//						float right = left + scrollView.getWidth();
				//						float bottom = top + scrollView.getHeight();
				//
				//						scrollView.setX( left );
				//						scrollView.setY( top );
				//
				//
				//						final RectF wArea = new RectF(left, top, right, bottom);
				//
				//						scrollView.requestFocus();
				//
				//						d2.setOnTouchListener(new OnTouchListener() {
				//							@Override
				//							public boolean onTouch(View v, MotionEvent event) {
				//								if( cancelable )
				//									if( !wArea.contains( event.getX(), event.getY()) ){
				//										d2.setOnTouchListener(null);
				//										ViewGroup vg = (ViewGroup)(d2.getParent());
				//										if( vg != null )
				//											vg.removeView(d2);
				//
				//										synchronized( popups ){
				//											popups.remove( d2 );
				//										}
				//										return true;
				//									}
				//
				//								return true;
				//							}
				//						});
				//
				//						observer.removeGlobalOnLayoutListener(this);
				//					}
				//				});
			}
		});
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




	public static void alert(String message) {
		final Activity a = Rpg.getGame().getActivity();
		DialogBuilder db = new DialogBuilder( a );
		db.setPositiveButton(DialogBuilder.SURE,null).setText( message ).show();
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
