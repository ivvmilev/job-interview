package com.example.demo.entity;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    /**
     * Find user by name
     * @param name Given name to be searched
     * @return Optional User
     */
    Optional<User> findByName(String name);
}
