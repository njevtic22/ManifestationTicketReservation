package repository;

import model.WithdrawalHistory;
import serializer.serializer.FileSerializer;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class WithdrawalHistoryJSONRepository implements Repository<WithdrawalHistory, Long> {
    // Filled in file deserializer
    private final Map<Long, WithdrawalHistory> data = new TreeMap<>();
    private final FileSerializer<WithdrawalHistory, Long> fileSerializer;

    public WithdrawalHistoryJSONRepository(FileSerializer<WithdrawalHistory, Long> fileSerializer) {
        this.fileSerializer = fileSerializer;
    }

    @Override
    public void save(WithdrawalHistory entity) {
        data.put(entity.getId(), entity);
        fileSerializer.save(data);
    }

    @Override
    public Collection<WithdrawalHistory> findAll() {
        return data.values();
    }

    @Override
    public Collection<WithdrawalHistory> findAllByArchivedFalse() {
        return findAll()
                .stream()
                .filter(history -> !history.isArchived())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<WithdrawalHistory> findById(Long id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public Optional<WithdrawalHistory> findByIdAndArchivedFalse(Long id) {
        Optional<WithdrawalHistory> historyOptional = findById(id);

        if (historyOptional.isPresent()) {
            WithdrawalHistory history = historyOptional.get();
            if (history.isArchived())
                return Optional.empty();
        }

        return historyOptional;
    }

    @Override
    public void delete(WithdrawalHistory entity) {
        data.remove(entity.getId());
        fileSerializer.save(data);
    }

    @Override
    public long count() {
        return data.size();
    }

    Map<Long, WithdrawalHistory> getData() {
        return data;
    }
}
