package homework3;

import java.util.Scanner;

public class Homework3 {

    static Graph graph;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] settings = scanner.nextLine().split("[ ]+");
        int nVertices = scanner.nextInt();

        boolean directed = settings[0].equals("directed");
        int[][] matrix = new int[nVertices][nVertices];
        int count = 0;
        while (scanner.hasNextLine()) {
            if (count == nVertices) {
                break;
            }
            int v1 = scanner.nextInt();
            int v2 = scanner.nextInt();
            matrix[v1][v2] = 1;
            count++;
        }
        System.out.println();

        graph = new Graph(matrix, directed);

        switch (settings[1]) {
            case "info": {
                printGraphInfo();
                break;
            }
            case "walks":
            case "dfs":
            case "bfs":
            case "sp":
            case "comp":
            case "ham":
            default: {
                System.err.println("Unsupported algorithm");
                System.exit(1);
            }
        }
    }

    static void printGraphInfo() {
        System.out.printf("%d %d\n", graph.verticesCount(), graph.edgesCount());
        for (int i = 0; i < graph.verticesCount(); i++) {
            int[] inOutDegree = graph.getVertexDegree(i);
            if (graph.directed) {
                System.out.printf("%d %d %d\n", i, inOutDegree[0], inOutDegree[1]);
            } else {
                System.out.printf("%d %d\n", i, inOutDegree[0]);
            }
        }
    }
}

class Graph {
    public int[][] matrix;
    public boolean directed;

    public Graph(int[][] matrix, boolean directed) {
        this.matrix = matrix;
        this.directed = directed;
    }

    public int verticesCount() {
        return this.matrix.length;
    }

    public int edgesCount() {
        int count = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = directed ? 0 : i; j < matrix[0].length; j++) {
                if (matrix[i][j] == 1) {
                    count++;
                }
            }
        }
        return count;
    }

    public int[] getVertexDegree(int v) {
        int inDegree = 0;
        int outDegree = 0;
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][v] == 1) {
                inDegree++;
            }
            if (matrix[v][i] == 1) {
                outDegree++;
            }
        }
        return new int[]{inDegree, outDegree};
    }

}