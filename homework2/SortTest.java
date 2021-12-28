package homework2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class SortTest {

    Array<Integer> array;

    @BeforeEach
    void beforeEach() {
        /*
         * INITIAL HEAP:
         *      42
         *    17  27
         *  51 29 - -
         */
        array = new Array<>(new Integer[]{42, 17, 27, 51, 29});
    }

    @Test
    void testMaxSiftDown() {
        Array<Integer> a = array.clone();
        Sort.siftDown(a, 1, a.size(), true);
        assertEquals(new Array<>(new Integer[]{42, 51, 27, 17, 29}), a);
    }

    @Test
    void testMinSiftDown() {
        Array<Integer> a = array.clone();
        Sort.siftDown(a, 0, a.size(), false);
        assertEquals(new Array<>(new Integer[]{17, 29, 27, 51, 42}), a);
    }

    @Test
    void testMaxHeapify() {
        /*
         * EXPECTED MAX-HEAP (p=0):
         *      51
         *    42  27
         *  17 29 - -
         */
        Array<Integer> maxHeap = array.clone();
        Sort.heapify(maxHeap, true);
        assertEquals(new Array<>(new Integer[]{51, 42, 27, 17, 29}), maxHeap);
    }

    @Test
    void testMinHeapify() {
        /*
         * EXPECTED MIN-HEAP (p=0):]
         *      17
         *    42  27
         *  51 29 - -
         *
         *      17
         *    29  27
         *  51 42 - -
         */
        Array<Integer> minHeap = array.clone();
        Sort.heapify(minHeap, false);
        assertEquals(new Array<>(new Integer[]{17, 29, 27, 51, 42}), minHeap);
    }

    @Test
    void testAscHeapSort() {
        Array<Integer> a = array.clone();
        Sort.heapSort(a, true);
        assertEquals(new Array<>(new Integer[]{17, 27, 29, 42, 51}), a);
    }

    @Test
    void testDescHeapSort() {
        Array<Integer> a = array.clone();
        Sort.heapSort(a, false);
        assertEquals(new Array<>(new Integer[]{51, 42, 29, 27, 17}), a);
    }
}