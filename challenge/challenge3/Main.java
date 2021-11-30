package challenge3;

public class Main {

    public static void main(String[] args) {
        int treeDepth;
        String traversalType;
        if (args.length == 2) {
            treeDepth = Integer.parseInt(args[0]);
            traversalType = args[1];
        } else {
            info("MRunning with default args: depth=3 traversal=postorder");
            treeDepth = 6;
            traversalType = "preorder";
        }

        BinaryTreeDraw binaryTree = new BinaryTreeDraw(treeDepth);
        binaryTree.init(2000, 1000);
        try {
            binaryTree.draw(traversalType);
        } catch (Exception e) {
            error(e.getMessage());
        }
    }

    private static void error(String message) {
        System.err.println(message);
        System.exit(1);
    }

    private static void info(String message) {
        System.out.println(message);
    }
}
