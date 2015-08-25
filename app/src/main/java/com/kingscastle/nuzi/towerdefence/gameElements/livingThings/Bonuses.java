package com.kingscastle.nuzi.towerdefence.gameElements.livingThings;

public class Bonuses {

	private float speedBonusMultiplier = 1;
	private int magicDamageBonus;
	private float damageBonus = 1;
	private int meleeDamageBonus;
	private float armorBonus;
	private int regenBonus;
	private int ROABonus;
	private int hpRegenBonus;
	private int mpRegenBonus;


	public int getMagicDamageBonus() {
		return magicDamageBonus;
	}
    public void setMagicDamageBonus(int magicDamageBonus) {
        this.magicDamageBonus = magicDamageBonus;
    }

	public float getSpeedBonusMultiplier() {
		return speedBonusMultiplier;
	}
	public void addToSpeedBonusMultiplier(float f) {
		this.speedBonusMultiplier += f;
	}
    public void subtractFromSpeedBonusMultiplier(float f) {
        this.speedBonusMultiplier -= f;
    }



	public float getDamageBonus() {
		return damageBonus;
	}

	public void setDamageBonus(float damageBonus)	{
		if( damageBonus != 0 )
			this.damageBonus = damageBonus;
	}

	public int getMeleeDamageBonus() {
		return meleeDamageBonus;
	}

	public void setMeleeDamageBonus(int meleeDamageBonus) {
		this.meleeDamageBonus = meleeDamageBonus;
	}

	public int getRegenBonus() {
		return regenBonus;
	}

	public void setRegenBonus(int regenBonus) {
		this.regenBonus = regenBonus;
	}

	public float getArmorBonus() {
		return armorBonus;
	}
	public void setArmorBonus(float ab) {
		armorBonus=ab;
	}

	public int getROABonus() {
		return ROABonus;
	}

	public void setROABonus(int rOABonus) {
		ROABonus = rOABonus;
	}

	public int getHpRegenBonus() {
		return hpRegenBonus;
	}
	public void setHpRegenBonus(int hpRegenBonus2) {
		hpRegenBonus=hpRegenBonus2;
	}
	public int getMpRegenBonus() {
		return mpRegenBonus;
	}
	public void setMpRegenBonus(int mpRegenBonus2) {
		mpRegenBonus=mpRegenBonus2;
	}


}
