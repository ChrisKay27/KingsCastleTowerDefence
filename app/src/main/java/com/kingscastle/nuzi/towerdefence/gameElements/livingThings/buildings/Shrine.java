package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings;


import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;


public abstract class Shrine extends AttackingBuilding
{

	private static ArrayList<vector> staticDamageOffsets;

	static
	{
		loadDamageOffsets();
	}

	Shrine(Buildings name) {
		super(name , null );
	}









	private static void loadDamageOffsets()
	{
		float dp = Rpg.getDp();

		staticDamageOffsets = new ArrayList<vector>();
		staticDamageOffsets.add( new vector( Math.random()*-5*dp , -5*dp + Math.random()*10*dp ) );
		staticDamageOffsets.add( new vector( Math.random()*-5*dp , -5*dp + Math.random()*10*dp ) );
		staticDamageOffsets.add( new vector( Math.random()*5*dp  , -5*dp + Math.random()*10*dp ) );
		staticDamageOffsets.add( new vector( Math.random()*5*dp  , -5*dp + Math.random()*10*dp ) );
	}

	@Override
	public ArrayList<vector> getDamageOffsets()
	{
		return staticDamageOffsets;
	}

}
