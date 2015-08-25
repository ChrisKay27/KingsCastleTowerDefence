package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army;

import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.effects.animations.Anim;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.gameElements.Cost;
import com.kingscastle.nuzi.towerdefence.gameElements.ImageFormatInfo;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingQualities;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.MageSoldier;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.TargetingParams;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.abilities.AbilityManager;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.abilities.HotBuff;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks.BuffAttack;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks.SpellAttack;
import com.kingscastle.nuzi.towerdefence.gameElements.spells.PoisonSpell;
import com.kingscastle.nuzi.towerdefence.gameElements.targeting.TargetFinder;
import com.kingscastle.nuzi.towerdefence.gameUtils.Age;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.teams.Teams;


public class KratosMage extends MageSoldier
{

	private static final String TAG = KratosMage.class.getSimpleName();
	private static final String NAME = "Kratos Mage";

	private static Image[] staticImages , redImages , blueImages , greenImages , orangeImages , whiteImages ;
	private static ImageFormatInfo imageFormatInfo;


	private static final AttackerQualities staticAttackerQualities; @Override
	protected AttackerQualities getStaticAQ() { return staticAttackerQualities; }
	private static final LivingQualities staticLivingQualities; @Override
	protected LivingQualities getStaticLQ() { return staticLivingQualities; }

	private static Cost cost = new Cost( 30 , 0 , 0 , 1 );


	static
	{
		float dp = Rpg.getDp();

		imageFormatInfo = new ImageFormatInfo( 0 , 0 , 2 , 0 , 4 , 2 );
		imageFormatInfo.setID(R.drawable.kratos_7);

		staticAttackerQualities= new AttackerQualities();

		staticAttackerQualities.setStaysAtDistanceSquared( 0 );
		staticAttackerQualities.setFocusRangeSquared(5000*dp*dp);
		staticAttackerQualities.setAttackRangeSquared( Rpg.getMeleeAttackRangeSquared() );
		staticAttackerQualities.setDamage( 30 );  staticAttackerQualities.setdDamageAge( 0 ); staticAttackerQualities.setdDamageLvl( 1 );
		staticAttackerQualities.setROF( 1000 );

		staticLivingQualities = new LivingQualities();  staticLivingQualities.setRequiresBLvl(1); staticLivingQualities.setRequiresAge(Age.STONE); staticLivingQualities.setRequiresTcLvl(1);
		staticLivingQualities.setRangeOfSight( 300 );
		staticLivingQualities.setLevel( 1 );
		staticLivingQualities.setFullHealth( 300 );
		staticLivingQualities.setHealth( 300 ); staticLivingQualities.setdHealthAge( 0 ); staticLivingQualities.setdHealthLvl( 10 ); //
		staticLivingQualities.setFullMana( 0 );
		staticLivingQualities.setMana( 0 );
		staticLivingQualities.setHpRegenAmount( 1 );
		staticLivingQualities.setRegenRate( 1000 );
		staticLivingQualities.setArmor( 10 );  staticLivingQualities.setdArmorAge( 3 ); staticLivingQualities.setdArmorLvl( 1 );
		staticLivingQualities.setSpeed( 1f * dp );
	}

	{
		setAQ( new AttackerQualities(staticAttackerQualities,getLQ().getBonuses()) );
		setGoldDropped(10);
	}


	public KratosMage(vector loc, Teams team)
	{
		super(team);
		setLoc( loc );
		super.team = team;
	}

	public KratosMage()
	{
	}

	@Override
	public boolean act() {

		boolean superAct = super.act();
		if( getFriendlyTarget() != null )
			setFriendlyTarget(null);
		return superAct;
	}

	@Override
	protected void setupSpells() {
		HotBuff buffSpell = new HotBuff(this,null);
		buffSpell.setHealAmount(5);
		this.buffSpell = buffSpell;
		getAQ().setFriendlyAttack( new BuffAttack(getMM(),this , buffSpell ) ) ;


		PoisonSpell pSpell = new PoisonSpell();
		pSpell.setDamage(getAQ().getDamage());
		pSpell.setCaster(this);

		SpellAttack poisonAttack = new SpellAttack(getMM(), this , pSpell  );
		getAQ().setCurrentAttack( poisonAttack ) ;
	}




