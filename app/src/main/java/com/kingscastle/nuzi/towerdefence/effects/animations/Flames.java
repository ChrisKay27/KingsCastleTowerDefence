package com.kingscastle.nuzi.towerdefence.effects.animations;


import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;

public class Flames extends Anim {

	private static ArrayList<Image> staticSImages = Assets.convToArrayList(Assets.loadImages(R.drawable.flames,3,1,0,0,1,4));
	private static ArrayList<Image> staticWImages = Assets.convToArrayList(Assets.loadImages(R.drawable.flames, 3, 1, 0, 1, 1, 4));
	private static ArrayList<Image> staticEImages = Assets.convToArrayList(Assets.loadImages(R.drawable.flames,3,1,0,2,1,4));
	private static ArrayList<Image> staticNImages = Assets.convToArrayList(Assets.loadImages(R.drawable.flames,3,1,0,3,1,4));

	private final int staticTfb = 100;

	/**
	 * North=0,East=1...
	 * @param loc
	 * @param dir
	 */
	public Flames(vector loc , int dir){
		switch( dir ){
		case 0:setImages(staticNImages); break;
		case 1:setImages(staticEImages); break;
		case 2:setImages(staticSImages); break;
		case 3:setImages(staticWImages); break;
		}
		setLoc(loc);
		setTbf(staticTfb);
		onlyShowIfOnScreen = true;
	}




}



