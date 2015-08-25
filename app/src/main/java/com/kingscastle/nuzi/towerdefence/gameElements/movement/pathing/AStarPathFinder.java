package com.kingscastle.nuzi.towerdefence.gameElements.movement.pathing;


import com.kingscastle.nuzi.towerdefence.TowerDefenceGame;
import com.kingscastle.nuzi.towerdefence.effects.animations.DarkStarAnim;
import com.kingscastle.nuzi.towerdefence.effects.animations.SmokeAnim;

import com.kingscastle.nuzi.towerdefence.framework.Pool;
import com.kingscastle.nuzi.towerdefence.framework.Pool.PoolObjectFactory;
import com.kingscastle.nuzi.towerdefence.framework.Settings;
import com.kingscastle.nuzi.towerdefence.gameUtils.BlockingQueue;
import com.kingscastle.nuzi.towerdefence.gameUtils.TimeOutException;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;

public class AStarPathFinder
{


	private static final String TAG = "AStarPathFinder";

	public static boolean stopSearch = false;

	private static BlockingQueue<Tile[][]> grids;
	private static BlockingQueue<Tile[]> openAndClosedLists;
	private static BlockingQueue<Tile[]> cleaningLists;

	private static final int numberOfTilesAllowedToSearch = 2500;


	public static void initializeGrids( int width , int height )
	{
		////Log.d( TAG , "before creating Tile[][]s");


		grids = new BlockingQueue<Tile[][]>(10);
		for( int i = 0 ; i < 10 ; ++i )
			grids.addLast( new Tile[width][height] );

		////Log.d( TAG , "done creating Tile[][]s");
		////Log.d( TAG , "grids.size()" + grids.size() );



		openAndClosedLists = new BlockingQueue<Tile[]>(10);
		for( int i = 0 ; i < 10 ; ++i )
			openAndClosedLists.addLast( new Tile[numberOfTilesAllowedToSearch] );




		cleaningLists = new BlockingQueue<Tile[]>(5);
		for( int i = 0 ; i < 5 ; ++i )
			cleaningLists.addLast( new Tile[numberOfTilesAllowedToSearch*2] );

		stopSearch = false;
	}


	public static Path findMeAPath( Grid grid , vector fromHere , vector toHere , long timeOutAt ) throws TimeOutException
	{
		return findMeAPath( grid , fromHere , toHere , timeOutAt , numberOfTilesAllowedToSearch );
	}

