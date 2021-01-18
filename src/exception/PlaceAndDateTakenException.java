package exception;

public class PlaceAndDateTakenException extends RuntimeException {
    public PlaceAndDateTakenException() {
        super("There is manifestation with same place and date.");
    }

    public PlaceAndDateTakenException(Throwable cause) {
        super("There is manifestation with same place and date.", cause);
    }
}
