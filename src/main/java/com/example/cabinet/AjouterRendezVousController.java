package com.example.cabinet;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.Time;


public class AjouterRendezVousController {

    @FXML
    private TextField nomdentistField;

    @FXML
    private TextField CINpatientField;

    @FXML
    private DatePicker dateField;

    @FXML
    private TextField heureField;

    private Appointment newAppointment;

    @FXML
    public void AddAppointment() {
        String CINPatient;
        Date date;
        Time time;
        String nomDentist;
        do {
            CINPatient =CINpatientField.getText();
            date = Date.valueOf(dateField.getValue());
            time = Time.valueOf(heureField.getText());
            nomDentist = nomdentistField.getText();
        }while (CINpatientField.getText().isEmpty()||dateField.getValue()==null||heureField.getText().isEmpty()||nomdentistField.getText().isEmpty());
        // Create new appointment object (ID is assumed to be auto-generated or handled elsewhere)
        newAppointment = new Appointment(CINPatient, nomDentist, date, time);
        boolean success = DatabaseManager.ajouterrendezvous(newAppointment);
        if (success) {
            // Close the dialog
            Stage stage = (Stage) CINpatientField.getScene().getWindow();
            stage.close();
        } else {
            showErrorDialog("Erreur d'ajout", "L'ajout du patient a échoué.");
        }

    }
    private void showErrorDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

}








