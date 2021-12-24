package challenge5;

import java.util.Scanner;

class Person implements Comparable<Person> {
    // class attributes
    String firstName;
    String lastName;
    int birthYear;
    // sort attributes
    static int atr = 0;
    static SortOrder sortOrder;

    static {
        sortOrder = SortOrder.DESCENDING;
    }

    Person(String firstName, String lastName, int birthYear) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthYear = birthYear;
    }

    @Override
    public int compareTo(Person o) {
        return sortOrder == SortOrder.ASCENDING
                ? this.toString().compareTo(o.toString())
                : o.toString().compareTo(this.toString());
    }

    @Override
    public String toString() {
        switch (atr) {
            case 0:
                return firstName;
            case 1:
                return lastName;
            case 2:
                return birthYear + "";
            default:
                return String.format(
                        "Person{firstName=%s,lastName=%s,birthYear=%d}",
                        firstName, lastName, birthYear
                );
        }
    }
}

enum SortOrder {
    DESCENDING,
    ASCENDING
}

class Sort {
    static void bubblesort(Comparable[] array) {
        int n = array.length;
        while (n >= 0) {
            int newN = n;
            n = -1;
            for (int i = 1; i < newN; i++) {
                Comparable a = array[i];
                Comparable b = array[i - 1];
                // A is less than B
                if (a.compareTo(b) < 0) {
                    array[i] = array[i - 1];
                    array[i - 1] = a;
                    n = i - 1;
                }
            }
            printTrace(array, n);
        }
    }

    static void printTrace(Comparable[] array, int sortedIndex) {
        for (int i = 0; i < array.length; i++) {
            System.out.printf("%s%s", array[i], i == sortedIndex ? " | " : " ");
        }
        System.out.println();
    }
}

public class Main {

    public static void main(String[] args) {
        int n = promptInt("Vnesi velikost tabele oseb: ");
        Person[] array = generate(n);
        while (true) {
            Person[] tempArray = array.clone();
            Person.atr = -1;
            Sort.printTrace(tempArray, -1);
            int attr = promptInt("Vnesi atribut (0-2): ");
            String sortOrder = promptString("Vnesi vrstni red (ASCENDING/DESCENDING): ");
            Person.atr = attr;
            Person.sortOrder = SortOrder.valueOf(sortOrder);
            Sort.bubblesort(tempArray);
            String repeatChar = promptString("Ponovitev ? (Y/N) ");
            if (repeatChar.equals("N")) {
                break;
            } else {
                System.out.print("\n--------------------------------------\n\n");
            }
        }
    }

    static int promptInt(String msg) {
        System.out.printf("%s", msg);
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    static String promptString(String msg) {
        System.out.printf("%s", msg);
        Scanner sc = new Scanner(System.in);
        return sc.next();
    }

    static Person[] generate(int n) {
        Person[] array = new Person[n];
        for (int i = 0; i < n; i++) {
            array[i] = new Person(randomName(10), randomName(10), randomYear());
        }
        return array;
    }

    static String randomName(int length) {
        String s = "";
        for (int i = 0; i < length; i++) {
            s += (char)(Math.random() * 25 + 65);
        }
        return s;
    }

    static int randomYear() {
        return (int) (Math.random() * 50 + 1950);
    }
}