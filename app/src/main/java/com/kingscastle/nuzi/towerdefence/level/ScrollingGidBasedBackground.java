package com.kingscastle.nuzi.towerdefence.level;

import android.graphics.Rect;

import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.level.Background.ScreenWidthProvider;

public class ScrollingGidBasedBackground
{
	private final GidBackground bg;
	private vector centeredOn;
	private final int fullScreenWidth,fullScreenWidthDiv2,fullScreenHeight,fullScreenHeightDiv2;

	private int scrolledByX, scrolledByY;
	private int scrollingX,scrollingY,scrollingToX,scrollingToY;
	private boolean autoScrolling=false,screenAreaHasChanged=true;
	private final Rect screenArea = new Rect();
	private int slideEvery=20;
	private long lastSlidImage= System.currentTimeMillis();

	//private int rightMinusWidth,rightMinusWidthDiv2,bottomMinusHeight,bottomMinusHeightDiv2;

	private int mapHeight,mapWidth;//,bottomMinusHeight,bottomMinusHeightDiv2;

	private final ScreenWidthProvider swp;

	public ScrollingGidBasedBackground(GidBackground bg , vector centeredOn, ScreenWidthProvider swp , int fullScreenWidth , int fullScreenHeight )
	{
		this.bg=bg;
		this.centeredOn=centeredOn;
		this.fullScreenWidth=fullScreenWidth;
		this.fullScreenHeight=fullScreenHeight;
		this.swp = swp;

		fullScreenWidthDiv2=fullScreenWidth/2;
		fullScreenHeightDiv2=fullScreenHeight/2;

		mapWidth  = bg.getWidthInPx();
		mapHeight = bg.getHeightInPx();

		if( centeredOn == null )
			this.centeredOn=new vector();
	}



	/**
	 * Call this method to ensure the screen area stays over the map.
	 */
	public void adjustScreenArea()
	{
		screenArea.left = centeredOn.getIntX() - swp.getScreenWidth()/2;
		screenArea.left = screenArea.left <= 0 ? 0 : screenArea.left;
		screenArea.top = centeredOn.getIntY() - swp.getScreenHeight()/2;
		screenArea.top = screenArea.top <= 0 ? 0 : screenArea.top;
		screenArea.right = screenArea.left + swp.getScreenWidth();
		screenArea.bottom = screenArea.top + swp.getScreenHeight();

		boolean Return = false;
		if( screenArea.width() > bg.getWidthInPx() ){
			int difX = (screenArea.width() - bg.getWidthInPx())/2;

			screenArea.left = -difX;
			screenArea.right = screenArea.left + swp.getScreenWidth();

			Return = true;
		}
		if( screenArea.height() > bg.getHeightInPx() ){
			int difY = (screenArea.height() - bg.getHeightInPx())/2;

			screenArea.top = -difY;
			screenArea.bottom = screenArea.top + swp.getScreenHeight();

			Return = true;
		}
		if( Return ) return;

		if (screenArea.right > bg.getWidthInPx()) {
			screenArea.left = mapWidth - swp.getScreenWidth();
			screenArea.right = bg.getWidthInPx();
		}

		if (screenArea.bottom > bg.getHeightInPx()) {
			screenArea.top = mapHeight - swp.getScreenHeight();
			screenArea.bottom = bg.getHeightInPx();
		}

		adjustCenteredOn();
	}


