package com.example.cabinet;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import  java.io.IOException;

public class dentaire extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(dentaire.class.getResource("pageLogin.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        //minimiser et maximiser la forme de apllication---------------------------------------------;
        stage.setMinWidth(440);
        stage.setMaxWidth(500);
        stage.setMinHeight(600);
        stage.setMaxHeight(700);
        //--------------------------------------------------------------------------------------------
        stage.setTitle("Cabinet Dentaire FOUNTY");
        scene.getStylesheets().add(getClass().getResource("style1.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}