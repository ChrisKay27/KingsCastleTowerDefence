package com.kingscastle.nuzi.towerdefence.ui.buttons;

import android.app.Activity;
import android.graphics.Paint;

import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.implementation.ImageDrawable;
import com.kingscastle.nuzi.towerdefence.ui.BuildingBuilder;


public class BuildTrapsButton extends SButton
{
	private static final String TAG = "BuildBuildingsButton";

	private static Image buttonIcon = Assets.loadImage(R.drawable.build_buildings_button);

	private final BuildingBuilder bb;

	private BuildTrapsButton( Activity a , final BuildingBuilder bb_ )
	{
		super( a );
		bb = bb_;

		ImageDrawable id = new ImageDrawable( buttonIcon.getBitmap() , 0 , 0 , new Paint());
		setForeground(id);


//		setOnClickListener( new OnClickListener(){
//			@Override
//			public void onClick(View v) {
//				bb.showScroller( Rpg.getGame().getPlayer().getAbs() );
//			}
//		});

	}




	public static BuildTrapsButton getInstance( Activity a , final BuildingBuilder bb )
	{

		return new BuildTrapsButton( a , bb );
	}


	@Override
	public BuildTrapsButton clone(){
		BuildTrapsButton bbb = new BuildTrapsButton( a , bb );
		return bbb;
	}



















}
