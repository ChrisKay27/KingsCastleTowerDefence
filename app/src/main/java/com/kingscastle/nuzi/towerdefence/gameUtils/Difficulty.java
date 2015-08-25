package com.kingscastle.nuzi.towerdefence.gameUtils;

/**
 * Created by Chris on 7/24/2015 for Tower Defence
 */
public enum Difficulty {
    Easy(0.75f),Medium(1),Hard(1.25f);

    public final float multiplier;
    Difficulty(float v) {
        multiplier = v;
    }

}
