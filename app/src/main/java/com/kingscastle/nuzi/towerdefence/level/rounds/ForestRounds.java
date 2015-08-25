package com.kingscastle.nuzi.towerdefence.level.rounds;

import android.util.Log;

import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.Unit;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.BrownTemplar;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.Coyote;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.Gaia;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.KratosArmored;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.KratosHeavyArmor;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.KratosLightArcher;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.KratosLightArm;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.KratosLightArmSword;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.KratosMage;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.KratosMedArm;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.KratosShielded;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.Spider;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.SpiderLarge;
import com.kingscastle.nuzi.towerdefence.level.rounds.AbstractRound.RoundParams;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 7/7/2015 for Tower Defence
 */
public class ForestRounds {
    private static final String TAG = "ForestRounds";

    public static final Object[][] roundParams = {
            //gold,period,numToSpawn,health,monster

            {1,1000,20},
            {2,1000,20},
            {3,1000,25},
            {4,1000,30},
            {5,600,50},
            {6,1000,40},
            {7, 900,45},
            {8, 800,1,true},
            {9, 500,50},
            {10,450,55},
            {11,400,55},
            {12,100,100},
            {13,300,60},
            {14,250,65},
            {15,200,65},
            {16,300,1,true},
    };


    private static final List<List<Class<? extends Unit>>> thingsToSpawnOnRounds = new ArrayList<>();



