package homework2;

import java.util.Arrays;

public class Sort {

    private static boolean log = true;

    /**
     * Navadno izbiranje.
     * - find the smallest/largest element in unordered part
     * - insert that element to the end of the ordered part
     */
    static void selectionSort(Array<Integer> a, boolean asc) {
        for (int i = 0; i < a.size() - 1; i++) {
            int m = i + 1; // index of smallest/largest element
            for (int j = i; j < a.size(); j++) {
                if (asc ? a.get(j) < a.get(m) : a.get(j) > a.get(m)) {
                    m = j;
                }
            }
            swap(a, i, m);
            printTrace(a, i);
        }
    }

    /**
     * Navadno vstavljanje.
     * - take the first element from unordered part
     * - insert that element at the valid index in ordered part
     */
    static void insertionSort(Array<Integer> a, boolean asc) {
        for (int i = 1; i < a.size(); i++) {
            int k = a.get(i);
            int j = i;
            while (j > 0 && (asc ? a.get(j - 1) > k : a.get(j - 1) < k)) {
                a.set(j, a.get(j - 1));
                j--;
            }
            a.set(j, k);
            printTrace(a, i);
        }
    }

    /**
     * Urejanje z zamenjavami.
     * - compare neighbouring elements
     * - switch them if not in correct order
     */
    static void bubblesort(Array<Integer> a, boolean asc) {
        int n = 0;
        while (n >= 0) {
            int newN = n;
            n = -1;
            for (int i = a.size() - 1; i > newN; i--) {
                if (asc ? a.get(i) < a.get(i - 1) : a.get(i) > a.get(i - 1)) {
                    swap(a, i, i - 1);
                    n = i;
                }
            }
            if (n >= 0) {
                printTrace(a, n - 1);
            }
        }
    }

    /**
     * - first part of array is a heap with largest/smallest element as root
     * - second part is an ordered list
     * - swap the root heap element (index 0) with the last element in heap part
     * - heap is unordered - sift down the root element
     */
    static void heapSort(Array<Integer> a, boolean asc) {
        heapify(a, asc);

        int last = a.size() - 1;
        while (last >= 1) {
            swap(a, 0, last);
            siftDown(a, 0, last, asc);
            last--;
        }
    }

    /**
     * - divide the table into 2 equal parts
     * - recursively order both sub arrays
     * - join both ordered sub arrays
     */
    static Array<Integer> mergeSort(Array<Integer> a, boolean asc) {
        if (a.size() <= 1) {
            return a;
        }
        int middle = (a.size() - 1) / 2;
        printTrace(a, middle);
        Array<Integer> left = mergeSort(a.subarray(0, middle), asc);
        Array<Integer> right = mergeSort(a.subarray(middle + 1, a.size() - 1), asc);

        return merge(left, right, asc);
    }

    /**
     * Merges arrays a and b, so that the resulting array is sorted.
     */
    static private Array<Integer> merge(Array<Integer> a, Array<Integer> b, boolean asc) {
        Array<Integer> joined = new Array<>(a.size() + b.size());
        joined.addAll(a);
        joined.addAll(b);
        Sort.log = false;
        insertionSort(joined, asc);
        Sort.log = true;
        printTrace(joined, -1);
        return joined;
    }

    /**
     *
     */
    static void quickSort(Array<Integer> a, boolean asc) {
        quickSortRec(a, asc, 0, a.size() - 1);
    }

    private static void quickSortRec(Array<Integer> a, boolean asc, int left, int right) {
        if (left >= right) return;
        int r = partition(a, left, right, asc);
        quickSortRec(a, asc, left, r - 1);
        quickSortRec(a, asc, r + 1, right);
    }

