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
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import com.example.hellojavafx.view.alert.AlertBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;
import java.util.Random;

public class GameController {

    private Board board;
    private int size;
    private IntegerProperty errors;
    private IntegerProperty help;
    private IntegerProperty emptyCells;

    @FXML
    private GridPane paneBoard;

    @FXML
    private Label lblErrors;

    @FXML
    private Label lblHelp;

    @FXML
    private Button btnHelpMe;

    /**
     * Constructor del controlador del juego.
     *
     * @param size El tamaño del tablero de Sudoku (6x6 o 9x9).
     */
    public GameController(int size) {
        this.size = size;
        this.board = new Board(size);
        this.errors = new SimpleIntegerProperty(0);
        this.help = new SimpleIntegerProperty(0);
    }

    /**
     * Inicializa el controlador del juego.
     */
    @FXML
    public void initialize() {
        lblErrors.textProperty().bind(errors.asString());
        lblHelp.textProperty().bind(help.asString());
        printBoard();
    }

    /**
     * Imprime el tablero de Sudoku en la interfaz gráfica.
     */
    private void printBoard() {
        this.emptyCells = new SimpleIntegerProperty(size * size);
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
                        emptyCells.set(emptyCells.get() - 1);
                    }
                    cellInput.setDisable(false);
                } else {
                    cellInput.setText(String.valueOf(board.getCell(i, j).getValue()));
                    cellInput.setDisable(true);
                    emptyCells.set(emptyCells.get() - 1);
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

                // Validar que no pongan letras ni números mayores al size
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
                            emptyCells.set(emptyCells.get() - 1);
                            System.out.println("emptyCells: " + emptyCells.get());
                            if (emptyCells.get() == 0) {
                                new AlertBox().showAlert("Ganaste!!!", null, "Felicitaciones! Has completado el sudoku");
                                Stage currentStage = (Stage) paneBoard.getScene().getWindow();
                                currentStage.close();
                            }
                        }
                    }
                });
                paneBoard.add(cellInput, j, i);
            }
        }
        clearBackgrounds();
    }

    /**
     * Limpia los fondos de las celdas del tablero y pinta los subgrupos para que se distingan.
     */
    private void clearBackgrounds() {
        int subGridSize = (size == 6) ? 2 : 3;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                TextField cellInput = (TextField) paneBoard.getChildren().get(i * size + j);
                int correctValue = board.getCell(i, j).getCorrectValue();
                if (!cellInput.getText().equals(String.valueOf(correctValue)) && !cellInput.getText().equals("")) {
                    cellInput.setStyle("-fx-border-color: red; -fx-alignment: center;");
                } else {
                    cellInput.setStyle("-fx-border-color: black; -fx-alignment: center;");
                }
                // Aplicar color de fondo #e2e2e2 a los subgrupos
                if ((i / subGridSize + j / subGridSize) % 2 == 0) {
                    cellInput.setStyle(cellInput.getStyle() + " -fx-background-color: #e2e2e2;");
                }
            }
        }
    }

    /**
     * Resalta la fila y la columna de la celda seleccionada.
     *
     * @param row La fila de la celda seleccionada.
     * @param col La columna de la celda seleccionada.
     */
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
                colCell.setStyle("-fx-border-color: black; -fx-alignment: center; -fx-background-color: #b7b7b7;");
            }
        }
        int subGridSize = (size == 6) ? 2 : 3;
        int startRow = (row / subGridSize) * subGridSize;
        int startCol = (col / 3) * 3;

        for (int i = startRow; i < startRow + subGridSize; i++) {
            for (int j = startCol; j < startCol + subGridSize; j++) {
                TextField subGridCell = (TextField) paneBoard.getChildren().get(i * size + j);
                int subGridCorrectValue = board.getCell(i, j).getCorrectValue();
                if (!subGridCell.getText().equals(String.valueOf(subGridCorrectValue)) && !subGridCell.getText().equals("")) {
                    subGridCell.setStyle("-fx-border-color: red; -fx-alignment: center;");
                } else {
                    subGridCell.setStyle("-fx-border-color: black; -fx-alignment: center; -fx-background-color: #b7b7b7;");
                }
            }
        }
    }

    /**
     * Ayuda al usuario llenando una celda vacía con el valor correcto.
     *
     * @param actionEvent El evento de acción del botón.
     */
    @FXML
    public void getHelp(ActionEvent actionEvent) {
        if (help.get() > 2) {
            new AlertBox().showAlert("Error", null, "Ooops, te has quedado sin ayudas");
            return;
        }
        if (emptyCells.get() == 1) {
            new AlertBox().showAlert("Error", null, "No puedes pedir ayuda cuando te falta una sola celda por llenar");
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
        help.set(help.get() + 1); // Se incrementa el contador de ayudas
        paneBoard.getChildren().clear();
        printBoard(); // Se rehace el tablero con los nuevos parámetros
    }

    /**
     * Muestra un mensaje de confirmación para iniciar un nuevo juego.
     *
     * @param size El tamaño del tablero de Sudoku (6x6 o 9x9).
     * @throws IOException Si no se puede cargar el archivo FXML.
     */
    public void promptNewGame(int size) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Nuevo Juego");
        alert.setHeaderText(null);
        alert.setContentText("Estás seguro de iniciar un nuevo juego " + size + "x" + size + "? Perderás el avance actual.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hellojavafx/game-view.fxml"));
            loader.setController(new GameController(size));
            Parent root = loader.load();
            Stage primaryStage = (Stage) paneBoard.getScene().getWindow();
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
    }

    /**
     * Inicia un nuevo juego de 6x6.
     *
     * @param event El evento de acción desde el botón
     * @throws IOException Si no se puede cargar el archivo FXML.
     */
    @FXML
    private void startGameSix(ActionEvent event) throws IOException {
        promptNewGame(6);
    }

    /**
     * Inicia un nuevo juego de 9x9.
     *
     * @param event El evento de acción desde el botón
     * @throws IOException Si no se puede cargar el archivo FXML.
     */
    @FXML
    private void startGameNine(ActionEvent event) throws IOException {
        promptNewGame(9);
    }

    /**
     * Muestra una alerta con las instrucciones del juego.
     */
    @FXML
    private void showInstructions() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Instrucciones del Juego");
        alert.setHeaderText(null);
        alert.setContentText("Aquí van las instrucciones del juego de Sudoku:\n\n" +
                "1. Llena las celdas con números del 1 al " + size + ".\n" +
                "2. Cada número debe aparecer solo una vez por fila, columna y subcuadro.\n" +
                "3. Usa las ayudas si te quedas atascado, pero solo tienes 3.\n" +
                "4. No puedes pedir ayuda cuando solo falta una celda por llenar.\n" +
                "5. Si cometes 3 errores, perderás el juego.\n\n" +
                "¡Buena suerte!");

        alert.showAndWait();
    }
}