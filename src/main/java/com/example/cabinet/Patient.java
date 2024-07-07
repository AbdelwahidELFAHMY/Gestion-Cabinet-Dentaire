package com.example.cabinet;

public class Patient {
    private int idPatient;
    private String nom;
    private String prenom;
    private String numeroSecuriteSociale;
    private String adresse;
    private String telephone;
    private String CIN;

    public Patient(int idPatient, String nom, String prenom, String numeroSecuriteSociale,
                   String adresse, String telephone, String CIN) {
        this.idPatient = idPatient;
        this.nom = nom;
        this.prenom = prenom;
        this.numeroSecuriteSociale = numeroSecuriteSociale;
        this.adresse = adresse;
        this.telephone = telephone;
        this.CIN = CIN;
    }

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNumeroSecuriteSociale() {
        return numeroSecuriteSociale;
    }

    public void setNumeroSecuriteSociale(String numeroSecuriteSociale) {
        this.numeroSecuriteSociale = numeroSecuriteSociale;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCIN() {
        return CIN;
    }

    public void setCIN(String CIN) {
        this.CIN = CIN;
    }
}
