package util;

/**
 * Utility class for managing the console user interface.
 * Provides methods for printing headers, boxes, and dividers.
 */
public class ConsoleUI {
    private static final int WIDTH = 60;

    /**
     * Prints a stylized header with the given title.
     * @param title the title to display in the header
     */
    public static void printHeader(String title) {
        System.out.println("\n" + "╔" + "═".repeat(WIDTH - 2) + "╗");
        int padding = (WIDTH - 2 - title.length()) / 2;
        String leftPad = " ".repeat(padding);
        String rightPad = " ".repeat(WIDTH - 2 - title.length() - padding);
        System.out.println("║" + leftPad + title.toUpperCase() + rightPad + "║");
        System.out.println("╚" + "═".repeat(WIDTH - 2) + "╝");
    }

    /**
     * Prints the given text inside a stylized box.
     * @param text the text to display inside the box
     */
    public static void printBox(String text) {
        String[] lines = wrapText(text, WIDTH - 6).split("\n");
        System.out.println("  ┌" + "─".repeat(WIDTH - 4) + "┐");
        for (String line : lines) {
            int padding = WIDTH - 4 - line.length();
            System.out.println("  │ " + line + " ".repeat(padding - 1) + "│");
        }
        System.out.println("  └" + "─".repeat(WIDTH - 4) + "┘");
    }

    /**
     * Prints a stylized divider line.
     */
    public static void printDivider() {
        System.out.println("  " + "┈".repeat(WIDTH - 4));
    }

    /**
     * Wraps text to fit within a specified character limit per line.
     * @param text the text to wrap
     * @param limit the maximum number of characters per line
     * @return the wrapped text string
     */
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
