import java.util.*;
import java.io.*;

public class HashTable {
	
	/**
	 * Entry class to store key-value pairs in the hash table.
	 * Includes a flag to mark deleted entries (tombstone for linear probing).
	 */
	private static class Entry {
		String key;
		Object value;
		boolean deleted; // Tombstone for deleted entries
		
		Entry(String key, Object value) {
			this.key = key;
			this.value = value;
			this.deleted = false;
		}
	}
	
	private Entry[] table; // Array to store entries
	private static final int DEFAULT_SIZE = 101; // Prime number for better distribution
	
	/**
	 * Constructor initializes the hash table with a default size.
	 */
	public HashTable() {
		this(DEFAULT_SIZE);
	}
	
	/**
	 * Constructor initializes the hash table with a specified size.
	 * @param size The size of the hash table array
	 */
	public HashTable(int size) {
		table = new Entry[size];
	}
	
	/**
	 * Computes the hash index for a given key.
	 * Uses String.hashCode() with absolute value and modulo by table size.
	 * @param key The key to hash
	 * @return The index in the table array
	 */
	private int hash(String key) {
		int hashCode = key.hashCode();
		int index = Math.abs(hashCode) % table.length;
		return index;
	}
	
	/**
	 * Puts a key-value pair into the hash table.
	 * If the key already exists, the value is replaced.
	 * Uses linear probing for collision resolution.
	 * @param key The key (must not be null)
	 * @param value The value to store
	 */
	public void put(String key, Object value) {
		if (key == null) {
			throw new IllegalArgumentException("Key cannot be null");
		}
		
		int index = hash(key);
		int originalIndex = index;
		int firstDeletedIndex = -1; // Track first deleted slot for optimization
		
		// Linear probing with collision handling
		while (table[index] != null) {
			// If we found the key, replace its value
			if (!table[index].deleted && table[index].key.equals(key)) {
				table[index].value = value;
				return;
			}
			// Track first deleted slot we encounter
			if (table[index].deleted && firstDeletedIndex == -1) {
				firstDeletedIndex = index;
			}
			// Move to next index
			index = (index + 1) % table.length;
			
			// Prevent infinite loop if table is full
			if (index == originalIndex) {
				throw new RuntimeException("Hash table is full");
			}
		}
		
		// Use first deleted slot if available, otherwise use the empty slot we found
		if (firstDeletedIndex != -1) {
			index = firstDeletedIndex;
		}
		
		table[index] = new Entry(key, value);
	}
	
	/**
	 * Retrieves the value associated with a given key.
	 * Uses linear probing to find the key.
	 * @param key The key to search for
	 * @return The value associated with the key, or null if not found
	 */
	public String get(String key) {
		if (key == null) {
			return null;
		}
		
		int index = hash(key);
		int originalIndex = index;
		
		// Linear probing to find the key
		while (table[index] != null) {
			// Found the key (and it's not deleted)
			if (!table[index].deleted && table[index].key.equals(key)) {
				return (String) table[index].value;
			}
			// Move to next index
			index = (index + 1) % table.length;
			
			// If we've wrapped around completely, key doesn't exist
			if (index == originalIndex) {
				return null;
			}
		}
		
		return null;
	}
	
	/**
	 * Removes the entry with the specified key from the hash table.
	 * Uses tombstone deletion to maintain linear probing integrity.
	 * @param key The key of the entry to remove
	 * @return The value that was removed, or null if key not found
	 */
	public String remove(String key) {
		if (key == null) {
			return null;
		}
		
		int index = hash(key);
		int originalIndex = index;
		
		// Linear probing to find the key
		while (table[index] != null) {
			// Found the key (and it's not already deleted)
			if (!table[index].deleted && table[index].key.equals(key)) {
				String removedValue = (String) table[index].value;
				table[index].deleted = true; // Mark as deleted (tombstone)
				return removedValue;
			}
			// Move to next index
			index = (index + 1) % table.length;
			
			// If we've wrapped around completely, key doesn't exist
			if (index == originalIndex) {
				return null;
			}
		}
		
		return null;
	}
	
