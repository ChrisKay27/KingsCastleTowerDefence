package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.orders;


import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Input.TouchEvent;
import com.kingscastle.nuzi.towerdefence.gameElements.CD;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.movement.pathing.Grid;
import com.kingscastle.nuzi.towerdefence.gameUtils.CoordConverter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Order
{
	public enum OrderTypes
	{
		MINE_THIS , LOG_THIS , BUILD_THIS , GO_HERE , ATTACK_THIS, HARVEST_THIS, CANCEL, REPAIR_THIS, GARRISON_HERE, HOLD_POSITION_STANCE, DEPOSIT_RESOURCES, CHANGE_STANCE, STAY_NEAR_HERE, BUILD_DECO


	}

	public abstract Image getIconImage();

	public abstract void saveYourSelf(BufferedWriter b) throws IOException;

	public abstract ArrayList<? extends LivingThing> getUnitsToBeOrdered();

	public abstract void setUnitToBeOrdered( LivingThing unit );

	public boolean analyseTouchEvent( TouchEvent event , CoordConverter cc ,CD cd){
		return false;
	}
	public boolean analyseTouchEvent(TouchEvent event, CoordConverter cc ,CD cd , Grid grid){
		return false;
	}

	public abstract void setUnitsToBeOrdered(ArrayList<? extends LivingThing> livingThings);

	public abstract OrderTypes getOrderType();














}
