package com.kingscastle.nuzi.towerdefence.gameElements.livingThings;


import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.CD;
import com.kingscastle.nuzi.towerdefence.gameElements.GameElement;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

public class WalkToLocationFinder
{


	public static vector walkTo( vector here , CD cd )
	{

		GameElement ge = cd.checkPlaceableOrTarget( here );

		if( ge == null )
			return here;


		vector newLoc = new vector();

		int mapWidthInPx = Rpg.getMapWidthInPx();
		int mapHeightInPx = Rpg.getMapHeightInPx();


		int Offs = 1;

		float dx = 21*Rpg.getDp();
		float dy = dx;

		newLoc.set( here );


		int count = 0;
		while( count++ < 8 );
		{
			for( int i = 0 ; i < Offs ; ++i )
			{
				newLoc.x += dx;
				if( newLoc.x < 0 || newLoc.x >= mapWidthInPx )
					return null;
				if( cd.checkPlaceable( newLoc ) )
					return newLoc;
			}
			for( int j = 0 ; j < Offs ; ++j )
			{
				newLoc.y += dy;
				if( newLoc.y < 0 || newLoc.y >= mapHeightInPx )
					return null;
				if( cd.checkPlaceable( newLoc ) )
					return newLoc;
			}

			++Offs;
			dx *= -1;
			dy *= -1;
		}
		return null;
	}


	//	float fiveTimesDp = Rpg.fiveDp;
	//
	//	int count = 0;
	//	do
	//	{
	//
	//
	//		for( int j = yOffs ; j < maxYOffs ; ++j )
	//		{
	//			for( int i = xOffs ; i < maxXOffs ; ++i )
	//			{
	//				newLoc.set( startX + i*( width + fiveTimesDp ) , startY + j*( height + fiveTimesDp ) );
	//
	//				if( CollisionDetector.getInstance().checkPlaceable( newLoc ) )
	//					return newLoc;
	//
	//
	//
	//			}
	//		}
	//
	//		--xOffs;
	//		--yOffs;
	//		++maxXOffs;
	//		++maxYOffs;
	//		++count;
	//	}
	//	while( count < 8 );
}
