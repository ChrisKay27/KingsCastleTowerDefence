package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes;


import com.kingscastle.nuzi.towerdefence.teams.Teams;

public abstract class UpperMeleeSoldier extends MeleeSoldier
{
	public UpperMeleeSoldier() {
	}

	public UpperMeleeSoldier(Teams team) {
		super(team);
	}

	{
		buildTime = 3*60*1000;
	}

}
