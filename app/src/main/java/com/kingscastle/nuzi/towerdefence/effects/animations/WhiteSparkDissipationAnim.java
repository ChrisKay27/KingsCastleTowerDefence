package com.kingscastle.nuzi.towerdefence.effects.animations;


import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Graphics;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;


public class WhiteSparkDissipationAnim extends Anim
{

	private static ArrayList<Image> staticImages;

	private final int staticTfb = 10;


	public WhiteSparkDissipationAnim(vector loc)
	{
		setLoc(loc);
		setTbf(staticTfb);
		loadImages();
		paint = Rpg.getXferAddPaint();
		onlyShowIfOnScreen = true;
	}

	void loadImages() {
		if( staticImages == null )
		{
			staticImages= Assets.loadAnimationImages(R.drawable.effect_white_spark_dissipation, 5, 5);
		}
		images = staticImages;
	}

	@Override
	public void paint(Graphics g, vector v)
	{
		Image image = getImage();

		if( image != null )
		{
			g.drawImage( image , v.x - image.getWidthDiv2() , v.y - image.getHeightDiv2() , paint );
		}

	}

}