	public static Path findMeAPath( Grid grid , vector fromHere , vector toHere , long timeOutAt , int numberOfTilesAllowedToSearchThrough ) throws TimeOutException
	{


		boolean[][] tiles = grid.getGridTiles();
		Path path;

		//////Log.d( TAG , "before getting first Tile[][]");
		Tile[][] closedListLookedAtAlready = grids.removeFirst();//new Tile[tiles.length][tiles[0].length];
		Tile[][] openListLookedAtAlready   = grids.removeFirst();//new Tile[tiles.length][tiles[0].length];
		//////Log.d( TAG , "after getting Tile[][]s");

		float tileSize = grid.getGridSize();
		float halfTileSize = tileSize/2;

		Tile[] openList     = openAndClosedLists.removeFirst(); //new Tile[2500];
		Tile[] closedList   = openAndClosedLists.removeFirst(); //new Tile[2500];
		Tile[] cleaningList = cleaningLists.removeFirst(); //new Tile[5000];
		int cleaningListSize = 0;
		int openListSize = 0;
		int closedListSize = 0;
		int currLosestIndex = 0;

		//act tile calcs
		int i = (int) (fromHere.x/tileSize);
		int j = (int) (fromHere.y/tileSize);

		Tile startTile = new Tile(); //tilePool.newObject();

		startTile.i = i;
		startTile.j = j;


		//end tile calcs
		int endi = (int) (toHere.x/tileSize);
		int endj = (int) (toHere.y/tileSize);

		Tile endTile = new Tile(); //tilePool.newObject();

		endTile.i = endi;
		endTile.j = endj;


		if( Settings.showPathFinding )
		{
			vector v = createVectorFromTile( startTile , tileSize , halfTileSize );
			DarkStarAnim dsa = new DarkStarAnim( v );
			dsa.setAliveTime( 4000 );
			dsa.setLooping( true );
			//mm.getEm().add( dsa );


			v = createVectorFromTile( endTile , tileSize , halfTileSize );
			dsa = new DarkStarAnim( v );
			dsa.setAliveTime( 4000 );
			dsa.setLooping( true );
			//mm.getEm().add( dsa );
		}

		ensureTileIsReachable( tiles , endTile , startTile );


		endi = endTile.i;
		endj = endTile.j;


		ensureTileIsReachable( tiles , startTile , endTile );

		i = startTile.i;
		j = startTile.j;

		if( Settings.showPathFinding )
		{
			vector v = createVectorFromTile( startTile , tileSize , halfTileSize );
			SmokeAnim smk = new SmokeAnim( v );
			smk.setAliveTime( 5000 );
			smk.setLooping( true );
			//mm.getEm().add( smk );

			v = createVectorFromTile( endTile , tileSize , halfTileSize );
			smk = new SmokeAnim( v );
			smk.setAliveTime( 5000 );
			smk.setLooping( true );
			//mm.getEm().add( smk );
		}



		try
		{
			boolean stopSearch = false;
			Tile currTile = startTile;


			Tile[] adjTiles;


			openList[openListSize++] = currTile;
			openListLookedAtAlready[currTile.i][currTile.j] = currTile;

			cleaningList[cleaningListSize++] = currTile;
			synchronized (grid) {
				while (!(currTile.i == endi && currTile.j == endj)) {
					////Log.d( TAG , "Cleaning list size pre check:" + cleaningListSize );

					////Log.d( TAG , "stopSearch? " + stopSearch );
					////Log.d( TAG , "Cleaning list size post check:" + cleaningListSize );

					if (cleaningListSize % 128 == 0)
						stopSearch = !tiles[endi][endj];
					stopSearch |= timeOutAt < System.currentTimeMillis();
					stopSearch |= closedListSize >= numberOfTilesAllowedToSearchThrough;
					stopSearch |= AStarPathFinder.stopSearch;
					if (stopSearch)
						return null;


					adjTiles = new Tile[8];

					int adjTilesSize = getAdjacentTiles(tiles, currTile, adjTiles, openList, openListSize, closedListLookedAtAlready);
					//////Log.d( TAG , "adjTiles(" + adjTiles.size() + ")" + adjTiles );

					calculateFGHValuesAndSetParentIfNeeded(adjTiles, adjTilesSize, openListLookedAtAlready, closedListLookedAtAlready, currTile, endTile);
					//////Log.d( TAG , "after calcing fgh values adjTiles(" + adjTiles.size() + ")" + adjTiles );

					openListSize = addAllNewToOpenList(openList, openListSize, openListLookedAtAlready, adjTiles);


					for (Tile t : adjTiles)
						if (t != null)
							cleaningList[cleaningListSize++] = t;


					////Log.d( TAG , "openList(" + openList.size() + ")" + openList );


					//
					for (int index = currLosestIndex; index < openListSize; ++index)
						openList[index] = openList[index + 1];

					--openListSize;
					//


					int curI = currTile.i;
					int curJ = currTile.j;

					closedList[closedListSize++] = currTile;
					closedListLookedAtAlready[curI][curJ] = currTile;
					openListLookedAtAlready[curI][curJ] = null;

					currLosestIndex = getLowestFValue(openList, openListSize);
					currTile = openList[currLosestIndex];


					//////Log.d( TAG , "currTile:" + currTile );
					if (currTile == null) {
						////Log.d( TAG , "No path found!" );
						//tiles[endTile.i][endTile.j] = false;
						//clean( closedListLookedAtAlready , openListLookedAtAlready , cleaningList , cleaningListSize );
						//clean( closedList , closedListSize );
						//clean( openList   , openListSize   );
						return null;
					}


					if (Settings.showPathFinding) {
						//Vector v = createVectorFromTile( currTile , tileSize , halfTileSize );
//					if( mm.getSdac().stillDraw( v ) )
//					{
//						Fire1Anim a = new Fire1Anim( v );
//						a.setAliveTime( 5000 );
//						mm.getEm().add( a );
//					}
					}

				}
			}

			////Log.d( TAG , "Found End Tile! - finding path. ");
			////Log.d( TAG , "EndTile: " + currTile );

			ArrayList<Tile> pathToStart = new ArrayList<Tile>();

			tracePathBackToStart( closedList , startTile , currTile , pathToStart );


			ArrayList<vector> vectorPathToStart = new ArrayList<vector>();

			convertToVectorPath( pathToStart , vectorPathToStart , tileSize , halfTileSize );

			path = new Path( vectorPathToStart );

			//freeAll( closedList );
			//freeAll( openList );
		}
		catch( ArrayIndexOutOfBoundsException e )
		{
			//tiles[endTile.i][endTile.j] = false;

			if( TowerDefenceGame.testingVersion ){
				e.printStackTrace();

				DarkStarAnim dsa = new DarkStarAnim( createVectorFromTile( endTile , tileSize , halfTileSize ) );
				dsa.setAliveTime( 40000 );
				dsa.setLooping( true );
				//mm.getEm().add( dsa );

				SmokeAnim smk = new SmokeAnim( new vector( i*tileSize + halfTileSize , j*tileSize + halfTileSize ) );
				smk.setAliveTime( 40000 );
				smk.setLooping( true );
				//mm.getEm().add( smk );
				//Log.d( TAG , "Could not find path from " + i + ","+ j + " to " + endTile.i + "," + endTile.j );
			}
			return null;
		}
		finally
		{
			clean( closedListLookedAtAlready , openListLookedAtAlready , cleaningList , cleaningListSize );
			clean( closedList , closedListSize );
			clean( openList   , openListSize   );
		}
		return path;

	}









