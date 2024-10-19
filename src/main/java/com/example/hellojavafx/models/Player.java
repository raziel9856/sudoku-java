package com.example.hellojavafx.models;

/**
 * Representa un jugador en el juego.
 */
public class Player {
    private int id;
    private String secretWord;

    /**
     * Establece el ID del jugador.
     *
     * @param id El ID del jugador.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el ID del jugador.
     *
     * @return El ID del jugador.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece la palabra secreta del jugador.
     *
     * @param secretWord La palabra secreta del jugador.
     */
    public void setSecretWord(String secretWord) {
        this.secretWord = secretWord;
    }

    /**
     * Obtiene la palabra secreta del jugador.
     *
     * @return La palabra secreta del jugador.
     */
    public String getSecretWord() {
        return secretWord;
    }

    /**
     * Obtiene la palabra secreta normalizada (sin acentos y en minúsculas).
     *
     * @return La palabra secreta normalizada.
     */
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