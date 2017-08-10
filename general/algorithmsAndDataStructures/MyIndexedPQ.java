package algorithmsAndDataStructures;

import java.util.ArrayList;
import java.util.Arrays;

public class MyIndexedPQ {
	private Comparable[] pq;
	private int[] indices;
	private int size;
	
	public MyIndexedPQ() {
		pq = new Comparable[16];
		indices = new int[16];
		size = 0;
	}
	
	public MyIndexedPQ(int capacity) {
		pq = new Comparable[capacity];
		indices = new int[capacity];
		size = 0;
	}
	
	//swim
	private void swim(int x) {
		while(x != 0 && pq[x].compareTo(pq[x/2]) < 0) {
			switchElements(x, x/2);
			x = x/2;
		}
	}
	
	//sink
	private void sink(int x) {
		while(true) {
			//System.out.println(x + " " + pq.length);
			if(size > 2*x+1 && pq[x].compareTo(pq[2*x+1]) > 0) {
				if(pq[2*x+1].compareTo(pq[2*x]) < 0) {
					switchElements(x, 2*x+1);
					x = 2*x+1;
				} else {
					switchElements(x, 2*x);
					x = 2*x;
				}
			} else if(size > 2*x && pq[x].compareTo(pq[2*x]) > 0) {
				switchElements(x, 2*x);
				x = 2*x;
			} else {
				break;
			}
		}
	}
	
	//insert
	public void insert(Comparable toInsert, int index) {
		pq[size] = toInsert;
		indices[size] = index;
		swim(size);
		size++;
		resize();
	}
	
	//min
	public Comparable minimum() {
		return pq[0];
	}
	
	public int minimumIndex() {
		return indices[0];
	}
	
	//delete min
	public Comparable deleteMin() {
		Comparable toReturn = minimum();
		switchElements(0, size - 1);
		pq[size-1] = null;
		indices[size-1] = 0;
		size--;
		sink(0);
		resize();
		return toReturn;
	}
	
	//is empty
	public boolean isEmpty() {
		return size == 0;
	}
	
	//size
	public int size() {
		return size;
	}
	
	//return sorted array
	public Comparable[] sortedArray() {
		Comparable[] sorted = new Comparable[size];
		Comparable[] preserved = new Comparable[pq.length];
		
		for(int x = 0; x < size; x++) {		
			preserved[x] = pq[x];
		}
		
		int x = 0;
		while(!isEmpty()) {
			sorted[x++] = deleteMin();
		}
		
		pq = preserved;
		size = sorted.length;
		
		return sorted;
	}
	
	private void switchElements(int a, int b) {
		Comparable temp = pq[a];
		pq[a] = pq[b];
		pq[b] = temp;
		
		int temp2 = indices[a];
		indices[a] = indices[b];
		indices[b] = temp2;
	}
	
	private void resize() {
		if(size <= pq.length/4) {
			resize(pq.length/2);
		}
		
		if(size >= pq.length*3/4) {
			resize(pq.length*2);
		}
	}
	
	private void resize(int newCap) {
		Comparable[] newArray = new Comparable[newCap];
		for(int x = 0; x < newCap && x < pq.length; x++) {
			newArray[x] = pq[x];
		}
		pq = newArray;
		
		int[] newIndices = new int[newCap];
		for(int x = 0; x < newCap && x < indices.length; x++) {
			newIndices[x] = indices[x];
		}
		indices = newIndices;
	}
	
	public boolean containsIndex(int i) {
		return Arrays.asList(indices).contains(i);
	}
	
	public void change(Comparable changeTo, int i) {
		int changeIndex = Arrays.asList(indices).indexOf(i);
		pq[changeIndex] = changeTo;
	}
	
	public static void main(String[] args) {
		Comparable[] toSort = new Integer[50];
		for(int x = 0; x < toSort.length; x++) {
			toSort[x] = toSort.length - x;
		}
		
		MyPriorityQueue myPQ = new MyPriorityQueue(toSort);
		toSort = myPQ.sortedArray();
		
		for(int x = 0; x < toSort.length; x++) {
			System.out.print(toSort[x] + " ");
			if((x+1)%10 == 0) {
				System.out.println();
			}
		}
	}
}
