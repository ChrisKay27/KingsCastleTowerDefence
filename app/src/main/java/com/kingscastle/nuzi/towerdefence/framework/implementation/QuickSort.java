package com.kingscastle.nuzi.towerdefence.framework.implementation;


import com.kingscastle.nuzi.towerdefence.effects.animations.Anim;

import java.util.Arrays;
import java.util.List;

public class QuickSort
{
	public static void sort(Anim[] a, int startIndex, int endIndex){
		//quicksortX( a , startIndex , endIndex );
		quicksortY( a , startIndex , endIndex );
	}

	public static void quicksortX(Anim[] a, int startIndex, int endIndex)
	{
		if( startIndex == endIndex && startIndex == 0 )
			return;

		int i = startIndex, j = endIndex;
		// Get the pivot element from the middle of the list
		float pivot = a[startIndex + (endIndex-startIndex)/2].loc.x;

		// Divide into two lists
		while (i <= j) {
			// If the current value from the left list is smaller then the pivot
			// element then get the next element from the left list
			while (a[i].loc.x > pivot) {
				i++;
			}
			// If the current value from the right list is larger then the pivot
			// element then get the next element from the right list
			while (a[j].loc.x < pivot) {
				j--;
			}

			// If we have found a values in the left list which is larger then
			// the pivot element and if we have found a value in the right list
			// which is smaller then the pivot element then we exchange the
			// values.
			// As we are done we can increase i and j
			if (i <= j) {
				swap(a, i, j);
				i++;
				j--;
			}
		}
		// Recursion
		if (startIndex < j)
			quicksortX(a, startIndex, j);
		if (i < endIndex)
			quicksortX(a, i, endIndex);
	}
	public static void quicksortY(Anim[] a, int startIndex, int endIndex)
	{
		int i = startIndex, j = endIndex;
		// Get the pivot element from the middle of the list
		float pivot = a[startIndex + (endIndex-startIndex)/2].loc.y;

		// Divide into two lists
		while (i <= j) {
			// If the current value from the left list is smaller then the pivot
			// element then get the next element from the left list
			while (a[i].loc.y > pivot) {
				i++;
			}
			// If the current value from the right list is larger then the pivot
			// element then get the next element from the right list
			while (a[j].loc.y < pivot) {
				j--;
			}


			// If we have found a values in the left list which is larger then
			// the pivot element and if we have found a value in the right list
			// which is smaller then the pivot element then we exchange the
			// values.
			// As we are done we can increase i and j
			if (i <= j) {
				swap(a, i, j);
				i++;
				j--;
			}
		}
		// Recursion
		if (startIndex < j)
			quicksortY(a, startIndex, j);
		if (i < endIndex)
			quicksortY(a, i, endIndex);
	}



	private static void swap(Anim[] a, int i, int j) {

		Anim temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}


	public static List<Anim> sort(List<Anim> anims) {
		Anim[] sorted;
		synchronized(anims) {
			sorted = new Anim[anims.size()];
			sorted = anims.toArray(sorted);
		}
		QuickSort.quicksortY(sorted, 0, sorted.length - 1);
		anims = Arrays.asList(sorted);
		return anims;
	}
}
