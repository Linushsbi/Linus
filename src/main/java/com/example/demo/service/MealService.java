package com.example.demo.service;

import org.springframework.stereotype.Service;
import com.example.demo.model.repositories.MealRepository;
import com.example.demo.model.entities.Meal;


import java.util.List;

@Service
public class MealService {

    private final MealRepository mealRepository;

    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public Meal saveMeal(Meal meal) {
        return mealRepository.save(meal);
    }

    public List<Meal> getMealsForUser(Long userId) {
        return mealRepository.findByUser_Id(userId);
    }

    public void deleteMeal(Long mealId) {
        mealRepository.deleteById(mealId);
    }

    public Integer calculateRemainingCalories(Long userId, Integer dailyCalorieGoal) {
        List<Meal> meals = getMealsForUser(userId);
        int totalCaloriesConsumed = meals.stream().mapToInt(Meal::getCalories).sum();
        return dailyCalorieGoal - totalCaloriesConsumed;
    }
}
