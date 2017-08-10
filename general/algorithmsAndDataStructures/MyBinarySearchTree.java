package algorithmsAndDataStructures;

public class MyBinarySearchTree {
	private class Node {
		Comparable key;
		Object value;
		Node left;
		Node right;
		int subSize;
		
		public Node(Comparable key, Object value, int subSize) {
			this.key = key;
			this.value = value;
			this.subSize = subSize;
		}
		
		public String toString() {
			return "[" + this.key.toString() + ":" + this.value.toString() + ":" + this.subSize + "]";
		}
	}
	
	private Node root;
	private Object lastRemovedValue;
	
	public MyBinarySearchTree() {	
		root = null;
	}
	
	public MyBinarySearchTree(Comparable key, Object value) {
		root = new Node(key, value, 1);
	}
	
	//search and return value or null
	public Object get(Comparable getKey) {
		return get(getKey, root);
	}
		
	public Object get(Comparable getKey, Node n) {
		if(n == null) {
			return null;
		}
		
		int comp = n.key.compareTo(getKey);
		
		if(comp == 0) {
			return n.value;
		} else if(comp > 0) {
			return get(getKey, n.left);
		} else {
			return get(getKey, n.right);
		}
	}
	
	//insert and return whether inserted
	public void put(Comparable putKey, Object putValue, boolean replaceIfPresent) {
		Node toPut = new Node(putKey, putValue, 1);
		root = put(toPut, replaceIfPresent, root);
	}
	
	public Node put(Node toPut, boolean replaceIfPresent, Node n) {
		if(n == null) {
			return toPut;
		}
		
		int comp = n.key.compareTo(toPut.key);
		
		if(comp > 0) {
			n.left = put(toPut, replaceIfPresent, n.left);
		} else if (comp < 0) {
			n.right = put(toPut, replaceIfPresent, n.right);
		} else {
			if(replaceIfPresent) {
				n.value = toPut.value;
			}
		}
		
		n.subSize = subSize(n.left) + subSize(n.right) + 1;
		return n;
	}
	
	//size
	public int size() {
		return root.subSize;
	}
	
	public int subSize(Node n) {
		return (n == null)? 0 : n.subSize;
	}
	
	//value of max key
	public Object max() {
		Node n = root;
		
		while(n.right != null) {
			n = n.right;
		}
		
		return n.value;
	}
	
	//max key
	public Comparable maxKey() {
		Node n = root;
		
		while(n.right != null) {
			n = n.right;
		}
		
		return n.key;
	}
	
	//min key
	public Comparable min() {
		Node n = root;
		
		while(n.left != null) {
			n = n.left;
		}
		
		return n.key;
	}
	
	//floor
	public Comparable floor(Comparable key) {
		Node toReturn = floor(key, root);
		return (toReturn == null) ? null : toReturn.key;
	}
	
	public Node floor(Comparable key, Node n) {
		if(n == null) {
			return null;
		}
		
		int comp = key.compareTo(n.key);
		
		Node rightFloor;
		
		if(comp == 0) {
			return n;
		} else if(comp < 0) {
			return floor(key, n.left);
		}
		
		rightFloor = floor(key, n.right);
		
		return (rightFloor == null)? n : rightFloor;
	}
	
	//ceiling
	public Comparable ceiling(Comparable key) {
		Node toReturn = ceiling(key, root);
		return (toReturn == null)? null : toReturn.key;
	}
	
	public Node ceiling(Comparable key, Node n) {
		if(n == null) {
			return null;
		}
		
		int comp = key.compareTo(n.key);
		
		if(comp == 0) {
			return n;
		} else if(comp > 0) {
			return ceiling(key, n.right);
		} else {
			Node leftCeiling = ceiling(key, n.left);
			return (leftCeiling == null)? n : leftCeiling;
		}
		
	}
	
	//rank
	public int rank(Comparable key) {
		return (get(key) == null)? -1 : rank(key, root);
	}
	
	public int rank(Comparable key, Node n) {
		if(n == null) {
			return 0;
		}
		
		int comp = key.compareTo(n.key);
		int toReturn = 0;
		
		if(comp == 0) {
			return subSize(n.left) + 1;
		} else if(comp > 0) {
			return subSize(n.left) + 1 + rank(key, n.right);
		} else {
			return rank(key, n.left);
		}
	}
	
	//delete min
	public Object removeMin() {
		root = removeMin(root);
		return lastRemovedValue;
	}
	
	public Node removeMin(Node n) {
		if(n.left == null) {
			lastRemovedValue = n.value;
			return n.right;
		} else {
			n.left = removeMin(n.left);
			n.subSize = subSize(n.left) + subSize(n.right) + 1;
			return n;
		}
	}
	
	//delete max
	public Object removeMax() {
		root = removeMax(root);
		return lastRemovedValue;
	}
	
	public Node removeMax(Node n) {
		if(n.right == null) {
			lastRemovedValue = n.value;
			return n.left;
		} else {
			n.right = removeMax(n.right);
			n.subSize = subSize(n.left) + subSize(n.right) + 1;
			return n;
		}
	}
	
	//delete using key, return value or null if not present
	public Object remove(Comparable key) {
		root = remove(key, root);
		return lastRemovedValue;
	}
	
	public Node remove(Comparable key, Node n) {
		if(n == null) {
			return null;
		}
		
		int comp = key.compareTo(n.key);
		
		if(comp > 0) {
			n.right = remove(key, n.right);
			n.subSize = subSize(n.left) + subSize(n.right) + 1;
			return n;
		} else if(comp < 0) {
			n.left = remove(key, n.left);
			n.subSize = subSize(n.left) + subSize(n.right) + 1;
			return n;
		} else {
			if(n.left == null) {
				return n.right;
			} else if(n.right == null) {
				return n.left;
			} else {
				lastRemovedValue = n.value;
				Node successor = removeMin(n.right);
				successor.left = n.left;
				successor.right = n.right;
				successor.subSize = subSize(successor.left) + subSize(successor.right) + 1;
				return successor;
			}
		}
	}
	
	public String toString() {
		return toString(root);
	}
	
	public String toString(Node n) {
		if(n == null) {
			return "";
		} else {
			return n.toString() + "\n" + toString(n.left) + "\n" + toString(n.right);
		}
	}
	
	public static void main(String[] args) {
		MyBinarySearchTree myBST = new MyBinarySearchTree();
		myBST.put("c", 1, false);
		myBST.put("f", 2, false);
		myBST.put("a", 3, false);
		myBST.put("d", 17, false);
		System.out.println(myBST.ceiling("b"));
		System.out.println(myBST);
	}
}
