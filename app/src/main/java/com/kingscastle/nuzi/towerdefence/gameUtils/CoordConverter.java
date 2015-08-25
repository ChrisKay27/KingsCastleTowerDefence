package com.kingscastle.nuzi.towerdefence.gameUtils;

public interface CoordConverter {

	public int getMapWidth();
	public int getMapHeight();

	public vector getCoordsScreenToMap(float x, float y, vector intoThis);

	public vector getCoordsScreenToMap(vector v, vector intoThis);


	public vector getCoordsMapToScreen(float x, float y, vector intoThis);


	public vector getCoordsMapToScreen(vector v, vector intoThis);


}
