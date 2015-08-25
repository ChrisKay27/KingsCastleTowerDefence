package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.effects.animations.LightEffect2;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.gameElements.Cost;
import com.kingscastle.nuzi.towerdefence.gameElements.ImageFormatInfo;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingQualities;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.MageSoldier;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks.SummonAttack;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameUtils.Age;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.teams.Teams;

import java.util.ArrayList;
import java.util.List;


public class Gaia extends MageSoldier {

	private static ImageFormatInfo imageFormatInfo;
	private static Image[] images = Assets.loadImages( R.drawable.gaia_red  , 0 , 0 , 1 , 1 );


	private static final AttackerQualities staticAttackerQualities; @Override
	protected AttackerQualities getStaticAQ() { return staticAttackerQualities; }
	private static final LivingQualities staticLivingQualities; @Override
	protected LivingQualities getStaticLQ() { return staticLivingQualities; }

	private static Cost cost = new Cost( 4000 , 4000 , 0 , 2 );

	static
	{
		float dp = Rpg.getDp();
		imageFormatInfo = new ImageFormatInfo( 0  , 0 ,
				0 , 0 , 1 , 1);

		staticAttackerQualities= new AttackerQualities();

		staticAttackerQualities.setStaysAtDistanceSquared(0);
		staticAttackerQualities.setFocusRangeSquared(5000*dp*dp);
		staticAttackerQualities.setAttackRangeSquared( Rpg.getMeleeAttackRangeSquared() );
		staticAttackerQualities.setDamage( 100 );  staticAttackerQualities.setdDamageAge( 0 ); staticAttackerQualities.setdDamageLvl( 5 );
		staticAttackerQualities.setROF(1000);

		staticLivingQualities = new LivingQualities(); staticLivingQualities.setRequiresBLvl(10); staticLivingQualities.setRequiresAge(Age.STEEL); staticLivingQualities.setRequiresTcLvl(16);
		staticLivingQualities.setLevel( 1 );
		staticLivingQualities.setFullHealth( 15000 );
		staticLivingQualities.setHealth( 15000 ); staticLivingQualities.setdHealthAge( 0 ); staticLivingQualities.setdHealthLvl( 30 );
		staticLivingQualities.setFullMana(0);
		staticLivingQualities.setMana(0);
		staticLivingQualities.setHpRegenAmount(1);
		staticLivingQualities.setRegenRate(1000);
		staticLivingQualities.setArmor( 0 );  staticLivingQualities.setdArmorAge( 0 ); staticLivingQualities.setdArmorLvl( 2 );
		staticLivingQualities.setSpeed(0.5f * dp);
	}

	private List<SummonAttack> summonAtks = new ArrayList<>();
	private long checkSummonsAt;

	{
		setAQ(new AttackerQualities(staticAttackerQualities, getLQ().getBonuses()));
		startTargetingAgainAt = Long.MAX_VALUE;
		setCostsLives(50);
	}

	public Gaia(vector loc, Teams team){
		super(team);
		setLoc(loc);

		setTeam(team);
	}


	public Gaia(){
	}

	@Override
	public boolean act() {
		if( checkSummonsAt < GameTime.getTime() ) {
			checkSummonsAt = GameTime.getTime() + 200;
			for (SummonAttack sa : summonAtks)
				sa.act();
		}
		return super.act();
	}

	@Override
	public boolean create(MM mm) {
		boolean superCreate =  super.create(mm);
		getAnim().setScale(1.5f);
		final LightEffect2 le = new LightEffect2(loc);
		le.setScale(2);
		mm.getEm().add(le);
		getAnim().addAnimationListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				le.setOver(true);
			}
		});
		return superCreate;
	}

	@Override
	protected void setupSpells(){
		summonAtks.add(new SummonAttack(getMM(), this, new Pigeon(new vector(), team), 12));
	}

	@Override
	public void setTarget(LivingThing nTarget) {
	}




	@Override
	public Image[] getImages()
	{
		loadImages();

		Teams teamName = getTeamName();
		if( teamName == null )
			teamName = Teams.BLUE;

		return images;
	}

	@Override
	public void loadImages()
	{
	}


	/**
	 * @return the imageFormatInfo
	 */
	@Override
	public ImageFormatInfo getImageFormatInfo() {
		return imageFormatInfo;
	}


	/**
	 * @param imageFormatInfo the imageFormatInfo to set
	 */
	public void setImageFormatInfo(ImageFormatInfo imageFormatInfo) {
		Gaia.imageFormatInfo = imageFormatInfo;
	}


	/**
	 * DO NOT LOAD THE IMAGES, USE GETIMAGES() to make sure they are not null.
	 * @return the staticImages
	 */
	@Override
	public Image[] getStaticImages() {
		return null;
	}


	/**
	 * @param staticImages the staticImages to set
	 */
	@Override
	public void setStaticImages(Image[] staticImages) {

	}




	@Override
	public RectF getStaticPerceivedArea() {
		return Rpg.getNormalPerceivedArea();
	}
	@Override
	public void setStaticPerceivedArea(RectF staticPercArea) {
	}
	@Override
	public LivingQualities getNewLivingQualities() {
		return new LivingQualities(staticLivingQualities);
	}



	@Override
	public Cost getCosts() {
		return cost;
	}
	public static void setCost(Cost cost) {
		Gaia.cost = cost;
	}



	private static final String TAG = "Knight";

	@Override
	public String toString() {
		return TAG;
	}
	@Override
	public String getName() {
		return TAG;
	}
}
