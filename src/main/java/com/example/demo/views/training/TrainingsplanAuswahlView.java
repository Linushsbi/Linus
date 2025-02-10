package com.example.demo.views.training;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.entities.Training;
import com.example.demo.model.entities.Trainingsplan;
import com.example.demo.service.TrainingService;
import com.example.demo.service.TrainingsplanService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@PageTitle("Trainingsplan auswählen")
@Route(value = "trainingsplanauswahl")
@PermitAll
@Uses(Icon.class)
public class TrainingsplanAuswahlView extends VerticalLayout{

    private TrainingsplanService trainingsplanService;
    private TrainingService trainingService;
    private VerticalLayout vlGrid = new VerticalLayout();
    private Grid<Trainingsplan> gridTrainingsplan = new Grid<>(Trainingsplan.class, false);
    private HorizontalLayout hlToolbar = new HorizontalLayout();
    private TextField tfFilterName = new TextField();
    private Button btnChoose = new Button("Trainingsplan auswählen");
    private Button btnExportPdf = new Button("PDF Exportieren", VaadinIcon.FILE_TEXT.create());
    private Button btnHome = new Button("Home");
    private Button btnEdit = new Button("Trainingsplan bearbeiten");
    private HorizontalLayout hlButtons_Trainingsplan = new HorizontalLayout(btnChoose, btnExportPdf, btnHome, btnEdit);
    private Dialog trainingDialog = new Dialog();
    private Grid<Training> gridTraining = new Grid<>(Training.class, false);
    private Grid<Training> currentTrainingsGrid = new Grid<>(Training.class, false);
    private Grid<Training> allTrainingsGrid = new Grid<>(Training.class, false);
    private Button btnStarten = new Button("Training starten");
    private Button btnAbbrechen = new Button("Abbrechen");
    private HorizontalLayout hlButtons_Training = new HorizontalLayout(btnStarten, btnAbbrechen);

