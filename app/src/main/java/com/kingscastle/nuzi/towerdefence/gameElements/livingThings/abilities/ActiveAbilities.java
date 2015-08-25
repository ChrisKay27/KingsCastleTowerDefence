package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.abilities;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class ActiveAbilities {

	private final List<Ability> activeAbilities = new LinkedList<>();
    private final List<Runnable> runnables = new ArrayList<>();


    public void act(){
        synchronized (runnables){
            for (Runnable r: runnables )
                r.run();
            runnables.clear();
        }

        synchronized (activeAbilities) {
            Iterator<Ability> it = activeAbilities.iterator();
            while (it.hasNext()) {
                Ability a = it.next();
                if (a.isOver()) {
                    it.remove();
                    a.undoAbility();
                }
            }
        }
    }


    public boolean containsInstanceOf( Ability a )	{
        synchronized (activeAbilities){
            for (Ability ab : activeAbilities)
                if (ab.getClass() == a.getClass())
                    return true;
        }
		return false;
	}


	public boolean remove(@NotNull final Ability a)	{
        synchronized (runnables) {
            runnables.add(new Runnable() {
                @Override
                public void run() {
                    synchronized (activeAbilities) {
                        activeAbilities.remove(a);
                    }
                    a.undoAbility();
                }
            });
        }
        return true;
	}

	public void add(@NotNull final Ability a) {
        synchronized (runnables) {
            runnables.add(new Runnable() {
                @Override
                public void run() {
                    synchronized (activeAbilities) {
                        if( a.isStackable() || !containsInstanceOf(a) )
                            activeAbilities.add(a);
                    }
                    a.doAbility();
                }
            });
        }
	}




	public void die()
	{
        synchronized (activeAbilities) {
            for (Ability activeAbility : activeAbilities)
                activeAbility.die();
            activeAbilities.clear();
        }
	}




}
