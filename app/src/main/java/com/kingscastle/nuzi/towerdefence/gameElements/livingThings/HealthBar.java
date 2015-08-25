package com.kingscastle.nuzi.towerdefence.gameElements.livingThings;

import android.graphics.Color;
import android.graphics.Rect;

import com.kingscastle.nuzi.towerdefence.effects.animations.Anim;
import com.kingscastle.nuzi.towerdefence.framework.Graphics;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.framework.Settings;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;


public class HealthBar extends Anim
{
	private LivingThing owner;
	private Rect healthBar;
	private int fullWidth;

	public HealthBar( LivingThing lt )
	{
		owner = lt;
	}


	@Override
	public void paint( Graphics g , vector v )
	{
		if( Settings.showHealthBar == false )
		{
			return;
		}
		if ( owner == null || owner.getLQ() == null )
		{
			setOver( true );
		}
		if( owner.getLQ().getHealth() < 0 )
		{
			return;
		}
		else
		{
			g.drawRect( getHealthBar() , v , Color.RED );
		}
	}



	Rect getHealthBar()
	{
		if ( healthBar == null )
		{
			loadFullHealthBar();
		}

		int w = (int)  ( fullWidth * owner.getLQ().getHealthPercent() );

		healthBar.set( healthBar.left , healthBar.top , healthBar.left + w , healthBar.bottom );

		return healthBar;
	}


	void loadFullHealthBar()
	{
		float dp = Rpg.getDp();
		fullWidth = (int) (dp * 16);

		int left = (int) (-dp * 8);
		int top = (int) (-dp * 15);
		healthBar = new Rect(left , top , left + fullWidth , (int) (top + dp * 1) );
	}


	@Override
	public void nullify()
	{
		owner = null;
		healthBar = null;
		super.nullify();
	}
}