	public void scrollBy(int i, int j)
	{
		if( i > 50 ) i = 50;
		if( j > 50 ) j = 50;


		int screenWidthDiv2 = swp.getScreenWidth()/2;
		int screenHeightDiv2 = swp.getScreenHeight()/2;

		if( i != 0 && !(centeredOn.getIntX() + i + screenWidthDiv2 > bg.getWidthInPx())
				&& (centeredOn.getIntX() + i - screenWidthDiv2 > 0)) {
			centeredOn.incX(i);
			centeredOn.x = Math.round(centeredOn.x);
			centeredOn.x -= centeredOn.x%2;
			scrolledByX = i;
			screenAreaHasChanged=true;

		}

		if( j != 0 && !(centeredOn.getIntY() + j + screenHeightDiv2 > bg.getHeightInPx())
				&& (centeredOn.getIntY() + j - screenHeightDiv2 > 0)) {
			centeredOn.incY(j);
			centeredOn.y = Math.round(centeredOn.y);
			scrolledByY = j;
			screenAreaHasChanged=true;
		}

		adjustScreenArea();
		adjustCenteredOn();

//		if( centeredOn.x < screenWidthDiv2 ) centeredOn.x = screenWidthDiv2;
//		if( centeredOn.x + screenWidthDiv2 > bg.getWidthInPx() ) centeredOn.x = bg.getWidthInPx() - screenWidthDiv2;
//		if( centeredOn.y < screenHeightDiv2 ) centeredOn.y = screenHeightDiv2;
//		if( centeredOn.y + screenHeightDiv2 > bg.getHeightInPx() ) centeredOn.y = bg.getHeightInPx() - screenHeightDiv2;

	}

	public void adjustCenteredOn()
	{
		centeredOn.x = screenArea.centerX();
		centeredOn.y = screenArea.centerY();

//		int screenWidthDiv2 = swp.getScreenWidth()/2;
//		if( centeredOn.x < screenWidthDiv2 )
//			centeredOn.x = screenWidthDiv2;
//
//		else if( centeredOn.x > mapWidth - screenWidthDiv2 )
//			centeredOn.x = mapWidth - screenWidthDiv2;
//
//
//		int screenHeightDiv2 = swp.getScreenHeight()/2;
//		if( centeredOn.y < screenHeightDiv2 )
//			centeredOn.y = screenHeightDiv2 ;
//		else if( centeredOn.y > mapHeight - screenHeightDiv2 )
//			centeredOn.y = mapHeight - screenHeightDiv2;
	}


	public vector getCenteredOn()
	{
		if(lastSlidImage + slideEvery<System.currentTimeMillis()){
			if( scrolledByX == 0 && scrolledByY == 0 )
			{
				return centeredOn;
			}

			if (scrolledByX < 0 && scrolledByY < 0)
			{
				//System.out.println("scrolledByX="+scrolledByX+ " so scrolledByX is less then zero");
				scrollBy(scrolledByX + 1, scrolledByY+1);
			}
			else if (scrolledByX > 0 && scrolledByY > 0)
			{
				//System.out.println("scrolledByX="+scrolledByX+ " so scrolledByX is greater then zero");
				scrollBy(scrolledByX - 1, scrolledByY-1);
			}
			else if (scrolledByX > 0 &&scrolledByY < 0)
			{
				//System.out.println("scrolledByY="+scrolledByY+ " so scrolledByY is less then zero");
				scrollBy(scrolledByX-1, scrolledByY + 1);
			}
			else if (scrolledByX < 0 && scrolledByY > 0)
			{
				//System.out.println("scrolledByY="+scrolledByY+ " so scrolledByY is greater then zero");
				scrollBy(scrolledByX+1, scrolledByY - 1);
			}
			else if (scrolledByX < 0 && scrolledByY > 0)
			{
				//System.out.println("scrolledByY="+scrolledByY+ " so scrolledByY is greater then zero");
				scrollBy(scrolledByX+1, scrolledByY - 1);
			}
			else if (scrolledByX == 0)
			{
				if(scrolledByY > 0)
				{
					//System.out.println("scrolledByY="+scrolledByY+ " so scrolledByY is greater then zero");
					scrollBy(0, scrolledByY - 1);
				}
				else if(scrolledByY < 0)
				{
					scrollBy(0, scrolledByY + 1);
				}
			} else if (scrolledByY == 0)
			{
				if(scrolledByX > 0)
				{
					//System.out.println("scrolledByY="+scrolledByY+ " so scrolledByY is greater then zero");
					scrollBy(scrolledByX-1, 0);
				}
				else if(scrolledByX < 0)
				{
					scrollBy(scrolledByX+1, 0);
				}
			}

			lastSlidImage = System.currentTimeMillis();
		}
		return centeredOn;
	}

