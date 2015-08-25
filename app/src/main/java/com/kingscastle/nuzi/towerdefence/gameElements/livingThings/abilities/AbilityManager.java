package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.abilities;


import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class AbilityManager
{
	private static final String TAG = "AbilityManager";

	private final List<Ability> activeAbilities = new LinkedList<>();

	private final MM mm;

	private final List<Runnable> runnables = new ArrayList<>();

	public AbilityManager( @NotNull MM mm )	{
		this.mm = mm;
	}

	public void act()	{
		synchronized (runnables) {
			for (Runnable r : runnables)
				r.run();
			runnables.clear();
		}

		if( mm.getLevel().isPaused() )
			return;

		try {
			synchronized (activeAbilities) {
				Iterator<Ability> i = activeAbilities.listIterator();
				while (i.hasNext())
					if (i.next().act())
						i.remove();
			}
		}
		catch( Throwable t ) {
			t.printStackTrace();
		}
	}


	public boolean add(@NotNull final Ability a)
	{
		synchronized (runnables){
			runnables.add(new Runnable() {
				@Override
				public void run() {
                if( a.cast( mm ) ){
                    synchronized (activeAbilities) {
                        activeAbilities.add(a);
                    }
                }
				}
			});
		}
		return true;
	}





	public List<Ability> getAbilities()
	{
		synchronized (activeAbilities) {
			return new ArrayList<>(activeAbilities);
		}
	}


}
