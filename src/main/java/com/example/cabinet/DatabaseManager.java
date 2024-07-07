package com.example.cabinet;

import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private static final String URL = "jdbc:mysql://localhost:3306/cabinet_dentaire";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static List<Patient> getPatients() {
        List<Patient> patients = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM patient")) {

            while (resultSet.next()) {
                int idPatient = resultSet.getInt("id_patient");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String numeroSecuriteSociale = resultSet.getString("numeroSecuriteSociale");
                String adresse = resultSet.getString("adresse");
                String telephone = resultSet.getString("telephone");
                String CIN = resultSet.getString("CIN");

                Patient patient = new Patient(idPatient, nom, prenom, numeroSecuriteSociale, adresse, telephone, CIN);
                patients.add(patient);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }
    public static boolean supprimerPatient(int idPatient) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM patient WHERE id_patient = ?")) {

            statement.setInt(1, idPatient);
            statement.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message informel");
            alert.setHeaderText("La Suppression a ete faite avec succes");
            alert.showAndWait();
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Méthode pour modifier un patient dans la base de données
    public static boolean modifierPatient(Patient patient) {
        // Établir la connexion à la base de données
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            // Requête SQL pour mettre à jour les informations du patient
            String sql = "UPDATE patient SET nom = ?, prenom = ?, numeroSecuriteSociale = ?, telephone = ?, adresse = ?, CIN = ? WHERE id_patient = ?";
            // Créer la déclaration préparée
            PreparedStatement statement = connection.prepareStatement(sql);
            // Définir les paramètres de la déclaration préparée avec les nouvelles valeurs du patient

            statement.setString(1, patient.getNom());
            statement.setString(2, patient.getPrenom());
            statement.setString(3, patient.getNumeroSecuriteSociale());
            statement.setString(4, patient.getTelephone());
            statement.setString(5, patient.getAdresse());
            statement.setString(6, patient.getCIN());
            statement.setInt(7, patient.getIdPatient());
            // Exécuter la mise à jour
            int rowsAffected = statement.executeUpdate();
            connection.close();
            // Retourner vrai si au moins une ligne a été modifiée, sinon retourner faux
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Retourner faux en cas d'erreur
        }
    }
    public static boolean addPatient(Patient patient) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement
             ("INSERT INTO patient (nom, prenom, numeroSecuriteSociale, adresse, telephone, CIN) VALUES (?, ?, ?, ?, ?, ?)")) {

            // Définir les paramètres de la déclaration préparée avec les valeurs du patient
            statement.setString(1, patient.getNom());
            statement.setString(2, patient.getPrenom());
            statement.setString(3, patient.getNumeroSecuriteSociale());
            statement.setString(4, patient.getAdresse());
            statement.setString(5, patient.getTelephone());
            statement.setString(6, patient.getCIN());

            // Exécuter l'insertion
            int rowsAffected = statement.executeUpdate();

            // Afficher un message de confirmation si l'insertion a réussi
            if (rowsAffected > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Ajouter un patient");
                alert.setHeaderText("L'ajout a été effectué avec succès");
                alert.showAndWait();
                return true;
            } else {
                // Retourner faux si aucune ligne n'a été affectée
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Retourner faux en cas d'erreur
        }
    }

    public static List<Appointment> getAppointement() {
        List<Appointment> appointments = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM rendezvous")) {

            while (resultSet.next()) {
                int idRendezVous = resultSet.getInt("id_rendez_vous");
                int idPatient = resultSet.getInt("id_patient");
                int idDentiste = resultSet.getInt("id_dentiste");
                Date date = resultSet.getDate("dateRendezVous");
                Time heure = resultSet.getTime("heureRendezVous");
                String notes = resultSet.getString("notes");

                // Fetch patient name
                String nomPatient = "";
                try (PreparedStatement patientStatement = connection.prepareStatement("SELECT nom FROM patient WHERE id_patient = ?")) {
                    patientStatement.setInt(1, idPatient);
                    try (ResultSet patientResultSet = patientStatement.executeQuery()) {
                        if (patientResultSet.next()) {
                            nomPatient = patientResultSet.getString("nom");
                        }
                    }
                }

                // Fetch dentist name
                String nomDentiste = "";
                try (PreparedStatement dentistStatement = connection.prepareStatement("SELECT nom FROM dentiste WHERE id_dentiste = ?")) {
                    dentistStatement.setInt(1, idDentiste);
                    try (ResultSet dentistResultSet = dentistStatement.executeQuery()) {
                        if (dentistResultSet.next()) {
                            nomDentiste = dentistResultSet.getString("nom");
                        }
                    }
                }

                Appointment appointment = new Appointment(idRendezVous, nomPatient, date, heure, nomDentiste,notes);
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;
    }



    public static boolean ajouterrendezvous(Appointment newAppointment) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement
                     ("INSERT INTO rendezvous (id_patient,id_dentiste,dateRendezVous,heureRendezVous) VALUES (?, ?, ?, ?)")) {

            // Définir les paramètres de la déclaration préparée avec les valeurs du patient
            statement.setInt(1, newAppointment.getIdPatient());
            statement.setInt(2, newAppointment.getIdDentist());
            statement.setDate(3, newAppointment.getDate());
            statement.setTime(4, newAppointment.getTime());

            // Exécuter l'insertion
            int rowsAffected = statement.executeUpdate();

            // Afficher un message de confirmation si l'insertion a réussi
            if (rowsAffected > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Ajouter un Rendez-Vous");
                alert.setHeaderText("L'ajout a été effectué avec succès");
                alert.showAndWait();
                return true;
            } else {
                // Retourner faux si aucune ligne n'a été affectée
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Retourner faux en cas d'erreur
        }
    }

    public static boolean modifierrendezvous(Appointment appoi) {
        // Établir la connexion à la base de données
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            // Requête SQL pour mettre à jour les informations du patient
            String sql = "UPDATE rendezvous SET dateRendezVous = ?, heureRendezVous = ?, notes = ? WHERE id_rendez_vous = ?";
            // Créer la déclaration préparée
            PreparedStatement statement = connection.prepareStatement(sql);
            // Définir les paramètres de la déclaration préparée avec les nouvelles valeurs du patient


            statement.setDate(1, appoi.getDate());
            statement.setTime(2, appoi.getTime());
            statement.setString(3,appoi.getNotes());
            statement.setInt(4, appoi.getId());
            // Exécuter la mise à jour
            int rowsAffected = statement.executeUpdate();
            connection.close();
            // Retourner vrai si au moins une ligne a été modifiée, sinon retourner faux
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Retourner faux en cas d'erreur
        }
    }

    public static boolean supprimerrendezvous(int id) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM rendzvous WHERE id_rendez_vous = ?")) {

            statement.setInt(1, id);
            statement.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message informel");
            alert.setHeaderText("La Suppression a ete faite avec succes");
            alert.showAndWait();
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
