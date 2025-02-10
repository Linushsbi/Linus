package com.example.demo.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.entities.Meal;
import com.example.demo.model.entities.NutritionPlan;
import com.example.demo.model.repositories.NutritionPlanRepository;

@Service
public class NutritionPlanService {

    private final NutritionPlanRepository nutritionPlanRepository;

    public NutritionPlanService(NutritionPlanRepository nutritionPlanRepository) {
        this.nutritionPlanRepository = nutritionPlanRepository;
    }

    public Optional<NutritionPlan> getNutritionPlanById(Long id) {
        return nutritionPlanRepository.findById(id);
    }

    public Optional<NutritionPlan> getNutritionPlanByUserId(Long userId) {
        String currentDate = LocalDate.now().toString();
    
        Optional<NutritionPlan> existingPlan = nutritionPlanRepository.findAll().stream()
                .filter(plan -> plan.getUserId().equals(userId))
                .findFirst();
    
        if (existingPlan.isPresent()) {
            String storedId = String.valueOf(existingPlan.get().getId());
            String storedDate = storedId.substring(3); // Datum extrahieren
            if (!storedDate.equals(currentDate)) {
                return Optional.of(createNewNutritionPlan(userId, (int) existingPlan.get().getTotalCalories())); // Neuer Plan für den Tag
            }
            return existingPlan;
        }
    
        return Optional.of(createNewNutritionPlan(userId, 2000)); // Falls kein Plan existiert, Standard-Kalorienziel 2000
    }

    @Transactional
    public NutritionPlan addMealToPlan(Long planId, Meal meal) {
        Optional<NutritionPlan> planOpt = nutritionPlanRepository.findById(planId);
        if (planOpt.isPresent()) {
            NutritionPlan plan = planOpt.get();

            // Update der Werte
            plan.updateWithMeal(meal);

            return nutritionPlanRepository.save(plan);
        }
        throw new IllegalArgumentException("Nutrition plan not found");
    }

    @Transactional
    public void setCalorieGoal(Long planId, int calorieGoal) {
        Optional<NutritionPlan> planOpt = nutritionPlanRepository.findById(planId);
        if (planOpt.isPresent()) {
            NutritionPlan plan = planOpt.get();
            plan.setTotalCalories(calorieGoal);
            plan.setRemainingCalories(calorieGoal);
            nutritionPlanRepository.save(plan);
        } else {
            throw new IllegalArgumentException("Nutrition plan not found");
        }
    }

    private NutritionPlan createNewNutritionPlan(Long userId, int calorieGoal) {
        NutritionPlan newPlan = new NutritionPlan();
        newPlan.setUserId(userId); // Setze die userId
        newPlan.setErnaehrungsziel("Erhalt");
        newPlan.setTotalProteins(0);
        newPlan.setTotalFats(0);
        newPlan.setTotalCarbs(0);
        newPlan.setTotalCalories(calorieGoal);
        newPlan.setTotalVitaminD(0);
        newPlan.setTotalVitaminC(0);
        newPlan.setTotalCalcium(0);
        newPlan.setTotalSodium(0);
        newPlan.setMeals(new HashSet<>());

        // Setze die ID basierend auf der dreistelligen ID und dem aktuellen Datum
        String currentDate = LocalDate.now().toString();
        String planIdWithDate = String.format("%03d", userId % 1000) + currentDate;
        newPlan.setId(Long.parseLong(planIdWithDate));

        return nutritionPlanRepository.save(newPlan);
    }

    @Transactional
    public void resetDailyMeals(Long userId) {
        Optional<NutritionPlan> planOpt = nutritionPlanRepository.findAll().stream()
                .filter(plan -> plan.getUserId().equals(userId))
                .findFirst();
        if (planOpt.isPresent()) {
            NutritionPlan plan = planOpt.get();
            plan.setMeals(new HashSet<>()); // Setze das Meal-Set auf leer
            nutritionPlanRepository.save(plan);
        } else {
            throw new IllegalArgumentException("Nutrition plan not found");
        }
    }
}