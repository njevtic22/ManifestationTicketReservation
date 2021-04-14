package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Admin;
import org.eclipse.jetty.http.HttpStatus;
import request.AddAdminRequest;
import request.UpdateAdminRequest;
import responseTransformer.GetAllAdminsTransformer;
import responseTransformer.GetByIdAdminTransformer;
import responseTransformer.dtoMappers.GetAllAdminsMapper;
import responseTransformer.dtoMappers.GetByIdAdminMapper;
import spark.Request;
import spark.Response;
import spark.Route;
import useCase.admin.AddAdminUseCase;
import useCase.admin.DeleteAdminUseCase;
import useCase.admin.GetAllAdminsUseCase;
import useCase.admin.GetByIdAdminUseCase;
import useCase.admin.UpdateAdminUseCase;
import useCase.admin.command.AddAdminCommand;
import useCase.admin.command.UpdateAdminCommand;
import useCase.database.InitDatabaseUseCase;
import utility.RoleEnsure;
import validation.SelfValidating;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.post;
import static spark.Spark.put;

public class AdminController {
    private Gson gson;
    private final SimpleDateFormat formatter;
    private AddAdminUseCase addAdminUseCase;
    private GetAllAdminsUseCase getAllAdminsUseCase;
    private GetByIdAdminUseCase getByIdAdminUseCase;
    private UpdateAdminUseCase updateAdminUseCase;
    private DeleteAdminUseCase deleteAdminUseCase;
    private InitDatabaseUseCase initDatabaseUseCase;

    private RoleEnsure ensureUserIsAdmin = AuthenticationController::ensureUserIsAdmin;

    public AdminController(
            Gson gson,
            SimpleDateFormat formatter,
            AddAdminUseCase addAdminUseCase,
            GetAllAdminsUseCase getAllAdminsUseCase,
            GetByIdAdminUseCase getByIdAdminUseCase,
            UpdateAdminUseCase updateAdminUseCase,
            DeleteAdminUseCase deleteAdminUseCase,
            InitDatabaseUseCase initDatabaseUseCase) {
        this.gson = gson;
        this.formatter = formatter;
        this.addAdminUseCase = addAdminUseCase;
        this.getAllAdminsUseCase = getAllAdminsUseCase;
        this.getByIdAdminUseCase = getByIdAdminUseCase;
        this.updateAdminUseCase = updateAdminUseCase;
        this.deleteAdminUseCase = deleteAdminUseCase;
        this.initDatabaseUseCase = initDatabaseUseCase;
        this.setUpRoutes();
    }

    private void setUpRoutes() {
        path("api", () -> {
            path("/admins", () -> {
                post("", add);
                get("", getAll, new GetAllAdminsTransformer(gson, new GetAllAdminsMapper(formatter)));
                get("/:id", getById, new GetByIdAdminTransformer(gson, new GetByIdAdminMapper(formatter)));
                put("/:id", update);
                delete("/:id", delete);

                post("/database", initDatabase);
            });
        });
    }

    public Route add = (Request request, Response response) -> {
        ensureUserIsAdmin.ensure(request);

        Type requestType = new TypeToken<AddAdminRequest>(){}.getType();
        AddAdminRequest requestBody = gson.fromJson(request.body(), requestType);

        AddAdminCommand command = new AddAdminCommand(
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
        ensureUserIsAdmin.ensure(request);

        response.status(HttpStatus.OK_200);
        return getAllAdminsUseCase.getAllAdmins();
    };

    public Route getById = (Request request, Response response) -> {
        ensureUserIsAdmin.ensure(request);

        Long id = SelfValidating.validId(request.params(":id"));
        Admin admin = getByIdAdminUseCase.getByIdAdmin(id);
        response.status(HttpStatus.OK_200);
        return admin;
    };

    public Route update = (Request request, Response response) -> {
        ensureUserIsAdmin.ensure(request);

        Type requestType = new TypeToken<UpdateAdminRequest>() {}.getType();
        UpdateAdminRequest requestBody = gson.fromJson(request.body(), requestType);

        UpdateAdminCommand command = new UpdateAdminCommand(
                SelfValidating.validId(request.params(":id")),
                requestBody.name,
                requestBody.surname,
                requestBody.username,
                requestBody.dateOfBirth,
                requestBody.gender
        );
        updateAdminUseCase.updateAdmin(command);
        response.status(HttpStatus.OK_200);
        return HttpStatus.OK_200 + " " + HttpStatus.Code.OK.getMessage();
    };

    public Route delete = (Request request, Response response) -> {
        ensureUserIsAdmin.ensure(request);

        Long id = SelfValidating.validId(request.params(":id"));
        deleteAdminUseCase.deleteAdmin(id);
        response.status(HttpStatus.OK_200);
        return HttpStatus.OK_200 + " " + HttpStatus.Code.OK.getMessage();
    };

    public Route initDatabase = (Request request, Response response) -> {
        ensureUserIsAdmin.ensure(request);

//        initDatabaseUseCase.initDatabase();
        response.status(HttpStatus.OK_200);
        return HttpStatus.OK_200 + " " + HttpStatus.Code.OK.getMessage();
    };
}
