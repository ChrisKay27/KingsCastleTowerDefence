package com.kingscastle.nuzi.towerdefence.level;

import android.util.Log;
import android.util.Pair;

import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.gameElements.Tree;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Buildings;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.level.rounds.AbstractRound;
import com.kingscastle.nuzi.towerdefence.level.rounds.Forest2Rounds;
import com.kingscastle.nuzi.towerdefence.level.rounds.Round;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Chris on 7/12/2015 for TowerDefence
 */
public class Valley extends TowerDefenceLevel{

    private static final String TAG = "ForestLevel2";


    @Override
    protected void addStartEndLocPairs(List<Pair<vector, vector>> startEndLocPairs) {
        vector startLoc = new vector(Rpg.sixTeenDp, Rpg.gridSize*6);
        vector startLoc2 = new vector(Rpg.sixTeenDp, Rpg.gridSize*16);
        vector startLoc3 = new vector(Rpg.sixTeenDp, Rpg.gridSize*25);
        vector endLoc = new vector(getLevelWidthInPx()-Rpg.sixTeenDp, getLevelHeightInPx() - Rpg.gridSize*14);
        startEndLocPairs.add(new Pair<>(startLoc,endLoc));
        startEndLocPairs.add(new Pair<>(startLoc2,endLoc));
        startEndLocPairs.add(new Pair<>(startLoc3,endLoc));
    }



