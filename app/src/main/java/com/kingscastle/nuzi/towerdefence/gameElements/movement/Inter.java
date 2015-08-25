package com.kingscastle.nuzi.towerdefence.gameElements.movement;

import android.graphics.Point;
import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.GameElement;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.ArmyManager.CollisionPartitions;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Building;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.GemManager;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.ListPkg;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameElements.movement.pathing.Grid;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.teams.Team;
import com.kingscastle.nuzi.towerdefence.teams.Teams;

import java.util.ArrayList;
//Log;

public class Inter extends Point
{
	private static final String TAG = "Inter";

	private static Grid grid;
	private static boolean[][] gridTiles;
	private static int gridWidth;
	private static int gridHeight;
	private static float gridSize;
	private static int intGridSize;
	private static float halfGridSize;


	private vector intersection;
	private vector normal;
	private vector force;

	private float distanceFromLineStart;
	private LivingThing hit;

	private LivingThing lt;


	private final float sixTeenDpSquared = Rpg.sixTeenDpSquared;
	private final float twentyDpSquared = Rpg.twentyDpSquared;
	private final float thirtyTwoDpSquared = Rpg.thirtyTwoDpSquared;

	private final float thirtyDp = Rpg.thirtyDp;
	private final float thirtyTwoDp = Rpg.thirtyTwoDp;




	public Inter(){}

	private Inter(vector inter, vector norm, LivingThing hit,
			float distanceFromLineStart)
	{
		normal = norm;
		intersection = inter;
		this.hit = hit;
		this.distanceFromLineStart = distanceFromLineStart;
	}

	public Inter(vector inter, vector norm)
	{
		this(inter, norm, null, -1);
	}

	public Inter( vector force )
	{
		this.setForce(force);
	}

	public Inter( ArrayList<RectF> allIntersections ){}


	public void clear()
	{
		normal = null;
		intersection = null;
		hit = null;
		distanceFromLineStart = 0;
		force = null;
	}


	public boolean checkForCollision2( vector loc , vector normal )
	{
		normal.set( 0 , 0 );

		float x = loc.x;
		float y = loc.y;

		int onTileX = ((int)x)/intGridSize;
		int onTileY = ((int)y)/intGridSize;

		if( onTileX < 0 ){
			loc.x = 5;
			return false;
		}
		if( onTileY < 0 ){
			loc.x = 5;
			return false;
		}
		if( onTileX >= gridWidth ){
			loc.x = Rpg.getMapWidthInPx() - 5;
			return false;
		}
		if( onTileY >= gridHeight ){
			loc.y = Rpg.getMapHeightInPx() - 5;
			return false;
		}


		int tempI;
		int tempJ;

		float remX = x%intGridSize;
		float remY = y%intGridSize;





		if( remX < halfGridSize ) // on left half of current tile
			tempI = onTileX - 1;

		else
			tempI = onTileX + 1;




		if( remY < halfGridSize ) // on top half of current tile
			tempJ = onTileY - 1;

		else
			tempJ = onTileY + 1;



		if( !gridTiles[onTileX][onTileY] )
		{

			if( remX < halfGridSize )
			{
				loc.x -= halfGridSize;
				normal.add( 1 , 0 );
			}
			else
			{
				loc.x += halfGridSize;
				normal.add( -1 , 0 );

			}
			if( remY < halfGridSize )
			{
				loc.y -= halfGridSize;
				normal.add( 0 , 1 );
			}
			else
			{
				loc.y += halfGridSize;
				normal.add( 0 , -1 );
			}
		}
		else
		{
			if( tempI > -1 && tempI < gridWidth && !gridTiles[tempI][onTileY] )

				if( remX < halfGridSize )
					normal.add( -1 , 0 );
				else
					normal.add( 1 , 0 );


			if( tempJ > -1 && tempJ < gridHeight && !gridTiles[onTileX][tempJ] )
				if( remY < halfGridSize )
					normal.add( 0 , -1 );
				else
					normal.add( 0 , 1 );

		}
		return false;
	}



