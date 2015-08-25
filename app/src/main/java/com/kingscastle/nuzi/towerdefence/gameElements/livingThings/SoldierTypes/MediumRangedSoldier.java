package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes;


import com.kingscastle.nuzi.towerdefence.teams.Teams;

public abstract class MediumRangedSoldier extends RangedSoldier
{
	public MediumRangedSoldier() {

	}
	public MediumRangedSoldier(Teams team) {
		super(team);
	}

	{
		buildTime = 60*1000;
	}

}
