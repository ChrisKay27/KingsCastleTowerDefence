package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.abilities;


import android.support.annotation.NonNull;

import com.kingscastle.nuzi.towerdefence.effects.animations.Anim;
import com.kingscastle.nuzi.towerdefence.effects.animations.HasteAnim;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.Bonuses;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import org.jetbrains.annotations.NotNull;

public class Haste extends Buff{		//System.out.println("");
	private static final int aliveTime = 3000;
	private final float speedBonus = 0.5f* Rpg.getDp();

	private Image iconImage;// = R.drawable.haste_icon;
	
	{
		setAliveTime(aliveTime);
	}

	public Haste(@NotNull LivingThing caster, @NotNull LivingThing target) {
		super(caster, target);
	}

    @Override
    public boolean isOver() {
        return isDead();
    }

    @Override
	public Abilities getAbility()				 {				return Abilities.HASTE ; 			}
	
	
	@Override
	public void doAbility()	{
		Bonuses b = getTarget().getLQ().getBonuses();
		b.addToSpeedBonusMultiplier(speedBonus);
	}
	
	@Override
	public void undoAbility(){
		Bonuses b = getTarget().getLQ().getBonuses();
		b.subtractFromSpeedBonusMultiplier(speedBonus);
	}

	@Override
	public int calculateManaCost(LivingThing aWizard)
	{
		return 80;
	}	

	
	@Override
	public void loadAnimation( @NonNull MM mm )	{
		if( getTarget() != null)
		{
			Anim anim;
			anim=new HasteAnim(getTarget().loc);
			anim.setAliveTime(aliveTime);
			anim.setOffs(new vector(0,getTarget().area.height()/2));
			anim.setLooping(true);
			setAnim(anim);
		}
	}

	
	@Override
	public Image getIconImage() {
		return iconImage;
	}

	


	@Override
	public Ability newInstance(@NotNull LivingThing target) {
		return new Haste(getCaster(),target);
	}

}
