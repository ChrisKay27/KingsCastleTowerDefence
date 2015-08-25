
package com.kingscastle.nuzi.towerdefence.effects;

import android.graphics.Color;
import android.graphics.Paint;

import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;


public class GoldText extends RisingText{

	public final static int txtColor = Color.YELLOW;
	private final static Paint yellowCenter = Palette.getPaint( Color.YELLOW , Rpg.getSmallestTextSize() );

	public GoldText(String t,LivingThing on){
		super(t, on);

	}
	public GoldText(int m,LivingThing on){
		super(m+"", on);

	}
	@Override
	public Paint getPaint(){
		return yellowCenter;
	}
}