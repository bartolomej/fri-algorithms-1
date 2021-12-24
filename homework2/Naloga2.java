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

        for (int i = 0; i < array.size(); i++) {
            System.out.println(array.get(i) + "");
        }

        switch (sort) {
            case "bubble": {
                Sort.bubblesort(array);
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
    static void bubblesort(Sequence<Integer> array) {
        int n = array.size();
        while (n >= 0) {
            int newN = n;
            n = -1;
            for (int i = 1; i < newN; i++) {
                Integer a = array.get(i);
                Integer b = array.get(i - 1);
                // A is less than B
                if (a.compareTo(b) < 0) {
                    array.set(i, array.get(i - 1));
                    array.set(i - 1, a);
                    n = i - 1;
                }
            }
            printTrace(array, n);
        }
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
        if (i > this.length) {
            this.length = i;
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
