package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings;



import java.util.ArrayList;


public enum Buildings
{

	WatchTower       (  "Watch Tower" ),
	UndeadWatchTower (  "Watch Tower" ),
	GuardTower       (  "Guard Tower" ),
	UndeadGuardTower (  "Guard Tower" ),
	StoneTower       (  "Stone Tower" ),
	UndeadStoneTower (  "Stone Tower" ),
	DwarfTower       (  "Dwarf Tower" ),


	Farm	         ( "Farm" ),
	PileOCorps       (  "Pile-O-Corps" ),

	TownCenter       (  "TownCenter" ),
	DwarfTownCenter  (  "Town Center" ),
	UndeadTownCenter (  "Town Center" ),

	Barracks         (  "Barracks" ),
	DwarfBarracks    (  "Barracks" ),
	UndeadBarracks   (  "Barracks" ),

	StorageDepot       ( "Depot" ),
	UndeadStorageDepot ( "Depot" ),

	Church              ("Church" ),
	DwarfMushroomFarm   ("Mushroom Farm" ),
	UndeadChurch        ("Church" ),

	ResearchBuilding    ("Research Building" ),

	PendingBuilding     ("Pending Building" ),



	Inn		         ("Watch Tower" ),
	Stables	         ("Watch Tower" ),
	BlackSmith       ("Watch Tower" ),
	StoneHenge       ("Watch Tower" ),
	WindMill         ("Watch Tower" ),
	Temple           ("Watch Tower" ),
	Crypt            ("Watch Tower" ),
	GraveYard        ("Watch Tower" ),


	WallHorzA         ("WallHorzA" ),
	WallHorzB         ("WallHorzB" ),
	WallHorzC         ("WallHorzC" ),
	WallVertA         ("WallVertA" ),
	WallVertB         ("WallVertB" ),
	WallEndA          ("WallEndA"  ),
	WallEndB          ("WallEndB"  ),
	StoneWall         ("Stone Wall"),
	Tower             ("Tower"     ),

	BasicHealingShrine("Basic Healing Shrine" ),
	HealingShrine     ("Healing Shrine" ),
	EvilHealingShrine ("Evil Healing Shrine" ),
	PoisonShrine      ("Poison Shrine" ),
	LightningShrine   ("Lightning Shrine" ),
	LaserShrine       ("Laser Shrine" ),
	DamageShrine      ("Damage Shrine" ),
	ProductionShrine  ("Production Shrine" ),
	ShieldShrine      ("Shield Shrine" ),
	LaserCatShrine    ("Laser Cat Shrine" ),
	DragonStatue      ("DragonStatue" ),
	Wall			  ("Wall" ),
	GoldMineBuilding  ("Gold Mine" ),
	LumberMill		  ("Lumber Mill" ),

	BombTrap		  ("Bomb Trap" ),
	SpikeTrap		  ("Spike Trap" ),



	TreasureChest	  ("Treasure Chest" ),

	WeaponsSpears	  ("Spears" ),
	StatueArmor		  ("Armor Statue" ),
	StatueArmor2	  ("Armor Statue" ),

	StatueMonk		  ("Monk Statue" ),
	StatueMonk2		  ("Monk Statue" ),
	StatueMonk3		  ("Monk Statue" ),
	StatueCross		  ("Cross Statue" ),
	StatueGoldScepter ("Scepter Statue" ),
	StatueDragon  	  ("Dragon Statue" ),
	StatueDragon2  	  ("Dragon Statue" ),
	StatueOracle  	  ("Oracle Statue" ),

	RuneStone	 	  ("Rune Stone" ),

	Snowman	 		  ("Snow Man" ),

	Monument	 	  ("Monument" ),
	Monument2	 	  ("Monument" ),

	Well	 		  ("Well" ),
	Well2 			  ("Well" ),


	StatuePillar	  ("Pillar" ),
	StatuePillar2 	  ("Pillar" ),
	StatuePillar3 	  ("Pillar" ),


