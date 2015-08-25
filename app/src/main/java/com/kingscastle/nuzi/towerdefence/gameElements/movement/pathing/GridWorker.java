package com.kingscastle.nuzi.towerdefence.gameElements.movement.pathing;

import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

public class GridWorker
{

	private static final String TAG = "GridWorker";

	private static final ArrayList<RectF> toBeGriddedQueue = new ArrayList<RectF>();

	private static final ArrayList<RectF> toBeRemovedQueue = new ArrayList<RectF>();



	public synchronized static boolean addToToBeGriddedQueue( RectF area , boolean walkable )
	{
		////Log.d( TAG , "Adding RectF " + area + ", walkable=" + walkable );

		if( walkable )
		{
			synchronized( toBeRemovedQueue )
			{
				toBeRemovedQueue.add(area);
			}
		}
		else
		{
			synchronized( toBeGriddedQueue )
			{
				toBeGriddedQueue.add(area);
			}
		}

		//	//Log.d( TAG , "Added RectF" );
		return true;
	}





	public static boolean workTheQueue( Grid grid )
	{
		////Log.d( TAG , "Working da Queue" );

		synchronized( toBeGriddedQueue )
		{
			workThisQueue( grid , toBeGriddedQueue , false );
			toBeGriddedQueue.clear();
		}

		synchronized( toBeRemovedQueue )
		{
			workThisQueue( grid , toBeRemovedQueue , true );
			toBeRemovedQueue.clear();
		}


		////Log.d( TAG , "Working da Queue finished" );
		return true;
	}



	private static boolean workThisQueue( Grid grid , ArrayList<RectF> areas , boolean walkable )
	{
		if( areas.size() == 0 )
			return false;

		////Log.d( TAG , "workThisQueue areasList size is " + areas.size() + " walkable? " + walkable );
		try
		{
			boolean[][] gridTiles = grid.getGridTiles();
			float gridSize = grid.getGridSize();

			int starti , endi;
			int startj , endj;



			for( RectF area : areas )
			{
				starti = (int) (area.left/gridSize);
				endi = (int) (area.right/gridSize);

				startj = (int) (area.top/gridSize);
				endj = (int) (area.bottom/gridSize);

				////Log.d( TAG , "area:" + area );
				////Log.d( TAG , "starti=" + starti + " endi=" + endi + " startj=" + startj + " endj=" + endj );

				if( starti > -1 && startj > -1 && endi <= gridTiles.length && endj <= gridTiles[0].length )
				{
					for( int i = starti ; i < endi ; ++i )
					{
						for( int j = startj ; j < endj ; ++j )
						{
							gridTiles[i][j] = walkable;
							////Log.d( TAG ,"i,j :" + i + "," + j + " set to " + walkable );
						}
					}
				}
				else
				{
					////Log.d( TAG ,"out of bounds of grid");
				}
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}

		return true;


	}





	public static synchronized void addToGridNow( RectF area , boolean walkable , Grid grid )
	{

		float gridSize = grid.getGridSize();
		boolean[][] tiles = grid.getGridTiles();

		int starti = (int) (area.left  /gridSize);
		int endi   = (int) (area.right /gridSize);

		int startj = (int) (area.top   /gridSize);
		int endj   = (int) (area.bottom/gridSize);

		synchronized (grid) {
			if (starti > -1 && startj > -1 && endi <= tiles.length && endj <= tiles[0].length)
				for (int i = starti; i < endi; ++i)
					for (int j = startj; j < endj; ++j)
						tiles[i][j] = walkable;
		}

		////Log.d( TAG ,"i,j :" + i + "," + j + " set to " + walkable );
	}




	/**
	 * Check if the area is placeable and add if it is.
	 * @param area
	 * @param walkable
	 * @param grid
	 * @return
	 */
	public synchronized static boolean checkAndAddToGrid( RectF area , boolean walkable , Grid grid )
	{
		if( area.width()*area.height() == 0 ){
			Log.e( TAG , "Area of area == 0, skipping adding" );
			return true;
		}


		float gridSize = grid.getGridSize();
		boolean[][] tiles = grid.getGridTiles();

		int starti = (int) (area.left/gridSize);
		int endi = (int) (area.right/gridSize);

		int startj = (int) (area.top/gridSize);
		int endj = (int) (area.bottom/gridSize);

		synchronized (grid) {
			if (starti > -1 && startj > -1 && endi <= tiles.length && endj <= tiles[0].length) {
				for (int i = starti; i < endi; ++i) {
					for (int j = startj; j < endj; ++j) {
						if (!tiles[i][j]) {
							Log.e(TAG, "i,j :" + i + "," + j + " not walkable");
							return false;
						}
						//Log.d( TAG ,"i,j :" + i + "," + j + " set to " + walkable );
					}
				}

				for (int i = starti; i < endi; ++i) {
					for (int j = startj; j < endj; ++j) {
						tiles[i][j] = walkable;
						//Log.d( TAG ,"i,j :" + i + "," + j + " set to " + walkable );
					}
				}
			}
		}
		return true;
	}

















}
