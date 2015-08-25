package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes;


import com.kingscastle.nuzi.towerdefence.teams.Teams;

public abstract class UpperHealer extends Healer
{
	public UpperHealer() {

	}
	public UpperHealer(Teams team) {
		super(team);
	}
	{
		buildTime = 3*60*1000;
	}

}
