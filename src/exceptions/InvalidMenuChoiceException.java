package exceptions;

/**
 * Exception thrown when a user makes an invalid choice in a menu.
 */
public class InvalidMenuChoiceException extends Exception {
    private final String menu;
    private final Integer min;
    private final Integer max;
    private final String input;

    /**
     * Constructs a new InvalidMenuChoiceException with no message or details.
     */
    public InvalidMenuChoiceException() {
        this.menu = null;
        this.min = null;
        this.max = null;
        this.input = null;
    }

    /**
     * Constructs a new InvalidMenuChoiceException with a specific message.
     * @param message the error message
     */
    public InvalidMenuChoiceException(String message) {
        super(message);
        this.menu = null;
        this.min = null;
        this.max = null;
        this.input = null;
    }

    /**
     * Constructs a new InvalidMenuChoiceException with a message and the invalid input.
     * @param message the error message
     * @param input the invalid input string
     */
    public InvalidMenuChoiceException(String message, String input) {
        super(buildInputMessage(message, input));
        this.menu = null;
        this.min = null;
        this.max = null;
        this.input = input;
    }

    /**
     * Constructs a new InvalidMenuChoiceException for a range-based menu choice.
     * @param menu the name of the menu
     * @param min the minimum valid choice
     * @param max the maximum valid choice
     * @param choice the actual invalid choice made
     */
    public InvalidMenuChoiceException(String menu, int min, int max, int choice) {
        super(buildRangeMessage(menu, min, max, choice));
        this.menu = menu;
        this.min = min;
        this.max = max;
        this.input = String.valueOf(choice);
    }

    /**
     * Builds a detailed error message for range-based menu errors.
     * @param menu the menu name
     * @param min the min valid value
     * @param max the max valid value
     * @param choice the invalid choice
     * @return the formatted error message
     */
    private static String buildRangeMessage(String menu, int min, int max, int choice) {
        return "Invalid choice '" + choice + "' for " + menu + ". Valid range: " + min + "-" + max + ".";
    }

    /**
     * Builds a detailed error message including the invalid input.
     * @param message the base error message
     * @param input the invalid input
     * @return the formatted error message
     */
    private static String buildInputMessage(String message, String input) {
        if (input == null || input.trim().isEmpty()) {
            return message + " (input was blank)";
        }
        return message + " (input was: '" + input.trim() + "')";
    }

    /**
     * Gets the name of the menu where the error occurred.
     * @return the menu name
     */
    public String getMenu() {
        return menu;
    }

    /**
     * Gets the minimum valid choice value.
     * @return the min value
     */
    public Integer getMin() {
        return min;
    }

    /**
     * Gets the maximum valid choice value.
     * @return the max value
     */
    public Integer getMax() {
        return max;
    }

    /**
     * Gets the invalid input that caused the exception.
     * @return the input string
     */
    public String getInput() {
        return input;
    }
}
