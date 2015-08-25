package com.kingscastle.nuzi.towerdefence.effects.animations;


import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;


public class BlackSummonSmokeAnim extends Anim {

	private static ArrayList<Image> smokeimages;

	private final int staticTfb = 50;

	public BlackSummonSmokeAnim( vector loc )
	{
		getLoc().set( loc.x , loc.y - Rpg.fiveDp );
		setTbf(staticTfb);
		loadImages();
		onlyShowIfOnScreen = true;
	}

	void loadImages()
	{
		if( smokeimages == null )
		{
			smokeimages = Assets.loadAnimationImages(R.drawable.black_explosion, 5, 4);
		}
		images = smokeimages;
	}



}
