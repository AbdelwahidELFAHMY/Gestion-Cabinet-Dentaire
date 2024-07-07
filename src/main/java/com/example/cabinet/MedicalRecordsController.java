package com.example.cabinet;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class MedicalRecordsController {

    @FXML
    private ListView<String> patientsListView;

    @FXML
    private ListView<String> documentsListView;

    @FXML
    private Button openButton;

    @FXML
    private Button addButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button downloadButton;

    @FXML
    private TextArea medicalRecordTextArea;

    @FXML
    public void initialize() {
        // Initialize the controller, e.g., populate patient list, document list, etc.
    }

    @FXML
    private void openMedicalRecord() {
        // Handle opening medical record
    }

    @FXML
    private void addDocument() {
        // Handle adding a document to medical record
    }

    @FXML
    private void editMedicalRecord() {
        // Handle editing medical record
    }

    @FXML
    private void deleteMedicalRecord() {
        // Handle deleting medical record
    }

    @FXML
    private void downloadDocument() {
        // Handle downloading a document
    }
}
