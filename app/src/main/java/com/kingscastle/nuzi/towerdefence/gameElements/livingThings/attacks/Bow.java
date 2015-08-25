package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks;


import com.kingscastle.nuzi.towerdefence.effects.SpecialEffects;
import com.kingscastle.nuzi.towerdefence.effects.animations.Anim;
import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.Humanoid;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks.BowAnimator.BowTypes;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameElements.projectiles.Arrow;
import com.kingscastle.nuzi.towerdefence.gameElements.projectiles.Projectile;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

public class Bow extends Attack{

	private final BowAnimator bowAnim;
	private Projectile arrow = new Arrow();
	private Projectile nextArrow;


	public Bow( MM mm ,Humanoid owner2 , Projectile arrow )
	{
		super(mm , owner2);
		this.arrow=arrow;
		nextArrow = arrow;
		bowAnim = new BowAnimator( owner2 );
	}


	public Bow( MM mm , Humanoid owner2 , Projectile arrow , BowTypes bowType )
	{
		super(mm , owner2);
		this.arrow=arrow;
		nextArrow = arrow;
		bowAnim = new BowAnimator( owner2 , bowType );
	}

	@Override
	public boolean attack( LivingThing target )
	{
		bowAnim.attack( arrow );
		nextArrow = arrow;
		doAttackAt = GameTime.getTime() + bowAnim.getTimeFromAttackStartTillDoAttack();
		return true;
	}

	@Override
	public void attack( vector inDirection )
	{
		bowAnim.attack( inDirection , arrow );
		nextArrow = arrow;
		doAttackAt = GameTime.getTime() + bowAnim.getTimeFromAttackStartTillDoAttack();
	}

	@Override
	public void attackFromUnitVector( vector unitVector )
	{
		bowAnim.attackFromUnitVector( unitVector , arrow );
		nextArrow = arrow;
		doAttackAt = GameTime.getTime() + bowAnim.getTimeFromAttackStartTillDoAttack();
	}

	public void setArrow( Projectile p )
	{
		arrow = p;
	}

	@Override
	public void removeAnim()
	{
		bowAnim.setOver(true);
	}

	@Override
	public Anim getAnimator()
	{
		return bowAnim;
	}




	private long doAttackAt;

	@Override
	public void act()
	{
		if( doAttackAt < GameTime.getTime() )
		{
			doAttack();
			doAttackAt = Long.MAX_VALUE;
		}
	}



	private void doAttack()
	{
		if (mm.add(getNewArrow() ))
			playSound();
	}


	private Projectile getNewArrow()
	{
		vector attackingInDirectionUnitVector = bowAnim.getAttackingInDirectionUnitVector();
		if( attackingInDirectionUnitVector != null )
		{
			Projectile p = nextArrow.newInstance( owner , attackingInDirectionUnitVector );
			attackingInDirectionUnitVector = null ;
			return p;
		}
		else
		{
			return nextArrow.newInstance( owner , null , owner.getTarget() );
		}
	}



	public void playSound() {
		SpecialEffects.playBowSound(owner.loc.x, owner.loc.y);
	}

	//
	//	//	@Override
	//	@Override
	//	public void loadSounds() {
	//		//		bowSound=Rpg.getGame().getAudio().createSound("attackSounds/attack_ranged.mp3");
	//		//
	//	}






}
