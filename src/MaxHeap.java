import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MaxHeap.
 *
 * @author Dhruv Sharma
 * @version 1.0
 * @userid dsharma97
 * @GTID 903690386
 */
public class MaxHeap<T extends Comparable<? super T>> {

    /*
     * The initial capacity of the MaxHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MaxHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MaxHeap() {
        backingArray = (T[]) (new Comparable[INITIAL_CAPACITY]);
        size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * Consider how to most efficiently determine if the list contains null data.
     * 
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MaxHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The inputted data ArrayList cannot be null.");
        }
        backingArray = (T[]) (new Comparable[2 * data.size() + 1]);
        size = data.size();
        for (int i = 0; i < size; i++) {
            if (data.get(i) == null) {
                throw new IllegalArgumentException("Data in the inputted ArrayList cannot be null.");
            }
            backingArray[i + 1] = data.get(i);
        }
        for (int i = size / 2; i > 0; i--) {
            downHeap(i);
        }
    }

    /**
     * Adds the data to the heap.
     *
     * If sufficient space is not available in the backing array (the backing
     * array is full except for index 0), resize it to double the current
     * length.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Inputted data should not be null.");
        }
        if (size + 1 >= backingArray.length) {
            T[] newBackingArray = (T[]) (new Comparable[2 * backingArray.length]);
            for (int i = 0; i < size + 1; i++) {
                newBackingArray[i] = backingArray[i];
            }
            backingArray = newBackingArray;
        }
        backingArray[size + 1] = data;
        upHeap(size + 1);
        size++;
    }

    /**
     * Removes and returns the root of the heap.
     *
     * Do not shrink the backing array.
     *
     * Replace any unused spots in the array with null.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("The heap is empty, so the root cannot be removed.");
        }
        T root = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;
        downHeap(1);
        return root;
    }

    /**
     * Returns the maximum element in the heap.
     *
     * @return the maximum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMax() {
        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) (new Comparable[INITIAL_CAPACITY]);
        size = 0;
    }

    /**
     * Swaps any two elements in the heap.
     *
     * @param index1 index of first element
     * @param index2 index of second element
     */
    private void swap(int index1, int index2) {
        T tempHolder = backingArray[index1];
        backingArray[index1] = backingArray[index2];
        backingArray[index2] = tempHolder;
    }

    /**
     * Recursively makes sure that the elements of the MaxHeap are in correct order.
     *
     * @param index index of data on which downHeap is applied
     */
    private void downHeap(int index) {
        if (index >= size || index < 1) {
            return;
        }
        if (backingArray[(index * 2) + 1] == null) {
            if (backingArray[index * 2] == null) {
                return;
            }
            if (backingArray[(index * 2)].compareTo(backingArray[index]) > 0) {
                swap(index, index * 2);
            }
        } else {
            int compareIndex;
            if (backingArray[index * 2].compareTo(backingArray[(index * 2) + 1]) > 0) {
                compareIndex = index * 2;
            } else {
                compareIndex = (index * 2) + 1;
            }
            if (backingArray[compareIndex].compareTo(backingArray[index]) > 0) {
                swap(index, compareIndex);
                downHeap(compareIndex);
            }
        }
    }

    /**
     * Recursively makes sure that the elements of the MaxHeap are in correct order after add.
     *
     * @param index index of data on which upHeap is applied
     */
    private void upHeap(int index) {
        if (index / 2 <= 0) {
            return;
        }
        if (backingArray[(index / 2)].compareTo(backingArray[index]) < 0) {
            swap(index, index / 2);
            upHeap(index / 2);
        }
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
