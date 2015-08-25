package com.kingscastle.nuzi.towerdefence.effects.animations;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;

import com.kingscastle.nuzi.towerdefence.effects.Palette;
import com.kingscastle.nuzi.towerdefence.framework.Graphics;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.framework.Settings;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.Health;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;


public class Bar extends Anim
{
	private static final int MS_PER_HR = 60*60*1000;
	private static final int MS_PER_DAY = 60*60*24*1000;
	private static final int MS_PER_MIN = 60*1000;
	private static final Paint dstOverPaint;
	private static final Paint redPaint;

	static
	{
		dstOverPaint = new Paint( Rpg.getDstOverPaint() );
		dstOverPaint.setColor( Color.RED );

		redPaint = new Paint(Palette.getPaint(Align.CENTER, Color.RED, Rpg.getTextSize()));
		redPaint.setTypeface( Rpg.getImpact() );
	}


	private Barrable barrable;
	private Rect bar;
	private int fullWidth;
	private boolean showCurrentAndMax;
	private boolean showValueAboveBar = true;
	private boolean showBar = true;
	private boolean showTime = true;

	private Paint barPaint = dstOverPaint;
	private Paint textPaint = redPaint;



	/**
	 * 
	 * @param barrable
	 * @param xOffs You must include dp factor.
	 * @param yOffs You must include dp factor.
	 */
	public Bar( Barrable barrable , float xOffs , float yOffs )
	{
		this.barrable = barrable;
		loadFullBar( xOffs , yOffs );
	}

	public Bar( Barrable barrable )
	{
		this.barrable = barrable;
		loadFullBar();
	}






	@Override
	public void paint( Graphics g , vector v )
	{
		if( Settings.showHealthBar == false )
			if( barrable instanceof Health)
				return;


		if( !isVisible() )
			return;


		float perc = barrable.getPercent();
		if( perc == 1f || perc < 0 )
			return;

		else
		{
			if( showBar ){
				if( perc < 0.33 )
					barPaint.setColor( Color.RED );
				else if( perc < 0.66 )
					barPaint.setColor( Color.YELLOW );
				else
					barPaint.setColor( Color.GREEN );

				g.drawRect( getBar( perc ) , v , barPaint );
			}


			if( showCurrentAndMax )
				if( showTime ){
					String time = barrable.getTimeToCompletion();
					if( time.contains("d") )
						time = time.substring(0, time.indexOf("h")+1);
					else if( time.contains("h") )
						time = time.substring(0, time.indexOf("m")+1);
					//					int ms =
					//					int days = ms/MS_PER_DAY;
					//					ms -= days*MS_PER_DAY;
					//					int hrs = ms/MS_PER_HR;
					//					ms -= hrs*MS_PER_HR;
					//					int mins = ms/MS_PER_MIN;
					//					ms -= mins*MS_PER_MIN;
					//					int secs = ms/1000;
					//					StringBuilder timeRem = new StringBuilder();
					//
					//					//String timeRem = "";
					//					if(days!=0){
					//						timeRem.append( days );
					//						timeRem.append( "d: " );
					//					}
					//					if(hrs!=0){
					//						timeRem.append( hrs );
					//						timeRem.append( "h: " );
					//					}
					//					if(mins!=0){
					//						timeRem.append( mins );
					//						timeRem.append( "m: " );
					//					}
					//					timeRem.append( secs );
					//					timeRem.append( "s" );

					if( showValueAboveBar ){
						////Log.d( "Bar" , "showCurrentMax & showTime & showAbove");
						//g.drawImage( timeBackground , bar.centerX()-textPaint.measureText(time) , bar.top - timeBackground.getHeight(), v );
						g.drawString( time , bar.centerX() , bar.top - Rpg.twoDp , v , textPaint );
					}
					else{
						////Log.d( "Bar" , "showCurrentMax & showTime & !showAbove");
						//g.drawImage( timeBackground , bar.centerX()-textPaint.measureText(time) , bar.bottom+1 , v);
						g.drawString( time , bar.centerX() , bar.bottom - textPaint.ascent() ,  v , textPaint );
					}
				}
				else{
					if( showValueAboveBar )
						g.drawString( barrable.getValue() + "/" + barrable.getMaxValue() , bar.centerX() , bar.top , v , textPaint );
					else
						g.drawString( barrable.getValue() + "/" + barrable.getMaxValue() , bar.centerX() , bar.bottom - textPaint.ascent() ,  v , textPaint );
				}
		}

	}




	Rect getBar(float perc)
	{

		//		if ( bar == null )
		//		{
		//			loadFullBar();
		//		}

		int w = (int)  ( fullWidth * perc );

		bar.set( bar.left , bar.top , bar.left + w , bar.bottom );

		return bar;

	}



	void loadFullBar()
	{
		loadFullBar( -8 * Rpg.getDp() , -10 * Rpg.getDp() );
	}




	void loadFullBar(float left, float top)
	{
		float dp = Rpg.getDp();
		fullWidth = (int) (dp * 16);

		int l = (int) ( left ) ;
		int t = (int) ( top ) ;

		bar = new Rect(l, t, l + fullWidth, t + (int) (dp * 2) );
	}



	public Paint getTextPaint() {
		return textPaint;
	}


	public void setTextPaint(Paint textPaint) {
		this.textPaint = textPaint;

	}


	public Paint getBarPaint() {
		return barPaint;
	}


	public void setBarPaint(Paint barPaint) {
		this.barPaint = barPaint;

	}


	public void setColor( int color )
	{
		textPaint = new Paint( textPaint );
		textPaint.setColor( color );

		barPaint = new Paint( barPaint );
		barPaint.setColor( color );

	}



	public Barrable getBarrable()
	{
		return barrable;
	}


	public void setBarrable( Barrable barrable )
	{
		this.barrable = barrable;
	}


	public Rect getBar() {
		return bar;
	}


	public void setBar( Rect bar )
	{
		this.bar = bar;
	}


	public int getFullWidth() {
		return fullWidth;
	}


	public void setFullWidth(int fullWidth) {
		this.fullWidth = fullWidth;
	}


	public boolean isShowCurrentAndMax() {
		return showCurrentAndMax;
	}


	public void setShowCurrentAndMax(boolean showCurrentAndMax) {
		this.showCurrentAndMax = showCurrentAndMax;
	}


	public boolean isShowValueAboveBar() {
		return showValueAboveBar;
	}


	public void setShowValueAboveBar(boolean showAboveBar) {
		showValueAboveBar = showAboveBar;
	}


	public boolean isShowBar() {
		return showBar;
	}


	public void setShowBar(boolean showBar) {
		this.showBar = showBar;
	}


	public boolean isShowTime() {
		return showTime;
	}


	public void setShowTime(boolean showTime) {
		this.showTime = showTime;
	}


	@Override
	public void nullify()
	{
		//		barrable = null;
		//		bar = null;
		//		super.nullify();
	}
}