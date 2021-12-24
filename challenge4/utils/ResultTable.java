package challenge4.utils;


public class ResultTable {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_GREEN = "\u001B[32m";

    private final String headerFormat;
    private final String bodyFormat;

    public ResultTable() {
        int w = 20; // column width
        this.headerFormat = sFormat(w) + sFormat(w) + sFormat(w) + sFormat(w);
        this.bodyFormat = sFormat(w) + fFormat(w) + dFormat(w) + dFormat(w);
    }

    private String sFormat(int width) {
        return "%" + width + "s";
    }

    private String dFormat(int width) {
        return "%" + width + "d";
    }

    private String fFormat(int width) {
        return "%" + width + "f";
    }

    public void printTableHeader() {
        String out = String.format(this.headerFormat + "\n", "Implementacija", "Cas [ms]", "Premikov", "Primerjav");
        System.out.format(formatWithColor(out, ANSI_BLUE));
    }

    public void printTiming(TimingDecorator timing) {
        System.out.format(this.bodyFormat + "\n", timing.label, timing.getExecutionTime(), timing.getTotalRelocations(), timing.getTotalComparisons());
    }

    private static String formatWithColor(String string, String color) {
        return String.format("%s%s%s", color, string, ANSI_RESET);
    }
}
