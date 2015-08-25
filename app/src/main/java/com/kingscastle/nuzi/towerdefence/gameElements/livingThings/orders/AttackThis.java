package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.orders;


import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Input;
import com.kingscastle.nuzi.towerdefence.gameElements.CD;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.Humanoid;
import com.kingscastle.nuzi.towerdefence.gameUtils.CoordConverter;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.ui.EnemyAtLocationChecker;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public final class AttackThis extends Order
{

	//	private static final String TAG = "AttackThis";

	private static Image iconImage;

	//private static BuildThis buildThis;


	private final ArrayList<LivingThing> unitsToBeOrdered = new ArrayList<>();




	public static AttackThis getInstance()
	{
		return new AttackThis();
	}




	@Override
	public Image getIconImage()
	{
		if( iconImage == null )
		{
			//	iconImage = Assets.loadImage( R.drawable.attack_icon );
		}
		return iconImage;
	}



	@Override
	public ArrayList<? extends LivingThing> getUnitsToBeOrdered()
	{
		return unitsToBeOrdered;
	}




	@Override
	public void setUnitsToBeOrdered(ArrayList<? extends LivingThing> livingThings)
	{
		unitsToBeOrdered.clear();
		unitsToBeOrdered.addAll( livingThings );
	}



	@Override
	public void setUnitToBeOrdered(LivingThing person)
	{
		unitsToBeOrdered.clear();
		unitsToBeOrdered.add( person );
	}


	private final vector mapRel = new vector();


	@Override
	public boolean analyseTouchEvent(Input.TouchEvent event , CoordConverter cc , CD cd)
	{
		cc.getCoordsScreenToMap( event.x , event.y , mapRel );

		return analyseCoordinate ( mapRel , unitsToBeOrdered );

	}


	public static boolean analyseCoordinate( vector mapRelCoord , ArrayList<? extends LivingThing> thingsToCommand  )
	{
		if( thingsToCommand == null || thingsToCommand.size() == 0 )
			throw new IllegalArgumentException( " thingsToCommand == null || thingsToCommand.size() == 0 ");

		if( mapRelCoord == null )
			throw new IllegalArgumentException( " mapRelCoord == null ");


		LivingThing enemy = EnemyAtLocationChecker.findEnemyHere(mapRelCoord, thingsToCommand.get(0).getTeamName(), false);

		if( enemy != null )
		{
			//////Log.d( TAG , "enemy == " + enemy + ", setting highTreatTarget" );
			for( LivingThing lt : thingsToCommand )
			{
				lt.setHighThreadTarget(enemy);
				if( lt instanceof Humanoid )
					((Humanoid)lt).walkTo( null );
			}
			return true;
		}
		//////Log.d( TAG , "enemy == null at mapRelCoord" );

		return false;

	}

	@Override
	public void saveYourSelf(BufferedWriter b) throws IOException
	{


	}


	@Override
	public OrderTypes getOrderType()
	{
		return OrderTypes.ATTACK_THIS;
	}

	private static final String name = "Attack";

	@Override
	public String toString()
	{
		return name;
	}




}
