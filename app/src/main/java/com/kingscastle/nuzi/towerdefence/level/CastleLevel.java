package com.kingscastle.nuzi.towerdefence.level;

import android.util.Pair;

import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Buildings;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.level.rounds.AbstractRound;
import com.kingscastle.nuzi.towerdefence.level.rounds.CastleRounds;
import com.kingscastle.nuzi.towerdefence.level.rounds.Round;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Chris on 7/12/2015 for TowerDefence
 */
public class CastleLevel extends TowerDefenceLevel{

    private static final String TAG = "CastleLevel";

    @Override
    protected void subOnCreate() {
        super.subOnCreate();
        background.getGidBackground().setBackgroundImage(Assets.loadImage(R.drawable.tilefloorbackground));
    }


    @Override
    protected void addStartEndLocPairs(List<Pair<vector, vector>> startEndLocPairs) {
        vector startLoc = new vector(2*dp, Rpg.gridSize*6);
        vector startLoc2 = new vector(2*dp, Rpg.gridSize*16);
        vector startLoc3 = new vector(2*dp, Rpg.gridSize*25);
        vector endLoc = new vector(getLevelWidthInPx()-2*dp, getLevelHeightInPx() - Rpg.gridSize*14);
        startEndLocPairs.add(new Pair<>(startLoc,endLoc));
        startEndLocPairs.add(new Pair<>(startLoc2, endLoc));
        startEndLocPairs.add(new Pair<>(startLoc3, endLoc));
    }



    /**
     * Must be called after the onCreate(UI ui, RectF onScreenArea)
     * or at least at the end of the method.
     */
    protected void createBorder() {
        super.createBorder();

//        int[] decoIDs = {R.drawable.statue_armor, R.drawable.statue_armor2,R.drawable.statue_manhood,R.drawable.statue_monk};
//        for( int i = 0 ; i < numHorz ; i += 3 )
//            addDecoGE(new Vectorr(pillarWidth / 2 + i * pillarWidth, pillarHeight / 2),decoIDs[(int)(Math.random()*decoIDs.length)]);


    }


    @Override
    protected Round getRound(AbstractRound.RoundParams rParams) {
        return CastleRounds.getRound(rParams);
    }


    @Override
    protected int getNumberOfRounds() {
        return CastleRounds.getNumberOfRounds();
    }

    public int highestLevelTowersAllowed(){
        return 5;
    }

    @Override
    protected int getStartingGold() {
        return 500;
    }


    private static List<Buildings> allowedBuildings = Collections.unmodifiableList(
            Arrays.asList(new Buildings[]{Buildings.WatchTower, Buildings.Barracks, Buildings.Wall,
                    Buildings.FireDragonTower, Buildings.IceDragonTower,Buildings.ShockDragonTower}));

    @Override
    public List<Buildings> getAllowedBuildings(){
        return allowedBuildings;
    }
}
