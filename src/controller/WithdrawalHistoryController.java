package controller;

import com.google.gson.Gson;
import model.Customer;
import org.eclipse.jetty.http.HttpStatus;
import responseTransformer.GetAllHistoriesTransformer;
import responseTransformer.dtoMappers.GetAllHistoriesMapper;
import spark.Request;
import spark.Response;
import spark.Route;
import useCase.withdrawalHistory.GetHistoriesUseCase;
import utility.RoleEnsure;

import java.text.SimpleDateFormat;

import static spark.Spark.get;
import static spark.Spark.path;

public class WithdrawalHistoryController {
    private Gson gson;
    private final SimpleDateFormat formatter;
    private GetHistoriesUseCase getHistoriesUseCase;

    private RoleEnsure ensureUserIsAdmin = AuthenticationController::ensureUserIsAdmin;
    private RoleEnsure ensureUserIsSalesman = AuthenticationController::ensureUserIsSalesman;
    private RoleEnsure ensureUserIsCustomer = AuthenticationController::ensureUserIsCustomer;
    private RoleEnsure ensureUserIsAdminOrSalesman = AuthenticationController::ensureUserIsAdminOrSalesman;
    private RoleEnsure ensureUserIsAdminOrCustomer = AuthenticationController::ensureUserIsAdminOrCustomer;

    public WithdrawalHistoryController(Gson gson, SimpleDateFormat formatter, GetHistoriesUseCase getHistoriesUseCase) {
        this.gson = gson;
        this.formatter = formatter;
        this.getHistoriesUseCase = getHistoriesUseCase;
        this.setUpRoutes();
    }

    private void setUpRoutes() {
        path("api", () -> {
            path("/histories", () -> {
                get("", getAll, new GetAllHistoriesTransformer(gson, new GetAllHistoriesMapper(formatter)));
            });
        });
    }

    public Route getAll = (Request request, Response response) -> {
        ensureUserIsCustomer.ensure(request);

        Customer customer = request.attribute("user");
        response.status(HttpStatus.OK_200);
        return getHistoriesUseCase.getHistories(customer);
    };
}
