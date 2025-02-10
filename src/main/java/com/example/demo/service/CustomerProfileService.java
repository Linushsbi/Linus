package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.entities.CustomerProfile;
import com.example.demo.model.repositories.CustomerProfileRepository;

@Service
public class CustomerProfileService {

    @Autowired
    private CustomerProfileRepository customerProfileRepository;

    // Kundenprofil anhand der Benutzer-ID laden
    public Object getProfileByUserId(Long userId) {
        return customerProfileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Kundenprofil nicht gefunden für Benutzer-ID: " + userId));
    }

    // Kundenprofil speichern/aktualisieren
    public CustomerProfile saveProfile(CustomerProfile profile) {
        return customerProfileRepository.save(profile);
    }
}