	Shrub	 	 	  ("Shrub" ),
	Flowers	 	 	  ("Flowers" ),
	Flowers2	 	  ("Flowers" ),
	Tree	 	  	  ("Tree" ),
	Tree2	 	  	  ("Tree" ),

	Hay	 	  	 	  ("Hay" ),
	Grains	  	 	  ("Grains" ),
	Scarecrow	 	  ("Scarecrow" ),


	Stump	 		  ("Stump" ),
	Stump2 			  ("Stump" ),
	Stump3 			  ("Stump" ),
	Log	 	  	 	  ("Log" ),
	LogMold	 	  	  ("Moldy Log" ),
	LogsHorz 	  	  ("Logs" ),
	LogsVert 	  	  ("Logs" ),



	Building		  ( "Building" ),
	FastTower("Fast Tower" ),
	DragonTower("Dragon Tower" ),
	FireDragonTower("Fire Dragon Tower" ),
	IceDragonTower("Ice Dragon Tower" ),
	PoisonDragonTower("Poison Dragon Tower" ),
	ShockDragonTower("Shock Dragon Tower" ),
	CatapultTower( "CatapultTower" );

	private static ArrayList<Buildings> allBuildings;
	private final String printableName;

	private Buildings( String printableName )
	{

		this.printableName = printableName;
		addToList( this );
	}

	private void addToList( Buildings b )
	{
		if( allBuildings == null )
			allBuildings = new ArrayList<Buildings>();
		allBuildings.add( this );
	}



	public String toClassLookUpString()
	{
		return name();
		//
		//		switch( this )
		//		{
		//
		//
		//		case WatchTowerOld: 			return "WatchTowerOld";
		//		case UndeadWatchTower: 		return "UndeadWatchTower";
		//		case GuardTower: 			return "GuardTower";
		//		case UndeadGuardTower:		return "UndeadGuardTower";
		//		case StoneTower: 			return "StoneTower";
		//		case UndeadStoneTower: 		return "UndeadStoneTower";
		//		case DwarfTower: 			return "DwarfTower";
		//
		//
		//		case Farm: 					return "Farm";
		//		case PileOCorps: 			return "PileOCorps";
		//
		//
		//		case DwarfTownCenter: 		return "DwarfTownCenter";
		//		case TownCenter: 			return "TownCenter";
		//		case UndeadTownCenter:		return "UndeadTownCenter";
		//
		//
		//		case Barracks: 				return "Barracks";
		//		case DwarfBarracks: 		return "DwarfBarracks";
		//		case UndeadBarracks:		return "UndeadBarracks";
		//
		//
		//		case StorageDepot: 			return "StorageDepot";
		//		case UndeadStorageDepot: 	return "UndeadStorageDepot";
		//
		//
		//		case Church:				return "Church";
		//		case DwarfMushroomFarm : 	return "DwarfMushroomFarm";
		//		case UndeadChurch:			return "UndeadChurch";
		//
		//		case Walls:					return "Walls";
		//
		//		case HealingShrine:					return "HealingShrine";
		//		case PoisonShrine:					return "PoisonShrine";
		//		case LightningShrine:				return "LightningShrine";
		//		case LaserShrine:					return "LaserShrine";
		//		case DamageShrine:					return "DamageShrine";
		//		case ProductionShrine:				return "ProductionShrine";
		//		case ShieldShrine:					return "ShieldShrine";
		//
		//		default:
		//			break;
		//		}
		//
		//
		//		return this.name();
	}


