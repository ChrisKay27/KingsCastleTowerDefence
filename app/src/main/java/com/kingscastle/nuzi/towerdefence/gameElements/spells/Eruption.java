package com.kingscastle.nuzi.towerdefence.gameElements.spells;

import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.effects.animations.Anim;
import com.kingscastle.nuzi.towerdefence.effects.animations.EruptionAnim;
import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;


public class Eruption extends InstantSpell
{

	private static Image iconImage;


	private static RectF staticPerceivedArea = new RectF(-Rpg.getDp()*30,-Rpg.getDp()*30,Rpg.getDp()*30,Rpg.getDp()*30);




	@Override
	public Abilities getAbility()				 {				return Abilities.ERUPTION ; 			}

	public Eruption()
	{
	}

	private Eruption(int damage)
	{
		setDamage(damage);
	}


	@Override
	public void refresh()
	{
		doDamage(cd.checkMultiHit(getTeamName(), getArea()));
	}




	@Override
	public int calculateManaCost( LivingThing aWizard)
	{
		if( aWizard != null )
			return 25 + aWizard.getLQ().getLevel() * 7;
		return 1;
	}




	@Override
	public boolean cast( MM mm )
	{
		super.cast(mm);
		if( mm.getSdac().stillDraw( loc ) ){
			loadAnimation();
			Anim anim = getAnim();
			setAliveTime( anim.getTbf()*anim.getAnimImages().size() );
			setRefreshEvery(500);
			mm.getEm().add( anim , true );
		}
		setStartTime(GameTime.getTime());
		setLastRefreshed(GameTime.getTime());

		return true;
	}


	@Override
	public void setLoc(vector loc){
		super.setLoc(loc);
		super.loc.translate(0,1);
	}


	@Override
	public void loadAnimation( MM mm )
	{
		loadAnimation();
	}
	@Override
	public void loadAnimation()
	{
		setAnim( new EruptionAnim(loc) );
	}



	@Override
	public RectF getPerceivedArea()
	{
		if( staticPerceivedArea == null )
		{
			int sizeDiv2 = (int) (30 * Rpg.getDp()) ;
			staticPerceivedArea = new RectF( -sizeDiv2 , -sizeDiv2 , sizeDiv2 , sizeDiv2 );
		}
		return staticPerceivedArea;
	}



	@Override
	public boolean hitsOnlyOneThing() {
		return false;
	}




	@Override
	public String getName() {
		return "Eruption";
	}



	@Override
	public Spell newInstance()
	{
		return new Eruption( getDamage() );
	}


	@Override
	public Image getIconImage()
	{
		if( iconImage == null )
		{
			//iconImage = Assets.loadImage(R.drawable.explosion_icon);
		}
		return iconImage;
	}




}
