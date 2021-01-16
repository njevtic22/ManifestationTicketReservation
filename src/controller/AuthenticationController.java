package controller;

import com.google.gson.Gson;
import exception.TokenNotFoundException;
import model.Customer;
import model.Salesman;
import model.User;
import org.eclipse.jetty.http.HttpStatus;
import request.LogInRequest;
import request.RegisterUserRequest;
import spark.Filter;
import spark.Route;
import useCase.authentication.CreateTokenAuthenticationCase;
import useCase.authentication.GetUserFromTokenAuthenticationCase;
import useCase.authentication.RegisterCustomerAuthenticationCase;
import useCase.authentication.RegisterSalesmanAuthenticationCase;
import useCase.authentication.command.RegisterUserCommand;
import useCase.authentication.dto.TokenResponse;

import static spark.Spark.before;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.post;
import static spark.Spark.put;

public class AuthenticationController {
    private Gson gson;
    private CreateTokenAuthenticationCase createTokenAuthenticationCase;
    private GetUserFromTokenAuthenticationCase getUserFromTokenAuthenticationCase;
    private RegisterCustomerAuthenticationCase registerCustomerAuthenticationCase;
    private RegisterSalesmanAuthenticationCase registerSalesmanAuthenticationCase;

    public AuthenticationController(Gson gson, CreateTokenAuthenticationCase createTokenAuthenticationCase, GetUserFromTokenAuthenticationCase getUserFromTokenAuthenticationCase, RegisterCustomerAuthenticationCase registerCustomerAuthenticationCase, RegisterSalesmanAuthenticationCase registerSalesmanAuthenticationCase) {
        this.gson = gson;
        this.createTokenAuthenticationCase = createTokenAuthenticationCase;
        this.getUserFromTokenAuthenticationCase = getUserFromTokenAuthenticationCase;
        this.registerCustomerAuthenticationCase = registerCustomerAuthenticationCase;
        this.registerSalesmanAuthenticationCase = registerSalesmanAuthenticationCase;
        this.setUpRoutes();
    }

    private void setUpRoutes() {
        path("api", () -> {
            before("/*", this.validateToken);
            path("/authentication", () -> {
                post("/login", logIn);
                post("/registerCustomer", registerCustomer);
                post("/registerSalesman", registerSalesman);
//                get("", getAll, new GetAllCustomersTransformer(gson, new GetAllCustomersMapper(formatter)));
//                get("/:id", getById, new GetByIdCustomerTransformer(gson, new GetByIdCustomerMapper(formatter)));
//                put("/:id", update);
//                put("/:id/password", updatePassword);
//                delete("/:id", delete);
            });
        });
    }

    public Filter validateToken = (request, response) -> {
        String authorization = request.headers("Authorization");

        if (authorization == null || authorization.equals("Bearer null")) {
            // TODO: Add other routes to ignore
            if (!request.uri().equals("/api/authentication/login") && !request.uri().equals("/api/authentication/registerCustomer")) {
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

    public Route logIn = (request, response) -> {
        LogInRequest requestBody = gson.fromJson(request.body(), LogInRequest.class);
        String jwt = createTokenAuthenticationCase.createToken(requestBody.username, requestBody.password);

        User user = getUserFromTokenAuthenticationCase.getUserFromToken(jwt);

        response.status(HttpStatus.OK_200);
        return gson.toJson(new TokenResponse(jwt, user.getClass().getSimpleName().toUpperCase()));

    };

    public Route registerCustomer = (request, response) -> {
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

        String jwt = createTokenAuthenticationCase.createToken(customer.getUsername(), customer.getPassword());

        response.status(HttpStatus.OK_200);
        return gson.toJson(new TokenResponse(jwt, customer.getClass().getSimpleName().toUpperCase()));
    };

    public Route registerSalesman = (request, response) -> {
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

        response.status(HttpStatus.OK_200);
        return HttpStatus.OK_200 + " " + HttpStatus.Code.OK.getMessage();
    };
}
