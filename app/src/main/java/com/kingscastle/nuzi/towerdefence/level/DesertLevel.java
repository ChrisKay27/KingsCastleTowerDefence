package com.kingscastle.nuzi.towerdefence.level;

import android.util.Pair;

import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.gameElements.GameElement;
import com.kingscastle.nuzi.towerdefence.gameElements.Rock;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Buildings;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.level.rounds.AbstractRound;
import com.kingscastle.nuzi.towerdefence.level.rounds.DesertRounds;
import com.kingscastle.nuzi.towerdefence.level.rounds.Round;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Chris on 7/12/2015 for TowerDefence
 */
public class DesertLevel extends TowerDefenceLevel {

    private static final String TAG = "DesertLevel";


    @Override
    protected void subOnCreate() {
        super.subOnCreate();
        background.getGidBackground().setBackgroundImage(Assets.loadImage(R.drawable.desert_tile));
    }

    @Override
    protected int getStartingGold() {
        return 400;
    }

    @Override
    protected void addStartEndLocPairs(List<Pair<vector, vector>> startEndLocPairs) {

        {
            vector startLoc = new vector(Rpg.hundDp, getLevelHeightInPx()-Rpg.sixTeenDp);
            vector endLoc = new vector(getLevelWidthInPx() / 2, getLevelHeightInPx() / 2);
            startEndLocPairs.add(new Pair<>(startLoc, endLoc));
        }
        {
            vector startLoc = new vector(getLevelWidthInPx() - Rpg.hundDp, Rpg.sixTeenDp);
            vector endLoc = new vector(getLevelWidthInPx() / 2, getLevelHeightInPx() / 2);
            startEndLocPairs.add(new Pair<>(startLoc, endLoc));
        }
    }


    @Override
    protected Round getRound(AbstractRound.RoundParams rParams) {
        return DesertRounds.getRound(rParams);
    }

    @Override
    public int highestLevelTowersAllowed() {
        return 3;
    }

    protected void createBorder() {
        super.createBorder();

        final int lvlWidthPx = getLevelWidthInPx();
        final int lvlHeightPx = getLevelHeightInPx();

        GameElement ge = getBorderElement(new vector());
        final int treeWidth = (int) ge.getStaticPerceivedArea().width();
        final int treeHeight = (int) ge.getStaticPerceivedArea().height();
        final int numHorz = lvlWidthPx/treeWidth;
        final int numVert = lvlHeightPx /treeHeight;


        for( int j = numVert/3 ; j < numVert ; ++j )
            addDecoGE(getBorderElement(new vector((numHorz/4)*treeWidth + (treeWidth / 2) , (treeHeight / 2) + (j * treeHeight))));

        for( int j = 0 ; j < (numVert*2)/3 ; ++j )
            addDecoGE(getBorderElement(new vector((3*numHorz/4)*treeWidth+(treeWidth / 2) , (treeHeight / 2) + (j * treeHeight))));


        Random rand = new Random(685823736592l);
        for( int i = 0 ; i < 20 ; ++i )
            addDecoGE(new Rock(new vector(rand.nextInt(50) * Rpg.gridSize, rand.nextInt(30) * Rpg.gridSize)));
    }

    protected GameElement getBorderElement(vector v){
        return new Rock(v);
    }

    @Override
    protected int getNumberOfRounds() {
        return DesertRounds.getNumberOfRounds();
    }

    private static List<Buildings> allowedBuildings = Collections.unmodifiableList(
            Arrays.asList(Buildings.WatchTower, Buildings.Barracks, Buildings.Wall, Buildings.CatapultTower,
                    Buildings.FireDragonTower, Buildings.IceDragonTower));

    @Override
    public List<Buildings> getAllowedBuildings(){
        return allowedBuildings;
    }
}
