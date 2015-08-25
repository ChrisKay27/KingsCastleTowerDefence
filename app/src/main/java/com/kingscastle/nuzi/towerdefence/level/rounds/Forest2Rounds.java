package com.kingscastle.nuzi.towerdefence.level.rounds;

import android.util.Log;

import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.Unit;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.BrownTemplar;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.Coyote;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.ExoticRings;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.FreakyMouse;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.Gaia;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.KratosArmored;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.KratosHeavyArmor;
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
public class Forest2Rounds {
    private static final String TAG = "Forest2Rounds";

    public static final Object[][] roundParams = {
            //gold,period,numToSpawn,health,monster

            {1,900,20},
            {2,800,25},
            {3,800,35},
            {4,800,40},
            {5,800,40},
            {6,800,45},
            {7,400,50},
            {8,700,60},
            {9,700,1,true},
            {10,400,55},
            {11,350,55},
            {12,300,60},
            {13,250,30},
            {14,200,65},
            {15,150,65},
            {16,300,1,true},
    };


    private static final List<Class<? extends Unit>> thingsToSpawn1 = new ArrayList<>();
    private static final List<Class<? extends Unit>> thingsToSpawn2 = new ArrayList<>();
    private static final List<Class<? extends Unit>> thingsToSpawn3 = new ArrayList<>();
    private static final List<Class<? extends Unit>> thingsToSpawn4 = new ArrayList<>();
    private static final List<Class<? extends Unit>> thingsToSpawn5 = new ArrayList<>();
    private static final List<Class<? extends Unit>> thingsToSpawn6 = new ArrayList<>();
    private static final List<Class<? extends Unit>> thingsToSpawn7 = new ArrayList<>();
    private static final List<Class<? extends Unit>> thingsToSpawn8 = new ArrayList<>();
    private static final List<Class<? extends Unit>> thingsToSpawn9 = new ArrayList<>();
    private static final List<Class<? extends Unit>> thingsToSpawn10 = new ArrayList<>();
    private static final List<Class<? extends Unit>> thingsToSpawn11 = new ArrayList<>();
    private static final List<Class<? extends Unit>> thingsToSpawn12 = new ArrayList<>();
    private static final List<Class<? extends Unit>> thingsToSpawn13 = new ArrayList<>();
    private static final List<Class<? extends Unit>> thingsToSpawn14 = new ArrayList<>();
    private static final List<Class<? extends Unit>> thingsToSpawn15 = new ArrayList<>();
    private static final List<Class<? extends Unit>> thingsToSpawn16 = new ArrayList<>();

    private static final List<List<Class<? extends Unit>>> thingsToSpawnOnRounds = new ArrayList<>();



