package com.emo_tunes.javafxapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MusicAppLoginController {

    @FXML
    private Button spotifyButton;

    @FXML
    private ImageView appLogo;

    @FXML
    public void initialize() {
        try {
            Image logo = new Image(getClass().getResource("/com/emo_tunes/javafxapp/logo.png").toExternalForm());
            appLogo.setImage(logo);
        } catch (Exception e) {
            System.out.println("Logo not found!");
        }

        spotifyButton.setOnAction(e -> {
            Stage stage = (Stage) spotifyButton.getScene().getWindow();
            Scene landing = LandingPage.createScene(stage);
            stage.setScene(landing);
        });
    }
}
