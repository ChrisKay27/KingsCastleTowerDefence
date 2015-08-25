package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes;


import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingQualities;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.TargetingParams;
import com.kingscastle.nuzi.towerdefence.gameElements.movement.pathing.Path;
import com.kingscastle.nuzi.towerdefence.gameElements.targeting.TargetFinder;
import com.kingscastle.nuzi.towerdefence.gameElements.targeting.TargetFinder.CondRespon;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.teams.Teams;

public abstract class Healer extends MageSoldier
{

	private LivingThing healingTarget;



	public Healer() {

	}
	public Healer(Teams team) {
		super(team);
	}


	@Override
	public boolean act()
	{
		if( getLQ().getHealth() <= 0 )
		{
			die();
			return true;
		}
		else
		{
			if( getLQ().getHealthPercent() < 0.6 )
				healingTarget = this;


			if( healingTarget == null )
				findATarget();


			if( isOutOfRangeOrDeadORFullHealth( this , healingTarget ) )
				healingTarget = null;

			LivingThing currTarget = getTarget();
			if( currTarget == null )
			{
				LivingThing lastHurter = this.lastHurter;
				if( lastHurter != null )
				{
					if( lastHurter.isDead() )
						lastHurter = null;
					else if( canAttack() )
						setTarget(lastHurter);
				}
			}
			else
			{
				if( isOutOfRangeOrDead( this , currTarget ) )
					setTarget(null);
			}

			legsAct();

			if( healingTarget != null )
			{
				float distSquared = 1;
				if( healingTarget != this )
					distSquared = loc.distanceSquared( healingTarget.loc ) ;

				boolean armsActed = getArms().act( distSquared , healingTarget );
//
//				if( armsActed && getPathToFollow() != null )
//					setPathToFollow(null);
			}

			regen();
		}

		return dead;
	}


	protected boolean canAttack() {
		return false;
	}




	@Override
	protected boolean legsActWithRespectToTarget()
	{
		if( healingTarget != null )
			if( loc.distanceSquared( healingTarget.loc ) > getAQ().getStaysAtDistanceSquared() )
			{
				legs.moveTowards( healingTarget.loc );
				return true;
			}

		return super.legsActWithRespectToTarget();
	}

	@Override
	protected void clearTarget() {
		setTarget( null );
		if( healingTarget != this )
			healingTarget = null;
	}


	private boolean isOutOfRangeOrDeadORFullHealth( Healer healer ,
			LivingThing healingTarget2 )
	{
		if( healer == null || healingTarget2 == null )
			return true;

		LivingQualities lq = healingTarget2.lq;
		if( healer == healingTarget2 )
			return lq.getHealth() == lq.getFullHealth();

		if ( isOutOfRangeOrDead ( healer, healingTarget2 ))
			return true;
		else
		{
			if( lq.getHealth() == lq.getFullHealth())
				return true;
		}

		return false;
	}


	private TargetingParams params;

	private void createTargetingParams()
	{
		if( params == null )
		{
			params = new TargetingParams()
			{
				vector mLoc = new vector();
				vector tLoc = new vector();

				@Override
				public CondRespon postRangeCheckCondition( LivingThing target )
				{
					LivingQualities lq = target.lq;

					if( lq.getHealth() == lq.getFullHealth() )
						return CondRespon.FALSE;
					return CondRespon.TRUE;
//
//					try {
//						tLoc.set(target.loc);
//						mLoc.set(loc);
//
//						Vector closestTargetLoc = gUtil.getNearestWalkableLocNextToThis(target.area, tLoc );
//						Path p = PathFinder.heyINeedAPath( gUtil.getGrid(), mLoc, closestTargetLoc, 1000);
//						if( p != null ){
//							setPathToFollow(p);
//							return CondRespon.RETURN_THIS_NOW;
//						}
//						else
//							return CondRespon.FALSE;
//					} catch( Exception e ) {
//						//Log.e(TAG, "Could not find path to target.");
//						return CondRespon.FALSE;
//					}
				}
			};

			params.setTeamOfInterest( getTeamName() );
			params.setOnThisTeam( true );
			params.setWithinRangeSquared( getAQ().getFocusRangeSquared() );
			params.setFromThisLoc ( loc );
			params.setLookAtBuildings( false );
			params.setLookAtSoldiers( true );
		}
	}

	@Override
	public void findATarget()
	{
		if( startTargetingAgainAt < GameTime.getTime() )
		{
			createTargetingParams();
			TargetFinder tf = this.targetFinder;
			if( tf != null ){
				tf.findTargetAsync(params, this);
			}

			startTargetingAgainAt = GameTime.getTime() + 2000;
		}
	}




	@Override
	public void setTarget(LivingThing nTarget) {
		if( nTarget != null && nTarget.getTeamName() == getTeamName() )
			setHealingTarget(nTarget);
		else if( canAttack() )
			super.setTarget(nTarget);
	}

	public LivingThing getHealingTarget() {
		return healingTarget;
	}

	public void setHealingTarget(LivingThing healingTarget) {
		this.healingTarget = healingTarget;
	}

	@Override
	protected boolean armsAct()
	{
		boolean armsActed = super.armsAct();
		if( armsActed ){
			Path path = getPathToFollow();
			if( path != null && !path.humanOrdered() ){
				setPathToFollow(null);
			}
		}
		return armsActed;
	}




}
