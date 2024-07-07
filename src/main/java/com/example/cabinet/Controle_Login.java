package com.example.cabinet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.cabinet.DataBase.connect_DB;

public class Controle_Login {

    @FXML
    private CheckBox login_checkBox;

    @FXML
    private ComboBox<String> login_comboBox;

    @FXML
    private AnchorPane login_form;

    @FXML
    private Button login_loginbrtn;

    @FXML
    private PasswordField login_password;

    @FXML
    private TextField login_showPassword;

    @FXML
    private TextField login_username;

    @FXML
    private Label titre;


    //Data BAse toools -----------------------------------------
    private Connection connect ;
    private PreparedStatement prepare;

    @FXML
    void login(ActionEvent event) throws SQLException {
        String username = login_username.getText();
        String password = login_password.getText();
        String userType = login_comboBox.getValue();
        AlertMessage alert = new AlertMessage();


        if (username.isEmpty() || password.isEmpty() || userType == null) {
            alert.errorMessage("Remplissez tous les champs.");
            return;
        }

        if ("Dentiste".equals(userType)) {
            if ("admin".equals(username) && "admin".equals(password)) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("dentiste.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                    login_loginbrtn.getScene().getWindow().hide(); // Cacher la fenêtre de connexion
                } catch (IOException e) {
                    alert.errorMessage("Erreur de chargement de l'interface de Dentiste.");
                }

            } else {
                alert.errorMessage("Identifiants administrateur incorrects.");
            }
        }

        else if ("Secretaire".equals(userType)) {
            if ("root".equals(username) && "root".equals(password)) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Secretaire.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                    login_loginbrtn.getScene().getWindow().hide();
                } catch (IOException e) {
                    alert.errorMessage("Erreur de chargement de l'interface de Secretaire."+e.getMessage());
                    e.printStackTrace();
                }

            } else {
                alert.errorMessage(" Les informations sont incorrects!!!!!");
            }
        }

    }


    @FXML
    void show_password(ActionEvent event) {
        if(login_checkBox.isSelected()){
            login_showPassword.setText(login_password.getText());
            login_showPassword.setVisible(true);
            login_password.setVisible(false);
        }else{
            login_password.setText(login_showPassword.getText());
            login_showPassword.setVisible(false);
            login_password.setVisible(true);
        }
    }


    @FXML
    private void handleComboBoxAction(ActionEvent event) {
        String selectedOption = login_comboBox.getValue();
        if (selectedOption != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Option sélectionnée");
            alert.setHeaderText(null);
            alert.setContentText("Option sélectionnée : " + selectedOption);
            alert.showAndWait();
        }
    }
    private void initializeCombox() {
        ObservableList<String> options = FXCollections.observableArrayList("Secretaire", "Dentiste");
        login_comboBox.setItems(options);
    }

}
