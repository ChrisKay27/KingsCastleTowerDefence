package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.abilities;


import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.teams.Teams;

public class AbilityParams
{

	private Ability abilityToBeCopied;

	private LivingThing shooter;
	private LivingThing target;

	private float rangeSquared;

	private Teams team;


	public Ability getAbilityToBeCopied() {
		return abilityToBeCopied;
	}

	public void setAbilityToBeCopied(Ability abilityToBeCopied) {
		this.abilityToBeCopied = abilityToBeCopied;
	}

	public LivingThing getShooter() {
		return shooter;
	}

	public void setShooter(LivingThing shooter) {
		this.shooter = shooter;
	}

	public LivingThing getTarget() {
		return target;
	}

	public void setTarget(LivingThing target) {
		this.target = target;
	}

	public float getRangeSquared() {
		return rangeSquared;
	}

	public void setRangeSquared(float rangeSquared) {
		this.rangeSquared = rangeSquared;
	}

	public Teams getTeam() {
		return team;
	}

	public void setTeam(Teams team) {
		this.team = team;
	}






}
