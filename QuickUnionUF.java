import java.io.*;
import java.util.Scanner;
public class QuickUnionUF {
    private int[] id;    // id[i] = component identifier of i
    private int[] rank;
    private int count;   // number of components

    public QuickUnionUF(int n) {
        count = n;
        id = new int[n];
        for (int i = 0; i < n; i++){
            id[i] = i;
            rank[i] = 0;
        }
    }

    public int count() {
        return count;
    }
  

    public int find(int p) {
        validate(p);
        while(p!=id[p]) {
            id[p]=id[id[p]]; 
            p = id[p];
        }
             
        return p;
    }

    // validate that p is a valid index
    private void validate(int p) {
        int n = id.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));
        }
    }


    @Deprecated
    public boolean connected(int p, int q) {
        validate(p);
        validate(q);
        return id[p] == id[q];
    }

    public void union(int p, int q) {
        validate(p);
        validate(q);
        int rootP = find(p);   // needed for correctness
        int rootQ = find(q);   // to reduce the number of array accesses

        // p and q are already in the same component
        if (rootP == rootQ) return;

        // id[rootP]=rootQ;
        if      (rank[rootP] < rank[rootQ]) id[rootP] = rootQ;
        else if (rank[rootP] > rank[rootQ]) id[rootQ] = rootP;
        else {
            id[rootQ] = rootP;
            rank[rootP]++;
        }
        count--;
    }


    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("test.txt"));
        int n = sc.nextInt();
        System.out.println(n);
        QuickFindUF uf = new QuickFindUF(n);
        while (sc.hasNextInt()) {
            int p = sc.nextInt();
            int q = sc.nextInt();
            if (uf.find(p) == uf.find(q)) continue;
            uf.union(p, q);
            System.out.println(p + " " + q);
        }
        System.out.println(uf.count() + " components");
    }
}