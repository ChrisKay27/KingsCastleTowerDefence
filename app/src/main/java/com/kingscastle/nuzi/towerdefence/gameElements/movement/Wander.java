package com.kingscastle.nuzi.towerdefence.gameElements.movement;


class Wander  {
//
//	private LivingThing driver;
//	private Vector EffectsManager.Position;
//	private Vector velocity;
//	private float max_force;
//	private float max_speed;
//	private Vector wiskerTip;
//	private Line wisker;
//	private Direction prevDir;
//	private int dirChangeCount;
//	private long timeSinceFirstDirChange;
//
//	public Wander(LivingThing m) {
//		driver = m;
//		position = driver.loc;
//	}
//
//	public void act(Vector dest) {
//		if(dest==null)
//			return;
//		// long startTime=GameTime.getTime();
//		position = driver.loc;
//
//		if (velocity == null)
//			velocity = truncate(Vector.vectorBetween(position, dest));
//		System.out.println("velocity is : " +velocity);
//		Vector unitVector = new Vector(position, dest).getUnitVector();
//		System.out.println("UnitVector: " +unitVector);
//		velocity = velocity.add(unitVector.times(max_force));
//		System.out.println("Velocity after adding unitVector times max_force: " +unitVector);
//		velocity = truncate(velocity);
//		System.out.println("Velocity after trucating : " +velocity);
//		wiskerTip = new Vector(unitVector).times(20*Rpg.getDp()).add(position);
//		System.out.println("position is  : " +position);
//		System.out.println("wiskerTip is  : " +wiskerTip);
//		wisker = new Line(position, wiskerTip);
//		Inter inter = Inter
//				.getClosestIntersection(wisker, driver);
//		System.out.println("inter is " + inter);
//		// boolean placeable =
//		// RpgConstants.getGame().getLevel().isPlaceable(driver.area);
//		Direction d = null;
//		if (inter != null) {
//			if (inter.getHitLivingThing() != null && driver.getTeam() == Teams.Evil
//					&& !inter.getHitLivingThing().isDead()
//					&& inter.getHitLivingThing().getTeam() != driver.getTeam()) {
//				driver.setTarget(inter.getHitLivingThing());
//				return;
//			} else if (inter.getHitLivingThing() != null && inter.getHitLivingThing().isWalking() && inter.getHitLivingThing().getTeam() == driver.getTeam()) {
//				return;
//			}
//			
//			velocity = velocity.removeComponentInDir(inter.getNormal());
//			velocity = truncate(velocity, max_speed);
//			
//			
//			
//			long t = GameTime.getTime();
//		
//			if (prevDir == null)
//				prevDir = d;
//			if (!d.equals(prevDir)) {
//				if (dirChangeCount == 0) {
//					timeSinceFirstDirChange = t;
//					dirChangeCount++;
//				} else if (t - timeSinceFirstDirChange < 2000) {
//					dirChangeCount++;
//					if (dirChangeCount > 4) {
//						driver.walkTo(null);
//						dirChangeCount = 0;
//					}
//				} else {
//					timeSinceFirstDirChange = t;
//					dirChangeCount = 1;
//				}
//				prevDir = d;
//
//			}
//		}
//		if (d != null)
//			driver.setLookDirection(d);
//		else
//		
//		position = EffectsManager.Position.add(velocity);
//		driver.setLoc(position);
//		// System.out.println("Took " + (GameTime.getTime()-startTime) +
//		// " milliseconds for seek to act");
//	}
//
//	public Vector truncate(Vector v) {
//		return v.getUnitVector().times(max_speed);
//	}
//
//	public static Vector truncate(Vector v, float max_force) {
//		return v.getUnitVector().times(max_force);
//	}
//
//	public void stop() {
//
//	}
//
//	public void updateVectorTip() {
//
//	}
//
//	@Override
//	public Vector getVelocity() {
//		return velocity;
//	}
//
//	//@Override
//	//public void act(Vector dest, float max_speed, float max_force) {
//		
////	}
//
//	@Override
//	public void act(Vector dest, MovementType movementType, float max_speed,
//			float max_force) {
//		// TODO Auto-generated method stub
//		
//	}

}

