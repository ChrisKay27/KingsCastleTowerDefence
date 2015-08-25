package com.kingscastle.nuzi.towerdefence.gameElements.livingThings;

public class LevelAdjustments {
	private int dHealth,dMana,dDamage,dROA,dFocusRange,dExpGiven;
	private double dSpeed;
	public LevelAdjustments(int dHealth2, int dMana2, double dSpeed2, int dDamage2, int dROA2, int dFocusRange2, int dExpGiven2) {
		setdHealth(dHealth2);setdMana(dMana2);setdSpeed(dSpeed2);setdDamage(dDamage2);
		setdROA(dROA2);dFocusRange=dFocusRange2;setdExpGiven(dExpGiven2);
		
	}
	public int getdHealth() {
		return dHealth;
	}
	void setdHealth(int dHealth) {
		this.dHealth = dHealth;
	}
	public int getdMana() {
		return dMana;
	}
	void setdMana(int dMana) {
		this.dMana = dMana;
	}
	public int getdDamage() {
		return dDamage;
	}
	void setdDamage(int dDamage) {
		this.dDamage = dDamage;
	}
	public int getdROA() {
		return dROA;
	}
	void setdROA(int dROA) {
		this.dROA = dROA;
	}
	public int getdFocusRange() {
		return dFocusRange;
	}
	public void setdFocusRange(int dFocusRange) {
		this.dFocusRange = dFocusRange;
	}
	public double getdSpeed() {
		return dSpeed;
	}
	void setdSpeed(double dSpeed) {
		this.dSpeed = dSpeed;
	}
	public int getdExpGiven() {
		return dExpGiven;
	}
	void setdExpGiven(int dExpGiven) {
		this.dExpGiven = dExpGiven;
	}

}
