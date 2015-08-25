package com.kingscastle.nuzi.towerdefence.gameElements.movement.pathing;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.framework.Graphics;


class GridPainter
{
	private static final Paint red;
	//private static final Vector offs;
	private static final String TAG = "GridPainter";

	static
	{
		red = new Paint();
		red.setColor( Color.RED );
		//red.setXfermode( new PorterDuffXfermode( PorterDuff.Mode.DST_ATOP ));
		//	offs = new Vector();
	}



	public static void paint( Graphics g , Rect onThisArea , RectF reducedTileArea , Grid grid )
	{
		//////Log.d( TAG , "paint()" );

		//offs.set( onThisArea.left , onThisArea.top );

		float reducedTileWidth = reducedTileArea.width();
		float reducedTileHeight = reducedTileArea.height();
		////Log.d( TAG , "reducedTileSize=" + reducedTileSize );

		boolean[][] gridTiles = grid.getGridTiles();


		int horzTiles = gridTiles.length;
		int vertTiles = gridTiles[0].length;
		////Log.d( TAG , "horzTiles=" + horzTiles );
		////Log.d( TAG , "vertTiles=" + vertTiles );

		float xOffs = 0;
		float yOffs = 0;

		////Log.d( TAG , "starting drawing tiles..." );
		for( int i = 0 ; i < horzTiles ; ++i )
		{
			for( int j = 0 ; j < vertTiles ; ++j )
			{
				if( !gridTiles[i][j] )
				{
					////Log.d( TAG , "drawing rect at i,j=" + i + "," + j );
					float onScreenX = xOffs + onThisArea.left;
					float onScreenY = yOffs + onThisArea.top;
					reducedTileArea.offsetTo( onScreenX , onScreenY );
					////Log.d( TAG , "onScreenX=" + onScreenX + " onScreenY=" + onScreenY );

					g.drawRect( reducedTileArea , red );
				}

				yOffs += reducedTileHeight;
			}

			yOffs = 0;
			xOffs += reducedTileWidth;
		}
		////Log.d( TAG , "end drawing tiles..." );


	}

















}
