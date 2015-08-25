package com.kingscastle.nuzi.towerdefence.ui;


import com.kingscastle.nuzi.towerdefence.framework.Input.TouchEvent;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.abilities.Ability;
import com.kingscastle.nuzi.towerdefence.gameElements.spells.ProjectileSpell;
import com.kingscastle.nuzi.towerdefence.gameElements.spells.Spell;
import com.kingscastle.nuzi.towerdefence.gameElements.spells.SpellCreationParams;
import com.kingscastle.nuzi.towerdefence.gameElements.spells.SpellInstanceCreator;
import com.kingscastle.nuzi.towerdefence.gameElements.spells.SpellManager;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.ui.buttons.AbilityButton;

class UISpellCreator
{




	/**
	 * 
	 * @param personCastingSpell
	 * @param sm
	 * @param event
	 * @return If null then the spell creating was successful.
	 */
	public static String createSpell( LivingThing personCastingSpell , SpellManager sm , TouchEvent event )
	{
		if( sm.getPendingSpell() == null || personCastingSpell == null || event == null )
		{
			return "false";
		}
		if( event.type != TouchEvent.TOUCH_UP )
		{
			return "false";
		}

		int manaCostPersonCasting = sm.getPendingSpell().getManaCost( personCastingSpell );

		if( personCastingSpell.getLQ().getMana() < manaCostPersonCasting )
		{
			return "Not enough mana.";
		}
		else
		{
			personCastingSpell.useMana( manaCostPersonCasting );

			vector loc = Rpg.getGame().getLevel().getCoordinatesScreenToMap(event.x, event.y);

			SpellCreationParams params = new SpellCreationParams();
			params.setSpellToBeCopied(sm.getPendingSpell());

			if( params.getSpellToBeCopied() instanceof ProjectileSpell)
			{
				params.setLocation(personCastingSpell.loc);
				params.setDestination(loc);
			}
			else
			{
				params.setLocation(loc);
			}

			params.setShooter( personCastingSpell );

			sm.add(SpellInstanceCreator.getSpellInstance(params));

			sm.setPendingSpell(null);

			return null;
		}



	}

	public static String abilityButtonPushed(LivingThing thingSelected , SpellManager sm , AbilityButton ab)
	{
		Ability a = ab.getAbility();


		if( a instanceof Spell)
		{
			Spell s = (Spell) a;

			if (thingSelected.getLQ().getMana() >= s.getManaCost(thingSelected))
			{
				if(s.isInstanceCast())
				{
					s = s.newInstance();
					s.setCaster(thingSelected);
					thingSelected.getLQ().addMana(-s.getManaCost(thingSelected));
					sm.add(s.newInstance());
				}
				else
				{
					sm.setPendingSpell(s);

					return "Click to cast.";

				}
			}
			else
			{
				return "Not enough mana.";
			}
		}
		return "false";

	}

}
