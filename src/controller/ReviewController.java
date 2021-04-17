package controller;

import com.google.gson.Gson;
import filterSearcher.ReviewFilterSearcher;
import model.Review;
import model.ReviewStatus;
import model.User;
import org.eclipse.jetty.http.HttpStatus;
import request.AddReviewRequest;
import request.UpdateReviewRequest;
import responseTransformer.GetAllReviewsTransformer;
import responseTransformer.dtoMappers.GetAllReviewsMapper;
import spark.Request;
import spark.Response;
import spark.Route;
import useCase.review.AddReviewUseCase;
import useCase.review.CanLeaveReviewUseCase;
import useCase.review.DeleteReviewUseCase;
import useCase.review.GetAllReviewsUseCase;
import useCase.review.UpdateReviewUseCase;
import useCase.review.command.AddReviewCommand;
import useCase.review.command.UpdateReviewCommand;
import utility.PaginatedResponse;
import utility.Pagination;
import utility.RoleEnsure;
import validation.SelfValidating;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    private CanLeaveReviewUseCase canLeaveReviewUseCase;
    private final ReviewFilterSearcher reviewFilterSearcher;
    private Pagination pagination;

    private RoleEnsure ensureUserIsAdmin = AuthenticationController::ensureUserIsAdmin;
    private RoleEnsure ensureUserIsSalesman = AuthenticationController::ensureUserIsSalesman;
    private RoleEnsure ensureUserIsCustomer = AuthenticationController::ensureUserIsCustomer;
    private RoleEnsure ensureUserIsAdminOrSalesman = AuthenticationController::ensureUserIsAdminOrSalesman;
    private RoleEnsure ensureUserIsAdminOrCustomer = AuthenticationController::ensureUserIsAdminOrCustomer;

    public ReviewController(
            Gson gson,
            AddReviewUseCase addReviewUseCase,
            GetAllReviewsUseCase getAllReviewsUseCase,
            UpdateReviewUseCase updateReviewUseCase,
            DeleteReviewUseCase deleteReviewUseCase,
            CanLeaveReviewUseCase canLeaveReviewUseCase,
            ReviewFilterSearcher reviewFilterSearcher,
            Pagination pagination
    ) {
        this.gson = gson;
        this.addReviewUseCase = addReviewUseCase;
        this.getAllReviewsUseCase = getAllReviewsUseCase;
        this.updateReviewUseCase = updateReviewUseCase;
        this.deleteReviewUseCase = deleteReviewUseCase;
        this.canLeaveReviewUseCase = canLeaveReviewUseCase;
        this.reviewFilterSearcher = reviewFilterSearcher;
        this.pagination = pagination;
        this.setUpRoutes();
    }

    private void setUpRoutes() {
        path("api", () -> {
            path("/reviews", () -> {
                post("", add);
                get("/:manifestationId", getAll, new GetAllReviewsTransformer(gson, new GetAllReviewsMapper()));
                post("/canLeaveReview/:manifestationId", canLeaveReview);
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
        Long manifestationId = SelfValidating.validId(request.params(":manifestationId"));

        List<Review> reviews = new ArrayList<>(getAllReviewsUseCase.getAllReviews(manifestationId));
        applyFilter(request, reviews);

        PaginatedResponse<Review> paginatedReviews = pagination.doPagination(
                reviews,
                request.queryParams("page"),
                request.queryParams("size")
        );

        response.status(HttpStatus.OK_200);
        return paginatedReviews;

    };

    public Route canLeaveReview = (Request request, Response response) -> {
        ensureUserIsCustomer.ensure(request);

        Long manifestationId = SelfValidating.validId(request.params(":manifestationId"));
        User user = request.attribute("user");

        response.status(HttpStatus.OK_200);
        return canLeaveReviewUseCase.canLeaveReview(user, manifestationId);
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

    private void applyFilter(Request request, Collection<Review> reviews) {
        if (request.queryParams("filterStatus") != null)
            reviewFilterSearcher.filterByStatus(ReviewStatus.valueOf(request.queryParams("filterStatus")), reviews);
    }
}
