package com.example.cabinet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PatientsController {

    @FXML
    private TextField searchPatientField;
    @FXML
    private TableView<Patient> patientTable;
    @FXML
    private TableColumn<Patient, Integer> patientIdColumn;
    @FXML
    private TableColumn<Patient, String> patientNomColumn;
    @FXML
    private TableColumn<Patient, String> patientPrenomColumn;
    @FXML
    private TableColumn<Patient, String> patientTelColumn;
    @FXML
    private TableColumn<Patient, String> patientNSSColumn;
    @FXML
    private TableColumn<Patient, String> patientAdresseColumn;
    @FXML
    private TableColumn<Patient, String> patientCINColumn;
    @FXML
    private TableColumn<Patient, Void> patientActionsColumn;

    private List<Patient> patients;

    public void initialize() {
        initializeTablePatients();

        // Assuming patients is your complete list of patients
        List<Patient> allPatients = new ArrayList<>(patients);

        // Add a listener to the search field to handle real-time filtering
        searchPatientField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                // If the search field is empty, show all patients
                patientTable.getItems().setAll(allPatients);
            } else {
                // Otherwise, filter the patient list
                String searchText = newValue.toLowerCase();
                List<Patient> filteredPatients = patients.stream()
                        .filter(patient -> patient.getNom().toLowerCase().contains(searchText))
                        .collect(Collectors.toList());
                patientTable.getItems().setAll(filteredPatients);
            }
        });
    }

    private void initializeTablePatients() {
        patientIdColumn.setCellValueFactory(new PropertyValueFactory<>("idPatient"));
        patientNomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        patientPrenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        patientTelColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        patientNSSColumn.setCellValueFactory(new PropertyValueFactory<>("numeroSecuriteSociale"));
        patientCINColumn.setCellValueFactory(new PropertyValueFactory<>("CIN"));
        patientAdresseColumn.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        setupPatientActionsColumn();
        remplirTableauPatients();
    }

    // Méthode pour remplir le tableau des patients
    private void remplirTableauPatients() {
        patients = DatabaseManager.getPatients();
        patientTable.getItems().addAll(patients);
    }

    // Configuration de la colonne "Actions" du tableau des patients
    private void setupPatientActionsColumn() {
        patientActionsColumn.setCellFactory(param -> new TableCell<>() {
            final Button editButton = new Button("Modifier");
            final Button deleteButton = new Button("Supprimer");

            {
                editButton.setOnAction(event -> {
                    // Action à effectuer lors du clic sur le bouton "Modifier"
                    Patient patient = getTableView().getItems().get(getIndex());
                    handleEditPatient(patient);
                });

                deleteButton.setOnAction(event -> {
                    // Action à effectuer lors du clic sur le bouton "Supprimer"
                    Patient patient = getTableView().getItems().get(getIndex());
                    handleDeletePatient(patient);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttons = new HBox(5);
                    buttons.getChildren().addAll(editButton, deleteButton);
                    setGraphic(buttons);
                }
            }
        });
    }

    // Méthode appelée lors du clic sur le bouton "Modifier" d'un patient
    private void handleEditPatient(Patient patient) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Modifier Patient");
        dialog.setHeaderText(null);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierPatient.fxml"));
            Parent root = loader.load();
            ModifierPatientController controller = loader.getController();
            controller.initData(patient);
            dialog.getDialogPane().setContent(root);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
            dialog.showAndWait();
            // Rafraîchir la table après modification
            patientTable.refresh();
        } catch (IOException e) {
            e.printStackTrace();
            showErrorDialog("Erreur de chargement", "Une erreur s'est produite lors du chargement de la vue FXML.");
        }
    }

    private void showErrorDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Méthode appelée lors du clic sur le bouton "Supprimer" d'un patient
    private void handleDeletePatient(Patient patient) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de Suppression");
        alert.setHeaderText("Voulez-vous vraiment supprimer ce patient ?");
        alert.setContentText("ID: " + patient.getIdPatient() + "\n"
                + "Nom: " + patient.getNom() + "\n"
                + "Prénom: " + patient.getPrenom());

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                boolean success = DatabaseManager.supprimerPatient(patient.getIdPatient());
                if (success) {
                    patientTable.getItems().remove(patient);
                } else {
                    showErrorDialog("Erreur de suppression", "La suppression du patient a échoué.");
                }
            }
        });
    }

    // Méthode appelée lors du clic sur le bouton "Ajouter"
    public void handleAddPatient(ActionEvent actionEvent) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Ajouter Patient");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterPatient.fxml"));
            Parent root = loader.load();
            dialog.getDialogPane().setContent(root);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
            dialog.showAndWait();

            // Rafraîchir la table après ajout
            patientTable.getItems().clear();
            remplirTableauPatients();
        } catch (IOException e) {
            e.printStackTrace();
            showErrorDialog("Erreur de chargement", "Une erreur s'est produite lors du chargement de la vue FXML.");
        }
    }

}
