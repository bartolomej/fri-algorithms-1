package homework2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayTest {

    @Test
    void testToString() {
        Array<Integer> a = new Array<>();
        a.add(1);
        a.add(2);

        assertEquals(2, a.size());
        assertEquals("[1,2]", a.toString());
    }

    @Test
    void testClone() {
        Array<Integer> a = new Array<>(new Integer[]{1,2});
        Array<Integer> b = a.clone();

        assertEquals(a, b);

        b.set(0, 2);
        assertNotEquals(a, b);
    }

    @Test
    void testEquals() {
        Array<Integer> a = new Array<>(new Integer[]{1,2});
        Array<Integer> b = new Array<>(new Integer[]{1,2});
        assertEquals(a, b);
    }
}