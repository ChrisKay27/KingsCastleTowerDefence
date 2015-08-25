package com.kingscastle.nuzi.towerdefence.effects;

import android.graphics.Color;

import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;


class ExperienceText extends RisingText {
	
	private final static int txtColor = Color.YELLOW;
	
	public ExperienceText(String t, LivingThing on) {
		super(t, on);
		setColor(txtColor);
	}

	
	
	
	
}
