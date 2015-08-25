package com.kingscastle.nuzi.towerdefence.gameElements;

import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Building;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.teams.Player;
import com.kingscastle.nuzi.towerdefence.teams.Team;
import com.kingscastle.nuzi.towerdefence.teams.Teams;

import java.io.BufferedWriter;
import java.io.IOException;

public class LevelUpTechnology extends Technology {

	private static final String TAG = "LevelUpTechnology";

	private MM mm;
	private Team team;
	private Teams teamName;

	private Building b;
	private LivingThing lt;
	private String lvlAdvancingTo = " "; // Must have this format for loading: NameOfThingLvlingUp Level 26
	private int buildTime = 10;
	private String ltClass;

	private int newLvl;

	public LevelUpTechnology( MM mm,Player p, Building b , LivingThing lt){
		this( mm, p , b , lt , -1 );
	}

	public LevelUpTechnology( MM mm, Player p, Building b , LivingThing lt , int buildTime_ )
	{
		this.b = b;
		setLivingThing(lt);
		team = p.getTeam();
		this.mm = mm;
		teamName = team.getTeamName();
		setNewLvl(lt.lq.getLevel()+1);

//	FIXME: Everything builds instantly in tower defence mode
//
//		if( buildTime_ == -1 ){
//			if( Settings.yourBaseMode )
//				switch( newLvl ){
//				case 2: buildTime = 1000*60*5;       break;
//				case 3: buildTime = 1000*60*15;      break;
//				case 4: buildTime = 1000*60*30;      break;
//				case 5: buildTime = 1000*60*60*1;    break;
//				case 6: buildTime = 1000*60*60*4;    break;
//				case 7: buildTime = 1000*60*60*8;    break;
//				case 8: buildTime = 1000*60*60*12;   break;
//				case 9: buildTime = 1000*60*60*18;   break;
//				case 10:buildTime = 1000*60*60*24*1; break;
//				case 11:buildTime = 1000*60*60*24*2; break;
//				case 12:buildTime = 1000*60*60*24*3; break;
//				case 13:buildTime = 1000*60*60*24*4; break;
//				case 14:buildTime = 1000*60*60*24*5; break;
//				case 15:buildTime = 1000*60*60*24*6; break;
//				case 16:buildTime = 1000*60*60*24*9; break;
//				case 17:buildTime = 1000*60*60*24*10;break;
//				case 18:buildTime = 1000*60*60*24*11;break;
//				case 19:buildTime = 1000*60*60*24*12;break;
//				case 20:buildTime = 1000*60*60*24*14;break;
//				}
//			else
//				buildTime = 20000*newLvl;
//		}
//		else
//			this.buildTime = buildTime_;
	}

	public LevelUpTechnology(){
	}

	@Override
	public String getName() {
		return lvlAdvancingTo;
	}

	@Override
	public int getBuildTime() {
		return buildTime;
	}

	@Override
	public void queueableComplete() {
		//Log.d( TAG , "queueableComplete() for a " + lt + " ltClass=" + ltClass );
		if( team == null )
			team = mm.getTM().getTeam( teamName );

		team.onTechResearchFinished( b , this);
	}


	@Override
	public void setBuildTime(int bt) {
		buildTime = bt;
	}


	@Override
	public void saveYourself( BufferedWriter b ) throws IOException
	{
		String s = START_TAG + " n=\"LevelUpTechnology\" lt=\""+ltClass+"\" lv=\""+newLvl+"\" bt=\""+ buildTime +"\">";

		b.write( s , 0 , s.length() );
		b.newLine();

		b.write( ENDTAG , 0 , ENDTAG.length() );
		b.newLine();
	}


	public LivingThing getLivingThing() {
		return lt;
	}


	@Override
	public void setLivingThing(LivingThing lt) {
		this.lt = lt;
		if( lt != null ){
			ltClass = lt.getClass().getSimpleName();

			if( lt instanceof Building ) //If building is leveling itself up no point showing its name
				lvlAdvancingTo = "Level " + (lt.lq.getLevel()+1) ;
			else
				lvlAdvancingTo = lt.getName() + " Level " + (lt.lq.getLevel()+1) ;

			teamName = lt.getTeamName();
		}
	}

	@Override
	public void setLTClassName( String ltClass ) {
		this.ltClass = ltClass;
		if( ltClass != null && !"null".equals(ltClass) )
			lvlAdvancingTo = ltClass + " Level ";
		else
			lvlAdvancingTo = "Level ";
	}
	@Override
	public String getLTClassName() {
		return ltClass;
	}

	@Override
	public void setTeamName( Teams team) {
		teamName = team;
	}

	public boolean isLvlingUpAUnit() {
		if( lt != null ){
			if( lt instanceof Building )
				return false;
			else
				return true;
		}
		else if( ltClass != null ){
			try
			{
				Class<?> aUnit = Class.forName("com.kaebe.kingscastle27.livingThings.army." + ltClass );
				if( aUnit != null )
					return true;
				else
					return false;
			}
			catch(Exception e)
			{
				//Log.v( TAG , "Did not find the Class in the livingThing.army. folder " );
				return false;
			}
		}
		else
			return false;

	}
	@Override
	public void setBuilding(Building b2) {
		b = b2;
	}



	public void setNewLvl(int nlvl) {
		newLvl = nlvl;
		if( lvlAdvancingTo.length() == 0 ) return;

		if( !lvlAdvancingTo.substring(lvlAdvancingTo.length()-1).matches("[0-9]"))
			lvlAdvancingTo += newLvl;
	}












}
