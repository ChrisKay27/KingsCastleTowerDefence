package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings;


import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;

import java.util.ArrayList;
import java.util.Collections;

public class BuildableUnits
{

	private ArrayList<LivingThing> buildableUnits;
	
	
	
	public BuildableUnits( LivingThing... units )
	{
		if( units == null || units.length == 0 )
		{
        }
		else
		{		
			buildableUnits = new ArrayList<LivingThing>();
            Collections.addAll(buildableUnits, units);
		}
	}



	public ArrayList<LivingThing> getBuildableUnits() {
		return buildableUnits;
	}



	public void setBuildableUnits(ArrayList<LivingThing> buildableUnits) {
		this.buildableUnits = buildableUnits;
	}
	
	
	

	
}
