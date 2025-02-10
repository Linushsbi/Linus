package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.model.entities.Training;
import com.example.demo.model.entities.Trainingsplan;
import com.example.demo.model.repositories.TrainingsplanRepository;
import com.example.demo.model.repositories.UebungRepository;

@Service
public class TrainingsplanService {

    private final TrainingsplanRepository trainingsplanRepository;
    private final UebungRepository uebungRepository;

    public TrainingsplanService(TrainingsplanRepository trainingsplanRepository, UebungRepository uebungRepository) {
        this.trainingsplanRepository = trainingsplanRepository;
        this.uebungRepository = uebungRepository;
    }

    public List<Trainingsplan> filterTrainingsplanByName(String name) {
        return trainingsplanRepository.filterTrainingsplanByName(name);
    }

    public List<Trainingsplan> getAllTrainingsplaene() {
        return trainingsplanRepository.findAll();
    }

    public long getAnzahlTrainingsplaene() {
        return trainingsplanRepository.count();
    }

    public Optional<Trainingsplan> findTrainingsplanById(Long id) {
        return trainingsplanRepository.findById(id);
    }

    public Trainingsplan saveTrainingsplan(Trainingsplan trainingsplan) {
        return trainingsplanRepository.save(trainingsplan);
    }

    public Trainingsplan updateTrainingsplan(Long id, Trainingsplan trainingsplanDetails) {
        Trainingsplan trainingsplan = trainingsplanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trainingsplan mit ID " + id + " nicht gefunden"));

        trainingsplan.setName(trainingsplanDetails.getName());
        trainingsplan.setBeschreibung(trainingsplanDetails.getBeschreibung());
        trainingsplan.setTrainings(trainingsplanDetails.getTrainings());
        trainingsplan.setBenutzer(trainingsplanDetails.getBenutzer());

        return trainingsplanRepository.save(trainingsplan);
    }

    public void deleteTrainingsplan(Long id) {
        trainingsplanRepository.deleteById(id);
    }


    public Trainingsplan addTrainingToTrainingsplan(Long trainingsplanId, Training training) {
        Trainingsplan trainingsplan = trainingsplanRepository.findById(trainingsplanId)
                .orElseThrow(() -> new RuntimeException("Trainingsplan mit ID " + trainingsplanId + " nicht gefunden"));

        trainingsplan.getTrainings().add(training);

        return trainingsplanRepository.save(trainingsplan);
    }

    public Trainingsplan removeTrainingFromTrainingsplan(Long trainingsplanId, Long trainingId) {
        Trainingsplan trainingsplan = trainingsplanRepository.findById(trainingsplanId)
                .orElseThrow(() -> new RuntimeException("Trainingsplan mit ID " + trainingsplanId + " nicht gefunden"));

        Training training = trainingsplan.getTrainings().stream()
                .filter(t -> t.getId() == trainingId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Training mit ID " + trainingId + " nicht gefunden"));

        trainingsplan.getTrainings().remove(training);

        return trainingsplanRepository.save(trainingsplan);
    }
}
