package com.kingscastle.nuzi.towerdefence.gameElements.spells;

import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.effects.animations.LightningStrikeAnim;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.abilities.Ability;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;

import org.jetbrains.annotations.NotNull;


public class LightningStrike extends InstantSpell{



	private static Image iconImage;

	private static RectF staticPerceivedArea;



    @Override
	public Abilities getAbility()				 {				return Abilities.LIGHTNINGSTRIKE ; 			}


	@Override
	public boolean cast( MM mm )
	{
		loadAnimation();
		mm.getEm().add(getAnim(),true);
		return true;
	}


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
	public RectF getPerceivedArea()
	{
		if (staticPerceivedArea == null )
		{
			int sizeDiv2 = (int) (30 * Rpg.getDp());
			staticPerceivedArea = new RectF(-sizeDiv2, -sizeDiv2, sizeDiv2, sizeDiv2);
		}
		return staticPerceivedArea;
	}



	@Override
	public boolean hitsOnlyOneThing() {
		return false;
	}




    @Override
	public String toString() {
		return "Lightning Strike";
	}


    @Override
	public String getName() {
		return "LightningStrike";
	}




	@Override
	public void loadAnimation()
	{
		if( getAnim() == null)
		{
			setAnim( new LightningStrikeAnim(loc) );
		}
	}



    @Override
	public Spell newInstance()	{
		return new LightningStrike();
	}

	@Override
	public Ability newInstance(@NotNull LivingThing target) {
		return new LightningStrike();
	}

	@Override
	public Image getIconImage()
	{
		if( iconImage == null )
		{
			//iconImage = Assets.loadImage(R.drawable.lightning_icon);
		}
		return iconImage;
	}




}
