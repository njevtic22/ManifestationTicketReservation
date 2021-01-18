package controller;

import com.google.gson.Gson;
import model.Customer;
import model.User;
import org.eclipse.jetty.http.HttpStatus;
import request.AddTicketRequest;
import responseTransformer.GetAllTicketsTransformer;
import responseTransformer.dtoMappers.GetAllTicketsMapper;
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
import utility.RoleEnsure;
import validation.SelfValidating;

import java.text.SimpleDateFormat;

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

    private RoleEnsure ensureUserIsAdmin = AuthenticationController::ensureUserIsAdmin;
    private RoleEnsure ensureUserIsSalesman = AuthenticationController::ensureUserIsSalesman;
    private RoleEnsure ensureUserIsCustomer = AuthenticationController::ensureUserIsCustomer;
    private RoleEnsure ensureUserIsAdminOrSalesman = AuthenticationController::ensureUserIsAdminOrSalesman;
    private RoleEnsure ensureUserIsAdminOrCustomer = AuthenticationController::ensureUserIsAdminOrCustomer;

    public TicketController(Gson gson, SimpleDateFormat formatter, AddTicketUseCase addTicketUseCase, GetAllTicketsUseCase getAllTicketsUseCase, ReserveTicketUseCase reserveTicketUseCase, WithdrawTicketUseCase withdrawTicketUseCase, DeleteTicketUseCase deleteTicketUseCase) {
        this.gson = gson;
        this.formatter = formatter;
        this.addTicketUseCase = addTicketUseCase;
        this.getAllTicketsUseCase = getAllTicketsUseCase;
        this.reserveTicketUseCase = reserveTicketUseCase;
        this.withdrawTicketUseCase = withdrawTicketUseCase;
        this.deleteTicketUseCase = deleteTicketUseCase;
        this.setUpRoutes();
    }

    private void setUpRoutes() {
        path("api", () -> {
            path("/tickets", () -> {
                post("", add);
                post("/:id", reserve);
                post("/:id", withdraw);
                get("", getAll, new GetAllTicketsTransformer(gson, new GetAllTicketsMapper(formatter)));
                delete("/:id", delete);
            });
        });
    }

    public Route add = (Request request, Response response) -> {
        ensureUserIsSalesman.ensure(request);

        AddTicketRequest requestBody = gson.fromJson(request.body(), AddTicketRequest.class);

        AddTicketCommand command = new AddTicketCommand(
                requestBody.type,
                requestBody.manifestationId
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
        response.status(HttpStatus.OK_200);
        return getAllTicketsUseCase.getAllTickets(user);
    };

    public Route delete = (Request request, Response response) -> {
        ensureUserIsSalesman.ensure(request);

        Long id = SelfValidating.validId(request.params(":id"));
        deleteTicketUseCase.deleteTicket(id);
        response.status(HttpStatus.OK_200);
        return HttpStatus.OK_200 + " " + HttpStatus.Code.OK.getMessage();
    };
}
