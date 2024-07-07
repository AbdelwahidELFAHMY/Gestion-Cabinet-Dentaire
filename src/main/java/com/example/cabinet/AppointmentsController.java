package com.example.cabinet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AppointmentsController {

    @FXML
    private TextField searchAppointmentField;
    @FXML
    private TableView<Appointment> appointmentTable;
    @FXML
    private TableColumn<Appointment, Integer> appointmentIdColumn;
    @FXML
    private TableColumn<Appointment, String> appointmentPatientColumn;
    @FXML
    private TableColumn<Appointment, Date> appointmentDateColumn;
    @FXML
    private TableColumn<Appointment, Time> appointmentTimeColumn;
    @FXML
    private TableColumn<Appointment, String> appointmentDentistColumn;
    @FXML
    private TableColumn<Appointment, Void> appointmentNotesColumn;
    @FXML
    private TableColumn<Appointment, Void> appointmentActionsColumn;

    private List<Appointment> appointments;

    public void initialize() {
        initializeTableRendezVous();

        List<Appointment> rendezVous = new ArrayList<>(appointments);

        // Add a listener to the search field to handle real-time filtering
        searchAppointmentField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {

                appointmentTable.getItems().setAll(rendezVous);
            } else {

                String searchText = newValue.toLowerCase();
                List<Appointment> filteredPatients = appointments.stream()
                        .filter(appointement-> appointement.getPatientName().toLowerCase().contains(searchText))
                        .collect(Collectors.toList());
                appointmentTable.getItems().setAll(filteredPatients);
            }
        });
    }

    private void initializeTableRendezVous() {
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        appointmentPatientColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        appointmentDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        appointmentTimeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        appointmentDentistColumn.setCellValueFactory(new PropertyValueFactory<>("dentist"));
        appointmentNotesColumn.setCellValueFactory(new PropertyValueFactory<>("notes"));
        setupAppointementActionsColumn();
        remplirTableauRendezVous();
    }

    // Méthode pour remplir le tableau des RendezVous
    private void remplirTableauRendezVous() {
        appointments = DatabaseManager.getAppointement();
        appointmentTable.getItems().addAll(appointments);
    }

    // Configuration de la colonne "Actions" du tableau des patients
    private void setupAppointementActionsColumn() {
        appointmentActionsColumn.setCellFactory(param -> new TableCell<>() {
            final Button editButton = new Button("Modifier");
            final Button deleteButton = new Button("Supprimer");

            {
                editButton.setOnAction(event -> {
                    // Action à effectuer lors du clic sur le bouton "Modifier"
                    Appointment appointment = getTableView().getItems().get(getIndex());
                    handleEditAppointement(appointment);

                });

                deleteButton.setOnAction(event -> {
                    // Action à effectuer lors du clic sur le bouton "Supprimer"
                    Appointment appointment = getTableView().getItems().get(getIndex());
                    handleDeleteAppointement(appointment);
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

    // Méthode appelée lors du clic sur le bouton "Modifier" d'un rendez vous
    private void handleEditAppointement(Appointment appointment) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Modifier Rendez Vous");
        dialog.setHeaderText(null);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierRendezVous.fxml"));
            Parent root = loader.load();
            ModifierrendezvousController controller = loader.getController();
            controller.initData(appointment);
            dialog.getDialogPane().setContent(root);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
            dialog.showAndWait();
            // Rafraîchir la table après modification
            appointmentTable.refresh();
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

    private void handleDeleteAppointement(Appointment appointment) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de Suppression");
        alert.setHeaderText("Voulez-vous vraiment supprimer ce rendez-vous ?");
        alert.setContentText("ID: " + appointment.getId() + "\n"
                + "Nom Patient: " + appointment.getPatientName() + "\n"
                + "Nom Dentist: " + appointment.getDentist());

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                boolean success = DatabaseManager.supprimerrendezvous(appointment.getId());
                if (success) {
                    appointmentTable.getItems().remove(appointment);
                } else {
                    showErrorDialog("Erreur de suppression", "La suppression du patient a échoué.");
                }
            }
        });
    }

    // Méthode appelée lors du clic sur le bouton "Ajouter"
    public void handleAddAppointement(ActionEvent actionEvent) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Ajouter Rendez Vous");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterRendezVous.fxml"));
            Parent root = loader.load();
            dialog.getDialogPane().setContent(root);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
            dialog.showAndWait();

            appointmentTable.getItems().clear();
            remplirTableauRendezVous();
        } catch (IOException e) {
            e.printStackTrace();
            showErrorDialog("Erreur de chargement", "Une erreur s'est produite lors du chargement de la vue FXML.");
        }
    }
}
