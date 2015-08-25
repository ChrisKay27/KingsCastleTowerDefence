package com.kingscastle.nuzi.towerdefence.gameElements.spells;

import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.effects.animations.LaserAnim;
import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;


public class Laser extends InstantSpell
{


	private static Image iconImage;

	private static final RectF staticPerceivedArea = new RectF( -Rpg.getDp()*2 , -Rpg.getDp()*2 , Rpg.getDp()*2 , Rpg.getDp()*2 ) ;

	private ArrayList<vector> offsets = new ArrayList<vector>();

	{

		setAliveTime     ( 1000 );
		setRefreshEvery  ( 200 );
		setLastRefreshed ( GameTime.getTime() );
	}




	@Override
	public Abilities getAbility()				 {				return Abilities.LASER ; 			}


	@Override
	protected void refresh()
	{
		////Log.d( "Laser" , "Laser refreshing, damage = " + getDamage() );
		doDamage( cd.checkMultiHit( getTeamName() , getArea() ) );
	}


	@Override
	public int calculateDamage()
	{
		if( getCaster() != null )
		{
			return getCaster().lq.getLevel()*7;
		}
		return 7;
	}


	@Override
	public int calculateManaCost(LivingThing aWizard)
	{
		return 0;
	}



	@Override
	public boolean cast( MM mm )
	{
		super.cast(mm);
		setAliveTime     ( 1000 );
		setRefreshEvery  ( 200 );
		setLastRefreshed ( GameTime.getTime() );
		setStartTime( GameTime.getTime() );

		loadAnimation();
		mm.getEm().add( getAnim() , true );
		area.set( staticPerceivedArea );
		area.offsetTo( getTarget().area.left , getTarget().area.top );
		return true;
	}


	@Override
	public void setLoc(vector loc){
		super.setLoc(loc);
	}



	@Override
	public void loadAnimation()
	{
		if( offsets.isEmpty() )
			setAnim( new LaserAnim( loc , getTarget().loc ) );
		else
		{
			LaserAnim anim = new LaserAnim( loc , getTarget().loc );
			anim.setOffs( offsets.get( 0 ));
			setAnim( anim );

			if( offsets.size() > 1 )
			{
				boolean first = true;
				for( vector offs : offsets )
				{
					if( first ){
						first = false;
						continue;
					}
					anim = new LaserAnim( loc , getTarget().loc );
					anim.setOffs( offs );
					getAnim().add( anim , true );
				}
			}
		}
	}




	@Override
	public RectF getPerceivedArea()
	{
		return staticPerceivedArea;
	}



	@Override
	public boolean hitsOnlyOneThing() {
		return true;
	}




	@Override
	public String getName() {
		return "Laser";
	}



	@Override
	public Spell newInstance() {
		Laser laser = new Laser();
		laser.offsets = this.offsets;
		return laser;
	}




	@Override
	public Image getIconImage()
	{
		if( iconImage == null )
		{
			//	iconImage = Assets.loadImage(R.drawable.explosion_icon);
		}
		return iconImage;
	}


	public void addOffs(vector offs)
	{
		offsets.add( offs );
	}

}
