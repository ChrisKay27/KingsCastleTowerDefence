package com.kingscastle.nuzi.towerdefence.ui;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;

import com.kingscastle.nuzi.towerdefence.framework.Rpg;

import java.util.ArrayList;



public class Palette
{
	private static final ArrayList<Paint> paints = new ArrayList<Paint>();
	public static int lightGray = Color.rgb( 214, 214, 214 );

	static
	{
		Paint yellowLeft = new Paint();
		yellowLeft.setTextSize( Rpg.getTextSize() );
		yellowLeft.setTextAlign(Align.LEFT);
		yellowLeft.setAntiAlias(true);
		yellowLeft.setColor(Color.YELLOW);
		Rpg.applyDTFont(yellowLeft);
		paints.add ( yellowLeft );

	}


	public static Paint getPaint( Align align , int color , float size )
	{
		boolean sameColor = false;
		boolean sameAlign = false;
		boolean sameSize = false;


		for ( Paint p : paints )
		{
			sameSize = p.getTextSize() == size;
			sameColor = p.getColor() == color;
			sameAlign = p.getTextAlign() == align;

			if ( sameAlign && sameColor && sameSize )
			{
				return p;
			}

		}

		Paint newPaint = new Paint();

		newPaint.setAntiAlias( true );
		newPaint.setTextSize( size );
		newPaint.setColor( color );
		newPaint.setTextAlign( align );
		newPaint.setTypeface( Rpg.getImpact() );
		//Rpg.applyDTFont(newPaint);
		paints.add ( newPaint );

		return newPaint;
	}


	public static Paint getPaint( Align align , int color )
	{
		return getPaint ( align , color , Rpg.getTextSize() );
	}



	public static Paint getPaint( int color , float textSize )
	{
		return getPaint ( Align.CENTER , color , textSize );
	}







	public static Paint getPaint( PaletteParams params )
	{
		Paint p = getPaint ( params.getTextAlign() , params.getColor() , params.getTextSize() );
		params.deconstruct();
		return p;
	}


	public static void adjustTextSizeSmallest( Paint paint , float smallestHeightOfText )
	{

		if( smallestHeightOfText < 10 )
			smallestHeightOfText = 20;

		paint.setTextSize( 0.01f );

		while( paint.getFontSpacing() < smallestHeightOfText )
		{

			paint.setTextSize( paint.getTextSize() + 1 );
		}

		paint.setTextSize( paint.getTextSize() - 1 );
	}


	public static void adjustSizeLargest( Paint paint , float largestTextHeight)
	{

		while( paint.getFontSpacing() > largestTextHeight )
			paint.setTextSize( paint.getTextSize() - 1 );


		paint.setTextSize( paint.getTextSize() - 1 );
	}


	public static void adjustTextLength( Paint paint , String line , int width )
	{
		if( paint.measureText( line ) > width )

			while( paint.measureText( line ) > width )
				paint.setTextSize( paint.getTextSize() - 1 );
		else

			while( paint.measureText( line ) < width )
				paint.setTextSize( paint.getTextSize() + 1 );




	}






}
