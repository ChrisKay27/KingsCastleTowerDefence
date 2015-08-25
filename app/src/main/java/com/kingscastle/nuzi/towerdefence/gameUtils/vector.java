package com.kingscastle.nuzi.towerdefence.gameUtils;

import android.graphics.Point;
import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.framework.Rpg;
import com.kingscastle.nuzi.towerdefence.framework.Rpg.Direction;
import com.kingscastle.nuzi.towerdefence.gameElements.movement.Line;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class vector implements Serializable
{

	public float x , y ;

	public vector() {}

	public vector(double x, double y)
	{
		this( (float) x , (float) y );
	}

	/**
	 * DONT USE , SLOW
	 * @param rad
	 */
	public vector(float rad)
	{
		this( Math.cos(rad) , Math.sin(rad) );
	}

	public vector(int x, int y)
	{
		this( (float) x , (float)  y );
	}
	public vector(float x, float y)
	{
		this.x=x;
		this.y=y;
	}
	public vector(vector from, vector to)
	{
		this(to.x-from.x,to.y-from.y);
	}


	public vector(Point p)
	{
		if( p != null )
		{
			x=p.x;
			y=p.y;
		}
	}

	public vector(vector p)
	{
		if( p!=null )
		{
			x = p.x;
			y = p.y;
		}
	}


	public vector getInstance()
	{
		return new vector();
	}


	public void recycle()
	{

	}


	public vector clear()
	{
		x = 0;
		y = 0;
		return this;
	}





	public void incY( int j )
	{
		y+=j;
	}
	public void incY( float j )
	{
		y+=j;
	}
	public void incX(int i) {
		x+=i;
	}

	public void incX(float i){
		x+=i;
	}
	public float getX(){
		return x;
	}
	public float getY(){
		return y;
	}
	public int getIntX(){
		return (int) x;
	}
	public int getIntY(){
		return (int) y;
	}
	public void set(int x, int y){
		this.set((float) x,(float)y);
	}

	public vector set(float x,float y)
	{
		this.x=x;this.y=y;
		return this;
	}
	public vector set(double x, double y)
	{
		this.x=(float) x;   this.y=(float) y;
		return this;
	}

	public vector set(vector v)
	{
		x=v.x;
		y=v.y;
		return this;
	}

	public void setX(float x)
	{
		this.x=x;
	}
	public void setY(float y)
	{
		this.y=y;
	}
	public vector minus( vector p )
	{
		x -= p.x;
		y -= p.y;
		return this;
	}

	public vector minus( float x2 , float y2 )
	{
		x -= x2;
		y -= y2;
		return this;
	}

	public vector times(float d)
	{
		x*=d;
		y*=d;
		return this;
	}

	public vector times(int d){
		return this.times((float) d);
	}

	public vector add(vector p){
		if(p!=null){
			x+=p.x;y+=p.y;}
		return this;
	}

	public vector add(double d, double e) {
		add((float) d, (float) e);
		return this;
	}

	public vector translate(float x,float y){
		this.x+=x;this.y+=y;
		return this;
	}

	public vector translate(int x,int y){
		this.x+=x;this.y+=y;
		return this;
	}

	float dot(vector b){
		return vector.dot(this, b);
	}

	float magnitude()
	{
		return sqrt(x*x+y*y);
	}

	public float magnitudeSquared()
	{
		return lengthSquared();
	}

	float lengthSquared()
	{
		return x*x + y*y;
	}

	public float comp( vector inDir )
	{
		float mag = magnitude();
		if( mag == 0 )
		{
			throw new IllegalArgumentException("magnitute of a == zero");
		}

		return dot(inDir)/(mag);
	}


	public vector proj( vector b )
	{
		return vector.proj(this, b);
	}

	public float distance(@NotNull vector v )
	{
		if( v == null )
			return 0;

		float dx = v.x - x ;
		float dy = v.y - y ;

		return sqrt(dx*dx + dy*dy);
	}


	public float distanceSquared(@NotNull vector v )
	{
		if( v == null )
			return 0;

		float dx = v.x - x ;
		float dy = v.y - y ;

		return dx*dx + dy*dy;
	}


	public float distanceSquared(float x2, float y2)
	{
		float dx = x2 - x ;
		float dy = y2 - y ;

		return dx*dx + dy*dy;
	}



	/**
	 * A new Vector is returned and the current vector is not mutated. The returned Vector has a magnitude of speed.
	 * @return A new vector is returned.
	 */
	public vector truncate(float speed)
	{
		return getUnitVector().times(speed);
	}

	/**
	 * if the normalVector is [1,0] this vector is set to [0,y], similarly with a normalVector of [0,1].
	 * @param normalVector A vector which MUST have only a x or a y value.
	 * @return The same vector but with the component in the same direction as the normalVector set to
	 */
	public vector removeComponentInDir( vector normalVector )
	{
		if( normalVector == null )
		{
			return this;
		}
		if( normalVector.x != 0 )
		{
			x = 0;
		}
		else if( normalVector.y != 0 )
		{
			y = 0 ;
		}
		return this;
	}

	private static float dot(vector a, vector b)
	{
		return 	a.x*b.x + a.y*b.y;
	}

	/**
	 * Vector projection of a onto b
	 * @param a vector 'a' which is projecting onto vector 'b'
	 * @param b vector 'b' which is being projected onto
	 */
	private static vector proj(vector a, vector b)
	{
		float mag = b.magnitude();
		if( mag == 0 )
		{
			throw new IllegalArgumentException("magnitute of b==zero");
		}
		return new vector(b).times(a.dot(b)/(mag*mag));
	}



	public static vector vectorBetween( vector from , vector to )
	{

		if( from == null || to == null )
		{
			return null;
		}


		vector newVector = new vector();

		newVector.set( to.x - from.x , to.y - from.y );

		return newVector;

		//return new Vector( to.x - from.x , to.y - from.y );
	}




	/**
	 * returns a new Vector object which is a vector in the direction of v but with a magnitute of 1. The vector v is not mutated.
	 */
	public vector getUnitVector(){
		return vector.getUnitVector(this);
	}

	public static vector getUnitVector( vector from , vector to )
	{
		return new vector(to.x-from.x,to.y-from.y).turnIntoUnitVector();
	}

	//	private static long lastDisplay,displayEvery=2000,startTime,sqrtTime,fastMathTime;
	//	private static int sqrtCount=0,fastMathCount=0;
	//	private static boolean onSqrt=false,dir4=false;

	/*
	 * returns a new Vector object which is a vector in the direction of v but with a magnitute of 1. The vector v is not mutated.
	 * @param v Vector used to create a unit vector in the same direction as v.
	 */
	private static vector getUnitVector(vector v)
	{
		return getUnitVectorSqrt(v);
	}
	//	public static Vector getUnitVector(Vector v){
	//		//System.out.println("Vector:getUnitVector() being called");
	//		if(sqrtCount!=0&&fastMathCount!=0&&lastDisplay+displayEvery<GameTime.getTime()){
	//			lastDisplay=GameTime.getTime();
	//			System.out.println("Average time for " + sqrtCount + " sqrts is " + sqrtTime/sqrtCount + " nanoSeconds.");
	//			System.out.println("Average time for " + fastMathCount + " FastMaths is " + fastMathTime/fastMathCount + " nanoSeconds.");
	//		}
	//
	//		if(onSqrt){
	//			startTime=System.nanoTime();
	//			Vector v2 = getUnitVectorSqrt(v);
	//			if(dir4)
	//				getDirection4(v2);
	//			else
	//				getDirection8(v2);
	//			sqrtTime+=System.nanoTime()-startTime;
	//			sqrtCount++;
	//			onSqrt=false;
	//			return v2;
	//		}
	//		else{
	//			startTime=System.nanoTime();
	//			Vector unit = getUnitVectorFastMath(v);
	//			fastMathTime+=System.nanoTime()-startTime;
	//			fastMathCount++;
	//			onSqrt=true;
	//			return unit;
	//		}
	//	}

	/**
	 * returns a new Vector object which is a vector in the direction of v but with a magnitute of 1. The vector v is not mutated.
	 * @param v Vector used to create a unit vector in the same direction as v.
	 */
	private static vector getUnitVectorSqrt(vector v)
	{
		float mag = sqrt( v.x*v.x + v.y*v.y );
		if( mag == 0)
		{
			return new vector(0,0);
		}
		return new vector( v.x/mag , v.y/mag );
	}

	public vector normalize()
	{
		return turnIntoUnitVector();
	}

	public vector turnIntoUnitVector( )
	{
		float mag = sqrt( x*x + y*y );

		if( mag == 0 )
			return this;

		x /= mag;
		y /= mag;

		return this;
	}



	public static Rpg.Direction getDirection4(vector unitVector)
	{
		if( unitVector.x >= 0f )
		{
			if( unitVector.y>= 0f )
			{
				if( unitVector.x < 0.707f )
				{
					return Direction.S;
				}
				else{
					return Direction.E;
				}
			}
			else{
				if( unitVector.x < 0.707f )
				{
					return Direction.N;
				}
				else{
					return Direction.E;
				}
			}
		}
		else{
			if( unitVector.y >= 0f )
			{
				if( unitVector.x > -0.707f )
				{
					return Direction.S;
				}
				else{
					return Direction.W;
				}
			}
			else{
				if( unitVector.x > -0.707f )
				{
					return Direction.N;
				}
				else{
					return Direction.W;
				}
			}
		}
	}


	public static Direction getDirection8( vector unitVector )
	{
		if( unitVector.x >= 0 )
		{
			if( unitVector.y >= 0 )
			{
				if(unitVector.x<0.383)
				{
					return Direction.S;
				}
				else if(unitVector.x<0.924)
				{
					return Direction.SE;
				}
				else{
					return Direction.E;
				}
			}
			else{
				if(unitVector.x<0.383){
					return Direction.N;
				}
				else if(unitVector.x<0.924){
					return Direction.NE;
				}
				else{
					return Direction.E;
				}
			}
		}
		else{
			if(unitVector.y>=0){
				if(unitVector.x>-0.383){
					return Direction.S;
				}
				else if(unitVector.x>-0.924){
					return Direction.SW;
				}
				else{
					return Direction.W;
				}
			}
			else{
				if(unitVector.x>-0.383){
					return Direction.N;
				}
				else if(unitVector.x>-0.924){
					return Direction.NW;
				}
				else{
					return Direction.W;
				}
			}
		}
	}





	@Override
	public Object clone(){
		return new vector(x,y);
	}
	public Point toPoint(){
		return new Point((int)x,(int)y);
	}
	@Override
	public String toString(){
		return "Vector [x=" + x + ",y=" + y + "]";

	}


	public vector add(float i, float j)
	{
		x+=i;
		y+=j;
		return this;
	}





	public static vector getAverage( ArrayList<vector> vectors )
	{

		if( vectors.size() == 0 )
		{
			return new vector( 0 , 0 );
		}

		float x = 0 , y = 0 ;

		for( vector v : vectors )
		{
			x+=v.x;
			y+=v.y;
		}

		x /= vectors.size();
		y /= vectors.size();

		return new vector( x , y );
	}




	/**
	 * creates a new vector +- i around both the x and y coordinates
	 */
	public vector randomize( int randomness )
	{
//		double xOffs = Math.random();
//		double bool = Math.random();

		x += -randomness + 2*Math.random()*randomness;
		y += -randomness + 2*Math.random()*randomness;
//		if( xOffs < 0.5 )
//		{
//			if( bool < 0.5 )
//				x += randomness*Math.random() ;
//			else
//				x -= randomness;
//		}
//		else
//		{
//			if( bool < 0.5 )
//				y += randomness ;
//			else
//				y -= randomness;
//		}


		return this;
	}

	public void randomize( float randomness , Random rand , boolean zeroToRandomness )
	{
		if( rand.nextBoolean() )
		{
			randomness = (int) (-rand.nextFloat()*randomness);
		}
		randomize( randomness ,rand );
	}


	void randomize(float randomness, Random rand)
	{

		if( rand.nextBoolean() )
		{
			randomness = -randomness;
		}


		if( rand.nextBoolean() )
		{
			x += randomness;
		}
		else
		{
			y += randomness;
		}

	}


	public boolean equals( vector v )
	{
		if( Math.abs( x - v.x ) > 0.1 || Math.abs( y -  v.y ) > 0.1 )
		{
			return false;
		}
		return true;
	}

	private static final int precision = 1; // gradations per degree, adjust to suit

	private static final int modulus = 360*precision;
	private static final float[] sin = new float[modulus]; // lookup table

	public static final vector ZeroVector = new vector(0,0);
	static {
		// a static initializer fills the table
		// in this implementation, units are in degrees
		for (int i = 0; i<sin.length; i++) {
			sin[i]=(float)Math.sin((i*Math.PI)/(precision*180));
		}
	}
	// Private function for table lookup
	private static float sinLookup(int a) {
		return a>=0 ? sin[a%(modulus)] : -sin[-a%(modulus)];
	}

	// These are your working functions:
	public static float sin(float a) {
		return sinLookup((int)(a * precision + 0.5f));
	}
	public static float cos(float a) {
		return sinLookup((int)((a+90f) * precision + 0.5f));
	}

	public static float sqrt(final double a) {
		final long x = Double.doubleToLongBits(a) >> 32;
		double y = Double.longBitsToDouble((x + 1072632448) << 31);

		// repeat the following line for more precision
		y = (y + a / y) * 0.5;
		return (float) y;
	}

	public vector rotate( int deg )
	{
		if(deg == 90)
		{
			float temp = y;
			y = x;
			x = -temp;
			return this;
		}
		else if( deg == 180 )
		{
			x = -x;
			y = -y;
			return this;
		}
		else if( deg == 270 || deg == -90 )
		{
			float temp = x;
			x = y;
			y = -temp;
			return this;
		}
		else
		{

		}
		return null;
	}
	public vector flipAboutY() {
		x=-x;
		return this;
	}
	public vector flipAboutX() {
		y=-y;
		return this;
	}

	public void divideBy(int dt)
	{
		x/=dt;
		y/=dt;
	}

	private transient vector tl , bl , tr , br , rectCenter , intersectsAt ;

	public float distanceSquared( RectF r )
	{
		if( intersectsAt == null )
		{
			tl = new vector();
			bl = new vector();
			tr = new vector();
			br = new vector();
			rectCenter = new vector();
			intersectsAt = new vector();
		}

		intersectsAt.set( 0 , 0 );
		rectCenter.set( r.centerX() , r.centerY() );

		tl.set( r.left , r.top );
		bl.set( r.left , r.bottom );
		tr.set( r.right , r.top );
		br.set( r.right , r.bottom );

		vector intersectsAt2 = Line.getLineRectIntersection(tl, bl, tr, br, this, rectCenter, intersectsAt);

		if( intersectsAt2 != null )
			return this.distanceSquared( intersectsAt2 );


		return Float.MAX_VALUE;

	}


	public boolean isNear( int x2 , int y2 , float distance )
	{
		if ( Math.abs(x - x2) < distance && Math.abs(y - y2) < distance)
			return true;
		else
			return false;
	}

	public boolean isNear( float x2 , float y2 , float distance )
	{
		if ( Math.abs(x - x2) < distance && Math.abs(y - y2) < distance)
			return true;
		else
			return false;
	}








}
