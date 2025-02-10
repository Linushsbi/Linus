package com.example.demo.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.entities.CustomerProfile;


@Repository
public interface CustomerProfileRepository extends JpaRepository<CustomerProfile, Long> {
}
