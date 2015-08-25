package com.kingscastle.nuzi.towerdefence.effects.animations;


import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;

public class FireHitAnim extends Anim {

	private static ArrayList<Image> boomImages;

	private final int staticTfb=10;

	public FireHitAnim(vector loc) {
		setLoc(loc);
		setTbf(staticTfb);
		loadImages();
		onlyShowIfOnScreen = true;
	}

	void loadImages() {
		if( boomImages == null ) {
			boomImages = Assets.loadAnimationImages(R.drawable.quick_fire_hit, 10, 3);
		}
		images = boomImages;
	}

}
