package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kingscastle.nuzi.towerdefence.effects.animations.Anim;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.GameElement;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.Animator;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.WalkToLocationFinder;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.AttackingBuilding;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Building;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameElements.movement.Legs;
import com.kingscastle.nuzi.towerdefence.gameElements.movement.pathing.Path;
import com.kingscastle.nuzi.towerdefence.gameElements.movement.pathing.PathFinder;
import com.kingscastle.nuzi.towerdefence.gameElements.movement.pathing.PathFoundListener;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.teams.Team;
import com.kingscastle.nuzi.towerdefence.teams.Teams;
import com.kingscastle.nuzi.towerdefence.teams.races.Races;

import java.util.ArrayList;

public abstract class Humanoid extends LivingThing
{
	protected final Legs legs = new Legs(this);

	protected long checkTargetsSituationAt;
	private int costsLives = 1;
	private int goldDropped = 2;

	Humanoid(){
	}

	public Humanoid(Teams team) {
		super(team);
	}


	//FIXME We do not want these things to upgrade because that will overwrite the health that we have set for it
	@Override
	protected void upgrade() {
	}


	@Override
	public boolean act()
	{
		boolean superAct = super.act();

		if( isDead() )
			return true;

		LivingThing currTarget = getTarget();
		if( currTarget == null )
			findATarget();

		legsAct();
		armsAct();

		return superAct;
	}

	protected void legsAct() {
	}


	protected boolean armsAct()
	{
		boolean attacked = false;
		LivingThing currTarget = getTarget();
		if( currTarget != null )
		{
			if( targetDistanceSquared == 0 )
				attacked = getArms().act();
			else
				attacked = getArms().act( targetDistanceSquared );
		}

		if( checkTargetsSituationAt < GameTime.getTime() )
		{
			checkTargetsSituation( currTarget );
			checkTargetsSituationAt = GameTime.getTime() + 300;
		}


		return attacked;
	}

	@Override
	public boolean create(@NonNull MM mm) {
		legs.setMm(mm);
		return super.create(mm);
	}

	@Override
	public void die()
	{
		if ( !isDead() )
		{
			super.die();

			getAQ().removeAllAttackAnims();
		}
	}


	@Override
	public void setTarget(@Nullable LivingThing nTarget) {
		super.setTarget(nTarget);
		unlockLookDirection();
	}


	@Override
	public void loadAnimation( MM mm )
	{
		Team team = mm.getTM().getTeam( getTeamName() );
		Races race = Races.HUMAN;
		if( team != null )
			race = team.getRace().getRace();

		////Log.d( TAG , "loadAnimation()" );
		if( aliveAnim == null )
		{
			////Log.d( TAG , "aliveAnim == null, creating new animator" );
			mm.getEm().add( aliveAnim = new Animator( this , getImages() ) , true );
			////Log.d( TAG , "after em.add( aliveAnim )");

			addHealthBarToAnim( race );

			////Log.d( TAG , "after addHealthBarToAnim()");
		}
		else if( aliveAnim.isOver() )
		{
			////Log.d( TAG , "aliveAnim != null");
			aliveAnim.setOver( false );

			healthBar = null;

			addHealthBarToAnim( race );
			////Log.d( TAG , "after addHealthBarToAnim()");
			mm.getEm().add( aliveAnim );
			////Log.d( TAG , "after em.add( aliveAnim )");
		}
	}



	//****************** Movement Methods *******************//

	private boolean movingIntoFormation;
	private boolean inFormation;
	protected vector destination;
	private vector stayHere;
	private vector holdThisPosition;

	protected Rpg.Direction lookDir = Rpg.Direction.S;
	private vector lookDirLockedInDirection;
	private boolean lookDirLocked;


	public boolean walkTo( @Nullable vector walkTo )
	{
		if( walkTo == null )
		{
			destination = null;
			return true;
		}

		vector v = WalkToLocationFinder.walkTo(walkTo, cd);
		if( v != null )
			return walkToAlreadyCheckedPlaceable( v );
		else
			return walkToAlreadyCheckedPlaceable( walkTo );
	}

	protected boolean walkToAlreadyCheckedPlaceable(vector walkTo)
	{
		destination = walkTo;
		return true;
	}




