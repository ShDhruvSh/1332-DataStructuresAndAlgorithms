import java.util.NoSuchElementException;

/**
 * My implementation of a CircularSinglyLinkedList without a tail pointer.
 *
 * @author Dhruv Sharma
 * @version 1.0
 * @userid dsharma97
 * @GTID 903690386
 */
public class CircularSinglyLinkedList<T> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private CircularSinglyLinkedListNode<T> head;
    private int size;

    /*
     * Do not add a constructor.
     */

    /**
     * Adds the data to the specified index.
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new data
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (data != null && index == 0) {
            addToFront(data);
        } else if (data != null && index > 0 && index == size) {
            addToBack(data);
        } else if (data != null && index > 0 && index < size) {
            CircularSinglyLinkedListNode<T> currNode = head;
            for (int i = 0; i < index - 1; i++) {
                currNode = currNode.getNext();
            }
            CircularSinglyLinkedListNode<T> toBeAdded = new CircularSinglyLinkedListNode<>(data);
            toBeAdded.setNext(currNode.getNext());
            currNode.setNext(toBeAdded);
            size++;
        } else if (data != null) {
            throw new IndexOutOfBoundsException("Enter an index >= 0 and <= size.");
        } else {
            throw new IllegalArgumentException("The entered data is null. Enter non-null data.");
        }
    }

    /**
     * Adds the data to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data != null && size == 0) {
            head = new CircularSinglyLinkedListNode<>(data);
            head.setNext(head);
            size++;
        } else if (data != null && size > 0) {
            CircularSinglyLinkedListNode<T> oldHead = new CircularSinglyLinkedListNode<>(head.getData());
            oldHead.setNext(head.getNext());
            head.setNext(oldHead);
            head.setData(data);
            size++;
        } else {
            throw new IllegalArgumentException("The entered data is null. Enter non-null data.");
        }
    }

    /**
     * Adds the data to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data != null && size == 0) {
            head = new CircularSinglyLinkedListNode<>(data);
            head.setNext(head);
            size++;
        } else if (data != null && size > 0) {
            CircularSinglyLinkedListNode<T> newHead = new CircularSinglyLinkedListNode<>(head.getData());
            newHead.setNext(head.getNext());
            head.setNext(newHead);
            head.setData(data);
            head = newHead;
            size++;
        } else {
            throw new IllegalArgumentException("The entered data is null. Enter non-null data.");
        }
    }

    /**
     * Removes and returns the data at the specified index.
     *
     * Must be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (size > 0 && index >= 0 && index < size) {
            if (index == 0) {
                return removeFromFront();
            } else {
                CircularSinglyLinkedListNode<T> currNode = head;
                for (int i = 0; i < index - 1; i++) {
                    currNode = currNode.getNext();
                }
                T removedData = currNode.getNext().getData();
                currNode.setNext(currNode.getNext().getNext());
                size--;
                return removedData;
            }
        } else {
            throw new IndexOutOfBoundsException("Enter an index >= 0 and < size.");
        }
    }

    /**
     * Removes and returns the first data of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (size == 1) {
            T removedData = head.getData();
            head = null;
            size--;
            return removedData;
        } else if (size > 1) {
            T removedData = head.getData();
            head.setData(head.getNext().getData());
            head.setNext(head.getNext().getNext());
            size--;
            return removedData;
        } else {
            throw new NoSuchElementException("The CircularSinglyLinkedList is empty. "
                    + "Add some elements before calling this method.");
        }
    }

    /**
     * Removes and returns the last data of the list.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (size > 0) {
            return removeAtIndex(size - 1);
        } else {
            throw new NoSuchElementException("The CircularSinglyLinkedList is empty. "
                    + "Add some elements before calling this method.");
        }
    }

    /**
     * Returns the data at the specified index.
     *
     * Should be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (size > 0 && index >= 0 && index < size) {
            if (index == 0) {
                return head.getData();
            } else {
                CircularSinglyLinkedListNode<T> currNode = head;
                for (int i = 0; i < index; i++) {
                    currNode = currNode.getNext();
                }
                return currNode.getData();
            }
        } else {
            throw new IndexOutOfBoundsException("Enter an index >= 0 and < size.");
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(n).
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The entered data is null. Enter non-null data.");
        } else {
            CircularSinglyLinkedListNode<T> currNode = head;
            CircularSinglyLinkedListNode<T> savedNode = null;

            for (int i = 0; i < size - 1; i++) {
                if (currNode.getNext().getData().equals(data)) {
                    savedNode = currNode;
                }
                currNode = currNode.getNext();
            }
            if (savedNode == null) {
                if (head.getData().equals(data)) {
                    return removeFromFront();
                }
            }
            if (savedNode != null) {
                T removedData = savedNode.getNext().getData();
                savedNode.setNext(savedNode.getNext().getNext());
                size--;
                return removedData;
            } else {
                throw new NoSuchElementException("The entered data does not exist in the CircularSinglyLinkedList.");
            }
        }
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return the array of length size holding all of the data (not the
     * nodes) in the list in the same order
     */
    public T[] toArray() {
        T[] arr = (T[]) new Object[size];
        CircularSinglyLinkedListNode<T> currNode = head;
        for (int i = 0; i < size; i++) {
            arr[i] = currNode.getData();
            if (i != size - 1) {
                currNode = currNode.getNext();
            }
        }
        return arr;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public CircularSinglyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
