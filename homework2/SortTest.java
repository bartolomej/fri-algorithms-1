package homework2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class SortTest {

    Array<Integer> array;
    Array<Integer> sortedDesc;
    Array<Integer> sortedAsc;

    @BeforeEach
    void beforeEach() {
        /*
         * INITIAL HEAP:
         *      42
         *    17  27
         *  51 29 - -
         */
        array = new Array<>(new Integer[]{42, 17, 27, 51, 29});
        sortedAsc = new Array<>(new Integer[]{17, 27, 29, 42, 51});
        sortedDesc = new Array<>(new Integer[]{51, 42, 29, 27, 17});
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
        assertEquals(sortedAsc, a);
    }

    @Test
    void testDescHeapSort() {
        Array<Integer> a = array.clone();
        Sort.heapSort(a, false);
        assertEquals(sortedDesc, a);
    }

    @Test
    void testQuicksortPartition1() {
        Array<Integer> a = new Array<>(new Integer[]{6, 12, 3, 6, 1});
        // STACK TRACE:
        // |6|, 12, 3, 6, 1
        // |6|, 1, 3, 6, 12
        // 6, 1, 3, |6|, 12
        Array<Integer> expected = new Array<>(new Integer[]{6, 1, 3, 6, 12});
        int pivot = Sort.partition(a, 0, a.size() - 1, true);
        assertEquals(3, pivot);
        assertEquals(expected, a);
    }

    @Test
    void testQuicksortPartition2() {
        Array<Integer> a = array.clone();
        // STACK TRACE:
        // |42|, 17, 27, 51, 29
        // |42|, 17, 27, 29, 51
        // 29, 17, 27, |41|, 51
        Array<Integer> expected = new Array<>(new Integer[]{29, 17, 27, 42, 51});
        int pivot = Sort.partition(a, 0, a.size() - 1, true);
        assertEquals(3, pivot);
        assertEquals(expected, a);
    }

    @Test
    void testAscQuickSort() {
        Array<Integer> a = array.clone();
        Sort.quickSort(a, true);
        assertEquals(sortedAsc, a);
    }

    @Test
    void testDescQuickSort() {
        Array<Integer> a = array.clone();
        Sort.quickSort(a, false);
        assertEquals(sortedDesc, a);
    }

    @Test
    void testAscRadixSort() {
        Array<Integer> a = array.clone();
        Sort.radixSort(a, true);
        assertEquals(sortedAsc, a);
    }

    @Test
    void testDescRadixSort() {
        Array<Integer> a = array.clone();
        Sort.radixSort(a, false);
        assertEquals(sortedDesc, a);
    }
}