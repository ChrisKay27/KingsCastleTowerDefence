package com.kingscastle.nuzi.towerdefence.effects.animations;




import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;

class Smoke2Anim extends Anim {

	private static ArrayList<Image> images;
	private final int staticTfb = 50;

	public Smoke2Anim(vector loc) {
		loadImages();
		setImages(images);
		setLoc(loc);
		setTbf(staticTfb);
		setLooping(true);
		onlyShowIfOnScreen = true;
	}

	void loadImages() {
		if (images != null) {
			return;
		}
		//	images = Assets.loadAnimationImages(R.drawable.fire2f, 8, 5);
		//ArrayList<Image> images2 = Assets.loadAnimationImages(
		//		R.drawable.fire2b, 8, 5);
		//for (int i = images2.size() - 1; i >= 0; i--) {
		//	images.add(images2.get(i));
		//}

	}

	@Override
	public String toString() {
		return "SmokeType2";
	}
}
