package com.kingscastle.nuzi.towerdefence.effects.animations;

import android.animation.Animator.AnimatorListener;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;

import com.kingscastle.nuzi.towerdefence.effects.EffectsManager;
import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.framework.Graphics;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Settings;
import com.kingscastle.nuzi.towerdefence.gameUtils.CoordConverter;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;


public class Anim
{
	private static final String TAG = Anim.class.getSimpleName();

	private static final int borderColor = Color.YELLOW;
	private static final Paint defaultPaint;
	static
	{
		defaultPaint = new Paint();
		defaultPaint.setXfermode( new PorterDuffXfermode( PorterDuff.Mode.DST_OVER ));
	}


	private String name;

	@NotNull
	protected final ArrayList<Anim> addedInFront = new ArrayList<>(), addedBehind = new ArrayList<>();

	List<Image> images;
	protected Paint paint = defaultPaint;
	Image image;
	protected Image temp;
	int currentImageIndex = 0;
	long nextImageChange = System.currentTimeMillis();
	int tbf = 0;
	long startTime = GameTime.getTime();
	int aliveTime = 0;

	protected float scaleX = 1f;
	protected float scaleY = 1f;

	public boolean shouldDrawThis = false;
	public boolean shouldDrawBorder = false;
	public boolean visible = true;

	boolean looping;
	public boolean over = false;
	private boolean isUIElement;
	private boolean clearAddedAnimations = false;
	private boolean wasDrawnThisFrame;
	public boolean ownedByHumanPlayer;
	public boolean shouldBeDrawnThroughFog;
	public boolean onlyShowIfOnScreen;

	@Nullable
	public vector loc = new vector();


	vector offs = new vector();

	protected final vector vTemp = new vector();

	protected RectF area;
	private EffectsManager.Position reqPos;


	public void reset()
	{
		shouldDrawThis = false;
		visible = true;
		over = false;
		currentImageIndex = 0;
		nextImageChange = System.currentTimeMillis();
		startTime = GameTime.getTime();
		wasDrawnThisFrame = false;
	}



	public Anim(){}


	public Anim(@NotNull vector location, @NotNull List<Image> imgs, int timeBetweenFrames, int stayAliveFor, boolean looping)
	{
		if( imgs == null )
			throw new NullPointerException("anim==null");

		images=imgs;
		setLoc(location);
		tbf=timeBetweenFrames;
		aliveTime=stayAliveFor;
		this.looping=looping;
	}


	public Anim(@NotNull vector location, @NotNull List<Image> imgs, int timeBetweenFrames)
	{
		if(imgs==null)
			throw new NullPointerException("anim==null");

		images=imgs;
		setLoc(location);
		tbf=timeBetweenFrames;
	}



	public Anim(@NotNull vector location, @NotNull List<Image> imgs, int timeBetweenFrames, int stayAliveFor)
	{
		if(imgs==null)
			throw new NullPointerException("anim==null");

		images=imgs;
		setLoc(location);
		tbf=timeBetweenFrames;
		aliveTime=stayAliveFor;
	}

	public Anim(@NotNull Image img) {
		image = img;
	}

	public Anim(@NotNull Image img, int aliveTime) {
		if(img==null)
			throw new NullPointerException("anim==null");

		//images=new ArrayList<Image>(1);
		//images.add(img);

		image = img;
		this.aliveTime=aliveTime;
	}


	public Anim(@NotNull vector loc, Image img, int timeBetweenFrames, int stayAliveFor) {
		setLoc(loc);
		images = new ArrayList<>(1);
		images.add(img);
		setTbf(timeBetweenFrames);
		aliveTime = stayAliveFor;
	}
	public Anim(@NotNull vector loc, @NotNull Image img) {
		setLoc(loc);
		images = new ArrayList<>(1);
		images.add(img);
	}

	public Anim(@NotNull Anim anim)
	{
		if( anim == null )
			throw new NullPointerException("anim==null");

		setLoc(anim.loc);
		images = anim.images;
		image = anim.image;
		aliveTime = anim.aliveTime;
		tbf = anim.tbf;
		looping = anim.looping;
	}



	public Anim(@NotNull vector loc2, @NotNull Anim other)
	{
		if( other.images == null && other.image == null )
			throw new NullPointerException("other.images==null %% other.image = null");

		setLoc(loc2);
		image=other.image;
		images=other.images;
		tbf=other.tbf;
		aliveTime=other.aliveTime;
		looping=other.looping;

	}


	public void setImage(@NotNull Image img )
	{
		image=img;
	}
	public Image getImage()
	{
		if( image != null )
			return image;
		if( over || images == null || currentImageIndex + 1 >= images.size() )
			return null;

		if( tbf == 0 )
			return images.get( 0 );

		return images.get( currentImageIndex );
	}


