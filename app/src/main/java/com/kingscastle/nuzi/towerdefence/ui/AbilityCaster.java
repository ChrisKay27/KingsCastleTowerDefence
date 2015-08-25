package com.kingscastle.nuzi.towerdefence.ui;


import com.kingscastle.nuzi.towerdefence.framework.Input;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.abilities.Ability;
import com.kingscastle.nuzi.towerdefence.teams.Teams;

public class AbilityCaster
{
	//private static final String TAG = "UnitOrders";

	private static final AbilityCaster abilityCaster = new AbilityCaster();

	private Ability pendingAbility;


	private AbilityCaster()
	{
	}


	public static AbilityCaster getInstance()
	{
		return abilityCaster;
	}







	public boolean giveOrders( Input.TouchEvent event )
	{
		if( pendingAbility == null ){
			return false;
		}

		try
		{
			if ( pendingAbility.analyseTouchEvent( event ) )
			{
				pendingAbility = null;
				return true;
			}
		}
		catch( Exception e )
		{
			pendingAbility = null;
		}
		return false;
	}



	public void setPendingAbility( Ability ab )
	{
		pendingAbility = ab;
		ab.setTeam( Teams.BLUE );
	}








	public boolean isThereAPendingOrder(){
		return pendingAbility != null;
	}


	public void cancel(){
		pendingAbility = null;
	}


	public Ability getPendingAbility(){
		return pendingAbility;
	}


	public void clearOrder(){
		pendingAbility = null;
	}




}
