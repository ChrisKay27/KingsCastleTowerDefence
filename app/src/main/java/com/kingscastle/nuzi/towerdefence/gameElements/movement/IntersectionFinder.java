package com.kingscastle.nuzi.towerdefence.gameElements.movement;


import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

class IntersectionFinder
{
	private final vector unitVectorinDirTemp = new vector();
	private final vector unitVectorsNormal = new vector();
	private final vector unitVectorsNormalLarge = new vector();

	private final Line whisker = new Line();
	private final Inter inter = new Inter();

	private static final float whiskerLength = Rpg.twentyDp + Rpg.fiveDp;
	private static final float unitsNormalsLength = Rpg.sixTeenDp;

	public Inter checkForIntersection( vector loc , vector unitVectorInDir , LivingThing driver )
	{
		//		if( driver.getTeamName() == Teams.BLUE )
		//		{
		//			//Log.d ( driver.toString() , "loc = " + loc + " unitVectorInDir = " + unitVectorInDir );
		//		}
		unitVectorinDirTemp.set( unitVectorInDir );
		unitVectorsNormal.set( unitVectorInDir );
		unitVectorsNormal.rotate( 90 );
		unitVectorsNormalLarge.set( unitVectorInDir );
		unitVectorsNormalLarge.rotate( 90 );
		unitVectorsNormalLarge.times( unitsNormalsLength );

		unitVectorinDirTemp.times( whiskerLength );
		unitVectorinDirTemp.add( loc );
		whisker.set( loc , unitVectorinDirTemp );

		//		if( driver.getTeamName() == Teams.BLUE )
		//		{
		//			//Log.d ( driver.toString() , "unitVectorsNormal = " + unitVectorsNormal );
		//		}

		inter.clear();
		return null;
		//		if( inter.checkForCollision( whisker , driver , unitVectorsNormalLarge , unitVectorsNormal ) )
		//		{
		//			return inter;
		//		}
		//		else
		//		{
		//			return null;
		//		}

	}


}
