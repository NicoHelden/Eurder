package be.switchfully.service.exception;

public class UnknownUserException extends RuntimeException{
    public UnknownUserException(String message) {
        super(message);
    }

    public UnknownUserException() {

    }
}
