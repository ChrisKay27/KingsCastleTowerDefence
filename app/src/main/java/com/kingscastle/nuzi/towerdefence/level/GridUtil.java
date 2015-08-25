package com.kingscastle.nuzi.towerdefence.level;

import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameElements.movement.pathing.Grid;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.gameUtils.VectorUtil;

import java.util.ArrayList;

public class GridUtil
{
	private static final String TAG = "GridUtil";

	private final MM mm;
	private final Grid grid;
	private final int mapWidth;
	private final int mapHeight;

	public GridUtil(MM mm ,Grid grid, int mapWidth , int mapHeight ){
		this.mm = mm;
		this.grid = grid;
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
	}




	public static void setProperlyOnGrid( vector v , float gridSize )
	{
		v.x = v.x - v.x%gridSize;
		v.y = v.y - v.y%gridSize;
	}



	public void setProperlyOnGrid( RectF rect , float gridSize )
	{
		float left = rect.left;
		float top = rect.top;
		float width = rect.width();
		float height = rect.height();

		left = left < 0 ? 0 : left;
		left = left + width > mapWidth ? mapWidth - width : left;

		top = top < 0 ? 0 : top;
		top = top + height > mapHeight ? mapHeight - height : top;

		left = left - left%gridSize;
		top  = top  - top %gridSize;

		rect.set( left , top , left + width , top + height );
	}




	public static void getLocFromArea( RectF rect , RectF percArea , vector loc )
	{
		loc.set( rect.left - percArea.left , rect.top - percArea.top );
	}



	public boolean isPlaceable( RectF area ) {
		return isPlaceable( area , false );
	}



	private boolean isPlaceable( RectF area, boolean ignoreUnits )
	{
		if( area.left < 0 || area.top < 0 || area.bottom >= mapHeight || area.right >= mapWidth )
			return false;

		return mm.getCD().checkPlaceable2( area , ignoreUnits );
	}





	public ArrayList<vector> getAllWalkableLocNextToThis( RectF area )
	{

		boolean[][] gridTiles = grid.getGridTiles();
		final float gridSize = grid.getGridSize();
		final float halfGridSize = gridSize/2;

		ArrayList<vector> locs = new ArrayList<vector>();

		int leftI = (int) ((area.left - 2)/gridSize);
		int topJ = (int) ((area.top - 2)/gridSize);

		int rightI = (int) ((area.right + 2)/gridSize);
		int bottomJ = (int) ((area.bottom + 2)/gridSize);

		int i_iters = rightI - leftI;
		int j_iters = bottomJ - topJ;


		leftI = leftI < 0 ? 0 : leftI ;
		topJ = topJ < 0 ? 0 : topJ;

		rightI = rightI >= gridTiles.length ? gridTiles.length -1 : rightI ;
		bottomJ = bottomJ >= gridTiles[0].length ? gridTiles[0].length -1 : bottomJ;



		for( int i = 0 ; i < i_iters ; ++i )
		{
			int tempI = leftI + i;
			if( gridTiles[tempI][topJ] )
				locs.add( new vector( tempI*gridSize + halfGridSize, topJ*gridSize + halfGridSize ) );
			if( gridTiles[tempI][bottomJ] )
				locs.add( new vector( tempI*gridSize + halfGridSize, bottomJ*gridSize + halfGridSize ) );
		}


		for( int j = 0 ; j < j_iters ; ++j )
		{
			int tempJ = topJ + j;
			if( gridTiles[rightI][tempJ] )
				locs.add( new vector( rightI*gridSize + halfGridSize, tempJ*gridSize + halfGridSize ) );
			if( gridTiles[leftI][tempJ] )
				locs.add( new vector( leftI*gridSize + halfGridSize , tempJ*gridSize + halfGridSize ) );
		}

		return locs;
	}



	public vector getNearestWalkableLocNextToThis( RectF area , vector toThis ){
		ArrayList<vector> locs = getAllWalkableLocNextToThis(area);
		if( locs == null ) return null;

		return VectorUtil.getClosest(locs, toThis);
	}



