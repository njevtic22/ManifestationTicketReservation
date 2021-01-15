package exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(Long id) {
        super(String.format("Customer with id %d not found.", id));
    }

    public CustomerNotFoundException(Long id, Throwable cause) {
        super(String.format("Customer with id %d not found.", id), cause);
    }
}
