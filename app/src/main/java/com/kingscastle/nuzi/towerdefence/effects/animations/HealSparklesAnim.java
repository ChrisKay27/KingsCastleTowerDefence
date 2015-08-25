package com.kingscastle.nuzi.towerdefence.effects.animations;


import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;

public class HealSparklesAnim extends Anim {

	private static ArrayList<Image> images;
	private final int staticTfb=50;


	public HealSparklesAnim(vector loc){
		loadImages();
		setImages(images);
		setLoc(loc);
		setTbf(staticTfb);
		onlyShowIfOnScreen = true;

	}

	void loadImages() {
		if(images!=null)
			return;
		images= Assets.loadAnimationImages(R.drawable.heal_sparkle, 6, 1);
	}



}
