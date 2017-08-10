package algorithmsAndDataStructures;

public class MyLinkedList<E> {

	private class Node {
		public E value;
		public Node nextNode;
	}
	
	// implement tail pointer?
	private Node firstNode;
	private int size;
	
	public MyLinkedList(E firstValue) {
		firstNode = new Node();
		firstNode.value = firstValue;
		size = 1;
	}
	
	// number of elements in list
	public int size() {
		return size;
	}
	
	// return true if list is empty
	public boolean isEmpty() {
		return size == 0;
	}
	
	// returns value at index or null if out of bounds
	public E valueAt(int index) {
		Node currNode = firstNode;
		for(int i = 0; i < index; i++) {
			currNode = currNode.nextNode;
			if(currNode == null) {
				return null;
			}
		}
		return currNode.value;
	}
	
	// add value to first
	public void addToFront(E toAdd) {
		Node newFirst = new Node();
		newFirst.value = toAdd;
		newFirst.nextNode = firstNode;
		firstNode = newFirst;
		size++;
	}
	
	// remove and return value at first
	public E removeFirst() {
		E firstValue = firstNode.value;
		firstNode = firstNode.nextNode;
		size--;
		return firstValue;
	}
	
	// add value to back
	public void addToBack(E toAdd) {
		Node newTail = new Node();
		newTail.value = toAdd;
		Node currNode = firstNode;
		while(currNode.nextNode != null) {
			currNode = currNode.nextNode;
		}
		currNode.nextNode = newTail;
		size--;
	}
	
	// remove and return value at back
	public E removeLast() {
		Node currNode = firstNode;
		for(int i = 0; i < size - 1 ; i++) {
			currNode = currNode.nextNode;
		}
		E lastValue = currNode.nextNode.value;
		currNode.nextNode = null;
		size--;
		return lastValue;
	}
	
	// return value at first
	public E getFirstValue() {
		return firstNode.value;
	}
	
	// return value at back
	public E getLastValue() {
		Node currNode = firstNode;
		for(int i = 0; i < size; i++) {
			currNode = currNode.nextNode;
		}
		E lastValue = currNode.value;
		return lastValue;
	}
	
	// insert value at index so that the current value is pointed at by the new one
	public void insertAt(E toInsert, int index) {
		if(index >= size) {
			return;
		}
		Node currNode = firstNode;
		for(int i = 0; i < index - 1; i++) {
			currNode = currNode.nextNode;
		}
		
		Node toShift = currNode.nextNode;
		currNode.nextNode = new Node();
		currNode.nextNode.value = toInsert;
		currNode.nextNode.nextNode = toShift;
		size++;
	}
	
	// remove and return value at index
	public E removeAt(int index) {
		if(index >= size) {
			return null;
		}
		Node currNode = firstNode;
		for(int i = 0; i < index - 1; i++) {
			currNode = currNode.nextNode;
		}
		E toReturn = currNode.nextNode.value;
		currNode.nextNode = currNode.nextNode.nextNode;
		
		size--;
		return toReturn;
	}
	
	// return value at nth position from the end of the list
	public E removeAtFromBack(int indexFromBack) {
		if(indexFromBack >= size) {
			return null;
		}
		
		Node currNode = firstNode;
		for(int i = 0; i < size - indexFromBack - 1; i++) {
			currNode = currNode.nextNode;
		}
		
		E toReturn = currNode.nextNode.value;
		currNode.nextNode = currNode.nextNode.nextNode;
		size--;
		return toReturn;
	}
	
	// remove first occurrence of value, return true if removed
	public boolean removeValue(E toRemove) {
		Node currNode = firstNode;
		if(firstNode.value.equals(toRemove)) {
			firstNode = firstNode.nextNode;
			return true;
		}
		
		currNode = firstNode;
		for(int i = 0; i < size - 1; i++) {
			if(currNode.nextNode.value.equals(toRemove)) {
				currNode.nextNode = currNode.nextNode.nextNode;
				return true;
			}
		}
		return false;
	}
	
}