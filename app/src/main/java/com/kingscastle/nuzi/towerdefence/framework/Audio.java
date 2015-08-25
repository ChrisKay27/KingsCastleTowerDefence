package com.kingscastle.nuzi.towerdefence.framework;


import com.kingscastle.nuzi.towerdefence.framework.implementation.AndroidSound;

public interface Audio {
    public Music createMusic(String file);

    public AndroidSound createSound(String file);
}