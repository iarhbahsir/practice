package algorithmsAndDataStructures;

import java.util.ArrayList;

public class MyGraphMST {
	//MST using edge-weighted graph
	private MyPriorityQueue pq;
	private boolean[] marked;
	private ArrayList<MyEdge> onTree;
	private double[] weightTo;
	
	public MyGraphMST(MyWeightedGraph g) {
		pq = new MyPriorityQueue();
		marked = new boolean[g.numVertices()];
		onTree = new ArrayList<MyEdge>();
		weightTo = new double[g.numVertices()];
		mark(g, 0);
		
		while(!pq.isEmpty()) {
			MyEdge e = (MyEdge) pq.deleteMin();
			
			int a = e.aVertex();
			int b = e.otherVertex(a);
			
			if(marked[a] && marked[b]) {
				continue;
			}
			
			onTree.add(e);
			
			if(!marked[a]) {
				mark(g, a);
			} else {
				mark(g, b);
			}
			
		}
	}
	
	public void mark(MyWeightedGraph g, int v) {
		marked[v] = true;
		
		for(MyEdge e : g.adjEdges(v)) {
			if(!marked[e.otherVertex(v)]) {
				pq.insert(e);
			}
		}
	}
	
	public ArrayList<MyEdge> mst() {
		return onTree;
	}
	
}
