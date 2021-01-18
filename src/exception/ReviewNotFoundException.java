package exception;

public class ReviewNotFoundException extends RuntimeException {
    public ReviewNotFoundException(Long id) {
        super(String.format("Review with id %d not found.", id));
    }

    public ReviewNotFoundException(Long id, Throwable cause) {
        super(String.format("Review with id %d not found.", id), cause);
    }
}
