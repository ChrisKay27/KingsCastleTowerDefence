package com.kingscastle.nuzi.towerdefence.level;

import android.graphics.Rect;

import com.kingscastle.nuzi.towerdefence.framework.Graphics;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

public class Background
{
	private final int mapWidth;
	private final int mapHeight;

	private final GidBackground gidBackground ;
	private final ScrollingGidBasedBackground scrollingBackground ;


	public interface ScreenWidthProvider{
		int getScreenWidth();
		int getScreenHeight();
	}


	/**
	 * 
	 * @param mapWidth
	 * @param mapHeight
	 * @param swp Provides dynamic screen dimensions based on current zoom level
	 * @param fullScreenWidth Width of the SurfaceView
	 * @param fullScreenHeight Height of the SurfaceView
	 */
	public Background( int mapWidth, int mapHeight , ScreenWidthProvider swp , int fullScreenWidth , int fullScreenHeight)
	{
		this.mapWidth = mapWidth ;
		this.mapHeight = mapHeight;


		gidBackground = new GidBackground( Rpg.getWidth(), Rpg.getHeight() , this.mapWidth , this.mapHeight , (int) ( 16*Rpg.getDp() ) , (int) ( 16*Rpg.getDp() ) );

		//gidBackground.setWidthAndHeight( this.width , this.height , (int) ( 16*Rpg.getDp() ) , (int) ( 16*Rpg.getDp() ) );

		vector centeredOn = new vector( ( this.mapWidth / 2 ) * 16*Rpg.getDp()  ,  ( this.mapHeight / 2 ) * 16*Rpg.getDp() );

		scrollingBackground = new ScrollingGidBasedBackground( gidBackground , centeredOn , swp , fullScreenWidth , fullScreenHeight );

		scrollingBackground.adjustCenteredOn();

	}




	public void scrollBy(int i, int j)
	{
		getScrollingBackground().scrollBy(i,j);
	}

	public vector getCenteredOn() {
		return getScrollingBackground().getCenteredOn();
	}


	/**
	 * 
	 * @param centerVector It is ok to setCenteredOn to be a game elements location, it is not edited.
	 */
	public void setCenteredOn(vector centerVector) {
		getScrollingBackground().setCenteredOn(centerVector);
	}



	private long lastAdjustedForSelectedGroup;
	private final long adjustEvery = 2000;


//	public void adjustCenteredOn(ArrayList<LivingThing> centeredAround)
//	{
//		if( centeredAround != null && centeredAround.size() != 0 )
//		{
//			if(lastAdjustedForSelectedGroup+adjustEvery<GameTime.getTime())
//			{
//				getScrollingBackground().setCenteredOn(GameElementUtil.getAverageLoc(centeredAround));
//				lastAdjustedForSelectedGroup=GameTime.getTime();
//			}
//		}
//	}




	public Rect getScreenArea() {
		return getScrollingBackground().getScreenArea();
	}

	//	public ScrollingBackground getScrollingBackground() {
	//		return scrBkrnd;
	//	}


	public ScrollingGidBasedBackground getScrollingBackground()
	{
		return scrollingBackground;
	}



	public void adjustCenteredOn(){
		getScrollingBackground().adjustCenteredOn();
	}
	public void adjustScreenArea(){
		getScrollingBackground().adjustScreenArea();
	}




	public void nullify(){
	}




	public GidBackground getGidBackground()
	{
		return gidBackground;
	}


	public void drawBackground( Graphics g )
	{
		getGidBackground().drawBackground( g , getCenteredOn() );
	}




	public void setCenteredOnVelocity( int dxdt , int dydt )
	{
		scrollingBackground.setCenteredOnVelocity( dxdt , dydt );

	}




	public int getHeightInPx() {
		return getGidBackground().getHeightInPx();
	}

	public int getWidthInPx() {
		return getGidBackground().getWidthInPx();
	}






}// END CLASS

