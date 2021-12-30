package homework2;

public class Sort {

    static boolean logging = true;
    static boolean loggingForce = true; // forced logging setting
    public static int assignments = 0;
    public static int comparisons = 0;

    /**
     * Navadno izbiranje.
     * - find the smallest/largest element in unordered part
     * - insert that element to the end of the ordered part
     */
    static void selectionSort(Array<Integer> a, boolean asc) {
        for (int i = 0; i < a.size() - 1; i++) {
            int m = i; // index of smallest/largest element
            for (int j = i + 1; j < a.size(); j++) {
                if (comparison() && (asc ? a.get(j) < a.get(m) : a.get(j) > a.get(m))) {
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
            assignments += 1; // a[i] assigned to k
            int j = i;
            while (j > 0 && comparison() && (asc ? a.get(j - 1) > k : a.get(j - 1) < k)) {
                a.set(j, a.get(j - 1));
                j--;
                assignments += 1; // array assignment
            }
            a.set(j, k);
            assignments += 1; // array value update
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
                if (comparison() && asc ? a.get(i) < a.get(i - 1) : a.get(i) > a.get(i - 1)) {
                    swap(a, i, i - 1);
                    n = i;
                }
            }
            if (n >= 0) {
                printTrace(a, n - 1);
            }
        }
        printTrace(a, a.size() - 2);
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
            printTrace(a, last);
            swap(a, 0, last);
            siftDown(a, 0, last, asc);
            last--;
        }
        printTrace(a, last);
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
        assignments += 6; // array assignments
        return merge(left, right, asc);
    }

    /**
     * Merges arrays a and b, so that the resulting array is sorted.
     */
    static private Array<Integer> merge(Array<Integer> a, Array<Integer> b, boolean asc) {
        Array<Integer> merged = new Array<>();
        int ai = 0, bi = 0;
        while (merged.size() < a.size() + b.size()) {
            if (ai < a.size() && bi < b.size()) {
                if (asc ? a.get(ai) < b.get(bi) : a.get(ai) > b.get(bi)) {
                    merged.add(a.get(ai));
                    ai++;
                } else {
                    merged.add(b.get(bi));
                    bi++;
                }
            } else if (ai < a.size()) {
                merged.add(a.get(ai));
                ai++;
            } else if (bi < b.size()) {
                merged.add(b.get(bi));
                bi++;
            }
        }
        printTrace(merged, -1);
        return merged;
    }

    /**
     * Wrapper method for quick sort recursive method.
     * This is just to keep all sorting methods interface consistent.
     */
    static void quickSort(Array<Integer> a, boolean asc) {
        quickSortRec(a, asc, 0, a.size() - 1);
        printTrace(a, -1);
    }

    /**
     * - choose a pivot element (the leftmost element)
     * - move all elements that are less than pivot on the left side, and all greater elements on the right
     * - repeat this process for the left and right sub-array, until "left" and "right" cursors meet
     */
    private static void quickSortRec(Array<Integer> a, boolean asc, int left, int right) {
        if (left >= right) return;
        int r = partition(a, left, right, asc);
        assignments++;
        printQuickTrace(a, left, right, r);
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
            do l++; while (comparison() && (asc ? a.get(l) < p : a.get(l) > p) && l < right);
            // move right cursor, until you find element lower or equal to pivot
            do r--; while (comparison() && (asc ? a.get(r) > p : a.get(r) < p));
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
            printTrace(a, -1);
            assignments += a.size() * 2;
            comparisons += a.size() * 2;
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
        int max = getMax(a);
        int min = getMin(a);
        double k = Math.floor(a.size() / 2.0);
        double v = (max - min + 1) / k;

        Array<Integer>[] buckets = new Array[(int)k];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new Array<>();
        }

        for (int i = 0; i < a.size(); i++) {
            int ki = (int)Math.floor((a.get(i) - min) / v);
            if (asc) {
                buckets[ki].add(a.get(i));
            } else {
                buckets[buckets.length - ki - 1].add(a.get(i));
            }
        }

        printBuckets(buckets);

        a.clear();
        for (int i = 0; i < buckets.length; i++) {
            a.addAll(buckets[i]);
        }

        insertionSort(a, asc);
    }

    /**
     * Helper method. Finds the minimum value in array.
     */
    static int getMin(Array<Integer> a) {
        int min = a.get(0);
        for (int i = 1; i < a.size(); i++) {
            if (a.get(i) < min) {
                min = a.get(i);
            }
        }
        return min;
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
        if (left < size && comparison() && (maxHeap ? a.get(left) > a.get(largest) : a.get(left) < a.get(largest))) {
            largest = left;
        }
        if (right < size && comparison() && (maxHeap ? a.get(right) > a.get(largest) : a.get(right) < a.get(largest))) {
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
        assignments += 3;
    }

    /**
     * General purpose trace printing method, that prints "|" after the dividerIndex.
     */
    static void printTrace(Array<Integer> array, int dividerIndex) {
        if (!logging || !loggingForce) {
            return;
        }
        for (int i = 0; i < array.size(); i++) {
            System.out.printf("%s%s", array.get(i), i == dividerIndex ? " | " : " ");
        }
        System.out.println();
    }

    /**
     * Specialised trace printing method used for bucket sort.
     */
    static void printBuckets(Array<Integer>[] buckets) {
        if (!logging || !loggingForce) {
            return;
        }
        for (int i = 0; i < buckets.length; i++) {
            for (int j = 0; j < buckets[i].size(); j++) {
                System.out.printf("%d ", buckets[i].get(j));
            }
            System.out.print(i < buckets.length - 1 ? "| " : "");
        }
        System.out.println();
    }

    /**
     * Specialised trace printing method used for bucket sort.
     */
    static void printBucketsTrace(Array<Integer>[] buckets, int dividerIndex) {
        if (!logging || !loggingForce) {
            return;
        }
        for (int i = 0; i < buckets.length; i++) {
            for (int j = 0; j < buckets[i].size(); j++) {
                System.out.printf("%d ", buckets[i].get(j));
            }
            System.out.print(i == dividerIndex ? "| " : "");
        }
        System.out.println();
    }

    /**
     * Specialised printing method, adapted for quick sort trace.
     */
    static void printQuickTrace(Array<Integer> array, int left, int right, int r) {
        if (!logging || !loggingForce) {
            return;
        }
        for (int i = left; i < right + 1; i++) {
            System.out.printf("%s%s%s", i == r ? "| " : "", array.get(i), i == r ? " | " : " ");
        }
        System.out.println();
    }

    /**
     * Helper method, used to increment comparisons count int while loop condition block.
     * Append the method before the desired statement, for example:
     * while (firstCondition() && comparison() && conditionOfInterest())
     *                            ^ here goes the call (doesn't change the expression)
     */
    private static boolean comparison() {
        comparisons++;
        return true;
    }

    static void resetCounts() {
        assignments = 0;
        comparisons = 0;
    }
}