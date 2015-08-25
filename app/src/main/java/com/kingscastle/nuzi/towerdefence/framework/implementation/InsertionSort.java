package com.kingscastle.nuzi.towerdefence.framework.implementation;


import com.kingscastle.nuzi.towerdefence.effects.animations.Anim;

public class InsertionSort
{


	public static void sort(Anim[] a, int startIndex, int endIndex)
	{
		for(int i = startIndex; i < endIndex; ++i){
			int j = i;
			Anim b = a[i];
			float B = b.loc.x;
			while ((j > startIndex) && (a[j-1].loc.x < B)){
				a[j] = a[j-1];
				j--;
			}
			a[j] = b;
		}

		for(int i = startIndex; i < endIndex; ++i){
			int j = i;
			Anim b = a[i];
			float B = b.loc.y;
			while ((j > startIndex) && (a[j-1].loc.y < B)){
				a[j] = a[j-1];
				j--;
			}
			a[j] = b;
		}


		return;

		//		if(p<r)
		//		{
		//			int size = r-p;
		//
		//			if( size < 40 )
		//			{
		//				for(int i = p; i < r; i++){
		//					int j = i;
		//					Animation b = a[i];
		//					float B = b.loc.y;
		//					while ((j > p) && (a[j-1].loc.y <= B)){
		//						a[j] = a[j-1];
		//						j--;
		//					}
		//					a[j] = b;
		//				}
		//				return;
		//			}
		//
		//			int q=partition(a,p,r);
		//			quickSort(a,p,q);
		//			quickSort(a,q+1,r);
		//		}
	}


	public static void sortY(Anim[] a, int startIndex, int endIndex)
	{
		for(int i = startIndex; i < endIndex; ++i){
			int j = i;
			Anim b = a[i];
			float B = b.loc.y;
			while ((j > startIndex) && (a[j-1].loc.y < B)){
				a[j] = a[j-1];
				j--;
			}
			a[j] = b;
		}
	}


	public static void sortX(Anim[] a, int startIndex, int endIndex)
	{
		for(int i = startIndex; i < endIndex; ++i){
			int j = i;
			Anim b = a[i];
			float B = b.loc.x;
			while ((j > startIndex) && (a[j-1].loc.x < B)){
				a[j] = a[j-1];
				j--;
			}
			a[j] = b;
		}
	}


	private static int partition(Anim[] a, int p, int r) {



		float y = a[p].loc.y;
		int i = p-1 ;
		int j = r+1 ;

		while (true) {
			i++;
			while ( i<r && a[i].loc.y <= y)
				i++;
			j--;
			while ( j>p && a[j].loc.y <= y)
				j--;

			if (i < j)
				swap(a, i, j);
			else
				return j;
		}
	}


	private static void swap(Anim[] a, int i, int j) {

		Anim temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}



}
