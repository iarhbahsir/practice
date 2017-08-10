package algorithmsAndDataStructures;

import java.util.HashMap;

public class MySymbolGraph<E>{
	
	//symbol graph
	
	private MyGraph g;
	private HashMap<E, Integer> symbols;
	private E[] symbolsInverted;
	private int currIndex;
	
	public MySymbolGraph(int v) {
		g = new MyGraph(v);
		symbols = new HashMap<E, Integer>();
		symbolsInverted = (E[]) new Object[v];
		currIndex = 0;
	}
	
	public void addEdge(E a, E b) {
		if(!symbols.containsKey(a)) {
			symbols.put(a, currIndex);
			symbolsInverted[currIndex] = a;
			currIndex++;
		}
		
		if(!symbols.containsKey(b)) {
			symbols.put(b, currIndex);
			symbolsInverted[currIndex] = b;
			currIndex++;
		}
		
		g.addEdge(indexOf(a), indexOf(b));
	}
	
	public int indexOf(E v) {
		if(symbols.containsKey(v)) {
			return symbols.get(v);
		}
		
		return -1;
	}
	
	public E atIndex(int i) {
		if(i >=0 && i < currIndex) {
			return symbolsInverted[i];
		}
		
		return null;
	}
	
	public MyGraph getGraph() {
		return g;
	}
}
