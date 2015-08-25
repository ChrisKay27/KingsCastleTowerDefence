package com.kingscastle.nuzi.towerdefence.framework;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.ui.ImageLabel;
import com.kingscastle.nuzi.towerdefence.ui.TextLabel;


public interface Graphics {


	public static enum ImageFormat {
		ARGB8888, ARGB4444, RGB565
	}

	public Image newImage(String fileName, ImageFormat format);

	public void clearScreen(int color);

	public void drawPaint(Paint p);

	void drawCircle(vector v, float radius);

	public void drawLine(float x, float y, float x2, float y2, int color);




	public void drawRect(float x, float y, float width, float height, int color);

	public void drawRect(RectF area, vector offset, int color);

	public void drawRect(float x, float y, float width, float height, Paint paint);

	public void drawRect(RectF rectF, Paint paint);

	public void drawRect(Rect rect, Paint paint);

	public void drawRect(Rect miniMapArea, int black);

	public void drawRect(Rect rect, vector v, int color);

	public void drawRect(Rect rect, vector v, Paint paint);

	public void drawRect(RectF rectF, vector v, Paint paint);


	public void drawRectBorder(RectF r, vector offset, int color, float borderWidth);

	public void drawRectBorder(float x, float y, float width, float height, int color,
							   float borderWidth);

	public void drawRectBorder(Rect rect, int yellow, float borderWidth);




	public void drawRoundRect(float left, float top, float right, float bottom, Paint paint);

	public void drawRoundRect(RectF rect, int color);

	public void drawRoundRect(float left, float top, float right, float bottom, int color);



	public void drawRectBorder(RectF selectionArea, int color, float borderWidth);

	public void drawRoundRectBorder(RectF rect, int color, float borderWidth);

	public void drawRoundRectBorder(float left, float top, float right, float bottom,
									int color, float borderWidth);






	void drawImage(Image Image, int srcLeft, int srcTop, int srcRight,
				   int srcBottom, int dstLeft, int dstTop, int dstRight,
				   int dstBottom);


	public void drawImage(Image image, int x, int y, int srcX, int srcY,
						  int srcWidth, int srcHeight);

	public void drawImage(Image Image, int x, int y);

	public void drawImage(Image fogtile, float xOffs, float yOffs);

	public void drawImage(Image image, float left, float top, Paint paint);

	public void drawImage(Image image, float left, float top, vector offset);

	public void drawImage(Image image, Rect src, Rect dst);
	public void drawImage(Image image, Rect srcRect, Rect dst, Paint paint);

	void drawImage(Image img, float x, float y, float width, float height);


	public void drawImageOnWholeScreen(Image loadingScreen);

	public void drawString(String text, float x, float y, Paint paint);

	public int getWidth();

	public int getHeight();

	public void drawARGB(int i, int j, int k, int l);




	public Bitmap getBitmap();

	public Image newImage(int id, ImageFormat rgb565);



	public int getWidthDiv2();

	public int getHeightDiv2();

	void drawLine(float x, float y, float x2, float y2, int color, float width);

	void drawTextLabel(TextLabel tl);

	void drawTextLabel(TextLabel tl, float xoffs, float yoffs);

	void drawImageLabel(ImageLabel il);

	void drawImageLabel(ImageLabel il, float xoffs, float yoffs);



	public void setCanvas(Canvas c);

	public Canvas getCanvas();



	public void drawString(String string, float centerX, float top, vector offset,
						   Paint paint);

	public void drawImage(Image currentImage, float f, float g, vector offset,
						  Paint dstoverpaint);

	void setBitmap(Bitmap framebuff);

	public void save();

	public void clipRect(Rect area);












}
