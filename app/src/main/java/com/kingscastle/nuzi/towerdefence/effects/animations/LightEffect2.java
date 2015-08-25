package com.kingscastle.nuzi.towerdefence.effects.animations;


import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;

import com.kingscastle.nuzi.towerdefence.effects.EffectsManager;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Graphics;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

public class LightEffect2 extends Anim
{
	private final static Image img = Assets.loadImage(R.drawable.light_effect);
	private static Paint clearPaint = new Paint();
	static{
		clearPaint.setXfermode(new PorterDuffXfermode( PorterDuff.Mode.MULTIPLY ));
	}


	public LightEffect2(vector loc)
	{
		setImage(img);
		setLoc(loc);
		setLooping(true);
		setAliveTime(Integer.MAX_VALUE);
		setPaint(clearPaint);
	}




	@Override
	public void paint( Graphics g , vector v )
	{
		Image image = getImage();
		if( image != null )
			g.drawImage( image , v.x - image.getWidthDiv2() , v.y - image.getHeightDiv2() , paint );
	}

	@Nullable
	@Override
	public EffectsManager.Position getRequiredPosition() {
		return EffectsManager.Position.LightEffectsInFront;

	}
}
