package com.kingscastle.nuzi.towerdefence.gameElements;

import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.teams.Teams;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class GameElement
{
	private static final String TAG = GameElement.class.getSimpleName();

	@NotNull
	public final vector loc = new vector();
	@NotNull
	public final RectF area = new RectF();

	private Teams team;
	protected vector offset;
	public boolean dead;
	public boolean created;
	private boolean createdProperly;
	protected boolean selected;

	@NotNull
	private MM mm;
	protected CD cd;


	@NotNull
	private Map<String,Object> extras = new HashMap<>();


	protected GameElement()	{
	}

    /**
     * This method is called when this game element is added to the appropriate manager.
     * Note: This method is override-able but MUST be called in the overridden method.
     *
     * @param mm sets the Manager Manager for this Game Element
     * @return true if this Game Element was created and should be added into the manager.
     */
	public boolean create(@NotNull MM mm) {
		if( mm == null )
			throw new NullPointerException("Cannot pass a null mm to create(MM mm)!");
		this.mm = mm;
		cd = mm.getCD();
		createdProperly = true;
		return true;
	}


    /**
     * @return true if this Game Element should be removed from the manager
     */
    public boolean act() {
        return dead;
    }




    protected void loadAnimation(@NotNull MM mm){
	}


	public void initialize()	{
		loadImages();
		updateArea();
	}




	public void setLoc( @NotNull vector mapLoc )	{
		loc.set(mapLoc);
		checkBounds();
	}




	public void checkBounds()	{
		if( loc.x < 30 )
			loc.x = 30;
//
//		int mapWidthInPxMinus10 = GameElement.mapWidthInPxMinus10;
//		if( mapWidthInPxMinus10 != 0 ){
//			if( loc.x > mapWidthInPxMinus10 )
//				loc.x = mapWidthInPxMinus10;
//		}

		if( loc.y < 30 )
			loc.y = 30;

//		int mapHeightInPxMinus20 = GameElement.mapHeightInPxMinus20;
//		if( mapHeightInPxMinus20 != 0 ){
//			if( loc.y > mapHeightInPxMinus20 )
//				loc.y = mapHeightInPxMinus20;
//		}
	}



	@NotNull
	public vector getLoc(){
		return loc;
	}

	public void setLoc( float x , float y )	{
		loc.x = x ;
		loc.y = y ;
	}



	@NotNull
	public RectF getArea(){
		return area;
	}




	public void updateArea()	{
		if( getPerceivedArea() == null )
			return;

		RectF percArea = getPerceivedArea();

		float left = loc.x + getPerceivedArea().left;
		float top =  loc.y + getPerceivedArea().top;


		area.set( left , top , left + percArea.width() , top
				+ percArea.height() );
	}


	public RectF getPerceivedArea()	{
		if( getStaticPerceivedArea() != null )
			return getStaticPerceivedArea();
		else
		{
			loadImages();
			if(getImages() != null)
				setStaticPerceivedArea( loadPerceivedAreaFromImage(getImages()[0]) );

			return getStaticPerceivedArea();
		}
	}



	@NotNull
	public RectF loadPerceivedAreaFromImage( @Nullable Image img )	{
		if ( img == null )
		{
			float dp = Rpg.getDp();
			////Log.e ( TAG , "Trying to load perc area from image and image is null for a " + this );
			return new RectF( -dp*8 , -dp*8 , 8*dp , 8*dp );
		}
		return new RectF(-img.getWidthDiv2(), -img.getHeightDiv2(),
				img.getWidthDiv2(), img.getHeightDiv2());
	}


	protected void setStaticPerceivedArea(RectF rectF) {
	}



	public Image[] getImages()	{
		loadImages();
		return getStaticImages();
	}

	protected void loadImages()	{
		if ( getStaticImages() == null )
			setStaticImages( Assets.loadImages(getImageFormatInfo()) );
	}


	@Nullable
	public Teams getTeamName() {
		return team;
	}
	public void setTeamName(Teams t) {
		team = t;
	}





	public void die(){
		setDead( true );
	}


    public final boolean isDead(){
		return dead;
	}
	protected final void setDead(boolean dead){
		this.dead = dead;
	}






	@Override
	public String toString()
	{
		return getClass().getSimpleName();
	}


	public boolean hasBeenCreated(){
		return created;
	}

	public void setCreated(boolean created) {
		this.created = created;
	}


	public void saveYourself( BufferedWriter bw ) throws IOException{	}



	public void setSelected(boolean b){
		selected = b;
	}
	public boolean isSelected(){
		return selected;
	}




	public String getName() {
		return getClass().getSimpleName();
	}


	public final boolean hasBeenCreatedProperly(){
		return createdProperly;
	}


	@NotNull
	public final Map<String, Object> getExtras() {
		return extras;
	}


	@NotNull
	protected final MM getMM() {
		return mm;
	}




	protected ImageFormatInfo getImageFormatInfo(){
		return null;
	}
	protected  Image[] getStaticImages(){
		return null;
	}
	protected void setStaticImages(Image[] images){
	}
	public RectF getStaticPerceivedArea(){
		return null;
	}

}
