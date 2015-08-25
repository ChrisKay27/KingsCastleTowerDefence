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
public class DestroyedBuildingAnim extends Anim {

    private static List<Image> images;
    static{
        List<Image> images = new ArrayList<>();
        images.add(Assets.loadImage(R.drawable.rubble));
        DestroyedBuildingAnim.images = images;
    }


    public DestroyedBuildingAnim(vector loc) {

        super.loc = loc;
        onlyShowIfOnScreen = true;
        setAliveTime(60);

        image = images.get((int) (Math.random()*images.size()));

        add(new RapidImpact(loc),true);
    }

    public Anim newInstance(vector loc){
        return new DestroyedBuildingAnim(loc);
    }



    @Override
    public String toString() {
        return "DestroyedBuildingAnim";
    }
}
