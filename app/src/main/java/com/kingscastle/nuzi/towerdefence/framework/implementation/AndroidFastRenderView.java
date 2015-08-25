package com.kingscastle.nuzi.towerdefence.framework.implementation;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.kingscastle.nuzi.towerdefence.TowerDefenceGame;
import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.framework.Graphics;
import com.kingscastle.nuzi.towerdefence.framework.Screen;
import com.kingscastle.nuzi.towerdefence.level.Level.GameState;


public class AndroidFastRenderView extends SurfaceView implements Runnable,SurfaceHolder.Callback
{

	private static final String TAG = "AndroidFastRenderView";


	private TowerDefenceGame game;
	private Graphics g;
	private Thread renderThread = null;
	private SurfaceHolder holder;
	private volatile boolean running = false;

	private Screen loadingScreen;
	private long nextMessage;

	private long removeLoadingScreenAt = Long.MAX_VALUE;

	private long framePeriod = 30;
//	private float scaleY;
//	private float scaleX;

	public AndroidFastRenderView(Context context) {
		super(context);
		init(null, 0);
	}

	public AndroidFastRenderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0);
	}

	public AndroidFastRenderView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs, defStyle);
	}

	private void init(AttributeSet attrs, int defStyle) {
		holder = getHolder();

		holder.addCallback( this );
		setFocusable(true);
		setFocusableInTouchMode(true);
	}


	public synchronized void onResume( TowerDefenceGame game)
	{
		if( game == null )
			throw new IllegalArgumentException("Game cannot be null");

		if(	running )
			return;

//		getLayoutParams().width = game.getScreenWidth()*2;
//		getLayoutParams().height = game.getScreenHeight()*2;

		this.game = game;
		this.g = game.getGraphics();
		running = true ;
		renderThread = new Thread( this , "Render Thread" );
		renderThread.start();

	}


	@Override
	public void draw( @NonNull Canvas canvas )
	{
		try{
			super.draw(canvas);
			//surface.draw(canvas);
		}
		catch( NullPointerException e ){
			e.printStackTrace();
		}
	}

	@Override
	protected void onDraw( Canvas c )
	{
		//try
		//{
		if( c == null )
			throw new IllegalArgumentException();
		TowerDefenceGame game_local = game;
		if( game_local == null )
			return;

		g.setCanvas( c );


		if( loadingScreen != null )
		{
			if( removeLoadingScreenAt < GameTime.getTime() )
				loadingScreen = null;
			else
				loadingScreen.paint( g );
			return;
		}
		else
		{
			try
			{
				Screen s = game_local.getCurrentScreen();
				if( s != null )
					s.paint( g );
			}
			catch( Exception e ){
				e.printStackTrace();
			}
		}

		GameState gameState = game_local.getState();

		if( gameState == GameState.InGamePlay  )
		{
			try{
				game_local.getUI().paint();
			}
			catch( Exception e )
			{
				e.printStackTrace();
			}
		}

		//surface.draw(c);

		if ( nextMessage < GameTime.getTime() )
		{
			nextMessage = GameTime.getTime() + 4000;

			////Log.d(TAG , "canvas.isHardwareAccelerated() = " + c.isHardwareAccelerated());
		}
	}

	@Override
	public void run()
	{
		long startTime , timeDif;
		try
		{
			while ( running )
			{
				startTime = GameTime.getTime();

				postInvalidate();

				timeDif = GameTime.getTime() - startTime;


				long framePeriod_local = framePeriod;

				if( timeDif < framePeriod_local )
					Thread.sleep( framePeriod_local - timeDif );
			}
		}
		catch( Throwable t )
		{
			t.printStackTrace();
		}
	}





	public void onPause()
	{
		//super.onPause();

		running = false;

		while ( true )
		{
			try
			{
				if( renderThread != null )
					renderThread.join();
				break;
			}
			catch ( InterruptedException e )
			{
				// retry
			}
		}

		renderThread = null;
	}



	public void setLoadingScreen( Screen ls )
	{
		if( ls == null )
			removeLoadingScreenAt = GameTime.getTime() + 500;
		else
			loadingScreen = ls;
	}


	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}


	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{
		//Rpg.print("///////////////////Surface Was Created!!///////////////");
		setWillNotDraw(false);
	}


	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	}


//	public float getScaleX() {
//		return scaleX;
//	}
//
//	public float getScaleY() {
//		return scaleY;
//	}
//
//	public void setScaleX(float scaleX) {
//		this.scaleX = scaleX;
//	}
//
//	public void setScaleY(float scaleY) {
//		this.scaleY = scaleY;
//	}


}