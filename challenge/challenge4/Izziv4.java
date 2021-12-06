package challenge4;

class CollectionException extends Exception {
    public CollectionException(String msg) {
        super(msg);
    }
}

interface Collection {
    static final String ERR_MSG_EMPTY = "Collection is empty.";

    boolean isEmpty();

    int size();

    String toString();
}

interface Queue<T> extends Collection {
    T front() throws CollectionException;

    void enqueue(T x);

    T dequeue() throws CollectionException;
}

interface PriorityQueue<T extends Comparable<T>> extends Queue<T> {
}

interface SequenceAdt<T> {
    T get(int i);

    void set(int i, T x);

    void insert(int i, T x);

    void add(T x);

    void delete(int i);
}

class Sequence<T> implements SequenceAdt<T> {

    public static final int DEFAULT_CAPACITY = 64;
    private int length;
    public Object[] array;

    public Sequence() {
        this(DEFAULT_CAPACITY);
    }

    public Sequence(int maxSize) {
        this.length = 0;
        this.array = new Object[maxSize];
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
        // extend array length if new index larger than current length
        if (i >= this.length - 1) {
            this.length = i + 1;
        }
        this.set(i, x);
    }

    @Override
    public void add(T x) {
        // insert into the next available space
        this.insert(this.length, x);
    }

