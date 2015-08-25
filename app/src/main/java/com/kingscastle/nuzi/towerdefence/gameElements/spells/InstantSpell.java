package com.kingscastle.nuzi.towerdefence.gameElements.spells;

import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.framework.Input;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingQualities;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;






public abstract class InstantSpell extends Spell {


	private long lastRefreshed = GameTime.getTime() + 999999,  //safe value
			startTime;
	private int refreshEvery = 9999, aliveTime = 0 ;





	@Override
	public boolean act()
	{
		////Log.d( "InstantSpell" , "act() , getLastRefreshed() =" + getLastRefreshed() + " , getRefreshEvery() = " + getRefreshEvery() );

		if( getLastRefreshed() + getRefreshEvery() < GameTime.getTime() )
		{
			refresh();
			setLastRefreshed(GameTime.getTime());
		}

		if( getAliveTime() == 0 )
		{
			if( getAnim() != null && getAnim().isOver() )
			{
				die();
				return true;
			}
		}
		else if( getStartTime() + getAliveTime() < GameTime.getTime() )
		{
			////Log.d( "InstantSpell" , "getStartTime() + getAliveTime() < GameTime.getTime()");
			die();
			return true;
		}

		return isDead();
	}



	void refresh() {

	}


	@Override
	public int calculateDamage()
	{
		if( getCaster() != null )
		{
			LivingQualities lq = getCaster().getLQ();

			return (int) (( getDamage() + (int) ( Math.random()*10 ) ) * lq.getBonuses().getDamageBonus() );
		}

		return 0;
	}


	void doDamage( ArrayList<LivingThing> lts)
	{
		if( lts == null )
			return;


		for( LivingThing lt : lts )
			if( lt != null )
				lt.takeDamage( getDamage() , getCaster() );

	}




//	@Override
//	public boolean isDead()
//	{
//		if(getAnim() == null) {
//			return true;
//		}
//
//		return getAnim().isOver();
//	}





	@Override
	public void saveYourSelf( BufferedWriter b) throws IOException
	{
		String s = "<"+this+" team=\""+ getTeamName() + "\" damage=\"" + getDamage() +
				"\" x=\"" + loc.getIntX() + "\" y=\"" + loc.getIntY() + "\"/>";

		b.write(s,0,s.length());
		b.newLine();
	}





	/**
	 * @return the lastRefreshed
	 */
	long getLastRefreshed() {
		return lastRefreshed;
	}

	/**
	 * @param lastRefreshed the lastRefreshed to set
	 */
	void setLastRefreshed(long lastRefreshed) {
		this.lastRefreshed = lastRefreshed;
	}

	/**
	 * @return the startTime
	 */
	long getStartTime() {
		return startTime;
	}

	/**
	 * @return the refreshEvery
	 */
	int getRefreshEvery() {
		return refreshEvery;
	}

	/**
	 * @return the aliveTime
	 */
	int getAliveTime() {
		return aliveTime;
	}





	/**
	 * @param startTime the startTime to set
	 */
	void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	/**
	 * @param refreshEvery the refreshEvery to set
	 */
	void setRefreshEvery(int refreshEvery) {
		this.refreshEvery = refreshEvery;
	}

	/**
	 * @param aliveTime the aliveTime to set
	 */
	void setAliveTime(int aliveTime) {
		this.aliveTime = aliveTime;
	}

	@Override
	public boolean analyseTouchEvent(Input.TouchEvent event) {
		return false;
	}


	@Override
	public boolean cast(MM mm, LivingThing target) {
		return false;
	}


    @Override
    public void undoAbility() {

    }
}
