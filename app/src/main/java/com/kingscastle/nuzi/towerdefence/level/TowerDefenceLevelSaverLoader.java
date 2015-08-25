package com.kingscastle.nuzi.towerdefence.level;

import android.util.Log;

import com.kingscastle.nuzi.towerdefence.framework.WtfException;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.Humanoid;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.Unit;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.ArmyManager;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Building;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.BuildingManager;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.ListPkg;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameUtils.Difficulty;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.level.rounds.AbstractRound;
import com.kingscastle.nuzi.towerdefence.level.rounds.Round;
import com.kingscastle.nuzi.towerdefence.teams.Teams;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Chris on 7/10/2015.
 */
public class TowerDefenceLevelSaverLoader {

    private static final String TAG = "TDLevelSaverLoader";
    public static final String CLASSNAME = "CN";
    public static final String TEAM = "Team";
    public static final String LOC = "Loc";
    public static final String LVL = "Lvl";
    public static final String HEALTH = "Health";

    public static void loadGame(ObjectInputStream ois, TowerDefenceLevel lvl) throws IOException, ClassNotFoundException {
        Log.v(TAG, "Loading Level: " + lvl);

        int roundNum = (Integer) ois.readObject();
        lvl.goToRound(roundNum);
        Round round = lvl.getRound();
        round.setNumberSpawned((Integer) ois.readObject());
        lvl.getHumanPlayer().setLives((Integer) ois.readObject());
        lvl.getHumanPlayer().getPR().setGoldAvailable((Integer) ois.readObject());
        lvl.setDifficulty((Difficulty) ois.readObject());

        Log.v(TAG, "Starting at round " + roundNum );


        MM mm = lvl.getMM();
        {
            BuildingManager bm = mm.getTeam(Teams.BLUE).getBm();

            ArrayList<HashMap<String,Object>> buildingsInfo = (ArrayList<HashMap<String,Object>>) ois.readObject();

            Log.v(TAG,"Loading " + buildingsInfo.size() + " buildings");

            for( final HashMap<String,Object> attr : buildingsInfo ) {
                Building buildin = Building.getFromString((String) attr.get(CLASSNAME),(Teams)attr.get(TEAM),(vector)attr.get(LOC));
                //Log.v(TAG, "Loaded " + buildin);
                bm.add(buildin,new BuildingManager.OnBuildingAddedListener() {
                    @Override
                    public boolean onBuildingAdded(Building b) {
                        b.upgradeToLevel((int) attr.get(LVL));
                        return true;
                    }
                });
            }
        }

        {
            ArmyManager am = mm.getTeam(Teams.RED).getAm();

            ArrayList<HashMap<String,Object>> armyInfo = (ArrayList<HashMap<String,Object>>) ois.readObject();

            for (HashMap<String,Object> attr : armyInfo) {

                final Humanoid lt = Unit.getFromString((String) attr.get(CLASSNAME), (Teams) attr.get(TEAM), (vector) attr.get(LOC));

                Log.d(TAG,"Loading soldier with team:" +  attr.get(TEAM));
                int startEndLocPairIndex = (Integer) attr.get(AbstractRound.START_END_LOC_INDEX);
                //Log.v(TAG, "Loaded " + lt);
                lt.getExtras().put(AbstractRound.START_END_LOC_INDEX,startEndLocPairIndex);
                lt.lq.setHealth((Integer) attr.get(HEALTH));
                am.add(lt);
                lt.upgradeToLevel((int) attr.get(LVL));
            }
        }
        ois.close();
        Log.v(TAG,"Loading complete");
    }


    public static void saveLevel(FileOutputStream fos , TowerDefenceLevel lvl) throws IOException {

        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(lvl.getClass().getSimpleName());
        oos.writeObject(lvl.getRound().getRoundNum());
        oos.writeObject(lvl.getRound().getNumberSpawned());
        oos.writeObject(lvl.getHumanPlayer().getLives());
        oos.writeObject(lvl.getHumanPlayer().getPR().getGold());
        oos.writeObject(lvl.getDifficulty());

        Log.v(TAG, "Saving  " + lvl.getClass().getSimpleName());
        Log.v(TAG, "Saving  lvl.getCurrentRoundNum(): " + lvl.getRound().getRoundNum());
        Log.v(TAG, "Saving  lvl.getHumanPlayer().getLives(): " + lvl.getHumanPlayer().getLives());
        Log.v(TAG, "Saving  lvl.getHumanPlayer().getPR().getGold(): " + lvl.getHumanPlayer().getPR().getGold());

        Log.v(TAG, "Saving Level: " + lvl);
        MM mm = lvl.getMM();
        {
            BuildingManager bm = mm.getTeam(Teams.BLUE).getBm();
            ListPkg<Building> pkg = bm.getBuildings();

            Log.v(TAG, "Saving " + pkg.size + " buildings.");
            ArrayList<Map<String,Object>> buildingInfo = new ArrayList<>();

            for( int i = 0 ; i < pkg.size ; ++i ) {
                Building building = pkg.list[i];
                HashMap<String,Object> attr = new HashMap<>();

                attr.put(CLASSNAME,building.getClass().getSimpleName());
                attr.put(TEAM,building.getTeamName());
                attr.put(LOC, building.getLoc());
                attr.put(LVL, building.getLQ().getLevel());

//                for( String s : attr.keySet() )
//                    Log.v(TAG, "Saving " + attr.get(s));

                buildingInfo.add(attr);
            }
            oos.writeObject(buildingInfo);
        }

        {
            ArmyManager am = mm.getTeam(Teams.RED).getAm();
            List<Humanoid> army = am.getArmy();
            //Log.v(TAG, "Saving " +army.size() + " monsters.");
            ArrayList<Map<String,Object>> armyInfo = new ArrayList<>();

            for (int i = 0; i < army.size(); ++i) {
                LivingThing lt = army.get(i);
                if( lt.isDead() ){
                   // Log.i(TAG,"Not saving a dead " + lt );
                    continue;
                }
                HashMap<String,Object> attr = new HashMap<>();

                attr.put(CLASSNAME,lt.getClass().getSimpleName());
                attr.put(TEAM,lt.getTeamName());
                attr.put(LOC, lt.getLoc());
                attr.put(LVL, lt.getLQ().getLevel());
                attr.put(HEALTH, lt.getLQ().getHealth());
                attr.put(AbstractRound.START_END_LOC_INDEX, lt.getExtras().get(AbstractRound.START_END_LOC_INDEX));

                if( lt.getExtras().get(AbstractRound.START_END_LOC_INDEX) == null )
                    throw new WtfException("lt.getExtras().get(START_END_LOC_INDEX)==null");

//                for( String s : attr.keySet() )
//                    Log.v(TAG, "Saving " + attr.get(s));

                armyInfo.add(attr);
            }
            oos.writeObject(armyInfo);
        }
        oos.close();
    }



}
