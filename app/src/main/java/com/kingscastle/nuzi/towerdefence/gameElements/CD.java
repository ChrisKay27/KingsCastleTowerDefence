package com.kingscastle.nuzi.towerdefence.gameElements;

import android.graphics.RectF;
import android.support.annotation.Nullable;

import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.ArmyManager.CollisionPartitions;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Building;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.GemManager.GemPackage;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.ListPkg;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameElements.movement.Inter;
import com.kingscastle.nuzi.towerdefence.gameElements.movement.Line;
import com.kingscastle.nuzi.towerdefence.gameElements.movement.pathing.Grid;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.level.Level;
import com.kingscastle.nuzi.towerdefence.teams.Team;
import com.kingscastle.nuzi.towerdefence.teams.Teams;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CD
{
	private static final String TAG = "CollisionDetector";


	private final MM mm;
	private final Grid grid;

	public CD( MM mm, Grid grid ){
		this.mm = mm;
		this.grid = grid;
	}




	public ArrayList<GameElement> checkCollision( Teams ignoreThisTeam , Teams onlyOnThisTeam , RectF r , ArrayList<GameElement> intoThisList ){
		return checkCollision( ignoreThisTeam ,  onlyOnThisTeam ,  false ,  r , intoThisList );
	}


	public ArrayList<GameElement> checkCollision( Teams ignoreThisTeam , Teams onlyOnThisTeam , boolean ignoreGes , RectF r , ArrayList<GameElement> intoThisList )
	{
		float x = r.centerX();
		float y = r.centerY();

		if( intoThisList == null )
			intoThisList = new ArrayList<GameElement>();

		for ( Team team : mm.getTM().getTeams() )
		{
			if( team.getTeamName() == ignoreThisTeam )
				continue;

			if( onlyOnThisTeam != null && team.getTeamName() != onlyOnThisTeam )
				continue;


			CollisionPartitions cp = team.getAm().getCollisionPartitions();

			LivingThing[] army = cp.getPartition(x, y);
			int size = cp.getSizeForPartition(x, y);


			for( int i = 0 ; i < size ; ++i )
			{
				LivingThing lt = army[i];

				if( lt.area == r )
					continue;



				if( RectF.intersects( r , lt.area ) )
					intoThisList.add( lt );
			}


			ListPkg<Building> bPkg = team.getBm().getBuildings();

			synchronized( bPkg )
			{
				Building[] buildings = bPkg.list;
				int size2 = bPkg.size;

				for( int i = 0 ; i < size2 ; ++i )
				{
					Building b = buildings[i];

					if( b.area == r )
						continue;


					if( RectF.intersects( r , b.area ) )
						intoThisList.add( b );
				}
			}
		}
		if( !ignoreGes ){
			GemPackage gemPkg = mm.getGem( x , y ).getGameElements();

			synchronized( gemPkg )
			{
				GameElement[] gems = gemPkg.gems;
				int gesSize = gemPkg.size;

				for( int i = 0 ; i < gesSize ; ++i )
				{
					GameElement ge = gems[i];
					if( RectF.intersects( r , ge.area ) )
						intoThisList.add( ge );

				}
			}
		}
		return intoThisList;
	}


	@NotNull
	public ArrayList<LivingThing> checkMultiHit(@NotNull Teams ignoreThisTeam,@NotNull RectF r , boolean onThisTeam ,@NotNull ArrayList<LivingThing> intoThisList )
	{
		return checkMultiHit ( ignoreThisTeam , r , onThisTeam , false , intoThisList );
	}


	@NotNull
	public ArrayList<LivingThing> checkMultiHit(@NotNull Teams ignoreThisTeam,@NotNull RectF r, boolean onThisTeam)
	{
		return checkMultiHit ( ignoreThisTeam , r , onThisTeam , new ArrayList<LivingThing>() );
	}

	@NotNull
	public ArrayList<LivingThing> checkMultiHit(@NotNull Teams ignoreThisTeam, RectF r)
	{
		return checkMultiHit ( ignoreThisTeam , r , false );
	}

	@NotNull
	public ArrayList<LivingThing> checkMultiHit(@NotNull Teams ignoreThisTeam,@NotNull RectF r, boolean onThisTeam, boolean unitsOnly,@NotNull ArrayList<LivingThing> intoThisList)
	{

		float x = r.centerX();
		float y = r.centerY();


		for ( Team team : mm.getTM().getTeams() )
		{
			if ( onThisTeam && team.getTeamName() != ignoreThisTeam )
				continue;

			if ( !onThisTeam && team.getTeamName() == ignoreThisTeam )
				continue;

			CollisionPartitions cp = team.getAm().getCollisionPartitions();


			LivingThing[] army = cp.getPartition(x, y);
			int size = cp.getSizeForPartition(x, y);


			for( int i = 0 ; i < size ; ++i )
			{
				LivingThing lt = army[i];

				if( lt.area == r )
					continue;


				if( RectF.intersects( r , lt.area ) )
					intoThisList.add( lt );
			}


			if( unitsOnly )
				continue;


			ListPkg<Building> bPkg = team.getBm().getBuildings();

			synchronized( bPkg )
			{
				Building[] buildings = bPkg.list;
				int size2 = bPkg.size;

				for( int i = 0 ; i < size2 ; ++i )
				{
					Building b = buildings[i];

					if( b.area == r )
						continue;

					if( RectF.intersects( r , b.area ) )
						intoThisList.add( b );

				}
			}
		}


		return intoThisList;
	}

	@Nullable
	public LivingThing checkSingleHit(@NotNull Teams notOnThisTeam ,@NotNull RectF r ){
		return checkSingleHit( notOnThisTeam , r , false );
	}

	@Nullable
	public LivingThing checkSingleHit(@NotNull Teams notOnThisTeam ,@NotNull RectF r , boolean onThisTeam )
	{
		return checkSingleHit(notOnThisTeam,r,onThisTeam,true);
	}

	@Nullable
	public LivingThing checkSingleHit(@NotNull Teams notOnThisTeam ,@NotNull RectF r , boolean onThisTeam , boolean lookAtBuildings )
	{
		float x = r.centerX();
		float y = r.centerY();


		//Unwalkable grid optimization
		if( lookAtBuildings ) {
			Level level = mm.getLevel();
			if (level == null) return null;
			Grid grid = level.getGrid();

			boolean[][] tiles = grid.getGridTiles();
			float gridSize = grid.getGridSize();

			int left = (int) (r.left / gridSize);
			int top = (int) (r.top / gridSize);
			int right = (int) (r.right / gridSize);
			int bottom = (int) (r.bottom / gridSize);

			left = left < 0 ? 0 : left;
			right = right == left ? left + 1 : right;
			top = top < 0 ? 0 : top;
			bottom = bottom == top ? top + 1 : bottom;

			left = left >= tiles.length ? tiles.length - 1 : left;
			right = right >= tiles.length ? tiles.length - 1 : right;
			top = top >= tiles[0].length ? tiles[0].length - 1 : top;
			bottom = bottom >= tiles[0].length ? tiles[0].length - 1 : bottom;

			//Log.d( TAG , "left: " + left + " right: " + right + " top: " + top + " bottom: " + bottom );

			//set this to false and if it finds the tile is unwalkable it will be set to true
			lookAtBuildings = false;
			for (int i = left; i <= right; ++i)
				for (int j = top; j <= bottom; ++j)
					if (!tiles[i][j])
						lookAtBuildings = true;
		}
		//Log.d( TAG , "lookAtBuildings: " + lookAtBuildings);
		//End Unwalkable grid optimization


		for ( Team team :  mm.getTM().getTeams() )
		{
			if ( onThisTeam && team.getTeamName() != notOnThisTeam )
				continue;

			if ( !onThisTeam && team.getTeamName() == notOnThisTeam)
				continue;


			CollisionPartitions cp = team.getAm().getCollisionPartitions();

			LivingThing[] army = cp.getPartition(x, y);
			int size = cp.getSizeForPartition(x, y);


			for( int i = 0 ; i < size ; ++i )
			{
				LivingThing lt = army[i];

				if( lt.area == r )
					continue;

				if( RectF.intersects( r , lt.area ) )
					return lt ;
			}

			if( lookAtBuildings ){
				ListPkg<Building> bPkg = team.getBm().getBuildings();

				synchronized( bPkg )
				{
					Building[] buildings = bPkg.list;
					int size2 = bPkg.size;

					for( int i = 0 ; i < size2 ; ++i )
					{
						Building b = buildings[i];

						//|| BuildingsUtil.isaFarm(b) )
						if( b.area == r )
							continue;
						else if( RectF.intersects( r , b.area ) )
							return b;
					}
				}
			}
		}

		return null;
	}


	@Nullable
	public GameElement checkPlaceableOrTarget( vector v )
	{
		Level level = mm.getLevel();

		float x = v.x;
		float y = v.y;

		Grid grid = level.getGrid();
		boolean[][] tiles = grid.getGridTiles();
		float gridSize = grid.getGridSize();

		int I   = (int)( x /gridSize);
		int J   = (int)( y /gridSize);


		I = I < 0 ? 0 : I;
		I = I >= tiles.length ? tiles.length-1 : I;
		J = J < 0 ? 0 : J;
		J = J >= tiles[0].length ? tiles[0].length-1 : J;

		boolean skipGesAndBuildings = tiles[I][J];




		for ( Team team :  mm.getTM().getTeams() )
		{
			CollisionPartitions cp = team.getAm().getCollisionPartitions();

			LivingThing[] army = cp.getPartition(x, y);
			int size = cp.getSizeForPartition(x, y);


			for( int i = 0 ; i < size ; ++i )
			{
				LivingThing lt = army[i];

				if( lt.area.contains( x , y ) )
					return lt ;

			}

			if( skipGesAndBuildings )
				continue;

			ListPkg<Building> bPkg = team.getBm().getBuildings();

			synchronized( bPkg )
			{
				Building[] buildings = bPkg.list;
				int size2 = bPkg.size;

				for( int i = 0 ; i < size2 ; ++i )
				{
					if( buildings[i].area.contains( x , y ) )
						return buildings[i];
				}
			}
		}

		if( skipGesAndBuildings )
			return null;

		GemPackage gemPkg = mm.getGem( x , y ).getGameElements();

		synchronized( gemPkg )
		{
			GameElement[] gems = gemPkg.gems;
			int gesSize = gemPkg.size;

			for( int i = 0 ; i < gesSize ; ++i )
			{
				if( gems[i].area.contains( x , y ) )
					return gems[i] ;
			}
		}

		return null;
	}


	public GameElement checkPlaceable( RectF rectF )
	{
		return checkPlaceable( rectF , false );
	}


	public GameElement checkPlaceable( RectF rectF , boolean ignoreUnits )
	{

		float x = rectF.centerX();
		float y = rectF.centerY();


		for ( Team team :  mm.getTM().getTeams() )
		{
			if( !ignoreUnits )
			{
				CollisionPartitions cp = team.getAm().getCollisionPartitions();

				LivingThing[] army = cp.getPartition(x, y);
				int size = cp.getSizeForPartition(x, y);

				for( int i = 0 ; i < size ; ++i )
				{
					LivingThing lt = army[i];

					if( RectF.intersects( lt.area , rectF ) )
						return lt ;
				}
			}

			ListPkg<Building> bPkg = team.getBm().getBuildings();

			synchronized( bPkg )
			{
				Building[] buildings = bPkg.list;
				int size2 = bPkg.size;

				for( int i = 0 ; i < size2 ; ++i )
				{
					Building b = buildings[i];
					if( RectF.intersects( b.area , rectF ) )
						return b ;
				}
			}
		}




		GemPackage gemPkg = mm.getGem( x , y ).getGameElements();

		synchronized( gemPkg )
		{
			GameElement[] gems = gemPkg.gems;
			int gesSize = gemPkg.size;

			for( int i = 0 ; i < gesSize ; ++i )
				if( RectF.intersects( gems[i].area , rectF ) )
					return gems[i] ;

		}


		return null;
	}



	public boolean checkPlaceable2( RectF area , boolean ignoreUnits ){
		return checkPlaceable2( area , ignoreUnits , true );
	}



	public boolean checkPlaceable2( RectF area , boolean ignoreUnits , boolean checkBuildings )
	{
		boolean[][] tiles = grid.getGridTiles();
		float gridSize = grid.getGridSize();

		int left   = (int)(area.left  /gridSize);
		int top    = (int)(area.top   /gridSize);
		int right  = (int)(area.right /gridSize);
		int bottom = (int)(area.bottom/gridSize);

		left = left < 0 ? 0 : left;
		right = right >= tiles.length ? tiles.length-1 : right;
		top = top < 0 ? 0 : top;
		bottom = bottom >= tiles[0].length ? tiles[0].length-1 : bottom;

		for( int i = left ; i < right ; ++i )
			for( int j = top ; j < bottom ; ++j )
				if( !tiles[i][j] )
					return false;



		float x = area.centerX();
		float y = area.centerY();

		for( Team team :  mm.getTM().getTeams() )
		{
			if( checkBuildings ){
				ListPkg<Building> pkg = team.getBm().getBuildings();
				synchronized( pkg ){
					Building[] buildings = pkg.list;
					int size2 = pkg.size;

					for( int i = 0 ; i < size2 ; ++i )
					{
						Building b = buildings[i];
						if( b == null )
							continue;
						if( area == b.area )
							continue;

						if( RectF.intersects( b.area, area ) )
							return false;
					}
				}
			}


			if( ignoreUnits )
				continue;

			CollisionPartitions cp = team.getAm().getCollisionPartitions();

			LivingThing[] army = cp.getPartition       (x, y);
			int size           = cp.getSizeForPartition(x, y);

			for( int i = 0 ; i < size ; ++i )
			{
				LivingThing lt = army[i];

				if( lt.area == area )
					continue;
				if( RectF.intersects( lt.area , area ) )
					return false;
			}

		}
		return true;
	}






	public boolean checkPlaceable( vector here )
	{
		if( here == null || grid == null )
			return false;


		boolean[][] tiles = grid.getGridTiles();
		float gridSize = grid.getGridSize();

		int locI = (int)(here.x/gridSize);
		int locJ = (int)(here.y/gridSize);

		if( locI < 0 || locI >= tiles.length || locJ < 0 || locJ >= tiles[0].length)
			return false;

		if( !tiles[locI][locJ] )
			return false;


		GameElement ge = checkPlaceableOrTarget( here ) ;

		if( ge == null )
			return true;
		else
			return false;
	}


	public boolean checkHitWall( Line l )
	{
		Grid grid = mm.getLevel().getGrid();
		boolean[][] tiles = grid.getGridTiles();
		float gridSize = grid.getGridSize();

		int locI = (int)(l.start.x/gridSize);
		int locJ = (int)(l.start.y/gridSize);

		if( !tiles[locI][locJ] )
			return true;

		locI = (int)(l.end.x/gridSize);
		locJ = (int)(l.end.y/gridSize);

		if( !tiles[locI][locJ] )
			return true;



		//Building buildingToBuild;

		for( Team t : mm.getTM().getTeams() )
		{
			ListPkg<Building> bPkg = t.getBm().getBuildings();

			synchronized( bPkg )
			{
				Building[] buildings = bPkg.list;
				int size2 = bPkg.size;

				for( int i = 0 ; i < size2 ; ++i )
				{
					Building b = buildings[i];

					if( b == null )
						continue;

					//					if ( b instanceof Farm )
					//						continue;
					//
					//					if ( b instanceof PendingBuilding )
					//					{
					//						buildingToBuild = ( (PendingBuilding) b ).getBuildingToBuild();
					//						if ( buildingToBuild != null && buildingToBuild.getBuildingsName() == Buildings.Farm )
					//						{
					//							continue;
					//						}
					//					}
					if( Inter.intersects(b.area, l) )
					{
						////////Log.d( TAG , "Whisker hit a " + lt );
						return true;
						// System.out.println("inter at " + lt.area);
					}
				}
			}
		}

		GemPackage gemPkg = mm.getGem( l.start.x , l.start.y ).getGameElements();

		synchronized( gemPkg )
		{
			GameElement[] gems = gemPkg.gems;
			int gesSize = gemPkg.size;

			for( int i = 0 ; i < gesSize ; ++i )
			{
				if ( Inter.intersects( gems[i].area , l ) )
				{
					return true ;
				}
			}
		}


		gemPkg = mm.getGem( l.end.x , l.end.y ).getGameElements();

		synchronized( gemPkg )
		{
			GameElement[] gems = gemPkg.gems;
			int gesSize = gemPkg.size;

			for( int i = 0 ; i < gesSize ; ++i )
				if ( Inter.intersects( gems[i].area , l ) )
					return true ;
		}

		//		ArrayList<GameElement> gems2 = Rpg.getMM().getGem( l.end.x , l.end.y ).getGameElements();
		//
		//		if( gems2 != gems )
		//		{
		//			gems = gems2;
		//
		//			for ( int i = gems.size() - 1 ; i > -1 ; --i )
		//			{
		//				ge = gems.get( i );
		//				////////Log.d( TAG , "Looking at a " + ge );
		//
		//				if ( Inter.intersects ( ge.area , l ) )
		//				{
		//					return true;
		//					////////Log.d( TAG , "Whisker hit a " + ge );
		//				}
		//			}
		//		}
		return false;
	}




	/**
	 * Finds something who's area intersects this line.
	 * @param l
	 * @return
	 */
	public GameElement getLineCollision( Line l )
	{




		for( Team t : mm.getTM().getTeams() )
		{
			ListPkg<Building> bPkg = t.getBm().getBuildings();

			synchronized( bPkg )
			{
				Building[] buildings = bPkg.list;
				int size2 = bPkg.size;

				for( int i = 0 ; i < size2 ; ++i )
				{
					Building b = buildings[i];


					if( b == null )
						continue;

					//					if( b instanceof Farm )
					//						continue;
					//
					//					if( b instanceof PendingBuilding )
					//					{
					//						Building buildingToBuild = ( (PendingBuilding) b ).getBuildingToBuild();
					//						if ( buildingToBuild != null && buildingToBuild.getBuildingsName() == Buildings.Farm )
					//							continue;
					//					}
					if( Inter.intersects( b.area, l ) )
					{
						////////Log.d( TAG , "Whisker hit a " + b );
						return b;
						// System.out.println("inter at " + lt.area);
					}
				}
			}
		}


		GemPackage gemPkg = mm.getGem( l.start.x , l.start.y ).getGameElements();

		synchronized( gemPkg )
		{
			GameElement[] gems = gemPkg.gems;
			int gesSize = gemPkg.size;

			for( int i = 0 ; i < gesSize ; ++i )
			{
				if ( Inter.intersects( gems[i].area , l ) )
				{
					return gems[i] ;
				}
			}
		}

		//		ArrayList<GameElement> gems = Rpg.getMM().getGem( l.act.x , l.act.y ).getGameElements();
		//
		//		for ( int i = gems.size() - 1 ; i > -1 ; --i )
		//		{
		//
		//			GameElement ge = gems.get( i );
		//			////////Log.d( TAG , "Looking at a " + ge );
		//
		//			if ( Inter.intersects ( ge.area , l ) )
		//			{
		//				return ge;
		//				////////Log.d( TAG , "Whisker hit a " + ge );
		//			}
		//		}

		return null;
	}







	/**
	 * Finds something who's area intersects this line.
	 * @param l
	 * @return
	 */
	public ArrayList<GameElement> getLineCollisions( Line l )
	{
		ArrayList<GameElement> hitThings = new ArrayList<GameElement>();

		for( Team t : mm.getTM().getTeams() )
		{
			ListPkg<Building> bPkg = t.getBm().getBuildings();

			synchronized( bPkg )
			{
				Building[] buildings = bPkg.list;
				int size2 = bPkg.size;

				for( int i = 0 ; i < size2 ; ++i )
				{
					Building b = buildings[i];
					if ( b == null )
					{
						continue;
					}
					//					if ( b instanceof Farm )
					//					{
					//						continue;
					//					}
					//					if ( b instanceof PendingBuilding )
					//					{
					//						Building  buildingToBuild = ( (PendingBuilding) b ).getBuildingToBuild();
					//						if ( buildingToBuild != null && buildingToBuild.getBuildingsName() == Buildings.Farm )
					//						{
					//							continue;
					//						}
					//					}
					if ( Inter.intersects( b.area, l ) )
					{
						////////Log.d( TAG , "Whisker hit a " + b );
						hitThings.add( b );
						// System.out.println("inter at " + lt.area);
					}
				}
			}
		}

		GemPackage gemPkg = mm.getGem().getGameElements();

		synchronized( gemPkg )
		{
			GameElement[] gems = gemPkg.gems;
			int gesSize = gemPkg.size;

			for( int i = 0 ; i < gesSize ; ++i )
			{
				if ( Inter.intersects( gems[i].area , l ) )
				{
					hitThings.add( gems[i] );
				}
			}
		}

		//		ArrayList<GameElement> gems = mm.getGem().getGameElements();
		//
		//		for ( int i = gems.size() - 1 ; i > -1 ; --i )
		//		{
		//
		//			GameElement ge = gems.get( i );
		//			////////Log.d( TAG , "Looking at a " + ge );
		//
		//			if ( Inter.intersects ( ge.area , l ) )
		//			{
		//				hitThings.add( ge );
		//				////////Log.d( TAG , "Whisker hit a " + ge );
		//			}
		//		}

		return hitThings;
	}



















}
