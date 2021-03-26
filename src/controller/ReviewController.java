package controller;

import com.google.gson.Gson;
import org.eclipse.jetty.http.HttpStatus;
import request.AddReviewRequest;
import request.UpdateReviewRequest;
import responseTransformer.GetAllReviewsTransformer;
import responseTransformer.dtoMappers.GetAllReviewsMapper;
import spark.Request;
import spark.Response;
import spark.Route;
import useCase.review.AddReviewUseCase;
import useCase.review.DeleteReviewUseCase;
import useCase.review.GetAllReviewsUseCase;
import useCase.review.UpdateReviewUseCase;
import useCase.review.command.AddReviewCommand;
import useCase.review.command.UpdateReviewCommand;
import utility.RoleEnsure;
import validation.SelfValidating;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.post;
import static spark.Spark.put;

public class ReviewController {
    private Gson gson;
    private AddReviewUseCase addReviewUseCase;
    private GetAllReviewsUseCase getAllReviewsUseCase;
    private UpdateReviewUseCase updateReviewUseCase;
    private DeleteReviewUseCase deleteReviewUseCase;

    private RoleEnsure ensureUserIsAdmin = AuthenticationController::ensureUserIsAdmin;
    private RoleEnsure ensureUserIsSalesman = AuthenticationController::ensureUserIsSalesman;
    private RoleEnsure ensureUserIsCustomer = AuthenticationController::ensureUserIsCustomer;
    private RoleEnsure ensureUserIsAdminOrSalesman = AuthenticationController::ensureUserIsAdminOrSalesman;
    private RoleEnsure ensureUserIsAdminOrCustomer = AuthenticationController::ensureUserIsAdminOrCustomer;

    public ReviewController(Gson gson, AddReviewUseCase addReviewUseCase, GetAllReviewsUseCase getAllReviewsUseCase, UpdateReviewUseCase updateReviewUseCase, DeleteReviewUseCase deleteReviewUseCase) {
        this.gson = gson;
        this.addReviewUseCase = addReviewUseCase;
        this.getAllReviewsUseCase = getAllReviewsUseCase;
        this.updateReviewUseCase = updateReviewUseCase;
        this.deleteReviewUseCase = deleteReviewUseCase;
        this.setUpRoutes();
    }

    private void setUpRoutes() {
        path("api", () -> {
            path("/reviews", () -> {
                post("", add);
                get("", getAll, new GetAllReviewsTransformer(gson, new GetAllReviewsMapper()));
                // TODO: forManifestation
                // TODO: createdForSalesman
                put("/:id", update);
                delete("/:id", delete);
            });
        });
    }

    public Route add = (Request request, Response response) -> {
        ensureUserIsCustomer.ensure(request);

        AddReviewRequest requestBody = gson.fromJson(request.body(), AddReviewRequest.class);
        AddReviewCommand command = new AddReviewCommand(
                requestBody.comment,
                requestBody.rating,
                requestBody.authorId,
                requestBody.manifestationId
        );
        addReviewUseCase.addReview(command);
        response.status(HttpStatus.CREATED_201);

        return HttpStatus.CREATED_201 + " " + HttpStatus.Code.CREATED.getMessage();
    };

    public Route getAll = (Request request, Response response) -> {
        ensureUserIsAdminOrSalesman.ensure(request);
        response.status(HttpStatus.OK_200);
        return getAllReviewsUseCase.getAllReviews();

    };

    public Route update = (Request request, Response response) -> {
        ensureUserIsCustomer.ensure(request);

        UpdateReviewRequest requestBody = gson.fromJson(request.body(), UpdateReviewRequest.class);
        UpdateReviewCommand command = new UpdateReviewCommand(
                requestBody.id,
                requestBody.comment,
                requestBody.rating,
                requestBody.status
        );
        updateReviewUseCase.updateReview(command);
        response.status(HttpStatus.OK_200);
        return HttpStatus.OK_200 + " " + HttpStatus.Code.OK.getMessage();
    };

    public Route delete = (Request request, Response response) -> {
        Long id = SelfValidating.validId(request.params(":id"));
        deleteReviewUseCase.deleteReview(id);
        response.status(HttpStatus.OK_200);
        return HttpStatus.OK_200 + " " + HttpStatus.Code.OK.getMessage();
    };
}
