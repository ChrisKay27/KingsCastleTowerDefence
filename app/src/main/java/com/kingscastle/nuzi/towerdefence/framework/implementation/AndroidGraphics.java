package com.kingscastle.nuzi.towerdefence.framework.implementation;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.framework.Graphics;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.ui.ImageLabel;
import com.kingscastle.nuzi.towerdefence.ui.TextLabel;

import java.io.IOException;
import java.io.InputStream;

public class AndroidGraphics implements Graphics
{
	private Image rangeCircle ;
	private AssetManager assets;
	private Bitmap frameBuffer;
	private Canvas canvas;
	private final Paint paint;
	private Resources rm;
	private final Rect srcRect = new Rect();
	private final Rect dstRect = new Rect();
	private final RectF dstRectF = new RectF();

	private final int width;
	private final int height;
	private final int widthDiv2,heightDiv2;

	public AndroidGraphics( Canvas canvas )
	{
		this.canvas = canvas;
		width = canvas.getWidth();
		height = canvas.getHeight();
		widthDiv2 = width/2;
		heightDiv2 = height/2;
		paint = new Paint();
	}


	public AndroidGraphics( AssetManager assets , Resources rm , int frameBufferWidth , int frameBufferHeight )
	{
		this.assets = assets;
		this.rm = rm;

		rangeCircle = newImage(R.drawable.range_circle,ImageFormat.RGB565);

		//	this.frameBuffer = frameBuffer;
		//this.canvas = new Canvas(frameBuffer);
		paint = new Paint();

		width = frameBufferWidth;
		height = frameBufferHeight;
		widthDiv2 = getWidth() / 2;
		heightDiv2 = getHeight() / 2;
	}



	@Override
	public Image newImage( int id , Graphics.ImageFormat format )
	{

		Config config = null;
		if (format == ImageFormat.RGB565) {
			config = Config.RGB_565;
		} else if (format == ImageFormat.ARGB4444) {
			config = Config.ARGB_4444;
		} else {
			config = Config.ARGB_8888;
		}

		Options options = new Options();
		options.inPreferredConfig = config;

		return new AndroidImage(BitmapFactory.decodeResource(rm, id, options),
				format);

	}


	@Override
	public Image newImage(String fileName, ImageFormat format) // ImageFormat is a class that a tutorial included to wrap the Config class
	{
		Config config = null;
		if (format == ImageFormat.RGB565)
		{
			config = Config.RGB_565;
		} else if (format == ImageFormat.ARGB4444)
		{
			config = Config.ARGB_4444;
		} else
		{
			config = Config.ARGB_8888;
		}

		Options options = new Options();
		options.inPreferredConfig = config;

		InputStream in = null;
		Bitmap bitmap = null;
		try
		{
			in = assets.open(fileName);
			bitmap = BitmapFactory.decodeStream(in, null, options);
			if (bitmap == null)
			{
				throw new RuntimeException("Couldn't load bitmap from asset '"
						+ fileName + "'");
			}
		}
		catch (IOException e)
		{
			throw new RuntimeException("Couldn't load bitmap from asset '"
					+ fileName + "'");
		}
		finally
		{
			if (in != null)
			{
				try
				{
					in.close();
				}
				catch (IOException e)
				{
				}
			}
		}

		if (bitmap.getConfig() == Config.RGB_565)
		{
			format = ImageFormat.RGB565;
		}
		else if (bitmap.getConfig() == Config.ARGB_4444)
		{
			format = ImageFormat.ARGB4444;
		}
		else
		{
			format = ImageFormat.ARGB8888;
		}

		return new AndroidImage(bitmap, format);
	}