	public void setCenteredOn( vector centeredOn2 )
	{
		if( centeredOn2 == null )
			return;

		centeredOn.set( centeredOn2.x , centeredOn2.y );

		//Zoom issue corrections
		centeredOn.x = Math.round(centeredOn.x);
		centeredOn.x -= centeredOn.x%2;

		centeredOn.y = Math.round(centeredOn.y);
		centeredOn.y -= centeredOn.y%2;


		adjustCenteredOn();
		setScreenAreaHasChanged(true);
	}






	//	public void adjustCenteredOn()
	//	{
	//		if( centeredOn.x < screenWidthDiv2 )
	//			centeredOn.x = screenWidthDiv2;
	//
	//		else if( centeredOn.x > rightMinusWidthDiv2 )
	//			centeredOn.x = rightMinusWidthDiv2;
	//
	//		if( centeredOn.y < screenHeightDiv2 )
	//			centeredOn.y = screenHeightDiv2 ;
	//		else if( centeredOn.y > bottomMinusHeightDiv2)
	//			centeredOn.y = bottomMinusHeightDiv2;
	//
	//	}


	public vector getCoordinatesMapToScreen(float x, float y, vector intoThisVector)
	{
		intoThisVector.set((x - centeredOn.x + fullScreenWidthDiv2), (y - centeredOn.y + fullScreenHeightDiv2));
		return intoThisVector;
	}

	public vector getCoordinatesMapToScreen(float x, float y )
	{
		return this.getCoordinatesMapToScreen( x , y , new vector() );
	}

	public vector getCoordinatesScreenToMap(float x, float y, vector intoThisVector )
	{
		intoThisVector.set(x - (fullScreenWidthDiv2) + centeredOn.x, (y - fullScreenHeightDiv2) + centeredOn.y);
		return intoThisVector;
	}

	public vector getCoordinatesScreenToMap(float x, float y)
	{
		return this.getCoordinatesScreenToMap( x , y , new vector() );
	}



	public int getScrollingY() {
		return scrollingY;
	}
	public void setScrollingY(int scrollingY) {
		this.scrollingY = scrollingY;
	}
	public int getScrollingToX() {
		return scrollingToX;
	}
	public void setScrollingToX(int scrollingToX) {
		this.scrollingToX = scrollingToX;
	}
	public int getScrollingToY() {
		return scrollingToY;
	}
	public void setScrollingToY(int scrollingToY) {
		this.scrollingToY = scrollingToY;
	}
	public boolean isAutoScrolling() {
		return autoScrolling;
	}
	public void setAutoScrolling(boolean autoScrolling) {
		this.autoScrolling = autoScrolling;
	}

	public int getSliderEvery() {
		return slideEvery;
	}
	public void setSliderEvery(int sliderEvery) {
		slideEvery = sliderEvery;
	}


	public int getScrolledByX() {
		return scrolledByX;
	}
	public void setScrolledByX(int scrolledByX) {
		this.scrolledByX = scrolledByX;
	}
	public int getScrolledByY() {
		return scrolledByY;
	}
	public void setScrolledByY(int scrolledByY) {
		this.scrolledByY = scrolledByY;
	}
	public int getScrollingX() {
		return scrollingX;
	}
	public void setScrollingX(int scrollingX) {
		this.scrollingX = scrollingX;
	}
	void setScreenAreaHasChanged(boolean updateScreenArea){
		screenAreaHasChanged=updateScreenArea;
	}




	public void nullify()
	{
		centeredOn = null;
	}



	public void setCenteredOnVelocity(int dxdt, int dydt)
	{
		scrolledByX = dxdt;
		scrolledByY = dydt;
	}

	public Rect getScreenArea()
	{
		if( screenAreaHasChanged )
		{
			adjustScreenArea();
			screenAreaHasChanged=false;
		}
		return screenArea;
	}

}
