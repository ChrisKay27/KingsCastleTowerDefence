package com.kingscastle.nuzi.towerdefence.teams;


import com.kingscastle.nuzi.towerdefence.TowerDefenceGame;
import com.kingscastle.nuzi.towerdefence.framework.GameTime;

class TeamThread implements Runnable
{

	private static final String TAG = "TeamThread";
	private Thread teamThread = null;
	private final TowerDefenceGame tdg;
	private final Team team;

	private volatile boolean running = false;
	//private long lastmessage;
	private int ommCount;

	private static int minThreadTime = 30;

	public TeamThread( Team team , TowerDefenceGame game )
	{
		this.team = team;
		this.tdg = game;
	}

	public synchronized void resume()
	{
		//Log.v(TAG, team.getTeamName() + " : resume()");
		if( running )
			return;

		minThreadTime = 30;

		ommCount = 0;
		running = true;
		teamThread = new Thread( this , team.getTeamName() + "TeamThread" );
		teamThread.start();
	}

	@Override
	public void run()
	{
		long startTime , timeDif;

		try {
			while ( running )
			{

				startTime = GameTime.getTime();
				//				if ( GameTime.getTime() - lastmessage > 2000 )
				//				{
				//					//System.out.println("TeamThread running : " + team);
				//					lastmessage=GameTime.getTime();
				//
				//					if ( game == null )
				//					{
				//						game = Rpg.getGame();
				//					}
				//
				//					if ( game.getLevel() != null)
				//					{
				//					//	System.out.println("Is Level Ready? " + game.getLevel().isReady());
				//					}
				//
				//				}
				try
				{
					team.act();
					//					if( game.getLevel() != null && game.getLevel().isReady() && !game.isPaused() && game.getCurrentScreen() instanceof GameScreen)
					//					{
					//						team.act();
					//
					//					}
				}
				catch ( OutOfMemoryError e ){
					ommCount++;
					if( ommCount >= 2 )
						throw e;
					//Log.e( TAG , "OOM!");
					System.gc();
				}
				catch ( Throwable e ){
					e.printStackTrace();
				}


				timeDif = GameTime.getTime() - startTime;

				if( timeDif < minThreadTime )
					Thread.sleep( minThreadTime - timeDif );

				// Thread.sleep(0);
			}

		}
		catch ( Throwable t )
		{
			t.printStackTrace();
		}
	}


	public synchronized void pause()
	{
		//Log.v(TAG, team.getTeamName() + " : pause()");
		if( !running )
			return;

		running = false;
		while( true )
		{
			try
			{
				if ( teamThread != null )
					teamThread.join();
				break;
			}
			catch ( InterruptedException e ){
				e.printStackTrace();
			}
		}
		teamThread = null;
	}




}
