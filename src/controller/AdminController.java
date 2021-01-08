package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.eclipse.jetty.http.HttpStatus;
import request.AddAdminRequest;
import responseTransformer.GetAllAdminsTransformer;
import responseTransformer.dtoMappers.GetAllAdminsMapper;
import spark.Request;
import spark.Response;
import spark.Route;
import useCase.admin.AddAdminUseCase;
import useCase.admin.GetAllAdminsUseCase;
import useCase.admin.command.AddAdminCommand;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;

import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.post;

public class AdminController {
    private Gson gson;
    private final SimpleDateFormat formatter;
    private AddAdminUseCase addAdminUseCase;
    private GetAllAdminsUseCase getAllAdminsUseCase;


    public AdminController(Gson gson, SimpleDateFormat formatter, AddAdminUseCase addAdminUseCase, GetAllAdminsUseCase getAllAdminsUseCase) {
        this.gson = gson;
        this.formatter = formatter;
        this.addAdminUseCase = addAdminUseCase;
        this.getAllAdminsUseCase = getAllAdminsUseCase;
        this.setUpRoutes();
    }

    private void setUpRoutes() {
        path("api", () -> {
            path("/admins", () -> {
                post("", add);
                get("", getAll, new GetAllAdminsTransformer(gson, new GetAllAdminsMapper(formatter)));
            });
        });
    }

    public Route add = (Request request, Response response) -> {
        Type requestType = new TypeToken<AddAdminRequest>(){}.getType();
        AddAdminRequest requestBody = gson.fromJson(request.body(), requestType);

        AddAdminCommand command = new AddAdminCommand(
                requestBody.id,
                requestBody.name,
                requestBody.surname,
                requestBody.username,
                requestBody.password,
                requestBody.dateOfBirth,
                requestBody.gender
        );

        addAdminUseCase.addAdmin(command);
        response.status(HttpStatus.CREATED_201);

        return HttpStatus.CREATED_201 + " " + HttpStatus.Code.CREATED.getMessage();
    };

    public Route getAll = (Request request, Response response) -> {
        response.status(HttpStatus.OK_200);
        return getAllAdminsUseCase.getAllAdmins();
    };
}
