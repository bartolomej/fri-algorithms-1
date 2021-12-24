package challenge4.interfaces;

public interface Collection {
    static final String ERR_MSG_EMPTY = "Collection is empty.";

    boolean isEmpty();

    int size();

    String toString();
}