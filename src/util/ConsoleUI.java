package util;

public class ConsoleUI {
    private static final int WIDTH = 60;

    public static void printHeader(String title) {
        System.out.println("\n" + "╔" + "═".repeat(WIDTH - 2) + "╗");
        int padding = (WIDTH - 2 - title.length()) / 2;
        String leftPad = " ".repeat(padding);
        String rightPad = " ".repeat(WIDTH - 2 - title.length() - padding);
        System.out.println("║" + leftPad + title.toUpperCase() + rightPad + "║");
        System.out.println("╚" + "═".repeat(WIDTH - 2) + "╝");
    }

    public static void printBox(String text) {
        String[] lines = wrapText(text, WIDTH - 6).split("\n");
        System.out.println("  ┌" + "─".repeat(WIDTH - 4) + "┐");
        for (String line : lines) {
            int padding = WIDTH - 4 - line.length();
            System.out.println("  │ " + line + " ".repeat(padding - 1) + "│");
        }
        System.out.println("  └" + "─".repeat(WIDTH - 4) + "┘");
    }

    public static void printDivider() {
        System.out.println("  " + "┈".repeat(WIDTH - 4));
    }

    private static String wrapText(String text, int limit) {
        StringBuilder sb = new StringBuilder();
        String[] words = text.split(" ");
        int lineLength = 0;
        for (String word : words) {
            if (lineLength + word.length() > limit) {
                sb.append("\n");
                lineLength = 0;
            }
            sb.append(word).append(" ");
            lineLength += word.length() + 1;
        }
        return sb.toString().trim();
    }
}
