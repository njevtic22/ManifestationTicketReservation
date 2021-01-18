package useCase.review;

import useCase.review.command.UpdateReviewCommand;

public interface UpdateReviewUseCase {
    void updateReview(UpdateReviewCommand command);
}
