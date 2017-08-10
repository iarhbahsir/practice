package algorithmsAndDataStructures;

public class MyGraphConnectedComponents {
	//connected components
	
	private boolean[] searched;
	private int[] compID;
	private int currComp;
	
	public MyGraphConnectedComponents(MyGraph g) {
		searched = new boolean[g.vertices()];
		compID = new int[g.vertices()];
		currComp = 0;
		
		for(int x = 0; x < g.vertices(); x++) {
			if(!searched[x]) {
				dfs(g, x);
				currComp++;
			}
		}
	}
	
	//depth first search
	private void dfs(MyGraph g, int v) {
		searched[v] = true;
		compID[v] = currComp;
		
		for(int x : g.adjVertices(v)) {
			if(searched[x] == false) {
				dfs(g, x);
			}
		}
	}
}
