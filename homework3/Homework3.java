package homework3;

import java.util.Scanner;

public class Homework3 {

    static Graph graph;

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String[] settings = scanner.nextLine().split("[ ]+");
        int nVertices = scanner.nextInt();

        boolean directed = settings[0].equals("directed");
        int[][] matrix = new int[nVertices][nVertices];
        while (scanner.hasNextLine()) {
            int v1 = scanner.nextInt();
            int v2 = scanner.nextInt();
            matrix[v1][v2] = 1;
            if (!directed) {
                matrix[v2][v1] = 1;
            }
        }

        graph = new Graph(matrix, directed);

        switch (settings[1]) {
            case "info": {
                printGraphInfo();
                break;
            }
            case "walks": {
                int k = Integer.parseInt(settings[2]);
                int[][] walksMatrix = graph.getWalksMatrix(k);
                Utils.printMatrix(walksMatrix);
                break;
            }
            case "bfs": {
                graph.bfsFull();
                break;
            }
            case "dfs": {
                graph.dfsFull(true);
                System.out.println();
                graph.dfsFull(false);
                break;
            }
            case "sp": {
                int start = Integer.parseInt(settings[2]);
                for (int i = 0; i < graph.verticesCount(); i++) {
                    int pathLength = graph.shortestPathLengthBfs(start, i);
                    System.out.printf("%d %d", i, pathLength);
                    if (i < graph.verticesCount() - 1) {
                        System.out.println();
                    }
                }
                break;
            }
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
        int[][] inOutDegrees = new int[graph.verticesCount()][graph.directed ? 3 : 2];
        for (int i = 0; i < graph.verticesCount(); i++) {
            int[] inOutDegree = graph.getVertexDegree(i);
            inOutDegrees[i][0] = i;
            inOutDegrees[i][1] = inOutDegree[0];
            if (graph.directed) {
                inOutDegrees[i][2] = inOutDegree[1];
            }
        }
        Utils.printMatrix(inOutDegrees);
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

    /**
     * Calculates total triangles in a graph.
     * - calculate matrix^3
     * - sum diagonal values (matrix trace ~ sled)
     * - divide by 6
     */
    public int getTrianglesCount() throws Exception {
        int[][] matrix3;
        matrix3 = Utils.matrixMultiply(matrix, matrix);
        matrix3 = Utils.matrixMultiply(matrix3, matrix);
        return Graph.getTrace(matrix3) / 6;
    }

    /**
     * Topological order can be calculated for DAG (Directed Acyclic Graphs):
     * - remove all vertices with inbound degree of 0
     * - add them to the list
     * - repeat
     */

    public static int getTrace(int[][] matrix) {
        int sum = 0;
        for (int i = 0; i < matrix.length; i++) {
            sum += matrix[i][i];
        }
        return sum;
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

    public int[][] getWalksMatrix(int length) throws Exception {
        int[][] walks = Utils.matrixMultiply(matrix, matrix);
        for (int i = 0; i < length - 2; i++) {
            walks = Utils.matrixMultiply(walks, matrix);
        }
        return walks;
    }

    public int shortestPathLengthBfs(int start, int end) throws Exception {
        boolean[] visited = new boolean[verticesCount()];
        int[] from = new int[verticesCount()];
        from[start] = -1;
        visited[start] = true;
        Queue nextNodes = new Queue();
        nextNodes.enqueue(start);
        while (!nextNodes.isEmpty()) {
            int node = nextNodes.dequeue();
            if (node == end) {
                return countPathLength(from, node);
            }
            int[] children = getChildren(node);
            for (int child : children) {
                if (visited[child]) {
                    continue;
                }
                from[child] = node;
                visited[child] = true;
                nextNodes.enqueue(child);
            }
        }
        return -1;
    }

    public static int countPathLength(int[] from, int node) {
        int length = 0;
        while (true) {
            node = from[node];
            if (node > -1) {
                length++;
            } else {
                break;
            }
        }
        return length;
    }

    public void dfsFull(boolean logEntry) {
        int time = 0;
        int[] visited = new int[verticesCount()];
        for (int i = 0; i < verticesCount(); i++) {
            if (visited[i] == 0) {
                time = dfs(i, visited, time, logEntry);
            }
        }
    }

    public int dfs(int node, int[] visited, int time, boolean logEntry) {
        if (logEntry) {
            boolean isLast = time == visited.length;
            System.out.printf("%d" + (isLast ? "" : " "), node);
        }
        time++;
        visited[node] = time;
        for (int child : getChildren(node)) {
            if (visited[child] == 0) {
                dfs(child, visited, time, logEntry);
            }
        }
        if (!logEntry) {
            // TODO: make sure the last call doesn't print " " at the end
            boolean isLast = time == visited.length;
            System.out.printf("%d" + (isLast ? "" : " "), node);
        }
        return time;
    }

    public void bfsFull() throws Exception {
        int time = 0;
        int[] visited = new int[verticesCount()];
        for (int i = 0; i < verticesCount(); i++) {
            if (visited[i] == 0) {
                time = bfs(i, visited, time);
            }
        }
    }

    public int bfs(int node, int[] visited, int time) throws Exception {
        Queue nextNodes = new Queue();
        nextNodes.enqueue(node);
        while (!nextNodes.isEmpty()) {
            node = nextNodes.dequeue();
            if (visited[node] != 0) {
                continue;
            }
            time++;
            visited[node] = time;
            boolean isLast = time == visited.length;
            System.out.printf("%d" + (isLast ? "" : " "), node);
            int[] children = getChildren(node);
            for (int child : children) {
                nextNodes.enqueue(child);
            }
        }
        return time;
    }

    public int getChildrenCount(int node) {
        int count = 0;
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[node][i] == 1) {
                count++;
            }
        }
        return count;
    }

