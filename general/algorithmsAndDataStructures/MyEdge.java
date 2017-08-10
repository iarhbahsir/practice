package algorithmsAndDataStructures;

public class MyEdge implements Comparable<MyEdge> {
	private int a;
	private int b;
	private double weight;
	
	public MyEdge(int a, int b, double w) {
		this.a = a;
		this.b = b;
		this.weight = w;
	}
	
	public int aVertex() {
		return a;
	}
	
	public int otherVertex(int x) {
		if(x == a) {
			return b;
		}
		
		return a;
	}
	
	public double weight() {
		return weight;
	}
	
	public int compareTo(MyEdge e) {
		if(e.weight > weight) {
			return -1;
		} else if(e.weight < weight) {
			return 1;
		} else {
			return 0;
		}
	}
}
