package com.kingscastle.nuzi.towerdefence.gameElements.targeting;


import com.kingscastle.nuzi.towerdefence.gameElements.GameElement;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

/**
 * I don't know what this class is for. Don't use it
 */
public class TargetingGrid
{

	private final float gridTileSize;
	private final GameElement[][] gridTiles;


	public TargetingGrid( int numHorzTiles , int numVertTiles , float gridTileSize )
	{
		gridTiles = new GameElement[numHorzTiles][numVertTiles];
		this.gridTileSize = gridTileSize;


		//Log.d( "Grid" , "numHorzTiles=" + numHorzTiles + " numVertTiles" + numVertTiles + " gridTileSize" + gridTileSize);
		//Log.d( "Grid" , "gridTiles.length=" + gridTiles.length + " gridTiles[0].length" + gridTiles[0].length );
	}


	public GameElement[][] getGridTiles(){
		return gridTiles;
	}

	public float getGridSize(){
		return gridTileSize;
	}



	public synchronized int width(){
		return gridTiles.length;
	}
	public synchronized int height(){
		if( gridTiles.length > 0 )
			return gridTiles[0].length;
		return 0;
	}

	public boolean isWalkable(vector v) {
		int i = (int) (v.x/gridTileSize);
		int j = (int) (v.y/gridTileSize);

		if( i < 0 || i > width() || j < 0 || j > height() )
			return false;
		synchronized( this ){
			return gridTiles[i][j] == null;
		}
	}




}
