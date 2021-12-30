package homework2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TestCases {

    private ByteArrayOutputStream outputStream;

    String getStdout() {
        return outputStream.toString();
    }

    @BeforeEach
    void beforeEach() {
        // store stdout in local buffer
        outputStream = new ByteArrayOutputStream();
        PrintStream stdout = new PrintStream(outputStream);
        System.setOut(stdout);
    }

    @Test
    void testMergeCount() throws Exception {
        Array<Integer> a = new Array<>(new Integer[]{42, 17, 27, 51, 39, 11, 90, 42});
        Naloga2.runCount("merge", a, true);
        assertEquals("48 16 | 48 12 | 48 12", getStdout());
    }

    @Test
    void testRadixCount() throws Exception {
        Array<Integer> a = new Array<>(new Integer[]{42, 17, 27, 51, 39, 11, 90, 42});
        Naloga2.runCount("radix", a, true);
        assertEquals("32 32 | 32 32 | 32 32", getStdout());
    }
}
