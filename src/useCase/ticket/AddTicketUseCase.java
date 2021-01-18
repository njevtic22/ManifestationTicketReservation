package useCase.ticket;

import useCase.ticket.command.AddTicketCommand;

public interface AddTicketUseCase {
    void addTicket(AddTicketCommand command);
}
