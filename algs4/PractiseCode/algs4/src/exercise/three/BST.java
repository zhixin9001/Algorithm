package exercise.three;

import java.util.Comparator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import javafx.scene.Node;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdDraw;
import java.awt.Font;

public class BST<Key extends Comparable<Key>, Value> {
    private Node root;
    private int current;

    private class Node {
        private Key key;
        private Value val;
        private Node left, right;
        private int size;

        public Node(Key key, Value val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null)
            return 0;
        else
            return x.size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        if (key == null)
            throw new IllegalArgumentException("calls get() with a null key");
        if (x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if (cmp > 0) {
            return get(x.right, key);
        } else if (cmp < 0) {
            return get(x.left, key);
        } else {
            return x.val;
        }
    }

    public void put(Key key, Value val) {
        current=0;
        root = put(root, key, val);
    }

    private Node put(Node x, Key key, Value val) {
        current++;
        if (key == null)
            throw new IllegalArgumentException("calls put() with a null key");
        if (x == null)
            return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            x.left = put(x.left, key, val);
        else if (cmp > 0)
            x.right = put(x.right, key, val);
        else {
            x.val = val;
        }
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    public Key min() {
        if (isEmpty())
            throw new NoSuchElementException("calls min() with empty symbol table");
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.left == null)
            return x;
        else
            return min(x.left);
    }

    public Key max() {
        if (isEmpty())
            throw new NoSuchElementException("calls max() with empty symbol table");
        return max(root).key;
    }

    private Node max(Node x) {
        if (x.right == null)
            return x;
        else
            return max(x.right);
    }

    public Iterable<Key> keys() {
        if (isEmpty())
            return new Queue<Key>();
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        if (lo == null)
            throw new IllegalArgumentException("first argument to keys() is null");
        if (hi == null)
            throw new IllegalArgumentException("second argument to keys() is null");

        Queue<Key> queue = new Queue<Key>();
        keys(root, queue, lo, hi);
        return queue;
    }

    private void keys(Node x, Queue<Key> queue, Key lo, Key hi) {
        if (x == null)
            return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if (cmplo < 0)
            keys(x.left, queue, lo, hi);
        if (cmplo <= 0 && cmphi >= 0)
            queue.enqueue(x.key);
        if (cmphi > 0)
            keys(x.right, queue, lo, hi);
    }

    public static void main(String[] args) {
        int minlen = 8;
        BST<String, Integer> st = new BST<String, Integer>();
        VisualAccumulator va = new VisualAccumulator(15000, 50);
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            if (key.length() < minlen)
                continue;
            st.put(key, i);
            va.addDataValue(st.current);
        }

        // StdOut.println();

        // for (String s : st.keys())
        //     StdOut.println(s + " " + st.get(s));
    }
}