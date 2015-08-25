package com.kingscastle.nuzi.towerdefence.gameUtils;

import com.kingscastle.nuzi.towerdefence.gameElements.Cost;

import java.util.Locale;



public enum Age
{

	STONE( 999999999 ,999999999 , 999999999 ) , BRONZE( 500 , 400 , 400 ) , IRON( 1000 , 1000 , 1000 ) , STEEL( 2000 , 2000 , 2000 ) , UNREACHABLE ( 999999999 , 999999999, 999999999 );

	private Cost advancementCost;
	private String string;

	private Age( int goldReq2 , int foodReq2 , int woodReq2 )
	{
		advancementCost = new Cost( goldReq2 , foodReq2 , woodReq2 , 0 );
		string = ""+name().charAt(0);
		string += name().substring(1).toLowerCase(Locale.CANADA);
	}


	public Age nextAge()
	{
		switch ( this )
		{
		default:
		case STONE:
			return BRONZE;

		case BRONZE:
			return IRON;

		case IRON:
			return STEEL;

		case STEEL:
			return UNREACHABLE;
		}
	}


	public Cost getAdvancementCost() {
		return advancementCost;
	}


	public void setAdvancementCost(Cost advancementCost) {
		this.advancementCost = advancementCost;
	}


	public static Age getAgeFromString( String age )
	{
		if( age == null ){
			//Log.e( "Age" , "getAgeFromString(null)");
			return STONE;
		}

		else if ( age.equals( BRONZE.toString() ))
			return BRONZE;

		else if ( age.equals( STONE.toString() ))
			return STONE;

		else if ( age.equals( IRON.toString() ))
			return IRON;

		else if ( age.equals( STEEL.toString() ))
			return STEEL;

		//Log.e( "Age" , "getAgeFromString("+age+") does not match any ages, returning Stone");
		return STONE;
	}



	@Override
	public String toString(){
		return string;
	}



}
