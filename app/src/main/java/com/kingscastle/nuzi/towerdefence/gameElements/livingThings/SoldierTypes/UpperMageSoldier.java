package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes;


import com.kingscastle.nuzi.towerdefence.teams.Teams;

public abstract class UpperMageSoldier extends MageSoldier{

	public UpperMageSoldier() {

	}
	public UpperMageSoldier(Teams team) {
		super(team);
	}

	{
		buildTime = 3*60*1000;
	}

}
