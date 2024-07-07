package com.example.cabinet;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PaymentsController {

    @FXML
    private Label label;

    @FXML
    public void initialize() {
        label.setText("Gestion des Paiements");
    }
}
