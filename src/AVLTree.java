import java.util.LinkedList;
import java.util.Queue;
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
        if(!root.isAVL()){
            balanceTree(savedNode);
        }
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
        balanceTree(lowestInvalidNode(root));
    }

    protected Node<K> lowestInvalidNode(Node<K> node){
        if(node == null){
            return null;
        }
        Queue<Node<K>> queue = new LinkedList<>();
        queue.add(node);
        Node<K> lowestInvalidNode = null;
        while(!queue.isEmpty()){
            Node<K> curr = queue.poll();
            if(!curr.isAVL()){
                lowestInvalidNode = curr;
            }
        }
        return lowestInvalidNode;
    }

    protected void balanceTree(Node<K> node){
        if(node == null){
            return;
        }
        node.updateHeight();
        if(node.isAVL()){
            balanceTree(node.parent);
            return;
        }
        Node<K> parentNode = node.parent;
        if(get_height(node.left) <= get_height(node.right)){
            if(get_height(node.right.left) <= get_height(node.right.right)){
                leftRotate(node);
                node.updateHeight();
            }
            else if(get_height(node.right.left) > get_height(node.right.right)){
                rightRotate(node.right);
                node.right.updateHeight();
                leftRotate(node);
                node.updateHeight();
            }
        }
        else{
            if(get_height(node.left.left) < get_height(node.left.right)){
                leftRotate(node.left);
                node.left.updateHeight();
                rightRotate(node);
                node.updateHeight();
            }
            else if(get_height(node.left.left) >= get_height(node.left.right)){
                rightRotate(node);
                node.updateHeight();
            }
        }
        balanceTree(parentNode);
    }

    protected void leftRotate(Node<K> node){
        node.right.parent = node.parent;
        if(node.parent != null){
            if(node == node.parent.right){
                node.parent.right = node.right;
            }
            else{
                node.parent.left = node.right;
            }
        }
        else{
            this.root = node.right;
        }
        node.parent = node.right;
        node.right = node.parent.left;
        node.parent.left = node;
    }

    protected void rightRotate(Node<K> node){
        node.left.parent = node.parent;
        if(node.parent != null){
            if(node == node.parent.left){
                node.parent.left = node.left;
            }
            else{
                node.parent.right = node.left;
            }
        }
        else{
            this.root = node.left;
        }
        node.parent = node.left;
        node.left = node.parent.right;
        node.parent.right = node;

    }

}
