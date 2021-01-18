package useCase.manifestation.dto;

import model.Review;

public class GetAllReviewsForManifestationDTO {
    public long id;
    public String comment;
    public float rating;
    public String status;

    public String author;
    public String manifestation;

    public GetAllReviewsForManifestationDTO(Review review) {
        this.id = review.getId();
        this.comment = review.getComment();
        this.rating = review.getRating();
        this.status = review.getStatus().toString();
        this.author = review.getAuthor().getName() + " " + review.getAuthor().getSurname();
        this.manifestation = review.getManifestation().getName();
    }
}
