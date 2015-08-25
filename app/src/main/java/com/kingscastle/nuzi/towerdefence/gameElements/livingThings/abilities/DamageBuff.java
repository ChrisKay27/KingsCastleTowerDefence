package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.abilities;


import android.support.annotation.NonNull;

import com.kingscastle.nuzi.towerdefence.effects.animations.BerzerkAnim;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.Bonuses;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import org.jetbrains.annotations.NotNull;

public class DamageBuff extends Buff
{
	private final static String name = "DamageBuff";
	private static Image iconImage;

	private final float damageBonus;
	private boolean hasBuffed = false;

	{
		setAliveTime( 10000 );
	}

	public DamageBuff(@NotNull LivingThing caster, @NotNull LivingThing target) {
		super(caster, target);
		damageBonus = 1.3f;
	}

    @Override
    public boolean isOver() {
        return isDead();
    }


    public DamageBuff(@NotNull LivingThing caster, @NotNull LivingThing target, float damageBonus )	{
		super(caster, target);
		this.damageBonus = damageBonus;
	}


	@Override
	public void doAbility()
	{
		if( !hasBuffed )
		{
			Bonuses b = getTarget().getLQ().getBonuses();
			b.setDamageBonus( b.getDamageBonus()*damageBonus );
			hasBuffed = true;
		}
	}

	@Override
	public void undoAbility()
	{
		if( hasBuffed )
		{
			Bonuses b = getTarget().getLQ().getBonuses();
			b.setDamageBonus( b.getDamageBonus()/damageBonus );
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
		if( getAnim() == null )
		{
			setAnim( new BerzerkAnim( getTarget().loc ));
			getAnim().setOffs(new vector(0,getTarget().area.height()/2));
			getAnim().setLooping(true);
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
		return new DamageBuff(getCaster(),target);
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


	public float getDamageBonusPercent() {
		return (damageBonus-1)*100;
	}


	@Override
	public Abilities getAbility()				 {				return Abilities.SPEEDSHOT ; 			}






}