	protected final Rect dst = new Rect();
	protected float pScaleX;
	protected float pScaleY;
	public void paint( Graphics g, vector v , CoordConverter cc )
	{
		paint( g , v );
	}

	public void paint( Graphics g, vector v )
	{
		if( !isVisible() )
			return;

		vTemp.set(v);
		vTemp.add( offs );


		synchronized (addedBehind) {
			for (Anim a : addedBehind)
				a.paint(g, vTemp);
		}


		if( ( shouldDrawBorder || Settings.showAllAreaBorders || Settings.alwaysShowAreaBorders ) && area != null )
			g.drawRectBorder( area , v , getSelectedBorderColor() , 1 );


		temp = getImage();
		if( temp != null ){
			if( scaleX != 1 || scaleY != 1 ){
				if( pScaleX != scaleX || pScaleY != scaleY ){
					dst.set(temp.getSrcRect());
					dst.inset((int) (-(dst.width()/2)*(scaleX-1)),(int) (-(dst.height()/2)*(scaleY-1)));
				}
				dst.offsetTo(0,0);
				dst.offset((int) vTemp.x-dst.width()/2,(int) vTemp.y-dst.height()/2);
				g.drawImage(temp, temp.getSrcRect(), dst, paint );
			}
			else
				g.drawImage( temp , vTemp.x - temp.getWidthDiv2() , vTemp.y - temp.getHeightDiv2() , paint );
		}


		synchronized (addedInFront) {
			for (Anim a : addedInFront)
				a.paint(g, vTemp);
		}
	}



	//private final ArrayList<Animation> deadAnims = new ArrayList<Animation>();

	/**
	 * @return true if the animation is over and should be removed.
	 */
	public boolean act()
	{
		if( over ) {
			synchronized (addedInFront) {
				for (Anim a : addedInFront)
					a.setOver(true);
				addedInFront.clear();
			}
			synchronized (addedBehind) {
				for (Anim a : addedBehind)
					a.setOver(true);
				addedBehind.clear();
			}

			return true;
		}

		synchronized (addedInFront) {
			Iterator<Anim> i = addedInFront.listIterator();
			while(i.hasNext()) {
				Anim a = i.next();
				if (a.act()) {
					a.setOver(true);
					i.remove();
				}
			}
		}


		if( images != null ) {
			if (nextImageChange < System.currentTimeMillis()) {
				nextImageChange = System.currentTimeMillis() + tbf;
				++currentImageIndex;

				if (currentImageIndex + 1 == images.size()) {
					if (looping)
						if (aliveTime != 0 && startTime + aliveTime < GameTime.getTime()) {
							over = true;
							synchronized (animListeners) {
								for (AnimatorListener al : animListeners)
									al.onAnimationEnd(null);
							}
						} else {
							currentImageIndex = 0;
							synchronized (animListeners) {
								for (AnimatorListener al : animListeners)
									al.onAnimationRepeat(null);
							}
						}
					else {
						over = true;
						synchronized (animListeners) {
							for (AnimatorListener al : animListeners)
								al.onAnimationEnd(null);
						}
					}
				}
			}
		}
		else{
			if( aliveTime != 0 && startTime + aliveTime < GameTime.getTime()){
				over = true;
				synchronized (animListeners) {
					for (AnimatorListener al : animListeners)
						al.onAnimationEnd(null);
				}
			}
		}


		synchronized (addedBehind) {
			Iterator<Anim> i = addedBehind.listIterator();
			while(i.hasNext()) {
				Anim a = i.next();
				if (a.act()) {
					a.setOver(true);
					i.remove();
				}
			}
		}

		return over;
	}


	public void add( Anim a , boolean infront )
	{
		if( a == null )
			return;

		if(infront)
			addedInFront.add( a );
		else
			addedBehind.add( a );
	}






	public void remove(@NotNull Anim a)
	{
		if( a == null )
			return;

		addedInFront.remove(a);

		addedBehind.remove(a);
	}



	public void start()
	{
		nextImageChange=GameTime.getTime();
		startTime=nextImageChange;
		synchronized( animListeners ){
			for( AnimatorListener al : animListeners )
				al.onAnimationStart(null);
		}
	}




	public boolean isOver()
	{
		return over;
	}

	public void restart()
	{
		startTime = GameTime.getTime();
		nextImageChange = System.currentTimeMillis();
		currentImageIndex=0;
	}

	public List<Image> getAnimImages(){
		return images;
	}

	public void setImages(List<Image> images2) {
		images=images2;
	}
	public int getTbf(){
		return tbf;
	}
	public void setTbf(int tbf){
		this.tbf=tbf;
	}
	public void setLooping(boolean b){
		looping=b;
	}

