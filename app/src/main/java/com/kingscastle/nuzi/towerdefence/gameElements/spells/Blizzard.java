package com.kingscastle.nuzi.towerdefence.gameElements.spells;

import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Input.TouchEvent;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.GameElement;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.abilities.Ability;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;


public class Blizzard extends InstantSpell
{
	private static Image iconImage;

	private static final ArrayList<vector> velocities = new ArrayList<vector>();
	private static ArrayList<Float> thetas = new ArrayList<Float>();
	private static final float icicleRange = 100* Rpg.getDp();

	private static final Icicle icicle = new Icicle(15, 0.5f);

	static
	{
		float i = Icicle.staticSpeed;

		float pi = (float) Math.PI;
		velocities.add(new vector(0,i));velocities.add(new vector(i,i));
		velocities.add(new vector(i,0));velocities.add(new vector(i,-i));
		velocities.add(new vector(0,-i));velocities.add(new vector(-i,-i));
		velocities.add(new vector(-i,0));velocities.add(new vector(-i,i));

		thetas.add(pi / 2);
		thetas.add(pi / 4);
		thetas.add((float) 0);
		thetas.add((7 * pi) / 4);
		thetas.add(3 * pi / 2);
		thetas.add((5 * pi) / 4);
		thetas.add(pi);
		thetas.add((3 * pi) / 4);

		//System.out.println(velocities);
		//System.out.println(thetas);
	}


	@Override
	public boolean analyseTouchEvent( TouchEvent event )
	{
		getMM().getCc().getCoordsScreenToMap( event.x ,  event.y , loc );
		cast(getMM());
		return true;
	}


	@Override
	public boolean cast( MM mm )
	{
		super.cast(mm);
		SpellCreationParams params;

		for( int j = 0 ; j < velocities.size() ; ++j )
		{

			params = new SpellCreationParams();
			params.setSpellToBeCopied(icicle);
			params.setShooter(getCaster());
			params.setLocation(loc);
			params.setVelocity(velocities.get(j));
			params.setSpeed(Icicle.staticSpeed);
			params.setRange(icicleRange);
			params.setTeam( getTeamName() );


			mm.add((GameElement) SpellInstanceCreator.getSpellInstance(params));
		}
        dead = true;
		return true;
	}


	@Override
	public boolean cast(MM mm, LivingThing target) {
		return false;
	}


	@Override
	public int calculateDamage() {
		return 0;
	}


	@Override
	public int calculateManaCost( LivingThing aWizard)
	{
		if(aWizard != null )
		{
			return 40 + aWizard.getLQ().getLevel() * 7;
		}
		return 40;
	}



	@Override
	public boolean act()
	{
		return true;
	}


	@Override
	public String getName()
	{
		return "Blizzard";
	}


	@Override
	public void uncast() {
	}



	@Override
	public void loadAnimation()
	{

	}

	@Override
	public boolean hitsOnlyOneThing()
	{
		return false;
	}


	public static ArrayList<Float> getThetas()
	{
		return thetas;
	}


	public static void setThetas(ArrayList<Float> thetas)
	{
		Blizzard.thetas = thetas;
	}



	@Override
	public Spell newInstance() {
		return new Blizzard();
	}



	@Override
	public RectF getPerceivedArea()
	{
		return null;
	}


	@Override
	public Image getIconImage()
	{
		if( iconImage == null )
		{
			//iconImage = Assets.loadImage( R.drawable.blizzard_icon );
		}
		return iconImage;
	}


	@Override
	public Ability.Abilities getAbility()
	{
		return Abilities.BLIZZARD ;
	}







}
