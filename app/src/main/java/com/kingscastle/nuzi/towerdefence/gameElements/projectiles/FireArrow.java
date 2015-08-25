package com.kingscastle.nuzi.towerdefence.gameElements.projectiles;

import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Chris on 8/5/2015 for Tower Defence
 */
public class FireArrow extends Arrow {

    public FireArrow()	{}
    public FireArrow(LivingThing shooter, vector unitVectorInDirection) {
        super( shooter , unitVectorInDirection);
    }
    public FireArrow( LivingThing caster , LivingThing target) {
        super( caster , target );
    }
    private FireArrow(LivingThing shooter, vector to, LivingThing target) {
    }


    @Override
    public Projectile newInstance(@NotNull LivingThing shooter, @NotNull vector unitVectorInDirection) {
        return new FireArrow(shooter,unitVectorInDirection);
    }



}
