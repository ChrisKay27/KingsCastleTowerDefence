package com.kingscastle.nuzi.towerdefence.teams;


import com.kingscastle.nuzi.towerdefence.framework.Image;

public class TeamColors
{
	private static final Image redTeamFlag = null;//Assets.loadImage( R.drawable.flag_red );
	private static final Image blueTeamFlag = null;//Assets.loadImage(R.drawable.flag_blue);
	private static final Image yellowTeamFlag = null;//Assets.loadImage( R.drawable.flag_yellow );
	private static final Image orangeTeamFlag = null;//Assets.loadImage( R.drawable.flag_orange );
	//private static final Image pinkTeamFlag = Assets.loadImage( R.drawable.flag_pink );
	private static final Image greenTeamFlag = null;//Assets.loadImage( R.drawable.flag_green );
	private static final Image whiteTeamFlag = null;//Assets.loadImage( R.drawable.flag_white );



	public static Image getFlagForTeam ( Teams teamName )
	{
		switch ( teamName )
		{


		case BLUE:
			return blueTeamFlag;


		case RED:
			return redTeamFlag;


		case GREEN:
			return greenTeamFlag;


		case WHITE:
			return whiteTeamFlag;


		case ORANGE:
			return orangeTeamFlag;


		case GRAY:
			return greenTeamFlag;


		case PURPLE:
			return blueTeamFlag;
		case BLACK:
			break;





		case YELLOW:
			return yellowTeamFlag;


		default:
			break;

		}

		return redTeamFlag;

	}




}