	public boolean checkForCollision( MM mm, Line l , vector obstAvoidanceForce )
	{
		//////////////////
		int gridSize = (int) grid.getGridSize();
		////////Log.d( TAG , "gridSize:" + gridSize );

		boolean[][] gridTiles = grid.getGridTiles();

		float toDotLocDotx = l.start.x;
		float toDotLocDoty = l.start.y;


		int tempI = ((int)toDotLocDotx)/gridSize - 1;
		int tempJ = ((int)toDotLocDoty)/gridSize - 1;


		int startI = tempI < 1 ? 0 : tempI;
		int startJ = tempJ < 1 ? 0 : tempJ;


		tempI = startI;
		tempJ = startJ;

		int end_i = tempI + 3 >= gridTiles.length ? gridTiles.length : tempI + 3;
		int end_j = tempJ + 3 >= gridTiles[0].length ? gridTiles[0].length : tempJ + 3;


		boolean byPassUnmovables = true;

		while( tempI < end_i )
		{
			while( tempJ < end_j )
			{
				byPassUnmovables &= gridTiles[tempI][tempJ];
				++tempJ;
			}
			tempJ = startJ;
			++tempI;
		}

		if( byPassUnmovables )
			return false;

		//////////////////


		vector force = obstAvoidanceForce;


		Building buildingToBuild;
		vector temp = new vector();


		for( Team t : mm.getTM().getTeams() )
		{

			ListPkg<Building> bPkg = t.getBm().getBuildings();

			synchronized( bPkg )
			{
				Building[] buildings = bPkg.list;
				int size2 = bPkg.size;

				for( int i = 0 ; i < size2 ; ++i )
				{
					Building b = buildings[i];

					float geX = b.loc.x;
					float geY = b.loc.y;

					float dx = toDotLocDotx - geX ;
					float dy = toDotLocDoty - geY ;

					float dist = dx*dx + dy*dy;


					if( dist > thirtyTwoDpSquared )
						continue;


					float thingsWidth = b.area.width();

					if( thingsWidth > thirtyTwoDp )
					{
						if( dist > thirtyTwoDpSquared )
							continue;

					}
					else if( thingsWidth < thirtyTwoDp )
					{
						if( dist > sixTeenDpSquared )
							continue;

					}

					if( dist == 0 )
					{
						////Log.d( TAG , "dist == " + dist );
						continue;
					}


					temp.set( dx , dy );
					float r = vector.sqrt(dist);
					temp.normalize();

					////Log.d( TAG , "dx = " + dx );
					////Log.d( TAG , "dy = " + dy );

					if( thingsWidth > thirtyDp )
						r /= 2;


					force.add( temp.x/r , temp.y/r );
				}
			}
		}

		GemManager.GemPackage gemPkg = mm.getGem(toDotLocDotx , toDotLocDoty).getGameElements();

		synchronized( gemPkg )
		{
			GameElement[] gems = gemPkg.gems;
			int gesSize = gemPkg.size;

			for( int i = 0 ; i < gesSize ; ++i )
			{
				GameElement ge = gems[i];

				float geX = ge.loc.x;
				float geY = ge.loc.y;

				float dx = toDotLocDotx - geX ;
				float dy = toDotLocDoty - geY ;

				float dist = dx*dx + dy*dy;

				if( dist > thirtyTwoDpSquared )
				{
					continue;
				}

				float thingsWidth = ge.area.width();

				if( thingsWidth > thirtyTwoDp )
				{
					if( dist > thirtyTwoDpSquared )
					{
						continue;
					}
				}
				else if( thingsWidth < thirtyTwoDp )
				{
					if( dist > sixTeenDpSquared )
					{
						continue;
					}
				}
				if( dist == 0 )
				{
					////Log.d( TAG , "dist == " + dist );
					continue;
				}

				temp.set( dx , dy );
				float r = vector.sqrt(dist);
				temp.normalize();

				////Log.d( TAG , "dx = " + dx );
				////Log.d( TAG , "dy = " + dy );
				force.add( temp.x/r , temp.y/r );
			}
		}


		//		GameElement ge;
		//		ArrayList<GameElement> gems = mm.getGem( toDotLocDotx , toDotLocDoty ).getGameElements();
		//
		//		for ( int i = gems.size() - 1 ; i > -1 ; --i )
		//		{
		//			ge = gems.get( i );
		//
		//			float geX = ge.loc.x;
		//			float geY = ge.loc.y;
		//
		//			float dx = toDotLocDotx - geX ;
		//			float dy = toDotLocDoty - geY ;
		//
		//			float dist = dx*dx + dy*dy;
		//
		//			if( dist > thirtyTwoDpSquared )
		//			{
		//				continue;
		//			}
		//
		//			float thingsWidth = ge.area.width();
		//
		//			if( thingsWidth > thirtyTwoDp )
		//			{
		//				if( dist > thirtyTwoDpSquared )
		//				{
		//					continue;
		//				}
		//			}
		//			else if( thingsWidth < thirtyTwoDp )
		//			{
		//				if( dist > sixTeenDpSquared )
		//				{
		//					continue;
		//				}
		//			}
		//			if( dist == 0 )
		//			{
		//				////Log.d( TAG , "dist == " + dist );
		//				continue;
		//			}
		//
		//			temp.set( dx , dy );
		//			float r = Vector.sqrt( dist );
		//			temp.normalize();
		//
		//			////Log.d( TAG , "dx = " + dx );
		//			////Log.d( TAG , "dy = " + dy );
		//			force.add( temp.x/r , temp.y/r );
		//		}


		return true;

	}