    static{
        thingsToSpawn1.add(KratosLightArm.class);

        thingsToSpawn2.add(KratosLightArm.class);
        thingsToSpawn2.add(KratosLightArm.class);
        thingsToSpawn2.add(KratosLightArm.class);
        thingsToSpawn2.add(KratosLightArmSword.class);


        thingsToSpawn3.add(KratosLightArm.class);
        thingsToSpawn3.add(KratosLightArmSword.class);
        thingsToSpawn3.add(KratosShielded.class);

        thingsToSpawn4.add(KratosLightArmSword.class);
        thingsToSpawn4.add(KratosLightArm.class);
        thingsToSpawn4.add(KratosShielded.class);
        thingsToSpawn4.add(KratosMedArm.class);


        thingsToSpawn5.add(KratosLightArm.class);
        thingsToSpawn5.add(KratosLightArmSword.class);
        thingsToSpawn5.add(KratosShielded.class);
        thingsToSpawn5.add(KratosMedArm.class);


        thingsToSpawn6.add(KratosLightArm.class);
        thingsToSpawn6.add(KratosLightArmSword.class);
        thingsToSpawn6.add(KratosMedArm.class);
        thingsToSpawn6.add(KratosShielded.class);

        thingsToSpawn7.add(Spider.class);
        thingsToSpawn7.add(Spider.class);
        thingsToSpawn7.add(SpiderLarge.class);

        thingsToSpawn8.add(KratosMedArm.class);
        thingsToSpawn8.add(KratosArmored.class);
        thingsToSpawn8.add(KratosMedArm.class);
        thingsToSpawn8.add(KratosArmored.class);
        thingsToSpawn8.add(KratosMedArm.class);
        thingsToSpawn8.add(KratosArmored.class);
        thingsToSpawn8.add(KratosMage.class);

        thingsToSpawn9.add(Coyote.class);

        thingsToSpawn10.add(KratosMedArm.class);
        thingsToSpawn10.add(KratosArmored.class);
        thingsToSpawn10.add(KratosShielded.class);
        thingsToSpawn10.add(KratosMedArm.class);
        thingsToSpawn10.add(KratosArmored.class);
        thingsToSpawn10.add(KratosShielded.class);
        thingsToSpawn10.add(KratosHeavyArmor.class);
        thingsToSpawn10.add(KratosMedArm.class);
        thingsToSpawn10.add(KratosArmored.class);
        thingsToSpawn10.add(KratosShielded.class);
        thingsToSpawn10.add(KratosMage.class);


        thingsToSpawn11.add(KratosMedArm.class);
        thingsToSpawn11.add(KratosShielded.class);
        thingsToSpawn11.add(KratosArmored.class);
        thingsToSpawn11.add(KratosMedArm.class);
        thingsToSpawn11.add(KratosShielded.class);
        thingsToSpawn11.add(KratosArmored.class);
        thingsToSpawn11.add(KratosMage.class);
        thingsToSpawn11.add(KratosHeavyArmor.class);
        thingsToSpawn11.add(ExoticRings.class);
        thingsToSpawn11.add(BrownTemplar.class);

        thingsToSpawn12.add(KratosArmored.class);
        thingsToSpawn12.add(KratosArmored.class);
        thingsToSpawn12.add(KratosMage.class);
        thingsToSpawn12.add(KratosHeavyArmor.class);
        thingsToSpawn12.add(KratosArmored.class);
        thingsToSpawn12.add(KratosMage.class);
        thingsToSpawn12.add(KratosHeavyArmor.class);
        thingsToSpawn12.add(ExoticRings.class);
        thingsToSpawn12.add(FreakyMouse.class);

        thingsToSpawn13.add(FreakyMouse.class);

        thingsToSpawn14.add(KratosArmored.class);
        thingsToSpawn14.add(KratosHeavyArmor.class);
        thingsToSpawn14.add(KratosMage.class);
        thingsToSpawn14.add(BrownTemplar.class);
        thingsToSpawn14.add(ExoticRings.class);
        thingsToSpawn14.add(FreakyMouse.class);

        thingsToSpawn15.add(KratosArmored.class);
        thingsToSpawn15.add(KratosHeavyArmor.class);
        thingsToSpawn15.add(KratosMage.class);
        thingsToSpawn15.add(KratosArmored.class);
        thingsToSpawn15.add(KratosHeavyArmor.class);
        thingsToSpawn15.add(KratosMage.class);
        thingsToSpawn15.add(BrownTemplar.class);
        thingsToSpawn15.add(FreakyMouse.class);

        thingsToSpawn16.add(Gaia.class);

        thingsToSpawnOnRounds.add(thingsToSpawn1);
        thingsToSpawnOnRounds.add(thingsToSpawn2);
        thingsToSpawnOnRounds.add(thingsToSpawn3);
        thingsToSpawnOnRounds.add(thingsToSpawn4);
        thingsToSpawnOnRounds.add(thingsToSpawn5);
        thingsToSpawnOnRounds.add(thingsToSpawn6);
        thingsToSpawnOnRounds.add(thingsToSpawn7);
        thingsToSpawnOnRounds.add(thingsToSpawn8);
        thingsToSpawnOnRounds.add(thingsToSpawn9);
        thingsToSpawnOnRounds.add(thingsToSpawn10);
        thingsToSpawnOnRounds.add(thingsToSpawn11);
        thingsToSpawnOnRounds.add(thingsToSpawn12);
        thingsToSpawnOnRounds.add(thingsToSpawn13);
        thingsToSpawnOnRounds.add(thingsToSpawn14);
        thingsToSpawnOnRounds.add(thingsToSpawn15);
        thingsToSpawnOnRounds.add(thingsToSpawn16);
    }




    public static Round getRound(RoundParams rParams){
        try {
            int roundNum = rParams.roundNum;
            if( roundNum > roundParams.length ) {
                Log.e(TAG, "roundNum > roundParams.length!");
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
