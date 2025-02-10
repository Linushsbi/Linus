package com.example.demo.service;

import java.util.HashSet;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.entities.Meal;
import com.example.demo.model.entities.NutritionPlan;
import com.example.demo.model.repositories.MealRepository;
import com.example.demo.model.repositories.NutritionPlanRepository;

@Service
public class NutritionPlanService {

    private final NutritionPlanRepository nutritionPlanRepository;
    private final MealRepository mealRepository;

    public NutritionPlanService(NutritionPlanRepository nutritionPlanRepository, MealRepository mealRepository) {
        this.nutritionPlanRepository = nutritionPlanRepository;
        this.mealRepository = mealRepository;
    }

    public Optional<NutritionPlan> getNutritionPlanByUserId(Long userId) {
        System.out.println("Fetching Nutrition Plan for user ID: " + userId);
        Optional<NutritionPlan> plan = nutritionPlanRepository.findByUserId(userId);
    
        if (plan.isEmpty()) {
            System.out.println("No Nutrition Plan found for user ID: " + userId);
        } else {
            System.out.println("Nutrition Plan found: " + plan.get());
        }
    
        return plan;
    }

    @Transactional
    public NutritionPlan addMealToPlan(Long planId, Meal meal) {
        Optional<NutritionPlan> planOpt = nutritionPlanRepository.findById(planId);
        if (planOpt.isPresent()) {
            NutritionPlan plan = planOpt.get();
            plan.updateWithMeal(meal);
            mealRepository.save(meal);
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
        newPlan.setUserId(userId);
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
        return nutritionPlanRepository.save(newPlan);
    }

    @Transactional
    public void resetDailyMeals(Long userId) {
        Optional<NutritionPlan> planOpt = nutritionPlanRepository.findByUserId(userId);
        if (planOpt.isPresent()) {
            NutritionPlan plan = planOpt.get();
            plan.setMeals(new HashSet<>());
            nutritionPlanRepository.save(plan);
        } else {
            throw new IllegalArgumentException("Nutrition plan not found");
        }
    }
    public Optional<NutritionPlan> getNutritionPlanById(Long id) {
        return nutritionPlanRepository.findById(id);
    }
}


