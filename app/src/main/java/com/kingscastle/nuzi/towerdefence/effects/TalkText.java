package com.kingscastle.nuzi.towerdefence.effects;

import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

public class TalkText extends RisingText
{

	public TalkText(String t, LivingThing on) {
		super(t, on);
	}

	public TalkText(String t, vector v) {
		super(t,v);
	}

	@Override
	public void	incrimentGraphics()
	{
		if( nextMove < GameTime.getTime() )
		{
			nextMove = GameTime.getTime() + 300;
			loc.translate(0, -1);
		}

		if( dieAt < GameTime.getTime() )
			setDead(true);

	}

}
