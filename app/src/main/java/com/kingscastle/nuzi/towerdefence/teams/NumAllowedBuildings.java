package com.kingscastle.nuzi.towerdefence.teams;


import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Buildings;

import java.util.HashMap;

public class NumAllowedBuildings {

	private static HashMap<Buildings, int[]> unlockedAtLevels = new HashMap<Buildings, int[]>();

	static{
		unlockedAtLevels.put( Buildings.TownCenter 			, new int[]{1}				);
		unlockedAtLevels.put( Buildings.StorageDepot 		, new int[]{1}				);
		unlockedAtLevels.put( Buildings.Barracks  			, new int[]{1,3,6,10,14,19} );
		unlockedAtLevels.put( Buildings.WatchTower			, new int[]{1,2,4,8,13,17}  );
		unlockedAtLevels.put( Buildings.Farm 				, new int[]{1,5,10,16}		);
		unlockedAtLevels.put( Buildings.LumberMill 			, new int[]{2,7,12,18}		);
		unlockedAtLevels.put( Buildings.GoldMineBuilding	, new int[]{2,6,11,17} 		);
		unlockedAtLevels.put( Buildings.BasicHealingShrine  , new int[]{3,5,8,13,20}	);
		unlockedAtLevels.put( Buildings.Church 				, new int[]{4,7,12,18}		);
		unlockedAtLevels.put( Buildings.Wall             	, new int[]{3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20} );
		unlockedAtLevels.put( Buildings.BombTrap 			, new int[]{1,2,3,6,8,13,15,19}				);
		unlockedAtLevels.put( Buildings.SpikeTrap 			, new int[]{1}				);

		unlockedAtLevels.put( Buildings.WeaponsSpears 		, new int[]{3,7,13,19}				);
		unlockedAtLevels.put( Buildings.StatueArmor 		, new int[]{6}				);
		unlockedAtLevels.put( Buildings.StatueArmor2 		, new int[]{11}				);

		unlockedAtLevels.put( Buildings.StatueMonk 			, new int[]{6}				);
		unlockedAtLevels.put( Buildings.StatueMonk2 		, new int[]{8}				);
		unlockedAtLevels.put( Buildings.StatueMonk3 		, new int[]{10}				);
		unlockedAtLevels.put( Buildings.StatuePillar 		, new int[]{7}				);
		unlockedAtLevels.put( Buildings.StatuePillar2 		, new int[]{7}				);
		unlockedAtLevels.put( Buildings.StatuePillar3 		, new int[]{7}				);
		unlockedAtLevels.put( Buildings.StatueDragon 		, new int[]{8,13,20}				);
		unlockedAtLevels.put( Buildings.StatueDragon2 		, new int[]{9,14,20}				);
		unlockedAtLevels.put( Buildings.StatueOracle 		, new int[]{16}				);

		unlockedAtLevels.put( Buildings.RuneStone 			, new int[]{4}				);

		unlockedAtLevels.put( Buildings.Snowman 			, new int[]{18,19,20}		);

		unlockedAtLevels.put( Buildings.Well 				, new int[]{6,16}				);
		unlockedAtLevels.put( Buildings.Well2 				, new int[]{6,16}				);

		unlockedAtLevels.put( Buildings.Stump 				, new int[]{1}				);
		unlockedAtLevels.put( Buildings.Stump2 				, new int[]{1}				);
		unlockedAtLevels.put( Buildings.Stump3 				, new int[]{1}				);
		unlockedAtLevels.put( Buildings.LogMold 			, new int[]{2}				);
		unlockedAtLevels.put( Buildings.LogsHorz 			, new int[]{4}				);
		unlockedAtLevels.put( Buildings.LogsVert 			, new int[]{4}				);

		unlockedAtLevels.put( Buildings.Hay 				, new int[]{5}				);
		unlockedAtLevels.put( Buildings.Grains 				, new int[]{8}				);
		unlockedAtLevels.put( Buildings.Scarecrow 			, new int[]{6}				);


		unlockedAtLevels.put( Buildings.Tree 				, new int[]{3}				);
		unlockedAtLevels.put( Buildings.Tree2 				, new int[]{3}				);
		unlockedAtLevels.put( Buildings.Shrub 				, new int[]{3}				);
		unlockedAtLevels.put( Buildings.Flowers 			, new int[]{2}				);
		unlockedAtLevels.put( Buildings.Flowers2 			, new int[]{2}				);


	}


	public static int canBuildMoreAtTcLevel(Buildings b , int currTcLevel){
		if( unlockedAtLevels.containsKey(b) ){
			int[] unlockedAtLvls = unlockedAtLevels.get(b);

			for( int i = 0 ; i < unlockedAtLvls.length ; ++i ){
				if( unlockedAtLvls[i] > currTcLevel )
					return unlockedAtLvls[i];
			}
		}
		return -1;
	}


