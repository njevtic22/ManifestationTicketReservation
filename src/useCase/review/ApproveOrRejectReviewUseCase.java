package useCase.review;

import useCase.review.command.ApproveOrRejectReviewCommand;

public interface ApproveOrRejectReviewUseCase {
    void approveOrReject(ApproveOrRejectReviewCommand command);
}
