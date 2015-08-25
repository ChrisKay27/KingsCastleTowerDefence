package com.kingscastle.nuzi.towerdefence.ui;

import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;

import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.framework.implementation.ImageDrawable;


public class UIUtil {

	private static ImageDrawable textViewBackRound;// = new ImageDrawable(Assets.loadImage(R.drawable.dashed_box).getBitmap(), 0, 0, new Paint());

	private static final int MS_PER_HR = 60*60*1000;
	private static final int MS_PER_DAY = 60*60*24*1000;
	private static final int MS_PER_MIN = 60*1000;


	public static String convertToTime(long millisUntilFinished) {
		int count = 0;

		long days = millisUntilFinished/MS_PER_DAY;
		millisUntilFinished -= days*MS_PER_DAY;
		long hrs = millisUntilFinished/MS_PER_HR;
		millisUntilFinished -= hrs*MS_PER_HR;
		long mins = millisUntilFinished/MS_PER_MIN;
		millisUntilFinished -= mins*MS_PER_MIN;
		long secs = millisUntilFinished/1000;

		StringBuilder timeRem = new StringBuilder();

		//String timeRem = "";
		if(days!=0){
			timeRem.append( days );
			timeRem.append( "d: " );
			count++;
		}
		if(hrs!=0){
			timeRem.append( hrs );
			timeRem.append( "h" + (count == 1 ? "" : ": "));
			count++;
		}
		if( count < 2  ){
			if( mins != 0 ){
				timeRem.append( mins );
				timeRem.append( "m"+ (count == 1 ? "" : ": "));
				count++;
			}
			if( count < 2  ){
				timeRem.append( secs );
				timeRem.append( "s" );
			}
		}
		return timeRem.toString();
	}



	static final float[] direction = new float[]{ -0.5f, -1.0f, 0.5f};
	public static final EmbossMaskFilter emf = new EmbossMaskFilter(direction, 0.8f , 16f, 1.5f);

	/**
	 * Must be called from the UI thread.
	 * @param tv
	 * @return
	 */
	public static TextView applyKcStyle(final TextView tv){
		if( tv == null ){
			//Log.e( "applyEmbroyMaskFilter()", "TextView is null! ");
			return null;
		}

		new ApplyKcStyle(tv).run();

		return tv;
	}



	/**
	 * Happens ASync on the main UI thread
	 * @param tv
	 * @return
	 */
	public static TextView applyKcStyleAsync(final TextView tv){
		if( tv == null ){
			//Log.e( "applyEmbroyMaskFilter()", "TextView is null! ");
			return null;
		}
		Rpg.getGame().getActivity().runOnUiThread(new ApplyKcStyle(tv));

		return tv;
	}



	private static class ApplyKcStyle implements Runnable{
		TextView tv;
		public ApplyKcStyle(TextView tv ){
			this.tv = tv;
		}
		@Override
		public void run() {
			if( tv == null ) return;
			tv.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
			//tv.setShadowLayer(0.1f, -2, -2, Color.BLACK);
			tv.setTypeface( Rpg.getImpact() );
			tv.setTextColor( Palette.lightGray );
			tv.getPaint().setMaskFilter(emf);

		}
	}//end MRunnable



	public static void applyKcStyle(Paint paint) {
		paint.setTypeface( Rpg.getImpact() );
		paint.setColor( Palette.lightGray );
		//paint.setMaskFilter(emf);
	}


	public static ImageDrawable getTextViewBackRound(){
		if( textViewBackRound == null )
			textViewBackRound = new ImageDrawable(Assets.loadImage(R.drawable.dashed_box).getBitmap(), 0, 0, new Paint());
		return textViewBackRound;
	}





	/**
	 * Must be called from the UI thread.
	 * @param tvs
	 * @return
	 */
	public static void applyCooperBlack(final TextView... tvs ){
		if( tvs == null )
			return;

		for( TextView tv : tvs ){
			if( tv != null )
				new ApplyCooperStyle(tv).run();
		}
	}


	private static class ApplyCooperStyle implements Runnable{
		TextView tv;
		public ApplyCooperStyle(TextView tv ){
			this.tv = tv;
		}
		@Override
		public void run() {
			if( tv == null ) return;
			tv.setTypeface( Rpg.getCooperBlack() );
			tv.setTextColor( Color.BLACK );
		}
	}//end MRunnable



	public static void applyCooperBlack(Paint mPaint) {
		mPaint.setTypeface(Rpg.getCooperBlack());
	}




	public static void setText(String text, TextView... tvs) {
		for( int i = 0; i < tvs.length; i++)
			tvs[i].setText(text);
	}

	public static void setTranslationY(float offs, TextView... tvs) {
		for( int i = 0; i < tvs.length; i++)
			tvs[i].setTranslationY(offs);
	}

	public static void setUpForBacking(float dr, TextView... tvs) {
		translate(dr,tvs);
		tvs[0].setTextColor(Palette.lightGray);
		tvs[1].setTextColor(Color.BLACK);
		tvs[2].setTextColor(Color.BLACK);
		tvs[0].bringToFront();
	}
	/**
	 * @param dr amount to shift the second and third TextViews to give the appearance of a border
	 * @param tvs MUST be an array of length=3 and no null elements
	 */
	public static void translate(float dr, TextView... tvs) {
		TextView tv1 = tvs[1];
		TextView tv2 = tvs[2];
		tv1.setTranslationX(tv1.getTranslationX()-dr);tv1.setTranslationY(tv1.getTranslationY()-dr);
		tv2.setTranslationX(tv2.getTranslationX()+dr);tv2.setTranslationY(tv2.getTranslationY()+dr);
	}




	public static void setVisibility(int visibility, TextView... tvs) {
		for( int i = 0; i < tvs.length; i++){
			TextView tv = tvs[i];
			if( tv != null && tv.getVisibility() != visibility )
				tv.setVisibility(visibility);
		}
	}




	public static void setTextSize(float twentyDp_, TextView... tvs) {
		for( int i = 0; i < tvs.length; i++)
			tvs[i].setTextSize(twentyDp_);
	}




	public static void setMaxLines(int lines, TextView... tvs) {
		for( int i = 0; i < tvs.length; i++)
			tvs[i].setMaxLines(lines);
	}



	public static void setLoc(float x, float y, TextView... tvs) {
		for( int i = 0; i < tvs.length; i++){
			tvs[i].setX(x);
			tvs[i].setY(y);
		}
	}
	public static void setTranslation(float x, float y, TextView... tvs) {
		for( int i = 0; i < tvs.length; i++){
			TextView tv = tvs[i];
			tv.setTranslationX(tv.getTranslationX()+x);
			tv.setTranslationY(tv.getTranslationY()+y);
		}
	}


	public static void bringToFront(TextView... tvs) {
		for( int i = 0; i < tvs.length; i++)
			tvs[i].bringToFront();
	}











}//end UIUtil

















