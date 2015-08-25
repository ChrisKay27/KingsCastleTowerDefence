package com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings;


import com.kingscastle.nuzi.towerdefence.R;
import com.kingscastle.nuzi.towerdefence.framework.Assets;
import com.kingscastle.nuzi.towerdefence.framework.Image;

public class UndeadStorageDepot extends StorageDepot
{
	private static final String TAG = "UndeadStorageDepot";
	private static final String NAME = "Storage Depot";

	public static final Buildings name = Buildings.UndeadStorageDepot;

	private static final Image image = Assets.loadImage( R.drawable.undead_storage_depot);

	private final Image iconImage = null;//Assets.loadImage(R.drawable.undead_storage_depot_icon);



	public UndeadStorageDepot()
	{
		super( name );
	}



	@Override
	public Image getImage(){
		return image;
	}


	@Override
	public Image getDamagedImage(){
		return getImage();
	}


	@Override
	public Image getIconImage(){
		return iconImage;
	}



	@Override
	public String toString() {
		return TAG;
	}


	@Override
	public String getName() {
		return NAME;
	}

}
