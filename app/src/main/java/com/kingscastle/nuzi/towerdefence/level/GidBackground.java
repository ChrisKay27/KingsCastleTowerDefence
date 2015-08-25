package com.kingscastle.nuzi.towerdefence.level;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Graphics;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;

public class GidBackground
{

	private final int mapWidth , mapHeight ,
	mapWidthInPx , mapHeightInPx ,
	regTileWidth , regTileHeight;

	private final int screenWidth;

	private final int screenHeight;

	private final int screenWidthDiv2;

	private final int screenHeightDiv2;

	private int numHorzTiles;

	private int numVertTiles;

	private ArrayList<TileLayer> layers = new ArrayList<TileLayer>();
	private GidTileHolder gidTileHolder = new GidTileHolder();

	private Image backgroundImage = Assets.loadImage(R.drawable.large_grass_tile);


	public GidBackground( int screenWidth , int screenHeight , int mapWidth2 , int mapHeight2 , int regTileWidth2, int regTileHeight2 )
	{
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;

		this.screenWidthDiv2  = Rpg.getWidthDiv2();
		this.screenHeightDiv2 = Rpg.getHeightDiv2();

		this.mapWidth = mapWidth2;
		this.mapHeight = mapHeight2;


		this.regTileWidth = regTileWidth2 ;
		this.regTileHeight = regTileHeight2 ;


		this.mapWidthInPx = mapWidth * regTileWidth2;
		this.mapHeightInPx = mapHeight * regTileHeight2;

		loadBackgroundLayer( mapWidthInPx , mapHeightInPx );
	}


	public void addTileset( TilesetParams readTileset )
	{
		gidTileHolder.loadImageIntoGids(readTileset);
	}

	void addTileLayer(TileLayer layer)
	{
//		if( layer == null )
//			return;
//
		//System.out.println("Adding layer : " + layer );
		layers.add( layer );
	}



	private void loadBackgroundLayer( int mapWidthInPx , int mapHeightInPx )
	{
		//Image backgroundImage = Assets.loadImage( R.drawable.large_grass_tile );

		//gidTileHolder.addImageToGidImages( backgroundImage , backgroundImage.getWidth() , backgroundImage.getHeight() );

		numHorzTiles = screenWidth / getRegTileWidth() + 1;
		numVertTiles = screenHeight / getRegTileHeight() + 1;

		TileLayer tl = getBackgroundLayer(backgroundImage, mapWidthInPx , mapHeightInPx , screenWidth , screenHeight );
		addTileLayer( tl );
	}




	private static TileLayer getBackgroundLayer(Image backgroundTile , int mapWidthInPx , int mapHeightInPx , int screenWidth, int screenHeight)
	{
		TileLayer tl = new TileLayer();

		int horzTiles = mapWidthInPx / backgroundTile.getWidth();
		int vertTiles = mapHeightInPx / backgroundTile.getHeight();

		short[][] backgroundGids= new short[vertTiles][horzTiles];

		fillWith ( backgroundGids , (short) 1 );

		tl.setTileWidth( backgroundTile.getWidth() );
		tl.setTileHeight( backgroundTile.getHeight() );

		tl.setThisLayersNumHorzTiles( ( screenWidth / tl.getTileWidth() ) + 2 );
		tl.setThisLayersNumVertTiles( ( screenHeight / tl.getTileHeight() ) + 2);

		tl.setGids(backgroundGids);

		return tl;
	}



	private static short[][] fillWith( short[][] array2D, short filler )
	{


		int i = 0, j = 0;
		while ( j < array2D.length)
		{
			while ( i < array2D[j].length)
			{
				array2D[j][i] = filler ;
				i++;
			}
			i = 0;
			j++;
		}

		return array2D;
	}


	//private long lastmessage;



	private int xTemp , yTemp , xOffs , yOffs , xGid , yGid ;
	private Rect src = new Rect(), dst = new Rect();

