package com.kingscastle.nuzi.towerdefence.effects.animations;

import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chris_000 on 7/9/2015.
 */
public class DeadSkeletonAnim extends Anim {

    private static List<Image> images;


    public DeadSkeletonAnim(vector loc) {
        loadImages();

        super.loc = loc;
        onlyShowIfOnScreen = true;
        setAliveTime(20000);

        image = images.get((int) (Math.random()*images.size()));

    }

    public Anim newInstance(vector loc){
        return new DeadSkeletonAnim(loc);
    }

    void loadImages() {
        if (DeadSkeletonAnim.images != null)
            return;
        List<Image> images = new ArrayList<>();
        images.add(Assets.loadImage(R.drawable.skeleton));
        images.addAll(Assets.loadAnimationImages(R.drawable.skeleton_corpse, 2, 1));
        images.addAll(Assets.loadAnimationImages(R.drawable.dead_skeletons, 4, 2));
        DeadSkeletonAnim.images = images;
    }

    @Override
    public String toString() {
        return "DeadSkeletonAnim";
    }
}
