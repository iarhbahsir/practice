package algorithmsAndDataStructures;

import java.util.ArrayList;

public class MyWeightedGraph {
	private int vertices;
	private int edges;
	private ArrayList<MyEdge>[] adjList;
	
	public MyWeightedGraph(int v) {
		vertices = v;
		edges = 0;
		adjList = (ArrayList<MyEdge>[]) new ArrayList[v];
	}
	
	public void addEdge(int a, int b, int w) {
		MyEdge toAdd = new MyEdge(a, b, w);
		addEdge(toAdd);
	}
	
	public void addEdge(MyEdge toAdd) {
		int a = toAdd.aVertex();
		int b = toAdd.otherVertex(a);
		adjList[a].add(toAdd);
		adjList[b].add(toAdd);
		edges++;
	}
	
	public int numEdges() {
		return edges;
	}
	
	public int numVertices() {
		return vertices;
	}
	
	public ArrayList<MyEdge> adjEdges(int v) {
		return adjList[v];
	}
}
