import java.util.*;
import java.util.function.Consumer;
import java.io.*;

public class HashTable {
	
	
	private static class Entry {
		String key;
		Object value;
		boolean deleted; 
		
		Entry(String key, Object value) {
			this.key = key;
			this.value = value;
			this.deleted = false;
		}
	}
	
	private Entry[] table;
	private static final int DEFAULT_SIZE = 101; 
	
	public HashTable() {
		this(DEFAULT_SIZE);
	}
	
	
	public HashTable(int size) {
		table = new Entry[size];
	}
	
	
	//Precondition: key is non-null.
	//Postcondition: returns a valid index within [0, table.length-1].
	private int hash(String key) {
		int hashCode = key.hashCode();
		int index = Math.abs(hashCode) % table.length;
		return index;
	}
	
	//Precondition: key is non-null.
	//Postcondition: key is stored with value, existing key value replaced.
	public void put(String key, Object value) {
		if (key == null) {
			throw new IllegalArgumentException("Key cannot be null");
		}
		
		int index = hash(key);
		int originalIndex = index;
		int firstDeletedIndex = -1; 
		
		
		while (table[index] != null) {
			
			if (!table[index].deleted && table[index].key.equals(key)) {
				table[index].value = value;
				return;
			}
			
			if (table[index].deleted && firstDeletedIndex == -1) {
				firstDeletedIndex = index;
			}
			
			index = (index + 1) % table.length;
			
			
			if (index == originalIndex) {
				throw new RuntimeException("Hash table is full");
			}
		}
		
		
		if (firstDeletedIndex != -1) {
			index = firstDeletedIndex;
		}
		
		table[index] = new Entry(key, value);
	}
	

	//Precondition: key may be null.
	//Postcondition: returns value for key if present, otherwise null.
	public String get(String key) {
		if (key == null) {
			return null;
		}
		
		int index = hash(key);
		int originalIndex = index;
		
		
		while (table[index] != null) {
			
			if (!table[index].deleted && table[index].key.equals(key)) {
				return (String) table[index].value;
			}
			
			index = (index + 1) % table.length;
			
			
			if (index == originalIndex) {
				return null;
			}
		}
		
		return null;
	}
	
	
	//Precondition: key may be null.
	//Postcondition: key is marked deleted if found; returns removed value or null.
	public String remove(String key) {
		if (key == null) {
			return null;
		}
		
		int index = hash(key);
		int originalIndex = index;
		
		
		while (table[index] != null) {
			
			if (!table[index].deleted && table[index].key.equals(key)) {
				String removedValue = (String) table[index].value;
				table[index].deleted = true;
				return removedValue;
			}
			
			index = (index + 1) % table.length;
			
			
			if (index == originalIndex) {
				return null;
			}
		}
		
		return null;
	}
	
	
	//Precondition: none.
	//Postcondition: returns an iterator over live keys in table.
	public Iterator<String> keys() {
		return new HashTableIterator();
	}
	
	
	//Precondition: none.
	//Postcondition: all stored key/value pairs printed to stdout.
	public void print() {
		Iterator iterator = keys();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			String value = get(key);
			System.out.println(key + ": " + value);
		}
	}
	
	
	private class HashTableIterator implements Iterator<String> {
		private int currentIndex = 0;
		private int nextIndex = -1;
		
		
		public HashTableIterator() {
			findNext();
		}
		
		
		private void findNext() {
			nextIndex = -1;
			while (currentIndex < table.length) {
				if (table[currentIndex] != null && !table[currentIndex].deleted) {
					nextIndex = currentIndex;
					break;
				}
				currentIndex++;
			}
		}
		
		
		@Override
		public boolean hasNext() {
			return nextIndex != -1;
		}
		
		
		@Override
		public String next() {
			if (nextIndex == -1) {
				throw new NoSuchElementException("No more elements");
			}
			String key = table[nextIndex].key;
			currentIndex = nextIndex + 1;
			findNext();
			return key;
		}
		
		
		@Override
		public void remove() {
			throw new UnsupportedOperationException("Remove not supported by HashTable iterator");
		}
		
		
		@Override
		public void forEachRemaining(Consumer<? super String> action) {
			throw new UnsupportedOperationException("forEachRemaining not supported by HashTable iterator");
		}
	}
	
	//Precondition: "Lookup.dat" exists and is formatted as key/value pairs.
	//Postcondition: table contains keys/values from file in addition to existing entries.
    public void load() {
        FileReader fileReader;
        BufferedReader bufferedReader = null;
        
        
        try {
            File f = new File(System.getProperty("user.home"), "Lookup.dat");
            fileReader = new FileReader(f);
            bufferedReader = new BufferedReader(fileReader);
        }
        catch (FileNotFoundException e) {
            System.err.println("Cannot find input file \"Lookup.dat\"");
        }
        
        
        try {
            while (true) {
                String key = bufferedReader.readLine();
                if (key == null) return;
                String value = bufferedReader.readLine();
                if (value == null) {
                    System.out.println("Error in input file");
                    System.exit(1);
                }
                String blankLine = bufferedReader.readLine();
                if (!"".equals(blankLine)) {
                    System.out.println("Error in input file");
                    System.exit(1);
                }
                put(key, value);
            }
        }
        catch (IOException e) {
            e.printStackTrace(System.out);
        }
        
        
        try {
            bufferedReader.close( );
        }
        catch(IOException e) {
            e.printStackTrace(System.out);
        }
    }

	
	//Precondition: file can be written in user home folder.
	//Postcondition: current table keys/values are written to Lookup.dat.
	public void save() {
        FileOutputStream stream;
        PrintWriter printWriter = null;
        Iterator iterator;
        
       
        try {
            File f = new File(System.getProperty("user.home"), "Lookup.dat");
            stream = new FileOutputStream(f);
            printWriter = new PrintWriter(stream);
        }
        catch (Exception e) {
            System.err.println("Cannot use output file \"Lookup.dat\"");
        }
       
        
        iterator = keys();
        while (iterator.hasNext()) {
            String key = (String)iterator.next();
            printWriter.println(key);
            String value = (String)get(key);
            value = removeNewlines(value);
            printWriter.println(value);
            printWriter.println();
        }
       
       
        printWriter.close( );
    }
    
    
    private String removeNewlines(String value) {
        return value.replaceAll("\r|\n", " ");
    }

    // /**
    //  * Simple main for manual testing of HashTable functionality.
    //  */
    // public static void main(String[] args) {
    //     HashTable table = new HashTable(11);

    //     System.out.println("=== put/get/remove test ===");
    //     table.put("one", "1");
    //     table.put("two", "2");
    //     table.put("three", "3");
    //     System.out.println("get(one) = " + table.get("one"));
    //     System.out.println("get(two) = " + table.get("two"));
    //     System.out.println("get(three) = " + table.get("three"));

    //     System.out.println("remove(two) -> " + table.remove("two"));
    //     System.out.println("get(two) after remove = " + table.get("two"));

    //     System.out.println("=== print keys after operations ===");
    //     table.print();

    //     System.out.println("=== collision/replace test ===");
    //     table.put("one", "ONE");
    //     System.out.println("get(one) after replace = " + table.get("one"));

    //     System.out.println("=== save/load test ===");
    //     table.save();

    //     HashTable newTable = new HashTable(11);
    //     newTable.load();
    //     newTable.print();
    // }
}
