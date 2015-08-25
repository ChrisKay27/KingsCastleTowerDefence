package com.kingscastle.nuzi.towerdefence.ui;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Paint;

import com.kingscastle.nuzi.towerdefence.framework.Graphics;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.Healer;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.MageSoldier;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.RangedSoldier;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.AttackingBuilding;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Building;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;


class StatsInfo
{

	private static LivingThing selectedUnit;

	private static final TextLabel statsLabel1;
	private static final TextLabel statsLabel2;

	private static final vector stats1Location;
	private static final vector stats2Location;

	private static final vector finalStats1Location;
	private static final vector finalStats2Location;

	private static final Paint statPaint;

	private static final float HEIGHT_PLUS_TWENTY_FIVE_DP;



	static
	{

		int dp = (int) Rpg.getDp();
		HEIGHT_PLUS_TWENTY_FIVE_DP =  Rpg.getHeight() + 25 * dp;

		statPaint = Palette.getPaint(Paint.Align.CENTER, Color.WHITE);//Layout.whiteCenter;



		finalStats1Location = new vector( Rpg.getWidthDiv2() , Rpg.getHeight() - 2*statPaint.getFontSpacing() );
		finalStats2Location = new vector( Rpg.getWidthDiv2() , Rpg.getHeight() - statPaint.getFontSpacing() );

		stats1Location = new vector( Rpg.getWidthDiv2() , HEIGHT_PLUS_TWENTY_FIVE_DP );
		stats2Location = new vector( Rpg.getWidthDiv2() , HEIGHT_PLUS_TWENTY_FIVE_DP );

		statsLabel1 = new TextLabel( "" , stats1Location , statPaint );
		statsLabel2 = new TextLabel( "" , stats2Location , statPaint );


		//statsDisplayOffset = new Vector( HEIGHT_PLUS_HUNDRED_DP , 0 );

	}



	public static void act()
	{

		if( selectedUnit == null )
		{
			return;
		}

		if( stats1Location.y > finalStats1Location.y )
		{
			stats1Location.y -= 7;
		}
		else if( stats1Location.y <= finalStats1Location.y )
		{
			stats1Location.y = finalStats1Location.y;
		}

		if( stats2Location.y > finalStats2Location.y )
		{
			stats2Location.y -= 7;
		}
		else if( stats2Location.y <= finalStats2Location.y )
		{
			stats2Location.y = finalStats2Location.y;
		}


		//		if( statsDisplayOffset.x < 0 )
		//		{
		//			statsDisplayOffset.x += 5;
		//		}
		//		else if( statsDisplayOffset.x > 0 )
		//		{
		//			statsDisplayOffset.x = 0;
		//		}


	}



	public static void paint( Graphics g )
	{

		if ( selectedUnit != null )
		{

			g.drawTextLabel( statsLabel1 );
			g.drawTextLabel( statsLabel2 );
		}

	}



	public static LivingThing getSelectedUnit() {
		return selectedUnit;
	}



	@SuppressLint("DefaultLocale")
	public static void setSelectedUnit( LivingThing selectedUnit )
	{


		if( selectedUnit != null )
		{
			InfoMessage.getInstance().setMessage( null , 0 );
			int damage = selectedUnit.getAQ() != null ? selectedUnit.getAQ().getDamage() : 0;

			String speed = String.format( "%.2f" , ( selectedUnit.lq.getSpeed() / Rpg.getDp() ) );
			String msg = "Type: " + getSoldierType( selectedUnit ) + "Atk: "+ damage + getOptionalInfo( selectedUnit );
			String msg2 = "MaxHp: " + selectedUnit.lq.getFullHealth() + "  Speed: " + speed + getOptionalInfo2( selectedUnit );

            statsLabel1.setMsg( msg );
			statsLabel2.setMsg( msg2 );

			stats1Location.y = HEIGHT_PLUS_TWENTY_FIVE_DP;
			stats2Location.y = HEIGHT_PLUS_TWENTY_FIVE_DP + statPaint.getFontSpacing();
		}



		StatsInfo.selectedUnit = selectedUnit;
	}


	private static final String RANGE = "  Range: ";
	private static final String MELeE    = "Melee";
	private static final String EMPTY    = "";
	private static final String METERS    = "m";

	private static String getOptionalInfo(LivingThing selUnit)
	{
		if( selUnit instanceof Building)
		{
			return EMPTY;
		}
		else if( selUnit.getAQ() == null )
		{
			return EMPTY;
		}

		int range = (int) (selUnit.getAQ().getAttackRange()/10);

		if( selUnit instanceof MageSoldier)
		{
			return RANGE + range + METERS;
		}
		else if( selUnit instanceof RangedSoldier)
		{
			return RANGE + range + METERS;
		}
		else if( selUnit instanceof Healer)
		{
			return RANGE + range + METERS;
		}
		else if( selUnit instanceof AttackingBuilding)
		{
			return RANGE + range + METERS;
		}
		else
		{
			return RANGE + MELeE;
		}

	}

	private static final String BUFF = "  Buff: ";
	//	private static final String DAMAGEBUFF = "Damage";
	//	private static final String ARMORBUFF = "Armor";
	//	private static final String ATTACKRATEBUFF = "Attack Rate";
	//	private static final String HEALINGBUFF = "Healing Buff";

	private static String getOptionalInfo2( LivingThing selUnit )
	{

		//		if( selUnit instanceof WizardBuffer2 )
		//		{
		//			WizardBuffer2 wb = (WizardBuffer2) selUnit;
		//
		//			return BUFF + wb.getAbilityMessage();
		//		}

		return EMPTY;
	}


	private static final String MELEE    = "Melee    ";
	private static final String RANGED   = "Ranged   ";
	private static final String HEALER   = "Healer   ";
	private static final String BUILDING = "Building ";
	private static final String MAGE     = "Mage     ";

	private static String getSoldierType( LivingThing selUnit )
	{
		if( selUnit instanceof MageSoldier )
		{
			return MAGE;
		}
		else if( selUnit instanceof Healer )
		{
			return HEALER;
		}
		else if( selUnit instanceof RangedSoldier )
		{
			return RANGED;
		}
		else if( selUnit instanceof AttackingBuilding )
		{
			return RANGED;
		}
		else if( selUnit instanceof Building )
		{
			return BUILDING;
		}
		else
		{
			return MELEE;
		}

	}























}
