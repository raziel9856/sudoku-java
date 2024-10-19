package com.example.hellojavafx.controllers;

import com.example.hellojavafx.models.Player;
import com.example.hellojavafx.view.GameView;
import com.example.hellojavafx.view.alert.AlertBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;

public class WelcomeController {

    @FXML
    public void startGame(ActionEvent event, int size) {
        try {
            GameView gameView = new GameView(size);
            gameView.show();
        } catch (IOException e) {
            showAlert("Failed to start the game: " + e.getMessage());
        }
    }

    public void showAlert(String message) {
        Alert errorAlert = new Alert(AlertType.ERROR);
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }

    public void startGameSix(ActionEvent actionEvent) {
        startGame(actionEvent, 6);
    }

    public void startGameNine(ActionEvent actionEvent) {
        startGame(actionEvent, 9);
    }
}
