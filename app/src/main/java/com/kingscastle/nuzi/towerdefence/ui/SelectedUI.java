package com.kingscastle.nuzi.towerdefence.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.TowerDefenceGame;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.framework.WtfException;
import com.kingscastle.nuzi.towerdefence.gameElements.CD;
import com.kingscastle.nuzi.towerdefence.gameElements.GameElement;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Building;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameUtils.CoordConverter;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.level.GridUtil;
import com.kingscastle.nuzi.towerdefence.teams.Team;
import com.kingscastle.nuzi.towerdefence.ui.Mover.Moveable;
import com.kingscastle.nuzi.towerdefence.ui.buttons.SButton;

import java.util.ArrayList;
import java.util.List;


public class SelectedUI
{
	private static final String TAG = "SelectedUI";
	//	private static final SelectedUI singularity;


	private GameElement selectedUnit;


	private  ArrayList<? extends GameElement> ges;

	public View selectedUIView;


	private String buttonsId;


	private TextView selTitle,selTitle2,selTitle3;

	private TextView selectedsHealthView;
	private final vector screenRel = new vector();
	private final vector selTitleOffs = new vector();


	private final RectF infoButtonArea = new RectF();
	private final RectF unselectButtonArea = new RectF();
	private final RectF optionScrollerArea = new RectF();
	private final RectF buildArea = new RectF();
	private final RectF cancelArea = new RectF();


	private final UI ui;
	private final MM mm;

	final RushBuilding rusher;
	final InfoDisplay infDisplay;

	protected SelectedUI( UI ui ){
		this.ui = ui;
		mm = ui.getMM();
		rusher = new RushBuilding(this);
		infDisplay = new InfoDisplay(ui);
	}



	public void runOnUIThread()
	{
		infDisplay.runOnUIThread();

		GameElement selected = selectedUnit;
		TextView selTitle = this.selTitle;
		TextView selTitle2 = this.selTitle2;
		TextView selTitle3 = this.selTitle3;
		//ArrayList<? extends GameElement> ges = SelectedUI.ges;

		//TextView selectedsHealthView = SelectedUI.selectedsHealthView;


		if( selected != null && selTitle != null && selTitle2 != null && selTitle3 != null )
		{
			screenRel.set( selected.loc );
			ui.getCoordsMapToScreen( screenRel , screenRel );
			selTitle.setX(screenRel.x+selTitleOffs.x);
			selTitle.setY(screenRel.y+selTitleOffs.y);
			selTitle2.setX(screenRel.x+selTitleOffs.x+2);
			selTitle2.setY(screenRel.y+selTitleOffs.y+2);
			selTitle3.setX(screenRel.x+selTitleOffs.x-2);
			selTitle3.setY(screenRel.y+selTitleOffs.y-2);

			//Log.d( TAG , "selTitle.setLoc( " + screenRel + " selTitle.getVisibility() =" + selTitle.getVisibility() + "selTitle.getText()=" + selTitle.getText());

			selTitle.requestLayout();
			selTitle.invalidate();
			selTitle2.requestLayout();
			selTitle2.invalidate();
			selTitle3.requestLayout();
			selTitle3.invalidate();

			//			selectedsHealthView.setX(screenRel.x+selTitleOffs.x);
			//			selectedsHealthView.setY(screenRel.y+selTitleOffs.y+selTitle.getPaint().getFontSpacing());
			//			////Log.d( TAG , "selTitle.setLoc( " + screenRel + " selTitle.getVisibility() =" + selTitle.getVisibility() + "selTitle.getText()=" + selTitle.getText());
			//			selectedsHealthView.requestLayout();
			//			selectedsHealthView.invalidate();


			//			if( selected instanceof LivingThing )
			//			{
			//				LivingThing selLt = (LivingThing) selected;
			//				LivingQualities lq = selLt.lq;
			//				selectedsHealthView.setText( HP + lq.getHealth() + "/" + lq.getFullHealth() + " hp");
			//			}
			//			else if( selected instanceof Workable )
			//			{
			//				Workable w = (Workable) selected;
			//				if( selectedsHealthView != null )
			//					selectedsHealthView.setText( w.getResourceType() + ": " + w.getRemainingResources() + "/" + w.getMaxResources()) ;
			//			}
		}
	}



