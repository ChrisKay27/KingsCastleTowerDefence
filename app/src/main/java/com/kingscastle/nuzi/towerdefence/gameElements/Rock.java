package com.kingscastle.nuzi.towerdefence.gameElements;

import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.effects.animations.Anim;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Rock extends GameElement {

	private int borderColor = Color.YELLOW;

	private static List<Image> images = new ArrayList<>();
	static{
		images.add(Assets.loadImage(R.drawable.tall_rock));
		images.add(Assets.loadImage(R.drawable.rocks_dark));
		images.add(Assets.loadImage(R.drawable.rocks_dark_large));
		images.add(Assets.loadImage(R.drawable.small_rock));
	}
	private Image img = images.get((int) (Math.random() * images.size()));
	//private static RT resourceType;// = RT.WOOD;

	private static RectF staticPerceivedArea = new RectF(Rpg.oneByOneArea);
	private Anim anim;


	public Image getImage(){
		return img;
	}

	public Rock(vector loc){
		this.loc.set(loc);
	}
	public Rock(){
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

	@Override
	public void loadAnimation( MM mm )
	{
		if( anim == null )
		{
			final Rock thisTree = this;
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
					return thisTree.borderColor;
				}
//				@Override
//				public void paint( Graphics g , Vectorr v ){
//					super.paint( g , v );
//					if( thisTree.isSelected() ){
//						if( sat >= 1 )
//							incAlpha = false;
//						else if( sat <= 0 )
//							incAlpha = true;
//
//						if( incAlpha )
//							sat += 0.07f;
//						else
//							sat -= 0.07f;
//						cm.setSaturation(sat);
//						paint.setColorFilter(new ColorMatrixColorFilter(cm));
//					}
//					else if( sat != 1){
//						sat = 1;
//						cm.setSaturation(sat);
//						paint.setColorFilter(new ColorMatrixColorFilter(cm));
//					}
//				}
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
