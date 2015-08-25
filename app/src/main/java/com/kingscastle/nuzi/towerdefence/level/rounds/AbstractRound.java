package com.kingscastle.nuzi.towerdefence.level.rounds;

import android.graphics.RectF;
import android.util.Log;
import android.util.Pair;

import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing.OnTargetSetListener;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.Humanoid;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.Unit;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Building;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.BuildingManager;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameElements.movement.pathing.Path;
import com.kingscastle.nuzi.towerdefence.gameElements.movement.pathing.PathFinder;
import com.kingscastle.nuzi.towerdefence.gameElements.movement.pathing.PathFinderParams;
import com.kingscastle.nuzi.towerdefence.gameElements.movement.pathing.PathFoundListener;
import com.kingscastle.nuzi.towerdefence.gameUtils.Difficulty;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.level.PR;
import com.kingscastle.nuzi.towerdefence.level.TowerDefenceLevel;
import com.kingscastle.nuzi.towerdefence.teams.RT;
import com.kingscastle.nuzi.towerdefence.teams.Teams;
import com.kingscastle.nuzi.towerdefence.util.ManagerListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Chris on 7/12/2015 for TowerDefence
 */
public abstract class AbstractRound implements BuildingManager.OnBuildingAddedListener, LivingThing.OnDeathListener,OnTargetSetListener, ManagerListener<Humanoid> {

    private static final String TAG = "AbstractRound";
    public static final String START_END_LOC_INDEX = "StartEndLocIndex";

    protected MM mm;
  //  protected final BuildingManager bm;
    private final int roundNum;
    protected PR pr;

    private RectF endArea = new RectF(-Rpg.sixTeenDp,-Rpg.sixTeenDp,Rpg.sixTeenDp,Rpg.sixTeenDp );

    protected final List<Humanoid> aliveMonsters = new LinkedList<>(),addedToMapMonsters = new LinkedList<>();;
    private List<Class<? extends Unit>> thingsToSpawn = new ArrayList<>();
    protected MonsterReachedGoalListener mrgl;

    private long nextSpawn;

    private boolean stop;

    private final RoundParams rp;
    private int numSpawned;
    private int numberToSpawn;
    private long spawnPeriod;


    public AbstractRound(RoundParams rp) {
        this.rp = rp;
        this.roundNum = rp.roundNum;
        this.mm = rp.mm;
        this.mrgl = rp.mrgl;
        this.pr = rp.playersPr;
      //  this.bm = rp.playersBm;
        this.numberToSpawn = rp.numberToSpawn;
        if( numberToSpawn != 1 )
            this.numberToSpawn = (int) (numberToSpawn * rp.difficulty.multiplier);
        this.spawnPeriod = (long) (rp.spawnPeriodMs/rp.difficulty.multiplier);
        this.thingsToSpawn.addAll(rp.thingsToSpawn);
       // bm.addBal(this);
    }

