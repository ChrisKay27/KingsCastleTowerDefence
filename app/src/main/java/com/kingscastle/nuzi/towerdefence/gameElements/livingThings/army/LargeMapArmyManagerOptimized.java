package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army;


import com.kingscastle.nuzi.towerdefence.effects.animations.Anim;
import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.teams.Team;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class LargeMapArmyManagerOptimized
{

	private static final String TAG = "ArmyManager";

	private static float fourTenthsMapWidth;
	private static float fourTenthsMapHeight;
	private static float sixTenthsMapWidth;
	private static float sixTenthsMapHeight;
	private static float mapWidthDiv2;
	private static float mapHeightDiv2;

	private boolean gameRunning = false;
	private final ReentrantLock gameRunningLock = new ReentrantLock();



	private final ArrayList<LivingThing> needsToBeAdded = new ArrayList<LivingThing>( 100 );
	private       ArrayList<LivingThing> army = new ArrayList<LivingThing>( 100 );
	private final ArrayList<LivingThing> deadTroops = new ArrayList<LivingThing>( 50 );


	private long lastSorted;

	private CollisionPartitions cp = new CollisionPartitions();

	private long refreshPartitionsAt;


	private final LivingThing[] NW1 = new LivingThing[450];
	private final LivingThing[] NE1 = new LivingThing[450];
	private final LivingThing[] SW1 = new LivingThing[450];
	private final LivingThing[] SE1 = new LivingThing[450];

	private final LivingThing[] NW2 = new LivingThing[450];
	private final LivingThing[] NE2 = new LivingThing[450];
	private final LivingThing[] SW2 = new LivingThing[450];
	private final LivingThing[] SE2 = new LivingThing[450];

	private final MM mm;


	public LargeMapArmyManagerOptimized(MM mm){
		this.mm = mm;
	}


	public void act()
	{
		//		if( team == Teams.BLUE && timeToSort() )
		//		{
		//			try
		//			{
		//				if( army != null && army.size() != 0 )
		//				{
		//					long dt = System.nanoTime();
		//					synchronized( army ){
		//						//Log.d(TAG , "Acquired army.monitorLock in " +(System.nanoTime()-dt) );
		//						LivingThing temp = army.get(0);
		//						if( temp != null )
		//							LivingThingRangeComparator.sortThis( army );
		//					}
		//				}
		//			}
		//			catch( Exception e ){
		//				e.printStackTrace();
		//			}
		//		}

		// Partitions all units in this army into groups based on where they are on the map to improve lookup times
		if( refreshPartitionsAt < GameTime.getTime() )
		{
			if( mapWidthDiv2 == 0 )
			{
				mapWidthDiv2  = Rpg.getMapWidthInPxDiv2();
				mapHeightDiv2 = Rpg.getMapHeightInPxDiv2();
				fourTenthsMapWidth = (Rpg.getMapWidthInPx()*4)/10;
				fourTenthsMapHeight = (Rpg.getMapHeightInPx()*4)/10;
				sixTenthsMapWidth = (Rpg.getMapWidthInPx()*6)/10;
				sixTenthsMapHeight = (Rpg.getMapHeightInPx()*6)/10;
			}
			LivingThing[] NW = NW1;
			LivingThing[] NE = NE1;
			LivingThing[] SW = SW1;
			LivingThing[] SE = SE1;
			if( cp.NW == NW2 ){
				NW = NW1;
				NE = NE1;
				SW = SW1;
				SE = SE1;
			}
			else{
				NW = NW2;
				NE = NE2;
				SW = SW2;
				SE = SE2;
			}


			int NWCount = 0;
			int SWCount = 0;
			int SECount = 0;
			int NECount = 0;


			//long dt = System.nanoTime();
			synchronized( army ){
				////Log.d(TAG , "Sorting Acquired army.monitorLock in " +(System.nanoTime()-dt) );
				for( LivingThing troop : army )
				{
					vector loc = troop.loc;

					boolean left = loc.x < fourTenthsMapWidth ;
					boolean top = loc.y < fourTenthsMapHeight ;

					if( left && top )
					{
						NW[NWCount++] = troop;
						continue;
					}

					boolean right = loc.x > sixTenthsMapWidth;
					if( right && top )
					{
						NE[NECount++] = troop;
						continue;
					}

					boolean bottom = loc.y > sixTenthsMapHeight;


					if( right && bottom )
					{
						SE[SECount++] = troop;
						continue;
					}
					if( left && bottom )
					{
						SW[SWCount++] = troop;
						continue;
					}
					if( left )
					{
						NW[NWCount++] = troop;
						SW[SWCount++] = troop;
						continue;
					}
					if( top )
					{
						NW[NWCount++] = troop;
						NE[NECount++] = troop;
						continue;
					}
					if( right )
					{
						NE[NECount++] = troop;
						SE[SECount++] = troop;
						continue;
					}
					if( bottom )
					{
						SE[SECount++] = troop;
						SW[SWCount++] = troop;
						continue;
					}

					NW[NWCount++] = troop;
					NE[NECount++] = troop;
					SE[SECount++] = troop;
					SW[SWCount++] = troop;
					continue;
				}
			}

			CollisionPartitions cp = new CollisionPartitions();
			cp.NE = NE;
			cp.NW = NW;
			cp.SE = SE;
			cp.SW = SW;
			cp.NECount = NECount;
			cp.SECount = SECount;
			cp.NWCount = NWCount;
			cp.SWCount = SWCount;

			synchronized( this.cp ){
				this.cp = cp;
			}
			refreshPartitionsAt = GameTime.getTime() + 2000;
		}

		boolean refreshPartitions = false;

		//long dt = System.nanoTime();


		synchronized( army ){
			////Log.d(TAG , "Act/Cleaning the Dead Acquired army.monitorLock in " +(System.nanoTime()-dt) );
			for( LivingThing troop : army )
			{
				if( troop.act() )//&& !(troop instanceof Hero) )
				{
					deadTroops.add( troop );
					mm.getTM().onUnitDestroyed( troop );
				}
			}
			army.removeAll( deadTroops );
			deadTroops.clear();
			//
			//			for( LivingThing lt : needsToBeAdded )
			//			{
			//				army.add( lt );
			//				refreshPartitions = true;
			//			}
			//			needsToBeAdded.clear();
		}



		if( refreshPartitions )
			refreshPartitionsAt = GameTime.getTime();
	}


	public boolean add( LivingThing u )
	{
		if( u == null )
			return false;
		else
		{
			synchronized( army ){
				if( army.contains( u ) )
					return false;
			}

			if( u.create( mm ) )
			{
				//long dt = System.nanoTime();
				synchronized( army ){
					////Log.d(TAG , "Add() Acquired army.monitorLock in " +(System.nanoTime()-dt) );
					army.add(u);
					refreshPartitionsAt = GameTime.getTime();
				}
				return true;
			}
			else
			{
				////Log.d( TAG , "u.create() == false" );
				return false;
			}
		}
	}



	public void remove( LivingThing lt )
	{
		if( lt == null )
			return;

		else
		{
			synchronized( army ){
				if( army.remove( lt ) )
				{
					if( lt.isDead() ){
						lt.getAnim().setOver( true );
						Team t = mm.getTeam(lt.getTeamName());
						if( t != null )
							t.onUnitDestroyed( lt );
					}
				}
			}
		}
	}

	public void remove( ArrayList<LivingThing> removedUnits )
	{
		if( removedUnits == null)
			return;

		else
		{
			synchronized( army ){
				army.removeAll( removedUnits );

				for( LivingThing lt : removedUnits )
				{
					Anim a = lt.getAnim();
					if( a != null )
						a.setOver( true );
					mm.getTM().onUnitDestroyed( lt );
				}
			}
		}
	}




	public void nullify()
	{
		if( army != null )
			synchronized( army ){
				army.clear();
				army = null;
			}
		fourTenthsMapWidth = 0;
		mapWidthDiv2 = 0;
	}


	public ArrayList<LivingThing> getNeedsToBeAdded(){
		return needsToBeAdded;
	}

	public ArrayList<LivingThing> getCloneArmy() {
		ArrayList<LivingThing> cloneArmy = new ArrayList<LivingThing>();

		synchronized( army ){
			for( LivingThing lt : army )
				cloneArmy.add( lt );
		}

		return cloneArmy;
	}

	public ArrayList<LivingThing> getArmy()
	{
		return army;
	}
	public void setArmy( ArrayList<LivingThing> cloneArmy )
	{
		army = cloneArmy;
	}





	public void finalInit( MM mm )
	{
		for( LivingThing lt: army )
			lt.finalInit( mm );
	}




	public CollisionPartitions getCollisionPartitions()
	{
		synchronized( cp )
		{
			return cp;
		}
	}






	public void resume(){
		synchronized( gameRunningLock ){
			gameRunning = true;
		}
	}
	public void pause(){
		synchronized( gameRunningLock ){
			gameRunning = false;
		}
	}





	public void saveYourSelf( BufferedWriter b ) throws IOException{

		synchronized( gameRunningLock ){
			if( gameRunning )
				throw new IllegalStateException("Trying to save when game is running");
		}
		//Log.d( TAG , "Saving AM, there are " + army.size() + " soldiers/workers");

		String s = "<ArmyManager>";
		b.write(s,0,s.length());
		b.newLine();


		for( LivingThing lt : army )
			lt.saveYourself( b );


		s = "</ArmyManager>";
		b.write(s,0,s.length());
		b.newLine();
	}










	public static class CollisionPartitions
	{

		public LivingThing[] NW;
		public LivingThing[] NE;
		public LivingThing[] SW;
		public LivingThing[] SE;

		public int NECount;
		public int SECount;
		public int SWCount;
		public int NWCount;


		public LivingThing[] getPartition( float x , float y )
		{
			if( x < mapWidthDiv2 )
			{
				if( y < mapHeightDiv2 )
					return NW;
				else
					return SW;
			}
			else
			{
				if( y < mapHeightDiv2 )
					return NE;
				else
					return SE;
			}
		}

		public int getSizeForPartition( float x , float y )
		{
			if( x < mapWidthDiv2 )
			{
				if( y < mapHeightDiv2 )
					return NWCount;
				else
					return SWCount;

			}
			else
			{
				if( y < mapHeightDiv2 )
					return NECount;
				else
					return SECount;
			}
		}
	}
















}
