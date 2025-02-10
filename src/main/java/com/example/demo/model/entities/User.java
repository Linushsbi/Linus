package com.example.demo.model.entities;

// Author: Delbrin Alazo

// Created: 2024-12-07
// Last Updated: 2024-12-07
// Modified by: Delbrin Alazo
// Description: Entity for User

import java.io.Serializable;
import java.util.Set;

import com.example.demo.model.enums.Role;
import com.example.demo.model.enums.Sicherheitsfrage;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "application_user")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String benutzername;

    @Column(nullable = false)
    private String passwort;

    @Column(nullable = false)
    private String vorname;

    @Column(nullable = false)
    private String nachname;

    @Column(nullable = false)
    private String sicherheitsfrageAntwort;

    @Enumerated(EnumType.STRING)
    private Sicherheitsfrage sicherheitsfrage;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Column(nullable = false)
    private String rolle;

    @Column
    private Double groesseCm;

    @Column
    private Double gewichtKg;

    @Column
    private Boolean isPremium; 

    // Getter and Setter

    public String getRolle() {
        return rolle;
    }

    public void setRolle(String rolle) {
        this.rolle = rolle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getSicherheitsfrageAntwort() {
        return sicherheitsfrageAntwort;
    }

    public void setSicherheitsfrageAntwort(String sicherheitsfrageAntwort) {
        this.sicherheitsfrageAntwort = sicherheitsfrageAntwort;
    }

    public Sicherheitsfrage getSicherheitsfrage() {
        return sicherheitsfrage;
    }

    public void setSicherheitsfrage(Sicherheitsfrage sicherheitsfrage) {
        this.sicherheitsfrage = sicherheitsfrage;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Double getGroesseCm() {
        return groesseCm;
    }

    public void setGroesseCm(Double groesseCm) {
        this.groesseCm = groesseCm;
    }

    public Double getGewichtKg() {
        return gewichtKg;
    }

    public void setGewichtKg(Double gewichtKg) {
        this.gewichtKg = gewichtKg;
    }

    public Boolean getIsPremium() {
        return isPremium;
    }

    public void setIsPremium(Boolean isPremium) {
        this.isPremium = isPremium;
    }
    
}