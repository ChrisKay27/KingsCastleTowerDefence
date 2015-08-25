package com.kingscastle.nuzi.towerdefence.level.rounds;

import android.util.Log;

import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.Unit;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.FullMetalJacket;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.SkeletonKing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.UndeadDeathKnight;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.UndeadMarshall;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.UndeadPossessed;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.UndeadPossessedKnight;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.UndeadSkeletonArcher;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.UndeadSkeletonBowman;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.UndeadSkeletonScout;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.UndeadSkeletonWarrior;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.UndeadSkullFucqued;
import com.kingscastle.nuzi.towerdefence.level.rounds.AbstractRound.RoundParams;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 7/7/2015 for Tower Defence
 */
public class ForestRoundsOld {
    private static final String TAG = "ForestRoundsOld";

    public static final Object[][] roundParams = {
            //gold,period,numToSpawn,health,monster

            {1,1000,20},
            {2,1000,20},
            {3,1000,35},
            {4,1000,40},
            {5,1000,40},
            {6,1000,45},
            {7, 900,45},
            {8, 800,50},
            {9, 700,50},
            {10,600,55},
            {11,500,55},
            {12,400,60},
            {13,400,60},
            {14,300,65},
            {15,300,65},
            {16,300,1},
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
        thingsToSpawn1.add(UndeadSkeletonWarrior.class);

        thingsToSpawn2.add(UndeadSkeletonWarrior.class);
        thingsToSpawn2.add(UndeadSkeletonWarrior.class);

        thingsToSpawn3.add(UndeadSkeletonWarrior.class);
        thingsToSpawn3.add(UndeadSkeletonWarrior.class);
        thingsToSpawn3.add(UndeadSkeletonScout.class);


        thingsToSpawn4.add(UndeadSkeletonScout.class);
        thingsToSpawn4.add(UndeadSkeletonScout.class);
        thingsToSpawn4.add(UndeadSkeletonWarrior.class);
        thingsToSpawn4.add(UndeadSkeletonWarrior.class);
        thingsToSpawn4.add(UndeadSkeletonWarrior.class);
        thingsToSpawn4.add(UndeadSkeletonWarrior.class);
        thingsToSpawn4.add(UndeadSkeletonWarrior.class);
        thingsToSpawn4.add(UndeadMarshall.class);


        thingsToSpawn5.add(UndeadSkeletonWarrior.class);
        thingsToSpawn5.add(UndeadSkeletonWarrior.class);
        thingsToSpawn5.add(UndeadSkeletonWarrior.class);
        thingsToSpawn5.add(UndeadSkeletonWarrior.class);
        thingsToSpawn5.add(UndeadSkeletonWarrior.class);
        thingsToSpawn5.add(UndeadSkeletonWarrior.class);
        thingsToSpawn5.add(UndeadSkeletonScout.class);
        thingsToSpawn5.add(UndeadSkeletonScout.class);
        thingsToSpawn5.add(UndeadSkeletonScout.class);
        thingsToSpawn5.add(UndeadSkeletonScout.class);
        thingsToSpawn5.add(UndeadMarshall.class);


        thingsToSpawn6.add(UndeadSkeletonWarrior.class);
        thingsToSpawn6.add(UndeadSkeletonWarrior.class);
        thingsToSpawn6.add(UndeadSkeletonWarrior.class);
        thingsToSpawn6.add(UndeadSkeletonWarrior.class);
        thingsToSpawn6.add(UndeadSkeletonWarrior.class);
        thingsToSpawn6.add(UndeadSkeletonWarrior.class);
        thingsToSpawn6.add(UndeadSkeletonScout.class);
        thingsToSpawn6.add(UndeadSkeletonScout.class);
        thingsToSpawn6.add(UndeadSkeletonScout.class);
        thingsToSpawn6.add(UndeadSkeletonScout.class);


        thingsToSpawn7.add(UndeadSkeletonWarrior.class);
        thingsToSpawn7.add(UndeadSkeletonWarrior.class);
        thingsToSpawn7.add(UndeadSkeletonWarrior.class);
        thingsToSpawn7.add(UndeadSkeletonWarrior.class);
        thingsToSpawn7.add(UndeadSkeletonWarrior.class);
        thingsToSpawn7.add(UndeadSkeletonScout.class);
        thingsToSpawn7.add(UndeadSkeletonScout.class);
        thingsToSpawn7.add(UndeadSkeletonScout.class);
        thingsToSpawn7.add(UndeadSkeletonScout.class);
        thingsToSpawn7.add(UndeadSkeletonArcher.class);
        thingsToSpawn7.add(UndeadMarshall.class);

        thingsToSpawn8.add(UndeadSkeletonWarrior.class);
        thingsToSpawn8.add(UndeadSkeletonWarrior.class);
        thingsToSpawn8.add(UndeadSkeletonWarrior.class);
        thingsToSpawn8.add(UndeadSkeletonWarrior.class);
        thingsToSpawn8.add(UndeadSkeletonWarrior.class);
        thingsToSpawn8.add(UndeadSkeletonWarrior.class);
        thingsToSpawn8.add(UndeadSkeletonScout.class);
        thingsToSpawn8.add(UndeadSkeletonScout.class);
        thingsToSpawn8.add(UndeadSkeletonScout.class);
        thingsToSpawn8.add(UndeadSkeletonScout.class);
        thingsToSpawn8.add(UndeadSkeletonScout.class);
        thingsToSpawn8.add(UndeadSkeletonArcher.class);
        thingsToSpawn8.add(UndeadMarshall.class);
        thingsToSpawn8.add(UndeadMarshall.class);



        thingsToSpawn9.add(UndeadSkeletonWarrior.class);
        thingsToSpawn9.add(UndeadSkeletonWarrior.class);
        thingsToSpawn9.add(UndeadSkeletonWarrior.class);
        thingsToSpawn9.add(UndeadSkeletonWarrior.class);
        thingsToSpawn9.add(UndeadSkeletonScout.class);
        thingsToSpawn9.add(UndeadSkeletonScout.class);
        thingsToSpawn9.add(UndeadSkeletonScout.class);
        thingsToSpawn9.add(UndeadSkeletonArcher.class);
        thingsToSpawn9.add(UndeadSkeletonArcher.class);
        thingsToSpawn9.add(UndeadSkullFucqued.class);
        thingsToSpawn9.add(UndeadMarshall.class);
        thingsToSpawn9.add(UndeadMarshall.class);

        thingsToSpawn10.add(UndeadSkeletonWarrior.class);
        thingsToSpawn10.add(UndeadSkeletonWarrior.class);
        thingsToSpawn10.add(UndeadSkeletonWarrior.class);
        thingsToSpawn10.add(UndeadSkeletonWarrior.class);
        thingsToSpawn10.add(UndeadSkeletonWarrior.class);
        thingsToSpawn10.add(UndeadSkeletonScout.class);
        thingsToSpawn10.add(UndeadSkeletonScout.class);
        thingsToSpawn10.add(UndeadSkeletonScout.class);
        thingsToSpawn10.add(UndeadSkeletonArcher.class);
        thingsToSpawn10.add(UndeadSkeletonArcher.class);
        thingsToSpawn10.add(UndeadSkullFucqued.class);
        thingsToSpawn10.add(UndeadMarshall.class);
        thingsToSpawn10.add(UndeadMarshall.class);

        thingsToSpawn11.add(UndeadSkeletonWarrior.class);
        thingsToSpawn11.add(UndeadSkeletonWarrior.class);
        thingsToSpawn11.add(UndeadSkeletonWarrior.class);
        thingsToSpawn11.add(UndeadSkeletonWarrior.class);
        thingsToSpawn11.add(UndeadSkeletonWarrior.class);
        thingsToSpawn11.add(UndeadSkeletonScout.class);
        thingsToSpawn11.add(UndeadSkeletonScout.class);
        thingsToSpawn11.add(UndeadSkeletonScout.class);
        thingsToSpawn11.add(UndeadSkeletonArcher.class);
        thingsToSpawn11.add(UndeadSkeletonArcher.class);
        thingsToSpawn11.add(UndeadSkullFucqued.class);
        thingsToSpawn11.add(UndeadMarshall.class);
        thingsToSpawn11.add(UndeadMarshall.class);


        thingsToSpawn12.add(UndeadSkeletonWarrior.class);
        thingsToSpawn12.add(UndeadSkeletonWarrior.class);
        thingsToSpawn12.add(UndeadSkeletonWarrior.class);
        thingsToSpawn12.add(UndeadSkeletonWarrior.class);
        thingsToSpawn12.add(UndeadSkeletonWarrior.class);
        thingsToSpawn12.add(UndeadSkeletonScout.class);
        thingsToSpawn12.add(UndeadSkeletonScout.class);
        thingsToSpawn12.add(UndeadSkeletonScout.class);
        thingsToSpawn12.add(UndeadSkeletonArcher.class);
        thingsToSpawn12.add(UndeadSkeletonArcher.class);
        thingsToSpawn12.add(UndeadSkullFucqued.class);
        thingsToSpawn12.add(UndeadMarshall.class);
        thingsToSpawn12.add(UndeadMarshall.class);
        thingsToSpawn12.add(UndeadPossessedKnight.class);
        thingsToSpawn12.add(UndeadPossessed.class);



        thingsToSpawn13.add(UndeadSkeletonWarrior.class);
        thingsToSpawn13.add(UndeadSkeletonWarrior.class);
        thingsToSpawn13.add(UndeadSkeletonWarrior.class);
        thingsToSpawn13.add(UndeadSkeletonWarrior.class);
        thingsToSpawn13.add(UndeadSkeletonWarrior.class);
        thingsToSpawn13.add(UndeadSkeletonWarrior.class);
        thingsToSpawn13.add(UndeadSkeletonScout.class);
        thingsToSpawn13.add(UndeadSkeletonScout.class);
        thingsToSpawn13.add(UndeadSkeletonScout.class);
        thingsToSpawn13.add(UndeadSkeletonScout.class);
        thingsToSpawn13.add(UndeadSkeletonArcher.class);
        thingsToSpawn13.add(UndeadSkeletonArcher.class);
        thingsToSpawn13.add(UndeadSkullFucqued.class);
        thingsToSpawn13.add(UndeadMarshall.class);
        thingsToSpawn13.add(UndeadPossessedKnight.class);
        thingsToSpawn13.add(UndeadPossessed.class);



        thingsToSpawn14.add(UndeadSkeletonWarrior.class);
        thingsToSpawn14.add(UndeadSkeletonWarrior.class);
        thingsToSpawn14.add(UndeadSkeletonWarrior.class);
        thingsToSpawn14.add(UndeadSkeletonWarrior.class);
        thingsToSpawn14.add(UndeadSkeletonWarrior.class);
        thingsToSpawn14.add(UndeadSkeletonWarrior.class);
        thingsToSpawn14.add(UndeadSkeletonScout.class);
        thingsToSpawn14.add(UndeadSkeletonScout.class);
        thingsToSpawn14.add(UndeadSkeletonScout.class);
        thingsToSpawn14.add(UndeadSkeletonScout.class);
        thingsToSpawn14.add(UndeadSkeletonScout.class);
        thingsToSpawn14.add(UndeadSkeletonArcher.class);
        thingsToSpawn14.add(UndeadSkeletonArcher.class);
        thingsToSpawn14.add(UndeadMarshall.class);
        thingsToSpawn14.add(UndeadMarshall.class);
        thingsToSpawn14.add(UndeadSkullFucqued.class);
        thingsToSpawn14.add(UndeadSkullFucqued.class);
        thingsToSpawn14.add(UndeadPossessedKnight.class);
        thingsToSpawn14.add(UndeadPossessed.class);
        thingsToSpawn14.add(FullMetalJacket.class);
        thingsToSpawn14.add(UndeadDeathKnight.class);



        thingsToSpawn15.add(UndeadSkeletonWarrior.class);
        thingsToSpawn15.add(UndeadSkeletonWarrior.class);
        thingsToSpawn15.add(UndeadSkeletonWarrior.class);
        thingsToSpawn15.add(UndeadSkeletonWarrior.class);
        thingsToSpawn15.add(UndeadSkeletonWarrior.class);
        thingsToSpawn15.add(UndeadSkeletonScout.class);
        thingsToSpawn15.add(UndeadSkeletonScout.class);
        thingsToSpawn15.add(UndeadSkeletonScout.class);
        thingsToSpawn15.add(UndeadSkeletonBowman.class);
        thingsToSpawn15.add(UndeadSkeletonArcher.class);
        thingsToSpawn15.add(UndeadMarshall.class);
        thingsToSpawn15.add(UndeadSkullFucqued.class);
        thingsToSpawn15.add(UndeadPossessedKnight.class);
        thingsToSpawn15.add(UndeadPossessed.class);
        thingsToSpawn15.add(FullMetalJacket.class);
        thingsToSpawn15.add(UndeadDeathKnight.class);

        thingsToSpawn16.add(SkeletonKing.class);

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
                Log.e(TAG,"roundNum > roundParams.length!");
            }

            Object[] o = roundParams[roundNum-1];

            rParams.roundNum = roundNum;

            rParams.spawnPeriodMs = (int) o[1];
            rParams.numberToSpawn = (int) o[2];
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
