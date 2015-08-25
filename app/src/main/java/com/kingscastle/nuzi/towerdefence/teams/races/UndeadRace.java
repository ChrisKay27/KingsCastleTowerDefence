package com.kingscastle.nuzi.towerdefence.teams.races;


import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.SoldierType;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.Catapult;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.UndeadDeathKnight;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.UndeadDemogorgon;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.UndeadDullahan;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.UndeadGoldenArcher;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.UndeadHealer;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.UndeadMarshall;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.UndeadPossessed;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.UndeadPossessedKnight;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.UndeadSkeletonArcher;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.UndeadSkeletonBowman;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.UndeadSkeletonScout;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.UndeadSkeletonWarrior;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.UndeadSkullFucqued;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.BasicHealingShrine;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.BuildableUnits;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Building;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Buildings;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.EvilHealingShrine;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.LaserCatShrine;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.UndeadBarracks;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.UndeadGuardTower;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.UndeadStoneTower;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.UndeadStorageDepot;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.UndeadWatchTower;
import com.kingscastle.nuzi.towerdefence.gameUtils.Age;

public class UndeadRace extends Race
{
	private static UndeadRace singularity;

	public static UndeadRace getInstance()
	{
		if( singularity == null )
		{
			singularity = new UndeadRace();
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
		UndeadSkeletonScout     scout = new UndeadSkeletonScout();
		UndeadSkeletonWarrior warrior = new UndeadSkeletonWarrior();
		UndeadHealer medic = new UndeadHealer();


		UndeadDullahan         dull = new UndeadDullahan();
		UndeadMarshall     marshall = new UndeadMarshall();
		UndeadSkeletonArcher archer = new UndeadSkeletonArcher();


		UndeadSkullFucqued     skullFucked = new UndeadSkullFucqued();
		UndeadSkeletonBowman armoredArcher = new UndeadSkeletonBowman();
		UndeadPossessedKnight blackDeath   = new UndeadPossessedKnight();

		UndeadDeathKnight dknight = new UndeadDeathKnight();
		UndeadGoldenArcher uga = new UndeadGoldenArcher();
		UndeadDemogorgon demogordon = new UndeadDemogorgon();
		UndeadPossessed   possessed = new UndeadPossessed();
		Catapult c = new Catapult();


		stoneAgeBarracksUnits  = new BuildableUnits( warrior , scout );
		bronzeAgeBarracksUnits = new BuildableUnits( warrior , scout , marshall , archer );
		ironAgeBarracksUnits   = new BuildableUnits( warrior , scout , marshall , archer , skullFucked , armoredArcher );
		steelAgeBarracksUnits  = new BuildableUnits( warrior , scout , marshall , archer , skullFucked , armoredArcher , dknight , uga , c );





		stoneAgeTownCenterUnits = new BuildableUnits( warrior );
		bronzeAgeTownCenterUnits = ironAgeTownCenterUnits = steelAgeTownCenterUnits = stoneAgeTownCenterUnits;
		//bronzeAgeTownCenterUnits = new BuildableUnits( worker , scout );
		//ironAgeTownCenterUnits = new BuildableUnits( worker , scout );
		//steelAgeTownCenterUnits = new BuildableUnits( worker , scout );



		stoneAgeChurchUnits  = new BuildableUnits( medic );
		bronzeAgeChurchUnits = new BuildableUnits( medic , dull );
		ironAgeChurchUnits   = new BuildableUnits( medic , dull , blackDeath );
		steelAgeChurchUnits  = new BuildableUnits( medic , dull , blackDeath , possessed , demogordon );
	}



	@Override
	public SoldierType getMy( GeneralSoldierType soldierType )
	{
		switch( soldierType )
		{

		case WORKER:
			return SoldierType.UNDEADWORKER;


		case BASIC_HEALER:
			return SoldierType.UNDEADHEALER;

		case ADVANCED_HEALER:
			return SoldierType.UNDEADDEMOGORGON;


		case BASIC_MELEE_SOLDIER:
			return SoldierType.UNDEADSKELETONWARRIOR;

		case MEDIUM_MELEE_SOLDIER:
			return SoldierType.UNDEADMARSHALL;

		case ADVANCED_MELEE_SOLDIER:
			return SoldierType.UNDEADSKULLFUCQUED;


		case BASIC_RANGED_SOLDIER:
			return SoldierType.UNDEADSKELETONSCOUT;

		case MEDIUM_RANGED_SOLDIER:
			return SoldierType.UNDEADSKELETONARCHER;

		case ADVANCED_RANGED_SOLDIER:
			return SoldierType.UNDEADSKELETONBOWMAN;

		case UPPER_MAGE_SOLDIER:
			return SoldierType.BLACKDEATH;

		case ADVANCED_MAGE_SOLDIER:
			return SoldierType.BLACKDEATH;

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
			return new UndeadHealer();
		case ADVANCED_HEALER:
			return new UndeadDemogorgon();




		case BASIC_MELEE_SOLDIER:
			return new UndeadSkeletonWarrior();
		case MEDIUM_MELEE_SOLDIER:
			return new UndeadMarshall();
		case UPPER_MELEE_SOLDIER:
			return new UndeadSkullFucqued();
		case ADVANCED_MELEE_SOLDIER:
			return new UndeadSkullFucqued();



			// Ranged
		case BASIC_RANGED_SOLDIER:
			return new UndeadSkeletonScout();
		case MEDIUM_RANGED_SOLDIER:
			return new UndeadSkeletonArcher();
		case UPPER_RANGED_SOLDIER:
			return new UndeadSkeletonBowman();
		case ADVANCED_RANGED_SOLDIER:
			return new UndeadGoldenArcher();




		case MEDIUM_MAGE_SOLDIER:
			return new UndeadDullahan();
		case UPPER_MAGE_SOLDIER:
			return new UndeadPossessedKnight();
		case ADVANCED_MAGE_SOLDIER:
			return new UndeadPossessed();



		case CATAPULT:
			return new Catapult();


		default:
			return null;

		}
	}





	@Override
	public Buildings getMyRacesBuildingType( Buildings building )
	{

		switch( building )
		{
		case UndeadBarracks:
		case DwarfBarracks:
		case Barracks:
			return Buildings.UndeadBarracks;

		case DwarfMushroomFarm:
		case UndeadChurch:
		case Church:
			return Buildings.UndeadChurch;



		case PileOCorps:
		case Farm:
			return Buildings.PileOCorps;


		case UndeadGuardTower:
		case GuardTower:
			return Buildings.UndeadGuardTower;



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


		case PendingBuilding:
			return Buildings.PendingBuilding;


		case UndeadStoneTower:
		case DwarfTower:
		case StoneTower:
			return Buildings.UndeadStoneTower;

		case UndeadStorageDepot:
		case StorageDepot:
			return Buildings.UndeadStorageDepot;

		case UndeadTownCenter:
		case DwarfTownCenter:
		case TownCenter:
			return Buildings.UndeadTownCenter;



		case UndeadWatchTower:
		case WatchTower:
			return Buildings.UndeadWatchTower;


		case BasicHealingShrine:
			return Buildings.BasicHealingShrine;

		case EvilHealingShrine:
		case HealingShrine:
			return Buildings.EvilHealingShrine;

		case LaserCatShrine:
			return Buildings.LaserCatShrine;


		default:
			break;

		}

		return Buildings.PileOCorps;
	}



	@Override
	public Building getMyVersionOfA( Buildings building )
	{
		switch( building )
		{

		case UndeadBarracks:
		case Barracks:
			return new UndeadBarracks();

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



		case PileOCorps:



		case UndeadGuardTower:
		case GuardTower:
			return new UndeadGuardTower();

		case UndeadStoneTower:
		case StoneTower:
			return new UndeadStoneTower();

		case UndeadStorageDepot:
		case StorageDepot:
			return new UndeadStorageDepot();


		case UndeadWatchTower:
		case WatchTower:
			return new UndeadWatchTower();

		case BasicHealingShrine:
			return new BasicHealingShrine();

		case EvilHealingShrine:
		case HealingShrine:
			return new EvilHealingShrine();

		case LaserCatShrine:
			return new LaserCatShrine();

		default:
			break;

		}

		return null;
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
		return Races.UNDEAD;
	}








}
