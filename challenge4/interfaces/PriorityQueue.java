package challenge4.interfaces;

interface Queue<T> extends Collection {
    T front() throws CollectionException;

    void enqueue(T x);

    T dequeue() throws CollectionException;
}

public interface PriorityQueue<T extends Comparable<T>> extends Queue<T> {
}