    /**
     * Must be called after the onCreate(UI ui, RectF onScreenArea)
     * or at least at the end of the method.
     */
    protected void createBorder() {
        //Spawn a border of trees
        final int lvlWidthPx = getLevelWidthInPx();
        final int lvlHeightPx = getLevelHeightInPx();

        Tree tree = new Tree();
        final int treeWidth = (int) tree.getStaticPerceivedArea().width();
        final int treeHeight = (int) tree.getStaticPerceivedArea().height();
        final int numHorz = lvlWidthPx/treeWidth;
        final int numVert = lvlHeightPx /treeHeight;

        vector loc = new vector();
        //Top wall of trees
        for( int i = 0 ; i < numHorz ; ++i ){
            tree = new Tree();
            loc.set(treeWidth / 2 + i * treeWidth, treeHeight / 2);
            tree.loc.set(loc);
            tree.updateArea();

            mm.getGridUtil().setProperlyOnGrid(tree.area, Rpg.gridSize);
            GridUtil.getLocFromArea(tree.area, tree.getPerceivedArea(), tree.loc);
            if(mm.getCD().checkPlaceable2(tree.area, false ))
                mm.addGameElement(tree);
            else {
                Log.d(TAG, "Tree not placeable!");
            }
        }

        //Top divider
//        for( int i = 0 ; i < numHorz-6 ; ++i ){
//            tree = new Tree();
//            loc.set(treeWidth / 2 + i * treeWidth, treeHeight*5 + treeHeight / 2);
//            tree.loc.set(loc);
//            tree.updateArea();
//
//            mm.getGridUtil().setProperlyOnGrid(tree.area, Rpg.gridSize);
//            GridUtil.getLocFromArea(tree.area, tree.getPerceivedArea(), tree.loc);
//            if(mm.getCD().checkPlaceable2(tree.area, false ))
//                mm.addGameElement(tree);
//            else {
//                Log.d(TAG, "Tree not placeable!");
//            }
//        }
//
//        //bottom divider
//        for( int i = 6 ; i < numHorz ; ++i ){
//            tree = new Tree();
//            loc.set(treeWidth / 2 + i * treeWidth, treeHeight*10 +treeHeight / 2);
//            tree.loc.set(loc);
//            tree.updateArea();
//
//            mm.getGridUtil().setProperlyOnGrid(tree.area, Rpg.gridSize);
//            GridUtil.getLocFromArea(tree.area, tree.getPerceivedArea(), tree.loc);
//            if(mm.getCD().checkPlaceable2(tree.area, false ))
//                mm.addGameElement(tree);
//            else {
//                Log.d(TAG, "Tree not placeable!");
//            }
//        }

        //Bottom wall of trees
        for( int i = 0 ; i < numHorz ; ++i ){
            tree = new Tree();
            loc.set((treeWidth / 2) + (i * treeWidth), lvlHeightPx - (treeHeight / 2));
            tree.loc.set(loc);
            tree.updateArea();

            mm.getGridUtil().setProperlyOnGrid(tree.area, Rpg.gridSize);
            GridUtil.getLocFromArea(tree.area, tree.getPerceivedArea(), tree.loc);
            if(mm.getCD().checkPlaceable2(tree.area, false ))
                mm.addGameElement(tree);
            else {
                Log.d(TAG, "Tree not placeable!");
            }
        }

        //Left wall of trees
        for( int j = 0 ; j < numVert ; ++j ){
            if( 2 == j || 3 == j || 7 == j || 8 == j || 11 == j || 12 == j ) continue;

            tree = new Tree();
            loc.set( (treeWidth / 2) , (treeHeight / 2) + (j * treeHeight));
            tree.loc.set(loc);
            tree.updateArea();

            mm.getGridUtil().setProperlyOnGrid(tree.area, Rpg.gridSize);
            GridUtil.getLocFromArea(tree.area, tree.getPerceivedArea(), tree.loc);
            if(mm.getCD().checkPlaceable2(tree.area, false ))
                mm.addGameElement(tree);
            else {
                Log.d(TAG, "Tree not placeable!");
            }
        }

        //Right wall of trees
        for( int j = 0 ; j < numVert ; ++j ){
            if( 7 == j || 8 == j ) continue;

            tree = new Tree();
            loc.set( lvlWidthPx - (treeWidth / 2) , (treeHeight / 2) + (j * treeHeight));
            tree.loc.set(loc);
            tree.updateArea();

            mm.getGridUtil().setProperlyOnGrid(tree.area, Rpg.gridSize);
            GridUtil.getLocFromArea(tree.area, tree.getPerceivedArea(), tree.loc);
            if(mm.getCD().checkPlaceable2(tree.area, false ))
                mm.addGameElement(tree);
            else {
                Log.d(TAG, "Tree not placeable!");
            }
        }

        addDecoGE(new vector(15 * Rpg.gridSize, 15 * Rpg.gridSize), R.drawable.farm);
        addDecoGE(new vector(17 * Rpg.gridSize, 16 * Rpg.gridSize), R.drawable.hay);
        addDecoGE(new vector(30 * Rpg.gridSize, 22 * Rpg.gridSize), R.drawable.farm);
        addDecoGE(new vector(30 * Rpg.gridSize, 24 * Rpg.gridSize), R.drawable.hay);
        addDecoGE(new vector(35 * Rpg.gridSize, 18 * Rpg.gridSize), R.drawable.tree_apple);
        addDecoGE(new vector(12 * Rpg.gridSize, 8 * Rpg.gridSize), R.drawable.tree_whiteflowers);
        addDecoGE(new vector(44 * Rpg.gridSize, 4 * Rpg.gridSize), R.drawable.lumber_mill_complete);
        addDecoGE(new vector(42 * Rpg.gridSize, 3 * Rpg.gridSize), R.drawable.logs_horz);

        for( int i=0 ; i < 10 ; ++i )
            addDeco(new vector(Rpg.thirtyDp + Math.random()*(getLevelWidthInPx()-Rpg.thirtyDp),
                    Rpg.thirtyDp + Math.random()*(getLevelHeightInPx()-Rpg.thirtyDp)), R.drawable.log_mold);

        for( int i=0 ; i < 10 ; ++i )
            addDeco(new vector(Rpg.thirtyDp + Math.random()*(getLevelWidthInPx()-Rpg.thirtyDp),
                    Rpg.thirtyDp + Math.random()*(getLevelHeightInPx()-Rpg.thirtyDp)), R.drawable.stump1);
        for( int i=0 ; i < 10 ; ++i )
            addDeco(new vector(Rpg.thirtyDp + Math.random()*(getLevelWidthInPx()-Rpg.thirtyDp),
                    Rpg.thirtyDp + Math.random()*(getLevelHeightInPx()-Rpg.thirtyDp)), R.drawable.stump2);
        for( int i=0 ; i < 10 ; ++i )
            addDeco(new vector(Rpg.thirtyDp + Math.random()*(getLevelWidthInPx()-Rpg.thirtyDp),
                    Rpg.thirtyDp + Math.random()*(getLevelHeightInPx()-Rpg.thirtyDp)), R.drawable.stump3);

    }



    @Override
    protected Round getRound(AbstractRound.RoundParams rParams) {
        return Forest2Rounds.getRound(rParams);
    }


    @Override
    protected int getNumberOfRounds() {
        return Forest2Rounds.getNumberOfRounds();
    }

    public int highestLevelTowersAllowed(){
        return 2;
    }


    @Override
    protected int getStartingGold() {
        return 300;
    }

    private static List<Buildings> allowedBuildings = Collections.unmodifiableList(
            Arrays.asList(Buildings.WatchTower, Buildings.Barracks,Buildings.CatapultTower, Buildings.Wall, Buildings.FireDragonTower));

    @Override
    public List<Buildings> getAllowedBuildings(){
        return allowedBuildings;
    }
}
