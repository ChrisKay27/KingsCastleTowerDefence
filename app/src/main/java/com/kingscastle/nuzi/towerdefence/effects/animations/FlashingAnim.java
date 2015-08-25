package com.kingscastle.nuzi.towerdefence.effects.animations;


import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.framework.Graphics;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;

public class FlashingAnim extends Anim {

	private final int staticTfb = 300;
	private long lastChangedAt;
	private boolean on=true;


	public FlashingAnim(vector loc,Image img,int aliveTime) {
		ArrayList<Image> imgs = new ArrayList<Image>(1);
		imgs.add(img);
		setImages(imgs);
		setAliveTime(aliveTime);
		setLoc(loc);
		onlyShowIfOnScreen = true;
	}

	@Override
	public void paint(Graphics g, vector v) {
		if(on){
			super.paint(g,v);
		}
		if (lastChangedAt + staticTfb < GameTime.getTime()) {
			on=!on;
			lastChangedAt=GameTime.getTime();
		}
	}

}
