package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes;


public enum SoldierType
{

	WORKER , WARRIOR , SOLDIER , KNIGHT  , SCOUT ,  ARCHER , LONGBOWMAN , MEDIC ,  PRIESTESS , WIZARD ,

	DWARFWORKER , DWARFHEALS , DWARFHIGHHEALER , DWARFWARRIOR , DWARFNOBLEAXEMAN , DWARFJUGGERNAUT ,
	DWARFSCOUT , DWARFCROSSBOWMAN , DWARFSHARPSHOOTER , GANTREE ,

	UNDEADWORKER , UNDEADHEALER , UNDEADDEMOGORGON , UNDEADSKELETONWARRIOR , UNDEADMARSHALL , UNDEADSKULLFUCQUED ,
	UNDEADSKELETONSCOUT , UNDEADSKELETONARCHER , UNDEADSKELETONBOWMAN , BLACKDEATH,


	HOLYSLAVEWORKER , HOLYGUARD , HOLYKNIGHT , HOLYPALADIN  , HOLYMISSIONARY ,  HOLYBOWMAN , HOLYARCHER , HOLYNUN , HOLYANGEL , JESUS, CATAPULT


    //	public static SoldierType getFromString( String class_dot_SimpleName )
	//	{
	//		String upper = class_dot_SimpleName.toUpperCase( Locale.US );
	//
	//		if( upper.equals( WORKER.name() ) )
	//		{
	//			return WORKER;
	//		}
	//		else if( upper.equals( CATAPULT.name() ) )
	//		{
	//			return CATAPULT;
	//		}
	//		else if( upper.equals( WARRIOR.name() ) )
	//		{
	//			return WARRIOR;
	//		}
	//		else if( upper.equals( SOLDIER.name() ) )
	//		{
	//			return SOLDIER;
	//		}
	//		else if( upper.equals( KNIGHT.name() ) )
	//		{
	//			return KNIGHT;
	//		}
	//		else if( upper.equals( SCOUT.name() ) )
	//		{
	//			return SCOUT;
	//		}
	//		else if( upper.equals( ARCHER.name() ) )
	//		{
	//			return ARCHER;
	//		}
	//		else if( upper.equals( LONGBOWMAN.name() ) )
	//		{
	//			return LONGBOWMAN;
	//		}
	//		else if( upper.equals( MEDIC.name() ) )
	//		{
	//			return MEDIC;
	//		}
	//		else if( upper.equals( PRIESTESS.name() ) )
	//		{
	//			return PRIESTESS;
	//		}
	//		else if( upper.equals( WIZARD.name() ) )
	//		{
	//			return WIZARD;
	//		}
	//
	//
	//		else if( upper.equals( DWARFWORKER.name() ) )
	//		{
	//			return DWARFWORKER;
	//		}
	//		else if( upper.equals( DWARFHEALS.name() ) )
	//		{
	//			return DWARFHEALS;
	//		}
	//		else if( upper.equals( DWARFHIGHHEALER.name() ) )
	//		{
	//			return DWARFHIGHHEALER;
	//		}
	//		else if( upper.equals( DWARFWARRIOR.name() ) )
	//		{
	//			return DWARFWARRIOR;
	//		}
	//		else if( upper.equals( DWARFNOBLEAXEMAN.name() ) )
	//		{
	//			return DWARFNOBLEAXEMAN;
	//		}
	//		else if( upper.equals( DWARFJUGGERNAUT.name() ) )
	//		{
	//			return DWARFJUGGERNAUT;
	//		}
	//		else if( upper.equals( DWARFSCOUT.name() ) )
	//		{
	//			return DWARFSCOUT;
	//		}
	//		else if( upper.equals( DWARFCROSSBOWMAN.name() ) )
	//		{
	//			return DWARFCROSSBOWMAN;
	//		}
	//		else if( upper.equals( DWARFSHARPSHOOTER.name() ) )
	//		{
	//			return DWARFSHARPSHOOTER;
	//		}
	//		else if( upper.equals( GANTREE.name() ) )
	//		{
	//			return GANTREE;
	//		}
	//
	//
	//		else if( upper.equals( UNDEADWORKER.name() ) )
	//		{
	//			return UNDEADWORKER;
	//		}
	//		else if( upper.equals( UNDEADHEALER.name() ) )
	//		{
	//			return UNDEADHEALER;
	//		}
	//		else if( upper.equals( UNDEADDEMOGORGON.name() ) )
	//		{
	//			return UNDEADDEMOGORGON;
	//		}
	//		else if( upper.equals( UNDEADSKELETONWARRIOR.name() ) )
	//		{
	//			return UNDEADSKELETONWARRIOR;
	//		}
	//		else if( upper.equals( UNDEADMARSHALL.name() ) )
	//		{
	//			return UNDEADMARSHALL;
	//		}
	//		else if( upper.equals( UNDEADSKULLFUCQUED.name() ) )
	//		{
	//			return UNDEADSKULLFUCQUED;
	//		}
	//		else if( upper.equals( UNDEADSKELETONSCOUT.name() ) )
	//		{
	//			return UNDEADSKELETONSCOUT;
	//		}
	//		else if( upper.equals( UNDEADSKELETONARCHER.name() ) )
	//		{
	//			return UNDEADSKELETONARCHER;
	//		}
	//		else if( upper.equals( UNDEADSKELETONBOWMAN.name() ) )
	//		{
	//			return UNDEADSKELETONBOWMAN;
	//		}
	//		else if( upper.equals( BLACKDEATH.name() ) )
	//		{
	//			return BLACKDEATH;
	//		}
	//
	//
	//		if( upper.equals( HOLYSLAVEWORKER.name() ) )
	//		{
	//			return HOLYSLAVEWORKER;
	//		}
	//		else if( upper.equals( HOLYGUARD.name() ) )
	//		{
	//			return HOLYGUARD;
	//		}
	//		else if( upper.equals( HOLYKNIGHT.name() ) )
	//		{
	//			return HOLYKNIGHT;
	//		}
	//		else if( upper.equals( HOLYPALADIN.name() ) )
	//		{
	//			return HOLYPALADIN;
	//		}
	//		else if( upper.equals( HOLYMISSIONARY.name() ) )
	//		{
	//			return HOLYMISSIONARY;
	//		}
	//		else if( upper.equals( HOLYBOWMAN.name() ) )
	//		{
	//			return HOLYBOWMAN;
	//		}
	//		else if( upper.equals( HOLYARCHER.name() ) )
	//		{
	//			return HOLYARCHER;
	//		}
	//		else if( upper.equals( HOLYNUN.name() ) )
	//		{
	//			return HOLYNUN;
	//		}
	//		else if( upper.equals( HOLYANGEL.name() ) )
	//		{
	//			return HOLYANGEL;
	//		}
	//		else if( upper.equals( JESUS.name() ) )
	//		{
	//			return JESUS;
	//		}
	//
	//
	//		//		else if( upper.equals( BLACK_DEATH.name() ) )
	//		//		{
	//		//			return BLACK_DEATH;
	//		//		}
	//
	//		return null;
	//
	//
	//	}


}
