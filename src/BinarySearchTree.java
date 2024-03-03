import java.util.List;
import java.util.function.BiPredicate;

/**
 * TODO: This is your first major task.
 * <p>
 * This class implements a generic unbalanced binary search tree (BST).
 */

public class BinarySearchTree<K> implements OrderedSet<K> {

    /**
     * A Node<K> is a Location (defined in OrderedSet.java), which
     * means that it can be the return value of a search on the tree.
     */

    static class Node<K> implements Location<K> {

        protected K data;
        protected Node<K> left, right;
        protected Node<K> parent;
        protected int height;

        /**
         * Constructs a leaf Node<K> with the given key.
         */
        public Node(K key) {
            this(key, null, null);
        }

        /**
         * TODO
         * <p>
         * Constructs a new Node<K> with the given values for fields.
         */
        public Node(K data, Node<K> left, Node<K> right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        /*
         * Provide the get() method required by the Location interface.
         */
        @Override
        public K get() {
            return data;
        }

        /**
         * Return true iff this Node<K> is a leaf in the tree.
         */
        protected boolean isLeaf() {
            return left == null && right == null;
        }

        /**
         * TODO
         * <p>
         * Performs a local update on the height of this Node<K>. Assumes that the
         * heights in the child Node<K>s are correct. Returns true iff the height
         * actually changed. This function *must* run in O(1) time.
         */
        protected boolean updateHeight() {
            /*
             * added if statements to check if height needs changed at all i think?
             * should work how i think they do
             */

            if(this.left != null){
                if(this.right != null){
                    if(this.height == this.right.height - 1){
                        return false;
                    }
                    if(this.left.height > this.right.height){
                        this.height = this.left.height + 1;
                        return true;
                    }
                    this.height = this.right.height + 1;
                    return true;
                }
                if(this.height == this.left.height - 1){
                    return false;
                }
                this.height = this.left.height + 1;
                return true;
            }

            return false;
        }

        /**
         * TODO
         * <p>
         * Returns the location of the Node<K> containing the inorder predecessor
         * of this Node<K>.
         */
        @Override
        public Location<K> previous() {
            if(this.left != null){
                return this.left.last();
            }
            else{
                return this.prevAncestor();
            }
        }

        /**
         * helper function for previous()
         *
         * @return the Node<K> that is the prev. ancestor wrt. inorder traversal
         */
        public Node<K> prevAncestor(){
            Node<K> n = this;
            while(n.parent != null && n == n.parent.left){
                n = n.parent;
            }
            return n;
        }

        /**
         * helper function for previous()
         *
         * @return the last Node<K> of the subtree wrt. inorder traversal
         */
        public Node<K> last(){
            Node<K> n = this;
            while(n.right != null){
                n = n.right;
            }
            return n;
        }

        /**
         * TODO
         * <p>
         * Returns the location of the Node<K> containing the inorder successor
         * of this Node<K>.
         */
        @Override
        public Location<K> next() {
            if(this.right != null){
                return this.right.first();
            }
            else{
                return this.nextAncestor();
            }
        }

        /**
         * helper function for next()
         *
         * @return the Node<K> that is the next ancestor wrt. inorder traversal
         */
        public Node<K> nextAncestor(){
            Node<K> n = this;
            while(n.parent != null && n == n.parent.right){
                n = n.parent;
            }
            return n.parent;
        }

        /**
         * helper function for next()
         *
         * @return the first Node<K> of the subtree wrt. inorder traversal
         */
        public Node<K> first(){
            Node<K> n = this;
            while(n.left != null){
                n = n.left;
            }
            return n;
        }



        public boolean isAVL() {
            int h1, h2;
            h1 = get_height(left);
            h2 = get_height(right);
            return Math.abs(h2 - h1) < 2;
        }

        public String toString() {
            return toStringPreorder(this);
        }

    }

    protected Node<K> root;
    protected int numNodes;
    protected BiPredicate<K, K> lessThan;

    /**
     * Constructs an empty BST, where the data is to be organized according to
     * the lessThan relation.
     */
    public BinarySearchTree(BiPredicate<K, K> lessThan) {
        this.lessThan = lessThan;
    }

