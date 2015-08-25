package com.kingscastle.nuzi.towerdefence.level;

import android.app.ActivityManager;
import android.util.Log;

import com.kingscastle.nuzi.towerdefence.framework.GameTime;

import java.util.ArrayList;
import java.util.List;

public class GameThread implements Runnable {

	private static final String TAG = "GameThread";

	private Thread renderThread = null;

	private Level level;
	//private long nextMessageAt;
	private static short minThreadTime = 30;

	private volatile boolean running = false;


	public GameThread(  ){

	}

	public void resume( Level level )
	{
		Log.v(TAG, "GameThread.resume()");
		if( running ){
			Log.e(TAG, "GameThread Already Running!");
			return;
		}
		this.level = level;

		running = true;
		renderThread = new Thread( this , "GameThread" );
		renderThread.start();
	}


	@Override
	public void run()
	{
		long startTime,timeDif;
		//try
		//{
		while( running )
		{
			try
			{
				startTime = GameTime.getTime();

				level.act();

				synchronized (sucls) {
					if (sucls.size() > 0) {
						List<OnLevelUpdateCompleteListener> deadsucl = new ArrayList<>();
						for (OnLevelUpdateCompleteListener sucl : sucls)
							if (sucl.onScreenUpdateComplete())
								deadsucl.add(sucl);
						sucls.removeAll(deadsucl);
					}
				}

				timeDif = GameTime.getTime() - startTime;

				long minThreadT = (level.isPaused() ? minThreadTime*4 : minThreadTime);
				if( timeDif < minThreadT )
				{
					try {
						Thread.sleep( minThreadT - timeDif );
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					//Thread.sleep(0);
				}
			}

			catch( Exception t )
			{
				t.printStackTrace();
			}
		}

	}
	public void pause()
	{
		Log.v(TAG, "GameThread.pause()");
		running = false;
		while(true)
		{
			try
			{
				if( renderThread != null )
					renderThread.join();

				break;
			} catch (InterruptedException e)
			{
				// retry
			}
		}

		renderThread = null;

	}

	public static short getMaxThreadTime() {
		return minThreadTime;
	}
	public static void setMaxThreadTime(short maxThreadTime2) {
		GameThread.minThreadTime = maxThreadTime2;
	}




	private static final ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
	private static final String MEM_INFO_TAG = "MemoryInfo";
	private static final String memInfo1 = "debug. =================================";

	/*@SuppressLint("UseValueOf")
	public static void logHeap()
	{
		Double allocated = new Double(Debug.getNativeHeapAllocatedSize())/new Double((1048576));
		Double available = new Double(Debug.getNativeHeapSize())/1048576.0;
		Double free = new Double(Debug.getNativeHeapFreeSize())/1048576.0;

		Double vmHeapSize = new Double(Runtime.getRuntime().totalMemory()/1048576);
		Double allocVmMem = new Double((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/1048576);
		Double vmHeapSizeLimit = new Double(Runtime.getRuntime().maxMemory()/1048576);


		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);

		//Log.v( MEM_INFO_TAG , memInfo1 );
		//Log.v( MEM_INFO_TAG , "debug.native heap   : allocated " + df.format(allocated)  + "MB of "            + df.format(available)  + "MB ("              + df.format(free) + "MB free)");
		//Log.v( MEM_INFO_TAG , "debug.VM heap memory: allocated " + df.format(allocVmMem) + "MB VM Heap Size: " + df.format(vmHeapSize) + "MB  MaxHeapSize: " + df.format(vmHeapSizeLimit)+ "MB");
		//ActivityManager am = (ActivityManager) game.getActivity().getSystemService(Context.ACTIVITY_SERVICE);
		//am.getMemoryInfo( memInfo );
		//Log.v( MEM_INFO_TAG ,  " availMemory " + df.format(memInfo.availMem/1048576.0) + "MB , lowMemory? " + memInfo.lowMemory + " , threshold " + df.format(memInfo.threshold/1048576.0) + "MB" );
	}*/




	private final ArrayList<OnLevelUpdateCompleteListener> sucls = new ArrayList<OnLevelUpdateCompleteListener>();


	public interface OnLevelUpdateCompleteListener {
		boolean onScreenUpdateComplete();
	}

	public void addSucl(OnLevelUpdateCompleteListener sucl)		        {	synchronized( sucls ){	sucls.add( sucl );			}   }
	public boolean removeSucl(OnLevelUpdateCompleteListener sucl)		{	synchronized( sucls ){	return sucls.remove( sucl );	}	}




	//	@SuppressLint("UseValueOf")
	//	public static void logHeap()
	//	{
	//		Double allocated = new Double(Debug.getNativeHeapAllocatedSize())/new Double((1048576));
	//		Double available = new Double(Debug.getNativeHeapSize())/1048576.0;
	//		Double free = new Double(Debug.getNativeHeapFreeSize())/1048576.0;
	//		DecimalFormat df = new DecimalFormat();
	//		df.setMaximumFractionDigits(2);
	//		df.setMinimumFractionDigits(2);
	//
	//		//Log.v( MEM_INFO_TAG , memInfo1 );
	//		//Log.v( MEM_INFO_TAG ,  "debug.heap native: allocated " + df.format(allocated) + "MB of " + df.format(available) + "MB (" + df.format(free) + "MB free)");
	//		//Log.v( MEM_INFO_TAG , "debug.memory: allocated: " + df.format(new Double(Runtime.getRuntime().totalMemory()/1048576)) + "MB of " + df.format(new Double(Runtime.getRuntime().maxMemory()/1048576))+ "MB (" + df.format(new Double(Runtime.getRuntime().freeMemory()/1048576)) +"MB free)");
	//		ActivityManager am = (ActivityManager) Rpg.getGame().getSystemService(Context.ACTIVITY_SERVICE);
	//		am.getMemoryInfo( memInfo );
	//		//Log.v( MEM_INFO_TAG ,  " availMemory " + df.format(memInfo.availMem/1048576.0) + "MB , lowMemory? " + memInfo.lowMemory + " , threshold " + df.format(memInfo.threshold/1048576.0) + "MB" );
	//	}



}
