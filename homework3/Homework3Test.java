package homework3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;

class Homework3Test {

    private OutputStream outputStream;
    private StringInputStream inputStream;

    String getStdout() {
        return outputStream.toString();
    }

    @BeforeEach
    void beforeEach() {
        // store stdout in local buffer
        outputStream = new ByteArrayOutputStream();
        inputStream = new StringInputStream();

        PrintStream stdout = new PrintStream(outputStream);
        System.setOut(stdout);
        System.setIn(inputStream);
    }

    // bellow graph can be visualized here: http://graphonline.ru/en/?graph=aEnwXqkLyJkiHIgN
    String graph = "10\n" +
            "0 1\n" +
            "1 2\n" +
            "1 3\n" +
            "1 4\n" +
            "2 4\n" +
            "2 5\n" +
            "3 0\n" +
            "4 3\n" +
            "4 5\n" +
            "6 7\n" +
            "7 9";

    @Test
    void testUndirectedInfo() throws Exception {
        String out = run("undirected info");
        assertEquals("10 11\n" +
                "0 2\n" +
                "1 4\n" +
                "2 3\n" +
                "3 3\n" +
                "4 4\n" +
                "5 2\n" +
                "6 1\n" +
                "7 2\n" +
                "8 0\n" +
                "9 1", out);
    }

    @Test
    void testWalks() throws Exception {
        String out = run("directed walks 3");
        assertEquals("1 0 0 1 1 2 0 0 0 0\n" +
                "1 1 0 1 0 1 0 0 0 0\n" +
                "1 0 0 0 0 0 0 0 0 0\n" +
                "0 0 1 1 1 0 0 0 0 0\n" +
                "0 1 0 0 0 0 0 0 0 0\n" +
                "0 0 0 0 0 0 0 0 0 0\n" +
                "0 0 0 0 0 0 0 0 0 0\n" +
                "0 0 0 0 0 0 0 0 0 0\n" +
                "0 0 0 0 0 0 0 0 0 0\n" +
                "0 0 0 0 0 0 0 0 0 0", out);
    }

    @Test
    void testBfs() throws Exception {
        String out = run("directed bfs");
        assertEquals("0 1 2 3 4 5 6 7 9 8", out);
    }

    @Test
    void testDfs() throws Exception {
        String out = run("undirected dfs");
        assertEquals(
                "0 1 2 4 3 5 6 7 9 8 \n" +
                        "3 5 4 2 1 0 9 7 6 8", out);
    }

    String run(String command) throws Exception {
        inputStream.append(command + "\n");
        inputStream.append(graph);
        Homework3.main(new String[0]);
        return getStdout();
    }

}

class StringInputStream extends InputStream {
    String value = "";
    int i = -1;

    @Override
    public int read() throws IOException {
        if (this.value.length() == 0) {
            throw new IOException("Read buffer empty");
        }
        i++;
        if (i >= value.length()) {
            return -1;
        } else {
            return value.charAt(i);
        }
    }

    public void append(String value) {
        this.value += value;
    }

    public void set(String value) {
        this.value = value;
    }
}