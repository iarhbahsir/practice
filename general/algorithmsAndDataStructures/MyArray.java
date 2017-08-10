package algorithmsAndDataStructures;

public class MyArray<E> {
	
	private int numElements;
	private int capacity;
	final private int DEFAULT_CAPACITY = 16;
	E[] elements; 
	
	//create MyArray with default capacity
	public MyArray() {
		capacity = DEFAULT_CAPACITY;
		numElements = 0;
		elements = (E[]) new Object[capacity];
	}
	
	//create MyArray based on given capacity
	public MyArray(int capacity) {
		this.capacity = capacity;
		numElements = 0;
		elements = (E[]) new Object[capacity];
	}
	
	//number of elements stored
	public int getNumElements() {
		return numElements;
	}
	
	//capacity
	public int getCapacity() {
		return capacity;
	}
	
	//empty?
	public boolean isEmpty() {
		return (numElements == 0);
	}
	
	//element at index, null if out of bounds
	public E atIndex(int index) {
		if(index < numElements) {
			return elements[index];
		} else {
			return null;
		}
	}
	
	//push
	public void push(E toPush) {
		elements[numElements] = toPush;
		numElements++;
		resize();
	}
	
	//pop
	public E pop() {
		E toPop = elements[numElements-1];
		elements[numElements-1] = null;
		numElements--;
		resize();
		return toPop;
	}
	
	//enqueue
	public void enqueue(E toEnqueue) {
		elements[numElements] = toEnqueue;
		numElements++;
		resize();
	}
	
	//dequeue
	public E dequeque() {
		E toDequeque = elements[0];
		for(int x = 1; x < numElements; x++) {
			elements[x-1] = elements[x];
		}
		numElements--;
		resize();
		return toDequeque;
	}
	
	//prepend
	public void prepend(E toPrepend) {
		for(int x = numElements; x > 0; x--) {
			elements[x] = elements[x-1];
		}
		elements[0] = toPrepend;
		numElements++;
		resize();
	}
	
	//insert element at index
	public void insertAtIndex(E toInsert, int atIndex) {
		for(int x = numElements; x > atIndex; x--) {
			elements[x] = elements[x-1];
		}
		elements[atIndex] = toInsert;
		numElements++;
		resize();
	}
	
	//delete element at index
	public void deleteFromIndex(int fromIndex) {
		for(int x = fromIndex; x < numElements; x++) {
			elements[x] = elements[x+1];
		}
		numElements--;
		resize();
	}
	
	//remove all occurrences of specified element
	public int remove(E toRemove) {
		int numRemoved = 0;
		for(int x = 0; x < numElements; x++) {
			if(elements[x].equals(toRemove)) {
				for(int i = 0; i < numElements; i++) {
					elements[x] = elements[x+1];
				}
				numRemoved++;
			}
		}
		numElements -= numRemoved;
		return numRemoved;
	}
	
	//return first index of specified element
	public int indexOf(E toFind) {
		for(int x = 0; x < numElements; x++) {
			if(elements[x].equals(toFind)) {
				return x;
			}
		}
		return -1;
	}
	//resize default
	private void resize() {
		if(numElements <= capacity/4) {
			resize(capacity/2);
		}
		
		if(numElements >= capacity*3/4) {
			resize(capacity*2);
		}
	}
	
	//resize to specified capacity
	private void resize(int newCap) {
		E[] newArray = (E[]) new Object[newCap];
		for(int x = 0; x < newCap && x < capacity; x++) {
			newArray[x] = elements[x];
		}
		elements = newArray;
		capacity = newCap;
	}
}
