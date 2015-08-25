package com.kingscastle.nuzi.towerdefence.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.kingscastle.nuzi.towerdefence.framework.Rpg;


public class CTextView2 extends TextView{

	{
		setPadding(3, 3, 3, 3);
		setTextColor(Palette.lightGray);
		setTypeface(Rpg.getCooperBlack());
	}

	public CTextView2(Context context) {
		super(context);
	}
	public CTextView2(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public CTextView2(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CTextView2(Context context, String text) {
		super( context );
		setText(text);
	}
	@Override
	protected void onDraw( Canvas c ){
		if( getTypeface() != Rpg.getCooperBlack() )
			setTypeface(Rpg.getCooperBlack());
		int textColor = getTextColors().getDefaultColor();
		setTextColor(Color.BLACK); // your stroke's color
		getPaint().setStrokeWidth(3);
		getPaint().setStyle(Paint.Style.STROKE);
		super.onDraw(c);
		setTextColor(textColor);
		getPaint().setStrokeWidth(0);
		getPaint().setStyle(Paint.Style.FILL);
		super.onDraw(c);
	}

}
