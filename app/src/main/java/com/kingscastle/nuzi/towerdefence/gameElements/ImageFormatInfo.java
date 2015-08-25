package com.kingscastle.nuzi.towerdefence.gameElements;


public class ImageFormatInfo
{

	private int aliveId;
	private int displayId;
	private int dyingId;
	private int damagedId;
	private int numOfDyingFrames;
	private int xOffs, yOffs;
	private int numHorzSpriteSets, numVertSpriteSets;
	private int numHorzImages;
	private int numVertImages;
	private int redId;
	private int whiteId;
	private int greenId;
	private int orangeId;
	private int blueId;

	public ImageFormatInfo()
	{
	}


	/**
	 * 
	 */
	public ImageFormatInfo(int aliveId , int displayId , int xOffs, int yOffs ,
			int numHorzSpriteSets , int numVertSpriteSets)
	{
		setAliveId(aliveId);
		setDisplayId(displayId);
		setxOffs(xOffs);
		setyOffs(yOffs);
		setNumHorzSpriteSets(numHorzSpriteSets);
		setNumVertSpriteSets(numVertSpriteSets);
	}



	public void setDyingInfo(int dyingId, int numOfDyingFrames)
	{
		setDyingId(dyingId);
		setNumOfDyingFrames(numOfDyingFrames);
	}



	/**
	 * @return the aliveId
	 */
	public int getAliveId() {
		return aliveId;
	}

	/**
	 * @param aliveId the aliveId to set
	 */
    void setAliveId(int aliveId2) {
		aliveId = aliveId2;
	}

	/**
	 * @return the displayId
	 */
	public int getDisplayId() {
		return displayId;
	}

	/**
	 * @param displayId the displayId to set
	 */
    void setDisplayId(int displayId2) {
		displayId = displayId2;
	}

	/**
	 * @return the xOffs
	 */
	public int getxOffs() {
		return xOffs;
	}

	/**
	 * @param xOffs the xOffs to set
	 */
    void setxOffs(int xOffs2) {
		xOffs = xOffs2;
	}

	/**
	 * @return the yOffs
	 */
	public int getyOffs() {
		return yOffs;
	}

	/**
	 * @param yOffs the yOffs to set
	 */
    void setyOffs(int yOffs2) {
		yOffs = yOffs2;
	}

	/**
	 * @return the numHorzSpriteSets
	 */
	public int getNumHorzSpriteSets() {
		return numHorzSpriteSets;
	}

	/**
	 * @param numHorzSpriteSets the numHorzSpriteSets to set
	 */
    void setNumHorzSpriteSets(int numHorzSpriteSets2) {
		numHorzSpriteSets = numHorzSpriteSets2;
	}

	/**
	 * @return the numVertSpriteSets
	 */
	public int getNumVertSpriteSets() {
		return numVertSpriteSets;
	}

	/**
	 * @param numVertSpriteSets the numVertSpriteSets to set
	 */
    void setNumVertSpriteSets(int numVertSpriteSets2) {
		numVertSpriteSets = numVertSpriteSets2;
	}



	public int getDyingId() {
		return dyingId;
	}



	void setDyingId(int dyingId) {
		this.dyingId = dyingId;
	}



	public int getNumOfDyingFrames() {
		return numOfDyingFrames;
	}



	void setNumOfDyingFrames(int numOfDyingFrames) {
		this.numOfDyingFrames = numOfDyingFrames;
	}


	public int getDamagedId() {
		return damagedId;
	}


	public void setDamagedId(int damagedId) {
		this.damagedId = damagedId;
	}


	public int getNumHorzImages() {
		return numHorzImages;
	}


	public void setNumHorzImages(int numHorzImages) {
		this.numHorzImages = numHorzImages;
	}


	public int getNumVertImages() {
		return numVertImages;
	}


	public void setNumVertImages(int numVertImages) {
		this.numVertImages = numVertImages;
	}

	public void setID(int ID) {
		this.redId = ID;
		whiteId = ID;
		greenId = ID;
		orangeId = ID;
		blueId = ID;
	}

	public int getRedId() {
		return redId;
	}


	public void setRedId(int redId) {
		this.redId = redId;
	}


	public int getWhiteId() {
		return whiteId;
	}


	public void setWhiteId(int whiteId) {
		this.whiteId = whiteId;
	}


	public int getGreenId() {
		return greenId;
	}


	public void setGreenId(int greenId) {
		this.greenId = greenId;
	}


	public int getOrangeId() {
		return orangeId;
	}


	public void setOrangeId(int orangeId) {
		this.orangeId = orangeId;
	}


	public int getBlueId() {
		return blueId;
	}


	public void setBlueId(int blueId) {
		this.blueId = blueId;
	}
}
