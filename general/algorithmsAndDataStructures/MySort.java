package algorithmsAndDataStructures;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MySort {
	
	private Comparable[] aux;
	
	private void switchElements(Comparable[] toSort, int a, int b) {
		Comparable temp = toSort[a];
		toSort[a] = toSort[b];
		toSort[b] = temp;
	}
	
	//selection sort
	/*
	 * Starting with first element, look at all following elements and swap with smallest smaller element found
	 * Run time: n^2
	 * Extra space: 1
	 * unstable, in place
	 */
	public void selectionSort(Comparable[] toSort) {
		int minIndex = 0;
		for(int a = 0; a < toSort.length; a++) {
			minIndex = a;
			for(int b = a; b < toSort.length; b++) {
				if (toSort[b].compareTo(toSort[minIndex]) < 0) {
					minIndex = b;
				}
			}
			switchElements(toSort, a, minIndex);
		}
	}
	
	//insertion sort
	/*
	 * Exchange each element with a bigger element to the left so that the left side is always sorted
	 * Run time: between n and n^2
	 * Extra space: 1
	 * stable, in place
	 */
	public void insertionSort(Comparable[] toSort) {
		for(int a = 0; a < toSort.length; a++) {
			for(int b = a; b > 0; b--) {
				if(toSort[b-1].compareTo(toSort[b]) > 0) {
					switchElements(toSort, b-1, b);
				} else {
					break;
				}
			}
		}
	}
	
	//shellsort
	/*
	 * Insertion sort, but sort with subsequences decreasing in increment size
	 * Run time: unknown, n^(6/5)?
	 * Extra space: 1
	 * unstable, in place
	 */
	public void shellSort(Comparable[] toSort) {
		int incSeq = 1;
		while(incSeq < toSort.length/3) {
			incSeq = incSeq*3 + 1;
		}
		while(incSeq > 0) {
			for(int a = 0; a < toSort.length; a += incSeq) {
				for(int b = a; b >= incSeq; b -= incSeq) {
					if(toSort[b-incSeq].compareTo(toSort[b]) > 0) {
						switchElements(toSort, b-incSeq, b);
					} else {
						break;
					}
				}
				
			}
			incSeq /= 3;
		}
	}
	
	//mergesort
	/*
	 * 
	 */
	public void mergeSort(Comparable[] toSort) {
		aux = new Comparable[toSort.length];
		mergeSort(toSort, 0, toSort.length-1);
	}
	
	private void mergeSort(Comparable[] toSort, int a, int b) {
		if(b > a) {
			mergeSort(toSort, a, a + (b-a)/2);
			mergeSort(toSort, a + (b-a)/2+1, b);
			merge(toSort, a, a + (b-a)/2, b);
		}
	}
	
	private void merge(Comparable[] toSort, int min, int mid, int max) {
		for(int x = min; x <= max; x++) {
			aux[x] = toSort[x];
		}
		
		int a = min;
		int b = mid + 1;
		
		for(int x = min; x <= max; x++) {
			if(a > mid) {
				toSort[x] = aux[b++];
			} else if(b > max) {
				toSort[x] = aux[a++];
			} else if(aux[a].compareTo(aux[b]) < 0) {
				toSort[x] = aux[a++];
			} else {
				toSort[x] = aux[b++];
			}
		}
	}
	
	//quicksort
	/*
	 * 
	 */
	public void quickSort(Comparable[] toSort) {
		shuffle(toSort);
		quickSort(toSort, 0, toSort.length-1);
	}
	
	public void quickSort(Comparable[] toSort, int min, int max) {
		if(min < max) {
			int partition = partition(toSort, min, max);
			quickSort(toSort, min, partition - 1);
			quickSort(toSort, partition + 1 , max);
		}
	}
	
	public int partition(Comparable[] toSort, int min, int max) {
		int a = min;
		int b = max;
		while(true) {
			while(toSort[a].compareTo(toSort[min]) < 0) {
				if(a == max) { break;}
				a++;
			}
			while(toSort[b].compareTo(toSort[min]) > 0) {
				if(b == min) { break;}
				b--;
			}
			if(b <= a) { break;}
			switchElements(toSort, a, b);
		}
		
		switchElements(toSort, min, b);
		
		return b;
	}
	
	public void shuffle(Comparable[] toShuffle) {
		Random rand = new Random();
		for(int x = 0; x < toShuffle.length; x++) {
			int randIndex = rand.nextInt(toShuffle.length);
			switchElements(toShuffle, x, randIndex);
		}
	}
	
	
	public static void main(String[] args) {
		Integer[] toSort = new Integer[50];
		for(int x = 0; x < toSort.length; x++) {
			toSort[x] = toSort.length - x;
		}
		MySort sorts = new MySort();
		
		//sorts.selectionSort(toSort);
		//sorts.insertionSort(toSort);
		//sorts.shellSort(toSort);
		//sorts.mergeSort(toSort);
		//sorts.quickSort(toSort);
		
		for(int x = 0; x < toSort.length; x++) {
			System.out.print(toSort[x] + " ");
			if((x+1)%10 == 0) {
				System.out.println();
			}
		}
		
	}
}
