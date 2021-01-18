package exception;

public class TicketNotFoundException extends RuntimeException {
    public TicketNotFoundException(Long id) {
        super(String.format("Ticket with id %d not found", id));
    }

    public TicketNotFoundException(Long id, Throwable cause) {
        super(String.format("Ticket with id %d not found", id), cause);
    }
}
