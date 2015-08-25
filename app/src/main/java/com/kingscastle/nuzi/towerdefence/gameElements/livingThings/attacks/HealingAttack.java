package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks;


import com.kingscastle.nuzi.towerdefence.effects.animations.BlackSummonSmokeAnim;
import com.kingscastle.nuzi.towerdefence.effects.animations.HealSparklesAnim;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.abilities.Ability;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;

public class HealingAttack extends Attack
{

	private final Ability healingAbility;
	private boolean showEvilAnimation = false;

	public HealingAttack( MM mm, LivingThing caster , Ability healingAbility2 )
	{
		super( mm, caster );
		healingAbility = healingAbility2;
	}


	@Override
	public boolean attack( LivingThing target )
	{
		if( target == null || healingAbility == null )
		{
			//System.out.println("HealingAttack told to act when target:" + target + " and owner:" + owner + " and healingSpell:" + healingAbility);
			return false;
		}

		if( showEvilAnimation )
		{
			mm.getEm().add(
					new BlackSummonSmokeAnim(owner.loc),true);

		}
		else
		{
			mm.getEm().add(
					new HealSparklesAnim(owner.loc),true);
		}

		healingAbility.cast( mm , target );
		return true;
	}



	/**
	 * @return the evilAnimation
	 */
	public boolean isUsingEvilAnimation() {
		return showEvilAnimation;
	}


	/**
	 * @param evilAnimation the evilAnimation to set
	 */
	public void setShowEvilAnimation(boolean evilAnimation) {
		this.showEvilAnimation = evilAnimation;
	}


	@Override
	public void act() {
	}
}
