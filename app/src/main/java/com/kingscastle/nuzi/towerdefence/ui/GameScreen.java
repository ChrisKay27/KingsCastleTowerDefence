package com.kingscastle.nuzi.towerdefence.ui;

import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.view.View;
import android.widget.TextView;

import com.kingscastle.nuzi.towerdefence.TowerDefenceGame;
import com.kingscastle.nuzi.towerdefence.effects.EffectsManager;
import com.kingscastle.nuzi.towerdefence.effects.RisingText;
import com.kingscastle.nuzi.towerdefence.effects.animations.Anim;
import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.framework.Graphics;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.framework.Screen;
import com.kingscastle.nuzi.towerdefence.framework.Settings;
import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.level.Level;
import com.kingscastle.nuzi.towerdefence.level.Level.GameState;

import java.util.ArrayList;
import java.util.List;


/**
 * This class is responsible for drawing the graphics such as the animations that it gets from the effects manager
 * and things like the rising texts caused by things like damage.
 */
public class GameScreen extends Screen
{

	private final Level level;
	private final UI ui;

	private final int width = game.getGraphics().getWidth();
	private final int height = game.getGraphics().getHeight();

	private final vector v = new vector();

	//private final RectF stillDrawMold = new RectF(  - width -  ( 2 * width ) / 3  ,  - height - ( 2 * height ) / 3    , width +  ( 2 * width ) / 3   , height +  ( 2 * height ) / 3  );
	private final RectF stillDrawMold = new RectF( - width , - height , width , height );
	private final RectF onScreenDrawMold;

	private long start_time;
	private int fps;
	private TextView fpsTextView;


	private final RectF stillDraw;
	private final RectF onScreen;

	private final Paint alphaPaint;


	private final float screenWidthDiv2  = Rpg.getWidthDiv2();
	private final float screenHeightDiv2 = Rpg.getHeightDiv2();
	private boolean nightTime = false;

	//private final Fire1Anim[] fires;


	public GameScreen( TowerDefenceGame game , Level lvl, UI ui_ )
	{
		super( game );

		level = lvl;
		ui = ui_;
		alphaPaint = new Paint();
		alphaPaint.setAlpha( 255 );

		onScreen = ui_.getOnScreenArea();
		stillDraw = ui.getStillDrawArea();

		onScreenDrawMold = new RectF( - Rpg.getWidthDiv2() - Rpg.sixTeenDp ,  - Rpg.getHeightDiv2() - Rpg.sixTeenDp , Rpg.getWidthDiv2() + Rpg.sixTeenDp , Rpg.getHeightDiv2() + Rpg.sixTeenDp);


		fpsTextView = (TextView) game.getActivity().findViewById(R.id.fpsTextView);

	}


	@Override
	public void update()
	{

	}

	Paint xfer = new Paint();{
		xfer.setXfermode(new PorterDuffXfermode( PorterDuff.Mode.CLEAR ));
		xfer.setARGB(0,0,0,0);
	}

	public static final Paint overlayPaint = new Paint();
	private int nightAlpha = 0;

