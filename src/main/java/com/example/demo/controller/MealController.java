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

    // POST /api/meals
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> addMeal(@RequestBody Meal meal) {
        try {
            Meal savedMeal = mealService.saveMeal(meal);
            return ResponseEntity.ok(savedMeal);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build(); // Fehler bei der Verarbeitung
        }
    }

    // GET /api/meals/{userId}
    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Meal>> getMeals(@PathVariable String userId) {
        try {
            Long parsedUserId = Long.valueOf(userId);
            List<Meal> meals = mealService.getMealsForUser(parsedUserId);

            // Rückgabe einer leeren Liste statt null
            if (meals == null) {
                meals = Collections.emptyList();
            }

            System.out.println("Fetched meals: " + meals); // Debug-Ausgabe
            return ResponseEntity.ok(meals);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build(); // Fehlerhafte Eingabe
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build(); // Allgemeiner Fehler
        }
    }

    // GET /api/meals
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Meal>> getAllMeals() {
        List<Meal> meals = mealService.getAllMeals();
        return ResponseEntity.ok(meals);
    }

    // DELETE /api/meals/{mealId}
    @DeleteMapping("/{mealId}")
    public ResponseEntity<Void> deleteMeal(@PathVariable String mealId) {
        try {
            Long parsedMealId = Long.valueOf(mealId);
            mealService.deleteMeal(parsedMealId);
            return ResponseEntity.noContent().build();
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // GET /api/meals/remaining-calories/{userId}
    @GetMapping("/remaining-calories/{userId}")
    public ResponseEntity<Integer> getRemainingCalories(
            @PathVariable String userId, @RequestParam Integer dailyCalorieGoal) {
        try {
            Long parsedUserId = Long.valueOf(userId);
            return ResponseEntity.ok(mealService.calculateRemainingCalories(parsedUserId, dailyCalorieGoal));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
