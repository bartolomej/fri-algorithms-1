package challenge4.tests;

import challenge4.impl.ArrayHeapPQ;
import challenge4.interfaces.CollectionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayHeapPQTest {

    @Test
    void enqueueDequeue() throws CollectionException {
        ArrayHeapPQ<Integer> arrayHeapPQ = new ArrayHeapPQ<>();
        assertEquals(arrayHeapPQ.size(), 0);
        arrayHeapPQ.enqueue(10);
        assertEquals(arrayHeapPQ.size(), 1);
        assertEquals(arrayHeapPQ.front(), 10);
        assertEquals(arrayHeapPQ.dequeue(), 10);
        assertEquals(arrayHeapPQ.size(), 0);
    }
}