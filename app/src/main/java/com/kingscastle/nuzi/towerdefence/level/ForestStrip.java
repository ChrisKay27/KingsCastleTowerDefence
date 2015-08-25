package com.kingscastle.nuzi.towerdefence.level;

import android.graphics.RectF;
import android.util.Log;
import android.util.Pair;

import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.gameElements.Tree;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Buildings;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.level.rounds.AbstractRound;
import com.kingscastle.nuzi.towerdefence.level.rounds.ForestStripRounds;
import com.kingscastle.nuzi.towerdefence.level.rounds.Round;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Chris on 7/12/2015 for TowerDefence
 */
public class ForestStrip extends TowerDefenceLevel {

    private static final String TAG = ForestStrip.class.getSimpleName();


    @Override
    protected void addStartEndLocPairs(List<Pair<vector, vector>> startEndLocPairs) {

        vector startLoc = new vector(getLevelWidthInPx()-Rpg.sixTeenDp, getLevelHeightInPx()/2);
        vector endLoc = new vector(Rpg.sixTeenDp, getLevelHeightInPx()/2);
        startEndLocPairs.add(new Pair<>(startLoc,endLoc));
    }



    /**
     * Must be called after the onCreate(UI ui, RectF onScreenArea)
     * or at least at the end of the method.
     */
    @Override
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
        for( int j = 0 ; j < numVert/3 ; ++j ) {
            for (int i = 0; i < numHorz; ++i) {
                tree = new Tree();
                loc.set(treeWidth / 2 + i * treeWidth, treeHeight / 2 + j * treeHeight);
                tree.loc.set(loc);
                tree.updateArea();

                mm.getGridUtil().setProperlyOnGrid(tree.area, Rpg.gridSize);
                GridUtil.getLocFromArea(tree.area, tree.getPerceivedArea(), tree.loc);

                boolean ¤‿¤  = false;
                for (RectF noBuildZone : noBuildZones)
                    if (RectF.intersects(tree.area, noBuildZone)) {
                        ¤‿¤ = true;
                        break;
                    }
                if (¤‿¤) continue;

                if (mm.getCD().checkPlaceable2(tree.area, false))
                    mm.addGameElement(tree);
                else {
                    Log.d(TAG, "Tree not placeable!");
                }
            }
        }


        for( int j = (2*numVert)/3 ; j < numVert ; ++j ) {
            for (int i = 0; i < numHorz; ++i) {
                tree = new Tree();
                loc.set(treeWidth / 2 + i * treeWidth, treeHeight / 2 + j * treeHeight);
                tree.loc.set(loc);
                tree.updateArea();

                mm.getGridUtil().setProperlyOnGrid(tree.area, Rpg.gridSize);
                GridUtil.getLocFromArea(tree.area, tree.getPerceivedArea(), tree.loc);

                boolean continu = false;
                for (RectF noBuildZone : noBuildZones)
                    if (RectF.intersects(tree.area, noBuildZone)) {
                        continu = true;
                        break;
                    }
                if (continu) continue;

                if (mm.getCD().checkPlaceable2(tree.area, false))
                    mm.addGameElement(tree);
                else {
                    Log.d(TAG, "Tree not placeable!");
                }
            }
        }

        Random rand = new Random(58567673937856l);

        for( int i=0 ; i < 6 ; ++i ){
            addDeco(new vector(rand.nextInt(lvlWidthPx), (numVert/3 + rand.nextInt(numVert/3))*Rpg.gridSize), R.drawable.log_mold);
            addDeco(new vector(rand.nextInt(lvlWidthPx), (numVert/3 + rand.nextInt(numVert/3))*Rpg.gridSize), R.drawable.stump1);
            addDeco(new vector(rand.nextInt(lvlWidthPx), (numVert/3 + rand.nextInt(numVert/3))*Rpg.gridSize), R.drawable.stump2);
            addDeco(new vector(rand.nextInt(lvlWidthPx), (numVert/3 + rand.nextInt(numVert/3))*Rpg.gridSize), R.drawable.stump3);
        }
    }



    @Override
    protected Round getRound(AbstractRound.RoundParams rParams) {
        return ForestStripRounds.getRound(rParams);
    }


    @Override
    protected int getNumberOfRounds() {
        return ForestStripRounds.getNumberOfRounds();
    }

    @Override
    public int highestLevelTowersAllowed(){
        return 2;
    }


    @Override
    protected int getStartingGold() {
        return 300;
    }

    private static List<Buildings> allowedBuildings = Collections.unmodifiableList(
            Arrays.asList(Buildings.WatchTower, Buildings.Barracks,Buildings.CatapultTower, Buildings.Wall,
                    Buildings.FireDragonTower, Buildings.ShockDragonTower));

    @Override
    public List<Buildings> getAllowedBuildings(){
        return allowedBuildings;
    }
}
