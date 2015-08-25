package com.kingscastle.nuzi.towerdefence.level;

import android.util.Log;

import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.Rock;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

/**
 * Created by chris_000 on 7/10/2015.
 */
public class HellLevel extends Level {


    private static final String TAG = "HellLevel";



    /**
     * Must be called after the onCreate(UI ui, RectF onScreenArea)
     * or at least at the end of the method.
     */
    protected void createBorder() {
        //Spawn a border of trees
        final int lvlWidthPx = getLevelWidthInPx();
        final int lvlHeightPx = getLevelHeightInPx();

        Rock tree = new Rock();
        final int treeWidth = (int) tree.getStaticPerceivedArea().width();
        final int treeHeight = (int) tree.getStaticPerceivedArea().height();
        final int numHorz = lvlWidthPx/treeWidth;
        final int numVert = lvlHeightPx /treeHeight;

        vector loc = new vector();
        //Top wall of trees
        for( int i = 0 ; i < numHorz ; ++i ){
            tree = new Rock();
            loc.set(treeWidth / 2 + i * treeWidth, treeHeight / 2);
            tree.loc.set(loc);
            tree.updateArea();

            mm.getGridUtil().setProperlyOnGrid(tree.area, Rpg.gridSize);
            GridUtil.getLocFromArea(tree.area, tree.getPerceivedArea(), tree.loc);
            if(mm.getCD().checkPlaceable2(tree.area, false ))
                mm.addGameElement(tree);
            else {
                Log.d(TAG, "Rock not placeable!");
            }
        }

        //Top divider
        for( int i = 0 ; i < numHorz-6 ; ++i ){
            tree = new Rock();
            loc.set(treeWidth / 2 + i * treeWidth, treeHeight*5 + treeHeight / 2);
            tree.loc.set(loc);
            tree.updateArea();

            mm.getGridUtil().setProperlyOnGrid(tree.area, Rpg.gridSize);
            GridUtil.getLocFromArea(tree.area, tree.getPerceivedArea(), tree.loc);
            if(mm.getCD().checkPlaceable2(tree.area, false ))
                mm.addGameElement(tree);
            else {
                Log.d(TAG, "Rock not placeable!");
            }
        }

        //bottom divider
        for( int i = 6 ; i < numHorz ; ++i ){
            tree = new Rock();
            loc.set(treeWidth / 2 + i * treeWidth, treeHeight*10 +treeHeight / 2);
            tree.loc.set(loc);
            tree.updateArea();

            mm.getGridUtil().setProperlyOnGrid(tree.area, Rpg.gridSize);
            GridUtil.getLocFromArea(tree.area, tree.getPerceivedArea(), tree.loc);
            if(mm.getCD().checkPlaceable2(tree.area, false ))
                mm.addGameElement(tree);
            else {
                Log.d(TAG, "Rock not placeable!");
            }
        }

        //Bottom wall of trees
        for( int i = 0 ; i < numHorz ; ++i ){
            tree = new Rock();
            loc.set((treeWidth / 2) + (i * treeWidth), lvlHeightPx - (treeHeight / 2));
            tree.loc.set(loc);
            tree.updateArea();

            mm.getGridUtil().setProperlyOnGrid(tree.area, Rpg.gridSize);
            GridUtil.getLocFromArea(tree.area, tree.getPerceivedArea(), tree.loc);
            if(mm.getCD().checkPlaceable2(tree.area, false ))
                mm.addGameElement(tree);
            else {
                Log.d(TAG, "Rock not placeable!");
            }
        }

        //Left wall of trees
        for( int j = 0 ; j < numVert ; ++j ){
            tree = new Rock();
            loc.set( (treeWidth / 2) , (treeHeight / 2) + (j * treeHeight));
            tree.loc.set(loc);
            tree.updateArea();

            mm.getGridUtil().setProperlyOnGrid(tree.area, Rpg.gridSize);
            GridUtil.getLocFromArea(tree.area, tree.getPerceivedArea(), tree.loc);
            if(mm.getCD().checkPlaceable2(tree.area, false ))
                mm.addGameElement(tree);
            else {
                Log.d(TAG, "Rock not placeable!");
            }
        }

        //Right wall of trees
        for( int j = 0 ; j < numVert ; ++j ){
            tree = new Rock();
            loc.set( lvlWidthPx - (treeWidth / 2) , (treeHeight / 2) + (j * treeHeight));
            tree.loc.set(loc);
            tree.updateArea();

            mm.getGridUtil().setProperlyOnGrid(tree.area, Rpg.gridSize);
            GridUtil.getLocFromArea(tree.area, tree.getPerceivedArea(), tree.loc);
            if(mm.getCD().checkPlaceable2(tree.area, false ))
                mm.addGameElement(tree);
            else {
                Log.d(TAG, "Rock not placeable!");
            }
        }
    }
}
