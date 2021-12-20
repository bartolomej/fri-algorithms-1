package challenge5;

class Person implements Comparable<Person> {
    // class attributes
    String firstName;
    String lastName;
    int birthYear;
    // sort attributes
    static int atr = 0;
    static SortOrder sortOrder = SortOrder.ASCENDING;

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
                        "Person {firstName=%s, lastName=%s, birthYear=%d}",
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
        boolean swapped = true;
        while (swapped) {
            swapped = false;
            for (int i = 1; i < array.length; i++) {
                Comparable a = array[i];
                Comparable b = array[i - 1];
                // A is less than B
                if (a.compareTo(b) < 0) {
                    array[i] = array[i - 1];
                    array[i - 1] = a;
                    swapped = true;
                }
            }
            printTrace(array);
        }
    }

    static private void printTrace(Comparable[] array) {
        for (Comparable e : array) {
            System.out.printf("%s ", e);
        }
        System.out.println();
    }
}

public class Main {

    public static void main(String[] args) {
        Person[] array = generate(10);
        Sort.bubblesort(array);
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