	private static void clean(Tile[] list, int listSize)
	{
		listSize = listSize >= list.length ? list.length : listSize;
		for( int i = 0 ; i < listSize ; ++i )
			list[i] = null;

		openAndClosedLists.addLast( list );
	}




	private static void clean( Tile[][] tiles , Tile[][] tiles2 , Tile[] cleaningList , int cleaningListSize )
	{
		////Log.d( TAG , "clean() , tiles:" + tiles + ", tiles2:" + tiles2 + ", cleaningList:" + cleaningList + ", cleaningListSize:" + cleaningListSize);

		for( int i = 0 ; i < cleaningListSize ; ++i )
		{
			Tile t = cleaningList[i];
			////Log.d( TAG , "cleaningList[i] = " + t );
			tiles[t.i][t.j] = null;
			tiles2[t.i][t.j] = null;
			cleaningList[i] = null;
		}
		grids.addLast( tiles );
		grids.addLast( tiles2 );
		cleaningLists.addLast( cleaningList );
	}




	private static void ensureTileIsReachable( boolean[][] tiles , Tile tile, Tile fromThisTile )
	{


		if( tiles[tile.i][tile.j] )
		{
			//////Log.d( TAG , "tile Walkable ");
			return;
		}
		else
		{
			short minXOffs = -1;
			short minYOffs = -1;
			int maxXOffs = 2;
			int maxYOffs = 2;

			boolean reset = true;

			do
			{
				for( short xOffs = minXOffs ; xOffs < maxXOffs ; ++xOffs )
				{
					int i = xOffs + tile.i;

					for( short yOffs = minYOffs ; yOffs < maxYOffs ; ++yOffs )
					{
						int j = yOffs + tile.j;

						if( i < 0 || j < 0 || i >= tiles.length || j >= tiles[0].length )
							continue;

						else if( xOffs == 0 && yOffs == 0 )
							continue;

						else if( tiles[i][j] )
						{
							//////Log.d( TAG , "Adjusted tile to " + i + "," + j) ;
							tile.i = i;
							tile.j = j;
							return;
						}
					}
				}

				--minXOffs;
				--minYOffs;
				++maxXOffs;
				++maxYOffs;

			}
			while( reset );

		}




	}



