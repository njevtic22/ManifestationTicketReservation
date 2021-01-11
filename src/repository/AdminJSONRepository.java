package repository;

import model.Admin;
import serializer.serializer.FileSerializer;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class AdminJSONRepository implements UserRepository<Admin>{
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
    public Collection<Admin> findAllByArchivedFalse() {
        Collection<Admin> allAdmins = this.findAll();
        return allAdmins
                .stream()
                .filter(admin -> !admin.isArchived())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Admin> findById(Long id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public Optional<Admin> findByIdAndArchivedFalse(Long id) {
        Optional<Admin> adminOptional = this.findById(id);

        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            if (admin.isArchived())
                return Optional.empty();
        }
        return adminOptional;
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

    @Override
    public Optional<Admin> findByUserName(String userName) {
        for (Admin admin : data.values()) {
            if (admin.getUsername().equals(userName))
                return Optional.of(admin);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Admin> findByUserNameAndArchivedFalse(String userName) {
        Optional<Admin> adminOptional = this.findByUserName(userName);

        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            if (admin.isArchived())
                return Optional.empty();
        }
        return adminOptional;
    }

    Map<Long, Admin> getData() {
        return data;
    }
}
