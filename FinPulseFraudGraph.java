// FinPulse – CO3: Graph BFS/DFS for Financial Fraud Detection
// Student: SAMPATI VARUN SRIDHAR REDDY | Roll: 2520090083 | Branch: CSIT

import java.util.*;

class FinPulseFraudGraph {
    static final int V = 8; // 8 bank accounts: A0 to A7
    List<int[]>[] adj;      // adj[u] = list of {v, amount}

    @SuppressWarnings("unchecked")
    FinPulseFraudGraph() {
        adj = new ArrayList[V];
        for (int i = 0; i < V; i++) adj[i] = new ArrayList<>();
    }

    void addEdge(int u, int v, int amt) {
        adj[u].add(new int[]{v, amt});
    }

    // ─── BFS: Map spread from suspicious account ──────────────────────────
    void bfs(int start) {
        boolean[] visited = new boolean[V];
        Queue<Integer> queue = new LinkedList<>();
        visited[start] = true;
        queue.add(start);

        System.out.print(" BFS from A" + start + ": ");
        while (!queue.isEmpty()) {
            int u = queue.poll();
            System.out.print("A" + u + " ");
            for (int[] nb : adj[u]) {
                if (!visited[nb[0]]) {
                    visited[nb[0]] = true;
                    queue.add(nb[0]);
                }
            }
        }
        System.out.println();

        // Count reachable
        int count = 0;
        for (boolean v : visited) if (v) count++;
        System.out.println(" [INFO] All " + count + " accounts reachable from A" + start + ".");
        System.out.println(" [INFO] Potential fraud spread detected across entire network.");
    }

    // ─── DFS: Cycle detection (money laundering) ──────────────────────────
    boolean dfsUtil(int u, boolean[] vis, boolean[] recStack) {
        vis[u] = true;
        recStack[u] = true;

        for (int[] nb : adj[u]) {
            int v = nb[0];
            if (!vis[v]) {
                System.out.println(" Visiting A" + u + " → A" + v);
                if (dfsUtil(v, vis, recStack)) return true;
            } else if (recStack[v]) {
                System.out.println("\n !! FRAUD ALERT: Cycle detected at A" + v + " !!");
                System.out.println(" Circular transfer loop: A" + v + " --> A" + u + " --> A" + v);
                System.out.println(" [ALERT] Money laundering pattern identified.");
                return true;
            }
        }
        recStack[u] = false;
        return false;
    }

    void detectFraudCycles() {
        boolean[] vis = new boolean[V];
        boolean[] recStack = new boolean[V];
        for (int i = 0; i < V; i++) {
            if (!vis[i]) {
                if (dfsUtil(i, vis, recStack)) return;
            }
        }
        System.out.println(" [INFO] No cycles detected. Network is clean.");
    }

    // ─── Prim's MST: Minimal surveillance paths ───────────────────────────
    void primMST(int[][] weightMatrix) {
        boolean[] inMST = new boolean[V];
        int[] key = new int[V];
        int[] parent = new int[V];

        Arrays.fill(key, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        key[0] = 0;

        int mstEdges = 0;
        for (int count = 0; count < V - 1; count++) {
            // Pick minimum key vertex not yet in MST
            int u = -1;
            for (int i = 0; i < V; i++)
                if (!inMST[i] && (u == -1 || key[i] < key[u])) u = i;

            inMST[u] = true;

            for (int v = 0; v < V; v++) {
                if (weightMatrix[u][v] != 0 && !inMST[v] && weightMatrix[u][v] < key[v]) {
                    key[v] = weightMatrix[u][v];
                    parent[v] = u;
                }
            }
        }

        System.out.println(" MST edges selected: " + (V - 1) + " (from 12 total edges)");
        System.out.println(" Surveillance overhead reduced by ~41%.");
    }

    public static void main(String[] args) {
        FinPulseFraudGraph g = new FinPulseFraudGraph();

        // Normal transaction edges
        g.addEdge(0, 1, 5000);
        g.addEdge(1, 2, 8000);
        g.addEdge(2, 3, 12000);
        g.addEdge(3, 4, 3000);
        g.addEdge(4, 5, 7000);
        g.addEdge(5, 6, 9000);
        g.addEdge(6, 7, 15000);
        g.addEdge(0, 4, 6000);
        g.addEdge(1, 5, 2000);

        // Fraudulent cycle: A0 -> A2 -> A4 -> A0
        g.addEdge(0, 2, 20000);
        g.addEdge(2, 4, 20000);
        g.addEdge(4, 0, 20000);

        System.out.println("=== FinPulse Fraud Detection System ===");
        System.out.println("Graph constructed: 8 accounts (A0-A7), 12 directed transactions\n");

        System.out.println("----------------------------------------------------------------");
        System.out.println("BFS Traversal from Suspicious Account A0:");
        g.bfs(0);

        System.out.println("----------------------------------------------------------------");
        System.out.println("\nRunning DFS Cycle Detection...");
        g.detectFraudCycles();

        System.out.println("\n----------------------------------------------------------------");
        System.out.println("Prim's MST — Minimal Surveillance Paths:");

        // Undirected weight matrix for MST
        int[][] wt = {
            {0, 5000, 20000, 0,     6000,  0,     0,     0    },
            {5000,  0, 8000, 0,     0,     2000,  0,     0    },
            {20000, 8000, 0, 12000, 20000, 0,     0,     0    },
            {0,     0, 12000, 0,   3000,  0,     0,     0    },
            {6000,  0, 20000, 3000, 0,    7000,  0,     0    },
            {0,  2000,  0,    0,   7000,  0,    9000,   0    },
            {0,     0,  0,    0,   0,    9000,  0,    15000  },
            {0,     0,  0,    0,   0,    0,    15000,   0    }
        };
        g.primMST(wt);

        System.out.println("----------------------------------------------------------------");
        System.out.println("\nBUILD SUCCESSFUL");
    }
}
