package com.kingscastle.nuzi.towerdefence.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.kingscastle.nuzi.towerdefence.TowerDefenceGame;
import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.framework.Graphics;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Input.TouchEvent;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.framework.Settings;
import com.kingscastle.nuzi.towerdefence.gameElements.GameElement;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Building;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.BuildingAnim;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Buildings;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.level.GridUtil;
import com.kingscastle.nuzi.towerdefence.teams.AllowedBuildings;
import com.kingscastle.nuzi.towerdefence.teams.Player;
import com.kingscastle.nuzi.towerdefence.teams.Team;
import com.kingscastle.nuzi.towerdefence.ui.buttons.BuildingButton;
import com.kingscastle.nuzi.towerdefence.ui.buttons.SButton;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BuildingBuilder implements TouchEventAnalyzer
{
	private static final String TAG = "BuildingBuilder";

	@NotNull
	private static final Paint alphaPaint;

	static{
		alphaPaint = new Paint(Rpg.getDstOverPaint());
		alphaPaint.setAlpha(128);
	}

	private CostDisplay costDisplay;


	@Nullable
	private Building pb;

	@NotNull
	private final ArrayList<BuildingButton> buildingButtons = new ArrayList<BuildingButton>();

	private boolean okToShowPendingbuilding = false;

	@NotNull
	private final vector PendingsScreenRelCoordinates = new vector();
	@NotNull
	private final vector pendingsMapRelCoord = new vector();

	private int pointerID = -1 ;
	private final vector downAt = new vector( -1 ,-1 );
	private final RectF mapRelArea = new RectF();

	private final UI ui;
	private final SelectedUI selUI;
	private final Selecter selecter;
	private final InfoDisplay infDisplay;
	private boolean busy;
	@NotNull
	private List<RectF> noBuildZones = new ArrayList<>();
	private boolean showingScroller;
	private long lastFailedToPurchase;

	BuildingBuilder( UI ui_ , SelectedUI selUI_ , Selecter selecter_ , InfoDisplay infDisplay_ )
	{
		ui = ui_;
		selUI = selUI_;
		selecter = selecter_;
		infDisplay = infDisplay_;

		//		buildingsScroller = new Scroller( Rpg.getRightScrollerArea() , false );
		//		buildingsScroller.setDoubleClick( false );
		//		buildingsScroller.setPaint( new Paint() );
	}




	public void cancel()
	{
		pb = null;
		Settings.showAllAreaBorders = false;
		//CostDisplay.getInstance().setCostToDisplay( null );
	}


	/**
	 * @param event
	 * @return true if the event was used up by this method
	 */
	@Override
	public boolean analyzeTouchEvent( @NotNull TouchEvent event )
	{

		if( showingScroller ) return true;

//		if( event.type == TouchEvent.TOUCH_UP)
//			return false;

		Building pending = pb;
		if( pending != null )
		{
			downAt.set(event.x, event.y);
			ui.getCc().getCoordsScreenToMap(downAt, downAt);


			mapRelArea.set(pending.getPerceivedArea());
			mapRelArea.offset(downAt.x, downAt.y);


			if( okToShowPendingbuilding && !downAt.isNear(pending.loc.x,pending.loc.y,200)){
				return false;
			}


			pending.loc.set(downAt);
			pending.updateArea();

			ui.getMM().getGridUtil().setProperlyOnGrid(mapRelArea, Rpg.gridSize);
			GridUtil.getLocFromArea(mapRelArea, pending.getPerceivedArea(), downAt);

			for( RectF r : noBuildZones )
				if(RectF.intersects(mapRelArea,r) || mapRelArea.contains(r) )
					return true;



			if( ui.getMM().getCD().checkPlaceable2(mapRelArea, false) )
			{
				ui.getCc().getCoordsMapToScreen(downAt, PendingsScreenRelCoordinates);

				pending.loc.set(downAt);
				pending.updateArea();

				pendingsMapRelCoord.set(downAt);

				//Log.v(TAG, "Pending Building's location is placeable.");

				//pending.setLoc( PendingsScreenRelCoordinates.x , PendingsScreenRelCoordinates.y );
				//pending.updateArea();
				showBuildButton();
				okToShowPendingbuilding = true;
			}else{
				Log.v(TAG, "Pending Building's location is not placeable because " + ui.getMM().getCD().checkPlaceable(mapRelArea, false));
			}
			downAt.x = event.x;
			downAt.y = event.y;

			return true;
		}

		return false;
	}



	public boolean buildPendingBuilding(final Activity activity)
	{
		Log.v(TAG, "buildPendingBuilding(), pendingBuilding == " + pb);
		Building pendingBuilding = this.pb;
		if( pendingBuilding == null )
			return false;

		MM mm = ui.getMM();
		Player player = mm.getPlayer(pendingBuilding.getTeamName());


		//Log.v( TAG , "buildPendingBuilding(), bCosts: " + b.getCosts() + " player pr:" + player.getPR());
		if( player.canAfford( pendingBuilding.getCosts() ) )
		{
			pendingBuilding.loc.set(pendingsMapRelCoord);
			pendingBuilding.updateArea();
			ui.getMM().getGridUtil().setProperlyOnGrid(pendingBuilding.loc, Rpg.gridSize);
			GridUtil.getLocFromArea(pendingBuilding.area, pendingBuilding.getPerceivedArea(), pendingBuilding.loc);

			//Log.v(TAG, "can afford pb");
			GameElement hitThing = mm.getCD().checkPlaceable(pendingBuilding.area, false );
			if( hitThing == null ) {
				//Log.v( TAG , "placeable");
				Team t = player.getTeam();

				if( player.spendCosts(pendingBuilding.getCosts()) ) {

					if ( t.getBm().add(pendingBuilding)) {
						//Log.v(TAG, "added to bm");

						selecter.clearSelection();

						//t.getBm().add(b);

						CostDisplay.getInstance().setCostToDisplay(null);
						Settings.showAllAreaBorders = false;
						okToShowPendingbuilding = false;

						pb = null;

						return true;
					}
					else{
						//Log.v(TAG, "not added to bm");
						player.refundCosts(pendingBuilding.getCosts());
					}
				}
				else{
					//Log.v(TAG, "Cannot Afford");
				}
			}else{
				//Log.d(TAG,"Cant place building "+pendingBuilding.area+" because of " + hitThing );
			}
		}
		else{
			if( lastFailedToPurchase + 1000 < GameTime.getTime() ) {
				activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast t = Toast.makeText(activity, "You Cannot Afford This.", Toast.LENGTH_SHORT);
						t.show();
					}
				});
				lastFailedToPurchase = GameTime.getTime();
			}
		}

		return false;
	}





	private void showBuildButton()
	{
		selUI.showBuildButton();
		//Log.d( TAG , "displayBuildButton()");
		//UI.getInstance().setBottomRightButton( BuildButton.getInstance() );
	}

	public void showCancelButton()
	{
		selUI.showCancelButton();
		//Log.d( TAG , "displayCancelButton()");
		//UI.getInstance().setBottomRightButton( BuildButton.getInstance() );
	}




	public void paintBeforePendingBuilding( Graphics g ){
		if ( pb != null && okToShowPendingbuilding ) {
			//g.drawImage(pb.getImage(), PendingsScreenRelCoordinates.x, PendingsScreenRelCoordinates.y , alphaPaint);
				//	pb.paint(g, PendingsScreenRelCoordinates);
		}
	}

	public void paintAfterPendingBuilding( @NotNull Graphics g )
	{
		Building pb = this.pb;
		if( pb != null && okToShowPendingbuilding )
		{
			ui.getCc().getCoordsMapToScreen(pendingsMapRelCoord,PendingsScreenRelCoordinates);
			Image img = pb.getImage();
			g.drawImage(img, PendingsScreenRelCoordinates.x - img.getWidthDiv2() , PendingsScreenRelCoordinates.y - img.getHeightDiv2() , alphaPaint);
			g.drawCircle(PendingsScreenRelCoordinates,pb.aq.getAttackRange());
			g.drawRectBorder(pb.getPerceivedArea(),PendingsScreenRelCoordinates, Color.YELLOW, 2);
		}
	}





	@NotNull
	private ArrayList<BuildingButton> getBuildingButtons( @NotNull TowerDefenceGame tdg, @NotNull Team t)
	{
		MM mm = tdg.getLevel().getMM();

		buildingButtons.clear();

		//Log.v(TAG, "getBuildingButtons()");
		for ( Building b : t.getPlayer().getAbs().getAllowedBuildings() )
		{
			//Log.v(TAG, "b=" + b.getName());

			BuildingButton bb = BuildingButton.getInstance( tdg.getActivity() , b , t , ui, infDisplay, selecter, ui.bb , mm.getCD(), mm.getGridUtil() );
			//Log.v( TAG , "Adding building button for a " + b );

			if ( bb != null )
				buildingButtons.add ( bb );
		}

		return buildingButtons;
	}


	public void clearScrollersButtons()
	{
		boolean wasVisible = selUI.getButtonsId() == TAG;//ui.getRightScroller() == buildingsScroller;

		if( wasVisible )
			selUI.clearScrollerButtons();
	}

	public void resetScroller()
	{
		//buildingsScroller = new Scroller( Rpg.getRightScrollerArea() , false );
		//buildingsScroller.setDoubleClick( false );
		//buildingsScroller.setVisible( true );
	}

	public boolean resetScroller( AllowedBuildings abs )
	{
		boolean wasVisible = selUI.getButtonsId() == TAG;

		buildingButtons.clear();

		//if( wasVisible )
		//	showScroller( abs );

		return wasVisible;
	}


	public void showScroller( @NotNull TowerDefenceGame tdg , @NotNull final ScrollView scroller, @NotNull final LinearLayout linearLayout )
	{
		showingScroller = true;
		final List<BuildingButton> buttons = getBuildingButtons(tdg,tdg.getLevel().getHumanPlayer().getTeam());

		List<BuildingButton> lockedBs = new ArrayList<>();
		List<Buildings> allowedBuildings = tdg.getLevel().getAllowedBuildings();
		for( BuildingButton bb : buttons ){
			if( !allowedBuildings.contains(bb.getAssocBuilding()) ) {
				bb.setEnabled(false);
				bb.addLockOverlay();
				lockedBs.add(bb);
			}
		}
		buttons.removeAll(lockedBs);
		buttons.addAll(lockedBs);


		tdg.getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Log.d(TAG, "Clearing and rebuilding scroller. scrollerLayout=" + linearLayout + " buttons: " + buttons);

				try {
					ValueAnimator animation = ValueAnimator.ofFloat(0f, 75 * Rpg.getDp());
					animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
						@Override
						public void onAnimationUpdate(@NotNull ValueAnimator animation) {
							//Log.d( TAG , "sl.getX()= " + scrollerLayout.getX() + " sl.getY()= " + scrollerLayout.getY() );
							linearLayout.setTranslationX((Float) animation.getAnimatedValue());
						}
					});
					animation.setDuration(75);

					animation.addListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							try {
								linearLayout.removeAllViews();

								ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams((int) (75 * Rpg.getDp()), (int) (75 * Rpg.getDp()));

								for (final SButton sb : buttons) {
									if (sb.getParent() != null)
										((ViewGroup) sb.getParent()).removeView(sb);

									sb.setLayoutParams(layoutParams);
									//sb.setScaleX(0.75f);
									//sb.setScaleY(0.75f);
									linearLayout.addView(sb);
								}

								final ValueAnimator animation2 = ValueAnimator.ofFloat(75 * Rpg.getDp(), 0f);
								animation2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
									@Override
									public void onAnimationUpdate(ValueAnimator animation) {
										////Log.d( TAG , "sl.getX()= " + scrollerLayout.getX() + " sl.getY()= " + scrollerLayout.getY() );
										linearLayout.setX((Float) animation2.getAnimatedValue());
										//scroller.setTranslationX((Float) animation.getAnimatedValue());
									}
								});
								animation2.setDuration(75);
								animation2.addListener(new AnimatorListenerAdapter() {
									@Override
									public void onAnimationStart(Animator animation) {
										scroller.setVisibility(View.VISIBLE);
									}

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

		});
		//Log.d( TAG , "showing buildings Scroller ");
		//		if( Settings.mapEditingMode )
		//		{
		//			buildingButtons.clear();
		//			clearScrollersButtons();
		//
		//			selUI.displayTheseInRightScroller( getBuildingButtons(MapEditorLevel.getAllBuildingsAbs() , workers_ ) , TAG );
		//		}
		//		else
		//		{
