package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.abilities;


import com.kingscastle.nuzi.towerdefence.effects.animations.Anim;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Input;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.teams.Teams;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.IOException;

public interface Ability
{
	enum Abilities
	{
		HEXARMOR, HEALINGSPELL, HASTE , STUN , MAGICSHIELD, SPEEDSHOT , BLIZZARD , EARTHQUAKE , ERUPTION , EXPLOSION ,
		FIREPUNCH, GROUNDSMASHER , ICICLE , LIGHTNINGBOLTS , LIGHTNINGSTRIKE , POWERBURN , QUAKE , RAINBOWSTRIKE, WHIRLWIND, FIREBALL, ARMOR_BUFF, HOT_BUFF, LASER, EVERYTHING_BUFF, SLOW, DOT_BUFF, TEMPORAL_SHOCK
	}

	int calculateManaCost(@NotNull LivingThing aWizard);
	//Ability newInstance();
	Ability newInstance(@NotNull LivingThing target);
	boolean cast(MM mm);
	Image getIconImage();
	void saveYourSelf(@NotNull BufferedWriter b) throws IOException;
	LivingThing getCaster();
	void setCaster(@NotNull LivingThing caster);
	void setTarget(@NotNull LivingThing target);
	boolean act();
	boolean cast(@NotNull MM mm, @NotNull LivingThing target);
	Abilities getAbility();
	LivingThing getTarget();
	boolean isStackable();
	Anim getAnim();
	void die();
    boolean isOver();
	boolean analyseTouchEvent(@NotNull Input.TouchEvent event);
	void setTeam(@NotNull Teams teamName);
    void doAbility();
    void undoAbility();
}
