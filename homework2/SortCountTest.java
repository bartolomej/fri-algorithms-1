package homework2;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

public class SortCountTest {

    private Array<Integer> array;
    private PrintStream stdout;
    private ByteArrayOutputStream outputStream;

    String getStdout() {
        return outputStream.toString();
    }

    @BeforeEach
    void beforeEach() {
        // store stdout in local buffer
        outputStream = new ByteArrayOutputStream();
        stdout = new PrintStream(outputStream);
        System.setOut(stdout);
        // example array
        array = new Array<>(new Integer[]{42, 17, 27, 51, 29});
    }

    @Test
    void testInsertionSort() throws Exception {
        Naloga2.runCount("insert", array, true);
        assertEquals("12 7 | 8 4 | 18 10", getStdout());
    }

    @Test
    void testSelectionSort() throws Exception {
        Naloga2.runCount("select", array, false);
        assertEquals("12 10 | 12 10 | 12 10", getStdout());
    }

    @Test
    void testBubbleSort() throws Exception {
        Naloga2.runCount("bubble", array, false);
        assertEquals("18 8 | 0 4 | 30 10", getStdout());
    }

    @Test
    void testHeapSort() throws Exception {
        Naloga2.runCount("heap", array, true);
        assertEquals("24 12 | 30 12 | 24 10", getStdout());
    }

    @Test
    void testMergeSort() throws Exception {
        // TODO: how to calculate comparisons for merge sort ?
        Naloga2.runCount("merge", array, true);
        assertEquals("24 8 | 24 7 | 24 5", getStdout());
    }

    @Test
    void testQuickSort() throws Exception {
        Naloga2.runCount("quick", array, false);
        assertEquals("14 10 | 16 18 | 16 16", getStdout());
    }

    @Test
    void testRadixSort() throws Exception {
        Naloga2.runCount("radix", array, true);
        assertEquals("20 20 | 20 20 | 20 20", getStdout());
    }

    @Test
    void testBucketSort() throws Exception {
        // TODO: how to calculate ?
        Naloga2.runCount("bucket", array, true);
        assertEquals("20 22 | 18 18 | 22 21", getStdout());
    }

}
