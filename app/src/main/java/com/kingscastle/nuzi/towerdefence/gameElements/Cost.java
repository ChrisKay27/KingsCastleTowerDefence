package com.kingscastle.nuzi.towerdefence.gameElements;

import java.io.BufferedWriter;
import java.io.IOException;

public class Cost
{
	public static final String GOLD = "g";
	public static final String FOOD = "f";
	public static final String WOOD = "w";
	public static final String MD = "m";
	public static final String POP = "p";




	private String name = "";
	private int goldCost;

	private int foodCost;

	private int woodCost;

	private int popCost;

	private int mdCost;

	public Cost(){}



	public Cost( int gold , int food , int wood , int md , int pop)
	{
		goldCost = gold;
		foodCost = food;
		woodCost = wood;
		mdCost = md;
	}


	public Cost ( int gold ){
		goldCost = gold;
	}



	public Cost( Cost costs )
	{
		if( costs == null )
			return;

		goldCost = costs.goldCost;
		foodCost = costs.foodCost;
		woodCost = costs.woodCost;
		popCost = costs.popCost;
		mdCost = costs.mdCost;
	}


	public Cost( int gold, int food, int wood, int pop ) {
		goldCost = gold;
		foodCost = food;
		woodCost = wood;
		popCost = pop;
	}


	public void set(Cost c) {
		if( c == null ) return;
		goldCost = c.goldCost;
		foodCost = c.foodCost;
		woodCost = c.woodCost;
		mdCost   = c.mdCost;
		popCost  = c.popCost;
	}

	/**
	 * Ex. 15 Gold, 5 Food, 10 Wood
	 * @return
	 */
	public String toResString(){
		StringBuilder sb = new StringBuilder();
		boolean b = false;
		if( goldCost != 0 ){ sb.append( goldCost + " Gold " ); b = true;}
		if( foodCost != 0 ){ sb.append( (b?" and ":"") + foodCost + " Food " ); b = true;}
		if( woodCost != 0 ){ sb.append( (b?" and ":"") + woodCost + " Wood " );  b = true; }

		return sb.toString();
	}

	@Override
	public String toString()
	{
		String s = "Costs: ";
		s+= goldCost!=0?"Gold:"   + goldCost +" ":"";
		s+= foodCost!=0?"Food:"   + foodCost +" ":"";
		s+= woodCost!=0?"Wood:"   + woodCost +" ":"";
		s+= mdCost!=0?"MagicDusts:"   + mdCost +" ":"";
		//s+= metalCost!=0?"Metal:" + metalCost+" ":"";
		s+= popCost!=0?"Pop:"   + popCost  +" ":"";
		return s;
	}


	public void reduceByPerc( double d )
	{
		goldCost         *= d;
		foodCost         *= d;
		woodCost         *= d;
	}


	public int getGoldCost() {
		return goldCost;
	}
	public void setGoldCost(int goldCost) {
		this.goldCost = goldCost;
	}
	public int getFoodCost() {
		return foodCost;
	}
	public void setFoodCost(int foodCost) {
		this.foodCost = foodCost;
	}
	public int getWoodCost() {
		return woodCost;
	}
	public void setWoodCost(int woodCost) {
		this.woodCost = woodCost;
	}
	public int getMagicDustCost() {
		return mdCost;
	}
	public void setMagicDustCost(int mds) {
		mdCost = mds;
	}
	public int getPopCost() {
		return popCost;
	}
	public void setPopCost( int popCost ) {
		this.popCost = popCost ;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}





	public void add(Cost c) {
		if( c == null )
			return;

		goldCost += c.goldCost;
		woodCost += c.woodCost;
		foodCost += c.foodCost;
		mdCost   += c.mdCost;
		popCost  += c.popCost;
	}

	/**
	 * Affects gold, wood, food and pop
	 * Ex. gold = (int) perc*gold;
	 * @param perc
	 */
	public void times( float perc ) {
		goldCost *= perc;
		woodCost *= perc;
		mdCost   *= perc;
		foodCost *= perc;
		popCost  *= perc;
	}

	public void divideBy(float i ) {

		goldCost /= i;
		woodCost /= i;
		mdCost /= i;
		foodCost /= i;
		popCost /= i;

	}






	public void saveYourself(BufferedWriter bw) throws IOException {
		String temp = "<" + getClass().getSimpleName() + " g=\"" + goldCost + "\" f=\"" +foodCost+ "\" w=\""+woodCost+"\" m=\""+mdCost+"\" p=\""+popCost+"\">";
		bw.write( temp , 0 , temp.length() );
		bw.newLine();


		temp = "</" + getClass().getSimpleName() + ">";
		bw.write( temp , 0 , temp.length() );
		bw.newLine();

	}







}
