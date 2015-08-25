package com.kingscastle.nuzi.towerdefence.util;

/**
 * Created by Chris on 8/8/2015 for Tower Defence
 */
public interface ManagerListener<GE> {

    boolean onAdded(GE ge);
    boolean onRemoved(GE ge);

}