    public boolean act(){

        synchronized (aliveMonsters) {
            Iterator<Humanoid> it = aliveMonsters.iterator();
            while(it.hasNext())
                if(it.next().isDead())
                    it.remove();
        }

        synchronized (addedToMapMonsters) {
            Iterator<Humanoid> it = addedToMapMonsters.iterator();
            while(it.hasNext())
                if(it.next().isDead())
                    it.remove();
        }

        for( int i=0 ; i < rp.startEndLocPairs.size() ; ++i ) {
            Pair<vector,vector> selp = rp.startEndLocPairs.get(i);

            endArea.set(-Rpg.sixTeenDp, -Rpg.sixTeenDp, Rpg.sixTeenDp, Rpg.sixTeenDp);
            endArea.offset(selp.second.x,selp.second.y);
            List<LivingThing> thingsAtEnd = mm.getCD().checkMultiHit(Teams.BLUE, endArea);

            synchronized (aliveMonsters) {
                thingsAtEnd.retainAll(aliveMonsters);
            }

            for( LivingThing lt : thingsAtEnd )
                if( lt instanceof Unit && (Integer)(lt.getExtras().get(START_END_LOC_INDEX)) == i)
                    monsterReachedGoal((Unit)lt);
        }

        if( stop )
            return true;

        //If we have spawned enough monsters return if they are all dead
        if( numSpawned >= numberToSpawn )
            return aliveMonsters.isEmpty() && addedToMapMonsters.isEmpty();

        //If its not time to spawn a monster return
        if( nextSpawn > GameTime.getTime() )
            return false;


        nextSpawn = GameTime.getTime() + (int) (spawnPeriod/2 + (Math.random()*spawnPeriod));

        Unit u;
        try {
            u = getNextThingToSpawn().getConstructor(vector.class,Teams.class).newInstance(new vector(),Teams.RED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        int startEndLocPairIndex = (int) (Math.random()*rp.startEndLocPairs.size());
        u.getExtras().put(START_END_LOC_INDEX, startEndLocPairIndex);

        Pair<vector,vector> startEndLoc = rp.startEndLocPairs.get(startEndLocPairIndex);
        u.loc.set(startEndLoc.first);

        addToMap(u);
        numSpawned++;

        //Don't return true even if it should be over, it'll call this method again and the check is done at the start.
        return false;
    }



    protected Class<? extends Unit> getNextThingToSpawn(){
        return thingsToSpawn.get((int) (Math.random() * thingsToSpawn.size()));
    }


    public boolean addToMap(final Humanoid lt){
        synchronized (addedToMapMonsters) {
            addedToMapMonsters.add(lt);
        }
        return mm.add(lt);
    }


    public void findPathFor(final Humanoid lt, int startEndLocPairIndex){
        if( lt.loc.x < 0 || lt.loc.y < 0 )
            Log.i(TAG, "lt's loc is less than zero : " + lt);


        Pair<vector,vector> startEndLoc = rp.startEndLocPairs.get(startEndLocPairIndex);

        lt.getExtras().put("EndLoc", startEndLoc.second);

        PathFinderParams pfp = new PathFinderParams();
        pfp.grid = mm.getGridUtil().getGrid();
        pfp.fromHere = lt.getLoc();
        pfp.toHere = startEndLoc.second;
        pfp.mapWidthInPx = mm.getLevel().getLevelWidthInPx();
        pfp.mapHeightInPx = mm.getLevel().getLevelHeightInPx();
        pfp.pathFoundListener = new PathFoundListener() {
            @Override
            public void onPathFound(Path path) {
                lt.setPathToFollow(path);
            }

            @Override
            public void cannotPathToThatLocation(String reason) {
                Log.e(TAG, "Cannot path to destination! " + reason);
            }
        };

        PathFinder.findPath(pfp);
    }

    private void monsterReachedGoal(Unit lt){
        lt.getExtras().put(TowerDefenceLevel.DONT_ADD_SCORE,true);
        lt.removeDL(AbstractRound.this);
        lt.loc.set(-1000, -1000);
        lt.getLQ().setHealth(0);
        mrgl.onMonsterReachedGoal(lt);
        synchronized (aliveMonsters) {
            aliveMonsters.remove(lt);
        }
    }

    @Override
    public boolean onBuildingAdded(Building b) {
        synchronized( aliveMonsters ){
            for( Humanoid lt : aliveMonsters )
                findPathFor(lt, (Integer) lt.getExtras().get(START_END_LOC_INDEX));
        }
        return false;
    }


    @Override
    public void onDeath(LivingThing lt) {
        pr.add(RT.GOLD, ((Unit) lt).getGoldDropped());

        synchronized(aliveMonsters) {
            aliveMonsters.remove(lt);
        }
    }

    @Override
    public boolean onAdded(Humanoid h) {
       // Log.v(TAG,"onAdded("+h);
        synchronized (addedToMapMonsters) {
            addedToMapMonsters.remove(h);
        }
        synchronized (aliveMonsters) {
            aliveMonsters.add(h);
        }
        Object o = h.getExtras().get(START_END_LOC_INDEX);
        if( o == null ){
            h.getExtras().put(START_END_LOC_INDEX,0);
            o = 0;
        }
        int startEndLocPairIndex = (int) o;
        findPathFor(h, startEndLocPairIndex);

        h.addDL(this);
        h.addTSL(this);
        return false;
    }

    @Override
    public boolean onRemoved(Humanoid humanoid) {
        return false;
    }

    @Override
    public void onTargetSet(LivingThing forThis, LivingThing target){
        if( target == null && forThis instanceof Humanoid )
            findPathFor((Humanoid) forThis, (Integer) forThis.getExtras().get(START_END_LOC_INDEX));
    }


    public void stop() {
        stop = true;
    }


    public Round next() {
        rp.roundNum++;
        return ForestRounds.getRound(rp);
    }

    public int getRoundNum() {
        return roundNum;
    }


    /** Used when loading the saved game.     */
    public void setNumberSpawned(int numberSpawned) {
        this.numSpawned = numberSpawned;
    }
    public int getNumberSpawned() {
        return numSpawned;
    }


    public boolean isTimeToSpawnSomething() {
        return false;
    }

    public boolean isNightRound() {
        return rp.nightRound;
    }

    public interface MonsterReachedGoalListener{
        void onMonsterReachedGoal(@NotNull Unit u);
    }


    public static class RoundParams{
        public int roundNum = 1;
        public int goldPerMonster;
        public int spawnPeriodMs;
        public int numberToSpawn;
        public int healthOfMonster;
        public LivingThing monster;
        public MM mm;
        public List<Pair<vector,vector>> startEndLocPairs = new ArrayList<>();
        public AbstractRound.MonsterReachedGoalListener mrgl;
        public PR playersPr;
       // public BuildingManager playersBm;
        public List<Class<? extends Unit>> thingsToSpawn;
        public boolean nightRound;
        public Difficulty difficulty = Difficulty.Medium;
    }

}
