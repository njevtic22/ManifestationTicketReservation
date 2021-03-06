package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Salesman;
import org.eclipse.jetty.http.HttpStatus;
import request.AddSalesmanRequest;
import request.UpdateSalesmanRequest;
import responseTransformer.GetAllSalesmenTransformer;
import responseTransformer.GetByIdSalesmanTransformer;
import responseTransformer.dtoMappers.GetAllSalesmenMapper;
import responseTransformer.dtoMappers.GetByIdSalesmanMapper;
import spark.Request;
import spark.Response;
import spark.Route;
import useCase.salesman.AddSalesmanUseCase;
import useCase.salesman.DeleteSalesmanUseCase;
import useCase.salesman.GetAllSalesmenUseCase;
import useCase.salesman.GetByIdSalesmanUseCase;
import useCase.salesman.UpdateSalesmanUseCase;
import useCase.salesman.command.AddSalesmanCommand;
import useCase.salesman.command.UpdateSalesmanCommand;
import utility.RoleEnsure;
import validation.SelfValidating;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.post;
import static spark.Spark.put;

public class SalesmanController {
    private Gson gson;
    private final SimpleDateFormat formatter;
    private AddSalesmanUseCase addSalesmanUseCase;
    private GetAllSalesmenUseCase getAllSalesmenUseCase;
    private GetByIdSalesmanUseCase getByIdSalesmanUseCase;
    private UpdateSalesmanUseCase updateSalesmanUseCase;
    private DeleteSalesmanUseCase deleteSalesmanUseCase;

    private RoleEnsure ensureUserIsAdmin = AuthenticationController::ensureUserIsAdmin;
    private RoleEnsure ensureUserIsSalesman = AuthenticationController::ensureUserIsSalesman;
    private RoleEnsure ensureUserIsAdminOrSalesman = AuthenticationController::ensureUserIsAdminOrSalesman;

    public SalesmanController(
            Gson gson,
            SimpleDateFormat formatter,
            AddSalesmanUseCase addSalesmanUseCase,
            GetAllSalesmenUseCase getAllSalesmenUseCase,
            GetByIdSalesmanUseCase getByIdSalesmanUseCase,
            UpdateSalesmanUseCase updateSalesmanUseCase,
            DeleteSalesmanUseCase deleteSalesmanUseCase
    ) {
        this.gson = gson;
        this.formatter = formatter;
        this.addSalesmanUseCase = addSalesmanUseCase;
        this.getAllSalesmenUseCase = getAllSalesmenUseCase;
        this.getByIdSalesmanUseCase = getByIdSalesmanUseCase;
        this.updateSalesmanUseCase = updateSalesmanUseCase;
        this.deleteSalesmanUseCase = deleteSalesmanUseCase;
        this.setUpRoutes();
    }

    private void setUpRoutes() {
        path("api", () -> {
            path("/salesmen", () -> {
                post("", add);
                get("", getAll, new GetAllSalesmenTransformer(gson, new GetAllSalesmenMapper(formatter)));
                get("/:id", getById, new GetByIdSalesmanTransformer(gson, new GetByIdSalesmanMapper(formatter)));
                put("/:id", update);
                delete("/:id", delete);
            });
        });
    }

    public Route add = (Request request, Response response) -> {
        ensureUserIsAdmin.ensure(request);

        Type requestType = new TypeToken<AddSalesmanRequest>(){}.getType();
        AddSalesmanRequest requestBody = gson.fromJson(request.body(), requestType);

        AddSalesmanCommand command = new AddSalesmanCommand(
                requestBody.name,
                requestBody.surname,
                requestBody.username,
                requestBody.password,
                requestBody.dateOfBirth,
                requestBody.gender
        );

        addSalesmanUseCase.addSalesman(command);
        response.status(HttpStatus.CREATED_201);

        return HttpStatus.CREATED_201 + " " + HttpStatus.Code.CREATED.getMessage();
    };

    public Route getAll = (Request request, Response response) -> {
        ensureUserIsAdmin.ensure(request);

        response.status(HttpStatus.OK_200);
        return getAllSalesmenUseCase.getAllSalesmen();
    };

    public Route getById = (Request request, Response response) -> {
        ensureUserIsSalesman.ensure(request);

        Long id = SelfValidating.validId(request.params(":id"));
        Salesman salesman = getByIdSalesmanUseCase.getByIdSalesman(id);
        response.status(HttpStatus.OK_200);
        return salesman;
    };

    public Route update = (Request request, Response response) -> {
        ensureUserIsSalesman.ensure(request);

        Type requestType = new TypeToken<UpdateSalesmanRequest>() {}.getType();
        UpdateSalesmanRequest requestBody = gson.fromJson(request.body(), requestType);

        UpdateSalesmanCommand command = new UpdateSalesmanCommand(
                SelfValidating.validId(request.params(":id")),
                requestBody.name,
                requestBody.surname,
                requestBody.username,
                requestBody.dateOfBirth,
                requestBody.gender
        );
        updateSalesmanUseCase.updateSalesman(command);
        response.status(HttpStatus.OK_200);
        return HttpStatus.OK_200 + " " + HttpStatus.Code.OK.getMessage();
    };

    public Route delete = (Request request, Response response) -> {
        ensureUserIsAdminOrSalesman.ensure(request);

        Long id = SelfValidating.validId(request.params(":id"));
        deleteSalesmanUseCase.deleteSalesman(id);
        response.status(HttpStatus.OK_200);
        return HttpStatus.OK_200 + " " + HttpStatus.Code.OK.getMessage();
    };
}
