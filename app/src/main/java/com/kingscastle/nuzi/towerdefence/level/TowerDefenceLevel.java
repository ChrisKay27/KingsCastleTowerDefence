package com.kingscastle.nuzi.towerdefence.level;

import android.graphics.RectF;
import android.support.annotation.DrawableRes;
import android.util.Log;
import android.util.Pair;
import android.widget.TextView;
import android.widget.Toast;

import com.kingscastle.nuzi.towerdefence.PlayerAchievements;
import com.kingscastle.nuzi.towerdefence.effects.EffectsManager;
import com.kingscastle.nuzi.towerdefence.effects.animations.Anim;
import com.kingscastle.nuzi.towerdefence.effects.animations.DecoAnimation;
import com.kingscastle.nuzi.towerdefence.effects.animations.StasisZoneAnim;
import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.framework.WtfException;
import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.gameElements.GameElement;
import com.kingscastle.nuzi.towerdefence.gameElements.GenericGameElement;
import com.kingscastle.nuzi.towerdefence.gameElements.Tree;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.Humanoid;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.Unit;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.ArmyManager;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Building;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.BuildingManager;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Buildings;
import com.kingscastle.nuzi.towerdefence.gameElements.movement.pathing.Path;
import com.kingscastle.nuzi.towerdefence.gameElements.movement.pathing.PathFinder;
import com.kingscastle.nuzi.towerdefence.gameElements.movement.pathing.PathFinderParams;
import com.kingscastle.nuzi.towerdefence.gameElements.movement.pathing.PathFoundListener;
import com.kingscastle.nuzi.towerdefence.gameUtils.Difficulty;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.level.rounds.AbstractRound;
import com.kingscastle.nuzi.towerdefence.level.rounds.AbstractRound.RoundParams;
import com.kingscastle.nuzi.towerdefence.level.rounds.Round;
import com.kingscastle.nuzi.towerdefence.teams.HumanPlayer;
import com.kingscastle.nuzi.towerdefence.teams.Team;
import com.kingscastle.nuzi.towerdefence.teams.Teams;
import com.kingscastle.nuzi.towerdefence.teams.races.HumanRace;
import com.kingscastle.nuzi.towerdefence.teams.races.UndeadRace;
import com.kingscastle.nuzi.towerdefence.util.ManagerListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 7/12/2015 for TowerDefence
 */
public abstract class TowerDefenceLevel extends Level{
    private static final String TAG = "TowerDefenceLevel";
    public static final String DONT_ADD_SCORE = "DontAddScore";

    protected Round round;
    private final RoundParams rParams = new RoundParams();
    private int startOnRound = 1;
    private long checkMonstersArePathingAt;
    private Difficulty difficulty;
    protected final List<RectF> noBuildZones = new ArrayList<>();
    private boolean roundActive = true;

    private long playersScore;
    private final PlayerAchievements  playerAchievements = new PlayerAchievements();

