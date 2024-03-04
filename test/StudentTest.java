import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.Random;

import static java.lang.Math.log;

public class StudentTest {

    @Test
    public void insertSmallBST() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>((Integer x, Integer y) -> x < y);
        TreeMap<Integer, Integer> map = new TreeMap<>();
        int[] a = new int[]{4, 8, 0, 2, 6, 10};
        /*
         *       4
         *     /  \
         *    /    \
         *   0      8
         *    \    / \
         *     2  6   10
         */
        for (Integer key : a) {
            bst.insert(key);
            map.put(key, key);
        }
        for (int i = 0; i != 11; ++i) {
            assertEquals(bst.contains(i), map.containsKey(i));
        }
    }

    @Test
    public void insertBigBST() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>((Integer x, Integer y) -> x < y);
        TreeMap<Integer, Integer> map = new TreeMap<>();
        int[] a = new int[]{4, 8, 0, 2, 6, 10, 12, 20, 16, 29, 19, 13, 25, 27, 14, 26, 39, 40, 65};
        for (Integer key : a) {
            bst.insert(key);
            map.put(key, key);
        }
        for (int i = 0; i != 11; ++i) {
            assertEquals(bst.contains(i), map.containsKey(i));
        }
    }

    @Test
    public void testKeys() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>((Integer x, Integer y) -> x < y);
        List<Integer> checkList = new LinkedList<Integer>();
        int[] a = new int[]{4, 8, 0, 2, 6, 10};

        for (Integer key : a) {
            bst.insert(key);
        }
        checkList.add(0);
        checkList.add(2);
        checkList.add(4);
        checkList.add(6);
        checkList.add(8);
        checkList.add(10);
        System.out.println("BST:" + bst);
        System.out.println("Keys: " + bst.keys());
        List<Integer> list = bst.keys();
        for (int i = 0; i != checkList.size(); ++i) {
            assertEquals(checkList.get(i), list.get(i));
        }
    }

    @Test
    public void testRemove() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>((Integer x, Integer y) -> x < y);
        TreeMap<Integer,Integer> map = new TreeMap<>();
        int[] a = new int[]{4, 8, 0, 2, 6, 10};
        /*
         *       4
         *     /  \
         *    /    \
         *   0      8
         *    \    / \
         *     2  6   10
         */
        for (Integer key : a) {
            bst.insert(key);
            map.put(key, key);
        }

        map.remove(4);
        map.remove(10);
        bst.remove(4);
        bst.remove(10);
        map.remove(0);
        bst.remove(0);
        for (int i = 0; i != 11; ++i) {
            assertEquals(bst.contains(i), map.containsKey(i));
        }
    }

    @Test
    public void testAVLSmallInsert(){
        AVLTree<Integer> avlTree = new AVLTree<>((Integer x, Integer y) -> x < y);
        TreeMap<Integer, Integer> map = new TreeMap<>();
        int[] a = new int[]{4, 8, 0, 2, 6, 10};
        for (Integer key : a) {
            avlTree.insert(key);
            map.put(key, key);
        }
        for (int i = 0; i != 11; ++i) {
            assertEquals(avlTree.contains(i), map.containsKey(i));
        }
    }

    @Test
    public void testAVLBigInsert(){
        AVLTree<Integer> avlTree = new AVLTree<>((Integer x, Integer y) -> x < y);
        TreeMap<Integer, Integer> map = new TreeMap<>();
        int[] a = new int[]{4, 8, 0, 2, 6, 10, 12, 20, 16, 29, 19, 13, 25, 27, 14, 26, 39, 40, 65};
        for (Integer key : a) {
            avlTree.insert(key);
            map.put(key, key);
        }
        for (int i = 0; i != 11; ++i) {
            assertEquals(avlTree.contains(i), map.containsKey(i));
        }
    }

    @Test
    public void testAVLBalanceSmall(){
        AVLTree<Integer> avlTree = new AVLTree<>((Integer x, Integer y) -> x < y);
        int[] a = new int[]{4, 8, 0, 2, 6, 10};
        for (Integer key : a) {
            avlTree.insert(key);
        }
        for (int i = 0; i != 11; ++i) {
            assertTrue(avlTree.isAVL());
        }
    }

    @Test
    public void testAVLBalanceBig(){
        AVLTree<Integer> avlTree = new AVLTree<>((Integer x, Integer y) -> x < y);
        int[] a = new int[]{4, 8, 0, 2, 6, 10, 12, 20, 16, 29, 19, 13, 25, 27, 14, 26, 39, 40, 65};
        for (Integer key : a) {
            avlTree.insert(key);
        }
        for (int i = 0; i != 11; ++i) {
            assertTrue(avlTree.isAVL());
        }
    }

    /**
     * TODO: Test cases
     */
    @Test
    public void test() {
        insertSmallBST();
        insertBigBST();
        testKeys();
        testRemove();
        testAVLSmallInsert();
        testAVLBigInsert();
        testAVLBalanceSmall();
        testAVLBalanceBig();
    }

}
