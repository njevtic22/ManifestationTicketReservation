package filterSearcher;

import model.Review;
import model.ReviewStatus;

import java.util.Collection;

public class ReviewFilterSearcher {
    public void filterByStatus(ReviewStatus status, Collection<Review> reviews) {
        reviews.removeIf(review -> review.getStatus() != status);
    }
}
