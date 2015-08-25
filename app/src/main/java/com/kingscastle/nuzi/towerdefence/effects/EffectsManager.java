package com.kingscastle.nuzi.towerdefence.effects;

import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import com.kingscastle.nuzi.towerdefence.effects.animations.Anim;
import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.framework.implementation.QuickSort;
import com.kingscastle.nuzi.towerdefence.gameElements.managment.MM;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class EffectsManager
{
	private static final String TAG = "EffectsManager";

	private final List<Runnable> runnables = new ArrayList<>();



	public enum Position{ LightEffectsInFront, InFront, Sorted,  Behind, XFerAdd }


	private final List<Anim> sortedAnimsList = new LinkedList<>();
	private final List<Anim> lightEffectsInFrontAnimsList = new LinkedList<>();
	private final List<Anim> inFrontAnimsList = new LinkedList<>();
	private final List<Anim> behindAnimsList = new LinkedList<>();
	private final List<Anim> xFerAddAnimsList = new LinkedList<>();


//	private final Animation[] behindAnims1  = new Animation[ 2000 ];
//	private final Animation[] sortedAnims1  = new Animation[ 6500 ];
//	private final Animation[] inFrontAnims1 = new Animation[ 2000 ];
//	private final Animation[] xFerAddAnims1 = new Animation[ 2000 ];

	//	private final Animation[] sortedAnims1_tl  = new Animation[ 2000 ];
	//	private final Animation[] sortedAnims1_tr  = new Animation[ 2000 ];
	//	private final Animation[] sortedAnims1_bl  = new Animation[ 2000 ];
	//	private final Animation[] sortedAnims1_br  = new Animation[ 2000 ];


//	private final Animation[] behindAnims2  = new Animation[ 2000 ];
//	private final Animation[] sortedAnims2  = new Animation[ 6500 ];
//	private final Animation[] inFrontAnims2 = new Animation[ 2000 ];
//	private final Animation[] xFerAddAnims2 = new Animation[ 2000 ];

	//	private final Animation[] sortedAnims2_tl  = new Animation[ 2000 ];
	//	private final Animation[] sortedAnims2_tr  = new Animation[ 2000 ];
	//	private final Animation[] sortedAnims2_bl  = new Animation[ 2000 ];
	//	private final Animation[] sortedAnims2_br  = new Animation[ 2000 ];


//	private Animation[] behindAnims  = behindAnims1  ; private int behindAnimsSize  = 0;
//	private Animation[] sortedAnims  = sortedAnims1  ; private int sortedAnimsSize  = 0;
//	private Animation[] inFrontAnims = inFrontAnims1 ; private int inFrontAnimsSize = 0;
//	private Animation[] xFerAddAnims = xFerAddAnims1 ; private int xFerAddAnimsSize = 0;

	//	private final Animation[] sortedAnims_tl  = sortedAnims1_tl  ; private final int sortedAnims_tlSize  = 0;
	//	private final Animation[] sortedAnims_tr  = sortedAnims1_tr  ; private final int sortedAnims_trSize  = 0;
	//	private final Animation[] sortedAnims_bl  = sortedAnims1_bl  ; private final int sortedAnims_blSize  = 0;
	//	private final Animation[] sortedAnims_br  = sortedAnims1_br  ; private final int sortedAnims_brSize  = 0;

//	private final ArrayList<Animation> needsToBeAddedInfrontAnims = new ArrayList<Animation>( 100 );
//	private final ArrayList<Animation> needsToBeAddedSortedAnims  = new ArrayList<Animation>( 100 );
//	private final ArrayList<Animation> needsToBeAddedBehindAnims  = new ArrayList<Animation>( 100 );
//	private final ArrayList<Animation> needsToBeAddedXFerAddAnims = new ArrayList<Animation>( 100 );
//
//	private Animation[] drawableBehindAnims;
//	private Animation[] drawableSortedAnims;
//	private Animation[] drawableInFrontAnims;
//	private Animation[] drawableXFerAddAnims;
//
//	private final Animation[] drawableBehindAnims1 = new Animation[drawableBehindAnimsArraySize];
//	private final Animation[] drawableSortedAnims1 = new Animation[drawableSortedAnimsArraySize];
//	private final Animation[] drawableInFrontAnims1 = new Animation[drawableInFrontAnimsArraySize];
//	private final Animation[] drawableXFerAddAnims1 = new Animation[drawableXFerAddAnimsArraySize];
//
//	private final Animation[] drawableBehindAnims2 = new Animation[drawableBehindAnimsArraySize];
//	private final Animation[] drawableSortedAnims2 = new Animation[drawableSortedAnimsArraySize];
//	private final Animation[] drawableInFrontAnims2 = new Animation[drawableInFrontAnimsArraySize];
//	private final Animation[] drawableXFerAddAnims2 = new Animation[drawableXFerAddAnimsArraySize];
//
//	private int drawableBehindAnimsSize  = 0;
//	private int drawableSortedAnimsSize  = 0;
//	private int drawableInFrontAnimsSize = 0;
//	private int drawableXFerAddAnimsSize = 0;

	//private final ArrayList<Animation> deadAnims = new ArrayList<Animation>( 200 );

	private long sortedAt , updateListAt , sortAllAt;
	private final long sortEvery = 400;

	private final MM mm;

	public EffectsManager(MM mm) {
		this.mm = mm;
	}

	@WorkerThread
	public void act()
	{
		synchronized (runnables) {
			for (Runnable r : runnables)
				r.run();
			runnables.clear();
		}

		if( sortAllAt < System.currentTimeMillis() )
		{
			sortAllAt = System.currentTimeMillis() + 1000;

			//			if( behindAnimsSize != 0 )
			//				QuickSort.quicksortY(behindAnims,0, behindAnimsSize-1);

			if( !sortedAnimsList.isEmpty() ) {
				Anim[] sorted;
				synchronized (sortedAnimsList) {
					sorted = new Anim[sortedAnimsList.size()];
					sorted = sortedAnimsList.toArray(sorted);
				}
				QuickSort.quicksortY(sorted, 0, sorted.length - 1);
				synchronized (sortedAnimsList) {
					sortedAnimsList.clear();
					Collections.addAll(sortedAnimsList,sorted);
				}
			}

			if( !xFerAddAnimsList.isEmpty() ) {
				Anim[] sorted;
				synchronized (xFerAddAnimsList) {
					sorted = new Anim[xFerAddAnimsList.size()];
					sorted = xFerAddAnimsList.toArray(sorted);
				}
				QuickSort.quicksortY(sorted, 0, sorted.length - 1);
				synchronized (xFerAddAnimsList) {
					xFerAddAnimsList.clear();
					Collections.addAll(xFerAddAnimsList,sorted);
				}
			}
//			if( !inFrontAnimsList.isEmpty() ) {
//				inFrontAnimsList = new LinkedList<>(QuickSort.sort(inFrontAnimsList));
//			}

//			if( !xFerAddAnimsList.isEmpty() ) {
//				xFerAddAnimsList = new LinkedList<>(QuickSort.sort(xFerAddAnimsList));
//			}
		}

		ShouldDrawAnimCalcer sdac = mm.getSdac();


		synchronized(lightEffectsInFrontAnimsList) {
			Iterator<Anim> i = lightEffectsInFrontAnimsList.listIterator();
			while (i.hasNext()) {
				Anim a = i.next();
				a.shouldDrawThis = sdac.shouldThisAnimBeDrawn( a );
				if (a.act()) {
					a.setOver(true);
					i.remove();
				}
			}
		}
		synchronized(inFrontAnimsList) {
			Iterator<Anim> i = inFrontAnimsList.listIterator();
			while (i.hasNext()) {
				Anim a = i.next();
				a.shouldDrawThis = sdac.shouldThisAnimBeDrawn( a );
				if (a.act()) {
					a.setOver(true);
					i.remove();
				}
			}
		}
		synchronized(behindAnimsList) {
			Iterator<Anim> i = behindAnimsList.listIterator();
			while (i.hasNext()) {
				Anim a = i.next();
				a.shouldDrawThis = sdac.shouldThisAnimBeDrawn( a );
				if (a.act()) {
					a.setOver(true);
					i.remove();
				}
			}
		}
		synchronized(sortedAnimsList) {
			Iterator<Anim> i = sortedAnimsList.listIterator();
			while (i.hasNext()) {
				Anim a = i.next();
				a.shouldDrawThis = sdac.shouldThisAnimBeDrawn( a );
				if (a.act()) {
					a.setOver(true);
					i.remove();
				}
			}
		}

		synchronized(xFerAddAnimsList) {
			Iterator<Anim> i = xFerAddAnimsList.listIterator();
			while (i.hasNext()) {
				Anim a = i.next();
				a.shouldDrawThis = sdac.shouldThisAnimBeDrawn( a );
				if (a.act()) {
					a.setOver(true);
					i.remove();
				}
			}
		}
//		if( updateListAt < GameTime.getTime() )
//		{
//			updateListAt = GameTime.getTime() + 200;
//			Animation[] newSortedAnims;
//			Animation[] newBehindAnims;
//			Animation[] newInFrontAnims;
//			Animation[] newXFerAddAnims;
//
//			if( sortedAnims != sortedAnims1 )
//			{
//				newSortedAnims = sortedAnims1;
//				newBehindAnims = behindAnims1;
//				newInFrontAnims = inFrontAnims1;
//				newXFerAddAnims = xFerAddAnims1;
//			}
//			else{
//				newSortedAnims = sortedAnims2;
//				newBehindAnims = behindAnims2;
//				newInFrontAnims = inFrontAnims2;
//				newXFerAddAnims = xFerAddAnims2;
//			}
//
//			int newSortedAnimsSize  = 0;
//			int newBehindAnimsSize  = 0;
//			int newInFrontAnimsSize = 0;
//			int newXFerAddAnimsSize = 0;
//
//
//			for( int i = 0 ; i < sortedAnimsSize ; ++i )
//			{
//				Animation temp = sortedAnims[i];
//
//				if( temp.act() ){
//					if( temp instanceof SparksAnim)
//					{
//						SparksAnim bloodSplatter = (SparksAnim) temp;
//						if( bloodSplatter.getColor() == Color.RED )
//							SpecialEffects.free( bloodSplatter );
//					}
//					else
//						temp.nullify();
//					sortedAnims[i] = null;
//				}
//				else
//					newSortedAnims[newSortedAnimsSize++] = temp ;
//			}
//
//			synchronized( needsToBeAddedSortedAnims )
//			{
//				for( Animation a : needsToBeAddedSortedAnims )
//				{
//					newSortedAnims[newSortedAnimsSize++] = a;
//					a.start();
//				}
//				needsToBeAddedSortedAnims.clear();
//			}
//
//
//
//			for( int i = 0 ; i < behindAnimsSize ; ++i )
//			{
//				Animation temp = behindAnims[i];
//
//				if( temp.act() ){
//					temp.nullify();
//					behindAnims[i] = null;
//				}
//				else
//					newBehindAnims[newBehindAnimsSize++] = temp ;
//			}
//			synchronized( needsToBeAddedBehindAnims )
//			{
//				for( Animation a : needsToBeAddedBehindAnims )
//				{
//					newBehindAnims[newBehindAnimsSize++] = a;
//					a.start();
//				}
//				needsToBeAddedBehindAnims.clear();
//			}
//
//
//
//			for( int i = 0 ; i < inFrontAnimsSize ; ++i )
//			{
//				Animation temp = inFrontAnims[i];
//
//				if( temp.act() ){
//					temp.nullify();
//					inFrontAnims[i] = null;
//				}
//				else
//					newInFrontAnims[newInFrontAnimsSize++] = temp ;
//			}
//			synchronized( needsToBeAddedInfrontAnims )
//			{
//				for( Animation a : needsToBeAddedInfrontAnims )
//				{
//					newInFrontAnims[newInFrontAnimsSize++] = a;
//					a.start();
//				}
//				needsToBeAddedInfrontAnims.clear();
//			}
//
//
//
//			for( int i = 0 ; i < xFerAddAnimsSize ; ++i )
//			{
//				Animation temp = xFerAddAnims[i];
//
//				if( temp.act() ){
//					temp.nullify();
//					xFerAddAnims[i] = null;
//				}
//				else
//					newXFerAddAnims[newXFerAddAnimsSize++] = temp ;
//			}
//			synchronized( needsToBeAddedXFerAddAnims )
//			{
//				for( Animation a : needsToBeAddedXFerAddAnims )
//				{
//					newXFerAddAnims[newXFerAddAnimsSize++] = a;
//					a.start();
//				}
//				needsToBeAddedXFerAddAnims.clear();
//			}
//
//
//			synchronized( behindAnims )
//			{
//				behindAnims = newBehindAnims;
//				behindAnimsSize = newBehindAnimsSize;
//			}
//			synchronized( sortedAnims )
//			{
//				sortedAnims = newSortedAnims;
//				sortedAnimsSize = newSortedAnimsSize;
//			}
//			synchronized( inFrontAnims )
//			{
//				inFrontAnims = newInFrontAnims;
//				inFrontAnimsSize = newInFrontAnimsSize;
//			}
//			synchronized( xFerAddAnims )
//			{
//				xFerAddAnims = newXFerAddAnims;
//				xFerAddAnimsSize = newXFerAddAnimsSize;
//			}
//		}
//		else //just tell them to act
//		{
//			try
//			{
//				for( int i = 0 ; i < sortedAnimsSize ; ++i )
//					sortedAnims[i].act();
//
//
//				for( int i = 0 ; i < behindAnimsSize ; ++i )
//					behindAnims[i].act();
//
//
//				for( int i = 0 ; i < inFrontAnimsSize ; ++i )
//					inFrontAnims[i].act();
//
//
//				for( int i = 0 ; i < xFerAddAnimsSize ; ++i )
//					xFerAddAnims[i].act();
//			}
//			catch(Exception e)
//			{
//				e.printStackTrace();
//			}
//		}
//
//
//
//
//		if( sortedAt < GameTime.getTime() )
//		{
//
//			sortedAt = GameTime.getTime() + sortEvery;
//			try
//			{
//				Animation[] drawableBehindAnims;
//				Animation[] drawableSortedAnims;
//				Animation[] drawableInFrontAnims;
//				Animation[] drawableXFerAddAnims;
//
//				if( this.drawableBehindAnims != drawableBehindAnims1 ){
//					drawableBehindAnims = drawableBehindAnims1;
//					drawableSortedAnims = drawableSortedAnims1;
//					drawableInFrontAnims = drawableInFrontAnims1;
//					drawableXFerAddAnims = drawableXFerAddAnims1;
//				}
//				else{
//					drawableBehindAnims = drawableBehindAnims2;
//					drawableSortedAnims = drawableSortedAnims2;
//					drawableInFrontAnims = drawableInFrontAnims2;
//					drawableXFerAddAnims = drawableXFerAddAnims2;
//				}
//
//				int drawableBehindAnimsSize = 0;
//				int drawableSortedAnimsSize = 0;
//				int drawableInFrontAnimsSize = 0;
//				int drawableXFerAddAnimsSize = 0;
//
//				ShouldDrawAnimCalcer sdac = mm.getSdac();
//				if( sdac == null )
//					if( !TowerDefenceGame.testingVersion )
//						return;
//
//				for( int i = 0 ; i < sortedAnimsSize ; ++i )
//				{
//					Animation temp = sortedAnims[i];
//					if( temp.over )
//						continue;
//
//
//					temp.shouldDrawThis = sdac.shouldThisAnimBeDrawn( temp );
//					if( temp.shouldDrawThis )
//						drawableSortedAnims[drawableSortedAnimsSize++] = temp;
//				}
//
//
//
//
//
//				for( int i = 0 ; i < behindAnimsSize ; ++i )
//				{
//					Animation temp = behindAnims[i];
//					if( temp.over )
//						continue;
//
//
//					temp.shouldDrawThis = sdac.shouldThisAnimBeDrawn( temp );
//					if( temp.shouldDrawThis )
//						drawableBehindAnims[drawableBehindAnimsSize++] = temp;
//
//				}
//
//
//				for( int i = 0 ; i < inFrontAnimsSize ; ++i )
//				{
//					Animation temp = inFrontAnims[i];
//
//					if( temp.over )
//						continue;
//
//
//					temp.shouldDrawThis = sdac.shouldThisAnimBeDrawn( temp );
//					if( temp.shouldDrawThis )
//						drawableInFrontAnims[drawableInFrontAnimsSize++] = temp;
//
//				}
//
//
//				for( int i = 0 ; i < xFerAddAnimsSize ; ++i )
//				{
//					Animation temp = xFerAddAnims[i];
//
//					if( temp.over )
//						continue;
//
//					temp.shouldDrawThis = sdac.shouldThisAnimBeDrawn( temp );
//					if( temp.shouldDrawThis )
//						drawableXFerAddAnims[drawableXFerAddAnimsSize++] = temp;
//				}
//
//				//if( drawableBehindAnimsSize != 0 )
//				//	QuickSort.sort(drawableBehindAnims,0, drawableBehindAnimsSize-1);
//				if( drawableSortedAnimsSize != 0 )
//					QuickSort.sort(drawableSortedAnims,0, drawableSortedAnimsSize-1);
//				if( drawableInFrontAnimsSize != 0 )
//					QuickSort.sort(drawableInFrontAnims,0, drawableInFrontAnimsSize-1);
//				if( drawableXFerAddAnimsSize != 0 )
//					QuickSort.sort(drawableXFerAddAnims,0, drawableXFerAddAnimsSize-1);
//
//
//				synchronized( drawableBehindAnims )
//				{
//					this.drawableBehindAnims = drawableBehindAnims;
//					this.drawableBehindAnimsSize = drawableBehindAnimsSize;
//				}
//				synchronized( drawableSortedAnims )
//				{
//					this.drawableSortedAnims = drawableSortedAnims;
//					this.drawableSortedAnimsSize = drawableSortedAnimsSize;
//				}
//				synchronized( drawableInFrontAnims )
//				{
//					this.drawableInFrontAnims = drawableInFrontAnims;
//					this.drawableInFrontAnimsSize = drawableInFrontAnimsSize;
//				}
//				synchronized( drawableXFerAddAnims )
//				{
//					this.drawableXFerAddAnims = drawableXFerAddAnims;
//					this.drawableXFerAddAnimsSize = drawableXFerAddAnimsSize;
//				}
//			}
//			catch( Exception e )
//			{
//				e.printStackTrace();
//				drawableBehindAnimsArraySize += 500;
//				drawableSortedAnimsArraySize += 500;
//				drawableInFrontAnimsArraySize += 500;
//				drawableXFerAddAnimsArraySize += 500;
//			}
//		}


	}




	public boolean add( @NotNull final Anim anim ,@NotNull final Position p )
	{
		if( anim == null )
			throw new NullPointerException("Anim cannot be null!");

		/* @FIXME Optimization not necessary for tower defence mode
		if( anim.onlyShowIfOnScreen ){
			ShouldDrawAnimCalcer sdac = mm.getSdac();
			if( sdac == null )
				if( !TowerDefenceGame.testingVersion )
					return false;
			if( !sdac.stillDraw( anim.loc ) )
				return false;
		}*/

		synchronized( runnables ){
			runnables.add(new Runnable(){
				@Override
				public void run() {
					Position requiredPosition = anim.getRequiredPosition();
					if( requiredPosition == null )
						requiredPosition = p;

					if( anim.getPaint() == Rpg.getXferAddPaint() || requiredPosition == Position.XFerAdd)
					{
						synchronized(xFerAddAnimsList) {
							xFerAddAnimsList.add(anim);
						}
						anim.start();
					}
					else
					{
						switch( requiredPosition )
						{
							case LightEffectsInFront:
								//Log.d(TAG ,"Adding " + anim + " to inFrontAnims.");
								synchronized(lightEffectsInFrontAnimsList) {
									lightEffectsInFrontAnimsList.add(anim);
								}
								anim.start();
								break;
							case InFront:
								//Log.d(TAG ,"Adding " + anim + " to inFrontAnims.");
								synchronized(inFrontAnimsList) {
									inFrontAnimsList.add(anim);
								}
								anim.start();
								break;
							case Behind:
								//Log.d(TAG ,"Adding " + anim + " to behindAnims.");
								synchronized(behindAnimsList) {
									behindAnimsList.add(anim);
								}
								anim.start();
								break;



							default:
							case Sorted:
								//Log.d(TAG ,"Adding " + anim + " to sortedAnims.");
								synchronized(sortedAnimsList) {
									sortedAnimsList.add(anim);
								}
								anim.start();
								break;
						}
					}
				}
			});
		}
		return true;
		/*if( anim.onlyShowIfOnScreen ){
			ShouldDrawAnimCalcer sdac = mm.getSdac();
			if( sdac == null )
				if( !TowerDefenceGame.testingVersion )
					return false;
			if( !sdac.stillDraw( anim.loc ) )
				return false;
		}


		if( anim.getPaint() == Rpg.getXferAddPaint() )
		{
			synchronized( needsToBeAddedXFerAddAnims ){
				needsToBeAddedXFerAddAnims.add( anim );
			}
		}
		else
		{
			switch( p )
			{
			case Behind:
				//Log.d(TAG ,"Adding " + anim + " to behindAnims.");
				synchronized( needsToBeAddedBehindAnims ){
					needsToBeAddedBehindAnims.add(anim);
				}

				break;

			case InFront:
				//Log.d(TAG ,"Adding " + anim + " to inFrontAnims.");
				synchronized( needsToBeAddedInfrontAnims ){
					needsToBeAddedInfrontAnims.add(anim);
				}
				break;

			default:
			case Sorted:
				//Log.d(TAG ,"Adding " + anim + " to sortedAnims.");
				synchronized( needsToBeAddedSortedAnims ){
					needsToBeAddedSortedAnims.add(anim);
				}
				break;
			}
		}
		updateListAt = 0;*/

	}

	public void add( @NonNull Anim anim , boolean sorted )
	{
		//////Log.d( TAG , "Adding " + anim + " inFront?:" + sorted );
		if( sorted )
			add( anim , EffectsManager.Position.Sorted );
		else
			add( anim , EffectsManager.Position.Behind );

	}


	public void add( @NonNull Anim anim )
	{
		add( anim , false );
	}





	public List<Anim> getLightEffectsInFrontAnimations() {
		synchronized( lightEffectsInFrontAnimsList )
		{
			return new ArrayList<>(lightEffectsInFrontAnimsList);
		}
	}

	public List<Anim> getBehindAnimations()
	{
		synchronized( behindAnimsList )
		{
			return new ArrayList<>(behindAnimsList);
		}
	}

	public List<Anim> getSortedAnimations()
	{
		synchronized( sortedAnimsList )
		{
			return new ArrayList<>(sortedAnimsList);
		}
	}

	public List<Anim> getInFrontAnimations()
	{
		synchronized( inFrontAnimsList )
		{
			return new ArrayList<>(inFrontAnimsList);
		}
	}

	public List<Anim> getXFerAddAnims()
	{
		synchronized( xFerAddAnimsList )
		{
			return new ArrayList<>(xFerAddAnimsList);
		}
	}

//
//	public int getDrawableBehindAnimsSize() {
//		return drawableBehindAnimsSize;
//	}
//
//
//	public int getDrawableSortedAnimsSize() {
//		return drawableSortedAnimsSize;
//	}
//
//
//	public int getDrawableInFrontAnimsSize() {
//		return drawableInFrontAnimsSize;
//	}
//
//
//	public int getDrawableXFerAddAnimsSize() {
//		return drawableXFerAddAnimsSize;
//	}
//
//
//
//



	public void sort(){
		sortAllAt = 0;
	}








	public void nullify(){
	}


	public void saveYourSelf(BufferedWriter b) throws IOException
	{
		String s = "<EffectsManager>";
		b.write(s,0,s.length());
		b.newLine();

		s = "<BehindAnimations>";
		b.write(s,0,s.length());
		b.newLine();

		for(Anim a: behindAnimsList)
		{
			a.saveYourSelf(b);
		}

		s = "</BehindAnimations>";
		b.write(s,0,s.length());
		b.newLine();

		s = "<SortedAnimations>";
		b.write(s,0,s.length());
		b.newLine();

		for(Anim a:sortedAnimsList)
		{
			a.saveYourSelf(b);
		}


		s = "</SortedAnimations>";
		b.write(s,0,s.length());
		b.newLine();

		s = "<InFrontAnimations>";
		b.write(s,0,s.length());
		b.newLine();


		for(Anim a:inFrontAnimsList)
		{
			a.saveYourSelf(b);
		}


		s = "</InFrontAnimations>";
		b.write(s,0,s.length());
		b.newLine();

		s = "</EffectsManager>";
		b.write(s,0,s.length());
		b.newLine();
	}









}
