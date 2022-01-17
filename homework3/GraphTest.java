package homework3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    // graph used in Homework3 tests
    int[][] matrix = new int[][]{
            {0, 1, 0, 1, 0, 0, 0, 0, 0, 0},
            {1, 0, 1, 1, 1, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 1, 1, 0, 0, 0, 0},
            {1, 1, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 1, 1, 1, 0, 1, 0, 0, 0, 0},
            {0, 0, 1, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
    };

    @Test
    void testEdgeCount() {
        int[][] matrix = new int[][]{
                {1, 1, 1, 1},
                {0, 1, 1, 1},
                {1, 0, 1, 1},
                {0, 1, 0, 1},
        };
        Graph directed = new Graph(matrix, true);
        assertEquals(12, directed.edgesCount());

        Graph undirected = new Graph(matrix, false);
        assertEquals(10, undirected.edgesCount());
    }

    @Test
    void testTrianglesCount() throws Exception {
        int[][] matrix = new int[][]{
                {0, 1, 1, 1, 1},
                {1, 0, 1, 1, 1},
                {1, 1, 0, 1, 1},
                {1, 1, 1, 0, 1},
                {1, 1, 1, 1, 0}
        };
        Graph graph = new Graph(matrix, false);
        assertEquals(10, graph.getTrianglesCount());
    }

    @Test
    void testDfsIntroOutroPath() {
        // http://graphonline.ru/en/?graph=hqlkRayAjLRIAhHr
        int[][] matrix = new int[][]{
                {0, 1, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 0},
                {1, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0},
                {0, 0, 0, 1, 0, 1},
                {0, 0, 0, 1, 0, 0}
        };
        Graph graph = new Graph(matrix, true);
        graph.dfsFull(true);
        graph.dfsFull(false);
    }

    @Test
    void testSp1() throws Exception {
        Graph graph = new Graph(matrix, false);
        // [3, 4, 1, 1, -1, 2, 0, 0, 0, 0]
        assertEquals(2, graph.shortestPathLengthBfs(4, 0));
    }

    @Test
    void testSp2() throws Exception {
        Graph graph = new Graph(matrix, false);
        // [0, 4, 4, 4, -1, 4, 0, 0, 0, 0]
        assertEquals(1, graph.shortestPathLengthBfs(4, 1));
    }

    @Test
    void testSp3() throws Exception {
        Graph graph = new Graph(matrix, false);
        // [1, 4, 1, 1, -1, 4, 0, 0, 0, 0]
        assertEquals(1, graph.shortestPathLengthBfs(4, 2));
    }
}