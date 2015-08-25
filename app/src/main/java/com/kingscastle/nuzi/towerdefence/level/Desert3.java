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
import com.kingscastle.nuzi.towerdefence.level.rounds.Desert3Rounds;
import com.kingscastle.nuzi.towerdefence.level.rounds.DesertRounds;
import com.kingscastle.nuzi.towerdefence.level.rounds.Round;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Chris on 7/12/2015 for TowerDefence
 */
public class Desert3 extends TowerDefenceLevel {

    private static final String TAG = Desert3.class.getSimpleName();


    @Override
    protected void subOnCreate() {
        super.subOnCreate();
        background.getGidBackground().setBackgroundImage(Assets.loadImage(R.drawable.desert_tile));
    }

    @Override
    protected int getStartingGold() {
        return 1000;
    }

    @Override
    protected void addStartEndLocPairs(List<Pair<vector, vector>> startEndLocPairs) {
        vector topLeft = new vector(Rpg.eightDp, Rpg.eightDp);
        vector bottomRight = new vector(getLevelWidthInPx() - Rpg.eightDp, getLevelHeightInPx() - Rpg.eightDp);

        startEndLocPairs.add(new Pair<>(topLeft, bottomRight));

        startEndLocPairs.add(new Pair<>(bottomRight, topLeft));

    }


    @Override
    protected Round getRound(AbstractRound.RoundParams rParams) {
        return Desert3Rounds.getRound(rParams);
    }

    @Override
    public int highestLevelTowersAllowed() {
        return 4;
    }


    @Override
    protected void createBorder() {
        super.createBorder();

        Random rand = new Random(829686829296778692l);
        for( int i = 0 ; i < 60 ; ++i )
            addDecoGE(new Rock(new vector(rand.nextInt(50) * Rpg.gridSize, rand.nextInt(30) * Rpg.gridSize)));
    }

    @Override
    protected GameElement getBorderElement(vector v){
        return new Rock(v);
    }

    @Override
    protected int getNumberOfRounds() {
        return DesertRounds.getNumberOfRounds();
    }

    private static List<Buildings> allowedBuildings = Collections.unmodifiableList(
            Arrays.asList(Buildings.WatchTower, Buildings.Barracks, Buildings.Wall, Buildings.CatapultTower,
                    Buildings.FireDragonTower, Buildings.IceDragonTower, Buildings.ShockDragonTower));

    @Override
    public List<Buildings> getAllowedBuildings(){
        return allowedBuildings;
    }
}
