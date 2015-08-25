package com.kingscastle.nuzi.towerdefence.framework;

import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Typeface;

import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.TowerDefenceGame;
import com.kingscastle.nuzi.towerdefence.gameElements.ImageFormatInfo;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.Knight;
import com.kingscastle.nuzi.towerdefence.level.Level;
import com.kingscastle.nuzi.towerdefence.ui.UI;



public abstract class Rpg
{
	private static float meleeAttackRangeSquared = 0;
	private static float meleeAttackRange = 0 , standardFocusRange ;

	private static int width ;
	private static int height ;
	private static int widthDiv2 ;
	private static int heightDiv2 ;

	private static int mapWidthInPx;
	private static int mapHeightInPx;
	private static int mapWidthInPxDiv2;
	private static int mapHeightInPxDiv2;

	private static float dpSquared;


	private static RectF normalPerceivedArea;

	private static TowerDefenceGame game;

	private static Typeface demonicTale;
	private static Typeface impact;
	private static Typeface cooperBlack;

	private static Paint xferAddPaint;
	private static Paint dstOverPaint;

	public static boolean unitTests = false;

	public static float twoDp;
	public static float fiveDp;
	public static float eightDp;
	public static float tenDp;
	public static float sixTeenDp;
	public static float gridSize;
	public static float fifteenDp;
	public static float twentyDp;
	public static float thirtyDp;
	public static float thirtyTwoDp;
	public static float sixtyFourDp;
	public static float seventyFiveDp;
	public static float hundDp;
	public static float twoHundDp;
	public static float twoFiddyDp;
	public static float threeHundDp;
	public static float fourHundDp;
	public static float fiveHundDp;

	public static float fiveDpSquared;
	public static float eightDpSquared;
	public static float tenDpSquared;
	public static float fifteenDpSquared;
	public static float sixTeenDpSquared;
	public static float twentyDpSquared;
	public static float thirtyDpSquared;
	public static float thirtyTwoDpSquared;
	public static float fiftyDpSquared;
	public static float hundDpSquared;
	public static float sixteenHundDpSquared;


	public static RectF oneByOneArea;
	public static RectF oneByTwoArea;
	public static RectF twoByTwoArea;
	public static RectF twoByOneArea;
	public static RectF threeByTwoArea;
	public static RectF fourByFourArea;
	public static RectF fourByTwoArea;

	public static RectF guardTowerArea;

	private static float textSize;
	private static float smallTextSize;
	private static float largeTitleTextSize;
	private static float smallestTextSize;

	public static float sp;





	//
	//
	//	public static final CoordConverter cc = new CoordConverter() {
	//		@Override
	//		public Vector getCoordsScreenToMap(float x, float y, Vector intoThis) {
	//			return Rpg.getCoordsScreenToMap(x,y,intoThis);
	//		}
	//
	//		@Override
	//		public Vector getCoordsScreenToMap(Vector v, Vector intoThis) {
	//			return Rpg.getCoordinatesScreenToMap(v,intoThis);
	//		}
	//
	//		@Override
	//		public Vector getCoordsMapToScreen(Vector v, Vector intoThis) {
	//			return Rpg.getCoordinatesMapToScreen(v,intoThis);
	//		}
	//
	//		@Override
	//		public Vector getCoordsMapToScreen(float x, float y, Vector intoThis) {
	//			return Rpg.getCoordinatesMapToScreen(x,y,intoThis);
	//		}
	//	};





	static
	{
		loadDstOverPaint();
	}



