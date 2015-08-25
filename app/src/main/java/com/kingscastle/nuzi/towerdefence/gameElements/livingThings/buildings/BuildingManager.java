package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings;

import android.support.annotation.Nullable;

import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.ListPkg;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameElements.movement.pathing.GridWorker;
import com.kingscastle.nuzi.towerdefence.teams.Team;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class BuildingManager
{
	private static final String TAG = "BuildingManager";

	private final List<Runnable> runnables = new ArrayList<>();

	//private final Teams team;

//	protected final Building[] buildings1 = new Building[1000];
//	protected final Building[] buildings2 = new Building[1000];

	protected List<Building> buildings = new LinkedList<>();

	//protected Building[] buildings = buildings1;

	protected int buildingsSize;


//	protected final ArrayList<Building> needsToBeAdded = new ArrayList<Building>( 100 );
//	protected final ArrayList<Building> wasAdded = new ArrayList<Building>();

	protected boolean pausedQueues = true;
	protected boolean gameRunning = false;
	protected final ReentrantLock gameRunningLock = new ReentrantLock();

	protected long timeToCleanBuildings;


//	protected final ListPkg<Building> pkg1 = new ListPkg<>();
//	protected final ListPkg<Building> pkg2 = new ListPkg<>();

	protected ListPkg<Building> pkg = new ListPkg<>();
	{
		Building[] bds = new Building[buildings.size()];
		pkg.list = buildings.toArray(bds);
		pkg.size = bds.length;
	}

	protected final MM mm;

	public BuildingManager( MM mm2 )
	{
		assert mm2 != null : "mm2 == null";
		mm = mm2;
	}

	public void act()
	{
		//Used to ensure no buildings will act when the game isn't running.
		//Since the game running flag can be
		synchronized( gameRunningLock ){
			if( !gameRunning )
				return;
		}

		synchronized (runnables) {
			for (Runnable r : runnables)
				r.run();
			runnables.clear();
		}

		if( timeToCleanBuildings < System.currentTimeMillis() ) {
			timeToCleanBuildings = System.currentTimeMillis() + 500;

			ListPkg<Building> nPkg = new ListPkg<>();
			Building[] bds = new Building[buildings.size()];
			nPkg.list = buildings.toArray(bds);
			nPkg.size = buildings.size();
			pkg = nPkg;
		}


		if( mm.getLevel().isPaused() )
			return;

		Iterator<Building> i = buildings.listIterator();
		while (i.hasNext())
			if (i.next().update())
				i.remove();


//
//
//
//		if( timeToCleanBuildings < GameTime.getTime() )
//		{
//			timeToCleanBuildings = GameTime.getTime() + 500;
//
//			Building[] newBuildings;
//			if( buildings == buildings1 )
//				newBuildings = buildings2;
//			else
//				newBuildings = buildings1;
//
//			int newBuildingsSize = 0;
//
//			for( int i = 0 ; i < buildingsSize ; ++i  )
//			{
//				Building b = buildings[i];
//				try {
//					if (b.act()) {                    ////Log.d( TAG , team + " : Removing a " + b + " from the buildingManager" );
//						GridWorker.addToGridNow(b.area,true,mm.getGridUtil().getGrid());
//						//GridWorker.addToToBeGriddedQueue(b.area, true);
//						mm.getTM().onBuildingDestroyed(b);
//					} else {
//						//b.checkBuildQueue(mm);
//						newBuildings[newBuildingsSize++] = b;
//					}
//				}catch(Exception e){
//					Log.e(TAG,"Error thrown while making " + b + " act.", e);
//				}
//			}
//
//
//
//			synchronized( needsToBeAdded ){
//				for( Building b : needsToBeAdded ){
//					newBuildings[newBuildingsSize++] = b;
//					wasAdded.add( b );
//				}
//				needsToBeAdded.clear();
//			}
//
//
//			ListPkg<Building> newPkg;
//			if( pkg == pkg1 )
//				newPkg = pkg2;
//			else
//				newPkg = pkg1;
//
//
//			synchronized( newPkg )
//			{
//				buildings = newBuildings;
//				buildingsSize = newBuildingsSize;
//				newPkg.list = buildings;
//				newPkg.size = buildingsSize;
//				pkg = newPkg;
//			}
//
//
//			for( Building b : wasAdded ){
//				Team t = mm.getTeam(b.getTeamName());
//				if( t != null )
//					t.onBuildingAddedToMap(b);
//			}
//			wasAdded.clear();
//
//		}
	}
	public boolean add( @NotNull final LivingThing u )
	{
		return add(u,null);
	}


	public boolean add( @NotNull final LivingThing u , @Nullable final OnBuildingAddedListener obal )
	{
//		if( u == null )
//		{
//			Log.e( TAG , "Trying to add a null building to BM");
//			return false;
//		}

		synchronized (runnables){
			runnables.add(new Runnable(){
				@Override
				public void run() {
					if( u.create( mm ) )
					{
						if( !u.hasBeenCreatedProperly() )
							throw new IllegalStateException("You must call super.create(mm) all subclasses of game element.");

						Building newB = ( Building ) u;
						newB.construct( mm );

						GridWorker.addToGridNow( u.area , false , mm.getLevel().getGrid() );

						buildings.add(newB);

						Team t = mm.getTeam(newB.getTeamName());
						if( t != null )
							t.onBuildingAddedToMap(newB);

						if( obal != null )
							obal.onBuildingAdded(newB);
						synchronized (bals){
							Iterator<OnBuildingAddedListener> i = bals.listIterator();
							while(i.hasNext())
								if( i.next().onBuildingAdded(newB))
									i.remove();
						}
//
//
//						synchronized( gameRunningLock )
//						{
//							if( gameRunning )
//							{
//								newB.finalInit(mm);
//								synchronized( needsToBeAdded ){
//									needsToBeAdded.add( newB );
//								}
//								//Log.d( TAG , newB + " added to Bm while game is running.");
//								//if( u instanceof PendingBuilding )
//								////Log.d( TAG , "PendingBuilding was added to the manager for team " + team );
//								//						if( !BuildingsUtil.isaFarm(u) )
//								//							GridWorker.addToToBeGriddedQueue( u.area , false );
//
//							}
//							else
//							{
//								////Log.d( TAG , newB + " added to Bm before game has started.");
//								buildings.add(newB);
//								//buildings[buildingsSize++] = newB;
//								//pkg.size = buildingsSize;
//								Team t = mm.getTeam(newB.getTeamName());
//								if( t != null )
//									t.onBuildingAddedToMap(newB);
//								//						if( !BuildingsUtil.isaFarm(u) )
//								//							GridWorker.addToGridNow( newB.area , false );
//
//							}


					}
				}
			});
		}

		return true;
	}



	public void finalInit( MM mm )
	{
		//Log.d( TAG , "finalInit(), buildingsSize=" + buildingsSize );

		for( Building b : buildings )
			BuildingFinalInitter.finalInit(b, mm );

		//Log.d( TAG , "finalInit() done");
	}



	public void resume()
	{
		synchronized( gameRunningLock ){
			gameRunning = true;
		}
		//resumeBuildQueues();
	}



	public ListPkg<Building> getBuildings()	{
		return pkg;
	}

/* FIXME Not used in tower defence
	void resumeBuildQueues()
	{
		if( !pausedQueues )
			return;

		pausedQueues = false;
		synchronized( pkg )
		{
			int size = pkg.size;
			Building[] buildings = pkg.list;
			for( int i = 0 ; i < size ; ++ i )
			{
				buildings[i].resumeBuildQueue();
			}
		}
	}*/

	public void pause()
	{
		synchronized( gameRunningLock ){
			gameRunning = false;
		}
		//pauseBuildQueues();
	}

/* FIXME Not used in tower defence
	void pauseBuildQueues()
	{
		if( pausedQueues )
			return;

		pausedQueues = true;
		synchronized( pkg )
		{
			for( int i = 0 ; i < buildingsSize ; ++ i )
				buildings[i].pauseBuildQueue();
		}
	}*/


	public long getTimeToCleanBuildings() {
		return timeToCleanBuildings;
	}

	public void setTimeToCleanBuildings(long timeToCleanBuildings) {
		this.timeToCleanBuildings = timeToCleanBuildings;
	}

	public void saveYourSelf( BufferedWriter b ) throws IOException {

		synchronized( gameRunningLock ){
			if( gameRunning )
				throw new IllegalStateException("Trying to save when game is running");
		}

		String s;

		s = "<BuildingManager>";

		b.write(s,0,s.length());
		b.newLine();


//		for( int i = 0 ; i < buildingsSize ; ++ i )
//		{
//			buildings[i].saveYourself( b );
//		}


		s = "</BuildingManager>";

		b.write(s,0,s.length());
		b.newLine();
	}


	//Building Completed
	private final ArrayList<OnBuildingAddedListener> bals = new ArrayList<>();

	public interface OnBuildingAddedListener{
		boolean onBuildingAdded(Building b);
	}

	public void addBal(OnBuildingAddedListener bcl)		   		{	synchronized( bals ){	bals.add( bcl );				}  	}
	public boolean removeBal(OnBuildingAddedListener bcl)		{	synchronized( bals ){	return bals.remove( bcl );		}	}


}


