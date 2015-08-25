package com.kingscastle.nuzi.towerdefence.ui.buttons;

import android.app.Activity;
import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.implementation.ImageDrawable;
import com.kingscastle.nuzi.towerdefence.R;


public class SButton extends FrameLayout {
	private static final String TAG = "SButton";

	protected static final Image buttonBase = Assets.loadImage( R.drawable.neutral_button_base );
	protected static final ImageDrawable buttonBaseDrawable = new ImageDrawable( buttonBase.getBitmap() , new Paint());

	private static final Image    questionMarkImage = Assets.loadImage(R.drawable.question_mark);
	private static final ImageDrawable questionMark = new ImageDrawable( questionMarkImage.getBitmap() , new Paint() );

	private static final Image redMinusImage = Assets.loadImage( R.drawable.red_minus );
	private static final ImageDrawable redMinus = new ImageDrawable( redMinusImage.getBitmap() , 0 , 0 , new Paint() );

	private static final Image lockImage = Assets.loadImage( R.drawable.lock );
	private static final ImageDrawable lock = new ImageDrawable( lockImage.getBitmap() , 0 , 0 , new Paint() );





	static{
		Log.e(TAG,"buttonBase size: " + buttonBase.getHeight() + " , " + buttonBase.getWidth() );
	}
	protected static final Paint grayscalePaint = new Paint();
	static{
		ColorMatrix cm = new ColorMatrix();
		cm.setSaturation(0);
		grayscalePaint.setColorFilter(new ColorMatrixColorFilter(cm));
	}

	protected Activity a;


	public SButton(Activity a) {
		super(a);
		this.a = a;
		setBackgroundDrawable(buttonBaseDrawable);
	}
	public SButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public SButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}





	public void setGrayedOut(boolean grayedOut) {
		if (grayedOut)
			setLayerType(View.LAYER_TYPE_HARDWARE, grayscalePaint);
		else
			setLayerType(View.LAYER_TYPE_NONE, null);
	}




	public void addQuestionMark( OnClickListener ocl ){
		ImageButton qm = new ImageButton( getContext() );
		qm.setBackgroundDrawable( questionMark );
		qm.setOnClickListener(ocl);
		qm.setTranslationX(buttonBase.getWidth()-questionMarkImage.getWidth());
		//remove.setTranslationY(Rpg.getDp()*2);
		addView(qm, new LayoutParams( questionMarkImage.getWidth(), questionMarkImage.getHeight() ) );
		qm.bringToFront();
	}



	public void addRedMinus( OnClickListener ocl ){
		ImageButton remove = new ImageButton( getContext() );
		remove.setBackgroundDrawable( redMinus );
		remove.setOnClickListener(ocl);
		remove.setTranslationX(buttonBase.getWidth()-redMinusImage.getWidth());
		//remove.setTranslationY(Rpg.getDp()*2);
		addView(remove, new LayoutParams( redMinusImage.getWidth(), redMinusImage.getHeight() ) );
		remove.bringToFront();
	}

	public void addLockOverlay(){
		setGrayedOut(true);
		ImageView lockView = new ImageView(getContext());
		lockView.setBackgroundDrawable(lock);
		addView(lockView, new LayoutParams(buttonBase.getWidth(), buttonBase.getHeight() ) );
		lockView.bringToFront();
	}
}
