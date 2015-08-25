package com.kingscastle.nuzi.towerdefence.ui;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.kingscastle.nuzi.towerdefence.TowerDefenceGame;
import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.framework.Graphics;
import com.kingscastle.nuzi.towerdefence.framework.Input.TouchEvent;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.framework.Settings;
import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.gameElements.CD;
import com.kingscastle.nuzi.towerdefence.gameElements.GameElement;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.AttackingBuilding;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Barracks;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Building;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameUtils.CoordConverter;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.level.Level;
import com.kingscastle.nuzi.towerdefence.level.Level.GameState;
import com.kingscastle.nuzi.towerdefence.ui.buttons.Zoomer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * This class in the main UI class, it delegates Touch Events to other classes for processing
 */
public class UI implements CoordConverter{
	private static final String TAG = "UI";
	public static boolean showDebugMessages = false;


	private TowerDefenceGame tdg;
	private static UI ui;
	private final UIThread uiThread;
	private final Level level;

	private GameState state;

	private int width, height;

	private final int heightDiv2;
	private float textSize;
	private int frameCount = 0;
	private long lastCalc = -1;

	private Paint whiteRight;

	private int pointerId;

	private int currentX, currentY;

	//	private Scroller rightScroller;
	//	private Scroller leftScroller;
	//
	//	private AndroidButton bottomLeftButton;
	//	private AndroidButton bottomRightButton;
	//	private AndroidButton topRightButton;

	private boolean troopSelectorEnabled = true;

	private final RectF onScreenArea;
	private final RectF stillDrawArea;

	public final SelectedUI selUI;
	public final BuildingOptions bo;
	public final Selecter selecter;
	public final BuildingBuilder bb;
	public final UIUpdater uiUpdater;
	private final TroopDeployLocPlacer tdlp;
	private final TowerController towerController;
	private final TapChecker tapChecker;
	private final EffectPlacer effectPlacer;
	final SelectedUnits selectedThings;

	UIView uiView;
	BuildingMover bMover;
	private Mover mover;
	private final vector temp = new vector();

	private final List<TouchEventAnalyzer> teas = new ArrayList<>();


	public UI( TowerDefenceGame tdg , Level level_) {
		Log.e(TAG, "UI.constructor()");
		this.tdg = tdg;
		level = level_;
		uiThread = new UIThread();
		ui = this;


		onScreenArea = new RectF();
		stillDrawArea = new RectF();

		MM mm = level.getMM();

		//Order of these is important, you must create the SelectedUI b4 passing it to BuildingOptions
		selUI      = getNewSelectedUI();//new SelectedUI( this );
		bo         = getNewBuildingOptions(selUI, level_);//new BuildingOptions( tdlp , selUI , this , level_ );

		selecter   = new Selecter( this );
		bb         = new BuildingBuilder( this , selUI, selecter, selUI.infDisplay );
		uiUpdater  = new UIUpdater(selUI, selecter);
		tdlp = new TroopDeployLocPlacer(ui.getCc(),mm);
		towerController = new TowerController(ui.getCc());
		tapChecker = new TapChecker(ui, ui.getCc());
		effectPlacer = new EffectPlacer(mm.getEm(),ui.getCc());

		selectedThings = new SelectedUnits();

		teas.addAll(Arrays.asList(tapChecker/*,towerController*/,tdlp,bb,effectPlacer));

		showUIView(tdg.getActivity(), level_, ui);
		//UIView.showUIView( tdg , level , this );


		Resources res = tdg.getActivity().getResources();
		textSize = (int) res.getDimension(R.dimen.game_info);

		width = tdg.getGraphics().getWidth();
		height = tdg.getGraphics().getHeight();
		heightDiv2 = tdg.getGraphics().getHeightDiv2();

		definePaints();

		ui = this;
	}


