package algorithmsAndDataStructures;

import java.util.ArrayList;
import java.util.Collections;

public class MyDigraphShortestPaths {
	//shortest path
	//distance to vertex
	private int srcV;
	private MyDiEdge[] edgeTo;
	private double[] weightTo;
	private MyIndexedPQ pq;  
	
	public MyDigraphShortestPaths(MyWeightedDigraph g, int srcV) {
		this.srcV = srcV;
		pq = new MyIndexedPQ();
		edgeTo = new MyDiEdge[g.numVertices()];
		weightTo = new double[g.numVertices()];
		for(double w : weightTo) {
			w = Double.POSITIVE_INFINITY;
		}
		weightTo[srcV] = 0;
		
		pq.insert(weightTo[srcV], srcV);
		
		while(!pq.isEmpty()) {
			int toRelax = pq.minimumIndex();
			pq.deleteMin(); 
			relax(g, toRelax);
		}
	}
	
	private void relax(MyWeightedDigraph g, int v) {
		for(MyDiEdge next : g.adjEdges(v)) {
			if(weightTo[v] + next.weight() < weightTo[next.to()]) {
				weightTo[next.to()] = weightTo[v] + next.weight();
				edgeTo[next.to()] = next;
			}
			
			if(!pq.containsIndex(next.to())) {
				pq.insert(weightTo[next.to()], next.to());
			} else {
				pq.change(weightTo[next.to()], next.to());
			}
		}
	}
	
	public ArrayList<Integer> pathTo(int dest) {
		if(!hasPathTo(dest)) {
			return null;
		}
		
		ArrayList<Integer> path = new ArrayList<Integer>();
		
		int curr = dest;
		while(curr != srcV) {
			path.add(curr);
			curr = edgeTo[curr].from();
		}
		path.add(srcV);
		Collections.reverse(path);
		
		return path;
	}
	
	public boolean hasPathTo(int dest) {
		return weightTo[dest] != Double.POSITIVE_INFINITY;
	}
	
	
}