	private static int getAdjacentTiles( boolean[][] tiles , Tile currTile , Tile[] adjTiles , Tile[] openList , int openListSize , Tile[][] closedListLookedAtAlready )
	{
		int lookAtDiagonalsCount = 0;
		int adjTileSize = 0;
		Tile tile;
		{
			int iTemp = currTile.i;
			int jTemp = currTile.j-1;

			if( jTemp > -1 && tiles[iTemp][jTemp] )
			{
				tile = new Tile(); //tilePool.newObject();
				tile.i = iTemp;
				tile.j = jTemp;

				adjTiles[adjTileSize++] = tile ;
				++lookAtDiagonalsCount;
			}
			jTemp += 2;
			if( jTemp < tiles[0].length && tiles[iTemp][jTemp] )
			{
				tile = new Tile(); //tilePool.newObject();
				tile.i = iTemp;
				tile.j = jTemp;
				adjTiles[adjTileSize++] = tile ;
				++lookAtDiagonalsCount;
			}

			jTemp -= 1;
			iTemp -= 1;

			if( iTemp > -1 && tiles[iTemp][jTemp] )
			{
				tile = new Tile(); //tilePool.newObject();
				tile.i = iTemp;
				tile.j = jTemp;
				adjTiles[adjTileSize++] = tile ;
				++lookAtDiagonalsCount;
			}
			iTemp += 2;
			if( iTemp < tiles.length && tiles[iTemp][jTemp] )
			{
				tile = new Tile(); //tilePool.newObject();
				tile.i = iTemp;
				tile.j = jTemp;
				adjTiles[adjTileSize++] = tile ;
				++lookAtDiagonalsCount;
			}
		}

		if( lookAtDiagonalsCount == 4 )
		{
			for( short xOffs = -1 ; xOffs < 2 ; xOffs += 2 )
			{

				int i = xOffs + currTile.i;

				for( short yOffs = -1 ; yOffs < 2 ; yOffs += 2 )
				{

					int j = yOffs + currTile.j;

					if( i < 0 || j < 0 || i >= tiles.length || j >= tiles[0].length )
					{
						continue;
					}
					else if( xOffs == 0 && yOffs == 0 )
					{
						continue;
					}
					else if( !tiles[i][j] )
					{
						continue;
					}
					else if( closedListLookedAtAlready[i][j] != null )
					{
						continue;
					}
					else //if( tileInThisListAlready( adjTiles , i-1 , j ) || tileInThisListAlready( openList , i-1 , j ) || tileInThisListAlready( adjTiles , i+1 , j )  || tileInThisListAlready( openList , i+1 , j ) ||
						//	tileInThisListAlready( adjTiles , i , j-1 ) || tileInThisListAlready( openList , i , j-1 ) || tileInThisListAlready( adjTiles , i , j+1 )  || tileInThisListAlready( openList , i , j+1 ) )
					{
						tile = new Tile(); //tilePool.newObject();
						tile.i = i;
						tile.j = j;
						adjTiles[adjTileSize++] = tile ;
					}
				}
			}
		}

		return adjTileSize;
	}








	private static boolean tileInThisListAlready( Tile[] closedList , int closedListSize , int i , int j )
	{
		return getThisTileFromThisList( closedList , closedListSize , i , j ) != null ;
	}



	private static Tile getThisTileFromThisList( Tile[] list , int size , int i , int j )
	{
		for( int index = 0 ; index < size ; ++index )
		{
			Tile t = list[index];
			if( t.i == i && t.j == j )
			{
				return t;
			}
		}

		return null;
	}








