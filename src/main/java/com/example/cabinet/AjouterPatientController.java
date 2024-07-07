package com.example.cabinet;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AjouterPatientController {

    @FXML
    private TextField CIN;

    @FXML
    private TextField adresseField;

    @FXML
    private Button AjoutButton;

    @FXML
    private TextField nomField;

    @FXML
    private TextField numeroSecuriteSociale;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField telephoneField;

    @FXML
    private void handleAddPatient() {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String telephone = telephoneField.getText();
        String adresse = adresseField.getText();
        String nss = numeroSecuriteSociale.getText();
        String cin = CIN.getText();
        // Vérifier si le champ CIN est vide
        if (CIN.getText().isEmpty()||numeroSecuriteSociale.getText().isEmpty()
              || adresseField.getText().isEmpty()||nomField.getText().isEmpty()||
                prenomField.getText().isEmpty()||telephoneField.getText().isEmpty()) {
            // Afficher une alerte pour informer l'utilisateur
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champ vide");
            alert.setContentText("Veuillez Remplir toutes les champs.");
            alert.showAndWait();
        } else {

            Patient newPatient = new Patient(0, nom, prenom, telephone, adresse, nss, cin);

            boolean success = DatabaseManager.addPatient(newPatient);
            if (success) {
                // Close the dialog
                Stage stage = (Stage) nomField.getScene().getWindow();
                stage.close();
            } else {
                showErrorDialog("Erreur d'ajout", "L'ajout du patient a échoué.");
            }
        }
    }

    private void showErrorDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
