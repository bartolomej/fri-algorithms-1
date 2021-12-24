package challenge4;

import challenge4.impl.ArrayHeapPQ;
import challenge4.impl.ArrayPQ;
import challenge4.interfaces.CollectionException;
import challenge4.utils.ResultTable;
import challenge4.utils.TimingDecorator;

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