	/**
	 * Loops through the touchevents queued and processes them
	 */
	public void update() {
		List<TouchEvent> touchEvents = tdg.getInput().getTouchEvents();
		act();
		//Log.d(TAG, "UI.update() touchEvents.size()=" + touchEvents.size());
		for (int i = 0; i < touchEvents.size(); ++i) {
			TouchEvent event = touchEvents.get(i);
			analyzeTouchEvent(event);
			if (event.type == TouchEvent.TOUCH_UP) {
				pointerLeftScreen(event.pointer);
			}
		}
	}

	/**
	 * Analyze an individual touch event.
	 */
	void analyzeTouchEvent(TouchEvent event) {
		GameState state = tdg.getState();
		//Log.d(TAG, "UI.analyzeTouchEvent() : " + event.x + ", " + event.y);

		if( showDebugMessages && state != GameState.InGamePlay ){
			Log.e(TAG, state + " != GameState.InGamePlay");
		}

		if( state == GameState.InGamePlay ) {
			if (event.type == TouchEvent.TOUCH_UP && event.pointer == pointerId) {
				pointerId = -1;
				currentX = -1;
				currentY = -1;
				//Log.v(TAG, "");
			}

			boolean eventWasUsed;

			for( TouchEventAnalyzer tea : teas ){
				eventWasUsed = tea.analyzeTouchEvent(event);
				if (eventWasUsed) {
					checkPointer(event);
					if( showDebugMessages ) {
						Log.d(TAG,tea.getClass().getSimpleName() + " used up touch event");
					}
					return;
				}
			}

			if (event.type == TouchEvent.TOUCH_DOWN) {

				if (pointerId == -1) {
					currentX = event.x;
					currentY = event.y;
					pointerId = event.pointer;
				}
                if( bb.getPendingBuilding() == null ) {
                    temp.set(event.x, event.y);
                    getCc().getCoordsScreenToMap(temp, temp);

                    GameElement ge = getCD().checkPlaceableOrTarget(temp);
                    //Log.d(TAG, "Found "+ge+" to select at " + temp);

                    if (ge != null && ge instanceof Building && !((Building) ge).isStunned()) {
                        selecter.setSelected(ge);
                        //setSelectedBuilding((Building) ge);
                    }
                }

			} else if (event.type == TouchEvent.TOUCH_DRAGGED) {
				//Slide the background if nothing else used up the touch event
				if (currentX != 0 && currentY != 0) {
					if (event.pointer == pointerId) {

						if( showDebugMessages ){
							Log.d(TAG, "event.pointer == pointerId sliding background.");
						}

						int x2 = event.x;
						int y2 = event.y;

						if (x2 != 0 || y2 != 0) {
							if( showDebugMessages ){
								Log.d(TAG, "Scrolling map by: " + x2 +"," + y2);
							}
							Level level = tdg.getLevel();
							if( level != null )
								level.getBackground().scrollBy(currentX - x2, currentY - y2);

							currentX = x2;
							currentY = y2;
						}
					}
				}
			} else if (event.type == TouchEvent.TOUCH_UP) {
			}

		} else if (state == GameState.GameOver) {
		}

	}

	public void paint() {
		Graphics g = tdg.getGraphics();

		if (width == 0 || height == 0) {
			width = g.getWidth();
			height = g.getHeight();
			textSize = Rpg.getTextSize();
		}

		state = tdg.getState();

		if (state == GameState.GameOver) {
			InfoMessage.getInstance().runOnUIThread();
			return;
		}
		paintUIElements(g , level );
	}


	public void paintUIElements(Graphics g , CoordConverter cc ) {

		selUI.runOnUIThread();

		tdlp.paint(g);

		++frameCount;

		if(lastCalc == -1)
			lastCalc = GameTime.getTime();

		if( Settings.showFps )
			if (GameTime.getTime() - lastCalc > 1000) {
				if (TowerDefenceGame.testingVersion)
					g.drawString(frameCount + "", width, heightDiv2, whiteRight);
				frameCount = 0;
				lastCalc = GameTime.getTime();
			}
	}




	/**
	 * For overriding to make a custom selectedUI
	 */
	protected SelectedUI getNewSelectedUI() {
		return new SelectedUI( this );
	}


