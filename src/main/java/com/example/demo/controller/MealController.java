package com.example.demo.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.entities.Meal;
import com.example.demo.service.MealService;

@RestController
@RequestMapping("/api/meals")
public class MealController {

    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
        System.out.println("MealController gestartet");
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> addMeal(@RequestBody Meal meal) {
        Meal savedMeal = mealService.saveMeal(meal);
        return ResponseEntity.ok(savedMeal);
    }

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Meal>> getMeals(@PathVariable Long userId) {
        List<Meal> meals = mealService.getMealsForUser(userId);
        
        // Stelle sicher, dass eine leere Liste statt null zurückgegeben wird
        if (meals == null) {
            meals = Collections.emptyList();
        }
        
        System.out.println("Fetched meals: " + meals); // Debug-Ausgabe
        return ResponseEntity.ok(meals);
    }

    @DeleteMapping("/{mealId}")
    public ResponseEntity<Void> deleteMeal(@PathVariable Long mealId) {
        mealService.deleteMeal(mealId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/remaining-calories/{userId}")
    public ResponseEntity<Integer> getRemainingCalories(
            @PathVariable Long userId, @RequestParam Integer dailyCalorieGoal) {
        return ResponseEntity.ok(mealService.calculateRemainingCalories(userId, dailyCalorieGoal));
    }
}
