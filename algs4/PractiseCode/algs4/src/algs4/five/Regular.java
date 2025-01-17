package algs4.five;

import algs4.four.Digraph;
import algs4.four.DirectedDFS;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;
import exercise.four.Bag;

public class Regular {
    private char[] re;
    private Digraph G;
    private int M;

    public Regular(String regexp) {
        Stack<Integer> ops = new Stack<Integer>();
        re = regexp.toCharArray();
        M = re.length;
        G = new Digraph(M + 1);

        for (int i = 0; i < M; i++) {
            int lp = i;
            if (re[i] == '(' || re[i] == '|')
                ops.push(i);
            else if (re[i] == ')') {
                int or = ops.pop();
                if (re[or] == '|') {
                    lp = ops.pop();
                    G.addEdge(lp, or + 1);
                    G.addEdge(or, i);
                } else
                    lp = or;
            }

            if (i < M - 1 && re[i + 1] == '*') {
                G.addEdge(lp, i + 1);
                G.addEdge(i + 1, lp);
            }

            if (re[i] == '(' || re[i] == '*' || re[i] == ')')
                G.addEdge(i, i + 1);
        }
    }

    public boolean recognize(String txt) {
        Bag<Integer> pc = new Bag<Integer>();
        DirectedDFS dfs = new DirectedDFS(G, 0);
        for (int v = 0; v < G.V(); v++) {
            if (dfs.marked(v))
                pc.add(v);
        }

        for (int i = 0; i < txt.length(); i++) {
            Bag<Integer> match = new Bag<Integer>();
            for (int v : pc)
                if (v < M)
                    if (re[v] == txt.charAt(i) || re[v] == '.')
                        match.add(v + 1);
            pc = new Bag<Integer>();
            dfs = new DirectedDFS(G, match);
            for (int v = 0; v < G.V(); v++)
                if (dfs.marked(v))
                    pc.add(v);
        }

        for (int v : pc)
            if (v == M)
                return true;
        return false;
    }

    // cmd /c --% java algs4.five.Regular "(A*B|AC)D" < ..\..\..\algs4-data\tinyL.txt
    public static void main(String[] args) {
        String regexp = "(.*" + args[0] + ".*)";
        Regular re = new Regular(regexp);
        while (StdIn.hasNextLine()) {
            String txt = StdIn.readLine();
            if (re.recognize(txt))
                StdOut.println(txt);
        }
    }
}