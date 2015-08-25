package com.kingscastle.nuzi.towerdefence.framework.implementation;

import android.graphics.Rect;

import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;


class ScrollingBackground {
	private Image image;
	private vector centeredOn;
	private final int screenWidth;
    private final int screenWidthDiv2;
    private final int screenHeight;
    private final int screenHeightDiv2;
	
	private int scrolledByX, scrolledByY;
	private int scrollingX,scrollingY,scrollingToX,scrollingToY;
	private boolean autoScrolling=false,screenAreaHasChanged=true;
	private Rect screenArea = new Rect();
	private int slideEvery=20;
	private long lastSlidImage = System.currentTimeMillis();
	private int rightMinusWidth,rightMinusWidthDiv2,bottomMinusHeight,bottomMinusHeightDiv2;
	
	public ScrollingBackground(Image img,vector centeredOn,int screenWidth,int screenHeight)
	{
		image=img;this.centeredOn=centeredOn;this.screenWidth=screenWidth;this.screenHeight=screenHeight;
		screenWidthDiv2=screenWidth/2;screenHeightDiv2=screenHeight/2;
		
		if( image != null )
		{
			rightMinusWidth=image.getWidth()-screenWidth;bottomMinusHeight=image.getHeight()-screenHeight;
			rightMinusWidthDiv2=image.getWidth()-screenWidthDiv2;
			bottomMinusHeightDiv2=image.getHeight()-screenHeightDiv2;
		}
		if( centeredOn == null )
			this.centeredOn = new vector();

	}
	
	public Rect getScreenArea()
	{
		if(screenAreaHasChanged){
			screenArea.left = centeredOn.getIntX() - screenWidthDiv2;
			screenArea.left = screenArea.left <= 0 ? 0 : screenArea.left;
			screenArea.top = centeredOn.getIntY() - screenHeightDiv2;
			screenArea.top = screenArea.top <= 0 ? 0 : screenArea.top;
			screenArea.right = screenArea.left + screenWidth;
			screenArea.bottom = screenArea.top + screenHeight;
			
			if (screenArea.right > image.getWidth()) {
				screenArea.left = rightMinusWidth;
				screenArea.right = image.getWidth();
			}
			
			if (screenArea.bottom > image.getHeight()) {
				screenArea.top = bottomMinusHeight;
				screenArea.bottom = image.getHeight();
			}
			
			screenAreaHasChanged=false;
		}
		return screenArea;
	}
	
	void scrollBy(int i, int j) {
		if (i!=0&&!(centeredOn.getIntX() + i + screenWidthDiv2 > image.getWidth())
				&& (centeredOn.getIntX() + i - screenWidthDiv2 > 0)) {
			centeredOn.incX(i);
			scrolledByX = i;
			screenAreaHasChanged=true;
		}
		if (j!=0&&!(centeredOn.getIntY() + j + screenHeightDiv2 > image.getHeight())
				&& (centeredOn.getIntY() + j - screenHeightDiv2 > 0)) {
			centeredOn.incY(j);
			scrolledByY = j;
			screenAreaHasChanged=true;
		}
	}
	
	public void scrollTo(int i, int j,int inThisMuchTime){
		if(inThisMuchTime==0)
			return;
		scrollingToX=i;scrollingToY=j;
		double dx=i-centeredOn.getX(),dy=j-centeredOn.getY();
		scrollingX = (int) (dx/inThisMuchTime);scrollingY = (int) (dy/inThisMuchTime);
		autoScrolling=true;
	}
	
	public void scroll(){
		if(!autoScrolling)
			return;
		boolean xWithinDist = centeredOn.getX()-scrollingToX<10&&centeredOn.getX()-scrollingToX>-10;
		boolean yWithinDist = centeredOn.getY()-scrollingToY<10&&centeredOn.getY()-scrollingToY>-10;
		
		if(xWithinDist){
			centeredOn.setX(scrollingToX);
		}
		else{
			centeredOn.incX(scrollingX);
		}
		if(yWithinDist){
			centeredOn.setY(scrollingToY);
		}
		else{
			centeredOn.incY(scrollingY);
		}
		if(yWithinDist&&xWithinDist){
			autoScrolling=false;
		}		
	}
	public vector getCoordinatesMapToScreen(float x, float y) {
		return new vector((x - centeredOn.getX() + screenWidthDiv2), (y - centeredOn.getY() + screenHeightDiv2));
	}

