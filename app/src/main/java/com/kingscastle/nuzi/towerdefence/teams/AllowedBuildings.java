package com.kingscastle.nuzi.towerdefence.teams;


import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Building;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Buildings;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AllowedBuildings
{
	private static final String TAG = "AllowedBuildings";
	private class AllowedBuildingsArrayList<T> extends ArrayList<T>{
		private static final long serialVersionUID = 7815637449367727912L;
		@Override
		public boolean add( T b ){
			throw new UnsupportedOperationException("Do not mess with this list outside the AllowedBuildings class.");
		}
		@Override
		public void add( int i , T b ){
			throw new UnsupportedOperationException("Do not mess with this list outside the AllowedBuildings class.");
		}
		@Override
		public boolean remove( Object b ){
			throw new UnsupportedOperationException("Do not mess with this list outside the AllowedBuildings class.");
		}
		@Override
		public T remove( int i ){
			throw new UnsupportedOperationException("Do not mess with this list outside the AllowedBuildings class.");
		}

		private boolean ladd( T b ){
			return super.add(b);
		}

		private void ladd( int i , T b ){
			super.add(i,b);
		}

		private boolean lremove( Object b ){
			return super.remove( b );
		}

		private T lremove( int i ){
			return super.remove(i);
		}
	}

	private final AllowedBuildingsArrayList<Building> allowedBuildings = new AllowedBuildingsArrayList<Building>();

	private boolean needsToRebuildScroller = true;




	public ArrayList<Building> getAllowedBuildings(){
		return allowedBuildings;
	}



	public void addBuilding(Building b)
	{
		//	Log.v(TAG, "addBuilding("+b+")");
		if( b == null || allowedBuildings.contains( b ) )
			return;


		allowedBuildings.ladd( b );

		sort();
	}


	private void sort(){
		Collections.sort(allowedBuildings, new Comparator<Building>(){
			@Override
			public int compare(Building lhs, Building rhs) {
				int l = lhs.lq.getRequiresTcLvl();
				int r = rhs.lq.getRequiresTcLvl();

				if( l < r )
					return -1;
				else if( l > r )
					return +1;
				else
					return 0;
			}
		});
	}


	public void addBuilding( Buildings b )
	{
		//Log.d( TAG , "addBuilding() b = " + b );
		for( Building bu : allowedBuildings )
		{
			if( bu.getBuildingsName() == b ) {
				//////Log.d( TAG , "bu.getBuildingsName() == b, building already in abs ");
				return;
			}
		}

		Building bd = Building.newInstance( b.name() );
		//////Log.d( TAG , "Building.newInstance( b ) = " + bd );
		if( bd != null )
		{
			allowedBuildings.ladd(bd);
			needsToRebuildScroller = true;
		}

		sort();
	}


	public void removeBuilding( Buildings b )
	{
		Building bu;

		for( int i = allowedBuildings.size() -1 ; i > -1 ; --i )
		{
			bu = allowedBuildings.get( i );
			if( bu.getBuildingsName() == b )
			{
				allowedBuildings.lremove( i );
				needsToRebuildScroller = true;
			}
		}
	}



	public void allowToBeBuild( Buildings b )
	{
		addBuilding( b );
	}


	public void replace(Building b) {

		Buildings bldin = b.getBuildingsName();

		for( int i = allowedBuildings.size() -1 ; i > -1 ; --i )
		{
			Building bu = allowedBuildings.get( i );
			if( bu.getBuildingsName() == bldin )
			{
				allowedBuildings.lremove( i );
				allowedBuildings.ladd( i , b  );
				needsToRebuildScroller = true;
			}
		}
	}



	public void setNeedsToRebuildScroller( boolean b )
	{
		needsToRebuildScroller = b ;
	}

	public boolean needToRebuildScroller()
	{
		return needsToRebuildScroller;
	}





	public void saveYourSelf( BufferedWriter b ) throws IOException
	{

		String s = "<AllowedBuildings>";
		b.write( s , 0 , s.length() );
		b.newLine();

		for( Building b2 : allowedBuildings )
		{
			s = "<Building name=\"" + b2 + "\"/>";
			b.write( s , 0 , s.length() );
			b.newLine();
		}

		s="</AllowedBuildings>";
		b.write( s , 0 , s.length() );
		b.newLine();
	}





}
