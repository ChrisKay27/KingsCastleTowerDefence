package com.kingscastle.nuzi.towerdefence.gameElements.spells;

import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.effects.animations.RainbowStrikeAnim;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;


public class RainbowStrike extends InstantSpell{


	private static RectF staticPerceivedArea;

	private static Image iconImage;



	@Override
	public Abilities getAbility()				 {				return Abilities.RAINBOWSTRIKE ; 			}

	@Override
	public int calculateDamage()
	{
		return 30 + getCaster().getLQ().getLevel() * 3;
	}


	@Override
	public int calculateManaCost( LivingThing aWizard)
	{
		return 20 + aWizard.getLQ().getLevel() * 3;
	}




	@Override
	public boolean hitsOnlyOneThing() {
		return false;
	}




	@Override
	public String toString() {
		return "Rainbow Strike";
	}


	@Override
	public String getName() {
		return "RainbowStrike";
	}



	@Override
	public void uncast() {
	}

	@Override
	public boolean cast( MM mm )
	{
		super.cast(mm);
		loadAnimation();
		hitCreatures(checkHit(getTeamName(),this));
		return true;
	}



	@Override
	public void loadAnimation()
	{
		setAnim( new RainbowStrikeAnim(loc ));
	}


	public RainbowStrike(){}


	@Override
	public Spell newInstance()
	{
		return new RainbowStrike();
	}


	@Override
	public RectF getStaticPerceivedArea()
	{
		if( staticPerceivedArea == null )
		{
			int sizeDiv2 = (int) (Rpg.getDp()*30);
			staticPerceivedArea = new RectF(-sizeDiv2,-sizeDiv2,sizeDiv2,sizeDiv2);
		}
		return staticPerceivedArea;
	}

	@Override
	public void setStaticPerceivedArea(RectF staticPercArea) {
		staticPerceivedArea = staticPercArea;
	}


	@Override
	public Image getIconImage()
	{

		if( iconImage == null )
		{
			//	iconImage = Assets.loadImage(R.drawable.lightning_icon);
		}

		return iconImage;

	}



}
