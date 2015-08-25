package com.kingscastle.nuzi.towerdefence.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.framework.implementation.ImageDrawable;
import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.gameElements.Cost;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.AdvancedHealer;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.AdvancedMageSoldier;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.AdvancedMeleeSoldier;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.AdvancedRangedSoldier;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.BasicHealer;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.BasicMageSoldier;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.BasicMeleeSoldier;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.BasicRangedSoldier;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.Healer;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.MageSoldier;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.MediumHealer;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.MediumMage;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.MediumMeleeSoldier;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.MediumRangedSoldier;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.MeleeSoldier;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.RangedSoldier;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.UpperHealer;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.UpperMageSoldier;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.UpperMeleeSoldier;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.UpperRangedSoldier;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.Catapult;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.HumanElementalWizard;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.WhiteWizard;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Building;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Buildings;
import com.kingscastle.nuzi.towerdefence.teams.Team;

import java.util.ArrayList;
import java.util.List;


/**
 * TODO: document your custom view class.
 */
public class InfoDisplay {

	protected static final String TAG = "InfoDisplay";

	private final UI ui;


	private LivingThing selectedThing;
	private Team team;
	private View statsDisplay;

	public CTextView2 hp , res;

	private List<Runnable> updaters = new ArrayList<>();

	private boolean infoDisplayIsOpen = false;

	InfoDisplay( UI ui ){
		this.ui = ui;
	}


	public void showInfoDisplay() {
		if( selectedThing != null && team != null )
			showInfoDisplay(selectedThing , team );
	}

	public void showInfoDisplay( final LivingThing st, final Team team_ ){
		showInfoDisplay( st , team_ , null );
	}

