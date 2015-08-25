package com.kingscastle.nuzi.towerdefence.ui.buttons;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.view.View;

import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.framework.implementation.ImageDrawable;
import com.kingscastle.nuzi.towerdefence.gameElements.Cost;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Building;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.level.TowerDefenceLevel;
import com.kingscastle.nuzi.towerdefence.teams.Team;


public class DestroyBuildingButton extends SButton
{
	private static Image buttonIcon = Assets.loadImage(R.drawable.sell);

	private final MM mm;
	private Building buildingSelected;
	private final Team team;


	private DestroyBuildingButton(Activity a , final MM mm, Team team_ ){
		super(a);
		this.mm = mm;
		team = team_;
		ImageDrawable id = new ImageDrawable( buttonIcon.getBitmap() , 0 , 0 , new Paint());
		setForeground(id);
		setOnClickListener( new OnClickListener(){
			@Override
			public void onClick(View v) {


			/*	Not necessary for tower defence.
			if( buildingSelected instanceof TownCenter)
				{
					int tcCount = 0;

					ListPkg<Building> bPkg = DestroyBuildingButton.this.mm.getBuildingsOnTeam(buildingSelected.getTeamName());

					synchronized( bPkg )
					{
						Building[] buildings = bPkg.list;
						int size2 = bPkg.size;

						for( int i = 0 ; i < size2 ; ++i )
						{
							Building b = buildings[i];

							if( b != buildingSelected && b instanceof TownCenter )
								++tcCount;
						}
						if( tcCount == 0 )
						{
							showCannotDestroyMessage();
							return;
						}
					}
				}*/
				if( ((TowerDefenceLevel)mm.getLevel()).canSellBuildingsRightNow() )
					showDestroyDialog();
				else
					showCannotDestroyMessage();
			}
		});
	}

	public static DestroyBuildingButton getInstance( Activity a , Building b , MM mm, Team team )
	{
		DestroyBuildingButton singleton = new DestroyBuildingButton( a , mm, team );

		singleton.buildingSelected = b;
		return singleton;
	}


	private static final String NO = "No";
	private static final String YES = "Yes";
	private static final CharSequence ONLY_TC_MESSAGE = "You cannot destroy your town center.";
	private static final CharSequence CANNOT_SELL_MID_ROUND = "You cannot sell buildings during the round. You must wait till the round is over.";
	private static final CharSequence OK = "Ok";



	private void showDestroyDialog()
	{
		a.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (buildingSelected == null)
					return;

				Cost cost = new Cost(buildingSelected.getCosts());
				cost.reduceByPerc(0.75);

				AlertDialog.Builder bld = new AlertDialog.Builder(getContext(), AlertDialog.THEME_HOLO_DARK);
				bld.setMessage(getContext().getString(R.string.sell, buildingSelected.getName(), cost.toResString()));

				bld.setNegativeButton(NO, null);
				bld.setPositiveButton(YES, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (which == DialogInterface.BUTTON_POSITIVE)
							if (team != null)
								team.sellThisBuilding(buildingSelected);
					}
				});

				//Log.d( TAG , "Showing YouHaveNoCheatPoints dialog called from : " + returnToScreen );
				bld.create().show();
			}
		});

	}

	private void showCannotDestroyMessage()
	{
		Rpg.getGame().getActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {
				AlertDialog.Builder bld = new AlertDialog.Builder(Rpg.getGame().getActivity(), AlertDialog.THEME_HOLO_DARK);
				bld.setMessage(CANNOT_SELL_MID_ROUND);

				bld.setPositiveButton(OK, null);

				//Log.d( TAG , "Showing YouHaveNoCheatPoints dialog called from : " + returnToScreen );
				bld.create().show();
			}
		});

	}

	@Override
	public DestroyBuildingButton clone(){
		DestroyBuildingButton dbb = new DestroyBuildingButton(a ,mm, team);
		dbb.buildingSelected = buildingSelected;

		return null;
	}



	@Override
	public boolean equals(Object o) {
		if(o == null)
			return false;
		if(o == this)
			return true;
		if( !(o instanceof DestroyBuildingButton) )
			return false;
		DestroyBuildingButton srb = (DestroyBuildingButton) o;
		if( buildingSelected == srb.buildingSelected && team == srb.team )
			return super.equals(o);
		else
			return false;
	}



}
