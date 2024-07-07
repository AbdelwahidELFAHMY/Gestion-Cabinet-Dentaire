package com.example.cabinet;

import java.sql.Date;

public class Consultation {
    private int id_consultation;
    private Date dateConsultation ;
    private String traitementPrescrit;
    private String description;
    private int id_maladie;
    private int id_patient ;
    private int id_rendez_vous;
    private String nom;
    private String prenom;

    public Consultation(int id_consultation, Date dateConsultation, String traitement, String description, int id_maladie, int id_patient, int id_rendez_vous, String nomPatient, String prenomPatient ) {
        this.id_consultation = id_consultation;
        this.dateConsultation = dateConsultation;
        this.traitementPrescrit = traitement;
        this.description = description;
        this.id_maladie = id_maladie;
        this.id_patient = id_patient;
        this.id_rendez_vous = id_rendez_vous;
        this.nom = nomPatient;
        this.prenom = prenomPatient;
    }

    public int getId_consultation() {
        return id_consultation;
    }

    public Date getDateConsultation() {
        return dateConsultation;
    }

    public String getTraitementPrescrit() {
        return traitementPrescrit;
    }

    public String getDescription() {
        return description;
    }

    public int getId_maladie() {
        return id_maladie;
    }

    public int getId_patient() {
        return id_patient;
    }

    public int getId_rendez_vous() {
        return id_rendez_vous;
    }

    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }


    public void setId_consultation(int id_consultation) {
        this.id_consultation = id_consultation;
    }

    public void setDateConsultation(Date dateConsultation) {
        this.dateConsultation = dateConsultation;
    }

    public void setTraitement(String traitement) {
        this.traitementPrescrit = traitement;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId_maladie(int id_maladie) {
        this.id_maladie = id_maladie;
    }

    public void setId_patient(int id_patient) {
        this.id_patient = id_patient;
    }

    public void setId_rendez_vous(int id_rendez_vous) {
        this.id_rendez_vous = id_rendez_vous;
    }
}
