package com.kingscastle.nuzi.towerdefence.framework;

import android.graphics.Bitmap;
import android.graphics.Rect;


public interface
		Image {
	int getWidth();
	int getHeight();
	Graphics.ImageFormat getFormat();
	void dispose();
	Bitmap getBitmap();
	void setBitmap(Bitmap bitmap);
	int getWidthDiv2();
	int getHeightDiv2();
	boolean isDisposed();
	Rect getSrcRect();
	Rect getDstRect();
}