    @Autowired
    public TrainingsplanAuswahlView(TrainingsplanService trainingsplanService, TrainingService trainingService) {
        this.trainingsplanService = trainingsplanService;
        this.trainingService = trainingService;

        // PDF Export-Button konfigurieren
        btnExportPdf.addClickListener(e -> {
            Trainingsplan selectedTrainingsplan = getSelectedTrainingsplan();
            if (selectedTrainingsplan != null) {
                // Aktualisierte Daten direkt abrufen
                Trainingsplan refreshedPlan = refreshTrainingsplan(selectedTrainingsplan.getId());
                if (refreshedPlan.getTrainings().isEmpty()) {
                    Notification.show("Der ausgewählte Trainingsplan enthält keine Übungen.", 3000, Notification.Position.MIDDLE);
                } else {
                    String exportUrl = "/trainingsplan/" + refreshedPlan.getId() + "/uebungen";
                    System.out.println("Exportiere PDF mit Trainingsplan-ID: " + refreshedPlan.getId() + " und " + refreshedPlan.getTrainings().size() + " Trainings.");
                    getUI().ifPresent(ui -> ui.getPage().open(exportUrl));
                }
            } else {
                Notification.show("Bitte wählen Sie einen Trainingsplan aus, um ihn zu exportieren.", 3000, Notification.Position.MIDDLE);
            }
        });
        btnExportPdf.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // Home-Button konfigurieren
        btnHome.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("mitglied-dashboard")));
        btnHome.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        // Trainingsplan bearbeiten-Button konfigurieren
        btnEdit.addClickListener(e -> {
            Trainingsplan selectedTrainingsplan = getSelectedTrainingsplan();
            if (selectedTrainingsplan != null) {
                openEditTrainingsplanDialog(selectedTrainingsplan);
            } else {
                Notification.show("Bitte wählen Sie einen Trainingsplan aus, um ihn zu bearbeiten.", 3000, Notification.Position.MIDDLE);
            }
        });
        btnEdit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // Trainingsplan auswählen
        btnChoose.addClickListener(e -> {
            Trainingsplan selectedTrainingsplan = getSelectedTrainingsplan();
            if (selectedTrainingsplan != null) {
                gridTraining.setItems(trainingService.getTrainingsByTrainingsplanId(selectedTrainingsplan.getId()));
                gridTraining.setColumns("name", "beschreibung");
                gridTraining.setHeight("95%");
                trainingDialog.open();
            } else {
                Notification.show("Bitte wählen Sie einen Trainingsplan aus.", 3000, Notification.Position.MIDDLE);
            }
        });

        // Trainingsplan starten / Abbrechen
        btnStarten.addClickListener(e -> {
            Training selectedTraining = gridTraining.asSingleSelect().getValue();
            if (selectedTraining != null) {
                getUI().ifPresent(ui -> ui.navigate("trainingview/" + selectedTraining.getId()));
            } else {
                Notification.show("Bitte wählen Sie ein Training aus.", 3000, Notification.Position.MIDDLE);
            }
        });
        btnAbbrechen.addClickListener(e -> {
            trainingDialog.close();
        });

        // Filter konfigurieren
        tfFilterName.setLabel("Filtern nach Name");
        tfFilterName.setPlaceholder("Name");
        tfFilterName.setClearButtonVisible(true);
        tfFilterName.setValueChangeMode(ValueChangeMode.LAZY);
        tfFilterName.addValueChangeListener(e -> {
            gridTrainingsplan.setItems(
                trainingsplanService.filterTrainingsplanByName(tfFilterName.getValue()));
        });
        hlToolbar.add(tfFilterName);
        hlToolbar.setAlignItems(Alignment.BASELINE);
        gridTrainingsplan.setItems(trainingsplanService.getAllTrainingsplaene());
        gridTrainingsplan.setColumns("name", "beschreibung");

        // Dialog konfigurieren
        trainingDialog.setWidth("40%");
        trainingDialog.setHeight("40%");
        trainingDialog.add(gridTraining);
        trainingDialog.getFooter().add(hlButtons_Training);
        trainingDialog.setCloseOnEsc(false);
        trainingDialog.setCloseOnOutsideClick(false);
        trainingDialog.setModal(true);
        trainingDialog.setHeaderTitle("Training auswählen");
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        vlGrid.setWidth("50%");
        hlButtons_Training.setSizeFull();
        hlButtons_Training.setJustifyContentMode(JustifyContentMode.START);
        hlButtons_Training.setAlignItems(Alignment.BASELINE);
        btnStarten.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnAbbrechen.addThemeVariants(ButtonVariant.LUMO_ERROR);

        vlGrid.add(hlToolbar, gridTrainingsplan);
        add(vlGrid, hlButtons_Trainingsplan, trainingDialog);
    }

    private void openEditTrainingsplanDialog(Trainingsplan trainingsplan) {
        Dialog editDialog = new Dialog();
        editDialog.setHeaderTitle("Trainingsplan bearbeiten");

        Grid<Training> currentTrainingsGrid = createTrainingGrid("Aktuelle Trainings");
        Grid<Training> allTrainingsGrid = createTrainingGrid("Alle verfügbaren Trainings");

        updateTrainingGrids(currentTrainingsGrid, allTrainingsGrid, trainingsplan);

        currentTrainingsGrid.setWidth("50%");
        allTrainingsGrid.setWidth("50%");

        Button addTrainingButton = new Button("Hinzufügen", event -> {
            Training selectedTraining = allTrainingsGrid.asSingleSelect().getValue();
            if (selectedTraining != null) {
                trainingsplanService.addTrainingToTrainingsplan(trainingsplan.getId(), selectedTraining);
                updateTrainingGrids(currentTrainingsGrid, allTrainingsGrid, trainingsplan);
                Notification.show("Training hinzugefügt: " + selectedTraining.getName(), 3000, Notification.Position.MIDDLE);
            } else {
                Notification.show("Bitte wählen Sie ein Training aus.", 3000, Notification.Position.MIDDLE);
            }
        });

        Button removeTrainingButton = new Button("Entfernen", event -> {
            Training selectedTraining = currentTrainingsGrid.asSingleSelect().getValue();
            if (selectedTraining != null) {
                trainingsplanService.removeTrainingFromTrainingsplan(trainingsplan.getId(), selectedTraining.getId());
                updateTrainingGrids(currentTrainingsGrid, allTrainingsGrid, trainingsplan);
                Notification.show("Training entfernt: " + selectedTraining.getName(), 3000, Notification.Position.MIDDLE);
            } else {
                Notification.show("Bitte wählen Sie ein Training aus.", 3000, Notification.Position.MIDDLE);
            }
        });

        Button closeButton = new Button("Schließen", event -> editDialog.close());

        HorizontalLayout buttonLayout = new HorizontalLayout(addTrainingButton, removeTrainingButton, closeButton);
        buttonLayout.setWidthFull();
        buttonLayout.setJustifyContentMode(com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode.END);

        HorizontalLayout gridLayout = new HorizontalLayout(currentTrainingsGrid, allTrainingsGrid);
        gridLayout.setWidthFull();
        gridLayout.setSpacing(true);

        VerticalLayout dialogLayout = new VerticalLayout(gridLayout, buttonLayout);
        dialogLayout.setWidthFull();
        dialogLayout.setSpacing(true);

        editDialog.add(dialogLayout);
        editDialog.setWidth("80%");
        editDialog.setHeight("70%");
        editDialog.open();
    }

    private Grid<Training> createTrainingGrid(String header) {
        Grid<Training> grid = new Grid<>(Training.class);
        grid.addColumn(Training::getId).setHeader("ID").setAutoWidth(true);
        grid.addColumn(Training::getName).setHeader("Name").setAutoWidth(true);
        grid.setColumns("id", "name"); // Nur ID und Name anzeigen
        grid.getStyle().set("border", "1px solid #e0e0e0");
        grid.setHeight("400px");
        return grid;
    }

    private void updateTrainingGrids(Grid<Training> currentTrainingsGrid, Grid<Training> allTrainingsGrid, Trainingsplan trainingsplan) {
        currentTrainingsGrid.setItems(trainingService.getTrainingsByTrainingsplanId(trainingsplan.getId()));
        allTrainingsGrid.setItems(trainingService.getAllTrainings());
    }

    private Trainingsplan getSelectedTrainingsplan() {
        return gridTrainingsplan.asSingleSelect().getValue();
    }

    private Trainingsplan refreshTrainingsplan(Long trainingsplanId) {
        return trainingsplanService.findTrainingsplanById(trainingsplanId).orElseThrow(() -> new RuntimeException("Trainingsplan nicht gefunden"));
    }
}
