package repository;

import java.util.Collection;
import java.util.Optional;

public interface Repository<T, ID> {
    void save(T entity);
    Collection<T> findAll();
    Optional<T> findById(ID id);
    void delete(T entity);
    long count();
}
