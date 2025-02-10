package com.example.demo.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entities.Meal;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findByUser_Id(Long userId); // Findet Mahlzeiten für einen bestimmten Benutzer
}
