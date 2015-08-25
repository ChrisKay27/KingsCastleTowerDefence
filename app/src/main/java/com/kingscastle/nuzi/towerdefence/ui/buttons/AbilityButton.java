package com.kingscastle.nuzi.towerdefence.ui.buttons;

import android.app.Activity;
import android.graphics.Paint;
import android.view.View;

import com.kingscastle.nuzi.towerdefence.framework.implementation.ImageDrawable;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.abilities.Ability;
import com.kingscastle.nuzi.towerdefence.ui.AbilityCaster;


public class AbilityButton extends SButton
{
	private Ability ab;


	private AbilityButton( Activity a, Ability ability )
	{
		super(a);

		if( ability.getIconImage() != null ){
			ImageDrawable id = new ImageDrawable( ability.getIconImage().getBitmap() , 0 , 0 , new Paint());
			setBackgroundDrawable(id);
		}

		setOnClickListener( new OnClickListener(){
			@Override
			public void onClick(View v) {
				AbilityCaster.getInstance().setPendingAbility( ab );
			}
		});
	}


	public Ability getAbility() {
		return ab;
	}



	public static AbilityButton getInstance( Activity a , Ability ability )
	{
		if( ability == null )
			throw new IllegalArgumentException("Trying to set ability of an abilityButton and ability was null.");

		AbilityButton ab = new AbilityButton( a , ability );
		return ab;
	}








	@Override
	public AbilityButton clone(){
		return new AbilityButton( a , ab );
	}









}
