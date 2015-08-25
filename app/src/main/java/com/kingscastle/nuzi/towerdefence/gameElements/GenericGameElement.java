package com.kingscastle.nuzi.towerdefence.gameElements;

import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.DrawableRes;

import com.kingscastle.nuzi.towerdefence.effects.animations.Anim;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Graphics;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chris on 7/19/2015 for Tower Defence
 */
public class GenericGameElement extends GameElement {

    private static Map<Integer,Image> images = new HashMap<>();
    private int borderColor = Color.YELLOW;

    private Image img;

    private static RectF staticPerceivedArea = new RectF(Rpg.guardTowerArea);
    private Anim anim;


    public GenericGameElement(vector loc, @DrawableRes int drawable){
        super.loc.set(loc);
        if( !images.containsKey(drawable) )
            images.put(drawable,Assets.loadImage(drawable));
        img = images.get(drawable);
    }

    @Override
    public boolean create( MM mm )
    {
        super.create(mm);
        if ( !hasBeenCreated() )
        {
            loadAnimation( mm );
            mm.getEm().add( anim , true );
            created = true ;
        }
        updateArea();
        return true;
    }


    public Image getImage(){
        return img;
    }


    @Override
    public void loadAnimation( MM mm )
    {
        if( anim == null )
        {
            final GenericGameElement thisGE = this;
            anim = new Anim( getImage() ){
                private boolean incAlpha = true;
                private float sat = 1;
                private final ColorMatrix cm;
                {
                    cm = new ColorMatrix();
                    cm.setSaturation(sat);
                    paint = new Paint();
                    paint.setColorFilter(new ColorMatrixColorFilter(cm));
                    paint.setXfermode( new PorterDuffXfermode( PorterDuff.Mode.DST_OVER ));
                }
                @Override
                public int getSelectedBorderColor(){
                    return thisGE.borderColor;
                }
                @Override
                public void paint( Graphics g , vector v ){
                    super.paint( g , v );
                    if( thisGE.isSelected() ){
                        if( sat >= 1 )
                            incAlpha = false;
                        else if( sat <= 0 )
                            incAlpha = true;

                        if( incAlpha )
                            sat += 0.07f;
                        else
                            sat -= 0.07f;
                        cm.setSaturation(sat);
                        paint.setColorFilter(new ColorMatrixColorFilter(cm));
                    }
                    else if( sat != 1){
                        sat = 1;
                        cm.setSaturation(sat);
                        paint.setColorFilter(new ColorMatrixColorFilter(cm));
                    }
                }
            };
            anim.setDrawAreaBorderWhenBuilding(getPerceivedArea());
        }
        anim.shouldBeDrawnThroughFog = true;
        anim.setLoc( getLoc() );
        //anim.setPaint( dstATopPaint );
    }



    @Override
    public void setSelected(boolean b){
        super.setSelected(b);
        getAnim().shouldDrawBorder = b;
    }

    public Anim getAnim(){
        return anim;
    }

    public void setAnim(Anim anim)
    {
        this.anim = anim;
    }




    @Override
    public ImageFormatInfo getImageFormatInfo() {
        return null;
    }

    @Override
    public Image[] getStaticImages() {
        return null;
    }

    @Override
    public void setStaticImages(Image[] images) {
    }


    @Override
    public void die(){
        if( anim != null ){
            anim.setOver( true );
        }
        setDead( true );
    }



    @Override
    public void saveYourself( BufferedWriter bw ) throws IOException
    {
        String temp;

        temp = "<" + getClass().getSimpleName() + " x=\"" + (int) (loc.x) + "\" y=\"" + (int) (loc.y) + "\" rr=\"" + 0 + "\" >";



        bw.write( temp , 0 , temp.length() );
        bw.newLine();


        temp = "</" + getClass().getSimpleName() + ">";
        bw.write( temp , 0 , temp.length() );
        bw.newLine();
    }



    public void setSelectedColor(int color)
    {
        borderColor = color;
    }




    @Override
    public RectF getStaticPerceivedArea() {
        return staticPerceivedArea;
    }

}
