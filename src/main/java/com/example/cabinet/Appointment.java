package com.example.cabinet;

import java.sql.Date;
import java.sql.Time;

public class Appointment {
    private int id;
    private int idPatient;
    private int idDentist;
    private String patientName;
    private Date date;
    private Time time;
    private String dentist;
    private String CINPatient;
    private String notes;

    public Appointment(int id, String patientName, Date date, Time time, String dentist,String notes) {
        this.id = id;
        this.patientName = patientName;
        this.date = date;
        this.time = time;
        this.dentist = dentist;
        this.notes = notes;
    }
    public Appointment(String CINPatient, String nomDentist, Date date, Time time) {
        this.CINPatient = CINPatient;
        this.dentist = nomDentist;
        this.date = date;
        this.time = time;
    }

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

    public int getIdDentist() {
        return idDentist;
    }

    public void setIdDentist(int idDentist) {
        this.idDentist = idDentist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getDentist() {
        return dentist;
    }

    public void setDentist(String dentist) {
        this.dentist = dentist;
    }

    public String getCINPatient() {
        return CINPatient;
    }

    public void setCINPatient(String CINPatient) {
        this.CINPatient = CINPatient;
    }

    public String getNomDentist() {
        return dentist;
    }

    public void setNomDentist(String nomDentist) {
        this.dentist = nomDentist;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