	public boolean walkToAndStayHere( @Nullable vector walkTo )
	{
		if( walkTo == null )
		{
			destination = null;
			stayHere = null;
			return true;
		}
		vector v = WalkToLocationFinder.walkTo( walkTo , cd );
		if( v != null )
			return walkToAndStayHereAlreadyCheckedPlaceable(v);
		else
			return walkToAndStayHereAlreadyCheckedPlaceable(walkTo);

	}

	public boolean walkToAndStayHereAlreadyCheckedPlaceable(vector walkTo)
	{
		destination = walkTo;
		stayHere = walkTo;
		return true;
	}

	public void walkToAndStayHere( vector unitsDest , boolean movingIntoFormation )
	{
		if( walkToAndStayHere(unitsDest) )
			this.movingIntoFormation = movingIntoFormation;

	}

	public void walkToAndStayHereAlreadyCheckedPlacement(vector dest, boolean movingIntoFormation)
	{
		this.movingIntoFormation = movingIntoFormation;
		walkToAndStayHereAlreadyCheckedPlaceable(dest);
	}

	protected vector casualDestination;

	public void setCasualDestination( vector nearTc )
	{
		casualDestination = nearTc;
	}

	public void pathDestinationReached(){
		//Log.v(TAG, this + " pathDestinationReached");
		destinationReached();
		legs.setPathToFollow(null);
		synchronized (pdrls){
			for( OnPathDestinationReachedListener pdrl : pdrls ){
				pdrl.onPathDestinationReached();
			}
			pdrls.clear();
		}
	}

	public void destinationReached(){
		//Log.v(TAG,this+" destinationReached");
		destination = null;
		synchronized (drls){
			for( OnDestinationReachedListener drl : drls ){
				drl.onDestinationReached();
			}
			drls.clear();
		}
	}

	public Legs getLegs() {
		return legs;
	}

	@Nullable
	public Path getPathToFollow(){
		return legs.getPathToFollow();
	}
	public void setPathToFollow( @Nullable Path pathToFollow ){
		//Log.d(TAG,this+" setting path to " + pathToFollow);
		//destination = null;
		//stayHere = null;
		legs.setPathToFollow( pathToFollow );
	}

	@Override
	public boolean isWalking(){
		return legs.areYouStillWalking();
	}

	@Override
	public vector getVelocity() {
		return legs.getVelocity();
	}


	@Nullable
	public vector getDestination(){
		return destination;
	}
	public void setDestination(@Nullable vector bestCerealEver){
		destination = bestCerealEver;
	}

	@Nullable
	public vector getStayHere() {
		return stayHere;
	}
	public void setStayHere( @Nullable vector v ){
		stayHere = v;
	}

	@Nullable
	public vector getHoldThisPosition() {
		return holdThisPosition;
	}
	public void setHoldThisPosition( @Nullable vector holdThisPosition ) {
		this.holdThisPosition = holdThisPosition;
	}


	public boolean isMovingIntoFormation() {
		return movingIntoFormation;
	}
	public void setMovingIntoFormation(boolean b){
		movingIntoFormation = b;
	}


	public boolean isInFormation() {
		return inFormation;
	}
	protected void setInFormation(boolean inFormation) {
		this.inFormation = inFormation;
	}

	public Rpg.Direction getLookDirection() {
		return lookDir;
	}

	public void setLookDirection(Rpg.Direction inDirection){
		if(!isLookDirectionLocked()){
			lookDir = inDirection;
		}
	}

	public void setLookDirectionFromUnit(vector unitVectorInDirection) {
		if(!isLookDirectionLocked()) {
			Rpg.Direction d = vector.getDirection4(unitVectorInDirection);
			setLookDirection(d);
		}
	}

	public void lockLookDirectionFromUnitVector(vector unitVectorInDirection) {
		if(unitVectorInDirection!=null) {
			setLookDirectionFromUnit(unitVectorInDirection);
			lookDirLocked=true;
		}
		else {
			lookDirLocked=false;
		}
		lookDirLockedInDirection = unitVectorInDirection;
	}

	public void lockLookDirection()
	{
		lookDirLocked=true;
	}
	public void unlockLookDirection()
	{
		lookDirLocked=false;
	}
	public boolean isLookDirectionLocked()
	{
		return lookDirLocked;
	}

	public vector getLookDirLockedInDirection() {
		return lookDirLockedInDirection;
	}


