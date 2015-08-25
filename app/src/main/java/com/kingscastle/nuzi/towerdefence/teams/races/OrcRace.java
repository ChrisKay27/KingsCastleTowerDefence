package com.kingscastle.nuzi.towerdefence.teams.races;


import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.SoldierType;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.HumanArcher;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.HumanLongBowMan;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.HumanScout;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.HumanSoldier;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.Knight;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.Medic;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.Priestess;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.Warrior;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Barracks;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.BuildableUnits;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Building;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Buildings;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Church;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.GuardTower;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.StoneTower;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.StorageDepot;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.WatchTower;
import com.kingscastle.nuzi.towerdefence.gameUtils.Age;

public class OrcRace extends Race
{
	private static OrcRace singularity;

	public static OrcRace getInstance()
	{
		if( singularity == null )
		{
			singularity = new OrcRace();
		}

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
		Warrior warrior = new Warrior();

		HumanSoldier soldier = new HumanSoldier();
		HumanArcher archer = new HumanArcher();

		HumanLongBowMan armoredArcher = new HumanLongBowMan();
		Knight knight = new Knight();

		stoneAgeBarracksUnits = new BuildableUnits( warrior );
		bronzeAgeBarracksUnits = new BuildableUnits( warrior , soldier, archer );
		ironAgeBarracksUnits = new BuildableUnits( soldier , archer , armoredArcher );
		steelAgeBarracksUnits = new BuildableUnits( soldier , knight , archer , armoredArcher );

		HumanScout scout = new HumanScout();

		stoneAgeTownCenterUnits = new BuildableUnits( scout );
		bronzeAgeTownCenterUnits = ironAgeTownCenterUnits = steelAgeTownCenterUnits = stoneAgeTownCenterUnits;
		//		bronzeAgeTownCenterUnits = new BuildableUnits( worker , scout );
		//		ironAgeTownCenterUnits = new BuildableUnits( worker , scout );
		//		steelAgeTownCenterUnits = new BuildableUnits( worker , scout );

		Medic medic = new Medic();
		Priestess priestess = new Priestess();

		stoneAgeChurchUnits = new BuildableUnits( medic );
		bronzeAgeChurchUnits = new BuildableUnits( medic );
		ironAgeChurchUnits = new BuildableUnits( medic , priestess );
		steelAgeChurchUnits = new BuildableUnits( priestess );
	}



	@Override
	public SoldierType getMy( GeneralSoldierType soldierType )
	{
		return null;
	}



	@Override
	public LivingThing getMyVersionOfA( GeneralSoldierType soldierType )
	{
		return null;
	}


	@Override
	public Buildings getMyRacesBuildingType( Buildings building )
	{


		switch( building )
		{
		case Barracks:
			return Buildings.DwarfBarracks;

		case Church:
			return Buildings.DwarfMushroomFarm;

		case Farm:
			return Buildings.Farm;


		case WallHorzA:
			return Buildings.WallHorzA;
		case WallHorzB:
			return Buildings.WallHorzB;
		case WallHorzC:
			return Buildings.WallHorzC;



		case WallVertA:
			return Buildings.WallVertA;
		case WallVertB:
			return Buildings.WallVertB;


		case GuardTower:

			return Buildings.GuardTower;



		case PendingBuilding:
			return Buildings.PendingBuilding;


		case StoneTower:
			return Buildings.StoneTower;

		case StorageDepot:
			return Buildings.StorageDepot;

		case TownCenter:
			return Buildings.DwarfTownCenter;



		case WatchTower:
			return Buildings.WatchTower;

		default:
			break;

		}
		return building;
	}


	@Override
	public Building getMyVersionOfA(Buildings building)
	{
		switch( building )
		{
		case Barracks:
			return new Barracks();

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



		case GuardTower:

			return new GuardTower();


		case StoneTower:
			return new StoneTower();

		case StorageDepot:
			return new StorageDepot();

//		case TownCenter:
//			return new TownCenter();



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
		return Races.ORC;
	}



}
