package useCase.review;

import model.User;

public interface CanLeaveReviewUseCase {
    boolean canLeaveReview(User user, Long manifestationId);
}
