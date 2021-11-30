package challenge3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BinaryTreeTest {

    @Test
    void binTreeInit() {
        BinaryTree tree = new BinaryTree(1);
        // root node has no parent
        assertNull(tree.root.parent);
        // non-leaf and non-root nodes have children
        assertNotNull(tree.root.left);
        assertNotNull(tree.root.right);
        // leaves have no children
        assertNull(tree.root.right.left);
        assertNull(tree.root.right.right);
        assertNull(tree.root.left.left);
        assertNull(tree.root.left.right);
    }
}