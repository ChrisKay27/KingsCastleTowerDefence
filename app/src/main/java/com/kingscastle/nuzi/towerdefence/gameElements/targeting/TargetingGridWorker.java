package com.kingscastle.nuzi.towerdefence.gameElements.targeting;

import android.graphics.RectF;
import android.util.Log;

import com.kingscastle.nuzi.towerdefence.gameElements.GameElement;
import com.kingscastle.nuzi.towerdefence.gameElements.movement.pathing.Grid;

import java.util.ArrayList;

/**
 * I don't know what this class is for. Don't use it
 */
public class TargetingGridWorker
{

	private static final String TAG = "GridWorker";

	private static final ArrayList<GameElement> toBeGriddedQueue = new ArrayList<GameElement>();

	private static final ArrayList<GameElement> toBeRemovedQueue = new ArrayList<GameElement>();



	public synchronized static boolean addToToBeGriddedQueue( GameElement ge , boolean walkable )
	{
		////Log.d( TAG , "Adding RectF " + area + ", walkable=" + walkable );

		if( walkable )
		{
			synchronized( toBeRemovedQueue )
			{
				toBeRemovedQueue.add(ge);
			}
		}
		else
		{
			synchronized( toBeGriddedQueue )
			{
				toBeGriddedQueue.add(ge);
			}
		}

		//	//Log.d( TAG , "Added RectF" );
		return true;
	}





	public static boolean workTheQueue( TargetingGrid grid )
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



	private static boolean workThisQueue( TargetingGrid grid , ArrayList<GameElement> ges , boolean adding )
	{
		if( ges.size() == 0 )
			return false;

		//Log.d( TAG , "workThisQueue areasList size is " + areas.size() + " walkable? " + walkable );
		try
		{
			GameElement[][] gridTiles = grid.getGridTiles();
			float gridSize = grid.getGridSize();

			int starti , endi;
			int startj , endj;



			for( GameElement ge : ges )
			{
				RectF area = ge.area;
				starti = (int) (area.left/gridSize);
				endi = (int) (area.right/gridSize);

				startj = (int) (area.top/gridSize);
				endj = (int) (area.bottom/gridSize);

				//Log.d( TAG , "area:" + area );
				//Log.d( TAG , "starti=" + starti + " endi=" + endi + " startj=" + startj + " endj=" + endj );

				if( starti > -1 && startj > -1 && endi <= gridTiles.length && endj <= gridTiles[0].length )
				{
					for( int i = starti ; i < endi ; ++i )
					{
						for( int j = startj ; j < endj ; ++j )
						{
							if( adding ){
								if( gridTiles[i][j] != null ){
									Log.e( TAG ,"i,j :" + i + "," + j + " contains " + gridTiles[i][j] + " and trying to set this tile to hold a " + ge );
								}
								gridTiles[i][j] = ge;
							}
							else{
								gridTiles[i][j] = null;
							}

							//Log.d( TAG ,"i,j :" + i + "," + j + " set to " + walkable );
						}
					}
				}
				else
				{
					//Log.d( TAG ,"out of bounds of grid");
				}
			}
		}
		catch( Exception e ){
			e.printStackTrace();
		}

		return true;
	}





	public static synchronized void addToGridNow( GameElement ge , boolean adding , TargetingGrid grid )
	{
		RectF area = ge.area;
		float gridSize = grid.getGridSize();
		GameElement[][] tiles = grid.getGridTiles();

		int starti = (int) (area.left  /gridSize);
		int endi   = (int) (area.right /gridSize);

		int startj = (int) (area.top   /gridSize);
		int endj   = (int) (area.bottom/gridSize);


		if( starti > -1 && startj > -1 && endi <= tiles.length && endj <= tiles[0].length )
			for( int i = starti ; i < endi ; ++i )
				for( int j = startj ; j < endj ; ++j ){
					if( adding ){
						if( tiles[i][j] != null ){
							Log.e( TAG ,"i,j :" + i + "," + j + " contains " + tiles[i][j] + " and trying to set this tile to hold a " + ge );
						}
						tiles[i][j] = ge;
					}
					else{
						tiles[i][j] = null;
					}
				}


		//Log.d( TAG ,"i,j :" + i + "," + j + " set to " + walkable );
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
			Log.v( TAG , "Area of area == 0, skipping adding" );
			return true;
		}


		float gridSize = grid.getGridSize();
		boolean[][] tiles = grid.getGridTiles();

		int starti = (int) (area.left/gridSize);
		int endi = (int) (area.right/gridSize);

		int startj = (int) (area.top/gridSize);
		int endj = (int) (area.bottom/gridSize);

		if( starti > -1 && startj > -1 && endi <= tiles.length && endj <= tiles[0].length )
		{
			for( int i = starti ; i < endi ; ++i )
			{
				for( int j = startj ; j < endj ; ++j )
				{
					if( !tiles[i][j] )
						return false;
					////Log.d( TAG ,"i,j :" + i + "," + j + " set to " + walkable );
				}
			}

			for( int i = starti ; i < endi ; ++i )
			{
				for( int j = startj ; j < endj ; ++j )
				{
					tiles[i][j] = walkable;
					////Log.d( TAG ,"i,j :" + i + "," + j + " set to " + walkable );
				}
			}
		}

		return true;
	}

















}
