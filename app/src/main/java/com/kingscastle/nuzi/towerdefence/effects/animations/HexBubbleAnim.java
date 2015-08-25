package com.kingscastle.nuzi.towerdefence.effects.animations;

import android.graphics.Color;

import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;

public class HexBubbleAnim extends Anim {

	private static ArrayList<Image> staticBlueImages;
	private static ArrayList<Image> staticWhiteImages;

	private final int staticTfb = 50;

	{
		onlyShowIfOnScreen = true;
	}
	public HexBubbleAnim(vector loc)
	{
		loadImages( Color.BLUE );
		setImages( getImages( Color.BLUE ) );
		setLoc(loc);
		setTbf(staticTfb);
		offs.add( Rpg.getDp() , Rpg.getDp() );
	}

	public HexBubbleAnim(vector loc , int color )
	{
		loadImages( color );
		setImages( getImages( color ));
		setLoc(loc);
		setTbf(staticTfb);
		offs.add( Rpg.getDp() , Rpg.getDp() );
	}




	private ArrayList<Image> getImages(int color)
	{
		switch( color )
		{
		default:
		case Color.BLUE:
			return staticBlueImages;
		case Color.WHITE:
			return staticWhiteImages;
		}
	}


	void loadImages(int color)
	{

		switch( color )
		{
		default:
		case Color.BLUE:
			if( staticBlueImages == null )
			{
				staticBlueImages = Assets.loadAnimationImages(R.drawable.hex_bubble, 4, 4);
			}
			break;

		case Color.WHITE:
			if( staticWhiteImages == null )
			{
				staticWhiteImages = Assets.loadAnimationImages( R.drawable.hex_shield_white , 4 , 4 );
			}
			break;

		}
	}


}