	public void showInfoDisplay( final LivingThing st, final Team team_ , final OnClickListener closeListener ){
		selectedThing = st;
		team = team_;
		if( st == null || team == null )
			return;

		final Activity a = Rpg.getGame().getActivity();

		a.runOnUiThread( new Runnable(){
			@Override
			public void run() {
				//Log.d( TAG , "run() st=" + st );

				{
					View v = statsDisplay;
					if( v != null ){
						ViewGroup vg = (ViewGroup)(v.getParent());
						if( vg != null )
							vg.removeView(v);
					}
				}

				infoDisplayIsOpen = true;
				List<Runnable> updaters = new ArrayList<>();

				LayoutInflater inflater = a.getLayoutInflater();
				final View tmpView = inflater.inflate(R.layout.info, null);
				statsDisplay = tmpView;
				final ScrollView scrollView = (ScrollView) tmpView.findViewById(R.id.scrollViewInfo);
				final RelativeLayout window = (RelativeLayout) tmpView.findViewById(R.id.relativeLayoutInfo);

				WindowBuilder wb = new WindowBuilder(a);

				//final ImageButton closeButton = (ImageButton) tmpView.findViewById(R.id.imageButtonInfoCloseButton2);

				OnClickListener cl = closeListener;
				if( cl == null )
					cl = new OnClickListener() {
					@Override
					public void onClick(View view) {
						ui.selUI.setSelected(st);
						hide();
					}
				};
				final OnClickListener onCloseListener = cl;
				//closeButton.setOnClickListener(onCloseListener);

				wb.setCloseButtonListener(onCloseListener);


				final ImageView selImage = new ImageView(a);//(ImageView) tmpView.findViewById(R.id.infoImage);
				final RelativeLayout selBacking = (RelativeLayout) tmpView.findViewById(R.id.infoImageBacking);


				ImageDrawable id;
				if( st instanceof Building )
					id = new ImageDrawable( ((Building)st).getImage().getBitmap() , 0 , 0 , 1f , 1f , new Paint() );
				else{
					throw new IllegalStateException("Trying to see info on a nonbuilding");
				}
				final float width = id.getWidth();
				final float height = id.getHeight();
				//Log.d( TAG , "imageDrawable= " + id );
				selImage.setImageDrawable(id);
				//selImage.invalidate();
				//selBacking.invalidate();
				//selBacking.requestLayout();
				window.addView(selImage, id.getWidth(),  id.getHeight());

				wb.setOnGlobalLayoutListener(new OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {
						float centerX = selBacking.getX() + selBacking.getWidth()/2;
						float centerY = selBacking.getY() + selBacking.getHeight()/2;
						selImage.setX( centerX - width/2 );
						selImage.setY( centerY - height/2 );
					}
				});

				{// Title
					wb.setTitle(st.getName());
				}

				{// Level
					CTextView2 lvl = (CTextView2) tmpView.findViewById(R.id.textViewInfoLvl);
					UIUtil.setText(lvl.getText() + " " + st.lq.getLevel() , lvl );
				}


//FIXME not used in tower defence
//				{// Hp:
//					CTextView2 hp = (CTextView2) tmpView.findViewById(R.id.textViewInfoHp);
//
//					// Hp Bar
//					final ProgressBar pb = (ProgressBar) tmpView.findViewById(R.id.progressBarInfoHpBar);
//					pb.getProgressDrawable().setColorFilter(Color.GREEN, Mode.MULTIPLY);
//
//					final CTextView2 hpValue = (CTextView2) tmpView.findViewById(R.id.textViewInfoHpValue);
//					//UIUtil.applyCooperBlack(hpValue);
//					//hpValue.setTextColor(Color.BLACK);
//					updaters.add( new Runnable(){
//						int php = 0;
//						int pfhp = 0;
//						@Override
//						public void run() {
//							int hp = st.lq.getHealth();
//							int fhp = st.lq.getFullHealth();
//
//							if( hp != php || fhp != pfhp ){
//								hpValue.setText( "  " + hp + "/" + fhp );
//								pb.setMax(fhp);
//								pb.setProgress(hp);
//							}
//						}
//					});
//				}

				//FIXME not used in tower defence
//				if( st instanceof BasicHealingShrine){
//
//					//final Healer h = (Healer) st;
//
//					CTextView2 armor = (CTextView2) tmpView.findViewById(R.id.textViewInfoArmor);
//					//					CTextView2 armor2 = (CTextView2) tmpView.findViewById(R.id.textViewInfoArmor2);
//					//					CTextView2 armor3 = (CTextView2) tmpView.findViewById(R.id.textViewInfoArmor3);
//					//					final CTextView2[] armors = { armor , armor2 , armor3 };
//					//					UIUtil.applyCooperBlack(armors);
//					//					UIUtil.setUpForBacking(2, armors);
//					//					armor.setTextColor( Palette.lightGray );
//
//					UIUtil.setText(armor.getText() + " " + st.lq.getArmor() + "% " + a.getString(R.string.reduction) , armor );
//					//v.requestLayout();
//
//
//					final String healString = a.getString(R.string.heal_amount , st.lq.getHealAmount() );
//
//					CTextView2 heal = (CTextView2) tmpView.findViewById(R.id.textViewInfoDps);
//					//					CTextView2 heal2 = (CTextView2) tmpView.findViewById(R.id.textViewInfoDps2);
//					//					CTextView2 heal3 = (CTextView2) tmpView.findViewById(R.id.textViewInfoDps3);
//					//					final CTextView2[] heals = { heal , heal2 , heal3 };
//					//					UIUtil.applyCooperBlack(heals);
//					//					UIUtil.setUpForBacking(2, heals);
//					//					heal.setTextColor( Palette.lightGray );
//					UIUtil.setText( healString , heal );
//
//				}
//				else
//				{
//					CTextView2 armor = (CTextView2) tmpView.findViewById(R.id.textViewInfoArmor);
//					//					CTextView2 armor2 = (CTextView2) tmpView.findViewById(R.id.textViewInfoArmor2);
//					//					CTextView2 armor3 = (CTextView2) tmpView.findViewById(R.id.textViewInfoArmor3);
//					//					final CTextView2[] armors = { armor , armor2 , armor3 };
//					//					UIUtil.applyCooperBlack(armors);
//					//					UIUtil.setUpForBacking(2, armors);
//					//					armor.setTextColor( Palette.lightGray );
//
//					UIUtil.setText(armor.getText() + " " + st.lq.getArmor() + "% " + a.getString(R.string.reduction) , armor );
//
//
					//CTextView2 dmg = (CTextView2) tmpView.findViewById(R.id.textViewInfoDps);
					//					CTextView2 dmg2 = (CTextView2) tmpView.findViewById(R.id.textViewInfoDps2);
					//					CTextView2 dmg3 = (CTextView2) tmpView.findViewById(R.id.textViewInfoDps3);
					//					final CTextView2[] dmgs = { dmg , dmg2 , dmg3 };
					//					UIUtil.applyCooperBlack(dmgs);
					//					UIUtil.setUpForBacking(2, dmgs);
//
					//UIUtil.setText( dmg.getText() + " " + st.getAQ().getDamage() , dmg );
//				}

				CTextView2 dmg = (CTextView2) tmpView.findViewById(R.id.textViewInfoDps);
				UIUtil.setText( dmg.getText() + " " + st.getAQ().getDamage() , dmg );

				CTextView2 rof = (CTextView2) tmpView.findViewById(R.id.textViewInfoROF);
				UIUtil.setText( rof.getText() + " " + st.getAQ().getROF() + "ms" , rof );

				Cost cost = st.getCosts();

				{//Gold
					CTextView2 v = (CTextView2) tmpView.findViewById(R.id.textViewInfoGoldCost);
					if( cost.getGoldCost() == 0) UIUtil.setVisibility(View.INVISIBLE , v );
					else{
						UIUtil.setText(v.getText() + " " + cost.getGoldCost() , v);
					}
				}

				Cost upgradeCost = st.getLvlUpCost();
				{//Upgrade Cost
					CTextView2 v = (CTextView2) tmpView.findViewById(R.id.textViewInfoUpgradeCost);
					if( upgradeCost.getGoldCost() == 0) UIUtil.setVisibility(View.INVISIBLE , v );
					else{
						UIUtil.setText(v.getText() + " " + upgradeCost.getGoldCost() , v);
					}
				}

//				{//Food
//					CTextView2 v = (CTextView2) tmpView.findViewById(R.id.textViewInfoFoodCost);
//					if( isATc || cost.getFoodCost() == 0) UIUtil.setVisibility(View.INVISIBLE , v );
//					else{
//						UIUtil.setText(v.getText() + " " + cost.getFoodCost() , v );
//						//v.requestLayout();
//					}
//				}
//				{//Wood
//					CTextView2 v = (CTextView2) tmpView.findViewById(R.id.textViewInfoWoodCost);
//					if( isATc || cost.getWoodCost() == 0) UIUtil.setVisibility(View.INVISIBLE , v );
//					else{
//						UIUtil.setText(v.getText() + " " + cost.getWoodCost() , v );
//						//v.requestLayout();
//					}
//				}
				{
					CTextView2 descr = (CTextView2) tmpView.findViewById(R.id.textViewInfoDescription);
					UIUtil.setText( getInfoMessage( a , st) , descr );
				}


				window.setLayoutParams(new ScrollView.LayoutParams( (int) Rpg.fiveHundDp , LayoutParams.WRAP_CONTENT ));
				scrollView.setLayoutParams(new RelativeLayout.LayoutParams( (int) Rpg.fiveHundDp , LayoutParams.WRAP_CONTENT ));

				//kc.getWindow().addContentView(tmpView, new ViewGroup.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.MATCH_PARENT));
				wb.setContent(tmpView).show();
				tmpView.requestLayout();

				synchronized( updaters ){
					InfoDisplay.this.updaters = updaters;
				}


			}
		});
	}





	private static final String Building = "Building";

	protected static String getInfoMessage(Activity a , LivingThing st) {

		if( st instanceof Building ){
			Building b = (Building) st;

			Buildings bName = b.getBuildingsName();
			if( Buildings.isInstanceOf( bName , Buildings.Barracks ))
				return a.getString(R.string.barracks_info);
			else if( Buildings.isInstanceOf( bName , Buildings.Church ))
				return a.getString(R.string.church_info);
			else if( Buildings.isInstanceOf( bName , Buildings.CatapultTower ))
				return a.getString(R.string.catapult_tower);
			else if( Buildings.isInstanceOf( bName , Buildings.WatchTower )){
				if( st.getName().contains("Stone") )
					return a.getString(R.string.stone_tower_info);
				else if( st.getName().contains("Guard") )
					return a.getString(R.string.guard_tower_info);
				else
					return a.getString(R.string.watch_tower_info);
			}else if( Buildings.isInstanceOf( bName , Buildings.TownCenter ))
				return a.getString(R.string.town_centre_info);
			else if( Buildings.isInstanceOf( bName , Buildings.Wall ))
				return a.getString(R.string.wall_info);
			else if( Buildings.isInstanceOf( bName , Buildings.LumberMill ))
				return a.getString(R.string.lumber_mill_info);
			else if( Buildings.isInstanceOf( bName , Buildings.GoldMineBuilding ))
				return a.getString(R.string.gold_mine_building_info);
			else if( Buildings.isInstanceOf( bName , Buildings.Farm ))
				return a.getString(R.string.farm_info);
			else if( Buildings.isInstanceOf( bName , Buildings.BasicHealingShrine ))
				return a.getString(R.string.basic_healing_shrine_info);
			else if( Buildings.isInstanceOf( bName , Buildings.StorageDepot ))
				return a.getString(R.string.storage_depot_info);
			else if( Buildings.isInstanceOf( bName , Buildings.BombTrap ))
				return a.getString(R.string.bomb_trap_info);
			else if( Buildings.isInstanceOf( bName , Buildings.SpikeTrap ))
				return a.getString(R.string.spike_trap_info);
			else if( Buildings.isInstanceOf( bName , Buildings.FastTower ))
				return a.getString(R.string.fast_tower_info);
			else if( Buildings.isInstanceOf( bName , Buildings.FireDragonTower ))
				return a.getString(R.string.fire_dragon_tower_info);
			else if( Buildings.isInstanceOf( bName , Buildings.IceDragonTower ))
				return a.getString(R.string.ice_dragon_tower_info);
			else if( Buildings.isInstanceOf( bName , Buildings.ShockDragonTower ))
				return a.getString(R.string.shock_dragon_tower_info);
			else
				return "";
		}
		else if( st instanceof MeleeSoldier){
			if( st instanceof BasicMeleeSoldier)
				return a.getString(R.string.basic_melee_info , st.getName() );
			else if( st instanceof MediumMeleeSoldier)
				return a.getString(R.string.medium_melee_info , st.getName() );
			else if( st instanceof UpperMeleeSoldier)
				return a.getString(R.string.upper_melee_info , st.getName() );
			else if( st instanceof AdvancedMeleeSoldier)
				return a.getString(R.string.advanced_melee_info , st.getName() );
		}
		else if( st instanceof RangedSoldier){
			if( st instanceof Catapult)
				return a.getString(R.string.catapult_info , st.getName() );
			else if( st instanceof BasicRangedSoldier)
				return a.getString(R.string.basic_ranged_info , st.getName() );
			else if( st instanceof MediumRangedSoldier)
				return a.getString(R.string.medium_ranged_info , st.getName() );
			else if( st instanceof UpperRangedSoldier)
				return a.getString(R.string.upper_ranged_info , st.getName() );
			else if( st instanceof AdvancedRangedSoldier)
				return a.getString(R.string.advanced_ranged_info , st.getName() );
		}
		else if( st instanceof Healer ){
			if( st instanceof BasicHealer)
				return a.getString(R.string.basic_healer_info , st.getName() );
			else if( st instanceof MediumHealer)
				return a.getString(R.string.medium_healer_info , st.getName() );
			else if( st instanceof UpperHealer)
				return a.getString(R.string.upper_healer_info , st.getName() );
			else if( st instanceof AdvancedHealer)
				return a.getString(R.string.advanced_healer_info , st.getName() );
		}
		else if( st instanceof MageSoldier){
			if( st instanceof WhiteWizard)
				return a.getString(R.string.mage );
			else if( st instanceof HumanElementalWizard)
				return a.getString(R.string.elementalist );
			else if( st instanceof BasicMageSoldier)
				return a.getString(R.string.basic_mage_info , st.getName() );
			else if( st instanceof MediumMage)
				return a.getString(R.string.medium_mage_info , st.getName() );
			else if( st instanceof UpperMageSoldier)
				return a.getString(R.string.upper_mage_info , st.getName() );
			else if( st instanceof AdvancedMageSoldier)
				return a.getString(R.string.advanced_mage_info , st.getName() );
		}

		return null;
	}


	public void runOnUIThread(){
		synchronized( updaters ){
			for( Runnable r : updaters )
				r.run();
		}
	}




	public void reset() {
		selectedThing = null;
		team = null;
		hide();
	}


	public void hide() {

		if( Rpg.getGame().uiThreadName.equals(Thread.currentThread().getName()) ){
			//Log.d(TAG , "Synchronious hiding of stats display!");
			hide.run();
		}else{
			//Log.d(TAG , "ASynch hiding of stats display!");
			Rpg.getGame().getActivity().runOnUiThread(hide);
		}
	}


	private final Runnable hide = new Runnable(){
		@Override
		public void run() {
			final View sdisp = statsDisplay;

			if( sdisp != null ){
				final ValueAnimator anim = ValueAnimator.ofFloat(1f,0f);
				anim.setDuration(200l);
				anim.addUpdateListener(new AnimatorUpdateListener() {
					@Override
					public void onAnimationUpdate(ValueAnimator animation) {
						sdisp.setScaleX( (Float) anim.getAnimatedValue() );
						sdisp.setScaleY( (Float) anim.getAnimatedValue() );
						sdisp.setAlpha ( (Float) anim.getAnimatedValue() );
					}
				});
				anim.addListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(Animator animation) {
						ViewGroup vg = (ViewGroup)(sdisp.getParent());
						if( vg != null )
							vg.removeView(sdisp);

					}
				});
				anim.start();
			}

			synchronized( updaters ){
				updaters.clear();
			}
			infoDisplayIsOpen = false;
		}
	};





	public boolean isInfoDisplayOpen() {
		return infoDisplayIsOpen;
	}























}










