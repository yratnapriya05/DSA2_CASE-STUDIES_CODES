// FinPulse – CO4: Dijkstra's, Bellman-Ford & Floyd-Warshall
// Logistics & Routing Optimization — Shortest Path Algorithms
// Student: SAMPATI VARUN SRIDHAR REDDY | Roll: 2520090083 | Branch: CSIT

import java.util.*;

public class ShortestPaths {

    static final int V   = 6;          // 6 city delivery hubs: C0–C5
    static final int INF = Integer.MAX_VALUE / 2;

    // ─────────────────────────────────────────────────────────────────────
    // 1. DIJKSTRA'S ALGORITHM
    //    Single-source, no negative weights, O(E log V)
    // ─────────────────────────────────────────────────────────────────────
    static void dijkstra(List<int[]>[] adj, int src) {
        int[] dist = new int[V];
        int[] prev = new int[V];
        Arrays.fill(dist, INF);
        Arrays.fill(prev, -1);
        dist[src] = 0;

        // PriorityQueue stores {distance, vertex}
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        pq.offer(new int[]{0, src});

        while (!pq.isEmpty()) {
            int[] top  = pq.poll();
            int   d    = top[0];
            int   u    = top[1];

            if (d > dist[u]) continue; // stale entry

            System.out.println("  Visited: C" + u + " (dist=" + dist[u] + ")"
                    + (prev[u] != -1 ? "  via C" + prev[u] + "->C" + u : ""));

            for (int[] edge : adj[u]) {
                int v   = edge[0];
                int wt  = edge[1];
                if (dist[u] + wt < dist[v]) {
                    dist[v] = dist[u] + wt;
                    prev[v] = u;
                    pq.offer(new int[]{dist[v], v});
                }
            }
        }

        System.out.println("\n  Shortest distances from C" + src + ":");
        for (int i = 0; i < V; i++)
            System.out.printf("  C%d->C%d: %s%n", src, i,
                    dist[i] == INF ? "INF" : String.valueOf(dist[i]));

        // Print optimal path C0 -> C5
        System.out.print("\n  Optimal delivery path C0->C5: ");
        printPath(prev, 5);
        System.out.println(" (cost=" + dist[5] + ")");
    }

    static void printPath(int[] prev, int v) {
        if (prev[v] == -1) { System.out.print("C" + v); return; }
        printPath(prev, prev[v]);
        System.out.print("->C" + v);
    }

    // ─────────────────────────────────────────────────────────────────────
    // 2. BELLMAN-FORD ALGORITHM
    //    Single-source, handles negative weights, O(V * E)
    // ─────────────────────────────────────────────────────────────────────
    static void bellmanFord(int[][] edges, int edgeCount, int src) {
        int[] dist = new int[V];
        Arrays.fill(dist, INF);
        dist[src] = 0;

        // Relax all edges V-1 times
        for (int iter = 1; iter <= V - 1; iter++) {
            boolean updated = false;
            for (int i = 0; i < edgeCount; i++) {
                int u  = edges[i][0];
                int v  = edges[i][1];
                int wt = edges[i][2];
                if (dist[u] != INF && dist[u] + wt < dist[v]) {
                    dist[v]  = dist[u] + wt;
                    updated  = true;
                }
            }
            System.out.println("  Iteration " + iter + ": "
                    + (updated ? "Updates made." : "No updates. Converged early."));
            if (!updated) break;
        }

        System.out.println("\n  Shortest distances from C" + src + ":");
        for (int i = 0; i < V; i++)
            System.out.printf("  C%d->C%d: %s%n", src, i,
                    dist[i] == INF ? "INF" : String.valueOf(dist[i]));

        // Check for negative-weight cycle (V-th relaxation)
        boolean negCycle = false;
        for (int i = 0; i < edgeCount; i++) {
            int u  = edges[i][0];
            int v  = edges[i][1];
            int wt = edges[i][2];
            if (dist[u] != INF && dist[u] + wt < dist[v]) {
                negCycle = true;
                break;
            }
        }
        System.out.println(negCycle
                ? "  [WARNING] Negative-weight cycle detected!"
                : "  [INFO] No negative-weight cycle detected. Graph is safe.");
    }

