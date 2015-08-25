package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings;

import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.effects.EffectsManager;
import com.kingscastle.nuzi.towerdefence.effects.animations.Anim;
import com.kingscastle.nuzi.towerdefence.effects.animations.Backing;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.gameElements.Cost;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingQualities;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameUtils.Age;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.teams.Teams;

import java.util.ArrayList;

public class RuneStone extends Building{

	private static final String TAG = "RuneStone";

	private static final RectF staticPerceivedArea = new RectF( -Rpg.eightDp , 0 , Rpg.eightDp , Rpg.sixTeenDp );

	private static final Image image = Assets.loadImage(R.drawable.rune_stone);

	private static final LivingQualities staticLivingQualities;

	private static Cost cost = new Cost( 1000 , 1000 , 1000 , 0 );

	public static final Buildings name = Buildings.RuneStone;

	static{
		staticLivingQualities = new LivingQualities(); staticLivingQualities.setRequiresAge(Age.STONE); staticLivingQualities.setRequiresTcLvl(4);

		staticLivingQualities.setLevel( 1 );
		staticLivingQualities.setFullHealth(300);
		staticLivingQualities.setHealth(300);
		staticLivingQualities.setHpRegenAmount(1);
		staticLivingQualities.setRegenRate(1000);


		staticLivingQualities.setAge( Age.STONE );

		//		images[i++] = Assets.loadImage(R.drawable.statue_monk);
		//		images[i++] = Assets.loadImage(R.drawable.statue_monk2);
		//		images[i++] = Assets.loadImage(R.drawable.statue_monk3);
	}



	private final Image damagedImage = image;
	private final Image deadImage = Assets.loadImage( R.drawable.small_rubble );
	private RectF percArea = staticPerceivedArea;





	@Override
	protected LivingQualities getStaticLQ() {
		return staticLivingQualities;
	}



	public RuneStone(){
		super(name);
	}

	public RuneStone(vector v, Teams t)
	{
		super(name );

		setTeam(t);
		setLoc(v);
	}



	@Override
	public boolean canLevelUp() {
		return false;
	}


	@Override
	public void upgrade(){
	}






	@Override
	public void addFlagToAnim(){
	}


	@Override
	public void loadAnimation( MM mm )
	{
		super.loadAnimation( mm );
		if( buildingAnim != null )
			buildingAnim.setMaxDamagedAnimations( 1 );
		backing.setSize(Backing.TINY);
		adjustBackingOffs( backing );
	}

	@Override
	protected void addAnimationToEm(Anim a, boolean sorted, EffectsManager em)
	{
		em.add( a , EffectsManager.Position.Sorted );
		em.add( backing, EffectsManager.Position.Behind );
	}




	private static void adjustBackingOffs( Backing backing ){
		if( backing != null )
			backing.setOffs(0,Rpg.eightDp);
	}




	@Override
	public Image getImage() {
		return image;
	}
	@Override
	public Image getDamagedImage() {
		return damagedImage;
	}
	@Override
	public Image getDeadImage() {

		return deadImage;
	}

	@Override
	public void loadImages()
	{
	}






	/**
	 * returns a rectangle to be placed with its center on the mapLocation of the tower
	 */
	@Override
	public RectF getPerceivedArea()
	{
		loadPerceivedArea();
		return percArea;
	}

	public void setPerceivedSpriteArea( RectF perceivedSpriteArea2 ){
	}

	@Override
	public RectF getStaticPerceivedArea(){
		return percArea;
	}

	@Override
	public void setStaticPerceivedArea(RectF staticPercArea){
	}


	@Override
	protected void loadPerceivedArea(){
		percArea = staticPerceivedArea; //This might cause them to sort properly
	}








	@Override
	public Cost getCosts() {
		return cost;
	}
	public static void setCost(Cost cost) {
		RuneStone.cost = cost;
	}





	private static ArrayList<vector> staticDamageOffsets;
	void loadDamageOffsets(){
		float dp = Rpg.getDp();
		staticDamageOffsets = new ArrayList<vector>();
		staticDamageOffsets.add(new vector(-4*dp,4*dp));
		staticDamageOffsets.add(new vector(-2*dp,2*dp));
		staticDamageOffsets.add(new vector(4*dp,-4*dp));
		staticDamageOffsets.add(new vector(2*dp,-2*dp));

	}
	@Override
	public ArrayList<vector> getDamageOffsets()
	{
		if( staticDamageOffsets == null )
		{
			loadDamageOffsets();
		}
		return staticDamageOffsets;
	}





	@Override
	public LivingQualities getNewLivingQualities()
	{
		return new LivingQualities(staticLivingQualities);
	}




	@Override
	public String toString() {
		return TAG;
	}
	@Override
	public String getName() {
		return name.getPrintableName();
	}






















}
