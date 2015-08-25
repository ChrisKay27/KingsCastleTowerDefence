package com.kingscastle.nuzi.towerdefence.effects;

import android.graphics.Color;
import android.graphics.Paint;

import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;


public class HealingText extends RisingText
{

	//private static final String TAG = "HealingText";
	private static Paint greenPaint;

	public HealingText( String t, LivingThing on )
	{
		super( t, on );
		// ////Log.v(TAG, "Healing Text Contructor");
		// ////Log.v(TAG, "Healing Amount: " + t);
		// ////Log.v(TAG, "On a : " + on);
	}



	@Override
	public Paint getPaint()
	{
		buildPaint();
		return greenPaint;
	}


	void buildPaint()
	{
		if (greenPaint == null )
		{
			greenPaint = new Paint();
			greenPaint.setTextSize( Rpg.getSmallestTextSize() );
			greenPaint.setTextAlign( Paint.Align.CENTER );
			greenPaint.setAntiAlias( true );
			greenPaint.setColor( Color.GREEN );
		}
	}
}
