import java.util.ArrayList;
import java.util.List;

class Solution {
    // Global variables for DFS context
    private int[] subXor;
    private int[] tin;
    private int[] tout;
    private int timer;
    private List<List<Integer>> adj;
    private int[] values; // copy of nums

    public int minimumScore(int[] nums, int[][] edges) {
        int n = nums.length;
        this.values = nums;
        this.adj = new ArrayList<>();
        this.subXor = new int[n];
        this.tin = new int[n];
        this.tout = new int[n];
        this.timer = 0;

        // 1. Build Adjacency List
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int[] edge : edges) {
            adj.get(edge[0]).add(edge[1]);
            adj.get(edge[1]).add(edge[0]);
        }

        // 2. DFS to compute subtree XORs and Ancestry times
        // We root at 0, parent of root is -1
        dfs(0, -1);

        int totalXor = subXor[0];
        int minScore = Integer.MAX_VALUE;

        // 3. Iterate over all pairs of edges
        // We iterate nodes i and j (from 1 to n-1) representing the child node
        // of the edge being removed. Node 0 cannot be a child of a removed edge.
        for (int i = 1; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int a, b, c;

                // Check if i is ancestor of j
                if (isAncestor(i, j)) {
                    a = subXor[j];                  // The lowest component
                    b = subXor[i] ^ subXor[j];      // The middle component
                    c = totalXor ^ subXor[i];       // The top component
                } 
                // Check if j is ancestor of i
                else if (isAncestor(j, i)) {
                    a = subXor[i];                  // The lowest component
                    b = subXor[j] ^ subXor[i];      // The middle component
                    c = totalXor ^ subXor[j];       // The top component
                } 
                // Disjoint subtrees
                else {
                    a = subXor[i];
                    b = subXor[j];
                    c = totalXor ^ subXor[i] ^ subXor[j];
                }

                int maxVal = Math.max(a, Math.max(b, c));
                int minVal = Math.min(a, Math.min(b, c));
                minScore = Math.min(minScore, maxVal - minVal);
            }
        }

        return minScore;
    }

    private void dfs(int u, int p) {
        tin[u] = timer++;
        subXor[u] = values[u];

        for (int v : adj.get(u)) {
            if (v != p) {
                dfs(v, u);
                subXor[u] ^= subXor[v]; // XOR sum propagates up
            }
        }
        tout[u] = timer++;
    }

    // Returns true if u is an ancestor of v
    private boolean isAncestor(int u, int v) {
        return tin[u] <= tin[v] && tout[u] >= tout[v];
    }
}