//		if ( abs.needToRebuildScroller() )
//		{
//			clearScrollersButtons();
//			selUI.displayTheseInRightScroller( getBuildingButtons( abs ), TAG );
//		}
		//}
	}

	public void clearScrollerButtons(@NotNull final Activity a , @NotNull final ScrollView scroller, @NotNull final LinearLayout linearLayout) {
	if( Settings.instantBuild ) return;
		a.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				//Log.v( TAG , "Clearing scroller." ) ;

				ValueAnimator animation = ValueAnimator.ofFloat(0f, 75 * Rpg.getDp());
				animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
					@Override
					public void onAnimationUpdate(@NotNull ValueAnimator animation) {
						////Log.d( TAG , "sl.getX()= " + scrollerLayout.getX() + " sl.getY()= " + scrollerLayout.getY() );
						linearLayout.setX((Float) animation.getAnimatedValue());
						//scroller.setTranslationX((Float) animation.getAnimatedValue());
					}
				});
				animation.setDuration(75);
				animation.addListener(new AnimatorListenerAdapter() {

					@Override
					public void onAnimationEnd(Animator animation) {
						linearLayout.removeAllViews();
						scroller.setVisibility(View.INVISIBLE);
					}
				});
				animation.start();
				}

		});
	}

//	public void startBuilding(Building newPb)
//	{
//		Building oldPb = this.pb;
//		if( oldPb != null ){
//			oldPb.dead = true;
//			BuildingAnim bAnim = oldPb.getBuildingAnim();
//			if( bAnim != null )
//				bAnim.setOver(true);
//		}
//		this.pb = newPb;
//
//		if( newPb != null ){
//
//			MM.get().add( newPb );
//			selecter.setSelected(newPb);
//			selUI.disappear();
//
//			showBuildButton();
//			showCancelButton();
//		}
//	}


	@Nullable
	Building getPendingBuilding()
	{
		return pb;
	}

	public void setPendingBuilding(Building newPb)
	{
		downAt.set(-1,-1);
		mapRelArea.set(-1,-1,-1,-1);
		PendingsScreenRelCoordinates.set(-1,-1);
		okToShowPendingbuilding = false;
		Log.d(TAG,"setPendingBuilding("+newPb);
		showingScroller = false;
		Building oldPb = this.pb;
		if( oldPb != null ){
			oldPb.dead = true;
			BuildingAnim bAnim = oldPb.getBuildingAnim();
			if( bAnim != null )
				bAnim.setOver(true);
		}
		this.pb = newPb;
		if( pb != null ) {
			Settings.showAllAreaBorders = false;
		}
		synchronized (pbsl){
			for( OnPendingBuildingSet pbs : pbsl)
				pbs.onPendingBuildingSet(pb);
		}

	}


	public void addNoBuildZone(@NotNull RectF... areasInMapPx){
		Collections.addAll(noBuildZones,areasInMapPx);
	}

	public void clearNoBuildZones(){
		noBuildZones.clear();
	}


	public CostDisplay getCostDisplay()
	{
		return costDisplay;
	}
	public void setCostDisplay(CostDisplay costDisplay)
	{
		this.costDisplay = costDisplay;
	}




	public void pointerLeftScreen(int pointer) {
		if( pointer == pointerID )
		{
			pointerID = -1;
			downAt.set( -1 , -1 );
		}
	}


	//On Pending Building Set Listener
	private final ArrayList<OnPendingBuildingSet> pbsl = new ArrayList<>();

	public static interface OnPendingBuildingSet{
		void onPendingBuildingSet(Building b);
	}

	public void addPBSL(OnPendingBuildingSet gol)		   		{	synchronized( pbsl ){	pbsl.add( gol );				}  	}
	public boolean removePBSL(OnPendingBuildingSet gol)		{	synchronized( pbsl ){	return pbsl.remove( gol );		}	}








	//
	//	public Scroller getScroller()
	//	{
	//		return buildingsScroller;
	//	}














}
