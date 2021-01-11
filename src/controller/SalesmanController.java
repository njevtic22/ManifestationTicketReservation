package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Salesman;
import org.eclipse.jetty.http.HttpStatus;
import request.AddAdminRequest;
import request.AddSalesmanRequest;
import spark.Request;
import spark.Response;
import spark.Route;
import useCase.salesman.AddSalesmanUseCase;
import useCase.salesman.command.AddSalesmanCommand;

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

    public SalesmanController(Gson gson, SimpleDateFormat formatter, AddSalesmanUseCase addSalesmanUseCase) {
        this.gson = gson;
        this.formatter = formatter;
        this.addSalesmanUseCase = addSalesmanUseCase;
        this.setUpRoutes();
    }

    private void setUpRoutes() {
        path("api", () -> {
            path("/salesmen", () -> {
                post("", add);
//                get("", getAll, new GetAllAdminsTransformer(gson, new GetAllAdminsMapper(formatter)));
//                get("/:id", getById, new GetByIdAdminTransformer(gson, new GetByIdAdminMapper(formatter)));
//                put("/:id", update);
//                put("/:id/password", updatePassword);
//                delete("/:id", delete);
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
}
