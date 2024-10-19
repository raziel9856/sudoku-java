package com.example.hellojavafx.models;

public class Player {
    private int id;
    private String secretWord;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setSecretWord(String secretWord) {
        this.secretWord = secretWord;
    }

    public String getSecretWord() {
        return secretWord;
    }

    public String getSecretWordNormalized() {
        String normalized = secretWord.toLowerCase();
        normalized = normalized.replaceAll("á", "a");
        normalized = normalized.replaceAll("é", "e");
        normalized = normalized.replaceAll("í", "i");
        normalized = normalized.replaceAll("ó", "o");
        normalized = normalized.replaceAll("ú", "u");
        return normalized;
    }
}
