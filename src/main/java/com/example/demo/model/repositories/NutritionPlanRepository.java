package com.example.demo.model.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.entities.NutritionPlan;

public interface NutritionPlanRepository extends JpaRepository<NutritionPlan, Long> {
    Optional<NutritionPlan> findByUserId(Long userId);
}
