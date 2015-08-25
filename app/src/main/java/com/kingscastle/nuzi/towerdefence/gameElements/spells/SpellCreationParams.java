package com.kingscastle.nuzi.towerdefence.gameElements.spells;


import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.teams.Teams;

public class SpellCreationParams
{

	private String spellName;
	private Spell spellToBeCopied;
	private vector lockedOnLocation , location , destination ,  unitVectorInDirection , velocity;
	private LivingThing shooter;
	private LivingThing target;
	private float rangeSquared;
	private int damage;
	private float speed;
	private Teams team;
	private int rangeRemaining;
	private float range;

	/**
	 * @return the location
	 */
	public vector getLocation() {
		return location;
	}


	/**
	 * @param location the location to set
	 */
	public void setLocation(vector location) {
		this.location = location;
	}


	/**
	 * @return the unitVectorInDirection
	 */
	public vector getUnitVectorInDirection() {
		return unitVectorInDirection;
	}


	/**
	 * @param unitVectorInDirection the unitVectorInDirection to set
	 */
	public void setUnitVectorInDirection(vector unitVectorInDirection) {
		this.unitVectorInDirection = unitVectorInDirection;
	}


	/**
	 * @return the velocity
	 */
	public vector getVelocity() {
		return velocity;
	}


	/**
	 * @param velocity the velocity to set
	 */
	public void setVelocity(vector velocity) {
		this.velocity = velocity;
	}


	/**
	 * @return the shooter
	 */
	public LivingThing getShooter() {
		return shooter;
	}


	/**
	 * @param shooter the shooter to set
	 */
	public void setShooter(LivingThing shooter) {
		this.shooter = shooter;
	}


	/**
	 * @return the target
	 */
	public LivingThing getTarget() {
		return target;
	}


	/**
	 * @param target the target to set
	 */
	public void setTarget(LivingThing target) {
		this.target = target;
	}


	/**
	 * @return the damage
	 */
	public int getDamage() {
		return damage;
	}


	/**
	 * @param damage the damage to set
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}


	/**
	 * @return the range
	 */
	public float getRangeSquared() {
		return rangeSquared;
	}


	/**
	 * @param icicleRangeSquared the range to set
	 */
	public void setRangeSquared(float icicleRangeSquared) {
		rangeSquared = icicleRangeSquared;
	}


	/**
	 * @return the speed
	 */
	public float getSpeed() {
		return speed;
	}


	/**
	 * @param staticSpeed the speed to set
	 */
	public void setSpeed(float staticSpeed) {
		speed = staticSpeed;
	}


	/**
	 * @return the team
	 */
	public Teams getTeam() {
		return team;
	}


	/**
	 * @param team2 the Teams to set
	 */
	public void setTeam(Teams team2) {
		team = team2;
	}


	public vector getDestination() {
		return destination;
	}


	public void setDestination(vector destination) {
		this.destination = destination;
	}


	public Spell getSpellToBeCopied() {
		return spellToBeCopied;
	}
	public void setSpellToBeCopied(Spell spellToBeCopied) {
		this.spellToBeCopied = spellToBeCopied;
	}


	public String getSpellName() {
		return spellName;
	}


	public void setSpellName(String spellName) {
		this.spellName = spellName;
	}


	public vector getLockedOnLocation() {
		return lockedOnLocation;
	}


	public void setLockedOnLocation(vector lockedOnLocation) {
		this.lockedOnLocation = lockedOnLocation;
	}


	public void setRangeRemaining(int rangeRemaining2) {
		rangeRemaining=rangeRemaining2;
	}

	public int getRangeRemaining() {
		return rangeRemaining;
	}


	public void setRange(float range) {
		this.range = range;
	}


	public float getRange() {
		return range;
	}
}
