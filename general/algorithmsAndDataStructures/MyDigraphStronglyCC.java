package algorithmsAndDataStructures;

import java.util.Collections;

public class MyDigraphStronglyCC {
	
	private int currIndex;
	private int[] comps;
	private boolean[] marked;
	
	
	public MyDigraphStronglyCC(MyDigraph g) {
		currIndex = 0;
		comps = new int[g.numVertices()];
		marked = new boolean[g.numVertices()];
		for(int v = 0; v < g.numVertices(); v++) {
			if(!marked[v]) {
				dfs(g, 0);
				currIndex++;
			}
			
		}
		
	}
	
	private void dfs(MyDigraph g, int v) {
		marked[v] = true;
		comps[v] = currIndex;
		
		for(int x : g.adjVertices(v)) {
			if(marked[x] == false) {
				dfs(g, x);
			}
		}
	}
	
	public int numSCC() {
		return currIndex;
	}
	
	public int sccIndex(int v) {
		return comps[v];
	}
}