package com.example.demo.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "meals")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private Integer calories;

    @NotNull
    private Integer protein;

    @NotNull
    private Integer carbs;

    @NotNull
    private Integer fat;

    @NotNull
    private Integer grams; // Gramm-Anzeige für das Essen

    @NotNull
    private Integer calcium;

    @NotNull
    private Integer sodium;

    @NotNull
    private Integer vitaminC;

    @NotNull
    private Integer vitaminD;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) 
    @JsonBackReference
    private User user; // Verweis auf den Benutzer

    // Standard-Konstruktor
    public Meal() {}

    // Parameterisierter Konstruktor für einfachere Erstellung
    public Meal(String name, int grams, int carbs, int fat, int protein, int calories, int calcium, int sodium, int vitaminC, int vitaminD, User user) {
        this.name = name;
        this.grams = grams;
        this.carbs = carbs;
        this.fat = fat;
        this.protein = protein;
        this.calories = calories;
        this.calcium = calcium;
        this.sodium = sodium;
        this.vitaminC = vitaminC;
        this.vitaminD = vitaminD;
        this.user = user;
    }

    public void setUserId(Long userId) {
        if (this.user == null) {
            this.user = new User();
        }
        this.user.setId(userId);
    }
}
