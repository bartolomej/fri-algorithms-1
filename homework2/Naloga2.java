package homework2;

import java.util.Scanner;

public class Naloga2 {
    public static void main(String[] args) throws Exception {
//        Scanner scanner = new Scanner(System.in);
//        String[] params = scanner.nextLine().split(" ");
//        if (params.length != 3) {
//            throw new Exception("Invalid input");
//        }
//        String command = params[0];
//        String sort = params[1];
//        boolean ascending = params[2].equals("up");

        String sort = "merge";
        boolean ascending = true;
        Scanner scanner = new Scanner("42 17 27 51 39");

        Array<Integer> array = readNumberSequence(scanner);

        System.out.println();
        Sort.printTrace(array, -1); // print starting state

        switch (sort) {
            case "bubble": {
                Sort.bubblesort(array, ascending);
                break;
            }
            case "insert": {
                Sort.insertionSort(array, ascending);
                break;
            }
            case "select": {
                Sort.selectionSort(array, ascending);
                break;
            }
            case "merge": {
                Sort.mergeSort(array, ascending);
                break;
            }
            default: {
                throw new Exception("Unsupported sort algorithm");
            }
        }
    }

    static Array<Integer> readNumberSequence(Scanner scanner) {
        Array<Integer> sequence = new Array<>();
        String line = scanner.nextLine();
        String[] chars = line.trim().split("[ ]+");
        for (int i = 0; i < chars.length; i++){
            sequence.insert(i, Integer.parseInt(chars[i]));
        }
        return sequence;
    }
}

class Sort {

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
            int k  = a.get(i);
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

    }

    /**
     *
     */
    static void radixSort(Array<Integer> a, boolean asc) {

    }

    /**
     *
     */
    static void bucketSort(Array<Integer> a, boolean asc) {

    }

    static void swap(Array<Integer> array, int i , int j) {
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

class Array<T> {

    public static final int DEFAULT_CAPACITY = 64;
    private Object[] array;
    private int length;

    public Array() {
        this(DEFAULT_CAPACITY);
    }

    public Array(int maxSize) {
        this.array = new Object[maxSize];
        this.length = 0;
    }

    @SuppressWarnings("unchecked")
    public T get(int i) {
        return (T) this.array[i];
    }

    public void set(int i, T x) {
        this.array[i] = x;
    }

    public void add(T x) {
        this.insert(this.length, x);
    }

    public void insert(int i, T x) {
        while (i > this.array.length - 1) {
            this.resize();
        }
        int l = i + 1;
        if (l > this.length) {
            this.length = l;
        }
        this.set(i, x);
    }

    public void delete(int i) {
        this.array[i] = null;
    }

    public Array<T> subarray(int start, int end) {
        Array<T> a = new Array<>();
        int length = end - start + 1;
        System.arraycopy(this.array, start, a.array, 0, length);
        a.length = length;
        return a;
    }

    public void addAll(Array<T> a) {
        int remainingCapacity = this.array.length - this.length;
        if (remainingCapacity < a.length) {
            this.resize();
        }
        System.arraycopy(a.array, 0, this.array, this.length, a.length);
        this.length += a.length;
    }

    public int size() {
        return this.length;
    }

    private void resize() {
        Object[] newArray = new Object[this.array.length * 2];
        System.arraycopy(this.array, 0, newArray, 0, this.array.length);
        this.array = newArray;
    }
}
