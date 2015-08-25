package com.kingscastle.nuzi.towerdefence.gameElements.movement.pathing;


import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;

public class Path {

	private ArrayList<vector> formationPositions;
	private ArrayList<vector> path;
	private vector myFormationPosition;
	private int indexOfNextNode = 1;
	private final Int nextFormationPosition;

	private boolean humanOrdered;

	public Path(ArrayList<vector> vectorPathToStart)
	{
		setPath(vectorPathToStart);
		nextFormationPosition = new Int(0);
	}

	public Path( Path anotherPath )
	{
		path = anotherPath.getPath();
		formationPositions = anotherPath.getFormationPositions();
		nextFormationPosition = anotherPath.nextFormationPosition;
		drawAgainAt = anotherPath.drawAgainAt;
	}

	public ArrayList<vector> getPath() {
		return path;
	}

	void setPath(ArrayList<vector> path) {
		this.path = path;
	}

	public int size() {
		return path.size();
	}

	public vector getNext()
	{

		if( indexOfNextNode >= size()-1 )
		{
			////Log.d( "Path" , "indexOfCurrentDest > size() : " + indexOfNextNode + " > " + size() );
			return null;
		}
		else
		{
			return path.get( ++indexOfNextNode );
		}

	}


	public vector get( int index )
	{
		if( index >= path.size() )
		{
			return null;
		}
		else
		{
			return path.get( index );
		}
	}



	public vector getMyFormationPosition()
	{
		return myFormationPosition;
	}

	public vector findMyFormationPosition()
	{
		if( myFormationPosition == null )
		{
			myFormationPosition = getNextFormationPosition();
		}
		return myFormationPosition;
	}

	vector getNextFormationPosition()
	{
		ArrayList<vector> formPos = formationPositions;
		if( formPos != null && formPos.size() != 0 )
		{
			if( formPos.size() <= nextFormationPosition.value )
				nextFormationPosition.value %= formPos.size();


			vector v = formPos.get( nextFormationPosition.value++ );
			////Log.d( "Path" , "Pre mode" + nextFormationPosition.value );
			nextFormationPosition.value %= formPos.size();
			////Log.d( "Path" , "After mod" + nextFormationPosition.value );
			return v;

		}
		return null;
	}


	public boolean isEmpty() {
		return path.isEmpty();
	}

	public int getIndexOfNextNode() {
		return indexOfNextNode;
	}


	public void setIndexOfNextNode(int indexOfNextNode)
	{
		if( indexOfNextNode < 1 )
			this.indexOfNextNode = 1;

		else
			this.indexOfNextNode = indexOfNextNode;
	}

	public ArrayList<vector> getFormationPositions() {
		return formationPositions;
	}

	public void setFormationPositions(ArrayList<vector> formationPositions) {
		this.formationPositions = formationPositions;
	}

	public void add(vector toHere)
	{
		path.add(toHere);
	}


	private Long2 drawAgainAt = new Long2(0);

	public boolean hasBeenDrawnRecently() {
		if( drawAgainAt.value > GameTime.getTime() )
			return true;
		else
		{
			drawAgainAt.value = GameTime.getTime() + 40 ;
			return false;
		}

	}


	private static class Long2
	{
		private long value = 0;
		public Long2( long value ){
			this.value = value;
		}
	}

	private static class Int
	{
		private int value = 0;
		public Int( int value ){
			this.value = value;
		}
	}



	public boolean humanOrdered() {
		return humanOrdered;
	}
	public void setHumanOrdered(boolean humanOrdered) {
		this.humanOrdered = humanOrdered;
	}







}
