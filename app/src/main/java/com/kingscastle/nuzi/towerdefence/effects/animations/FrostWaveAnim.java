package com.kingscastle.nuzi.towerdefence.effects.animations;


import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Graphics;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;


public class FrostWaveAnim extends Anim {

	private static final ArrayList<Image> staticImages;

	private final int staticTfb = 50;

	static
	{
		staticImages = Assets.loadAnimationImages(R.drawable.frostwave, 5, 5);
	}
	public FrostWaveAnim( vector loc )
	{
		setLoc( loc );
		setTbf( staticTfb );
		setImages( staticImages );
		setLooping( true );
		paint = Rpg.getXferAddPaint();
		onlyShowIfOnScreen = true;
	}

	//	private final Paint paint;

	//	private final Paint normalPaint = new Paint();
	//	private String xFerMode;
	//	private long nextSwitch;
	//
	//	private ArrayList<String> names;
	//	private ArrayList<Paint> paints;

	//private int index ;
	@Override
	public void paint(Graphics g, vector v)
	{
		//		if( nextSwitch < GameTime.getTime() )
		//		{
		//			paint = getNextPaint();
		//
		//			nextSwitch = 2000 + GameTime.getTime();
		//		}

		Image image = getImage();

		if( image != null )
		{
			g.drawImage( image , v.x - image.getWidthDiv2() , v.y - image.getHeightDiv2() , paint );
			//g.drawString( xFerMode , v.x + image.getWidthDiv2() , v.y + image.getHeightDiv2() + Rpg.twentyDp , normalPaint );
		}

	}


	//	{
	//		paints = new ArrayList<Paint>();
	//		names = new ArrayList<String>();
	//		Paint p;
	//
	//		p = new Paint();
	//		p.setXfermode(new PorterDuffXfermode( Mode.DST_OVER ));
	//		paints.add( p );
	//		names.add( "DST_OVER" );
	//
	//		p = new Paint();
	//		p.setXfermode(new PorterDuffXfermode( Mode.DST_IN ));
	//		paints.add( p );
	//		names.add( "DST_IN" );
	//
	//		p = new Paint();
	//		p.setXfermode(new PorterDuffXfermode( Mode.DST_OUT ));
	//		paints.add( p );
	//		names.add( "DST_OUT" );
	//
	//		p = new Paint();
	//		p.setXfermode(new PorterDuffXfermode( Mode.DST ));
	//		paints.add( p );
	//		names.add( "DST" );
	//
	//		p = new Paint();
	//		p.setXfermode(new PorterDuffXfermode( Mode.SRC_ATOP ));
	//		paints.add( p );
	//		names.add( "SRC_ATOP" );
	//
	//		p = new Paint();
	//		p.setXfermode(new PorterDuffXfermode( Mode.SRC_OVER ));
	//		paints.add( p );
	//		names.add( "SRC_OVER" );
	//
	//		p = new Paint();
	//		p.setXfermode(new PorterDuffXfermode( Mode.SRC_IN ));
	//		paints.add( p );
	//		names.add( "SRC_IN" );
	//
	//		p = new Paint();
	//		p.setXfermode(new PorterDuffXfermode( Mode.SRC_OUT ));
	//		paints.add( p );
	//		names.add( "SRC_OUT" );
	//
	//		p = new Paint();
	//		p.setXfermode(new PorterDuffXfermode( Mode.XOR ));
	//		paints.add( p );
	//		names.add( "XOR" );
	//
	//		p = new Paint();
	//		p.setXfermode(new PorterDuffXfermode( Mode.ADD ));
	//		paints.add( p );
	//		names.add( "ADD" );
	//
	//		p = new Paint();
	//		p.setXfermode(new PorterDuffXfermode( Mode.SCREEN ));
	//		paints.add( p );
	//		names.add( "SCREEN" );
	//
	//		p = new Paint();
	//		names.add( "No Xfer Mode" );
	//		paints.add( p );
	//
	//		index = (int) (Math.random()*paints.size());
	//
	//	}



	//	public Paint getNextPaint()
	//	{
	//		Paint p = paints.get( index );
	//		xFerMode = names.get( index++ );
	//		index = index%paints.size();
	//		return p;
	//	}


	//	public Paint getXferAddPaint()
	//	{
	//		double rand = Math.random();
	//
	//		if( rand < 0.05 )
	//		{
	//			Paint p = new Paint();
	//			//p.setXfermode(new PorterDuffXfermode( Mode.DST_OVER ));
	//			xFerMode = "No Xfer Mode";
	//			return p;
	//		}
	//		if( rand < 0.1 )
	//		{
	//			Paint p = new Paint();
	//			p.setXfermode(new PorterDuffXfermode( Mode.DST_OVER ));
	//			xFerMode = "DST_OVER";
	//			return p;
	//		}
	//		if( rand < 0.2 )
	//		{
	//			Paint p = new Paint();
	//			p.setXfermode(new PorterDuffXfermode( Mode.DST_IN ));
	//			xFerMode = "DST_IN";
	//			return p;
	//		}
	//		if( rand < 0.3 )
	//		{
	//			Paint p = new Paint();
	//			p.setXfermode(new PorterDuffXfermode( Mode.DST_OUT ));
	//			xFerMode = "DST_OUT";
	//			return p;
	//		}
	//		if( rand < 0.4 )
	//		{
	//			Paint p = new Paint();
	//			p.setXfermode(new PorterDuffXfermode( Mode.DST ));
	//			xFerMode = "DST"; return p;
	//		}
	//		if( rand < 0.5 )
	//		{
	//			Paint p = new Paint();
	//			p.setXfermode(new PorterDuffXfermode( Mode.SRC_ATOP ));
	//			xFerMode = "SRC_ATOP"; return p;
	//		}
	//		if( rand < 0.6 )
	//		{
	//			Paint p = new Paint();
	//			p.setXfermode(new PorterDuffXfermode( Mode.SRC_OVER ));
	//			xFerMode = "SRC_OVER"; return p;
	//		}
	//		if( rand < 0.7 )
	//		{
	//			Paint p = new Paint();
	//			p.setXfermode(new PorterDuffXfermode( Mode.SRC_IN ));
	//			xFerMode = "SRC_IN"; return p;
	//		}
	//		if( rand < 0.8 )
	//		{
	//			Paint p = new Paint();
	//			p.setXfermode(new PorterDuffXfermode( Mode.SRC_OUT ));
	//			xFerMode = "SRC_OUT"; return p;
	//		}
	//		if( rand < 0.9 )
	//		{
	//			Paint p = new Paint();
	//			p.setXfermode(new PorterDuffXfermode( Mode.XOR ));
	//			xFerMode = "XOR";
	//			return p;
	//		}
	//
	//		Paint p = new Paint();
	//		p.setXfermode(new PorterDuffXfermode( Mode.DST_ATOP ));
	//		xFerMode = "DST_ATOP";
	//		return p;
	//	}








}
