package com.kingscastle.nuzi.towerdefence.gameElements.livingThings;


import com.kingscastle.nuzi.towerdefence.gameElements.targeting.TargetFinder.CondRespon;
import com.kingscastle.nuzi.towerdefence.gameUtils.NotAllowedInTowerDefenceException;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.teams.Teams;

public class TargetingParams
{
	public enum SoldierType
	{
		ANY , RANGED , MELEE , HEALER , MAGE , SUMMONER
	}

	public LivingThing[] possibleTargets = new LivingThing[500];


	public Teams team;
	public boolean onThisTeam = false;
	private float withMaxSpeedOf = 50;

	private SoldierType preferableType;
	private SoldierType secondChoiceType;
	private SoldierType definatelyNotType;

	public float withinRangeSquared;
	private float preferablyWithHealthPercentBelow = 0.5f ;
	private float willAcceptOneWithHealthPercentBelow = 1.0f;
	private float withHealthPercentAbove;
	public vector fromThisPoint;
	public boolean lookAtBuildings = false;
	public boolean lookAtBuildingsFirst = false;
	public boolean lookAtSoldiers = true;

	public boolean soldierPriority = true;


	public TargetingParams()
	{
	}

	public TargetingParams( TargetingParams params )
	{
		team = params.team;
		withMaxSpeedOf = params.withMaxSpeedOf;
		preferableType = params.preferableType;
		secondChoiceType = params.secondChoiceType;
		definatelyNotType = params.definatelyNotType;
		withinRangeSquared = params.withinRangeSquared;
		preferablyWithHealthPercentBelow = params.preferablyWithHealthPercentBelow;
		willAcceptOneWithHealthPercentBelow = params.willAcceptOneWithHealthPercentBelow;
		withHealthPercentAbove = params.withHealthPercentAbove;
		fromThisPoint = params.fromThisPoint;
		onThisTeam = params.onThisTeam;
	}

	public static TargetingParams newInstance()
	{
		return new TargetingParams();
		//		if ( notBeingUsedParams == null )
		//		{
		//			notBeingUsedParams = new ArrayList<TargetingParams>();
		//		}
		//
		//		if ( notBeingUsedParams.size() == 0 )
		//		{
		//			addTargetingParams( notBeingUsedParams , 40 );
		//		}
		//
		//		return notBeingUsedParams.remove(notBeingUsedParams.size()-1);
		//
	}


	public void deconstruct()
	{
		//		team = null;
		//		withMaxSpeedOf = 50;
		//		preferableType = null;
		//		secondChoiceType = null;
		//		definatelyNotType = null;
		//		withinRangeSquared = 0;
		//		preferablyWithHealthPercentBelow  = 0.5f;
		//		willAcceptOneWithHealthPercentBelow = 1.0f;
		//		withHealthPercentAbove = 0;
		//		fromThisPoint = null;
		//		notBeingUsedParams.add(this);
	}



	/**
	 * @return the team
	 */
	public Teams getTeam() {
		return team;
	}
	/**
	 * @param team2 the Teams to set
	 */
	public void setTeamOfInterest(Teams team2)
	{
		this.team = team2;
	}
	/**
	 * @return the withMaxSpeedOf
	 */
	public float getWithMaxSpeedOf() {
		return withMaxSpeedOf;
	}
	/**
	 * @param withMaxSpeedOf the withMaxSpeedOf to set
	 */
	public void setWithMaxSpeedOf(float withMaxSpeedOf) {
		this.withMaxSpeedOf = withMaxSpeedOf;
	}
	/**
	 * @return the preferableType
	 */
	public SoldierType getPreferableType() {
		return preferableType;
	}
	/**
	 * @param preferableType the preferableType to set
	 */
	public void setPreferableType(SoldierType preferableType) {
		this.preferableType = preferableType;
	}
	/**
	 * @return the secondChoiceType
	 */
	public SoldierType getSecondChoiceType() {
		return secondChoiceType;
	}
	/**
	 * @param secondChoiceType the secondChoiceType to set
	 */
	public void setSecondChoiceType(SoldierType secondChoiceType) {
		this.secondChoiceType = secondChoiceType;
	}
	/**
	 * @return the definatelyNotType
	 */
	public SoldierType getDefinatelyNotType() {
		return definatelyNotType;
	}
	/**
	 * @param definatelyNotType the definatelyNotType to set
	 */
	public void setDefinatelyNotType(SoldierType definatelyNotType) {
		this.definatelyNotType = definatelyNotType;
	}
	/**
	 * @return the withinRangeSquared
	 */
	public float getWithinRangeSquared() {
		return withinRangeSquared;
	}
	/**
	 * @param f the withinRangeSquared to set
	 */
	public void setWithinRangeSquared(float f) {
		this.withinRangeSquared = f;
	}
	/**
	 * @return the ofThisPoint
	 */
	public vector getFromThisPoint() {
		return fromThisPoint;
	}
	/**
	 * @param fromThisPoint2 the ofThisPoint to set
	 */
	public void setFromThisLoc(vector fromThisPoint2) {
		this.fromThisPoint = fromThisPoint2;
	}

