package challenge4.utils;

public class IntegerDecorator extends Number implements Comparable<IntegerDecorator> {

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