	//Path Destination Reached
	private final ArrayList<OnPathDestinationReachedListener> pdrls = new ArrayList<OnPathDestinationReachedListener>();

	public interface OnPathDestinationReachedListener{
		void onPathDestinationReached();
	}

	public void addPDRL(OnPathDestinationReachedListener pdrl)		   		{	synchronized(pdrls){	pdrls.add(pdrl);				}  	}
	public boolean removeRDRL(OnPathDestinationReachedListener pdrl)		{	synchronized(pdrls){	return pdrls.remove( pdrl );		}	}


	//Destination Reached
	private final ArrayList<OnDestinationReachedListener> drls = new ArrayList<OnDestinationReachedListener>();

	public interface OnDestinationReachedListener{
		void onDestinationReached();
	}

	public void addDRL(OnDestinationReachedListener drl)		   		{	synchronized(drls){	drls.add(drl);				}  	}
	public boolean removeDRL(OnDestinationReachedListener drl)		{	synchronized(drls){	return drls.remove( drl );		}	}

	//****************** End Movement Methods *******************//




	@Override
	protected void checkBeingStupid()
	{
		if ( checkedBeingStupidAt < GameTime.getTime() )
		{
			checkedBeingStupidAt = GameTime.getTime() + 2000;
			if( destination != null && !movingIntoFormation )
			{
				GameElement ge = cd.checkPlaceableOrTarget( destination );
				//Log.d( TAG , "checkBeingStupid(), found a " + ge + " at destination");
				if( ge != null && ge != this )//&& !(ge instanceof Farm) && !( ge instanceof PendingBuilding && ((PendingBuilding)ge).getBuildingToBuild() instanceof Farm ) )
					walkTo( destination );
			}

			final LivingThing highThreadTarget_local = this.highThreadTarget;
			if( highThreadTarget_local != null )
			{
				if( highThreadTarget_local.isDead() )
					this.highThreadTarget = null;
				else
				{
					if( target != highThreadTarget_local )
					{
						PathFinder.findPath(gUtil.getGrid(), loc, highThreadTarget_local.loc, new PathFoundListener() {
							@Override
							public void onPathFound(Path path) {
								setTarget(highThreadTarget_local);
								setPathToFollow(path);
							}

							@Override
							public void cannotPathToThatLocation(String reason) {
								Humanoid.this.highThreadTarget = null;
							}
						}, getMM().getLevel().getLevelWidthInPx(), getMM().getLevel().getLevelHeightInPx());
						return;
					}
				}
			}

			final LivingThing lastHurter_local = this.lastHurter;
			if( lastHurter_local != null )
			{
				if( lastHurter_local.isDead() )
					this.lastHurter = null;
				else
				{
					if( target != null )
					{
						if( target == highThreadTarget_local )
							return;
						else if( target instanceof Building && !( target instanceof AttackingBuilding) )
						{
							if( !(lastHurter_local instanceof Building) ){
								PathFinder.findPath( gUtil.getGrid() , loc , lastHurter_local.loc , new PathFoundListener() {
									@Override
									public void onPathFound(Path path) {
										setTarget(lastHurter_local);
										setPathToFollow(path);
									}
									@Override
									public void cannotPathToThatLocation(String reason) {
										Humanoid.this.lastHurter = null;
									}
								}, getMM().getLevel().getLevelWidthInPx(), getMM().getLevel().getLevelHeightInPx());
							}
						}
						else
						{
							if( target instanceof RangedSoldier)
								if( !(lastHurter_local instanceof RangedSoldier) )
									setTarget(lastHurter_local);
						}

						if( isOutOfRangeOrDead( this , target ))
							setTarget(null);
					}
					else{
						if( !(lastHurter_local instanceof Building) )
							setTarget(lastHurter_local);
					}
				}
			}
		}
	}


	@Override
	public Anim getDyingAnimation(){
		return Assets.genericDyingAnimation;
	}


	public final int getGoldDropped(){
		return goldDropped;
	}

	public final void setGoldDropped(int goldDropped) {
		this.goldDropped = goldDropped;
	}

	public final void setCostsLives(int costsLives) {
		this.costsLives = costsLives;
	}

	public final int getCostsLives(){
		return costsLives;
	}


//
//
//	protected void loadLegs()
//	{
//		if( legs == null )
//			legs = new Legs( this );
//	}


}
