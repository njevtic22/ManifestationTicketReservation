package useCase.ticket;

import useCase.ticket.command.WithdrawTicketCommand;

public interface WithdrawTicketUseCase {
    void withdrawTicket(WithdrawTicketCommand command);
}
