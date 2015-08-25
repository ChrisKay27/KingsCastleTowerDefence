package com.kingscastle.nuzi.towerdefence.framework;


import com.kingscastle.nuzi.towerdefence.TowerDefenceGame;

public abstract class Screen
{

	protected TowerDefenceGame game;


	protected boolean firstTimeCreated=false;

	protected Screen(TowerDefenceGame game)
	{
		this.game = game;
	}

	protected void setGame(TowerDefenceGame game)
	{
		this.game = game;
	}

	public abstract void update();

	public abstract void pause();

	public abstract void resume();

	public abstract void dispose();

	public abstract void backButton();

	public void paint(Graphics g)
	{
	}

	public void updateUI() {
	}
}