	@Override
	public String toString()
	{
		return printableName;
		//
		//		switch( this )
		//		{
		//
		//		case UndeadWatchTower:
		//		case WatchTowerOld : return "Watch Tower";
		//
		//		case UndeadGuardTower:
		//		case GuardTower : return "Guard Tower";
		//
		//		case StoneTower : return "Stone Tower";
		//		case UndeadStoneTower: 		return "Undead Stone Tower";
		//		case DwarfTower : return "Dwarf Tower";
		//
		//
		//		case Farm : return "Farm";
		//		case PileOCorps: return "Pile-O-Corps (Farm)";
		//
		//
		//
		//		case DwarfTownCenter :       return "Dwarf Town Center";
		//		case TownCenter :            return "Town Center";
		//		case UndeadTownCenter :      return "Undead Town Center";
		//
		//		case Barracks :              return "Barracks";
		//		case DwarfBarracks :         return "Dwarf Barracks";
		//		case UndeadBarracks :        return "Undead Barracks";
		//
		//
		//		case StorageDepot :          return "Storage Depot";
		//		case UndeadStorageDepot :    return "Undead Storage Depot";
		//
		//		case Church :                return "Church";
		//		case DwarfMushroomFarm :	 return "Dwarf Mushroom Farm";
		//		case UndeadChurch :			 return "Undead Church";
		//
		//		case Walls:					 return Walls.name();
		//
		//		case WallHorzA:      		 return WallHorzA.name();
		//		case WallHorzB:     		 return WallHorzB.name();
		//		case WallHorzC:       		 return WallHorzC.name();
		//
		//		case WallVertA:       		 return WallVertA.name();
		//		case WallVertB:      		 return WallVertB.name();
		//
		//		case WallEndA:     			 return WallEndA.name();
		//		case WallEndB:        		 return WallEndB.name();
		//
		//		case HealingShrine:		    return "Healing Shrine";
		//		case PoisonShrine:			return "Poison Shrine";
		//		case LightningShrine:		return "Lightning Shrine";
		//		case LaserShrine:			return "Laser Shrine";
		//		case DamageShrine:			return "Damage Shrine";
		//		case ProductionShrine:		return "Production Shrine";
		//		case ShieldShrine:			return "Shield Shrine";
		//		default:
		//			break;
		//		}
		//
		//
		//		return this.name();

	}