	public static void addBuildingsAllowed(int tcLevel, HashMap<Buildings, Integer> numBuildingsAllowed) {

		if( tcLevel >= 1 ){
			numBuildingsAllowed.put( Buildings.Barracks , 1 );
			numBuildingsAllowed.put( Buildings.TownCenter , 1 );
			numBuildingsAllowed.put( Buildings.WatchTower , 1 );
			numBuildingsAllowed.put( Buildings.Farm , 1 );
			numBuildingsAllowed.put( Buildings.StorageDepot , 1 );
			numBuildingsAllowed.put( Buildings.BombTrap , 1 );


			numBuildingsAllowed.put( Buildings.Stump , 20 );
			numBuildingsAllowed.put( Buildings.Stump2 , 20 );
			numBuildingsAllowed.put( Buildings.Stump3, 20 );
		}


		if( tcLevel >= 2 ){
			numBuildingsAllowed.put( Buildings.WatchTower , 2 );
			numBuildingsAllowed.put( Buildings.GoldMineBuilding , 1 );
			numBuildingsAllowed.put( Buildings.LumberMill , 1 );
			numBuildingsAllowed.put( Buildings.StorageDepot , 1 );
			numBuildingsAllowed.put( Buildings.SpikeTrap , 1 );
			numBuildingsAllowed.put( Buildings.BombTrap , 2 );

			numBuildingsAllowed.put( Buildings.LogMold , 20 );
			numBuildingsAllowed.put( Buildings.Flowers , 50 );
			numBuildingsAllowed.put( Buildings.Flowers2 , 50 );
		}



		if( tcLevel >= 3 ){
			numBuildingsAllowed.put( Buildings.BasicHealingShrine , 1 );
			numBuildingsAllowed.put( Buildings.Wall , 20 );
			numBuildingsAllowed.put( Buildings.SpikeTrap , 2 );
			numBuildingsAllowed.put( Buildings.BombTrap , 3 );

			numBuildingsAllowed.put( Buildings.WeaponsSpears , 1 );
			numBuildingsAllowed.put( Buildings.Tree , 10 );
			numBuildingsAllowed.put( Buildings.Tree2 , 10 );
			numBuildingsAllowed.put( Buildings.Shrub , 20 );
		}


		if( tcLevel >= 4 ){
			//Log.d("NumAllowedBuildings", "tcLevel >= 4  adding 40 walls");
			numBuildingsAllowed.put( Buildings.WatchTower , 3 );
			numBuildingsAllowed.put( Buildings.Church , 1 );
			numBuildingsAllowed.put( Buildings.Wall , 40 );
			numBuildingsAllowed.put( Buildings.SpikeTrap , 3 );

			numBuildingsAllowed.put( Buildings.LogsHorz , 10 );
			numBuildingsAllowed.put( Buildings.LogsVert , 10 );
			numBuildingsAllowed.put( Buildings.RuneStone , 10 );
		}


		if( tcLevel >= 5 ){
			numBuildingsAllowed.put( Buildings.Farm , 2 );
			numBuildingsAllowed.put( Buildings.BasicHealingShrine , 2 );
			numBuildingsAllowed.put( Buildings.Wall , 60 );

			numBuildingsAllowed.put( Buildings.Hay , 5 );
		}


		if( tcLevel >= 6 ){
			numBuildingsAllowed.put( Buildings.GoldMineBuilding , 2 );
			numBuildingsAllowed.put( Buildings.Barracks , 2 );
			numBuildingsAllowed.put( Buildings.Wall , 80 );
			numBuildingsAllowed.put( Buildings.SpikeTrap , 4 );
			numBuildingsAllowed.put( Buildings.BombTrap , 4 );

			numBuildingsAllowed.put( Buildings.Scarecrow , 5 );
			numBuildingsAllowed.put( Buildings.StatueArmor , 4 );
			numBuildingsAllowed.put( Buildings.Well , 1 );
			numBuildingsAllowed.put( Buildings.Well2 , 1 );
			numBuildingsAllowed.put( Buildings.StatueMonk , 4 );
		}

		if( tcLevel >= 7 ){
			numBuildingsAllowed.put( Buildings.LumberMill , 2 );
			numBuildingsAllowed.put( Buildings.Church , 2 );
			numBuildingsAllowed.put( Buildings.Wall , 90 );

			numBuildingsAllowed.put( Buildings.WeaponsSpears , 2 );
			numBuildingsAllowed.put( Buildings.StatuePillar , 10 );
			numBuildingsAllowed.put( Buildings.StatuePillar2 , 10 );
			numBuildingsAllowed.put( Buildings.StatuePillar3 , 10 );
		}

		if( tcLevel >= 8 ){
			numBuildingsAllowed.put( Buildings.WatchTower , 4 );
			numBuildingsAllowed.put( Buildings.BasicHealingShrine , 3 );
			numBuildingsAllowed.put( Buildings.Wall , 100 );
			numBuildingsAllowed.put( Buildings.SpikeTrap , 5 );
			numBuildingsAllowed.put( Buildings.BombTrap , 5 );

			numBuildingsAllowed.put( Buildings.Grains , 5 );
			numBuildingsAllowed.put( Buildings.StatueDragon , 1 );
			numBuildingsAllowed.put( Buildings.StatueMonk2 , 4 );
		}

		if( tcLevel >= 9 ){
			numBuildingsAllowed.put( Buildings.Wall , 110 );

			numBuildingsAllowed.put( Buildings.StatueDragon2 , 1 );
		}

		if( tcLevel >= 10 ){
			numBuildingsAllowed.put( Buildings.Farm , 3 );
			numBuildingsAllowed.put( Buildings.Barracks , 3 );
			numBuildingsAllowed.put( Buildings.Wall , 120 );
			numBuildingsAllowed.put( Buildings.SpikeTrap , 6 );

			numBuildingsAllowed.put( Buildings.StatueMonk3 , 4 );
		}

		if( tcLevel >= 11 ){
			numBuildingsAllowed.put( Buildings.GoldMineBuilding , 3 );
			numBuildingsAllowed.put( Buildings.Wall , 130 );

			numBuildingsAllowed.put( Buildings.StatueArmor2 , 4 );
		}

		if( tcLevel >= 12 ){
			numBuildingsAllowed.put( Buildings.LumberMill , 3 );
			numBuildingsAllowed.put( Buildings.Church , 3 );
			numBuildingsAllowed.put( Buildings.Wall , 140 );
			numBuildingsAllowed.put( Buildings.SpikeTrap , 7 );
		}

		if( tcLevel >= 13 ){
			numBuildingsAllowed.put( Buildings.WatchTower , 5 );
			numBuildingsAllowed.put( Buildings.BasicHealingShrine , 4 );
			numBuildingsAllowed.put( Buildings.Wall , 150 );
			numBuildingsAllowed.put( Buildings.BombTrap , 6 );


			numBuildingsAllowed.put( Buildings.StatueDragon , 2 );
			numBuildingsAllowed.put( Buildings.WeaponsSpears , 3 );
		}

		if( tcLevel >= 14 ){
			numBuildingsAllowed.put( Buildings.Barracks , 4 );
			numBuildingsAllowed.put( Buildings.Wall , 160 );
			numBuildingsAllowed.put( Buildings.SpikeTrap , 8 );

			numBuildingsAllowed.put( Buildings.StatueDragon2 , 2 );
		}

		if( tcLevel >= 15 ){
			numBuildingsAllowed.put( Buildings.Wall , 170 );
			numBuildingsAllowed.put( Buildings.BombTrap , 7 );
		}

		if( tcLevel >= 16 ){
			numBuildingsAllowed.put( Buildings.Farm , 4 );
			numBuildingsAllowed.put( Buildings.Wall , 180 );
			numBuildingsAllowed.put( Buildings.SpikeTrap , 9 );

			numBuildingsAllowed.put( Buildings.Well , 2 );
			numBuildingsAllowed.put( Buildings.Well2 , 2 );
			numBuildingsAllowed.put( Buildings.StatueOracle , 1 );
		}

		if( tcLevel >= 17 ){
			numBuildingsAllowed.put( Buildings.WatchTower , 6 );
			numBuildingsAllowed.put( Buildings.GoldMineBuilding , 4 );
			numBuildingsAllowed.put( Buildings.Wall , 190 );
		}

		if( tcLevel >= 18 ){
			numBuildingsAllowed.put( Buildings.LumberMill , 4 );
			numBuildingsAllowed.put( Buildings.Church , 4 );
			numBuildingsAllowed.put( Buildings.Wall , 200 );
			numBuildingsAllowed.put( Buildings.SpikeTrap , 10 );

			numBuildingsAllowed.put( Buildings.Snowman , 1 );
		}

		if( tcLevel >= 19 ){
			numBuildingsAllowed.put( Buildings.Barracks , 5 );
			numBuildingsAllowed.put( Buildings.Wall , 210 );
			numBuildingsAllowed.put( Buildings.BombTrap , 8 );

			numBuildingsAllowed.put( Buildings.WeaponsSpears , 4 );
			numBuildingsAllowed.put( Buildings.Snowman , 2 );
		}

		if( tcLevel >= 20 ){
			numBuildingsAllowed.put( Buildings.BasicHealingShrine , 5 );
			numBuildingsAllowed.put( Buildings.Wall , 220 );
			numBuildingsAllowed.put( Buildings.SpikeTrap , 11 );

			numBuildingsAllowed.put( Buildings.StatueDragon , 3 );
			numBuildingsAllowed.put( Buildings.StatueDragon2 , 3 );
			numBuildingsAllowed.put( Buildings.Snowman , 3 );
		}
	}

}



/*
 * {
			numBuildingsAllowed.put( Buildings.Barracks , 1 );
			numBuildingsAllowed.put( Buildings.Church , 1 );
			numBuildingsAllowed.put( Buildings.TownCenter , 1 );
			numBuildingsAllowed.put( Buildings.WatchTowerOld , 1 );
			numBuildingsAllowed.put( Buildings.BasicHealingShrine , 1 );
			numBuildingsAllowed.put( Buildings.Farm , 1 );
			numBuildingsAllowed.put( Buildings.LumberMill , 1 );
			numBuildingsAllowed.put( Buildings.GoldMineBuilding , 1 );
			numBuildingsAllowed.put( Buildings.LaserCatShrine , 1 );
			numBuildingsAllowed.put( Buildings.StorageDepot , 1 );
		}
 */
