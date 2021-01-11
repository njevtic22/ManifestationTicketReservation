package repository;

import model.Salesman;
import serializer.serializer.FileSerializer;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class SalesmanJSONRepository implements UserRepository<Salesman> {
    // Filled in file deserializer
    private final Map<Long, Salesman> data = new TreeMap<>();
    private final FileSerializer<Salesman, Long> fileSerializer;

    public SalesmanJSONRepository(FileSerializer<Salesman, Long> fileSerializer) {
        this.fileSerializer = fileSerializer;
    }

    @Override
    public void save(Salesman entity) {
        data.put(entity.getId(), entity);
        fileSerializer.save(data);
    }

    @Override
    public Collection<Salesman> findAll() {
        return data.values();
    }

    @Override
    public Collection<Salesman> findAllByArchivedFalse() {
        Collection<Salesman> allSalesmen = this.findAll();
        return allSalesmen
                .stream()
                .filter(salesman -> !salesman.isArchived())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Salesman> findById(Long id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public Optional<Salesman> findByIdAndArchivedFalse(Long id) {
        Optional<Salesman> salesmanOptional = this.findById(id);

        if (salesmanOptional.isPresent()) {
            Salesman salesman = salesmanOptional.get();
            if (salesman.isArchived())
                return Optional.empty();
        }
        return salesmanOptional;
    }

    @Override
    public void delete(Salesman entity) {
        data.remove(entity.getId());
        fileSerializer.save(data);
    }

    @Override
    public long count() {
        return data.size();
    }

    @Override
    public Optional<Salesman> findByUserName(String userName) {
        for (Salesman salesman : data.values()) {
            if (salesman.getUsername().equals(userName))
                return Optional.of(salesman);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Salesman> findByUserNameAndArchivedFalse(String userName) {
        Optional<Salesman> salesmanOptional = this.findByUserName(userName);

        if (salesmanOptional.isPresent()) {
            Salesman salesman = salesmanOptional.get();
            if (salesman.isArchived())
                return Optional.empty();
        }
        return salesmanOptional;
    }

    Map<Long, Salesman> getData() {
        return data;
    }
}