	public static Buildings getNameFromString( String name )
	{
		if( name == null )
			return null;


		for( Buildings b : allBuildings )
		{
			if( b.name().equals( name ) )
				return b;

			else if( b.printableName.equals( name ) )
				return b;
		}
		//
		//
		//		if( name.equals("WatchTowerOld") || name.equals("Watch Tower")) {
		//			return WatchTowerOld;
		//		}
		//		if( name.equals("UndeadWatchTower") || name.equals("Undead Watch Tower")) {
		//			return UndeadWatchTower;
		//		}
		//		if( name.equals("GuardTower") || name.equals("Guard Tower")) {
		//			return GuardTower;
		//		}
		//		if( name.equals("UndeadGuardTower") || name.equals("Undead Guard Tower")) {
		//			return UndeadGuardTower;
		//		}
		//		if( name.equals("StoneTower") || name.equals("Stone Tower")) {
		//			return StoneTower;
		//		}
		//		if( name.equals("UndeadStoneTower") || name.equals("Undead Stone Tower")) {
		//			return UndeadStoneTower;
		//		}
		//		if( name.equals("DwarfTower") || name.equals("Dwarf Tower")) {
		//			return DwarfTower;
		//		}
		//
		//
		//		if( name.equals("Farm")) {
		//			return Farm;
		//		}
		//		if( name.equals("PileOCorps") || name.equals("Pile-O-Corps (Farm)") ) {
		//			return PileOCorps;
		//		}
		//
		//
		//		if( name.equals("StorageDepot") || name.equals("Storage Depot")) {
		//			return StorageDepot;
		//		}
		//		if( name.equals("UndeadStorageDepot") || name.equals("Undead Storage Depot")) {
		//			return UndeadStorageDepot;
		//		}
		//
		//
		//		if(name.equals("DwarfTownCenter") || name.equals("Dwarf Town Center")) {
		//			return DwarfTownCenter;
		//		}
		//		if(name.equals("TownCenter") || name.equals("Town Center") ) {
		//			return TownCenter;
		//		}
		//
		//		if(name.equals( UndeadTownCenter.name() ) || name.equals("Undead Town Center") ) {
		//			return UndeadTownCenter;
		//		}
		//
		//
		//
		//		if( name.equals("Barracks")) {
		//			return Barracks;
		//		}
		//
		//		if( name.equals("DwarfBarracks") || name.equals("Dwarf Barracks") ) {
		//			return DwarfBarracks;
		//		}
		//
		//		if( name.equals( UndeadBarracks.name() ) || name.equals("Undead Barracks") ) {
		//			return UndeadBarracks;
		//		}
		//
		//
		//
		//		if( name.equals("Church")) {
		//			return Church;
		//		}
		//		if( name.equals("DwarfMushroomFarm") || name.equals("Dwarf Mushroom Farm") ) {
		//			return DwarfMushroomFarm;
		//		}
		//		if( name.equals("UndeadChurch") || name.equals("Undead Church") ) {
		//			return UndeadChurch;
		//		}
		//
		//
		//
		//		if( name.equals( WallHorzA.name() ) || name.equals("Wall Horz A") ) {
		//			return WallHorzA;
		//		}
		//		if( name.equals(WallHorzB.name()) || name.equals("Wall Horz B") ) {
		//			return WallHorzB;
		//		}
		//		if( name.equals( WallHorzC.name() ) || name.equals("Wall Horz C") ) {
		//			return WallHorzC;
		//		}
		//
		//
		//		if( name.equals( WallVertA.name() ) || name.equals("Wall Vert A") ) {
		//			return WallVertA;
		//		}
		//		if( name.equals( WallVertB.name() ) || name.equals("Wall Vert B") ) {
		//			return WallVertB;
		//		}
		//
		//
		//		if( name.equals( WallEndA.name() ) || name.equals("Wall End A") ) {
		//			return WallEndA;
		//		}
		//
		//		if( name.equals( WallEndB.name() ) || name.equals("Wall End B") ) {
		//			return WallEndB;
		//		}
		//
		//		if( name.equals( Walls.name() ) ) {
		//			return Walls;
		//		}
		//
		//
		//	case HealingShrine:		    return "Healing Shrine";
		//	case PoisonShrine:			return "Poison Shrine";
		//	case LightningShrine:		return "Lightning Shrine";
		//	case LaserShrine:			return "Laser Shrine";
		//	case DamageShrine:			return "Damage Shrine";
		//	case ProductionShrine:		return "Production Shrine";
		//	case ShieldShrine:			return "Shield Shrine";
		return null;
	}



	public static ArrayList<Buildings> getAllBuildings() {
		return allBuildings;
	}


	public String getPrintableName() {
		return printableName;
	}



	public static boolean isInstanceOf(Buildings unknown,
			Buildings humansType )
	{

		if( unknown == null || humansType == null )
			return false;


		switch( unknown )
		{

		case WatchTower:
		case UndeadWatchTower:
			return humansType == WatchTower || humansType == Tower;

		case GuardTower:
		case UndeadGuardTower:
			return humansType == GuardTower || humansType == Tower;

		case StoneTower:
		case UndeadStoneTower:
		case DwarfTower:
			return humansType == StoneTower || humansType == Tower;

		case Farm:
		case PileOCorps:
			return humansType == Farm ;

		case DwarfTownCenter:
		case TownCenter:
		case UndeadTownCenter:
			return humansType == TownCenter ;

		case Barracks:
		case DwarfBarracks:
		case UndeadBarracks:
			return humansType == Barracks ;

		case StorageDepot:
		case UndeadStorageDepot:
			return humansType == StorageDepot ;

		case Church:
		case DwarfMushroomFarm :
		case UndeadChurch:
			return humansType == Church ;


		default:
			return humansType == unknown ;
		}

	}



	public static boolean isInstanceOf( Building b , Buildings humansType ){
		if( b == null || humansType == null )
			return false;


		return isInstanceOf(b.getBuildingsName(),humansType);
	}










}
