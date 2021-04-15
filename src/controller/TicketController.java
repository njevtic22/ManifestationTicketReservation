package controller;

import com.google.gson.Gson;
import filterSearcher.TicketFilterSearcher;
import model.Customer;
import model.ManifestationStatus;
import model.ManifestationType;
import model.Ticket;
import model.TicketStatus;
import model.TicketType;
import model.User;
import org.eclipse.jetty.http.HttpStatus;
import request.AddTicketRequest;
import responseTransformer.GetAllTicketsTransformer;
import responseTransformer.dtoMappers.GetAllTicketsMapper;
import sorter.TicketSorter;
import spark.Request;
import spark.Response;
import spark.Route;
import useCase.ticket.AddTicketUseCase;
import useCase.ticket.DeleteTicketUseCase;
import useCase.ticket.GetAllTicketsUseCase;
import useCase.ticket.ReserveTicketUseCase;
import useCase.ticket.WithdrawTicketUseCase;
import useCase.ticket.command.AddTicketCommand;
import useCase.ticket.command.ReserveTicketCommand;
import useCase.ticket.command.WithdrawTicketCommand;
import utility.PaginatedResponse;
import utility.Pagination;
import utility.RoleEnsure;
import validation.SelfValidating;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.post;

public class TicketController {
    private Gson gson;
    private final SimpleDateFormat formatter;
    private AddTicketUseCase addTicketUseCase;
    private GetAllTicketsUseCase getAllTicketsUseCase;
    private ReserveTicketUseCase reserveTicketUseCase;
    private WithdrawTicketUseCase withdrawTicketUseCase;
    private DeleteTicketUseCase deleteTicketUseCase;
    private final TicketFilterSearcher ticketFilterSearcher;
    private final TicketSorter ticketSorter;
    private Pagination pagination;

    private RoleEnsure ensureUserIsAdmin = AuthenticationController::ensureUserIsAdmin;
    private RoleEnsure ensureUserIsSalesman = AuthenticationController::ensureUserIsSalesman;
    private RoleEnsure ensureUserIsCustomer = AuthenticationController::ensureUserIsCustomer;
    private RoleEnsure ensureUserIsAdminOrSalesman = AuthenticationController::ensureUserIsAdminOrSalesman;
    private RoleEnsure ensureUserIsAdminOrCustomer = AuthenticationController::ensureUserIsAdminOrCustomer;

    public TicketController(
            Gson gson,
            SimpleDateFormat formatter,
            AddTicketUseCase addTicketUseCase,
            GetAllTicketsUseCase getAllTicketsUseCase,
            ReserveTicketUseCase reserveTicketUseCase,
            WithdrawTicketUseCase withdrawTicketUseCase,
            DeleteTicketUseCase deleteTicketUseCase,
            TicketFilterSearcher ticketFilterSearcher,
            TicketSorter ticketSorter,
            Pagination pagination
    ) {
        this.gson = gson;
        this.formatter = formatter;
        this.addTicketUseCase = addTicketUseCase;
        this.getAllTicketsUseCase = getAllTicketsUseCase;
        this.reserveTicketUseCase = reserveTicketUseCase;
        this.withdrawTicketUseCase = withdrawTicketUseCase;
        this.deleteTicketUseCase = deleteTicketUseCase;
        this.ticketFilterSearcher = ticketFilterSearcher;
        this.ticketSorter = ticketSorter;
        this.pagination = pagination;
        this.setUpRoutes();
    }

    private void setUpRoutes() {
        path("api", () -> {
            path("/tickets", () -> {
                post("", add);
                post("/reserve/:id", reserve);
                post("/withdraw/:id", withdraw);
                get("", getAll, new GetAllTicketsTransformer(gson, new GetAllTicketsMapper(formatter)));
                delete("/:id", delete);
            });
        });
    }

    public Route add = (Request request, Response response) -> {
        ensureUserIsSalesman.ensure(request);

        AddTicketRequest requestBody = gson.fromJson(request.body(), AddTicketRequest.class);

        AddTicketCommand command = new AddTicketCommand(
                requestBody.manifestationId,
                requestBody.numberOfRegularTickets,
                requestBody.numberOfFanPitTickets,
                requestBody.numberOfVIPTickets
        );
        addTicketUseCase.addTicket(command);
        response.status(HttpStatus.CREATED_201);

        return HttpStatus.CREATED_201 + " " + HttpStatus.Code.CREATED.getMessage();
    };

    public Route reserve = (Request request, Response response) -> {
        ensureUserIsCustomer.ensure(request);

        Customer customer = request.attribute("user");
        Long id = SelfValidating.validId(request.params(":id"));

        ReserveTicketCommand command = new ReserveTicketCommand(
                id,
                customer.getId()
        );
        reserveTicketUseCase.reserveTicket(command);
        response.status(HttpStatus.OK_200);
        return HttpStatus.OK_200 + " " + HttpStatus.Code.OK.getMessage();
    };

