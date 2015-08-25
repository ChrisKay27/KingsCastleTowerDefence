package com.kingscastle.nuzi.towerdefence.effects;

import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.framework.Audio;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.framework.Sound;

import java.util.ArrayList;

class SpecialSoundEffects
{

	//private static final String TAG = "SpecialSoundEffects";

	private static ArrayList<Sound> hitSounds;
	private static ArrayList<Sound> missSounds;

	private static ArrayList<Sound> hammerSounds;
	private static ArrayList<Sound> axeSounds;


	private static ArrayList<Sound> pickaxeSounds;
	private static ArrayList<Sound> bowSounds;

	private static ArrayList<Sound> iceSpellSounds;
	private static ArrayList<Sound> fireSpellSounds;
	private static ArrayList<Sound> lightningSpellSounds;


	static RectF stillDraw;


	static
	{
		loadHitSounds();
		loadMissSounds();
		loadHammerSounds();
		loadPickaxeSounds();
		loadAxeSounds();
		loadBowSounds();
		loadSpellSounds();
	}


	public static boolean playHitSound( float x , float y )
	{
		////Log.d( TAG , "onCreatureDamaged( " + x + "," + y + ","+ dam + ")");
		if( hitSounds == null || hitSounds.isEmpty() )
		{
			return false;
		}

		if( stillDraw.contains( x , y ) )
		{
			Sound s = hitSounds.get( (int) (Math.random()*hitSounds.size()) );

			s.play( 0.5f );
		}

		return false;
	}



	public static boolean playMissSound( float x , float y )
	{
		if( missSounds == null || missSounds.isEmpty() )
			return false;


		if( stillDraw.contains( x , y ) )
		{
			Sound s = missSounds.get( (int) (Math.random()*missSounds.size()) );

			s.play( 0.5f );
		}

		return true;
	}

	public static boolean playBowSound( float x , float y )
	{
		if( bowSounds == null || bowSounds.isEmpty() )
			return false;

		if( stillDraw.contains( x , y ) )
		{
			Sound s = bowSounds.get( (int) (Math.random()*bowSounds.size()) );
			s.play( 0.5f );
		}
		return true;
	}

	public static boolean playSpellCastSound( SpecialEffects.SpellType st , float x , float y )
	{
		switch( st ){
		case FIRE:
			return playFireSpellSound(x,y);
		case HEAL:
			break;
		case ICE:
			return playIceSpellSound(x,y);
		case LIGHTNING:
			return playLightningSpellSound(x,y);
		default:
			break;
		}

		return false;
	}

	public static boolean playIceSpellSound( float x , float y )
	{
		if( iceSpellSounds == null || iceSpellSounds.isEmpty() )
			return false;

		if( stillDraw.contains( x , y ) )
		{
			Sound s = iceSpellSounds.get( (int) (Math.random()*iceSpellSounds.size()) );
			s.play( 0.5f );
		}
		return true;
	}
	public static boolean playFireSpellSound( float x , float y )
	{
		if( fireSpellSounds == null || fireSpellSounds.isEmpty() )
			return false;

		if( stillDraw.contains( x , y ) )
		{
			Sound s = fireSpellSounds.get( (int) (Math.random()*fireSpellSounds.size()) );
			s.play( 0.5f );
		}
		return true;
	}
	public static boolean playLightningSpellSound( float x , float y )
	{
		if( lightningSpellSounds == null || lightningSpellSounds.isEmpty() )
			return false;

		if( stillDraw.contains( x , y ) )
		{
			Sound s = lightningSpellSounds.get( (int) (Math.random()*lightningSpellSounds.size()) );
			s.play( 0.5f );
		}
		return true;
	}



	public static boolean playHammerSound( float x , float y )
	{
		if( hammerSounds == null || hammerSounds.isEmpty() )
			return false;


		if( stillDraw.contains( x , y ) )
		{
			Sound s = hammerSounds.get( (int) (Math.random()*hammerSounds.size()) );
			s.play( 0.3f );
		}

		return true;
	}

	public static boolean playAxeSound( float x , float y )
	{
		if( axeSounds == null || axeSounds.isEmpty() )
			return false;

		if( stillDraw.contains( x , y ) ){
			Sound s = axeSounds.get( (int) (Math.random()*axeSounds.size()) );
			s.play( 0.5f );
		}

		return true;
	}

	public static boolean playPickaxeSound( float x , float y )
	{
		if( pickaxeSounds == null || pickaxeSounds.isEmpty() )
			return false;


		if( stillDraw.contains( x , y ) )
		{
			Sound s = pickaxeSounds.get((int) (Math.random() * pickaxeSounds.size()));
			s.play( 0.5f );
		}

		return true;
	}







