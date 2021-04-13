package exception;

public class TicketReservedException extends RuntimeException {
    public TicketReservedException(String message) {
        super(message);
    }

    public TicketReservedException(String message, Throwable cause) {
        super(message, cause);
    }

    public TicketReservedException(Long id) {
        super(String.format("Ticket with id %d is already reserved.", id));
    }

    public TicketReservedException(Long id, Throwable cause) {
        super(String.format("Ticket with id %d is already reserved.", id), cause);
    }
}
