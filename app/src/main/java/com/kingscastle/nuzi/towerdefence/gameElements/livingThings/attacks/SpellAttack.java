package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks;


import com.kingscastle.nuzi.towerdefence.effects.SpecialEffects;
import com.kingscastle.nuzi.towerdefence.effects.SpecialEffects.SpellType;
import com.kingscastle.nuzi.towerdefence.gameElements.GameElement;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.Humanoid;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameElements.spells.FireBall;
import com.kingscastle.nuzi.towerdefence.gameElements.spells.Icicle;
import com.kingscastle.nuzi.towerdefence.gameElements.spells.InstantSpell;
import com.kingscastle.nuzi.towerdefence.gameElements.spells.LightningBolts;
import com.kingscastle.nuzi.towerdefence.gameElements.spells.Spell;
import com.kingscastle.nuzi.towerdefence.gameElements.spells.SpellCreationParams;
import com.kingscastle.nuzi.towerdefence.gameElements.spells.SpellInstanceCreator;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import org.jetbrains.annotations.NotNull;

public class SpellAttack extends Attack {
	private static final String TAG = "SpellAttack";

	private SpellType st;
	private Spell s;
	private final vector tempUnit = new vector();

//	public SpellAttack(MM mm, LivingThing lt , Spell p ){
//		super(mm, lt);
//	}

    /**
     * @param p This spell instance is recreated whenever the spell is cast using the newInstance method
     */
	public SpellAttack( @NotNull MM mm, @NotNull LivingThing caster ,@NotNull Spell p )
	{
		super(mm, caster);
		setSpell(p);
	}

	@Override
	public void attackFromUnitVector(vector unitVector) {
		SpellCreationParams params = getSpellCreationParams(this);
		tempUnit.set(unitVector);
		params.setUnitVectorInDirection(tempUnit);
		Spell spell = SpellInstanceCreator.getSpellInstance(params);

		mm.add((GameElement) spell);

		if( owner instanceof Humanoid )
			((Humanoid)owner).setLookDirectionFromUnit( params.getUnitVectorInDirection() );
	}

	@Override
	public void attack( vector inDirection )
	{
		SpellCreationParams params = getSpellCreationParams(this);
		tempUnit.set( inDirection );
		params.setUnitVectorInDirection(tempUnit.turnIntoUnitVector());
		Spell spell = SpellInstanceCreator.getSpellInstance(params);

		mm.add((GameElement) spell);

		if( owner instanceof Humanoid )
			((Humanoid)owner).setLookDirectionFromUnit( params.getUnitVectorInDirection() );
	}


	@Override
	public boolean attack( @NotNull LivingThing target )
	{
		////Log.d(TAG , "attack(LivingThing target)");

		SpellCreationParams params = getSpellCreationParams(this);
		params.setTarget(target);

		if( s instanceof InstantSpell && target != null )
			params.setLocation( target.loc );

		if( params.getUnitVectorInDirection() == null )
		{
			tempUnit.set( target.loc );
			vector tVelocity = target.getVelocity();
			if( tVelocity != null ){
				float multiplier = owner.loc.distanceSquared(target.loc)/10000;
				tempUnit.add( tVelocity.x*multiplier , tVelocity.y*multiplier );
			}
			tempUnit.minus(owner.loc);
			tempUnit.turnIntoUnitVector();
			params.setUnitVectorInDirection( tempUnit );
		}

		Spell spell = SpellInstanceCreator.getSpellInstance(params);


		if (mm.add((GameElement) spell ))
			SpecialEffects.playSpellCastSound(st, spell.loc.x, spell.loc.y);


		//boolean added =
		////Log.d(TAG , "attack(" + target + ") s= " + s + " owner= " + owner );
		////Log.d(TAG , "Spell added?: " + added );

		if( owner instanceof Humanoid )
			((Humanoid)owner).setLookDirectionFromUnit(params.getUnitVectorInDirection());

		return true;
	}


	private static SpellCreationParams getSpellCreationParams( SpellAttack sa )
	{
		SpellCreationParams params = new SpellCreationParams();
		params.setShooter( sa.getOwner() );
		params.setSpellToBeCopied( sa.s );
		params.setTeam( sa.getOwner().getTeamName() );

		return params;
	}


	public Spell getSpell()
	{
		return s;
	}

	public void setSpell(Spell s)
	{
		this.s = s;
		if( s instanceof Icicle)
			st = SpellType.ICE;
		else if( s instanceof FireBall)
			st = SpellType.FIRE;
		else if( s instanceof LightningBolts)
			st = SpellType.LIGHTNING;
		else
			st = SpellType.NONE;
	}


	@Override
	public void act() {
	}


}
