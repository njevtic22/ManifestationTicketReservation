package useCase.ticket;

import model.Ticket;
import model.User;

import java.util.Collection;

public interface GetAllTicketsUseCase {
    Collection<Ticket> getAllTickets(User user);
}
