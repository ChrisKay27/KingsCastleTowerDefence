/**
 * 
 */
package com.kingscastle.nuzi.towerdefence.gameElements.managment;

import android.util.Log;

import com.kingscastle.nuzi.towerdefence.TowerDefenceGame;
import com.kingscastle.nuzi.towerdefence.gameElements.GameElement;
import com.kingscastle.nuzi.towerdefence.gameElements.movement.pathing.GridWorker;


/**
 * @author Chris
 *
 */
public class SubGemManager extends GemManager
{

	private static final String TAG = "SubGemManager";


	private final MM mm;

	public SubGemManager(MM mm)	{
		super(mm);
		this.mm = mm;
	}



	@Override
	public boolean add( GameElement ge )
	{
		if( ge == null )
		{
			if( TowerDefenceGame.testingVersion ){
				Log.e( TAG , "Trying to add a null.");}
			return false;

		}

		if( team != null && ge.getTeamName() != team  )
		{
			if( TowerDefenceGame.testingVersion ){
				Log.e( TAG , "Trying something with the wrong team to this manager.");}
			return false;
		}


		if( ge.created )
		{
			synchronized( needsToBeAdded ){
				needsToBeAdded.add( ge );
			}

			return true;
		}


		if( TowerDefenceGame.testingVersion ){
			Log.e( TAG , ge + ".created == false");}

		return false;
	}




	@Override
	public void act()
	{
		try
		{
			GameElement[] newGes;
			if( gameElements != gameElements1 )
				newGes = gameElements1;
			else
				newGes = gameElements2;


			int newGesSize = 0;

			for( int i = 0 ; i < gameElementsSize  ; ++i )
			{
				GameElement ge = gameElements[i];
				if( ge.dead )
				{
					GridWorker.addToToBeGriddedQueue(ge.area, true);
					gameElements[i] = null;
				}
				else
					newGes[newGesSize++] = ge;
			}
			synchronized( needsToBeAdded ){
				for( GameElement ge : needsToBeAdded )
				{
					newGes[newGesSize++] = ge;
				}
				needsToBeAdded.clear();
			}

			GemPackage newGemPkg;
			if( gemPkg != gemPkg1 )
				newGemPkg = gemPkg1;
			else
				newGemPkg = gemPkg2;

			synchronized( gemPkg ){
				gemPkg = newGemPkg;
				gemPkg.gems = newGes;
				gemPkg.size = newGesSize;
				gameElements = newGes;
				gameElementsSize = newGesSize;
			}

		}
		catch( Exception t )
		{
			t.printStackTrace();
		}
	}



}
