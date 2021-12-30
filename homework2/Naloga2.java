package homework2;

import java.util.Scanner;

public class Naloga2 {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String[] params = scanner.nextLine().split(" ");
        if (params.length != 3) {
            throw new Exception("Invalid input");
        }
        String command = params[0];
        String sort = params[1];
        boolean ascending = params[2].equals("up");

        Array<Integer> array = readNumberSequence(scanner);

        System.out.println();

        switch (command) {
            case "trace": {
                runTrace(sort, array, ascending);
                break;
            }
            case "count": {
                runCount(sort, array, ascending);
                break;
            }
            default: {
                throw new Exception("Command not supported");
            }
        }
    }

    static void runTrace(String sort, Array<Integer> array, boolean ascending) throws Exception {
        Sort.printTrace(array, -1); // print starting state
        runSort(sort, array, ascending);
    }

    static void runCount(String sort, Array<Integer> array, boolean ascending) throws Exception {
        Sort.loggingForce = false;
        Sort.resetCounts();

        // sort given array
        runSort(sort, array, ascending);
        String sortStats = getSortCounts();
        Sort.resetCounts();

        // sort already sorted array
        runSort(sort, array.clone(), ascending);
        String sortStats2 = getSortCounts();
        Sort.resetCounts();

        // sort already sorted array in reverse direction
        runSort(sort, array.clone(), !ascending);
        String reverseSortStats = getSortCounts();
        Sort.loggingForce = true;

        System.out.printf("%s | %s | %s", sortStats, sortStats2, reverseSortStats);
    }

    static String getSortCounts() {
        return String.format("%d %d", Sort.assignments, Sort.comparisons);
    }

    static void runSort(String sort, Array<Integer> array, boolean ascending) throws Exception {
        switch (sort) {
            case "bubble": {
                Sort.bubblesort(array, ascending);
                break;
            }
            case "insert": {
                Sort.insertionSort(array, ascending);
                break;
            }
            case "select": {
                Sort.selectionSort(array, ascending);
                break;
            }
            case "merge": {
                Sort.mergeSort(array, ascending);
                break;
            }
            case "heap": {
                Sort.heapSort(array, ascending);
                break;
            }
            case "quick": {
                Sort.quickSort(array, ascending);
                break;
            }
            case "radix": {
                Sort.radixSort(array, ascending);
                break;
            }
            default: {
                throw new Exception("Unsupported sort algorithm");
            }
        }
    }

    static Array<Integer> readNumberSequence(Scanner scanner) {
        Array<Integer> sequence = new Array<>();
        String line = scanner.nextLine();
        String[] chars = line.trim().split("[ ]+");
        for (int i = 0; i < chars.length; i++) {
            sequence.set(i, Integer.parseInt(chars[i]));
        }
        return sequence;
    }
}