	/**
	 * For overriding to make a custom selectedUI
	 */
	protected BuildingOptions getNewBuildingOptions(SelectedUI selUI2, Level level_) {
		return new BuildingOptions( Rpg.getGame(), selUI , this , level_ );
	}

	public void addTappable(GameElement ge, Runnable callback) {
		tapChecker.addTappable(ge, callback);
	}

	/**
	 * Safe to call from outside the UI thread
	 * @param a
	 */
	public void showUIView( final Activity a, final Level level , final UI ui ){
		//UIView.showUIView(a, level, ui);
	}


	public static UI get() {
		return ui;
	}


	void act() {

	}


	@Override
	public vector getCoordsMapToScreen(vector v, vector intoThisVector){
		return getCoordsMapToScreen( v.x , v.y , intoThisVector );
	}

	@Override
	public vector getCoordsMapToScreen(float x, float y, vector intoThisVector){
		intoThisVector.set((x - getCenteredOn().x)* Zoomer.getxScale() + Rpg.getWidthDiv2(), (y - getCenteredOn().y)*Zoomer.getyScale() + Rpg.getHeightDiv2());
		return intoThisVector;
	}

//	public Vector getCoordsMapToScreen(float x, float y )
//	{
//		return null;//this.getCoordsMapToScreen( x , y , new Vector() );
//	}



	@Override
	public vector getCoordsScreenToMap(float x, float y, vector intoThisVector ){
		intoThisVector.set(x - (Rpg.getWidthDiv2()/Zoomer.getxScale()) + getCenteredOn().x, (y - Rpg.getHeightDiv2()/Zoomer.getyScale()) + getCenteredOn().y );
		return intoThisVector;
	}

//	public Vector getCoordsScreenToMap(float x, float y)
//	{
//		return this.getCoordsScreenToMap( x , y , new Vector() );
//	}

	@Override
	public vector getCoordsScreenToMap(vector v, vector intoThis) {
		return getCoordsScreenToMap(v.x , v.y , intoThis );
	}




	private void checkPointer(TouchEvent event) {
		if (event.pointer == pointerId) {
			currentX = 0;
			currentY = 0;
		}
	}

	private void pointerLeftScreen(int pointer) {
		bb.pointerLeftScreen(pointer);
	}


	private void definePaints() {
		whiteRight = new Paint();
		whiteRight.setTextSize(textSize);
		whiteRight.setTextAlign(Paint.Align.RIGHT);
		whiteRight.setAntiAlias(true);
		whiteRight.setColor(Color.WHITE);
	}




	public void reset() {
		reset(Rpg.getGame());
	}

	public void reset(TowerDefenceGame game){
		this.tdg = game;

		bb.resetScroller();
		bb.clearScrollersButtons();
		bb.setPendingBuilding(null);

		CostDisplay.getInstance().setCostToDisplay(null);
		//PauseGameButton.getInstance().setVisible(true);

		bb.setPendingBuilding(null);

		InfoMessage.getInstance().clearMessages();
	}


	public void resetEverything() {
		UIView uiView = this.uiView;
		if( uiView != null ){
			uiView.hide();
		}

		selUI.reset();

		//PurchaseScreen.hide();
		//ViewArmyButton.hidePopUp();
		DialogBuilder.hidePopUps();
	}




	public void onResume() {
		uiThread.resume(this,tdg.getRenderView());
		lastCalc = GameTime.getTime() + 100;
	}

	public void onPause() {
		lastCalc = Long.MAX_VALUE;
		uiThread.pause();
	}

	public void onStop() {
		uiThread.pause();
	}




	/**
	 * Safe to call from any thread.
	 * @param message
	 */
	public void warn( final String message ) {
		//Log.d(TAG , "warn("+message+")");
		if( message == null ){
			//Log.e( TAG , "warn() -> message == null ");
			return;
		}
		final Activity a = Rpg.getGame().getActivity();
		DialogBuilder db = new DialogBuilder(a);
		db.setPositiveButton(DialogBuilder.OK, null).setText( message ).show();
	}




