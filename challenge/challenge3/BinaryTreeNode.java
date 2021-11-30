package challenge3;

public class BinaryTreeNode {

    public BinaryTreeNode left;
    public BinaryTreeNode right;
    public BinaryTreeNode parent;

    public int depth;
    public int xOffset;

    public BinaryTreeNode(BinaryTreeNode parent, BinaryTreeNode left, BinaryTreeNode right, int depth, int xOffset) {
        this.parent = parent;
        this.left = left;
        this.right = right;
        this.depth = depth;
        this.xOffset = xOffset;
    }

}
