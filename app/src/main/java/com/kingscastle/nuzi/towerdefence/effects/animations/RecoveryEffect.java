package com.kingscastle.nuzi.towerdefence.effects.animations;


import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Graphics;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;

public class RecoveryEffect extends Anim
{

	private static ArrayList<Image> staticImages = Assets.loadAnimationImages(R.drawable.recovery_effect_large, 5, 6);
	private final int staticTfb = 50;


	public RecoveryEffect( vector loc )
	{
		loadImages();
		setImages( images );
		setLoc( loc );
		setTbf( staticTfb );
		paint = Rpg.getXferAddPaint();
		onlyShowIfOnScreen = true;
	}

	void loadImages(){
		setImages( staticImages );
	}

	@Override
	public void paint( Graphics g , vector v )
	{
		vTemp.set( v );
		vTemp.add( offs );

		Image image = getImage();
		if( image != null ) {
			if (scaleX != 1 || scaleY != 1) {
				if (pScaleX != scaleX || pScaleY != scaleY) {
					dst.set(image.getSrcRect());
					dst.inset((int) (-(dst.width() / 2) * (scaleX - 1)), (int) (-(dst.height() / 2) * (scaleY - 1)));
				}
				dst.offsetTo(0, 0);
				dst.offset((int) vTemp.x - dst.width() / 2, (int) vTemp.y - dst.height() / 2);
				g.drawImage(image, image.getSrcRect(), dst, paint);
			} else
				g.drawImage(image, v.x - image.getWidthDiv2(), v.y - image.getHeightDiv2(), paint);
		}

	}
}
