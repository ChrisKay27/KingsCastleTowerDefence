package com.kingscastle.nuzi.towerdefence.effects.animations;


import com.kingscastle.nuzi.towerdefence.framework.Graphics;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

public class LoopingSmoke extends Anim
{

	private final SmokeAnim smoke1;
	private SmokeAnim smoke2;

	public LoopingSmoke( vector loc )
	{
		setLoc( loc );
		smoke1 = new SmokeAnim( loc );
		smoke1.setLooping( true );
		setLooping( true );
		onlyShowIfOnScreen = true;
	}

	@Override
	public void paint( Graphics g , vector v )
	{
		if( smoke1 != null )
		{
			smoke1.paint(g,v);
		}
		if( smoke2 != null )
		{
			smoke2.paint(g,v);
		}

	}

	@Override
	public boolean act()
	{
		if( smoke1 != null )
		{
			smoke1.act();
		}

		if( smoke2 != null )
		{
			smoke2.act();
		}

		if( smoke1 != null && smoke2 == null && smoke1.getCurrentImageIndex() > 20 )
		{
			smoke2 = new SmokeAnim( loc );
			smoke2.setLooping(true);
			smoke2.setOffs(getOffs());
			//System.out.println("creating smoke 2");
		}

		return isOver();
	}


	@Override
	public String toString()
	{
		return "LoopingSmoke";
	}

	@Override
	public void setOver(boolean b)
	{
		//System.out.println("setting looping smoke to over: " + b);
		super.setOver( b );

		if( smoke1 != null )
		{
			smoke1.setOver(b);
		}

		if( smoke2 != null )
		{
			smoke2.setOver(b);
		}
	}

	public void randomStart()
	{
		smoke1.setCurrentImageIndex( (int) ( Math.random() * 20 ) );
	}

	@Override
	public Anim setOffs( vector offs )
	{
		if( smoke1 != null )
			smoke1.setOffs( offs );

		if( smoke2 != null )
			smoke2.setOffs( offs );

		return super.setOffs(offs);
	}

}
