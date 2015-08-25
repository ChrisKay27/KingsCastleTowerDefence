package com.kingscastle.nuzi.towerdefence.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by Chris on 7/15/2015 for Tower Defence
 */
public class ViewAnimatorHelper {

    public static void shinkAway(@NonNull final View v, int shrinkTime, @Nullable Animator.AnimatorListener listener ){
        ValueAnimator a = ValueAnimator.ofFloat(1f,0f);
        a.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.setScaleX((Float) animation.getAnimatedValue());
                v.setScaleY((Float) animation.getAnimatedValue());
            }
        });
        a.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                v.setVisibility(View.INVISIBLE);
                v.setScaleX(1);
                v.setScaleY(1);
            }
        });
        if( listener != null )
            a.addListener(listener);

        a.setDuration(shrinkTime);
        a.start();
    }

    public static void grow(final View v, int growTime, @Nullable Animator.AnimatorListener listener  ){
        v.setVisibility(View.VISIBLE);
        v.bringToFront();
        ValueAnimator a = ValueAnimator.ofFloat(0f, 1f);
        a.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.setScaleX((Float) animation.getAnimatedValue());
                v.setScaleY((Float) animation.getAnimatedValue());
            }
        });
        a.setDuration(growTime);
        if( listener != null )
            a.addListener(listener);
        a.start();
    }

    public static void slideToBottomOfScreen(final View v, int screenHeight, int slideTime, @Nullable Animator.AnimatorListener listener  ){
        ValueAnimator a = ValueAnimator.ofFloat(0f,screenHeight-v.getBottom());
        a.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.setTranslationY((Float) animation.getAnimatedValue());
            }
        });
        a.setDuration(slideTime);
        if( listener != null )
            a.addListener(listener);
        a.start();
    }


}
