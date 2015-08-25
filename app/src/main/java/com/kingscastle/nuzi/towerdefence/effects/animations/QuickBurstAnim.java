package com.kingscastle.nuzi.towerdefence.effects.animations;


import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;

public class QuickBurstAnim extends Anim {

	private static ArrayList<Image> images;
	private final int staticTfb = 80;

	public QuickBurstAnim(vector loc) {
		loadImages();
		setImages(images);
		setLoc(loc);
		setTbf(staticTfb);
		paint = Rpg.getXferAddPaint();
		onlyShowIfOnScreen = true;
	}

	void loadImages() {
		if (images != null)
			return;
		images = Assets.loadAnimationImages(R.drawable.quick_burst, 5, 2);
	}

}
