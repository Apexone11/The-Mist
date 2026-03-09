package exceptions;

public class SaveNotFoundException extends Exception {

    public SaveNotFoundException() {

    }

    public SaveNotFoundException(String message) {
        super(message);
    }
}