	/**
	 * Returns an iterator over all keys in the hash table.
	 * This iterator is used for save, load, and print operations.
	 * @return An Iterator over the keys
	 */
	public Iterator keys() {
		return new HashTableIterator();
	}
	
	/**
	 * Prints all key-value pairs in the hash table.
	 * Each pair is printed on its own line in the format "key: value".
	 */
	public void print() {
		Iterator iterator = keys();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			String value = get(key);
			System.out.println(key + ": " + value);
		}
	}
	
	/**
	 * Iterator implementation for traversing hash table keys.
	 * Skips deleted entries and null slots.
	 */
	private class HashTableIterator implements Iterator {
		private int currentIndex = 0;
		private int nextIndex = -1;
		
		/**
		 * Constructor finds the first valid entry.
		 */
		public HashTableIterator() {
			findNext();
		}
		
		/**
		 * Finds the next valid (non-deleted, non-null) entry.
		 */
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
		
		/**
		 * Returns true if there are more elements to iterate.
		 * @return true if hasNext() would return an element
		 */
		@Override
		public boolean hasNext() {
			return nextIndex != -1;
		}
		
		/**
		 * Returns the next key in the iteration.
		 * @return The next key
		 * @throws NoSuchElementException if no more elements exist
		 */
		@Override
		public Object next() {
			if (nextIndex == -1) {
				throw new NoSuchElementException("No more elements");
			}
			String key = table[nextIndex].key;
			currentIndex = nextIndex + 1;
			findNext();
			return key;
		}
		
		/**
		 * Optional operation - not implemented for this use case.
		 * @throws UnsupportedOperationException always
		 */
		@Override
		public void remove() {
			throw new UnsupportedOperationException("Remove not supported by HashTable iterator");
		}
		
		/**
		 * Optional operation - not implemented for this use case.
		 * @throws UnsupportedOperationException always
		 */
		@Override
		public void forEachRemaining(Consumer action) {
			throw new UnsupportedOperationException("forEachRemaining not supported by HashTable iterator");
		}
	}
	/**
	 * Loads this HashTable from a file named "Lookup.dat".
	 */
    public void load() {
        FileReader fileReader;
        BufferedReader bufferedReader = null;
        
        // Open the file for reading
        try {
            File f = new File(System.getProperty("user.home"), "Lookup.dat");
            fileReader = new FileReader(f);
            bufferedReader = new BufferedReader(fileReader);
        }
        catch (FileNotFoundException e) {
            System.err.println("Cannot find input file \"Lookup.dat\"");
        }
        
        // Read the file contents and save in the HashTable
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
        
        // Close the file when we're done
        try {
            bufferedReader.close( );
        }
        catch(IOException e) {
            e.printStackTrace(System.out);
        }
    }

	/**
	 * Saves this HashTable onto a file named "Lookup.dat".
	 */
	public void save() {
        FileOutputStream stream;
        PrintWriter printWriter = null;
        Iterator iterator;
        
        // Open the file for writing
        try {
            File f = new File(System.getProperty("user.home"), "Lookup.dat");
            stream = new FileOutputStream(f);
            printWriter = new PrintWriter(stream);
        }
        catch (Exception e) {
            System.err.println("Cannot use output file \"Lookup.dat\"");
        }
       
        // Write the contents of this HashTable to the file
        iterator = keys();
        while (iterator.hasNext()) {
            String key = (String)iterator.next();
            printWriter.println(key);
            String value = (String)get(key);
            value = removeNewlines(value);
            printWriter.println(value);
            printWriter.println();
        }
       
        // Close the file when we're done
        printWriter.close( );
    }
    
    /**
     * Replaces all line separator characters (which vary from one platform
     * to the next) with spaces.
     * 
     * @param value The input string, possibly containing line separators.
     * @return The input string with line separators replaced by spaces.
     */
    private String removeNewlines(String value) {
        return value.replaceAll("\r|\n", " ");
    }
}