	public vector getWalkableLocNextToThis( vector comingFrom , RectF area )
	{
		boolean[][] gridTiles = grid.getGridTiles();
		final float gridSize = grid.getGridSize();

		//		int comingFromI = (int)(comingFrom.x/gridSize);
		//		int comingFromJ = (int)(comingFrom.y/gridSize);
		//
		//		int numTilesToSearchX = (int)(area.width()/gridSize) + 1;
		//		int numTilesToSearchY = (int)(area.height()/gridSize) + 1;
		float extraX = gridSize*0.8f;
		float extraY = gridSize*0.8f;

		int leftI = (int) ((area.left - 2)/gridSize);
		int topJ = (int) ((area.top - 2)/gridSize);

		int rightI = (int) ((area.right + 2)/gridSize);
		int bottomJ = (int) ((area.bottom + 2)/gridSize);

		leftI = leftI < 0 ? 0 : leftI ;
		topJ = topJ < 0 ? 0 : topJ;

		rightI = rightI >= gridTiles.length ? gridTiles.length -1 : rightI ;
		bottomJ = bottomJ >= gridTiles[0].length ? gridTiles[0].length -1 : bottomJ;


		int iInc,startI;
		if( comingFrom.x > area.centerX() ){
			startI = rightI;
			iInc = -1;
		}
		else{
			startI = leftI;
			iInc = 1;
		}


		int jInc,startJ;
		if( comingFrom.y > area.centerY() ){
			startJ = bottomJ;
			jInc = -1;
		}
		else{
			startJ = topJ;
			jInc = 1;
		}


		int i_iters = rightI - leftI;

		if( startJ > -1 && startJ < gridTiles[0].length )
			for( int i = 0 ; i < i_iters ; ++i )
			{
				int tempI = startI + iInc*i;
				if( tempI > -1 && tempI < gridTiles.length )
					if( gridTiles[tempI][startJ] )
					{
						vector v = new vector( tempI*gridSize , startJ*gridSize );
						v.x += v.x < area.centerX() ? extraX : 0;
						v.y += v.y < area.centerY() ? extraY : 0;
						return v;
					}

			}


		int j_iters = bottomJ - topJ;


		if( startI > -1 && startI < gridTiles.length )
			for( int j = 0 ; j < j_iters ; ++j )
			{
				int tempJ = startJ + jInc*j;
				if( tempJ > -1 && tempJ < gridTiles[0].length )
					if( gridTiles[startI][tempJ] )
					{
						vector v = new vector( startI*gridSize , tempJ*gridSize );
						v.x += v.x < area.centerX() ? extraX : 0;
						v.y += v.y < area.centerY() ? extraY : 0;
						return v;
					}
			}


		//adjust index's, switch sides
		jInc *= -1;
		iInc *= -1;

		startI = startI == rightI ? leftI : rightI;
		startJ = startJ == topJ ? bottomJ : topJ;


		if( startJ > -1 && startJ < gridTiles[0].length )
			for( int i = 0 ; i < i_iters ; ++i )
			{
				int tempI = startI + iInc*i;
				if( tempI > -1 && tempI < gridTiles.length )
					if( gridTiles[tempI][startJ] )
					{
						vector v = new vector( tempI*gridSize , startJ*gridSize );
						v.x += v.x < area.centerX() ? extraX : 0;
						v.y += v.y < area.centerY() ? extraY : 0;
						return v;
					}
			}


		if( startI > -1 && startI < gridTiles.length )
			for( int j = 0 ; j < j_iters ; ++j )
			{
				int tempJ = startJ + jInc*j;
				if( tempJ > -1 && tempJ < gridTiles[0].length )
					if( gridTiles[startI][tempJ] )
					{
						vector v = new vector( startI*gridSize , tempJ*gridSize );
						v.x += v.x < area.centerX() ? extraX : 0;
						v.y += v.y < area.centerY() ? extraY : 0;
						return v;
					}
			}

		////Log.d( TAG , "comingFrom = " + comingFrom );
		////Log.d( TAG , "area = " + area );
		////Log.d( TAG , "leftI:" + leftI + " topJ" + topJ + " rightI:" + rightI + " bottomJ:" + bottomJ );

		//Log.e( TAG , "Trying to path to unwalkable tile(startI=" +startI+" , startJ=" + startJ + ")");
		return null;

	}


	/**
	 * Only looks at the adjacent tiles.
	 * @param v
	 * @param grid
	 * @return
	 */
	public vector getClosestAdjWalkableTile( vector v )
	{

		boolean[][] gridTiles = grid.getGridTiles();
		final float gridSize = grid.getGridSize();

		int x = (int) (v.x/gridSize);
		int y = (int) (v.y/gridSize);

		if( x < 1 )
			x = 1;
		else if( x > gridTiles.length-2 )
			x = gridTiles.length-2;

		if( y < 1 )
			y = 1;
		else if( y > gridTiles[0].length-2 )
			y = gridTiles[0].length-2;


		//		int minDx = x < 1 ? 0 : -1;
		//		int minDy = y < 1 ? 0 : -1;
		//
		//		int maxDx = x >= gridTiles   .length-2 ? 1 : 2;
		//		int maxDy = y >= gridTiles[0].length-2 ? 1 : 2;

		for( int i = -1 ; i < 2 ; ++i )
			for( int j = -1 ; j < 2 ; ++j )
				if( gridTiles[x+i][y+j] )
					if( i == j && i == 0 )
						return v;
					else
						return new vector( ((x+i)* gridSize) + gridSize/2 , ((y+j) * gridSize) + gridSize/2 );

		return v;
	}



	public static boolean isWalkable(vector nearDp, Grid grid) {

		int i = (int) (nearDp.x/grid.getGridSize());
		int j = (int) (nearDp.y/grid.getGridSize());

		boolean[][] gridTiles = grid.getGridTiles();

		if( i >= gridTiles.length || i < 0 )
			return false;
		if( j >= gridTiles[0].length || j < 0 )
			return false;

		return gridTiles[i][j];
	}







	public Grid getGrid() {
		return grid;
	}


	public float getGridSize() {
		return grid.getGridSize();
	}






	public int getMapWidth() {
		return mapWidth;
	}
	public int getMapHeight() {
		return mapHeight;
	}


























}
