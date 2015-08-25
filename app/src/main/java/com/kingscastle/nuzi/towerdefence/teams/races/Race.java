package com.kingscastle.nuzi.towerdefence.teams.races;


import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.SoldierType;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.BuildableUnits;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Building;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Buildings;
import com.kingscastle.nuzi.towerdefence.gameUtils.Age;

public abstract class Race
{
	public abstract BuildableUnits getUnitsFor( Buildings building , Age age );

	public abstract SoldierType getMy( GeneralSoldierType advancedHealer ) ;

	public abstract LivingThing getMyVersionOfA( GeneralSoldierType basicMeleeSoldier );

	public abstract Building getMyVersionOfA( Buildings building );

	public abstract Races getRace();

	public abstract Buildings getMyRacesBuildingType(Buildings building);
}
