package com.kingscastle.nuzi.towerdefence.gameElements.spells;

import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class SpellManager
{
	private static final String TAG = "SpellManager";

	private final MM mm;

	private final ArrayList<Spell> spells = new ArrayList<>( 100 );

	private final List<Runnable> runnables = new ArrayList<>();

	private Spell pendingSpell;

	public SpellManager( MM mm) {
		this.mm = mm;
	}


	public boolean add( @NotNull final Spell s )
	{
		synchronized (runnables){
			runnables.add(new Runnable() {
				@Override
				public void run() {
					if (s.create(mm) && s.cast(mm)) {
						if (!s.hasBeenCreatedProperly())
							throw new IllegalStateException("You must call super.create(mm) all subclasses of game element.");

						spells.add(s);
					}
				}
			});
		}
		return true;
	}

	public void act()
	{
		synchronized (runnables) {
			for (Runnable r : runnables)
				r.run();
			runnables.clear();
		}

		if( mm.getLevel().isPaused() )
			return;

		try
		{
			synchronized (runnables) {
				Iterator<Spell> i = spells.listIterator();
				while (i.hasNext())
					if (i.next().act())
						i.remove();
			}
		}
		catch( Throwable t )
		{
			t.printStackTrace();
		}
	}

	public boolean canCastOn( @NotNull LivingThing on, @NotNull Spell s)
	{
		//System.out.println("canCastOn() on=" + on + " spell=" + s);

		if (s == null || on == null)
			return false;

		synchronized (runnables) {
			for (Spell sp : spells) {
				if (sp.toString().equals(s.toString())) {
					if (sp.getCaster() == s.getCaster()) {
						if (!sp.isDead()) {
							//System.out
							//.println("spell already exists with the same caster, returning false");
							return false;
						}
					}
				}
			}
		}
		return true;
	}



	public Spell getPendingSpell() {
		return pendingSpell;
	}

	public void setPendingSpell(Spell pendingSpell) {
		this.pendingSpell = pendingSpell;
	}


	public ArrayList<Spell> getSpells() {
		return new ArrayList<>(spells);
	}



}
