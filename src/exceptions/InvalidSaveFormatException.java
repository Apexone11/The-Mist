package exceptions;

public class InvalidSaveFormatException extends Exception {


    public InvalidSaveFormatException() {

    }

    public InvalidSaveFormatException(String message) {
        super(message);
    }
}