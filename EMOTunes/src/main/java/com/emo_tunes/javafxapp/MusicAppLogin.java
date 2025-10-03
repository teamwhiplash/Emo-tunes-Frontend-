package com.emo_tunes.javafxapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MusicAppLogin extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/emo_tunes/javafxapp/MusicAppLogin.fxml"));
        Scene scene = new Scene(loader.load(), 500, 600);
        primaryStage.setTitle("EMOTunes");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
