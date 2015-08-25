package com.kingscastle.nuzi.towerdefence.level.rounds;

import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.Unit;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.FullMetalJacket;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.PumpkinKing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.SkeletonKing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.UndeadDeathKnight;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.UndeadMarshall;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.UndeadPossessed;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.UndeadPossessedKnight;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.UndeadSkeletonArcher;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.UndeadSkeletonScout;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.UndeadSkeletonWarrior;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.UndeadSkullFucqued;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.VampLord;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.ZombieFast;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.ZombieMedium;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.ZombieStrong;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.ZombieWeak;
import com.kingscastle.nuzi.towerdefence.level.rounds.AbstractRound.RoundParams;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chris_000 on 7/7/2015.
 */
public class DesertRounds {
    public static final Object[][] roundParams = {
            //gold,period,numToSpawn,health,monster

            {1,600,20},
            {2,600,25},
            {3,600,25},
            {4,600,25},
            {5,600,35},
            {6,550,30},
            {7, 500,4},
            {8, 550,35},
            {9, 700,1,true},
            {10,600,40},
            {11,500,45},
            {12,150,35},
            {13,300,50},
            {14,200,60},
            {15,150,80},
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
        thingsToSpawn1.add(UndeadSkeletonWarrior.class);
        thingsToSpawn1.add(ZombieWeak.class);
        thingsToSpawn1.add(UndeadSkeletonScout.class);

        thingsToSpawn2.add(UndeadSkeletonWarrior.class);
        thingsToSpawn2.add(ZombieWeak.class);
        thingsToSpawn2.add(UndeadSkeletonScout.class);

        thingsToSpawn3.add(ZombieWeak.class);
        thingsToSpawn3.add(UndeadSkeletonWarrior.class);
        thingsToSpawn3.add(UndeadSkeletonScout.class);
        thingsToSpawn3.add(ZombieWeak.class);
        thingsToSpawn3.add(UndeadSkeletonWarrior.class);
        thingsToSpawn3.add(UndeadSkeletonScout.class);
        thingsToSpawn3.add(UndeadMarshall.class);

        thingsToSpawn4.add(ZombieWeak.class);
        thingsToSpawn4.add(UndeadSkeletonScout.class);
        thingsToSpawn4.add(UndeadSkeletonWarrior.class);
        thingsToSpawn4.add(UndeadSkeletonWarrior.class);
        thingsToSpawn4.add(UndeadMarshall.class);

        thingsToSpawn5.add(ZombieWeak.class);
        thingsToSpawn5.add(UndeadSkeletonScout.class);
        thingsToSpawn5.add(UndeadSkeletonArcher.class);
        thingsToSpawn5.add(UndeadMarshall.class);

        thingsToSpawn6.add(ZombieMedium.class);
        thingsToSpawn6.add(UndeadSkeletonWarrior.class);
        thingsToSpawn6.add(UndeadSkeletonScout.class);
        thingsToSpawn6.add(UndeadSkeletonArcher.class);
        thingsToSpawn6.add(UndeadMarshall.class);

        thingsToSpawn7.add(UndeadMarshall.class);
        thingsToSpawn7.add(VampLord.class);
        thingsToSpawn7.add(ZombieMedium.class);
        thingsToSpawn7.add(UndeadMarshall.class);

        thingsToSpawn8.add(ZombieMedium.class);
        thingsToSpawn8.add(UndeadSkeletonWarrior.class);
        thingsToSpawn8.add(UndeadSkeletonScout.class);
        thingsToSpawn8.add(UndeadSkeletonArcher.class);
        thingsToSpawn8.add(UndeadSkullFucqued.class);
        thingsToSpawn8.add(UndeadMarshall.class);
        thingsToSpawn8.add(VampLord.class);


        thingsToSpawn9.add(PumpkinKing.class);


        thingsToSpawn10.add(UndeadSkeletonWarrior.class);
        thingsToSpawn10.add(UndeadSkeletonScout.class);
        thingsToSpawn10.add(ZombieStrong.class);
        thingsToSpawn10.add(UndeadSkeletonArcher.class);
        thingsToSpawn10.add(UndeadSkullFucqued.class);
        thingsToSpawn10.add(UndeadMarshall.class);
        thingsToSpawn10.add(UndeadMarshall.class);
        thingsToSpawn10.add(VampLord.class);

        thingsToSpawn11.add(UndeadSkeletonWarrior.class);
        thingsToSpawn11.add(UndeadSkeletonWarrior.class);
        thingsToSpawn11.add(ZombieStrong.class);
        thingsToSpawn11.add(UndeadSkeletonScout.class);
        thingsToSpawn11.add(UndeadSkeletonScout.class);
        thingsToSpawn11.add(UndeadSkeletonArcher.class);
        thingsToSpawn11.add(UndeadSkeletonArcher.class);
        thingsToSpawn11.add(UndeadSkullFucqued.class);
        thingsToSpawn11.add(UndeadMarshall.class);
        thingsToSpawn11.add(VampLord.class);


        thingsToSpawn12.add(ZombieFast.class);




        thingsToSpawn13.add(VampLord.class);
        thingsToSpawn13.add(ZombieStrong.class);
        thingsToSpawn13.add(UndeadSkeletonArcher.class);
        thingsToSpawn13.add(UndeadSkullFucqued.class);
        thingsToSpawn13.add(UndeadPossessedKnight.class);
        thingsToSpawn13.add(UndeadPossessed.class);


        thingsToSpawn14.add(ZombieFast.class);
        thingsToSpawn14.add(VampLord.class);
        thingsToSpawn14.add(UndeadSkullFucqued.class);
        thingsToSpawn14.add(UndeadPossessedKnight.class);
        thingsToSpawn14.add(UndeadPossessed.class);
        thingsToSpawn14.add(FullMetalJacket.class);
        thingsToSpawn14.add(UndeadDeathKnight.class);


        thingsToSpawn15.add(ZombieFast.class);
        thingsToSpawn15.add(VampLord.class);
        thingsToSpawn15.add(UndeadMarshall.class);
        thingsToSpawn15.add(UndeadSkullFucqued.class);
        thingsToSpawn15.add(UndeadPossessedKnight.class);
        thingsToSpawn15.add(UndeadPossessed.class);
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
            if( roundNum > roundParams.length )
                roundNum = (int) (Math.random()*(roundParams.length+1));

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
