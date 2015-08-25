package com.kingscastle.nuzi.towerdefence.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.level.Level;

import java.util.ArrayList;


/**
 * TODO: document your custom view class.
 */
public class UIView extends RelativeLayout {

	protected static final String TAG = "UIView";

	private static UIViewBuilder mUIViewBuilder = new UIViewBuilder();

	public static void setUIViewBuilder(UIViewBuilder uiViewBuilder){
		if( uiViewBuilder != null )
			mUIViewBuilder = uiViewBuilder;
	}

	public static class UIViewBuilder{
		public void showUIView(final Activity a , final Level level , final UI ui ){

			a.runOnUiThread(new Runnable() {
				@Override
				public void run() {

					View v = uiView;
					//Remove old selectedUIView if it is present
					if( v != null ){
						if( v instanceof UIView )
							((UIView)v).hide();
						uiView = null;
					}


					v = levelLayer;
					//Remove old levelLayer if it is present
					if( v != null ){
						ViewGroup vg = (ViewGroup) v.getParent();
						if( vg != null )
							vg.removeView(v);
						levelLayer = null;
					}



					final UIView uiView_ = (UIView) a.getLayoutInflater().inflate(R.layout.uiview, null);
					uiView_.setVisibility(VISIBLE);
					uiView_.handler = new Handler();


//					ZoomControls zoom = (ZoomControls) uiView_.findViewById(R.id.zoomControls);
//					Zoomer.setup(zoom, kc.getRenderView());


//					TextView gold = (TextView) uiView_.findViewById(R.id.textViewGold);
//					TextView gold2 = (TextView) uiView_.findViewById(R.id.textViewGold2);
//					TextView gold3 = (TextView) uiView_.findViewById(R.id.textViewGold3);
//					TextView food = (TextView) uiView_.findViewById(R.id.textViewFood);
//					TextView food2 = (TextView) uiView_.findViewById(R.id.textViewFood2);
//					TextView food3 = (TextView) uiView_.findViewById(R.id.textViewFood3);
//					TextView wood = (TextView) uiView_.findViewById(R.id.textViewWood);
//					TextView wood2 = (TextView) uiView_.findViewById(R.id.textViewWood2);
//					TextView wood3 = (TextView) uiView_.findViewById(R.id.textViewWood3);
//					TextView pop  = (TextView) uiView_.findViewById(R.id.textViewPop);
//					TextView pop2  = (TextView) uiView_.findViewById(R.id.textViewPop2);
//					TextView pop3  = (TextView) uiView_.findViewById(R.id.textViewPop3);
//					TextView md   = (TextView) uiView_.findViewById(R.id.textViewMD);
//					TextView md2   = (TextView) uiView_.findViewById(R.id.textViewMD2);
//					TextView md3   = (TextView) uiView_.findViewById(R.id.textViewMD3);



				//ResourceDisplay.setViews( gold, gold2,gold3 );//, food, food2, food3, wood, wood2, wood3 , pop , pop2 , pop3 , md , md2 , md3 );
					//ResourceDisplay.setBars( goldBar, foodBar, woodBar );



					// Menu Button
//					ImageButton ib = (ImageButton) uiView_.findViewById(R.id.imageButtonMenuButton);
//					//PauseGameButton.getInstance().uiLayout = uiView_;
//					ib.setOnClickListener( new OnClickListener() {
//						@Override
//						public void onClick(View view) {
//							Menu.showMenu(level);
//						}
//					});




					//					// Purchase Screen
//					ib = (ImageButton) uiView_.findViewById(R.id.imageButtonPurchaseScreen);
//					ib.setVisibility(GONE);


					//Only used in testing version
//					final EditText text = (EditText) uiView_.findViewById(R.id.editText1);
//
//
//					if( TowerDefenceGame.testingVersion ){
//						text.setOnEditorActionListener(new OnEditorActionListener() {
//							private static final String hideThis = "hidethis";
//							@Override
//							public boolean onEditorAction(TextView v_, int actionId_, KeyEvent event_) {
//								try{
//									String input = text.getText().toString();
//									if( hideThis.equals( input ) )
//										text.setVisibility(INVISIBLE);
//									Console.cmd(input);
//								}
//								catch(Exception e){
//									e.printStackTrace();
//								}
//								text.setText("");
//								return false;
//							}
//						});
//					}else
//						text.setVisibility(GONE);


					//
					//					//Inactivity Monitor
					//					InactivityMonitor im = InactivityMonitor.getInstance();
					//					im.act((KingsCastleCD) kc);
					//					uiView_.setOnTouchListener(im);



					//Add view to App
					a.addContentView(uiView_, new ViewGroup.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.MATCH_PARENT ));
					UIView.uiView = uiView_;
					ui.setUIView( uiView_ );




					levelLayer = new RelativeLayout(a);
					levelLayer.setLayerType(View.LAYER_TYPE_SOFTWARE, new Paint());
					uiView_.addLevelLayerViews();
					a.addContentView(levelLayer, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

					uiView_.bringToFront();
//					final ViewTreeObserver observer= uiView_.getViewTreeObserver();
//					observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
//						@Override
//						public void onGlobalLayout() {
//
//							//uiView_.pillageButtonBottom = war.getBottom();
//							//uiView_.troopSelArea.set(troopSelB.getLeft() , troopSelB.getTop() , troopSelB.getRight() , troopSelB.getBottom() );
//							if( observer.isAlive() )
//								observer.removeGlobalOnLayoutListener(this);
//							uiView_.bringToFront();
//						}
//					});
				}
			});
		}
	}

