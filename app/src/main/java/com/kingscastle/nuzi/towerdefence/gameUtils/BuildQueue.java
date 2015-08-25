package com.kingscastle.nuzi.towerdefence.gameUtils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;

import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.framework.Graphics;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.framework.Settings;
import com.kingscastle.nuzi.towerdefence.gameElements.Technology;
import com.kingscastle.nuzi.towerdefence.effects.animations.Bar;
import com.kingscastle.nuzi.towerdefence.effects.animations.Barrable;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.Unit;
import com.kingscastle.nuzi.towerdefence.ui.CTextView;
import com.kingscastle.nuzi.towerdefence.ui.UI;
import com.kingscastle.nuzi.towerdefence.ui.UIUtil;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class BuildQueue implements Barrable, Serializable
{
	private static final String TAG = "BuildQueue";

	private static final Paint paint = new Paint();static{
		paint.setTextSize(Rpg.getTextSize());
	}
	private static final Bitmap buildIcon = null;//Assets.loadImage(R.drawable.build_icon).getBitmap();

	private transient final ArrayList<Queueable> theQueued = new ArrayList<Queueable>();
	private transient final ArrayList<OnQueueUpdateListener> quls = new ArrayList<OnQueueUpdateListener>();

	private long startedBuilding;
	private float dpdt;
	public transient Queueable currentlyBuilding;
	private final Bar progressBar;

	private double percDone;
	private boolean paused;
	private boolean showTime = true;

	private int finishNowCount;

	private transient final CTextView timer = new CTextView(Rpg.getGame().getActivity());
	//private final TextView timer = new TextView(Rpg.getGame());
	//private final TextView timer2 = new TextView(Rpg.getGame());
	//private final TextView timer3 = new TextView(Rpg.getGame());
	//private final TextView[] tvs = { timer, timer2, timer3 };

	private transient final vector screenRel = new vector();
	private transient final Runnable buildQueueUpdater;

	private boolean showName = false;

	private ImageView icon;

	{
		final float xOffs = Rpg.fiveDp;

		icon = new ImageView(Rpg.getGame().getActivity()){
			float x,y;
			@Override
			public void setX(float x) {
				this.x=x;
			}
			@Override
			public void setY(float y) {
				this.y=y;
			}
			@Override
			protected void onDraw(Canvas canvas) {
				canvas.drawBitmap(buildIcon, x + xOffs , y, paint);
			}
		};
		//icon.setImageResource(R.drawable.build_icon);
		icon.setVisibility(View.GONE);

		//UIUtil.applyCooperBlack(tvs);
		//UIUtil.setUpForBacking( 2 , tvs );
		//UIUtil.setVisibility(View.GONE, tvs);
	}

	public BuildQueue( final vector loc2 )
	{
		//timer.setTextSize(Rpg.getTextSize());
		//timer.setMaxLines(2);
		//		UIUtil.setTextSize(20, tvs);
		//		UIUtil.setMaxLines(2 , tvs);

		final float yOffs = timer.getPaint().getFontSpacing();
		progressBar = new Bar( this , -8*Rpg.getDp() , -20*Rpg.getDp() ){
			@Override
			public void paint(Graphics g, vector v) {
			}
		};

		buildQueueUpdater = new Runnable() {
			float width;
			long nextUpdate;
			@Override
			public void run() {
				Queueable cb = currentlyBuilding;

				if( Settings.hideBuildQueue ){
					icon.setVisibility(View.GONE);
					timer.setVisibility(View.GONE);
					return;
				}

				////Log.d( "buildQueueUpdater" , "run() currentlyBuilding=" + currentlyBuilding);

				if( cb != null && getPercent() != 0 ){

					if( nextUpdate < GameTime.getTime() ){
						nextUpdate = GameTime.getTime() + 1000;


						String time = getTimeToCompletion();

						if( showName ){
							timer.setVisibility(View.VISIBLE);
							icon.setVisibility(View.INVISIBLE);
							//UIUtil.setVisibility(View.VISIBLE, tvs);

							String text = cb.getName() + "\n" + time;
							timer.setText(text);
							timer.invalidate();
							//UIUtil.setText(text, tvs);
							//timer.setText( text );

							width = timer.getPaint().measureText( cb.getName() );


							//							if( timer.getBackground() == null )
							//								timer.setBackgroundResource(R.drawable.dashed_box_small);
						}
						else{
							timer.setVisibility(View.INVISIBLE);
							//UIUtil.setVisibility(View.GONE, tvs);
							//timer.setVisibility(View.GONE);
							icon.setVisibility(View.VISIBLE);
							//timer.setText("");
							//width = -Rpg.thirtyDp;//timer.getPaint().measureText( timer.getText().toString() );

							//timer.setBackgroundResource(R.drawable.build_icon);
						}
					}
					//Log.d( "buildQueueUpdater" , "loc2=" + loc2);

					UI.get().getCoordsMapToScreen( loc2 , screenRel );
					timer.setX(screenRel.x - width/2);
					timer.setY(screenRel.y - yOffs);
					//UIUtil.setLoc( screenRel.x - width/2 , screenRel.y - yOffs , tvs );
					//UIUtil.setUpForBacking( 2 , tvs );
					//timer.bringToFront();
					//UIUtil.translate(Rpg.twoDp, tvs);
					//timer.setX( screenRel.x - width/2);
					//timer.setY( screenRel.y - yOffs);
					icon.setX( screenRel.x );
					icon.setY( screenRel.y );
				}
				else{
					timer.setVisibility(View.GONE);
					//UIUtil.setVisibility(View.GONE, tvs);
					//timer.setVisibility(View.GONE);
					icon.setVisibility(View.GONE);
				}


				////Log.d( "buildQueueUpdater" , "timer.getText() = " + timer.getText());
			}
		};
		//		progressBar.setTextPaint( orangePaint );
		//		progressBar.setBarPaint( orangePaint );
		//		progressBar.setShowValueAboveBar( true );
	}




	public Runnable getBuildQueueUpdater() {
		return buildQueueUpdater;
	}



	public boolean add( Queueable q )
	{
		synchronized( theQueued ){
			if( theQueued.add( q )){
				synchronized( quls ){
					for( OnQueueUpdateListener qul : quls )
						qul.onQueueableAdded(q);
				}
				return true;
			}
			return false;
		}
	}
	public boolean remove(Queueable q) {
		//Log.d( TAG , "remove("+q+") ");

		synchronized( theQueued ){
			if( theQueued.remove( q )){
				//Log.d( TAG , "theQueued.remove( q )");
				synchronized( quls ){
					for( OnQueueUpdateListener qul : quls )
						qul.onQueueableRemoved(q);
				}
				return true;
			}
		}
		if( currentlyBuilding == q ){
			//Log.d( TAG , "currentlyBuilding == q ");
			currentlyBuilding = null;
			synchronized( quls ){
				for( OnQueueUpdateListener qul : quls )
					qul.onQueueableRemoved(q);
			}
			return true;
		}
		return false;
	}



	public Queueable getNext()
	{
		synchronized( theQueued ){
			if( theQueued.size() == 0 )
				return null;
			else
				return theQueued.get( 0 );
		}
	}
	public Queueable removeNext()
	{
		synchronized( theQueued ){
			if( theQueued.size() == 0 )
				return null;
			else
				return theQueued.remove( 0 );
		}
	}




	public synchronized void finishAll() {
		finishNowCount = size();
	}



	public int size()
	{
		synchronized( theQueued ){
			return theQueued.size() + (currentlyBuilding != null ? 1 : 0) ;
		}
	}






	public boolean startBuildingFirst()
	{
		if( paused )
			return false;

		synchronized( theQueued ){
			if( theQueued.size() == 0 )
			{
				progressBar.setShowCurrentAndMax( false );
				return false;
			}
			else if( currentlyBuilding == null )
			{
				startedBuilding = GameTime.getTime();
				currentlyBuilding = theQueued.remove( 0 );

				dpdt = 1f / currentlyBuilding.getBuildTime();

				progressBar.setShowCurrentAndMax( true );
				return true;
			}
		}
		return false;
	}




	public synchronized boolean isThereACompletedQueueable() {
		if( currentlyBuilding == null )
			return false;

		return startedBuilding + currentlyBuilding.getBuildTime() < GameTime.getTime()
				|| Settings.instantBuild
				|| finishNowCount > 0 ;
	}




	public synchronized Queueable getCompletedQueueable()
	{
		if( paused || currentlyBuilding == null )
			return null;

		else if( startedBuilding + currentlyBuilding.getBuildTime() < GameTime.getTime() || Settings.instantBuild || finishNowCount > 0 )
		{
			finishNowCount--;
			if( finishNowCount < 0 ) finishNowCount = 0;

			Queueable q = currentlyBuilding;
			startedBuilding = 0;
			currentlyBuilding = null;
			q.queueableComplete();
			//progressBar.setShowCurrentAndMax( false );

			synchronized( quls ){
				for( OnQueueUpdateListener qul : quls )
					qul.onQueueableCompleted(q);
			}

			startBuildingFirst();
			return q;
		}
		else
			return null;
	}
	public boolean cancelCurrentlyBuilding() {
		Queueable q = currentlyBuilding;
		if( q == null )
			return false;
		currentlyBuilding = null;
		startBuildingFirst();
		return true;
	}





	@Override
	public synchronized float getPercent()
	{
		if( paused )
			return (float) percDone;

		if( currentlyBuilding == null || startedBuilding == 0 )
		{
			progressBar.setShowCurrentAndMax( false );
			return 0;
		}
		else
		{
			long dt =  GameTime.getTime() - startedBuilding;

			if( dt > currentlyBuilding.getBuildTime() )
				return 0;
			else{
				percDone = dpdt * dt;
				return (float) percDone;
			}
		}
	}




	public Bar getProgressBar(){
		return progressBar;
	}




	private static final String SLASH = "/";

	public String getFractionComplete(){
		return getValue() + SLASH + getMaxValue();
	}




	@Override
	public int getMaxValue(){
		return 100;
	}
	@Override
	/**
	 * @return If showTime=true then the remaining time in ms is returned, else the % is returned in integer format ( 46% = 46 )
	 */
	public synchronized int getValue()
	{
		if( showTime && currentlyBuilding != null )
			return (int) (currentlyBuilding.getBuildTime()*(1-getPercent()));

		return (int) ( 100*getPercent() );
	}




	@Override
	public String getTimeToCompletion(){
		int ms = getValue();
		return UIUtil.convertToTime(ms);
	}




	/**
	 * You MUST synchronize on this list...
	 * @return The actual list that is being used by other threads.
	 */
	public ArrayList<Queueable> getQueued(){
		return theQueued;
	}
	public ArrayList<Queueable> cloneTheQueued()
	{
		synchronized( theQueued ){
			ArrayList<Queueable> queue = new ArrayList<Queueable>();
			for( Queueable q : theQueued ){
				queue.add( q );
			}

			return queue;
		}
	}

	public synchronized boolean isEmpty()
	{
		if( size() == finishNowCount )
			return true;
		if( currentlyBuilding != null && finishNowCount == 0)
			return false;
		else{
			synchronized( theQueued ){
				if( theQueued.isEmpty() )
					return true;
			}
		}
		return false;
	}




	public void pause()
	{
		if( paused )
			return;

		//Log.d( "BuildQueue" , "Pausing, percDone = " + percDone );
		percDone = getPercent();
		//Log.d( "BuildQueue" , "After getting percDone, percDone = " + percDone );

		paused = true;
	}
	public void unpause()
	{
		if( !paused )
			return;

		paused = false;

		if( percDone != 0 && currentlyBuilding != null )
		{
			//Log.d( "BuildQueue" , "UnPausing, percDone = " + percDone );

			int startedAgo = (int) ( currentlyBuilding.getBuildTime() * ( percDone ) );
			//Log.d( "BuildQueue" , "startedAgo:" + startedAgo );

			startedBuilding = GameTime.getTime() - startedAgo;

			//Log.d( "BuildQueue" , "GameTime.getTime() :" + GameTime.getTime() );
			//Log.d( "BuildQueue" , "startedBuilding :" + startedBuilding );

			dpdt = 1f / currentlyBuilding.getBuildTime();
			//Log.d( "BuildQueue" , "dpdt :" + dpdt );
			progressBar.setShowCurrentAndMax( true );

			percDone = 0;
		}
	}





	public Queueable getCurrentlyBuilding() {
		return currentlyBuilding;
	}
	public synchronized void setCurrentlyBuilding(Queueable currentlyBuilding) {
		this.currentlyBuilding = currentlyBuilding;
	}




	public double getPercDone() {
		return percDone;
	}
	public void setPercDone(double percDone2) {
		percDone = percDone2;
	}









	public void initFromALoad(){
		if( currentlyBuilding != null ){
			dpdt = 1f / currentlyBuilding.getBuildTime();
			startedBuilding = GameTime.getTime();
			progressBar.setShowCurrentAndMax( true );
		}
	}



	public void clear() {
		currentlyBuilding = null;
		synchronized( theQueued ){
			theQueued.clear();
		}
		synchronized( quls ){
			quls.clear();
		}
	}




	private static final String TAGNAME = "<bq>";//<BuildQueue>";
	private static final String ENDTAGNAME = "</bq>";///BuildQueue>";
	private static final String CURRENTLYBUILDINGSTART = "<cb";//"<CurrentlyBuilding";
	private static final String ENDCURRENTLYBUILDING = "</cb>";//"</CurrentlyBuilding>";
	private static final String THEQUEUED = "<tq>";//"<TheQueued>";
	private static final String ENDTHEQUEUED = "</tq>";//"</TheQueued>";

	public void saveYourself( BufferedWriter b ) throws IOException
	{

		if( currentlyBuilding == null && theQueued.isEmpty() )
			return;

		if( currentlyBuilding == null )
		{
			currentlyBuilding = theQueued.remove( 0 );
			percDone = 0;
		}

		//////Log.d( "BuildQueue" , TAGNAME );
		b.write( TAGNAME , 0 , TAGNAME.length() );
		b.newLine();


		if( currentlyBuilding != null )
		{
			getPercent();
			String curr = CURRENTLYBUILDINGSTART + " pd=\"" + percDone + "\" bt=\""+currentlyBuilding.getBuildTime()+"\" >";

			//////Log.d( "BuildQueue" , curr );
			b.write( curr , 0 , curr.length() );
			b.newLine();

			if( currentlyBuilding instanceof Unit)
				((Unit)currentlyBuilding).saveYourself( b );
			else if( currentlyBuilding instanceof Technology)
				((Technology)currentlyBuilding).saveYourself( b );

			//////Log.d( "BuildQueue" , ENDCURRENTLYBUILDING );
			b.write( ENDCURRENTLYBUILDING , 0 , ENDCURRENTLYBUILDING.length() );
			b.newLine();
		}

		if( !theQueued.isEmpty() )
		{
			b.write( THEQUEUED , 0 , THEQUEUED.length() );
			b.newLine();

			for( Queueable q : theQueued )
				if( q instanceof Unit )
					((Unit) q ).saveYourself( b );

				else if( q instanceof Technology )
					((Technology) q ).saveYourself( b );

			b.write( ENDTHEQUEUED , 0 , ENDTHEQUEUED.length() );
			b.newLine();
		}

		//Log.d( "BuildQueue" , ENDTAGNAME );
		b.write( ENDTAGNAME , 0 , ENDTAGNAME.length()  );
		b.newLine();

	}






	public boolean isShowTime() {
		return showTime;
	}

	public void setShowTime(boolean showTime) {
		this.showTime = showTime;
	}







	public void setFinishThisOneNow(boolean finishThisOneNow) {
		if( finishThisOneNow )
			finishNowCount++;
		else
			finishNowCount = 0;
	}



	public CTextView getTimers() {

		return timer;
	}



	public boolean isShowName() {
		return showName;
	}
	public void setShowName(boolean showName) {
		this.showName = showName;
	}


	public synchronized long getSumOfBuildTimes(){
		long ms = getValue();
		synchronized( theQueued ){
			for( Queueable q : theQueued )
				ms += q.getBuildTime();
		}
		return ms;
	}




	public void nullify() {
	}




	public static abstract class OnQueueUpdateListener{
		public void onQueueableAdded(Queueable q){}
		public void onQueueableRemoved(Queueable q){}
		public void onQueueableCompleted(Queueable q){}
	}

	public boolean addQul( OnQueueUpdateListener ql ){
		synchronized( quls ){
			return quls.add( ql );
		}
	}
	public boolean removeQul( OnQueueUpdateListener ql ){
		synchronized( quls ){
			return quls.remove( ql );
		}
	}
	public void removeAll(ArrayList<OnQueueUpdateListener> quls2) {
		synchronized(quls) {
			quls.removeAll(quls2);
		}
	}






	public View getIcon() {
		return icon;
	}































}
