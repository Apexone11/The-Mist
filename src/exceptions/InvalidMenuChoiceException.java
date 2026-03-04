package exceptions;

public class InvalidMenuChoiceException extends IllegalAccessException {

    public InvalidMenuChoiceException(){

    }
    public InvalidMenuChoiceException(String message){
        super(message);
    }
}