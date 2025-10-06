package com.emo_tunes.javafxapp;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.emo_tunes.javafxapp.UserInfo;


public class PlaylistPageController {

    @FXML
    private VBox playlistContainer;

    // Map of playlists and their songs (example data)
    private final Map<String, List<String>> playlistsToSongs = new HashMap<>();

    // Track currently expanded card to collapse when needed
    private VBox expandedCard = null;

    @FXML
    public void initialize() {
        for (int i = 1; i <= 3; i++) {
            String name = "Playlist " + i;
            String coverImage = "https://i.scdn.co/image/ab67616d0000b2732db7ff835f7a3d7ff7e6b6c9"; // Replace with real image from backend URL
            String[] songs = {
                    "Song 1 from " + name,
                    "Song 2 from " + name,
                    "Song 3 from " + name
            };
            playlistContainer.getChildren().add(createPlaylistCard(name, coverImage, songs));
        }
    }

    private UserInfo userInfo;

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
        // Optional: use it later to personalize playlists
    }

    private HBox createPlaylistCard(String name, String imageUrl, String[] songs) {
        ImageView cover = new ImageView(new Image(imageUrl, true));
        cover.setFitWidth(80);
        cover.setFitHeight(80);
        cover.setStyle("-fx-background-radius: 10;");

        Label label = new Label(name);
        label.getStyleClass().add("playlist-name");

        VBox textBox = new VBox(label);
        textBox.setAlignment(Pos.CENTER_LEFT);
        textBox.setSpacing(5);

        VBox songListBox = new VBox();
        songListBox.setSpacing(5);
        songListBox.setVisible(false);
        songListBox.setManaged(false);

        for (String song : songs) {
            Label songLabel = new Label("♪ " + song);
            songLabel.getStyleClass().add("song-label");
            songListBox.getChildren().add(songLabel);
        }

        VBox rightBox = new VBox(textBox, songListBox);
        rightBox.setAlignment(Pos.CENTER_LEFT);
        rightBox.setSpacing(10);

        HBox card = new HBox(20, cover, rightBox);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); " +
                "-fx-background-radius: 15; -fx-cursor: hand;");
        card.setEffect(new DropShadow(10, Color.rgb(0, 0, 0, 0.25)));

        card.setOnMouseEntered(e -> card.setStyle("-fx-background-color: rgba(60, 60, 60, 0.6); " +
                "-fx-background-radius: 15; -fx-cursor: hand;"));
        card.setOnMouseExited(e -> card.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); " +
                "-fx-background-radius: 15; -fx-cursor: hand;"));

        card.setOnMouseClicked(e -> {
            boolean visible = songListBox.isVisible();
            songListBox.setVisible(!visible);
            songListBox.setManaged(!visible);
        });

        return card;
    }

    private void expandCard(VBox card, VBox songsBox) {
        // Show songs
        songsBox.setVisible(true);
        songsBox.setManaged(true);

        // Animate height increase for smooth effect
        Timeline timeline = new Timeline();
        double startHeight = card.getHeight();
        double endHeight = startHeight + songsBox.getChildren().size() * 24 + 20; // approx song height + padding

        KeyValue kv = new KeyValue(card.minHeightProperty(), endHeight);
        KeyFrame kf = new KeyFrame(Duration.millis(250), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();

        // Change background to lighter on expand
        card.setStyle(
                "-fx-background-color: rgba(50,50,50,0.85);" +
                        "-fx-background-radius: 15;" +
                        "-fx-cursor: hand;"
        );
    }

    private void collapseCard(VBox card) {
        // Hide songs
        if (card.getChildren().size() < 2) return;
        VBox songsBox = (VBox) card.getChildren().get(1);
        songsBox.setVisible(false);
        songsBox.setManaged(false);

        // Animate height decrease
        Timeline timeline = new Timeline();
        double startHeight = card.getHeight();
        double endHeight = 110;  // approx height of collapsed card (adjust if needed)

        KeyValue kv = new KeyValue(card.minHeightProperty(), endHeight);
        KeyFrame kf = new KeyFrame(Duration.millis(250), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();

        // Restore background style
        card.setStyle(
                "-fx-background-color: rgba(0,0,0,0.6);" +
                        "-fx-background-radius: 15;" +
                        "-fx-cursor: hand;"
        );
    }
}
