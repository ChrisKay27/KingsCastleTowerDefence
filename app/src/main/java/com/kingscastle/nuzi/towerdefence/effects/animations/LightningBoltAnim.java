package com.kingscastle.nuzi.towerdefence.effects.animations;


import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;

public class LightningBoltAnim extends Anim {

	private static ArrayList<Image> images;
	private final int staticTfb=50;

	public LightningBoltAnim(vector loc){
		loadImages();
		setImages(images);
		setLoc(loc);
		setTbf(staticTfb);
		setLooping(true);
		onlyShowIfOnScreen = true;
		LightEffect le = new LightEffect(this.loc, LightEffect.LightEffectColor.LIGHT_ORANGE);
		le.setScale(0.5f);
		add(le, true);
	}

	void loadImages() {

		if(images!=null)	return;

		images= Assets.loadAnimationImages(R.drawable.lightning_bolt, 5, 4);

	}

}
