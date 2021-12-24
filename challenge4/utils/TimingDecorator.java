package challenge4.utils;

import challenge4.interfaces.CollectionException;
import challenge4.interfaces.PriorityQueue;

public class TimingDecorator implements PriorityQueue<IntegerDecorator> {

    private final PriorityQueue<IntegerDecorator> target;
    public double executionTime = 0; // ms
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
        this.executionTime = (System.nanoTime() - startTime) / Math.pow(10, 6);
    }

    public double getExecutionTime() {
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
