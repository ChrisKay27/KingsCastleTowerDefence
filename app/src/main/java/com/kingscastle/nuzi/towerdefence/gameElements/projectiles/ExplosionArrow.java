package com.kingscastle.nuzi.towerdefence.gameElements.projectiles;

import android.graphics.Rect;
import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.effects.animations.ExplosionAnim;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;

import java.util.ArrayList;

public class ExplosionArrow extends Arrow
{
	private static final String NAME = "Explosion Arrow";
	private static ArrayList<Image> images;
	private static Rect explosionArea;

	static
	{
		int dp = (int) Rpg.getDp();
		int radius = 20*dp;
		explosionArea = new Rect(-radius,-radius,radius,radius);
		//images = Assets.loadAnimationImages(R.drawable.explosion_arrow,8,1);

	}


	private ExplosionArrow()	{}


	@Override
	public String toString()
	{
		return NAME;
	}
	@Override
	public String getName()
	{
		return NAME;
	}

	@Override
	protected boolean checkIfHitSomething()
	{

		LivingThing lt = cd.checkSingleHit( getTeamName() , getArea());

		if( lt != null )
		{
			getMM().getEm().add( new ExplosionAnim( loc ) );
			RectF newArea = new RectF(getExplosionArea());
			newArea.offset(loc.getIntX(),loc.getIntY());
			ArrayList<LivingThing> victums = cd.checkMultiHit( getTeamName() , newArea );

			for(LivingThing victum: victums)
				victum.takeDamage( getDamage() , getShooter() );

			die(lt);
			return true;
		}
		else
			return false;
	}


	@Override
	public void die()
	{
		super.die();
	}

	/**
	 * @return the explosionArea
	 */
	private static Rect getExplosionArea() {
		return explosionArea;
	}


	/**
	 * @param explosionArea the explosionArea to set
	 */
	public static void setExplosionArea(Rect explosionArea) {
		ExplosionArrow.explosionArea = explosionArea;
	}


	@Override
	public Image getDeadImage()
	{
		return null;
	}


	//	@Override
	//	public void loadAnimation( MM mm )
	//	{
	//		super.
	//		Animation a = new Animation();
	//		a.setImage(images.get(getDir().getDir()));
	//		a.setLoc(loc);
	//
	//		setProjAnim(a);
	//		mm.getEm().add(getProjAnim() , true);
	//	}


	@Override
	public ArrayList<Image> getDeadImages()
	{
		return null;
	}


	@Override
	public Projectile newInstance()
	{
		return new ExplosionArrow();
	}
}
