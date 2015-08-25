package com.kingscastle.nuzi.towerdefence.gameElements.spells;


import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

public class SpellInstanceCreator {




	public static Spell getSpellInstance( SpellCreationParams params)
	{
		Spell s;

		//Getting an instance of the spell.
		s = params.getSpellToBeCopied().newInstance();


		//Finding its caster.
		if( params.getShooter() != null )
			s.setCaster( params.getShooter() );


		//Finding its location.
		if( params.getLocation() != null )
			s.setLoc( params.getLocation() );

		else if( params.getShooter() != null )
			s.setLoc( new vector( params.getShooter().loc ) );

		else if( params.getTarget() != null )
			s.setLoc( new vector( params.getTarget().loc ) );

		else if( params.getDestination() != null )
			s.setLoc(new vector(params.getDestination()));


		//Finding its target.
		if( params.getTarget() != null )
			s.setTarget(params.getTarget());


		if( s instanceof ProjectileSpell )
		{
			ProjectileSpell ps = (ProjectileSpell) s;

			if( params.getUnitVectorInDirection() == null ) //determine its units vector
			{
				if ( params.getVelocity() != null )
					params.setUnitVectorInDirection( params.getVelocity().getUnitVector() );

				else if( params.getDestination() != null)
				{
					vector unit = new vector(ps.loc,params.getDestination());

					unit= unit.getUnitVector();
					params.setUnitVectorInDirection(unit); // Used to load the animation on some spells
				}
			}
			if( params.getUnitVectorInDirection() != null )
			{
				if( ps.getVelocity() == null )
				{
					float speed = params.getSpeed();

					if( speed == 0 )
						speed = ps.getStaticSpeed();

					vector velocity = new vector( params.getUnitVectorInDirection() ).times(speed);
					params.setVelocity( velocity );
					//ps.setVelocity(velocity);
				}
			}

			if( params.getUnitVectorInDirection() != null )
				ps.loadAnimation(params.getUnitVectorInDirection());


			if( params.getRangeSquared() != 0 )
				ps.setRangeSquared( params.getRangeSquared() );

			else
				ps.setRangeSquared( ps.getStaticRangeSquared()) ;


			if( ps.loc != null )
				ps.setStartLoc( s.loc );



			vector velocity = ps.getVelocity();
			if( velocity == null )
				velocity = params.getVelocity();

			LivingThing shooter = ps.getCaster();

			if( velocity != null )
			{
				velocity.add( Math.random()*2 - 1 , Math.random()*2 - 1 );

				int flightTime = 1000;
				if( shooter != null  )
				{
					vector shootersVelocity = shooter.getVelocity();

					if( shootersVelocity != null ){
						boolean SVXPos = shootersVelocity.x > 0;
						boolean PVXPos = velocity.x > 0;

						if( ( SVXPos && PVXPos ) || ( !SVXPos && !PVXPos ) )		 //if both the shooter velocity and the proj' x velocities are both positive or both negative
						{
							boolean SVYPos = shootersVelocity.y > 0;
							boolean PVYPos = velocity.y > 0;
							if( ( SVYPos && PVYPos ) || ( !SVYPos && !PVYPos ) )	 //if both the shooter velocity and the proj' y velocities are both positive or both negative
							{
								velocity.add( shootersVelocity );
							}
						}
					}
					flightTime = (int) ( shooter.getAQ().getAttackRange() / ps.getStaticSpeed() );
				}
				else
				{
					flightTime = (int) ( params.getRange() / params.getSpeed() );
				}
				ps.setVelocity( velocity );
				ps.calculateFlight( flightTime );
			}

		}



		if( params.getTeam() != null )
		{
			s.setTeam( params.getTeam() );
		}

		s.loadAnimation();
		s.updateArea();

		return s;
	}


}
