package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import filterSearcher.CustomerFilterSearcher;
import filterSearcher.UserFilterSearcher;
import model.Customer;
import model.CustomerType;
import org.eclipse.jetty.http.HttpStatus;
import request.AddCustomerRequest;
import request.UpdateCustomerRequest;
import responseTransformer.GetAllCustomersTransformer;
import responseTransformer.GetByIdCustomerTransformer;
import responseTransformer.dtoMappers.GetAllCustomersMapper;
import responseTransformer.dtoMappers.GetByIdCustomerMapper;
import sorter.CustomerSorter;
import sorter.UserSorter;
import spark.Request;
import spark.Response;
import spark.Route;
import useCase.customer.AddCustomerUseCase;
import useCase.customer.DeleteCustomerUseCase;
import useCase.customer.GetAllCustomersUseCase;
import useCase.customer.GetByIdCustomerUseCase;
import useCase.customer.GetSuspiciousCustomersUseCase;
import useCase.customer.UpdateCustomerUseCase;
import useCase.customer.command.AddCustomerCommand;
import useCase.customer.command.UpdateCustomerCommand;
import useCase.customer.dto.TypeDiscountDTO;
import utility.PaginatedResponse;
import utility.Pagination;
import utility.RoleEnsure;
import validation.SelfValidating;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    private GetSuspiciousCustomersUseCase getSuspiciousCustomersUseCase;
    private final CustomerFilterSearcher customerFilterSearcher;
    private final CustomerSorter customerSorter;
    private  Pagination pagination;

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
            DeleteCustomerUseCase deleteCustomerUseCase,
            GetSuspiciousCustomersUseCase getSuspiciousCustomersUseCase,
            CustomerFilterSearcher customerFilterSearcher,
            CustomerSorter customerSorter,
            Pagination pagination
    ) {
        this.gson = gson;
        this.formatter = formatter;
        this.addCustomerUseCase = addCustomerUseCase;
        this.getAllCustomersUseCase = getAllCustomersUseCase;
        this.getByIdCustomerUseCase = getByIdCustomerUseCase;
        this.updateCustomerUseCase = updateCustomerUseCase;
        this.deleteCustomerUseCase = deleteCustomerUseCase;
        this.getSuspiciousCustomersUseCase = getSuspiciousCustomersUseCase;
        this.customerFilterSearcher = customerFilterSearcher;
        this.customerSorter = customerSorter;
        this.pagination = pagination;
        this.setUpRoutes();
    }

    private void setUpRoutes() {
        path("api", () -> {
            path("/customers", () -> {
                post("", add);
                get("", getAll, new GetAllCustomersTransformer(gson, new GetAllCustomersMapper(formatter)));
                get("/suspicious", getSuspicious, new GetAllCustomersTransformer(gson, new GetAllCustomersMapper(formatter)));
                get("/type", getType);
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

    public Route getSuspicious = (Request request, Response response) -> {
        ensureUserIsAdmin.ensure(request);

        List<Customer> customers = new ArrayList<>(getSuspiciousCustomersUseCase.getSuspiciousCustomers());
        applyFilter(request, customers);
        applySearch(request, customers);
        applySort(request, customers);
        PaginatedResponse<Customer> paginatedCustomers = pagination.doPagination(
                customers,
                request.queryParams("page"),
                request.queryParams("size")
        );

        response.status(HttpStatus.OK_200);
        return paginatedCustomers;
    };

    public Route getById = (Request request, Response response) -> {
        ensureUserIsCustomer.ensure(request);

        Long id = SelfValidating.validId(request.params(":id"));
        Customer customer = getByIdCustomerUseCase.getByIdCustomer(id);
        response.status(HttpStatus.OK_200);
        return customer;
    };

    public Route getType = (Request request, Response response) -> {
        ensureUserIsCustomer.ensure(request);

        Customer customer = request.attribute("user");
        TypeDiscountDTO responseDTO = new TypeDiscountDTO(
                customer.getType().toString(),
                customer.getType().getDiscount()
        );
        response.status(HttpStatus.OK_200);
        return gson.toJson(responseDTO);
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

    private void applyFilter(Request request, Collection<Customer> users) {
        if (request.queryParams("filterRole") != null)
            customerFilterSearcher.filterByRole(request.queryParams("filterRole"), users);
        if (request.queryParams("filterType") != null)
            customerFilterSearcher.filterByType(CustomerType.valueOf(request.queryParams("filterType")), users);
    }

    private void applySearch(Request request, List<Customer> users) {
        if (request.queryParams("searchName") != null)
            customerFilterSearcher.searchByName(request.queryParams("searchName"), users);
        if (request.queryParams("searchSurname") != null)
            customerFilterSearcher.searchBySurname(request.queryParams("searchSurname"), users);
        if (request.queryParams("searchUsername") != null)
            customerFilterSearcher.searchByUsername(request.queryParams("searchUsername"), users);
    }

    private void applySort(Request request, List<Customer> customers) {
        String sortBy = request.queryParams("sortBy");
        if (sortBy == null)
            return;

        String sortOrderStr = request.queryParams("sortOrder");
        if (sortOrderStr == null)
            return;

        int sortOrder = sortOrderStr.equals("asc") ? 1 : -1;

        switch (sortBy) {
            case "name":
                customerSorter.sortByName(customers, sortOrder);
                break;
            case "surname":
                customerSorter.sortBySurname(customers, sortOrder);
                break;
            case "username":
                customerSorter.sortByUsername(customers, sortOrder);
                break;
            case "date":
                customerSorter.sortByDateOfBirth(customers, sortOrder);
                break;
            case "gender":
                customerSorter.sortByGender(customers, sortOrder);
                break;
            case "role":
                customerSorter.sortByRole(customers, sortOrder);
                break;
            case "type":
                customerSorter.sortByType(customers, sortOrder);
                break;
            case "points":
                customerSorter.sortByPoints(customers, sortOrder);
                break;
        }
    }
}
