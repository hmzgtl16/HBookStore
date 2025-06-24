package org.example.hbookstore.repository;

import org.example.hbookstore.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
