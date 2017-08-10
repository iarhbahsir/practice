package algorithmsAndDataStructures;

import java.util.ArrayList;
import java.util.Collections;

public class MyGraphPaths {
	private boolean[] connected;
	private int[] edgeTo;
	private int srcV;
	private int numConnected;
	
	private enum searchType {
		DFS, BFS
	}
	
	public MyGraphPaths(MyGraph g, int srcV, searchType type) {
		connected = new boolean[g.vertices()];
		this.srcV = srcV;
		
		if(type == searchType.DFS) {
			dfs(g, srcV);
		} else {
			bfs(g);
		}
		
	}
	
	//source vertex
	public int sourceVertex() {
		return srcV;
	}
	
	public int numConnected() {
		return numConnected;
	}
	
	//depth first search
	private void dfs(MyGraph g, int v) {
		connected[v] = true;
		
		for(int x : g.adjVertices(v)) {
			if(connected[x] == false) {
				edgeTo[x] = v;
				dfs(g, x);
			}
		}
	}
	
	//breadth first search
	private void bfs(MyGraph g) {
		ArrayList<Integer> toSearch = new ArrayList<Integer>();
		
		toSearch.add(srcV);
		
		while(!toSearch.isEmpty()) {
			int v = toSearch.remove(0);
			
			if(!connected[v]) {
				connected[v] = true;
				
				for(int adj : g.adjVertices(v)) {
					toSearch.add(adj);
					edgeTo[adj] = v;
				}
			}
		}
	}
	
	//whether v1 and v2 are connected (DFS)
	public boolean isConnected(int v) {
		return connected[v];
	}
	
	public ArrayList<Integer> pathTo(int dest) {
		if(!connected[dest]) {
			return null;
		}
		
		ArrayList<Integer> path = new ArrayList<Integer>();
		
		int curr = dest;
		while(curr != srcV) {
			path.add(curr);
			curr = edgeTo[curr];
		}
		path.add(srcV);
		Collections.reverse(path);
		
		return path;
	}
}