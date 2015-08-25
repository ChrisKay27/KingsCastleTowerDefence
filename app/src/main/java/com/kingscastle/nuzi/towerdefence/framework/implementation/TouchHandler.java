package com.kingscastle.nuzi.towerdefence.framework.implementation;

import android.view.View.OnTouchListener;

import com.kingscastle.nuzi.towerdefence.framework.Input;

import java.util.List;

interface TouchHandler extends OnTouchListener {
	public boolean isTouchDown(int pointer);

	public int getTouchX(int pointer);

	public int getTouchY(int pointer);

	public List<Input.TouchEvent> getTouchEvents();

}
