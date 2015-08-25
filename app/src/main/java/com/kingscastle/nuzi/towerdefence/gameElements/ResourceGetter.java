package com.kingscastle.nuzi.towerdefence.gameElements;


import android.util.Log;

public class ResourceGetter
{
	private static final String TAG = "ResourceGetter";


	public static GameElement getResourceFromString( String name , int remainingResources  , int x , int y )
	{
		String className = name;

		try
		{
			Class<?> aResource = Class.forName("com.kingscastle.nuzi.towerdefence.gameElements." + className );

			GameElement resource = (GameElement) aResource.newInstance();


			resource.setLoc( x , y );

			return resource;
		}
		catch(Exception e)
		{
			Log.e(TAG, "com.kingscastle.nuzi.towerdefence.gameElements.");
			return null;
		}

	}

}
