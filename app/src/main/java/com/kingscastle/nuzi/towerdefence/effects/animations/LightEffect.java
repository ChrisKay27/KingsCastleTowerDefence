package com.kingscastle.nuzi.towerdefence.effects.animations;

import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.List;

/**
 * Created by chris_000 on 7/9/2015.
 */
public class LightEffect extends Anim {

    public enum LightEffectColor{LIGHT_BLUE,DARK_BLUE,LIGHT_ORANGE,DARK_ORANGE};

    private static List<Image> images;


    public LightEffect(vector loc, LightEffectColor color) {
        loadImages();

        switch(color){
            case DARK_BLUE: super.image = images.get(4); break;
            case LIGHT_BLUE: super.image = images.get(5); break;
            case LIGHT_ORANGE: super.image = images.get(0); break;
            case DARK_ORANGE: super.image = images.get(2); break;
        }

        super.loc = loc;
        onlyShowIfOnScreen = true;
        setPaint(Rpg.getXferAddPaint());
    }

    void loadImages() {
        if (images != null)
            return;
        images = Assets.loadAnimationImages(R.drawable.light_effects,3,4);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
