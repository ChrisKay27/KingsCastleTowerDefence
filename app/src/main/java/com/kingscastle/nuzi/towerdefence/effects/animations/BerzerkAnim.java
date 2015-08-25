package com.kingscastle.nuzi.towerdefence.effects.animations;


import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;

public class BerzerkAnim extends Anim
{

	private static ArrayList<Image> images;
	private final int staticTfb = 50;

	static
	{
		images = Assets.loadAnimationImages(R.drawable.berzerk_rune, 4, 1);
	}

	public BerzerkAnim( vector loc )
	{
		loadImages();
		setImages(images);
		setLoc(loc);
		setTbf(staticTfb);
		onlyShowIfOnScreen = true;
		//setPaint( Rpg.getXferAddPaint() );
	}

	void loadImages()
	{
		if (images == null)
		{
			images = Assets.loadAnimationImages( R.drawable.berzerk_rune , 4 , 1 );
		}

	}

}