    @Override
    public void delete(int i) {
        // shorten array length when removing last element
        if (i == this.length - 1) {
            this.length--;
        }
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

// items are stored in unordered array
class ArrayPQ<T extends Comparable<T>> implements PriorityQueue<T> {
    private final Sequence<T> array = new Sequence<>();

    @Override
    public boolean isEmpty() {
        return this.array.size() == 0;
    }

    @Override
    public int size() {
        return this.array.size();
    }

    @Override
    public T front() throws CollectionException {
        int index = this.findNextIndex();
        return this.array.get(index);
    }

    @Override
    public void enqueue(T x) {
        this.array.add(x);
    }

    @Override
    public T dequeue() throws CollectionException {
        int index = this.findNextIndex();
        T e = this.array.get(index);
        this.array.delete(index);
        return e;
    }

    private int findNextIndex() throws CollectionException {
        this.checkEmpty();
        int index = 0;
        for (int i = 0; i < this.array.size(); i++) {
            T e = this.array.get(i);
            if (e != null && e.compareTo(this.array.get(index)) > 0) {
                index = i; // e has a higher priority than max
            }
        }
        return index;
    }

    private void checkEmpty() throws CollectionException {
        if (this.isEmpty()) {
            throw new CollectionException(Collection.ERR_MSG_EMPTY);
        }
    }
}

// implicit max heap with array
class ArrayHeapPQ<T extends Comparable<T>> implements PriorityQueue<T> {

    private final Sequence<T> items = new Sequence<>();
    private int last;

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public T front() throws CollectionException {
        return null;
    }

    @Override
    public void enqueue(T x) {
        this.items.add(x);
        this.siftUp(this.items.size() - 1);
    }

    @Override
    public T dequeue() throws CollectionException {
        T e = this.items.get(0);
        this.items.delete(0);
        this.siftDown(0);
        return e;
    }

    /**
     * DVIGOVANJE ELEMENTA:
     * – skoraj kopica, v kateri le en element
     * – otrok kvari urejenost (glede na starša)
     * – zamenjano ga z njegovim staršem
     * – ponavljamo dokler gre
     */
    private void siftUp(int c) {
        while (c > 0) {
            int p = (c - 1) / 2;
            if (items.get(p).compareTo(items.get(c)) >= 0) {
                break;
            }
            swap(p, c);
            c = p;
        }
    }

    /**
     * UGREZANJE ELEMENTA:
     * - starš na indeksu i kvari urejenost (glede na otroke)
     * – obe poddrevesi na 2i+1 in 2i+2 sta že kopici
     * – zamenjamo ga z večjim (max-kopica) otrokom
     */
    private void siftDown(int p) {
        int c = 2 * p + 1;
        while (c <= last) {
            // primerjavo desni (c+1) in levi (c) element
            if (c + 1 <= last && items.get(c + 1).compareTo(items.get(c)) > 0) {
                c += 1;
            }
            if (items.get(p).compareTo(items.get(c)) >= 0) {
                break;
            }
            swap(p, c);
            p = c;
            c = 2 * p + 1;
        }
    }

    private void swap(int a, int b) {
        T tempB = this.items.get(b);
        this.items.set(b, this.items.get(a));
        this.items.set(a, tempB);
    }
}

class HeapNode<T> {
    T item;
    HeapNode<T> left;
    HeapNode<T> right;
}

// explicit max pseudo-heap with Node objects (represent nodes of a binary tree)
class LinkedHeapPQ<T extends Comparable<T>> implements PriorityQueue<T> {

    private HeapNode<T> root;

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public T front() throws CollectionException {
        return null;
    }

    @Override
    public void enqueue(T x) {

    }

    @Override
    public T dequeue() throws CollectionException {
        return null;
    }

    private void siftUp() {
    }
}

public class Izziv4 {
    private static final int nElements = 1000; // number of elements in priority queue
    private static final int nOperations = 500; // number of dequeue+enqueue+front calls
    private static final ResultTable resultTable = new ResultTable();

    public static void main(String[] args) throws CollectionException {
        resultTable.printTableHeader();
        TimingDecorator arrayPQ = new TimingDecorator("Neurejeno polje", nElements, nOperations, new ArrayPQ<>());
        arrayPQ.testPerformance();
        resultTable.printTiming(arrayPQ);
        TimingDecorator arrayHeapPQ = new TimingDecorator("Implicitna kopica", nElements, nOperations, new ArrayHeapPQ<>());
        arrayHeapPQ.testPerformance();
        resultTable.printTiming(arrayHeapPQ);
    }

}

class ResultTable {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_GREEN = "\u001B[32m";

    private final String headerFormat;
    private final String bodyFormat;

    public ResultTable() {
        int w = 10; // column width
        this.headerFormat = sFormat(20) + sFormat(w) + sFormat(w) + sFormat(w);
        this.bodyFormat = sFormat(20) + dFormat(w) + dFormat(w) + dFormat(w);
    }

    private String sFormat(int width) {
        return "%" + width + "s";
    }

    private String dFormat(int width) {
        return "%" + width + "d";
    }

    void printTableHeader() {
        String out = String.format(this.headerFormat + "\n", "Implementacija", "Cas [ms]", "Premikov", "Primerjav");
        System.out.format(formatWithColor(out, ANSI_BLUE));
    }

    void printTiming(TimingDecorator timing) {
        System.out.format(this.bodyFormat + "\n", timing.label, timing.getExecutionTime(), timing.getTotalRelocations(), timing.getTotalComparisons());
    }

    private static String formatWithColor(String string, String color) {
        return String.format("%s%s%s", color, string, ANSI_RESET);
    }
}

class TimingDecorator implements PriorityQueue<IntegerDecorator> {

    private final PriorityQueue<IntegerDecorator> target;
    public long executionTime = 0;
    private final int nElements;
    private final int nOperations;
    public String label;
    private final IntegerDecorator[] elements;

    public TimingDecorator(String label, int nElements, int nOperations, PriorityQueue<IntegerDecorator> target) {
        this.target = target;
        this.label = label;
        this.nElements = nElements;
        this.nOperations = nOperations;
        this.elements = new IntegerDecorator[nElements + nOperations];
    }

    public void testPerformance() throws CollectionException {
        long startTime = System.nanoTime();
        for (int i = 0; i < nElements; i++) {
            this.elements[i] = new IntegerDecorator();
            target.enqueue(this.elements[i]);
        }
        for (int i = 0; i < nOperations; i++) {
            elements[nElements + i] = new IntegerDecorator();
            target.dequeue();
            target.enqueue(elements[nElements + i]);
            target.front();
        }
        this.executionTime = System.nanoTime() - startTime;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public int getTotalComparisons() {
        int sum = 0;
        for (IntegerDecorator e : elements) {
            sum += e.totalComparisons;
        }
        return sum;
    }

    public int getTotalRelocations() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return target.isEmpty();
    }

    @Override
    public int size() {
        return target.size();
    }

    @Override
    public IntegerDecorator front() throws CollectionException {
        return target.front();
    }

    @Override
    public void enqueue(IntegerDecorator x) {
        target.enqueue(x);
    }

    @Override
    public IntegerDecorator dequeue() throws CollectionException {
        return this.target.dequeue();
    }
}

class IntegerDecorator extends Number implements Comparable<IntegerDecorator> {

    private final Integer target;
    public int totalComparisons = 0;

    public IntegerDecorator() {
        this.target = randomInt();
    }

    @Override
    public int compareTo(IntegerDecorator o) {
        this.totalComparisons++;
        return this.target.compareTo(o.target);
    }

    @Override
    public int intValue() {
        return target;
    }

    @Override
    public long longValue() {
        return target;
    }

    @Override
    public float floatValue() {
        return target;
    }

    @Override
    public double doubleValue() {
        return target;
    }

    static int randomInt() {
        return (int) (Math.random() * 100000);
    }
}