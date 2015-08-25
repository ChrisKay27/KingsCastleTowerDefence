package com.kingscastle.nuzi.towerdefence.effects.animations;


import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;

public class EruptionAnim extends Anim {

	private static ArrayList<Image> staticImages = Assets.loadAnimationImages(R.drawable.spell_eruption_large, 5, 6);
	private final int staticTfb=40;

	public EruptionAnim(vector loc){
		setImages(staticImages);
		setLoc(loc);
		setTbf(staticTfb);
		setPaint(Rpg.getXferAddPaint());
	}




}
