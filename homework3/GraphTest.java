package homework3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    @Test
    void testEdgeCount() {
        int[][] matrix = new int[][]{
            {1,1,1,1},
            {0,1,1,1},
            {1,0,1,1},
            {0,1,0,1},
        };
        Graph directed = new Graph(matrix, true);
        assertEquals(12, directed.edgesCount());

        Graph undirected = new Graph(matrix, false);
        assertEquals(10, undirected.edgesCount());
    }
}