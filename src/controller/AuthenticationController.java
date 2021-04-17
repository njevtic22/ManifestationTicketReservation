package controller;

import com.google.gson.Gson;
import exception.InvalidRoleException;
import exception.TokenNotFoundException;
import model.Admin;
import model.Customer;
import model.Salesman;
import model.User;
import org.eclipse.jetty.http.HttpStatus;
import request.LogInRequest;
import request.RegisterUserRequest;
import request.UpdatePasswordRequest;
import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Route;
import useCase.admin.dto.GetByIdAdminDTO;
import useCase.authentication.ChangePasswordAuthenticationCase;
import useCase.authentication.CreateTokenAuthenticationCase;
import useCase.authentication.GetUserFromTokenAuthenticationCase;
import useCase.authentication.RegisterCustomerAuthenticationCase;
import useCase.authentication.RegisterSalesmanAuthenticationCase;
import useCase.authentication.command.ChangePasswordCommand;
import useCase.authentication.command.RegisterUserCommand;
import useCase.authentication.dto.TokenResponse;
import useCase.customer.dto.GetByIdCustomerDTO;
import useCase.salesman.dto.GetByIdSalesmanDTO;
import validation.SelfValidating;

import java.text.SimpleDateFormat;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.post;

public class AuthenticationController {
    private Gson gson;
    private SimpleDateFormat formatter;
    private CreateTokenAuthenticationCase createTokenAuthenticationCase;
    private ChangePasswordAuthenticationCase changePasswordAuthenticationCase;
    private GetUserFromTokenAuthenticationCase getUserFromTokenAuthenticationCase;
    private RegisterCustomerAuthenticationCase registerCustomerAuthenticationCase;
    private RegisterSalesmanAuthenticationCase registerSalesmanAuthenticationCase;

    public AuthenticationController(
            Gson gson,
            SimpleDateFormat formatter,
            CreateTokenAuthenticationCase createTokenAuthenticationCase,
            ChangePasswordAuthenticationCase changePasswordAuthenticationCase,
            GetUserFromTokenAuthenticationCase getUserFromTokenAuthenticationCase,
            RegisterCustomerAuthenticationCase registerCustomerAuthenticationCase,
            RegisterSalesmanAuthenticationCase registerSalesmanAuthenticationCase
    ) {
        this.gson = gson;
        this.formatter = formatter;
        this.createTokenAuthenticationCase = createTokenAuthenticationCase;
        this.changePasswordAuthenticationCase = changePasswordAuthenticationCase;
        this.getUserFromTokenAuthenticationCase = getUserFromTokenAuthenticationCase;
        this.registerCustomerAuthenticationCase = registerCustomerAuthenticationCase;
        this.registerSalesmanAuthenticationCase = registerSalesmanAuthenticationCase;
        this.setUpRoutes();
    }

    private void setUpRoutes() {
        path("api", () -> {
            before("/*", this.validateToken);
            path("/authentication", () -> {
                get("/authenticated", getAuthenticated);
                post("/:id/password", changePassword);
                post("/login", logIn);
                post("/registerCustomer", registerCustomer);
                post("/registerSalesman", registerSalesman);
            });
        });
    }

    public static void ensureUserIsAdmin(Request request) {
        User user = request.attribute("user");
        if (!user.getClass().getSimpleName().toUpperCase().equals("ADMIN"))
            throw new InvalidRoleException("User does not posses necessary role admin");
    }

    public static void ensureUserIsSalesman(Request request) {
        User user = request.attribute("user");
        if (!user.getClass().getSimpleName().toUpperCase().equals("SALESMAN"))
            throw new InvalidRoleException("User does not posses necessary role salesman");
    }

    public static void ensureUserIsCustomer(Request request) {
        User user = request.attribute("user");
        if (!user.getClass().getSimpleName().toUpperCase().equals("CUSTOMER"))
            throw new InvalidRoleException("User does not posses necessary role customer");
    }

    public static void ensureUserIsAdminOrSalesman(Request request) {
        User user = request.attribute("user");
        if (!user.getClass().getSimpleName().toUpperCase().equals("ADMIN") && !user.getClass().getSimpleName().toUpperCase().equals("SALESMAN"))
            throw new InvalidRoleException("User does not posses necessary role admin or salesman");
    }

    public static void ensureUserIsAdminOrCustomer(Request request) {
        User user = request.attribute("user");
        if (!user.getClass().getSimpleName().toUpperCase().equals("ADMIN") && !user.getClass().getSimpleName().toUpperCase().equals("CUSTOMER"))
            throw new InvalidRoleException("User does not posses necessary role admin or customer");
    }

