package com.example.demo.model.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "mitarbeiter")
public class EmployeeProfile {

    @Id
    private Long id; // Referenz zur Basistabelle application_user

    private Date geburtsdatum;
    private String adresse;
    private String telefon;

    @ManyToOne
    @JoinColumn(name = "geschaeftsfuehrer_id")
    private ManagerProfile manager; // Beziehung zu einem Geschäftsführer

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User userProfile; // Verknüpfung zur Basistabelle

    // Getters und Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(Date geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public ManagerProfile getManager() {
        return manager;
    }

    public void setManager(ManagerProfile manager) {
        this.manager = manager;
    }

    public User getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(User userProfile) {
        this.userProfile = userProfile;
    }
}
