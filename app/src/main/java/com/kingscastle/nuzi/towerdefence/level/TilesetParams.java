package com.kingscastle.nuzi.towerdefence.level;

public class TilesetParams
{
	private int imageId;
	private String fileName;
	private int firstgid;
	private int numHorzTiles;
	private int numVertTiles;
	private int tileHeight;
	private int tileWidth
	
	;
	/**
	 * @return the imageId
	 */
	public int getImageId() {
		return imageId;
	}
	/**
	 * @param imageId the imageId to set
	 */
	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
	/**
	 * @return the numHorzTiles
	 */
	public int getNumHorzTiles() {
		return numHorzTiles;
	}
	/**
	 * @param numHorzTiles the numHorzTiles to set
	 */
	public void setNumHorzTiles(int numHorzTiles) {
		this.numHorzTiles = numHorzTiles;
	}
	/**
	 * @return the numVertTiles
	 */
	public int getNumVertTiles() {
		return numVertTiles;
	}
	/**
	 * @param numVertTiles the numVertTiles to set
	 */
	public void setNumVertTiles(int numVertTiles) {
		this.numVertTiles = numVertTiles;
	}
	/**
	 * @return the tileHeight
	 */
	public int getTileHeight() {
		return tileHeight;
	}
	/**
	 * @param tileHeight the tileHeight to set
	 */
	public void setTileHeight(int tileHeight) {
		this.tileHeight = tileHeight;
	}
	/**
	 * @return the tileWidth
	 */
	public int getTileWidth() {
		return tileWidth;
	}
	/**
	 * @param tileWidth the tileWidth to set
	 */
	public void setTileWidth(int tileWidth) {
		this.tileWidth = tileWidth;
	}
	public int getFirstgid() {
		return firstgid;
	}
	public void setFirstgid(int firstgid) {
		this.firstgid = firstgid;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
