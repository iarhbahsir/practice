package algorithmsAndDataStructures;

public class MyDiEdge implements Comparable<MyDiEdge>{
	private int a;
	private int b;
	private double weight;
	
	public MyDiEdge(int a, int b, double w) {
		this.a = a;
		this.b = b;
		this.weight = w;
	}
	
	public int from() {
		return a;
	}
	
	public int to() {
		return b;
	}
	
	public double weight() {
		return weight;
	}
	
	public int compareTo(MyDiEdge e) {
		if(e.weight > weight) {
			return -1;
		} else if(e.weight < weight) {
			return 1;
		} else {
			return 0;
		}
	}
}