	public void refreshUI() {
		//Log.d( TAG , "refreshUI()");

		GameElement ge = selectedUnit;
		if( ge != null ){
			if( ge instanceof LivingThing && infDisplay.isInfoDisplayOpen() ){
				//LivingThing sel = (LivingThing) selectedUnit;
				//Log.d( TAG , "ge instanceof LivingThing && StatsDisplay2.isStatsDisplayOpen()");
				infDisplay.hide();
				infDisplay.showInfoDisplay();

			}else if( ge instanceof Building){
				//Log.d( TAG , "ge instanceof Building");

				Building b = (Building) ge;
				//			if( b.getBuildQueue() == QueueDisplay.getBuildQueue() )
				//				QueueDisplay.displayQueue( Rpg.getGame() , b.getBuildQueue() );
				//TrainSoldiers.refresh( b );

				ui.bo.refresh( b );
				rusher.showFinishNowButtonIfNeededTSafe(ge);
			}
			else
				setSelected( selectedUnit );
			return;
		}

		ArrayList<? extends GameElement> ges = this.ges;
		if( ges != null ){
			ui.bo.refresh( ges );
		}
	}




	public GameElement getSelectedUnit(){
		return selectedUnit;
	}



	public synchronized void setSelected( final GameElement selGe  )
	{

//		if( selGe != null )
//			setSelectedThings( null );

		if( selGe != null && !(selGe instanceof Building) )
			throw new IllegalArgumentException("Can only select buildings");

		////Log.d( TAG , "setSelected selectedUnit=" + selGe );
		boolean somethingWasSelected = false;

		if( selectedUnit != null ){
			somethingWasSelected = true;
			selectedUnit.setSelected( false );
		}



		if( selGe != null )
		{
			selGe.setSelected( true );
			if( somethingWasSelected )
				hide();

			openSelectedUIView( selGe , null );


			if( selGe instanceof LivingThing )
			{

				LivingThing lt = (LivingThing) selGe;
				lt.setSelectedColor( Color.YELLOW );

			}

			if( selGe instanceof Building ){
				Building b = (Building) selGe;

				ui.bo.showBuildingOptionsScroller( b );


				//Create building mover
				//You may not want to create a mover if you don't the building to be moveable
				//ui.setMover(createMover(mm, b, mm.getCD(), ui.getCc()));

					//ui.setMover(new Mover( mm.getCD(), ui.getCc() , b , tcLoc , (float) Math.pow((300+t.getTcLevel()*50)*Rpg.getDp(), 2)  , t ));
					//..ui.setBuildingMover(new BuildingMover( mm.getCD(), ui.getCc() , b , tcLoc , (float) Math.pow((300+t.getTcLevel()*50)*Rpg.getDp(), 2)  , t ));

			}

			//			if( selGe instanceof Trap ){
			//				Trap trap = (Trap) selGe;
			//				//Create mover
			//
			//				MM mm = MM.get();
			//				if( mm != null )
			//					ui.setMover( createMover( mm , trap , mm.getCD() , ui.getCc() ) );
			//			}
		}
		else
		{
			if( somethingWasSelected ){
				UIView uiView = ui.uiView;

				hide();
			}
			//ui.setBuildingMover( null );
			ui.setMover( null );
		}

		selectedUnit = selGe;

	}

/* FIXME Not used in tower defence
	private Mover createMover( MM mm , final Building b , final CD cd , CoordConverter cc ) {
		final Team t = mm.getTeam( b.getTeamName() );
		Vector tcLoc;
		TownCenter tc = t.getTownCenter();
		if( tc != null )
			tcLoc = tc.loc;
		else{
			Level lvl = mm.getLevel();
			tcLoc = new Vector( lvl.getLevelWidthInPx()/2 , lvl.getLevelHeightInPx()/2 );
		}


		Moveable m = new Moveable(){
			@Override
			public RectF getArea() {
				return b.area;
			}

			@Override
			public Vector getLoc() {
				return b.loc;
			}

			@Override
			public void setSelected(boolean selected) {
				b.setSelected(selected);
			}

			@Override
			public void updateArea() {
				b.updateArea();
			}

			@Override
			public void setColorFilter(ColorMatrixColorFilter cmcf) {
				BuildingAnim bAnim = b.getBuildingAnim();
				if( bAnim != null )
					bAnim.getPaint().setColorFilter(cmcf);
			}

			@Override
			public RectF getPerceivedArea() {
				return b.getPerceivedArea();
			}

			@Override
			public void onMovedSuccessfully() {
				t.onBuildingMoved(b);
			}

			@Override
			public boolean checkPlaceable() {
				return cd.checkPlaceable2(getArea(), false);
			}

		};
		return createMover(  tcLoc , t.getTcLevel() , m , cd , cc, mm.getGridUtil() );
		//return new Mover( cd , cc , m , tcLoc , (float) Math.pow((300+t.getTcLevel()*50)*Rpg.getDp(), 2)  );
	}*/

