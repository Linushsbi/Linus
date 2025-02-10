package com.example.demo.model.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "mitglied")
public class CustomerProfile {

    @Id
    private Long id; // Referenz zur Basistabelle application_user

    private Date geburtsdatum;
    private String adresse;
    private String telefon;

    @Column(name = "ist_premium", nullable = false)
    private boolean istPremium;

    @Column(name = "gewicht_kg")
    private double gewichtKg;

    @Column(name = "groesse_cm")
    private double groesseCm;

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

    public boolean isIstPremium() {
        return istPremium;
    }

    public void setIstPremium(boolean istPremium) {
        this.istPremium = istPremium;
    }

    public double getGewichtKg() {
        return gewichtKg;
    }

    public void setGewichtKg(double gewichtKg) {
        this.gewichtKg = gewichtKg;
    }

    public double getGroesseCm() {
        return groesseCm;
    }

    public void setGroesseCm(double groesseCm) {
        this.groesseCm = groesseCm;
    }

    public User getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(User userProfile) {
        this.userProfile = userProfile;
    }
}
