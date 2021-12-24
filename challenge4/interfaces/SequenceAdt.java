package challenge4.interfaces;

public interface SequenceAdt<T> {
    T get(int i);

    void insert(int i, T x);

    void add(T x);

    void delete(int i);
}