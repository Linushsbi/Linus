package com.example.demo.views;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.entities.Meal;
import com.example.demo.model.entities.NutritionPlan;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route(value = "mitglied/ernaehrungsplan")
@PageTitle("Nutrition Plan")
@RolesAllowed("MITGLIED")
public class NutritionPlanView extends VerticalLayout {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "http://localhost:8080/api/nutrition-plan";
    private List<Meal> meals = new ArrayList<>();
    private Grid<Meal> mealGrid;
    private int dailyCalorieGoal = 2000;
    private int remainingCalories;
    private Span calorieCounter;

    public NutritionPlanView() {
        setSpacing(true);
        setPadding(true);
        setAlignItems(Alignment.CENTER);

        H2 header = new H2("My Nutrition Plan");

        calorieCounter = new Span(getCalorieSummary());
        calorieCounter.getStyle().set("font-weight", "bold");
        calorieCounter.getStyle().set("font-size", "18px");
        calorieCounter.getStyle().set("color", "green");

        Button homeButton = new Button("Home", event -> getUI().ifPresent(ui -> ui.navigate("mitglied-dashboard")));
        Button setGoalButton = new Button("Ziel setzen", event -> openGoalPopup());
        Button addFoodButton = new Button("Add Food", event -> openAddFoodDialog());
        Button pdfExportButton = new Button("Export PDF", event -> Notification.show("PDF export triggered!", 3000, Notification.Position.MIDDLE));

        HorizontalLayout buttonLayout = new HorizontalLayout(homeButton, setGoalButton, addFoodButton, pdfExportButton);

        mealGrid = new Grid<>(Meal.class, false);
        mealGrid.addColumn(Meal::getName).setHeader("Meal Name").setSortable(true);
        mealGrid.addColumn(Meal::getGrams).setHeader("Weight (g)");
        mealGrid.addColumn(Meal::getCarbs).setHeader("Carbs (g)");
        mealGrid.addColumn(Meal::getFat).setHeader("Fats (g)");
        mealGrid.addColumn(Meal::getProtein).setHeader("Proteins (g)");
        mealGrid.addColumn(Meal::getCalories).setHeader("Calories (kcal)");
        mealGrid.addColumn(Meal::getCalcium).setHeader("Calcium (mg)");
        mealGrid.addColumn(Meal::getSodium).setHeader("Sodium (mg)");
        mealGrid.addColumn(Meal::getVitaminC).setHeader("Vitamin C (mg)");
        mealGrid.addColumn(Meal::getVitaminD).setHeader("Vitamin D (IU)");
        mealGrid.addComponentColumn(this::createDeleteButton).setHeader("Actions");

        add(header, calorieCounter, buttonLayout, mealGrid);
        initializeNutritionPlan();
        loadNutritionPlan();
        loadMeals();
    }

    private String getCalorieSummary() {
        return "Remaining Calories: " + remainingCalories + " kcal";
    }

    private void loadMeals() {
        try {
            Meal[] backendMeals = restTemplate.getForObject(baseUrl + "/meals", Meal[].class);
            meals = backendMeals != null ? List.of(backendMeals) : new ArrayList<>();
            mealGrid.setItems(meals);
        } catch (Exception e) {
            Notification.show("Error loading meals: " + e.getMessage(), 5000, Notification.Position.MIDDLE);
        }
    }

    private void updateCalorieCounter() {
        calorieCounter.setText(getCalorieSummary());
    }

    private Long getCurrentNutritionPlanId() {
        try {
            NutritionPlan plan = restTemplate.getForObject(baseUrl + "/user/{userId}", NutritionPlan.class, getCurrentUserId());
            return plan != null ? plan.getId() : null;
        } catch (Exception e) {
            Notification.show("Error retrieving nutrition plan: " + e.getMessage(), 5000, Notification.Position.MIDDLE);
            return null;
        }
    }

