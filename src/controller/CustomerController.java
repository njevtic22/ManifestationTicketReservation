package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Customer;
import org.eclipse.jetty.http.HttpStatus;
import request.AddCustomerRequest;
import request.UpdateCustomerRequest;
import responseTransformer.GetAllCustomersTransformer;
import responseTransformer.GetByIdCustomerTransformer;
import responseTransformer.dtoMappers.GetAllCustomersMapper;
import responseTransformer.dtoMappers.GetByIdCustomerMapper;
import spark.Request;
import spark.Response;
import spark.Route;
import useCase.customer.AddCustomerUseCase;
import useCase.customer.DeleteCustomerUseCase;
import useCase.customer.GetAllCustomersUseCase;
import useCase.customer.GetByIdCustomerUseCase;
import useCase.customer.UpdateCustomerUseCase;
import useCase.customer.command.AddCustomerCommand;
import useCase.customer.command.UpdateCustomerCommand;
import utility.RoleEnsure;
import validation.SelfValidating;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.post;
import static spark.Spark.put;

public class CustomerController {
    private Gson gson;
    private final SimpleDateFormat formatter;
    private AddCustomerUseCase addCustomerUseCase;
    private GetAllCustomersUseCase getAllCustomersUseCase;
    private GetByIdCustomerUseCase getByIdCustomerUseCase;
    private UpdateCustomerUseCase updateCustomerUseCase;
    private DeleteCustomerUseCase deleteCustomerUseCase;

    private RoleEnsure ensureUserIsAdmin = AuthenticationController::ensureUserIsAdmin;
    private RoleEnsure ensureUserIsCustomer = AuthenticationController::ensureUserIsCustomer;
    private RoleEnsure ensureUserIsAdminOrCustomer = AuthenticationController::ensureUserIsAdminOrCustomer;

    public CustomerController(
            Gson gson,
            SimpleDateFormat formatter,
            AddCustomerUseCase addCustomerUseCase,
            GetAllCustomersUseCase getAllCustomersUseCase,
            GetByIdCustomerUseCase getByIdCustomerUseCase,
            UpdateCustomerUseCase updateCustomerUseCase,
            DeleteCustomerUseCase deleteCustomerUseCase
    ) {
        this.gson = gson;
        this.formatter = formatter;
        this.addCustomerUseCase = addCustomerUseCase;
        this.getAllCustomersUseCase = getAllCustomersUseCase;
        this.getByIdCustomerUseCase = getByIdCustomerUseCase;
        this.updateCustomerUseCase = updateCustomerUseCase;
        this.deleteCustomerUseCase = deleteCustomerUseCase;
        this.setUpRoutes();
    }

    private void setUpRoutes() {
        path("api", () -> {
            path("/customers", () -> {
                post("", add);
                get("", getAll, new GetAllCustomersTransformer(gson, new GetAllCustomersMapper(formatter)));
                get("/:id", getById, new GetByIdCustomerTransformer(gson, new GetByIdCustomerMapper(formatter)));
                put("/:id", update);
                delete("/:id", delete);
            });
        });
    }

    public Route add = (Request request, Response response) -> {
        Type requestType = new TypeToken<AddCustomerRequest>(){}.getType();
        AddCustomerRequest requestBody = gson.fromJson(request.body(), requestType);

        AddCustomerCommand command = new AddCustomerCommand(
                requestBody.name,
                requestBody.surname,
                requestBody.username,
                requestBody.password,
                requestBody.dateOfBirth,
                requestBody.gender
        );

        addCustomerUseCase.addCustomer(command);
        response.status(HttpStatus.CREATED_201);

        return HttpStatus.CREATED_201 + " " + HttpStatus.Code.CREATED.getMessage();
    };

    public Route getAll = (Request request, Response response) -> {
        ensureUserIsAdmin.ensure(request);

        response.status(HttpStatus.OK_200);
        return getAllCustomersUseCase.getAllCustomers();
    };

    public Route getById = (Request request, Response response) -> {
        ensureUserIsCustomer.ensure(request);

        Long id = SelfValidating.validId(request.params(":id"));
        Customer customer = getByIdCustomerUseCase.getByIdCustomer(id);
        response.status(HttpStatus.OK_200);
        return customer;
    };

    public Route update = (Request request, Response response) -> {
        ensureUserIsCustomer.ensure(request);

        Type requestType = new TypeToken<UpdateCustomerRequest>() {}.getType();
        UpdateCustomerRequest requestBody = gson.fromJson(request.body(), requestType);

        UpdateCustomerCommand command = new UpdateCustomerCommand(
                SelfValidating.validId(request.params(":id")),
                requestBody.name,
                requestBody.surname,
                requestBody.username,
                requestBody.dateOfBirth,
                requestBody.gender
        );
        updateCustomerUseCase.updateCustomer(command);
        response.status(HttpStatus.OK_200);
        return HttpStatus.OK_200 + " " + HttpStatus.Code.OK.getMessage();
    };

    public Route delete = (Request request, Response response) -> {
        ensureUserIsAdminOrCustomer.ensure(request);

        Long id = SelfValidating.validId(request.params(":id"));
        deleteCustomerUseCase.deleteCustomer(id);
        response.status(HttpStatus.OK_200);
        return HttpStatus.OK_200 + " " + HttpStatus.Code.OK.getMessage();
    };
}
