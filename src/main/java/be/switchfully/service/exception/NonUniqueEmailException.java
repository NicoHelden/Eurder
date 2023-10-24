package be.switchfully.service.exception;

public class NonUniqueEmailException extends RuntimeException {
    public NonUniqueEmailException(String message) { super(message);
    }
}
