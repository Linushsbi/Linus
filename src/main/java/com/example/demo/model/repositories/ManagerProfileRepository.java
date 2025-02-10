package com.example.demo.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.entities.ManagerProfile;

@Repository
public interface ManagerProfileRepository extends JpaRepository<ManagerProfile, Long> {
}
