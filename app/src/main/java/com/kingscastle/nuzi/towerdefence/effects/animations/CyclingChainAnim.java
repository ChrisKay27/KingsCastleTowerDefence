package com.kingscastle.nuzi.towerdefence.effects.animations;


import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Graphics;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;

public class CyclingChainAnim extends Anim
{

	private static ArrayList<Image> staticImages;
	private final int staticTfb = 50;


	public CyclingChainAnim( vector loc )
	{
		loadImages();
		setLoc( loc );
		setTbf( staticTfb );
		paint = Rpg.getXferAddPaint();
		onlyShowIfOnScreen = true;
	}

	void loadImages()
	{
		if( staticImages == null )
		{
			staticImages = Assets.loadAnimationImages(R.drawable.cycling_chain, 5, 4);
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
