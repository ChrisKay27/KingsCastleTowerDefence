package com.kingscastle.nuzi.towerdefence.framework.implementation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;



public class ImageDrawable extends Drawable {

	public Bitmap img;
	public int left;
	public int top;

	private final Rect src = new Rect();
	private final RectF dst = new RectF();
	public final Paint paint;



	public ImageDrawable( Bitmap img , Paint paint ){
		this( img , 0 , 0 , 1f , 1f , paint );
	}

	public ImageDrawable( Bitmap img , int left , int top , Paint paint ){
		this( img , left , top , 1f , 1f , paint );
	}

	public ImageDrawable( Bitmap img , int left , int top , float scaleX , float scaleY , Paint paint ){
		this.img = img;
		this.left = left;
		this.top = top;

		if( paint == null )
			this.paint = new Paint();
		else
			this.paint = paint;

		src.set( 0 , 0 , img.getWidth() , img.getHeight() );
		dst.set( left , top , left+(img.getWidth()*scaleX) , top+(img.getHeight()*scaleY) );
	}




	@Override
	public void draw(Canvas canvas) {
		if( img != null )
			canvas.drawBitmap(img, src, dst, paint);
	}



	@Override
	public int getOpacity() {
		return 0;
	}

	@Override
	public void setAlpha(int alpha) {
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
	}



	public int getWidth() {
		return (int) dst.width();
	}

	public int getHeight() {
		return (int) dst.height();
	}

}
