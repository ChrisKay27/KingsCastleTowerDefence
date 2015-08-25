package com.kingscastle.nuzi.towerdefence.effects.animations;


import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Graphics;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;

public class IcicleAnim extends Anim {

	private static ArrayList<Image> Wimages, NWimages, Nimages,
	NEimages, Eimages, SEimages, Simages, SWimages;
	private static final int staticTfb=80;
	private static final int imageid = R.drawable.icicles;



	static
	{
		Image icicles = Assets.loadImage(imageid);
		Wimages = Assets.loadAnimationImages( icicles, 8, 8, 0, 8 , false );
		NWimages = Assets.loadAnimationImages( icicles, 8, 8, 1, 8 , false );
		Nimages = Assets.loadAnimationImages( icicles, 8, 8, 2, 8 , false );
		NEimages = Assets.loadAnimationImages( icicles , 8, 8, 3, 8 , false );
		Eimages = Assets.loadAnimationImages( icicles , 8, 8, 4, 8 , false );
		SEimages = Assets.loadAnimationImages( icicles , 8, 8, 5, 8 , false );
		Simages = Assets.loadAnimationImages( icicles , 8, 8, 6, 8 , false );
		SWimages = Assets.loadAnimationImages( icicles , 8, 8, 7, 8 , true );
	}



	public IcicleAnim(vector loc,int dir){
		setImages(getImages(dir));
		setLoc(loc);
		setTbf(staticTfb);
		setLooping(true);
		onlyShowIfOnScreen = true;
		LightEffect le = new LightEffect(this.loc, LightEffect.LightEffectColor.LIGHT_BLUE);
		le.setScale(0.5f);
		add(le, true);
	}


	@Override
	public void paint(Graphics g, vector v)
	{
		vTemp.set( v );
		vTemp.add( offs );

		if( addedBehind != null )
			for(int i = addedBehind.size() - 1 ; i > -1 ; --i )
				addedBehind.get( i ).paint( g , vTemp );

		Image image = getImage();
		if(image != null )
			g.drawImage( image , v.getIntX() - image.getWidthDiv2() , v.getIntY() - image.getHeightDiv2() );

		if( addedInFront != null )
			for( int i = addedInFront.size() - 1 ; i > -1 ; --i )
				addedInFront.get(i).paint( g , vTemp );

	}


	private static ArrayList<Image> getImages(int dir){
		switch (dir) {
		default:
		case 0:
			if (Wimages == null) {
				Wimages = Assets.loadAnimationImages(imageid, 8, 8, 0, 8);
			}
			return Wimages;
		case 1:
			if (NWimages == null) {
				NWimages = Assets.loadAnimationImages(imageid, 8, 8, 1, 8);
			}
			return NWimages;
		case 2:
			if (Nimages == null) {
				Nimages = Assets.loadAnimationImages(imageid, 8, 8, 2, 8);
			}
			return Nimages;
		case 3:
			if (NEimages == null) {
				NEimages = Assets.loadAnimationImages(imageid, 8, 8, 3, 8);
			}
			return NEimages;
		case 4:
			if (Eimages == null) {
				Eimages = Assets.loadAnimationImages(imageid, 8, 8, 4, 8);
			}
			return Eimages;
		case 5:
			if (SEimages == null) {
				SEimages = Assets.loadAnimationImages(imageid, 8, 8, 5, 8);
			}
			return SEimages;
		case 6:
			if (Simages == null) {
				Simages = Assets.loadAnimationImages(imageid, 8, 8, 6, 8);
			}
			return Simages;
		case 7:
			if (SWimages == null) {
				SWimages = Assets.loadAnimationImages(imageid, 8, 8, 7, 8);
			}
			return SWimages;
		}
	}
}