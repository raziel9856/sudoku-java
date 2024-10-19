package com.example.hellojavafx.controllers;

import com.example.hellojavafx.models.Board;
import com.example.hellojavafx.models.Cell;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import com.example.hellojavafx.view.alert.AlertBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class GameController {

    private Board board;
    private int size;
    private IntegerProperty errors;
    private IntegerProperty help;

    @FXML
    private GridPane paneBoard;

    @FXML
    private Label lblErrors;

    @FXML
    private Label lblHelp;

    @FXML
    private Button btnHelpMe;

    public GameController(int size) {
        this.size = size;
        this.board = new Board(size);
        this.errors = new SimpleIntegerProperty(0);
        this.help = new SimpleIntegerProperty(0);
    }

    @FXML
    public void initialize() {
        lblErrors.textProperty().bind(errors.asString());
        lblHelp.textProperty().bind(help.asString());
        printBoard();
    }

    private void printBoard() {
        paneBoard.getChildren().clear();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                TextField cellInput = new TextField();
                final int row = i;
                final int col = j;
                if (board.getCell(i, j).isEditable()) {
                    cellInput.setText("");
                    int value = board.getCell(i, j).getValue();
                    if (value != 0) {
                        cellInput.setText(String.valueOf(value));
                    }
                    cellInput.setDisable(false);
                } else {
                    cellInput.setText(String.valueOf(board.getCell(i, j).getValue()));
                    cellInput.setDisable(true);
                }
                cellInput.setMinSize(35, 35);
                if (size == 6) {
                    cellInput.setMaxSize(40, 40);
                }
                cellInput.setStyle("-fx-border-color: black; -fx-alignment: center;");

                cellInput.setOnMouseClicked(event -> {
                    clearBackgrounds();
                    highlightRowAndColumn(row, col);
                });

                // Validar que no pongan letras ni numeros mayores al size
                cellInput.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("[1-" + size + "]")) {
                        cellInput.setText("");
                    } else {
                        int correctValue = board.getCell(row, col).getCorrectValue();
                        if (Integer.parseInt(newValue) != correctValue) {
                            errors.set(errors.get() + 1);
                            if (errors.get() > 2) {
                                new AlertBox().showAlert("Game Over", null, "Lo siento, has perdido el juego");
                                Stage currentStage = (Stage) paneBoard.getScene().getWindow();
                                currentStage.close();
                            } else {
                                cellInput.setStyle("-fx-border-color: red; -fx-alignment: center;");
                            }
                        } else {
                            board.getCell(row, col).setValue(correctValue);
                            cellInput.setStyle("-fx-border-color: black; -fx-alignment: center;");
                        }
                    }
                });
                paneBoard.add(cellInput, j, i);
            }
        }
    }

    private void clearBackgrounds() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                TextField cellInput = (TextField) paneBoard.getChildren().get(i * size + j);
                int correctValue = board.getCell(i, j).getCorrectValue();
                if (!cellInput.getText().equals(String.valueOf(correctValue)) && !cellInput.getText().equals("")) {
                    cellInput.setStyle("-fx-border-color: red; -fx-alignment: center;");
                } else {
                    cellInput.setStyle("-fx-border-color: black; -fx-alignment: center;");
                }
            }
        }
    }

    private void highlightRowAndColumn(int row, int col) {
        for (int i = 0; i < size; i++) {
            TextField rowCell = (TextField) paneBoard.getChildren().get(row * size + i);
            TextField colCell = (TextField) paneBoard.getChildren().get(i * size + col);
            int rowCorrectValue = board.getCell(row, i).getCorrectValue();
            int colCorrectValue = board.getCell(i, col).getCorrectValue();
            if (!rowCell.getText().equals(String.valueOf(rowCorrectValue)) && !rowCell.getText().equals("")) {
                rowCell.setStyle("-fx-border-color: red; -fx-alignment: center;");
            } else {
                rowCell.setStyle("-fx-border-color: black; -fx-alignment: center; -fx-background-color: lightgray;");
            }
            if (!colCell.getText().equals(String.valueOf(colCorrectValue)) && !colCell.getText().equals("")) {
                colCell.setStyle("-fx-border-color: red; -fx-alignment: center;");
            } else {
                colCell.setStyle("-fx-border-color: black; -fx-alignment: center; -fx-background-color: lightgray;");
            }
        }
        int subGridSize = (size == 6) ? 2 : 3;
        int startRow = (row / subGridSize) * subGridSize;
        int startCol = (col / 3) * 3;

        for (int i = startRow; i < startRow + subGridSize; i++) {
            for (int j = startCol; j < startCol + subGridSize; j++) {
                TextField subGridCell = (TextField) paneBoard.getChildren().get(i * size + j);
                int subGridCorrectValue = board.getCell(i, j).getCorrectValue();
                if (!subGridCell.getText().equals(String.valueOf(subGridCorrectValue)) && board.getCell(i, j).getValue() != 0) {
                    subGridCell.setStyle("-fx-border-color: red; -fx-alignment: center;");
                } else {
                    subGridCell.setStyle("-fx-border-color: black; -fx-alignment: center; -fx-background-color: lightgray;");
                }
            }
        }
    }

    @FXML
    public void getHelp(ActionEvent actionEvent) {
        if (help.get() > 2) {
            new AlertBox().showAlert("Error", null, "Ooops, te has quedado sin ayudas");
            return;
        }

        Random random = new Random();
        int row, col;
        Cell cell;

        do {
            row = random.nextInt(size);
            col = random.nextInt(size);
            cell = board.getCell(row, col);
        } while (cell.isInitial() || cell.getValue() != 0);

        cell.setValue(cell.getCorrectValue());
        cell.setEditable(false); // Se marca como no editable
        help.set(help.get() + 1); // Se incrementa el contado de ayudas
        paneBoard.getChildren().clear();
        printBoard(); // Se rehace el tablero con los nuevos parametros
    }
}