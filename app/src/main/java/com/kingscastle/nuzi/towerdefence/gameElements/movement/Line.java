package com.kingscastle.nuzi.towerdefence.gameElements.movement;

import android.graphics.RectF;

import com.kingscastle.nuzi.towerdefence.gameUtils.vector;


public class Line
{

	public vector start;
	public vector end;

	private final vector tl = new vector();
	private final vector bl = new vector();
	private final vector tr = new vector();
	private final vector br = new vector();

	public Line( vector start, vector end )
	{
		//this.act = (Vector) act.clone();
		//this.end = (Vector) end.clone();
		this.start = start;
		this.end = end;
	}

	public Line() {
	}


	public boolean intersects( RectF r )
	{

		tl.set( r.left  , r.top     );
		bl.set( r.left  , r.bottom );
		tr.set( r.right , r.top     );
		br.set( r.right , r.bottom  );


		return intersects( tl , bl , tr , br , this );
	}




	private static boolean intersects(vector tl, vector bl, vector tr, vector br, Line l)
	{

		vector start = l.start;
		vector end = l.end;

		if( linesIntersect( start , end , tl , bl ) )
		{
			return true;
		}
		if( linesIntersect( start, end , tl , tr ) )
		{
			return true;
		}
		if( linesIntersect( start, end , bl , br ) )
		{
			return true;
		}
		if( linesIntersect( start , end , tr , br ) )
		{
			return true;
		}

		return false;
	}




	//	public static boolean intersects( RectF r , Line l )
	//	{
	//		Vector p1 = new Vector( r.left , r.top    ); //top left
	//		Vector p2 = new Vector( r.left , r.bottom ); //bottom left
	//		Vector p3 = new Vector( r.right, r.top    ); //top right
	//		Vector p4 = new Vector( r.right, r.bottom ); //bottom Right
	//
	//		if( linesIntersect( l.act , l.end , p1 , p2 ) )
	//		{
	//			return true;
	//		}
	//		if(linesIntersect( l.act , l.end , p1 , p3 ) )
	//		{
	//			return true;
	//		}
	//		if( linesIntersect( l.act , l.end , p2 , p4 ) )
	//		{
	//			return true;
	//		}
	//		if( linesIntersect( l.act , l.end , p3 , p4 ) )
	//		{
	//			return true;
	//		}
	//		return false;
	//
	//	}


	private static boolean linesIntersect(vector p1, vector p2, vector p3, vector p4)
	{

		//		float x1 = p1.getX();
		//		float y1 = p1.getY();
		//		float x2 = p1.getX();
		//		float y2 = p1.getY();
		//		float x3 = p1.getX();
		//		float y3 = p1.getY();
		//		float x4 = p1.getX();
		//		float y4 = p1.getY();
		//
		//		if ( x1 < x3 && x1 < x4 && x2 <  < p3.getX() && p4.getX() )
		//		{
		//
		//		}

		// Return false if either of the lines have zero length
		if (p1.x == p2.x && p1.y == p2.y ||
				p3.x == p4.x && p3.y == p4.y)
		{
			return false;
		}

		float p1x = p1.x;
		float p1y = p1.y;
		float p2x = p2.x;
		float p2y = p2.y;
		float p3x = p3.x;
		float p3y = p3.y;
		float p4x = p4.x;
		float p4y = p4.y;


		// Fastest method, based on Franklin Antonio's "Faster Line Segment Intersection" topic "in Graphics Gems III" book (http://www.graphicsgems.org/)
		float ax = p2x-p1x;
		float ay = p2y-p1y;
		float bx = p3x-p4x;
		float by = p3y-p4y;
		float cx = p1x-p3x;
		float cy = p1y-p3y;

		float alphaNumerator = by*cx - bx*cy;
		float commonDenominator = ay*bx - ax*by;

		if (commonDenominator > 0)
		{
			if (alphaNumerator < 0 || alphaNumerator > commonDenominator)
			{
				return false;
			}
		}else if (commonDenominator < 0)
		{
			if (alphaNumerator > 0 || alphaNumerator < commonDenominator )
			{
				return false;
			}
		}
		double betaNumerator = ax*cy - ay*cx;
		if (commonDenominator > 0)
		{
			if (betaNumerator < 0 || betaNumerator > commonDenominator )
			{
				return false;
			}
		}
		else if (commonDenominator < 0)
		{
			if (betaNumerator > 0 || betaNumerator < commonDenominator )
			{
				return false;
			}
		}
		if (commonDenominator == 0)
		{
			// This code wasn't in Franklin Antonio's method. It was added by Keith Woodward.
			// The lines are parallel.
			// Check if they're collinear.

			double y3LessY1 = p3y-p1y;
			double collinearityTestForP3 = p1x*(p2y-p3y) + p2x*(y3LessY1) + p3x*(p1y-p2y);   // see http://mathworld.wolfram.com/Collinear.html

			// If p3 is collinear with p1 and p2 then p4 will also be collinear, since p1-p2 is parallel with p3-p4

			if ( collinearityTestForP3 == 0 )
			{
				// The lines are collinear. Now check if they overlap.
				if (p1x >= p3x && p1x <= p4x || p1x <= p3x && p1x >= p4x ||
						p2x >= p3x && p2x <= p4x || p2x <= p3x && p2x >= p4x ||
						p3x >= p1x && p3x <= p2x || p3x <= p1x && p3x >= p2x)
				{
					if (p1y >= p3y && p1y <= p4y || p1y <= p3y && p1y >= p4y ||
							p2y >= p3y && p2y <= p4y || p2y <= p3y && p2y >= p4y ||
							p3y >= p1y && p3y <= p2y || p3y <= p1y && p3y >= p2y)
					{
						return true;
					}
				}
			}

			return false;
		}
		return true;
	}