    public Route withdraw = (Request request, Response response) -> {
        ensureUserIsCustomer.ensure(request);

        Customer customer = request.attribute("user");
        Long id = SelfValidating.validId(request.params(":id"));

        WithdrawTicketCommand command = new WithdrawTicketCommand(
                id,
                customer.getId()
        );
        withdrawTicketUseCase.withdrawTicket(command);
        response.status(HttpStatus.OK_200);
        return HttpStatus.OK_200 + " " + HttpStatus.Code.OK.getMessage();
    };

    public Route getAll = (Request request, Response response) -> {
        User user = request.attribute("user");

        List<Ticket> tickets = new ArrayList<>(getAllTicketsUseCase.getAllTickets(user));
        applyFilter(request, tickets);
        applySearch(request, tickets);
        applySort(request, tickets);

        PaginatedResponse<Ticket> paginatedTickets = pagination.paginate(
                tickets,
                request.queryParams("page"),
                request.queryParams("size")
        );

        response.status(HttpStatus.OK_200);
        return paginatedTickets;
    };

    public Route delete = (Request request, Response response) -> {
        ensureUserIsAdmin.ensure(request);

        Long id = SelfValidating.validId(request.params(":id"));
        deleteTicketUseCase.deleteTicket(id);
        response.status(HttpStatus.OK_200);
        return HttpStatus.OK_200 + " " + HttpStatus.Code.OK.getMessage();
    };

    private void applyFilter(Request request, Collection<Ticket> tickets) {
        if (request.queryParams("filterType") != null)
            ticketFilterSearcher.filterByType(TicketType.valueOf(request.queryParams("filterType")), tickets);
        if (request.queryParams("filterTicketStatus") != null)
            ticketFilterSearcher.filterByTicketStatus(TicketStatus.valueOf(request.queryParams("filterTicketStatus")), tickets);
        if (request.queryParams("filterManifestationType") != null)
            ticketFilterSearcher.filterByManifestationType(ManifestationType.valueOf(request.queryParams("filterManifestationType")), tickets);
        if (request.queryParams("filterManifestationStatus") != null)
            ticketFilterSearcher.filterByManifestationStatus(ManifestationStatus.valueOf(request.queryParams("filterManifestationStatus")), tickets);
    }

    private void applySearch(Request request, Collection<Ticket> tickets) throws ParseException {
        if (request.queryParams("searchManifestation") != null)
            ticketFilterSearcher.searchByManifestation(request.queryParams("searchManifestation"), tickets);
        if (request.queryParams("searchPriceFrom") != null)
            ticketFilterSearcher.searchByPriceFrom(Long.parseLong(request.queryParams("searchPriceFrom")), tickets);
        if (request.queryParams("searchPriceTo") != null)
            ticketFilterSearcher.searchByPriceTo(Long.parseLong(request.queryParams("searchPriceTo")), tickets);
        if (request.queryParams("searchDateFrom") != null)
            ticketFilterSearcher.searchByDateFrom(formatter.parse(request.queryParams("searchDateFrom")), tickets);
        if (request.queryParams("searchDateTo") != null)
            ticketFilterSearcher.searchByDateTo(formatter.parse(request.queryParams("searchDateTo")), tickets);
    }

    private void applySort(Request request, List<Ticket> tickets) {
        String sortBy = request.queryParams("sortBy");
        if (sortBy == null)
            return;
        sortBy = sortBy.toLowerCase();

        String sortOrderStr = request.queryParams("sortOrder");
        if (sortOrderStr == null)
            return;

        int sortOrder = sortOrderStr.equals("asc") ? 1 : -1;

        switch (sortBy) {
            case "id":
                ticketSorter.sortById(tickets, sortOrder);
            case "manifestation":
                ticketSorter.sortByManifestation(tickets, sortOrder);
                break;
            case "manifestationdate":
                ticketSorter.sortByManifestationDate(tickets, sortOrder);
                break;
            case "manifestationtype":
                ticketSorter.sortByManifestationType(tickets, sortOrder);
                break;
            case "manifestationstatus":
                ticketSorter.sortByManifestationStatus(tickets, sortOrder);
                break;
            case "price":
                ticketSorter.sortByPrice(tickets, sortOrder);
                break;
            case "status":
                ticketSorter.sortByStatus(tickets, sortOrder);
                break;
            case "type":
                ticketSorter.sortByType(tickets, sortOrder);
                break;
            case "customer":
                ticketSorter.sortByCustomer(tickets, sortOrder);
                break;
        }


    }
}
