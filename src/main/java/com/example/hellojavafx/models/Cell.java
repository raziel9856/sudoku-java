package com.example.hellojavafx.models;

public class Cell {
    private int value;
    private int correctValue;
    private boolean isEditable;
    private boolean isInitial;
    private boolean isCorrect;

    public Cell(int value, boolean isEditable, boolean isInitial) {
        this.value = value;
        this.correctValue = value;
        this.isEditable = isEditable;
        this.isInitial = isInitial;
        this.isCorrect = true; // Por defecto, asumimos que la celda es correcta
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        if (isEditable) {
            this.value = value;
        }
    }

    public void setCorrectValue(int value) {
        this.correctValue = value;
        this.value = value;
    }

    public int getCorrectValue() {
        return this.correctValue;
    }

    public void setEditable(boolean isEditable) {
        this.isEditable = isEditable;
    }

    public void setInitial(boolean isInitial) {
        this.isInitial = isInitial;
    }

    public boolean isEditable() {
        return this.isEditable;
    }

    public boolean isInitial() {
        return this.isInitial;
    }

    public boolean isCorrect() {
        return this.isCorrect;
    }

    public void setCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }
}