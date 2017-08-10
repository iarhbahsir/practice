package algorithmsAndDataStructures;

import java.util.ArrayList;

public class MyGraph {
	
	private int vertices;
	private int edges;
	private ArrayList<Integer>[] adjList;
	
	public MyGraph(int v) {
		adjList = (ArrayList<Integer>[]) new ArrayList[v];
	}
	
	//number of vertices
	public int vertices() {
		return vertices;
	}
	
	//number of edges
	public int edges() {
		return edges;
	}
	
	//add edge
	public boolean addEdge(int a, int b) {
		if (a >= 0 && b >= 0 && a < adjList.length && b < adjList.length) {
			adjList[a].add(b);
			adjList[b].add(a);
			edges++;
			return true;
		}
		
		return false;
	}
	
	//vertices adjacent to v
	public ArrayList<Integer> adjVertices(int v) {
		return adjList[v];
	}
	
	//toString
	public String toString() {
		String toReturn  = "";
		return toReturn;
	}
	
	//average degree
	public double avgDegree() {
		double total = 0;
		for(ArrayList<Integer> adj : adjList) {
			total += adj.size();
		}
		return total/((double) adjList.length);
	}
	
	//number of self loops
	public int numSelfLoops() {
		int sl = 0;
		for(int x = 0; x < adjList.length; x++) {
			for(int i = 0; i < adjList[x].size(); i++) {
				if(adjList[adjList[x].get(i)].contains(x)) {
					sl++;
				}
			}
		}
		return sl/2;
	}
	
	//max degree
	public int maxDegree() {
		int max = 0;
		for(ArrayList<Integer> adj : adjList) {
			if(max < adj.size()) {
				max = adj.size();
			}
		}
		return max;
	}
	
	//min degree
	public int minDegree() {
		int min = Integer.MAX_VALUE;
		for(ArrayList<Integer> adj : adjList) {
			if(min > adj.size()) {
				min = adj.size();
			}
		}
		return min;
	}
}
