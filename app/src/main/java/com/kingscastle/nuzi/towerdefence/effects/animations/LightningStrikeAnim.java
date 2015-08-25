package com.kingscastle.nuzi.towerdefence.effects.animations;


import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;

public class LightningStrikeAnim extends Anim {

	private static ArrayList<Image> images = Assets.loadAnimationImages(R.drawable.lightning2, 2, 2);
	private final int staticTfb=60;


	public LightningStrikeAnim(vector loc){
		setImages(images);
		setLoc(loc);
		setTbf(staticTfb);
		onlyShowIfOnScreen = true;
	}



}
