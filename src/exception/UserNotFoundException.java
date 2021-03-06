package exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super(String.format("User with id %d not found.", id));
    }

    public UserNotFoundException(Long id, Throwable cause) {
        super(String.format("User with id %d not found.", id), cause);
    }
}