    // ─────────────────────────────────────────────────────────────────────
    // 3. FLOYD-WARSHALL ALGORITHM
    //    All-pairs shortest paths, O(V^3)
    // ─────────────────────────────────────────────────────────────────────
    static void floydWarshall(int[][] graph) {
        int[][] dist = new int[V][V];

        // Initialize distance matrix
        for (int i = 0; i < V; i++)
            for (int j = 0; j < V; j++)
                dist[i][j] = graph[i][j];

        // Relax through each intermediate vertex k
        for (int k = 0; k < V; k++) {
            System.out.println("  Processing intermediate vertex k=" + k + "...");
            for (int i = 0; i < V; i++)
                for (int j = 0; j < V; j++)
                    if (dist[i][k] != INF && dist[k][j] != INF)
                        dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
        }

        // Print all-pairs distance matrix
        System.out.println("\n  All-Pairs Distance Matrix (INF = no path):");
        System.out.print("       ");
        for (int j = 0; j < V; j++) System.out.printf("C%-4d", j);
        System.out.println();
        for (int i = 0; i < V; i++) {
            System.out.print("  C" + i + " [ ");
            for (int j = 0; j < V; j++) {
                if (dist[i][j] == INF) System.out.printf("%-5s", "INF");
                else                   System.out.printf("%-5d", dist[i][j]);
            }
            System.out.println("]");
        }

        // Check for negative-weight cycle (negative diagonal)
        boolean negCycle = false;
        for (int i = 0; i < V; i++) if (dist[i][i] < 0) { negCycle = true; break; }
        System.out.println(negCycle
                ? "\n  [WARNING] Negative-weight cycle detected (negative diagonal)!"
                : "\n  [INFO] No negative-weight cycle (no negative diagonal).");
    }

    // ─────────────────────────────────────────────────────────────────────
    // MAIN
    // ─────────────────────────────────────────────────────────────────────
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {

        System.out.println("=== FinPulse Logistics – Shortest Path Algorithms ===");
        System.out.println("Graph: 6 city hubs (C0-C5), weighted directed edges (delivery costs)\n");

        // ── Build adjacency list (for Dijkstra) ──────────────────────────
        List<int[]>[] adj = new ArrayList[V];
        for (int i = 0; i < V; i++) adj[i] = new ArrayList<>();

        // Edges: {u, v, weight}
        int[][] edgeList = {
            {0, 1, 4}, {0, 2, 7}, {1, 3, 5}, {2, 3, 2},
            {3, 4, 2}, {4, 5, 5}, {1, 4, 3}, {2, 5, 8}, {1, 5, 15}
        };
        for (int[] e : edgeList) adj[e[0]].add(new int[]{e[1], e[2]});

        // ── 1. Dijkstra ───────────────────────────────────────────────────
        System.out.println("━".repeat(65));
        System.out.println(" 1. DIJKSTRA'S ALGORITHM (Source: C0, no negative weights)");
        System.out.println("━".repeat(65));
        dijkstra(adj, 0);

        // ── 2. Bellman-Ford (positive edges) ─────────────────────────────
        System.out.println("\n" + "━".repeat(65));
        System.out.println(" 2. BELLMAN-FORD ALGORITHM (Source: C0, handles negative weights)");
        System.out.println("━".repeat(65));
        bellmanFord(edgeList, edgeList.length, 0);

        // ── 2b. Bellman-Ford with negative edge (discount) ────────────────
        System.out.println("\n  [TEST] Adding discount edge C2->C3 = -3 (toll rebate):");
        int[][] edgesWithNeg = {
            {0,1,4},{0,2,7},{1,3,5},{2,3,-3},   // <-- negative discount
            {3,4,2},{4,5,5},{1,4,3},{2,5,8},{1,5,15}
        };
        bellmanFord(edgesWithNeg, edgesWithNeg.length, 0);

        // ── 3. Floyd-Warshall ─────────────────────────────────────────────
        System.out.println("\n" + "━".repeat(65));
        System.out.println(" 3. FLOYD-WARSHALL ALGORITHM (All-pairs shortest paths)");
        System.out.println("━".repeat(65));

        int[][] graphMatrix = {
            //C0    C1    C2    C3    C4    C5
            {  0,    4,    7,  INF,  INF,  INF },  // C0
            {INF,    0,  INF,    5,    3,   15  },  // C1
            {INF,  INF,    0,    2,  INF,    8  },  // C2
            {INF,  INF,  INF,    0,    2,  INF  },  // C3
            {INF,  INF,  INF,  INF,    0,    5  },  // C4
            {INF,  INF,  INF,  INF,  INF,    0  },  // C5
        };
        floydWarshall(graphMatrix);

        // ── Comparison Summary ────────────────────────────────────────────
        System.out.println("\n" + "━".repeat(65));
        System.out.println(" ALGORITHM COMPARISON SUMMARY");
        System.out.println("━".repeat(65));
        System.out.printf("  %-16s | %-14s | %-8s | %-16s%n",
                "Algorithm", "Time", "Space", "Negative Weights");
        System.out.println("  " + "-".repeat(60));
        System.out.printf("  %-16s | %-14s | %-8s | %-16s%n",
                "Dijkstra's",     "O(E log V)", "O(V)", "No");
        System.out.printf("  %-16s | %-14s | %-8s | %-16s%n",
                "Bellman-Ford",   "O(V * E)",   "O(V)", "Yes");
        System.out.printf("  %-16s | %-14s | %-8s | %-16s%n",
                "Floyd-Warshall", "O(V^3)",     "O(V^2)","Yes");

        System.out.println("\nBUILD SUCCESSFUL");
    }
}
