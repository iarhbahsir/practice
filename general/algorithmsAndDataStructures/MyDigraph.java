package algorithmsAndDataStructures;

import java.util.ArrayList;

public class MyDigraph {
	private int vertices;
	private int edges;
	private ArrayList<Integer>[] adjList;
	
	public MyDigraph(int v) {
		vertices = v;
		edges = 0;
		adjList = (ArrayList<Integer>[]) new ArrayList[v];
	}
	
	//number of vertices
	public int numVertices() {
		return vertices;
	}
	
	//number of edges
	public int numEdges() {
		return edges;
	}
	
	//add directed edge
	public void addEdge(int a, int b) {
		adjList[a].add(b);
		edges++;
	}
	
	//reverse of graph
	public MyDigraph reversed() {
		MyDigraph reverse = new MyDigraph(vertices);
		
		for(int a = 0; a < adjList.length; a++) {
			for(int b = 0; b < adjList[a].size(); b++) {
				reverse.addEdge(b, a);
			}
		}
		
		return reverse;
	}
	
	public ArrayList<Integer> adjVertices(int v) {
		return adjList[v];
	}
}