	//	private Mover createMover(MM mm, final Trap trap, CD cd, CoordConverter cc) {
	//		final Team t = mm.getTeam( trap.getTeamName() );
	//		Vector tcLoc;
	//		TownCenter tc = t.getTownCenter();
	//		if( tc != null )
	//			tcLoc = tc.loc;
	//		else{
	//			Level lvl = mm.getLevel();
	//			tcLoc = new Vector( lvl.getLevelWidthInPx()/2 , lvl.getLevelHeightInPx()/2 );
	//		}
	//
	//		return createMover( tcLoc , t.getTcLevel() , trap , cd , cc );
	//	}
	/**
	 * Overrideable
	 */
	protected Mover createMover(vector tcLoc , int tcLevel , Moveable m, CD cd, CoordConverter cc, GridUtil gUtil) {
		return new Mover( cd , cc , gUtil , m , tcLoc , Float.MAX_VALUE  );
	}






	/*public synchronized void setSelectedThings( ArrayList<? extends GameElement> ges_  )
	{
		if( ges_ != null )
			setSelected( null );

		boolean somethingWasSelected = false;

		if( ges != null )
			somethingWasSelected = true;

		ui.setMover(null);


		ges = ges_;


		if( ges_ != null && !ges_.isEmpty() )
		{
			openSelectedUIView(null, ges_);
			GameElement firstGe = ges_.get(0);
			if( firstGe instanceof Unit ){
				ArrayList<? extends LivingThing> lts = GameElementUtil.getUnits(ges_);
				if( lts != null && !lts.isEmpty() ){
					UnitOptions uo = ui.uo;
					if( uo != null )
						uo.showScroller( lts );
				}
			}
			else if( firstGe instanceof Wall && !Settings.challengeLevel ){
				ArrayList<Wall> walls = WallWorker.getAllWalls(ges_);
				ui.setMover(createMover( MM.get() , walls , ui.getCD() , ui.getCc() ));
				ui.bo.showBuildingOptionsScroller( ges_ );
			}
			else if( firstGe instanceof PendingBuilding && Buildings.isInstanceOf((PendingBuilding) firstGe, Buildings.Wall)){
				rusher.showFinishNowButtonIfNeeded(ges_);
				ui.bo.showBuildingOptionsScroller( ges_ );
			}
		}
		else if( somethingWasSelected ){
			UIView uiView = ui.uiView;
			if( uiView != null )
				uiView.showTroopSelectorButton();
			hide();
		}
	}*/

	/*
	@SuppressWarnings("unchecked")
	private Mover createMover( final MM mm , final ArrayList<? extends Building> walls_ , final CD cd , CoordConverter cc ) {
		if( walls_ == null || walls_.isEmpty() )
			return null;


		Object o = walls_.clone();
		ArrayList<Building> walls2=null;
		if( o instanceof ArrayList<?>)
			walls2 = (ArrayList<Building>) o;
		if( walls2 == null ) return null;
		final ArrayList<Building> walls = walls2;


		//For collision detecting
		final RectF area = new RectF();
		area.set( walls.get(0).area );
		for( Building b : walls )
			area.union(b.area);
		final RectF percArea = new RectF(area);
		percArea.offsetTo(-percArea.width()/2, -percArea.height()/2);


		final Vector avgLoc = GameElementUtil.getAverageLoc(walls);
		final ArrayList<Vector> offsets = new ArrayList<Vector>();
		for( Building b : walls )
			offsets.add( new Vector( avgLoc , b.loc ) );




		Moveable m = new Mover.Moveable(){
			@Override
			public RectF getArea() {
				return area;
			}

			@Override
			public Vector getLoc() {
				return avgLoc;
			}

			@Override
			public void setSelected(boolean selected) {
				for( Building b : walls )
					b.setSelected(selected);
			}

			@Override
			public void updateArea() {
				area.offsetTo(avgLoc.x, avgLoc.y);
				area.offset(-area.width()/2, -area.height()/2);

				for( int i = 0 ; i < walls.size() ; ++i ){
					Building b = walls.get(i);
					b.loc.set( avgLoc ).add( offsets.get(i) );
					b.updateArea();
					mm.getGridUtil().setProperlyOnGrid(b.area, Rpg.gridSize);
					GridUtil.getLocFromArea(b.area, b.getPerceivedArea(), b.loc);
				}
			}

			@Override
			public void setColorFilter(ColorMatrixColorFilter cmcf) {
				for( Building b : walls ){
					BuildingAnim bAnim = b.getBuildingAnim();
					if( bAnim != null )
						bAnim.getPaint().setColorFilter(cmcf);
				}
			}

			@Override
			public RectF getPerceivedArea() {
				return percArea;
			}

			@Override
			public void onMovedSuccessfully() {
				for( Building b : walls )
					t.onBuildingMoved(b);
				//				for( int i = 0 ; i < walls.size() ; ++i ){
				//					Building b = walls.get(i);
				//					b.loc.set( avgLoc ).add( offsets.get(i) );
				//					b.updateArea();
				//					t.onBuildingMoved(walls.get(0));
				//				}
			}

			@Override
			public boolean checkPlaceable() {
				boolean placeable = true;
				for( int i = 0 ; i < walls.size() ; ++i )
					placeable &= cd.checkPlaceable2(walls.get(i).getArea(), false, true);

				return placeable;
			}
		};

		return new Mover( cd , cc , mm.getGridUtil(), m , tcLoc , (float) Math.pow((300+t.getTcLevel()*50)*Rpg.getDp(), 2)  );
	}
*/




