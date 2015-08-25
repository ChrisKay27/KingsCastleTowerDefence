package com.kingscastle.nuzi.towerdefence.gameElements;

import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;

public class MapBorderObject extends GameElement
{

	{
		created = true;
	}

	@Override
	public boolean act()
	{
		return false;
	}



	@Override
	public boolean create(MM mm)
	{
		return true;
	}

	public MapBorderObject(RectF sideBorder )
	{
		area.set( sideBorder );  //CHANGED jan 12
	}




	@Override
	public RectF getStaticPerceivedArea()
	{

		return null;
	}




	@Override
	public ImageFormatInfo getImageFormatInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Image[] getStaticImages() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStaticImages(Image[] images) {
		// TODO Auto-generated method stub

	}


}
