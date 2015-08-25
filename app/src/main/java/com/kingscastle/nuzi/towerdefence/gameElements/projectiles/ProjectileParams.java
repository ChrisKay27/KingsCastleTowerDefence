package com.kingscastle.nuzi.towerdefence.gameElements.projectiles;


import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.teams.Teams;

public class ProjectileParams {

	private String projName;
	private Projectile projToBeCopied;
	private vector location , destination ,  unitVectorInDirection , velocity;
	private LivingThing shooter , target;
	private int damage , rangeSquared , speed;
	private Teams team;
	
	
	
	/**
	 * @return the projName
	 */
	public String getProjName() {
		return projName;
	}
	/**
	 * @param projName the projName to set
	 */
	public void setProjName(String projName) {
		this.projName = projName;
	}
	/**
	 * @return the projToBeCopied
	 */
	public Projectile getProjToBeCopied() {
		return projToBeCopied;
	}
	/**
	 * @param projToBeCopied the projToBeCopied to set
	 */
	public void setProjToBeCopied(Projectile projToBeCopied) {
		this.projToBeCopied = projToBeCopied;
	}
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
	 * @return the destination
	 */
	public vector getDestination() {
		return destination;
	}
	/**
	 * @param destination the destination to set
	 */
	public void setDestination(vector destination) {
		this.destination = destination;
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
	 * @return the rangeSquared
	 */
	public int getRangeSquared() {
		return rangeSquared;
	}
	/**
	 * @param rangeSquared the rangeSquared to set
	 */
	public void setRangeSquared(int rangeSquared) {
		this.rangeSquared = rangeSquared;
	}
	/**
	 * @return the speed
	 */
	public int getSpeed() {
		return speed;
	}
	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	/**
	 * @return the team
	 */
	public Teams getTeam() {
		return team;
	}
	/**
	 * @param Teams the Teams to set
	 */
	public void setTeam(Teams team) {
		this.team = team;
	}
	

}
