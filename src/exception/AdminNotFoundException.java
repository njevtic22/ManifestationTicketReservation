package exception;

public class AdminNotFoundException extends RuntimeException {
    public AdminNotFoundException(Long id) {
        super(String.format("Admin with id %d not found.", id));
    }

    public AdminNotFoundException(Long id, Throwable cause) {
        super(String.format("Admin with id %d not found.", id), cause);
    }
}
