package algs4.four;

import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.Queue;

public class BreadthFirstPaths {
    private boolean[] marked;
    private int[] edgeTo;
    private final int s;

    public BreadthFirstPaths(Graph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        bfs(G, s);
    }

    private void bfs(Graph G, int s) {
        Queue<Integer> queue = new Queue<Integer>();
        marked[s] = true;
        queue.enqueue(s);
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (int w : G.adj(v)) {
                if(!marked[w]){
                    edgeTo[w]=v;
                    marked[w]=true;
                    queue.enqueue(w);
                }
            }
        }
    }

    public boolean hasPathTo(int v){
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v))
            return null;

        Stack<Integer> path = new Stack<Integer>();
        for (int a = v; a != s; a = edgeTo[a]) {
            path.push(a);
        }

        path.push(s);
        return path;
    }

    
     // cmd /c --% java algs4.four.BreadthFirstPaths ..\..\..\algs4-data\tinyCG.txt 0
     public static void main(String[] args) {
        In in = new In(args[0]);
        int s = Integer.parseInt(args[1]);
        Graph g = new Graph(in);
        BreadthFirstPaths search = new BreadthFirstPaths(g, s);

        for (int i = 0; i < g.V(); i++) {
            StdOut.print(i + ":");
            Iterable<Integer> path = search.pathTo(i);
            for (Integer p : path) {
                if (search.s != p) {
                    StdOut.print("-" + p);
                } else {
                    StdOut.print(p);
                }
            }
            StdOut.println();
        }
    }
}