package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.entities.AdminProfile;
import com.example.demo.model.repositories.AdminProfileRepository;


@Service
public class AdminProfileService {

    @Autowired
    private AdminProfileRepository adminProfileRepository;

    // Administratorprofil anhand der Benutzer-ID laden
    public Object getProfileByUserId(Long userId) {
        return adminProfileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Administratorprofil nicht gefunden für Benutzer-ID: " + userId));
    }

    // Administratorprofil speichern/aktualisieren
    public AdminProfile saveProfile(AdminProfile profile) {
        return adminProfileRepository.save(profile);
    }
}
