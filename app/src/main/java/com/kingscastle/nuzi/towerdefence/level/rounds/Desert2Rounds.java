package com.kingscastle.nuzi.towerdefence.level.rounds;

import android.util.Log;

import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.Unit;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.FullMetalJacket;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.PumpkinKing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.SkeletonKing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.UndeadDeathKnight;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.UndeadMarshall;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.UndeadPossessed;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.UndeadPossessedKnight;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.UndeadSkeletonArcher;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.UndeadSkullFucqued;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.VampLord;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.ZombieFast;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.ZombieStrong;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.ZombieWeak;
import com.kingscastle.nuzi.towerdefence.level.rounds.AbstractRound.RoundParams;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 7/7/2015 for Tower Defence
 */
public class Desert2Rounds {
    private static final String TAG = Desert2Rounds.class.getSimpleName();

    public static final Object[][] roundParams = {
            //gold,period,numToSpawn,health,monster

            {1,700,20},
            {2,675,20},
            {3,650,25},
            {4,625,30},
            {5,650,30},
            {6,600,40},
            {7, 575,45},
            {8, 575,1,true},
            {9, 550,50},
            {10,500,55},
            {11,400,55},
            {12,100,100},
            {13,350,60},
            {14,300,65},
            {15,250,65},
            {16,300,1,true},
    };


    private static final List<List<Class<? extends Unit>>> thingsToSpawnOnRounds = new ArrayList<>();



