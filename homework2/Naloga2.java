package homework2;

import java.util.Scanner;

public class Naloga2 {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String[] params = scanner.nextLine().split(" ");
        if (params.length != 3) {
            throw new Exception("Invalid input");
        }
        String command = params[0];
        String sort = params[1];
        boolean ascending = params[2].equals("up");

        Sequence<Integer> array = readNumberSequence(scanner);

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
            default: {
                throw new Exception("Unsupported sort algorithm");
            }
        }
    }

    static Sequence<Integer> readNumberSequence(Scanner scanner) {
        Sequence<Integer> sequence = new Sequence<>();
        String line = scanner.nextLine();
        String[] chars = line.trim().split("[ ]+");
        for (int i = 0; i < chars.length; i++){
            sequence.insert(i, Integer.parseInt(chars[i]));
        }
        return sequence;
    }
}

class Sort {

    /**
     * Navadno izbiranje.
     * - find the smallest/largest element in unordered part
     * - insert that element to the end of the ordered part
     */
    static void selectionSort(Sequence<Integer> a, boolean asc) {
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
    static void insertionSort(Sequence<Integer> a, boolean asc) {
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
    static void bubblesort(Sequence<Integer> a, boolean asc) {
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

    static void swap(Sequence<Integer> array, int i , int j) {
        int a = array.get(i);
        array.set(i, array.get(j));
        array.set(j, a);
    }

    static void printTrace(Sequence<Integer> array, int sortedIndex) {
        for (int i = 0; i < array.size(); i++) {
            System.out.printf("%s%s", array.get(i), i == sortedIndex ? " | " : " ");
        }
        System.out.println();
    }
}

interface SequenceAdt<T> {
    T get(int i);
    void set(int i, T x);
    void insert(int i, T x);
    void delete(int i);
}

class Sequence<T> implements SequenceAdt<T> {

    public static final int DEFAULT_CAPACITY = 64;
    public Object[] array;
    private int length;

    public Sequence() {
        this(DEFAULT_CAPACITY);
    }

    public Sequence(int maxSize) {
        this.array = new Object[maxSize];
        this.length = 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(int i) {
        return (T) this.array[i];
    }

    @Override
    public void set(int i, T x) {
        this.array[i] = x;
    }

    @Override
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

    @Override
    public void delete(int i) {
        this.array[i] = null;
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
