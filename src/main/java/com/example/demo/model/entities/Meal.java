package com.example.demo.model.entities;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "meals")
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "calories")
    private Integer calories;

    @NotNull
    @Column(name = "proteins")
    private Integer protein;

    @NotNull
    @Column(name = "carbs")
    private Integer carbs;

    @NotNull
    @Column(name = "fats")
    private Integer fats;

    @NotNull
    @Column(name = "weight")
    private Integer grams; // Umbenannt für den Code

    @NotNull
    @Column(name = "calcium")
    private Integer calcium;

    @NotNull
    @Column(name = "sodium")
    private Integer sodium;

    @NotNull
    @Column(name = "vitamin_c")
    private Integer vitaminC;

    @NotNull
    @Column(name = "vitamin_d")
    private Integer vitaminD;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    // Getter und Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public Integer getProtein() {
        return protein;
    }

    public void setProtein(Integer protein) {
        this.protein = protein;
    }

    public Integer getCarbs() {
        return carbs;
    }

    public void setCarbs(Integer carbs) {
        this.carbs = carbs;
    }

    public Integer getFats() {
        return fats;
    }

    public void setFats(Integer fats) {
        this.fats = fats;
    }

    public Integer getGrams() {
        return grams;
    }

    public void setGrams(Integer grams) {
        this.grams = grams;
    }

    public Integer getCalcium() {
        return calcium;
    }

    public void setCalcium(Integer calcium) {
        this.calcium = calcium;
    }

    public Integer getSodium() {
        return sodium;
    }

    public void setSodium(Integer sodium) {
        this.sodium = sodium;
    }

    public Integer getVitaminC() {
        return vitaminC;
    }

    public void setVitaminC(Integer vitaminC) {
        this.vitaminC = vitaminC;
    }

    public Integer getVitaminD() {
        return vitaminD;
    }

    public void setVitaminD(Integer vitaminD) {
        this.vitaminD = vitaminD;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
