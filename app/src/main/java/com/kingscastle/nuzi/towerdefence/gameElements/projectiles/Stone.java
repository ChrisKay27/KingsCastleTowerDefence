package com.kingscastle.nuzi.towerdefence.gameElements.projectiles;

import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.effects.animations.QuakeAnim;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Stone extends Projectile
{
	private static final String TAG = "Stone";

	private static final Image image = Assets.loadImage(R.drawable.stone) ;
	private static final RectF staticPerceivedArea = new RectF( -Rpg.thirtyDp, -Rpg.thirtyDp , Rpg.thirtyDp , Rpg.thirtyDp );
	private static final int baseDamage = 50;
	private static final float staticSpeed = Rpg.getDp() * 6;

	public Stone()	{}

	public Stone( LivingThing caster , LivingThing target )
	{
		this(caster, new vector(target.loc), target);
	}

	private Stone(@NotNull LivingThing shooter,@NotNull vector to,@NotNull LivingThing target)
	{
		super(shooter);
		setShooter(shooter);

		to.randomize((int)(Math.random()*Rpg.tenDp));

		vector unit = new vector( shooter.loc , to );
		unit.turnIntoUnitVector();

		setUnit(unit);
		setAttributes(shooter, unit, to);
	}

	@Override
	public boolean act()
	{
		if( dead )
			return true;

		loc.x += velocity.x ;
		loc.y += velocity.y ;

		updateArea();

		if( getStartLoc().distanceSquared(loc) > getRangeSquared() )
			checkIfHitSomething();

//		if( loc.x < endPosIfBelow.x || loc.y < endPosIfBelow.y )
//			checkIfHitSomething();
//		else if( loc.x > endPosIfAbove.x || loc.y > endPosIfAbove.y )
//			checkIfHitSomething();

		return false;
	}

	@Override
	boolean checkIfHitSomething()
	{
		List<LivingThing> lts = cd.checkMultiHit(team, getArea(), false);

		die();
		if( !lts.isEmpty() ){
			for( LivingThing lt : lts ) {
				lt.takeDamage(shooter.getDamage(), shooter);
			}
			return true;
		}
		else
			return false;
	}

	@Override
	protected void addDeathAnimation(LivingThing hit) {
		if(projAnim instanceof ProjectileAnim)
			((ProjectileAnim) projAnim ).changeToDeadProj( null , null );
		getMM().getEm().add(new QuakeAnim(loc, QuakeAnim.QuakeColor.Brown).setScale(0.75f));
	}

	private void setAttributes(@NotNull LivingThing shooter,@NotNull vector unit,@NotNull vector to)	{

		vector loc = new vector();
		loc.set(shooter.loc);
		setLoc(loc);
		setStartLoc(shooter.loc);
		setDamage(shooter.getAQ().getDamage() + getDamageBonus());

		setImage(image);

		vector velocity = new vector();
		velocity.set( unit );
		velocity.times(staticSpeed);

		float distSquared = Math.min(shooter.aq.getAttackRangeSquared(),loc.distanceSquared(to));
		setRangeSquared(distSquared);
		//velocity.add( Math.random()*2 - 1 , Math.random()*2 - 1 );

		//K so the velocity is set first, then the range is set, when the range is set( see method )
		// it calculates out how long it will be in the air, then the shooters
		// velocity is added to the proj's velocity for Galilian Trans~ Eqn satisfaction?

		setVelocity(velocity);

		if( to.x < loc.x )
			endPosIfBelow.setX( to.x );
		else
			endPosIfAbove.setX( to.x );

		if( to.y < loc.y )
			endPosIfBelow.setY( to.y );
		else
			endPosIfAbove.setY( to.y );


		//int flightTime = (int) ( new Vector(loc).distance(to) / staticSpeed );

		//calculateFlight( flightTime );

		updateArea();
	}

//
//	@Override
//	void calculateFlight(int flightTime)
//	{
//		float dx = flightTime*velocity.x;
//		if( dx < 0 )
//			endPosIfBelow.setX( loc.x + dx );
//		else
//			endPosIfAbove.setX( loc.x + dx );
//
//
//		float dy = flightTime*velocity.y;
//		if( dy < 0 )
//			endPosIfBelow.setY( loc.y + dy );
//		else
//			endPosIfAbove.setY( loc.y + dy );
//	}

	@Override
	public String getName()
	{
		return TAG;
	}



	@Override
	public Projectile newInstance(@NotNull LivingThing shooter ,@NotNull vector predLoc ,@NotNull LivingThing target )	{
		return new Stone( shooter , predLoc , target );
	}



	@Override
	public Projectile newInstance(@NotNull LivingThing shooter,@NotNull vector unitVectorInDirection)	{
		return new Stone(shooter,unitVectorInDirection,null);
	}






	@Override
	public Image getDeadImage()
	{
		return image;
	}


	@Override
	public void loadAnimation( @NotNull MM mm )
	{
		setProjAnim( new ProjectileAnim( this ) );
		mm.getEm().add( getProjAnim() , true );
	}





	@Override
	public Projectile newInstance()
	{
		return new Stone();
	}

	@Override
	public float getStaticRangeSquared()
	{
		return staticSpeed;
	}

	@Override
	public int getStaticDamage()
	{
		return baseDamage;
	}

	@Override
	public float getStaticSpeed()	{
		return staticSpeed;
	}







	@Override
	public RectF getStaticPerceivedArea()	{
		return staticPerceivedArea;
	}
	@Override
	public RectF getPerceivedArea()	{
		return staticPerceivedArea;
	}

	@Override
	public void setStaticPerceivedArea( RectF staticPercArea2 )	{
		staticPerceivedArea.set(staticPercArea2);
	}

	
	@Override
	public ArrayList<Image> getDeadImages(){	
		return null;
	}




}
