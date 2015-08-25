package com.kingscastle.nuzi.towerdefence.gameElements.spells;

import java.util.ArrayList;



public class AvailableSpells
{
	private final ArrayList<Spell> availableSpells = new ArrayList<Spell>();

	public synchronized boolean addSpell(  Spell s )
	{
		for( Spell s2 : availableSpells )
		{
			if( s2.getClass() == s.getClass() )
			{
				return false;
			}
		}

		return availableSpells.add( s );
	}









    public ArrayList<Spell> getAvailableSpells() {
		return availableSpells;
	}



}
