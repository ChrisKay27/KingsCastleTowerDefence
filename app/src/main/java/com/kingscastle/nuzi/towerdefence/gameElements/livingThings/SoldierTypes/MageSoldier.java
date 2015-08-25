package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes;

import android.support.annotation.Nullable;

import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.TargetingParams;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.abilities.AbilityManager;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.abilities.Buff;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks.Attack;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks.BuffAttack;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameElements.movement.pathing.Path;
import com.kingscastle.nuzi.towerdefence.gameElements.targeting.TargetFinder;
import com.kingscastle.nuzi.towerdefence.gameElements.targeting.TargetFinder.CondRespon;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.teams.Teams;


public abstract class MageSoldier extends Unit{
	protected static final String TAG = "MageSoldier";

	private LivingThing friendlyTarget;
	protected Buff buffSpell;

	private long lastLookedForFriendlyTarget;
	private long checkFriendlyTargetsSituationTime;
	private long timeToBuffFriendly;


	public MageSoldier() {
	}

	public MageSoldier(Teams team) {
		super(team);
	}


	@Override
	public boolean create(MM mm) {
		boolean superCreate =  super.create(mm);
		setupSpells();
		return superCreate;
	}

	/** Override if needed */
	protected abstract void setupSpells();


	@Override
	protected void upgrade(){
		super.upgrade();
		setupSpells();
	}



	@Override
	public boolean act()
	{
		if ( lq.getHealth() <= 0 )
			die();
		else
		{
			regen();

			LivingThing currTarget = getTarget();
			if( shouldIForgetAboutThisTarget( currTarget ) )
				setTarget(null);

			currTarget = getTarget();
			if( currTarget == null )
			{
				findATarget(false);

				//				currTarget = getTarget();
				//				if( currTarget == null )
				//					if( getSquad() != null )
				//						if( this == getSquad().getLeader() )
				//							getSquad().moveTheTroopsIntoFormation();

			}


			if( getAQ().getFriendlyAttack() != null ){
				if( checkFriendlyTargetsSituationTime < GameTime.getTime() )
				{
					if( shouldIForgetAboutThisTarget( friendlyTarget ) )
						friendlyTarget = null;


					if( friendlyTarget == null )
						findATarget( true );

					checkFriendlyTargetsSituationTime = GameTime.getTime() + 1000 ;
				}
			}

			legsAct();



			checkBeingStupid();






			if( arms.timeToAttack() )
			{
				//Log.d( TAG ,"Its time to attack");
				currTarget = getTarget();
				if( currTarget != null )
				{
					//Log.d( TAG , " Trying to attack target" );
					float targetDistSquared = legs.getTargetDistSquared();

					if( targetDistSquared == 0 )
					{
						//Log.d( TAG , "getArms().act( target.loc.distanceSquared( loc ) , target , getAQ().getCurrentAttack()  )" );
						boolean armsActed = getArms().act( currTarget.loc.distanceSquared( loc ) , currTarget , getAQ().getCurrentAttack()  );

						if( armsActed && getPathToFollow() != null )
							setPathToFollow(null);

					}
					else
					{
						//Log.d( TAG , "getArms().act( targetDistSquared , target , getAQ().getCurrentAttack() )" );
						boolean armsActed = getArms().act( targetDistSquared , currTarget , getAQ().getCurrentAttack() );
						if( armsActed && getPathToFollow() != null )
							setPathToFollow(null);
					}

				}
			}


			if( friendlyTarget != null && getAQ().getFriendlyAttack() != null )
			{
				if( timeToBuffFriendly < GameTime.getTime() )
				{
					//Log.d( TAG ,"Trying to buff friendly: " + friendlyTarget );
					float targetDistSquared = 1;
					if( friendlyTarget != this )
						targetDistSquared = loc.distanceSquared( friendlyTarget.area );

					if( targetDistSquared < getAQ().getAttackRange() )
						getAQ().getFriendlyAttack().attack( friendlyTarget );

					getArms().act( targetDistSquared , friendlyTarget , getAQ().getFriendlyAttack() );

					timeToBuffFriendly =  GameTime.getTime() + getAQ().getROF() ;
				}
			}

		}

		return isDead();
	}




	protected TargetingParams friendlyParams;


	LivingThing findAFriendlyTarget()
	{

		if( lastLookedForFriendlyTarget + waitLength < GameTime.getTime() )
		{
			//Log.d( TAG ," findAFriendlyTarget() act");
			LivingThing newTarget=null;

			createFriendlyTargetingParams();

			TargetFinder tf = this.targetFinder;
			if( tf != null )
				tf.findTargetAsync( friendlyParams , this );


			lastLookedForFriendlyTarget = GameTime.getTime();

			//Log.d( TAG ,"found a target: " + target );
			return newTarget;
		}

		//Log.d( TAG ,"did not find a target: ");
		return null;

	}



