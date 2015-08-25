package com.kingscastle.nuzi.towerdefence.ui;

import android.util.Log;

import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.framework.implementation.AndroidFastRenderView;


public class UIThread implements Runnable {

	private static final String TAG = "UIThread";

	private Thread thread = null;
	private volatile boolean running = false;
	private UI ui;
	private AndroidFastRenderView renderView;

	public UIThread(){
	}

	public void resume( UI ui , AndroidFastRenderView renderView )
	{
		Log.v( TAG , "UIThread.resume()");
		if( ui == null || renderView == null )
			throw new IllegalArgumentException(" ui=" + ui + " renderView=" + renderView );

		if( running ){
			Log.e( TAG , " Already running");
			return;
		}

		this.ui = ui;
		this.renderView = renderView;

		running = true;
		thread = new Thread( this  , "UI Thread" );
		thread.start();
	}


	@Override
	public void run()
	{
		long startTime , timeDif;
		try
		{
			while( running )
			{
				startTime = GameTime.getTime();

				try{
					ui.update();
				}
				catch( OutOfMemoryError e ){
					e.printStackTrace();
					System.gc();
				}
				catch ( Throwable e ){
					e.printStackTrace();
				}

				timeDif = GameTime.getTime() - startTime;


				if( timeDif < 50 )
					Thread.sleep( 50 - timeDif );

				//Thread.sleep(0);
			}
		}
		catch( Throwable t )
		{
			t.printStackTrace();
		}
	}


	public void pause()
	{
		Log.v( TAG , "UIThread.pause(), was running:" + running );
		running = false;
		while( true )
		{
			try
			{
				if( thread != null ) {
					thread.join();
				}
				break;
			}
			catch ( InterruptedException e )
			{
				e.printStackTrace();
				// retry
			}
		}

		thread = null;

	}


}
