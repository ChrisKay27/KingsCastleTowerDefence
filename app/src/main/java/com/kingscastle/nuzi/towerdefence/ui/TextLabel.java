package com.kingscastle.nuzi.towerdefence.ui;

import android.graphics.Paint;

import com.kingscastle.nuzi.towerdefence.gameUtils.vector;


public class TextLabel
{

	private String msg;
	private vector loc;
	private Paint paint;
	private boolean visible = true;


	public TextLabel( String msg,vector loc,Paint paint)
	{
		this.msg=msg;this.loc=loc;this.paint=paint;
	}

	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	public vector getLoc() {
		return loc;
	}


	public void setLoc(vector loc) {
		this.loc = loc;
	}


	public Paint getPaint() {
		return paint;
	}


	public void setPaint(Paint paint) {
		this.paint = paint;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}



}
