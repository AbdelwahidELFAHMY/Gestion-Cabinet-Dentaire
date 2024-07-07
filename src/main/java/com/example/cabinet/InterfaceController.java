package com.example.cabinet;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InterfaceController {

    @FXML
    private TabPane tabPane;
    @FXML
    private Tab patientsTab;
    @FXML
    private Tab appointmentsTab;
    @FXML
    private Tab medicalRecordsTab;
    @FXML
    private Tab paymentsTab;

    @FXML
    private Label dateTimeLabel;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm  dd-MM-yyyy");

    @FXML
    public void initialize() {

        // Initialiser la date et l'heure
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            LocalDateTime now = LocalDateTime.now();
            dateTimeLabel.setText(now.format(formatter));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        loadTabContent(patientsTab, "patients.fxml");
        loadTabContent(appointmentsTab, "rendez-vous.fxml");
        loadTabContent(paymentsTab, "payments.fxml");
        loadTabContent(medicalRecordsTab, "MedicalRecords.fxml");
    }

    private void loadTabContent(Tab tab, String fxmlPath) {
        try {
            AnchorPane content = FXMLLoader.load(getClass().getResource(fxmlPath));
            tab.setContent(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
