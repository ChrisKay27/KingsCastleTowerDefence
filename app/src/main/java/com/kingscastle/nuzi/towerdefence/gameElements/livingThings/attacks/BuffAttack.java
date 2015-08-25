package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks;


import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.abilities.Buff;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;

public class BuffAttack extends Attack
{

	//private static final String TAG = "BuffAttack";

	private Buff buffSpell;

	public BuffAttack( MM mm , LivingThing lt , Buff is )
	{
		super( mm , lt );
		this.setSpell( is );
	}


	@Override
	public boolean attack( LivingThing target )
	{
		mm.add( buffSpell.newInstance(target) );
		//boolean added =  ;

		//////Log.d( TAG , "success? : " + );
		return true;
	}


	public Buff getSpell()
	{
		return buffSpell;
	}


	void setSpell(Buff is)
	{
		this.buffSpell = is;
	}


	@Override
	public void act() {
		// TODO Auto-generated method stub
	}



}