	private static void loadHitSounds()
	{
		if( hitSounds == null )
		{
			hitSounds = new ArrayList<Sound>();
			Audio a = Rpg.getGame().getAudio();
			try
			{
				hitSounds.add( a.createSound( "attackSounds/drop_sword.wav"     ));
				hitSounds.add( a.createSound( "attackSounds/swords_collide.wav" ));
				hitSounds.add( a.createSound( "attackSounds/sfx_attack_sword.mp3" ));

//				for( int i = 1 ; i < 10 ; ++i )
//					hitSounds.add( a.createSound( "attackSounds/cast iron clangs - Marker #" + i + ".wav" ));

			}
			catch( Exception e )
			{
				e.printStackTrace();
			}
		}

	}
	private static void loadMissSounds()
	{
		if( missSounds == null )
		{
//			missSounds = new ArrayList<Sound>();
//			Audio a = Rpg.getGame().getAudio();
//			try
//			{
//				missSounds.add( a.createSound( "attackSounds/melee_sound.wav"     ));
//				missSounds.add( a.createSound( "attackSounds/melee_sound2.wav"    ));
//				missSounds.add( a.createSound( "attackSounds/sword_sound.wav"    ));
//				hitSounds.add( a.createSound( "attackSounds/melee_sound.wav"     ));
//				hitSounds.add( a.createSound( "attackSounds/melee_sound2.wav"    ));
//				hitSounds.add( a.createSound( "attackSounds/sword_sound.wav"    ));
//				for( int i = 1 ; i < 14 ; ++i )
//					hitSounds.add( a.createSound( "attackSounds/swish-" + i + ".wav" ));
//			}
//			catch( Exception e )
//			{
//				e.printStackTrace();
//			}
		}

	}
	private static void loadBowSounds()
	{
		if( bowSounds == null )
		{
			bowSounds = new ArrayList<>();
			Audio a = Rpg.getGame().getAudio();
			try
			{
				for( int i = 1 ; i < 14 ; ++i ) {
					Sound s = a.createSound("attackSounds/swish-" + i + ".wav");
					if( s != null ) bowSounds.add( s );
				}
			}
			catch( Exception e )
			{
				e.printStackTrace();
			}
		}
	}
	private static void loadSpellSounds() {
		loadIceSpellSounds();
		loadFireSpellSounds();
		loadLightningSpellSounds();
	}
	private static void loadIceSpellSounds()
	{
		if( iceSpellSounds == null )
		{
			iceSpellSounds = new ArrayList<Sound>();
			Audio a = Rpg.getGame().getAudio();
			try
			{
				for (int i = 1; i < 3; i++)
					iceSpellSounds.add( a.createSound( "attackSounds/ice_damage_0" + i + ".wav" ));
			}
			catch( Exception e )
			{
				e.printStackTrace();
			}
		}
	}
	private static void loadFireSpellSounds()
	{
		if( fireSpellSounds == null )
		{
			fireSpellSounds = new ArrayList<Sound>();
			Audio a = Rpg.getGame().getAudio();
			try
			{
				fireSpellSounds.add( a.createSound( "attackSounds/fireball_shooting.wav" ));
				fireSpellSounds.add( a.createSound( "attackSounds/fireball_shooting2.wav" ));
			}
			catch( Exception e )
			{
				e.printStackTrace();
			}
		}
	}
	private static void loadLightningSpellSounds()
	{
		if( lightningSpellSounds == null )
		{
			lightningSpellSounds = new ArrayList<Sound>();
			Audio a = Rpg.getGame().getAudio();
			try
			{
				lightningSpellSounds.add( a.createSound( "attackSounds/lightning_bolt.wav" ));
				lightningSpellSounds.add( a.createSound( "attackSounds/lightning_bolt2.wav" ));
			}
			catch( Exception e )
			{
				e.printStackTrace();
			}
		}
	}




	private static void loadAxeSounds()
	{
		if( axeSounds == null )
		{
//			axeSounds = new ArrayList<Sound>();
//			Audio a = Rpg.getGame().getAudio();
//			try
//			{
//
//				String commonPath = "workerSounds/axe_chopping_-0";
//				String wav = ".wav";
//
//				for( int i = 1 ; i < 10 ; ++i )
//				{
//					axeSounds.add( a.createSound( commonPath + i + wav ));
//				}
//				axeSounds.add( a.createSound( "workerSounds/axe_chopping_-10" + wav ));
//				axeSounds.add( a.createSound( "workerSounds/axe_chopping_-11" + wav ));
//
//			}
//			catch( Exception e )
//			{
//				e.printStackTrace();
//			}
		}
	}
	private static void loadPickaxeSounds()
	{
		if( pickaxeSounds == null )
		{
//			pickaxeSounds = new ArrayList<Sound>();
//			Audio a = Rpg.getGame().getAudio();
//			try
//			{
//
//				String commonPath = "pickaxe/pickaxe_";
//				String wav = ".wav";
//
//				for( int i = 1 ; i < 12 ; ++i )
//					pickaxeSounds.add( a.createSound( commonPath + i + wav ));
//
//			}
//			catch( Exception e )
//			{
//				e.printStackTrace();
//			}
		}
	}


	private static void loadHammerSounds(){
		if( hammerSounds == null )
		{
//			hammerSounds = new ArrayList<Sound>();
//			Audio a = Rpg.getGame().getAudio();
//			try
//			{
//				hammerSounds.add( a.createSound( "buildingSounds/hammering1.mp3"));
//				hammerSounds.add( a.createSound( "buildingSounds/hammering2.mp3"));
//				hammerSounds.add( a.createSound( "buildingSounds/hammering3.mp3"));
//
//				hammerSounds.add( a.createSound( "buildingSounds/wood_saw1.mp3"));
//				hammerSounds.add( a.createSound( "buildingSounds/wood_saw2.mp3"));
//				hammerSounds.add( a.createSound( "buildingSounds/wood_saw3.mp3"));
//			}
//			catch( Exception e )
//			{
//				e.printStackTrace();
//			}
		}
	}







	public static void setStillDrawArea( RectF rect ){
		SpecialSoundEffects.stillDraw = rect;
	}









}
