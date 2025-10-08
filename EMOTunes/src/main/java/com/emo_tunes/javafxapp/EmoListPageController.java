package com.emo_tunes.javafxapp;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class EmoListPageController {

    @FXML
    private VBox emolistContainer; // VBox in FXML where playlists will be added

    @FXML
    private Button createPlaylistBtn;

    // This method is called by FXML button
    @FXML
    private void handleCreateEmoList() {
        handleCreatePlaylist();
    }

    // Core playlist creation logic
    private void handleCreatePlaylist() {
        Dialog<EmoPlaylist> dialog = new Dialog<>();
        dialog.setTitle("Create EmoList Playlist");
        dialog.setHeaderText("Enter playlist details");

        ButtonType createButtonType = new ButtonType("Create", ButtonType.OK.getButtonData());
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

        VBox dialogContent = new VBox(10);
        dialogContent.setPadding(new Insets(20));

        Label nameLabel = new Label("Playlist Name:");
        javafx.scene.control.TextField nameField = new javafx.scene.control.TextField();

        Label emotionLabel = new Label("Emotion:");
        javafx.scene.control.TextField emotionField = new javafx.scene.control.TextField();

        Label colorLabel = new Label("Color:");
        ColorPicker colorPicker = new ColorPicker(Color.LIGHTBLUE);

        dialogContent.getChildren().addAll(nameLabel, nameField, emotionLabel, emotionField, colorLabel, colorPicker);
        dialog.getDialogPane().setContent(dialogContent);

        Button createButton = (Button) dialog.getDialogPane().lookupButton(createButtonType);
        createButton.setDisable(true);
        nameField.textProperty().addListener((obs, oldVal, newVal) -> createButton.setDisable(newVal.trim().isEmpty()));

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButtonType) {
                return new EmoPlaylist(nameField.getText().trim(),
                        emotionField.getText().trim(),
                        colorPicker.getValue());
            }
            return null;
        });

        dialog.showAndWait().ifPresent(this::addEmoPlaylistCard);
    }

    // Add playlist card to the container
    private void addEmoPlaylistCard(EmoPlaylist playlist) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setAlignment(Pos.TOP_CENTER);
        card.setStyle("-fx-background-color: " + toRgbString(playlist.color) + "; -fx-background-radius: 15;");

        Label nameLabel = new Label(playlist.name);
        nameLabel.setFont(Font.font("Segoe UI Bold", 20));
        nameLabel.setTextFill(Color.WHITE);

        Label emotionLabel = new Label("Mood: " + playlist.emotion);
        emotionLabel.setFont(Font.font("Segoe UI", 14));
        emotionLabel.setTextFill(Color.WHITE);

        HBox songsContainer = new HBox(10);
        songsContainer.setAlignment(Pos.CENTER_LEFT);
        songsContainer.setPadding(new Insets(10));

        // Example placeholder songs
        for (int i = 1; i <= 3; i++) {
            VBox songCard = new VBox();
            songCard.setAlignment(Pos.CENTER);
            songCard.setPadding(new Insets(10));
            songCard.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-background-radius: 10;");

            Rectangle albumArt = new Rectangle(60, 60, Color.WHITE);
            Label songLabel = new Label("Song " + i);
            songLabel.setTextFill(Color.WHITE);

            songCard.getChildren().addAll(albumArt, songLabel);
            songsContainer.getChildren().add(songCard);
        }

        card.getChildren().addAll(nameLabel, emotionLabel, songsContainer);
        emolistContainer.getChildren().add(card);
    }

    // Convert Color to CSS RGB string
    private String toRgbString(Color color) {
        int r = (int) (color.getRed() * 255);
        int g = (int) (color.getGreen() * 255);
        int b = (int) (color.getBlue() * 255);
        return "rgb(" + r + "," + g + "," + b + ")";
    }

    public void setUserInfo(UserInfo userInfo) {
    }

    // Simple POJO for playlist info
    public static class EmoPlaylist {
        public String name;
        public String emotion;
        public Color color;

        public EmoPlaylist(String name, String emotion, Color color) {
            this.name = name;
            this.emotion = emotion;
            this.color = color;
        }
    }
}
