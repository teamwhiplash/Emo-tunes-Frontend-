package com.emo_tunes.javafxapp;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class EmoListPageController implements UserAwareController {

    @FXML
    private VBox emolistContainer; // VBox in FXML where playlists will be added

    @FXML
    private Button createPlaylistBtn;

    // For storing expanded/collapsed state
    private VBox lastExpandedCard = null;

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

        VBox songsBox = new VBox(10);
        songsBox.setAlignment(Pos.CENTER_LEFT);
        songsBox.setPadding(new Insets(10));
        songsBox.setVisible(false);
        songsBox.setManaged(false);

        // ✅ Example placeholder songs
        for (int i = 1; i <= 3; i++) {
            final int index = i; // make it effectively final

            VBox songCard = new VBox(5);
            songCard.setAlignment(Pos.CENTER);
            songCard.setPadding(new Insets(10));
            songCard.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-background-radius: 10;");
            songCard.setCursor(javafx.scene.Cursor.HAND);

            Rectangle albumArt = new Rectangle(60, 60, Color.WHITE);
            Label songLabel = new Label("Song " + index);
            songLabel.setTextFill(Color.WHITE);
            songLabel.setFont(Font.font("Segoe UI", 13));

            songCard.getChildren().addAll(albumArt, songLabel);

            // ✅ Set click handler — pass the card for stage reference
            songCard.setOnMouseClicked(e -> openSongDetails("Song " + index, card));

            songsBox.getChildren().add(songCard);
        }

        card.getChildren().addAll(nameLabel, emotionLabel, songsBox);
        emolistContainer.getChildren().add(card);

        // Expand / collapse behavior
        card.setOnMouseClicked(e -> toggleCard(card, songsBox, playlist));
    }

    private void openSongDetails(String songName, VBox parentCard) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SongDetailsPage.fxml"));
            Parent songDetailsPage = loader.load();

            SongInfo dummySong = new SongInfo();
            dummySong.setSongName(songName);
            dummySong.setArtistName("Unknown Artist");
            dummySong.setReleaseDate("2025-01-01");
            dummySong.setDuration(210);
            dummySong.setCoverURL("https://via.placeholder.com/300x300.png?text=" + songName.replace(" ", "+"));
            dummySong.setSongURL("https://open.spotify.com");

            SongDetailsPageController controller = loader.getController();
            controller.setSong(dummySong);

            Stage stage = (Stage) parentCard.getScene().getWindow();
            Scene scene = stage.getScene();

            // ✅ Save current content for back navigation
            Parent previousContent = scene.getRoot();

            // ✅ Go to song details
            scene.setRoot(songDetailsPage);

            // ✅ Make Back button restore previous page
            controller.setOnBack(() -> scene.setRoot(previousContent));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void toggleCard(VBox card, VBox songsBox, EmoPlaylist playlist) {
        boolean expanding = !songsBox.isVisible();

        if (expanding) {
            if (lastExpandedCard != null && lastExpandedCard != card) {
                VBox previousSongs = (VBox) lastExpandedCard.getChildren().get(2);
                EmoPlaylist prevPlaylist = (EmoPlaylist) lastExpandedCard.getUserData();
                collapseCard(lastExpandedCard, previousSongs, prevPlaylist);
            }
            expandCard(card, songsBox, playlist);
            lastExpandedCard = card;
        } else {
            collapseCard(card, songsBox, playlist);
            lastExpandedCard = null;
        }
    }

    private void expandCard(VBox card, VBox songsBox, EmoPlaylist playlist) {
        songsBox.setVisible(true);
        songsBox.setManaged(true);

        double startHeight = card.getHeight();
        double endHeight = startHeight + songsBox.getChildren().size() * 70 + 20;

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(300),
                        new KeyValue(card.minHeightProperty(), endHeight))
        );
        timeline.play();

        card.setStyle(
                "-fx-background-color: " + toRgbString(playlist.color) + "; " +
                        "-fx-background-radius: 15; -fx-cursor: hand;"
        );
    }

    private void collapseCard(VBox card, VBox songsBox, EmoPlaylist playlist) {
        songsBox.setVisible(false);
        songsBox.setManaged(false);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(300),
                        new KeyValue(card.minHeightProperty(), 110))
        );
        timeline.play();

        card.setStyle(
                "-fx-background-color: " + toRgbString(playlist.color) + "; " +
                        "-fx-background-radius: 15; -fx-cursor: hand;"
        );
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
