package com.kingscastle.nuzi.towerdefence.teams.races;


import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.SoldierType;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.Catapult;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.HumanArcher;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.HumanArmoredSoldier;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.HumanBoresArcher;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.HumanElementalWizard;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.HumanLongBowMan;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.HumanScout;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.HumanSoldier;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.HumanWizard;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.Knight;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.Medic;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.Priestess;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.Warrior;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.WhiteWizard;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Barracks;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.BasicHealingShrine;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.BuildableUnits;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Building;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Buildings;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Church;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.GuardTower;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.HealingShrine;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.LaserCatShrine;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.StoneTower;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.StorageDepot;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Wall;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.WatchTower;
import com.kingscastle.nuzi.towerdefence.gameUtils.Age;

public class HumanRace extends Race
{
	private static HumanRace singularity;

	public static HumanRace getInstance()
	{
		if( singularity == null )
			singularity = new HumanRace();

		return singularity;
	}



	private BuildableUnits stoneAgeBarracksUnits;
	private  BuildableUnits bronzeAgeBarracksUnits;
	private  BuildableUnits ironAgeBarracksUnits;
	private  BuildableUnits steelAgeBarracksUnits;


	private  BuildableUnits stoneAgeChurchUnits;
	private  BuildableUnits bronzeAgeChurchUnits;
	private  BuildableUnits ironAgeChurchUnits;
	private  BuildableUnits steelAgeChurchUnits;


	private  BuildableUnits stoneAgeTownCenterUnits;
	private  BuildableUnits bronzeAgeTownCenterUnits;
	private  BuildableUnits ironAgeTownCenterUnits;
	private  BuildableUnits steelAgeTownCenterUnits;



	{

		Warrior  warrior = new Warrior();
		HumanScout scout = new HumanScout();
		Medic      medic = new Medic();

		HumanSoldier soldier    = new HumanSoldier();
		HumanArcher archer           = new HumanArcher();
		WhiteWizard whiteWizard = new WhiteWizard();


		HumanArmoredSoldier has = new HumanArmoredSoldier();
		HumanElementalWizard hew = new HumanElementalWizard();
		HumanBoresArcher hba     = new HumanBoresArcher();


		HumanLongBowMan armoredArcher = new HumanLongBowMan();
		Knight knight                 = new Knight();
		Catapult catapult             = new Catapult();
		Priestess priestess           = new Priestess();
		HumanWizard galdolf           = new HumanWizard();


		//		stoneAgeBarracksUnits  = new BuildableUnits( warrior , scout );
		//		bronzeAgeBarracksUnits = new BuildableUnits( warrior , scout , soldier , archer );
		//		ironAgeBarracksUnits   = new BuildableUnits( warrior , scout , soldier , archer , has , hba );
		steelAgeBarracksUnits  = new BuildableUnits( warrior , scout , soldier , archer , has , hba , catapult , knight , armoredArcher );
		stoneAgeBarracksUnits = bronzeAgeBarracksUnits = ironAgeBarracksUnits = steelAgeBarracksUnits;

		stoneAgeTownCenterUnits = new BuildableUnits( warrior );
		bronzeAgeTownCenterUnits = ironAgeTownCenterUnits = steelAgeTownCenterUnits = stoneAgeTownCenterUnits;
		//bronzeAgeTownCenterUnits = new BuildableUnits( worker , scout );
		//ironAgeTownCenterUnits = new BuildableUnits( worker , scout );
		//steelAgeTownCenterUnits = new BuildableUnits( worker , scout );


		//		stoneAgeChurchUnits  = new BuildableUnits( medic );
		//		bronzeAgeChurchUnits = new BuildableUnits( medic , whiteWizard );
		//		ironAgeChurchUnits   = new BuildableUnits( medic , whiteWizard , hew );
		steelAgeChurchUnits  = new BuildableUnits( medic , whiteWizard , hew , priestess , galdolf );
		stoneAgeChurchUnits = bronzeAgeChurchUnits = ironAgeChurchUnits = steelAgeChurchUnits;
	}



