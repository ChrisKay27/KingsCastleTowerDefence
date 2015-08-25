package com.kingscastle.nuzi.towerdefence.ui;

import com.kingscastle.nuzi.towerdefence.effects.EffectsManager;
import com.kingscastle.nuzi.towerdefence.effects.animations.Anim;
import com.kingscastle.nuzi.towerdefence.framework.Input.TouchEvent;
import com.kingscastle.nuzi.towerdefence.gameUtils.CoordConverter;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

/**
 * Created by Chris on 8/2/2015 for Tower Defence
 */
public class EffectPlacer implements TouchEventAnalyzer{

    private static final String TAG = EffectPlacer.class.getSimpleName();

    private final EffectsManager em;
    private final CoordConverter cc;
    private Anim animToPlace;

    public EffectPlacer(EffectsManager em, CoordConverter cc){
        this.cc = cc;
        this.em = em;
    }


    @Override
    public boolean analyzeTouchEvent(TouchEvent e) {
        Anim a = animToPlace;
        if( a == null || e.type == TouchEvent.TOUCH_UP )
            return false;

        vector dir = new vector(e.x,e.y);
        cc.getCoordsScreenToMap(dir, dir);

        a.setLoc(new vector(dir));
        em.add(a);
        animToPlace = null;

        return true;
    }


    public void setAnimToPlace(Anim a) {
        this.animToPlace = a;
    }

}