	private void openSelectedUIView(final GameElement selUnit , final ArrayList<? extends GameElement> selecteds ) {

		if( selUnit == null && (selecteds == null || selecteds.isEmpty() )) return;

		//TODO: Should probably have a better way of getting the activity
		final Activity a = Rpg.getGame().getActivity();

		a.runOnUiThread(new Runnable() {
			@Override
			public void run() {


				Log.d(TAG, "openUIView.run() selUnit=" + selUnit + " selecteds=" + selecteds);

				View v = selectedUIView;
				//Remove old selectedUIView if it is present
				if( v != null ){
					ViewGroup vg = (ViewGroup) v.getParent();
					if( vg != null )
						vg.removeView(v);
				}

				//Inflate a new one
				selectedUIView = a.getLayoutInflater().inflate(R.layout.selected_ui, null);

				a.addContentView(selectedUIView, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
				UIView uiView = ui.uiView;
				if( uiView != null )
					uiView.bringUIViewToFront();

				final ScrollView selOptionsScroller = (ScrollView) selectedUIView.findViewById( R.id.scrollViewSelOptions );


				final ImageButton build = (ImageButton) a.findViewById(R.id.build_button);
				build.setVisibility(View.INVISIBLE);
//				build.setOnClickListener(new OnClickListener() {
//					@Override
//					public void onClick(View v) {
//					if( true ){
//						hideCancelButton();
//					}
//					}
//				});



				//More info button
				final ImageButton info = (ImageButton) selectedUIView.findViewById(R.id.buttonSelectedsInfo);
				info.setOnClickListener(openInfoView);
				if( selecteds != null || !(selUnit instanceof LivingThing) )
					info.setVisibility(View.GONE);



				//Finish now button
				// This version of Tower Defence builds towers instantly.
				final ImageButton finish = (ImageButton) selectedUIView.findViewById(R.id.buttonFinishNow);
				finish.setVisibility(View.GONE);
				/*if( selUnit instanceof Building || areBuildings( selecteds ) ){
					rusher.setHurryButtonAndSetup(finish);
					if( selUnit != null )
						rusher.showFinishNowButtonIfNeeded( selUnit );
					else
						rusher.showFinishNowButtonIfNeeded( selecteds );
				}
				else
					finish.setTranslationY(RushBuilding.FINISH_NOW_TRANSLATION_Y_OFFSCREEN);*/


				//Un-select Button
				final ImageButton closeSelDisplay = (ImageButton) selectedUIView.findViewById(R.id.imageButtonCloseSelDisplay2);
				closeSelDisplay.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						ui.selecter.clearSelection();
					}
				});


				//A button used to drag and select multiple soldiers at once
				//final ImageButton select = (ImageButton) v.findViewById(R.id.imageButtonSelectSoldiers);
				//select.setVisibility(View.GONE);
//				if( uiView != null )
//					uiView.hideTroopSelectorButton();



				//Selected's title
				selTitle = (TextView) selectedUIView.findViewById(R.id.textViewSelectedsTitle);
				selTitle2 = (TextView) selectedUIView.findViewById(R.id.textViewSelectedsTitle2);
				selTitle3 = (TextView) selectedUIView.findViewById(R.id.textViewSelectedsTitle3);
				if( selecteds != null )
					setText("",selTitle,selTitle2,selTitle3);
				else{
					UIUtil.applyCooperBlack(selTitle,selTitle2,selTitle3);
					UIUtil.setUpForBacking(2, new TextView[]{ selTitle,selTitle2,selTitle3 });
					setText(selUnit.getName(),selTitle,selTitle2,selTitle3);

					selTitle.setVisibility(View.VISIBLE);
					selTitle2.setVisibility(View.VISIBLE);
					selTitle3.setVisibility(View.VISIBLE);


					Paint tPaint = selTitle.getPaint();
					selTitleOffs.set( -tPaint.measureText(selUnit.getName())/2 , -2*tPaint.getFontSpacing() );


					ValueAnimator animation = ValueAnimator.ofFloat(0f, 1f);
					animation.addUpdateListener(new AnimatorUpdateListener() {
						@Override
						public void onAnimationUpdate(ValueAnimator animation) {
							selTitle.setAlpha((Float) animation.getAnimatedValue());
							selTitle2.setAlpha((Float) animation.getAnimatedValue());
							selTitle3.setAlpha((Float) animation.getAnimatedValue());
						}
					});
					animation.setDuration(200);
					animation.start();


					selTitle.bringToFront();
				}




				//Selected's hpDisplay, currently not used
				selectedsHealthView = (TextView) selectedUIView.findViewById(R.id.textViewHpDisplay );
				UIUtil.applyKcStyle(selectedsHealthView);
				selectedsHealthView.setVisibility(View.INVISIBLE);





				final ViewTreeObserver observer= selectedUIView.getViewTreeObserver();
				observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {
						infoButtonArea.set(info.getLeft() , info.getTop() , info.getRight() , info.getBottom() );
						unselectButtonArea.set(closeSelDisplay.getLeft() , closeSelDisplay.getTop() , closeSelDisplay.getRight() , closeSelDisplay.getBottom() );

						optionScrollerArea.set(selOptionsScroller.getLeft() , selOptionsScroller.getTop() , selOptionsScroller.getRight() , selOptionsScroller.getBottom() );

						buildArea   .set(    build.getLeft() ,     build.getTop() ,     build.getRight() ,     build.getBottom() );
						//cancelArea  .set(   cancel.getLeft() ,    cancel.getTop() ,    cancel.getRight() ,    cancel.getBottom() );



						{
							final float origY = infoButtonArea.top;
							ValueAnimator animation = ValueAnimator.ofFloat( 75*Rpg.getDp() , 0f );
							animation.addUpdateListener(new AnimatorUpdateListener() {
								@Override
								public void onAnimationUpdate(ValueAnimator animation) {
									info.setY(origY + (Float) animation.getAnimatedValue());
								}
							});
							animation.setDuration(300);
							animation.start();


							ValueAnimator alphaAnimation = ValueAnimator.ofFloat( 0f , 1f );
							alphaAnimation.addUpdateListener(new AnimatorUpdateListener() {
								@Override
								public void onAnimationUpdate(ValueAnimator animation) {
									info.setAlpha((Float) animation.getAnimatedValue());
								}
							});
							alphaAnimation.setDuration(300);
							alphaAnimation.start();
						}

						{
							final float origY = unselectButtonArea.top;
							ValueAnimator animation = ValueAnimator.ofFloat( 75*Rpg.getDp() , 0f );
							animation.addUpdateListener(new AnimatorUpdateListener() {
								@Override
								public void onAnimationUpdate(ValueAnimator animation) {
									closeSelDisplay.setY(origY + (Float) animation.getAnimatedValue());
								}
							});
							animation.setDuration(300);
							animation.start();

							ValueAnimator alphaAnimation = ValueAnimator.ofFloat( 0f , 1f );
							alphaAnimation.addUpdateListener(new AnimatorUpdateListener() {
								@Override
								public void onAnimationUpdate(ValueAnimator animation) {
									closeSelDisplay.setAlpha((Float) animation.getAnimatedValue());
								}
							});
							alphaAnimation.setDuration(300);
							alphaAnimation.start();
						}

						observer.removeGlobalOnLayoutListener(this);
					}
				});
			}
		});
	}



	protected void setText(String text, TextView... tvs) {
		for( int i = 0 ; i < tvs.length ; ++i )
			tvs[i].setText(text);
	}



	protected boolean areBuildings(ArrayList<? extends GameElement> selecteds) {
		if( selecteds == null || selecteds.isEmpty() )
			return false;
		else
			return selecteds.get(0) instanceof Building;
	}


	private final OnClickListener openInfoView = new OnClickListener() {
		@Override
		public void onClick(View v) {
			//Log.d( TAG , "openInfoView.onClick() selectedUnit=" + selectedUnit );

			final GameElement selUnit = selectedUnit;
			if( selUnit == null || !(selUnit instanceof LivingThing)) return;

			Team team = mm.getTeam( selUnit.getTeamName() );
			if( team == null && TowerDefenceGame.testingVersion )
				throw new WtfException(" MM.get().getTeam( selUnit.getTeamName() ) == null, fix getting the MM like this.");
			//selectedUIView.setVisibility(View.INVISIBLE);
			hide();
			infDisplay.showInfoDisplay( (LivingThing) selUnit , team );
		}
	};








	private boolean busy = false;

	public void displayTheseInRightScroller( final List<? extends SButton> arrayList, final String buttonsId) {
		if( arrayList == null || buttonsId == null ){
			//Log.e( TAG , "displayTheseInRightScroller( "+arrayList+" , buttonsId=" + buttonsId );
			return;
		}
		//Log.d( "RefreshUITrace" , "displayTheseInRightScroller()");

		if( busy ){
			//Log.d( "RefreshUITrace" , "but busy so returning");
			return;
		}

		this.buttonsId = buttonsId;
		final Activity a = Rpg.getGame().getActivity();


		a.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				//Log.d( TAG , "Clearing and rebuilding scroller. selectedUIView=" + selectedUIView ) ;
				View v = selectedUIView;
				if (v != null) {
					try {

						final LinearLayout scrollerLayout = (LinearLayout) v.findViewById(R.id.scrollerLayout);


						ValueAnimator animation = ValueAnimator.ofFloat(0f, 75 * Rpg.getDp());
						animation.addUpdateListener(new AnimatorUpdateListener() {
							@Override
							public void onAnimationUpdate(ValueAnimator animation) {
								////Log.d( TAG , "sl.getX()= " + scrollerLayout.getX() + " sl.getY()= " + scrollerLayout.getY() );

								scrollerLayout.setTranslationX((Float) animation.getAnimatedValue());
							}
						});
						animation.setDuration(75);

						animation.addListener(new AnimatorListenerAdapter() {
							@Override
							public void onAnimationEnd(Animator animation) {
								try {
									scrollerLayout.removeAllViews();

									LayoutParams layoutParams = new LayoutParams((int) (75 * Rpg.getDp()), (int) (75 * Rpg.getDp()));

									for (final SButton sb : arrayList) {
										if (sb.getParent() != null)
											((ViewGroup) sb.getParent()).removeView(sb);

										sb.setLayoutParams(layoutParams);
										scrollerLayout.addView(sb);
									}

									final ValueAnimator animation2 = ValueAnimator.ofFloat(75 * Rpg.getDp(), 0f);
									animation2.addUpdateListener(new AnimatorUpdateListener() {
										@Override
										public void onAnimationUpdate(ValueAnimator animation) {
											////Log.d( TAG , "sl.getX()= " + scrollerLayout.getX() + " sl.getY()= " + scrollerLayout.getY() );
											scrollerLayout.setX((Float) animation2.getAnimatedValue());
										}
									});
									animation2.setDuration(150);
									animation2.addListener(new AnimatorListenerAdapter() {
										@Override
										public void onAnimationCancel(Animator animation) {
											busy = false;
										}

										@Override
										public void onAnimationEnd(Animator animation) {
											busy = false;
										}
									});
									animation2.start();

									//								//Log.d( TAG , "scrollerLayout=" + scrollerLayout );
									//								//Log.d( TAG , "scrollerLayout.getChildCount()= " + scrollerLayout.getChildCount() );
									//								//Log.d( TAG , "scrollerLayout.getVisibility()= " + scrollerLayout.getVisibility() );
									//								//Log.d( TAG , "scrollView=" + scrollView );
									//								//Log.d( TAG , "scrollView.getChildCount()= " + scrollView.getChildCount() );
									//							//Log.d( TAG , "scrollView.getVisibility()= " + scrollView.getVisibility() );
								} catch (Exception e) {
									//Log.e( TAG , buttonsId + ":" , e );
									busy = false;
								}

							}
						});
						animation.start();
					} catch (Exception e) {
						//Log.e( TAG , buttonsId + ":" , e );
						busy = false;
					}
				}

			}

		});
	}


	public void clearScrollerButtons() {
		final Activity a = Rpg.getGame().getActivity();
		a.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				View v = selectedUIView;
				if (v != null) {
					//Log.v( TAG , "Clearing scroller." ) ;
					final LinearLayout scrollerLayout = (LinearLayout) v.findViewById(R.id.scrollerLayout);


					ValueAnimator animation = ValueAnimator.ofFloat(0f, 75 * Rpg.getDp());
					animation.addUpdateListener(new AnimatorUpdateListener() {
						@Override
						public void onAnimationUpdate(ValueAnimator animation) {
							////Log.d( TAG , "sl.getX()= " + scrollerLayout.getX() + " sl.getY()= " + scrollerLayout.getY() );
							scrollerLayout.setX((Float) animation.getAnimatedValue());
						}
					});
					animation.setDuration(75);
					animation.addListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							scrollerLayout.removeAllViews();
						}
					});
					animation.start();
				}
			}
		});
	}

	public void hideMyScrollerButtons( String tag2) {
		if( tag2.equals(buttonsId) )
			clearScrollerButtons();
	}






	public String getButtonsId() {
		return buttonsId;
	}

	public void setButtonsId(String buttonsId) {
		this.buttonsId = buttonsId;
	}






	public void showBuildButton() {
		final Activity a = Rpg.getGame().getActivity();
		a.runOnUiThread(new Runnable() {
			@Override
			public void run() {

				final ImageButton build = (ImageButton) a.findViewById(R.id.build_now_button);
				if (build.getVisibility() == View.VISIBLE)
					return;
				build.setVisibility(View.VISIBLE);

				ValueAnimator animation2 = ValueAnimator.ofFloat(build.getHeight(), 0f);
				animation2.addUpdateListener(new AnimatorUpdateListener() {
					@Override
					public void onAnimationUpdate(ValueAnimator animation) {
						build.setTranslationY((Float) animation.getAnimatedValue());
					}
				});
				animation2.setDuration(75);
				animation2.start();

				/* FIXME Not how this is implemented in tower defence
				final View v = selectedUIView;
				if (v != null) {
					//Log.d( TAG , "Showing build button button" ) ;

					final ImageButton cancel = (ImageButton) a.findViewById(R.id.imageButtonCancelButton);
					cancel.setVisibility(View.VISIBLE);


					final ImageButton build = (ImageButton) v.findViewById(R.id.imageButtonBuildButton);
					if (build.getVisibility() == View.VISIBLE)
						return;
					build.setVisibility(View.VISIBLE);


					ValueAnimator animation2 = ValueAnimator.ofFloat(cancel.getHeight(), 0f);
					animation2.addUpdateListener(new AnimatorUpdateListener() {
						@Override
						public void onAnimationUpdate(ValueAnimator animation) {
							cancel.setTranslationY((Float) animation.getAnimatedValue());
							build.setTranslationY((Float) animation.getAnimatedValue());
						}
					});
					animation2.setDuration(150);
					animation2.start();

				} else {
					//Log.d( TAG , "selectedUIView == null" ) ;
				}*/
			}
		});
	}

	public void showCancelButton() {
		Rpg.getGame().getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				View v = selectedUIView;
				if (v != null) {
					//Log.v( TAG , "Showing cancel button" ) ;

					final ImageButton cancel = (ImageButton) v.findViewById(R.id.imageButtonCancelButton);
					cancel.setVisibility(View.VISIBLE);


					ValueAnimator animation2 = ValueAnimator.ofFloat(cancel.getHeight(), 0f);
					animation2.addUpdateListener(new AnimatorUpdateListener() {
						@Override
						public void onAnimationUpdate(ValueAnimator animation) {
							cancel.setTranslationY((Float) animation.getAnimatedValue());
						}
					});
					animation2.setDuration(75);
					animation2.start();
				}
			}
		});
	}

	public void hideCancelButton() {
		Rpg.getGame().getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				View v = selectedUIView;
				if (v != null) {
					//Log.v( TAG , "Hiding cancel button" ) ;

					final ImageButton cancel = (ImageButton) v.findViewById(R.id.imageButtonCancelButton);

					ValueAnimator animation = ValueAnimator.ofFloat(0f, cancelArea.height() + 15 * Rpg.getDp());
					animation.addUpdateListener(new AnimatorUpdateListener() {
						@Override
						public void onAnimationUpdate(ValueAnimator animation) {
							cancel.setTranslationY((Float) animation.getAnimatedValue());
						}
					});
					animation.setDuration(75);
					animation.start();
				}
			}
		});
	}





	public static void updateFlagLoc( LivingThing selectedUnit )
	{
	}

	public static void updateFlagLoc(ArrayList<LivingThing> selectedUnits)
	{
	}



	public InfoDisplay getInfDisplay() {
		return infDisplay;
	}



	public void clearSelections()
	{
		//Log.d( TAG , "clearSelections() ges=" + ges + " selectedUnit=" + selectedUnit );
		setSelected(null);
		//setSelectedThings( null );
	}




	public void reset() {
		clearSelections();
		infDisplay.reset();
	}


	public void hide() {
		if( Rpg.getGame().uiThreadName.equals(Thread.currentThread().getName()) ){
			//Log.d(TAG , "Synchronious hiding of UI!");
			hide.run();
		}else{
			//Log.d(TAG , "ASynch hiding of UI!");
			Rpg.getGame().getActivity().runOnUiThread(hide);
		}
	}





	private final Runnable hide = new Runnable(){
		@Override
		public void run() {
			final View v = selectedUIView;
			if( v != null ){
				clearScrollerButtons();
				rusher.onSelUIHiden();

				{
					final ImageButton info = (ImageButton) selectedUIView.findViewById(R.id.buttonSelectedsInfo);
					if( info.getVisibility() == View.VISIBLE )
					{

						ValueAnimator animation = ValueAnimator.ofFloat( 0f ,infoButtonArea.height()+Rpg.tenDp   );
						animation.addUpdateListener(new AnimatorUpdateListener() {
							@Override
							public void onAnimationUpdate(ValueAnimator animation) {
								info.setTranslationY( (Float) animation.getAnimatedValue() );
							}
						});
						animation.setDuration(75);
						animation.start();
					}
				}



				{
					final ImageButton finish = (ImageButton) selectedUIView.findViewById(R.id.buttonFinishNow);

					if( finish.getTranslationY() == 0f )
					{
						ValueAnimator animation = ValueAnimator.ofFloat( 0f ,infoButtonArea.height()+Rpg.tenDp   );
						animation.addUpdateListener(new AnimatorUpdateListener() {
							@Override
							public void onAnimationUpdate(ValueAnimator animation) {
								finish.setTranslationY((Float) animation.getAnimatedValue());
							}
						});
						animation.setDuration(75);
						animation.start();
					}
				}



				{
					final ImageButton closeSelDisplay = (ImageButton) selectedUIView.findViewById(R.id.imageButtonCloseSelDisplay2);
					if( closeSelDisplay.getVisibility() == View.VISIBLE )
					{
						ValueAnimator animation = ValueAnimator.ofFloat( 0f , infoButtonArea.height() +Rpg.tenDp  );
						animation.addUpdateListener(new AnimatorUpdateListener() {
							@Override
							public void onAnimationUpdate(ValueAnimator animation) {
								closeSelDisplay.setTranslationY( (Float) animation.getAnimatedValue());
							}
						});
						animation.setDuration(75);
						animation.addListener(new AnimatorListenerAdapter() {
							@Override
							public void onAnimationEnd(Animator animation) {
								//Log.d(TAG, "SelectedUI should all be gone now");
								//closeSelDisplay.setVisibility(View.GONE);
								ViewGroup vg = (ViewGroup) v.getParent();
								if (vg != null)
									vg.removeView(v);
								//v.setVisibility(View.GONE);
							}
						});
						animation.start();
					}
				}

				{
					final ImageButton buildButton = (ImageButton) Rpg.getGame().getActivity().findViewById(R.id.build_button);
					buildButton.setVisibility(View.VISIBLE);

						ValueAnimator animation = ValueAnimator.ofFloat( infoButtonArea.height() +Rpg.tenDp , 0f );
						animation.addUpdateListener(new AnimatorUpdateListener() {
							@Override
							public void onAnimationUpdate(ValueAnimator animation) {
								buildButton.setTranslationY( (Float) animation.getAnimatedValue());
							}
						});
						animation.setDuration(75);
						animation.addListener(new AnimatorListenerAdapter() {
							@Override
							public void onAnimationEnd(Animator animation) {
								//Log.d(TAG, "SelectedUI should all be gone now");
								//closeSelDisplay.setVisibility(View.GONE);
								ViewGroup vg = (ViewGroup) v.getParent();
								if( vg != null )
									vg.removeView( v );
								//v.setVisibility(View.GONE);
							}
						});
						animation.start();

				}

			}
		}
	};

	public void disappear() {
		if( Rpg.getGame().uiThreadName.equals(Thread.currentThread().getName()) ){
			disappear.run();
		}else{
			Rpg.getGame().getActivity().runOnUiThread(disappear);
		}
	}

	private final Runnable disappear = new Runnable(){
		@Override
		public void run() {
			final View v = selectedUIView;
			if( v != null ){
				clearScrollerButtons();

				{
					final ImageButton info = (ImageButton) selectedUIView.findViewById(R.id.buttonSelectedsInfo);
					if( info.getVisibility() == View.VISIBLE )
					{
						final float origY = infoButtonArea.top;
						ValueAnimator animation = ValueAnimator.ofFloat( 0f ,infoButtonArea.height()+Rpg.tenDp   );
						animation.addUpdateListener(new AnimatorUpdateListener() {
							@Override
							public void onAnimationUpdate(ValueAnimator animation) {
								info.setY(origY + (Float) animation.getAnimatedValue());
							}
						});
						animation.setDuration(75);
						animation.start();
					}
				}



				{
					final ImageButton finish = (ImageButton) selectedUIView.findViewById(R.id.buttonFinishNow);
					if( finish.getVisibility() == View.VISIBLE )
					{
						final float origY = infoButtonArea.top;
						ValueAnimator animation = ValueAnimator.ofFloat( 0f ,infoButtonArea.height()+Rpg.tenDp   );
						animation.addUpdateListener(new AnimatorUpdateListener() {
							@Override
							public void onAnimationUpdate(ValueAnimator animation) {
								finish.setY(origY + (Float) animation.getAnimatedValue());
							}
						});
						animation.setDuration(75);
						animation.start();
					}
				}



				{
					final ImageButton closeSelDisplay = (ImageButton) selectedUIView.findViewById(R.id.imageButtonCloseSelDisplay2);
					if( closeSelDisplay.getVisibility() == View.VISIBLE )
					{
						final float origY = unselectButtonArea.top;
						ValueAnimator animation = ValueAnimator.ofFloat( 0f ,infoButtonArea.height() +Rpg.tenDp  );
						animation.addUpdateListener(new AnimatorUpdateListener() {
							@Override
							public void onAnimationUpdate(ValueAnimator animation) {
								closeSelDisplay.setY(origY + (Float) animation.getAnimatedValue());
							}
						});
						animation.setDuration(75);
						animation.start();
					}
				}
			}
		}
	};


















}
