package com.kingscastle.nuzi.towerdefence.ui;


import com.kingscastle.nuzi.towerdefence.gameElements.GameElement;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.teams.Teams;

public class EnemyAtLocationChecker
{

	/**
	 * 
	 * @param mapRelCoord
	 * @param teamOfInterest Team to either ignore or look on based on onThatTeam paramater.
	 * @param onThatTeam If true it looks only on that team, used for finding players to heal on your own team. If false only looks at every other team.
	 * @return null if nothing is found.
	 */
	public static LivingThing findEnemyHere( vector mapRelCoord , Teams teamOfInterest , boolean onThatTeam )
	{
		if( true )
			throw new IllegalArgumentException( "FIX THIS, NEED TO GET MM FROM SOMEWHERE" );
		if ( mapRelCoord == null )
			throw new IllegalArgumentException( " mapRelCoord == null  " );
		if ( teamOfInterest == null )
			throw new IllegalArgumentException( " onAllTeamsButThis == null  " );


		GameElement possibleTarget = null;//mm.getCD().checkPlaceableOrTarget( mapRelCoord ) ;

		if( possibleTarget instanceof LivingThing )
		{
			LivingThing lt = (LivingThing) possibleTarget;

			if ( lt.getTeamName() == teamOfInterest )
			{
				if( onThatTeam ) {
					return lt;
				} else {
					return null;
				}
			}
			else
			{
				if( !onThatTeam ) {
					return lt;
				} else {
					return null;
				}
			}
		}
		//
		//		for ( Team team : ManagerManager.getInstance().getTeamManager().getTeams() )
		//		{
		//
		//			if ( team.getTeamName() == teamOfInterest && !onThatTeam )
		//			{
		//				continue;
		//			}
		//			if( onThatTeam && team.getTeamName() != teamOfInterest ) // looks ON onAllTeamsButThis
		//			{
		//				continue;
		//			}
		//
		//			for ( LivingThing lt : team.getAm().getArmy() )
		//			{
		//				if( lt.area.contains( mapRelCoord.getIntX() ,mapRelCoord.getIntY() ))
		//				{
		//					return lt;
		//				}
		//			}
		//
		//			for ( LivingThing b : team.getBm().getBuildings() )
		//			{
		//				if( b.area.contains( mapRelCoord.getIntX() ,mapRelCoord.getIntY() ))
		//				{
		//					return b;
		//				}
		//			}
		//
		//		}



		return null;
	}

}
