package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks;


import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.Bonuses;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;

public class AttackerQualities
{
	private final Bonuses bonuses;

	private float focusRangeSquared;
	private float focusRange;
	private float attackRange;
	private float attackRangeSquared;
	private float staysAtDistanceSquared;
	private int damage;
	private int regenRate;
	private int ROF;
	private int healAmount;

	private Attack currentAttack;

	private Attack friendlyAttack;
	private final ArrayList<Attack> friendlyAttacks = new ArrayList<>();
	private int friendlyAttackCursor;
	private boolean cycleThroughFriendlyAttacks;

	private final ArrayList<Attack> attacks = new ArrayList<>();
	private int attacksCursor;
	private boolean cycleThroughAttacks;

	private int dDamageAge = 0;
	private int dDamageLvl = 0;
	private int dROFAge = 0;
	private int dROFLvl = 0;
	private float dRangeAge = 0;
	private float dRangeLvl = 0;
	private float dRangeSquaredAge = 0;
	private float dRangeSquaredLvl = 0;


	public AttackerQualities()
	{
		bonuses = new Bonuses();
	}
	public AttackerQualities( AttackerQualities aq )
	{
		bonuses = new Bonuses();
	}




	public AttackerQualities( AttackerQualities aq , Bonuses bonuses2 )
	{
		focusRangeSquared=aq.focusRangeSquared;
		attackRangeSquared=aq.attackRangeSquared;
		attackRange=aq.attackRange;
		staysAtDistanceSquared=aq.staysAtDistanceSquared;
		damage=aq.damage;
		regenRate=aq.regenRate;
		ROF=aq.ROF;
		healAmount=aq.healAmount;
		focusRange= aq.focusRange ;
		bonuses = bonuses2;

		dDamageAge = aq.dDamageAge;
		dDamageLvl = aq.dDamageLvl;
		dROFAge = aq.dROFAge;
		dROFLvl = aq.dROFLvl;

		dRangeAge = aq.dRangeAge;
		dRangeLvl = aq.dRangeLvl;
		dRangeSquaredAge = aq.dRangeSquaredAge;
		dRangeSquaredLvl = aq.dRangeSquaredLvl;
	}



	/**
	 * The focus range is the range used to see if a target should be forgotten about because it is too far away.
	 * Focus range is useful for melee soldiers whose attack range is low.
	 * This is pointless to use for buildings.
	 */
	 public float getFocusRangeSquared() {
		 return focusRangeSquared;
	 }
	 public void setFocusRangeSquared(float f)
	 {
		 focusRangeSquared = f;
		 focusRange = (int) vector.sqrt(f);
	 }



	 public float getAttackRange() {
		 return attackRange;
	 }

	 public void setAttackRange(float attackRange_) {
		 attackRange = attackRange_;
		 attackRangeSquared = attackRange*attackRange;
	 }
	 /**
	  * @return the attackRangeSquared
	  */
	 public float getAttackRangeSquared() {
		 return attackRangeSquared;
	 }
	 /**
	  * @param f the attackRangeSquared to set
	  */
	 public void setAttackRangeSquared(float f) {
		 attackRangeSquared = f;
		 attackRange = vector.sqrt(f);
	 }



	 /**
	  * @return the staysAtDistanceSquared
	  */
	 public float getStaysAtDistanceSquared() {
		 return staysAtDistanceSquared;
	 }
	 /**
	  * @param staysAtDistanceSquared the staysAtDistanceSquared to set
	  */
	 public void setStaysAtDistanceSquared(float staysAtDistanceSquared) {
		 this.staysAtDistanceSquared = staysAtDistanceSquared;
	 }

	 /**
	  * @return the damage
	  */
	 public int getDamage()
	 {
		 return (int) (damage * bonuses.getDamageBonus()) ;
	 }
	 /**
	  * @param damage the damage to set
	  */
	 public void setDamage(int damage) {
		 this.damage = damage;
	 }


	 /**
	  * @return the regenRate
	  */
	 public int getRegenRate()
	 {
		 return regenRate + bonuses.getHpRegenBonus() ;
	 }

