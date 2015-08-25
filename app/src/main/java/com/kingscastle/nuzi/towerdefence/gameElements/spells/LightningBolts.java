package com.kingscastle.nuzi.towerdefence.gameElements.spells;

import android.graphics.Color;
import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.effects.EffectsManager.Position;
import com.kingscastle.nuzi.towerdefence.effects.animations.LightningBoltAnim;
import com.kingscastle.nuzi.towerdefence.effects.animations.SparksAnim;
import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.abilities.Ability;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import org.jetbrains.annotations.NotNull;


public class LightningBolts extends ProjectileSpell {

	private static final String TAG = "LightningBolts";

	protected long lastMoved = GameTime.getTime();

	private static Image iconImage;

	private static RectF staticPerceivedArea;

	private static final float staticSpeed= Rpg.getDp() * 11;
	private static final float staticRangeSquared = 40000*Rpg.getDp()*Rpg.getDp(); //200*200=40000

	public LightningBolts(){
	}

	public LightningBolts(int damage){

	}

	public LightningBolts(LightningBolts lbs) {
		setDamage(lbs.getDamage());
	}

	public LightningBolts(int damage, float attackRangeSquared) {
		setDamage(damage);
		setRangeSquared(attackRangeSquared);
	}


	@Override
	public Abilities getAbility()				 {				return Abilities.LIGHTNINGBOLTS ; 			}


	@Override
	public boolean cast( MM mm )
	{
		super.cast(mm);
		//Log.d(TAG, "cast("+mm+")");
		if( getDamage() == 0 )
			setDamage( calculateDamage( ));

		if( getAnim() != null )
		{
			if( getAnim().isOver() )
				getAnim().restart();

			//////Log.d( TAG , "adding Anim to Em");
			mm.getEm().add( getAnim() , true );
			return true;
		}

		return false;
		//		loadAnimation();
		//		getAnim().restart();
		//		ManagerManager.getInstance().getEm().add( getAnim() , true );
		//		return true;
	}



	@Override
	public int calculateDamage()
	{
		return getDamage();
	}


	@Override
	public int calculateManaCost(LivingThing aWizard)
	{
		//		if( aWizard != null )
		//		{
		//			return 5 + aWizard.getLQ().getLevel() * 1;
		//		}
		return 0;
	}

	@Override
	public Ability newInstance(@NotNull LivingThing target) {
		return null;
	}


	@Override
	public void die() {
		getMM().getEm().add(new SparksAnim(loc, Color.BLUE ), Position.Sorted);
		getMM().getEm().add(new SparksAnim(loc, Color.YELLOW ),Position.Sorted);
		super.die();
	}



	@Override
	public RectF getStaticPerceivedArea(){
		loadStaticPerceivedArea();
		return staticPerceivedArea;
	}
	void loadStaticPerceivedArea() {
		if (staticPerceivedArea == null)
		{
			int sizeDiv2 = (int) (2 * Rpg.getDp());
			staticPerceivedArea = new RectF(-sizeDiv2, -sizeDiv2, sizeDiv2 , sizeDiv2);
		}
	}



	@Override
	public void loadAnimation()
	{
		if( getAnim() == null )
			setAnim( new LightningBoltAnim( loc ) );
	}





	@Override
	public String toString() {
		return "Lightning Bolts";
	}
	@Override
	public String getName() {
		return "LightningBolts";
	}







	@Override
	public float getStaticSpeed(){
		return staticSpeed;
	}

	@Override
	public float getStaticRangeSquared(){
		return staticRangeSquared;
	}




	@Override
	public Spell newInstance() {
		return new LightningBolts(this);
	}



	@Override
	public void setLoc(vector loc)
	{
		super.setLoc(new vector(loc));
	}



	@Override
	public Image getIconImage()
	{
		//if( iconImage == null )
			//iconImage = Assets.loadImage( R.drawable.lightning_strike_icon );
		return iconImage;
	}


	@Override
	public void loadAnimation(vector unit) {

	}











}
