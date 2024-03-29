package controller;

import com.google.gson.Gson;
import filterSearcher.UserFilterSearcher;
import model.CustomerType;
import model.User;
import org.eclipse.jetty.http.HttpStatus;
import responseTransformer.GetAllUsersTransformer;
import responseTransformer.dtoMappers.GetAllUsersMapper;
import sorter.UserSorter;
import spark.Request;
import spark.Response;
import spark.Route;
import useCase.user.GetAllUsersUseCase;
import utility.PaginatedResponse;
import utility.Pagination;
import utility.RoleEnsure;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.post;
import static spark.Spark.put;

public class UserController {
    private Gson gson;
    private SimpleDateFormat formatter;
    private GetAllUsersUseCase getAllUsersUseCase;
    private final UserFilterSearcher userFilterSearcher;
    private final UserSorter userSorter;
    private Pagination pagination;

    private RoleEnsure ensureUserIsAdmin = AuthenticationController::ensureUserIsAdmin;
    private RoleEnsure ensureUserIsSalesman = AuthenticationController::ensureUserIsSalesman;
    private RoleEnsure ensureUserIsCustomer = AuthenticationController::ensureUserIsCustomer;
    private RoleEnsure ensureUserIsAdminOrSalesman = AuthenticationController::ensureUserIsAdminOrSalesman;
    private RoleEnsure ensureUserIsAdminOrCustomer = AuthenticationController::ensureUserIsAdminOrCustomer;


    public UserController(
            Gson gson,
            SimpleDateFormat formatter,
            GetAllUsersUseCase getAllUsersUseCase,
            UserFilterSearcher userFilterSearcher,
            UserSorter userSorter,
            Pagination pagination
    ) {
        this.gson = gson;
        this.formatter = formatter;
        this.getAllUsersUseCase = getAllUsersUseCase;
        this.userFilterSearcher = userFilterSearcher;
        this.userSorter = userSorter;
        this.pagination = pagination;
        this.setUpRoutes();
    }

    private void setUpRoutes() {
        path("api", () -> {
            path("/users", () -> {
//                post("", add);
                get("", getAll, new GetAllUsersTransformer(gson, new GetAllUsersMapper(formatter)));
//                put("/:id", update);
//                put("/:id/password", updatePassword);
//                delete("/:id", delete);
            });
        });
    }

    public Route getAll = (Request request, Response response) -> {
        ensureUserIsAdmin.ensure(request);

        List<User> users = new ArrayList<>(getAllUsersUseCase.getAllUsers());
        applyFilter(request, users);
        applySearch(request, users);
        applySort(request, users);
        PaginatedResponse<User> paginatedUsers = pagination.doPagination(
                users,
                request.queryParams("page"),
                request.queryParams("size")
        );

        response.status(HttpStatus.OK_200);
        return paginatedUsers;
    };

    private void applyFilter(Request request, Collection<User> users) {
        if (request.queryParams("filterRole") != null)
            userFilterSearcher.filterByRole(request.queryParams("filterRole"), users);
        if (request.queryParams("filterType") != null)
            userFilterSearcher.filterByType(CustomerType.valueOf(request.queryParams("filterType")), users);
    }

    private void applySearch(Request request, List<User> users) {
        if (request.queryParams("searchName") != null)
            userFilterSearcher.searchByName(request.queryParams("searchName"), users);
        if (request.queryParams("searchSurname") != null)
            userFilterSearcher.searchBySurname(request.queryParams("searchSurname"), users);
        if (request.queryParams("searchUsername") != null)
            userFilterSearcher.searchByUsername(request.queryParams("searchUsername"), users);
    }

    private void applySort(Request request, List<User> users) {
        String sortBy = request.queryParams("sortBy");
        if (sortBy == null)
            return;

        String sortOrderStr = request.queryParams("sortOrder");
        if (sortOrderStr == null)
            return;

        int sortOrder = sortOrderStr.equals("asc") ? 1 : -1;

        switch (sortBy) {
            case "name":
                userSorter.sortByName(users, sortOrder);
                break;
            case "surname":
                userSorter.sortBySurname(users, sortOrder);
                break;
            case "username":
                userSorter.sortByUsername(users, sortOrder);
                break;
            case "date":
                userSorter.sortByDateOfBirth(users, sortOrder);
                break;
            case "gender":
                userSorter.sortByGender(users, sortOrder);
                break;
            case "role":
                userSorter.sortByRole(users, sortOrder);
                break;
            case "type":
                userSorter.sortByType(users, sortOrder);
                break;
            case "points":
                userSorter.sortByPoints(users, sortOrder);
                break;
        }
    }
}
