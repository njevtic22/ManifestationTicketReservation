package exception;

public class SoldOutException extends RuntimeException {
    public SoldOutException(String manifestationName) {
        super(String.format("Manifestation with name %s is sold out", manifestationName));
    }

    public SoldOutException(String manifestationName, Throwable cause) {
        super(String.format("Manifestation with name %s is sold out", manifestationName), cause);
    }
}
