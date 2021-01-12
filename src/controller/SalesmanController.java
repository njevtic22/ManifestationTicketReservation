package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Salesman;
import org.eclipse.jetty.http.HttpStatus;
import request.AddSalesmanRequest;
import request.UpdatePasswordRequest;
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
import useCase.salesman.UpdateSalesmanPasswordUseCase;
import useCase.salesman.UpdateSalesmanUseCase;
import useCase.salesman.command.AddSalesmanCommand;
import useCase.salesman.command.UpdateSalesmanCommand;
import useCase.salesman.command.UpdateSalesmanPasswordCommand;
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
    private UpdateSalesmanPasswordUseCase updateSalesmanPasswordUseCase;
    private DeleteSalesmanUseCase deleteSalesmanUseCase;

    public SalesmanController(Gson gson, SimpleDateFormat formatter, AddSalesmanUseCase addSalesmanUseCase, GetAllSalesmenUseCase getAllSalesmenUseCase, GetByIdSalesmanUseCase getByIdSalesmanUseCase, UpdateSalesmanUseCase updateSalesmanUseCase, UpdateSalesmanPasswordUseCase updateSalesmanPasswordUseCase, DeleteSalesmanUseCase deleteSalesmanUseCase) {
        this.gson = gson;
        this.formatter = formatter;
        this.addSalesmanUseCase = addSalesmanUseCase;
        this.getAllSalesmenUseCase = getAllSalesmenUseCase;
        this.getByIdSalesmanUseCase = getByIdSalesmanUseCase;
        this.updateSalesmanUseCase = updateSalesmanUseCase;
        this.updateSalesmanPasswordUseCase = updateSalesmanPasswordUseCase;
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
                put("/:id/password", updatePassword);
                delete("/:id", delete);
            });
        });
    }

    public Route add = (Request request, Response response) -> {
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
        response.status(HttpStatus.OK_200);
        return getAllSalesmenUseCase.getAllSalesmen();
    };

    public Route getById = (Request request, Response response) -> {
        Long id = SelfValidating.validId(request.params(":id"));
        Salesman salesman = getByIdSalesmanUseCase.getByIdSalesman(id);
        response.status(HttpStatus.OK_200);
        return salesman;
    };

    public Route update = (Request request, Response response) -> {
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

    public Route updatePassword = (Request request, Response response) -> {
        Type requestType = new TypeToken<UpdatePasswordRequest>() {}.getType();
        UpdatePasswordRequest requestBody = gson.fromJson(request.body(), requestType);

        UpdateSalesmanPasswordCommand command = new UpdateSalesmanPasswordCommand(
                SelfValidating.validId(request.params(":id")),
                requestBody.password
        );
        updateSalesmanPasswordUseCase.updatePassword(command);
        response.status(HttpStatus.OK_200);
        return HttpStatus.OK_200 + " " + HttpStatus.Code.OK.getMessage();
    };

    public Route delete = (Request request, Response response) -> {
        Long id = SelfValidating.validId(request.params(":id"));
        deleteSalesmanUseCase.deleteSalesman(id);
        response.status(HttpStatus.OK_200);
        return HttpStatus.OK_200 + " " + HttpStatus.Code.OK.getMessage();
    };
}
