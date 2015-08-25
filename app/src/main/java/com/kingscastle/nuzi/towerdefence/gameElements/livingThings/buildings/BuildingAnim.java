package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings;


import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;

import com.kingscastle.nuzi.towerdefence.effects.EffectsManager;
import com.kingscastle.nuzi.towerdefence.effects.Palette;
import com.kingscastle.nuzi.towerdefence.effects.animations.Anim;
import com.kingscastle.nuzi.towerdefence.effects.animations.Fire1Anim;
import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.framework.Graphics;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.framework.Settings;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameUtils.RomanNumerals;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;

public class BuildingAnim extends Anim
{
//	private static Image glow4x4 = Assets.loadImage(R.drawable.glow_4x4);
//	private static Image glow2x2 = Assets.loadImage(R.drawable.glow_2x2);
//	private static Image glow1x1 = Assets.loadImage(R.drawable.glow_1x1);

	private static Paint glowPaint = new Paint();static{
		glowPaint.setXfermode(new PorterDuffXfermode(Mode.DST_OVER));
	}
	private static final Paint yellowPaint = Palette.getPaint(Color.YELLOW,Rpg.getSmallestTextSize());
	static{
		Rpg.applyCooperBlackFont(yellowPaint);
	}


	private final Building building;
	private long buildingDiedAt;


	private ArrayList<Anim> addedDamageEffects;

	private static ArrayList<vector> normalDamageOffsets;
	private ArrayList<vector> damageOffsets;
	private int maxDamagedAnimations = 6;

	private final ColorMatrix cm;

	public BuildingAnim( Building b )
	{
		super( b.getImage() );
		building = b ;
		setLoc( building.loc );
		setLooping( true );
		loadDamageOffsets( b );
		paint = new Paint();
		cm = new ColorMatrix();
		cm.setSaturation(sat);
		paint.setColorFilter(new ColorMatrixColorFilter(cm));
		paint.setXfermode( new PorterDuffXfermode( Mode.DST_OVER ));
	}

	private final boolean incAlpha = true;
	private final float sat = 1;


	@Override
	public boolean act()
	{
		super.act();


		//		if( building.isSelected() ){
		//			if( sat >= 1 )
		//				incAlpha = false;
		//			else if( sat <= 0 )
		//				incAlpha = true;
		//
		//			if( incAlpha )
		//				sat += 0.07f;
		//			else
		//				sat -= 0.07f;
		//			cm.setSaturation(sat);
		//			paint.setColorFilter(new ColorMatrixColorFilter(cm));
		//		}
		//		else if( sat != 1){
		//			sat = 1;
		//			cm.setSaturation(sat);
		//			paint.setColorFilter(new ColorMatrixColorFilter(cm));
		//		}


		return isOver();
	}

    private int lvl = 1;
    private String romanNumeralLvl = "";

	@Override
	public void paint( Graphics g , vector v )
	{

		//g.drawImage(ground, v.x-ground.getWidthDiv2() , v.y-ground.getHeightDiv2());
		if( Settings.showAllAreaBorders || Settings.alwaysShowAreaBorders )
			g.drawRectBorder(building.getPerceivedArea(), v, Color.YELLOW, 1);

		if( building.isSelected() )
			g.drawCircle(v, building.aq.getAttackRange()*1.2f);

		if( building.lq.getLevel() > 1 ) {
            if( lvl != building.lq.getLevel() ){
                romanNumeralLvl = RomanNumerals.getRomanNumberals(building.lq.getLevel());
                lvl = building.lq.getLevel();
            }
            g.drawString( romanNumeralLvl, v.x + Rpg.tenDp, v.y - Rpg.tenDp, yellowPaint);
        }
		//else if( building.isSelected() ){
//			if( building.area.width() >= Rpg.sixtyFourDp )
//				g.drawImage( glow4x4 , -glow4x4.getWidthDiv2() , -glow4x4.getHeightDiv2() , v , glowPaint);
//			else if( building.area.width() >= Rpg.thirtyTwoDp )
//				g.drawImage( glow2x2 , -glow2x2.getWidthDiv2() , -glow2x2.getHeightDiv2() , v , glowPaint);
//			else
//				g.drawImage( glow1x1 , -glow1x1.getWidthDiv2() , -glow1x1.getHeightDiv2() , v , glowPaint);
		//}

		super.paint( g , v );



	}




