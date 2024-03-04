import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

/**
 * TODO: This is your second major task.
 * <p>
 * This class implements a height-balanced binary search tree,
 * using the AVL algorithm. Beyond the constructor, only the insert()
 * and remove() methods need to be implemented. All other methods are unchanged.
 */

public class AVLTree<K> extends BinarySearchTree<K> {

    /**
     * Creates an empty AVL tree as a BST organized according to the
     * lessThan predicate.
     */
    public AVLTree(BiPredicate<K, K> lessThan) {
        super(lessThan);
    }

    public boolean isAVL() {
        if (root == null)
            return true;
        else
            return root.isAVL();
    }

    /**
     * TODO
     * Inserts the given key into this AVL tree such that the ordering
     * property for a BST and the balancing property for an AVL tree are
     * maintained.
     */
    public Node insert(K key) {
        Node<K> savedNode = super.insert(key);
        balanceTree(savedNode);
        return savedNode;
    }

    /**
     * TODO
     * <p>
     * Removes the key from this BST. If the key is not in the tree,
     * nothing happens.
     */
    public void remove(K key) {
        super.remove(key);
    }

    protected void balanceTree(Node<K> node){
        if(node == null){
            return;
        }
        if(Math.abs(node.left.height - node.right.height) <= 1){
            balanceTree(node.parent);
            return;
        }
        Node<K> parentNode = node.parent;
        if(node.left.height <= node.right.height){
            if(node.right.left.height <= node.right.right.height){
                leftRotate(node);
            }
            if(node.right.left.height > node.right.right.height){
                rightRotate(node.right);
                leftRotate(node);
            }
        }
        else{
            if(node.left.left.height < node.left.right.height){
                leftRotate(node.left);
                rightRotate(node);
            }
            if(node.left.left.height >= node.left.right.height){
                rightRotate(node);
            }
        }
        balanceTree(parentNode);
    }

    protected void leftRotate(Node<K> node){
        node.right.parent = node.parent;
        node.parent = node.right;
        node.right = node.parent.left;
        node.parent.left = node;
    }

    protected void rightRotate(Node<K> node){
        node.left.parent = node.parent;
        node.parent = node.left;
        node.left = node.parent.right;
        node.parent.right = node;
    }

}