	public void setOver(boolean over) {
		this.over = over;
		if( over )
			synchronized( animListeners ){
				for( AnimatorListener al : animListeners )
					al.onAnimationEnd(null);
			}
	}

	public static Paint getDefaultpaint() {
		return defaultPaint;
	}


	public void setAliveTime(int i) {
		aliveTime=i;
	}
	int getCurrentImageIndex(){
		return currentImageIndex;
	}
	void setCurrentImageIndex(int i){
		if(i<images.size()) {
			currentImageIndex=i;
		}
	}

	public boolean isUIElement() {
		return isUIElement;
	}

	public void saveYourSelf(BufferedWriter b) throws IOException
	{
		if(name != null)
		{
			String s = "<" + name + " loc = " + loc + ">";
			b.write(s,0,s.length());b.newLine();
		}

	}

	public void nullify()
	{
		for( Anim a : addedInFront )
		{
			a.setOver(true);
			a.nullify();
		}

		for( Anim a : addedBehind )
		{
			a.setOver( true );
			a.nullify();
		}
	}


	private static final AnimationComparator sorter = new AnimationComparator();

	public void sortThis( ArrayList<Anim> things )
	{
		Collections.sort(things , sorter);
	}

	private static class AnimationComparator implements Comparator<Anim>
	{

		@Override
		public int compare( Anim ge1 , Anim ge2 )
		{
			if( ge1 == null )
			{
				if( ge2 == null )
				{
					return 0;
				}
				return -1;
			}
			if( ge2 == null )
			{
				return -1;
			}
			//			if( ge2 == null )
			//			{
			//				return -1;
			//			}

			float y1 = ge1.loc.y + ge1.offs.y;
			float y2 = ge2.loc.y + ge2.offs.y;

			if( y1 > y2 )
			{
				return 1;
			}
			else if( y1 < y2 )
			{
				return -1;
			} else
			{
				return 0;
			}
		}
	}




	public Anim setOffs( float i , float j )
	{
		offs.set( i , j );
		return this;
	}

	public Anim setOffs(vector vector) {
		if(vector == null)
			offs.set(0,0);
		else
			offs=vector;
		return this;
	}
	public vector getOffs() {
		return offs;
	}


	public Paint getPaint() {
		return paint;
	}

	public void setPaint(Paint paint) {
		this.paint = paint;
	}



	@Nullable
	public vector getLoc()
	{
		return loc;
	}

	/**
	 * If loc2 is not null then this animations loc object is set to be loc2, it does not copy its value.
	 * This allows animations to follow around other objects without having to worry about moving the animation.
	 * @param loc2
	 */
	public void setLoc( @NotNull vector loc2 )
	{
		if( loc2 == null )
			loc.set( 0 , 0 );
		else
			loc = loc2;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public boolean isClearAddedAnimations() {
		return clearAddedAnimations;
	}

	public void clearAddedAnimations() {
		clearAddedAnimations = true;
	}

	public void setClearAddedAnimations(boolean clearAddedAnimations) {
		this.clearAddedAnimations = clearAddedAnimations;
	}


	public void setDrawAreaBorderWhenBuilding( RectF percArea )
	{
		area = percArea;
	}



	public void setWasDrawnThisFrame( boolean b )
	{
		wasDrawnThisFrame = b ;
	}

	public boolean wasDrawnThisFrame()
	{
		return wasDrawnThisFrame;
	}

	public boolean isVisible()
	{
		return visible;
	}

	public void setVisible( boolean b )
	{
		visible = b;
	}
	public int getSelectedBorderColor()
	{
		return borderColor;
	}




	/**
	 * Sets both scaleX and scaleY to scale
	 */
	public Anim setScale(float scale) {
		this.scaleX = scale;
		this.scaleY = scale;
		return this;
	}
	public float getScaleX() {
		return scaleX;
	}
	public void setScaleX(float scaleX) {
		this.scaleX = scaleX;
	}
	public float getScaleY() {
		return scaleY;
	}
	public void setScaleY(float scaleY) {
		this.scaleY = scaleY;
	}



	public Anim newInstance(vector loc){
		Anim a = new Anim();
		a.loc = loc;
		a.image = image;
		a.images = images;
		a.tbf = tbf;
		a.aliveTime = aliveTime;
		a.looping=looping;
		return a;
	}

	@Nullable
	public EffectsManager.Position getRequiredPosition(){
		return reqPos;
	}

	public void setRequiredPosition(EffectsManager.Position reqPos){
		this.reqPos = reqPos;
	}



	private final ArrayList<AnimatorListener> animListeners = new ArrayList<>();

	public void addAnimationListener(AnimatorListener al)		        {	synchronized( animListeners ){	animListeners.add( al );			}  	}
	public boolean removeAnimationListener(AnimatorListener al)		{	synchronized( animListeners ){	return animListeners.remove( al );	}	}










}
