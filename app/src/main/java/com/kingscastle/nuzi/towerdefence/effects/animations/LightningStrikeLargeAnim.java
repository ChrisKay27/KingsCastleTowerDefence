package com.kingscastle.nuzi.towerdefence.effects.animations;


import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.List;

public class LightningStrikeLargeAnim extends Anim {

	private static List<Image> images = Assets.loadAnimationImages(R.drawable.lightning_large, 5, 5).subList(0,23);
	private final int staticTfb=60;


	public LightningStrikeLargeAnim(vector loc){
		setImages(images);
		setLoc(loc);
		setTbf(staticTfb);
		setPaint(Rpg.getXferAddPaint());
		onlyShowIfOnScreen = true;
	}



}
