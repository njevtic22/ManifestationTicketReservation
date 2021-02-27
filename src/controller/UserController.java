package controller;

import com.google.gson.Gson;
import filters.UserFilter;
import model.CustomerType;
import model.User;
import org.eclipse.jetty.http.HttpStatus;
import responseTransformer.GetAllUsersTransformer;
import responseTransformer.dtoMappers.GetAllUsersMapper;
import spark.Request;
import spark.Response;
import spark.Route;
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
    private final SimpleDateFormat formatter;
    private GetAllUsersUseCase getAllUsersUseCase;

    private RoleEnsure ensureUserIsAdmin = AuthenticationController::ensureUserIsAdmin;
    private RoleEnsure ensureUserIsSalesman = AuthenticationController::ensureUserIsSalesman;
    private RoleEnsure ensureUserIsCustomer = AuthenticationController::ensureUserIsCustomer;
    private RoleEnsure ensureUserIsAdminOrSalesman = AuthenticationController::ensureUserIsAdminOrSalesman;
    private RoleEnsure ensureUserIsAdminOrCustomer = AuthenticationController::ensureUserIsAdminOrCustomer;


    public UserController(Gson gson, SimpleDateFormat formatter, GetAllUsersUseCase getAllUsersUseCase) {
        this.gson = gson;
        this.formatter = formatter;
        this.getAllUsersUseCase = getAllUsersUseCase;
        this.setUpRoutes();
    }

    private void setUpRoutes() {
        path("api", () -> {
            path("/users", () -> {
//                post("", add);
                get("", getAll, new GetAllUsersTransformer(gson, new GetAllUsersMapper(formatter)));
//                get("/:id", getById, new GetByIdAdminTransformer(gson, new GetByIdAdminMapper(formatter)));
//                put("/:id", update);
//                put("/:id/password", updatePassword);
//                delete("/:id", delete);
            });
        });
    }

    public Route getAll = (Request request, Response response) -> {
        ensureUserIsAdmin.ensure(request);

        Collection<User> users = getAllUsersUseCase.getAllUsers();
        applyQueryFilter(request, users);
        List<User> paginatedUsers = applyQueryPagination(request, users);
        response.status(HttpStatus.OK_200);
        return paginatedUsers;
    };

    private void applyQueryFilter(Request request, Collection<User> users) {
        if (request.queryParams("role") != null)
            UserFilter.filterByRole(request.queryParams("role"), users);
        if (request.queryParams("type") != null)
            UserFilter.filterByType(CustomerType.valueOf(request.queryParams("type")), users);
    }

    private List<User> applyQueryPagination(Request request, Collection<User> users) {
        String pageStr = request.queryParams("page");
        String sizeStr = request.queryParams("size");
        if (pageStr != null && sizeStr != null) {
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