	@Override
	public void clearScreen(int color)
	{
		canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8,
				(color & 0xff));
	}


	@Override
	public void drawCircle(vector v, float radius) {
		drawImage(rangeCircle, v.x, v.y, radius, radius);
	}


	@Override
	public void drawLine(float x, float y, float x2, float y2, int color)
	{
		this.drawLine(x, y, x2, y2, color, 1);
	}




	@Override
	public void drawLine(float x, float y, float x2, float y2, int color , float width)
	{
		if ( width < 0.5 )
		{
			throw new IllegalArgumentException("possibly using to small of a width");
		}
		paint.setColor( color );
		paint.setStrokeWidth( width );
		paint.setStyle( Style.STROKE );
		paint.setAntiAlias( true );
		canvas.drawLine(x, y, x2, y2, paint);
	}




	@Override
	public void drawRoundRect( RectF rect , int color )
	{
		if( rect == null )
		{
			throw new IllegalArgumentException(" rect == null ");
		}
		paint.setColor(color);
		paint.setStyle(Style.FILL);

		canvas.drawRoundRect(rect, 10, 10, paint);
	}

	@Override
	public void drawRoundRect( float left , float top , float right , float bottom , int color )
	{

		paint.setColor(color);
		paint.setStyle(Style.FILL);
		dstRectF.set(left, top, right, bottom);

		canvas.drawRoundRect( dstRectF , 10 , 10 , paint );
	}


	@Override
	public void drawRoundRect( float left , float top , float right , float bottom , Paint paint )
	{
		Paint p = paint;
		if( p == null )
		{
			p = this.paint;
		}

		p.setStyle(Style.FILL);
		dstRectF.set( left , top , right , bottom );

		canvas.drawRoundRect( dstRectF , 10 , 10 , p );
	}



	@Override
	public void drawRoundRectBorder( RectF rect , int color , float borderWidth)
	{
		if( rect == null )
		{
			throw new IllegalArgumentException(" rect == null ");
		}
		paint.setColor(color);
		paint.setStrokeWidth(borderWidth);
		paint.setStyle(Style.STROKE);

		canvas.drawRoundRect( rect , 10 , 10 , paint );
	}

	@Override
	public void drawRoundRectBorder( float left , float top , float right , float bottom , int color , float borderWidth)
	{
		paint.setColor(color);
		paint.setStrokeWidth(borderWidth);
		paint.setStyle(Style.STROKE);

		dstRectF.set(left, top, right, bottom);

		canvas.drawRoundRect( dstRectF , 10 , 10 , paint );
	}




	@Override
	public void drawRectBorder( float x , float y , float width , float height , int color , float borderWidth)
	{
		paint.setColor(color);
		paint.setStrokeWidth(borderWidth);
		paint.setStyle(Style.STROKE);

		canvas.drawRect(x, y, x + width, y + height, paint);

	}



	@Override
	public void drawRectBorder( RectF r , int color , float borderWidth )
	{
		paint.setColor( color );
		paint.setStrokeWidth( borderWidth );
		paint.setStyle(Style.STROKE);
		canvas.drawRect( r.left , r.top , r.right , r.bottom , paint);
	}


	@Override
	public void drawRectBorder( Rect r , int color , float borderWidth )
	{
		paint.setColor( color );
		paint.setStrokeWidth( borderWidth );
		paint.setStyle(Style.STROKE);
		canvas.drawRect( r , paint );
	}



	@Override
	public void drawRectBorder( RectF rect , vector offset , int color , float borderWidth )
	{
		if( rect == null )
		{
			throw new IllegalArgumentException(" rect == null ");
		}
		if( offset == null )
		{
			throw new IllegalArgumentException(" offset == null ");
		}
		paint.setColor( color );
		paint.setStrokeWidth( borderWidth );
		paint.setStyle( Style.STROKE );
		canvas.drawRect( rect.left + offset.x, rect.top + offset.y , rect.right + offset.x,
				rect.bottom + offset.y , paint );
	}



	@Override
	public void drawRect( float x, float y, float width, float height, int color )
	{
		paint.setColor(color);
		paint.setStyle( Style.FILL );
		canvas.drawRect( x , y , x + width , y + height , paint);
	}



	@Override
	public void drawRect( RectF r , vector offset , int color )
	{
		if( r == null )
		{
			throw new IllegalArgumentException(" r == null ");
		}
		if( offset == null )
		{
			throw new IllegalArgumentException(" offset == null ");
		}

		paint.setColor( color );
		paint.setStyle( Style.FILL );
		canvas.drawRect( r.left + offset.x , r.top + offset.y , r.right + offset.x , r.bottom + offset.y,
				paint );
	}




	@Override
	public void drawRect( Rect r , vector offset , int color )
	{
		if( r == null )
		{
			throw new IllegalArgumentException(" r == null ");
		}

		paint.setColor(color);
		paint.setStyle(Style.FILL);

		if( offset == null )
		{
			canvas.drawRect( r.left , r.top , r.right , r.bottom , paint );
		}
		else
		{
			canvas.drawRect( r.left + offset.x , r.top + offset.y , r.right+offset.x , r.bottom+offset.y,
					paint );
		}
	}

	@Override
	public void drawRect( RectF r , vector offset , Paint paint )
	{
		float x = offset.x;
		float y = offset.y;
		canvas.drawRect(r.left + x, r.top + y, r.right + x, r.bottom + y, paint);
	}

	@Override
	public void drawRect( Rect r , vector offset , Paint paint )
	{
		float x = offset.x;
		float y = offset.y;
		canvas.drawRect( r.left + x , r.top + y , r.right + x , r.bottom + y, paint );
	}

	@Override
	public void drawRect( float x, float y, float width, float height , Paint paint)
	{
		Paint p = paint;
		if( paint == null )
			p = this.paint;

		canvas.drawRect( x , y , x + width , y + height , p );
	}


	@Override
	public void drawRect( RectF rectF , Paint paint )
	{
		if( rectF == null )
		{
			throw new IllegalArgumentException(" r == null ");
		}

		Paint p = paint;
		if( paint == null )
		{
			p = this.paint;
		}

		canvas.drawRect( rectF , p );
	}



	@Override
	public void drawRect(Rect rect, Paint paint)
	{
		if( rect == null )
		{
			throw new IllegalArgumentException(" r == null ");
		}

		Paint p = paint;
		if( paint == null )
		{
			p = this.paint;
		}
		canvas.drawRect( rect , p );
	}



	@Override
	public void drawRect(Rect miniMapArea, int color )
	{
		paint.setColor( color );
		paint.setStyle( Style.FILL );

		canvas.drawRect( miniMapArea , paint );

	}








	@Override
	public void drawARGB(int a, int r, int g, int b)
	{
		paint.setStyle(Style.FILL);
		canvas.drawARGB(a, r, g, b);
	}




	@Override
	public void drawString( String text, float x, float y, Paint paint )
	{
		if( text == null )
		{
			throw new IllegalArgumentException(" text == null ");
		}

		Paint p = paint;
		if( paint == null )
		{
			p = this.paint;
		}

		canvas.drawText(text, x, y, p);
	}


	@Override
	public void drawString( String text , float x , float y , vector offset , Paint paint )
	{
		if( text == null )
		{
			throw new IllegalArgumentException(" text == null ");
		}

		Paint p = paint;
		if( paint == null )
		{
			p = this.paint;
		}


		if ( offset == null )
		{
			canvas.drawText(text, x , y , p);
		}
		else
		{
			canvas.drawText(text, x + offset.x , y + offset.y , p);
		}

	}



	@Override
	public void drawTextLabel( TextLabel tl )
	{
		if( tl == null )
		{
			throw new IllegalArgumentException(" tl == null ");
		}
		if( tl.getPaint() == null )
		{
			throw new IllegalArgumentException(" tl.getPaint() == null ");
		}
		if( tl.getLoc() == null )
		{
			throw new IllegalArgumentException(" tl.getLoc() == null ");
		}
		if( tl.getMsg() == null )
		{
			throw new IllegalArgumentException(" tl.getMsg() == null ");
		}
		if( tl.isVisible() )
		{
			canvas.drawText( tl.getMsg(), tl.getLoc().x,  tl.getLoc().y, tl.getPaint() );
		}
	}

	@Override
	public void drawTextLabel( TextLabel tl , float xoffs , float yoffs)
	{
		if( tl == null )
			throw new IllegalArgumentException(" tl == null ");

		if( tl.getPaint() == null )
			throw new IllegalArgumentException(" tl.getPaint() == null ");

		if( tl.getLoc() == null )
			throw new IllegalArgumentException(" tl.getLoc() == null ");

		if( tl.getMsg() == null )
			throw new IllegalArgumentException(" tl.getMsg() == null ");

		if( tl.isVisible() )
			canvas.drawText( tl.getMsg(), tl.getLoc().x + xoffs,  tl.getLoc().y + yoffs, tl.getPaint() );

	}


	@Override
	public void drawImage( Image image , int srcLeft , int srcTop ,
			int srcRight , int srcBottom , int dstLeft , int dstTop ,
			int dstRight , int dstBottom )
	{
		if( image == null )
			throw new IllegalArgumentException(" image == null ");

		srcRect.left = srcLeft;
		srcRect.top = srcTop;
		srcRect.right = srcRight ;
		srcRect.bottom = srcBottom;

		dstRect.left = dstLeft;
		dstRect.top = dstTop;
		dstRect.right = dstRight;
		dstRect.bottom = dstBottom;

		canvas.drawBitmap(((AndroidImage) image).bitmap, srcRect, dstRect, paint );
	}

	@Override
	public void drawImage( Image image , int x , int y , int srcX , int srcY ,
			int srcWidth , int srcHeight )
	{
		if( image == null )
			throw new IllegalArgumentException(" image == null ");

		srcRect.left = srcX;
		srcRect.top = srcY;
		srcRect.right = srcX + srcWidth;
		srcRect.bottom = srcY + srcHeight;

		dstRect.left = x;
		dstRect.top = y;
		dstRect.right = x + srcWidth;
		dstRect.bottom = y + srcHeight;

		canvas.drawBitmap(((AndroidImage) image).bitmap, srcRect, dstRect, paint );
	}

	@Override
	public void drawImage(Image image, Rect src, Rect dst)
	{
		drawImage( image,  src,  dst,  paint);
		//		if( image == null )
		//			throw new IllegalArgumentException(" image == null ");
		//
		//		if( src == null )
		//			throw new IllegalArgumentException(" src == null ");
		//
		//		if( dst == null )
		//			throw new IllegalArgumentException(" dst == null ");
		//
		//		canvas.drawBitmap(((AndroidImage) image).bitmap, src, dst, paint );
	}

	@Override
	public void drawImage(Image image, Rect src, Rect dst, Paint paint) {
		if( image == null )
			throw new IllegalArgumentException(" image == null ");

		if( src == null )
			throw new IllegalArgumentException(" src == null ");

		if( dst == null )
			throw new IllegalArgumentException(" dst == null ");

		if( paint == null )
			paint = this.paint;

		canvas.drawBitmap(((AndroidImage) image).bitmap, src, dst, paint );
	}


	@Override
	public void drawImage( Image image , int x , int y )
	{
		if( paint == null )
			throw new IllegalStateException( " paint == null " );

		if( image == null )
			throw new IllegalArgumentException( " image == null " );

		if(  image.getBitmap() == null )
			throw new IllegalArgumentException( "  image.getBitmap() == null " );

		if( canvas == null )
			throw new IllegalStateException( "  canvas == null  " );


		Rect dstRect = image.getDstRect();
		dstRect.offsetTo( x , y );
		canvas.drawBitmap( image.getBitmap() , image.getSrcRect() , dstRect , paint );
	}


	@Override
	public void drawImage( Image image , float x , float y )
	{
		canvas.drawBitmap( image.getBitmap() , x , y , paint );
	}



	@Override
	public void drawImage( Image image, float x, float y, Paint paint )
	{
		if( image == null )
		{
			return;
			//throw new IllegalArgumentException( " image == null " );
		}
		Paint p = paint;
		if ( p == null )
			p = this.paint;


		canvas.drawBitmap( image.getBitmap() , x , y , p );
	}




	@Override
	public void drawImage( Image image , float x , float y , vector offset )
	{
		if( image == null )
			throw new IllegalArgumentException( " image == null " );

		if( offset == null )
			drawImage(image , (int) x , (int) y );
		else
			canvas.drawBitmap( image.getBitmap() , x+offset.x , y+offset.y , paint );
	}


	@Override
	public void drawImage( Image image , float x , float y , vector offset , Paint paint )
	{
		if( image == null )
			throw new IllegalArgumentException( " image == null " );

		Paint p = paint;
		if ( p == null )
			p = this.paint;

		if( offset == null )
			drawImage( image , (int) x , (int) y , p );
		else if( image != null )
			canvas.drawBitmap( image.getBitmap() , x+offset.x , y+offset.y , p );

	}


	@Override
	public void drawImage(Image img, float dstCenterX, float dstCenterY, float dst_width, float dst_height) {
		dstRect.set((int) (dstCenterX-dst_width/2) ,(int)(dstCenterY-dst_height/2),(int)(dstCenterX+dst_width/2),(int)(dstCenterY+dst_height/2));
		drawImage(img, img.getSrcRect(), dstRect);
	}



	@Override
	public void drawImageLabel(ImageLabel il)
	{
		if( il == null )
			throw new IllegalArgumentException( " il == null " );

		drawImageLabel( il , 0 , 0 );
	}

	@Override
	public void drawImageLabel(ImageLabel il , float xoffs , float yoffs )
	{
		if( il == null )
			throw new IllegalArgumentException( " il == null " );

		if( il.getPaint() != null )
		{
			switch(il.getPaint().getTextAlign())
			{
			default:
			case LEFT: canvas.drawBitmap(il.getImage().getBitmap(), il.getLoc().x+xoffs,  il.getLoc().y+yoffs, il.getPaint()); return;
			case RIGHT: canvas.drawBitmap(il.getImage().getBitmap(), il.getLoc().x-il.getImage().getWidth()+xoffs,  il.getLoc().y+yoffs, il.getPaint()); return;
			case CENTER: canvas.drawBitmap(il.getImage().getBitmap(), il.getLoc().x-il.getImage().getWidthDiv2()+xoffs,  il.getLoc().y+yoffs, il.getPaint()); return;
			}
		}

		canvas.drawBitmap(il.getImage().getBitmap(), il.getLoc().x + xoffs,  il.getLoc().y + yoffs, paint );
	}


	void drawScaledImage(Image image, int x, int y, int width,
			int height, int srcX, int srcY, int srcWidth, int srcHeight)
	{
		if( image == null )
			throw new IllegalArgumentException( " il == null " );

		srcRect.left = srcX;
		srcRect.top = srcY;
		srcRect.right = srcX + srcWidth;
		srcRect.bottom = srcY + srcHeight;

		dstRect.left = x;
		dstRect.top = y;
		dstRect.right = x + width;
		dstRect.bottom = y + height;

		canvas.drawBitmap(((AndroidImage) image).bitmap, srcRect, dstRect, paint );

	}



	@Override
	public void drawImageOnWholeScreen ( Image image )
	{
		if( image == null )
			throw new IllegalArgumentException( " il == null " );

		drawScaledImage( image , 0 , 0 , width,
				height , 0 , 0 , image.getWidth(),
				image.getHeight());
	}





	@Override
	public int getWidth() 							{		return width;		}
	@Override
	public int getHeight()							 {		return height;		}
	@Override
	public int getWidthDiv2()							 {		return widthDiv2;		}
	@Override
	public int getHeightDiv2()							 {		return heightDiv2;		}




	@Override
	public void setCanvas(Canvas c)
	{
		canvas=c;
	}

	@Override
	public Canvas getCanvas()
	{
		return canvas;
	}


	@Override
	public void setBitmap( Bitmap framebuff)
	{
		frameBuffer = framebuff;
	}

	@Override
	public Bitmap getBitmap()
	{
		return frameBuffer;
	}



	@Override
	public void drawPaint(Paint p) {
		canvas.drawPaint(p);
	}



	@Override
	public void save()
	{
		if( canvas != null )
		{
			canvas.save();
		}

	}


	@Override
	public void clipRect(Rect area)
	{
		if( canvas != null )
		{
			canvas.clipRect( area );
		}

	}










}
