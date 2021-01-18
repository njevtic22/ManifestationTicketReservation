package useCase.review.dto;

import model.Review;

public class GetAllReviewsDTO {
    public Long id;
    public String comment;
    public float rating;
    public String status;

    public String author;
    public Long authorId;

    public String manifestation;
    public Long manifestationId;


    public GetAllReviewsDTO(Review review) {
        this.id = review.getId();
        this.comment = review.getComment();
        this.rating = review.getRating();
        this.status = review.getStatus().toString();
        this.author = review.getAuthor().getName() + " " + review.getAuthor().getSurname();
        this.authorId = review.getAuthor().getId();
        this.manifestation = review.getManifestation().getName();
        this.manifestationId = review.getManifestation().getId();
    }
}
