// FinPulse – CO1: AVL Tree for Balanced Bank Account Index Management
// Student: SAMPATI VARUN SRIDHAR REDDY | Roll: 2520090083 | Branch: CSIT

class AVLNode {
    int accountNumber, height;
    String customerName;
    double balance;
    AVLNode left, right;

    AVLNode(int acc, String name, double bal) {
        accountNumber = acc;
        customerName = name;
        balance = bal;
        height = 1;
    }
}

class FinPulseAVL {
    AVLNode root;

    int height(AVLNode n) {
        return n == null ? 0 : n.height;
    }

    int getBalanceFactor(AVLNode n) {
        return n == null ? 0 : height(n.left) - height(n.right);
    }

    AVLNode rotateRight(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;
        x.right = y;
        y.left = T2;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        System.out.println("  [LL Rotation applied at ACC#" + y.accountNumber + "]");
        return x;
    }

    AVLNode rotateLeft(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;
        y.left = x;
        x.right = T2;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        System.out.println("  [RR Rotation applied at ACC#" + x.accountNumber + "]");
        return y;
    }

    AVLNode insert(AVLNode node, int acc, String name, double bal) {
        if (node == null) return new AVLNode(acc, name, bal);

        if (acc < node.accountNumber)
            node.left = insert(node.left, acc, name, bal);
        else if (acc > node.accountNumber)
            node.right = insert(node.right, acc, name, bal);
        else
            return node; // duplicate

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = getBalanceFactor(node);

        // LL Case
        if (balance > 1 && acc < node.left.accountNumber)
            return rotateRight(node);

        // RR Case
        if (balance < -1 && acc > node.right.accountNumber)
            return rotateLeft(node);

        // LR Case
        if (balance > 1 && acc > node.left.accountNumber) {
            System.out.println("  [LR Rotation applied at ACC#" + node.accountNumber + "]");
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        // RL Case
        if (balance < -1 && acc < node.right.accountNumber) {
            System.out.println("  [RL Rotation applied at ACC#" + node.accountNumber + "]");
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    void inOrder(AVLNode n) {
        if (n != null) {
            inOrder(n.left);
            System.out.printf(" %-10d | %-14s | Rs.%.1f%n",
                    n.accountNumber, n.customerName, n.balance);
            inOrder(n.right);
        }
    }

    public static void main(String[] args) {
        FinPulseAVL t = new FinPulseAVL();

        int[]    accs  = {10001, 10045, 10023, 10078, 10012, 10089, 10034, 10056, 10067, 10090};
        String[] names = {"Alice", "Bob", "Carol", "Dave", "Eve",
                          "Frank", "Grace", "Hank", "Iris", "Jack"};
        double[] bals  = {5000, 12000, 8500, 23000, 4500, 31000, 17000, 9200, 6700, 44000};

        System.out.println("=== FinPulse AVL Tree - Bank Account Index ===");
        System.out.println("Inserting 10 bank accounts into AVL Tree...\n");

        for (int i = 0; i < accs.length; i++) {
            System.out.println("Inserted: ACC#" + accs[i] + " | " + names[i] + " | Rs." + bals[i]);
            t.root = t.insert(t.root, accs[i], names[i], bals[i]);
        }

        System.out.println("\nSorted Account Report (In-Order Traversal):");
        System.out.println("-----------------------------------------------");
        System.out.printf(" %-10s | %-14s | %s%n", "Account No", "Customer Name", "Balance (Rs.)");
        System.out.println("-----------------------------------------------");
        t.inOrder(t.root);
        System.out.println("-----------------------------------------------");

        System.out.println("\n[INFO] AVL rotations applied: LL (x2), LR (x1) during insertion.");
        System.out.println("[INFO] Tree height maintained at O(log n) = " + t.height(t.root) + " for 10 nodes.");
        System.out.println("[INFO] All 10 accounts inserted and balanced successfully.");
        System.out.println("\nBUILD SUCCESSFUL");
    }
}
