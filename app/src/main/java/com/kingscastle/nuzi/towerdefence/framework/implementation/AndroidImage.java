package com.kingscastle.nuzi.towerdefence.framework.implementation;


import android.graphics.Bitmap;
import android.graphics.Rect;

import com.kingscastle.nuzi.towerdefence.framework.Graphics.ImageFormat;
import com.kingscastle.nuzi.towerdefence.framework.Image;

public class AndroidImage implements Image {
	Bitmap bitmap;
	private ImageFormat format;
	private int wDiv2;
    private int hDiv2;
	private Rect srcRect;
	private Rect dstRect;

	public AndroidImage( Bitmap bitmap , ImageFormat format)
	{
		this.bitmap = bitmap;
		this.format = format;
		srcRect = new Rect ( 0 , 0 , bitmap.getWidth() , bitmap.getHeight() ) ;
		dstRect = new Rect ( srcRect );
	}

	public AndroidImage() {
	}

	@Override
	public int getWidth() {
		return bitmap.getWidth();
	}
	@Override
	public int getWidthDiv2()
	{
		if( wDiv2 == 0 )
		{
			wDiv2=bitmap.getWidth()/2;
		}
		return wDiv2;
	}
	@Override
	public int getHeight() {
		return bitmap.getHeight();
	}
	@Override
	public int getHeightDiv2()
	{
		if( hDiv2 == 0 )
		{
			hDiv2 = bitmap.getHeight()/2;
		}
		return hDiv2;
	}
	@Override
	public ImageFormat getFormat() {
		return format;
	}

	@Override
	public void dispose()
	{
		if( bitmap != null )
		{
			bitmap.recycle();
		}
	}
	@Override
	public Bitmap getBitmap(){
		return bitmap;
	}

	@Override
	public void setBitmap(Bitmap bitmap) {
		this.bitmap=bitmap;

	}

	@Override
	public boolean isDisposed()
	{
		return bitmap == null || bitmap.isRecycled();
	}

	@Override
	public Rect getSrcRect()
	{
		return srcRect;
	}

	@Override
	public Rect getDstRect() {

		return dstRect;
	}

}
