package rus.cheremisin.itktasksspringmvc.exception;

public class NoCustomerFoundException extends RuntimeException {
    public NoCustomerFoundException(String message) {
        super(message);
    }
}
