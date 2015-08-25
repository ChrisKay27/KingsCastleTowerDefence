package com.kingscastle.nuzi.towerdefence.framework;


import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout.LayoutParams;

import com.kingscastle.nuzi.towerdefence.TowerDefenceGame;
import com.kingscastle.nuzi.towerdefence.effects.EffectsManager;
import com.kingscastle.nuzi.towerdefence.effects.animations.Anim;
import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.Unit;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Building;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.level.Level;
import com.kingscastle.nuzi.towerdefence.teams.RT;
import com.kingscastle.nuzi.towerdefence.teams.Team;
import com.kingscastle.nuzi.towerdefence.teams.Teams;
import com.kingscastle.nuzi.towerdefence.ui.GameScreen;

public class Console {

	public static TowerDefenceGame tdg;
	private static View graphicsView;

	public static void cmd( String input ){
		if( input == null ) input = "";

		//		if( input.startsWith("save "))
		//			save(input);
		//		else
		if( input.startsWith("test") ) {
			TowerDefenceGame.testingVersion = !TowerDefenceGame.testingVersion;
		}else if( input.startsWith("showfogofwar") )
			Settings.showFogOfWar = !Settings.showFogOfWar;
		else if( input.startsWith("showgarrisondisplay") )
			Settings.showGarrisonDisplay = !Settings.showGarrisonDisplay;
		else if( input.startsWith("build") )
			Settings.instantBuild = !Settings.instantBuild;
		else if( input.startsWith("fps") )
			Settings.showFps = !Settings.showFps;
		else if( input.startsWith("hidebuildqueue") )
			Settings.hideBuildQueue = !Settings.hideBuildQueue;
		else if( input.startsWith("freemode") )
			Settings.freeMode = !Settings.freeMode;
		else if( input.startsWith("showvectors") )
			Settings.showVectors = !Settings.showVectors;
		else if( input.startsWith("showpaths ") )
			Settings.showPath = !Settings.showPath;
		else if( input.startsWith("showpathfinding") )
			Settings.showPathFinding = !Settings.showPathFinding;
		else if( input.startsWith("crash") )
			throw new Error("Wtf?");
		else if( input.startsWith("unlimitedpop ") ){
			String[] inputs = input.split(" ");
			setPopMax(Integer.parseInt(inputs[1]));
		}
		else if( input.startsWith("alpha ") ){
			String[] inputs = input.split(" ");
			GameScreen.overlayPaint.setAlpha(Integer.parseInt(inputs[1]));
		}
		else if( input.startsWith("red ") ){
			String[] inputs = input.split(" ");
			Paint p = GameScreen.overlayPaint;
			int a = Color.alpha(p.getColor());
			int r = Integer.parseInt(inputs[1]);
			int g = Color.green(p.getColor());
			int b = Color.blue(p.getColor());
			p.setARGB(a,r,g,b);
		}
		else if( input.startsWith("green ") ){
			String[] inputs = input.split(" ");
			Paint p = GameScreen.overlayPaint;
			int a = Color.alpha(p.getColor());
			int r = Color.red(p.getColor());
			int g = Integer.parseInt(inputs[1]);
			int b = Color.blue(p.getColor());
			p.setARGB(a, r, g, b);
		}
		else if( input.startsWith("blue ") ){
			String[] inputs = input.split(" ");
			Paint p = GameScreen.overlayPaint;
			int a = Color.alpha(p.getColor());
			int r = Color.red(p.getColor());
			int g = Color.green(p.getColor());
			int b = Integer.parseInt(inputs[1]);
			p.setARGB(a, r, g, b);
		}
		else if( input.startsWith("gold") ){
			addgold();
		}
		else if( input.startsWith("lives") ){
			String[] inputs = input.split(" ");
			int lives = 9999999;
			if( inputs.length > 1 )
				lives = Integer.parseInt(inputs[1]);
			tdg.getLevel().getHumanPlayer().setLives(lives);
		}
		else if( input.startsWith("round ") ){
			String[] inputs = input.split(" ");
			tdg.getLevel().goToRound(Integer.parseInt(inputs[1]));
		}
		else if( input.startsWith("night ") ){
			String[] inputs = input.split(" ");
			tdg.getLevel().setNightTime(Boolean.parseBoolean(inputs[1]));
		}
		else if( input.startsWith("killall") ){
			for( LivingThing lt : tdg.getLevel().getMM().getTeam(Teams.RED).getAm().getCloneArmy())
				lt.lq.setHealth(-100);
		}
		//else if( input.startsWith("restock") )
		//	restock();
		else if( input.startsWith("spawn ") )
			spawn(input);
		//		else if( input.startsWith("leaderboard") ){
		//			KingsCastle kc2 = Rpg.getGame();
		//			if( kc2 instanceof KingsCastleCD )
		//				((KingsCastleCD)kc2).showHSLeaderBoard();
		//		}

		else if( input.startsWith("g") ){
			if( graphicsView != null ){
				graphicsView.setVisibility(View.GONE);
				graphicsView = null;
				return;
			}

			Activity a = tdg.getActivity();
			final View gView = a.getLayoutInflater().inflate(R.layout.sample_graphics_tester, null);
			graphicsView = gView;


			final RadioGroup rg = (RadioGroup) gView.findViewById(R.id.porter_mode_radio_group);

			for( Mode m : Mode.values() ) {
				RadioButton rb = new RadioButton(a);
				rb.setText(m.name());
				RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,RadioGroup.LayoutParams.WRAP_CONTENT);
				rg.addView(rb, 0, lp);
			}

			RadioButton rb = new RadioButton(a);
			rb.setText("None");
			RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,RadioGroup.LayoutParams.WRAP_CONTENT);
			rg.addView(rb, 0, lp);
			rb.toggle();

