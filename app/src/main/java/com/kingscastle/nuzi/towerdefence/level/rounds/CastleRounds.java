package com.kingscastle.nuzi.towerdefence.level.rounds;

import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.Unit;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.UndeadGoldenArcher;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.UndeadHealer;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.UndeadMarshall;
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
public class CastleRounds {

    public static final Object[][] roundParams = {
            //gold,period,health,monster
            {1,2000,15},
            {2,1500,15},
            {3,1000,15},
            {3,1000,15},
            {4,1000,15},
            {5,1000,15},
            {6,1000,15},
            {7,1000,15},
            {8,1000,15},
    };


    private static final List<Class<? extends Unit>> thingsToSpawn1 = new ArrayList<>();
    private static final List<Class<? extends Unit>> thingsToSpawn2 = new ArrayList<>();
    private static final List<Class<? extends Unit>> thingsToSpawn3 = new ArrayList<>();
    private static final List<Class<? extends Unit>> thingsToSpawn4 = new ArrayList<>();
    private static final List<Class<? extends Unit>> thingsToSpawn5 = new ArrayList<>();
    private static final List<Class<? extends Unit>> thingsToSpawn6 = new ArrayList<>();
    private static final List<Class<? extends Unit>> thingsToSpawn7 = new ArrayList<>();
    private static final List<Class<? extends Unit>> thingsToSpawn8 = new ArrayList<>();

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
        thingsToSpawn4.add(UndeadMarshall.class);


        thingsToSpawn5.add(UndeadSkeletonWarrior.class);
        thingsToSpawn5.add(UndeadSkeletonWarrior.class);
        thingsToSpawn5.add(UndeadSkeletonWarrior.class);
        thingsToSpawn5.add(UndeadSkeletonScout.class);
        thingsToSpawn5.add(UndeadMarshall.class);
        thingsToSpawn5.add(UndeadMarshall.class);

        
        thingsToSpawn6.add(UndeadSkeletonWarrior.class);
        thingsToSpawn6.add(UndeadSkeletonWarrior.class);
        thingsToSpawn6.add(UndeadSkeletonScout.class);
        thingsToSpawn6.add(UndeadMarshall.class);
        thingsToSpawn6.add(UndeadMarshall.class);
        thingsToSpawn6.add(UndeadHealer.class);

        
        thingsToSpawn7.add(UndeadSkeletonWarrior.class);
        thingsToSpawn7.add(UndeadSkeletonWarrior.class);
        thingsToSpawn7.add(UndeadSkeletonArcher.class);
        thingsToSpawn7.add(UndeadSkullFucqued.class);
        thingsToSpawn7.add(UndeadMarshall.class);
        thingsToSpawn7.add(UndeadHealer.class);


        thingsToSpawn8.add(UndeadSkeletonWarrior.class);
        thingsToSpawn8.add(UndeadSkeletonBowman.class);
        thingsToSpawn8.add(UndeadSkeletonScout.class);
        thingsToSpawn8.add(UndeadMarshall.class);
        thingsToSpawn8.add(UndeadMarshall.class);
        thingsToSpawn8.add(UndeadHealer.class);
        thingsToSpawn8.add(UndeadGoldenArcher.class);



        thingsToSpawnOnRounds.add(thingsToSpawn1);
        thingsToSpawnOnRounds.add(thingsToSpawn2);
        thingsToSpawnOnRounds.add(thingsToSpawn3);
        thingsToSpawnOnRounds.add(thingsToSpawn4);
        thingsToSpawnOnRounds.add(thingsToSpawn5);
        thingsToSpawnOnRounds.add(thingsToSpawn6);
        thingsToSpawnOnRounds.add(thingsToSpawn7);
        thingsToSpawnOnRounds.add(thingsToSpawn8);
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
