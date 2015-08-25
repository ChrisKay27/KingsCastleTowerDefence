package com.kingscastle.nuzi.towerdefence.effects.animations;

import android.graphics.Paint;

import com.kingscastle.nuzi.towerdefence.framework.Graphics;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;

public class RisingHeartsAnim extends Anim
{

	private static ArrayList<Image> staticImages;
	private final int staticTfb = 100;


	public RisingHeartsAnim( vector loc )
	{
		loadImages();
		setLoc( loc );
		setTbf( staticTfb );
		paint = new Paint();
		onlyShowIfOnScreen = true;
	}

	void loadImages()
	{
		if( staticImages == null )
		{
			//staticImages = Assets.loadAnimationImages()R.drawable.rising_hearts, 3, 3);
		}
		setImages( staticImages );
	}


	@Override
	public void paint( Graphics g , vector v )
	{
		Image image = getImage();
		if( image != null )
		{
			g.drawImage( image , v.x - image.getWidthDiv2() , v.y - image.getHeightDiv2() , paint );
		}

	}
}
