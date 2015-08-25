package com.kingscastle.nuzi.towerdefence.effects.animations;


import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;

public class AuraAnim extends Anim {

	private static final ArrayList<Image> staticImages;
	//private static final Vector staticOffs = new Vector();
	private final int staticTfb = 30;

	static
	{
		staticImages = Assets.loadAnimationImages(R.drawable.aura, 8, 4);
	}


	public AuraAnim(vector loc)
	{

		setImages( staticImages );
		setLoc( loc );
		setTbf(staticTfb);
		onlyShowIfOnScreen = true;
		//setOffs( staticOffs );

	}



	public void loadImages()
	{

	}



}
