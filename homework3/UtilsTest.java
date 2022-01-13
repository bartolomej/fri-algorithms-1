package homework3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Test
    void testMatrixMultiply() throws Exception {
        int[][] a = new int[][]{
                {1},
                {2}
        };
        int[][] b = new int[][]{
                {3, 4}
        };
        int[][] result = Utils.matrixMultiply(a, b);
        int[][] expected = new int[][]{
                {3, 4},
                {6, 8}
        };
        assertArrayEquals(expected, result);
    }

    @Test
    void testMatrixMultiply2() throws Exception {
        int[][] a = new int[][]{
                {1,2},
                {3,4}
        };
        int[][] b = new int[][]{
                {1,2},
                {3,4}
        };
        int[][] result = Utils.matrixMultiply(a, b);
        int[][] expected = new int[][]{
                {7, 10},
                {15, 22}
        };
        assertArrayEquals(expected, result);
    }

}