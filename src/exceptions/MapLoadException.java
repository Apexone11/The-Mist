package exceptions;

/**
 * Exception thrown when there is an error loading the game map.
 */
public class MapLoadException extends Exception {

    /**
     * Constructs a new MapLoadException with no message.
     */
    public MapLoadException() {
    }

    /**
     * Constructs a new MapLoadException with a specific message.
     * @param message the error message
     */
    public MapLoadException(String message) {
        super(message);
    }
}