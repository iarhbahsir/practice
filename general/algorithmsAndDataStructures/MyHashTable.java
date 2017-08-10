package algorithmsAndDataStructures;

public class MyHashTable {
	
	private int size;
	private Comparable[] keys;
	private Object[] values;
	
	public MyHashTable() {
		size = 0;
		keys = new Comparable[16];
		values = new Object[16];
	}
	
	public void put(Comparable key, Object value, boolean replaceIfPresent) {
		int pos = hash(key);
		
		if(keys[pos] != null && keys[pos].compareTo(key) == 0) {
			if(replaceIfPresent) {
				values[pos] = value;
			}
		} else {
			while(keys[pos] != null) {
				pos = ++pos % keys.length;
			}
			
			keys[pos] = key;
			values[pos]  = value;
			size++;
		}
		
		resize();
	}
	
	public Object get(Comparable key) {
		int pos = hash(key);
		int ogPos = pos; 
		
		while(keys[pos].compareTo(key) != 0) {
			pos = ++pos % keys.length;
			if(pos == ogPos) {
				return null;
			}
		}
		
		return values[pos];
	}
	
	public boolean remove(Comparable key) {
		int pos = hash(key);
		int ogPos = pos;
		
		while(keys[pos] == null || keys[pos].compareTo(key) != 0) {
			pos = ++pos % keys.length;
			if(pos == ogPos) {
				return false;
			}
		}
		
		keys[pos] = null;
		values[pos] = null;
		
		resize();
		return true;
	}
	
	public int size() {
		return size;
	}
	
	private int hash(Comparable key) {
		return (key.hashCode() & 0x7fffffff) % keys.length;
	}
	
	private void resize() {
		if(size > keys.length / 2) {
			resize(keys.length * 2);
		} else if(size < keys.length / 8) {
			resize(keys.length / 2);
		}
	}
	
	private void resize(int newCap) {
		int x = 0;
		Comparable[] newKeys = new Comparable[newCap];
		Object[] newValues = new Object[newCap];
		
		for(int i = 0; i < keys.length; i++) {
			if(keys[i] != null) {
				newKeys[x] = keys[i];
				newValues[x] = values[i];
				x++;
				
				if(x == newKeys.length) {
					break;
				}
			}
		}
		
		keys = newKeys;
		values = newValues;
	}
	
	public String toString() {
		if(size == 0) {
			return "Empty";
		}
		
		String toReturn = "";
		
		for(int x = 0; x < keys.length; x++) {
			if(keys[x] != null) {
				toReturn += "[" + keys[x].toString() + ":" + values[x].toString() + "]\n";
			}
		}
		
		return toReturn;
	}
	
	public static void main(String[] args) {
		MyHashTable myHT = new MyHashTable();
		myHT.put("a", 0, false);
		myHT.put("b", 1, false);
		myHT.put("c", 2, false);
		myHT.put("d", 3, false);
		myHT.put("e", 4, false);
		myHT.put("f", 5, false);
		myHT.put("f", 6, true);
		myHT.remove("c");
		System.out.println(myHT.get("f"));
		
		
		System.out.println(myHT);
	}
}