	@Override
	public SoldierType getMy( GeneralSoldierType soldierType )
	{

		switch( soldierType )
		{


		case BASIC_HEALER:
			return SoldierType.MEDIC;
		case ADVANCED_HEALER:
			return SoldierType.PRIESTESS;



		case BASIC_MELEE_SOLDIER:
			return SoldierType.WARRIOR;
		case MEDIUM_MELEE_SOLDIER:
			return SoldierType.SOLDIER;
		case UPPER_MELEE_SOLDIER:
			return SoldierType.KNIGHT;
		case ADVANCED_MELEE_SOLDIER:
			return SoldierType.KNIGHT;



		case BASIC_RANGED_SOLDIER:
			return SoldierType.SCOUT;
		case MEDIUM_RANGED_SOLDIER:
			return SoldierType.ARCHER;
		case ADVANCED_RANGED_SOLDIER:
			return SoldierType.LONGBOWMAN;




		case UPPER_MAGE_SOLDIER:
			return SoldierType.WIZARD;
		case ADVANCED_MAGE_SOLDIER:
			return SoldierType.WIZARD;

		case CATAPULT:
			return SoldierType.CATAPULT;

		default:
			break;

		}
		return null;
	}



	@Override
	public LivingThing getMyVersionOfA( GeneralSoldierType soldierType )
	{
		switch( soldierType )
		{

		case BASIC_HEALER:
			return new Medic();
		case ADVANCED_HEALER:
			return new Priestess();



		case BASIC_MELEE_SOLDIER:
			return new Warrior();
		case MEDIUM_MELEE_SOLDIER:
			return new HumanSoldier();
		case UPPER_MELEE_SOLDIER:
			return new HumanArmoredSoldier();
		case ADVANCED_MELEE_SOLDIER:
			return new Knight();



		case BASIC_RANGED_SOLDIER:
			return new HumanScout();
		case MEDIUM_RANGED_SOLDIER:
			return new HumanArcher();
		case UPPER_RANGED_SOLDIER:
			return new HumanBoresArcher();
		case ADVANCED_RANGED_SOLDIER:
			return new HumanLongBowMan();



		case MEDIUM_MAGE_SOLDIER:
			return new WhiteWizard();
		case UPPER_MAGE_SOLDIER:
			return new HumanElementalWizard();
		case ADVANCED_MAGE_SOLDIER:
			return new HumanWizard();


		case CATAPULT:
			return new Catapult();



		default:
			return null;
		}
	}



	@Override
	public Building getMyVersionOfA(Buildings building)
	{
		switch( building )
		{
		case UndeadBarracks:
		case DwarfBarracks:
		case Barracks:
			return new Barracks();

		case DwarfMushroomFarm:
		case UndeadChurch:
		case Church:
			return new Church();

			//
			//		case WallHorzA:
			//			return new WallHorzA();
			//		case WallHorzB:
			//			return new WallHorzB();
			//		case WallHorzC:
			//			return new WallHorzC();
			//
			//
			//		case WallVertA:
			//			return new WallVertA();
			//		case WallVertB:
			//			return new WallVertB();



		case BasicHealingShrine:
			return new BasicHealingShrine();

		case EvilHealingShrine:
		case HealingShrine:
			return new HealingShrine();

		case LaserCatShrine:
			return new LaserCatShrine();

		case GuardTower:
			return new GuardTower();

		case Wall:
			return new Wall();

		case UndeadStoneTower:
		case DwarfTower:
		case StoneTower:
			return new StoneTower();

		case UndeadStorageDepot:
		case StorageDepot:
			return new StorageDepot();

//		case UndeadTownCenter:
//		case DwarfTownCenter:
//		case TownCenter:
//			return new TownCenter();


		case UndeadWatchTower:
		case WatchTower:
			return new WatchTower();

		default:
			break;

		}
		return new WatchTower();
	}


	@Override
	public  BuildableUnits getUnitsFor( Buildings building , Age age )
	{

		switch( building )
		{
		case UndeadBarracks:
		case Barracks:
		case DwarfBarracks:
			return getBarracksUnitsFor ( age );

		case DwarfMushroomFarm:
		case UndeadChurch:
		case Church:
			return getChurchUnitsFor ( age );


		case DwarfTownCenter:
		case UndeadTownCenter:
		case TownCenter:
			return getTownCenterUnitsFor ( age );


		default:
			return null;
		}


	}



	BuildableUnits getChurchUnitsFor(Age age)
	{
		switch( age )
		{
		default:
		case STONE:
			return getStoneAgeChurchUnits();

		case BRONZE:
			return getBronzeAgeChurchUnits();

		case IRON:
			return getIronAgeChurchUnits();

		case STEEL:
			return getSteelAgeChurchUnits();

		}
	}