	//
	//	public Scroller getRightScroller() {
	//		return rightScroller;
	//	}
	//
	//	public void setRightScroller(Scroller rightScroller) {
	//		this.rightScroller = rightScroller;
	//		if (this.rightScroller != null) {
	//			this.rightScroller.setVisible(true);
	//		}
	//	}
	//
	//	public Scroller getLeftScroller() {
	//		return leftScroller;
	//	}
	//
	//	public void setLeftScroller(Scroller leftScroller) {
	//		this.leftScroller = leftScroller;
	//		if (this.leftScroller != null) {
	//			this.leftScroller.setVisible(true);
	//		}
	//	}
	//
	//	public AndroidButton getBottomLeftButton() {
	//		return bottomLeftButton;
	//	}
	//
	//	public void setBottomLeftButton(AndroidButton bottomLeftButton) {
	//		this.bottomLeftButton = bottomLeftButton;
	//		if (this.bottomLeftButton != null) {
	//			this.bottomLeftButton.setVisible(true);
	//		}
	//	}
	//
	//	public AndroidButton getBottomRightButton() {
	//		return bottomRightButton;
	//	}
	//
	//	public void setBottomRightButton(AndroidButton bottomRightButton) {
	//		this.bottomRightButton = bottomRightButton;
	//		if (this.bottomRightButton != null) {
	//			this.bottomRightButton.setVisible(true);
	//		}
	//	}
	//
	//	public AndroidButton getTopRightButton() {
	//		return topRightButton;
	//	}
	//
	//	public void setTopRightButton(AndroidButton topRightButton) {
	//		////Log.d(TAG, "setTopRightButton( " + topRightButton + " )");
	//		this.topRightButton = topRightButton;
	//		if (this.topRightButton != null) {
	//			this.topRightButton.setVisible(true);
	//		}
	//	}


	public void setBuildingMover(BuildingMover bMover) {

		BuildingMover thisBMover = this.bMover;
		if( thisBMover != null )
			thisBMover.cancel();

		this.bMover = bMover;
		////Log.d(TAG, "setBuildingMover(" + bMover );
	}

	public void setMover(Mover mover) {
		Mover thisMover = this.mover;
		if( thisMover != null )
			thisMover.cancel();

		this.mover = mover;
		//Log.d(TAG, "setBuildingMover(" + bMover );
	}


	public vector getCenteredOn() {
		return level.getBackground().getCenteredOn();
	}

	public void setCenteredOn(vector centeredOn) {
		this.level.getBackground().setCenteredOn(centeredOn);
	}

	public MM getMM() {
		return level.getMM();
	}
	public CD getCD() {
		return level.getMM().getCD();
	}
	public Level getLevel() {
		return level;
	}

	public CoordConverter getCc() {
		return level;
	}



	public Selecter getSelecter() {
		return selecter;
	}

	public void refreshSelectedUI() {
		selUI.refreshUI();
	}

	public void hideSelectedUI() {
		selUI.clearSelections();
	}



	public void nullify() {
		tdg = null;
	}

	public void hide() {
		UIView uiView = this.uiView;
		if( uiView != null )
			uiView.hide();

	}



	public void showUIView(TowerDefenceGame tdg) {


	}

	protected int uiViewVisibility = View.INVISIBLE;




	public void setUIViewVisibility(boolean b) {
		if( b ) uiViewVisibility = View.VISIBLE;
		else if( !b ) uiViewVisibility = View.INVISIBLE;

		UIView uiView = this.uiView;
		if( uiView != null )
			uiView.setUIViewVisibility(b);
	}

	public void setUIView(UIView uiView_) {
		uiView = uiView_;
		if( uiView_ != null ){
			uiView_.setVisibility(uiViewVisibility);
		}
	}



	public void hideMenuButton() {
		UIView uiView = this.uiView;
		if( uiView != null ){
//			View menu = uiView.findViewById(R.id.imageButtonMenuButton );
//			if( menu != null )
//				menu.setVisibility(View.INVISIBLE);
		}

	}







	public void paintBeforePendingBuilding(Graphics g) {
		bb.paintBeforePendingBuilding(g);
	}

