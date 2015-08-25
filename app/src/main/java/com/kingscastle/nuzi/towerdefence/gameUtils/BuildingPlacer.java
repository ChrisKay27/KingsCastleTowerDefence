package com.kingscastle.nuzi.towerdefence.gameUtils;

import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.level.GridUtil;


public class BuildingPlacer {

	public static interface Checker{
		boolean check();
	}


	public static boolean findAPlaceForThis( GridUtil gUtil , RectF area , RectF percArea , float gridSize , Checker checker ){

		int xOffs = -5;
		int yOffs = -5;
		int maxXOffs = 5;
		int maxYOffs = 5;
		float startX = area.left;
		float startY = area.top;

		float width  = gridSize;
		float height = width;
		float spacing = gridSize;

		boolean reset = true;

		float percAreaWidthDiv2 = percArea.width()/2;
		float percAreaHeightDiv2 = percArea.height()/2;

		int count2 = 0;
		// find a placeable EffectsManager.Position near current location
		do
		{
			for( int j = yOffs ; j < maxYOffs ; ++j )
			{
				for( int i = xOffs ; i < maxXOffs ; ++i )
				{
					area.offsetTo( startX + i*( spacing ) , startY + j*( spacing ) );
					area.offset( -percAreaWidthDiv2, -percAreaHeightDiv2 );

					gUtil.setProperlyOnGrid( area , gridSize );


					if( checker.check() )
						return true;


					if( count2 > 50 )
					{
						//						if( KingsCastle.testingVersion )
						//						{
						//							DarkStarAnim aza = new DarkStarAnim(new Vector(bLoc) );
						//							aza.setAliveTime( 40000 );
						//							aza.setLooping( true );
						//							MM.get().getEm().add( aza );
						//							////Log.e( TAG , "TimeOutException Did not find placement for a" + b.getName() );
						//						}
						return false;
					}
				}
			}

			xOffs -= 4 ;
			yOffs -= 4 ;
			maxXOffs += 4 ;
			maxYOffs += 4 ;
			++count2;
		}
		while( reset );

		return false;
	}




}
