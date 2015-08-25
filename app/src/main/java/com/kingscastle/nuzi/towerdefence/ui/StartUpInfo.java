package com.kingscastle.nuzi.towerdefence.ui;

import android.graphics.Color;
import android.graphics.Paint;

import com.kingscastle.nuzi.towerdefence.framework.Graphics;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;






class StartUpInfo
{


	private static final String TAP_TO_START = "Tap to act";
	private static final ArrayList<TextLabel> SCREENLABELS ;

	private static int alpha = 255;

	static
	{
		SCREENLABELS = new ArrayList<TextLabel>();

		int width = Rpg.getWidth();
		int heightDiv2 = Rpg.getHeightDiv2();

		Paint temp =  Palette.getPaint( Color.WHITE , Rpg.getLargeTitleTextSize() );
		Paint smallYellow =  null;

		float yInc = temp.descent() - temp.ascent();


		SCREENLABELS.add ( new TextLabel( "Kings Castle" , new vector( width/2 , heightDiv2 - 2*yInc ) ,  temp ) );
		SCREENLABELS.add ( new TextLabel( TAP_TO_START , new vector( width/2 , heightDiv2 - 3*yInc ) ,  smallYellow ) );
	}





	public static void paint( Graphics g )
	{

		g.drawARGB( alpha , 0 , 0 , 0 );
		alpha = alpha > 230 ? alpha - 1 : alpha ;
		alpha = alpha > 155 ? alpha - 3 : 155 ;


		//g.drawString( TAP_TO_START , Rpg.getWidthDiv2() , Rpg.getHeightDiv2() , yellowCenter );


		//g.drawRectBorder( Layout.getRightScrollerArea() , Color.YELLOW , 1 );

		//	g.drawRectBorder( Layout.getUnselectButtonArea() , Color.YELLOW , 1 );

		//	g.drawRectBorder( Layout.getTroopSelectorButtonArea() , Color.YELLOW , 1 );

		//	g.drawRectBorder( Layout.getCancelButtonArea() , Color.YELLOW , 1 );

		//g.drawRectBorder( titleArea , Color.YELLOW , 1 );

		for( TextLabel tl : SCREENLABELS )
		{
			g.drawTextLabel( tl );
		}
	}




	public static boolean moveToNextMessage()
	{
		boolean done = alpha == 155;
		if( done )
			alpha = 255;
		return done ;
	}


}

