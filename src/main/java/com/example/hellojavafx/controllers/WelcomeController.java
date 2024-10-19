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

    /**
     * Inicia el juego con el tamaño especificado.
     *
     * @param event El evento de acción que desencadena el inicio del juego.
     * @param size El tamaño del tablero de Sudoku (6x6 o 9x9).
     */
    @FXML
    public void startGame(ActionEvent event, int size) {
        try {
            GameView gameView = new GameView(size);
            gameView.show();
        } catch (IOException e) {
            showAlert("Failed to start the game: " + e.getMessage());
        }
    }

    /**
     * Muestra una alerta con el mensaje especificado.
     *
     * @param message El mensaje a mostrar en la alerta.
     */
    public void showAlert(String message) {
        Alert errorAlert = new Alert(AlertType.ERROR);
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }

    /**
     * Inicia un nuevo juego de 6x6.
     *
     * @param actionEvent El evento de acción que desencadena el inicio del juego.
     */
    public void startGameSix(ActionEvent actionEvent) {
        startGame(actionEvent, 6);
    }

    /**
     * Inicia un nuevo juego de 9x9.
     *
     * @param actionEvent El evento de acción que desencadena el inicio del juego.
     */
    public void startGameNine(ActionEvent actionEvent) {
        startGame(actionEvent, 9);
    }
}