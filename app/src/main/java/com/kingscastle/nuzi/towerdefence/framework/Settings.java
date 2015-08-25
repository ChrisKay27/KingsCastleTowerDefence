package com.kingscastle.nuzi.towerdefence.framework;

import android.content.SharedPreferences;

public class Settings
{
	public static boolean autoAdjustSettings = true;
	public static boolean showFogOfWar = true;
	public static boolean showAllAreaBorders = false;
	public static boolean alwaysShowAreaBorders = false;

	public static boolean instantBuild;
	public static boolean freeMode;
	public static boolean showFps;

	public static boolean showSmoothFogOfWar = true;
	public static boolean showHealthBar = true;
	public static boolean showDamageText = false;
	public static boolean showVectors = false;
	public static boolean showPath = false;
	public static boolean showGarrisonDisplay = true;
	public static boolean hideBuildQueue;

	public static boolean muteMusic = false;
	public static boolean muteSounds = false;
	public static boolean drawUnwalkableTilesOnMiniMap = false;

	private static final boolean[] savedSettings = new boolean[10];
	private static boolean hasSavedState = false;
	public static boolean showPathFinding = false;

	public static boolean targetFindingDisabled = false;
	public static boolean hideResourceDisplay   = false;
	public static boolean mapEditingMode        = false;

	public static int keepDeadAnimsAroundFor = 60000;

	public static boolean loadingLevel = false;
	public static boolean savingLevel = false;


	public static void save()
	{
		if( hasSavedState )
			return;

		hasSavedState = true;

		savedSettings[0] = showHealthBar;
		savedSettings[1] = showVectors;
		savedSettings[2] = showPath;
		savedSettings[3] = showSmoothFogOfWar;
		savedSettings[4] = muteMusic;
		savedSettings[5] = muteSounds;
		//savedSettings[6] = alwaysShowAreaBorders;

	}


	public static void restore()
	{
		if( hasSavedState )
		{
			showHealthBar = savedSettings[0];
			showVectors = savedSettings[1];
			showPath = savedSettings[2];
			showSmoothFogOfWar = savedSettings[3];
			muteMusic = savedSettings[4];
			muteSounds =  savedSettings[5];
			//alwaysShowAreaBorders =  savedSettings[6];
			hasSavedState = false;
		}
	}


	public static void initializeSettings(){
	}






	public static void toggleHealthBars()
	{
		showHealthBar = !showHealthBar;
		savedSettings[0] = showHealthBar;
	}



	public static void toggleShowVectors()
	{
		showVectors = !showVectors;
		savedSettings[1] = showVectors;
	}


	public static void toggleShowPath()
	{
		showPath = !showPath;
		savedSettings[2] = showPath;
	}


	public static void toggleSmoothFogOfWar()
	{
		showSmoothFogOfWar = !showSmoothFogOfWar;
		savedSettings[3] = showSmoothFogOfWar;
	}


	public static void toggleMuteMusic()
	{
		muteMusic = !muteMusic;
		savedSettings[4] = muteMusic;
	}


	public static void toggleMuteSounds()
	{
		muteSounds = !muteSounds;
		savedSettings[5] = muteSounds;
	}

	public static void toggleAlwaysShowAreaBorders()
	{
		alwaysShowAreaBorders = !alwaysShowAreaBorders;
		savedSettings[6] = alwaysShowAreaBorders;
	}

	public static void saveToPreferences(SharedPreferences prefObs)
	{
		SharedPreferences.Editor e = prefObs.edit();
		e.putString( "muteSounds" , muteSounds + "");
		e.putString( "muteMusic" , muteMusic + "");
		e.putString( "showSmoothFogOfWar" , showSmoothFogOfWar + "");
		e.putString( "showPath" , showPath + "");
		e.putString( "showVectors" , showVectors + "");
		e.putString( "showHealthBar" , showHealthBar + "");
		//e.putString( "alwaysShowAreaBorders" , alwaysShowAreaBorders + "");
		e.apply();
	}


	public static void loadFromPreferences(SharedPreferences prefObs)
	{
		muteSounds = Boolean.parseBoolean( prefObs.getString( "muteSounds" , "false" ) );
		muteMusic = Boolean.parseBoolean( prefObs.getString( "muteMusic" , "false" ) );
		showPath = Boolean.parseBoolean( prefObs.getString( "showPath" , "false" ) );
		showSmoothFogOfWar = Boolean.parseBoolean( prefObs.getString( "showSmoothFogOfWar" , "true" ) );
		showVectors = Boolean.parseBoolean( prefObs.getString( "showVectors" , "false" ) );
		showHealthBar = Boolean.parseBoolean( prefObs.getString( "showHealthBar" , "true" ) );
		//alwaysShowAreaBorders = Boolean.parseBoolean( prefObs.getString( "alwaysShowAreaBorders" , "true" ) );
	}




	public static void reset() {

		autoAdjustSettings = true;

		showFogOfWar = true;
		showAllAreaBorders = false;
		alwaysShowAreaBorders = true;
		showSmoothFogOfWar = true;
		showHealthBar = true;

		//showVectors = KingsCastle.testingVersion;
		//showPath = KingsCastle.testingVersion;

		//muteMusic = false;
		//muteSounds = false;
		drawUnwalkableTilesOnMiniMap = false;


		hasSavedState = false;
		//showPathFinding = false;

		targetFindingDisabled = false;
		hideResourceDisplay   = false;
		mapEditingMode        = false;

	}





}
