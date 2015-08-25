package com.kingscastle.nuzi.towerdefence.gameElements.spells;

import android.support.annotation.Nullable;

import com.kingscastle.nuzi.towerdefence.effects.animations.Anim;
import com.kingscastle.nuzi.towerdefence.effects.animations.BlackSummonSmokeAnim;
import com.kingscastle.nuzi.towerdefence.effects.animations.LightEffect2;
import com.kingscastle.nuzi.towerdefence.effects.animations.MagicShieldAnim;
import com.kingscastle.nuzi.towerdefence.effects.animations.TapAnim;
import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.framework.Input;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.Humanoid;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.teams.Teams;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Chris on 8/6/2015 for Tower Defence
 */
public class SummoningPortal extends Spell {

    private static final String TAG = SummoningPortal.class.getSimpleName();
    private final OnCreatureSummonedListener ocsl;

    private List<Class<? extends Humanoid>> summons;

    private final long summonFreq;
    private long nextSummonAt;

    private final long aliveTime;
    private long dieAt;

    private final List<Anim> anims = new ArrayList<>();

    public SummoningPortal(@NotNull vector loc, @NotNull LivingThing caster, long summonFreq, long aliveTime,
                           @Nullable OnCreatureSummonedListener ocsl, @NotNull Class<? extends Humanoid>... summons){
        this(loc, caster,summonFreq,aliveTime, ocsl, Arrays.asList(summons));
    }
    public SummoningPortal(@NotNull vector loc, @NotNull LivingThing caster, long summonFreq, long aliveTime,
                           @Nullable OnCreatureSummonedListener ocsl,@NotNull List<Class<? extends Humanoid>> summons){
        this.ocsl = ocsl;
        setLoc(loc);
        setCaster(caster);
        this.summonFreq = summonFreq;
        this.aliveTime = aliveTime;
        this.summons = summons;
    }

    @Override
    public boolean act() {
        if( nextSummonAt < GameTime.getTime() ){
            nextSummonAt = GameTime.getTime()+summonFreq;

            Class<? extends Humanoid> h = summons.get((int) (Math.random()*summons.size()));
            try {
                Humanoid summon = h.getConstructor(vector.class,Teams.class).newInstance(new vector(loc),getCaster().getTeamName());
                summon.setCostsLives(0);
                getMM().getEm().add(new BlackSummonSmokeAnim(loc));

                getMM().add(summon);
                if( ocsl != null )
                    ocsl.creatureSummoned(summon);

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        if( dieAt < GameTime.getTime() )
            die();

        return isDead();
    }


    @Override
    public void loadAnimation() {
        final LightEffect2 le = new LightEffect2(loc);
        le.setScale(2);


        Anim a = new MagicShieldAnim(loc);
        a.setLooping(true);
        a.setAliveTime((int) aliveTime);
        Anim tapThis = new TapAnim(loc);
        tapThis.setAliveTime(4000);

        anims.add(le);
        anims.add(a);
        anims.add(tapThis);

        getMM().getEm().add(le);
        getMM().getEm().add(a);
        getMM().getEm().add(tapThis);

    }

    @Override
    public boolean cast(MM mm) {
        loadAnimation();
        dieAt = GameTime.getTime()+aliveTime;
        nextSummonAt = GameTime.getTime()+1000;
        return super.cast(mm);
    }

    @Override
    public void die() {
        super.die();
        for(Anim a : anims)
            a.setOver(true);
    }

    @Override
    public String getName() {
        return TAG;
    }


    @Override
    protected int calculateDamage() {
        return 0;
    }



    @Override
    public Abilities getAbility() {
        return null;
    }

    @Override
    public boolean analyseTouchEvent(Input.TouchEvent event) {
        return false;
    }

    @Override
    public Spell newInstance() {
        return new SummoningPortal(loc, getCaster(),summonFreq,aliveTime,ocsl, summons);
    }

    public interface OnCreatureSummonedListener{
        void creatureSummoned(Humanoid h);
    }
}