	protected static UIView uiView;
	//private static InactivityMonitor inactivityMonitor;
	protected static RelativeLayout levelLayer;


	public final RectF troopSelArea = new RectF();

	public Handler handler;




	public UIView(Context context) {
		super(context);
		init(null, 0);
	}

	public UIView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0);
	}

	public UIView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs, defStyle);
	}

	private void init(AttributeSet attrs, int defStyle) {
	}


	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}

	/**
	 * Safe to call from outside the UI thread
	 * @param a
	 */
	public static void showUIView( final Activity a , final Level level , final UI ui ){
		mUIViewBuilder.showUIView(a, level, ui);
	}



	private int pillageButtonBottom;

	public int getBottomOfFindPillage(){
		return pillageButtonBottom;
	}




	public void hide() {
		Log.d(TAG, "hide()");
		Rpg.getGame().getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				UIView uiv = uiView;
				if (uiv != null) {
					ViewGroup vg = (ViewGroup) uiv.getParent();
					if (vg != null)
						vg.removeView(uiv);
					uiView = null;
				}

				//InactivityMonitor.getInstance().stop();

				View v = levelLayer;
				if (v != null) {
					ViewGroup vg = (ViewGroup) v.getParent();
					if (vg != null)
						vg.removeView(v);
					levelLayer = null;
				}

			}
		});
	}





	public void bringUIViewToFront() {
		//Log.d(TAG , "bringUIViewToFront()");
		if( Rpg.getGame().uiThreadName.equals(Thread.currentThread().getName()) ){
			bringToFront.run();
		}else{
			Rpg.getGame().getActivity().runOnUiThread(bringToFront);
		}
	}




	private final Runnable bringToFront = new Runnable(){
		@Override
		public void run() {
			{
				final View v = uiView;
				if( v != null )
					v.bringToFront();
			}
		}
	};


	private boolean troopSelectorEnabled;




	public void setUIViewVisibility( final boolean b ){
		//Log.d(TAG , "setUIViewVisibility("+b+")");

		Runnable changeVisibility = new Runnable(){
			@Override
			public void run() {
				final View v = uiView;
				if( v != null ){
					if( b ) v.setVisibility( VISIBLE );

					ValueAnimator animation;
					if( b )
						animation = ValueAnimator.ofFloat( 0f , 1f );
					else
						animation = ValueAnimator.ofFloat( 1f , 0f );

					animation.addUpdateListener(new AnimatorUpdateListener() {
						@Override
						public void onAnimationUpdate(ValueAnimator animation) {
							v.setAlpha( (Float) animation.getAnimatedValue());
						}
					});
					animation.setDuration(500);
					animation.addListener(new AnimatorListenerAdapter(){
						@Override
						public void onAnimationEnd(Animator animation) {
							if( !b ) v.setVisibility( INVISIBLE );
						}
					});
					animation.start();
				}
			}
		};

		if( Rpg.getGame().uiThreadName.equals(Thread.currentThread().getName()) )
			changeVisibility.run();
		else
			Rpg.getGame().getActivity().runOnUiThread(changeVisibility);
	}


	private final static ArrayList<Runnable> addToLevelLayer = new ArrayList<Runnable>();

	protected static void addContentViewToLevelLayer( final View v , final LayoutParams params ){
		RelativeLayout levelLayer = UIView.levelLayer;

		Runnable r = new Runnable(){
			@Override
			public void run() {
				RelativeLayout levelLayer = UIView.levelLayer;
				if( levelLayer != null ){

					if( v.getParent() != null )
						UI.removeThis(v);


					levelLayer.addView(v, params);
				}
			}
		};

		if( levelLayer == null ){
			synchronized( addToLevelLayer ){
				addToLevelLayer.add(r);
			}
		}
		else{
			final Activity act = Rpg.getGame().getActivity();
			act.runOnUiThread(r);
		}
	}


	public void addLevelLayerViews(){
		if( levelLayer == null ){
			//Log.e(TAG, "addLevelLayerViews() called and levelLayer is null");
			return;
		}

		final Activity act = Rpg.getGame().getActivity();

		synchronized( addToLevelLayer ){
			for( Runnable r : addToLevelLayer ){
				act.runOnUiThread(r);
			}

			addToLevelLayer.clear();
		}
		bringUIViewToFront();
	}















}

