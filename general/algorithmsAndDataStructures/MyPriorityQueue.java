package algorithmsAndDataStructures;

public class MyPriorityQueue {
	
	private Comparable[] pq;
	private int size;
	
	public MyPriorityQueue() {
		pq = new Comparable[16];
		size = 0;
	}
	
	public MyPriorityQueue(int capacity) {
		pq = new Comparable[capacity];
		size = 0;
	}
	
	//constructor for array to PQ
	public MyPriorityQueue(Comparable[] toPQ) {
		pq = new Comparable[toPQ.length];
		size = 0;
		for(Comparable toInsert: toPQ) {
			insert(toInsert);
		}
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
	public void insert(Comparable toInsert) {
		pq[size] = toInsert;
		swim(size);
		size++;
		resize();
	}
	
	//min
	public Comparable minimum() {
		return pq[0];
	}
	
	//delete min
	public Comparable deleteMin() {
		Comparable toReturn = minimum();
		switchElements(0, size - 1);
		pq[size-1] = null;
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
