package exceptions;

/**
 * Exception thrown when a requested save file cannot be found.
 */
public class SaveNotFoundException extends Exception {

    /**
     * Constructs a new SaveNotFoundException with no message.
     */
    public SaveNotFoundException() {

    }

    /**
     * Constructs a new SaveNotFoundException with a specific message.
     * @param message the error message
     */
    public SaveNotFoundException(String message) {
        super(message);
    }
}