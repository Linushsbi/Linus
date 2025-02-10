package com.example.demo.model.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entities.User;

@Repository
public interface ApplicationUserRepository extends JpaRepository<User, Long> {

    // Benutzer anhand von Benutzername finden
    @Query("SELECT u FROM User u WHERE u.benutzername = :benutzername")
    Optional<User> findByBenutzername(@Param("benutzername") String benutzername);

    // Benutzer anhand der ID finden (JpaRepository stellt automatisch findById bereit)
    default Optional<User> findUserById(Long id) {
        return findById(id);
    }
}
