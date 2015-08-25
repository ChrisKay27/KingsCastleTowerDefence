package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks;


import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import org.jetbrains.annotations.NotNull;

public class Arms {
	private static final String TAG = "Arms";

	private final LivingThing owner;
	private long lastAttackedAt;
	private long attackAgainAt1;

	public Arms(@NotNull LivingThing lt)	{
		owner = lt;
	}


	public boolean act( float targetDistSquared )	{
		return act( targetDistSquared , owner.getTarget() );
	}

	/**
	 * Attacks towards another location on the map
	 * @return true if the attack happened
	 */
	public boolean act(@NotNull vector inDirection )	{
		if( attackAgainAt1 < GameTime.getTime())
		{
			attackAgainAt1 = GameTime.getTime() + owner.getAQ().getROF();
			//owner.setLookDirection(owner.getTarget().loc);
			return attack( inDirection );
		}
		return false;
	}


	/**
	 * Attacks in a direction
	 * @param unitVector unit vector which represents a direction. magnitude(unitVector) = 1
	 * @return true if the attack happened
	 *
	 */
	public boolean actFromUnitVector(@NotNull vector unitVector )	{
		if( timeToAttack() )
		{
			lastAttackedAt = GameTime.getTime();
			//owner.setLookDirection(owner.getTarget().loc);
			return attackFromUnitVector( unitVector );
		}
		return false;
	}

	/**
	 * Tries to attack by getting the owners target
	 * @return true if the attack happened
	 */
	public boolean act(){
		LivingThing target = owner.getTarget();
		if( target == null )
			return false;

		return act( owner.loc.distanceSquared( target.loc ) );
	}


	public boolean act( float targetDistSquared ,@NotNull LivingThing target )	{
		return act( targetDistSquared , target , owner.getAQ().getCurrentAttack() );
	}


	/**
	 * Tries to attack the target which is at targetDistSquared distance
	 * @param targetDistSquared The distance to the target. Use this if you have already calculated
	 *                          the target distance... even tho it probably isn't much work to recalculate it
	 * @param target The target to attack
	 * @param attack The attack to use
	 * @return true if the attack happened
	 */
	public boolean act( float targetDistSquared ,@NotNull LivingThing target ,@NotNull Attack attack )	{
		makeAttackAct( attack );

		if( target == null )
		{
			//if( owner instanceof WizardBuffer ) {
			//////Log.d( TAG ,"Arms.act() target == null" );
			//}
			return false;
		}
		if( attack == null )
		{
			//if( owner instanceof WizardBuffer ) {
			//////Log.d( TAG ,"Arms.act() attack == null" );
			//}
			return false;
		}


		if( timeToAttack() )
		{
			if( targetDistSquared == 0 )
			{
				if( target.area.contains( owner.loc.x , owner.loc.x ) )
					targetDistSquared = 1;
				else
					targetDistSquared = owner.loc.distanceSquared( target.area );
			}

			if( targetDistSquared <= owner.getAQ().getAttackRangeSquared() )
			{
				lastAttackedAt = GameTime.getTime();

				return attack( target , attack );
			}
		}

		return false;
	}


	/**
	 * Attacks need a chance to update themselves so we give it a chance like this
	 * @param attack
	 */
	public static void makeAttackAct( @NotNull Attack attack )	{
		if( attack != null )
			attack.act();
	}


	private static boolean attack(@NotNull LivingThing target ,@NotNull Attack attack )	{
		if( attack != null )
			return attack.attack( target );
		return false;
	}



	private boolean attackFromUnitVector(@NotNull vector unitVector )	{
		Attack a = owner.getAQ().getCurrentAttack();
		if( a != null )		{
			a.attackFromUnitVector(unitVector);
			return true;
		}
		return false;
	}



	private boolean attack(@NotNull vector inDirection)
	{
		Attack a = owner.getAQ().getCurrentAttack();
		if( a != null )
		{
			a.attack(inDirection);
			return true;
		}
		return false;
	}


	public boolean timeToAttack()
	{
		return lastAttackedAt + owner.getAQ().getROF() < GameTime.getTime();
	}

	public LivingThing getOwner()	{
		return owner;
	}


}
