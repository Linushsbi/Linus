package com.example.demo.controller;

import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.entities.Meal;
import com.example.demo.model.entities.NutritionPlan;
import com.example.demo.service.NutritionPlanService;

@RestController
@RequestMapping("/api/nutrition-plan")
public class NutritionPlanController {

    private final NutritionPlanService nutritionPlanService;

    public NutritionPlanController(NutritionPlanService nutritionPlanService) {
        this.nutritionPlanService = nutritionPlanService;
    }

    @GetMapping(value = "/{planId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NutritionPlan> getNutritionPlan(@PathVariable Long planId) {
        Optional<NutritionPlan> plan = nutritionPlanService.getNutritionPlanById(planId);
        return plan.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/{planId}/add-meal", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NutritionPlan> addMealToPlan(@PathVariable Long planId, @RequestBody Meal meal) {
        try {
            NutritionPlan updatedPlan = nutritionPlanService.addMealToPlan(planId, meal);
            return ResponseEntity.ok(updatedPlan);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/{planId}/set-goal", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> setCalorieGoal(@PathVariable Long planId, @RequestBody Integer calorieGoal) {
        try {
            nutritionPlanService.setCalorieGoal(planId, calorieGoal);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/user/{userId}/reset-meals", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> resetDailyMeals(@PathVariable Long userId) {
        nutritionPlanService.resetDailyMeals(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NutritionPlan> getNutritionPlanByUserId(@PathVariable Long userId) {
        Optional<NutritionPlan> plan = nutritionPlanService.getNutritionPlanByUserId(userId);
        return plan.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}