    private Long getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return 1L; // Hier kann die Benutzer-ID aus der Datenbank geladen werden
        }
        return null;
    }

    private void openGoalPopup() {
        Dialog goalDialog = new Dialog();
        goalDialog.add(new Span("Kalorienziel setzen: Aufbau +500, Abnehmen -500, Erhalt 0."));
        NumberField calorieField = new NumberField("Kalorienziel");
        Button saveButton = new Button("Speichern", event -> {
            dailyCalorieGoal = calorieField.getValue().intValue();
            saveCalorieGoal(dailyCalorieGoal);
            updateCalorieCounter();
            goalDialog.close();
        });
        goalDialog.add(calorieField, saveButton);
        goalDialog.open();
    }

    private Button createDeleteButton(Meal meal) {
        return new Button("Delete", event -> {
            restTemplate.delete(baseUrl + "/" + meal.getId());
            loadMeals();
        });
    }

    private void saveMealToDatabase(Meal meal) {
        try {
            restTemplate.postForObject(baseUrl + "/meals", meal, Meal.class);
            Notification.show("Meal saved successfully!", 3000, Notification.Position.MIDDLE);
            loadMeals(); // Nach dem Speichern die Mahlzeiten neu laden
        } catch (Exception e) {
            Notification.show("Error saving meal: " + e.getMessage(), 5000, Notification.Position.MIDDLE);
        }
    }

    private void resetDailyMeals(Long userId) {
        restTemplate.delete(baseUrl + "/user/" + userId + "/reset-meals");
    }

    private void initializeNutritionPlan() {
        Long userId = getCurrentUserId();
        String currentDate = java.time.LocalDate.now().toString();
        String planIdWithDate = restTemplate.getForObject(baseUrl + "/user/" + userId + "/date", String.class);

        if (planIdWithDate == null || !planIdWithDate.substring(3).equals(currentDate)) {
            resetDailyMeals(userId);
            String newPlanIdWithDate = String.format("%03d", userId % 1000) + currentDate;
            restTemplate.postForObject(baseUrl + "/user/" + userId + "/set-date", newPlanIdWithDate, Void.class);
        }
    }

    private void loadNutritionPlan() {
        try {
            NutritionPlan plan = restTemplate.getForObject(baseUrl + "/user/{userId}", NutritionPlan.class, getCurrentUserId());
            if (plan != null) {
                dailyCalorieGoal = (int) plan.getTotalCalories();
                updateCalorieCounter();
            }
        } catch (Exception e) {
            Notification.show("Error loading nutrition plan: " + e.getMessage(), 5000, Notification.Position.MIDDLE);
        }
    }

    private void saveCalorieGoal(int calorieGoal) {
        Long nutritionPlanId = getCurrentNutritionPlanId();
        if (nutritionPlanId != null) {
            restTemplate.postForObject(baseUrl + "/" + nutritionPlanId + "/set-goal", calorieGoal, Void.class);
        }
    }

    private void openAddFoodDialog() {
        Dialog foodDialog = new Dialog();
        foodDialog.setHeaderTitle("Add Food");

        TextField foodNameField = new TextField("Meal Name");
        NumberField weightField = new NumberField("Weight (g)");
        NumberField carbsField = new NumberField("Carbs (g)");
        NumberField fatsField = new NumberField("Fats (g)");
        NumberField proteinsField = new NumberField("Proteins (g)");
        NumberField caloriesField = new NumberField("Calories (kcal)");
        NumberField calciumField = new NumberField("Calcium (mg)");
        NumberField sodiumField = new NumberField("Sodium (mg)");
        NumberField vitaminCField = new NumberField("Vitamin C (mg)");
        NumberField vitaminDField = new NumberField("Vitamin D (IU)");

        Button saveButton = new Button("Save", event -> {
            if (foodNameField.isEmpty() || weightField.isEmpty() || carbsField.isEmpty() || 
                fatsField.isEmpty() || proteinsField.isEmpty() || caloriesField.isEmpty()) {
                Notification.show("All fields are required!", 3000, Notification.Position.MIDDLE);
            } else {
                Meal meal = new Meal();
                meal.setName(foodNameField.getValue());
                meal.setGrams(weightField.getValue().intValue());
                meal.setCarbs(carbsField.getValue().intValue());
                meal.setFat(fatsField.getValue().intValue());
                meal.setProtein(proteinsField.getValue().intValue());
                meal.setCalories(caloriesField.getValue().intValue());
                meal.setCalcium(calciumField.getValue().intValue());
                meal.setSodium(sodiumField.getValue().intValue());
                meal.setVitaminC(vitaminCField.getValue().intValue());
                meal.setVitaminD(vitaminDField.getValue().intValue());

                saveMealToDatabase(meal);
                foodDialog.close();
            }
        });

        foodDialog.add(foodNameField, weightField, carbsField, fatsField, proteinsField, caloriesField, 
                       calciumField, sodiumField, vitaminCField, vitaminDField, saveButton);
        foodDialog.open();
    }
}