    static{
        
        {// Round 1
            List<Class<? extends Unit>> thingsToSpawn = new ArrayList<>();
            thingsToSpawn.add(KratosLightArm.class);
            thingsToSpawnOnRounds.add(thingsToSpawn);
        }

        {// Round 2
            List<Class<? extends Unit>> thingsToSpawn = new ArrayList<>();
            thingsToSpawn.add(KratosLightArm.class);
            thingsToSpawn.add(KratosLightArm.class);
            thingsToSpawn.add(KratosLightArmSword.class);
            thingsToSpawnOnRounds.add(thingsToSpawn);
        }
        {// Round 3
            List<Class<? extends Unit>> thingsToSpawn = new ArrayList<>();
            thingsToSpawn.add(KratosLightArm.class);
            thingsToSpawn.add(KratosLightArmSword.class);
            thingsToSpawn.add(KratosShielded.class);
            thingsToSpawnOnRounds.add(thingsToSpawn);
        }
        {// Round 4
            List<Class<? extends Unit>> thingsToSpawn = new ArrayList<>();

            thingsToSpawn.add(KratosLightArmSword.class);
            thingsToSpawn.add(KratosLightArmSword.class);
            thingsToSpawn.add(KratosLightArm.class);
            thingsToSpawn.add(KratosLightArm.class);
            thingsToSpawn.add(KratosMedArm.class);
            thingsToSpawnOnRounds.add(thingsToSpawn);
        }

        {// Round 5
            List<Class<? extends Unit>> thingsToSpawn = new ArrayList<>();
            thingsToSpawn.add(Spider.class);
            thingsToSpawn.add(Spider.class);
            thingsToSpawn.add(SpiderLarge.class);
            thingsToSpawnOnRounds.add(thingsToSpawn);}

        {// Round 6
            List<Class<? extends Unit>> thingsToSpawn = new ArrayList<>();
            thingsToSpawn.add(KratosLightArmSword.class);
            thingsToSpawn.add(KratosMedArm.class);
            thingsToSpawnOnRounds.add(thingsToSpawn);
        }

        {// Round 7
            List<Class<? extends Unit>> thingsToSpawn = new ArrayList<>();
            thingsToSpawn.add(KratosLightArmSword.class);
            thingsToSpawn.add(KratosLightArcher.class);
            thingsToSpawn.add(KratosMedArm.class);
            thingsToSpawn.add(KratosArmored.class);
            thingsToSpawnOnRounds.add(thingsToSpawn);
        }
        {// Round 8
            List<Class<? extends Unit>> thingsToSpawn = new ArrayList<>();
            thingsToSpawn.add(Coyote.class);
            thingsToSpawnOnRounds.add(thingsToSpawn);
        }
        {// Round 9
            List<Class<? extends Unit>> thingsToSpawn = new ArrayList<>();
            thingsToSpawn.add(KratosMedArm.class);
            thingsToSpawn.add(KratosArmored.class);
            thingsToSpawn.add(KratosLightArcher.class);
            thingsToSpawn.add(KratosLightArcher.class);
            thingsToSpawn.add(KratosMedArm.class);
            thingsToSpawn.add(KratosArmored.class);
            thingsToSpawn.add(KratosMage.class);
            thingsToSpawnOnRounds.add(thingsToSpawn);
        }
        {// Round 10
            List<Class<? extends Unit>> thingsToSpawn = new ArrayList<>();
            thingsToSpawn.add(KratosMedArm.class);
            thingsToSpawn.add(KratosArmored.class);
            thingsToSpawn.add(KratosMedArm.class);
            thingsToSpawn.add(KratosArmored.class);
            thingsToSpawn.add(KratosMage.class);
            thingsToSpawnOnRounds.add(thingsToSpawn);
        }
        {// Round 11
            List<Class<? extends Unit>> thingsToSpawn = new ArrayList<>();
            thingsToSpawn.add(KratosArmored.class);
            thingsToSpawn.add(KratosArmored.class);
            thingsToSpawn.add(KratosMedArm.class);
            thingsToSpawn.add(KratosHeavyArmor.class);
            thingsToSpawn.add(KratosMage.class);
            thingsToSpawnOnRounds.add(thingsToSpawn);
        }
        {// Round 12
            List<Class<? extends Unit>> thingsToSpawn = new ArrayList<>();
            thingsToSpawn.add(Spider.class);
            thingsToSpawn.add(SpiderLarge.class);
            thingsToSpawn.add(SpiderLarge.class);
            thingsToSpawnOnRounds.add(thingsToSpawn);
        }
        {// Round 13
            List<Class<? extends Unit>> thingsToSpawn = new ArrayList<>();
            thingsToSpawn.add(KratosArmored.class);
            thingsToSpawn.add(KratosMage.class);
            thingsToSpawn.add(KratosHeavyArmor.class);
            thingsToSpawn.add(KratosArmored.class);
            thingsToSpawn.add(KratosMage.class);
            thingsToSpawn.add(KratosHeavyArmor.class);
            thingsToSpawn.add(BrownTemplar.class);
            thingsToSpawnOnRounds.add(thingsToSpawn);
        }
        {// Round 14
            List<Class<? extends Unit>> thingsToSpawn = new ArrayList<>();
            thingsToSpawn.add(BrownTemplar.class);
            thingsToSpawn.add(KratosMedArm.class);
            thingsToSpawn.add(KratosArmored.class);
            thingsToSpawn.add(KratosHeavyArmor.class);
            thingsToSpawn.add(KratosMage.class);
            thingsToSpawnOnRounds.add(thingsToSpawn);
        }
        {// Round 15
            List<Class<? extends Unit>> thingsToSpawn = new ArrayList<>();
            thingsToSpawn.add(BrownTemplar.class);
            thingsToSpawn.add(KratosArmored.class);
            thingsToSpawn.add(KratosHeavyArmor.class);
            thingsToSpawn.add(KratosMage.class);
            thingsToSpawnOnRounds.add(thingsToSpawn);
        }
        {// Round 16
            List<Class<? extends Unit>> thingsToSpawn = new ArrayList<>();
            thingsToSpawn.add(Gaia.class);
            thingsToSpawnOnRounds.add(thingsToSpawn);
        }
//        
//        thingsToSpawnOnRounds.add(thingsToSpawn1);
//        thingsToSpawnOnRounds.add(thingsToSpawn2);
//        thingsToSpawnOnRounds.add(thingsToSpawn3);
//        thingsToSpawnOnRounds.add(thingsToSpawn4);
//        thingsToSpawnOnRounds.add(thingsToSpawn5);
//        thingsToSpawnOnRounds.add(thingsToSpawn6);
//        thingsToSpawnOnRounds.add(thingsToSpawn7);
//        thingsToSpawnOnRounds.add(thingsToSpawn8);
//        thingsToSpawnOnRounds.add(thingsToSpawn9);
//        thingsToSpawnOnRounds.add(thingsToSpawn10);
//        thingsToSpawnOnRounds.add(thingsToSpawn11);
//        thingsToSpawnOnRounds.add(thingsToSpawn12);
//        thingsToSpawnOnRounds.add(thingsToSpawn13);
//        thingsToSpawnOnRounds.add(thingsToSpawn14);
//        thingsToSpawnOnRounds.add(thingsToSpawn15);
//        thingsToSpawnOnRounds.add(thingsToSpawn16);
    }




    public static Round getRound(RoundParams rParams){
        try {
            int roundNum = rParams.roundNum;
            if( roundNum > roundParams.length ) {
                Log.e(TAG,"roundNum > roundParams.length!");
            }

            Object[] o = roundParams[roundNum-1];

            rParams.roundNum = roundNum;

            rParams.spawnPeriodMs = (int) o[1];
            rParams.numberToSpawn = (int) o[2];
            rParams.nightRound = o.length > 3 ? (Boolean) o[3] : false;
            rParams.thingsToSpawn = thingsToSpawnOnRounds.get(roundNum-1);

            Constructor<Round> constr = Round.class.getConstructor(RoundParams.class);

            Object[] params = {rParams};
            return constr.newInstance(params);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public static int getNumberOfRounds(){
        return thingsToSpawnOnRounds.size();
    }


}
