package com.kingscastle.nuzi.towerdefence.gameElements.livingThings;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kingscastle.nuzi.towerdefence.effects.Palette;
import com.kingscastle.nuzi.towerdefence.effects.SpecialEffects;
import com.kingscastle.nuzi.towerdefence.effects.animations.Anim;
import com.kingscastle.nuzi.towerdefence.effects.animations.Bar;
import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.Cost;
import com.kingscastle.nuzi.towerdefence.gameElements.GameElement;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.RangedSoldier;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.SoldierType;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.abilities.Ability;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.abilities.ActiveAbilities;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks.Arms;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.AttackingBuilding;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Building;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.orders.Order;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameElements.targeting.TargetFinder;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.level.GridUtil;
import com.kingscastle.nuzi.towerdefence.teams.Team;
import com.kingscastle.nuzi.towerdefence.teams.Teams;
import com.kingscastle.nuzi.towerdefence.teams.races.Races;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public abstract class LivingThing extends GameElement
{
	private static final String TAG = "LivingThing";


	public enum DamageTypes{
		Exploding, Normal, Burning, Ice, Lightning
	}

	protected boolean hasFinalInited = false;

	protected Teams team;

	protected final Arms arms = new Arms( this );

	public final LivingQualities lq;
	public AttackerQualities aq = new AttackerQualities();

	protected Animator aliveAnim;
	protected Anim dyingAnim;
	protected Bar healthBar;

	protected long startTargetingAgainAt;
	protected TargetingParams targetingParams;
	protected TargetFinder targetFinder;

	protected LivingThing target;
	protected LivingThing attacker;
	protected LivingThing highThreadTarget;
	protected LivingThing lastHurter;
	protected float targetDistanceSquared;
	protected DamageTypes lastDamageType = DamageTypes.Normal;

	protected long waitingSince = GameTime.getTime() - 300;
	protected long waitForIt = GameTime.getTime();
	private final long spawnedAt = GameTime.getTime();
	private long nextRegen = spawnedAt;
	protected long diedAt;

	protected long stunnedUntil;

	private int selectedColor;

	private ArrayList<Ability> abilities;

	private final ActiveAbilities activeAbilities = new ActiveAbilities();


	protected long checkedBeingStupidAt;
	protected int waitLength = 50;
	protected int buildTime = 10000;

	protected GridUtil gUtil;

	protected abstract Anim getDyingAnimation();
	protected abstract LivingQualities getNewLivingQualities();

	protected LivingThing(Teams team){
		this.team = team;
		lq = getNewLivingQualities();
	}

	protected LivingThing(){
		lq = getNewLivingQualities();
	}

	//	private final void loadLQAndLegs()
	//	{
	//		//		if( lq == null )
	//		//			setLQ( getNewLivingQualities() );
	//
	//		loadLegs();
	//	}


	@Override
	public boolean act()
	{
		if ( lq.getHealth() <= 0 )
			die();
		else
		{
			if( !isStunned() ) {
				regen();

				checkBeingStupid();
			}
		}
		return isDead();
	}

    /**
     * Used to do work that must be done every game cycle.
     * @return true if this LivingThing must be removed from the Manager
     */
    public final boolean update(){
        activeAbilities.act();
        return act();
    }


	@Override
	public void initialize(){
	}

	protected void setAttributes(){
	}

	@Override
	public boolean create( @NonNull MM mm )
	{
		super.create(mm);

		targetFinder = mm.getTargetFinder();
		gUtil = mm.getGridUtil();

        //Null asserts... lazy coder
		cd.toString();
		targetFinder.toString();
		gUtil.toString();

		if( created ){
			Animator anim = getAnim();
			if( anim != null )
				anim.setVisible( true );
			else
				finalInit( mm );

			return true;
		}
		////Log.d( TAG , "create()" );


		//finalInit(mm);
		////Log.d( TAG , "finalInit() completed" );
		updateArea();
		////Log.d( TAG , "updateArea() complete" );
		created = true;

		loadAnimation(mm);
		upgrade();

		if( aliveAnim != null )
			aliveAnim.setVisible(true);

		return true;
	}



	public void finalInit( MM mm)
	{
//
//		////Log.d( TAG , "finalInit()" );
//		loadAnimation(mm);
//		////Log.d( TAG , "done loadAnimation()" );
//		upgrade();
//
//		aliveAnim.setVisible(true);
	}



	@Override
	 public void loadAnimation( @NotNull MM mm ){
}

	protected void addHealthBarToAnim( Races race )
	{
		////Log.d( TAG , "addHealthBarToAnim()" );
		if( healthBar == null )
		{
			////Log.d( TAG , "healthBar == null" );
			if( getTeamName() == null )
				throw new IllegalStateException( " getTeamName() == null " );


			if( getTeamName() == Teams.BLUE ) //TODO People will be able to choose their color later on!
			{
				healthBar = new Bar( getLQ().getHealthObj() , -8 * Rpg.getDp() , -12 * Rpg.getDp() );
				Paint nonDistOverPaint = Palette.getPaint(Align.CENTER, Color.RED, Rpg.getTextSize());
				healthBar.setBarPaint( nonDistOverPaint );
				healthBar.setTextPaint( nonDistOverPaint );
				healthBar.setShowCurrentAndMax( false );
			}
			else
			{
				float yOffs = 12 * Rpg.getDp();

				if( getImages() != null && getImages().length > 0 )
				{
					Image img = getImages()[0];
					if( img != null )
					{
						yOffs = img.getHeightDiv2() + Rpg.twoDp;
					}
				}

				healthBar = new Bar( getLQ().getHealthObj() , -8 * Rpg.getDp() , - yOffs );
				healthBar.setShowCurrentAndMax( false );
			}

			if( race == Races.UNDEAD )
				healthBar.setColor( Color.BLACK );

		}

		aliveAnim.add(healthBar, false);

	}



	public synchronized void upgradeToLevel( int lvl ){
		if( lvl > Team.MAX_LEVEL )
			lvl = Team.MAX_LEVEL;
		while( lq.getLevel() < lvl )
			upgradeLevel();
	}

	public synchronized void upgradeLevel(){
		if( lq.getLevel() >= Team.MAX_LEVEL )
			return;
		lq.setLevel(lq.getLevel() + 1);
		SpecialEffects.onCreatureLvledUp(loc.x, loc.y);
		upgrade();
	}


	protected void upgrade() {
		AttackerQualities saq = getStaticAQ();
		AttackerQualities aq = getAQ();

		LivingQualities slq = getStaticLQ();

		int lvl = lq.getLevel()-1;


		if( aq != null && saq != null ){
			aq.setROF( saq.getROF() + aq.getdROFLvl()*lvl );
			aq.setDamage( saq.getDamage() + aq.getdDamageLvl()*lvl );
			aq.setAttackRange( saq.getAttackRange() + aq.getdRangeLvl()*lvl );
		}

		float healthPerc = lq.getHealthPercent();

		lq.setFullHealth(slq.getFullHealth() + lq.getdHealthLvl() * lvl);
		lq.setHealth((int) (healthPerc * lq.getFullHealth()));


		lq.setRegenRate(slq.getRegenRate() + lq.getdRegenRateLvl() * lvl);

		lq.setArmor(slq.getArmor() + lq.getdArmorLvl() * lvl);

		lq.setHealAmount(slq.getHealAmount() + lq.getdHealLvl() * lvl);
	}


	public boolean canLevelUp() {
		return lq.getLevel() < lq.getMaxLevel();

		/* @FIXME Not used in Tower Defense
		Team t = MM.get().getTeam(team);
		if( t != null )
			if( this instanceof Building)
				return t.canThisLevelUp( (Building) this );
			else{
				return false;
				String s = t.canThisLevelUp( this );
				String[] srhtybh = s.split(":");
				return srhtybh[0].equals("Yes");
			}

		else
			return false;*/
	}



	protected AttackerQualities getStaticAQ(){
		return null;
	}
	protected abstract LivingQualities getStaticLQ();




	protected void checkBeingStupid()
	{
		if ( checkedBeingStupidAt < GameTime.getTime() )
		{
			checkedBeingStupidAt = GameTime.getTime() + 2000;

			final LivingThing highThreadTarget = this.highThreadTarget;
			if( highThreadTarget != null )
			{
				if( highThreadTarget.isDead() )
					this.highThreadTarget = null;
				else
					if( target != highThreadTarget )
					{
						target = highThreadTarget;
						return;
					}
			}

			final LivingThing lastHurter = this.lastHurter;
			if( lastHurter != null )
			{
				if( lastHurter.isDead() )
					this.lastHurter = null;
				else
				{
					if( target != null )
					{
						if( target == highThreadTarget )
							return;
						else if( target instanceof Building && !( target instanceof AttackingBuilding) )
						{
							if( !(lastHurter instanceof Building) )
								target = lastHurter;
						}
						else
						{
							if( target instanceof RangedSoldier)
								if( !(lastHurter instanceof RangedSoldier) )
									setTarget(lastHurter);
						}

						if( isOutOfRangeOrDead( this , target ))
							setTarget(null);
					}
					else{
						if( !(lastHurter instanceof Building) )
							setTarget(lastHurter);
					}
				}
			}
		}
	}



	protected void regen()
	{
		if ( nextRegen < GameTime.getTime())
		{
			if (lq.getHealth() < lq.getFullHealth())
				takeHealing( lq.getHpRegenAmount() , false );

			//			if (livingQualities.getHealth() > livingQualities.getFullHealth())
			//			{
			//				livingQualities.setHealth( livingQualities.getFullHealth() );
			//			}
			if ( lq.getMana() < lq.getFullMana() )
				lq.addMana( lq.getMpRegenAmount() );

			if ( lq.getMana() > lq.getFullMana() )
				lq.setMana( lq.getFullMana() );

			nextRegen = GameTime.getTime() + (getTarget() == null && team != Teams.RED ? lq.getRegenRate()/5 : lq.getRegenRate()) ;
		}
	}




	public LivingThing getLastHurter(){
		return lastHurter;
	}


	public void setLastHurter( LivingThing lastHurter ){
		this.lastHurter = lastHurter;
	}



	public void takeDamage( int dam, LivingThing enemy )	{
		this.takeDamage( dam, enemy, DamageTypes.Normal );
	}

	//private final SparksAnim bloodSplatter = new SparksAnim( loc , Color.RED );

	public void takeDamage(int dam, LivingThing enemy, DamageTypes damageType)	{
		if( isDead() )
			return;

		float dam2 = dam;

		switch( damageType ){
			case Exploding:
				break;
			case Normal: dam2 -= Math.min( dam2/2 , dam2*lq.getArmor()/100 );
				break;
			case Burning: dam2 -= lq.getFireResistancePerc()*dam2;
				break;
			case Ice: dam2 -= lq.getIceResistancePerc()*dam2;
				break;
			case Lightning: dam2 -= lq.getLightningResistancePerc()*dam2;
				break;
		}

		int damage = (int) dam2;

		SpecialEffects.onCreatureDamaged( loc.x , loc.y, damage );

		getLQ().setHealth( getLQ().getHealth() - damage );

		if( enemy != null )
			lastHurter = enemy;

		lastDamageType = damageType;
	}



	@Override
	public void die()
	{
		if ( !isDead() )
		{
			createDyingAnimation();
			setDead(true);

			Animator anim = aliveAnim;
			if( anim != null )
				anim.setOver( true );


			activeAbilities.die();

			synchronized (dls){
				for( OnDeathListener dl : dls ){
					dl.onDeath(this);
				}
			}
		}
	}


	protected void findATarget()
	{
		//long act = System.nanoTime();
		if( startTargetingAgainAt > GameTime.getTime() )
			return;


		if( target == null )
		{
			//			if( team == Teams.RED && this instanceof TownCenter )
			//				Log.v(getClass().getSimpleName(), "Finding a target");
			//			if ( squad != null )
			//			{
			//				target = getSquad().getATarget(this);
			//
			//				if( target != null )
			//				{
			//					//lastLookedForTarget = GameTime.getTime();
			//					return;
			//				}
			//				LivingThing lastHurter = this.lastHurter;
			//				if( lastHurter != null && !lastHurter.isDead()
			//						&& loc.distanceSquared( lastHurter.loc ) < getAQ().getFocusRangeSquared() )
			//				{
			//
			//					//lastLookedForTarget=GameTime.getTime();
			//					target = lastHurter;
			//					return;
			//				}
			//			}
			//If the target is still null find a target from the level.
			//			if( target == null )
			//			{

			setupTargetingParams();
			//long act = System.nanoTime();
			TargetFinder tf = this.targetFinder;
			if( tf != null )
				tf.findTargetAsync(targetingParams,this);
			//target = tf.findTarget( targetingParams );

			//			if( target != null ){
			//				long ms = (System.nanoTime()-act)/1000000;
			//				if( ms > 500 )
			//					Log.e(getClass().getSimpleName(), "Time to find a "+target+":" + ms + " millisec");
			//			}

			startTargetingAgainAt = GameTime.getTime() + 100;
		}
	}


	public Animator getAnim()
	{
		return aliveAnim;
	}

	protected void createDyingAnimation()	{
		getMM().getEm().add(dyingAnim = getDyingAnimation().newInstance(loc));
	}




	protected int getLevel()
	{
		return 0;
	}

	public int getDamage()
	{
		//LivingThing atker = this;
//		if( bonuses != null )
//			return (int) (aq.getDamage() * bonuses.getDamageBonus());
//		else
		return aq.getDamage();
	}

//
//
//	public void setTeam(Teams team)
//	{
//		this.team = team;
//		if( legs != null )
//			legs.getMovingTechnique().setTeam( team );
//	}




	public void useMana(int manaCost)
	{
		getLQ().setMana(getLQ().getMana() - manaCost);
	}

	public void respawn()
	{
		getLQ().setHealth(getLQ().getFullHealth());
	}

	@Override
	public Teams getTeamName() {
		return team;
	}
	public void setTeam(Teams team) {
		this.team = team;
	}

	public void setArea(RectF r) {
		area.set(r) ; //CHANGED jan 12
	}



	public vector getImageOffset() {
		return offset;
	}



	//******************************** Targeting Methods *****************************************//



	protected void setupTargetingParams()
	{
		if( targetingParams == null )
		{
			targetingParams = new TargetingParams(){
				@Override
				public TargetFinder.CondRespon postRangeCheckCondition(LivingThing target) {
					if( target.getAttacker() != null )
						return TargetFinder.CondRespon.FALSE;
					if( target.getTarget() != null && target.getTarget() != LivingThing.this )
						return TargetFinder.CondRespon.FALSE;

					return TargetFinder.CondRespon.TRUE;
				}
			};
			targetingParams.setTeamOfInterest(team);
			targetingParams.setWithinRangeSquared(getAQ().getFocusRangeSquared());
			targetingParams.setFromThisLoc(loc);
			targetingParams.lookAtBuildings = false;
		}
	}

	private boolean ignoreRange = false;

	protected void checkTargetsSituation(LivingThing target)
	{
		if( target == null )
			return;

		else if( target == highThreadTarget && !target.isDead() )
			return;

		else if( isOutOfRangeOrDead( this , target ) )
			setTarget( null );
	}


	protected boolean isOutOfRangeOrDead(LivingThing thing1, LivingThing thing_a)
	{
		return isOutOfRangeOrDead( thing1 , thing_a , ignoreRange );
	}


	private static boolean isOutOfRangeOrDead(LivingThing thing1, LivingThing thing_a, boolean ignoreRange)
	{
		if( thing1 == null || thing_a == null || thing_a.isDead() )
			return true;

		if( ignoreRange )
			return false;

		if( thing1 == thing_a )
			return thing1.isDead();

		if( thing1.loc.distanceSquared( thing_a.loc ) > thing1.getAQ().getFocusRangeSquared() )
			return true;
		else
			return false;
	}


	public boolean getIgnoringRange() {
		return ignoreRange;
	}
	public void setIgnoreRange(boolean ignoreRange) {
		this.ignoreRange = ignoreRange;
	}


	public void setAttacker(@Nullable LivingThing attacker) {
		this.attacker = attacker;
	}
	public LivingThing getAttacker() {
		return attacker;
	}


	public void setTarget(@Nullable LivingThing nTarget) {
		//Log.d( TAG , "setTarget("+hit+")");

		if( nTarget instanceof Building )
			throw new IllegalStateException("Cannot target buildings in tower defence mode!");


		LivingThing oldTarget = target;
		//If we are clearing our target
		if( nTarget == null ) {
			//And we did have a target and it was being attacked by me then set his attacker to not be me
			if( oldTarget != null && oldTarget.getAttacker() == this )
				oldTarget.setAttacker(null);

		}
		else { //else set his attacker to be me
			if( nTarget.getTeamName() == team )
				return;
			nTarget.setAttacker(this);
		}
		target = nTarget;

		synchronized (tsls){
			for(OnTargetSetListener tsl : tsls)
				tsl.onTargetSet(this, nTarget);
		}
	}

	@Nullable
	public LivingThing getTarget() {
		return target;
	}


	public void setHighThreadTarget( @Nullable LivingThing enemy )
	{
		highThreadTarget = enemy;
		target = highThreadTarget;
	}


	//******************************** End Targeting Methods *************************************//











	public boolean isWalking() {
		return false;
	}
	@Nullable
	public vector getVelocity() {
		return null;
	}




	/**
	 * If the selectedColor is not yellow it WILL be drawn even if isSelected() is false.
	 * @param color
	 */
	public void setSelectedColor( int color )
	{
		selectedColor = color;
	}

	/**
	 * if true, if getSquad()!=null the color will be set to red for the leader, and blue for a soldier. If false or getSquad()==null the color will be set to yellow.
	 * @param b
	 */
	@Override
	public void setSelected ( boolean b )
	{
		selected = b;
		if( b )
		{
		}
		else
			setSelectedColor( Color.TRANSPARENT );
	}


	public boolean getSelected()
	{
		return selected;
	}


	public int getSelectedColor()
	{
		return selectedColor;
	}


	@Override
	public boolean isSelected()
	{
		return selected;
	}


	public void showHealthPercentage()
	{
		if( healthBar != null )
			healthBar.setShowCurrentAndMax( true );

	}

	public void hideHealthPercentage()
	{
		if( healthBar != null )
			healthBar.setShowCurrentAndMax( false );

	}

	protected void takeHealing(int healAmount, boolean graphics)
	{
		if( getLQ().getHealth() == getLQ().getFullHealth() )
			return;


		int prevHealth = getLQ().getHealth();

		getLQ().addHealth( healAmount );

		int healedAmount = getLQ().getHealth() - prevHealth;


		if ( graphics &&  healedAmount != 0 )
		{
			SpecialEffects.onCreatureHealed( loc.x , loc.y, healAmount );
			////////Log.v(TAG, "///LivingThing.takeHealing()///");
			//	//////Log.v(TAG, "Creating Healing Text as healedAmount = " + healedAmount);
			//	ManagerManager.getInstance().getRtm()
			//.add( new HealingText( healedAmount + "", this ) );
		}

	}



	public void takeHealing( int healAmount ){
		takeHealing( healAmount , true );
	}



	public Bonuses getBonuses(){
		return lq.getBonuses();
	}


	public ArrayList<Ability> getAbilities(){
		if( abilities == null )
			abilities = new ArrayList<>();
		return abilities;
	}

	protected void setAbilities(ArrayList<Ability> castableSpells)
	{
		abilities = castableSpells;
	}




	/**
	 * 	WTF
	 */
	public LivingThing newInstance(vector at, Teams teams) {
		return null;
	}



	@Override
	public void saveYourself( BufferedWriter b ) throws IOException{
		saveYourself( b , false );
	}

	public void saveYourself(BufferedWriter b, boolean ignoreGarrisonCheck) throws IOException
	{
		String s;

		s = "<" + getClass().getSimpleName() + " team=\""+ getTeamName() + "\" exp=\""+ 0 + "\" healthPercent=\"" + getLQ().getHealthPercent() + "\" x=\"" + loc.getIntX() + "\" y=\"" +
					loc.getIntY() + "\"/>";


		b.write( s , 0 , s.length() );
		b.newLine();
	}



	public boolean isWithinRange( LivingThing lt )
	{
		if(lt != null)
		{
			AttackerQualities aq = this.getAQ();
			return loc.distanceSquared(lt.loc) < aq.getFocusRangeSquared();
		}
		return false;
	}



	public LivingQualities getLQ()
	{
		return lq;
	}

	public AttackerQualities getAQ() {
		return aq;
	}
	protected void setAQ(AttackerQualities attackerQualities) {
		aq = attackerQualities;
	}




	public Arms getArms() {
		return arms;
	}





	@Override
	public RectF getPerceivedArea(){
		return Rpg.getNormalPerceivedArea();
	}

	@Override
	public RectF getStaticPerceivedArea(){
		return Rpg.getNormalPerceivedArea();
	}





	public ActiveAbilities getActiveAbilities()
	{
		return activeAbilities;
	}





	public ArrayList<Order> getPossibleOrders(){
		return null;
	}



	private final Cost costs = new Cost( 0,0,0,0,0 );

	public Cost getCosts()	{
		return costs;
	}

	public void setCosts(Cost costs) {
		costs.set(costs);
	}


	public boolean thisLocationMayBeOfInterestToYou( vector vtemp2 )
	{
		return false;
	}


	public int getBuildTime(){
		return buildTime;
	}


	public void setBuildTime( int bt ){
		buildTime = bt;
	}


	public void queueableComplete() {
	}


	public void setSoldierType( SoldierType soldierType ){
	}


	public SoldierType getSoldierType()
	{
		return null;
	}



	public void forgetAboutTargetingUntil( long startTargetingAgainAt )
	{
		this.startTargetingAgainAt = startTargetingAgainAt;
	}



	public Cost getLvlUpCost() {
		return getCosts();
		//if( getCosts() == null ){
			//Log.e( this.toString() , "getCosts() == null" );
		//	return null;
		//}

		//return null;//KcParams.getCostToLvlUp( this );
	}



	public void setStunnedUntil(long stunnedUntil) {
		this.stunnedUntil = stunnedUntil;
	}
	public boolean isStunned() {
		return stunnedUntil > GameTime.getTime();
	}



	//On Death Listener
	private final ArrayList<OnDeathListener> dls = new ArrayList<>();

	public interface OnDeathListener{
		void onDeath(LivingThing lt);
	}

	public void addDL(OnDeathListener gol)		   		{	synchronized( dls ){	dls.add( gol );				}  	}
	public boolean removeDL(OnDeathListener gol)		{	synchronized( dls ){	return dls.remove( gol );		}	}



	//On Target Set Listener
	protected final ArrayList<OnTargetSetListener> tsls = new ArrayList<>();

	public interface OnTargetSetListener{
		void onTargetSet(LivingThing forThis, LivingThing target);
	}
	public void addTSL(OnTargetSetListener gol)		   		{	synchronized( tsls ){	tsls.add( gol );				}  	}
	public boolean removeTSL(OnTargetSetListener gol)		{	synchronized( tsls ){	return tsls.remove( gol );		}	}

}
