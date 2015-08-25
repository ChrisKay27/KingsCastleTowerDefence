package com.kingscastle.nuzi.towerdefence.effects;

import android.graphics.Color;
import android.graphics.RectF;
import android.util.Log;

import com.kingscastle.nuzi.towerdefence.framework.Pool;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.Cost;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;

import java.util.ArrayList;

public class SpecialTextEffects
{
	private static final String TAG = "SpecialTextEffects";

	private static final float SMALLEST_TXT_SIZE = Rpg.getSmallestTextSize();
	private static final float TXT_SIZE = Rpg.getTextSize();


	static RectF stillDraw;
	static MM mm;


	public static boolean onCreatureDamaged( float x , float y , int dam )
	{
		////Log.d( TAG , "onCreatureDamaged( " + x + "," + y + ","+ dam + ")");

		if( stillDraw != null && stillDraw.contains( x , y ) )
		{
			////Log.d( TAG , "stillDraw.contains( x , y )");
			return makeText( x , y , "" + dam , Color.RED , SMALLEST_TXT_SIZE );
		}

		return false;
	}



	public static boolean onCreatureHealed( float x , float y , int heal )
	{
		if( stillDraw != null && stillDraw.contains( x , y ))
			return makeText( x , y , "" + heal , Color.GREEN , SMALLEST_TXT_SIZE );

		return true;
	}



	public static boolean onCreatureLvledUp(float x, float y) {
		if( stillDraw != null && stillDraw.contains( x , y ) )
			return makeText( x , y , "Lvl Up!" , Color.WHITE , TXT_SIZE );

		return false;
	}

	public static boolean makeText(float x, float y, String text, int color , float size )
	{
		////Log.d( TAG , "makeText(...)");
		RisingText textObj = textPool.newObject();
		if( textObj == null )
		{
			////Log.d( TAG , "textObj == null from pool");
			textObj = new RisingText();
		}
		textObj.loc.set( x , y );
		textObj.setColor( color );
		textObj.setText( text );
		textObj.setTextSize( size );
		textObj.reset();
		mm.getRtm().add( textObj );
		return true;
	}


	public static void showResourcesDropped(float x, float y, Cost cost) {
		if( cost == null ){
			Log.e( TAG , "showResourcesDropped() && cost==null");
			return;
		}
		if( cost.getGoldCost() != 0 )
			makeText(x,y,cost.getGoldCost()+"",Color.YELLOW,Rpg.getSmallestTextSize());
		if( cost.getFoodCost() != 0 )
			makeText(x-Rpg.twentyDp,y+Rpg.tenDp,cost.getFoodCost()+"",Color.RED,Rpg.getSmallestTextSize());
		if( cost.getWoodCost() != 0 )
			makeText(x+Rpg.twentyDp,y+Rpg.tenDp,cost.getWoodCost()+"",Color.rgb(127,51,0),Rpg.getSmallestTextSize());
		if( cost.getMagicDustCost() != 0 )
			makeText(x,y+Rpg.twentyDp,cost.getMagicDustCost()+" Magic Dusts",Color.WHITE,Rpg.getSmallestTextSize());
	}










	public static void setStillDrawArea( RectF rect )
	{
		SpecialTextEffects.stillDraw = rect;
	}




	static void freeAll(ArrayList<RisingText> list)
	{
		for( RisingText t : list )
			textPool.free( t );
	}




	private static final Pool<RisingText> textPool;

	static
	{
		Pool.PoolObjectFactory<RisingText> factory = new Pool.PoolObjectFactory<RisingText>() {
			@Override
			public RisingText createObject() {
				return new RisingText();
			}
		};
		textPool = new Pool<RisingText>(factory, 200);
	}












}
