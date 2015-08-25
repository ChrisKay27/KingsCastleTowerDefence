package com.kingscastle.nuzi.towerdefence.ui;

import android.util.Log;

import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.GameElement;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Building;

import java.util.ArrayList;






public class Selecter
{
	private static final String TAG = "Selecter";

	private final UI ui;


	Selecter( UI ui_ ){
		ui = ui_;
	}



	public boolean setSelected( GameElement ge )
	{
		Log.v(TAG, ge + " selected");

		if( ge == null )
			throw new IllegalArgumentException( "Selecter : ge == null" );

		if( Rpg.getGame().getLevel() == null )
			throw new IllegalArgumentException( "Selecter :  Rpg.getGame().getLevel() == null ,  Game not yet started." );

		clearSelection();
		if( ge instanceof Building)
			ui.setSelectedBuilding((Building)ge);
		else
			ui.setSelectedThing( ge );

		synchronized( sls ){
			for( OnSelectedListener sl : sls )
				sl.onSelected(ge);
		}


		return false;
	}






	public boolean setSelected( ArrayList<? extends GameElement> ges )
	{
		if( ges == null )
			throw new IllegalArgumentException( "Selecter : ge == null" );

		if( ges.isEmpty() )
			throw new IllegalArgumentException( "Selecter : ges.isEmpty() " );

		if( Rpg.getGame().getLevel() == null )
			throw new IllegalArgumentException( "Selecter :  Rpg.getGame().getLevel() == null ,  Game not yet started." );



		ArrayList<LivingThing> lts = new ArrayList<LivingThing>();

		for( GameElement ge : ges )
			if( ge instanceof LivingThing )
				lts.add( (LivingThing) ge );

		////Log.d( TAG , "lts.size() = " + lts.size() );
		if( lts.size() == 1 )
			return setSelected( lts.get(0) );


		else if( !lts.isEmpty() )
		{
			clearSelection();

			ui.setSelected( lts );


			//UI ui = UI.getInstance();


			//ui.setTopRightButton( UnselectButton.getInstance() );
			//ui.setBottomRightButton( TroopSelectorButton.getInstance() );

			return true;
		}


		return false;
	}


	public boolean clearSelection()
	{
		//Log.d( TAG , "clearSelection()" );

		ui.clearSelected();

//		UIView uiView = ui.uiView;
//		if( uiView != null )
//			uiView.showTroopSelectorButton();


		ui.selUI.clearScrollerButtons();
		ui.selUI.clearSelections();
		ui.bo.clearButtons();
		//ui.uo.clearButtons();
		ui.bb.cancel();

			////Log.d( TAG , "end of clearSelection()" );
		return true;
	}

	public void unselect( GameElement ge )
	{
		ge.setSelected( false );
		ui.setUnSelected( ge );
	}


	private final ArrayList<OnSelectedListener> sls = new ArrayList<OnSelectedListener>();

	public void addSl(OnSelectedListener usl) {
		synchronized( sls ){
			sls.add( usl );
		}
	}
	public boolean Slcontains(Object object) {
		synchronized( sls ){
			return sls.contains(object);
		}
	}
	public boolean Slremove(Object object) {
		synchronized( sls ){
			return sls.remove(object);
		}
	}


	public static interface OnSelectedListener{
		void onSelected(GameElement ge);
	}




}
