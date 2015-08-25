package com.kingscastle.nuzi.towerdefence.effects.animations;


import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;

public class SmokeAnim extends Anim {

	private static ArrayList<Image> images;
	private final int staticTfb = 50;

	public SmokeAnim(vector loc) {
		loadImages();
		super.images = images;
		super.loc = loc;
		tbf = staticTfb;
		onlyShowIfOnScreen = true;
	}

	void loadImages() {
		if (images != null) {
			return;
		}
		images = Assets.loadAnimationImages(R.drawable.smoke_loop, 12, 3);
	}

	@Override
	public String toString() {
		return "SmokeType1";
	}
}