	/**
	 * Occurs on the AndroidFastRenderViewThread
	 * @param g
	 */
	@Override
	public void paint( Graphics g )
	{
		if( level.getBackground() == null )
			return;

		if(nightTime) {
			if( nightAlpha < 128 ) nightAlpha++;
			else nightAlpha = 128;
		}
		else{
			if( nightAlpha > 0 ) nightAlpha--;
			else nightAlpha = 0;
		}
		overlayPaint.setAlpha(nightAlpha);
		g.getCanvas().drawColor(overlayPaint.getColor());


		vector centeredOn = level.getBackground().getCenteredOn();

		int centeredOnX = centeredOn.getIntX();
		int centeredOnY = centeredOn.getIntY();

		stillDraw.set(stillDrawMold);
		stillDraw.offset(centeredOnX, centeredOnY);

		onScreen.set(onScreenDrawMold);
		onScreen.offset( centeredOnX , centeredOnY );


		GameState state = game.getState();

		if( true ) { // state == GameState.InGamePlay

			//behindEverything.paint( g , behindEverything.loc );

			vector loc;

			ui.paintBeforePendingBuilding(g);


			RisingText rt;
			MM mm = level.getMM();

			ArrayList<RisingText> risingTexts = mm.getRtm().getTexts();

			for (int i = risingTexts.size() - 1; i > -1; --i) {
				try {
					rt = risingTexts.get(i);

					if (rt == null)
						continue;

					loc = rt.loc;

					if (stillDraw(onScreen, loc) && rt.shouldDrawThis)
						g.drawString(rt.getText(), loc.x - centeredOn.x + screenWidthDiv2, loc.y - centeredOn.y + screenHeightDiv2, rt.getPaint());

				} catch (Exception e) {
					i = risingTexts.size() - 1;
					e.printStackTrace();
				}
			}

			EffectsManager em = mm.getEm();

			{
				List<Anim> lightEffectsInFrontAnims = em.getLightEffectsInFrontAnimations();
				for (Anim a : lightEffectsInFrontAnims) {
					if (a.shouldDrawThis) {
						loc = a.loc;
						v.x = loc.x - centeredOn.x + screenWidthDiv2;
						v.y = loc.y - centeredOn.y + screenHeightDiv2;
						a.paint(g, v);
						a.setWasDrawnThisFrame(true);
					} else
						a.setWasDrawnThisFrame(false);
				}
			}

			{
				List<Anim> inFrontAnims = em.getInFrontAnimations();
				for (Anim a : inFrontAnims) {
					if (a.shouldDrawThis) {
						loc = a.loc;
						v.x = loc.x - centeredOn.x + screenWidthDiv2;
						v.y = loc.y - centeredOn.y + screenHeightDiv2;
						a.paint(g, v);
						a.setWasDrawnThisFrame(true);
					} else
						a.setWasDrawnThisFrame(false);
				}
			}

			{
				List<Anim> sortedAnims = em.getSortedAnimations();
				for (Anim a : sortedAnims) {
					if (a.shouldDrawThis) {
						loc = a.loc;
						v.x = loc.x - centeredOn.x + screenWidthDiv2;
						v.y = loc.y - centeredOn.y + screenHeightDiv2;
						a.paint(g, v);
						a.setWasDrawnThisFrame(true);
					} else
						a.setWasDrawnThisFrame(false);
				}
			}
			{
				List<Anim> behindAnims = em.getBehindAnimations();
				for (Anim a : behindAnims) {
					if (a.shouldDrawThis) {
						loc = a.loc;
						v.x = loc.x - centeredOn.x + screenWidthDiv2;
						v.y = loc.y - centeredOn.y + screenHeightDiv2;
						a.paint(g, v);
						a.setWasDrawnThisFrame(true);
					} else
						a.setWasDrawnThisFrame(false);

				}
			}

			ui.paintAfterPendingBuilding( g );

			level.getBackground().drawBackground( g );

			{
				List<Anim> xFerAddAnims = em.getXFerAddAnims();
				for (Anim a : xFerAddAnims) {
					if (a.shouldDrawThis) {
						loc = a.loc;
						v.x = loc.x - centeredOn.x + screenWidthDiv2;
						v.y = loc.y - centeredOn.y + screenHeightDiv2;
						a.paint(g, v);
						a.setWasDrawnThisFrame(true);
					} else
						a.setWasDrawnThisFrame(false);
				}
			}

			fps++;
			if( GameTime.getTime() - start_time > 1000 ){
				fpsTextView.setText(fps + " FPS");
				start_time = GameTime.getTime();
				fps = 0;
				if( Settings.showFps )
					fpsTextView.setVisibility(View.VISIBLE);
			}
		}

	}






	private boolean stillDraw( RectF r, vector bLoc )
	{
		if ( r.contains( bLoc.x , bLoc.y ) )
			return true;
		else
			return false;
	}



	@Override
	public void resume() {

	}

	@Override
	public void dispose()
	{
		nullify();
	}

	@Override
	public void backButton() {
		pause();
	}


	public int getFps() {
		return fps;
	}

	public void nullify(){
	}

	@Override
	public void pause() {
	}


	public RectF getStillDrawRect() {
		return stillDraw;
	}

	public RectF getOnScreen() {
		return onScreen;
	}


	public void setNightTime(boolean nightTime) {
		this.nightTime = nightTime;
	}

}
