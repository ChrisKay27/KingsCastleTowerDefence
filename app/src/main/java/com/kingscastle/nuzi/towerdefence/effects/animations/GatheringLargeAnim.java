package com.kingscastle.nuzi.towerdefence.effects.animations;


import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;

public class GatheringLargeAnim extends Anim {

	private static ArrayList<Image> images = Assets.loadAnimationImages(0/*R.drawable.gathering_large */, 5, 4);
	private final int staticTfb = 40;

	public GatheringLargeAnim(vector loc) {
		setImages(images);
		setLoc(loc);
		setTbf(staticTfb);
		setPaint(Rpg.getXferAddPaint());
		onlyShowIfOnScreen = true;
	}

}
