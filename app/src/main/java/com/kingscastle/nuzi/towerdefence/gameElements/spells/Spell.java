package com.kingscastle.nuzi.towerdefence.gameElements.spells;

import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.effects.animations.Anim;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.gameElements.GameElement;
import com.kingscastle.nuzi.towerdefence.gameElements.ImageFormatInfo;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.abilities.Ability;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.teams.Teams;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public abstract class Spell extends GameElement implements Ability
{
	LivingThing caster;
	private LivingThing target;

	private boolean instanceCast;

	private Anim anim;

	private int damage;
	private int manaCost;

	private final ArrayList<LivingThing> hitBucket = new ArrayList<>();

	public boolean shouldAct = true;




	public abstract void loadAnimation();
	@Override
	public abstract String getName();

	@Override
	public boolean cast( MM mm ){
		cd = mm.getCD();
		return true;
	}

	/**
	 * Not used to save spells anymore. Saving spells simply doesn't happen anymore.
	 */
	@Override
	public void saveYourSelf(BufferedWriter b) throws IOException{
	}

	protected abstract int calculateDamage();

	@Override
	public int calculateManaCost( LivingThing possibleCaster ){return 0;}

	@Override
	public abstract boolean act();

	@Override
	public boolean cast(MM mm,LivingThing target)
	{
		return false;
	}



	ArrayList<LivingThing> checkHit(Teams teams,  GameElement ge)
	{
		hitBucket.clear();
		return cd.checkMultiHit( teams , ge.area , false , hitBucket);
	}



	void hitCreatures( List<LivingThing> lts)
	{
		if( lts == null )
			return;

		for(LivingThing lt : lts)
			if ( lt != null )
				lt.takeDamage( getDamage() ,caster );
	}




	/**
	 * @return the anim
	 */
	@Override
	public Anim getAnim()
	{
		return anim;
	}
	/**
	 * @param anim the anim to set
	 */
	void setAnim(Anim anim)	{
		this.anim = anim;
	}



	@Override
	public void setCaster(@NotNull LivingThing caster){
		this.caster = caster;
		setTeam(caster.getTeamName());
	}

	@Override
	public LivingThing getCaster(){
		return caster;
	}



	public boolean hitsOnlyOneThing()
	{
		return true;
	}

	@Override
	public void die()
	{
		if( anim != null )
			anim.setOver(true);

		setDead(true);
	}

    @Override
    public boolean isOver() {
        return isDead();
    }


    public boolean isInstanceCast() {
		return instanceCast;
	}
	public void setInstanceCast(boolean instanceCast) {
		this.instanceCast = instanceCast;
	}



	@Override
	public LivingThing getTarget()
	{
		return target;
	}
	@Override
	public void setTarget(LivingThing target)
	{
		this.target = target;
	}



	public final int getDamage(){
		//Log.v("Spell", "damage = " + damage );
		return damage;
	}

	public final void setDamage(int damage)
	{
		this.damage = damage;
	}



	public void setManaCost(int manaCost) {
		this.manaCost = manaCost;
	}

	public int getManaCost()	{
		if( manaCost == 0 )
			manaCost = getManaCost( getCaster() );
		return manaCost;
	}


	public int getManaCost( LivingThing possibleCaster )	{
		return calculateManaCost( possibleCaster );
	}


	@Override
    public Teams getTeamName(){
		return super.getTeamName();
	}

	@Override
	public void setTeam(Teams teamName) {
		super.setTeamName(teamName);
	}

	@Override
	public RectF getStaticPerceivedArea() {
		return null;
	}

	@Override
	public void setStaticPerceivedArea(RectF staticPercArea) {
	}



	public void uncast() {
	}

	@Override
	public Image getIconImage() {
		return null;
	}

	@Override
	public ImageFormatInfo getImageFormatInfo() {
		return null;
	}
	@Override
	public Image[] getStaticImages() {
		return null;
	}
	@Override
	public void setStaticImages(Image[] images) {
	}
	@Override
	public boolean isStackable() {
		return false;
	}

    public abstract Spell newInstance();
    @Override
    public Ability newInstance(@NotNull LivingThing target) {
        return newInstance();
    }


    @Override
    public void doAbility() {

    }
    @Override
    public void undoAbility() {

    }
}
