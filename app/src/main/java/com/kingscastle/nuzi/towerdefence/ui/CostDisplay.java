package com.kingscastle.nuzi.towerdefence.ui;


import android.graphics.Paint;

import com.kingscastle.nuzi.towerdefence.framework.Graphics;
import com.kingscastle.nuzi.towerdefence.framework.Settings;
import com.kingscastle.nuzi.towerdefence.gameElements.Cost;


public class CostDisplay
{
	private static CostDisplay costDisplay;

	private static Paint whiteRight , yellowRight , redRight , greyRight , brownRight , purpleRight , orangeRight;

	private Cost costToDisplay;
	private String namePlusCosts;

	private final float nearRightScroller;
	private static float xOffs;
	private static float yOffs;
	private static float yInc;



	private CostDisplay()
	{
		nearRightScroller = 100;//Rpg.getWidth() - whiteRight.measureText( "buildings " ) - Layout.getRightScrollerArea().width() ;
	}

	public static CostDisplay getInstance()
	{
		if ( costDisplay == null )
			costDisplay = new CostDisplay();
		return costDisplay;
	}



	public void paint( Graphics g )
	{
		if ( costToDisplay == null )
			return;
		else
			drawCosts ( g , costToDisplay );
	}


	private static final String COSTS = " Costs: ";
	private static final String gold = "Gold:" , food = "Food:"  , wood = "Wood:"  , metal = "Metal:" ,  magicEssenses = "MagicEssense:" , pop = "Pop:";


	private void drawCosts( Graphics g , Cost costToDisplay )
	{

		float y = yOffs;


		g.drawString( namePlusCosts , xOffs , y , whiteRight );

		y += yInc;


		int i = costToDisplay.getGoldCost();

		if( i != 0 )
		{
			g.drawString( gold + addPrefix( i ) , xOffs , y , yellowRight );
			y += yInc;
		}





		i = costToDisplay.getFoodCost();

		if( i != 0 )
		{
			g.drawString( food + addPrefix(i) , xOffs , y , redRight );
			y += yInc;
		}



		i = costToDisplay.getWoodCost();

		if(i!=0)
		{
			g.drawString( wood + addPrefix(i) , xOffs , y , brownRight );
			y+=yInc;
		}



		//i = costToDisplay.getMetalCost();

		//if(i!=0)
		//{
		//	g.drawString( metal + addPrefix(i) , xOffs , y , greyRight );
		//	y+=yInc;
		//}


		//
		//		i = costToDisplay.getMagicEssenseCost();
		//
		//		if( i != 0 )
		//		{
		//			g.drawString( magicEssenses + addPrefix(i), xOffs ,y,purpleRight);
		//			y += yInc;
		//		}
		//


		i = costToDisplay.getPopCost();

		if( i != 0 )
		{
			g.drawString( pop + i , xOffs , y , orangeRight );
			y += yInc;
		}

	}



	private String addPrefix(int i)
	{
		if( i > 0 )
			return " -" + i;
		if( i < 0 )
			return " " + -i;
		return null;
	}


	public Cost getCostToDisplay() {
		return costToDisplay;
	}


	public void setCostToDisplay( Cost costToDisplay )
	{
		if( !Settings.mapEditingMode )
		{
			this.costToDisplay = costToDisplay;
			if( costToDisplay != null )
				namePlusCosts = costToDisplay.getName() + COSTS;
		}
	}

	public void setCostToDisplay( Cost costToDisplay , float yLoc )
	{
		if( !Settings.mapEditingMode )
		{
			setCostToDisplay( costToDisplay );
			yOffs = yLoc;
			xOffs = nearRightScroller;
		}
	}

	public void setCostToDisplay( Cost costToDisplay , float rightAlignedX , float yLoc )
	{
		if( !Settings.mapEditingMode )
		{
			setCostToDisplay( costToDisplay );
			xOffs = rightAlignedX;
			yOffs = yLoc;
		}
	}


	public boolean isVisible()
	{
		if( costToDisplay != null )
			return true;
		else
			return false;
	}

	public void setVisible( boolean b ){
	}




	/**
	 * @return the whiteRight
	 */
	public static Paint getWhiteRight() {
		return whiteRight;
	}

	/**
	 * @param whiteRight the whiteRight to set
	 */
	public static void setWhiteRight(Paint whiteRight) {
		CostDisplay.whiteRight = whiteRight;
	}

	public static Paint getYellowRight() {
		return yellowRight;
	}

	public static void setYellowRight(Paint yellowRight) {
		CostDisplay.yellowRight = yellowRight;
	}

	public static Paint getRedRight() {
		return redRight;
	}

	public static Paint getOrangeRight() {
		return orangeRight;
	}

	public static void setOrangeRight(Paint orangeRight) {
		CostDisplay.orangeRight = orangeRight;
	}

	public static void setRedRight(Paint redRight) {
		CostDisplay.redRight = redRight;
	}

	public static Paint getGreyRight() {
		return greyRight;
	}

	public static void setGreyRight(Paint greyRight) {
		CostDisplay.greyRight = greyRight;
	}

	public static Paint getBrownRight() {
		return brownRight;
	}

	public static void setBrownRight(Paint brownRight) {
		CostDisplay.brownRight = brownRight;
	}

	public static Paint getPurpleRight() {
		return purpleRight;
	}

	public static void setPurpleRight(Paint purpleRight) {
		CostDisplay.purpleRight = purpleRight;
	}

	public static float getyOffs() {
		return yOffs;
	}

	public static void setYOffs(float yOffs) {
		CostDisplay.yOffs = yOffs;
	}

	public static float getyInc() {
		return yInc;
	}

	public static void setYInc(float yInc) {
		CostDisplay.yInc = yInc;
	}




}