    /**
     * TODO
     * <p>
     * Looks up the key in this tree and, if found, returns the
     * location containing the key.
     */
    public Node<K> search(K key) {
        Node<K> n = find(key, root, null);
        if(n == null){
            return null;
        }
        else if(n.data.equals(key)){
            return n;
        }
        else{
            return null;
        }
    }

    /*
      find() is a helper function for search() ^^
     */

    protected Node<K> find(K key, Node<K> curr, Node<K> parent){
        if(curr == null){
            return parent;
        }
        else if(lessThan.test(key, curr.data)){
            return find(key, curr.left, curr);
        }
        else if(lessThan.test(curr.data, key)){
            return find(key, curr.right, curr);
        }
        else{
            return curr;
        }
    }

    /**
     * TODO
     * <p>
     * Returns the height of this tree. Runs in O(1) time!
     */
    public int height() {
        return root.height;
    }

    /**
     * TODO
     * <p>
     * Clears all the keys from this tree. Runs in O(1) time!
     */
    public void clear() {
        remove(root.data); //maybe real. i think :)
    }

    /**
     * Returns the number of keys in this tree.
     */
    public int size() {
        return numNodes;
    }

    /**
     * TODO
     * <p>
     * Inserts the given key into this BST, as a leaf, where the path
     * to the leaf is determined by the predicate provided to the tree
     * at construction time. The parent pointer of the new Node<K> and
     * the heights in all Node<K> along the path to the root are adjusted
     * accordingly.
     * <p>
     * Note: we assume that all keys are unique. Thus, if the given
     * key is already present in the tree, nothing happens.
     * <p>
     * Returns the location where the insert occurred (i.e., the leaf
     * Node<K> containing the key), or null if the key is already present.
     */
    public Node<K> insert(K key) {
        if(contains(key)){
            return null;
        }

        return insertHelper(key, root);
    }

    protected Node<K> insertHelper(K key, Node<K> curr){
        if(curr == null){
            return new Node<>(key, null, null);
        }
        else if(lessThan.test(key, curr.data)){
            curr.left = insertHelper(key, curr.left);
            return curr;
        }
        else if(lessThan.test(curr.data, key)){
            curr.right = insertHelper(key, curr.right);
            return curr;
        }
        else{
            return curr;
        }
    }

    /**
     * Returns a textual representation of this BST.
     */
    public String toString() {
        return toStringPreorder(root);
    }

    /**
     * Returns true iff the given key is in this BST.
     */
    public boolean contains(K key) {
        Node<K> p = search(key);
        return p != null;
    }

    /**
     * TODO
     * <p>
     * Removes the key from this BST. If the key is not in the tree,
     * nothing happens.
     */
    public void remove(K key) {
        if(!contains(key)){
            root = removeHelper(root, key);
        }
    }

    /**
     * Helper function for remove()
     *
     * @param curr
     * @param key
     * @return Node<K>
     */
    private Node<K> removeHelper(Node<K> curr, K key){
        if(curr == null){
            return null;
        }
        else if(lessThan.test(key, curr.data)){
            curr.left = removeHelper(curr.left, key);
            return curr;
        }
        else if(lessThan.test(curr.data, key)){
            curr.right = removeHelper(curr.right, key);
            return curr;
        }
        else{
            if(curr.left == null){
                return curr.right;
            }
            else if(curr.right == null){
                return curr.left;
            }
            else{
                Node<K> min = curr.right.first();
                curr.data = min.data;
                curr.right = removeHelper(curr.right, min.data);
                return curr;
            }
        }
    }

    /**
     * TODO * <p> * Returns a sorted list of all the keys in this tree.
     */
    public List<K> keys() {
        return null;  // delete this line and add your code
    }

    static private <K> String toStringPreorder(Node<K> p) {
        if (p == null) return ".";
        String left = toStringPreorder(p.left);
        if (left.length() != 0) left = " " + left;
        String right = toStringPreorder(p.right);
        if (right.length() != 0) right = " " + right;
        String data = p.data.toString();
        return "(" + data + "[" + p.height + "]" + left + right + ")";
    }

    /**
     * The get_height method returns the height of the Node<K> n, which may be null.
     */
    static protected <K> int get_height(Node<K> n) {
        if (n == null) return -1;
        else return n.height;
    }
}
