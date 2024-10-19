package com.example.hellojavafx.view;

import com.example.hellojavafx.controllers.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class GameView extends Stage {

    private GameController gameController;

    public GameView(int size) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/hellojavafx/game-view.fxml")
        );
        loader.setController(new GameController(size));
        Parent root = loader.load();
        this.gameController = loader.getController();
        this.setTitle("Sudoku");
        Scene scene = new Scene(root);
        this.getIcons().add(new Image(
                getClass().getResourceAsStream("/com/example/hellojavafx/images/sudoku.png")
        ));
        this.setScene(scene);
        this.show();
    }

    public GameController getGameController() {
        return this.gameController;
    }
}