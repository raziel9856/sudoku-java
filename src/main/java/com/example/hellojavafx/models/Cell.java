package com.example.hellojavafx.models;

/**
 * Representa una celda en el tablero de Sudoku.
 */
public class Cell {
    private int value;
    private int correctValue;
    private boolean isEditable;
    private boolean isInitial;
    private boolean isCorrect;

    /**
     * Constructor de la clase Cell.
     *
     * @param value El valor de la celda.
     * @param isEditable Indica si la celda es editable.
     * @param isInitial Indica si la celda es parte de la configuración inicial.
     */
    public Cell(int value, boolean isEditable, boolean isInitial) {
        this.value = value;
        this.correctValue = value;
        this.isEditable = isEditable;
        this.isInitial = isInitial;
        this.isCorrect = true; // Por defecto, asumimos que la celda es correcta
    }

    /**
     * Obtiene el valor de la celda.
     *
     * @return El valor de la celda.
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Establece el valor de la celda.
     *
     * @param value El valor a establecer en la celda.
     */
    public void setValue(int value) {
        if (isEditable) {
            this.value = value;
        }
    }

    /**
     * Establece el valor correcto de la celda.
     *
     * @param value El valor correcto a establecer en la celda.
     */
    public void setCorrectValue(int value) {
        this.correctValue = value;
        this.value = value;
    }

    /**
     * Obtiene el valor correcto de la celda.
     *
     * @return El valor correcto de la celda.
     */
    public int getCorrectValue() {
        return this.correctValue;
    }

    /**
     * Establece si la celda es editable.
     *
     * @param isEditable true si la celda es editable, false en caso contrario.
     */
    public void setEditable(boolean isEditable) {
        this.isEditable = isEditable;
    }

    /**
     * Establece si la celda es parte de la configuración inicial.
     *
     * @param isInitial true si la celda es parte de la configuración inicial, false en caso contrario.
     */
    public void setInitial(boolean isInitial) {
        this.isInitial = isInitial;
    }

    /**
     * Verifica si la celda es editable.
     *
     * @return true si la celda es editable, false en caso contrario.
     */
    public boolean isEditable() {
        return this.isEditable;
    }

    /**
     * Verifica si la celda es parte de la configuración inicial.
     *
     * @return true si la celda es parte de la configuración inicial, false en caso contrario.
     */
    public boolean isInitial() {
        return this.isInitial;
    }

    /**
     * Verifica si la celda es correcta.
     *
     * @return true si la celda es correcta, false en caso contrario.
     */
    public boolean isCorrect() {
        return this.isCorrect;
    }

    /**
     * Establece si la celda es correcta.
     *
     * @param isCorrect true si la celda es correcta, false en caso contrario.
     */
    public void setCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }
}