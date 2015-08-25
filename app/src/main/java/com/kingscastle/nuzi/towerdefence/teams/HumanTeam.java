package com.kingscastle.nuzi.towerdefence.teams;


import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Building;
import com.kingscastle.nuzi.towerdefence.level.GridUtil;
import com.kingscastle.nuzi.towerdefence.teams.races.Race;
import com.kingscastle.nuzi.towerdefence.ui.UI;

import java.io.BufferedWriter;
import java.io.IOException;


public class HumanTeam extends Team
{

	private static final String TAG = "HumanTeam";

	public HumanTeam( Teams team , HumanPlayer player, Race race, GridUtil gUtil )
	{
		super( team, player, race, gUtil );
		maxWorkersAllowed = Integer.MAX_VALUE;
	}


	public void setUpListeners( final UI ui ){


		addBcl(new OnBuildingCompleteListener() {
			@Override
			public void onBuildingComplete(Building b) {
				if (b == null) return;
				if (b.isSelected()) {
					mm.getUI().setSelectedBuilding(b);
					ui.selUI.setSelected(b);
				}
			}
		});

		addBdl(new OnBuildingDestroyedListener() {
			@Override
			public void onBuildingDestroyed(Building b) {
				if (b == null) return;

				if (b.isSelected())
					mm.getUI().setUnSelected(b);


			}
		});

		addUdl(new OnUnitDestroyedListener() {
			@Override
			public void onUnitDestroyed(LivingThing lt) {
				if (lt == null) return;
				if (lt.isSelected())
					mm.getUI().setUnSelected(lt);


			}
		});
	}


	@Override
	public void saveYourself( BufferedWriter b ) throws IOException
	{
		String s;

		Player p = getPlayer();

		String race = "";
		if( p != null )
			race = p.getRace().getRace().toString();


		wps = 0; fps = 0; gps = 0;buildingWorkers=0;


		s = "<Team name=\"" + getTeamName() + "\" t=\"H\" aicontrolled=\"false\" race=\"" + race + "\" tclvl=\""+getTcLevel()
				+"\" ww=\""+wps+"\" fw=\""+fps+"\" gw=\""+gps+"\" bw=\""+buildingWorkers+"\" >";
		b.write(s, 0, s.length());
		b.newLine();
		////Log.d( TAG , s);

		getPlayer().saveYourSelf(b);


		getAm().saveYourSelf( b );
		getBm().saveYourSelf( b );

		//t.getPm().saveYourSelf( bw );
		//t.getSm().saveYourSelf( bw );

		s = "</Team>";

		b.write( s , 0 , s.length() );
		b.newLine();
	}







	@Override
	public HumanPlayer getPlayer() {
		return (HumanPlayer) super.getPlayer();
	}








}
