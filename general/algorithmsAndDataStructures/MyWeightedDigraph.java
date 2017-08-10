package algorithmsAndDataStructures;

import java.util.ArrayList;

public class MyWeightedDigraph {
	private int vertices;
	private int edges;
	private ArrayList<MyDiEdge>[] adjList;
	
	public MyWeightedDigraph(int v) {
		vertices = v;
		edges = 0;
		adjList = (ArrayList<MyDiEdge>[]) new ArrayList[v];
	}
	
	public void addEdge(int a, int b, int w) {
		MyDiEdge toAdd = new MyDiEdge(a, b, w);
		addEdge(toAdd);
	}
	
	public void addEdge(MyDiEdge toAdd) {
		int a = toAdd.from();
		adjList[a].add(toAdd);
		edges++;
	}
	
	public int numEdges() {
		return edges;
	}
	
	public int numVertices() {
		return vertices;
	}
	
	public ArrayList<MyDiEdge> adjEdges(int v) {
		return adjList[v];
	}
}
