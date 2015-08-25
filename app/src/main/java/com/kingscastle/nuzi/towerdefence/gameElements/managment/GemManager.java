/**
 * 
 */
package com.kingscastle.nuzi.towerdefence.gameElements.managment;

import android.util.Log;

import com.kingscastle.nuzi.towerdefence.TowerDefenceGame;
import com.kingscastle.nuzi.towerdefence.gameElements.GameElement;
import com.kingscastle.nuzi.towerdefence.gameElements.movement.pathing.GridWorker;
import com.kingscastle.nuzi.towerdefence.teams.Teams;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @author Chris
 *
 */
public class GemManager
{
	private static final String TAG = "Manager";

	final GameElement[] gameElements1 = new GameElement[ 5000 ];
	final GameElement[] gameElements2 = new GameElement[ 5000 ];

	GameElement[] gameElements = gameElements1;
	int gameElementsSize = 0;



	final GemPackage gemPkg1 = new GemPackage();
	final GemPackage gemPkg2 = new GemPackage();

	GemPackage gemPkg = gemPkg1;{
		gemPkg.gems = gameElements;
		gemPkg.size = gameElementsSize;
	}

	ArrayList<GameElement> needsToBeAdded = new ArrayList<GameElement>( 100 );


	Teams team;

	private String name;

	private final MM mm;

	private final ReentrantLock gameRunningLock = new ReentrantLock();
	private boolean gameRunning = false;

	public GemManager(MM mm){
		this.mm = mm;
	}







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
				if( ge.act() )
				{
					GridWorker.addToToBeGriddedQueue(ge.area, true);
					gameElements[i] = null;
				}
				else
					newGes[newGesSize++] = ge;

			}
			synchronized( needsToBeAdded ){
				for( GameElement ge : needsToBeAdded )
					newGes[newGesSize++] = ge;

				needsToBeAdded.clear();
			}

			GemPackage newGemPkg;
			if( gemPkg != gemPkg1 )
				newGemPkg = gemPkg1;
			else
				newGemPkg = gemPkg2;

			synchronized( newGemPkg ){
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


	public boolean add( GameElement ge )
	{
		if( ge == null )
		{
			if( TowerDefenceGame.testingVersion ){
				Log.e( TAG , "Trying to add a null GameElement.");}
			return false;
		}

		if( team != null && ge.getTeamName() != team  )
		{
			if( TowerDefenceGame.testingVersion ){
				Log.e( TAG , "Trying something with the wrong team to this manager.");}
			return false;
		}


		if( ge.create( mm ) )
		{

			if( GridWorker.checkAndAddToGrid( ge.area , false , mm.getLevel().getGrid() ))
			{
				//				if( KingsCastle.testingVersion && ge instanceof TreasureChest ){
				//					Log.e( TAG , "TreasureChest added to gem" );
				//				}

				synchronized( gameRunningLock )
				{
					if( gameRunning )
					{
						synchronized( needsToBeAdded ){
							needsToBeAdded.add( ge );
						}
						//Log.d( TAG , newB + " added to Bm while game is running.");
					}
					else
					{
						//Log.d( TAG , newB + " added to Bm before game has started.");
						gameElements[gameElementsSize++] = ge;
						gemPkg.size = gameElementsSize;
					}
				}
				return true;
			}
			if( TowerDefenceGame.testingVersion ){
				Log.e( TAG , ge + "'s area("+ge.area+") could not be added to grid");
			}
			return false;

		}
		if( TowerDefenceGame.testingVersion ){
			Log.e( TAG , ge + ".create() == false");
		}
		return false;

	}

	public void resume()
	{
		synchronized( gameRunningLock ){
			gameRunning = true;
		}
	}


	public void pause()
	{
		synchronized( gameRunningLock ){
			gameRunning = false;
		}
	}





	/**
	 * @return the name
	 */
	String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	void setName(String name) {
		this.name = name;
	}
	public int getSize() {
		return gameElementsSize;
	}


	void setTeam(Teams team2)
	{
		team = team2;
	}

	public void setGameElements( GameElement[] gameElements2 )
	{
		gameElements = gameElements2;
	}

	public GemPackage getGameElements()
	{
		synchronized( gemPkg ){
			return gemPkg;
		}
	}





	public void saveYourself( BufferedWriter b ) throws IOException
	{
		//////Log.d( TAG , "Saving gems");
		String s="<GEM>" ;
		b.write( s , 0 , s.length() ) ;
		b.newLine();

		GemPackage gemPkg = getGameElements();

		synchronized( gemPkg )
		{
			GameElement[] gems = gemPkg.gems;
			int gesSize = gemPkg.size;

			for( int i = 0 ; i < gesSize ; ++i )
			{
				gems[i].saveYourself( b );
			}
		}


		s = "</GEM>";
		b.write( s , 0 , s.length() );
		b.newLine();
	}





	public static class GemPackage{

		public GameElement[] gems;
		public int size;
	}




}
