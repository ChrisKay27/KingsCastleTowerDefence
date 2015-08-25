package com.kingscastle.nuzi.towerdefence.teams;


import android.graphics.Color;

import java.io.Serializable;
import java.util.ArrayList;


public enum Teams implements Serializable
{
	BLUE ( Color.BLUE ) , RED ( Color.RED ) , GREEN ( Color.GREEN ) , PURPLE ( Color.MAGENTA ) , YELLOW ( Color.YELLOW ) ,
	CYAN ( Color.CYAN ) , WHITE ( Color.WHITE ) , GRAY ( Color.GRAY ) , BLACK ( Color.BLACK ), ORANGE ( Color.rgb( 255 , 106 , 0 ) ) ;

	private final int color;


	private Teams ( int color )
	{
		this.color = color;
	}

	private static final ArrayList<Teams> allTeams;



	static
	{
		allTeams = new ArrayList<Teams>();
		allTeams.add(  BLUE   );
		allTeams.add(  RED    );
		allTeams.add(  GREEN  );
		allTeams.add(  WHITE  );
		//allTeams.add(  ORANGE );


		//		allTeams.add(PURPLE);
		//		allTeams.add(YELLOW);
		//		allTeams.add(CYAN);
		//		allTeams.add(WHITE);
		//		allTeams.add(GRAY);
		//		allTeams.add(BLACK);

	}


	public static Teams getFromString(String t)
	{
		if( t == null )
			return null;


		if(t.equals("BLUE")) {
			return Teams.BLUE;
		}
		if(t.equals("RED")) {
			return Teams.RED;
		}
		if(t.equals("GREEN")) {
			return Teams.GREEN;
		}
		if(t.equals("PURPLE")) {
			return Teams.PURPLE;
		}
		if(t.equals("YELLOW")) {
			return Teams.YELLOW;
		}
		if(t.equals("TEAL")) {
			return Teams.CYAN;
		}
		if(t.equals("WHITE")) {
			return Teams.WHITE;
		}
		if(t.equals("GREY")) {
			return Teams.GRAY;
		}
		if(t.equals("BLACK")) {
			return Teams.BLACK;
		}
		if(t.equals("ORANGE")) {
			return Teams.ORANGE;
		} else {
			return RED;
		}
	}


	public static ArrayList<Teams> getAllTeams()
	{
		ArrayList<Teams> newTeamList = new ArrayList<Teams>();
		newTeamList.addAll( allTeams );
		return newTeamList;
	}


	public int getColor() {
		return color;
	}


}
