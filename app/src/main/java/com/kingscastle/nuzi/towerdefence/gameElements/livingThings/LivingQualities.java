package com.kingscastle.nuzi.towerdefence.gameElements.livingThings;

import com.kingscastle.nuzi.towerdefence.effects.animations.Barrable;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameUtils.Age;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

public class LivingQualities {
	private static float speedMultiplier = 1.5f;

	private int requiresTcLvl = 1;
	private int requiresBLvl = -1;
	private int requiresCLvl = -1;
	private Age requiresAge = Age.STONE;

	private int level = 1;
	private int maxLevel = 1;
	private Age age = Age.STONE;

	private final Health health = new Health();


	private int fullMana;
	private int mana;

	private float rangeOfSight;
	private float rangeOfSightSquared;
	private float rangeOfSightSquaredDiv8;

	private int hpRegenAmount;
	private int mpRegenAmount;
	private int regenRate;

	private float savedSpeed;
	private float speed;
	private float force;
	private int expGiven;
	private int goldDropped = 5;

	private float armor = 0;
	private float fireResistance;
	private float iceResistance;
	private float lightningResistance;

	private int popCost = 1;
	private int healAmount = 0;

	private int dHealthAge = 0;
	private int dHealthLvl = 0;
	private int dRegenRateAge = 0;
	private int dRegenRateLvl = 0;
	private float dArmorAge = 0;
	private float dArmorLvl = 0;

	private int dHealAge = 0;
	private int dHealLvl = 0;

	private final Bonuses bonuses = new Bonuses();

	{
		setRangeOfSight(250);
	}

	public LivingQualities(LivingQualities lq) {
		level = lq.level;
		maxLevel = lq.maxLevel;
		health.setFullHealth(lq.health.getFullHealth());
		health.setHealth(lq.health.getHealth());

		fullMana = lq.fullMana;
		mana = lq.mana;
		hpRegenAmount = lq.hpRegenAmount;
		mpRegenAmount = lq.mpRegenAmount;
		regenRate = lq.regenRate;
		speed = lq.speed;
		force = lq.force;
		expGiven = lq.expGiven;
		goldDropped = lq.goldDropped;
		rangeOfSightSquared = lq.rangeOfSightSquared;
		rangeOfSight = lq.rangeOfSight;
		armor = lq.armor;
		fireResistance = lq.fireResistance;
		iceResistance = lq.iceResistance;
		lightningResistance = lq.lightningResistance;

		popCost = lq.popCost;
		healAmount = lq.healAmount;

		dHealthAge = lq.dHealthAge;
		dHealthLvl = lq.dHealthLvl;
		dRegenRateAge = lq.dRegenRateAge;
		dRegenRateLvl = lq.dRegenRateLvl;
		dArmorAge = lq.dArmorAge;
		dArmorLvl = lq.dArmorLvl;

		dHealAge = lq.dHealAge;
		dHealLvl = lq.dHealLvl;

		requiresTcLvl = lq.requiresTcLvl;
		requiresBLvl = lq.requiresBLvl;
		requiresCLvl = lq.requiresCLvl;
		requiresAge = lq.requiresAge;
	}

	public LivingQualities() {
	}

	/**
	 * 
	 * @return the healthPercent
	 */
	public float getHealthPercent() {
		return ((float) health.getHealth()) / health.getFullHealth();
	}

	public int getRequiresBLvl() {
		return requiresBLvl;
	}

	public void setRequiresBLvl(int requiresBLvl) {
		this.requiresBLvl = requiresBLvl;
	}

	/**
	 * @return the requiresCLvl
	 */
	public int getRequiresCLvl() {
		return requiresCLvl;
	}

	/**
	 * @param requiresCLvl the requiresCLvl to set
	 */
	public void setRequiresCLvl(int requiresCLvl) {
		this.requiresCLvl = requiresCLvl;
	}

	public Age getRequiresAge() {
		return requiresAge;
	}