			LinearLayout ll = (LinearLayout) gView.findViewById(R.id.effects_linear_layout);

			View.OnClickListener ocl = new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					RadioButton rb = (RadioButton) rg.findViewById(rg.getCheckedRadioButtonId());

					Anim a = null;
					try {
						Class<? extends Anim> aClass = (Class<? extends Anim>) Class.forName("com.kingscastle.nuzi.towerdefence.effects.animations."+ ((Button)v).getText() );
						a = aClass.getConstructor(vector.class).newInstance(new vector());
					} catch (Exception e) {
						e.printStackTrace();
						return;
					}
					Paint p = a.getPaint();

					String modeString = rb.getText().toString();
					if( !modeString.equals("None"))
						p.setXfermode(new PorterDuffXfermode(Mode.valueOf(modeString)));

					a.setLooping(true);
					a.setAliveTime(1000000);

					RadioGroup layerRg = (RadioGroup) gView.findViewById(R.id.radiogroup_layer);
					switch (layerRg.getCheckedRadioButtonId()){
						case R.id.radioButton_InfrontLayer: a.setRequiredPosition(EffectsManager.Position.InFront); break;
						case R.id.radioButton_XferLayer: a.setRequiredPosition(EffectsManager.Position.XFerAdd); break;
					}

					tdg.getUI().getEffectPlacer().setAnimToPlace(a);
				}
			};

			Button b = new Button(a);
			b.setText("GroundSmasherLargeAnim");
			b.setOnClickListener(ocl);
			ll.addView(b);

			b = new Button(a);
			b.setText("FireEffect2");
			b.setOnClickListener(ocl);
			ll.addView(b);

			b = new Button(a);
			b.setText("VoltAetherAnim");
			b.setOnClickListener(ocl);
			ll.addView(b);

			b = new Button(a);
			b.setText("WhiteSparkDissipationAnim");
			b.setOnClickListener(ocl);
			ll.addView(b);

			b = new Button(a);
			b.setText("TapAnim");
			b.setOnClickListener(ocl);
			ll.addView(b);

			LayoutParams lParams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
			tdg.getActivity().addContentView(gView, lParams);
		}
	}





	private static void addgold() {
		tdg.getLevel().getMM().getTeam(Teams.BLUE).getPlayer().getPR().add(RT.GOLD,100000);
	}


	private static void setPopMax(int max) {
//		Level level = Rpg.getGame().getLevel() ;
//		if( level != null ){
//			Team t = level.getMM().getTeam(Teams.BLUE);
//			t.getPR().setPopMax(max);
//		}
	}


	private static void spawn(String input) {
		Level level = tdg.getLevel();
		if( level != null ){
			Teams teams = Teams.RED;
			String[] inputs = input.split(" ");

			if( inputs.length > 2 ){
				teams = Teams.getFromString(inputs[2]);
				if( teams == null )
					teams.hashCode(); // Throw NPE
			}
			int lvl = 1;
			if( inputs.length > 3 )
				lvl = Integer.parseInt( inputs[3] );

			Team t = level.getMM().getTeam(teams);
			vector loc = new vector(level.getBackground().getCenteredOn());
			Unit u = Unit.getFromString( inputs[1] , teams , loc );
			Building b=null;
			if( u != null ){
				u.setNearDistSquared(u.getAQ().getFocusRangeSquared()*4);
				u.upgradeToLevel(lvl);
				t.getAm().add( u );
			}
			else{
				b = Building.getFromString(inputs[1], teams, loc);
				if( b != null ){
					b.upgradeToLevel(lvl);
					t.getBm().add( b );
				}
			}
		}
	}

	//	private static void restock() {
	//		Level level = Rpg.getGame().getLevel();
	//		if( level instanceof YourBaseLevel )
	//			((YourBaseLevel)level).restock();
	//	}

	//	private static void save(String input) {
	//		String name = input.substring(input.indexOf(" ")+1);
	//		Level level = Rpg.getGame().getLevel();
	//		if( level instanceof YourBaseLevel ){
	//			YourBaseLevel ybLevel = (YourBaseLevel) level;
	//			//Log.v( TAG , "Button pressed to upload base as new entity under name:" + text.getText().toString() ) ;
	//			ybLevel.onPause();
	//			KingsCastle kc2 = Rpg.getGame();
	//			if( kc2 instanceof KingsCastleCD )
	//				((KingsCastleCD)kc2).saveBaseAndAsyncUpload( ybLevel , name , null , true );
	//			ybLevel.onResume();
	//		}
	//	}

}