	@Override
	protected void createFriendlyTargetingParams()
	{
		if( friendlyParams == null )
		{
			final AbilityManager abm = getMM().getTM().getTeam( getTeamName() ).getAbm();

			friendlyParams = new TargetingParams()
			{
				@Override
				public TargetFinder.CondRespon postRangeCheckCondition( LivingThing target )
				{
					if( !buffSpell.canCastOn(target) )
						return TargetFinder.CondRespon.FALSE;

					if( target == KratosMage.this) {
						if (KratosMage.this.lq.getHealthPercent() < 0.8f )
							return TargetFinder.CondRespon.TRUE;
						else
							return TargetFinder.CondRespon.FALSE;
					}

					if( loc.distanceSquared(target.loc) < aq.getAttackRangeSquared() )
						return TargetFinder.CondRespon.RETURN_THIS_NOW;
					return TargetFinder.CondRespon.FALSE;
				}
			};

			friendlyParams.setTeamOfInterest( getTeamName() );
			friendlyParams.setOnThisTeam( true );
			friendlyParams.setWithinRangeSquared(getAQ().getFocusRangeSquared());
			friendlyParams.setFromThisLoc ( loc );
			friendlyParams.setLookAtBuildings( false );
			friendlyParams.setLookAtSoldiers( true );
		}
	}



	@Override
	public Image[] getImages()
	{
		loadImages();
		Teams teamName = getTeamName();
		if( teamName == null )		
			teamName = Teams.BLUE;
		switch( teamName )
		{
		default:
		case RED:
			return redImages;
		case GREEN:
			return greenImages;
		case BLUE:
			return blueImages;
		case ORANGE:
			return orangeImages;
		case WHITE:
			return whiteImages;
		}
	}

	@Override
	public void loadImages()
	{
		if( redImages == null )
			redImages = Assets.loadImages(imageFormatInfo.getRedId(), 0, 0, 1, 1);
		if( orangeImages == null )
			orangeImages = Assets.loadImages( imageFormatInfo.getOrangeId()  , 0 , 0 , 1 , 1 );
		if( blueImages == null )
			blueImages = Assets.loadImages( imageFormatInfo.getBlueId()  , 0 , 0 , 1 , 1 );
		if( greenImages == null )
			greenImages = Assets.loadImages( imageFormatInfo.getGreenId()  , 0 , 0 , 1 , 1 );
		if( whiteImages == null )
			whiteImages = Assets.loadImages( imageFormatInfo.getWhiteId()  , 0 , 0 , 1 , 1 );		
	}

	@Override
	public Anim getDyingAnimation(){
		return Assets.deadSkeletonAnim;
	}



	@Override
	public ImageFormatInfo getImageFormatInfo(){
		return imageFormatInfo;
	}

	public void setImageFormatInfo(ImageFormatInfo imageFormatInfo2){
		imageFormatInfo=imageFormatInfo2;
	}


	@Override
	public RectF getStaticPerceivedArea(){
		return Rpg.getNormalPerceivedArea();
	}



	@Override
	public void setStaticPerceivedArea(RectF staticPercArea2) {

	}


	/**
	 * DO NOT LOAD THE IMAGES, USE GETIMAGES() to make sure they are not null.
	 * @return the staticImages
	 */
	@Override
	public Image[] getStaticImages() {
		return staticImages;
	}

	/**
	 * @param staticImages the staticImages to set
	 */
	@Override
	public void setStaticImages(Image[] staticImages) {
		KratosMage.staticImages = staticImages;
	}





	@Override
	public LivingQualities getNewLivingQualities()
	{
		return new LivingQualities( staticLivingQualities );
	}

	@Override
	public Cost getCosts() {
		return cost;
	}
	public static void setCost(Cost cost) {
		KratosMage.cost = cost;
	}





	@Override
	public String toString() {
		return TAG;
	}

	@Override
	public String getName() {
		return NAME;
	}





}
