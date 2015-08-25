package com.kingscastle.nuzi.towerdefence.effects.animations;


import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;
import java.util.List;

public class FreezeAnim extends Anim {

	private static final String TAG = "FreezeAnim";

	private static final ArrayList<Image> images0 = Assets.loadAnimationImages(R.drawable.freeze, 4, 6, 0, 4);
	private static final ArrayList<Image> images1 = Assets.loadAnimationImages(R.drawable.freeze, 4, 6, 1, 4);
	private static final ArrayList<Image> images2 = Assets.loadAnimationImages(R.drawable.freeze, 4, 6, 2, 4);
	private static final List<List<Image>> staticImages = new ArrayList<>();
	static{
		staticImages.add(images0); staticImages.add(images1);
		staticImages.add(images2);
	}

	private final int staticTfb = 100;



	public FreezeAnim( vector loc , int aliveTime )
	{
		////Log.d( TAG , "FreezeAnim constructed, staticImages.size() = " + staticImages.size() );

		setImages(staticImages.get((int) (Math.random() * staticImages.size())));
		setLoc( loc );
		setTbf( staticTfb );
		setAliveTime( aliveTime );
		//setPaint(Rpg.getXferAddPaint());
	}



	@Override
	public boolean act()
	{
		if( nextImageChange < GameTime.getTime() )
		{
			nextImageChange = GameTime.getTime() + tbf ;

			if( currentImageIndex+1 == staticImages.size() )
			{
				if( startTime + aliveTime < GameTime.getTime() ){
					//Log.d( TAG , "Out of time , over = true");
					return over = true;}
				else if( looping )
					currentImageIndex = 0;
				else{
					//Log.d( TAG , "Cycled through all staticImages holding at last one");
					return false;}
			}
			else
				++currentImageIndex;
		}
		return over;
	}

	@Override
	public Image getImage()
	{
		return super.images.get( currentImageIndex );
	}

	public FreezeAnim newInstance(vector v, int aliveTime){
		return new FreezeAnim(v,aliveTime);
	}

}
