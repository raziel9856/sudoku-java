package com.example.hellojavafx.models;

import java.util.Random;

public class Board {
    private Cell[][] cells;
    private int size;

    public Board(int size) {
        this.size = size;
        this.cells = new Cell[size][size];
        generateSudoku();
    }

    public Cell getCell(int row, int col) {
        return this.cells[row][col];
    }

    public void setCellValue(int row, int col, int value) {
        this.cells[row][col].setValue(value);
    }

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

    private void generateSudoku() {
        int cont = 0;
        do {
            cont++;
            initializeCells();
        } while (!fillCells());
        System.out.print("Intentos para generar el sudoku: " + cont);
        removeCells();
    }

    private void initializeCells() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new Cell(0, false, false); // Initialize each cell with default values
            }
        }
    }

    private boolean fillCells() {
        int attemps = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int num;
                do {
                    attemps ++;
                    if (attemps > size*2) {
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

    private boolean isInRow(int row, int num) {
        for (int col = 0; col < size; col++) {
            if (this.cells[row][col].getValue() == num) {
                return true;
            }
        }
        return false;
    }

    private boolean isInCol(int col, int num) {
        for (int row = 0; row < size; row++) {
            if (this.cells[row][col].getValue() == num) {
                return true;
            }
        }
        return false;
    }

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

    private void removeCells() {
        Random random = new Random();
        int blanks = (size == 6) ? 25 : 60;
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