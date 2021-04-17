package useCase.review;

import model.Review;

import java.util.Collection;

public interface GetAllReviewsUseCase {
    Collection<Review> getAllReviews(Long manifestationId);
}
