import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Collection;
import java.util.List;



/**
 * Your implementation of a BST.
 *
 * @author Dhruv Sharma
 * @version 1.0
 * @userid dsharma97
 * @GTID 903690386
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null || data.contains(null)) {
            throw new IllegalArgumentException("Input a non-null value for data and its elements.");
        }
        for (T dataInput : data) {
            add(dataInput);
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Input a non-null value for data.");
        }
        if (size == 0) {
            root = new BSTNode<>(data);
            size++;
        } else {
            addHelper2(data, root);
        }
    }

    /**
     * Helps add the data to the tree recursively.
     *
     * @param data the data to add
     * @param currNode the current Node
     */
    private void addHelper1(T data, BSTNode<T> currNode) {
        if (data.compareTo(currNode.getData()) < 0) {
            if (currNode.getLeft() == null) {
                currNode.setLeft(new BSTNode<>(data));
                size++;
            } else {
                addHelper1(data, currNode.getLeft());
            }
        } else if (data.compareTo(currNode.getData()) > 0) {
            if (currNode.getRight() == null) {
                currNode.setRight(new BSTNode<>(data));
                size++;
            } else {
                addHelper1(data, currNode.getRight());
            }
        }
    }

    /**
     * Helps add the data to the tree recursively using pointer reinforcement.
     *
     * @param data the data to add
     * @param currNode the current Node
     * @return the pointer reinforcing BSTNode
     */
    private BSTNode<T> addHelper2(T data, BSTNode<T> currNode) {
        if (currNode == null) {
            size++;
            return new BSTNode<>(data);
        }
        if (data.compareTo(currNode.getData()) < 0) {
            currNode.setLeft(addHelper2(data, currNode.getLeft()));
        } else if (data.compareTo(currNode.getData()) > 0) {
            currNode.setRight(addHelper2(data, currNode.getRight()));
        }
        return currNode;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Input a non-null value for data.");
        }
        if (size == 0) {
            throw new NoSuchElementException("This data does not exist in the BST.");
        }
        BSTNode<T> toRemove = new BSTNode<>(null);
        root = removeHelper(data, root, toRemove);
        size--;
        return toRemove.getData();
    }

    /**
     * Helps remove the data to the tree recursively.
     *
     * @param data the data to add
     * @param currNode the current Node
     * @param toRemove the Node whose data will be returned by the remove method
     */
    private BSTNode<T> removeHelper(T data, BSTNode<T> currNode, BSTNode<T> toRemove) {
        if (data.compareTo(currNode.getData()) < 0) {
            if (currNode.getLeft() == null) {
                throw new NoSuchElementException("This data does not exist in the BST.");
            } else {
                currNode.setLeft(removeHelper(data, currNode.getLeft(), toRemove));
            }
        } else if (data.compareTo(currNode.getData()) > 0) {
            if (currNode.getRight() == null) {
                throw new NoSuchElementException("This data does not exist in the BST.");
            } else {
                currNode.setRight(removeHelper(data, currNode.getRight(), toRemove));
            }
        } else {
            toRemove.setData(currNode.getData());
            if (currNode.getLeft() == null && currNode.getRight() == null) {
                return null;
            } else if (currNode.getLeft() == null) {
                return currNode.getRight();
            }  else if (currNode.getRight() == null) {
                return currNode.getLeft();
            } else {
                if (currNode.getRight().getLeft() == null) {
                    currNode.getRight().setLeft(currNode.getLeft());
                    return currNode.getRight();
                } else {
                    BSTNode<T> successor = successorFinder(currNode.getRight(), currNode);
                    successor.setLeft(currNode.getLeft());
                    successor.setRight(currNode.getRight());
                    return successor;
                }
            }
        }
        return currNode;
    }

    /**
     * Removes and returns successor node.
     *
     * @param currNode the Node at the right of the Node whose successor is to be returned
     * @param previousNode the Node whose successor is to be returned
     */
    private BSTNode<T> successorFinder(BSTNode<T> currNode, BSTNode<T> previousNode) {
        if (currNode.getLeft() == null) {
            if (currNode.getRight() != null) {
                previousNode.setLeft(currNode.getRight());
            } else {
                previousNode.setLeft(null);
            }
            return currNode;
        } else {
            return successorFinder(currNode.getLeft(), currNode);
        }
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Input a non-null value for data.");
        }
        return getHelper(data, root);
    }

    /**
     * Helps get the data in the tree recursively.
     *
     * @param data the data to get
     * @param currNode the current Node
     */
    private T getHelper(T data, BSTNode<T> currNode) {
        if (currNode.getData().equals(data)) {
            return currNode.getData();
        } else if (data.compareTo(currNode.getData()) < 0) {
            if (currNode.getLeft() == null) {
                throw new NoSuchElementException("This data does not exist in the BST.");
            } else {
                return getHelper(data, currNode.getLeft());
            }
        } else if (data.compareTo(currNode.getData()) > 0) {
            if (currNode.getRight() == null) {
                throw new NoSuchElementException("This data does not exist in the BST.");
            } else {
                return getHelper(data, currNode.getRight());
            }
        } else {
            throw new NoSuchElementException("This data does not exist in the BST.");
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Input a non-null value for data.");
        }
        try {
            return get(data).equals(data);
        } catch (NoSuchElementException n) {
            return false;
        }
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the pre-order traversal of the tree
     */
    public List<T> preorder() {
        List<T> traversedElements = new ArrayList<>();
        preorderHelper(root, traversedElements);
        return traversedElements;
    }

    /**
     * Helps do a preorder traversal of the BST.
     *
     * @param currNode the current Node
     * @param traversedElements a List of the elements that are in the BST in pre-order
     */
    private void preorderHelper(BSTNode<T> currNode, List<T> traversedElements) {
        if (currNode != null) {
            traversedElements.add(currNode.getData());
            preorderHelper(currNode.getLeft(), traversedElements);
            preorderHelper(currNode.getRight(), traversedElements);
        }
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the in-order traversal of the tree
     */
    public List<T> inorder() {
        List<T> traversedElements = new ArrayList<>();
        inorderHelper(root, traversedElements);
        return traversedElements;
    }

    /**
     * Helps do an in-order traversal of the BST.
     *
     * @param currNode the current Node
     * @param traversedElements a List of the elements that are in the BST in in-order
     */
    private void inorderHelper(BSTNode<T> currNode, List<T> traversedElements) {
        if (currNode != null) {
            inorderHelper(currNode.getLeft(), traversedElements);
            traversedElements.add(currNode.getData());
            inorderHelper(currNode.getRight(), traversedElements);
        }
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the post-order traversal of the tree
     */
    public List<T> postorder() {
        List<T> traversedElements = new ArrayList<>();
        postorderHelper(root, traversedElements);
        return traversedElements;
    }

    /**
     * Helps do a post-order traversal of the BST.
     *
     * @param currNode the current Node
     * @param traversedElements a List of the elements that are in the BST in post-order
     */
    private void postorderHelper(BSTNode<T> currNode, List<T> traversedElements) {
        if (currNode != null) {
            postorderHelper(currNode.getLeft(), traversedElements);
            postorderHelper(currNode.getRight(), traversedElements);
            traversedElements.add(currNode.getData());
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level-order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode<T>> traverser = new LinkedList<>();
        List<T> traversedElements = new ArrayList<>();
        if (size == 0) {
            return traversedElements;
        }
        traverser.add(root);
        while (!traverser.isEmpty()) {
            if (traverser.peek().getLeft() != null) {
                traverser.add(traverser.peek().getLeft());
            }
            if (traverser.peek().getRight() != null) {
                traverser.add(traverser.peek().getRight());
            }
            traversedElements.add(traverser.remove().getData());
        }
        return traversedElements;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightHelper(root);
    }

    /**
     * Helps find the height of the root of the BST recursively.
     *
     * @param currNode the current Node
     */
    private int heightHelper(BSTNode<T> currNode) {
        if (currNode == null) {
            return -1;
        } else {
            return Math.max(heightHelper(currNode.getLeft()) + 1, heightHelper(currNode.getRight()) + 1);
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds the path between two elements in the tree, specifically the path
     * from data1 to data2, inclusive of both.
     *
     * To do this, you must first find the deepest common ancestor of both data
     * and add it to the list. Then traverse to data1 while adding its ancestors
     * to the front of the list. Finally, traverse to data2 while adding its
     * ancestors to the back of the list. Please note that there is no
     * relationship between the data parameters in that they may not belong
     * to the same branch. You will most likely have to split off and
     * traverse the tree for each piece of data.
     * *
     * You may only use 1 list instance to complete this method. Think about
     * what type of list to use since you will have to add to the front and
     * back of the list.
     *
     * This method only need to traverse to the deepest common ancestor once.
     * From that node, go to each data in one traversal each. Failure to do
     * so will result in a penalty.
     *
     * If both data1 and data2 are equal and in the tree, the list should be
     * of size 1 and contain the element from the tree equal to data1 and data2.
     *
     * Ex:
     * Given the following BST composed of Integers
     *              50
     *          /        \
     *        25         75
     *      /    \
     *     12    37
     *    /  \    \
     *   11   15   40
     *  /
     * 10
     * findPathBetween(10, 40) should return the list [10, 11, 12, 25, 37, 40]
     * findPathBetween(50, 37) should return the list [50, 25, 37]
     * findPathBetween(75, 75) should return the list [75]
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data1 the data to start the path from
     * @param data2 the data to end the path on
     * @return the unique path between the two elements
     * @throws java.lang.IllegalArgumentException if either data1 or data2 is
     *                                            null
     * @throws java.util.NoSuchElementException   if data1 or data2 is not in
     *                                            the tree
     */
    public List<T> findPathBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("Input a non-null value for data1 and data2.");
        }
        List<T> path = new LinkedList<>();
        if (data1.equals(data2)) {
            path.add(data1);
            return path;
        }
        T dataLower;
        T dataHigher;
        if (data1.compareTo(data2) > 0) {
            dataHigher = data1;
            dataLower = data2;
        } else {
            dataLower = data1;
            dataHigher = data2;
        }
        BSTNode<T> deepestCommonAncestor = findDCA(dataLower, dataHigher, root);
        addFrontPath(data1, deepestCommonAncestor, path);
        if (!deepestCommonAncestor.getData().equals(data2)) {
            if (data1.compareTo(data2) > 0) {
                addBackPath(data2, deepestCommonAncestor.getLeft(), path);
            } else if ((data1.compareTo(data2) < 0)) {
                addBackPath(data2, deepestCommonAncestor.getRight(), path);
            }
        }
        if (!((LinkedList<T>) path).getFirst().equals(data1)) {
            ((LinkedList<T>) path).addFirst(data1);
        }
        if (!((LinkedList<T>) path).getLast().equals(data2)) {
            path.add(data2);
        }
        return path;
    }

    /**
     * Helps find the Deepest Common Ancestor.
     *
     * @param dataL the lower bound
     * @param dataH the upper bound
     * @param currNode the current Node
     */
    private BSTNode<T> findDCA(T dataL, T dataH, BSTNode<T> currNode) {
        if ((currNode.getData().compareTo(dataL) >= 0) && (currNode.getData().compareTo(dataH) <= 0)) {
            return currNode;
        } else {
            if ((currNode.getData().compareTo(dataL) > 0) && (currNode.getLeft() != null)) {
                return findDCA(dataL, dataH, currNode.getLeft());
            } else if ((currNode.getData().compareTo(dataH) < 0) && (currNode.getRight() != null)) {
                return findDCA(dataL, dataH, currNode.getRight());
            } else {
                throw new NoSuchElementException("Data1 or data2 does not exist in the BST.");
            }
        }
    }

    /**
     * Helps find the path from the first number to the DCA, and adds to the front of the List.
     *
     * @param data the first bound
     * @param currNode the current Node
     * @param path the List of the path between data1 and data2
     */
    private void addFrontPath(T data, BSTNode<T> currNode, List<T> path) {
        if (currNode.getData().equals(data)) {
            return;
        } else {
            if (currNode.getData().compareTo(data) < 0) {
                ((LinkedList<T>) path).addFirst(currNode.getData());
                if (currNode.getRight() != null) {
                    addFrontPath(data, currNode.getRight(), path);
                } else {
                    throw new NoSuchElementException("Data1 or data2 does not exist in the BST.");
                }
            } else {
                ((LinkedList<T>) path).addFirst(currNode.getData());
                if (currNode.getLeft() != null) {
                    addFrontPath(data, currNode.getLeft(), path);
                } else {
                    throw new NoSuchElementException("Data1 or data2 does not exist in the BST.");
                }
            }
        }
    }

    /**
     * Helps find the path from the DCA to the second number, and adds to the back of the List.
     *  @param data the second bound
     * @param currNode the current Node
     * @param path the List of the path between data1 and data2
     */
    private void addBackPath(T data, BSTNode<T> currNode, List<T> path) {
        if (currNode.getData().equals(data)) {
            return;
        } else {
            if (currNode.getData().compareTo(data) < 0) {
                path.add(currNode.getData());
                if (currNode.getRight() != null) {
                    addBackPath(data, currNode.getRight(), path);
                } else {
                    throw new NoSuchElementException("Data1 or data2 does not exist in the BST.");
                }
            } else {
                path.add(currNode.getData());
                if (currNode.getLeft() != null) {
                    addBackPath(data, currNode.getLeft(), path);
                } else {
                    throw new NoSuchElementException("Data1 or data2 does not exist in the BST.");
                }
            }
        }
    }
    
    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