    static{

        {// Round 1
            List<Class<? extends Unit>> thingsToSpawn = new ArrayList<>();
            thingsToSpawn.add(ZombieWeak.class);
            thingsToSpawnOnRounds.add(thingsToSpawn);
        }

        {// Round 2
            List<Class<? extends Unit>> thingsToSpawn = new ArrayList<>();
            thingsToSpawn.add(UndeadMarshall.class);
            thingsToSpawn.add(ZombieWeak.class);
            thingsToSpawn.add(UndeadSkeletonArcher.class);
            thingsToSpawn.add(UndeadMarshall.class);
            thingsToSpawn.add(ZombieWeak.class);
            thingsToSpawn.add(UndeadSkeletonArcher.class);
            thingsToSpawn.add(UndeadMarshall.class);
            thingsToSpawn.add(ZombieWeak.class);
            thingsToSpawn.add(UndeadSkeletonArcher.class);
            thingsToSpawn.add(VampLord.class);
            thingsToSpawnOnRounds.add(thingsToSpawn);
        }
        {// Round 3
            List<Class<? extends Unit>> thingsToSpawn = new ArrayList<>();
            thingsToSpawn.add(UndeadMarshall.class);
            thingsToSpawn.add(ZombieWeak.class);
            thingsToSpawn.add(UndeadSkeletonArcher.class);
            thingsToSpawn.add(UndeadMarshall.class);
            thingsToSpawn.add(ZombieWeak.class);
            thingsToSpawn.add(UndeadSkeletonArcher.class);
            thingsToSpawn.add(UndeadMarshall.class);
            thingsToSpawn.add(ZombieWeak.class);
            thingsToSpawn.add(UndeadSkeletonArcher.class);
            thingsToSpawn.add(VampLord.class);
            thingsToSpawnOnRounds.add(thingsToSpawn);
        }
        {// Round 4
            List<Class<? extends Unit>> thingsToSpawn = new ArrayList<>();
            thingsToSpawn.add(UndeadMarshall.class);
            thingsToSpawn.add(ZombieWeak.class);
            thingsToSpawn.add(UndeadSkeletonArcher.class);
            thingsToSpawn.add(UndeadMarshall.class);
            thingsToSpawn.add(ZombieWeak.class);
            thingsToSpawn.add(UndeadSkeletonArcher.class);
            thingsToSpawn.add(UndeadMarshall.class);
            thingsToSpawn.add(ZombieWeak.class);
            thingsToSpawn.add(UndeadSkeletonArcher.class);
            thingsToSpawn.add(VampLord.class);
            thingsToSpawnOnRounds.add(thingsToSpawn);
        }

        {// Round 5
            List<Class<? extends Unit>> thingsToSpawn = new ArrayList<>();
            thingsToSpawn.add(ZombieFast.class);
            thingsToSpawnOnRounds.add(thingsToSpawn);}

        {// Round 6
            List<Class<? extends Unit>> thingsToSpawn = new ArrayList<>();
            thingsToSpawn.add(UndeadMarshall.class);
            thingsToSpawn.add(ZombieWeak.class);
            thingsToSpawn.add(UndeadSkeletonArcher.class);
            thingsToSpawn.add(UndeadMarshall.class);
            thingsToSpawn.add(ZombieWeak.class);
            thingsToSpawn.add(UndeadSkeletonArcher.class);
            thingsToSpawn.add(VampLord.class);
            thingsToSpawn.add(UndeadPossessed.class);
            thingsToSpawnOnRounds.add(thingsToSpawn);
        }

        {// Round 7
            List<Class<? extends Unit>> thingsToSpawn = new ArrayList<>();
            thingsToSpawn.add(UndeadMarshall.class);
            thingsToSpawn.add(ZombieWeak.class);
            thingsToSpawn.add(UndeadSkeletonArcher.class);
            thingsToSpawn.add(UndeadMarshall.class);
            thingsToSpawn.add(ZombieWeak.class);
            thingsToSpawn.add(UndeadSkeletonArcher.class);
            thingsToSpawnOnRounds.add(thingsToSpawn);
        }
        {// Round 8
            List<Class<? extends Unit>> thingsToSpawn = new ArrayList<>();
            thingsToSpawn.add(PumpkinKing.class);
            thingsToSpawnOnRounds.add(thingsToSpawn);
        }
        {// Round 9
            List<Class<? extends Unit>> thingsToSpawn = new ArrayList<>();
            thingsToSpawn.add(UndeadSkullFucqued.class);
            thingsToSpawn.add(VampLord.class);
            thingsToSpawn.add(UndeadMarshall.class);
            thingsToSpawnOnRounds.add(thingsToSpawn);
        }
        {// Round 10
            List<Class<? extends Unit>> thingsToSpawn = new ArrayList<>();
            thingsToSpawn.add(UndeadPossessed.class);
            thingsToSpawn.add(UndeadSkullFucqued.class);
            thingsToSpawn.add(ZombieStrong.class);
            thingsToSpawnOnRounds.add(thingsToSpawn);
        }
        {// Round 11
            List<Class<? extends Unit>> thingsToSpawn = new ArrayList<>();
            thingsToSpawn.add(ZombieStrong.class);
            thingsToSpawn.add(UndeadSkullFucqued.class);
            thingsToSpawn.add(UndeadMarshall.class);
            thingsToSpawn.add(VampLord.class);
            thingsToSpawn.add(UndeadPossessed.class);
            thingsToSpawnOnRounds.add(thingsToSpawn);
        }
        {// Round 12
            List<Class<? extends Unit>> thingsToSpawn = new ArrayList<>();
            thingsToSpawn.add(ZombieFast.class);
            thingsToSpawnOnRounds.add(thingsToSpawn);
        }
        {// Round 13
            List<Class<? extends Unit>> thingsToSpawn = new ArrayList<>();
            thingsToSpawn.add(UndeadPossessedKnight.class);
            thingsToSpawn.add(UndeadPossessed.class);
            thingsToSpawn.add(UndeadMarshall.class);
            thingsToSpawn.add(UndeadSkullFucqued.class);
            thingsToSpawn.add(ZombieStrong.class);
            thingsToSpawn.add(ZombieFast.class);
            thingsToSpawnOnRounds.add(thingsToSpawn);
        }
        {// Round 14
            List<Class<? extends Unit>> thingsToSpawn = new ArrayList<>();
            thingsToSpawn.add(FullMetalJacket.class);
            thingsToSpawn.add(UndeadDeathKnight.class);
            thingsToSpawn.add(UndeadPossessedKnight.class);
            thingsToSpawn.add(UndeadPossessed.class);
            thingsToSpawn.add(ZombieFast.class);
            thingsToSpawn.add(UndeadSkullFucqued.class);
            thingsToSpawn.add(ZombieStrong.class);
            thingsToSpawn.add(ZombieFast.class);
            thingsToSpawnOnRounds.add(thingsToSpawn);
        }
        {// Round 15
            List<Class<? extends Unit>> thingsToSpawn = new ArrayList<>();
            thingsToSpawn.add(FullMetalJacket.class);
            thingsToSpawn.add(UndeadDeathKnight.class);
            thingsToSpawn.add(UndeadPossessedKnight.class);
            thingsToSpawn.add(UndeadPossessed.class);
            thingsToSpawn.add(ZombieFast.class);
            thingsToSpawn.add(UndeadSkullFucqued.class);
            thingsToSpawn.add(ZombieStrong.class);
            thingsToSpawn.add(ZombieFast.class);
            thingsToSpawnOnRounds.add(thingsToSpawn);
        }
        {// Round 16
            List<Class<? extends Unit>> thingsToSpawn = new ArrayList<>();
            thingsToSpawn.add(SkeletonKing.class);
            thingsToSpawnOnRounds.add(thingsToSpawn);
        }
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
