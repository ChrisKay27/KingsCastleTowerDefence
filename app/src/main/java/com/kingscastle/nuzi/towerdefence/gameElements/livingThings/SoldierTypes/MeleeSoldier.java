package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes;


import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks.MeleeAnimator;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks.MeleeAttack;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameElements.movement.pathing.Path;
import com.kingscastle.nuzi.towerdefence.teams.Teams;

public abstract class MeleeSoldier extends Unit {

	public MeleeSoldier() {
	}

	public MeleeSoldier(Teams team) {
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
		getAQ().setCurrentAttack(new MeleeAttack(mm, this, MeleeAnimator.MeleeTypes.LongSword, mm.getCD()));
		aliveAnim.add(getAQ().getCurrentAttack().getAnimator(), true);
	}

}
