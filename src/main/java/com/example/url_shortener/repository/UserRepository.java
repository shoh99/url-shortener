package com.example.url_shortener.repository;

import com.example.url_shortener.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT user FROM User user WHERE LOWER(user.email) = :email")
    Optional<User> findUserByEmail(@Param("email") String email);
}