	/**
	 * @return the withHealthPercentAbove
	 */
	public float getWithHealthPercentAbove() {
		return withHealthPercentAbove;
	}

	/**
	 * @param withHealthPercentAbove the withHealthPercentAbove to set
	 */
	public void setWithHealthPercentAbove(float withHealthPercentAbove) {
		this.withHealthPercentAbove = withHealthPercentAbove;
	}

	/**
	 * @return the preferablyWithHealthPercentBelow
	 */
	public float getHealthPercentBelow() {
		return preferablyWithHealthPercentBelow;
	}

	/**
	 * @param preferablyWithHealthPercentBelow the preferablyWithHealthPercentBelow to set
	 */
	public void setHealthPercentBelow(
			float preferablyWithHealthPercentBelow) {
		this.preferablyWithHealthPercentBelow = preferablyWithHealthPercentBelow;
	}

	/**
	 * @return the willAcceptOneWithHealthPercentBelow
	 */
	public float getHealthPercentBelow2() {
		return willAcceptOneWithHealthPercentBelow;
	}

	/**
	 * @param willAcceptOneWithHealthPercentBelow the willAcceptOneWithHealthPercentBelow to set
	 */
	public void setHealthPercentBelow2(
			int willAcceptOneWithHealthPercentBelow) {
		this.willAcceptOneWithHealthPercentBelow = willAcceptOneWithHealthPercentBelow;
	}

	public boolean checkOnThisTeam() {
		return onThisTeam;
	}

	public void setOnThisTeam(boolean onThisTeam) {
		this.onThisTeam = onThisTeam;
	}


	/**
	 * 
//	 * @param target thing to look at to see if its a good target.
//	 * @return TRUE:Continue With Checks.    FALSE:skip rest of checks, not a good target.    RETURN_THIS_NOW: this target will be returned as the target found.
//	 */
//	public CondRespon preRangeCheckCondition( LivingThing target )
//	{
//		return CondRespon.DEFAULT;
//	}
	/**
	 * 
	 * @param target thing to look at to see if its a good target.
	 * @return TRUE:Continue With Checks.    FALSE:skip rest of checks, not a good target.    RETURN_THIS_NOW: this target will be returned as the target found.
	 */
	public CondRespon postRangeCheckCondition( LivingThing target ){
		return CondRespon.DEFAULT;
	}

	/**
	 * 
	 * @param lt thing to look at to see if its a good target.
	 * @return TRUE:Saved in a list to be looked at after everything else is looked at.    FALSE: Doesnt do anything with this check.
	 * RETURN_THIS_NOW: this target will be returned as the target found.
	 */
	public CondRespon wouldWorkButSecondChoice(LivingThing lt) {
		return CondRespon.DEFAULT;
	}

	public void setLookAtBuildings(boolean b) {
		if( b ) throw new NotAllowedInTowerDefenceException("Cannot attack buildings in tower defence mode!");
		lookAtBuildings = b;
	}

	public boolean lookAtSoldiers() {
		return lookAtSoldiers;
	}

	public void setLookAtSoldiers(boolean lookAtSoldiers) {
		this.lookAtSoldiers = lookAtSoldiers;
	}

	public boolean lookAtBuildings() {
		return lookAtBuildings;
	}


}
