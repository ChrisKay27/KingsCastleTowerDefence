package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings;

import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.effects.EffectsManager;
import com.kingscastle.nuzi.towerdefence.effects.animations.Anim;
import com.kingscastle.nuzi.towerdefence.effects.animations.Backing;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.gameElements.Cost;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingQualities;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.TargetingParams;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.abilities.HealingSpell;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks.HealingAttack;
import com.kingscastle.nuzi.towerdefence.gameElements.targeting.TargetFinder;
import com.kingscastle.nuzi.towerdefence.gameElements.targeting.TargetFinder.CondRespon;
import com.kingscastle.nuzi.towerdefence.gameUtils.Age;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.teams.Teams;


public class BasicHealingShrine extends Shrine
{

	private static final String TAG = "Basic Healing Shrine";
	private static final String NAME = "Healing Shrine";

	public static final Buildings name = Buildings.BasicHealingShrine;

	private static final Image image = Assets.loadImage(R.drawable.stone_circle);
	private static final Image deadImage = Assets.loadImage( R.drawable.small_rubble );
	private static final Image iconImage = Assets.loadImage( R.drawable.stone_circle_icon );

	private static RectF staticPerceivedArea = Rpg.oneByOneArea; // this is only the offset from the mapLocation.

	private static final AttackerQualities staticAttackerQualities;
	private static final LivingQualities staticLivingQualities;

	private static final Cost costs = new Cost( 1000 , 0 , 0 , 0 );

	@Override
	protected AttackerQualities getStaticAQ() { return staticAttackerQualities; }
	@Override
	protected LivingQualities getStaticLQ() { return staticLivingQualities;   }

	static
	{
		float dpSquared = Rpg.getDpSquared();
		staticAttackerQualities = new AttackerQualities();

		staticAttackerQualities.setFocusRangeSquared     ( 30000 * dpSquared );
		staticAttackerQualities.setAttackRangeSquared    ( 30000 * dpSquared );
		staticAttackerQualities.setROF( 1000 );
		staticAttackerQualities.setdROFAge(-100);
		staticAttackerQualities.setdROFLvl(-20);
		staticAttackerQualities.setdRangeSquaredAge(3000 * dpSquared);
		staticAttackerQualities.setdRangeSquaredLvl(100 * dpSquared);

		staticLivingQualities = new LivingQualities(); staticLivingQualities.setRequiresAge(Age.STONE); staticLivingQualities.setRequiresTcLvl(3);
		staticLivingQualities.setRangeOfSight( 250 );
		staticLivingQualities.setLevel( 1 );
		staticLivingQualities.setFullHealth( 200 );
		staticLivingQualities.setHealth( 200 );
		staticLivingQualities.setFullMana( 100 );
		staticLivingQualities.setMana( 100 );
		staticLivingQualities.setHpRegenAmount( 2 );
		staticLivingQualities.setRegenRate( 1000 );
		staticLivingQualities.setSpeed( 0 );

		staticLivingQualities.setHealAmount(15); staticLivingQualities.setdHealLvl(10);

		staticLivingQualities.setdHealthAge(0);
		staticLivingQualities.setdHealthLvl(35);
		staticLivingQualities.setdRegenRateAge(0);
		staticLivingQualities.setdRegenRateLvl(0);

	}


	private final HealingSpell hs;

	{
		setAQ( new AttackerQualities( staticAttackerQualities , getLQ().getBonuses() ) );
		hs = new HealingSpell(this,null);
		hs.setCaster( this );
		getAQ().setCurrentAttack( new HealingAttack(getMM(), this , hs ) );
	}

	public BasicHealingShrine()
	{
		super(name);
	}

	@Override
	protected void setupAttack() {

	}

	public BasicHealingShrine( vector v, Teams t )
	{
		this();
		setTeam(t);
		setLoc(v);
	}


	private TargetingParams params;

	private void createTargetingParams()
	{
		if( params == null )
		{
			params = new TargetingParams()
			{
				@Override
				public CondRespon postRangeCheckCondition( LivingThing target )
				{
					if( target.lq.getHealth() == target.lq.getFullHealth() )
						return CondRespon.FALSE;

					if( hs.canCastOn(target) )
						return CondRespon.TRUE;

					return CondRespon.FALSE;
				}
			};
			params.setLookAtBuildings( false );
			params.setTeamOfInterest( getTeamName() );
			params.setOnThisTeam( true );
			params.setWithinRangeSquared( getAQ().getAttackRangeSquared() );
			params.setFromThisLoc ( loc );
		}
	}

	@Override
	public void findATarget()
	{
		if( startTargetingAgainAt < GameTime.getTime() )
		{
			createTargetingParams();
			TargetFinder tf = this.targetFinder;
			if( tf != null )
				setTarget(tf.findTarget( params ));

			startTargetingAgainAt = GameTime.getTime() + 2000;

			////Log.d( TAG ,"Looking for target, found a " + target );
		}
	}



	@Override
	public boolean isOutOfRangeOrDead( LivingThing thing1 , LivingThing thingA )
	{
		boolean outOfRange = isOutOfRangeOrDeadORFullHealth( thing1 , thingA );
		////Log.d( TAG ,"Target is out of range or dead or full health" );
		return outOfRange;


		//return isOutOfRangeOrDeadORFullHealth( thing1 , thingA );
	}

	private boolean isOutOfRangeOrDeadORFullHealth( LivingThing healer ,
			LivingThing healingTarget2 )
	{

		if( healer == null || healingTarget2 == null )
			return true;

		if( healer == healingTarget2 )
		{
			return healingTarget2.lq.getHealth() == healingTarget2.lq.getFullHealth();
		}
		if ( super.isOutOfRangeOrDead ( healer, healingTarget2 ))
		{
			return true;
		}
		else
		{
			if( healingTarget2.lq.getHealth() == healingTarget2.lq.getFullHealth())
			{
				return true;
			}
		}

		return false;
	}




	@Override
	protected void addAnimationToEm(Anim a, boolean sorted, EffectsManager em)
	{
		em.add( a , true);
		backing.setSize(Backing.TINY);
		em.add( backing, EffectsManager.Position.Behind );
	}
	@Override
	public Image getImage() {
		return image;
	}
	@Override
	public Image getDamagedImage() {
		return image;
	}
	@Override
	public Image getDeadImage() {
		return deadImage;
	}
	@Override
	public Image getIconImage() {
		return iconImage;
	}

	/**
	 * returns a rectangle to be placed with its center on the mapLocation of the tower
	 */
	@Override
	public RectF getPerceivedArea()
	{
		return staticPerceivedArea;
	}


	public void setPerceivedSpriteArea(RectF perceivedSpriteArea2)
	{
		staticPerceivedArea = perceivedSpriteArea2;
	}

	@Override
	public RectF getStaticPerceivedArea()
	{
		return staticPerceivedArea;
	}

	@Override
	public void setStaticPerceivedArea(RectF staticPercArea)
	{
		staticPerceivedArea = staticPercArea;
	}


	//	@Override
	//	public void addHealthBarToAnim( Races race )
	//	{
	//		super.addHealthBarToAnim( race );
	//		if( healthBar != null )
	//			healthBar.setShowBar( false );
	//	}


	@Override
	public Cost getCosts(){
		return costs;
	}

	@Override
	public String getName() {
		return NAME;
	}


	@Override
	public LivingQualities getNewLivingQualities() {
		return new LivingQualities(staticLivingQualities);
	}



}
