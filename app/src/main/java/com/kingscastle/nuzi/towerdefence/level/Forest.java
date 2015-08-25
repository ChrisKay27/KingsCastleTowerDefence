package com.kingscastle.nuzi.towerdefence.level;

import android.util.Pair;

import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.GameElement;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Buildings;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.level.rounds.AbstractRound;
import com.kingscastle.nuzi.towerdefence.level.rounds.ForestRounds;
import com.kingscastle.nuzi.towerdefence.level.rounds.Round;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Chris on 7/12/2015 for TowerDefence
 */
public class Forest extends TowerDefenceLevel{

    private static final String TAG = "ForestLevel";


    @Override
    protected void addStartEndLocPairs(List<Pair<vector, vector>> startEndLocPairs) {
        vector startLoc = new vector(Rpg.eightDp, 100*dp);
        vector endLoc = new vector(getLevelWidthInPx()-Rpg.eightDp, getLevelHeightInPx() - 60*dp);
        startEndLocPairs.add(new Pair<>(startLoc, endLoc));
    }

    @Override
    protected void createBorder() {
        super.createBorder();

        final int lvlWidthPx = getLevelWidthInPx();
        final int lvlHeightPx = getLevelHeightInPx();

        GameElement ge = getBorderElement(new vector());
        final int treeWidth = (int) ge.getStaticPerceivedArea().width();
        final int treeHeight = (int) ge.getStaticPerceivedArea().height();
        final int numHorz = lvlWidthPx/treeWidth;
        final int numVert = lvlHeightPx /treeHeight;

        for( int i = 0 ; i < (3*numHorz)/4 ; ++i )
            addDecoGE(getBorderElement(new vector(treeWidth / 2 + i * treeWidth, ((numVert/3)*treeHeight) + (treeHeight/2) )));
        for( int i = numHorz/4 ; i < numHorz ; ++i )
            addDecoGE(getBorderElement(new vector(treeWidth / 2 + i * treeWidth, ((2*numVert/3)*treeHeight) + (treeHeight/2) )));
    }

    @Override
    protected Round getRound(AbstractRound.RoundParams rParams) {
        return ForestRounds.getRound(rParams);
    }


    @Override
    protected int getNumberOfRounds() {
        return ForestRounds.getNumberOfRounds();
    }

    public int highestLevelTowersAllowed(){
        return 2;
    }

    @Override
    protected int getStartingGold() {
        return 200;
    }

    private static List<Buildings> allowedBuildings = Collections.unmodifiableList(Arrays.asList(
            new Buildings[]{Buildings.WatchTower,Buildings.Barracks,Buildings.Wall,Buildings.CatapultTower}));

    @Override
    public List<Buildings> getAllowedBuildings(){
        return allowedBuildings;
    }
}
