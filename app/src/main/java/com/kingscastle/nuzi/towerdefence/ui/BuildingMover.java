package com.kingscastle.nuzi.towerdefence.ui;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.RectF;
import android.widget.Toast;

import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.framework.Input.TouchEvent;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.CD;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Building;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.BuildingAnim;
import com.kingscastle.nuzi.towerdefence.gameElements.movement.pathing.GridWorker;
import com.kingscastle.nuzi.towerdefence.gameUtils.CoordConverter;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.level.GridUtil;


public class BuildingMover {


	private static final ColorMatrixColorFilter cmcf;
	static{
		ColorMatrix cm = new ColorMatrix();
		cm.setScale(1, 0, 0, 1);
		cmcf = new ColorMatrixColorFilter(cm);
	}


	private final vector origLoc;
	private final RectF origArea;
	private final RectF largerArea = new RectF();

	private final vector tcLoc;
	private final Building b;
	private final BuildingAnim bAnim;

	private final CD cd;
	private final GridUtil gUtil;
	private final CoordConverter cc;
	private final vector downAt = new vector();
	private final vector mapRel = new vector();
	private final float maxDistFromTc;
	private boolean movingB;
	private boolean bIsPlaceable = true;
	private boolean bHasMoved;
	private final OnBuildingMovedListener obml;
	private long showAgainAt;

	private int pointer = -1;



	public BuildingMover( CD cd , GridUtil gUtil , CoordConverter cc , Building b , vector tcLoc , float maxDistFromTc , OnBuildingMovedListener obml ){
		this.tcLoc = tcLoc;
		this.maxDistFromTc = maxDistFromTc;
		this.cd = cd;
		this.gUtil = gUtil;
		this.cc = cc;
		this.b = b;
		this.bAnim = b.getBuildingAnim();
		this.obml = obml;
		origLoc = new vector(b.loc);
		origArea = new RectF(b.area);
	}



	public synchronized boolean analyzeTouchEvent( TouchEvent e ){
		if( bAnim == null ){
			//Log.e( "BuildingMover" , "bAnim == null");
			return false;
		}

		if( pointer != -1 )
			if( pointer != e.pointer )
				return false;


		if( e.type == TouchEvent.TOUCH_DOWN ){
			//if( movingB )
			//Log.e( "BuildingMover" , pointer + ":Touch Down Event and movingB is set to true!");
			////Log.d("BuildingMover", "TouchEvent.TOUCH_DOWN at " + e.x + "," + e.y );


			//Record down location to check to see if the building was actually moved
			downAt.set( e.x , e.y );
			mapRel.set(e.x , e.y);


			cc.getCoordsScreenToMap(mapRel, mapRel);



			largerArea.set( b.area );
			largerArea.inset( -Rpg.gridSize , -Rpg.gridSize );

			////Log.d("BuildingMover", "mapRel= " + mapRel );
			if( largerArea.contains(mapRel.x, mapRel.y) ){
				//Log.d("BuildingMover", pointer + ":Started moving the building");
				b.setSelected( false );

				movingB = true;
				pointer = e.pointer;

				if( bIsPlaceable ){
					GridWorker.addToToBeGriddedQueue(new RectF(b.area), true);
					//Log.v("BuildingMover", pointer + ":Removed building area from grid area:" + b.area);
				}
				else{
					//Log.d("BuildingMover", pointer + ":TouchDown and b is not placeable");
				}
				return false;
			}
		}
		else if( e.type == TouchEvent.TOUCH_DRAGGED && movingB ){
			mapRel.set(e.x,e.y);
			cc.getCoordsScreenToMap(mapRel, mapRel);

			b.loc.set(mapRel);
			b.updateArea();

			gUtil.setProperlyOnGrid(b.area, Rpg.gridSize);
			GridUtil.getLocFromArea(b.area,b.getPerceivedArea(),b.loc);

			if( !b.area.equals(origArea) ){
				////Log.d("BuildingMover", "bHasMoved=true");
				bHasMoved = true;
			}


			if( b.loc.distanceSquared(tcLoc) > maxDistFromTc ){
				showToFarFromTcMessage();
				//Reset loc to a placeable loc
				b.loc.set( origLoc); //Adding June 25
				b.area.set(origArea ); //Adding June 25
				b.setSelected( true );
				bHasMoved = false;
				movingB = false;

				//				if( b.getBuildingAnim() != null )
				//					b.getBuildingAnim().getPaint().setColorFilter(cmcf);
				return true;
			}
			if( cd.checkPlaceable2( b.area , false ) ){

				/////Log.d("BuildingMover", pointer + ":Moved building is placeable.");
				if( b.getBuildingAnim() != null )
					b.getBuildingAnim().getPaint().setColorFilter(null);

				bIsPlaceable = true;
			}
			else{
				////Log.d("BuildingMover", pointer + ":Moved building is not placeable.");
				if( b.getBuildingAnim() != null )
					b.getBuildingAnim().getPaint().setColorFilter(cmcf);

				bIsPlaceable = false;
			}
			return true;
		}
		else if( e.type == TouchEvent.TOUCH_UP && movingB ){
			movingB = false;
			pointer = -1;

			if( b.loc.distanceSquared(tcLoc) > maxDistFromTc ){
				////Log.d("BuildingMover", pointer + ":TouchEvent.TOUCH_UP and not placeable" );
				showToFarFromTcMessage();

				bIsPlaceable = false;
				if( b.getBuildingAnim() != null )
					b.getBuildingAnim().getPaint().setColorFilter(cmcf);
			}
			else if( !cd.checkPlaceable2(b.area, false ) ){
				bIsPlaceable = false;
				////Log.d("BuildingMover", pointer + ":TouchEvent.TOUCH_UP and not placeable" );
				if( b.getBuildingAnim() != null )
					b.getBuildingAnim().getPaint().setColorFilter(cmcf);
			}
			else{
				bIsPlaceable = true;

				GridWorker.addToToBeGriddedQueue( new RectF(b.area) , false );
				//Log.d("BuildingMover", pointer + ":TouchEvent.TOUCH_UP and placeable." );
				//Log.v("BuildingMover", pointer + ":Adding " + b.area + " to grid." );
				b.setSelected( true );
				origLoc.set( b.loc );
				origArea.set( b.area );
				bHasMoved = false;
				obml.onBuildingMoved(b);
			}

			//See if the building was actually moved, if not do not use up touch event.
			if( downAt.isNear(e.x, e.y, Rpg.eightDp ))
				return bHasMoved;


			return true;
		}


		return false;
	}






	private void showToFarFromTcMessage(){
		if( showAgainAt < GameTime.getTime() ){
			Rpg.getGame().alert("To far from Town Centre", Toast.LENGTH_SHORT);
			showAgainAt =GameTime.getTime() + 2000;
		}

	}



	public synchronized void cancel(){

		b.setLoc( origLoc );
		b.updateArea();
		if( b.getBuildingAnim() != null )
			b.getBuildingAnim().getPaint().setColorFilter(null);

		GridWorker.addToToBeGriddedQueue( b.area , false );
		bHasMoved = false;
	}

	public boolean placeBuilding(){
		return cd.checkPlaceable2(b.area, false);
	}















}
