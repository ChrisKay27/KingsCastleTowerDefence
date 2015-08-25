package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes;

import android.support.annotation.Nullable;
import android.util.Log;

import com.kingscastle.nuzi.towerdefence.TowerDefenceGame;
import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.Cost;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingQualities;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.TargetingParams;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.WhiteWizard;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.orders.Order;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.orders.Stance;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameElements.movement.pathing.Path;
import com.kingscastle.nuzi.towerdefence.gameElements.movement.pathing.PathFinder;
import com.kingscastle.nuzi.towerdefence.gameElements.movement.pathing.PathFinderParams;
import com.kingscastle.nuzi.towerdefence.gameElements.movement.pathing.PathFoundListener;
import com.kingscastle.nuzi.towerdefence.gameElements.targeting.TargetFinder;
import com.kingscastle.nuzi.towerdefence.gameElements.targeting.TargetFinder.CondRespon;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.teams.Teams;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

public abstract class Unit extends Humanoid
{
	private static final String TAG = "Unit";

	private static final float nearDistanceSquared = 15000 * Rpg.getDpSquared();
	private static final float closeEnoughDist = 30*30* Rpg.getDpSquared();
	private static final float closeEnoughDistWithTarget = 60*60* Rpg.getDpSquared();

	private final ArrayList<Order> possibleOrders;

	{
		possibleOrders = new ArrayList<>();
		//possibleOrders.add( ChangeStance.getInstance() );
		//possibleOrders.add( StayNearHere.getInstance() );
	}

	//private final SoldierType soldierType;

	private Stance stance = Stance.FREE;

	private long checkDestAt;
	private long checkTargetDistAt;
	private long checkTargetDistAt2;
	private float nearDistSquared = nearDistanceSquared;



	private long checkStayHereDistAt;


	protected Unit()
	{
		//		soldierType = SoldierType.getFromString( this.getClass().getSimpleName() );
		//
		//		if( soldierType == null )
		//		{
		//			////Log.d( TAG , "soldierType == null , forgot to update SoldierType.getFromString() for a " + this );
		//		}
	}

	@Override
	public boolean create(@NotNull MM mm){
		boolean superCreate = super.create(mm);

		if( team == Teams.RED )
			stance = Stance.FREE;
		else if( team == Teams.BLUE )
			stance = Stance.GUARD_LOCATION;

		return superCreate;
	}

	public Unit(Teams team) {
		super(team);
	}


	@Override
	protected void legsAct()
	{

//		switch( stance )
//		{
//		default:
//		case PLAYING_THE_OBJECTIVE:
//		case FREE:
//
//			break;
//
//
//		case HOLD_POSITION:
//			if( highThreadTarget == null )
//			{
//				if( legs.getPathToFollow() != null )
//					legs.followPath();
//
//				else if( destination != null )
//				{
//					if( isAt( loc , destination ) )
//					{
//						loc.set( destination );
//						destinationReached();
//					}
//					else
//						legs.moveTowards( destination );
//				}
//				else if( casualDestination != null )
//				{
//					if( checkDestAt < GameTime.getTime() )
//					{
//						if( loc.distanceSquared( casualDestination ) < closeEnoughDist )
//							casualDestination = null;
//
//						else
//							legs.moveTowards( casualDestination );
//					}
//				}
//			}
//			return;
//
//		case GUARD_LOCATION:
//			if( stayHere == null )
//				break;
////			if( stayHere == null )
////				stayHere = new Vector( loc );
//
//			if( highThreadTarget == null )
//			{
//				if( checkStayHereDistAt < GameTime.getTime() )
//				{
//					if( loc.distanceSquared( stayHere ) > nearDistSquared )
//					{
//						////Log.d( TAG , "To far from stayHere, clearing target and setting dest=stayHere");
//						clearTarget();
//						////Log.d( TAG , "post clearTarget(), target is " + target );
//						walkTo( stayHere );
//					}
//					checkStayHereDistAt = GameTime.getTime() + 1000;
//				}
//			}
//			break;
//		}
		vector stayHere_local = getStayHere();
//		if (stayHere_local != null && loc.distanceSquared(stayHere_local) > closeEnoughDist) {
//			if( getTarget() == null ){
//				if( loc.distanceSquared(stayHere_local) > closeEnoughDist )
//					legs.moveTowards(stayHere_local);
//			}
//			else if (loc.distanceSquared(stayHere_local) > closeEnoughDistWithTarget){
//
//			}
//		}
//		else
		if( getTarget() != null )
			legsActWithRespectToTarget();
		else if( legs.getPathToFollow() != null )
		{
			//Log.d( TAG , "destination = " + destination );
			//Log.d( TAG , "stayHere = " + stayHere );
			legs.followPath();
			return;
		}
		else if (checkStayHereDistAt < GameTime.getTime()) {
			if (stayHere_local != null && loc.distanceSquared(stayHere_local) > closeEnoughDist) {
				PathFinderParams pfp = new PathFinderParams();
				pfp.fromHere = new vector(loc);
				pfp.toHere = new vector(stayHere_local);
				pfp.mapWidthInPx = getMM().getLevel().getLevelWidthInPx();
				pfp.mapHeightInPx = getMM().getLevel().getLevelHeightInPx();
				pfp.grid = getMM().getGridUtil().getGrid();
				pfp.pathFoundListener = new PathFoundListener() {
					@Override
					public void onPathFound(Path path) {
						setPathToFollow(path);
					}
					@Override
					public void cannotPathToThatLocation(String reason) {
					}
				};
				PathFinder.findPath(pfp);
				checkStayHereDistAt = GameTime.getTime() + 1000;
				//legs.moveTowards(stayHere_local);
			}
		}
//		else if( destination != null )
//		{
//			if( isAt( loc , destination ) )
//			{
//				loc.set( destination );
//				destinationReached();
//			}
//			else
//			{
//				legs.moveTowards( destination );
//				return;
//			}
//		}
//
//		if( !legsActWithRespectToTarget() )
//		{
//			if( casualDestination != null )
//			{
//				if( checkDestAt < GameTime.getTime() )
//				{
//					if( loc.distanceSquared( casualDestination ) < closeEnoughDist )
//						casualDestination = null;
//					else
//						legs.moveTowards( casualDestination );
//				}
//			}
//			else if( getTarget() == null )
//				wander();
//		}

	}



