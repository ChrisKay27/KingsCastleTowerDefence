package com.kingscastle.nuzi.towerdefence.effects.animations;


import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Graphics;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;

public class IlluminatedFog extends Anim
{
	public static final int SMALL = 0;
	public static final int LARGE = 1;

	private static ArrayList<Image> smallStaticImages = Assets.loadAnimationImages(R.drawable.illuminated_fog, 5, 4);
	private static ArrayList<Image> largeStaticImages = Assets.loadAnimationImages( R.drawable.illuminated_fog_large , 5 , 4 );
	private final int staticTfb = 50;



	public IlluminatedFog( vector loc , int size )
	{
		if( size == LARGE )
			setImages( largeStaticImages );
		else
			setImages( smallStaticImages );

		setLoc( loc );
		setTbf( staticTfb );
		paint = Rpg.getXferAddPaint();
		onlyShowIfOnScreen = true;
	}



	@Override
	public void paint( Graphics g , vector v )
	{
		Image image = getImage();
		if( image != null )
			g.drawImage( image , v.x - image.getWidthDiv2() , v.y - image.getHeightDiv2() , paint );
	}
}