	public static void holdThis( TowerDefenceGame game1 )
	{
		game = (TowerDefenceGame) game1;
		width = game.getGraphics().getWidth();
		height = game.getGraphics().getHeight();


		float dp = getDp();
		sp = getDp();//game.getResources().getDimension( R.dimen.an_sp );

		twoDp       = 2 *dp;
		fiveDp      = 5 *dp;
		eightDp     = 8 *dp;
		tenDp       = 10*dp;
		sixTeenDp   = 16*dp;
		twentyDp    = 20*dp;
		thirtyDp    = 30*dp;
		thirtyTwoDp = 32*dp;
		sixtyFourDp = 64*dp;
		seventyFiveDp = 75*dp;
		hundDp      = 100*dp;
		twoHundDp   = 200*dp;
		twoFiddyDp  = 250*dp;
		threeHundDp = 300*dp;
		fourHundDp  = 400*dp;
		fiveHundDp  = 500*dp;


		fiveDpSquared     = (float) Math.pow( fiveDp , 2 );
		eightDpSquared    = (float) Math.pow( eightDp , 2 );
		tenDpSquared      = (float) Math.pow( tenDp , 2 );
		fifteenDpSquared  = (float) Math.pow( tenDp + fiveDp , 2 );

		sixTeenDpSquared     = (float) Math.pow( sixTeenDp , 2 );
		twentyDpSquared      = (float) Math.pow( 20*dp   , 2 );
		thirtyDpSquared      = (float) Math.pow( 30*dp   , 2 );
		thirtyTwoDpSquared   = (float) Math.pow( 32*dp   , 2 );
		fiftyDpSquared       = (float) Math.pow( 50*dp   , 2 );
		hundDpSquared        = (float) Math.pow( 100*dp  , 2 );
		sixteenHundDpSquared = (float) Math.pow( 1600*dp , 2 );

		fourByFourArea = new RectF( -dp*32 , -dp*32 , dp*32 , dp*32 );
		fourByTwoArea  = new RectF( -dp*32 , -dp*16 , dp*32 , dp*16 );
		oneByTwoArea   = new RectF( -eightDp , -sixTeenDp , eightDp , sixTeenDp );
		twoByTwoArea   = new RectF( -dp*16 , -dp*16 , dp*16 , dp*16 );
		twoByOneArea   = new RectF( -sixTeenDp , -eightDp , sixTeenDp , eightDp );
		oneByOneArea   = new RectF( -dp*8  , -dp*8  , dp*8  , dp*8  );
		threeByTwoArea = new RectF( -dp*16 , -dp*16 , dp*32 , dp*16 );
		guardTowerArea = new RectF( -dp*16 , -dp*16 , dp*16 , dp*16 );

		gridSize = sixTeenDp; //GRID SIZE!!!
	}

	private static void loadDstOverPaint()
	{
		if( dstOverPaint == null )
		{
			dstOverPaint = new Paint();
			dstOverPaint.setXfermode( new PorterDuffXfermode( Mode.DST_OVER ));
		}
	}

	public static enum Direction
	{
		E(4), SE(5), S(6), SW(7), W(0), NW(1), N(2), NE(3);
		private final int dir;
		private Direction(int d)
		{
			dir = d;
		}
		public int getDir()
		{
			return dir;
		}
		public Direction opposite()
		{
			switch(this){
			case E:return W;
			case N:return S;
			case NE:return SW;
			case NW:return SE;
			case S:return N;
			case SE:return NW;
			case SW:return NE;
			case W:return E;
			}
			return E;
		}
		public Direction CW90(){
			switch(this){
			case E:return S;
			case N:return E;
			case NE:return SE;
			case NW:return NE;
			case S:return W;
			case SE:return SW;
			case SW:return NW;
			case W:return N;
			}
			return E;
		}
		public Direction CCW90() {
			return this.CW90();
		}
		public Direction CW180() {
			return this.CW90().CW90();
		}
		public Direction flipAboutNSAxis(){
			switch(this){
			case E:return W;
			case N:return N;
			case NE:return NW;
			case NW:return NE;
			case S:return S;
			case SE:return SW;
			case SW:return SE;
			case W:return E;
			}
			return E;
		}
	}

	public static enum Monsters{
		Skeleton,SkeletonSummoner,SkeletonMage,SkeletonArcher,SkeletonSoldier,SkeletonKing,
		Ogre,Dementor,Bandit,Shoe,Gargoyle, GrimReaper, FireDjinn, ZombieMinor,Zombie, SkullFucked, Pigeon
	}
	public static enum Towers{
		WatchTower, GuardTower
	}



	//public static void add(Paint p){
	//	paints.add(p);
	//}

	public static TowerDefenceGame getGame(){
		return game;
	}
	public static Level getLevel() {
		return getGame().getLevel();
	}
	public static UI getUI(){
		return game.getUI();
	}


	public static int getWidth(){
		return width;
	}
	public static int getHeight(){
		return height;
	}
	public static int getWidthDiv2()
	{
		return getGame().getGraphics().getWidthDiv2();
	}


	public static int getHeightDiv2()
	{
		return getGame().getGraphics().getHeightDiv2();
	}


	public static float getDp()
	{
		if( game == null )
			return 1;

		return game.getDp();
	}


	//public static Paint getPaint(int i) {
	//	return paints.get(i-1);
	//}


	public static void print(String s)
	{
		System.out.println(s);
	}



	public static float getMeleeAttackRangeSquared()
	{
		if (meleeAttackRangeSquared == 0 )
			meleeAttackRangeSquared = 16*16*getDp()*getDp();

		return meleeAttackRangeSquared;
	}


