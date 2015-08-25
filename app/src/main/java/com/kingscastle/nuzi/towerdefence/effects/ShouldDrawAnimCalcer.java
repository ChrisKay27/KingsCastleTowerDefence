package com.kingscastle.nuzi.towerdefence.effects;

import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.effects.animations.Anim;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.framework.Settings;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.Humanoid;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Building;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.ListPkg;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.level.Level;
import com.kingscastle.nuzi.towerdefence.teams.Team;
import com.kingscastle.nuzi.towerdefence.teams.TeamManager;
import com.kingscastle.nuzi.towerdefence.teams.Teams;

import java.util.List;

public class ShouldDrawAnimCalcer
{
	private static final float drawRangeExtender = Rpg.getDp() * 100;

	private final RectF screenArea;
	private final Level level;
	private final TeamManager tm;


	public ShouldDrawAnimCalcer(Level level ,RectF screenArea, TeamManager tm ) {
		this.level = level;
		this.screenArea = screenArea;
		this.tm = tm;
	}





	public boolean shouldThisAnimBeDrawn( Anim a )
	{
		//boolean onAVisibleTile = true;

//		if( Settings.showFogOfWar ){
//			onAVisibleTile = isThisOnAVisibleTile( a.loc );
//
//			if( a instanceof BuildingAnim)
//				a.shouldBeDrawnThroughFog |= onAVisibleTile;
//		}

		return a.visible && stillDraw( a.loc );
//				&& ( a.ownedByHumanPlayer || onAVisibleTile || !Settings.showFogOfWar || Settings.mapEditingMode ||
//						( a.shouldBeDrawnThroughFog && isThisOnAVisibleOrFogTile( a.loc )) );
	}




	public boolean shouldThisRtBeDrawn( RisingText rt )
	{
//		boolean onAVisibleTile = true;//isThisOnAVisibleTile( rt.loc );
//		return ( onAVisibleTile || !Settings.showFogOfWar || Settings.mapEditingMode ) && rt.isVisible() && stillDraw( rt.loc );

		return rt.isVisible() && stillDraw( rt.loc );

	}




	public boolean stillDraw( vector bLoc )	{
		return screenArea.contains( bLoc.x , bLoc.y );
	}




	public boolean isThisOnAVisibleTile( vector loc )	{
		return true;
	}



	public boolean isThisOnAVisibleOrFogTile( vector loc )
	{
		return true;
	}




	public boolean isWithinRangeOfSightOfHumanPlayer( vector loc )
	{
		if( Settings.showFogOfWar == false )
			return true;


		Team humanTeam = tm.getTeam(Teams.BLUE);

		if( loc == null )
		{
			//Log.d( TAG , " humanTeam == " + humanTeam +  "loc == " + loc );
			return true;
		}


		Humanoid lt ;
		List<Humanoid> soldiers = humanTeam.getAm().getArmy();
		vector tempLoc;

		for( int i = soldiers.size() - 1 ; i > -1 ; --i )
		{
			lt = soldiers.get( i );

			if( lt == null )
				continue;


			tempLoc = lt.loc;

			if( tempLoc == null || lt.lq == null )
			{
				////Log.d( TAG , " lt == null || lt.loc == null " );
				continue;
			}



			if( loc == tempLoc )
			{
				//distanceSquared =  loc.distanceSquared( lt.loc );
				//Log.d( TAG , "  loc == lt.loc distanceSquared is " + distanceSquared );
				return true;
			}



			if( loc.distanceSquared( tempLoc ) < lt.getLQ().getRangeOfSightSquared()  + drawRangeExtender  )
			{
				//Log.d( TAG , "  loc within range of a " + lt );
				return true;
			}
			//Log.d( TAG , loc + " not within range of " + lt + " on team " + lt.getTeamName() + " at loc = " + lt.loc );
		}

		ListPkg<Building> bPkg = humanTeam.getBm().getBuildings();

		synchronized( bPkg )
		{
			Building[] buildings = bPkg.list;
			int size2 = bPkg.size;

			for( int i = 0 ; i < size2 ; ++i )
			{
				Building b = buildings[i];

				if( b == null )
					continue;


				tempLoc = b.loc;

				if( tempLoc == null || b.getLQ() == null )
				{
					//////Log.d( TAG , " b == null || b.loc == null " );
					continue;
				}

				if( loc == tempLoc )
				{
					return true;
				}

				if( loc.distanceSquared( tempLoc ) < b.getLQ().getRangeOfSightSquared() + drawRangeExtender )
				{
					////Log.d( TAG , "  loc within range of a " + lt );
					return true;
				}
				////Log.d( TAG , loc + " not within range of " + lt + " on team " + lt.getTeamName() + " at loc = " + lt.loc );
			}
		}

		return false;
	}






}
