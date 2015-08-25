package com.kingscastle.nuzi.towerdefence.ui.buttons;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.implementation.ImageDrawable;
import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.gameElements.Cost;
import com.kingscastle.nuzi.towerdefence.gameElements.LevelUpTechnology;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Building;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.teams.Player;
import com.kingscastle.nuzi.towerdefence.teams.Team;
import com.kingscastle.nuzi.towerdefence.ui.DialogBuilder;

import java.util.ArrayList;


public class LevelUpButton extends SButton
{
	private static final String TAG = "LevelUpButton";

	private static Image buttonIcon = Assets.loadImage(R.drawable.level_up);
	private static final ImageDrawable id = new ImageDrawable( buttonIcon.getBitmap() , 0 , 0 , new Paint());
	private final LivingThing lt;
	private final Building fromHere;
	private final ArrayList<? extends Building> selBs;
	private final Team team ;
	private final MM mm ;

	public LevelUpButton(Activity a , MM mm, final LivingThing lt_ , final Building fromHere_ , ArrayList<? extends Building> selBs_, final Team team_ )
	{
		super(a);
		this.mm = mm;
		lt = lt_;
		fromHere = fromHere_;
		team = team_;
		selBs = selBs_;
		setForeground(id);
		setOnClickListener( new OnLvlUpClickListener(mm, lt_, fromHere_ , selBs_ , team_ ));
	}



	public static LevelUpButton getInstance( Activity a , MM mm,  LivingThing lt , Building fromHere , ArrayList<? extends Building> selBs, Team team_ )
	{
		LevelUpButton singleton = new LevelUpButton( a , mm ,  lt , fromHere , selBs , team_ );
		return singleton;
	}






	/*	private static void displayDialog(final String string) {
	//		final KingsCastle kc = Rpg.getGame();
	//		kc.runOnUiThread( new Runnable(){
	//			@Override
	//			public void run()
	//			{
	//				try
	//				{
	//					AlertDialog.Builder builder = new AlertDialog.Builder( kc , AlertDialog.THEME_HOLO_DARK );
	//
	//					builder.setCancelable(true).setTitle( string ).setNegativeButton( "Close" , null );
	//					AlertDialog alert = builder.create();
	//					alert.show();
	//				}
	//				catch( Exception e ){
	//					e.printStackTrace();
	//				}
	//			}
	//		});
		}*/


	@Override
	public LevelUpButton clone(){
		LevelUpButton ab = new LevelUpButton( a , mm ,  lt , fromHere , selBs, team );
		return ab;
	}



	public class OnLvlUpClickListener implements OnClickListener{
		private final LivingThing lt;
		private final Building fromHere;
		private final ArrayList<? extends  Building> bldings;
		private final Team team;
		private final MM mm;

		public OnLvlUpClickListener( MM mm , LivingThing lt_ , Building fromHere_ ,  ArrayList<? extends Building> selBs_, Team team_ ){
			lt=lt_; fromHere=fromHere_; team=team_; bldings = selBs_; this.mm = mm;

		}


