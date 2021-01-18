package useCase.ticket;

import useCase.ticket.command.ReserveTicketCommand;

public interface ReserveTicketUseCase {
    void reserveTicket(ReserveTicketCommand command);
}