    @Override
    protected void subOnCreate() {

        hPlayer = new HumanPlayer();
        mm.add(Team.getNewHumanInstance(hPlayer, new HumanRace(), mm.getGridUtil()));
        hPlayer.getPR().set((int) (getStartingGold() / difficulty.multiplier), 0, 0, 0);
        hPlayer.setLives((int) (hPlayer.getLives() / difficulty.multiplier));

        mm.add(Team.getNewInstance(Teams.RED, new UndeadRace(), mm.getGridUtil()));

        hPlayer.addLVCL(new HumanPlayer.onLivesValueChangedListener() {
            @Override
            public void onLivesValueChanged(int newLivesValue) {
                if (newLivesValue <= 0)
                    playerHasLost();
            }
        });

       // playerAchievements.load((GameActivity) tdg.getActivity(), tdg.getActivity().getSharedPreferences("Achievements", Activity.MODE_PRIVATE));


        rParams.difficulty = difficulty;
        addStartEndLocPairs(rParams.startEndLocPairs);
        rParams.mm = mm;
        rParams.mrgl = new Round.MonsterReachedGoalListener() {
            @Override
            public void onMonsterReachedGoal(@NotNull Unit u) {
                hPlayer.removeLives(u.getCostsLives());
                playersScore -= u.getGoldDropped();
            }
        };
        rParams.playersPr = hPlayer.getPR();

        setRound(getRound(rParams));


        setUpStartAndEndLocs(rParams.startEndLocPairs);

        final LivingThing.OnDeathListener dl = new LivingThing.OnDeathListener() {
            @Override
            public void onDeath(LivingThing lt) {
                if( !lt.getExtras().containsKey(DONT_ADD_SCORE)) {
                    if (lt instanceof Unit) {
                        playersScore += ((Unit) lt).getGoldDropped() * rParams.roundNum;
                        tdg.setText((TextView) tdg.getActivity().findViewById(R.id.score_textview), "Score: " + playersScore);
                    }
                    playerAchievements.incMonstersKilled();
                }
            }
        };

        mm.getTeam(Teams.RED).getAm().addListener(new ManagerListener<Humanoid>() {
            @Override
            public boolean onAdded(Humanoid h) {
                h.addDL(dl);
                return false;
            }
            @Override
            public boolean onRemoved(Humanoid h) {
                return false;
            }
        });

        for( LivingThing lt : mm.getTeam(Teams.RED).getAm().getArmy() )
            lt.addTSL(round);



        for( final Pair<vector,vector> startEndLocPair : rParams.startEndLocPairs ) {
            hPlayer.getTeam().getBm().addBal(new BuildingManager.OnBuildingAddedListener() {
                PathFinderParams pfp = new PathFinderParams();

                {
                    pfp.fromHere = startEndLocPair.first;
                    pfp.toHere = startEndLocPair.second;
                    pfp.grid = mm.getGridUtil().getGrid();
                    pfp.mapWidthInPx = getLevelWidthInPx();
                    pfp.mapHeightInPx = getLevelHeightInPx();
                }

                @Override
                public boolean onBuildingAdded(final Building b) {
                    Log.d(TAG, "On building added");
                    pfp.pathFoundListener = new PathFoundListener() {
                        @Override
                        public void onPathFound(Path path) {
                            if (path == null)
                                cannotPathToThatLocation("Path passed was null for some reason");
//                            else
//                                Log.e(TAG, "Path found to dest");
                        }

                        @Override
                        public void cannotPathToThatLocation(String reason) {
                            Log.e(TAG, "Cannot path to destination! " + reason);

                            hPlayer.getPR().add(b.getCosts());
                            b.die();
                            Anim backing = b.getBacking();
                            if (backing != null) backing.setOver(true);

                            TowerDefenceLevel.this.tdg.getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast t = Toast.makeText(TowerDefenceLevel.this.tdg.getActivity(), "You cannot block path to destination!", Toast.LENGTH_SHORT);
                                    t.show();
                                }
                            });
                        }
                    };

                    PathFinder.findPath(pfp);
                    return false;
                }
            });
        }

        createBorder();
    }

    protected void setUpStartAndEndLocs(List<Pair<vector, vector>> startEndLocPairs){
        for( Pair<vector, vector> startEndLocPair : startEndLocPairs ) {
            vector startLoc = startEndLocPair.first;
            vector endLoc = startEndLocPair.second;

            RectF noBuildZone1 = new RectF(-1 * Rpg.sixTeenDp, -1 * Rpg.sixTeenDp, 1 * Rpg.sixTeenDp, 1 * Rpg.sixTeenDp);
            RectF noBuildZone2 = new RectF(noBuildZone1);
            noBuildZone1.offset(startLoc.x, startLoc.y);
            noBuildZone2.offset(endLoc.x, endLoc.y);
            noBuildZones.add(noBuildZone1);
            noBuildZones.add(noBuildZone2);
            ui.bb.addNoBuildZone(noBuildZone1, noBuildZone2);

            //Setup start and end locations animations
            Anim startAnim = new StasisZoneAnim(startLoc);
            startAnim.setLooping(true);
            startAnim.onlyShowIfOnScreen = false;
            startAnim.setScale(0.25f);
            mm.getEm().add(startAnim, EffectsManager.Position.Behind);

            Anim endAnim = new StasisZoneAnim(endLoc);
            endAnim.setLooping(true);
            endAnim.setScale(0.25f);
            endAnim.onlyShowIfOnScreen = false;
            mm.getEm().add(endAnim, EffectsManager.Position.Behind);
        }
    }


    @Override
    protected void subAct() {
        if( paused )
            return;
        
        if( checkMonstersArePathingAt < GameTime.getTime() ) {
            checkMonstersArePathingAt = GameTime.getTime() + 2000;
            for( Humanoid lt : mm.getTeam(Teams.RED).getAm().getCloneArmy()) {
                if ( lt.getPathToFollow() == null ) {

                    Round round_local = round;
                    if( round_local != null )
                        round_local.findPathFor(lt,(Integer) lt.getExtras().get(AbstractRound.START_END_LOC_INDEX));
                }
            }
        }
        
        if( timeout < GameTime.getTime() ) {
            roundActive = true;

            Round round_local = round;
            if (round_local != null && round_local.act()) {
                ArmyManager evilAM = mm.getTeam(Teams.RED).getAm();
                evilAM.removeListener(round_local);

                int lastRound = rParams.roundNum;
                roundActive = false;
                hPlayer.getTeam().getBm().removeBal(round_local);
                rParams.roundNum++;
                if( rParams.roundNum > getNumberOfRounds() ){
                    playerHasWon();
                }else {
                    setRound(getRound(rParams));
                    timeout = GameTime.getTime() + 10000;
                }
                synchronized (rols){
                    Log.d(TAG,"Calling on round over listeners");
                    for( RoundOverListener rol : rols ) {
                        rol.onRoundOver(lastRound);
                    }
                }
            }
        }

        Round round_local = round;
        setNightTime(round_local != null && round_local.isNightRound());
    }

    @Override
    protected void createBorder() {
        //Create a border
        final int lvlWidthPx = getLevelWidthInPx();
        final int lvlHeightPx = getLevelHeightInPx();

        GameElement ge = getBorderElement(new vector());
        final int treeWidth = (int) ge.getStaticPerceivedArea().width();
        final int treeHeight = (int) ge.getStaticPerceivedArea().height();
        final int numHorz = lvlWidthPx/treeWidth;
        final int numVert = lvlHeightPx /treeHeight;


        //Top wall
        for( int i = 0 ; i < numHorz ; ++i )
            addDecoGE(getBorderElement(new vector(treeWidth / 2 + i * treeWidth, treeHeight / 2)));


        //Bottom wall of trees
        for( int i = 0 ; i < numHorz ; ++i )
            addDecoGE(getBorderElement(new vector((treeWidth / 2) + (i * treeWidth), lvlHeightPx - (treeHeight / 2))));


        //Left wall of trees
        for( int j = 0 ; j < numVert ; ++j )
            addDecoGE(getBorderElement(new vector((treeWidth / 2) , (treeHeight / 2) + (j * treeHeight))));


        //Right wall of trees
        for( int j = 0 ; j < numVert ; ++j )
            addDecoGE(getBorderElement(new vector(lvlWidthPx - (treeWidth / 2) , (treeHeight / 2) + (j * treeHeight))));
    }

    protected GameElement getBorderElement(vector v){
        return new Tree(v);
    }

    protected void addDeco(vector loc, int drawable) {
        mm.getEm().add(new DecoAnimation(loc, drawable));
    }

    protected void addDecoGE(GameElement ge) {
        ge.updateArea();
        mm.getGridUtil().setProperlyOnGrid(ge.area, Rpg.gridSize);
        GridUtil.getLocFromArea(ge.area, ge.getPerceivedArea(), ge.loc);

        boolean ¤‿¤ = false;
        for( RectF noBuildZone: noBuildZones )
            if( RectF.intersects(ge.area,noBuildZone) ){
                ¤‿¤ = true;
                break;
            }
        if( ¤‿¤ ) return;

        if (mm.getCD().checkPlaceable2(ge.area, false))
            mm.addGameElement(ge);
        else {
            Log.d(TAG, "Deco not placeable!");
        }
    }

    protected void addDecoGE(vector loc, @DrawableRes int drawableID) {
        GenericGameElement f = new GenericGameElement(loc,drawableID);
        addDecoGE(f);
    }


    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public long getPlayersScore() {
        return playersScore;
    }

    //*******************************   Abstract Methods   ***************************************//

    public abstract List<Buildings> getAllowedBuildings();

    protected abstract int getNumberOfRounds();

    protected abstract Round getRound(RoundParams rParams);

    public abstract int highestLevelTowersAllowed();

    protected abstract int getStartingGold();

    protected abstract void addStartEndLocPairs(List<Pair<vector, vector>> startEndLocPairs);

    //****************************** End Abstract Methods   **************************************//





    //**********************************   Round Methods   ***************************************//

    public Round getRound() {
        Round round_local = round;
        if(round_local == null)
            throw new WtfException("Must have called onCreate() for round to be initialized!");
        return round_local;
    }

    public void goToRound(int roundNum){
        //RoundParams rParams = new RoundParams();
        rParams.roundNum = roundNum;
        //addStartEndLocPairs(rParams.startEndLocPairs);
        //rParams.mm = mm;
        //rParams.mrgl = new Round.MonsterReachedGoalListener() {
       //     @Override
        //    public void onMonsterReachedGoal(LivingThing lt) {
        //        hPlayer.livesMinusMinus();
        //    }
       // };
       // rParams.playersBm = hPlayer.getTeam().getBm();
       // rParams.playersPr = hPlayer.getPR();

        setRound(getRound(rParams));

    }

    private void setRound(@NotNull Round r){
        Round round_local = round;
        if( round_local != null ) {
            mm.getTeam(Teams.RED).getAm().removeListener(round_local);
            hPlayer.getTeam().getBm().removeBal(round);
        }
        round = r;
        mm.getTeam(Teams.RED).getAm().addListener(round);
        hPlayer.getTeam().getBm().addBal(round);
    }

    public void setStartOnRound(Integer startOnRound) {
        this.startOnRound = startOnRound;
    }

    public boolean canSellBuildingsRightNow() {
        return !roundActive;
    }

    //******************************   End Round Methods   **************************************//




    //************************  Listener Methods And Interfaces   ********************************//

    //Round Over Listener
    protected final ArrayList<RoundOverListener> rols = new ArrayList<>();


    public interface RoundOverListener{
        void onRoundOver(int roundJustFinished);
    }

    public void addROL(RoundOverListener gol)		   		{	synchronized( rols ){	rols.add( gol );			}  	}
    public boolean removeROL(RoundOverListener gol)		{	synchronized( rols ){	return rols.remove( gol );		}	}

}