	public void drawBackground( Graphics g , vector centeredOn )
	{
		xTemp = centeredOn.getIntX() - screenWidthDiv2;
		yTemp = centeredOn.getIntY() - screenHeightDiv2;

		int x = 0 ;
		int y = 0 ;

		//		if ( GameTime.getTime() - lastmessage > 1000)
		//		{
		//			System.out.println("GidBackground.drawBackground() :  centeredOn is " + centeredOn);
		//			System.out.println(" Current xTemp value for drawing is : " + xTemp );
		//			System.out.println(" Current xTemp value for drawing is : " + yTemp );
		//			System.out.println(" There are " + layers.size() + " layers.");
		//		}


		//	short gid;

		for( TileLayer tl : layers)
		{
			xGid = xTemp / tl.getTileWidth();
			yGid = yTemp / tl.getTileHeight();

			xOffs = -xTemp % tl.getTileWidth();
			yOffs = -yTemp % tl.getTileHeight();

			for( int j = 0 ; j < tl.getThisLayersNumVertTiles() ; ++ j )
			{
				for( int i = 0 ; i < tl.getThisLayersNumHorzTiles() ; ++ i )
				{

					//gid = tl.getGidFor( xGid + i ,  yGid + j );

					//if( gid != 0 )
					//{
					//g.drawImage( gidTileHolder.getImage( (short) 1 ), x + xOffs , y + yOffs , dstATopPaint );
//					if( x+xOffs+backgroundImage.getWidth() > mapWidthInPx || y+yOffs+backgroundImage.getHeight() > mapHeightInPx ) {
//
//					dst.set( x + xOffs,y + yOffs,x + xOffs + (mapWidthInPx-centeredOn.getIntX()) , y + yOffs + (mapHeightInPx-centeredOn.getIntY()));
//					g.drawImage(backgroundImage, src, dst , getPaint());
//				}else
						g.drawImage(backgroundImage, x + xOffs , y + yOffs , getPaint() );

					//}

					//					if ( GameTime.getTime() - lastmessage > 1000)
					//					{
					//						System.out.println("GidBackground.drawBackground() : current tile gid for this layer is " + gid);
					//						System.out.println(" Current x value for drawing is : " + x );
					//					}

					x += tl.getTileWidth();

				}
				//				if ( GameTime.getTime() - lastmessage > 1000)
				//				{
				//					lastmessage=GameTime.getTime();
				//					System.out.println(" Current y value for drawing is : " + y );
				//
				//				}
				x = 0;
				y += tl.getTileHeight();
			}
		}

		//g.drawString( porterType , Rpg.getWidthDiv2() , Rpg.getHeightDiv2() , clearPaint );

	}

	private final Paint dstATopPaint = new Paint();{
		dstATopPaint.setXfermode( new PorterDuffXfermode(PorterDuff.Mode.DST_OVER ) );
	}
	private final Paint clearPaint = new Paint();

	private int currentPorterIndex;
	private String porterType;
	private long timeToSwitchPorterMode;

	private final ArrayList<PorterDuff.Mode> porterDuffModes = new ArrayList<PorterDuff.Mode>();

	{
		//porterDuffModes.add( PorterDuff.Mode.DST );
		porterDuffModes.add( PorterDuff.Mode.DST_OVER );
		//porterDuffModes.add( PorterDuff.Mode.DST_ATOP );
		//porterDuffModes.add( PorterDuff.Mode.DST_IN );
		//porterDuffModes.add( PorterDuff.Mode.DST_OUT );
		//porterDuffModes.add( PorterDuff.Mode.SRC );
		//porterDuffModes.add( PorterDuff.Mode.SRC_ATOP );
		//porterDuffModes.add( PorterDuff.Mode.SRC_IN );
		//porterDuffModes.add( PorterDuff.Mode.SRC_OUT );
		//porterDuffModes.add( PorterDuff.Mode.SRC_OVER );
		//porterDuffModes.add( PorterDuff.Mode.ADD );
		//porterDuffModes.add( PorterDuff.Mode.CLEAR );
		//porterDuffModes.add( PorterDuff.Mode.DARKEN );
		//porterDuffModes.add( PorterDuff.Mode.LIGHTEN );
		//porterDuffModes.add( PorterDuff.Mode.MULTIPLY );
		//porterDuffModes.add( PorterDuff.Mode.OVERLAY );
		//porterDuffModes.add( PorterDuff.Mode.SCREEN );
		//porterDuffModes.add( PorterDuff.Mode.XOR );

		//clearPaint.setXfermode( new PorterDuffXfermode( PorterDuff.Mode.CLEAR ));
		//clearPaint.setTextSize( Rpg.getGame().getTextSize() * 2 );
		clearPaint.setColor( Color.WHITE );
		clearPaint.setTextAlign( Align.CENTER );
	}

