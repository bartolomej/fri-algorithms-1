package challenge3;

import java.awt.*;

public class BinaryTree {

    private static final int CANVAS_SIZE = 1000;
    private static final int CANVAS_WIDTH = CANVAS_SIZE;
    private static final int CANVAS_HEIGHT = CANVAS_SIZE;
    private static final int CANVAS_BORDER = CANVAS_SIZE / 10;
    private static final Color NODE_COLOR = new Color(119,118,188);
    private static final Color EDGE_COLOR = new Color(205,199,229);
    private boolean drawNodes;

    public BinaryTreeNode root;
    public int depth;

    public BinaryTree(int depth) {
        this.depth = depth;
        this.root = new BinaryTreeNode(null, null, null, 0, 0);
        initPerfectTree(1, 0, CANVAS_WIDTH / 2, root);
        initCanvas();
    }

    private void initCanvas() {
        StdDraw.setCanvasSize(CANVAS_SIZE, CANVAS_SIZE);
        StdDraw.setXscale(-CANVAS_WIDTH / 2.0, CANVAS_WIDTH / 2.0);
        StdDraw.setYscale(-CANVAS_HEIGHT, 0);
    }

    private void initPerfectTree(int depth, int xMiddle, int width, BinaryTreeNode node) {
        if (depth == this.depth + 1) {
            return;
        }
        int nextWidth = width / 2;
        int leftOffset = xMiddle - nextWidth;
        int rightOffset = xMiddle + nextWidth;
        node.left = new BinaryTreeNode(node, null, null, depth, leftOffset);
        node.right = new BinaryTreeNode(node, null, null, depth, rightOffset);
        initPerfectTree(depth + 1, leftOffset, nextWidth, node.left);
        initPerfectTree(depth + 1, rightOffset, nextWidth, node.right);
    }

    public void draw(String traversalType) throws Exception {
        // first draw node edges and then nodes to prevent overwriting node dots
        drawNodes = false;
        drawTreeTraversal(traversalType);
        drawNodes = true;
        drawTreeTraversal(traversalType);
    }

    private void drawTreeTraversal(String traversalType) throws Exception {
        switch (traversalType) {
            case "preorder": {
                preorderDraw(root);
                break;
            }
            case "postorder": {
                postorderDraw(root);
                break;
            }
            case "inorder": {
                inorderDraw(root);
                break;
            }
            default: {
                throw new Exception("Unknown traversal");
            }
        }
    }

    public void preorderDraw(BinaryTreeNode node) {
        if (node == null) {
            return;
        }
        visitNode(node);
        preorderDraw(node.left);
        preorderDraw(node.right);
    }

    public void postorderDraw(BinaryTreeNode node) {
        if (node == null) {
            return;
        }
        preorderDraw(node.left);
        preorderDraw(node.right);
        visitNode(node);
    }

    public void inorderDraw(BinaryTreeNode node) {
        if (node == null) {
            return;
        }
        preorderDraw(node.left);
        visitNode(node);
        preorderDraw(node.right);
    }

    private void visitNode(BinaryTreeNode node) {
        if (drawNodes) {
            drawNode(node);
        } else {
            drawNodeEdge(node);
        }
    }

    public void drawNode(BinaryTreeNode node) {
        StdDraw.setPenRadius(getNodeRadius());
        StdDraw.setPenColor(NODE_COLOR);
        StdDraw.point(getX(node), getY(node));
    }

    public void drawNodeEdge(BinaryTreeNode node) {
        if (node.parent != null) {
            StdDraw.setPenRadius(getLineWidth());
            StdDraw.setPenColor(EDGE_COLOR);
            StdDraw.line(getX(node), getY(node), getX(node.parent), getY(node.parent));
        }
    }

    public int getNLeafNodes() {
        return (int)Math.pow(2, this.depth);
    }

    private double getNodeRadius() {
        return 0.05;
    }

    private double getLineWidth() {
        return getNodeRadius() / 2;
    }

    private int getX(BinaryTreeNode node) {
        return node.xOffset;
    }

    private int getY(BinaryTreeNode node) {
        int factor = ((CANVAS_HEIGHT - 2 * CANVAS_BORDER) / this.depth) ;
        return -node.depth * factor - CANVAS_BORDER;
    }
}
