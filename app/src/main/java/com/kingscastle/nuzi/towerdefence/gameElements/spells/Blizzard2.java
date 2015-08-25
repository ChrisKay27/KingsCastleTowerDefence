package com.kingscastle.nuzi.towerdefence.gameElements.spells;

import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.GameElement;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;


public class Blizzard2 extends InstantSpell {

	private static Image iconImage;

	private static final ArrayList<vector> velocities = new ArrayList<vector>();
	private static final ArrayList<vector> initialForces = new ArrayList<vector>();
	private static ArrayList<Float> thetas = new ArrayList<Float>();
	private static final float icicleRangeSquared= 10000* Rpg.getDp()*Rpg.getDp();
	private static final Icicle icicle = new Icicle(15, 0.5f);


	@Override
	public Abilities getAbility()				 {				return Abilities.BLIZZARD ; 			}
	static
	{

		float i = Icicle.staticSpeed;
		float pi = (float) Math.PI;
		velocities.add(new vector(0,i));velocities.add(new vector(i,i));
		velocities.add(new vector(i,0));velocities.add(new vector(i,-i));
		velocities.add(new vector(0,-i));velocities.add(new vector(-i,-i));
		velocities.add(new vector(-i,0));velocities.add(new vector(-i,i));

		for( int j = 0 ; j < velocities.size() ; j++ )
		{
			vector v = new vector(velocities.get(j));
			vector v2 = new vector(velocities.get(j));

			v.set(v.getY(),v.getX());
			//v2.set(v2.getX()/10,v2.getY()/10);
			System.out.println("initialForces.. v2 = " + v2);
			//v.add(v2);
			System.out.println("initialForces["+j+"] = " + v);
			initialForces.add(v);

		}

		thetas.add(Float.valueOf(pi/2));
		thetas.add(Float.valueOf(pi/4));
		thetas.add(Float.valueOf(0));
		thetas.add(Float.valueOf((7*pi)/4));
		thetas.add(Float.valueOf(3*pi/2));
		thetas.add(Float.valueOf((5*pi)/4));
		thetas.add(Float.valueOf(pi));
		thetas.add(Float.valueOf((3*pi)/4));

		System.out.println(velocities);
		System.out.println(initialForces);
		System.out.println(thetas);

	}



	@Override
	public boolean cast( MM mm )
	{
		super.cast(mm);
		SpellCreationParams params;

		for( int j = 0 ; j < velocities.size() ; ++j )
		{
			System.out.println(velocities.get(j));

			System.out.println(thetas.get(j));

			params = new SpellCreationParams();
			params.setSpellToBeCopied(icicle);
			params.setShooter(getCaster());
			params.setLocation(loc);
			params.setVelocity(velocities.get(j));
			params.setSpeed(Icicle.staticSpeed);
			params.setRangeSquared(icicleRangeSquared);
			params.setTeam(getCaster().getTeamName());

			Icicle i = (Icicle) SpellInstanceCreator.getSpellInstance(params);
			i.setForce(new vector(initialForces.get(j)));

			mm.add( (GameElement) i );
		}
		return true;
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
		return 25;
	}






	@Override
	public boolean act()
	{
		return isDead();
	}




	@Override
	public String getName() {
		return "Blizzard2";
	}

	@Override
	public void uncast() {
	}


	@Override
	public void loadAnimation() {

	}

	public static ArrayList<Float> getThetas() {
		return thetas;
	}

	public static void setThetas(ArrayList<Float> thetas) {
		Blizzard2.thetas = thetas;
	}



	@Override
	public Spell newInstance() {
		return new Blizzard2();
	}





	@Override
	public Image getIconImage()
	{
		if( iconImage == null )
		{
			//	iconImage = Assets.loadImage(R.drawable.blizzard_icon);
		}
		return iconImage;
	}

}
