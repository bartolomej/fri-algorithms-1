package homework3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

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
}