	public void paintAfterPendingBuilding(Graphics g) {
		bb.paintAfterPendingBuilding(g);
	}



	public void addContentViewToLevelLayer(View v,
			android.widget.RelativeLayout.LayoutParams layoutParams) {
		////Log.v( TAG , "addContentViewToLevelLayer("+v+")");

		UIView.addContentViewToLevelLayer(v, layoutParams);
	}



	public void setSelected(LivingThing lt) {
		selectedThings.setSelected(lt);
		selUI.setSelected( lt );
	}

	public void setSelectedThing(GameElement ge) {
		selectedThings.setSelectedThing(ge);
		selUI.setSelected( ge );
	}

	public void setSelected(ArrayList<? extends GameElement> ges) {
		selectedThings.setSelected(ges);
		//selUI.setSelectedThings( ges );
	}

	/* Not used in Tower Defence
	public void setSelectedUnit(LivingThing u) {
		selectedThings.setSelectedUnit(u);
	}

	public void setSelectedUnits(ArrayList<LivingThing> soldiers) {
		selectedThings.setSelected(soldiers);
	}*/

	public void setSelectedBuilding(Building b) {
		selectedThings.setSelectedBuilding(b);
		selUI.setSelected(b);
        if( b instanceof Barracks)
		    tdlp.setSelectedBuilding((Barracks) b);
		if( b instanceof AttackingBuilding )
			towerController.setSelectedBuilding((AttackingBuilding)b);
	}


	public void clearSelected() {
		selectedThings.clearSelected();
		selUI.clearSelections();
        tdlp.setSelectedBuilding(null);
		towerController.setSelectedBuilding(null);
	}

	public void clearSelectedBuilding() {
		selectedThings.clearSelectedBuilding();
	}

	public void clearSelectedUnit() {
		selectedThings.clearSelectedUnit();
	}

	public void clearSelectedUnits() {
		selectedThings.clearSelectedThings();
	}

	public void setUnSelected(GameElement ge) {
		selectedThings.setUnSelected(ge);
		selecter.clearSelection();
		//if( !somethingIsSelected() )
		//	selecter.clearSelection();
	}

	public LivingThing getSelectedUnit() {
		return selectedThings.getSelectedUnit();
	}

	public void moveSelected(vector inDirection) {
		selectedThings.moveSelected(inDirection);
	}

	//	public boolean moveSelectedUnits(Vector dest) {
	//		return selectedThings.moveSelectedUnits(dest);
	//	}

	public boolean somethingIsSelected() {
		return selectedThings.somethingIsSelected();
	}

	public boolean multipleThingsAreSelected() {
		return selectedThings.multipleThingsAreSelected();
	}

	public LivingThing getSelectedBuilding() {
		return selectedThings.getSelectedBuilding();
	}

	public GameElement getSelectedThing() {
		return selectedThings.getSelectedThing();
	}

	public ArrayList<? extends GameElement> getSelectedUnits() {
		return selectedThings.getSelectedUnits();
	}




	public RectF getOnScreenArea() {
		return onScreenArea;
	}
	public RectF getStillDrawArea() {
		return stillDrawArea;
	}

	@Override
	public int getMapWidth() {
		return level.getMapWidth();
	}
	@Override
	public int getMapHeight() {
		return level.getMapHeight();
	}
	public int getScreenWidth() {
		return (int) (onScreenArea.width()/Zoomer.getxScale());
	}
	public int getScreenHeight() {
		return (int) (onScreenArea.height()/Zoomer.getyScale());
	}




	public static void removeThis( final View v ){
		if( v == null ) return;

		if( Rpg.getGame().uiThreadName.equals(Thread.currentThread().getName()) ){
			ViewParent vp = v.getParent();
			if( vp instanceof ViewGroup )
				((ViewGroup)vp).removeView(v);
		}
		else {
			Rpg.getGame().getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					ViewParent vp = v.getParent();
					if (vp instanceof ViewGroup)
						((ViewGroup) vp).removeView(v);
				}
			});
		}
	}


	public EffectPlacer getEffectPlacer() {
		return effectPlacer;
	}
}
