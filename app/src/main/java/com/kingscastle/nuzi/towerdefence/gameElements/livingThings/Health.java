package com.kingscastle.nuzi.towerdefence.gameElements.livingThings;

import com.kingscastle.nuzi.towerdefence.effects.animations.Barrable;

public class Health implements Barrable
{
	private int fullHealth;
	private int health;


	public int getHealth()
	{
		return health;
	}

	public void addHealth( int dHealth )
	{
		if( dHealth < 0 )
		{
			throw new IllegalArgumentException();
		}

		health += dHealth;

		if ( health > fullHealth )
		{
			health = fullHealth;
		}

	}

	public void setHealth( int health2 )
	{

		health = health2;

		if( health > fullHealth )
		{
			health = fullHealth;
		}
	}



	public int getFullHealth()
	{
		return fullHealth;
	}

	public void setFullHealth( int fullHealth2 )
	{
		fullHealth = fullHealth2;
	}



	public void setHealthPercent( float d )
	{
		health = (int) (fullHealth*d);

	}
	@Override
	public float getPercent()
	{
		return ((float) getHealth() ) / getFullHealth();
	}
	@Override
	public int getMaxValue()
	{
		return getFullHealth();
	}
	@Override
	public int getValue()
	{
		return getHealth();
	}



	@Override
	public String getTimeToCompletion(){
		return "";
	}




}
