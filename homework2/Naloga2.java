package homework2;

import java.util.Arrays;
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
        Sort.printTrace(array, -1); // print starting state

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
