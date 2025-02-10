package com.example.demo.model.entities;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "nutrition_plans")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class NutritionPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId; // Hinzugefügtes Feld

    private String ernaehrungsziel;
    private double totalProteins;
    private double totalFats;
    private double totalCarbs;
    private double totalCalories;
    private double totalVitaminD;
    private double totalVitaminC;
    private double totalCalcium;
    private double totalSodium;

    @ElementCollection
    @CollectionTable(name = "nutrition_plan_meals", joinColumns = @JoinColumn(name = "nutrition_plan_id"))
    @Column(name = "meal")
    private Set<Meal> meals;

    public void updateWithMeal(Meal meal) {
        this.totalCalories -= meal.getCalories();
        this.totalCarbs += meal.getCarbs();
        this.totalFats += meal.getFat();
        this.totalProteins += meal.getProtein();
        this.totalCalcium += meal.getCalcium();
        this.totalSodium += meal.getSodium();
        this.totalVitaminC += meal.getVitaminC();
        this.totalVitaminD += meal.getVitaminD();
        this.meals.add(meal);
    }

    public void setRemainingCalories(int calorieGoal) {
        this.totalCalories = calorieGoal - this.totalCalories;
    }

    // Getter und Setter für alle Felder
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getErnaehrungsziel() {
        return ernaehrungsziel;
    }

    public void setErnaehrungsziel(String ernaehrungsziel) {
        this.ernaehrungsziel = ernaehrungsziel;
    }

    public double getTotalProteins() {
        return totalProteins;
    }

    public void setTotalProteins(double totalProteins) {
        this.totalProteins = totalProteins;
    }

    public double getTotalFats() {
        return totalFats;
    }

    public void setTotalFats(double totalFats) {
        this.totalFats = totalFats;
    }

    public double getTotalCarbs() {
        return totalCarbs;
    }

    public void setTotalCarbs(double totalCarbs) {
        this.totalCarbs = totalCarbs;
    }

    public double getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(double totalCalories) {
        this.totalCalories = totalCalories;
    }

    public double getTotalVitaminD() {
        return totalVitaminD;
    }

    public void setTotalVitaminD(double totalVitaminD) {
        this.totalVitaminD = totalVitaminD;
    }

    public double getTotalVitaminC() {
        return totalVitaminC;
    }

    public void setTotalVitaminC(double totalVitaminC) {
        this.totalVitaminC = totalVitaminC;
    }

    public double getTotalCalcium() {
        return totalCalcium;
    }

    public void setTotalCalcium(double totalCalcium) {
        this.totalCalcium = totalCalcium;
    }

    public double getTotalSodium() {
        return totalSodium;
    }

    public void setTotalSodium(double totalSodium) {
        this.totalSodium = totalSodium;
    }

    public Set<Meal> getMeals() {
        return meals;
    }

    public void setMeals(Set<Meal> meals) {
        this.meals = meals;
    }
}