import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.LinkedList;

/**
 * Your implementation of a ExternalChainingHashMap.
 *
 * @author Dhruv Sharma
 * @version 1.0
 * @userid dsharma97
 * @GTID 903690386
 */
public class ExternalChainingHashMap<K, V> {

    /*
     * The initial capacity of the ExternalChainingHashMap when created with the
     * default constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /*
     * The max load factor of the ExternalChainingHashMap.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final double MAX_LOAD_FACTOR = 0.67;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private ExternalChainingMapEntry<K, V>[] table;
    private int size;

    /**
     * Constructs a new ExternalChainingHashMap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     *
     * Use constructor chaining.
     */
    public ExternalChainingHashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Constructs a new ExternalChainingHashMap.
     *
     * The backing array should have an initial capacity of capacity.
     *
     * You may assume capacity will always be positive.
     *
     * @param capacity the initial capacity of the backing array
     */
    public ExternalChainingHashMap(int capacity) {
        table = (ExternalChainingMapEntry<K, V>[]) new ExternalChainingMapEntry[capacity];
        size = 0;
    }

    /**
     * Adds the given key-value pair to the map. If an entry in the map
     * already has this key, replace the entry's value with the new one
     * passed in.
     *
     * In the case of a collision, use external chaining as your resolution
     * strategy. Add new entries to the front of an existing chain, but don't
     * forget to check the entire chain for duplicate keys first.
     *
     * If you find a duplicate key, then replace the entry's value with the new
     * one passed in. When replacing the old value, replace it at that position
     * in the chain, not by creating a new entry and adding it to the front.
     *
     * Before actually adding any data to the HashMap, you should check to
     * see if the array would violate the max load factor if the data was
     * added. Resize if the load factor is greater than max LF (it is okay
     * if the load factor is equal to max LF). For example, let's say the
     * array is of length 5 and the current size is 3 (LF = 0.6). For this
     * example, assume that no elements are removed in between steps. If
     * another entry is attempted to be added, before doing anything else,
     * you should check whether (3 + 1) / 5 = 0.8 is larger than the max LF.
     * It is, so you would trigger a resize before you even attempt to add
     * the data or figure out if it's a duplicate. Be careful to consider the
     * differences between integer and double division when calculating load
     * factor.
     *
     * When regrowing, resize the length of the backing table to
     * 2 * old length + 1. You must use the resizeBackingTable method to do so.
     *
     * Return null if the key was not already in the map. If it was in the map,
     * return the old value associated with it.
     *
     * @param key   the key to add
     * @param value the value to add
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     * @throws java.lang.IllegalArgumentException if key or value is null
     */
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Enter non-null key and value inputs.");
        }
        double loadFactor = (size + 1.0) / table.length;
        int tableLength = table.length;
        int hashCodeIndex = Math.abs(key.hashCode() % table.length);
        int oldSize = size;

        if (loadFactor > MAX_LOAD_FACTOR) {
            ExternalChainingMapEntry<K, V>[] oldTable = table;
            table = (ExternalChainingMapEntry<K, V>[]) new ExternalChainingMapEntry[tableLength * 2 + 1];
            size = 0;
            for (int i = 0; i < oldTable.length; i++) {
                ExternalChainingMapEntry currentExternalChainingMapEntry = oldTable[i];
                int count = 0;
                while (currentExternalChainingMapEntry != null && count < oldSize) {
                    put((K) currentExternalChainingMapEntry.getKey(), (V) currentExternalChainingMapEntry.getValue());
                    currentExternalChainingMapEntry = currentExternalChainingMapEntry.getNext();
                    count++;
                }
            }
        }
        if (table[hashCodeIndex] == null) {
            table[hashCodeIndex] = new ExternalChainingMapEntry(key, value, null);
            size++;
            return null;
        } else {
            ExternalChainingMapEntry currentExternalChainingMapEntry = table[hashCodeIndex];
            while (currentExternalChainingMapEntry != null) {
                if (key.equals(currentExternalChainingMapEntry.getKey())) {
                    V oldVal = (V) currentExternalChainingMapEntry.getValue();
                    currentExternalChainingMapEntry.setValue(value);
                    return oldVal;
                }
                currentExternalChainingMapEntry = currentExternalChainingMapEntry.getNext();
            }
            table[hashCodeIndex] = new ExternalChainingMapEntry(key, value, table[hashCodeIndex]);
            size++;
            return null;
        }
    }

    /**
     * Removes the entry with a matching key from the map.
     *
     * @param key the key to remove
     * @return the value previously associated with the key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Enter a non-null key input.");
        }
        if (key == null) {
            throw new IllegalArgumentException("Enter a non-null key input.");
        }
        int hashCodeIndex = Math.abs(key.hashCode() % table.length);
        ExternalChainingMapEntry currentExternalChainingMapEntry = table[hashCodeIndex];
        if (currentExternalChainingMapEntry != null) {
            if (key.equals(currentExternalChainingMapEntry.getKey())) {
                V toReturn = (V) currentExternalChainingMapEntry.getValue();
                table[hashCodeIndex] = currentExternalChainingMapEntry.getNext();
                size--;
                return toReturn;
            }
            while (currentExternalChainingMapEntry.getNext() != null) {
                if (key.equals(currentExternalChainingMapEntry.getNext().getKey())) {
                    V toReturn = (V) currentExternalChainingMapEntry.getNext().getValue();
                    currentExternalChainingMapEntry.setNext(currentExternalChainingMapEntry.getNext().getNext());
                    size--;
                    return toReturn;
                }
                currentExternalChainingMapEntry = currentExternalChainingMapEntry.getNext();
            }
        }
        throw new NoSuchElementException("The entered element is not in the HashMap.");
    }

    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for in the map
     * @return the value associated with the given key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Enter a non-null key input.");
        }
        ExternalChainingMapEntry currentExternalChainingMapEntry = table[Math.abs(key.hashCode() % table.length)];
        while (currentExternalChainingMapEntry != null) {
            if (key.equals(currentExternalChainingMapEntry.getKey())) {
                return (V) currentExternalChainingMapEntry.getValue();
            }
            currentExternalChainingMapEntry = currentExternalChainingMapEntry.getNext();
        }
        throw new NoSuchElementException("The entered element is not in the HashMap.");
    }

    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for in the map
     * @return true if the key is contained within the map, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if key is null
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Enter a non-null key input.");
        }
        try {
            get(key);
            return true;
        } catch (NoSuchElementException n) {
            return false;
        }
    }

    /**
     * Returns a Set view of the keys contained in this map.
     *
     * Use java.util.HashSet.
     *
     * @return the set of keys in this map
     */
    public Set<K> keySet() {
        Set<K> returnSet = new HashSet<>();
        for (int i = 0; i < table.length; i++) {
            ExternalChainingMapEntry currentExternalChainingMapEntry = table[i];
            int count = 0;
            while (currentExternalChainingMapEntry != null && count < size) {
                returnSet.add((K) currentExternalChainingMapEntry.getKey());
                currentExternalChainingMapEntry = currentExternalChainingMapEntry.getNext();
                count++;
            }
        }
        return returnSet;
    }

    /**
     * Returns a List view of the values contained in this map.
     *
     * Use java.util.ArrayList or java.util.LinkedList.
     *
     * You should iterate over the table in order of increasing index and add
     * entries to the List in the order in which they are traversed.
     *
     * @return list of values in this map
     */
    public List<V> values() {
        List<V> returnList = new LinkedList<>();
        for (int i = 0; i < table.length; i++) {
            ExternalChainingMapEntry currentExternalChainingMapEntry = table[i];
            int count = 0;
            while (currentExternalChainingMapEntry != null && count < size) {
                returnList.add((V) currentExternalChainingMapEntry.getValue());
                currentExternalChainingMapEntry = currentExternalChainingMapEntry.getNext();
                count++;
            }
        }
        return returnList;
    }

    /**
     * Resize the backing table to length.
     *
     * Disregard the load factor for this method. So, if the passed in length is
     * smaller than the current capacity, and this new length causes the table's
     * load factor to exceed MAX_LOAD_FACTOR, you should still resize the table
     * to the specified length and leave it at that capacity.
     *
     * You should iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed.
     *
     * Since resizing the backing table is working with the non-duplicate
     * data already in the table, you shouldn't explicitly check for
     * duplicates.
     *
     * Hint: You cannot just simply copy the entries over to the new array.
     *
     * @param length new length of the backing table
     * @throws java.lang.IllegalArgumentException if length is less than the
     *                                            number of items in the hash
     *                                            map
     */
    public void resizeBackingTable(int length) {
        if (length < size) {
            throw new IllegalArgumentException("Enter a length that is not less than the total number of elements.");
        }
        ExternalChainingMapEntry<K, V>[] oldTable = table;
        table = (ExternalChainingMapEntry<K, V>[]) new ExternalChainingMapEntry[length];
        size = 0;
        for (int i = 0; i < oldTable.length; i++) {
            ExternalChainingMapEntry currentExternalChainingMapEntry = oldTable[i];
            while (currentExternalChainingMapEntry != null) {
                putWithoutLF((K) currentExternalChainingMapEntry.getKey(), (V) currentExternalChainingMapEntry.
                        getValue());
                currentExternalChainingMapEntry = currentExternalChainingMapEntry.getNext();
            }
        }
    }

    /**
     * This is the put method bypassing Load Factor.
     *
     * @param key   the key to add
     * @param value the value to add
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     */
    private V putWithoutLF(K key, V value) {
        int hashCodeIndex = Math.abs(key.hashCode() % table.length);
        if (table[hashCodeIndex] == null) {
            table[hashCodeIndex] = new ExternalChainingMapEntry<K, V>(key, value, null);
            size++;
            return null;
        } else {
            ExternalChainingMapEntry currentExternalChainingMapEntry = table[hashCodeIndex];
            while (currentExternalChainingMapEntry != null) {
                if (key.equals(currentExternalChainingMapEntry.getKey())) {
                    V oldVal = (V) currentExternalChainingMapEntry.getValue();
                    currentExternalChainingMapEntry.setValue(value);
                    return oldVal;
                }
                currentExternalChainingMapEntry = currentExternalChainingMapEntry.getNext();
            }
            table[hashCodeIndex] = new ExternalChainingMapEntry(key, value, table[hashCodeIndex]);
            size++;
            return null;
        }
    }

    /**
     * Clears the map.
     *
     * Resets the table to a new array of the initial capacity and resets the
     * size.
     */
    public void clear() {
        table = (ExternalChainingMapEntry<K, V>[]) new ExternalChainingMapEntry[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the table of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the table of the map
     */
    public ExternalChainingMapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

    /**
     * Returns the size of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the map
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