	private static vector getLineLineIntersection(vector p1, vector p2, vector p3, vector p4)
	{
		return getLineLineIntersection( p1 , p2 , p3 , p4 , new vector() );
	}


	private static vector getLineLineIntersection(vector p1, vector p2, vector p3, vector p4, vector intoThisVector)
	{

		float det1And2 = det( p1.x , p1.y , p2.x , p2.y );
		float det3And4 = det( p3.x , p3.y , p4.x , p4.y );
		float x1LessX2 = p1.x - p2.x;
		float y1LessY2 = p1.y - p2.y;
		float x3LessX4 = p3.x - p4.x;
		float y3LessY4 = p3.y - p4.y;

		float det1Less2And3Less4 = det( x1LessX2 , y1LessY2 , x3LessX4 , y3LessY4 );

		if ( det1Less2And3Less4 == 0 )
		{			// the denominator is zero so the lines are parallel and there's either no solution (or multiple solutions if the lines overlap) so return null.

			return null;
		}
		float x = (det(det1And2, x1LessX2,
				det3And4, x3LessX4) /
				det1Less2And3Less4);
		float y = (det(det1And2, y1LessY2,
				det3And4, y3LessY4) /
				det1Less2And3Less4);

		intoThisVector.set( x , y );

		return intoThisVector;
	}




	private static float det(float a, float b, float c, float d)
	{
		return a * d - b * c;
	}

	//	protected static double det( double a, double b, double c, double d )
	//	{
	//		return a * d - b * c;
	//	}



	@Override
	public String toString()
	{
		return "[Start:" + start + " end:" + end + "]" ;

	}


	public static vector getLineRectIntersection ( vector tl , vector bl , vector tr  , vector br , vector start , vector end , vector intoThisVector )
	{
		if( linesIntersect( start , end , tl , bl ) )
		{
			return getLineLineIntersection( start , end , tl , bl , intoThisVector );
		}
		if( linesIntersect( start , end , tl , tr ) )
		{
			return getLineLineIntersection( start , end , tl , tr , intoThisVector);
		}
		if( linesIntersect( start , end , bl , br ) )
		{
			return getLineLineIntersection( start , end , bl , br , intoThisVector);
		}
		if( linesIntersect( start , end , tr , br ) )
		{
			return getLineLineIntersection( start , end , tr , br , intoThisVector);
		}

		return null;
	}


	public vector getLineRectIntersection ( vector tl , vector bl , vector tr  , vector br , Line l )
	{
		vector start = l.start;
		vector end = l.start;

		if( linesIntersect( start , end , tl , bl ) )
		{
			return getLineLineIntersection(start, end,tl,bl);
		}
		if(linesIntersect(start, end,tl,tr))
		{
			return getLineLineIntersection(start, end,tl,tr);
		}
		if( linesIntersect(start, end,bl,br) )
		{
			return getLineLineIntersection(start, end,bl,br);
		}
		if(linesIntersect(start, end,tr,br))
		{
			return getLineLineIntersection(start, end,tr,br);
		}

		return null;
	}



	public void set( vector start , vector end )
	{
		this.start = start;
		this.end = end;

	}



}
