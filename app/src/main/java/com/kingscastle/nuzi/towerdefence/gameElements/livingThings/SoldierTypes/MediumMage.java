package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes;


import com.kingscastle.nuzi.towerdefence.teams.Teams;

public abstract class MediumMage extends MageSoldier
{

	public MediumMage() {
	}

	public MediumMage(Teams team) {
		super(team);
	}

	{
		buildTime = 60*1000;
	}

}
