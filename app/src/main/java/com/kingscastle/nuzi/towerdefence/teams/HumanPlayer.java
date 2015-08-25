package com.kingscastle.nuzi.towerdefence.teams;


import java.util.ArrayList;

public class HumanPlayer extends Player
{
	private int lives=20;

	public HumanPlayer(){
		super();
	}


	public void livesMinusMinus() {
		lives--;
		synchronized (lvcls){
			for( onLivesValueChangedListener lvcl : lvcls){
				lvcl.onLivesValueChanged(lives);
			}
		}
	}

	public int getLives() {
		return lives;
	}

	public void setLives(Integer lives) {
		this.lives = lives;
		synchronized (lvcls){
			for( onLivesValueChangedListener lvcl : lvcls){
				lvcl.onLivesValueChanged(lives);
			}
		}
	}
	public void removeLives(int costsLives) {
		lives -= costsLives;
		synchronized (lvcls){
			for( onLivesValueChangedListener lvcl : lvcls){
				lvcl.onLivesValueChanged(lives);
			}
		}
	}


	//Lives Value Changed Completed
	private final ArrayList<onLivesValueChangedListener> lvcls = new ArrayList<>();



	public static interface onLivesValueChangedListener{
		void onLivesValueChanged(int newLivesValue);
	}

	public void addLVCL(onLivesValueChangedListener rvcl)		   		{	synchronized( lvcls ){	lvcls.add( rvcl );				}  	}
	public boolean removeLVCL(onLivesValueChangedListener rvcl)		{	synchronized( lvcls ){	return lvcls.remove( rvcl );		}	}
}