	protected void wander() {
		//if( team != Teams.BLUE && Settings.yourBaseMode )
		//	legs.wander();
	}



	boolean legsActWithRespectToTarget()
	{
		LivingThing currTarget = getTarget();
		if( currTarget != null )
		{
			if( checkTargetDistAt2 < GameTime.getTime() )
			{
				if( currTarget.area.contains( loc.x , loc.y ) )
				{
					targetDistanceSquared = 1;
					legs.moveAwayFrom( currTarget.loc );
				}
				else
				{
					targetDistanceSquared = loc.distanceSquared( currTarget.area );
					checkTargetDistAt2 = GameTime.getTime() + 200;
				}
			}

			if( targetDistanceSquared > getAQ().getAttackRangeSquared()  ){
				legs.moveTowards( currTarget.loc );
				return true;
			}
		}
		return false;
	}

	void clearTarget() {
		setTarget( null );
	}


	private boolean isAt( vector loc, vector dest )
	{

		if( checkDestAt < GameTime.getTime() )
		{
			if( this instanceof WhiteWizard)
				Log.e(TAG, "Checking distanceof to destination (" + loc.distanceSquared( dest ) + ")");

			if( loc.distanceSquared( dest ) < Rpg.fiveDpSquared ){
				if( this instanceof WhiteWizard )
					Log.e(TAG, " is at destination");
				return true;
			}

			checkDestAt = GameTime.getTime() + 200;
		}
		return false;
	}


	@Override
	protected boolean armsAct()
	{
		LivingThing currTarget = getTarget();
		if( currTarget != null )
		{
			switch( stance )
			{
			default:
			case PLAYING_THE_OBJECTIVE:
			case GUARD_LOCATION:
			case FREE:
				return super.armsAct();

			case HOLD_POSITION:
				boolean attacked = getArms().act();

				if( checkTargetsSituationAt < GameTime.getTime() )
				{
					checkTargetsSituation( currTarget );
					checkTargetsSituationAt = GameTime.getTime() + 300;
				}

				return attacked;
			}
		}
		return false;
	}


