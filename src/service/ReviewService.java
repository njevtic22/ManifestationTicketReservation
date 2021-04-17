package service;

import exception.CustomerNotFoundException;
import exception.ManifestationNotFoundException;
import exception.ReviewNotFoundException;
import model.Customer;
import model.Manifestation;
import model.ManifestationStatus;
import model.Review;
import model.ReviewStatus;
import model.Ticket;
import model.User;
import repository.Repository;
import repository.UserRepository;
import useCase.review.AddReviewUseCase;
import useCase.review.CanLeaveReviewUseCase;
import useCase.review.DeleteReviewUseCase;
import useCase.review.GetAllReviewsUseCase;
import useCase.review.UpdateReviewUseCase;
import useCase.review.command.AddReviewCommand;
import useCase.review.command.UpdateReviewCommand;

import java.util.Collection;

public class ReviewService implements
        AddReviewUseCase,
        GetAllReviewsUseCase,
        UpdateReviewUseCase,
        DeleteReviewUseCase,
        CanLeaveReviewUseCase {
    private final Repository<Review, Long> reviewRepository;
    private final Repository<Manifestation, Long> manifestationRepository;
    private final UserRepository<Customer> customerRepository;

    public ReviewService(Repository<Review, Long> reviewRepository, Repository<Manifestation, Long> manifestationRepository, UserRepository<Customer> customerRepository) {
        this.reviewRepository = reviewRepository;
        this.manifestationRepository = manifestationRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void addReview(AddReviewCommand command) {
        Manifestation manifestation = manifestationRepository.findByIdAndArchivedFalse(command.manifestationId)
                .orElseThrow(() -> new ManifestationNotFoundException(command.manifestationId));

        Customer customer = customerRepository.findByIdAndArchivedFalse(command.authorId)
                .orElseThrow(() -> new CustomerNotFoundException(command.authorId));

        Review review = new Review(
                command.comment,
                command.rating,
                ReviewStatus.CREATED,
                false,
                customer,
                manifestation
        );

        manifestation.addReview(review);

        reviewRepository.save(review);
        manifestationRepository.save(manifestation);
    }

    @Override
    public Collection<Review> getAllReviews(Long manifestationId) {
        Collection<Review> reviews = reviewRepository.findAllByArchivedFalse();
        reviews.removeIf(review -> !review.getManifestation().getId().equals(manifestationId));
        return reviews;
    }

    @Override
    public void updateReview(UpdateReviewCommand command) {
        Review review = reviewRepository.findByIdAndArchivedFalse(command.id)
                .orElseThrow(() -> new ReviewNotFoundException(command.id));

        review.setComment(command.comment);
        review.setRating(command.rating);
        review.setStatus(ReviewStatus.valueOf(command.status));

        reviewRepository.save(review);
    }

    @Override
    public void deleteReview(Long id) {
        Review review = reviewRepository.findByIdAndArchivedFalse(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));

        review.archive();

        reviewRepository.save(review);
    }

    @Override
    public boolean canLeaveReview(User user, Long manifestationId) {
        if (!(user instanceof Customer))
            return false;

        Manifestation manifestation = manifestationRepository.findByIdAndArchivedFalse(manifestationId)
                .orElseThrow(() -> new ManifestationNotFoundException(manifestationId));

        if (manifestation.getStatus() != ManifestationStatus.INACTIVE)
            return false;

        Customer customer = (Customer) user;
        for (Ticket ticket : customer.getTickets()) {
            Manifestation canMan = ticket.getManifestation();
            if (canMan.equals(manifestation))
                return true;
        }
        return false;
    }
}
