package exceptions;

public class InvalidMenuChoiceException extends Exception {
    private final String menu;
    private final Integer min;
    private final Integer max;
    private final String input;

    public InvalidMenuChoiceException() {
        this.menu = null;
        this.min = null;
        this.max = null;
        this.input = null;
    }

    public InvalidMenuChoiceException(String message) {
        super(message);
        this.menu = null;
        this.min = null;
        this.max = null;
        this.input = null;
    }

    public InvalidMenuChoiceException(String message, String input) {
        super(buildInputMessage(message, input));
        this.menu = null;
        this.min = null;
        this.max = null;
        this.input = input;
    }

    public InvalidMenuChoiceException(String menu, int min, int max, int choice) {
        super(buildRangeMessage(menu, min, max, choice));
        this.menu = menu;
        this.min = min;
        this.max = max;
        this.input = String.valueOf(choice);
    }

    private static String buildRangeMessage(String menu, int min, int max, int choice) {
        return "Invalid choice '" + choice + "' for " + menu + ". Valid range: " + min + "-" + max + ".";
    }

    private static String buildInputMessage(String message, String input) {
        if (input == null || input.trim().isEmpty()) {
            return message + " (input was blank)";
        }
        return message + " (input was: '" + input.trim() + "')";
    }

    public String getMenu() {
        return menu;
    }

    public Integer getMin() {
        return min;
    }

    public Integer getMax() {
        return max;
    }

    public String getInput() {
        return input;
    }
}
