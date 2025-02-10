package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.entities.ManagerProfile;
import com.example.demo.model.repositories.ManagerProfileRepository;

@Service
public class ManagerProfileService {

    @Autowired
    private ManagerProfileRepository managerProfileRepository;

    // Geschäftsführerprofil anhand der Benutzer-ID laden
    public Object getProfileByUserId(Long userId) {
        return managerProfileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Geschäftsführerprofil nicht gefunden für Benutzer-ID: " + userId));
    }

    // Geschäftsführerprofil speichern/aktualisieren
    public ManagerProfile saveProfile(ManagerProfile profile) {
        return managerProfileRepository.save(profile);
    }
}
