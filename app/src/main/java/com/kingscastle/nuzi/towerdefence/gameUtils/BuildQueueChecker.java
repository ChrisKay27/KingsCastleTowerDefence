package com.kingscastle.nuzi.towerdefence.gameUtils;

import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Building;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.teams.Team;
import com.kingscastle.nuzi.towerdefence.teams.Teams;
import com.kingscastle.nuzi.towerdefence.ui.UI;

import java.util.ArrayList;



public class BuildQueueChecker
{

	/**
	 * 
	 * @param buildQueue
	 * @param building
	 * @param mm
	 * @return
	 */
	public static boolean checkBuildQueue( BuildQueue buildQueue , Building building , MM mm )
	{
		ArrayList<LivingThing> lts=null;
		while( buildQueue.isThereACompletedQueueable() ){
			Queueable builtOrResearched = buildQueue.getCompletedQueueable();

			if( builtOrResearched != null)
			{
				if( builtOrResearched instanceof LivingThing)
				{
					LivingThing unit = (LivingThing) builtOrResearched;


					Team t = mm.getTeam( unit.getTeamName() );
					if( t != null )
						t.onUnitCreated( unit );

					if( lts == null )
						lts = new ArrayList<LivingThing>();
					lts.add( unit );




						//						Path path = building.getPathToDeployLocation(mm.getLevel().getGrid());
						//						if( path != null )
						//							unit.setPathToFollow( new Path(path) );
						//
						//						else
						//							unit.setDestination( building.getDeployLoc() );




				}


				//				boolean anotherInLine = buildQueue.startBuildingFirst();
				//				if( !anotherInLine )
				//				{
				//					if( building.getTeamName() == Teams.BLUE )
				//					{
				//						building.getBuildingAnim().remove( buildQueue.getProgressBar() );
				//						buildQueue.getProgressBar().setShowCurrentAndMax( false );
				//					}
				//				}

			}
		}


		return buildQueue.currentlyBuilding != null;
	}



	public static boolean addToBuildQueue( BuildQueue buildQueue , Queueable toBeBuild , Building building )
	{
		if ( toBeBuild == null )
			throw new IllegalArgumentException(" toBeBuild == null " );

		buildQueue.add( toBeBuild );

		if( buildQueue.startBuildingFirst() )
		{
			if( building.getTeamName() == Teams.BLUE )
			{
				building.getBuildingAnim().add( buildQueue.getProgressBar() , true );

				UI.get().refreshSelectedUI();
				//				if ( building.isSelected() ){
				//					QueueDisplay.displayQueue( Rpg.getGame() , buildQueue );
				//					RushBuilding.showFinishNowButtonIfNeededTSafe(building);
				//				}

				return true;
			}
			return true;
		}

		return true;
	}




}
