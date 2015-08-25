package com.kingscastle.nuzi.towerdefence.framework.implementation;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;

import com.kingscastle.nuzi.towerdefence.framework.Input.TouchEvent;
import com.kingscastle.nuzi.towerdefence.framework.Pool;
import com.kingscastle.nuzi.towerdefence.framework.Pool.PoolObjectFactory;

import java.util.ArrayList;
import java.util.List;

public class MultiTouchHandler implements TouchHandler {
	private static final int MAX_TOUCHPOINTS = 10;

	private final boolean[] isTouched = new boolean[MAX_TOUCHPOINTS];
	private final int[] touchX = new int[MAX_TOUCHPOINTS];
	private final int[] touchY = new int[MAX_TOUCHPOINTS];
	private final int[] id = new int[MAX_TOUCHPOINTS];
	private final Pool<TouchEvent> touchEventPool;
	private final List<TouchEvent> touchEvents = new ArrayList<TouchEvent>();
	private final List<TouchEvent> touchEventsBuffer = new ArrayList<TouchEvent>();
	private final float scaleX;
	private final float scaleY;

	public MultiTouchHandler(Activity activity, View view, float scaleX, float scaleY) {
		PoolObjectFactory<TouchEvent> factory = new PoolObjectFactory<TouchEvent>() {
			@Override
			public TouchEvent createObject() {
				return new TouchEvent();
			}
		};
		touchEventPool = new Pool<TouchEvent>(factory, 100);
		view.setOnTouchListener(this);

		this.scaleX = scaleX;
		this.scaleY = scaleY;

		//mActivity = activity;
		//final View controlsView = findViewById(R.id.fullscreen_content_controls);
		//final View contentView = findViewById(R.id.fullscreen_content);
		// Set up an instance of SystemUiHider to control the system UI for
		// this activity.
		//		mSystemUiHider = SystemUiHider.getInstance(activity, view,
		//				Game.HIDER_FLAGS);
		//		mSystemUiHider.setup();
		//		activity.getWindow().setFlags(
		//				WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//
		//		mSystemUiHider
		//		.setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
		//			// Cached values.
		//			//			int mControlsHeight;
		//			//			int mShortAnimTime;
		//
		//			@Override
		//			@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
		//			public void onVisibilityChange(boolean visible) {
		//				//Log.d( "MultiTouchHandler" , "onVisibilityChange("+ visible +")");
		//				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
		//					// If the ViewPropertyAnimator API is available
		//					// (Honeycomb MR2 and later), use it to animate the
		//					// in-layout UI controls at the bottom of the
		//					// screen.
		//					//if (mControlsHeight == 0) {
		//					//		mControlsHeight = controlsView.getHeight();
		//					//}
		//					//					if (mShortAnimTime == 0) {
		//					//						mShortAnimTime = getResources().getInteger(
		//					//								android.R.integer.config_shortAnimTime);
		//					//					}
		//					//					controlsView
		//					//					.animate()
		//					//					.translationY(visible ? 0 : mControlsHeight)
		//					//					.setDuration(mShortAnimTime);
		//				} else {
		//					// If the ViewPropertyAnimator APIs aren't
		//					// available, simply show or hide the in-layout UI
		//					// controls.
		//					//					controlsView.setVisibility(visible ? View.VISIBLE
		//					//							: View.GONE);
		//				}
		//
		//				if (visible && Game.AUTO_HIDE) {
		//					//Log.d( "MultiTouchHandler" , "Scheduling a hide()");
		//					// Schedule a hide().
		//					delayedHide(Game.AUTO_HIDE_DELAY_MILLIS);
		//				}
		//			}
		//		});
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		//		if (Game.AUTO_HIDE) {
		//			if( event.getY() < Rpg.thirtyDp ){
		//
		//				////Log.d( "MultiTouchHandler" , "Touch was < 30dp, UI should display");
		//				//mSystemUiHider.show();
		//				delayedHide(Game.AUTO_HIDE_DELAY_MILLIS);
		//			}
		//		}

		synchronized (this) {
			int action = event.getAction() & MotionEvent.ACTION_MASK;

			int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_ID_MASK) >> MotionEvent.ACTION_POINTER_ID_SHIFT;
			int pointerCount = event.getPointerCount();
			TouchEvent touchEvent;
			for (int i = 0; i < MAX_TOUCHPOINTS; i++) {
				if (i >= pointerCount) {
					isTouched[i] = false;
					id[i] = -1;
					continue;
				}
				int pointerId = event.getPointerId(i);
				if (event.getAction() != MotionEvent.ACTION_MOVE && i != pointerIndex) {
					// if it's an up/down/cancel/out event, mask the id to see if we should process it for this touch
					// point
					continue;
				}
				switch (action) {
				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_POINTER_DOWN:
					touchEvent = touchEventPool.newObject();
					touchEvent.type = TouchEvent.TOUCH_DOWN;
					touchEvent.pointer = pointerId;
					touchEvent.x = touchX[i] = (int) (event.getX(i) * scaleX);
					touchEvent.y = touchY[i] = (int) (event.getY(i) * scaleY);
					isTouched[i] = true;
					id[i] = pointerId;
					touchEventsBuffer.add(touchEvent);
					break;

				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_POINTER_UP:
				case MotionEvent.ACTION_CANCEL:
					touchEvent = touchEventPool.newObject();
					touchEvent.type = TouchEvent.TOUCH_UP;
					touchEvent.pointer = pointerId;
					touchEvent.x = touchX[i] = (int) (event.getX(i) * scaleX);
					touchEvent.y = touchY[i] = (int) (event.getY(i) * scaleY);
					isTouched[i] = false;
					id[i] = -1;
					touchEventsBuffer.add(touchEvent);
					break;

				case MotionEvent.ACTION_MOVE:
					touchEvent = touchEventPool.newObject();
					touchEvent.type = TouchEvent.TOUCH_DRAGGED;
					touchEvent.pointer = pointerId;
					touchEvent.x = touchX[i] = (int) (event.getX(i) * scaleX);
					touchEvent.y = touchY[i] = (int) (event.getY(i) * scaleY);
					isTouched[i] = true;
					id[i] = pointerId;
					touchEventsBuffer.add(touchEvent);
					break;
				}
			}
			return true;
		}
	}

	@Override
	public boolean isTouchDown(int pointer) {
		synchronized (this) {
			int index = getIndex(pointer);
			if (index < 0 || index >= MAX_TOUCHPOINTS) {
				return false;
			} else {
				return isTouched[index];
			}
		}
	}

	@Override
	public int getTouchX(int pointer) {
		synchronized (this) {
			int index = getIndex(pointer);
			if (index < 0 || index >= MAX_TOUCHPOINTS) {
				return 0;
			} else {
				return touchX[index];
			}
		}
	}

	@Override
	public int getTouchY(int pointer) {
		synchronized (this) {
			int index = getIndex(pointer);
			if (index < 0 || index >= MAX_TOUCHPOINTS) {
				return 0;
			} else {
				return touchY[index];
			}
		}
	}

	@Override
	public List<TouchEvent> getTouchEvents() {
		synchronized (this) {
			int len = touchEvents.size();
			for (int i = 0; i < len; i++) {
				touchEventPool.free(touchEvents.get(i));
			}
			touchEvents.clear();
			touchEvents.addAll(touchEventsBuffer);
			touchEventsBuffer.clear();
			return touchEvents;
		}
	}

	// returns the index for a given pointerId or -1 if no index.
	private int getIndex(int pointerId) {
		for (int i = 0; i < MAX_TOUCHPOINTS; i++) {
			if (id[i] == pointerId) {
				return i;
			}
		}
		return -1;
	}





	//	/**
	//	 * The activity associated with this UI hider object.
	//	 */
	//	protected Activity mActivity;
	//
	//
	//
	//	/**
	//	 * The instance of the {@link SystemUiHider} for this activity.
	//	 */
	//	private SystemUiHider mSystemUiHider;
	//	/**
	//	 * Touch listener to use for in-layout UI controls to delay hiding the
	//	 * system UI. This is to prevent the jarring behavior of controls going away
	//	 * while interacting with activity UI.
	//	 */
	//	//	View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
	//	//		@Override
	//	//		public boolean onTouch(View view, MotionEvent motionEvent) {
	//	//			if (Game.AUTO_HIDE) {
	//	//				delayedHide(Game.AUTO_HIDE_DELAY_MILLIS);
	//	//			}
	//	//			return false;
	//	//		}
	//	//	};
	//
	//	Handler mHideHandler = new Handler();
	//	Runnable mHideRunnable = new Runnable() {
	//		@Override
	//		public void run() {
	//			//Log.d( "MultiTouchHandler" , "mHideRunnable:run()");
	//			mSystemUiHider.hide();
	//		}
	//	};



}