	protected void createFriendlyTargetingParams()
	{

		if( friendlyParams == null )
		{
			final AbilityManager abm = getMM().getTM().getTeam( getTeamName() ).getAbm();
			//final LivingThing thisWizardBuffer = this;

			friendlyParams = new TargetingParams()
			{
				//Vector mLoc = new Vector();
				//Vector tLoc = new Vector();
				@Override
				public CondRespon postRangeCheckCondition( LivingThing target )
				{

					if( !buffSpell.canCastOn(target))
						return CondRespon.FALSE;

					if( target == MageSoldier.this )
						return CondRespon.FALSE;

					if( loc.distanceSquared(target.loc) < aq.getAttackRangeSquared() )
						return CondRespon.RETURN_THIS_NOW;
					return CondRespon.FALSE;
				}

				//				@Override
				//				public CondRespon wouldWorkButSecondChoice( LivingThing lt ) {
				//					if( lt == thisWizardBuffer )
				//					{
				//						return CondRespon.TRUE;
				//					}
				//					else
				//					{
				//						return CondRespon.DEFAULT;
				//					}
				//				}
			};

			friendlyParams.setTeamOfInterest( getTeamName() );
			friendlyParams.setOnThisTeam( true );
			friendlyParams.setWithinRangeSquared(getAQ().getFocusRangeSquared());
			friendlyParams.setFromThisLoc ( loc );
			friendlyParams.setLookAtBuildings( false );
			friendlyParams.setLookAtSoldiers( true );
		}
	}



	private TargetingParams enemyParams;

	void findATarget(boolean friendly)
	{
		if( friendly )
			findAFriendlyTarget();


		if( startTargetingAgainAt < GameTime.getTime() )
		{
			////////Log.d( TAG ," findATarget() act");

			createEnemyTargetingParams();
			TargetFinder tf = this.targetFinder;
			if( tf != null )
				tf.findTargetAsync( enemyParams , this );
			//target = tf.findTarget( enemyParams );

			startTargetingAgainAt = GameTime.getTime() + 2000;

			////////Log.d( TAG ,"found a target: " + target );
		}

	}


	private void createEnemyTargetingParams()
	{

		if( enemyParams == null )
		{
			final Teams teamName = getTeamName();
			final float myAttackRangeSquared = getAQ().getAttackRangeSquared();
			enemyParams = new TargetingParams()
			{
				vector mLoc = new vector();
				vector tLoc = new vector();
				@Override
				public CondRespon postRangeCheckCondition( LivingThing target )
				{
					if( target.getAttacker() != null )
						return TargetFinder.CondRespon.FALSE;
					return TargetFinder.CondRespon.TRUE;

//					if( loc.distanceSquared(target.loc) < aq.getAttackRangeSquared() )
//						return CondRespon.RETURN_THIS_NOW;
//					try {
//						tLoc.set(target.loc);
//						mLoc.set(loc);
//
//						Vector closestTargetLoc = gUtil.getNearestWalkableLocNextToThis(target.area, tLoc);
//						Path p = PathFinder.heyINeedAPath( gUtil.getGrid() , mLoc , closestTargetLoc, 1000);
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
					//
					//					if( target.getTarget() != null && target.getTarget().getTeamName() == teamName )
					//						return CondRespon.RETURN_THIS_NOW;
					//
					//					return CondRespon.DEFAULT;
				}

				@Override
				public CondRespon wouldWorkButSecondChoice(LivingThing lt)
				{
					if( lt.getAQ() == null )
						return CondRespon.TRUE;

					else if( lt.getAQ().getAttackRangeSquared() > myAttackRangeSquared )
						return CondRespon.TRUE;

					else
						return CondRespon.DEFAULT;
				}
			};
			enemyParams.lookAtBuildingsFirst = true;
			enemyParams.soldierPriority = true;
			enemyParams.setTeamOfInterest( teamName );
			enemyParams.setWithinRangeSquared( getAQ().getFocusRangeSquared() );
			enemyParams.setFromThisLoc ( loc );
		}
	}

	@Override
	public void setTarget(LivingThing nTarget) {
		if( nTarget != null && nTarget.getTeamName() == getTeamName() )
			friendlyTarget = nTarget;
		else
			super.setTarget(nTarget);
	}

	boolean shouldIForgetAboutThisTarget(LivingThing target)
	{
		if( target == null )
			return false;

		else if( target == highThreadTarget && !target.isDead() )
			return false;

		else if( isOutOfRangeOrDead( this , target ) )
			return true;

		else if( target.getTeamName() == getTeamName() )
		{
			boolean canCastOnTarget = buffSpell.canCastOn(target);
			//Log.d( TAG ,"Forgetting about target, can I cast on my target :" + canCastOnTarget);
			return !canCastOnTarget;
		}
		else
			return false;

	}


	protected String buffMessage = "";


	public String getAbilityMessage() {
		return buffMessage;
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

	public void setFriendlyTarget(LivingThing friendlyTarget) {
		this.friendlyTarget = friendlyTarget;
	}

	public LivingThing getFriendlyTarget() {
		return friendlyTarget;
	}


	public void setFriendlyAttack(@Nullable Attack atk){
		getAQ().setFriendlyAttack(atk);
		if( atk instanceof BuffAttack)
			buffSpell = ((BuffAttack)atk).getSpell();
	}
}
