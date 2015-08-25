package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes;

import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;

import java.util.ArrayList;

public abstract class Summoner extends RangedSoldier
{
	private ArrayList<LivingThing> aliveSummons;
    private final ArrayList<LivingThing> deadSummons ;

	protected abstract int getMaxNumSummons();
	protected abstract LivingThing getAPossibleSummon();
	
	private long lastCheckedSummonSituation;	
	
	public Summoner()
	{
		aliveSummons = new ArrayList<LivingThing>();
		deadSummons = new ArrayList<LivingThing>();
	}
	
	
	protected boolean checkTimeToDoAndSummon()
	{
		if( lastCheckedSummonSituation + 4000 < GameTime.getTime() )
		{
			checkTargetsSituation(getTarget());
			
			removeDeadSummons( aliveSummons , deadSummons);
			
			
			if( getTarget() != null)
			{			
				if( aliveSummons.size() < getMaxNumSummons() )
				{
					aliveSummons.add(getAPossibleSummon());
				}
			}
			
			
			
			lastCheckedSummonSituation = GameTime.getTime();
		}
		
		return false;
	}
	

	
	private static void removeDeadSummons(ArrayList<LivingThing> aliveSummons , ArrayList<LivingThing> deadSummons)
	{
		deadSummons.clear();
		
		if( aliveSummons == null)
		{
			return;
		}
		
		for(LivingThing lt : aliveSummons)
		{
			if( lt.isDead() )
			{
				deadSummons.add(lt);
			}
		}
		
		aliveSummons.removeAll(deadSummons);
		deadSummons.clear();
	}




	public ArrayList<LivingThing> getAliveSummons() {
		return aliveSummons;
	}

	public void setAliveSummons(ArrayList<LivingThing> summons) {
		this.aliveSummons = summons;
	}
	
}
