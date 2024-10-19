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
    public void startGame(ActionEvent event, int size) throws IOException {
        GameView gameView = new GameView(size);
        gameView.show();
    }

    /**
     * Inicia un nuevo juego de 6x6.
     *
     * @param actionEvent El evento de acción que desencadena el inicio del juego.
     */
    public void startGameSix(ActionEvent actionEvent) throws IOException {
        startGame(actionEvent, 6);
    }

    /**
     * Inicia un nuevo juego de 9x9.
     *
     * @param actionEvent El evento de acción que desencadena el inicio del juego.
     */
    public void startGameNine(ActionEvent actionEvent) throws IOException {
        startGame(actionEvent, 9);
    }
}