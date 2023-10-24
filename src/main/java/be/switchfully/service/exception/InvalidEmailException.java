package be.switchfully.service.exception;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String message) { super(message);
    }
}