	private Paint getPaint()
	{
		//
		//		if( timeToSwitchPorterMode < GameTime.getTime() )
		//		{
		//
		//			dstATopPaint.setXfermode( new PorterDuffXfermode(porterDuffModes.get(currentPorterIndex) ) );
		//
		//			timeToSwitchPorterMode = GameTime.getTime() + 2000;
		//
		//			porterType = porterDuffModes.get(currentPorterIndex).toString();
		//
		//			++currentPorterIndex;
		//
		//			if( currentPorterIndex >= porterDuffModes.size() )
		//			{
		//				currentPorterIndex = 0;
		//			}
		//		}

		return dstATopPaint;
	}


	public void setBackgroundImage(Image backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	/**
	 * @return the width
	 */
	public int getWidthInPx() {
		return mapWidthInPx;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return mapWidth;
	}



	/**
	 * @return the width
	 */
	public int getHeightInPx() {
		return mapHeightInPx;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return mapHeight;
	}



	/**
	 * @return the mapWidth
	 */
	public int getMapWidth() {
		return mapWidth;
	}


	/**
	 * @return the mapHeight
	 */
	public int getMapHeight() {
		return mapHeight;
	}



	/**
	 * @return the layers
	 */
	public ArrayList<TileLayer> getLayers() {
		return layers;
	}

	/**
	 * @param layers the layers to set
	 */
	public void setLayers(ArrayList<TileLayer> layers) {
		this.layers = layers;
	}

	/**
	 * @return the tileGidHolder
	 */
	public GidTileHolder getTileGidHolder() {
		return gidTileHolder;
	}

	/**
	 * @param gidTileHolder the tileGidHolder to set
	 */
	public void setTileGidHolder(GidTileHolder gidTileHolder) {
		this.gidTileHolder = gidTileHolder;
	}

	/**
	 * @return the screenWidth
	 */
	public int getScreenWidth() {
		return screenWidth;
	}



	/**
	 * @return the screenHeight
	 */
	public int getScreenHeight() {
		return screenHeight;
	}


	/**
	 * @return the screenWidthDiv2
	 */
	public int getScreenWidthDiv2() {
		return screenWidthDiv2;
	}



	/**
	 * @return the screenHeightDiv2
	 */
	public int getScreenHeightDiv2() {
		return screenHeightDiv2;
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
	 * @return the xTemp
	 */
	public int getxTemp() {
		return xTemp;
	}

	/**
	 * @param xTemp the xTemp to set
	 */
	public void setxTemp(int xTemp) {
		this.xTemp = xTemp;
	}

	/**
	 * @return the yTemp
	 */
	public int getyTemp() {
		return yTemp;
	}

	/**
	 * @param yTemp the yTemp to set
	 */
	public void setyTemp(int yTemp) {
		this.yTemp = yTemp;
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
	public void setxOffs(int xOffs) {
		this.xOffs = xOffs;
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
	public void setyOffs(int yOffs) {
		this.yOffs = yOffs;
	}

	/**
	 * @return the xGid
	 */
	public int getxGid() {
		return xGid;
	}

	/**
	 * @param xGid the xGid to set
	 */
	public void setxGid(int xGid) {
		this.xGid = xGid;
	}

	/**
	 * @return the yGid
	 */
	public int getyGid() {
		return yGid;
	}

	/**
	 * @param yGid the yGid to set
	 */
	public void setyGid(int yGid) {
		this.yGid = yGid;
	}


	int getRegTileWidth() {
		return regTileWidth;
	}



	int getRegTileHeight() {
		return regTileHeight;
	}












}
