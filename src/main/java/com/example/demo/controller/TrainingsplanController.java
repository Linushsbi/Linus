package com.example.demo.controller;

import com.example.demo.model.entities.Trainingsplan;
import com.example.demo.model.entities.Uebung;
import com.example.demo.service.TrainingsplanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainingsplaene")
public class TrainingsplanController {

    private final TrainingsplanService trainingsplanService;

    public TrainingsplanController(TrainingsplanService trainingsplanService) {
        this.trainingsplanService = trainingsplanService;
    }

    @GetMapping
    public ResponseEntity<List<Trainingsplan>> getAllTrainingsplaene() {
        return ResponseEntity.ok(trainingsplanService.getAllTrainingsplaene());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trainingsplan> getTrainingsplanById(@PathVariable Long id) {
        return trainingsplanService.findTrainingsplanById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Trainingsplan> createTrainingsplan(@RequestBody Trainingsplan trainingsplan) {
        return ResponseEntity.ok(trainingsplanService.saveTrainingsplan(trainingsplan));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trainingsplan> updateTrainingsplan(@PathVariable Long id, @RequestBody Trainingsplan trainingsplan) {
        return ResponseEntity.ok(trainingsplanService.updateTrainingsplan(id, trainingsplan));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainingsplan(@PathVariable Long id) {
        trainingsplanService.deleteTrainingsplan(id);
        return ResponseEntity.noContent().build();
    }

}
