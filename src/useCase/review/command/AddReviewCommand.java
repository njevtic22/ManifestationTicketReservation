package useCase.review.command;

import exception.ConstraintViolationException;
import validation.SelfValidating;

public class AddReviewCommand implements SelfValidating {
    public String comment;
    public float rating;

    public Long authorId;
    public Long manifestationId;

    public AddReviewCommand(String comment, float rating, Long authorId, Long manifestationId) {
        this.comment = comment;
        this.rating = rating;
        this.authorId = authorId;
        this.manifestationId = manifestationId;
    }

    @Override
    public void validateSelf() {
        if (comment == null || comment.trim().isEmpty())
            throw new ConstraintViolationException("Comment must not be empty.");

        if (rating <= 0)
            throw new ConstraintViolationException("Rating must be positive number.");

        if (authorId == null || authorId <= 0)
            throw new ConstraintViolationException("Author id must be positive number.");

        if (manifestationId == null || manifestationId <= 0)
            throw new ConstraintViolationException("Manifestation id must be positive number.");
    }
}
