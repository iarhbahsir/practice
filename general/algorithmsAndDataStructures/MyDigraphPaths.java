package algorithmsAndDataStructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class MyDigraphPaths {
	private boolean[] connected;
	private int[] edgeTo;
	private int srcV;
	private int numConnected;
	private ArrayList<Integer> dc;
	private boolean[] onDCStack;
	private ArrayList<Integer> preOrder;
	private ArrayList<Integer> postOrder;
	private Stack<Integer> revPostOrder;
	
	
	private enum searchType {
		DFS, BFS
	}
	
	public MyDigraphPaths(MyDigraph g, int srcV, searchType type) {
		dc = new ArrayList<Integer>();
		onDCStack = new boolean[g.numVertices()];
		preOrder = new ArrayList<Integer>();
		postOrder = new ArrayList<Integer>();
		revPostOrder = new Stack<Integer>();
		connected = new boolean[g.numVertices()];
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
	private void dfs(MyDigraph g, int v) {
		preOrder.add(v);
		connected[v] = true;
		onDCStack[v] = true;
		
		for(int x : g.adjVertices(v)) {
			if(connected[x] == false) {
				edgeTo[x] = v;
				dfs(g, x);
			} else if(onDCStack[x] && !hasDirectedCycle()) {
				int i = x;
				do {
					dc.add(i);
					i = edgeTo[x];
				} while(i != x);
				dc.add(i);
				Collections.reverse(dc);
			}
		}
		postOrder.add(v);
		revPostOrder.push(v);
	}
	
	//breadth first search
	private void bfs(MyDigraph g) {
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
		
	//reachable vertices from source vertex
	public ArrayList<Integer> reachable() {
		ArrayList<Integer> reachable = new ArrayList<Integer>();
		for(int x = 0; x < connected.length; x++) {
			if(connected[x]) {
				reachable.add(x);
			}
		}
		
		return reachable;
	}

	//find directed cycles
	public ArrayList<Integer> directedCycle() {
		if(dc.isEmpty()) {
			return null;
		}
		
		return dc;
	}
	
	public boolean hasDirectedCycle() {
		return !dc.isEmpty();
	}
	
	//vertices in pre order, post order, reverse post order
	//topological order 
	public ArrayList<Integer> preOrder() {
		return preOrder;
	}
	
	public ArrayList<Integer> postOrder() {
		return postOrder;
	}
	
	public Stack<Integer> revPostOrder() {
		return revPostOrder;
	}
	
	public Stack<Integer> topological() {
		if(hasDirectedCycle()) {
			return null;
		}
		
		return revPostOrder;
	}
	
	
}