    public int[] getChildren(int node) {
        int[] children = new int[getChildrenCount(node)];
        int count = 0;
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[node][i] == 1) {
                children[count] = i;
                count++;
            }
        }
        return children;
    }

}

class Utils {

    /**
     * Each element a[i,j] in the resulting is calculated by a vector product of i-th row of matrix A and j-th column of matrix B.
     *
     * The number of columns in the first matrix must be equal to the number of rows in the second matrix.
     */
    public static int[][] matrixMultiply(int[][] a, int[][] b) throws Exception {
        int[][] result = new int[a.length][b[0].length];
        if (a[0].length != b.length) {
            throw new Exception("Invalid matrix dimensions");
        }
        for (int row = 0; row < a.length; row++) {
            for (int col = 0; col < b[0].length; col++) {
                result[row][col] = multiplyMatricesCell(a, b, row, col);
            }
        }
        return result;
    }

    private static int multiplyMatricesCell(int[][] a, int[][] b, int row, int col) {
        int cell = 0;
        for (int i = 0; i < b.length; i++) {
            cell += a[row][i] * b[i][col];
        }
        return cell;
    }

    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            int[] row = matrix[i];
            for (int j = 0; j < matrix[0].length; j++) {
                if (j < matrix[0].length - 1) {
                    System.out.printf("%d ", row[j]);
                } else {
                    System.out.printf("%d", row[j]);
                }
            }
            if (i < matrix.length - 1) {
                System.out.println();
            }
        }
    }

    public static void printArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
            if (i < array.length - 1) {
                System.out.print(" ");
            }
        }
    }
}

class Queue {

    private int[] array;
    private int back;
    private int front;
    private int count;

    public Queue(int size) {
        this.front = 0;
        this.back = 0;
        this.count = 0;
        this.array = new int[size];
    }

    public Queue() {
        this(50);
    }

    public void enqueue(int e) throws Exception {
        if (isFull()) {
            throw new Exception("Queue full");
        }
        array[back] = e;
        back = (back + 1) % capacity();
        count++;
    }

    public int dequeue() throws Exception {
        if (isEmpty()) {
            throw new Exception("Queue empty");
        }
        int e = array[front];
        front = (front + 1) % capacity();
        count--;
        return e;
    }

    public int peek() throws Exception {
        if (isEmpty()) {
            throw new Exception("Queue empty");
        }
        return array[front];
    }

    public int capacity() {
        return array.length;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public boolean isFull() {
        return count == capacity();
    }

    public Queue clone() {
        Queue queue = new Queue(this.capacity());
        queue.array = this.array;
        queue.front = this.front;
        queue.back = this.back;
        queue.count = this.count;
        return queue;
    }

    public String toString() {
        Queue queue = clone();
        String out = "";
        while (!queue.isEmpty()) {
            try {
                int e = queue.dequeue();
                out += e + (queue.isEmpty() ? "" : " ");
            } catch (Exception ignored) {}
        }
        return out;
    }
}