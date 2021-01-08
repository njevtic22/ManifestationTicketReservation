package model;

import utility.generator.LongGenerator;

import java.util.Objects;

public class Review {
    private Long id;
    private String comment;
    private float rating;
    private ReviewStatus status;
    private boolean archived;

    private Customer author;
    private Manifestation manifestation;

    private static LongGenerator idGenerator;

    public Review(String comment, float rating, ReviewStatus status, boolean archived, Customer author, Manifestation manifestation) {
        this(idGenerator.next(), comment, rating, status, archived, author, manifestation);
    }

    public Review(Long id, String comment, float rating, ReviewStatus status, boolean archived, Customer author, Manifestation manifestation) {
        this.id = id;
        this.comment = comment;
        this.rating = rating;
        this.status = status;
        this.archived = archived;
        this.author = author;
        this.manifestation = manifestation;
    }

    public static void initGenerator() {
        Review.initGenerator(0L);
    }

    public static void initGenerator(Long start) {
        Review.idGenerator = new LongGenerator(start);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review)) return false;
        Review review = (Review) o;
        return id.equals(review.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public ReviewStatus getStatus() {
        return status;
    }

    public void setStatus(ReviewStatus status) {
        this.status = status;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public Customer getAuthor() {
        return author;
    }

    public void setAuthor(Customer author) {
        this.author = author;
    }

    public Manifestation getManifestation() {
        return manifestation;
    }

    public void setManifestation(Manifestation manifestation) {
        this.manifestation = manifestation;
    }
}