		private long lastClicked;
		@Override
		public void onClick(View v) {
			if( lastClicked + 500 > System.currentTimeMillis() )
				return;
			lastClicked = System.currentTimeMillis();

			try
			{
				final Player p = team.getPlayer();
				final Cost lvlUpCost = new Cost();
				final ArrayList<Building> thingsThatCanLevelUp = new ArrayList<>();

				//If there is only one thing to level up
				if( lt != null ){
					//String canLvlUp = lt.canLevelUp();//team.canThisLevelUp(lt);
					boolean cannotLvlUp = !lt.canLevelUp();

					if( cannotLvlUp ){
						String msg = "This tower is at its max level\nHigher levels will be available in the full version";
						Log.d(TAG,lt + " cannot level up for some reason");
						new DialogBuilder(a)
						.setText(msg)
						.setPositiveButton(DialogBuilder.OK, null)
						.setCancelable(true)
						.show();

						//Toast.makeText( kc , msg , Toast.LENGTH_SHORT ).show();
						return;
					}
					lvlUpCost.set(lt.getLvlUpCost());
				}
				/*	@FIXME Not needed for TowerDefense
				else //There are many things to level up
				{

					for( Building b : bldings ){
						boolean canLvlUp = team.canThisLevelUp(b);
						if( canLvlUp ){
							thingsThatCanLevelUp.add( b );
							lvlUpCost.add( b.getLvlUpCost() );
						}
					}

					if( thingsThatCanLevelUp.isEmpty() )
						return;

				}*/



//				if( !p.getPR().canAffordIgnorePop(lvlUpCost) )
//				{
//					final int mdCost = MDRC.getMds(lvlUpCost);
//
//					if( p.getPR().getMD() >= mdCost )
//					{
//						String message = "Sorry you cannot afford the "+lvlUpCost.toResString()+
//								".\nWould you like to spend " + mdCost + " magic dusts to purchase these resources?";
//
//						DialogBuilder db = new DialogBuilder( kc );
//						db.setPositiveButton(DialogBuilder.SURE, new OnClickListener() {
//							@Override
//							public void onClick(View v)
//							{
//								if( p.getPR().spend( RT.MAGIC_DUST , mdCost ))
//								{
//									if( lt != null ){
//										startResearchingLevelUpTech( team , lt , fromHere );
//									}
//									else{
//										for( Building b : thingsThatCanLevelUp )
//											startResearchingLevelUpTech( team , b , b );
//									}
//								}
//
//							}
//						}).setNegativeButton( DialogBuilder.NO_THANKS, null ).setText( message ).show();
//
//					}
//					else{
//						String message = "Sorry you cannot afford the\n"+lvlUpCost.toResString()+".";
//						DialogBuilder db = new DialogBuilder( kc );
//						db.setPositiveButton(DialogBuilder.OK, null ).setText( message ).show();
//					}
//				}
//				else

				if( p.getPR().canAffordIgnorePop(lvlUpCost) ){
					String message;
					if( lt != null )
						message = "Do you want to level up " + (lt instanceof Building ? "this " + lt.getName() : "all " + lt.getName() + "s")
						+ "\nto level "+ (lt.lq.getLevel()+1) +" for " + lvlUpCost.toResString() + "?";
					else
						message = "Do you want to level up these for " + lvlUpCost.toResString() + "?";

					DialogBuilder db = new DialogBuilder( a );
					db.setPositiveButton(DialogBuilder.SURE, new OnClickListener() {
						@Override
						public void onClick(View v) {
							if( !p.spendCosts(lvlUpCost) ){
								DialogBuilder.alert("Sorry, you cannot afford this.");
								return;
							}
							if( lt != null ){
								startResearchingLevelUpTech( team , lt , fromHere );
							}
							else{
								for( Building b : thingsThatCanLevelUp )
									startResearchingLevelUpTech( team , b , b );
							}
						}
					}).setNegativeButton( DialogBuilder.NO_THANKS, null ).setText( message ).show();
				}
				else{
					Context context = a;
					CharSequence text = "You cannot afford this upgrade";
					int duration = Toast.LENGTH_SHORT;

					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
				}
			}
			catch( Exception e ){
				e.printStackTrace();
			}

			////Log.d( TAG , "doAction() finished" );
		}

		private void startResearchingLevelUpTech( Team team , LivingThing lt , Building fromHere ){
			if( lt instanceof Building )
			{
				LevelUpTechnology lut;

				lut = new LevelUpTechnology( mm,team.getPlayer() , fromHere , lt );
				lut.queueableComplete();
				//((Building) lt).addToBuildQueue(lut);
				team.onTechResearchStarted(((Building) lt), lut );
				mm.getUI().clearSelected();
			}
//			else{
//				LevelUpTechnology lut = new LevelUpTechnology( mm,team.getPlayer() , fromHere , lt );
//				fromHere.addToBuildQueue( lut );
//				team.onTechResearchStarted( fromHere , lut );
//			}
		}


	}//End onLvlUpClickListener



	@Override
	public boolean equals(Object o) {
		if(o == null)
			return false;
		if(o == this)
			return true;
		if( !(o instanceof LevelUpButton) )
			return false;
		LevelUpButton lub = (LevelUpButton) o;
		if( lt == lub.lt && team == lub.team && fromHere == lub.fromHere ){
			if( selBs == null && lub.selBs == null )
				return true;
			else if( selBs != null )
				return selBs.equals(lub.selBs);
		}

		return false;
	}


}


















