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
    public ResponseEntity<NutritionPlan> getNutritionPlan(@PathVariable String planId) {
        try {
            Long parsedPlanId = Long.valueOf(planId); // Konvertiert den String in Long
            Optional<NutritionPlan> plan = nutritionPlanService.getNutritionPlanById(parsedPlanId);
            return plan.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(null); // Ungültiges Format
        }
    }

    @PostMapping(value = "/{planId}/add-meal", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NutritionPlan> addMealToPlan(@PathVariable String planId, @RequestBody Meal meal) {
        try {
            Long parsedPlanId = Long.valueOf(planId);
            NutritionPlan updatedPlan = nutritionPlanService.addMealToPlan(parsedPlanId, meal);
            return ResponseEntity.ok(updatedPlan);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/{planId}/set-goal", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> setCalorieGoal(@PathVariable String planId, @RequestBody Integer calorieGoal) {
        try {
            Long parsedPlanId = Long.valueOf(planId);
            nutritionPlanService.setCalorieGoal(parsedPlanId, calorieGoal);
            return ResponseEntity.ok().build();
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/user/{userId}/reset-meals", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> resetDailyMeals(@PathVariable String userId) {
        try {
            Long parsedUserId = Long.valueOf(userId);
            nutritionPlanService.resetDailyMeals(parsedUserId);
            return ResponseEntity.ok().build();
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NutritionPlan> getNutritionPlanByUserId(@PathVariable String userId) {
        try {
            Long parsedUserId = Long.valueOf(userId);
            Optional<NutritionPlan> plan = nutritionPlanService.getNutritionPlanByUserId(parsedUserId);
            return plan.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
