package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks;


import com.kingscastle.nuzi.towerdefence.effects.animations.Anim;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import org.jetbrains.annotations.NotNull;

public abstract class Attack
{
	protected final MM mm;
	protected final LivingThing owner;


	public abstract void act();
	public abstract boolean attack(LivingThing target);

	Attack(@NotNull MM mm,@NotNull LivingThing owner2){
		owner = owner2;
		this.mm = mm;
		if( mm == null )
			throw new NullPointerException(owner + " has created a "+this+" and passed it a null mm!");
	}


	/**
	 * @return the owner
	 */
	LivingThing getOwner()
	{
		return owner;
	}



	public void removeAnim()	{
	}

	public void attack(vector inDirection)
	{

	}

	public Anim getAnimator() {
		return null;
	}

	public void checkHit(vector attackingInDirection) {
	}

	public void attackFromUnitVector(vector unitVector) {
	}




}
