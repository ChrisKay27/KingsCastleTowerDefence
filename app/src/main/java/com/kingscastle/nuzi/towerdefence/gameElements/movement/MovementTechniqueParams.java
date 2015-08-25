package com.kingscastle.nuzi.towerdefence.gameElements.movement;


import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameElements.movement.pathing.Path;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

public class MovementTechniqueParams
{


	private MM mm;

	public void setMM(MM MM) {
		this.mm = MM;
	}

	public MM getMM() {
		return mm;
	}

	public enum MovementType
	{
		SEEK , AVOID , DPAD, FOLLOW_PATH
	}
	public enum Direction
	{
		TOWARDS_ENEMY , TOWARDS_LOCATION , AWAY_FROM
	}

	private float speed;
	private float force;
	private MovementType movementType;
	private Direction direction;
	private vector locationOfInterest, inDirection;
	private Path pathToFollow;

	/**
	 * @return the speed
	 */
	protected float getSpeed()
	{
		return speed;
	}
	/**
	 * @param speed the speed to set
	 */
	protected void setSpeed(float speed)
	{
		this.speed = speed;
	}

	/**
	 * @return the force
	 */
	protected float getMaxForce()
	{
		if( force == 0 )
		{
			force = getSpeed()/3;
		}
		return force;
	}


	/**
	 * @param force the force to set
	 */
	protected void setForce(float force) {
		this.force = force;
	}


	/**
	 * @return the movementType
	 */
	protected MovementType getMovementType() {
		return movementType;
	}


	/**
	 * @param movementType the movementType to set
	 */
	protected void setMovementType(MovementType movementType) {
		this.movementType = movementType;
	}


	/**
	 * @return the destionation
	 */
	protected vector getLocationOfInterest() {
		return locationOfInterest;
	}
	/**
	 * @param destionation the destionation to set
	 */
	protected void setLocationOfInterest(vector locationOfInterest) {
		this.locationOfInterest = locationOfInterest;
	}
	/**
	 * @return the inDirection
	 */
	protected vector getInDirection() {
		return inDirection;
	}
	/**
	 * @param inDirection the inDirection to set
	 */
	protected void setInDirection(vector inDirection) {
		this.inDirection = inDirection;
	}
	public Direction getDirection() {
		return direction;
	}
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	public Path getPathToFollow() {
		return pathToFollow;
	}
	public void setPathToFollow(Path pathToFollow) {
		this.pathToFollow = pathToFollow;
	}


}
