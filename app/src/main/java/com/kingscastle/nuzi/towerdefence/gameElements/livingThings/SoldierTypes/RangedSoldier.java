package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes;


import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks.Bow;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameElements.movement.pathing.Path;
import com.kingscastle.nuzi.towerdefence.gameElements.projectiles.Arrow;
import com.kingscastle.nuzi.towerdefence.teams.Teams;

public abstract class RangedSoldier extends Unit {

	public RangedSoldier() {
	}

	public RangedSoldier(Teams team) {
		super(team);
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


	@Override
	public void loadAnimation(MM mm) {
		super.loadAnimation(mm);
		getAQ().setCurrentAttack( new Bow( mm, this , new Arrow() ) );
		aliveAnim.add(getAQ().getCurrentAttack().getAnimator(), true);
	}


}