	BuildableUnits getTownCenterUnitsFor(Age age)
	{

		switch( age )
		{
		default:
		case STONE:
			return getStoneAgeTownCenterUnits();

		case BRONZE:
			return getBronzeAgeTownCenterUnits();

		case IRON:
			return getIronAgeTownCenterUnits();

		case STEEL:
			return getSteelAgeTownCenterUnits();

		}
	}




	BuildableUnits getBarracksUnitsFor(Age age)
	{
		switch( age )
		{
		default:
		case STONE:
			return getStoneAgeBarracksUnits();

		case BRONZE:
			return getBronzeAgeBarracksUnits();

		case IRON:
			return getIronAgeBarracksUnits();

		case STEEL:
			return getSteelAgeBarracksUnits();

		}
	}












	BuildableUnits getStoneAgeBarracksUnits() {
		return stoneAgeBarracksUnits;
	}

	public  void setStoneAgeBarracksUnits(BuildableUnits stoneAgeBarracksUnits) {
		this.stoneAgeBarracksUnits = stoneAgeBarracksUnits;
	}

	BuildableUnits getBronzeAgeBarracksUnits() {
		return bronzeAgeBarracksUnits;
	}

	public  void setBronzeAgeBarracksUnits(BuildableUnits bronzeAgeBarracksUnits) {
		this.bronzeAgeBarracksUnits = bronzeAgeBarracksUnits;
	}

	BuildableUnits getIronAgeBarracksUnits() {
		return ironAgeBarracksUnits;
	}

	public  void setIronAgeBarracksUnits(BuildableUnits ironAgeBarracksUnits) {
		this.ironAgeBarracksUnits = ironAgeBarracksUnits;
	}

	BuildableUnits getSteelAgeBarracksUnits() {
		return steelAgeBarracksUnits;
	}

	public  void setSteelAgeBarracksUnits(BuildableUnits steelAgeBarracksUnits) {
		this.steelAgeBarracksUnits = steelAgeBarracksUnits;
	}



	BuildableUnits getStoneAgeChurchUnits() {
		return stoneAgeChurchUnits;
	}



	public  void setStoneAgeChurchUnits(BuildableUnits stoneAgeChurchUnits) {
		this.stoneAgeChurchUnits = stoneAgeChurchUnits;
	}



	BuildableUnits getBronzeAgeChurchUnits() {
		return bronzeAgeChurchUnits;
	}



	public  void setBronzeAgeChurchUnits(BuildableUnits bronzeAgeChurchUnits) {
		this.bronzeAgeChurchUnits = bronzeAgeChurchUnits;
	}



	BuildableUnits getIronAgeChurchUnits() {
		return ironAgeChurchUnits;
	}



	public  void setIronAgeChurchUnits(BuildableUnits ironAgeChurchUnits) {
		this.ironAgeChurchUnits = ironAgeChurchUnits;
	}



	BuildableUnits getSteelAgeChurchUnits() {
		return steelAgeChurchUnits;
	}



	public  void setSteelAgeChurchUnits(BuildableUnits steelAgeChurchUnits) {
		this.steelAgeChurchUnits = steelAgeChurchUnits;
	}



	BuildableUnits getStoneAgeTownCenterUnits() {
		return stoneAgeTownCenterUnits;
	}



	public  void setStoneAgeTownCenterUnits(
			BuildableUnits stoneAgeTownCenterUnits) {
		this.stoneAgeTownCenterUnits = stoneAgeTownCenterUnits;
	}



	BuildableUnits getBronzeAgeTownCenterUnits() {
		return bronzeAgeTownCenterUnits;
	}



	public  void setBronzeAgeTownCenterUnits(
			BuildableUnits bronzeAgeTownCenterUnits) {
		this.bronzeAgeTownCenterUnits = bronzeAgeTownCenterUnits;
	}



	BuildableUnits getIronAgeTownCenterUnits() {
		return ironAgeTownCenterUnits;
	}



	public  void setIronAgeTownCenterUnits(
			BuildableUnits ironAgeTownCenterUnits) {
		this.ironAgeTownCenterUnits = ironAgeTownCenterUnits;
	}



	BuildableUnits getSteelAgeTownCenterUnits() {
		return steelAgeTownCenterUnits;
	}



	public  void setSteelAgeTownCenterUnits(
			BuildableUnits steelAgeTownCenterUnits) {
		this.steelAgeTownCenterUnits = steelAgeTownCenterUnits;
	}



	@Override
	public Races getRace()
	{
		return Races.HUMAN;
	}



	@Override
	public Buildings getMyRacesBuildingType( Buildings building )
	{
		return building;
	}





}
