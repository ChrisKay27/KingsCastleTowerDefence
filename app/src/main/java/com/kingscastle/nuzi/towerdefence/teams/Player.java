package com.kingscastle.nuzi.towerdefence.teams;


import com.kingscastle.nuzi.towerdefence.gameElements.Cost;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.level.PR;
import com.kingscastle.nuzi.towerdefence.teams.races.Race;

import java.io.BufferedWriter;
import java.io.IOException;

public class Player
{
	private static final String TAG = "Player";

	private String savedGameName;



	private Teams teamName;

	private Team team;


	public boolean spendCosts( Cost costs )
	{
		if( costs == null )
			throw new IllegalArgumentException("Costs cannot be null");
		else
			return team.getPR().spend( costs );
		//totalZoneResources.spend(costs);

	}



	public void refundCosts(Cost costs)
	{
		if( costs == null )
			return;
		else
			team.getPR().refund(costs );
		//	totalZoneResources.refund(costs);

	}


	public boolean canAfford( Cost cost )
	{
		if( cost == null )
			return true;
		////Log.e( TAG , " Warning cost was null! ");

		return team.canAfford( cost );
	}




	public PR getPR()
	{
		return team.getPR();
	}






	public void saveYourSelf( BufferedWriter b ) throws IOException
	{
		if( b == null )
			return;

		String s = "<Player>";

		b.write( s , 0, s.length() );
		b.newLine();

		team.getPR().saveYourSelf( b );


		if( getAbs() != null )
			getAbs().saveYourSelf(b);

		s = "</Player>";
		b.write(s,0,s.length());
		b.newLine();
	}






	public String getSavedGameName() {
		return savedGameName;
	}


	public void nullify() {
	}

	public AllowedBuildings getAbs(){
		return team.abs;
	}

	public void setAbs(AllowedBuildings abs) {
		team.abs = abs;
	}


	public void setSavedGameName(String savedGameName) {
		this.savedGameName = savedGameName;
	}




	public void finalInit( MM mm ) {
		// TODO Auto-generated method stub

	}




	public boolean isDead() {
		// TODO Auto-generated method stub
		return false;
	}




	public Teams getTeamName()
	{
		return teamName;
	}
	public void setTeamName(Teams teamName) {
		this.teamName = teamName;
	}

	public void setTeam(Team team) {
		this.team = team;
		if( team != null )
			teamName = team.getTeamName();
	}
	public Team getTeam()
	{
		return team;
	}








	public Race getRace(){
		return team.race;
	}



	public void act() {
	}

	public void pauseThread() {
	}



	public void startThread() {
	}




//	public AvailableSpells getAs() {
//		return team.as;
//	}
//
//
//	public void setAs(AvailableSpells as) {
//		team.as = as;
//	}















}