	 /**
	  * @param regenRate the regenRate to set
	  */
	 public void setRegenRate(int regenRate) {
		 this.regenRate = regenRate;
	 }
	 /**
	  * @return the rOF
	  */
	 public int getROF()
	 {
		 return ROF + bonuses.getROABonus() ;
	 }
	 /**
	  * @param rOF the rOF to set
	  */
	 public void setROF(int rOF) {
		 ROF = rOF;
	 }
	 public int getHealAmount() {
		 return healAmount;
	 }
	 public void setHealAmount(int healAmount) {
		 this.healAmount = healAmount;
	 }

	 public void addAttack( Attack atk )
	 {
		 attacks.add( atk );
		 cycleThroughAttacks = true;
	 }

	public void clearAttacks(){
		currentAttack = null;
		attacks.clear();
	}

	 /**
	  * @return one of the attacks
	  */
	 public synchronized Attack getCurrentAttack()
	 {
		 if( cycleThroughAttacks )
		 {
			 Attack currAttack = attacks.get(attacksCursor++);
			 attacksCursor %= attacks.size();
			 return currAttack;
		 }
		 else
			 return currentAttack;
	 }


	 public void setCurrentAttack(Attack attack)
	 {
		 currentAttack = attack;
		 addAttack( attack );
	 }



	 public void addFriendlyAttack( Attack atk )
	 {
		 friendlyAttacks.add( atk );
		 cycleThroughFriendlyAttacks = true;
	 }

	 public Attack getFriendlyAttack() {
		 if( cycleThroughFriendlyAttacks )
		 {
			 Attack currAttack = friendlyAttacks.get(friendlyAttackCursor++);
			 friendlyAttackCursor %= friendlyAttacks.size();
			 return currAttack;
		 }
		 else
			 return friendlyAttack;
	 }

	 public void setFriendlyAttack(Attack friendlyAttack) {
		 this.friendlyAttack = friendlyAttack;
	 }




	 public float getFocusRange()
	 {
		 return focusRange;
	 }
	 public void setCycleThroughAttacks(boolean b) {
		 cycleThroughAttacks = b;
	 }
	 public boolean isCycleThroughFriendlyAttacks() {
		 return cycleThroughFriendlyAttacks;
	 }
	 public void setCycleThroughFriendlyAttacks(boolean cycleThroughFriendlyAttacks) {
		 this.cycleThroughFriendlyAttacks = cycleThroughFriendlyAttacks;
	 }

	 public int getdDamageAge() {
		 return dDamageAge;
	 }

	 public int getdDamageLvl() {
		 return dDamageLvl;
	 }

	 public void setdDamageAge(int dDamageAge) {
		 this.dDamageAge = dDamageAge;
	 }

	 public void setdDamageLvl(int dDamageLvl) {
		 this.dDamageLvl = dDamageLvl;
	 }

	 public int getdROFAge() {
		 return dROFAge;
	 }

	 public int getdROFLvl() {
		 return dROFLvl;
	 }

	 public void setdROFAge(int dROFAge) {
		 this.dROFAge = dROFAge;
	 }

	 public void setdROFLvl(int dROFLvl) {
		 this.dROFLvl = dROFLvl;
	 }

	 public void setdRangeSquaredAge(float dRangeSquaredAge) {
		 this.dRangeSquaredAge = dRangeSquaredAge;
		 dRangeAge = (float) Math.sqrt( dRangeSquaredAge );
	 }

	 public void setdRangeSquaredLvl(float dRangeSquaredLvl) {
		 this.dRangeSquaredLvl = dRangeSquaredLvl;
		 dRangeLvl = (float) Math.sqrt( dRangeSquaredLvl );
	 }

	 public float getdRangeSquaredAge() {
		 return dRangeSquaredAge;
	 }

	 public float getdRangeSquaredLvl() {
		 return dRangeSquaredLvl;
	 }

	 public float getdRangeAge() {
		 return dRangeAge;
	 }

	 public void setdRangeAge(float dRangeAge) {
		 this.dRangeAge = dRangeAge;
	 }

	 public float getdRangeLvl() {
		 return dRangeLvl;
	 }

	 public void setdRangeLvl(float dRangeLvl) {
		 this.dRangeLvl = dRangeLvl;
	 }

	public void removeAllAttackAnims() {
		if( currentAttack != null )
			currentAttack.removeAnim();
		if( friendlyAttack != null )
			friendlyAttack.removeAnim();
		for( Attack a : attacks )
			a.removeAnim();
	}
}
