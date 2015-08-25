package com.kingscastle.nuzi.towerdefence.effects.animations;


import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;

public class FireBallAnim extends Anim
{

	private static ArrayList<Image> Wimages, NWimages, Nimages,
	NEimages, Eimages, SEimages, Simages, SWimages;

	private final static int staticTfb = 50;
	private final static int imageId = R.drawable.fireballs;

	static
	{
		Image fireballs = Assets.loadImage(imageId);
		Wimages = Assets.loadAnimationImages( fireballs, 8, 8, 0, 8 , false );
		NWimages = Assets.loadAnimationImages( fireballs, 8, 8, 1, 8 , false );
		Nimages = Assets.loadAnimationImages( fireballs, 8, 8, 2, 8 , false );
		NEimages = Assets.loadAnimationImages( fireballs , 8, 8, 3, 8 , false );
		Eimages = Assets.loadAnimationImages( fireballs , 8, 8, 4, 8 , false );
		SEimages = Assets.loadAnimationImages( fireballs , 8, 8, 5, 8 , false );
		Simages = Assets.loadAnimationImages( fireballs , 8, 8, 6, 8 , false );
		SWimages = Assets.loadAnimationImages( fireballs , 8, 8, 7, 8 , true );
	}


	public FireBallAnim(vector loc , int dir)
	{
		setImages( getImages(dir) );
		setLoc(loc);
		setTbf(staticTfb);
		setLooping(true);
		onlyShowIfOnScreen = true;

		LightEffect le = new LightEffect(this.loc, LightEffect.LightEffectColor.DARK_ORANGE);
		le.setScale(0.5f);
		add(le, true);
	}



	ArrayList<Image> getImages(int dir){
		switch (dir) {
		default:
		case 0:
			if (Wimages == null) {
				Wimages = Assets.loadAnimationImages( imageId , 8, 8, 0, 8);
			}
			return Wimages;
		case 1:
			if (NWimages == null) {
				NWimages = Assets.loadAnimationImages( imageId , 8, 8, 1, 8);
			}
			return NWimages;
		case 2:
			if (Nimages == null) {
				Nimages = Assets.loadAnimationImages( imageId , 8, 8, 2, 8);
			}
			return Nimages;
		case 3:
			if (NEimages == null) {
				NEimages = Assets.loadAnimationImages( imageId , 8, 8, 3, 8);
			}
			return NEimages;
		case 4:
			if (Eimages == null) {
				Eimages = Assets.loadAnimationImages( imageId , 8, 8, 4, 8);
			}
			return Eimages;
		case 5:
			if (SEimages == null) {
				SEimages = Assets.loadAnimationImages( imageId , 8, 8, 5, 8);
			}
			return SEimages;
		case 6:
			if (Simages == null) {
				Simages = Assets.loadAnimationImages( imageId , 8, 8, 6, 8);
			}
			return Simages;
		case 7:
			if (SWimages == null) {
				SWimages = Assets.loadAnimationImages( imageId , 8, 8, 7, 8);
			}
			return SWimages;
		}
	}
}