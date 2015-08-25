package com.kingscastle.nuzi.towerdefence.effects.animations;

import android.graphics.Color;
import android.graphics.Paint;

import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Graphics;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.gameUtils.CoordConverter;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;

public class LaserAnim extends Anim
{

	private static ArrayList<Image> staticImages;
	private final int staticTfb = 50;

	private final vector to;
	private vector temp = new vector();

	public LaserAnim( vector from , vector to )
	{
		loadImages();
		this.to = to;
		setLoc( from );
		setTbf( staticTfb );
		paint = new Paint();
		setAliveTime( 1000 );
		setLooping( true );
		onlyShowIfOnScreen = true;
	}

	void loadImages()
	{
		if( staticImages == null )
			staticImages = Assets.loadAnimationImages(R.drawable.small_laser_strike, 4, 4);

		setImages( staticImages );
	}


	@Override
	public void paint( Graphics g , vector v , CoordConverter cc )
	{
		temp = cc.getCoordsMapToScreen( to , temp );

		g.drawLine( v.x + offs.x , v.y + offs.y , temp.x , temp.y , Color.RED , 2 );

		if( addedBehind != null )
			for(int i = addedBehind.size() - 1 ; i > -1 ; --i )
				addedBehind.get( i ).paint( g , v );


		Image image = getImage();
		if( image != null )
		{
			g.drawImage( image , v.x    - image.getWidthDiv2() , v.y    - image.getHeightDiv2() , offs , paint );
			g.drawImage( image , temp.x - image.getWidthDiv2() , temp.y - image.getHeightDiv2() , paint );
		}

		if( addedInFront != null )
			for( int i = addedInFront.size() - 1 ; i > -1 ; --i )
				addedInFront.get(i).paint( g , v );

	}
}
