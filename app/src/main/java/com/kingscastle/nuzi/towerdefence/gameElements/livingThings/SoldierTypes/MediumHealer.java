package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes;

import com.kingscastle.nuzi.towerdefence.teams.Teams;



public abstract class MediumHealer extends Healer
{
	public MediumHealer() {

	}
	public MediumHealer(Teams team) {
		super(team);
	}
	{
		buildTime = 60*1000;
	}

}
