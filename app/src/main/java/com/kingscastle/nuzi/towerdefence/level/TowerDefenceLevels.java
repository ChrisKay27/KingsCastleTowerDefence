package com.kingscastle.nuzi.towerdefence.level;

import com.kingscastle.nuzi.towerdefence.TowerDefenceGame;

import java.lang.reflect.Constructor;

/**
 * Created by Chris on 7/10/2015 for Tower Defence
 */
public class TowerDefenceLevels {

    public enum TDLevels{
        Forest(Forest.class,null),
        Valley(Valley.class,TDLevels.Forest),
        ForestStrip(ForestStrip.class,TDLevels.Valley),
        Desert(DesertLevel.class,TDLevels.ForestStrip),
        Desert2(Desert2.class,TDLevels.Desert),
        Desert3(Desert3.class,TDLevels.Desert2),
        Desert4(Desert4.class,TDLevels.Desert3);

        public final Class<? extends TowerDefenceLevel> klass;
        public final TDLevels previousLevel;

        TDLevels(Class<? extends TowerDefenceLevel> aClass, TDLevels prevLevel) {
            klass = aClass;
            previousLevel = prevLevel;
        }

        public TDLevels getNextLevel(){
            switch(this){
                default:
                case Forest: return Valley;
                case Valley: return ForestStrip;
                case ForestStrip: return Desert;
                case Desert: return Desert2;
                case Desert2: return Desert3;
                case Desert3: return Desert4;
                case Desert4: return null;
            }
        }

    }


    public static TowerDefenceLevel get(String levelClass) {

        try
        {
            Class<?> levelKlass = Class.forName("com.kingscastle.nuzi.towerdefence.level."+levelClass);

            Constructor<?>[] constrs = levelKlass.getConstructors();

            for( Constructor<?> c : constrs ){
                try{
                    return (TowerDefenceLevel) c.newInstance();
                }catch(Exception e)
                {
                    if( TowerDefenceGame.testingVersion ){
                        e.printStackTrace();
                    }
                }
            }

            return new Forest();
        }
        catch( NoClassDefFoundError e )
        {
            if( TowerDefenceGame.testingVersion ){
                e.printStackTrace();
            }
        }
        catch(Exception e)
        {
            if( TowerDefenceGame.testingVersion ){
                e.printStackTrace();
            }
            //Log.v( TAG , "Did not find the Class in the livingThing.army. folder " );
        }


        return new Forest();
    }
}
