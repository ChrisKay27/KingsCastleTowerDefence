package com.kingscastle.nuzi.towerdefence.effects.animations;


import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Graphics;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;

public class TorchLightAnim extends Anim
{

	private static ArrayList<Image> staticImages = Assets.loadAnimationImages(R.drawable.torchlight, 8, 8);
	private final int staticTfb = 35;


	public TorchLightAnim( vector loc )
	{
		setImages( staticImages );
		setLoc( loc );
		setTbf( staticTfb );
		setPaint(Rpg.getXferAddPaint());
	}


	@Override
	public void paint( Graphics g , vector v )
	{
		Image image = getImage();
		if( image != null )
			g.drawImage( image , v.x - image.getWidthDiv2() , v.y - image.getHeightDiv2() , paint );
	}
}
