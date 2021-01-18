package service;

import exception.CustomerNotFoundException;
import exception.ManifestationNotFoundException;
import exception.TicketNotFoundException;
import exception.TicketReservedException;
import model.Admin;
import model.Customer;
import model.Manifestation;
import model.Salesman;
import model.Ticket;
import model.TicketStatus;
import model.TicketType;
import model.User;
import model.WithdrawalHistory;
import repository.Repository;
import repository.UserRepository;
import useCase.ticket.AddTicketUseCase;
import useCase.ticket.DeleteTicketUseCase;
import useCase.ticket.GetAllTicketsUseCase;
import useCase.ticket.ReserveTicketUseCase;
import useCase.ticket.WithdrawTicketUseCase;
import useCase.ticket.command.AddTicketCommand;
import useCase.ticket.command.ReserveTicketCommand;
import useCase.ticket.command.WithdrawTicketCommand;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class TicketService implements
        AddTicketUseCase,
        GetAllTicketsUseCase,
        ReserveTicketUseCase,
        WithdrawTicketUseCase,
        DeleteTicketUseCase {
    private final SimpleDateFormat formatter;
    private final Repository<Ticket, Long> ticketRepository;
    private final Repository<Manifestation, Long> manifestationRepository;
    private final Repository<WithdrawalHistory, Long> historyRepository;
    private final UserRepository<Customer> customerRepository;

    public TicketService(SimpleDateFormat formatter, Repository<Ticket, Long> ticketRepository, Repository<Manifestation, Long> manifestationRepository, Repository<WithdrawalHistory, Long> historyRepository, UserRepository<Customer> customerRepository) {
        this.formatter = formatter;
        this.ticketRepository = ticketRepository;
        this.manifestationRepository = manifestationRepository;
        this.historyRepository = historyRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void addTicket(AddTicketCommand command) {
        Manifestation manifestation = manifestationRepository.findByIdAndArchivedFalse(command.manifestationId)
                .orElseThrow(() -> new ManifestationNotFoundException(command.manifestationId));

        Ticket ticket = new Ticket(
                manifestation.getRegularTicketPrice(),
                TicketStatus.FREE,
                TicketType.valueOf(command.type),
                false,
                manifestation,
                null
        );

        manifestation.addTicket(ticket);
        manifestation.setNumberOfTicketsLeft(manifestation.getNumberOfTicketsLeft() + 1);

        ticketRepository.save(ticket);
        manifestationRepository.save(manifestation);
    }

    @Override
    public Collection<Ticket> getAllTickets(User user) {
        Collection<Ticket> tickets = new ArrayList<>();

        if (user instanceof Admin)
            tickets = ticketRepository.findAllByArchivedFalse();
        else if (user instanceof Salesman) {
            Salesman salesman = (Salesman) user;
            for (Manifestation salesmanManifestations : salesman.getManifestations()) {
//                 TODO: Change to this if deleting tickets is not implemented
//                tickets.addAll(salesmanManifestations.getTickets());

                for (Ticket salesmanTicket : salesmanManifestations.getTickets()) {
                    if (!salesmanTicket.isArchived()) {
                        tickets.add(salesmanTicket);
                    }
                }
            }
        } else {
            Customer customer = (Customer) user;
            tickets = customer.getTickets();
        }

        return tickets;
    }

    @Override
    public void reserveTicket(ReserveTicketCommand command) {
        Ticket ticket = ticketRepository.findByIdAndArchivedFalse(command.ticketId)
                .orElseThrow(() -> new TicketNotFoundException(command.ticketId));

        Customer customer = customerRepository.findByIdAndArchivedFalse(command.customerId)
                .orElseThrow(() -> new CustomerNotFoundException(command.customerId));

        if (ticket.getStatus() == TicketStatus.RESERVED)
            throw new TicketReservedException(ticket.getId());

        ticket.setStatus(TicketStatus.RESERVED);
        ticket.setPriceDiscount(customer.getType().getDiscount());

        ticket.setCustomer(customer);
        customer.addTicket(ticket);
        double reward = ticket.getPrice() / 1000 * 133;
        customer.addPoints(reward);

        ticketRepository.save(ticket);
        customerRepository.save(customer);
    }

    @Override
    public void withdrawTicket(WithdrawTicketCommand command) {
        Ticket ticket = ticketRepository.findByIdAndArchivedFalse(command.ticketId)
                .orElseThrow(() -> new TicketNotFoundException(command.ticketId));

        Customer customer = customerRepository.findByIdAndArchivedFalse(command.customerId)
                .orElseThrow(() -> new CustomerNotFoundException(command.customerId));

        WithdrawalHistory history = new WithdrawalHistory(
                new Date(),
                ticket,
                false
        );
        historyRepository.save(history);
        customer.addHistory(history);

        double priceToCalculateInFine = ticket.getPrice();

        ticket.setCustomer(null);
        ticket.setStatus(TicketStatus.FREE);
        ticket.setPrice(ticket.getManifestation().getRegularTicketPrice());

        customer.getTickets().remove(ticket);
        double fine = priceToCalculateInFine / 1000 * 133 * 4;
        customer.subtractPoints(fine);

        ticketRepository.save(ticket);
        customerRepository.save(customer);
    }

    @Override
    public void deleteTicket(Long id) {
        Ticket ticket = ticketRepository.findByIdAndArchivedFalse(id)
                .orElseThrow(() -> new TicketNotFoundException(id));

        ticket.archive();

        ticketRepository.save(ticket);
    }
}
