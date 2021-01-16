package repository;

import model.Manifestation;
import serializer.serializer.FileSerializer;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class ManifestationJSONRepository implements Repository<Manifestation, Long> {
    // Filled in file deserializer
    private final Map<Long, Manifestation> data = new TreeMap<>();
    private final FileSerializer<Manifestation, Long> fileSerializer;

    public ManifestationJSONRepository(FileSerializer<Manifestation, Long> fileSerializer) {
        this.fileSerializer = fileSerializer;
    }

    @Override
    public void save(Manifestation entity) {
        data.put(entity.getId(), entity);
        fileSerializer.save(data);
    }

    @Override
    public Collection<Manifestation> findAll() {
        return data.values();
    }

    @Override
    public Collection<Manifestation> findAllByArchivedFalse() {
        return findAll()
                .stream()
                .filter(manifestation -> !manifestation.isArchived())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Manifestation> findById(Long id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public Optional<Manifestation> findByIdAndArchivedFalse(Long id) {
        Optional<Manifestation> manifestationOptional = findById(id);

        if (manifestationOptional.isPresent()) {
            Manifestation manifestation = manifestationOptional.get();
            if (manifestation.isArchived())
                return Optional.empty();
        }

        return manifestationOptional;
    }

    @Override
    public void delete(Manifestation entity) {
        data.remove(entity.getId());
        fileSerializer.save(data);
    }

    @Override
    public long count() {
        return data.size();
    }

    Map<Long, Manifestation> getData() {
        return data;
    }
}
