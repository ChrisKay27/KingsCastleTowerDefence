package com.kingscastle.nuzi.towerdefence.gameUtils;

import java.util.Collection;

public class VectorUtil {







	/**
	 * Does not synchronize on the list locs
	 * @param locs
	 * @param toThis
	 * @return null if locs or toThis is null
	 */
	public static vector getClosest( Collection<vector> locs , vector toThis ){
		if( locs == null ) return null;
		if( toThis == null ) return null;

		float x = toThis.x , y = toThis.y;
		float closestDist = Float.MAX_VALUE;
		vector closest=null;

		for( vector v : locs ){
			float temp = v.distanceSquared(x, y);
			if( temp < closestDist ){
				closestDist = temp;
				closest = v;
			}
		}
		return closest;
	}























}
