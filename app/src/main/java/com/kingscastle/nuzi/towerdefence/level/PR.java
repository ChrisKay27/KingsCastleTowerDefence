package com.kingscastle.nuzi.towerdefence.level;

import com.kingscastle.nuzi.towerdefence.gameElements.Cost;
import com.kingscastle.nuzi.towerdefence.teams.RT;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PR
{
	private static final String TAG = "PR";

	private int maxGold = Integer.MAX_VALUE, maxFood= Integer.MAX_VALUE, maxWood= Integer.MAX_VALUE, popMax = Integer.MAX_VALUE ;
	private int gold , food , wood , md = 100 , popCurr;

	private String popString = "";

	public PR(){}

	public PR(int g , int f , int w , int m , int me)
	{
		gold=g;food=f;wood=w;md=me;
	}
	public PR( int g , int f , int w, int m ){
		gold=g;food=f;wood=w;md = m;
	}

	public PR(PR pr) {
		if( pr == null ){
			//Log.e(TAG ,  "PR(pr) && pr == null ");
			return;
		}
		gold = pr.gold;
		food = pr.food;
		wood = pr.wood;
		popCurr = pr.popCurr;
		md = pr.md;
		updatePopCostString();
	}

	public synchronized void set(PR pr) {
		if( pr == null ){
			//Log.e(TAG ,  "set(pr) && pr == null ");
			return;
		}

		gold = pr.gold;
		food = pr.food;
		wood = pr.wood;
		popCurr = pr.popCurr;
		maxGold = pr.maxGold;
		maxFood = pr.maxFood;
		maxWood = pr.maxWood;
		popMax = pr.popMax;
		updatePopCostString();
		md = pr.md;
		synchronized (rvcls) {
			for (OnResourceValuesChangedListener rvcl : rvcls)
				rvcl.onGoldValueChanged(gold);
		}
	}


	public synchronized int getGold()
	{
		return gold;
	}
	public synchronized void setGoldAvailable(int good)
	{
		gold = good;
		synchronized (rvcls) {
			for (OnResourceValuesChangedListener rvcl : rvcls)
				rvcl.onGoldValueChanged(gold);
		}
	}
	public synchronized int getFood()
	{
		return food;
	}
	public synchronized  void setFoodAvailable(int food)
	{
		this.food = food;
	}
	public synchronized  int getWood()
	{
		return wood;
	}
	public synchronized  void setWoodAvailable(int wood)
	{
		this.wood = wood;
	}
	public synchronized int getMetalAvailable()
	{
		return 0;
	}
	public synchronized void setMetalAvailable(int metal)
	{
	}
	public synchronized int getMD() {
		return md;
	}
	public synchronized void setMDAvailable(int magicEssense) {
		md = magicEssense;
	}
	public synchronized int getPopMax()
	{
		return popMax;
	}

	public synchronized void setPopMax( int popMax )
	{
		this.popMax = popMax;
		updatePopCostString();
	}

	public synchronized int getPopCurr() {
		return popCurr;
	}

	public synchronized void setPopCurr(int popCurr)
	{
		this.popCurr = popCurr;
		updatePopCostString();
	}

	public synchronized void incPopCurr( int popCurrInc )
	{
		popCurr += popCurrInc;
		updatePopCostString();
	}

	public synchronized boolean canAfford(Cost cost)
	{
		if( cost == null )
			return true;

//		if( wood < cost.getWoodCost() ) {
//			return false;
//		}
//		if( food < cost.getFoodCost() ) {
//			return false;
//		}
		if( gold < cost.getGoldCost() ) {
			return false;
		}
//		if( md < cost.getMagicDustCost() ) {
//			return false;
//		}
//		if( cost.getPopCost() != 0 )
//		{
//			if( popMax - popCurr < cost.getPopCost() ) {
//				return false;
//			}
//		}
		return true;
	}

	public synchronized boolean canAfford(RT type, int res) {
		if( type == null )
			return false;

		switch( type )
		{
		case GOLD 		: return gold >= res ;
		default:		  return false;
		}
	}
	public boolean canAffordIgnorePop(Cost cost) {
		if( cost == null ) return true;

		return wood >= cost.getWoodCost() && food >= cost.getFoodCost() && gold >= cost.getGoldCost() && md >= cost.getMagicDustCost();
	}
	public synchronized void refund( Cost costs )
	{
		if( costs == null ){
			//Log.e(TAG ,  "refund(Cost costs) && costs == null ");
			return;
		}

		add(RT.GOLD, costs.getGoldCost());
	}


	public synchronized boolean spend( Cost costs )
	{
		if( costs == null ){
			throw new IllegalArgumentException("Costs cannot be null");
		}

		//if( wood < costs.getWoodCost() || food < costs.getFoodCost() || gold < costs.getGoldCost() || md < costs.getMagicDustCost() || popCurr + costs.getPopCost() > popMax )
		if( gold < costs.getGoldCost() )
			return false;


		//wood -=costs.getWoodCost();
		//food -=costs.getFoodCost();
		gold -= costs.getGoldCost();
		//md   -=costs.getMagicDustCost();
		//popCurr += costs.getPopCost();
		//updatePopCostString();
		synchronized (rvcls) {
			for (OnResourceValuesChangedListener rvcl : rvcls)
				rvcl.onGoldValueChanged(gold);
		}
		return true;
	}




	public synchronized void saveYourSelf( BufferedWriter b ) throws IOException
	{
		String s = "<PersonalResources gold=\"" + gold + "\" food=\"" + food + "\" wood=\"" + wood + "\" magicEssenses=\"" + md +
				"\" popCurrent =\"" + popCurr + "\" popMax =\"" + popMax + "\" />";
		//System.out.println(s);
		b.write( s , 0 , s.length() );
		b.newLine();
	}




	public synchronized void add( int gold , int food , int wood )
	{
		add( RT.GOLD , gold );
	}
	public synchronized void add( RT resourceType , int amount )
	{
		if( amount < 1 || resourceType == null )
			return;

		switch( resourceType )
		{
		case GOLD : {
			int ngold = gold + amount;
			if (ngold > maxGold)
				ngold = maxGold;
			gold = ngold;
			synchronized (rvcls) {
				for (OnResourceValuesChangedListener rvcl : rvcls)
					rvcl.onGoldValueChanged(gold);
			}
			break;
		}

		default:
			break;
		}
	}
	public void add(Cost c) {
		add( RT.GOLD , c.getGoldCost() );
	}



	public synchronized int getMaxGold() {
		return maxGold;
	}

	public synchronized void setMaxGold(int maxGold) {
		this.maxGold = maxGold;
	}

	public synchronized int getMaxFood() {
		return maxFood;
	}

	public synchronized void setMaxFood(int maxFood) {
		this.maxFood = maxFood;
	}

	public synchronized int getMaxWood() {
		return maxWood;
	}

	public synchronized void setMaxWood(int maxWood) {
		this.maxWood = maxWood;
	}

	private void updatePopCostString()
	{
		popString = popCurr + "/" + popMax;
	}

	public synchronized String getPopulationAvailable()
	{
		return popString;
	}

	/**
	 * adds gold,food and wood.
	 * @param pr
	 */
	public synchronized void add(PR pr) {
		if( pr == null ){
			//Log.e(TAG ,  "add(PR pr) && pr == null ");
			return;
		}

		add( RT.GOLD , pr.gold );
	}

	public synchronized void set(int gold, int food, int wood, int pop) {
		this.gold = gold;
		this.food = food;
		this.wood = wood;
		popCurr = pop;
		synchronized(rvcls){
			for( OnResourceValuesChangedListener rvcl : rvcls )
				rvcl.onGoldValueChanged(gold);
		}
	}


	public synchronized void setMaxes(int maxes) {
		maxFood = maxes;
		maxGold = maxes;
		maxWood = maxes;
	}


	public synchronized void setMaxes(Integer maxG, Integer maxF, Integer maxW) {
		maxGold = maxG;
		maxFood = maxF;
		maxWood = maxW;
	}




	public synchronized boolean spend(RT type, int res) {
		if( res < 1 || type == null )
			return false;

		switch( type )
		{
		case GOLD:
			int ngold = gold - res;
			if( ngold < 0 )
				return false;
			gold = ngold;
			synchronized(rvcls){
				for( OnResourceValuesChangedListener rvcl : rvcls )
					rvcl.onGoldValueChanged(gold);
			}
			return true;
		default:		  return false;
		}

	}


	public synchronized boolean isFullOn(RT type) {
		if( type == null )
			return true;

		switch( type )
		{
		case GOLD 		: return gold >= maxGold ;
		default:		  return false;
		}
	}

	public synchronized void fillGFW(float perc) {
		add( RT.GOLD , (int) (perc*maxGold) );
	}



	public String toResString(){
		return "Gold " + gold + "/" + maxGold + ", Food " + food + "/" + maxFood + ", Wood " + wood + "/" + maxWood;
	}
	@Override
	public String toString(){
		return "Gold " + gold + "/" + maxGold + ", Food " + food + "/" + maxFood + ", Wood " + wood + "/" + maxWood + ", Magic Dusts " + md + ", Pop " + popCurr + "/" + popMax;
	}


	/**
	 * Affects gold,food,wood and md
	 * Ex. gold = (int) perc*gold;
	 * @param perc
	 */
	public synchronized void times(float perc) {
		gold *= perc;
		food *= perc;
		wood *= perc;
		md   *= perc;
	}




	//PR Value Changed Completed
	private final ArrayList<OnResourceValuesChangedListener> rvcls = new ArrayList<>();

	public static interface OnResourceValuesChangedListener{
		void onGoldValueChanged(int newGoldValue);
	}

	public void addRVCL(OnResourceValuesChangedListener rvcl)		   		{	synchronized( rvcls ){	rvcls.add( rvcl );				}  	}
	public boolean removeRVCL(OnResourceValuesChangedListener rvcl)		{	synchronized( rvcls ){	return rvcls.remove( rvcl );		}	}


}