	public vector getCoordinatesScreenToMap(float x, float y) {
		return new vector(x - (screenWidthDiv2)
				+ centeredOn.getX(), (y - screenHeightDiv2)
				+ centeredOn.getY());
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
	public void setScreenArea(Rect screenArea) {
		this.screenArea = screenArea;
	}
	public int getSliderEvery() {
		return slideEvery;
	}
	public void setSliderEvery(int sliderEvery) {
		this.slideEvery = sliderEvery;
	}
	
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		if(image!=null){
			rightMinusWidth=image.getWidth()-screenWidth;bottomMinusHeight=image.getHeight()-screenHeight;
		}
		this.image = image;
	}
	public vector getCenteredOn() {
		if(lastSlidImage+slideEvery< System.currentTimeMillis()){
			if(scrolledByX==0&&scrolledByY==0)
				return centeredOn;
			
			if (scrolledByX < 0 && scrolledByY < 0){
				//System.out.println("scrolledByX="+scrolledByX+ " so scrolledByX is less then zero");
				scrollBy(scrolledByX + 1, scrolledByY+1);}
			else if (scrolledByX > 0 && scrolledByY > 0){
				//System.out.println("scrolledByX="+scrolledByX+ " so scrolledByX is greater then zero");
				scrollBy(scrolledByX - 1, scrolledByY-1);}
			else if (scrolledByX > 0 &&scrolledByY < 0){
				//System.out.println("scrolledByY="+scrolledByY+ " so scrolledByY is less then zero");
				scrollBy(scrolledByX-1, scrolledByY + 1);}
			else if (scrolledByX < 0 && scrolledByY > 0){
				//System.out.println("scrolledByY="+scrolledByY+ " so scrolledByY is greater then zero");
				scrollBy(scrolledByX+1, scrolledByY - 1);}
			else if (scrolledByX < 0 && scrolledByY > 0){
				//System.out.println("scrolledByY="+scrolledByY+ " so scrolledByY is greater then zero");
				scrollBy(scrolledByX+1, scrolledByY - 1);}
			else if (scrolledByX == 0){
				if(scrolledByY > 0){
					//System.out.println("scrolledByY="+scrolledByY+ " so scrolledByY is greater then zero");
					scrollBy(0, scrolledByY - 1);}
				else if(scrolledByY < 0){
					scrollBy(0, scrolledByY + 1);}
			} else if (scrolledByY == 0){
				if(scrolledByX > 0){
				//System.out.println("scrolledByY="+scrolledByY+ " so scrolledByY is greater then zero");
				scrollBy(scrolledByX-1, 0);}
				else if(scrolledByX < 0){
					scrollBy(scrolledByX+1, 0);}
			}
			lastSlidImage=System.currentTimeMillis();
		}
		return centeredOn;
	} 
	public void setCenteredOn(vector centeredOn) {
		if(centeredOn==null) return;
		this.centeredOn.set(centeredOn);
		adjustCenteredOn();
		setScreenAreaHasChanged(true);
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
	

	void adjustCenteredOn() {
		if(centeredOn.getX()<screenWidthDiv2)	centeredOn.setX(screenWidthDiv2);
		else if(centeredOn.getX()>rightMinusWidthDiv2) centeredOn.setX(rightMinusWidthDiv2);
		if(centeredOn.getY()<screenHeightDiv2)	centeredOn.setY(screenHeightDiv2);
		else if(centeredOn.getY()>bottomMinusHeightDiv2) centeredOn.setY(bottomMinusHeightDiv2);
	}
	


}