    /**
     * Pick a pivot (leftmost element) and partition a table,
     * so that all the elements greater (or equal) than pivot are placed on the right side of the pivot,
     * and all the elements smaller (or equal) than pivot are placed on the left side of the pivot.
     * <p>
     * EXAMPLE
     * initial array: 6 12 3 6 1
     * pivot: 6 (index=0)
     * partition:
     */
    static int partition(Array<Integer> a, int left, int right, boolean asc) {
        int p = a.get(left);
        int l = left;
        int r = right + 1;

        while (true) {
            // move left cursor, until you find element greater or equal to pivot
            do l++; while ((asc ? a.get(l) < p : a.get(l) > p) && l < r);
            // move right cursor, until you find element lower or equal to pivot
            do r--; while ((asc ? a.get(r) > p : a.get(r) < p));
            // if cursors cross, stop
            if (l >= r) break;
            // swap out of place elements
            swap(a, l, r);
        }

        swap(a, left, r);
        return r;
    }

    /**
     * It avoids comparison by creating and distributing elements into buckets according to their radix (base).
     * For numeric elements with more than one digit, the "bucketing" process is repeated for each digit (with stable sort).
     */
    static void radixSort(Array<Integer> a, boolean asc) {
        int max = getMax(a);

        for (int exp = 1; max / exp > 0; exp *= 10) {
            countSort(a, asc, exp);
        }
    }

    /**
     *
     */
    static void countSort(Array<Integer> a, boolean asc, int exp) {
        int numberBase = 10;
        int n = a.size();
        int i = 0;

        Array<Integer> output = new Array<>(a.size());
        Array<Integer> count = new Array<>(numberBase);
        count.fill(0);

        // Store count of occurrences in count[]
        for (i = 0; i < n; i++) {
            int p = (a.get(i) / exp) % 10;
            count.set(p, count.get(p) + 1);
        }

        // Change count[i] so that count[i] now contains
        // actual position of this digit in output[]
        for (i = 1; i < count.size(); i++) {
            count.set(i, count.get(i) + count.get(i - 1));
        }

        // Build the output array
        i = asc ? n - 1 : 0;
        while (asc ? i >= 0 : i < n) {
            int p = (a.get(i) / exp) % 10;
            count.set(p, count.get(p) - 1);
            if (asc) {
                output.set(count.get(p), a.get(i));
                i--;
            } else {
                output.set(n - count.get(p) - 1, a.get(i));
                i++;
            }
        }

        // Copy the output array to arr[], so that arr[] now
        // contains sorted numbers according to current digit
        for (i = 0; i < n; i++) {
            a.set(i, output.get(i));
        }
    }

    /**
     *
     */
    static void bucketSort(Array<Integer> a, boolean asc) {

    }

    /**
     * Helper method. Finds the maximum value in array.
     */
    static int getMax(Array<Integer> a) {
        int max = a.get(0);
        for (int i = 1; i < a.size(); i++) {
            if (a.get(i) > max) {
                max = a.get(i);
            }
        }
        return max;
    }

    /**
     * Restore the heap property by comparing and possibly swapping a node with one of its children.
     */
    static void heapify(Array<Integer> a, boolean maxHeap) {
        int h = a.size() / 2 - 1; // location of last node before leaf nodes
        for (int i = h; i >= 0; i--) {
            siftDown(a, i, a.size(), maxHeap);
        }
    }

    static void siftDown(Array<Integer> a, int p, int size, boolean maxHeap) {
        int left = 2 * p + 1;
        int right = left + 1;
        int largest = p;

        // set the largest element to be the largest of the children elements
        if (left < size && (maxHeap ? a.get(left) > a.get(largest) : a.get(left) < a.get(largest))) {
            largest = left;
        }
        if (right < size && (maxHeap ? a.get(right) > a.get(largest) : a.get(right) < a.get(largest))) {
            largest = right;
        }

        // if the largest element is the parent itself, stop
        if (largest != p) {
            swap(a, p, largest);
            siftDown(a, largest, size, maxHeap);
        }
    }

    static void swap(Array<Integer> array, int i, int j) {
        int a = array.get(i);
        array.set(i, array.get(j));
        array.set(j, a);
    }

    static void printTrace(Array<Integer> array, int dividerIndex) {
        if (!Sort.log) {
            return;
        }
        for (int i = 0; i < array.size(); i++) {
            System.out.printf("%s%s", array.get(i), i == dividerIndex ? " | " : " ");
        }
        System.out.println();
    }
}