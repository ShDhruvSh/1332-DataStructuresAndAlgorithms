import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Dhruv Sharma
 * @userid dsharma97
 * @GTID 903690386
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it appears in the Collection.
     *
     * @throws IllegalArgumentException if data or any element in data is null
     * @param data the data to add to the tree
     */
    public AVL(Collection<T> data) {
        if (data == null || data.contains(null)) {
            throw new IllegalArgumentException("Input a non-null value for data and its elements.");
        }
        for (T dataInput : data) {
            add(dataInput);
        }
    }

    /**
     * Adds the data to the AVL. Start by adding it as a leaf like in a regular
     * BST and then rotate the tree as needed.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors going up the tree,
     * rebalancing if necessary.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Input a non-null value for data.");
        }
        if (size == 0) {
            root = new AVLNode<>(data);
            size++;
        } else {
            root = addHelper(data, root);
        }
    }

    /**
     * Helps add the data to the tree recursively using pointer reinforcement.
     *
     * @param data the data to add
     * @param currNode the current Node
     * @return the pointer reinforcing AVLNode
     */
    private AVLNode<T> addHelper(T data, AVLNode<T> currNode) {
        if (currNode == null) {
            size++;
            return new AVLNode<>(data);
        }
        if (data.compareTo(currNode.getData()) < 0) {
            currNode.setLeft(addHelper(data, currNode.getLeft()));
        } else if (data.compareTo(currNode.getData()) > 0) {
            currNode.setRight(addHelper(data, currNode.getRight()));
        } else {
            return currNode;
        }
        recalculate(currNode);
        return autoRotate(currNode);
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the successor to replace the data,
     * not the predecessor. As a reminder, rotations can occur after removing
     * the successor node.
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Input a non-null value for data.");
        }
        if (size == 0) {
            throw new NoSuchElementException("This data does not exist in the AVL.");
        }
        AVLNode<T> toRemove = new AVLNode<>(null);
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
     * @return the pointer reinforcing AVLNode
     */
    private AVLNode<T> removeHelper(T data, AVLNode<T> currNode, AVLNode<T> toRemove) {
        if (data.compareTo(currNode.getData()) < 0) {
            if (currNode.getLeft() == null) {
                throw new NoSuchElementException("This data does not exist in the AVL.");
            } else {
                currNode.setLeft(removeHelper(data, currNode.getLeft(), toRemove));
            }
        } else if (data.compareTo(currNode.getData()) > 0) {
            if (currNode.getRight() == null) {
                throw new NoSuchElementException("This data does not exist in the AVL.");
            } else {
                currNode.setRight(removeHelper(data, currNode.getRight(), toRemove));
            }
        } else {
            toRemove.setData(currNode.getData());
            if (currNode.getLeft() == null && currNode.getRight() == null) {
                return null;
            } else if (currNode.getLeft() == null) {
                return currNode.getRight();
            } else if (currNode.getRight() == null) {
                return currNode.getLeft();
            } else {
                AVLNode<T> successorData = new AVLNode<>(null);
                successorFinder(successorData, currNode);
                currNode.setData(successorData.getData());
                recalculate(currNode);
                currNode = autoRotate(currNode);
                return currNode;
            }
        }
        recalculate(currNode);
        return autoRotate(currNode);
    }

    /**
     * Removes and returns successor node.
     *
     * @param currNode the AVLNode where the data of the successor is stored
     * @param previousNode the AVLNode whose successor is to be removed
     * @return the successor's right AVLNode
     */
    private AVLNode<T> successorFinder(AVLNode<T> currNode, AVLNode<T> previousNode) {
        if (previousNode.getLeft() == null) {
            currNode.setData(previousNode.getData());
            return previousNode.getRight();
        } else {
            previousNode.setLeft(successorFinder(currNode, previousNode.getLeft()));
        }
        recalculate(previousNode);
        return autoRotate(previousNode);
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
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
     * @return data of AVLNode called
     */
    private T getHelper(T data, AVLNode<T> currNode) {
        if (data == null) {
            throw new IllegalArgumentException("Input a non-null value for data.");
        }
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
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
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
     * The predecessor is the largest node that is smaller than the current data.
     *
     * This method should retrieve (but not remove) the predecessor of the data
     * passed in. There are 2 cases to consider:
     * 1: The left subtree is non-empty. In this case, the predecessor is the
     * rightmost node of the left subtree.
     * 2: The left subtree is empty. In this case, the predecessor is the lowest
     * ancestor of the node containing data whose right child is also
     * an ancestor of data.
     *
     * This should NOT be used in the remove method.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *     76
     *   /    \
     * 34      90
     *  \    /
     *  40  81
     * predecessor(76) should return 40
     * predecessor(81) should return 76
     *
     * @param data the data to find the predecessor of
     * @return the predecessor of data. If there is no smaller data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T predecessor(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Input a non-null value for data.");
        }
        AVLNode<T> ancestorPredecessor = new AVLNode<>(null);
        AVLNode<T> nodeWithData = getNode(data, ancestorPredecessor, root);
        if (nodeWithData.getLeft() == null) {
            return ancestorPredecessor.getData();
        } else {
            return getPredecessor(nodeWithData.getLeft()).getData();
        }
    }

    /**
     * Helps get the AVLNode corresponding to the data in the tree recursively.
     *
     * @param data the data to get
     * @param ancestorPredecessor the AVLNode where the data of the ancestor predecessor will be stored
     * @param currNode the current Node
     * @return the AVLNode with the corresponding data
     */
    private AVLNode<T> getNode(T data, AVLNode<T> ancestorPredecessor, AVLNode<T> currNode) {
        if (data == null) {
            throw new IllegalArgumentException("Input a non-null value for data.");
        }
        if (currNode.getData().equals(data)) {
            return currNode;
        } else if (data.compareTo(currNode.getData()) < 0) {
            if (currNode.getLeft() == null) {
                throw new NoSuchElementException("This data does not exist in the BST.");
            } else {
                return getNode(data, ancestorPredecessor, currNode.getLeft());
            }
        } else if (data.compareTo(currNode.getData()) > 0) {
            if (currNode.getRight() == null) {
                throw new NoSuchElementException("This data does not exist in the BST.");
            } else {
                ancestorPredecessor.setData(currNode.getData());
                return getNode(data, ancestorPredecessor, currNode.getRight());
            }
        } else {
            throw new NoSuchElementException("This data does not exist in the BST.");
        }
    }

    /**
     * Helps get the predecessor of the entered AVLNode.
     *
     * @param currNode the current Node
     * @return the AVLNode with predecessor
     */
    private AVLNode<T> getPredecessor(AVLNode currNode) {
        if (currNode.getRight() == null) {
            return currNode;
        } else {
            return getPredecessor(currNode.getRight());
        }
    }

    /**
     * Finds and retrieves the k-smallest elements from the AVL in sorted order,
     * least to greatest.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *              50
     *            /    \
     *         25      75
     *        /  \     / \
     *      13   37  70  80
     *    /  \    \      \
     *   12  15    40    85
     *  /
     * 10
     * kSmallest(0) should return the list []
     * kSmallest(5) should return the list [10, 12, 13, 15, 25].
     * kSmallest(3) should return the list [10, 12, 13].
     *
     * @param k the number of smallest elements to return
     * @return sorted list consisting of the k smallest elements
     * @throws java.lang.IllegalArgumentException if k < 0 or k > n, the number
     *                                            of data in the AVL
     */
    public List<T> kSmallest(int k) {
        if (k < 0 || k > size) {
            throw new IllegalArgumentException("Input a k => 0 and <= n");
        }
        List<T> elements = new ArrayList<>();
        kSmallestHelper(elements, k, root);
        return elements;
    }

    /**
     * Helps create a List of elements
     *
     * @param elements the List of the smallest elements
     * @param k counter for how many elements left
     * @param currNode current AVLNode in the traversal
     */
    private void kSmallestHelper(List<T> elements, int k, AVLNode<T> currNode) {
        if (currNode != null && elements.size() < k) {
            kSmallestHelper(elements, k, currNode.getLeft());
            if (elements.size() < k) {
                elements.add(currNode.getData());
            }
            kSmallestHelper(elements, k, currNode.getRight());
        }
    }

    /**
     * Clears the tree.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * Since this is an AVL, this method does not need to traverse the tree
     * and should be O(1)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        } else {
            return root.getHeight();
        }
    }

    /**
     * Right rotates the entered node.
     *
     * @param rotateNode the AVLNode to be rotated
     * @return AVLNode that was rotated about
     */
    private AVLNode<T> rightRotation(AVLNode<T> rotateNode) {
        AVLNode<T> left = rotateNode.getLeft();
        AVLNode<T> rightOfLeft = rotateNode.getLeft().getRight();
        left.setRight(rotateNode);
        rotateNode.setLeft(rightOfLeft);
        recalculate(rotateNode);
        recalculate(left);
        return left;
    }

    /**
     * Left rotates the entered node.
     *
     * @param rotateNode the AVLNode to be rotated
     * @return AVLNode that was rotated about
     */
    private AVLNode<T> leftRotation(AVLNode<T> rotateNode) {
        AVLNode<T> right = rotateNode.getRight();
        AVLNode<T> leftOfRight = rotateNode.getRight().getLeft();
        right.setLeft(rotateNode);
        rotateNode.setRight(leftOfRight);
        recalculate(rotateNode);
        recalculate(right);
        return right;
    }

    /**
     * Recalculates the height and balance factors of the entered AVLNode.
     *
     * @param currNode the AVLNode to be recalculated
     */
    private void recalculate(AVLNode<T> currNode) {
        int height;
        int balanceFactor = 0;
        if (currNode.getLeft() == null) {
            balanceFactor += -1;
            height = -1;
        } else {
            balanceFactor += currNode.getLeft().getHeight();
            height = currNode.getLeft().getHeight();
        }
        if (currNode.getRight() == null) {
            balanceFactor -= -1;
        } else {
            balanceFactor -= currNode.getRight().getHeight();
            if (currNode.getRight().getHeight() > height) {
                height = currNode.getRight().getHeight();
            }
        }
        height++;
        currNode.setHeight(height);
        currNode.setBalanceFactor(balanceFactor);
    }

    /**
     * Automatically rotates the entered AVLNode if needed.
     *
     * @param currNode the AVLNode to be checked for rotation
     * @return the AVLNode that is the root of the rotation subtree
     */
    private AVLNode<T> autoRotate(AVLNode<T> currNode) {
        if (currNode.getBalanceFactor() > 1) {
            if (currNode.getLeft().getBalanceFactor() < 0) {
                currNode.setLeft(leftRotation(currNode.getLeft()));
            }
            currNode = rightRotation(currNode);
        } else if (currNode.getBalanceFactor() < -1) {
            if (currNode.getRight().getBalanceFactor() > 0) {
                currNode.setRight(rightRotation(currNode.getRight()));
            }
            currNode = leftRotation(currNode);
        }
        return currNode;
    }

    /**
     * Returns the size of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the AVL tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * Returns the root of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the AVL tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}