	private static void calculateFGHValuesAndSetParentIfNeeded( Tile[] adjTiles , int adjTilesSize , Tile[][] openList , Tile[][] closedList , Tile currTile , Tile endTile )
	{
		int g;
		Tile t;

		for( int i = 0 ; i < adjTilesSize ; ++i )
		{
			t = adjTiles[i];

			Tile existingTile = closedList[t.i][t.j];
			if( existingTile != null )
			{
				adjTiles[i] = null;
				continue;
			}

			if( currTile.i == t.i || currTile.j == t.j )
			{
				g = 10 + currTile.g;
			}
			else
			{
				g = 14 + currTile.g;
			}

			existingTile = openList[t.i][t.j];
			if( existingTile != null )
			{
				if( g < existingTile.g )
				{
					existingTile.parent = currTile;
					existingTile.g = g;
					existingTile.f = existingTile.g + existingTile.h;
				}

				adjTiles[i] = null;

			}
			else
			{
				t.parent = currTile;
				t.g = g;
				t.h = calcH( t , endTile );
				t.f = t.g + t.h;
			}
		}

	}



	private static int addAllNewToOpenList( Tile[] openList , int openListSize , Tile[][] openListLookedAtAlready , Tile[] adjTiles )
	{
		int length = adjTiles.length;
		for( int i = 0 ; i < length ; ++i )
		{
			Tile t = adjTiles[i];
			if( t != null )
			{
				openList[openListSize++] = t;
				openListLookedAtAlready[t.i][t.j] = t;
			}
		}

		return openListSize;
	}









	private static int calcH( Tile t , Tile endTile )
	{

		int dx = endTile.i - t.i;
		int dy = endTile.j - t.j;



		return Math.abs( dx )*10 + Math.abs( dy )*10;
	}








	private static int getLowestFValue( Tile[] list , int size )
	{
		int lowestF = Integer.MAX_VALUE;
		int lowestIndex = 0;

		for( int i = 0 ; i < size ; ++i )
		{
			int f = list[i].f;
			if( f < lowestF )
			{
				lowestIndex = i;
				lowestF = f;
			}
		}

		return lowestIndex;
	}










	private static void tracePathBackToStart( Tile[] closedList , Tile startTile , Tile endTile , ArrayList<Tile> pathToStart )
	{
		pathToStart.add( endTile );

		Tile currTile = endTile;


		while( !currTile.equals( startTile ) )
		{
			currTile = currTile.parent;
			pathToStart.add( currTile );
		}
	}








	private static void convertToVectorPath( ArrayList<Tile> pathToStart , ArrayList<vector> vectorPathToStart, float tileSize , float halfTileSize )
	{
		Tile t;

		for( int i = pathToStart.size()-1 ; i > -1  ; --i )
		{
			t = pathToStart.get( i );

			vectorPathToStart.add( createVectorFromTile( t , tileSize , halfTileSize ) );
		}
	}




	private static vector createVectorFromTile( Tile t , float tileSize , float halfTileSize )
	{
		return new vector( t.i*tileSize + halfTileSize , t.j*tileSize + halfTileSize );
	}















	private static void freeAll(Tile[] list)
	{

		for( Tile t : list )
		{
			tilePool.free( t );
		}

	}



	private static final Pool<Tile> tilePool;

	static
	{
		PoolObjectFactory<Tile> factory = new PoolObjectFactory<Tile>() {
			@Override
			public Tile createObject() {
				return new Tile();
			}
		};
		tilePool = new Pool<Tile>(factory, 10000);
	}



	private static class Tile
	{
		Tile parent;

		int i;
		int j;
		int f;
		int g;
		int h;



		public boolean equals( Tile t )
		{
			return t.i == i && t.j == j ;
		}

		@Override
		public String toString()
		{
			return "Tile[" + i + "," + j + "]  [f,g,h]:[" + f + "," + g + "," + h + "]";
		}

	}




}
