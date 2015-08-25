package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks;


import com.kingscastle.nuzi.towerdefence.effects.EffectsManager;
import com.kingscastle.nuzi.towerdefence.effects.animations.Anim;
import com.kingscastle.nuzi.towerdefence.effects.animations.BlackSummonSmokeAnim;
import com.kingscastle.nuzi.towerdefence.effects.animations.LightningStrikeAnim;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.Humanoid;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.level.rounds.AbstractRound;
import com.kingscastle.nuzi.towerdefence.teams.Teams;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SummonAttack extends Attack
{
	private static final String TAG = "SummonAttack";

	//private static Random rand = new Random();

	private final Humanoid creatureToSummon;

	private int maxSummons;

	private final Anim summonAnimation = new BlackSummonSmokeAnim( new vector() );

	private final List<Humanoid> summons = new LinkedList<>();


	public SummonAttack( MM mm, LivingThing owner , Humanoid summon , int maxSummons )
	{
		super( mm, owner );
		this.maxSummons = maxSummons;
		creatureToSummon = summon;
	}

	@Override
	public boolean attack(LivingThing target)
	{
		return false;
	}

	/**
	 * Only summons 1 summon max!
	 */
	@Override
	public void act() {
		Iterator<Humanoid> it = summons.iterator();
		while(it.hasNext())
			if( it.next().isDead() )
				it.remove();

		if( summons.size() < maxSummons )
		{
			vector at = new vector(owner.loc);

			int maxAttempts = 20;
			do {
				at = at.randomize((int) Rpg.thirtyDp);
				maxAttempts--;
			}while( !mm.getCD().checkPlaceable(at) && maxAttempts > 0);

			if( maxAttempts <= 0 )
				return;

			Humanoid toBeSummoned = null;
			Object[] args = {at, owner.getTeamName() };
			try {
				toBeSummoned = creatureToSummon.getClass().getConstructor(vector.class, Teams.class).newInstance(args);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//TODO Tower Defence fix
			if( toBeSummoned != null ) {
				toBeSummoned.getExtras().put(AbstractRound.START_END_LOC_INDEX, owner.getExtras().get(AbstractRound.START_END_LOC_INDEX));
				toBeSummoned.setCostsLives(0);
				//Round r = ((TowerDefenceLevel)mm.getLevel()).getRound();
				//if( r != null && r.addToMap(toBeSummoned,(Integer)owner.getExtras().get(AbstractRound.START_END_LOC_INDEX))) {
					//mm.getEm().add(new BlackSummonSmokeAnim( owner.loc ), EffectsManager.Position.InFront);
					vector v = new vector(toBeSummoned.loc);
					mm.getEm().add(new LightningStrikeAnim(v),EffectsManager.Position.InFront);
					mm.getEm().add(new BlackSummonSmokeAnim(v), EffectsManager.Position.InFront);
					summons.add(toBeSummoned);
				//}
				mm.add(toBeSummoned);
			}
		}
	}

	public void setMaxSummons(int maxSummons) {
		this.maxSummons = maxSummons;
	}
}
