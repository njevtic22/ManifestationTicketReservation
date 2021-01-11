package repository;

import model.Admin;

import java.util.Optional;

public interface UserRepository<T> extends Repository<T, Long> {
    Optional<T> findByUserName(String userName);
    Optional<T> findByUserNameAndArchivedFalse(String userName);
}
