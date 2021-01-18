package exception;

public class ManifestationNotFoundException extends RuntimeException {
    public ManifestationNotFoundException(Long id) {
        super(String.format("Manifestation with id %d not found", id));
    }

    public ManifestationNotFoundException(Long id, Throwable cause) {
        super(String.format("Manifestation with id %d not found", id), cause);
    }
}
