package repository;

import model.Customer;
import serializer.serializer.FileSerializer;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class CustomerJSONRepository implements UserRepository<Customer> {
    // Filled in file deserializer
    private final Map<Long, Customer> data = new TreeMap<>();
    private final FileSerializer<Customer, Long> fileSerializer;

    public CustomerJSONRepository(FileSerializer<Customer, Long> fileSerializer) {
        this.fileSerializer = fileSerializer;
    }

    @Override
    public void save(Customer entity) {
        data.put(entity.getId(), entity);
        fileSerializer.save(data);
    }

    @Override
    public Collection<Customer> findAll() {
        return data.values();
    }

    @Override
    public Collection<Customer> findAllByArchivedFalse() {
        return this.findAll()
                .stream()
                .filter(customer -> !customer.isArchived())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public Optional<Customer> findByIdAndArchivedFalse(Long id) {
        Optional<Customer> customerOptional = this.findById(id);

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            if (customer.isArchived())
                return Optional.empty();
        }

        return customerOptional;
    }

    @Override
    public void delete(Customer entity) {
        data.remove(entity.getId());
        fileSerializer.save(data);
    }

    @Override
    public long count() {
        return data.size();
    }

    @Override
    public Optional<Customer> findByUserName(String userName) {
        for (Customer customer : data.values()) {
            if (customer.getUsername().equals(userName))
                return Optional.of(customer);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Customer> findByUserNameAndArchivedFalse(String userName) {
        Optional<Customer> customerOptional = this.findByUserName(userName);

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            if (customer.isArchived())
                return Optional.empty();
        }
        return customerOptional;
    }

    Map<Long, Customer> getData() {
        return data;
    }
}
