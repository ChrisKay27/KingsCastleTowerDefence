package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings;

import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.effects.EffectsManager;
import com.kingscastle.nuzi.towerdefence.effects.animations.Anim;
import com.kingscastle.nuzi.towerdefence.effects.animations.Backing;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.framework.Image;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.gameElements.Cost;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingQualities;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.Humanoid;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.Unit;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.HumanArmoredSoldier;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.HumanSoldier;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.Knight;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.army.Warrior;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.attacks.AttackerQualities;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;
import com.kingscastle.nuzi.towerdefence.gameUtils.Age;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;
import com.kingscastle.nuzi.towerdefence.teams.Teams;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Barracks extends Building
{
	private static final String TAG = "Barracks";
	public static final Buildings name = Buildings.Barracks;

	private static final float toFarDistance = 60*60* Rpg.getDpSquared();

	private static RectF staticPerceivedArea;

	private static Image damagedImage , deadImage , iconImage;
	private static final Image image = Assets.loadImage(R.drawable.barracks_bronze_age);

	private static final Cost cost = new Cost( 75 , 0 , 0 , 0 );

	private static final AttackerQualities staticAttackerQualities;
	private static final LivingQualities staticLivingQualities;
	private static ArrayList<vector> staticDamageOffsets;


	static
	{
		staticAttackerQualities = new AttackerQualities();
		staticAttackerQualities.setFocusRangeSquared(15000*Rpg.getDpSquared());
		staticAttackerQualities.setAttackRangeSquared(15000*Rpg.getDpSquared());

		staticLivingQualities = new LivingQualities();staticLivingQualities.setRequiresAge(Age.STONE); staticLivingQualities.setRequiresTcLvl(1);
		staticLivingQualities.setRangeOfSight(250);
		staticLivingQualities.setLevel( 1 );
		staticLivingQualities.setFullHealth(250);
		staticLivingQualities.setHealth(250);
		staticLivingQualities.setFullMana(125);
		staticLivingQualities.setMana(125);
		staticLivingQualities.setHpRegenAmount(1);
		staticLivingQualities.setRegenRate(1000);
		staticLivingQualities.setArmor( 2 );  staticLivingQualities.setdArmorAge( 0 ); staticLivingQualities.setdArmorLvl( 2 );

		staticPerceivedArea = new RectF(Rpg.guardTowerArea);

		staticLivingQualities.setAge( Age.STONE );
		staticLivingQualities.setMaxLevel(4);
	}


	private long lastCheckedSoldiers;

	private List<Class<? extends Unit>> soldersForLvls = new ArrayList<>();
	{
		soldersForLvls.add(Warrior.class);
		soldersForLvls.add(HumanSoldier.class);
		soldersForLvls.add(HumanArmoredSoldier.class);
		soldersForLvls.add(Knight.class);
	}
	private vector troopDeployLoc = new vector();
	protected List<vector> troopDeployLocs = new ArrayList<>();
	{
		troopDeployLocs.add(new vector());
		troopDeployLocs.add(new vector());
		troopDeployLocs.add(new vector());

		setAQ(new AttackerQualities(staticAttackerQualities, getLQ().getBonuses()));
	}

	private List<Humanoid> soldiers = new ArrayList<>();
	private final vector v = new vector(500,1000000);
	private boolean killSoldiers;
	private OnDeathListener deathListener = new OnDeathListener() {
		@Override
		public void onDeath(LivingThing lt) {
			soldiers.remove(lt);
		}
	};


	public Barracks(){
		super(name, null);
		loadPerceivedArea();
	}
	public Barracks(vector v, Teams t){
		super( name , t );
		setLoc(v);
		setTeam(t);
	}


	@Override
	public boolean act() {
		boolean superAct =  super.act();
		if( killSoldiers ) {
			for (LivingThing u : soldiers){
				u.removeDL(deathListener);
				u.die();
			}
			soldiers.clear();
			killSoldiers = false;
		}
		if( lastCheckedSoldiers + 10000 < GameTime.getTime() ){
			if( soldiers.size() < 3 ){
				//we add to soldiers in this loop so we cant keep calling soldiers.size() or we wont create enough
				int numSoldiers = soldiers.size();
				for( int i = 0 ; i < 3-numSoldiers; i++){
					Class<? extends Unit> c = soldersForLvls.get(lq.getLevel()-1);
					Object[] args = {getMM().getGridUtil().getWalkableLocNextToThis(v,getArea()),team};
					Unit u=null;
					try {
						u = c.getConstructor(vector.class,Teams.class).newInstance(args);
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					}

					u.addDL(deathListener);
					soldiers.add(u);
					u.setStayHere(troopDeployLocs.get(soldiers.indexOf(u)));
					getMM().add(u);
					//w.walkToAndStayHere(new Vector(loc));
				}
			}
			lastCheckedSoldiers = GameTime.getTime();
		}
		for(Humanoid lt : soldiers){
			if( lt.loc.distanceSquared(troopDeployLocs.get(soldiers.indexOf(lt))) > toFarDistance){
				lt.setStayHere(troopDeployLocs.get(soldiers.indexOf(lt)));
				lt.setTarget(null);
				lt.forgetAboutTargetingUntil(GameTime.getTime()+400);
				//lt.walkTo(new Vector(loc).add(0,Rpg.twentyDp));
			}
		}
		return superAct;
	}


	@Override
	public boolean create(MM mm) {
		boolean superCreate = super.create(mm);
		setDeployLoc(loc.x , loc.y+Rpg.twentyDp);
		return superCreate;
	}

	public void setDeployLoc(float x, float y) {
		troopDeployLoc.set(x,y);
		troopDeployLocs.get(0).set(x,y).add(-Rpg.tenDp, -Rpg.tenDp);
		troopDeployLocs.get(1).set(x,y).add(Rpg.tenDp,-Rpg.tenDp);
		troopDeployLocs.get(2).set(x,y).add(0, Rpg.tenDp);
	}

	public vector getTroopDeployLoc() {
		return troopDeployLoc;
	}


	
	@Override
	public void upgrade(){
		super.upgrade();
		adjustAnimForLevel(lq.getLevel());
		killSoldiers = true;
		lastCheckedSoldiers = 0;
	}

	@Override
	protected void addAnimationToEm(Anim a, boolean sorted, EffectsManager em) {
		backing.setSize(Backing.TINY);
		super.addAnimationToEm(a, sorted, em);
	}

	void loadDamageOffsets()
	{
		float dp = Rpg.getDp();

		staticDamageOffsets = new ArrayList<vector>();
		staticDamageOffsets.add(new vector(Math.random() * -5 * dp, -15 * dp + Math.random() * 30 * dp));
		staticDamageOffsets.add(new vector(Math.random() * -5 * dp, -15 * dp + Math.random() * 30 * dp));
		staticDamageOffsets.add( new vector( Math.random()*5*dp , -15*dp + Math.random()*30*dp ) );
		staticDamageOffsets.add( new vector( Math.random()*5*dp , -15*dp + Math.random()*30*dp ) );
	}

	@Override
	public ArrayList<vector> getDamageOffsets()
	{
		if( staticDamageOffsets == null )
			loadDamageOffsets();

		return staticDamageOffsets;
	}

	protected void adjustAnimForLevel( int lvl ){
		BuildingAnim bAnim = getBuildingAnim();
		if( bAnim != null )
			bAnim.setImage(image);
//		BuildingAnim bAnim = getBuildingAnim();
//		if( bAnim != null ){
//			if( lvl < 7 )
//				bAnim.setImage(watchTowerImage);
//			else if( lvl < 14 )
//				bAnim.setImage(guardTowerImage);
//			else if( lvl >= 14 )
//				bAnim.setImage(stoneTowerImage);
//
//			//			float scale = lvl%7;
//			//			//bAnim.setScale(1.1f);
//			//			bAnim.setScale(1f+((scale-1)/10f));
//		}
//		backing.setSize(Backing.MEDIUM);
	}

	@Override
	public Image getImage() {
		loadImages();
		int lvl = lq.getLevel();

//		if( lvl < 7 )
//			return watchTowerImage;
//		else if( lvl < 14 )
//			return guardTowerImage;
//		else if( lvl >= 14 )
//			return stoneTowerImage;

		return image;
	}
	@Override
	public Image getDamagedImage() {
		loadImages();
		return image;
	}
	@Override
	public Image getDeadImage() {
		loadImages();
		return deadImage;
	}
	@Override
	public Image getIconImage() {
		loadImages();
		return iconImage;
	}
	@Override
	public void loadImages(){
		damagedImage = image;
		deadImage = Assets.smallDestroyedBuilding;
	}



	/** returns a rectangle to be placed with its center on the mapLocation of the tower */
	@Override
	public RectF getPerceivedArea(){
		loadPerceivedArea();
		return staticPerceivedArea;
	}

	public void setPerceivedSpriteArea(RectF perceivedSpriteArea2){
		staticPerceivedArea = perceivedSpriteArea2;
	}

	@Override
	public RectF getStaticPerceivedArea()
	{
		return staticPerceivedArea;
	}

	@Override
	public void setStaticPerceivedArea(RectF staticPercArea)
	{
		staticPerceivedArea = staticPercArea;
	}



	@Override
	public String toString()
	{
		return TAG;
	}

	@Override
	public String getName()
	{
		BuildingAnim bAnim = getBuildingAnim();
//		if( bAnim != null ){
//			if( bAnim.getImage() == guardTowerImage )
//				return GUARD_TOWER;
//			else if( bAnim.getImage() == stoneTowerImage )
//				return STONE_TOWER;
//		}


		return TAG;
	}





	@Override
	public Cost getCosts() {
		return cost;
	}




	@Override
	public LivingQualities getNewLivingQualities()
	{
		return new LivingQualities(staticLivingQualities);
	}





	@Override
	protected AttackerQualities getStaticAQ() { return staticAttackerQualities; }
	@Override
	protected LivingQualities getStaticLQ() { return staticLivingQualities;   }



}
