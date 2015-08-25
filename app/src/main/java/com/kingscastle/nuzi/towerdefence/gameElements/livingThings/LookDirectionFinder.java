package com.kingscastle.nuzi.towerdefence.gameElements.livingThings;


import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

public class LookDirectionFinder
{

	public static Rpg.Direction getDir( vector from , vector towards )
	{		
		return vector.getDirection4(new vector(from, towards).turnIntoUnitVector());
	}

}
