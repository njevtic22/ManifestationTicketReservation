package repository;

import model.Admin;
import serializer.serializer.FileSerializer;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class AdminJSONRepository implements Repository<Admin, Long>{
    // Filled in file deserializer
    private final Map<Long, Admin> data = new TreeMap<>();
    private final FileSerializer<Admin, Long> fileSerializer;

    public AdminJSONRepository(FileSerializer<Admin, Long> fileSerializer) {
        this.fileSerializer = fileSerializer;
    }

    @Override
    public void save(Admin entity) {
        data.put(entity.getId(), entity);
        fileSerializer.save(data);
    }

    @Override
    public Collection<Admin> findAll() {
        return data.values();
    }

    @Override
    public Optional<Admin> findById(Long id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public void delete(Admin entity) {
        data.remove(entity.getId());
        fileSerializer.save(data);
    }

    @Override
    public long count() {
        return data.size();
    }

    Map<Long, Admin> getData() {
        return data;
    }
}
