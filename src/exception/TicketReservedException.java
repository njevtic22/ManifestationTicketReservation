package exception;

public class TicketReservedException extends RuntimeException {
    public TicketReservedException(Long id) {
        super(String.format("Ticket with id %d is already reserved.", id));
    }

    public TicketReservedException(Long id, Throwable cause) {
        super(String.format("Ticket with id %d is already reserved.", id), cause);
    }
}
