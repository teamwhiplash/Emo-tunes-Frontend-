package com.emo_tunes.javafxapp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.List;

public class EmotionSongsPopupController {

    @FXML private Label titleLabel;
    @FXML private ListView<String> songsListView;

    private Stage popupStage;

    public void setPopupStage(Stage stage) {
        this.popupStage = stage;
    }

    // Call this to populate songs dynamically
    public void setSongs(String emotion, List<String> songs) {
        titleLabel.setText("Songs for " + emotion);
        songsListView.getItems().setAll(songs);
    }
}
