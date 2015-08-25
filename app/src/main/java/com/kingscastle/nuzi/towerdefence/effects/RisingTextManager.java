package com.kingscastle.nuzi.towerdefence.effects;


import com.kingscastle.nuzi.towerdefence.TowerDefenceGame;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;

import java.util.ArrayList;

public class RisingTextManager
{

	private ArrayList<RisingText> texts = new ArrayList<RisingText>( 100 );
	private final ArrayList<RisingText> deadTexts = new ArrayList<RisingText>( 100 );
	private final ArrayList<RisingText> needsToBeAdded = new ArrayList<RisingText>( 100 );



	//private Game game;
	private boolean editingTexts = false;

	private final MM mm;

	public RisingTextManager(MM mm){
		this.mm = mm;
	}




	public void act()
	{
		clearDeadDamageTexts();
		incrimentGraphics();
	}


	public void add( RisingText txt )
	{
		////Log.d("RisingTextManager", "///RisingTextManager.add()///");
		////Log.d( "RisingTextManager" , "Trying to add RisingText : " + txt );

		if( txt == null )
			return;
		else
		{
			if( editingTexts  )
				needsToBeAdded.add( txt );
			else
				texts.add( txt );
		}
	}


	public void clearDeadDamageTexts()
	{
		RisingText rt;

		editingTexts = true;

		for( int i = texts.size()-1 ; i > -1 ; --i )
		{

			rt = texts.get( i );

			if( rt == null )
			{
				continue;
			}

			if( rt.isDead() )
			{
				deadTexts.add( texts.remove( i ) );
				//deadTexts.add( rt );
			}

		}

		SpecialEffects.freeAll( deadTexts );
		deadTexts.clear();

		//editingTexts = true;

		//texts.removeAll( deadTexts );

		editingTexts = false;



		for( int i = needsToBeAdded.size() - 1 ; i > -1 ; --i )
		{
			texts.add( needsToBeAdded.get( i ) );
		}

		needsToBeAdded.clear();

	}


	public void incrimentGraphics()
	{
		ShouldDrawAnimCalcer sdac = mm.getSdac();
		if( sdac == null )
			if( !TowerDefenceGame.testingVersion )
				return;

		RisingText rt;

		for( int i = texts.size()-1 ; i > -1 ; --i )
		{
			rt = texts.get( i );

			if( rt == null )
				continue;


			rt.shouldDrawThis = sdac.shouldThisRtBeDrawn( rt );
			rt.incrimentGraphics();
		}

	}


	public ArrayList<RisingText> getTexts()
	{
		ArrayList<RisingText> txts = new ArrayList<RisingText>();
		txts.addAll( texts );
		return txts;
	}


	public void nullify()
	{
		texts = null;
	}


	public void addGoldText( int amount , LivingThing on )
	{
		if( amount == 0 || on == null )
		{
			return;
		}
		texts.add( new GoldText( amount , on ) );
	}


	public void addDamageText( int dam , LivingThing on )
	{
		if( dam == 0 || on == null )
		{
			return;
		}
		texts.add( new DamageText( dam + "" , on ) );

	}


}
