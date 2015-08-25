package com.kingscastle.nuzi.towerdefence.gameUtils;


import com.kingscastle.nuzi.towerdefence.gameElements.Cost;

public interface Queueable
{
	public String getName();
	public int getBuildTime();
	public void setBuildTime(int bt);
	public void queueableComplete();
	public Cost getCosts();
}
