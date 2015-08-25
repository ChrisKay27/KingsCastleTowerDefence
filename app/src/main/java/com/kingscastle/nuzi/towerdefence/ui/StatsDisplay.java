package com.kingscastle.nuzi.towerdefence.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.Cost;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingQualities;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks.AttackerQualities;

import java.util.Locale;







/**
 * Created by Chris on 4/9/14.
 */
public class StatsDisplay extends View
{

    private static final String TAG = "StatsDisplay";
    private LivingThing selectedThing;


    public StatsDisplay( Context context ) {
        super(context);

    }


    @Override
	public void onDraw(Canvas c){

    }



    public static void showStatsDisplay( final LivingThing selectedThing ){

        String name = selectedThing.getName();
        LivingQualities lq = selectedThing.lq;
        AttackerQualities aq = selectedThing.getAQ();

        String msg = "";
        msg += "Health: " + lq.getFullHealth() + "hp +" + lq.getdHealthLvl() +"hp";
        if( lq.getArmor() != 0 )
            msg += "\nArmor: " + lq.getArmor() + (lq.getdArmorLvl() != 0 ? "arm +" + lq.getdArmorLvl() : "") + "arm";

        if( aq != null){
            if( aq.getDamage() != 0 )
                msg += "\nDamage: " + aq.getDamage() + (aq.getdDamageLvl() != 0 ? "dmg +" + aq.getdDamageLvl() : "") + "dmg";
            if( aq.getROF() != 0 ){
                msg += "\nAttack Rate: " + String.format(Locale.CANADA ,"%.1fatks/sec" , 1000.0/aq.getROF());
                if(aq.getdROFLvl() != 0 )
                    msg += " +" + String.format(Locale.CANADA ,"%.1fatks/sec" , -aq.getdROFLvl()/1000.0);
            }
            if( aq.getAttackRangeSquared() != Rpg.getMeleeAttackRangeSquared() ){
                String range = "" + (int)(aq.getAttackRange()/(16*Rpg.getDp()));
                String dRange =  "" + (int) Math.ceil(aq.getdRangeLvl()/(16*Rpg.getDp()));
                msg += "\nRange: " + range + (aq.getdRangeLvl() != 0 ? "m +" + dRange: "") + "m";
            }


        }

       // final Cost upgradeCost = getUpgradeCost( selectedThing.getCosts() , lq.getLevel() +1 );

        /*AlertDialog.Builder builder = new AlertDialog.Builder(game, AlertDialog.THEME_HOLO_DARK);

        builder.setCancelable(false).setTitle(name).setMessage(msg).setPositiveButton("Level up!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                try {
                    //Log.d(TAG, "onClick()");
                    if (Rpg.getGame().getPlayer().canAfford(upgradeCost)) {
                        Rpg.getGame().getPlayer().spendCosts(upgradeCost);
                        selectedThing.upgradeLevel();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).setNegativeButton("Back", null);*/
    }




    private static Cost getUpgradeCost( Cost orgCost , int lvl )
    {
        Cost upgradeCost = new Cost( orgCost );
        upgradeCost.reduceByPerc(lvl/10.0);
        return upgradeCost;
    }







}
