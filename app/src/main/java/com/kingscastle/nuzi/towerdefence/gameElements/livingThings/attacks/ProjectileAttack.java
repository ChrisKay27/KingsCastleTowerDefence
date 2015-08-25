package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks;


import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameElements.projectiles.Projectile;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import org.jetbrains.annotations.NotNull;

public class ProjectileAttack extends Attack {

	protected Projectile proj;


	public ProjectileAttack(@NotNull MM mm,@NotNull LivingThing lt ,@NotNull Projectile p )
	{
		super(mm,lt);
		proj = p;
	}


	public void attackFromUnitVector(@NotNull vector unitVector) {
		mm.add(proj.newInstance(owner, unitVector));
	}



	@Override
	public boolean attack(@NotNull LivingThing target )
	{
		if( target != null)
		{
			vector tVelocity = target.getVelocity();
			if( tVelocity != null )
			{
				vector temp = new vector(target.loc);
				float multiplier = temp.distanceSquared(owner.loc)/10000;
				temp.add( tVelocity.x*multiplier , tVelocity.y*multiplier );
				mm.add(proj.newInstance(owner, temp, target));
			}
			else
				mm.add(proj.newInstance(owner, new vector(target.loc), target));
		}
		return true;
	}



	public Projectile getProj()
	{
		return proj;
	}

	public void setProj(Projectile proj)
	{
		this.proj = proj;
	}



	@Override
	public void act() {
	}




}
