package com.kingscastle.nuzi.towerdefence.ui;


import android.support.annotation.Nullable;

import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Graphics;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Input.TouchEvent;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.gameElements.GameElement;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Barracks;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameUtils.CoordConverter;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

public class TroopDeployLocPlacer implements TouchEventAnalyzer
{
	enum State
	{
		NOT_SETTING_DELPOY_FLAG , PLACED_WAITING_FOR_ACCEPT
	}

	private static State state = State.NOT_SETTING_DELPOY_FLAG;
	private static final Image flag = Assets.loadImage(R.drawable.deploy_flag);

	private static Barracks selBuilding;
	private static final vector v = new vector();
	private static final vector downAt = new vector();
	private static float tenDp = Rpg.tenDp;

	private final CoordConverter cc;
	private final MM mm;

	public TroopDeployLocPlacer( CoordConverter cc , MM mm ){
		this.cc=cc;
		this.mm = mm;
	}


	public boolean analyzeTouchEvent( TouchEvent event )
	{
//
//		if( state == State.NOT_SETTING_DELPOY_FLAG )
//			return false;


		Barracks selBarracks_local = selBuilding;
		if( selBarracks_local == null )
		{
			state = State.NOT_SETTING_DELPOY_FLAG;
			return false;
		}

//		if( event.x > Rpg.getWidth() - Rpg.seventyFiveDp )
//			return false;


		if( event.type == TouchEvent.TOUCH_DOWN )
		{
			cc.getCoordsScreenToMap( event.x , event.y , downAt );
			if( downAt.distanceSquared(selBarracks_local.loc) >  selBarracks_local.aq.getFocusRangeSquared() )
				return false;

			GameElement ge = mm.getCD().checkPlaceableOrTarget(downAt) ;

			if( ge == null )
			{
				selBuilding.setDeployLoc( downAt.x , downAt.y );
				return true;
			}
			return false;
		}

		if( event.type != TouchEvent.TOUCH_UP )
			return false;



		switch( state )
		{

		case PLACED_WAITING_FOR_ACCEPT:

			cc.getCoordsScreenToMap( event.x , event.y , v );

			if( v.distanceSquared( downAt ) > tenDp )
				return false;


			GameElement ge = mm.getCD().checkPlaceableOrTarget(v) ;

			if( ge == null )
			{
				selBuilding.setDeployLoc( v.x , v.y );
				return true;
			}
			return false;

		default: break;
		}

		return false;
	}



	public void paint( Graphics g )
	{
		Barracks selBuilding_local = selBuilding;
		if( selBuilding_local != null ){
			v.set(selBuilding_local.getTroopDeployLoc());
			cc.getCoordsMapToScreen(v,v);
			v.minus(flag.getWidthDiv2(),flag.getHeightDiv2());
			g.drawImage(flag,0,0,v);
		}
	}


//	public void setStateToPlacingFlag( Building selBuilding )
//	{
//		TroopDeployLocPlacer.selBuilding = selBuilding;
//
//		if( selBuilding == null )
//			state = State.NOT_SETTING_DELPOY_FLAG;
//		else
//			state = State.PLACED_WAITING_FOR_ACCEPT;
//	}

	public void setSelectedBuilding(@Nullable Barracks selectedBuilding) {
		this.selBuilding = selectedBuilding;
		if( selectedBuilding != null ){
			v.set(selectedBuilding.getTroopDeployLoc());
			cc.getCoordsMapToScreen(v,v);
		}
	}




}
