package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks;

import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.effects.SpecialEffects;
import com.kingscastle.nuzi.towerdefence.effects.animations.Anim;
import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.CD;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.Humanoid;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks.MeleeAnimator.MeleeTypes;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;


public class MeleeAttack extends Attack
{

	private final AttackAnimator weapon;

	private static final RectF weaponStrikePercArea = new RectF( -8* Rpg.getDp() , -8*Rpg.getDp() , 8*Rpg.getDp() , 8*Rpg.getDp() );

	private final vector checkHitHere;

	private final RectF inThisArea;

	private LivingThing possibleVictum;

	private MeleeTypes weaponType;

	private final CD cd;


	public MeleeAttack( MM mm , Humanoid owner , MeleeTypes weaponType , CD cd )
	{
		super( mm, owner );
		this.weaponType = weaponType;
		switch( weaponType )
		{

		default:
			weapon = new MeleeAnimator( owner , weaponType , this );
			break;
		case PickAxe:
			weapon = new PickAxeAnimator( owner , this );
			break;
		case Axe:
			weapon = new AxeAnimator( owner , this );
			break;

		}
		this.cd = cd;
		checkHitHere = new vector();
		inThisArea = new RectF();
	}


	public MeleeAttack( MM mm, Humanoid owner , CD cd )
	{
		super(mm, owner);

		if( Math.random() < 0.25 )
			weapon = new MeleeAnimator(owner,MeleeTypes.LongSword,this);
		else if( Math.random() < 0.5 )
			weapon = new MeleeAnimator(owner,MeleeTypes.Sabre,this);
		else if( Math.random() < 0.75 )
			weapon = new AxeAnimator    ( owner , this );
		else
			weapon = new PickAxeAnimator( owner , this );

		this.cd = cd;
		inThisArea = new RectF();
		checkHitHere = new vector();
		//ManagerManager.getInstance().getEm().add(weapon,true);
	}



	@Override
	public void attack( vector inDirection )
	{
		weapon.attack( inDirection );
		doAttackAt = GameTime.getTime() + weapon.getTimeFromAttackStartTillDoAttack();
	}



	@Override
	public void attackFromUnitVector( vector unitVector )
	{
		weapon.attackFromUnitVector(unitVector);
	}



	@Override
	public boolean attack( LivingThing target )
	{
		possibleVictum = target;
		weapon.attack();
		doAttackAt = GameTime.getTime() + weapon.getTimeFromAttackStartTillDoAttack();
		return true;
	}



	@Override
	public void checkHit( vector inDirection )
	{
		//System.out.println("MeleeAttack: checkHit() starting");
		if( possibleVictum != null && owner.loc.distanceSquared(possibleVictum.loc) < Rpg.getMeleeAttackRangeSquared() ){
			possibleVictum.takeDamage(owner.getDamage(),owner);
			return;
		}

		if( inDirection != null )
		{
			checkHitHere.set( inDirection );
			checkHitHere.times( Rpg.getMeleeAttackRange()).add( owner.loc );

			//Log.d( "MeleeAttack" , "checkHit(): checkHitHere = " + checkHitHere);

			inThisArea.set( weaponStrikePercArea );

			inThisArea.offset( checkHitHere.x , checkHitHere.y );

			//Log.d( "MeleeAttack" , "inThisArea=" + inThisArea);
			//System.out.println("owner.getTeam()=" + owner.getTeam());

			possibleVictum = cd.checkSingleHit( owner.getTeamName() , inThisArea );


			if( possibleVictum != null )
			{
				possibleVictum.takeDamage( owner.getDamage() , owner );

				if( weapon.wasDrawnThisFrame() )
					playHitSound( weaponType , owner.loc.x , owner.loc.y );
			}
			else
			{
				if( weapon.wasDrawnThisFrame() )
					playMissSound( weaponType , owner.loc.x , owner.loc.y );
			}
		}

		//System.out.println("MeleeAttack: checkHit() ending");
	}




	public void checkHit( LivingThing target )
	{
		if( target == null )
			return;


		if( owner.loc.distanceSquared( target.loc ) <  owner.getAQ().getAttackRangeSquared() )
			target.takeDamage( owner.getDamage() , owner );
	}




	@Override
	public void removeAnim()
	{
		weapon.setOver( true );
	}



	@Override
	public Anim getAnimator()
	{
		return weapon;
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
		checkHit( weapon.getAttackingInDirectionUnitVector() );
	}



	private static void playHitSound( MeleeTypes weaponType , float x , float y )
	{
		if( weaponType == null )
			return;

		switch( weaponType )
		{
		case Mace:
		case Sabre:
		case LongSword:
			SpecialEffects.playHitSound(x, y);
			return;

		case Hammer:
			SpecialEffects.playHammerSound( x , y );
			return;

		case Axe:
			SpecialEffects.playAxeSound( x , y );
			return;

		case Hoe:
			return;

		case PickAxe:
			SpecialEffects.playPickaxeSound( x , y );
			return;

		default:
			return;

		}


	}




	private static void playMissSound( MeleeTypes weaponType , float x , float y )
	{
		if( weaponType == null )
			return;

		switch( weaponType )
		{
		case Mace:
		case Sabre:
		case LongSword:
			SpecialEffects.playMissSound( x , y );
			return;

		case Hammer:
			SpecialEffects.playHammerSound( x , y );
			return;

		case Axe:
			SpecialEffects.playAxeSound( x , y );
			return;

		case Hoe:
			return;

		case PickAxe:
			SpecialEffects.playPickaxeSound( x , y );
			return;

		default:
			return;
		}

	}








}
