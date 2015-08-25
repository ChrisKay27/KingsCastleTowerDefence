package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army;

import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.effects.animations.Anim;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.Cost;
import com.kingscastle.nuzi.towerdefence.gameElements.ImageFormatInfo;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingQualities;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.AdvancedRangedSoldier;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks.Bow;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameElements.projectiles.Arrow;
import com.kingscastle.nuzi.towerdefence.gameUtils.Age;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.teams.Teams;


public class SkullFucker extends AdvancedRangedSoldier
{

	private static Image[] staticImages;
	private static ImageFormatInfo imageFormatInfo;

	private static final AttackerQualities staticAttackerQualities; @Override
	protected AttackerQualities getStaticAQ() { return staticAttackerQualities; }
	private static final LivingQualities staticLivingQualities; @Override
	protected LivingQualities getStaticLQ() { return staticLivingQualities; }

	private static Cost cost = null;

	static
	{
		setImageFormatInfo(new ImageFormatInfo(R.drawable.skull_fucque_red , 0, 0 ,0 , 1 , 1 ));

		float dp = Rpg.getDp();

		staticAttackerQualities= new AttackerQualities();

		staticAttackerQualities.setStaysAtDistanceSquared(10000 * dp * dp);
		staticAttackerQualities.setFocusRangeSquared(5000*dp*dp);
		staticAttackerQualities.setAttackRangeSquared(22500 * dp * dp);
		staticAttackerQualities.setDamage( 10 );  staticAttackerQualities.setdDamageAge( 3 ); staticAttackerQualities.setdDamageLvl( 1 ); //20);
		staticAttackerQualities.setROF(600);

		staticLivingQualities = new LivingQualities();  staticLivingQualities.setRequiresBLvl(1); staticLivingQualities.setRequiresAge(Age.STONE); staticLivingQualities.setRequiresTcLvl(1);
		staticLivingQualities.setLevel( 1 ); //20);
		staticLivingQualities.setFullHealth(400);
		staticLivingQualities.setHealth( 100 ); staticLivingQualities.setdHealthAge( 50 ); staticLivingQualities.setdHealthLvl( 10 ); //400);
		staticLivingQualities.setFullMana(200);
		staticLivingQualities.setMana(200);
		staticLivingQualities.setHpRegenAmount(1);
		staticLivingQualities.setRegenRate(500);
		staticLivingQualities.setSpeed(3f * dp);

	}

	{
		setAQ(new AttackerQualities(staticAttackerQualities,getLQ().getBonuses()));
	}


	public SkullFucker(vector loc, Teams team)
	{
		super(team);
		setLoc(loc);
		setTeam(team);
	}

	public SkullFucker()
	{

	}



	@Override
	public void finalInit( MM mm )
	{
		super.finalInit( mm );
		getAQ().setCurrentAttack( new Bow( mm , this , new Arrow() ) );
		aliveAnim.add(getAQ().getCurrentAttack().getAnimator(),true);
	}








	/**
	 * DO NOT LOAD THE IMAGES, USE GETIMAGES() to make sure they are not null. This method is used to check if the static images are null so they can be loaded.
	 * @return the staticImages
	 */
	@Override
	public Image[] getStaticImages()
	{
		return staticImages;
	}


	/**
	 * @param staticImages2 the staticImages to set
	 */
	@Override
	public void setStaticImages(Image[] staticImages2) {
		staticImages = staticImages2;
	}







	@Override
	public ImageFormatInfo getImageFormatInfo() {
		return imageFormatInfo;
	}

	public static void setImageFormatInfo(ImageFormatInfo imageFormatInfo) {
		SkullFucker.imageFormatInfo = imageFormatInfo;
	}







	@Override
	public LivingQualities getNewLivingQualities()
	{
		return new LivingQualities(staticLivingQualities);
	}




	@Override
	public Anim getDyingAnimation(){
		return Assets.deadSkeletonAnim;
	}


	@Override
	public void setStaticPerceivedArea(RectF staticPercArea) {
	}

	@Override
	public Cost getCosts()
	{
		return cost;
	}

	public static void setCost(Cost cost) {
		SkullFucker.cost = cost;
	}
	private static final String TAG = "Ancient Mecha Winged";
	@Override
	public String toString() {
		return TAG;
	}
	@Override
	public String getName() {
		return TAG;
	}
}
