public class Izziv2 {

    public static void main(String[] args) {
        testComplexity();
    }

    static void testComplexity() {
        printTableHeader();
        for (double n = Math.pow(10, 5); n < Math.pow(10, 6); n += Math.pow(10, 4)) {
            long binaryTime = timeBinary((int)n);
            long linearTime = timeLinear((int)n);
            printTableRow((int)n, linearTime, binaryTime);
        }
    }

    static void printTableHeader() {
        System.out.format("%10s%10s%10s\n", "n", "linear", "binary");
    }

    static void printTableRow(int n, long linearTime, long binaryTime) {
        System.out.format("%10d%10d%10d\n", n, linearTime, binaryTime);
    }

    static long timeBinary(int n) {
        long startTime = System.nanoTime();
        int[] array = generateTable(n);
        for (int i = 0; i < 1000; i++) {
            findBinary(array, 0, array.length - 1, randomN(n));
        }
        long executionTime = System.nanoTime() - startTime;
        return executionTime / 1000;
    }

    static long timeLinear(int n) {
        long startTime = System.nanoTime();
        int[] array = generateTable(n);
        for (int i = 0; i < 1000; i++) {
            findLinear(array, randomN(n));
        }
        long executionTime = System.nanoTime() - startTime;
        return executionTime / 1000;
    } 

    static int findLinear(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }
    
    static int findBinary(int[] array, int left, int right, int value) {
        int middleIndex = (int)Math.floor((right + left) / 2);
        int middleValue = array[middleIndex];
        if (left > right) {
            return -1;
        }
        if (middleValue < value) {
            return findBinary(array, middleIndex + 1, right, value);
        }
        if (middleValue > value) {
            return findBinary(array, left, middleIndex - 1, value);
        }
        return middleIndex;
    }

    static int[] generateTable(int n) {
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = i;
        }
        return array;
    }

    static int randomN(int n) {
        return (int)(Math.random() * n);
    }
}
