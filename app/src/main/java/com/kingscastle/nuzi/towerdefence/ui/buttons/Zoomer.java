package com.kingscastle.nuzi.towerdefence.ui.buttons;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ZoomControls;

import com.kingscastle.nuzi.towerdefence.framework.implementation.AndroidFastRenderView;

import java.util.ArrayList;


/**
 * Created by Chris on 4/11/2014.
 */
public class Zoomer {

	private static final String TAG = "Zoomer";
	static ZoomControls zb;
	static Activity a;
	static int inCount = 0;
	static AndroidFastRenderView renderView;

	static float xScale = 1;
	static float yScale = 1;
	private static float minScale = 1;

	/**
	 * Must call before trying to use the zoomer!
	 * @param a The activity your view in on
	 * @param zb Controls displayed on that activities content view
	 * @param renderView The View used to display the game content
	 */
	public static void setup(Activity a, ZoomControls zb, final AndroidFastRenderView renderView){
		Zoomer.zb = zb;
		Zoomer.renderView = renderView;
		Zoomer.a = a;
		xScale = renderView.getScaleX();
		yScale = renderView.getScaleY();
		Log.d(TAG, "xScale:" + xScale + " yScale:" + yScale );
		inCount = 0;
		zb.setIsZoomInEnabled(true);
		zb.setIsZoomOutEnabled(true);
		zb.setOnZoomInClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				//if(inCount >= 2) return;
				if( xScale == 2 ) return;
				else if( xScale > 2 ){
					xScale = 2f;
					yScale = 2f;
				}
				else
				{
					xScale += 0.5f;
					yScale += 0.5f;
				}
				renderView.setScaleX(xScale);
				renderView.setScaleY(yScale);
				inCount++;
				//Log.d(TAG, "xScale:" + xScale + " yScale:" + yScale );
				callListeners();
			}
		});
		zb.setOnZoomOutClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				//if (inCount == 0) return;
				if( xScale == minScale ) return;

				xScale -= 0.5f;
				yScale -= 0.5f;

				if( xScale < minScale )
					xScale = minScale;

				if( yScale < minScale  )
					yScale = minScale;

				renderView.setScaleX(xScale);
				renderView.setScaleY(yScale);
				--inCount;
				//Log.d(TAG, "xScale:" + xScale + " yScale:" + yScale );
				callListeners();
			}
		});
	}


	public static void saveZoomAndZoomOut(){
		a.runOnUiThread(saveZoomAndZoomOut);
	}


	public static void restoreZoom() {
		a.runOnUiThread(restoreZoom);
	}


	private static Runnable saveZoomAndZoomOut = new Runnable(){
		@Override
		public void run() {
			//final AndroidFastRenderView renderView = Zoomer.renderView;
			//if( renderView != null ){
				renderView.setScaleX( minScale );
				renderView.setScaleY( minScale );

				callListeners();
			//}
		};
	};

	private static Runnable restoreZoom = new Runnable(){
		@Override
		public void run() {
			//final AndroidFastRenderView renderView = Zoomer.renderView;
			//if( renderView != null ){
				renderView.setScaleX( xScale );
				renderView.setScaleY( yScale );

				callListeners();
			//}

			//			for( int i = 0 ; i < inCount ; ++i ) {
			//				renderView.setScaleX(renderView.getScaleX() + 0.5f);
			//				renderView.setScaleY(renderView.getScaleY() + 0.5f);
			//			}
		};
	};

	public static void setScale(float scale) {
		xScale = scale;
		yScale = scale;
		restoreZoom();
	}

	public static void setMinScale(float minScale) {
		Zoomer.minScale = minScale;
		if( xScale < minScale || yScale < minScale )
			setScale(minScale);
	}



	public static float getxScale() {
		return xScale;
	}
	public static float getyScale() {
		return yScale;
	}



	protected static void callListeners() {
		synchronized( zlcls ){
			for( onZoomLevelChangedListener zlcl : zlcls )
				zlcl.onZoomLevelChanged(xScale, yScale);
		}
	}



	public static interface onZoomLevelChangedListener{
		void onZoomLevelChanged(float xScale, float yScale);
	}



	private static ArrayList<onZoomLevelChangedListener> zlcls = new ArrayList<onZoomLevelChangedListener>();

	public static boolean addZlcl(onZoomLevelChangedListener object) {
		synchronized( zlcls ){
			return zlcls.add(object);
		}
	}
	public static void clearZlcls() {
		synchronized( zlcls ){
			zlcls.clear();
		}
	}
	public static boolean remove(Object object) {
		synchronized( zlcls ){
			return zlcls.remove(object);
		}
	}











}
