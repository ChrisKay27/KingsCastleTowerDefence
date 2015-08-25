package com.kingscastle.nuzi.towerdefence.gameElements.projectiles;


import com.kingscastle.nuzi.towerdefence.effects.animations.Anim;
import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.framework.Graphics;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg.Direction;
import com.kingscastle.nuzi.towerdefence.gameElements.GameElement;
import com.kingscastle.nuzi.towerdefence.gameElements.ImageFormatInfo;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.teams.Teams;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Projectile extends GameElement
{
	public static final int updatePositionInterval = 27; //ms
	private static final float velocityFramerateBonus = 1.5f ;
	private static float normalSpeed;
	private static int damageBonus = 0;
	protected final vector endPosIfBelow = new vector( Float.MIN_VALUE , Float.MIN_VALUE );
	protected final vector endPosIfAbove = new vector( Float.MAX_VALUE , Float.MAX_VALUE );
	protected Teams team;
	protected vector velocity;
	protected LivingThing shooter;
	protected Anim projAnim;
	private vector startLoc;
	private vector unit;
	private Direction dir;
	private int damage;
	private Image image;
	//private long endFlightTime = GameTime.getTime() + 1000;

	private float rangeSquared;
	private long lastMoved;

	Projectile()	{}

	Projectile(LivingThing shooter)
	{
		this.shooter = shooter;

		if( shooter != null )
			team = shooter.getTeamName();
	}

	public static Projectile getProjectileByString(String name, Teams team2,
			vector loc, vector velocity)
	{
		Projectile p;
		if(name.equals("Arrow"))
		{
			p=new Arrow();p.setLoc(loc);p.setStartLoc(loc);p.setVelocity(velocity);p.setTeam(team2);
			return p;
		}
		if(name.equals("Arrow"))
		{
			p=new Arrow();p.setLoc(loc);p.setStartLoc(loc);p.setVelocity(velocity);p.setTeam(team2);
			return p;
		}

		return null;
	}

	public static void addTechBonusDamage(int i) {
		damageBonus+=i;
	}

	/**
	 * @return the normalSpeed
	 */
	public static float getNormalSpeed() {
		return normalSpeed;
	}

	/**
	 * @param normalSpeed the normalSpeed to set
	 */
	public static void setNormalSpeed(int normalSpeed) {
		Projectile.normalSpeed = normalSpeed;
	}

	/**
	 * @return the damageBonus
	 */
	static int getDamageBonus() {
		return damageBonus;
	}

	/**
	 * @param damageBonus the damageBonus to set
	 */
	public static void setDamageBonus(int damageBonus) {
		Projectile.damageBonus = damageBonus;
	}

	@Override
	public boolean act()
	{
		if( dead )
			return true;

		//if( lastMoved + updatePositionInterval < GameTime.getTime() )
		//{
		loc.x += velocity.x ;
		loc.y += velocity.y ;
		//lastMoved = GameTime.getTime();
		updateArea();

		if( checkIfHitSomething() )
			return false;


		if( loc.x < endPosIfBelow.x || loc.y < endPosIfBelow.y )
			die();
		else if( loc.x > endPosIfAbove.x || loc.y > endPosIfAbove.y )
			die();

		//		if( endFlightTime < GameTime.getTime() )
		//		{
		//			die();
		//		}
		//	if ( startLoc.distanceSquared( loc ) > rangeSquared )
		//	{
		//		die();
		//	}
		//}
		return false;
	}

	void calculateFlight(int flightTime)
	{
		float dx = flightTime*velocity.x;
		if( dx < 0 )
			endPosIfBelow.setX( loc.x + dx );
		else
			endPosIfAbove.setX( loc.x + dx );


		float dy = flightTime*velocity.y;
		if( dy < 0 )
			endPosIfBelow.setY( loc.y + dy );
		else
			endPosIfAbove.setY( loc.y + dy );
	}

	boolean checkIfHitSomething()
	{
		LivingThing lt = cd.checkSingleHit( team , getArea() ,false ,false );

		if( lt != null ){
			lt.takeDamage(damage, shooter);
			die(lt);
			return true;
		}
		else
			return false;
	}

	void die(LivingThing hit)
	{
		if( !dead )
		{
			addDeathAnimation(hit);
			Anim projAnim_local = projAnim;
			if( projAnim_local != null )
				projAnim_local.setOver(true);
			dead = true;
		}
	}

	protected void addDeathAnimation(LivingThing hit) {
		if(projAnim instanceof ProjectileAnim)
			((ProjectileAnim) projAnim ).changeToDeadProj( vector.getDirection4(unit) , hit );
	}

	@Override
	public void die()
	{
		die( null );
	}



	LivingThing getShooter() {
		return shooter;
	}

	/**
	 * @param shooter the shooter to set
	 */
	public void setShooter(LivingThing shooter) {
		this.shooter = shooter;
	}

	/**
	 * @return the unit
	 */
	vector getUnit() {
		return unit;
	}

	/**
	 * @param unit the unit to set
	 */
	public void setUnit(vector unit) {
		this.unit = unit;
	}

	/**
	 * @return the projAnim
	 */
	Anim getProjAnim()
	{
		return projAnim;
	}

	/**
	 * @param a the projAnim to set
	 */
	void setProjAnim(Anim a) {
		projAnim = a;
	}

	/**
	 * @return the rangeSquared
	 */
	public float getRangeSquared() {
		return rangeSquared;
	}

	/**
	 * @param f the rangeSquared to set
	 */
	public void setRangeSquared(float f)
	{
		rangeSquared = f;
	}

	/**
	 * @return the lastMoved
	 */
	public long getLastMoved() {
		return lastMoved;
	}

	/**
	 * @param lastMoved the lastMoved to set
	 */
	public void setLastMoved(long lastMoved) {
		this.lastMoved = lastMoved;
	}

	/**
	 * @return the damage
	 */
	int getDamage() {
		return damage;
	}

	/**
	 * @param damage the damage to set
	 */
	void setDamage(int damage) {
		this.damage = damage;
	}

	/**
	 * @return the startLoc
	 */
	public vector getStartLoc() {
		return startLoc;
	}

	public void setStartLoc( vector loc )
	{
		startLoc = new vector(loc);
	}

	/**
	 * @return the velocity
	 */
	public vector getVelocity() {
		return velocity;
	}

	public void setVelocity( vector velocity2 )
	{
		velocity = velocity2;
		velocity.times( velocityFramerateBonus );
	}

	public void setTeam(Teams team2)
	{
		team=team2;
	}

	public void saveYourSelf(BufferedWriter b) throws IOException
	{
		try
		{
			int rangeRemaining = (int) ( rangeSquared - startLoc.distanceSquared( loc ) );
			String s = "<"+this+" team=\""+ getTeamName() + "\" x=\"" + loc.getIntX() + "\" y=\"" +
					loc.getIntY() + "\"" + " velocityX=\"" + velocity.getIntX() + "\" velocityY=\"" +
					velocity.getIntY() + "\" damage=\"" + getDamage() + "\" rangeRemaining=\""+
					rangeRemaining + "\"/>";
			b.write(s,0,s.length());b.newLine();
		}
		catch(Exception e){
			System.out.println("Error saving projectile:" + this);
			e.printStackTrace();
		}
	}

	@Override
	public Teams getTeamName()
	{
		if( team != null ) {
			return team;
		} else {
			return shooter.getTeamName();
		}
	}

	Direction getDir() {
		return dir;
	}

	/**
	 * @param dir the dir to set
	 */
	void setDir(Direction dir) {
		this.dir = dir;
	}

	@Override
	public boolean create( MM mm )
	{
		boolean superCreate = super.create(mm);
		if( superCreate )
			loadAnimation(mm);

		return superCreate;
	}

	@Override
	public void saveYourself(BufferedWriter b) throws IOException
	{
		String s = "<" + getName() + " team=\""+getTeamName()+"\" x=\""+loc.getIntX()+
				"\" y=\""+loc.getIntY()+"\" velocityX=\""+getVelocity().getIntX()+
				"\" velocityY=\""+getVelocity().getIntY()+"\" />";
		b.write(s,0,s.length());b.newLine();
	}





	Image getImage() {
		return image;
	}
	void setImage(Image image) {
		this.image = image;
	}

	@Override
	public Image[] getStaticImages() {
		return null;
	}

	//	private class StuckPosition
	//	{
	//		private Image img;
	//		private Vector offs;
	//	//	private Direction dir;
	//
	//		public StuckPosition(Direction dir,Vector offs,Image img){
	//			//this.dir=dir;
	//			this.offs=offs;this.img=img;
	//		}
	//		public Image getImg() {
	//			return img;
	//		}
	//		public Vector getOffs() {
	//			return offs;
	//		}
	//		//public void setOffs(Vector offs) {
	//		//	this.offs = offs;
	//		//}
	//		//public Direction getDir() {
	//		//	return dir;
	//		//}
	//		//public void setDir(Direction dir) {
	//		//	this.dir = dir;
	//		//}
	//	}

	@Override
	public void setStaticImages(Image[] images) {

	}

	@Override
	public ImageFormatInfo getImageFormatInfo() {
		return null;
	}

	protected abstract Image getDeadImage();

	public abstract ArrayList<Image> getDeadImages();

	public abstract Projectile newInstance();

	public abstract Projectile newInstance(@NotNull LivingThing shooter ,@NotNull vector predictedLocation ,@NotNull LivingThing target);

	public abstract Projectile newInstance(@NotNull LivingThing shooter , @NotNull vector unitVectorInDirection );

	public abstract float getStaticRangeSquared();

	public abstract int getStaticDamage();

	public abstract float getStaticSpeed();

	@Override
	public abstract String getName();

	public static class ProjectileAnim extends Anim
	{
		private Projectile proj;
		private long projDiedAt;
		private Image image;
		private LivingThing hit;
		public ProjectileAnim(Projectile a)
		{
			proj=a;
			image=proj.getImage();
			setLoc(proj.loc);
		}
		//private StuckPosition eastOffs,southOffs,northOffs,westOffs;
		//private Side sideHit;

		@Override
		public void paint(Graphics g,vector v2)
		{
			if( hit == null )
			{
				super.paint(g, v2);
				return;
			}
			else
			{
				setOver( true );
			}
			//			else
			//			{
			//				switch(hit.getLookDirection())
			//				{
			//				case N: if(northOffs!=null){image=northOffs.getImg(); super.paint(g, v2.add(northOffs.getOffs()));}return;
			//				case S: if(southOffs!=null){image=southOffs.getImg(); super.paint(g, v2.add(southOffs.getOffs()));}return;
			//				case E: if(eastOffs!=null){image=eastOffs.getImg();super.paint(g, v2.add(eastOffs.getOffs()));}return;
			//				default: case W: if(westOffs!=null){image=westOffs.getImg(); super.paint(g, v2.add(westOffs.getOffs()));}return;
			//				}
			//			}
		}

		public void changeToDeadProj(Direction dir, LivingThing hit)
		{
			if( hit != null )
				setLoc(hit.loc);

			setProjDiedAt(GameTime.getTime());
			image=proj.getDeadImage();
			proj=null;
		}

		public void setProjDiedAt( long currentTimeMillis )
		{
			projDiedAt = currentTimeMillis;
		}

		@Override
		public boolean act()
		{
			return isOver();
		}

		@Override
		public Image getImage()
		{
			return image;
		}

		@Override
		public boolean isOver()
		{
			if ( projDiedAt != 0 && projDiedAt + 2000 < GameTime.getTime() || (hit != null && hit.isDead() ) )
			{
				proj = null;
				return true;
			}
			else
			{
				return false;
			}
		}

		public enum Side{Top,Bottom,Left,Right}

		//		private Side determineSideHit(Vector loc,Rect hitsArea)
		//		{
		//			int distToLeft=Math.abs(loc.getIntX()-hitsArea.left),distToRight=Math.abs(loc.getIntX()-hitsArea.right),
		//				distToTop=Math.abs(loc.getIntY()-hitsArea.top),	distToBottom=Math.abs(loc.getIntY()-hitsArea.bottom);
		//			if(distToLeft<distToRight)
		//			{
		//				if(distToTop<distToBottom)
		//				{
		//					if(distToTop<=distToLeft)
		//					{
		//						return Side.Top;
		//					}else{
		//						return Side.Left;
		//					}
		//				}else{
		//					if(distToBottom<distToLeft){
		//						return Side.Bottom;
		//					}else{
		//						return Side.Left;
		//					}
		//				}
		//			} else{
		//				if(distToTop<distToBottom){
		//					if(distToTop<=distToRight){
		//						return Side.Top;
		//					}else{
		//						return Side.Right;
		//					}
		//				}else{
		//					if(distToBottom<distToRight){
		//						return Side.Bottom;
		//					}else{
		//						return Side.Right;
		//					}
		//				}
		//			}
		//
		//		}
	}



}