	private void loadDamageOffsets( Building b )
	{
		ArrayList<vector> offsets = b.getDamageOffsets();
		if( offsets == null && damageOffsets == null )
		{
			if( normalDamageOffsets == null )
			{
				normalDamageOffsets = new ArrayList<>();
				normalDamageOffsets.add(new vector(10*Rpg.getDp(),9*Rpg.getDp()));
				normalDamageOffsets.add(new vector(-12*Rpg.getDp(),-9*Rpg.getDp()));
				normalDamageOffsets.add(new vector(-6*Rpg.getDp(),10*Rpg.getDp()));
				normalDamageOffsets.add(new vector(7*Rpg.getDp(),-5*Rpg.getDp()));
				normalDamageOffsets.add(new vector(8*Rpg.getDp(),-9*Rpg.getDp()));
				normalDamageOffsets.add(new vector(6*Rpg.getDp(),-7*Rpg.getDp()));
				normalDamageOffsets.add(new vector(4*Rpg.getDp(),-11*Rpg.getDp()));
			}
			damageOffsets=new ArrayList<vector>();
			damageOffsets.addAll(normalDamageOffsets);
		}
		else{
			damageOffsets=offsets;
		}
	}


	public void setBuildingDiedAt(long currentTimeMillis)
	{
		buildingDiedAt = currentTimeMillis;
	}

	public void changeToDestroyedBuilding()
	{
		buildingDiedAt = GameTime.getTime() ;

		if( building.getDeadImage() != null )
			setImage( building.getDeadImage() );

		area = null;
	}


	public void removeAllDamagedEffects()
	{
		if( addedDamageEffects == null )
			return;

		synchronized( addedDamageEffects )
		{
			for( Anim dmgFx : addedDamageEffects )
			{
				dmgFx.setOver( true );
				dmgFx.setLooping( false );
				dmgFx.setAliveTime( 0 );
			}
			addedDamageEffects.clear();
		}
	}


	public void removeADamagedEffect()
	{
		if( addedDamageEffects == null )
			return;


		synchronized( addedDamageEffects )
		{
			if( addedDamageEffects.size() == 0 )
				return;

			Anim a = addedDamageEffects.remove( addedDamageEffects.size() - 1 );
			a.setOver( true );
			a.setLooping( false );
			a.setAliveTime( 0 );
			//	a = addedDamageEffects.remove( addedDamageEffects.size() - 1 );
			//	a.setOver( true );
		}
	}




	public void addDamagedEffect( int i, MM mm )
	{
		addARandomDamageEffect( mm );

		switch(i)
		{
		case 2:
			Image damagedImage = building.getDamagedImage();
			if ( damagedImage != null )
				setImage( damagedImage );

			break;
		}
	}


	synchronized void addARandomDamageEffect( MM mm )
	{
		if( addedDamageEffects == null )
			addedDamageEffects = new ArrayList<Anim>();

		synchronized( addedDamageEffects )
		{
			if( addedDamageEffects.size() >= maxDamagedAnimations )
				return;

			Anim a2;

			//a = new LoopingSmoke( loc );
			a2 = new Fire1Anim( loc );

			vector offs = getARandomDamageOffset();
			//a.setOffs( offs );
			a2.setOffs(offs);

			boolean placeEffectsBehind = offs.y < building.area.height()/3;

			if( placeEffectsBehind && Math.random() < 0.5 )
			{
				mm.getEm().add( a2 , EffectsManager.Position.Behind );
				//ManagerManager.getInstance().getEm().add( a , EffectsManager.Position.Behind );
			}
			else
			{
				mm.getEm().add( a2 , EffectsManager.Position.InFront );
				//ManagerManager.getInstance().getEm().add( a , EffectsManager.Position.InFront );
			}

			//addedDamageEffects.add( a );
			addedDamageEffects.add( a2 );
		}
	}


	vector getARandomDamageOffset()
	{
		if( damageOffsets == null )
			return null;

		else
			return damageOffsets.get((int) ( Math.random() * damageOffsets.size() ) );
	}



	@Override
	public boolean isOver()
	{
		if( buildingDiedAt != 0 && buildingDiedAt + Settings.keepDeadAnimsAroundFor < GameTime.getTime() )
			return true;

		else
			return super.isOver();

	}



	@Override
	public void nullify()
	{
		if( addedDamageEffects != null )
			synchronized( addedDamageEffects )
			{
				for( Anim a : addedDamageEffects )
				{
					a.setOver( true );
					a.setLooping( false );
					a.setAliveTime( 0 );
				}
			}

		if( addedInFront != null )
			for( Anim a : addedInFront )
			{
				a.setOver( true );
				a.setLooping( false );
				a.setAliveTime( 0 );
			}

		if( addedBehind != null )
			for( Anim a : addedBehind )
			{
				a.setOver( true );
				a.setLooping( false );
				a.setAliveTime( 0 );
			}
	}


	public int getMaxDamagedAnimations() {
		return maxDamagedAnimations;
	}

	public void setMaxDamagedAnimations(int maxDamagedAnimations) {
		this.maxDamagedAnimations = maxDamagedAnimations;
	}







}