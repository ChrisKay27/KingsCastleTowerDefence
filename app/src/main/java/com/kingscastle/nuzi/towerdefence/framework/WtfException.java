package com.kingscastle.nuzi.towerdefence.framework;

public class WtfException extends IllegalStateException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -742017689720007492L;

	public WtfException(){
	}

	public WtfException(String wtf){
		super(wtf);
	}


	public WtfException(Exception e_) {
		super(e_);
	}


}
