package com.dbfinal.prac.repositories;

import com.dbfinal.prac.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findUserByName(String name);
    Optional<User> findByEmail(String email);

}
