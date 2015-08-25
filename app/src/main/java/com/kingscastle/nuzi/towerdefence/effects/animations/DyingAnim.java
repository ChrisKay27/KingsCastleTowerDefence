package com.kingscastle.nuzi.towerdefence.effects.animations;


import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;

public class DyingAnim extends Anim {

	private static ArrayList<Image> staticImages;
	private final int staticTfb = 150;


	public DyingAnim(vector loc)
	{
		loadImages();
		setImages(staticImages);
		setLoc(loc);
		setTbf(staticTfb);
		setAliveTime( 30000 );
		onlyShowIfOnScreen = true;
	}

	@Override
	public Image getImage()
	{
		return images.get( currentImageIndex );
	}



	@Override
	public boolean act()
	{
		if( startTime + aliveTime < GameTime.getTime() )
			over = true;

		if( currentImageIndex == images.size()-1 )		{
		}
		else if( nextImageChange + tbf < GameTime.getTime() )		{
			++currentImageIndex;
			nextImageChange = GameTime.getTime();;
		}
		return over;
	}





	void loadImages()
	{
		if( staticImages != null )
			return;

		staticImages = Assets.loadAnimationImages(R.drawable.genericexplodingperson, 3);
		staticImages.add( Assets.loadImage( R.drawable.holy_corpse ) );

	}



}
