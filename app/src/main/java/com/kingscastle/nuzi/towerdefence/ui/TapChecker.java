package com.kingscastle.nuzi.towerdefence.ui;

import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.framework.Input.TouchEvent;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.GameElement;
import com.kingscastle.nuzi.towerdefence.gameUtils.CoordConverter;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Chris on 8/2/2015 for Tower Defence
 */
public class TapChecker implements TouchEventAnalyzer{

    private static final String TAG = TapChecker.class.getSimpleName();

    private final CoordConverter cc;
    private final Map<GameElement,Integer> thingsToCheckForTapped = new HashMap<>();
    private final Map<GameElement,Runnable> onTappedCalledBack = new HashMap<>();
    private final UI ui;

    public TapChecker(UI ui, CoordConverter cc){
        this.ui = ui;
        this.cc = cc;
    }

    private final RectF tapArea = new RectF();

    @Override
    public boolean analyzeTouchEvent(TouchEvent e) {
        if( e.type != TouchEvent.TOUCH_UP )
            return false;

        if( thingsToCheckForTapped.size() == 0 )
            return false;

        if( ui.getLevel().isPaused() )
            return false;

        vector mapRel = new vector(e.x,e.y);
        cc.getCoordsScreenToMap(mapRel, mapRel);

        List<GameElement> tappedGes = new ArrayList<>();
        boolean returnValue = false;

        synchronized (thingsToCheckForTapped) {
            for (GameElement ge : thingsToCheckForTapped.keySet()) {
                tapArea.set(Rpg.guardTowerArea);
                tapArea.offset(ge.loc.x, ge.loc.y);

                if (tapArea.contains(mapRel.x, mapRel.y))
                    tappedGes.add(ge);
            }

            for (GameElement ge : tappedGes) {
                int tapCount = thingsToCheckForTapped.get(ge) + 1;
                thingsToCheckForTapped.put(ge, tapCount);
                //Log.v(TAG, "Tapped " + ge + " for the "+tapCount+"th time.");

                if (tapCount >= 5) {
                    onTappedCalledBack.remove(ge).run();
                    thingsToCheckForTapped.remove(ge);
                }
                returnValue = true;
            }
        }
        return returnValue;
    }

    public void addTappable(GameElement ge, Runnable callback){
        synchronized (thingsToCheckForTapped) {
            thingsToCheckForTapped.put(ge, 0);
            onTappedCalledBack.put(ge, callback);
        }
    }



}