	public void setRequiresAge(Age requiresAge) {
		this.requiresAge = requiresAge;
	}

	/**
	 * @return the requiresTcLvl
	 */
	public int getRequiresTcLvl() {
		return requiresTcLvl;
	}

	/**
	 * @param requiresTcLvl the requiresTcLvl to set
	 */
	public void setRequiresTcLvl(int requiresTcLvl) {
		this.requiresTcLvl = requiresTcLvl;
	}

	/**
	 * @return the manaPercent
	 */
	public float getManaPercent() {
		return ((float) mana) / fullMana;
	}

	/**
	 * 
	 */
	public void addHealth(int dHealth) {
		health.addHealth(dHealth);
	}

	/**
	 * 
	 */
	public void addMana(int dmana) {
		mana += dmana;
	}

	/**
	 * @return the fullMana
	 */
	public int getFullMana() {
		return fullMana;
	}

	/**
	 * @param fullMana
	 *            the fullMana to set
	 */
	public void setFullMana(int fullMana) {
		this.fullMana = fullMana;
	}

	/**
	 * @return the mana
	 */
	public int getMana() {
		return mana;
	}

	/**
	 * @param mana
	 *            the mana to set
	 */
	public void setMana(int mana) {
		this.mana = mana;
	}

	public void setHealth(int hp, int fullHp) {
		setFullHealth(fullHp);
		setHealth(hp);
	}
	/**
	 * @return the fullHealth
	 */
	public int getFullHealth() {
		return health.getFullHealth();
	}

	/**
	 * @param fullHealth
	 *            the fullHealth to set
	 */
	public void setFullHealth(int fullHealth) {
		health.setFullHealth(fullHealth);
	}

	/**
	 * @return the health
	 */
	public int getHealth() {
		return health.getHealth();
	}

	/**
	 * @param health
	 *            the health to set
	 */
	public void setHealth(int health) {
		this.health.setHealth(health);
	}

	public Barrable getHealthObj() {
		return health;
	}

	/**
	 * 
	 * @return the hpRegenAmount
	 */
	public int getHpRegenAmount() {
		return hpRegenAmount;
	}

	/**
	 * @param hpRegenAmount
	 *            the hpRegenAmount to set
	 */
	public void setHpRegenAmount(int hpRegenAmount) {
		this.hpRegenAmount = hpRegenAmount;
	}

	/**
	 * @return the mpRegenAmount
	 */
	public int getMpRegenAmount() {
		return mpRegenAmount;
	}

	/**
	 * @param mpRegenAmount
	 *            the mpRegenAmount to set
	 */
	public void setMpRegenAmount(int mpRegenAmount) {
		this.mpRegenAmount = mpRegenAmount;
	}

	/**
	 * @return the regenRate
	 */
	public int getRegenRate() {

		return regenRate + bonuses.getHpRegenBonus();

	}

	/**
	 * @param regenRate
	 *            the regenRate to set
	 */
	public void setRegenRate(int regenRate) {
		this.regenRate = regenRate;
	}

	public float getSpeed() {
		return speed * bonuses.getSpeedBonusMultiplier();
	}

	public void setSpeed(float f) {
		speed = f;
		setForce(speed / 1.5f);
	}

	public float getForce() {
		return force;
	}

	void setForce(float force) {
		this.force = force;
	}

	public int getExpGiven() {
		return expGiven;
	}

	public void setExpGiven(int expGiven) {
		this.expGiven = expGiven;
	}

	public static float getSpeedMultiplier() {
		return speedMultiplier;
	}

	public static void setSpeedMultiplier(float speedMultiplier) {
		LivingQualities.speedMultiplier = speedMultiplier;
	}

