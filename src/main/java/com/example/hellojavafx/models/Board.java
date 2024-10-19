package com.example.hellojavafx.models;

import java.util.Random;

public class Board {
    private Cell[][] cells;
    private int size;

    /**
     * Constructor de la clase Board.
     *
     * @param size El tamaño del tablero de Sudoku.
     */
    public Board(int size) {
        this.size = size;
        this.cells = new Cell[size][size];
        generateSudoku();
    }

    /**
     * Obtiene la celda en la posición especificada.
     *
     * @param row La fila de la celda.
     * @param col La columna de la celda.
     * @return La celda en la posición especificada.
     */
    public Cell getCell(int row, int col) {
        return this.cells[row][col];
    }

    /**
     * Establece el valor de la celda en la posición especificada.
     *
     * @param row La fila de la celda.
     * @param col La columna de la celda.
     * @param value El valor a establecer en la celda.
     */
    public void setCellValue(int row, int col, int value) {
        this.cells[row][col].setValue(value);
    }

    /**
     * Verifica si el tablero está completo.
     *
     * @return true si el tablero está completo, false en caso contrario.
     */
    public boolean isComplete() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (this.cells[i][j].getValue() == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Genera un tablero de Sudoku.
     */
    private void generateSudoku() {
        int cont = 0;
        do {
            cont++;
            initializeCells();
        } while (!fillCells());
        System.out.print("Intentos para generar el sudoku: " + cont);
        removeCells();
    }

    /**
     * Inicializa las celdas del tablero.
     */
    private void initializeCells() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new Cell(0, false, false); // Inicializa cada celda con valores predeterminados
            }
        }
    }

    /**
     * Llena las celdas del tablero con valores válidos.
     *
     * @return true si se llenaron todas las celdas correctamente, false en caso contrario.
     */
    private boolean fillCells() {
        int attemps = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int num;
                do {
                    attemps++;
                    if (attemps > size * 2) {
                        return false;
                    }
                    num = new Random().nextInt(size) + 1;
                } while (isInRow(i, num) || isInCol(j, num) || isInGroup(i, j, num));
                cells[i][j].setCorrectValue(num);
                attemps = 0;
            }
        }
        return true;
    }

    /**
     * Verifica si un número está en la fila especificada.
     *
     * @param row La fila a verificar.
     * @param num El número a buscar.
     * @return true si el número está en la fila, false en caso contrario.
     */
    private boolean isInRow(int row, int num) {
        for (int col = 0; col < size; col++) {
            if (this.cells[row][col].getValue() == num) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica si un número está en la columna especificada.
     *
     * @param col La columna a verificar.
     * @param num El número a buscar.
     * @return true si el número está en la columna, false en caso contrario.
     */
    private boolean isInCol(int col, int num) {
        for (int row = 0; row < size; row++) {
            if (this.cells[row][col].getValue() == num) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica si un número está en el subgrupo especificado.
     *
     * @param row La fila de la celda.
     * @param col La columna de la celda.
     * @param num El número a buscar.
     * @return true si el número está en el subgrupo, false en caso contrario.
     */
    private boolean isInGroup(int row, int col, int num) {
        int rowSize = (size == 6) ? 2 : 3;
        int startRow = row - row % rowSize;
        int startCol = col - col % 3;
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.cells[i + startRow][j + startCol].getValue() == num) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Vacia celdas del tablero para crear el Sudoku.
     */
    private void removeCells() {
        Random random = new Random();
        int blanks = (size == 6) ? 10 : 60;
        int rowSize = (size == 6) ? 2 : 3;
        int colSize = 3;

        // Asegurar que cada subgrupo tenga al menos 4 números
        for (int startRow = 0; startRow < size; startRow += rowSize) {
            for (int startCol = 0; startCol < size; startCol += colSize) {
                int numbersToKeep = 4;
                while (numbersToKeep > 0) {
                    int row = startRow + random.nextInt(rowSize);
                    int col = startCol + random.nextInt(colSize);
                    if (this.cells[row][col].getValue() != 0) {
                        numbersToKeep--;
                    }
                }
            }
        }

        // Eliminar celdas adicionales hasta alcanzar el número deseado de celdas vacías
        for (int i = 0; i < blanks; i++) {
            int row, col;
            do {
                row = random.nextInt(size);
                col = random.nextInt(size);
            } while (this.cells[row][col].getValue() == 0);
            this.cells[row][col].setEditable(true);
            this.cells[row][col].setValue(0);
            this.cells[row][col].setInitial(false);
        }
    }
}