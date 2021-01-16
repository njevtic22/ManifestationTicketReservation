package repository;

import model.Review;
import serializer.serializer.FileSerializer;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class ReviewJSONRepository implements Repository<Review, Long> {
    // Filled in file deserializer
    private final Map<Long, Review> data = new TreeMap<>();
    private final FileSerializer<Review, Long> fileSerializer;

    public ReviewJSONRepository(FileSerializer<Review, Long> fileSerializer) {
        this.fileSerializer = fileSerializer;
    }

    @Override
    public void save(Review entity) {
        data.put(entity.getId(), entity);
        fileSerializer.save(data);
    }

    @Override
    public Collection<Review> findAll() {
        return data.values();
    }

    @Override
    public Collection<Review> findAllByArchivedFalse() {
        return findAll()
                .stream()
                .filter(review -> !review.isArchived())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Review> findById(Long id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public Optional<Review> findByIdAndArchivedFalse(Long id) {
        Optional<Review> reviewOptional = findById(id);

        if (reviewOptional.isPresent()) {
            Review review = reviewOptional.get();
            if (review.isArchived())
                return Optional.empty();
        }

        return reviewOptional;
    }

    @Override
    public void delete(Review entity) {
        data.remove(entity.getId());
        fileSerializer.save(data);
    }

    @Override
    public long count() {
        return data.size();
    }

    Map<Long, Review> getData() {
        return data;
    }
}
