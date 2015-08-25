package com.kingscastle.nuzi.towerdefence.gameElements.spells;


import com.kingscastle.nuzi.towerdefence.effects.animations.GroundSmasherAnim;
import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;

public class GroundSmasher extends InstantSpell
{

	private static Image iconImage;


	{
		setRefreshEvery(100);
	}

	public GroundSmasher(LivingThing caster) {
		setCaster(caster);
	}


	@Override
	public Abilities getAbility()				 {				return Abilities.GROUNDSMASHER ; 			}



	private int count = 0;
	@Override
	public void refresh()
	{
		doDamage(cd.checkMultiHit(getTeamName(), getArea()));
		++count;

		if( count >= 2 )
			die();
	}




	@Override
	public boolean cast( MM mm )
	{
		super.cast(mm);
		setLoc(getCaster().loc);
		setStartTime( GameTime.getTime() );
		loadAnimation();
		mm.getEm().add(getAnim());

		return true;
	}




	@Override
	public int calculateDamage()
	{
		if(getCaster() != null )
		{
			return 60 + getCaster().getLQ().getLevel() * 3;
		}
		else
		{
			return 30;
		}
	}



	@Override
	public int calculateManaCost( LivingThing aWizard)
	{
		return 30 + aWizard.getLQ().getLevel() * 5 ;
	}

	@Override
	public Spell newInstance() {
		return new GroundSmasher(getCaster());
	}


	@Override
	public String getName()
	{
		return "GroundSmasher";
	}


	@Override
	public boolean hitsOnlyOneThing()
	{
		return false;
	}





	@Override
	public String toString()
	{
		return "Ground Smasher";
	}




	@Override
	public void loadAnimation()
	{
		setAnim( new GroundSmasherAnim( loc ) );

	}






	@Override
	public Image getIconImage()
	{
		if( iconImage == null )
		{
			//	iconImage = Assets.loadImage(R.drawable.quake_icon);
		}
		return iconImage;
	}






}
