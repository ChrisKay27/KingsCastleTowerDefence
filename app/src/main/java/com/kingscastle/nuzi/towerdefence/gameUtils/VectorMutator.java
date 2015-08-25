package com.kingscastle.nuzi.towerdefence.gameUtils;

import java.util.Random;

public class VectorMutator
{

	private static final Random rand = new Random();

	//private static ArrayList<VectorMutatorParameters> vectorParams = new ArrayList<VectorMutatorParameters>();



	public static void act()
	{



	}


	public static void moveThisVector( VectorMutatorParameters params )
	{

	}




	public static vector rotateCW90(vector v)
	{
		v.set(v.getY(),v.getX());
		return v;
	}


	public static void randomlyRotate( vector v , float plusOrMinusX , float plusOrMinusY )
	{

		v.add( - plusOrMinusX +   rand.nextFloat() * 2 * plusOrMinusX , - plusOrMinusY +   rand.nextFloat() * 2 * plusOrMinusY );


	}



}
