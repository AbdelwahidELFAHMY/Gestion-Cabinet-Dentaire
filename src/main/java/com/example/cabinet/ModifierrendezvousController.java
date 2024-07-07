package com.example.cabinet;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

public class ModifierrendezvousController {

    @FXML
    private TextField nomPatientField;

    @FXML
    private TextField nomdentistField;

    @FXML
    private DatePicker dateField;

    @FXML
    private TextField heureField;
    @FXML
    private TextField notesField;

    private Appointment appoi;

    // Méthode pour initialiser les données du patient à modifier
    public void initData(Appointment appoi) {
        this.appoi = appoi;

        // Remplir les champs avec les données actuelles du patient
        nomPatientField.setText(String.valueOf(appoi.getPatientName()));
        nomdentistField.setText(String.valueOf(appoi.getDentist()));
        dateField.setValue(appoi.getDate().toLocalDate());
        heureField.setText(String.valueOf(appoi.getTime()));
        notesField.setText(String.valueOf(appoi.getNotes()));
    }

    @FXML
    public void modifierrendezvous() {
        // Mettre à jour les données du patient avec les valeurs des champs
        appoi.setPatientName(nomPatientField.getText());
        appoi.setDate(Date.valueOf(dateField.getValue()));
        appoi.setTime(Time.valueOf(heureField.getText()));
        appoi.setDentist((nomdentistField.getText()));
        appoi.setNotes(notesField.getText());

        if(DatabaseManager.modifierrendezvous(appoi)) {
            // Afficher une confirmation de modification
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modification Réussie");
            alert.setHeaderText(null);
            alert.setContentText("Les informations du patient ont été modifiées avec succès.");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modification echouée");
            alert.setHeaderText(null);
            alert.setContentText("Erreur lors de la modification.");
            alert.showAndWait();

        }

        // Fermer la fenêtre de modification
        Stage stage = (Stage) nomPatientField.getScene().getWindow();
        stage.close();
    }


}
