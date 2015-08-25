package com.kingscastle.nuzi.towerdefence.level;

public class TileLayer
{
	private short[][] gids;
	
	private int  tileWidth , tileHeight , thisLayersNumHorzTiles , thisLayersNumVertTiles; 
	
		
	public short[][] getGids() {
		return gids;
	}
	

	public void setGids(short[][] gids) {
		this.gids = gids;
	}

	
	public short getGidFor(int i, int j)
	{
		if( j < 0 || i < 0 || j > gids.length - 1 || i > gids[j].length -1)
			return 0;
		
		return gids[j][i];
	}
	
	
	@Override
	public String toString()
	{
		String s = "" ;
		for(short[] ys : gids)
		{
			s += "[ " ;
			for(short sx : ys)
			{
				s += ", " + sx;
			}
			s += " ]\n" ;
		}
		return s;
	}
	
	public int getTileWidth()
	{
		return tileWidth;
	}
	
	
	public int getTileHeight()
	{
		return tileHeight;
	}
	

	/**
	 * @param tileWidth the tileWidth to set
	 */
	public void setTileWidth(int tileWidth) {
		this.tileWidth = tileWidth;
	}

	
	/**
	 * @param tileHeight the tileHeight to set
	 */
	public void setTileHeight(int tileHeight) {
		this.tileHeight = tileHeight;
	}
	

	/**
	 * @return the thisLayersNumHorzTiles
	 */
	public int getThisLayersNumHorzTiles() {
		return thisLayersNumHorzTiles;
	}


	/**
	 * @param thisLayersNumHorzTiles the thisLayersNumHorzTiles to set
	 */
	public void setThisLayersNumHorzTiles(int thisLayersNumHorzTiles) {
		this.thisLayersNumHorzTiles = thisLayersNumHorzTiles;
	}


	/**
	 * @param thisLayersNumVertTiles the thisLayersNumVertTiles to set
	 */
	public void setThisLayersNumVertTiles(int thisLayersNumVertTiles) {
		this.thisLayersNumVertTiles = thisLayersNumVertTiles;
	}


	public int getThisLayersNumVertTiles()
	{
		return thisLayersNumVertTiles;
	}
	
}
