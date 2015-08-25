package com.kingscastle.nuzi.towerdefence.gameElements.projectiles;

import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.effects.SpecialEffects;
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

public class Arrow extends Projectile
{
	private static final String TAG = "Arrow";

	private static final ArrayList<Image> images = Assets.loadAnimationImages( R.drawable.serrated_arrow , 8 , 1 );
	private static ArrayList<Image> deadImages ;

	private static RectF staticPerceivedArea;
	private static final int baseDamage = 5;

	private static final float staticSpeed = Rpg.getDp() * 8;



	public Arrow()	{}

	public Arrow(LivingThing shooter, vector unitVectorInDirection)
	{
		super( shooter );
		setUnit( unitVectorInDirection );
		setAttributes( shooter , getUnit() );
	}

	public Arrow( LivingThing caster , LivingThing target )
	{
		this( caster , null , target );
	}


	private Arrow(LivingThing shooter, vector to, LivingThing target)
	{
		super(shooter);
		setShooter(shooter);

		vector unit = null;

		if( to != null )
		{
			unit = new vector( shooter.loc , to );
			unit.turnIntoUnitVector();

			//setUnit( Vector.vectorBetween( shooter.loc , to ).getUnitVector());
		}
		else if( target != null )
		{
			unit = new vector( shooter.loc , target.loc );
			unit.turnIntoUnitVector();
			//setUnit( Vector.vectorBetween( shooter.loc , target.loc ).getUnitVector() );
		}
		if( unit == null )
			unit = new vector();


		setUnit( unit );

		setAttributes( shooter ,unit );
	}

	@Override
	public boolean create(MM mm) {
		if(super.create(mm)){
			SpecialEffects.playBowSound(loc.x, loc.y);
			return true;
		}
		return false;
	}

	private void setAttributes( LivingThing shooter ,@NotNull vector unit )	{
		if( shooter != null )
		{
			vector loc = new vector();
			loc.set( shooter.loc );
			setLoc( loc );
			setStartLoc( shooter.loc );
			setDamage( shooter.getAQ().getDamage() + getDamageBonus() );
		}


		setDir( vector.getDirection8(unit) );
		setImage( images.get( getDir().getDir() ) );

		vector velocity = new vector();
		velocity.set( unit );
		velocity.times( staticSpeed );


		velocity.add( Math.random()*2 - 1 , Math.random()*2 - 1 );

		//K so the velocity is set first, then the range is set, when the range is set( see method ) it calculates out how long it will be in the air, then the shooters
		// velocity is added to the proj's velocity for Galilian Trans~ Eqn satisfaction?


		if( shooter != null && shooter.getVelocity() != null )
		{
			vector shootersVelocity = shooter.getVelocity();

			boolean SVXPos = shootersVelocity.x > 0;
			boolean PVXPos = velocity.x > 0;

			if( ( SVXPos && PVXPos ) || ( !SVXPos && !PVXPos ) )			 //if both the shooter velocity and the proj' x velocities are both positive or both negative
			{
				boolean SVYPos = shootersVelocity.y > 0;
				boolean PVYPos = velocity.y > 0;
				if( ( SVYPos && PVYPos ) || ( !SVYPos && !PVYPos ) )			 //if both the shooter velocity and the proj' y velocities are both positive or both negative
				{
					velocity.add( shootersVelocity );
				}
			}
		}

		setVelocity( velocity );

		int flightTime = (int) ( shooter.getAQ().getAttackRange() / staticSpeed );

		calculateFlight( flightTime );


		//setVelocity( new Vector(unit).times(staticSpeed) );


		updateArea();
	}


	@Override
	public String getName()
	{
		return TAG;
	}

	public List<Image> getProjImages(){
		return images;
	}


	@Override
	public Projectile newInstance(@NotNull LivingThing shooter ,@NotNull vector predLoc ,@NotNull LivingThing target )	{
		return new Arrow( shooter , predLoc , target );
	}


	@Override
	public Projectile newInstance(@NotNull LivingThing shooter, @NotNull vector unitVectorInDirection)	{
		return new Arrow(shooter,unitVectorInDirection);
	}




	@Override
	public Image getDeadImage()	{
		if( deadImages == null )
			deadImages= Assets.loadAnimationImages(R.drawable.projectiles, 8, 4, 1, 8);

		return deadImages.get(getDir().getDir());
	}


	@Override
	public void loadAnimation(@NotNull MM mm )	{
		cd = mm.getCD();
		if( mm.getSdac().stillDraw(loc) ){
			setProjAnim(new ProjectileAnim(this));
			mm.getEm().add(getProjAnim(), true);
		}
	}


	@Override
	public ArrayList<Image> getDeadImages()	{
		if( deadImages == null )
			deadImages= Assets.loadAnimationImages( R.drawable.projectiles , 8 , 4 , 1 , 8 );

		return deadImages;
	}


	@Override
	public Projectile newInstance(){
		return new Arrow();
	}

	@Override
	public float getStaticRangeSquared(){
		return staticSpeed;
	}

	@Override
	public int getStaticDamage(){
		return baseDamage;
	}

	@Override
	public float getStaticSpeed(){
		return staticSpeed;
	}







	@Override
	public RectF getStaticPerceivedArea()	{
		loadStaticPerceivedArea();
		return staticPerceivedArea;
	}

	@Override
	public RectF getPerceivedArea()	{
		loadStaticPerceivedArea();
		return staticPerceivedArea;
	}

	void loadStaticPerceivedArea()	{
		if( staticPerceivedArea == null )
		{
			float staticSizeDiv2 = Rpg.getDp()*1;
			staticPerceivedArea = new RectF( -staticSizeDiv2, -staticSizeDiv2 , staticSizeDiv2 , staticSizeDiv2 );
		}
	}

	@Override
	public void setStaticPerceivedArea( RectF staticPercArea2 )	{
		staticPerceivedArea=staticPercArea2;
	}




}
