package controller;

import com.google.gson.Gson;
import filterSearcher.UserFilterSearcher;
import model.Admin;
import model.Customer;
import model.CustomerType;
import model.Salesman;
import model.User;
import org.eclipse.jetty.http.HttpStatus;
import responseTransformer.GetAllUsersTransformer;
import responseTransformer.dtoMappers.GetAllUsersMapper;
import sorter.UserSorter;
import spark.Request;
import spark.Response;
import spark.Route;
import useCase.admin.dto.GetByIdAdminDTO;
import useCase.customer.dto.GetByIdCustomerDTO;
import useCase.salesman.dto.GetByIdSalesmanDTO;
import useCase.user.GetAllUsersUseCase;
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

    private RoleEnsure ensureUserIsAdmin = AuthenticationController::ensureUserIsAdmin;
    private RoleEnsure ensureUserIsSalesman = AuthenticationController::ensureUserIsSalesman;
    private RoleEnsure ensureUserIsCustomer = AuthenticationController::ensureUserIsCustomer;
    private RoleEnsure ensureUserIsAdminOrSalesman = AuthenticationController::ensureUserIsAdminOrSalesman;
    private RoleEnsure ensureUserIsAdminOrCustomer = AuthenticationController::ensureUserIsAdminOrCustomer;


    public UserController(Gson gson, SimpleDateFormat formatter, GetAllUsersUseCase getAllUsersUseCase, UserFilterSearcher userFilterSearcher, UserSorter userSorter) {
        this.gson = gson;
        this.formatter = formatter;
        this.getAllUsersUseCase = getAllUsersUseCase;
        this.userFilterSearcher = userFilterSearcher;
        this.userSorter = userSorter;
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
        applyQueryFilter(request, users);
        applyQuerySearch(request, users);
        applyQuerySort(request, users);
        List<User> paginatedUsers = applyQueryPagination(request, users);
        response.status(HttpStatus.OK_200);
        return paginatedUsers;
    };

    private void applyQueryFilter(Request request, Collection<User> users) {
        if (request.queryParams("filterRole") != null)
            userFilterSearcher.filterByRole(request.queryParams("filterRole"), users);
        if (request.queryParams("filterType") != null)
            userFilterSearcher.filterByType(CustomerType.valueOf(request.queryParams("filterType")), users);
    }

    private void applyQuerySearch(Request request, List<User> users) {
        if (request.queryParams("searchName") != null)
            userFilterSearcher.searchByName(request.queryParams("searchName"), users);
        if (request.queryParams("searchSurname") != null)
            userFilterSearcher.searchBySurname(request.queryParams("searchSurname"), users);
        if (request.queryParams("searchUsername") != null)
            userFilterSearcher.searchByUsername(request.queryParams("searchUsername"), users);
    }

    private void applyQuerySort(Request request, List<User> users) {
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

    // TODO: implement as util function
    private List<User> applyQueryPagination(Request request, Collection<User> users) {
        String pageStr = request.queryParams("page");
        String sizeStr = request.queryParams("size");
        if (pageStr != null && sizeStr != null) {
            if (sizeStr.equals("All"))
                return new ArrayList<>(users);

            int page = Integer.parseInt(pageStr);
            int size = Integer.parseInt((sizeStr));

            if (page < 0 || size < 1)
                return new ArrayList<>();

            /*
            *
            * @throws IndexOutOfBoundsException if an endpoint index value is out of range
            *         {@code (fromIndex < 0 || toIndex > size)}
            * @throws IllegalArgumentException if the endpoint indices are out of order
            *         {@code (fromIndex > toIndex)}
            *
            * arrUsers.subList(fromIndex, toIndex);
            *
            * */

            int from = Math.max(page * size, 0);
            int to = Math.min((page + 1) * size, users.size());

            if (from > to)
                return new ArrayList<>();

            ArrayList<User> arrUsers = new ArrayList<>(users);
            return arrUsers.subList(from, to);
        }
        return new ArrayList<>();
    }
}
