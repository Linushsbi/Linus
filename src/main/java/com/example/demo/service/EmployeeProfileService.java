package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.entities.EmployeeProfile;
import com.example.demo.model.repositories.EmployeeProfileRepository;

@Service
public class EmployeeProfileService {

    @Autowired
    private EmployeeProfileRepository employeeProfileRepository;

    // Mitarbeiterprofil anhand der Benutzer-ID laden
    public Object getProfileByUserId(Long userId) {
        return employeeProfileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Mitarbeiterprofil nicht gefunden für Benutzer-ID: " + userId));
    }

    // Mitarbeiterprofil speichern/aktualisieren
    public EmployeeProfile saveProfile(EmployeeProfile profile) {
        return employeeProfileRepository.save(profile);
    }
}
