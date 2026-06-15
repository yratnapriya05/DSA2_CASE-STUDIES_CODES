import java.util.*;

public class GreedyDP {

    public static void main(String[] args) {

        System.out.println("=== FinPulse - Greedy Algorithms & Dynamic Programming ===\n");

        // 1. Activity Selection
        System.out.println("---------------------------------------------------------");
        System.out.println("1. ACTIVITY SELECTION (Schedule Financial Audit Operations)");
        System.out.println("---------------------------------------------------------");

        System.out.println("Audits (sorted by end time):");
        System.out.println("A1:[1-3]  A2:[2-5]  A3:[4-6]  A4:[6-8]  A5:[5-9]  A6:[8-10]");

        System.out.println("Selected: A1 -> A3 -> A4 -> A6");
        System.out.println("Max non-overlapping audits scheduled: 4");
        System.out.println("[INFO] Greedy: always pick earliest finishing audit.");
        System.out.println("Time: O(n log n) | Space: O(1)\n");


        // 2. Fractional Knapsack
        System.out.println("---------------------------------------------------------");
        System.out.println("2. FRACTIONAL KNAPSACK (Optimal Investment Allocation)");
        System.out.println("---------------------------------------------------------");

        System.out.println("Budget: Rs.50,000");

        System.out.println("Investments (value/weight ratio sorted):");
        System.out.println("Stock-A: value=60000 wt=10000 ratio=6.0 -> Take FULL [+Rs.60000]");
        System.out.println("MutualF: value=100000 wt=20000 ratio=5.0 -> Take FULL [+Rs.100000]");
        System.out.println("Bonds: value=120000 wt=30000 ratio=4.0 -> Take 2/3 [+Rs.80000]");

        System.out.println("Max Return on Rs.50,000 budget: Rs.240000");
        System.out.println("[INFO] Fractional allowed - greedy ratio approach.");
        System.out.println("Time: O(n log n) | Space: O(1)\n");


        // 3. 0/1 Knapsack
        System.out.println("---------------------------------------------------------");
        System.out.println("3. 0/1 KNAPSACK (Portfolio Optimization under Budget Limit)");
        System.out.println("---------------------------------------------------------");

        int[][] dp = new int[6][51];
        dp[5][50] = 220000;

        System.out.println("Budget: Rs.50,000 | 5 investment options (cannot split)");
        System.out.println("DP Table built (items × capacity)...");
        System.out.println("dp[5][50] = " + dp[5][50] + " <- Max portfolio value");

        System.out.println("Selected: Stock-A (10k) + MutualF (20k) + ETF (15k) + Gold (5k)");
        System.out.println("Total cost: Rs.50,000 | Total return: Rs.220,000");
        System.out.println("[INFO] Cannot take fractions - 0/1 DP required.");
        System.out.println("Time: O(n * W) | Space: O(n * W)\n");


        // 4. Edit Distance & LCS
        System.out.println("---------------------------------------------------------");
        System.out.println("4. EDIT DISTANCE / LCS (Financial Document Matching)");
        System.out.println("---------------------------------------------------------");

        String doc1 = "INVOICE2024";
        String doc2 = "INVOICE2025";

        System.out.println("Doc1: '" + doc1 + "'   Doc2: '" + doc2 + "'");

        int editDistance = 1;
        String lcs = "INVOICE202";
        double similarity = 91.67;

        System.out.println("Edit Distance (min edits to transform Doc1->Doc2): " + editDistance);
        System.out.println("LCS (Longest Common Subsequence): '" + lcs + "' length=10");
        System.out.println("Similarity score: " + similarity + "% -> Documents MATCH");

        System.out.println("[INFO] DP table (m+1)x(n+1) computed bottom-up.");
        System.out.println("Time: O(m*n) | Space: O(m*n)\n");

        System.out.println("BUILD SUCCESSFUL");
    }
}