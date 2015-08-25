package com.kingscastle.nuzi.towerdefence.effects.animations;

import android.graphics.Color;

import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;


public class SparksAnim extends Anim {

	private static ArrayList<Image> blueImages1,blueImages2,redImages1,redImages2,yellowImages1,yellowImages2;

	private final int staticTfb = 50;
	private final int color;

	public SparksAnim( vector loc , int color )
	{
		setLoc( loc );
		setTbf( staticTfb );
		this.color = color;
		loadImages( color );
		onlyShowIfOnScreen = true;
	}

	public SparksAnim( int color )
	{
		setTbf( staticTfb );
		this.color = color;
		loadImages( color );
	}


	private void loadImages(int color) {
		switch(color){
		case Color.BLUE:
			if(Math.random()<0.5){
				if(blueImages1==null){
					blueImages1= Assets.loadAnimationImages(R.drawable.sparks, 4, 6, 5, 4);
				}
				images= blueImages1;
			} else{
				if(blueImages2==null){
					blueImages2=Assets.loadAnimationImages(R.drawable.sparks,4,6,4,4);
				}
				images= blueImages2;
			}
			break;
		case Color.RED:
			if(Math.random()<0.5){
				if(redImages1==null){
					redImages1=Assets.loadAnimationImages(R.drawable.sparks,4,6,0,4);
				}
				images= redImages1;
			} else{
				if(redImages2==null){
					redImages2=Assets.loadAnimationImages(R.drawable.sparks,4,6,1,4);
				}
				images= redImages2;
			}
			break;
		case Color.YELLOW:
			if(Math.random()<0.5){
				if(yellowImages1==null){
					yellowImages1=Assets.loadAnimationImages(R.drawable.sparks,4,6,2,4);
				}
				images= yellowImages1;
			} else{
				if(yellowImages2==null){
					yellowImages2=Assets.loadAnimationImages(R.drawable.sparks,4,6,3,4);
				}
				images= yellowImages2;
			}
			break;


		}

		//		p=new Paint();
		//		p.setAntiAlias(true);
		//		p.setAlpha(255);
		//		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		//		if (currentapiVersion >= android.os.Build.VERSION_CODES.HONEYCOMB){
		//			p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
		//		} else{
		//			p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN));
		//		}
	}
	//	public void paint(Graphics g, Vector v) {
	//		if(getImage()!=null){
	//			g.drawImage(getImage(), v.getIntX()-getImage().getWidthDiv2(), v.getIntY()-getImage().getHeightDiv2());
	//		}
	//
	//	}


	public int getColor()
	{
		return color;
	}



}
