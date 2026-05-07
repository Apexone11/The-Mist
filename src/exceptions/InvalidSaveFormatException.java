package exceptions;

/**
 * Exception thrown when a save file has an invalid format.
 */
public class InvalidSaveFormatException extends Exception {


    /**
     * Constructs a new InvalidSaveFormatException with no message.
     */
    public InvalidSaveFormatException() {

    }

    /**
     * Constructs a new InvalidSaveFormatException with a specific message.
     * @param message the error message
     */
    public InvalidSaveFormatException(String message) {
        super(message);
    }
}