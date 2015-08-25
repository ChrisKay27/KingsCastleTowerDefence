package com.kingscastle.nuzi.towerdefence.ui;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.gameElements.Cost;
import com.kingscastle.nuzi.towerdefence.teams.RT;
import com.kingscastle.nuzi.towerdefence.teams.Team;


public class CannotAfford {


	public static void showCannotAffordMessage( final Activity a , final OnClickListener posListener , final OnClickListener negListener , final Team team , final Cost cost , final int magicDustCost ){

		DialogBuilder db = new DialogBuilder(a).setText(a.getString(R.string.cannot_afford) + "\n" + a.getString(R.string.would_you_like_to_buy_plural, cost.toResString() , magicDustCost+ " Magic Dusts" ));


		db.setPositiveButton(DialogBuilder.SURE, new OnClickListener() {
			@Override
			public void onClick(View v) {
				if( team.getPR().canAfford( RT.MAGIC_DUST , magicDustCost )){
					if( team.getPR().spend( RT.MAGIC_DUST , magicDustCost ) ){
						team.getPR().refund(cost);
						if( posListener != null )
							posListener.onClick(v);
					}
				}
				else
					showCannotAffordMdMessage( a , magicDustCost );
			}
		});

		db.setNegativeButton(DialogBuilder.NO_THANKS, new OnClickListener() {
			@Override
			public void onClick(View v) {
				if( negListener != null )
					negListener.onClick(v);
			}
		});
		db.show();
	}




	public static void showCannotAffordMdMessage( final Activity a , final int magicDustCost ){

		DialogBuilder db = new DialogBuilder(a).setText(a.getString(R.string.you_do_not_have) + a.getString(R.string.would_you_like_to_get_some ));


		db.setPositiveButton(DialogBuilder.SURE, new OnClickListener() {
			@Override
			public void onClick(View v) {
				//PurchaseScreen.showPurchaseScreen();
			}
		})
		.setNegativeButton(DialogBuilder.NO_THANKS, null).show();
	}












}//end Class
