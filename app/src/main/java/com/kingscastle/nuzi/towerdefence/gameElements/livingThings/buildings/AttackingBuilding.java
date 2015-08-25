package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings;


import android.support.annotation.Nullable;

import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.TargetingParams;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.teams.Teams;

public abstract class AttackingBuilding extends Building
{
	private LivingThing target;
	private vector atkInDirVector;

	protected AttackingBuilding(){}
	protected AttackingBuilding(Buildings buildingsName , Teams team )	{
		super( buildingsName , team );
	}

	@Override
	public boolean create(MM mm) {
		boolean superCreate = super.create(mm);
		setupAttack();
		return superCreate;
	}


	@Override
	public boolean act()
	{
		if( isDead() )
			return true;

		if ( getLQ().getHealth() < 1 ){
			die();
			return true;
		}

		if( !isStunned() ) {
			LivingThing currTarget = getTarget();
			if (currTarget == null)
				findATarget();

			armsAct();

			currTarget = getTarget();
			if (currTarget != null) {
				if (isOutOfRangeOrDead(this, currTarget))
					setTarget(null);
			}
		}
		return isDead();
	}

	protected boolean armsAct()	{
		vector dir = atkInDirVector;
		if( dir != null )
			return getArms().actFromUnitVector(dir);
		return getArms().act();
	}

	@Override
	public void setTarget(@Nullable LivingThing nTarget) {

		target = nTarget;

		synchronized (tsls){
			for(OnTargetSetListener tsl : tsls)
				tsl.onTargetSet(this, nTarget);
		}
	}

	@Nullable
	@Override
	public LivingThing getTarget() {
		return target;
	}

	protected void setupTargetingParams()
	{
		if( targetingParams == null )
		{
			targetingParams = new TargetingParams();
			targetingParams.setTeamOfInterest(team);
			targetingParams.setWithinRangeSquared(getAQ().getFocusRangeSquared());
			targetingParams.setFromThisLoc(loc);
			targetingParams.lookAtBuildings = false;
		}
	}

	/** Override this if you need to */
	protected abstract void setupAttack();

	public void setAttackInDirectionVector(vector dir) {
		atkInDirVector = dir;
	}
}
