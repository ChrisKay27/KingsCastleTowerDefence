package com.kingscastle.nuzi.towerdefence.ui;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.RectF;
import android.widget.Toast;

import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.framework.Input.TouchEvent;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.CD;
import com.kingscastle.nuzi.towerdefence.gameElements.movement.pathing.GridWorker;
import com.kingscastle.nuzi.towerdefence.gameUtils.CoordConverter;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.level.GridUtil;


public class Mover {


	private static final ColorMatrixColorFilter cmcf;
	static{
		ColorMatrix cm = new ColorMatrix();
		cm.setScale(1, 0, 0, 1);
		cmcf = new ColorMatrixColorFilter(cm);
	}


	private final vector origLoc;
	private final RectF origArea;
	private final RectF largerArea = new RectF();

	private final GridUtil gUtil;
	private final vector tcLoc;
	private final Moveable m;


	private final CD cd;
	private final CoordConverter cc;
	private final vector downAt = new vector();
	private final vector mapRel = new vector();
	private final float maxDistFromTc;
	private boolean movingB;
	private boolean bIsPlaceable = true;
	private boolean bHasMoved;
	private long showAgainAt;

	private int pointer = -1;

	public interface Moveable{
		RectF getArea();
		vector getLoc();
		void setSelected(boolean b);
		void updateArea();
		void setColorFilter(ColorMatrixColorFilter cmcf);
		RectF getPerceivedArea();
		void onMovedSuccessfully();
		boolean checkPlaceable();
	}


	public Mover( CD cd , CoordConverter cc , GridUtil gUtil , Moveable m , vector tcLoc , float maxDistFromTc ){
		this.tcLoc = tcLoc;
		this.maxDistFromTc = maxDistFromTc;
		this.cd = cd;
		this.cc = cc;
		this.gUtil = gUtil;
		this.m = m;

		origLoc = new vector(m.getLoc());
		origArea = new RectF(m.getArea());
	}



	public synchronized boolean analyzeTouchEvent( TouchEvent e ){


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



			largerArea.set( m.getArea() );
			largerArea.inset( -Rpg.gridSize , -Rpg.gridSize );

			////Log.d("BuildingMover", "mapRel= " + mapRel );
			if( largerArea.contains(mapRel.x, mapRel.y) ){
				//Log.d("BuildingMover", pointer + ":Started moving the building");
				m.setSelected( false );

				movingB = true;
				pointer = e.pointer;

				if( bIsPlaceable ){
					GridWorker.addToToBeGriddedQueue(new RectF(m.getArea()), true);
					//Log.v("BuildingMover", pointer + ":Removed building area from grid area:" + m.getArea());
				}
				else
					//Log.d("BuildingMover", pointer + ":TouchDown and b is not placeable");

					return false;
			}
		}
		else if( e.type == TouchEvent.TOUCH_DRAGGED && movingB ){
			mapRel.set(e.x,e.y);
			cc.getCoordsScreenToMap(mapRel, mapRel);

			m.getLoc().set(mapRel);
			m.updateArea();

			gUtil.setProperlyOnGrid(m.getArea(), Rpg.gridSize);
			GridUtil.getLocFromArea( m.getArea(), m.getPerceivedArea() , m.getLoc() );

			if( !m.getArea().equals(origArea) ){
				////Log.d("BuildingMover", "bHasMoved=true");
				bHasMoved = true;
			}


			if( m.getLoc().distanceSquared(tcLoc) > maxDistFromTc ){
				showToFarFromTcMessage();

				m.setSelected( true );
				m.getLoc().set( origLoc );
				m.getArea().set( origArea );
				bHasMoved = false;
				movingB = false;
				pointer = -1;

				//m.setColorFilter(cmcf);
				return true;
			}
			if( m.checkPlaceable() ){
				//Log.d("BuildingMover", pointer + ":Moved building is placeable.");

				m.setColorFilter(null);

				bIsPlaceable = true;
			}
			else{
				//Log.d("BuildingMover", pointer + ":Moved building is not placeable.");

				m.setColorFilter(cmcf);

				bIsPlaceable = false;
			}
			return true;
		}
		else if( e.type == TouchEvent.TOUCH_UP && movingB ){
			movingB = false;
			pointer = -1;

			if( m.getLoc().distanceSquared(tcLoc) > maxDistFromTc ){
				//Log.d("BuildingMover", pointer + ":TouchEvent.TOUCH_UP and not placeable" );
				showToFarFromTcMessage();
				bIsPlaceable = false;

				m.setColorFilter(cmcf);
			}
			else if( !m.checkPlaceable() ){
				bIsPlaceable = false;
				//Log.d("BuildingMover", pointer + ":TouchEvent.TOUCH_UP and not placeable" );

				m.setColorFilter(cmcf);
			}
			//			else if( !cd.checkPlaceable2(m.getArea(), false) ){
			//				bIsPlaceable = false;
			//				//Log.d("BuildingMover", pointer + ":TouchEvent.TOUCH_UP and not placeable" );
			//
			//				m.setColorFilter(cmcf);
			//			}
			else{
				bIsPlaceable = true;

				GridWorker.addToToBeGriddedQueue( new RectF(m.getArea()) , false );
				//Log.d("BuildingMover", pointer + ":TouchEvent.TOUCH_UP and placeable." );
				//Log.v("BuildingMover", pointer + ":Adding " + m.getArea() + " to grid." );
				m.setSelected( true );
				origLoc.set( m.getLoc() );
				origArea.set( m.getArea() );

				bHasMoved = false;

				m.onMovedSuccessfully();
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

		m.getLoc().set( origLoc );
		m.updateArea();
		m.setColorFilter(null);

		GridWorker.addToToBeGriddedQueue( m.getArea() , false );
		bHasMoved = false;
	}

	public boolean placeBuilding(){
		return cd.checkPlaceable2(m.getArea(), false);
	}















}
