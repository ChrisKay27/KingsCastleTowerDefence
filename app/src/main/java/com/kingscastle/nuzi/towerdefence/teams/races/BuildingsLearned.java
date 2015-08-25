package com.kingscastle.nuzi.towerdefence.teams.races;

import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Buildings;
import com.kingscastle.nuzi.towerdefence.teams.AllowedBuildings;

public class BuildingsLearned
{

	public static void addBuildingsLearned( AllowedBuildings abs, Race race, int roundNum)
	{
		switch( race.getRace() )
		{
		case HUMAN:
			addHumanBuildingsLearned(abs, race , roundNum);
			break;

//
//		case DWARF:
//			addDwarfBuildingsLearned( abs , race );
//			break;
//
//		case ORC:
//			addOrcBuildingsLearned( abs , race );
//			break;
//
//		case UNDEAD:
//			addUndeadBuildingsLearned( abs , race );
//			break;
//
//		case HOLY:
//			addHumanBuildingsLearned( abs , race );
//			break;

		default:
			break;
		}
	}



	private static void addHumanBuildingsLearned(AllowedBuildings abs, Race race, int roundNum)
	{

		switch ( roundNum )
		{
		case 1:{

			abs.addBuilding( Buildings.Wall );
			abs.addBuilding( Buildings.WatchTower );
			abs.addBuilding( Buildings.Barracks );
			abs.addBuilding( Buildings.FireDragonTower );
			abs.addBuilding( Buildings.IceDragonTower );
			abs.addBuilding( Buildings.ShockDragonTower );
			abs.addBuilding( Buildings.CatapultTower );
			//abs.addBuilding( Buildings.PoisonDragonTower );
			//abs.addBuilding( Buildings.WeaponsSpears );
			//abs.addBuilding( Buildings.StatueArmor );
			//abs.addBuilding( Buildings.StatueArmor2 );

//			abs.addBuilding( Buildings.StatueMonk );
//			abs.addBuilding( Buildings.StatueMonk2 );
//			abs.addBuilding( Buildings.StatueMonk3 );
//			abs.addBuilding( Buildings.StatuePillar );
//			abs.addBuilding( Buildings.StatuePillar2 );
//			abs.addBuilding( Buildings.StatuePillar3 );
//			abs.addBuilding( Buildings.StatueDragon );
//			abs.addBuilding( Buildings.StatueDragon2 );
			//abs.addBuilding( Buildings.StatueOracle );

			//abs.addBuilding( Buildings.RuneStone );

			//abs.addBuilding( Buildings.Snowman );

//			abs.addBuilding( Buildings.Well );
//			abs.addBuilding( Buildings.Well2 );

//			abs.addBuilding( Buildings.Stump );
//			abs.addBuilding( Buildings.Stump2 );
//			abs.addBuilding( Buildings.Stump3 );
//			abs.addBuilding( Buildings.LogMold );
//			abs.addBuilding( Buildings.LogsHorz );
//			abs.addBuilding( Buildings.LogsVert );
//
//			abs.addBuilding( Buildings.Hay );
//			abs.addBuilding( Buildings.Grains );
			//abs.addBuilding( Buildings.Scarecrow );

//			abs.addBuilding( Buildings.Tree );
//			abs.addBuilding( Buildings.Tree2 );
//			abs.addBuilding( Buildings.Shrub );
//			abs.addBuilding( Buildings.Flowers );
//			abs.addBuilding( Buildings.Flowers2 );

//			abs.addBuilding( Buildings.SpikeTrap );
//			abs.addBuilding( Buildings.BombTrap );
//			abs.addBuilding( race.getMyVersionOfA(Buildings.Barracks) );
//			abs.addBuilding( race.getMyVersionOfA(Buildings.Farm) );
			//abs.addBuilding( race.getMyVersionOfA(Buildings.WatchTowerOld) );
			//abs.addBuilding( race.getMyVersionOfA(Buildings.Church) );
			//abs.addBuilding( race.getMyVersionOfA(Buildings.BasicHealingShrine) );
//			abs.addBuilding( race.getMyVersionOfA(Buildings.StorageDepot) );
//			abs.addBuilding( race.getMyVersionOfA(Buildings.LumberMill) );
//			abs.addBuilding( race.getMyVersionOfA(Buildings.GoldMineBuilding) );

			//Bronze
			//abs.addBuilding( Buildings.GuardTower );
			//abs.addBuilding( Buildings.Wall );

			//Iron
			//abs.addBuilding( Buildings.StoneTower );

			//Steel
			//abs.addBuilding( Buildings.LaserCatShrine );



			break;
		}
		case 2:{
//			Barracks b = new Barracks(); b.upgradeToAge(Age.BRONZE); abs.replace( b );
//			Church c = new Church(); c.upgradeToAge(Age.BRONZE); abs.replace( c );

			//			abs.addBuilding( Buildings.GuardTower );
			//			abs.addBuilding( Buildings.Walls );

			break;
		}
		case 3:{
//			Barracks b = new Barracks(); b.upgradeToAge(Age.IRON); abs.replace( b );
//			Church c = new Church(); c.upgradeToAge(Age.IRON); abs.replace( c );
			//
			//			abs.addBuilding( Buildings.StoneTower );
			//			abs.addBuilding( Buildings.HealingShrine );
			//abs.addBuilding( Buildings.StoneTower );
			//abs.addBuilding( Buildings.DiagonalNEWall );
			//abs.addBuilding( Buildings.DiagonalNWWall );
			//abs.addBuilding( Buildings.HorizontalWall );
			//abs.addBuilding( Buildings.VerticalWall );
			break;
		}
		case 4:{
//			Barracks b = new Barracks(); b.upgradeToAge(Age.STEEL); abs.replace( b );
//			Church c = new Church(); c.upgradeToAge(Age.STEEL); abs.replace( c );

			//			if( !Settings.yourBaseMode ){
			//				TownCenter tc = new TownCenter(); tc.upgradeToAge(Age.STEEL); abs.replace( tc );
			//			}
			//
			//			abs.addBuilding( Buildings.LaserCatShrine );
			break;
		}
		default:
			break;

		}

	}

/*

	private static void addUndeadBuildingsLearned(AllowedBuildings abs, Race race) {

		switch ( age )
		{
		case STONE:
			abs.addBuilding( Buildings.WeaponsSpears );
			abs.addBuilding( Buildings.StatueArmor );
			abs.addBuilding( Buildings.StatueArmor2 );

			abs.addBuilding( Buildings.StatueMonk );
			abs.addBuilding( Buildings.StatueMonk2 );
			abs.addBuilding( Buildings.StatueMonk3 );
			abs.addBuilding( Buildings.StatuePillar );
			abs.addBuilding( Buildings.StatuePillar2 );
			abs.addBuilding( Buildings.StatuePillar3 );
			abs.addBuilding( Buildings.StatueDragon );
			abs.addBuilding( Buildings.StatueDragon2 );
			abs.addBuilding( Buildings.StatueOracle );

			abs.addBuilding( Buildings.RuneStone );

			abs.addBuilding( Buildings.Snowman );

			abs.addBuilding( Buildings.Well );
			abs.addBuilding( Buildings.Well2 );

			abs.addBuilding( Buildings.Stump );
			abs.addBuilding( Buildings.Stump2 );
			abs.addBuilding( Buildings.Stump3 );
			abs.addBuilding( Buildings.LogMold );
			abs.addBuilding( Buildings.LogsHorz );
			abs.addBuilding( Buildings.LogsVert );

			abs.addBuilding( Buildings.Hay );
			abs.addBuilding( Buildings.Grains );
			abs.addBuilding( Buildings.Scarecrow );

			abs.addBuilding( Buildings.Tree );
			abs.addBuilding( Buildings.Tree2 );
			abs.addBuilding( Buildings.Shrub );
			abs.addBuilding( Buildings.Flowers );
			abs.addBuilding( Buildings.Flowers2 );

			abs.addBuilding( Buildings.SpikeTrap );
			abs.addBuilding( Buildings.BombTrap );
			abs.addBuilding( race.getMyVersionOfA(Buildings.Barracks) );
			abs.addBuilding( race.getMyVersionOfA(Buildings.Farm) );
			abs.addBuilding( race.getMyVersionOfA(Buildings.WatchTowerOld) );
			abs.addBuilding( race.getMyVersionOfA(Buildings.Church) );
			abs.addBuilding( race.getMyVersionOfA(Buildings.BasicHealingShrine) );
			abs.addBuilding( race.getMyVersionOfA(Buildings.StorageDepot) );
			abs.addBuilding( race.getMyVersionOfA(Buildings.LumberMill) );
			abs.addBuilding( race.getMyVersionOfA(Buildings.GoldMineBuilding) );
			//			abs.addBuilding( Buildings.UndeadBarracks );
			//			abs.addBuilding( Buildings.PileOCorps );
			//			abs.addBuilding( Buildings.UndeadWatchTower );
			//			abs.addBuilding( Buildings.UndeadChurch );
			//			abs.addBuilding( Buildings.BasicHealingShrine );
			//			abs.addBuilding( Buildings.LumberMill );
			//			abs.addBuilding( Buildings.GoldMineBuilding );

			abs.addBuilding( Buildings.UndeadGuardTower );
			abs.addBuilding( Buildings.UndeadStorageDepot );
			abs.addBuilding( Buildings.Wall );


			abs.addBuilding( Buildings.UndeadStoneTower  );
			abs.addBuilding( Buildings.EvilHealingShrine );


			abs.addBuilding( Buildings.UndeadTownCenter );
			abs.addBuilding( Buildings.LaserCatShrine );
			break;

		case BRONZE:{
			UndeadBarracks b = new UndeadBarracks(); b.upgradeToAge(Age.BRONZE); abs.replace( b );
			UndeadChurch c = new UndeadChurch(); c.upgradeToAge(Age.BRONZE); abs.replace( c );

			break;
		}
		case IRON:{
			UndeadBarracks b = new UndeadBarracks(); b.upgradeToAge(Age.IRON); abs.replace( b );
			UndeadChurch c = new UndeadChurch(); c.upgradeToAge(Age.IRON); abs.replace( c );

			break;
		}
		case STEEL:{
			UndeadBarracks b = new UndeadBarracks(); b.upgradeToAge(Age.STEEL); abs.replace( b );
			UndeadChurch c = new UndeadChurch(); c.upgradeToAge(Age.STEEL); abs.replace( c );

			break;
		}
		default:
			break;

		}

	}






	private static void addOrcBuildingsLearned(AllowedBuildings abs, Race race)
	{

		switch ( age )
		{

		case STONE:
			abs.addBuilding( Buildings.Barracks );
			abs.addBuilding( Buildings.Farm );
			abs.addBuilding( Buildings.WatchTowerOld );
			abs.addBuilding( Buildings.Church );
			abs.addBuilding( Buildings.BasicHealingShrine );
			break;


		case BRONZE:

			abs.addBuilding( Buildings.GuardTower );
			abs.addBuilding( Buildings.StorageDepot );
			abs.addBuilding( Buildings.Wall );
			break;


		case IRON:
			abs.addBuilding( Buildings.StoneTower );
			//abs.addBuilding( Buildings.DiagonalNEWall );
			//abs.addBuilding( Buildings.DiagonalNWWall );
			//abs.addBuilding( Buildings.HorizontalWall );
			//abs.addBuilding( Buildings.VerticalWall );
			break;

		case STEEL:
			abs.addBuilding( Buildings.TownCenter );

			break;


		default:
			break;

		}

	}



	private static void addDwarfBuildingsLearned(AllowedBuildings abs, Race race) {
		if( true )
			throw new WtfException("Dwarf race not included in this version");
		switch ( age )
		{
		case STONE:
			abs.addBuilding( Buildings.WeaponsSpears );
			abs.addBuilding( Buildings.StatueArmor );
			abs.addBuilding( Buildings.StatueArmor2 );

			abs.addBuilding( Buildings.StatueMonk );
			abs.addBuilding( Buildings.StatueMonk2 );
			abs.addBuilding( Buildings.StatueMonk3 );
			abs.addBuilding( Buildings.StatuePillar );
			abs.addBuilding( Buildings.StatuePillar2 );
			abs.addBuilding( Buildings.StatuePillar3 );
			abs.addBuilding( Buildings.StatueDragon );
			abs.addBuilding( Buildings.StatueDragon2 );
			abs.addBuilding( Buildings.StatueOracle );

			abs.addBuilding( Buildings.RuneStone );

			abs.addBuilding( Buildings.Snowman );

			abs.addBuilding( Buildings.Well );
			abs.addBuilding( Buildings.Well2 );

			abs.addBuilding( Buildings.Stump );
			abs.addBuilding( Buildings.Stump2 );
			abs.addBuilding( Buildings.Stump3 );
			abs.addBuilding( Buildings.LogMold );
			abs.addBuilding( Buildings.LogsHorz );
			abs.addBuilding( Buildings.LogsVert );

			abs.addBuilding( Buildings.Hay );
			abs.addBuilding( Buildings.Grains );
			abs.addBuilding( Buildings.Scarecrow );

			abs.addBuilding( Buildings.Tree );
			abs.addBuilding( Buildings.Tree2 );
			abs.addBuilding( Buildings.Shrub );
			abs.addBuilding( Buildings.Flowers );
			abs.addBuilding( Buildings.Flowers2 );

			abs.addBuilding( Buildings.SpikeTrap );
			abs.addBuilding( Buildings.BombTrap );
			abs.addBuilding( Buildings.DwarfBarracks );
			abs.addBuilding( Buildings.Farm );
			abs.addBuilding( Buildings.WatchTowerOld );
			abs.addBuilding( Buildings.DwarfMushroomFarm );
			abs.addBuilding( Buildings.BasicHealingShrine );

			abs.addBuilding( Buildings.GuardTower );
			abs.addBuilding( Buildings.StorageDepot );
			abs.addBuilding( Buildings.Wall );

			abs.addBuilding( Buildings.DwarfTower );
			abs.addBuilding( Buildings.HealingShrine );

			abs.addBuilding( Buildings.DwarfTownCenter );
			abs.addBuilding( Buildings.LaserCatShrine );
			break;
			//		case BRONZE:{
			//			DwarfBarracks b = new DwarfBarracks(); b.upgradeToAge(Age.BRONZE); abs.replace( b );
			//			DwarfChurch c = new DwarfChurch(); c.upgradeToAge(Age.BRONZE); abs.replace( c );
			//
			//			break;
			//		}
			//		case IRON:{
			//			DwarfBarracks b = new DwarfBarracks(); b.upgradeToAge(Age.IRON); abs.replace( b );
			//			DwarfChurch c = new DwarfChurch(); c.upgradeToAge(Age.IRON); abs.replace( c );
			//
			//			break;
			//		}
			//		case STEEL:{
			//			DwarfBarracks b = new DwarfBarracks(); b.upgradeToAge(Age.STEEL); abs.replace( b );
			//			DwarfChurch c = new DwarfChurch(); c.upgradeToAge(Age.STEEL); abs.replace( c );
			//
			//			break;
			//		}
		default:
			break;

		}

	}
*/
















}
