package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.abilities;


import com.kingscastle.nuzi.towerdefence.effects.animations.Anim;
import com.kingscastle.nuzi.towerdefence.effects.animations.BlackSummonSmokeAnim;
import com.kingscastle.nuzi.towerdefence.effects.animations.HealSparklesAnim;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingQualities;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.Healer;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.teams.Teams;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.IOException;

public class HealingSpell extends InstantAbility{

	//private static final String TAG = "HealingSpell";
	private Healer healer;
	private static Image iconImage;
	private boolean showEvilAnimation = false;

	private int healAmount;

	public HealingSpell(LivingThing caster, LivingThing target) {
		super(caster,target);
	}


	@Override
	public void doAbility()
	{
        LivingThing target2 = getTarget();
		if( target2 == null )
			return;

        MM mm = getMM();

		if( showEvilAnimation )
		{
			Anim a = new BlackSummonSmokeAnim( target2.loc );
			mm.getEm().add ( a , true );
		}
		else
		{
			mm.getEm().add(	new HealSparklesAnim( target2.loc ) , true );
		}


		//////Log.v(TAG, "Healing amount for this heal is " + healAmount );

		target2.takeHealing( calculateHealAmount() );
	}



	int calculateHealAmount()
	{
		if( healAmount != 0 ){
			float hpBase = healAmount/1.5f;
			return (int) (hpBase + (Math.random()*hpBase));
		}
		else
		{
			LivingQualities lq = getCaster().getLQ();
			float lvlMultiplier = lq.getHealAmount() + lq.getdHealLvl()*lq.getLevel();

			//////Log.v(TAG, "lvlMultiplier for this heal is " + lvlMultiplier );
			//////Log.v(TAG, "Caster is " + getCaster() + ", its level is " + getCaster().getLQ().getLevel());

			float lvlmultdivthing = (float) (Math.random()*((lvlMultiplier*7.0)/10.0));

			//////Log.v(TAG, "lvlmultdivthing = " + lvlmultdivthing );

			return (int) ( (lvlmultdivthing) + (lvlmultdivthing) );
		}
	}



	@Override
	public int calculateManaCost( LivingThing aWizard )
	{
		return 5 + aWizard.getLQ().getLevel()*2;
	}


	@Override
	public Image getIconImage()
	{
		if( iconImage == null )
		{
			//iconImage = Assets.loadImage(R.drawable.fireball_icon);
		}
		return iconImage;
	}

	public LivingThing getHealer() {
		return healer;
	}

	public void setHealer(Healer healer) {
		this.healer = healer;
	}


	private static final String name = "Healing Spell";
	@Override
	public String toString()
	{
		return name;
	}
	public String getName()	{
		return name;
	}


	@Override
	public Ability newInstance(@NotNull LivingThing target)	{
		return new HealingSpell(getCaster(),target);
	}


	@Override
	public void saveYourSelf(BufferedWriter b) throws IOException {
	}





	public void setShowEvilAnimation(boolean b)
	{
		showEvilAnimation = b;
	}


	@Override
	public boolean isStackable() {
		return true;
	}





	@Override
	public void setTeam(Teams teamName) {
	}



	public int getHealAmount() {
		return healAmount;
	}
	public void setHealAmount(int healAmount) {
		this.healAmount = healAmount;
	}



	@Override
	public Abilities getAbility()				 {				return Abilities.HEALINGSPELL ; 			}


}
