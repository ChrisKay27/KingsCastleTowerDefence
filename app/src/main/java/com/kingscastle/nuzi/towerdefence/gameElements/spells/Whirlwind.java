package com.kingscastle.nuzi.towerdefence.gameElements.spells;

import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.effects.animations.WhirlwindAnim;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;


public class Whirlwind extends InstantSpell
{



	private static Image iconImage;
	private static final RectF perceivedArea = new RectF(-Rpg.getDp()*30,-Rpg.getDp()*30,Rpg.getDp()*30,Rpg.getDp()*30);
	private final boolean hitsOnlyOneThing = false;


	@Override
	public Abilities getAbility()				 {				return Abilities.WHIRLWIND ; 			}

	{
		setRefreshEvery(500);
	}

	private Whirlwind(){}


	@Override
	public Spell newInstance()
	{
		return new Whirlwind();
	}




	@Override
	public int calculateDamage()
	{
		if( getCaster() != null )
		{
			return 10 + getCaster().getLQ().getLevel() * 3;
		}
		return 5;
	}



	@Override
	public int calculateManaCost( LivingThing aWizard)
	{
		if( aWizard != null )
		{
			return 15 + aWizard.getLQ().getLevel() * 5;
		}
		return 5;
	}




	@Override
	public void loadAnimation()
	{
		setAnim( new WhirlwindAnim( loc ) );
	}




	@Override
	public boolean cast( MM mm )
	{
		super.cast(mm);
		mm.getEm().add(getAnim(),true);
		return true;
	}




	@Override
	public RectF getPerceivedArea()
	{
		return perceivedArea;
	}


	@Override
	public boolean hitsOnlyOneThing()
	{
		return hitsOnlyOneThing;
	}



	@Override
	public String toString() {
		return "Whirl Wind";
	}


	@Override
	public String getName() {
		return "WhirlWind";
	}


	@Override
	public Image getIconImage()
	{

		if( iconImage == null )
		{
			//	iconImage = Assets.loadImage(R.drawable.whirlwind_icon);
		}

		return iconImage;
	}






}
