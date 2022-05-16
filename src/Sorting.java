import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.PriorityQueue;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Dhruv Sharma
 * @version 1.0
 * @userid dsharma97
 * @GTID 903690386
 */

public class Sorting {

    /**
     * Implement selection sort.
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n^2)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Enter a non-null array or comparator.");
        }
        if (arr.length == 0) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (comparator.compare(arr[j], arr[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            swap(minIndex, i, arr);
        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Enter a non-null array or comparator.");
        }
        if (arr.length == 0) {
            return;
        }
        int start = 0;
        int end = arr.length - 1;
        while (start < end) {
            int swapped = start;
            for (int i = start; i < end; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    swap(i, i + 1, arr);
                    swapped = i;
                }
            }
            end = swapped;
            for (int i = end; i > start; i--) {
                if (comparator.compare(arr[i], arr[i - 1]) < 0)  {
                    swap(i, i - 1, arr);
                    swapped = i;
                }
            }
            start = swapped;
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Enter a non-null array or comparator.");
        }
        if (arr.length > 1) {
            int midIndex = arr.length / 2;
            int leftSize = arr.length / 2;
            int rightSize = arr.length - leftSize;
            T[] leftArr = (T[]) new Object[leftSize];
            T[] rightArr = (T[]) new Object[rightSize];

            for (int i = 0; i < leftSize; i++) {
                leftArr[i] = arr[i];
            }
            for (int i = leftSize; i < arr.length; i++) {
                rightArr[i - leftSize] = arr[i];
            }

            mergeSort(leftArr, comparator);
            mergeSort(rightArr, comparator);

            int i = 0;
            int j = 0;

            while (i < midIndex && j < arr.length - midIndex) {
                if (comparator.compare(leftArr[i], rightArr[j]) <= 0) {
                    arr[i + j] = leftArr[i];
                    i++;
                } else {
                    arr[i + j] = rightArr[j];
                    j++;
                }
            }
            while (i < midIndex) {
                arr[i + j] = leftArr[i];
                i++;
            }
            while (j < arr.length - midIndex) {
                arr[i + j] = rightArr[j];
                j++;
            }
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("Enter a non-null array, comparator, or random.");
        }
        if (arr.length == 0) {
            return;
        }
        quickSortHelper(arr, comparator, 0, arr.length, rand);
    }

    /**
     * Helper method for QuickSort.
     *
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param start the start index
     * @param end the end index
     * @param rand the Random object used to select pivots
     * @param <T> data type to sort
     */
    private static <T> void quickSortHelper(T[] arr, Comparator<T> comparator, int start, int end,
                                            Random rand) {
        if (arr.length <= 1 || start >= end - 1) {
            return;
        }

        int pivotIndex = rand.nextInt(end - start) + start;
        T pivot = arr[pivotIndex];
        swap(start, pivotIndex, arr);
        int i = start + 1;
        int j = end - 1;

        while (i <= j) {
            while (i <= j && comparator.compare(arr[i], pivot) <= 0) {
                i++;
            }
            while (i <= j && comparator.compare(arr[j], pivot) >= 0) {
                j--;
            }
            if (i <= j) {
                swap(i, j, arr);
                i++;
                j--;
            }
        }
        swap(start, j, arr);
        quickSortHelper(arr, comparator, start, j, rand);
        quickSortHelper(arr, comparator, j + 1, end, rand);
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Enter a non-null array.");
        }
        if (arr.length == 0) {
            return;
        }
        LinkedList<Integer>[] buckets = (LinkedList<Integer>[]) new LinkedList[19];

        int maxNum = arr[0];
        int k = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == Integer.MIN_VALUE) {
                maxNum = arr[i];
                break;
            }
            if (Math.abs(arr[i]) > maxNum) {
                maxNum = Math.abs(arr[i]);
            }
        }
        while (maxNum != 0) {
            maxNum /= 10;
            k++;
        }

        int mod = 10;
        int div = 1;

        for (int i = 0; i < k; i++) {
            for (int num : arr) {
                int bucket = num / div;
                if (buckets[(bucket % mod) + 9] == null) {
                    buckets[(bucket % mod) + 9] = new LinkedList<>();
                }
                buckets[(bucket % mod) + 9].add(num);
            }

            int currIndex = 0;

            for (int j = 0; j < buckets.length; j++) {
                if (buckets[j] != null) {
                    for (int num : buckets[j]) {
                        arr[currIndex] = num;
                        currIndex++;
                    }
                    buckets[j].clear();
                }
            }
            div *= 10;
        }
    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list in sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("Enter non-null data.");
        }
        int[] returnArray = new int[data.size()];
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(data);

        for (int i = 0; i < data.size(); i++) {
            returnArray[i] = minHeap.remove();
        }
        return returnArray;
    }

    /**
     * Swaps two elements in an array
     *
     * @param <T> data type to sort
     * @param index1 first index to swap
     * @param index2 second index to swap
     * @param arr array where indexes will be swaped
     */
    private static <T> void swap(int index1, int index2, T[] arr) {
        T data1 = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = data1;
    }
}
