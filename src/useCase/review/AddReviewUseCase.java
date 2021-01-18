package useCase.review;

import useCase.review.command.AddReviewCommand;

public interface AddReviewUseCase {
    void addReview(AddReviewCommand command);
}
