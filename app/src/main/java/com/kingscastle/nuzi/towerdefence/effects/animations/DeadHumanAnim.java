package com.kingscastle.nuzi.towerdefence.effects.animations;

import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.List;

/**
 * Created by chris_000 on 7/9/2015.
 */
public class DeadHumanAnim extends Anim {

    private static List<Image> images;


    public DeadHumanAnim(vector loc) {
        loadImages();

        super.loc = loc;
        onlyShowIfOnScreen = true;
        setAliveTime(20000);

        image = images.get((int) (Math.random()*images.size()));
    }

    public Anim newInstance(vector loc){
        return new DeadHumanAnim(loc);
    }

    void loadImages() {
        if (images != null)
            return;
        List<Image> images = Assets.loadAnimationImages(R.drawable.dead_people,12,5);
        DeadHumanAnim.images = images.subList(0,images.size()-10);
    }

    @Override
    public String toString() {
        return "DeadHumanAnim";
    }
}
