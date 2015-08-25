package com.kingscastle.nuzi.towerdefence.effects.animations;


import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Graphics;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;

public class Fire1Anim extends Anim
{

	private static ArrayList<Image> images,images1,images2,images3,images4;
	private final int staticTfb = 50;
	private final int imageNum = 0;

	public Fire1Anim( vector loc )
	{
		loadImages();
		setImages( images );
		setLoc( loc );
		setTbf( staticTfb );
		setLooping( true );
		onlyShowIfOnScreen = true;
	}

	//	public Fire1Anim( Vector loc , int i )
	//	{
	//		this( loc );
	//
	//		loadImages();
	//		imageNum = i;
	//		switch( i )
	//		{
	//		case 0:
	//			setImages( images ); break;
	//		case 1:
	//			setImages( images1 ); break;
	//		case 2:
	//			setImages( images2 ); break;
	//		case 3:
	//			setImages( images3 ); break;
	//		case 4:
	//			setImages( images4 ); break;
	//		}
	//
	//		setLoc( loc );
	//		setTbf( staticTfb );
	//		setLooping( true );
	//	}


	void loadImages()
	{
		if( images == null )
			images = Assets.loadAnimationImages(R.drawable.smaller_fire, 15, 5);
		//		if( images1 == null )
		//			images1 = Assets.loadAnimationImages( R.drawable.smaller_fire , 15 , 5 );
		//		if( images2 == null )
		//			images2 = Assets.loadAnimationImages( R.drawable.flame2 , 12 , 6 );
		//		if( images3 == null )
		//			images3 = Assets.loadAnimationImages( R.drawable.flame3 , 12 , 6 );
		//		if( images4 == null )
		//			images4 = Assets.loadAnimationImages( R.drawable.flame4 , 12 , 6 );

	}

	@Override
	public void paint( Graphics g , vector v )
	{
		super.paint(g, v);


		//	g.drawString( imageNum + "", v.x, v.y + Rpg.thirtyDp , paint );

		//g.drawString( currentImageIndex +"", v.x, v.y + 2*Rpg.thirtyDp , paint );
	}


	@Override
	public String toString()
	{
		return "FireType1";
	}
}
