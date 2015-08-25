package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.abilities;


import android.support.annotation.NonNull;

import com.kingscastle.nuzi.towerdefence.effects.animations.RisingHeartsAnim;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.Bonuses;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;

import org.jetbrains.annotations.NotNull;

public class EverythingBuff extends Buff
{
	private final static String name = "EverythingBuff";
	private static Image iconImage;

	private final float everythingBonus = 1.15f;
	private boolean hasBuffed = false;

	{
		setAliveTime( 16000 );
	}

	public EverythingBuff(@NotNull LivingThing caster, @NotNull LivingThing target) {
		super(caster, target);
	}

    @Override
    public boolean isOver() {
        return isDead();
    }

    @Override
	public Abilities getAbility()				 {				return Abilities.EVERYTHING_BUFF ; 			}


	@Override
	public void doAbility()
	{
		if( !hasBuffed )
		{
			Bonuses b = getTarget().getLQ().getBonuses();
			b.setDamageBonus ( b.getDamageBonus() * everythingBonus );
			b.addToSpeedBonusMultiplier(everythingBonus);
			b.setArmorBonus  ( b.getArmorBonus()  * everythingBonus );
			b.setHpRegenBonus( b.getHpRegenBonus() + 1 );
			b.setROABonus    ( (int) (b.getROABonus() * everythingBonus) );

			hasBuffed = true;
		}
	}

	@Override
	public void undoAbility()
	{
		if( hasBuffed )
		{
			Bonuses b = getTarget().getLQ().getBonuses();
			b.setDamageBonus( b.getDamageBonus()/everythingBonus );
			b.subtractFromSpeedBonusMultiplier(everythingBonus);
			b.setArmorBonus  ( b.getArmorBonus()/everythingBonus );
			b.setHpRegenBonus( b.getHpRegenBonus() -1 );
			b.setROABonus    ( (int) (b.getROABonus()/everythingBonus) );
			hasBuffed = false;
		}
	}



	@Override
	public int calculateManaCost(LivingThing aWizard)
	{
		return 0;
	}



	@Override
	public void loadAnimation( @NonNull MM mm )
	{
		if( getAnim() == null )		{
			setAnim( new RisingHeartsAnim( getTarget().loc ));
			getAnim().setLooping( false );
		}
	}



	@Override
	public String toString() {
		return name;
	}
	public String getName() {
		return name;
	}


	@Override
	public Ability newInstance(@NotNull LivingThing target)	{
		return new EverythingBuff(getCaster(),target);
	}



	@Override
	public Image getIconImage()
	{
		if( iconImage == null )
		{
			//iconImage = Assets.loadImage(R.drawable.multishot_icon);
		}
		return null;
	}


	public int getBonusPercent()
	{
		return (int) ((everythingBonus-1)*100);
	}









}