	@Override
	public void setupTargetingParams()
	{
		if( targetingParams == null )
		{
			//targetingParams = TargetingParams.newInstance();
			targetingParams = new TargetingParams()
			{
				//Vector mLoc = new Vector();
				//Vector tLoc = new Vector();

				@Override
				public CondRespon postRangeCheckCondition(LivingThing target) {
					if( target.getAttacker() != null )
						return TargetFinder.CondRespon.FALSE;

//					Vector stayHere = getStayHere();
//					if( stayHere != null && stayHere.distanceSquared(target.loc) > aq.getFocusRangeSquared() )
//						return CondRespon.FALSE;

					return CondRespon.TRUE;
//					try {
//						tLoc.set(target.loc);
//						mLoc.set(loc);
//
//
//						Vector closestTargetLoc = gUtil.getNearestWalkableLocNextToThis(target.area, tLoc);
//						Path p = PathFinder.heyINeedAPath( gUtil.getGrid(), mLoc, closestTargetLoc, 1000);
//						if( p != null ){
//							//startTargetingAgainAt = 0;
//							setPathToFollow(p);
//							return CondRespon.RETURN_THIS_NOW;
//						}
//						else{
//							//startTargetingAgainAt = GameTime.getTime() + 5000;
//							return CondRespon.FALSE;
//						}
//					} catch( Exception e ) {
//						//Log.e(TAG, "Could not find path to target.");
//						return CondRespon.FALSE;
//					}
				}
			};
			targetingParams.lookAtBuildingsFirst = true;
			targetingParams.soldierPriority = true;
			targetingParams.setTeamOfInterest(team);
			targetingParams.setWithinRangeSquared( getAQ().getFocusRangeSquared() );


		}

		switch( stance )
		{
		default:
			targetingParams.setWithinRangeSquared( getAQ().getFocusRangeSquared() );
			break;
		case HOLD_POSITION:
			targetingParams.setWithinRangeSquared( getAQ().getAttackRangeSquared() );
			break;
		}

		vector stayHere_local = getStayHere();
		if( team == Teams.BLUE && stayHere_local != null)
			targetingParams.setFromThisLoc( stayHere_local );
		else
			targetingParams.setFromThisLoc( loc );
	}


	@Override
	public void setTarget(@Nullable LivingThing nTarget) {
		if( nTarget != null && nTarget.getTarget() != null && nTarget.getTarget() != this )
			return;
		super.setTarget(nTarget);
	}

	@Override
	public Cost getCosts()
	{
		return null;
	}









	public Stance getStance() {
		return stance;
	}

	public void setStance(Stance stance)
	{
		if( stance == null )
			this.stance = Stance.FREE;
		else
			this.stance = stance;
	}




	@Override
	public ArrayList<Order> getPossibleOrders(){
		return possibleOrders;
	}




	@Override
	public String getName() {
		return super.getName();
	}







	public float getNearDistSquared() {
		return nearDistSquared;
	}
	public void setNearDistSquared(float nearDistSquared) {
		this.nearDistSquared = nearDistSquared;
	}



	public static LivingThing getUnitByName(String unitName, LivingThing createdFrom,
			Teams team)
	{

		vector newLoc = new vector( createdFrom.loc );
		newLoc.translate(
				(float) (-createdFrom.getPerceivedArea().width() / 2 + Math
						.random() * createdFrom.getPerceivedArea().width()),
						createdFrom.getPerceivedArea().height());

		return getFromString(unitName, team, newLoc);
	}



	private static final ArrayList<String> packagesToLookForUnits = new ArrayList<String>();
	static{
		addPackageToFindUnits("com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.");
	}
	public static void addPackageToFindUnits(String pkg){
		synchronized(packagesToLookForUnits){
			packagesToLookForUnits.add(pkg);
		}
	}


	@Nullable
	public static Unit getFromString(@NotNull String unitName,@NotNull Teams team,@NotNull vector newLoc)
	{

		synchronized(packagesToLookForUnits){
			for( String s : packagesToLookForUnits ){
				try
				{
					Class<?> aUnit = Class.forName(s + unitName);

					Unit unit;

					Constructor<?>[] constrs = aUnit.getConstructors();

					for( Constructor<?> c : constrs ){
						try{
							Class<?>[] params = c.getParameterTypes();
							if( params.length == 2 )
								if( params[1] == vector.class && params[0] == Teams.class ){
									unit = (Unit) c.newInstance(newLoc , team );
									return unit;
								}
						}catch(Exception e)
						{
							if( TowerDefenceGame.testingVersion ){
								e.printStackTrace();
							}
							//Log.v( TAG , "" );
						}
					}

					//throw new WtfException("Cannot find unit:" + unitName + " team:" + team + " at " + newLoc);
					unit = (Unit) aUnit.newInstance();
					unit.setTeam( team );
					unit.setLoc( newLoc );

					return unit;
				}
				catch( NoClassDefFoundError e )
				{
					if( TowerDefenceGame.testingVersion ){
						e.printStackTrace();
					}
				}
				catch(Exception e)
				{
					if( TowerDefenceGame.testingVersion ){
						e.printStackTrace();
					}
					//Log.v( TAG , "Did not find the Class in the livingThing.army. folder " );
				}
			}
		}
		return null;
	}



	@Override
	protected LivingQualities getStaticLQ() { return null; }



}
