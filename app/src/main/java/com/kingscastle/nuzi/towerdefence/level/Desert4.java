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
import com.kingscastle.nuzi.towerdefence.level.rounds.Desert4Rounds;
import com.kingscastle.nuzi.towerdefence.level.rounds.Round;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Chris on 7/12/2015 for TowerDefence
 */
public class Desert4 extends TowerDefenceLevel {

    private static final String TAG = Desert4.class.getSimpleName();


    @Override
    protected void subOnCreate() {
        super.subOnCreate();
        background.getGidBackground().setBackgroundImage(Assets.loadImage(R.drawable.desert_tile));
    }

    @Override
    protected int getStartingGold() {
        return 1500;
    }

    @Override
    protected void addStartEndLocPairs(List<Pair<vector, vector>> startEndLocPairs) {

        vector topLeft = new vector(Rpg.sixTeenDp, Rpg.twoHundDp);
        vector bottomLeft = new vector(Rpg.sixTeenDp, getLevelHeightInPx()-Rpg.hundDp);

        vector topRight = new vector(getLevelWidthInPx() - Rpg.sixTeenDp, Rpg.twoHundDp);
        vector bottomRight = new vector(getLevelWidthInPx() - Rpg.sixTeenDp, getLevelHeightInPx()-Rpg.hundDp);
                
        startEndLocPairs.add(new Pair<>(topLeft, topRight));
        startEndLocPairs.add(new Pair<>(bottomLeft,bottomRight));
        startEndLocPairs.add(new Pair<>(topRight, bottomLeft));
        startEndLocPairs.add(new Pair<>(bottomRight,topLeft));      
    }


    @Override
    protected Round getRound(AbstractRound.RoundParams rParams) {
        return Desert4Rounds.getRound(rParams);
    }

    @Override
    public int highestLevelTowersAllowed() {
        return 4;
    }


    /**
     * Must be called after the onCreate(UI ui, RectF onScreenArea)
     * or at least at the end of the method.
     */
    @Override
    protected void createBorder() {
        super.createBorder();

        Random rand = new Random(84989678678389386l);
        for( int i = 0 ; i < 40 ; ++i )
            addDecoGE(new Rock(new vector(rand.nextInt(50) * Rpg.gridSize, rand.nextInt(30) * Rpg.gridSize)));

    }

    @Override
    protected GameElement getBorderElement(vector v){
        return new Rock(v);
    }

    @Override
    protected int getNumberOfRounds() {
        return Desert4Rounds.getNumberOfRounds();
    }

    private static List<Buildings> allowedBuildings = Collections.unmodifiableList(
            Arrays.asList(Buildings.WatchTower, Buildings.Barracks, Buildings.Wall, Buildings.CatapultTower,
                    Buildings.FireDragonTower, Buildings.IceDragonTower, Buildings.ShockDragonTower));

    @Override
    public List<Buildings> getAllowedBuildings(){
        return allowedBuildings;
    }
}
