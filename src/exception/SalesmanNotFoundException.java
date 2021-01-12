package exception;

public class SalesmanNotFoundException extends RuntimeException {
    public SalesmanNotFoundException(Long id) {
        super(String.format("Salesman with id %d not found.", id));
    }

    public SalesmanNotFoundException(Long id, Throwable cause) {
        super(String.format("Admin with id %d not found.", id), cause);
    }
}
