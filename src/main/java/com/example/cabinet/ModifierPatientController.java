package com.example.cabinet;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class ModifierPatientController {

    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField numeroSecuriteSociale;

    @FXML
    private TextField telephoneField;

    @FXML
    private TextField adresseField;

    @FXML
    private TextField CIN;

    private Patient patient;

    // Méthode pour initialiser les données du patient à modifier
    public void initData(Patient patient) {
        this.patient = patient;

        // Remplir les champs avec les données actuelles du patient
        nomField.setText(patient.getNom());
        prenomField.setText(patient.getPrenom());
        numeroSecuriteSociale.setText(patient.getNumeroSecuriteSociale());
        telephoneField.setText(patient.getTelephone());
        adresseField.setText(patient.getAdresse());
        CIN.setText(patient.getCIN());
    }

    @FXML
    public void modifierPatient() {
        // Mettre à jour les données du patient avec les valeurs des champs
        patient.setNom(nomField.getText());
        patient.setPrenom(prenomField.getText());
        patient.setNumeroSecuriteSociale(numeroSecuriteSociale.getText());
        patient.setTelephone(telephoneField.getText());
        patient.setAdresse(adresseField.getText());
        patient.setCIN(CIN.getText());

        if(DatabaseManager.modifierPatient(patient)) {
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
        Stage stage = (Stage) nomField.getScene().getWindow();
        stage.close();
    }

}