	/**
	 * Use this method to avoid walking on top of each other.
	 * @return The thing you collided with.. only one of them tho...
	 */
	public LivingThing checkForSeparation(MM mm, vector loc, vector force, vector temp, Teams team, LivingThing currTarget)
	{
		ArrayList<Team> teams = mm.getTM().getTeams();

		float x = loc.x;
		float y = loc.y;

		LivingThing target = null;

		for( Team t : teams )
		{
			Teams teamName = t.getTeamName();
			CollisionPartitions cp = t.getAm().getCollisionPartitions();
			if( cp == null ) {
				continue;
			}

			LivingThing[] lts = cp.getPartition(x, y);
			int size = cp.getSizeForPartition(x, y);


			for( int i = 0 ; i < size ; ++i )
			{
				lt = lts[i];

				if ( loc == lt.loc || lt == currTarget )
					continue;


				float dx = x - lt.loc.x ;
				float dy = y - lt.loc.y ;


				float dist = dx*dx + dy*dy;

				if( dist > twentyDpSquared )
					continue;


				if( dist == 0 )
				{
					////Log.d( TAG , "dist == " + dist );
					continue;
				}

				temp.set( dx , dy );
				float r = vector.sqrt(dist);
				temp.normalize();

				////Log.d( TAG , "dx = " + dx );
				////Log.d( TAG , "dy = " + dy );
				force.add( temp.x/r , temp.y/r );

				if( teamName != team )
					target = lt;
			}
		}

		return target;
	}

	//	for ( int i = lts.size() - 1 ; i > -1 ; --i )
	//	{
	//		lt = lts.get( i );
	//
	//		if ( loc == lt.loc || lt.garrisoned )
	//		{
	//			continue;
	//		}
	//
	//		float dx = x - lt.loc.x ;
	//		float dy = y - lt.loc.y ;
	//
	//
	//
	//		float dist = dx*dx + dy*dy;
	//
	//		if( dist > twentyDpSquared )
	//		{
	//			continue;
	//		}
	//
	//		if( dist == 0 )
	//		{
	//			////Log.d( TAG , "dist == " + dist );
	//			continue;
	//		}
	//
	//		temp.set( dx , dy );
	//		float r = Vector.sqrt( dist );
	//		temp.normalize();
	//
	//		////Log.d( TAG , "dx = " + dx );
	//		////Log.d( TAG , "dy = " + dy );
	//		force.add( temp.x/r , temp.y/r );


	@Override
	public String toString() {
		return "Intersection at {" + intersection + " normal is " + normal
				+ "]";
	}

	public static Grid getGrid() {
		return grid;
	}

	public static void setGrid(Grid grid) {
		Inter.grid = grid;
		gridTiles = grid.getGridTiles();
		gridSize = grid.getGridSize();
		intGridSize = (int) gridSize;
		halfGridSize = gridSize/2;
		gridWidth = gridTiles.length;
		gridHeight = gridTiles[0].length;

	}


	public LivingThing getHitLivingThing() {
		return hit;
	}

	public void setHit(LivingThing hit) {
		this.hit = hit;
	}



	public static boolean intersects( RectF r, Line l )
	{

		if( ( l.start.x < r.left && l.end.x < r.left ) ||
				( l.start.x > r.right && l.end.x > r.right ) ||
				( l.start.y < r.top && l.end.y < r.top ) ||
				( l.start.y > r.bottom && l.end.y > r.bottom ) )
		{
			return false;
		}

		boolean lIntersectsR = l.intersects( r );
		////Log.d( TAG , "lIntersectsR=" + lIntersectsR );

		return lIntersectsR;

	}



	public static boolean intersects( RectF r, RectF r2 )
	{
		if ( r.left >= r2.right || r.top >= r2.bottom || r.right <= r2.left
				|| r.bottom <= r2.top )
		{
			return false;
		}
		else
		{
			return RectF.intersects( r, r2 );
		}
	}


	public vector getForce() {
		return force;
	}

	void setForce(vector force) {
		this.force = force;
	}

	public vector getPoint()
	{
		return intersection;
	}

	public void setIntersection(vector intersection) {
		this.intersection = intersection;
	}

	public float getDist() {
		return distanceFromLineStart;
	}

	public vector getNormal() {
		return normal;
	}

	public void setNormal(vector normal) {
		this.normal = normal;
	}
}
