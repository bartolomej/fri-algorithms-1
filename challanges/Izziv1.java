import java.lang.reflect.Array;

class CollectionException extends Exception {
    public CollectionException(String msg) {
         super(msg);
    }
}


interface Collection {
    static final String ERR_MSG_EMPTY = "Collection is empty.";
    static final String ERR_MSG_FULL = "Collection is full.";

    boolean isEmpty();
    boolean isFull();
    int size();
    String toString();
}


interface Stack<T> extends Collection {
    T top() throws CollectionException;
    void push(T x) throws CollectionException;
    T pop() throws CollectionException;
}


interface Deque<T> extends Collection {
    T front() throws CollectionException;
    T back() throws CollectionException;
    void enqueue(T x) throws CollectionException;
    void enqueueFront(T x) throws CollectionException;
    T dequeue() throws CollectionException;
    T dequeueBack() throws CollectionException;
}


interface Sequence<T> extends Collection {
    static final String ERR_MSG_INDEX = "Wrong index in sequence.";
    T get(int i) throws CollectionException;
    void add(T x) throws CollectionException;
}

class ArrayDeque<T> implements Deque<T>, Stack<T>, Sequence<T> {
    private static final int DEFAULT_CAPACITY = 64;
    public Object[] array;
    public int length;

    public ArrayDeque() {
        this.array = this.newArray(DEFAULT_CAPACITY);
        this.length = 0;
    }

    public ArrayDeque(int length) {
        this.array = this.newArray(length);
        this.length = 0;
    }

    private Object[] newArray(int length) {
        return new Object[length];
    }

    @SuppressWarnings("unchecked")
    T arrayData(int index) {
        return (T) array[index];
    }

    private void resize() {
        Object[] newArray = this.newArray(length * 2);
        for (int i = 0; i < this.array.length; i++) {
            newArray[i] = this.array[i];
        }
        this.array = newArray;
    }

    @Override
    public boolean isEmpty() {
        return this.length == 0;
    }

    @Override
    public boolean isFull() {
        return this.length == this.array.length;
    }

    @Override
    public int size() {
        return this.length;
    }

    @Override
    public T get(int i) throws CollectionException {
        if (i < 0 || i >= this.size()) {
            throw new CollectionException(ERR_MSG_INDEX);
        }
        return this.arrayData(i);
    }

    @Override
    public void add(T x) throws CollectionException {
        if (this.length >= this.array.length) {
            this.resize();;
        }
        this.array[this.length] = x;
        this.length++;
    }

    @Override
    public T top() throws CollectionException {
        return this.arrayData(this.length - 1);
    }

    @Override
    public void push(T x) throws CollectionException {
        this.add(x);
    }

    @Override
    public T pop() throws CollectionException {
        if (this.length == 0) {
            throw new CollectionException(ERR_MSG_EMPTY);
        }
        this.length--;
        return this.arrayData(this.length);
    }

    @Override
    public T front() throws CollectionException {
        return this.arrayData(0);
    }

    @Override
    public T back() throws CollectionException {
        return this.arrayData(this.length - 1);
    }

    @Override
    public void enqueue(T x) throws CollectionException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void enqueueFront(T x) throws CollectionException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public T dequeue() throws CollectionException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public T dequeueBack() throws CollectionException {
        // TODO Auto-generated method stub
        return null;
    }

}

public class Izziv1 {

    public static void main(String[] args) throws CollectionException {
        ArrayDeque<Number> a = new ArrayDeque<Number>(2);
        ArrayDeque<String> b = new ArrayDeque<String>();
        b.add("Test");
        assertEquals(b.top(), "Test", "ArrayDeque<String> works correctly");

        assertTrue(a.isEmpty(), "Initial ArrayDequeue is empty");
        assertFalse(a.isFull(), "Initial ArrayDequeue is not full");
    
        assertEquals(a.array.length, 2, "Internal array structure initialised correctly");
        try {
            a.get(1);
        } catch (CollectionException e) {
            assertEquals(e.getMessage(), Sequence.ERR_MSG_INDEX, "ERR_MSG_INDEX exception thrown correctly");
        }

        a.add(1);
        a.add(2);
        a.push(3);

        assertEquals(a.get(1), 2, "get() returns correct element");
        assertEquals(a.size(), 3, "size() correct when adding items");
        assertEquals(a.array.length, 4, "Internal array structure resized correctly");

        assertEquals(a.top(), 3, "top() returns the top element");

        assertEquals(a.pop(), 3, "pop() returns the correct element");
        assertEquals(a.top(), 2, "top() returns the correct element after pop() call");
        assertEquals(a.size(), 2, "size() is correct after pop() call");

        assertEquals(a.front(), 1, "front() returns the first element");
        assertEquals(a.back(), 2, "back() returns the last element");
    }

    static private void assertEquals(String actual, String expected, String message) {
        assertTrue(actual.equals(expected), message);
    }

    static private void assertEquals(Number actual, Number expected, String message) {
        assertTrue(actual == expected, message);
    }

    static private void assertFalse(boolean actual, String message) {
        assertTrue(!actual, message);
    }

    static private void assertTrue(boolean actual, String message) {
        if (actual) {
            System.out.println("✅ Passed: " + message);
        } else {
            System.out.println("❌ Assertion failed: " + message);
        }
    }
}