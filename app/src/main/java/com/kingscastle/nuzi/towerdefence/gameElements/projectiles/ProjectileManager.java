package com.kingscastle.nuzi.towerdefence.gameElements.projectiles;


import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.teams.Teams;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ProjectileManager
{
	private final MM mm;

	private final Teams team;

	private List<Projectile> projectiles = new LinkedList<>();

	private final List<Runnable> runnables = new ArrayList<>();


	public ProjectileManager( Teams team, MM mm )	{
		this.team = team;
		this.mm = mm;
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
			Iterator<Projectile> i = projectiles.listIterator();
			while(i.hasNext())
				if(i.next().act())
					i.remove();
		}
		catch( Throwable t )
		{
			t.printStackTrace();
		}
	}




	public boolean add( @NotNull final Projectile p )	{
		if( team != p.getTeamName() )
			throw new IllegalArgumentException("projectile "+p+" has the wrong team!!");

		synchronized (runnables){
			runnables.add(new Runnable() {
				@Override
				public void run() {
					if( p.create( mm ) ){
						if( !p.hasBeenCreatedProperly() )
							throw new IllegalStateException("You must call super.create(mm) all subclasses of game element.");

						projectiles.add( p );
					}
				}
			});
		}
		return true;
	}




	public List<Projectile> getProjectiles()	{
		return projectiles;
	}



	public void saveYourSelf(BufferedWriter b) throws IOException
	{
		String s;

		s = "<ProjectileManager team=\"" + team + "\" >" ;


		b.write(s,0,s.length());b.newLine();

		for(Projectile p:projectiles)
		{
			p.saveYourSelf(b);
		}


		s = "</ProjectileManager>";

		b.write(s,0,s.length());b.newLine();
	}


	public void setProjectiles(ArrayList<Projectile> projectiles2)
	{
		projectiles=projectiles2;
	}



}
