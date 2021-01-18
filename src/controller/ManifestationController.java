package controller;

import com.google.gson.Gson;
import model.Manifestation;
import model.Salesman;
import model.User;
import org.eclipse.jetty.http.HttpStatus;
import request.AddManifestationRequest;
import request.UpdateLocationRequest;
import request.UpdateManifestationRequest;
import responseTransformer.GetAllManifestationsTransformer;
import responseTransformer.GetByIdManifestationTransformer;
import responseTransformer.dtoMappers.GetAllManifestationsMapper;
import responseTransformer.dtoMappers.GetByIdManifestationMapper;
import spark.Request;
import spark.Response;
import spark.Route;
import useCase.manifestation.AddManifestationUseCase;
import useCase.manifestation.DeleteManifestationUseCase;
import useCase.manifestation.GetAllManifestationsUseCase;
import useCase.manifestation.GetByIdManifestationUseCase;
import useCase.manifestation.UpdateLocationUseCase;
import useCase.manifestation.UpdateManifestationUseCase;
import useCase.manifestation.command.AddManifestationCommand;
import useCase.manifestation.command.UpdateLocationCommand;
import useCase.manifestation.command.UpdateManifestationCommand;
import utility.RoleEnsure;
import validation.SelfValidating;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Collection;

import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.delete;

public class ManifestationController {
    private Gson gson;
    private final SimpleDateFormat formatter;
    private AddManifestationUseCase addManifestationUseCase;
    private GetAllManifestationsUseCase getAllManifestationsUseCase;
    private GetByIdManifestationUseCase getByIdManifestationUseCase;
    private UpdateManifestationUseCase updateManifestationUseCase;
    private UpdateLocationUseCase updateLocationUseCase;
    private DeleteManifestationUseCase deleteManifestationUseCase;

    private RoleEnsure ensureUserIsAdmin = AuthenticationController::ensureUserIsAdmin;
    private RoleEnsure ensureUserIsSalesman = AuthenticationController::ensureUserIsSalesman;
    private RoleEnsure ensureUserIsCustomer = AuthenticationController::ensureUserIsCustomer;
    private RoleEnsure ensureUserIsAdminOrSalesman = AuthenticationController::ensureUserIsAdminOrSalesman;
    private RoleEnsure ensureUserIsAdminOrCustomer = AuthenticationController::ensureUserIsAdminOrCustomer;

    public ManifestationController(Gson gson, SimpleDateFormat formatter, AddManifestationUseCase addManifestationUseCase, GetAllManifestationsUseCase getAllManifestationsUseCase, GetByIdManifestationUseCase getByIdManifestationUseCase, UpdateManifestationUseCase updateManifestationUseCase, UpdateLocationUseCase updateLocationUseCase, DeleteManifestationUseCase deleteManifestationUseCase) {
        this.gson = gson;
        this.formatter = formatter;
        this.addManifestationUseCase = addManifestationUseCase;
        this.getAllManifestationsUseCase = getAllManifestationsUseCase;
        this.getByIdManifestationUseCase = getByIdManifestationUseCase;
        this.updateManifestationUseCase = updateManifestationUseCase;
        this.updateLocationUseCase = updateLocationUseCase;
        this.deleteManifestationUseCase = deleteManifestationUseCase;
        this.setUpRoutes();
    }

    private void setUpRoutes() {
        path("api", () -> {
            path("/manifestations", () -> {
                post("", add);
                get("", getAll, new GetAllManifestationsTransformer(gson, new GetAllManifestationsMapper(formatter)));
                get("/:id", getById, new GetByIdManifestationTransformer(gson, new GetByIdManifestationMapper(formatter)));
                put("/:id", updateManifestation);
                put("/:id/location", updateLocation);
                delete("/:id", delete);
            });
        });
    }

    public Route add = (Request request, Response response) -> {
        ensureUserIsSalesman.ensure(request);

        AddManifestationRequest requestBody = gson.fromJson(request.body(), AddManifestationRequest.class);

        Salesman salesman = request.attribute("user");
        AddManifestationCommand command = new AddManifestationCommand(
                salesman.getId(),
                requestBody.name,
                requestBody.regularTicketPrice,
                requestBody.holdingDate,
                requestBody.status,
                requestBody.type,
                requestBody.longitude,
                requestBody.latitude,
                requestBody.street,
                requestBody.number,
                requestBody.city,
                requestBody.postalCode,
                requestBody.imageLocation
        );
        addManifestationUseCase.addManifestation(command);
        response.status(HttpStatus.CREATED_201);

        return HttpStatus.CREATED_201 + " " + HttpStatus.Code.CREATED.getMessage();
    };

    public Route getAll = (Request request, Response response) -> {
        User user = request.attribute("user");
        response.status(HttpStatus.OK_200);
        return getAllManifestationsUseCase.getAllManifestations(user);
    };

    public Route getById = (Request request, Response response) -> {
        Long id = SelfValidating.validId(request.params(":id"));
        response.status(HttpStatus.OK_200);
        return getByIdManifestationUseCase.getByIdManifestation(id);
    };

    public Route updateManifestation = (Request request, Response response) -> {
        ensureUserIsSalesman.ensure(request);

        UpdateManifestationRequest requestBody = gson.fromJson(request.body(), UpdateManifestationRequest.class);
        UpdateManifestationCommand command = new UpdateManifestationCommand(
                SelfValidating.validId(request.params(":id")),
                requestBody.name,
                requestBody.regularTicketPrice,
                requestBody.holdingDate,
                requestBody.status,
                requestBody.type,
                requestBody.imageLocation
        );
        updateManifestationUseCase.updateManifestation(command);
        response.status(HttpStatus.OK_200);
        return HttpStatus.OK_200 + " " + HttpStatus.Code.OK.getMessage();
    };

    public Route updateLocation = (Request request, Response response) -> {
        ensureUserIsSalesman.ensure(request);

        UpdateLocationRequest requestBody = gson.fromJson(request.body(), UpdateLocationRequest.class);
        UpdateLocationCommand command = new UpdateLocationCommand(
                SelfValidating.validId(request.params(":id")),
                requestBody.longitude,
                requestBody.latitude,
                requestBody.street,
                requestBody.number,
                requestBody.city,
                requestBody.postalCode
        );
        updateLocationUseCase.updateLocation(command);
        response.status(HttpStatus.OK_200);
        return HttpStatus.OK_200 + " " + HttpStatus.Code.OK.getMessage();
    };

    public Route delete = (Request request, Response response) -> {
        ensureUserIsAdminOrSalesman.ensure(request);

        Long id = SelfValidating.validId(request.params(":id"));
        deleteManifestationUseCase.deleteManifestation(id);
        response.status(HttpStatus.OK_200);
        return HttpStatus.OK_200 + " " + HttpStatus.Code.OK.getMessage();
    };
}
