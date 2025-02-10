package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.entities.User;
import com.example.demo.model.repositories.ApplicationUserRepository;

@Service
public class ProfileService {

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private CustomerProfileService customerProfileService;

    @Autowired
    private EmployeeProfileService employeeProfileService;

    @Autowired
    private ManagerProfileService managerProfileService;

    @Autowired
    private AdminProfileService adminProfileService;

    public Object getProfileByUsername(String username) {
        Optional<User> userOpt = applicationUserRepository.findByBenutzername(username);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("Benutzer nicht gefunden: " + username);
        }

        User user = userOpt.get();

        // Nutzung der Methode getRolle(), um die Rolle des Benutzers zu bestimmen
        String role = user.getRolle(); 

        switch (role.toLowerCase()) { // Sicherstellen, dass die Vergleichswerte in Kleinbuchstaben erfolgen
            case "mitglied":
                return customerProfileService.getProfileByUserId(user.getId());
            case "mitarbeiter":
                return employeeProfileService.getProfileByUserId(user.getId());
            case "geschaeftsfuehrer":
                return managerProfileService.getProfileByUserId(user.getId());
            case "administrator":
                return adminProfileService.getProfileByUserId(user.getId());
            default:
                throw new RuntimeException("Unbekannte Rolle: " + role);
        }
    }
}
