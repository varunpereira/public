package com.rmit.sept.bk_loginservices.Repositories;

import com.rmit.sept.bk_loginservices.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    // Finds the user by username
    User findByUsername(String username);
    // Finds the user by ID
    User getById(Long id);
    // deletes user
    void delete(User user);
    // Security - comparing the token, username and account type in a single query
    // 1. Finds Username
    // 2. Finds Token
    User findByUsernameAndToken(String username, String token);
    // finds all users
    List<User> findAll();
    // checks if a user exists
    boolean existsByUsername(String username);
}
