package com.kingscastle.nuzi.towerdefence.ui;

import android.graphics.Color;
import android.graphics.Paint.Align;

import com.kingscastle.nuzi.towerdefence.framework.Rpg;

import java.util.ArrayList;



public class PaletteParams 
{
	private static ArrayList<PaletteParams> paletteParams = new ArrayList<PaletteParams>();
	
	
	private float textSize;
	private Align textAlign;
	private int color;
	
	private PaletteParams()
	{		
		clear();
	}
	
	public static PaletteParams getInstance()
	{
		
		if ( paletteParams.size() == 0 )
		{			
			addPaletteParams( paletteParams , 5 );
		}	
			
		
		PaletteParams param = paletteParams.remove( paletteParams.size() - 1 );
			
		param.clear();
		
		return param;
		
	}
	
		
	public void deconstruct()
	{
		clear();
		paletteParams.add( this );
	}

	
	
	private void clear()
	{
		textSize = Rpg.getTextSize();
		textAlign = Align.CENTER;
		color = Color.WHITE;
	}

	
	private static void addPaletteParams( ArrayList<PaletteParams> paletteParams , int howMany )
	{
		int count = 0 ;
		
		while ( count < howMany )
		{
			paletteParams.add(new PaletteParams());
			++ count;
		}
	}

	public static ArrayList<PaletteParams> getPaletteParams() {
		return paletteParams;
	}

	public static void setPaletteParams(ArrayList<PaletteParams> paletteParams) {
		PaletteParams.paletteParams = paletteParams;
	}

	public float getTextSize() {
		return textSize;
	}

	public void setTextSize(float textSize) {
		this.textSize = textSize;
	}

	public Align getTextAlign() {
		return textAlign;
	}

	public void setTextAlign(Align textAlign) {
		this.textAlign = textAlign;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}


	
	
	
	
}
