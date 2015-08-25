package com.kingscastle.nuzi.towerdefence.effects.animations;


import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Graphics;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

public class Backing extends Anim {

	private static final Image slarge = Assets.loadImage(R.drawable.stone_large);
	private static final Image smedium = Assets.loadImage(R.drawable.stone_medium);
	private static final Image ssmall = Assets.loadImage(R.drawable.stone_small);
	private static final Image stiny = Assets.loadImage(R.drawable.stone_tiny);

	private static final Image rlarge = Assets.loadImage(R.drawable.rock_large);
	private static final Image rmedium = Assets.loadImage(R.drawable.rock_medium);
	private static final Image rsmall = Assets.loadImage(R.drawable.rock_small);
	private static final Image rtiny = Assets.loadImage(R.drawable.rock_tiny);

	private static final Image largest = Assets.loadImage(R.drawable.backing_largest);
	private static final Image large = Assets.loadImage(R.drawable.backing_large);
	private static final Image medium = Assets.loadImage(R.drawable.backing_medium);
	private static final Image small = Assets.loadImage(R.drawable.backing_small);
	private static final Image tiny = Assets.loadImage(R.drawable.backing_tiny);

	private static final Image dlarge = Assets.loadImage(R.drawable.dirt_large);
	private static final Image dmedium = Assets.loadImage(R.drawable.dirt_medium);
	private static final Image dsmall = Assets.loadImage(R.drawable.dirt_small);
	private static final Image dtiny = Assets.loadImage(R.drawable.dirt_tiny);

	public static final int LARGEST = 0;
	public static final int LARGE = 1;
	public static final int MEDIUM = 2;
	public static final int SMALL = 3;
	public static final int TINY = 4;

	public static final int DIRT = 0;
	public static final int COBBLESTONE = 1;
	public static final int ROCK = 2;
	public static final int STONE = 3;

	private int size = MEDIUM;
	private int type = DIRT;

	private Image image = dmedium;


	public Backing( int size , int type, vector loc ){
		setSize( size );
		setType( type );
		looping = true;
		this.loc = loc;
	}



	@Override
	public boolean act(){
		return isOver();
	}

	@Override
	public void paint( Graphics g , vector v ){
		g.drawImage(image, v.x-image.getWidthDiv2()+offs.x , v.y-image.getHeightDiv2()+offs.y , paint );
	}


	public void setSize( int size ){
		if( size < LARGEST || size > TINY)
			this.size = SMALL;
		else
			this.size = size;

		image = getImage( this.size , type );
	}
	public int getSize() {
		return size;
	}


	public void setType( int type ){
		if( type == DIRT )
			this.type = DIRT;
		else if( type == COBBLESTONE )
			this.type = COBBLESTONE;
		else if( type == ROCK )
			this.type = ROCK ;
		else if( type == STONE )
			this.type = STONE;

		image = getImage( size , this.type );
	}




	private static Image getImage(int size, int type) {
		if( type == DIRT ){
			switch( size ){
			case LARGEST: 	return dlarge;
			case LARGE:		return dlarge;
			case MEDIUM:	return dmedium;
			case SMALL:		return dsmall;
			case TINY: 		return dtiny;
			}
		}
		else if( type == COBBLESTONE ){
			switch( size ){
			case LARGEST: 	return largest;
			case LARGE:		return large;
			case MEDIUM:	return medium;
			case SMALL:		return small;
			case TINY: 		return tiny;
			}
		}
		else if( type == ROCK ){
			switch( size ){
			case LARGEST: 	return rlarge;
			case LARGE:		return rlarge;
			case MEDIUM:	return rmedium;
			case SMALL:		return rsmall;
			case TINY: 		return rtiny;
			}
		}
		else if( type == STONE ){
			switch( size ){
			case LARGEST: 	return slarge;
			case LARGE:		return slarge;
			case MEDIUM:	return smedium;
			case SMALL:		return ssmall;
			case TINY: 		return stiny;
			}
		}
		return dmedium;
	}











}
