package com.kingscastle.nuzi.towerdefence.gameElements.spells;

import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.effects.EffectsManager;
import com.kingscastle.nuzi.towerdefence.effects.animations.FireBallAnim;
import com.kingscastle.nuzi.towerdefence.effects.animations.FireHitAnim;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.abilities.FireDOT;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;


public class FireBall extends ProjectileSpell {


	private static final String TAG = "FireBall";

	private static RectF staticPerceivedArea;

	private static Image iconImage;

	private static final int staticSpeed =  (int) ( 10 * Rpg.getDp() );
	private static final int staticRangeSquared = (int) ( 40000*Rpg.getDp()*Rpg.getDp() ); //200*200 = 40000

	public FireBall() {
	}
	public FireBall(int damage) {
		setDamage(damage);
	}
	public FireBall(FireBall fb) {
		setDamage(fb.getDamage());
	}


	@Override
	public Abilities getAbility()				 {				return Abilities.FIREBALL ; 			}

	private MM mm;
	@Override
	public boolean cast( MM mm )
	{
		this.mm = mm;
		super.cast(mm);

		//Log.d( TAG , "cast()");
		if( getDamage() == 0 )
			setDamage( calculateDamage() );


		if( getAnim() != null )
		{
			if( getAnim().isOver() )
				getAnim().restart();

			//Log.d( TAG , "adding Anim to Em");
			mm.getEm().add(getAnim(), true);
			return true;
		}

		return false;
	}

	@Override
	void doDamage( LivingThing lt){
		if( lt != null ){
			die();
			lt.takeDamage(getDamage(), getCaster(), LivingThing.DamageTypes.Burning);

			FireDOT burning = new FireDOT(getCaster(), lt, getDamage() / 2);
			mm.add(burning);

		}
	}



	@Override
	public int calculateManaCost(LivingThing aWizard)
	{
		return 0;
		//		if( aWizard != null )
		//		{
		//			return 1 + aWizard.getLQ().getLevel() * 2;
		//		}
		//		return 1;
	}

	@Override
	public Spell newInstance() {
		return new FireBall(this);
	}


	@Override
	public void setLoc(vector loc){
		super.setLoc(new vector(loc));
	}




	@Override
	public void die()
	{
		super.die();
		EffectsManager em = mm.getEm();
		em.add(new FireHitAnim(loc),true);
	}


	@Override
	public RectF getStaticPerceivedArea()
	{
		loadStaticPerceivedArea();
		return staticPerceivedArea;
	}


	void loadStaticPerceivedArea()
	{
		if (staticPerceivedArea == null)
		{
			float sizeDiv2 = 2 * Rpg.getDp();
			staticPerceivedArea = new RectF(-sizeDiv2, -sizeDiv2, sizeDiv2 , sizeDiv2);
		}
	}



	@Override
	public void loadAnimation(vector unit)
	{
		FireBallAnim fba = new FireBallAnim( loc , vector.getDirection8(unit).getDir() );
		setAnim(fba);
	}



	@Override
	public void loadAnimation() {

	}

	@Override
	public boolean hitsOnlyOneThing()
	{
		return true;
	}



	@Override
	public String toString()
	{
		return TAG;
	}

	@Override
	public String getName()
	{
		return TAG;
	}





	@Override
	public float getStaticRangeSquared()
	{
		return staticRangeSquared;
	}



	@Override
	public float getStaticSpeed()
	{
		return staticSpeed;
	}





	@Override
	public Image getIconImage()
	{
		if( iconImage == null )
		{
			//	iconImage = Assets.loadImage(R.drawable.fireball_icon);
		}
		return iconImage;
	}


	@Override
	public void uncast()
	{
	}





}