	public void setManaPercent(double d) {
		mana = (int) (fullMana * d);

	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {

		this.level = level;
	}

	public int getGoldDropped() {
		return goldDropped;
	}

	public void setGoldDropped(int goldDropped) {
		this.goldDropped = goldDropped;
	}

	public Bonuses getBonuses() {
		return bonuses;
	}

	public float getRangeOfSightSquared() {
		return rangeOfSightSquared;
	}

	public void setRangeOfSightSquared(float rangeOfSightSquared) {
		this.rangeOfSightSquared = rangeOfSightSquared * Rpg.getDp()
				* Rpg.getDp();
		rangeOfSight = vector.sqrt(rangeOfSightSquared);
		rangeOfSightSquaredDiv8 = this.rangeOfSightSquared / 8;
	}

	public float getRangeOfSight() {
		return rangeOfSight;
	}

	/**
	 * Dp is multiplied here.
	 */
	public void setRangeOfSight(float rangeOfSight2) {
		rangeOfSight = rangeOfSight2 * Rpg.getDp();

		rangeOfSightSquared = rangeOfSight * rangeOfSight;
		rangeOfSightSquaredDiv8 = rangeOfSightSquared / 8;
	}

	public float getRangeOfSightSquaredDiv8() {
		return rangeOfSightSquaredDiv8;
	}

	public int getPopCost() {
		return popCost;
	}

	public void setPopCost(int popCost) {
		if (popCost < 1) {
			this.popCost = 1;
		} else {
			this.popCost = popCost;
		}
	}




	public float getArmor() {
		return armor + bonuses.getArmorBonus();
	}
	public void setArmor(float armor) {
		this.armor = armor;
	}

	public float getFireResistancePerc(){
		return fireResistance;
	}

    public void setFireResistancePerc(float fireResistance){
		this.fireResistance = fireResistance;
	}

	public float getIceResistancePerc() {
		return iceResistance;
	}
	public void setIceResistancePerc(float iceResistance) {
		this.iceResistance = iceResistance;
	}

	public float getLightningResistancePerc() {
		return lightningResistance;
	}
	public void setLightningResistancePerc(float lightningResistance) {
		this.lightningResistance = lightningResistance;
	}



	public int getHealAmount() {
		return healAmount;
	}

	public void setHealAmount(int healAmount) {
		this.healAmount = healAmount;
	}

	public void saveAndReduceSpeed() {
		savedSpeed = speed;
        speed /= 2;
	}

	public void restoreSpeed() {
		speed = savedSpeed;
	}

	public Age getAge() {
		return age;
	}

	public void setAge(Age age) {
		this.age = age;
	}

	public int getdHealthAge() {
		return dHealthAge;
	}

	public int getdHealthLvl() {
		return dHealthLvl;
	}

	public int getdRegenRateAge() {
		return dRegenRateAge;
	}

	public int getdRegenRateLvl() {
		return dRegenRateLvl;
	}

	public void setdHealthAge(int dHealthAge) {
		this.dHealthAge = dHealthAge;
	}

	public void setdHealthLvl(int dHealthLvl) {
		this.dHealthLvl = dHealthLvl;
	}

	public void setdRegenRateAge(int dRegenRateAge) {
		this.dRegenRateAge = dRegenRateAge;
	}

	public void setdRegenRateLvl(int dRegenRateLvl) {
		this.dRegenRateLvl = dRegenRateLvl;
	}

	public float getdArmorLvl() {
		return dArmorLvl;
	}

	public float getdArmorAge() {
		return dArmorAge;
	}

	public void setdArmorAge(float f) {
		dArmorAge = f;
	}

	public void setdArmorLvl(float f) {
		dArmorLvl = f;
	}

	public int getdHealAge() {
		return dHealAge;
	}

	public void setdHealAge(int dHealAge) {
		this.dHealAge = dHealAge;
	}

	public int getdHealLvl() {
		return dHealLvl;
	}

	public void setdHealLvl(int dHealLvl) {
		this.dHealLvl = dHealLvl;
	}

	public void setHealthPercent(float d) {
		health.setHealthPercent(d);
	}


	public int getMaxLevel() {
		return maxLevel;
	}

	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}
}