	public static float getMeleeAttackRange()
	{
		if( meleeAttackRange == 0)
			meleeAttackRange = 12*getDp();

		return meleeAttackRange;
	}


	public static void setMeleeAttackRangeSquared(int meleeAttackRangeSquared) {
		Rpg.meleeAttackRangeSquared = meleeAttackRangeSquared;
	}






	public static RectF getNormalPerceivedArea()
	{
		if( normalPerceivedArea == null )
		{
			ImageFormatInfo imageFormatInfo = new ImageFormatInfo( R.drawable.skull_fucque_red , 0 ,
					0 , 0 , 1 , 1);
			Image[] images = Assets.loadImages(imageFormatInfo);
			Knight k = new Knight();
			normalPerceivedArea = k.loadPerceivedAreaFromImage(images[0]);
			System.out.println("Loaded normal perceivedarea of " + normalPerceivedArea );
		}
		return normalPerceivedArea;
	}



	public static void setNormalPerceivedArea( RectF normalPerceivedArea )
	{
		Rpg.normalPerceivedArea = normalPerceivedArea;
	}





	public static void applyDTFont( Paint p ){
		p.setTypeface( getDemonicTale() );
	}
	public static void applyCooperBlackFont( Paint p ){
		p.setTypeface( getCooperBlack() );
	}




	public static Typeface getDemonicTale() {
		return demonicTale;
	}
	public static void setDemonicTale(Typeface demonicTale)
	{
		Rpg.demonicTale = demonicTale;
	}

	public static Typeface getImpact() {
		return impact;
	}
	public static void setImpact(Typeface impact) {
		Rpg.impact = impact;
	}

	public static Typeface getCooperBlack() {
		return cooperBlack;
	}
	public static void setCooperBlack(Typeface cooperBlack) {
		Rpg.cooperBlack = cooperBlack;
	}



	public static float getStandardFocusRange()
	{
		if( standardFocusRange == 0 )
			standardFocusRange= 250*250*Rpg.getDp()*Rpg.getDp();

		return standardFocusRange;
	}

	public static Paint getXferAddPaint()
	{
		if( xferAddPaint == null )
		{
			xferAddPaint = new Paint();
			xferAddPaint.setXfermode(new PorterDuffXfermode( Mode.ADD ));
		}

		return xferAddPaint;
	}

	public static void setXferAddPaint(Paint xferAddPaint) {
		Rpg.xferAddPaint = xferAddPaint;
	}

	public static void setTextSize( float textSize )
	{
		Rpg.textSize = textSize;
	}

	public static float getTextSize()
	{
		return textSize;
	}

	public static void setSmallestTextSize(float smallestTextSize)
	{
		Rpg.smallestTextSize = smallestTextSize;
	}

	public static float getSmallestTextSize()
	{
		return smallestTextSize;
	}

	public static void setLargeTitleTextSize(float largeTitleTextSize)	{
		Rpg.largeTitleTextSize = largeTitleTextSize;
	}
	public static float getLargeTitleTextSize()	{
		return largeTitleTextSize;
	}


	public static int getMapWidthInPxDiv2()
	{
		return mapWidthInPxDiv2;
	}

	public static void setMapWidthInPxDiv2(int mapWidthDiv2)
	{
		Rpg.mapWidthInPxDiv2 = mapWidthDiv2;
	}
	public static int getMapHeightInPxDiv2()
	{
		return mapHeightInPxDiv2;
	}

	public static void setMapHeightInPxDiv2(int mapHeightDiv2)
	{
		Rpg.mapHeightInPxDiv2 = mapHeightDiv2;
	}



	public static int getMapWidthInPx()
	{
		return mapWidthInPx;
	}

	public static int getMapHeightInPx()
	{
		return mapHeightInPx;
	}


	public static void setMapHeightInPx( int mapHeightInPx )
	{
		Rpg.mapHeightInPx = mapHeightInPx;
	}

	public static void setMapWidthInPx( int levelWidthInPx )
	{
		Rpg.mapWidthInPx = levelWidthInPx;
	}




	public static float getDpSquared()
	{
		if( dpSquared == 0 )
		{
			dpSquared = getDp()*getDp();
		}
		if( dpSquared == 0 )
		{
			throw new IllegalStateException(" dpSquared == 0 ");
		}
		return dpSquared;
	}

	public static Paint getDstOverPaint()
	{
		loadDstOverPaint();
		return dstOverPaint;
	}







}
