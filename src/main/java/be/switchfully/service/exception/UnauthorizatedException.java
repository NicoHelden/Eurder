package be.switchfully.service.exception;

public class UnauthorizatedException extends RuntimeException {
    public UnauthorizatedException(String message) {
        super(message);
    }

    public UnauthorizatedException() {

    }

}