    public Filter validateToken = (Request request, Response response) -> {
        String authorization = request.headers("Authorization");

        if (authorization == null || authorization.equals("Bearer null")) {
            if (request.uri().equals("/api/authentication/login")) {
                // Ignoring
            } else if (request.uri().equals("/api/authentication/registerCustomer")) {
                // Ignoring
            }
            else if (request.uri().startsWith("/api/reviews")) {
                if (!request.requestMethod().equals("GET")) {
                    throw new TokenNotFoundException("No token id found");
                }
            }

//            else if (request.uri().equals("/api/admins/database")) {
//                // Ignoring
//            }

            else if (request.uri().equals("/api/manifestations")) {
                if (!request.requestMethod().equals("GET")) {
                    throw new TokenNotFoundException("No token id found");
                }
            } else if (request.uri().startsWith("/api/manifestations")) {
                if (!request.requestMethod().equals("GET")) {
                    throw new TokenNotFoundException("No token id found");
                }
            } else {
                throw new TokenNotFoundException("No token id found");
            }
        } else {
            if (authorization.startsWith("Bearer ")) {
                String jwt = authorization.substring(7);
                User user = getUserFromTokenAuthenticationCase.getUserFromToken(jwt);
                if (user != null) {
                    request.attribute("user", user);
                } else {
                    throw new Exception("Something is wrong. This should not happen.");
                }
            } else {
                throw new TokenNotFoundException("No token id found");
            }
        }
    };

    public Route logIn = (Request request, Response response) -> {
        LogInRequest requestBody = gson.fromJson(request.body(), LogInRequest.class);
        String jwt = createTokenAuthenticationCase.createToken(requestBody.username, requestBody.password);

        User user = getUserFromTokenAuthenticationCase.getUserFromToken(jwt);
        String customerType = null;
        float customerDiscount = 0;

        if (user instanceof Customer) {
            Customer customer = (Customer) user;
            customerType = customer.getType().toString();
            customerDiscount = customer.getType().getDiscount();
        }

        response.status(HttpStatus.OK_200);
        return gson.toJson(new TokenResponse(jwt, user.getClass().getSimpleName().toUpperCase(), customerType, customerDiscount));

    };

    public Route registerCustomer = (Request request, Response response) -> {
        RegisterUserRequest requestBody = gson.fromJson(request.body(), RegisterUserRequest.class);

        RegisterUserCommand command = new RegisterUserCommand(
                requestBody.name,
                requestBody.surname,
                requestBody.username,
                requestBody.password,
                requestBody.dateOfBirth,
                requestBody.gender
        );
        Customer customer = registerCustomerAuthenticationCase.registerCustomer(command);

        String jwt = createTokenAuthenticationCase.createToken(customer.getUsername(), command.password);

        response.status(HttpStatus.OK_200);
        return gson.toJson(new TokenResponse(jwt, customer.getClass().getSimpleName().toUpperCase(), customer.getType().toString(), customer.getType().getDiscount()));
    };

    public Route registerSalesman = (Request request, Response response) -> {
        ensureUserIsAdmin(request);

        RegisterUserRequest requestBody = gson.fromJson(request.body(), RegisterUserRequest.class);

        RegisterUserCommand command = new RegisterUserCommand(
                requestBody.name,
                requestBody.surname,
                requestBody.username,
                requestBody.password,
                requestBody.dateOfBirth,
                requestBody.gender
        );
        registerSalesmanAuthenticationCase.registerSalesman(command);

        response.status(HttpStatus.CREATED_201);
        return HttpStatus.CREATED_201 + " " + HttpStatus.Code.CREATED.getMessage();
    };

    public Route getAuthenticated = (Request request, Response response) -> {
        User user = request.attribute("user");
        if (user instanceof Admin) {
            return gson.toJson(new GetByIdAdminDTO((Admin) user, formatter.format(user.getDateOfBirth())));
        } else if (user instanceof Salesman)
            return gson.toJson(new GetByIdSalesmanDTO((Salesman) user, formatter.format(user.getDateOfBirth())));
        else
            return gson.toJson(new GetByIdCustomerDTO((Customer) user, formatter.format(user.getDateOfBirth())));
    };

    public Route changePassword = (Request request, Response response) -> {
        UpdatePasswordRequest requestBody = gson.fromJson(request.body(), UpdatePasswordRequest.class);

        ChangePasswordCommand command = new ChangePasswordCommand(
                SelfValidating.validId(request.params(":id")),
                requestBody.oldPassword,
                requestBody.newPassword,
                requestBody.repPassword
        );
        changePasswordAuthenticationCase.changePassword(command);
        response.status(HttpStatus.OK_200);
        return HttpStatus.OK_200 + " " + HttpStatus.Code.OK.getMessage();
    };
}
