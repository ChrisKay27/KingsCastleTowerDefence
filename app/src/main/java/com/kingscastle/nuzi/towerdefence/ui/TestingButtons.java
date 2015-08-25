package com.kingscastle.nuzi.towerdefence.ui;

public class TestingButtons
{

	//	private static final AndroidButton testBuildingPlacement;
	//	private static final AndroidButton testGridBuilder;
	//	private static final AndroidButton testPathing;

	private static final String TAG = "TestingButtons";


	static
	{
		//		testBuildingPlacement = new AndroidButton()
		//		{
		//			private boolean showingAreas = false;
		//			@Override
		//			public boolean doAction()
		//			{
		//				Rpg.getGame().getLevel().onPause();
		//
		//				if( !showingAreas )
		//				{
		//					showingAreas = true;
		//					boolean failed = false;
		//					//Log.d( TAG , "sixTeenDp=" + Rpg.sixTeenDp );
		//					//					for( Team team : ManagerManager.getInstance().getTeamManager().getTeams() )
		//					//					{
		//					//						for( Building b : team.getBm().getBuildings() )
		//					//						{
		//					//							if( b.area.left % Rpg.sixTeenDp != 0 || b.area.top % Rpg.sixTeenDp != 0 || b.area.right % Rpg.sixTeenDp != 0 || b.area.bottom % Rpg.sixTeenDp != 0 )
		//					//							{
		//					//								failed = true;
		//					//								//Log.d( TAG , b + " failed area = " + b.area );
		//					//								b.setSelectedColor( Color.RED );
		//					//							}
		//					//							else
		//					//							{
		//					//								b.setSelectedColor( Color.GREEN );
		//					//							}
		//					//
		//					//							b.setSelected( true );
		//					//						}
		//					//						for( LivingThing lt : team.getAm().getArmy() )
		//					//						{
		//					//							lt.setSelectedColor( Color.YELLOW );
		//					//							lt.setSelected( true );
		//					//						}
		//					//					}
		//
		//					//					for( GameElement ge : ManagerManager.getInstance().getGem().getGameElements() )
		//					//					{
		//					//
		//					//						if( ge instanceof Mine )
		//					//						{
		//					//							Mine mine = (Mine) ge;
		//					//
		//					//							if( mine.area.left % Rpg.sixTeenDp != 0 || mine.area.top % Rpg.sixTeenDp != 0 || mine.area.right % Rpg.sixTeenDp != 0 || mine.area.bottom % Rpg.sixTeenDp != 0 )
		//					//							{
		//					//								failed = true;
		//					//								//Log.d( TAG , mine + " failed area = " + mine.area );
		//					//								mine.setSelectedColor( Color.RED );
		//					//							}
		//					//							else
		//					//							{
		//					//								mine.setSelectedColor( Color.GREEN );
		//					//							}
		//					//						}
		//					//						else if( ge instanceof Tree )
		//					//						{
		//					//							Tree tree = (Tree) ge;
		//					//
		//					//							if( tree.area.left % Rpg.sixTeenDp != 0 || tree.area.top % Rpg.sixTeenDp != 0 || tree.area.right % Rpg.sixTeenDp != 0 || tree.area.bottom % Rpg.sixTeenDp != 0 )
		//					//							{
		//					//								failed = true;
		//					//								//Log.d( TAG , tree + " failed area = " + tree.area );
		//					//								tree.setSelectedColor( Color.RED );
		//					//							}
		//					//							else
		//					//							{
		//					//								tree.setSelectedColor( Color.GREEN );
		//					//							}
		//					//						}
		//					//					}
		//
		//					Settings.showAllAreaBorders = true;
		//
		//					if( failed )
		//					{
		//						InfoMessage.getInstance().addMessage( "BUILDING PLACEMENT TEST FAILED ",  5000 );
		//					}
		//				}
		//				else
		//				{
		//
		//					Settings.showAllAreaBorders = false;
		//
		//					//					for( Team team : ManagerManager.getInstance().getTeamManager().getTeams() )
		//					//					{
		//					//						//Log.d( TAG , "Start unselecting team:" + team.getTeamName() );
		//					//						for( Building b : team.getBm().getBuildings() )
		//					//						{
		//					//							b.setSelectedColor( Color.YELLOW );
		//					//							b.setSelected( false );
		//					//						}
		//					//						for( LivingThing lt : team.getAm().getArmy() )
		//					//						{
		//					//							lt.setSelectedColor( Color.YELLOW );
		//					//							lt.setSelected( false );
		//					//						}
		//					//						//Log.d( TAG , "Done unselecting team." );
		//					//					}
		//
		//					//Log.d( TAG , "Done unselecting teams, starting Gems." );
		//					//					for( GameElement ge : ManagerManager.getInstance().getGem().getGameElements() )
		//					//					{
		//					//
		//					//						if( ge instanceof Mine )
		//					//						{
		//					//							Mine mine = (Mine) ge;
		//					//							mine.setSelectedColor( Color.YELLOW );
		//					//						}
		//					//						else if( ge instanceof Tree )
		//					//						{
		//					//							Tree tree = (Tree) ge;
		//					//							tree.setSelectedColor( Color.YELLOW );
		//					//						}
		//					//
		//					//					}
		//
		//					//Log.d( TAG , "Done unselecting everything." );
		//					showingAreas = false;
		//				}
		//
		//
		//				//ManagerManager.getInstance().getTeamManager().startThreadsAndResumeQueues();
		//				MM.get().onResume();
		//
		//				return true;
		//			}
		//		};
		//
		//		testBuildingPlacement.setMessage( "Test building placement" );
		//		testBuildingPlacement.setPressOn( TouchEvent.TOUCH_UP );
		//		testBuildingPlacement.setVisible( true );
		//
		//
		//
		//
		//		testGridBuilder = new AndroidButton()
		//		{
		//
		//			@Override
		//			public boolean doAction()
		//			{
		//				//				if( !Settings.drawUnwalkableTilesOnMiniMap )
		//				//				{
		//				//					//Log.d( TAG , "Start collecting unwalkables." );
		//				//					ArrayList<RectF> unwalkables = new ArrayList<RectF>();
		//				//
		//				//					for( Team team : ManagerManager.getInstance().getTeamManager().getTeams() )
		//				//					{
		//				//						for( Building b : team.getBm().getBuildings() )
		//				//						{
		//				//							unwalkables.add( b.area );
		//				//						}
		//				//					}
		//				//					for( GameElement ge : ManagerManager.getInstance().getGem().getGameElements() )
		//				//					{
		//				//						unwalkables.add( ge.area );
		//				//					}
		//				//					//Log.d( TAG , "Done collecting unwalkables, starting building grid." );
		//				//
		//				//					GridBuilder.buildGrid( unwalkables , Rpg.getGame().getLevel().getGrid() );
		//				//
		//				//					//Log.d( TAG , "Done building grid." );
		//				//				}
		//
		//				Settings.drawUnwalkableTilesOnMiniMap = !Settings.drawUnwalkableTilesOnMiniMap;
		//				return Settings.drawUnwalkableTilesOnMiniMap;
		//			}
		//
		//		};
		//		testGridBuilder.setMessage( "TestGridBuilder" );
		//		testGridBuilder.setPressOn( TouchEvent.TOUCH_UP );
		//		testGridBuilder.setVisible( true );
		//
		//
		//
		//		testPathing = new AndroidButton()
		//		{
		//
		//			@Override
		//			public boolean doAction()
		//			{
		//
		//				//				Thread t = new Thread(new Runnable(){
		//				//
		//				//					@Override
		//				//					public void run()
		//				//					{
		//				//						try {
		//				//							Thread.sleep( 2000 );
		//				//						} catch (InterruptedException e) {
		//				//							e.printStackTrace();
		//				//						}
		//				//						ManagerManager mm = ManagerManager.getInstance();
		//				//
		//				//						TownCenter tc = getTownCenter( Rpg.getGame().getPlayer().getTeam().getBm().getBuildings() );
		//				//						TownCenter evilTc = getTownCenter( ManagerManager.getInstance().getTeamManager().getTeam( Teams.RED ).getBm().getBuildings() );
		//				//
		//				//						Vector tcStart = new Vector( tc.loc );
		//				//
		//				//						Vector evilTcStart = new Vector( evilTc.loc );
		//				//						//evilTcStart.add( 0 , Rpg.thirtyDp*2 );
		//				//
		//				//						Path path = null;
		//				//						try {
		//				//							path = AStarPathFinder.findMeAPath( Rpg.getGame().getLevel().getGrid() , tcStart , evilTcStart , GameTime.getTime() + 500 );
		//				//						} catch (TimeOutException e) {
		//				//							e.printStackTrace();
		//				//						}
		//				//
		//				//						if( path == null )
		//				//						{
		//				//							return;
		//				//						}
		//				//
		//				//						for( Vector v : path.getPath() )
		//				//						{
		//				//							mm.getEm().add( new LoopingSmoke(v) );
		//				//						}
		//				//					}
		//				//				});
		//				//
		//				//				t.act();
		//
		//				//
		//				//				path = AStarPathFinder.findMeAPath( Rpg.getGame().getLevel().getGrid() , tcStart , evilGreenTcStart );
		//				//
		//				//				for( Vector v : path.getPath() )
		//				//				{
		//				//					mm.getEm().add( new LoopingSmoke(v) );
		//				//				}
		//				//
		//				//
		//				//				path = AStarPathFinder.findMeAPath( Rpg.getGame().getLevel().getGrid() , tcStart , evilWhiteTcStart );
		//				//
		//				//				for( Vector v : path.getPath() )
		//				//				{
		//				//					mm.getEm().add( new LoopingSmoke(v) );
		//				//				}
		//				//
		//				//
		//				//				path = AStarPathFinder.findMeAPath( Rpg.getGame().getLevel().getGrid() , tcStart , evilOrangeTcStart );
		//				//
		//				//				for( Vector v : path.getPath() )
		//				//				{
		//				//					mm.getEm().add( new LoopingSmoke(v) );
		//				//				}
		//
		//
		//				return true;
		//			}
		//
		//		};
		//		testPathing.setMessage( "Test Pathing" );
		//		testPathing.setPressOn( TouchEvent.TOUCH_UP );
		//		testPathing.setVisible( true );

	}




	//
	//	public static AndroidButton getTestbuildingplacement() {
	//		return testBuildingPlacement;
	//	}
	//
	//
	//	public static AndroidButton getTestgridbuilder() {
	//		return testGridBuilder;
	//	}
	//
	//
	//
	//	public static AndroidButton getTestpathing() {
	//		return testPathing;
	//	}
	//
	//
	//
	//



}
