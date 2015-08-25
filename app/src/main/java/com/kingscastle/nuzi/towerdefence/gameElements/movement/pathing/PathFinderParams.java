package com.kingscastle.nuzi.towerdefence.gameElements.movement.pathing;

import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

/**
 * Created by chris_000 on 7/5/2015.
 */
public class PathFinderParams {

    public Grid grid;
    public int mapWidthInPx,mapHeightInPx;
    public vector fromHere,toHere;

    public PathFoundListener pathFoundListener;
}
