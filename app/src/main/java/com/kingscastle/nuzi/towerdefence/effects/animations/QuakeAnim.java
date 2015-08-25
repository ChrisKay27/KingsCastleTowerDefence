package com.kingscastle.nuzi.towerdefence.effects.animations;


import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class QuakeAnim extends Anim {
	public enum QuakeColor {Blue, Brown, Grey}

	private static ArrayList<Image> staticImagesGrey = Assets.loadAnimationImages(R.drawable.quake_large, 6, 3, 0, 6);
	private static ArrayList<Image> staticImagesBrown = Assets.loadAnimationImages(R.drawable.quake_large, 6, 3, 1, 6);
	private static ArrayList<Image> staticImagesBlue = Assets.loadAnimationImages(R.drawable.quake_large, 6, 3, 2, 6);

	private final int staticTfb = 60;

	public QuakeAnim(@NotNull vector loc , QuakeColor color) {
		switch( color ){
			case Blue: setImages(staticImagesBlue); break;
			case Brown: setImages(staticImagesBlue); break;
			case Grey: setImages(staticImagesBlue); break;
		}

		setLoc(loc);
		setTbf(staticTfb);
		onlyShowIfOnScreen = true;
	}

	public QuakeAnim(@NotNull vector loc) {
		setImages(staticImagesBlue);
		setLoc(loc);
		setTbf(staticTfb);
		onlyShowIfOnScreen = true;
	}

}
