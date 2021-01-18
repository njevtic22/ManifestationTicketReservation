package useCase.review.command;

import exception.ConstraintViolationException;
import validation.SelfValidating;

public class UpdateReviewCommand implements SelfValidating {
    public Long id;
    public String comment;
    public float rating;
    public String status;

    public UpdateReviewCommand(Long id, String comment, float rating, String status) {
        this.id = id;
        this.comment = comment;
        this.rating = rating;
        this.status = status;
    }

    @Override
    public void validateSelf() {
        if (id == null || id <= 0)
            throw new ConstraintViolationException("Review id must be positive number.");

        if (comment == null || comment.trim().isEmpty())
            throw new ConstraintViolationException("Comment must not be empty.");

        if (rating <= 0)
            throw new ConstraintViolationException("Rating must be positive number.");

        if (status == null || status.trim().isEmpty())
            throw new ConstraintViolationException("Status must not be empty.");

    }
}
