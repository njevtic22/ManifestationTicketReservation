package controller;

import com.google.gson.Gson;
import filterSearcher.ManifestationFilterSearcher;
import model.Manifestation;
import model.ManifestationType;
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
import sorter.ManifestationSorter;
import spark.Request;
import spark.Response;
import spark.Route;
import useCase.manifestation.AddManifestationUseCase;
import useCase.manifestation.DeleteManifestationUseCase;
import useCase.manifestation.GetAllCreatedManifestationsUseCase;
import useCase.manifestation.GetAllManifestationsForSalesmanUseCase;
import useCase.manifestation.GetAllManifestationsUseCase;
import useCase.manifestation.GetByIdManifestationUseCase;
import useCase.manifestation.UpdateLocationUseCase;
import useCase.manifestation.UpdateManifestationUseCase;
import useCase.manifestation.command.AddManifestationCommand;
import useCase.manifestation.command.UpdateLocationCommand;
import useCase.manifestation.command.UpdateManifestationCommand;
import utility.PaginatedResponse;
import utility.Pagination;
import utility.RoleEnsure;
import validation.SelfValidating;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    private GetAllManifestationsForSalesmanUseCase getAllManifestationsForSalesmanUseCase;
    private GetAllCreatedManifestationsUseCase getAllCreatedManifestationsUseCase;
    private GetByIdManifestationUseCase getByIdManifestationUseCase;
    private UpdateManifestationUseCase updateManifestationUseCase;
    private UpdateLocationUseCase updateLocationUseCase;
    private DeleteManifestationUseCase deleteManifestationUseCase;
    private final ManifestationFilterSearcher manifestationFilterSearcher;
    private final ManifestationSorter manifestationSorter;
    private Pagination pagination;

    private RoleEnsure ensureUserIsAdmin = AuthenticationController::ensureUserIsAdmin;
    private RoleEnsure ensureUserIsSalesman = AuthenticationController::ensureUserIsSalesman;
    private RoleEnsure ensureUserIsCustomer = AuthenticationController::ensureUserIsCustomer;
    private RoleEnsure ensureUserIsAdminOrSalesman = AuthenticationController::ensureUserIsAdminOrSalesman;
    private RoleEnsure ensureUserIsAdminOrCustomer = AuthenticationController::ensureUserIsAdminOrCustomer;

    public ManifestationController(
            Gson gson,
            SimpleDateFormat formatter,
            AddManifestationUseCase addManifestationUseCase,
            GetAllManifestationsUseCase getAllManifestationsUseCase,
            GetAllManifestationsForSalesmanUseCase getAllManifestationsForSalesmanUseCase,
            GetAllCreatedManifestationsUseCase getAllCreatedManifestationsUseCase,
            GetByIdManifestationUseCase getByIdManifestationUseCase,
            UpdateManifestationUseCase updateManifestationUseCase,
            UpdateLocationUseCase updateLocationUseCase,
            DeleteManifestationUseCase deleteManifestationUseCase,
            ManifestationFilterSearcher manifestationFilterSearcher,
            ManifestationSorter manifestationSorter,
            Pagination pagination
    ) {
        this.gson = gson;
        this.formatter = formatter;
        this.addManifestationUseCase = addManifestationUseCase;
        this.getAllManifestationsUseCase = getAllManifestationsUseCase;
        this.getAllManifestationsForSalesmanUseCase = getAllManifestationsForSalesmanUseCase;
        this.getAllCreatedManifestationsUseCase = getAllCreatedManifestationsUseCase;
        this.getByIdManifestationUseCase = getByIdManifestationUseCase;
        this.updateManifestationUseCase = updateManifestationUseCase;
        this.updateLocationUseCase = updateLocationUseCase;
        this.deleteManifestationUseCase = deleteManifestationUseCase;
        this.manifestationFilterSearcher = manifestationFilterSearcher;
        this.manifestationSorter = manifestationSorter;
        this.pagination = pagination;
        this.setUpRoutes();
    }

    private void setUpRoutes() {
        path("api", () -> {
            path("/manifestations", () -> {
                post("", add);
                get("", getAll, new GetAllManifestationsTransformer(gson, new GetAllManifestationsMapper(formatter)));
                get("/forSalesman", getAllForSalesman, new GetAllManifestationsTransformer(gson, new GetAllManifestationsMapper(formatter)));
                get("/created", getCreated, new GetAllManifestationsTransformer(gson, new GetAllManifestationsMapper(formatter)));
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
                requestBody.maxNumberOfTickets,
                requestBody.regularTicketPrice,
                requestBody.holdingDate,
                requestBody.description,
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

        List<Manifestation> manifestations = new ArrayList<>(getAllManifestationsUseCase.getAllManifestations(user));
        applyFilter(request, manifestations);
        applySearch(request, manifestations);
        applySort(request, manifestations);

        PaginatedResponse<Manifestation> paginatedManifestations = pagination.paginate(
                manifestations,
                request.queryParams("page"),
                request.queryParams("size")
        );

        response.status(HttpStatus.OK_200);
        return paginatedManifestations;
    };

    public Route getAllForSalesman = (Request request, Response response) -> {
        ensureUserIsSalesman.ensure(request);

        Salesman salesman = request.attribute("user");
        List<Manifestation> manifestations = new ArrayList<>(getAllManifestationsForSalesmanUseCase.getAllManifestationsForSalesman(salesman));
        applyFilter(request, manifestations);
        applySearch(request, manifestations);
        applySort(request, manifestations);

        PaginatedResponse<Manifestation> paginatedManifestations = pagination.paginate(
                manifestations,
                request.queryParams("page"),
                request.queryParams("size")
        );

        response.status(HttpStatus.OK_200);
        return paginatedManifestations;
    };

    public Route getCreated = (Request request, Response response) -> {
        ensureUserIsAdmin.ensure(request);
        // TODO: Implement pagination, sorting, filtering, searching
        response.status(HttpStatus.OK_200);
        return getAllCreatedManifestationsUseCase.getCreatedManifestations();
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
                requestBody.maxNumberOfTickets,
                requestBody.regularTicketPrice,
                requestBody.holdingDate,
                requestBody.description,
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

    private void applyFilter(Request request, Collection<Manifestation> manifestations) {
        if (request.queryParams("filterType") != null)
            manifestationFilterSearcher.filterByType(ManifestationType.valueOf(request.queryParams("filterType")), manifestations);
        if (request.queryParams("filterAvailable") != null && request.queryParams("filterAvailable").toLowerCase().equals("available"))
            manifestationFilterSearcher.filterByAvailable(manifestations);
    }

    private void applySearch(Request request, Collection<Manifestation> manifestations) throws ParseException {
        if (request.queryParams("searchName") != null)
            manifestationFilterSearcher.searchByName(request.queryParams("searchName"), manifestations);
        if (request.queryParams("searchDateFrom") != null)
            manifestationFilterSearcher.searchByDateFrom(formatter.parse(request.queryParams("searchDateFrom")), manifestations);
        if (request.queryParams("searchDateTo") != null)
            manifestationFilterSearcher.searchByDateTo(formatter.parse(request.queryParams("searchDateTo")), manifestations);
        if (request.queryParams("searchCity") != null)
            manifestationFilterSearcher.searchByCity(request.queryParams("searchCity"), manifestations);
        if (request.queryParams("searchStreet") != null)
            manifestationFilterSearcher.searchByStreet(request.queryParams("searchStreet"), manifestations);
        if (request.queryParams("searchPriceFrom") != null)
            manifestationFilterSearcher.searchByPriceFrom(Long.parseLong(request.queryParams("searchPriceFrom")), manifestations);
        if (request.queryParams("searchPriceTo") != null)
            manifestationFilterSearcher.searchByPriceTo(Long.parseLong(request.queryParams("searchPriceTo")), manifestations);
    }

    private void applySort(Request request, List<Manifestation> manifestations) {
        String sortBy = request.queryParams("sortBy");
        if (sortBy == null)
            return;
        sortBy = sortBy.toLowerCase();

        String sortOrderStr = request.queryParams("sortOrder");
        if (sortOrderStr == null)
            return;

        int sortOrder = sortOrderStr.equals("asc") ? 1 : -1;

        switch (sortBy) {
            case "name":
                manifestationSorter.sortByName(manifestations, sortOrder);
                break;
            case "date":
                manifestationSorter.sortByDate(manifestations, sortOrder);
                break;
            case "price":
                manifestationSorter.sortByPrice(manifestations, sortOrder);
                break;
            case "location":
                manifestationSorter.sortByLocation(manifestations, sortOrder);
                break;
        }
    }
}
