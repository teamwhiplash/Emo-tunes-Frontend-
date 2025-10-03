package com.emo_tunes.javafxapp;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class LandingPage {

    public static Scene createScene(Stage stage) {
        VBox root = new VBox(30);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new javafx.geometry.Insets(30));
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #00bfff, #1e90ff);");

        Label title = new Label("Welcome to EMOTunes!");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        title.setTextFill(Color.WHITE);

        HBox topRow = new HBox(20);
        topRow.setAlignment(Pos.CENTER);
        topRow.getChildren().addAll(
                createBox("Happy", "#FFA94D", "#FFD580"),
                createBox("Love", "#F472B6", "#F9A1C1"),
                createBox("Uplift", "#38BDF8", "#A0E7FF")
        );

        HBox bottomRow = new HBox(20);
        bottomRow.setAlignment(Pos.CENTER);
        bottomRow.getChildren().addAll(
                createBox("Sad", "#1E3A5F", "#3E5C7E"),
                createBox("Rage", "#B91C1C", "#FF4C4C")
        );

        root.getChildren().addAll(title, topRow, bottomRow);
        return new Scene(root, 700, 500);
    }

    private static VBox createBox(String label, String startColor, String endColor) {
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setPrefSize(120, 120);
        box.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, "
                + startColor + ", " + endColor + "); -fx-background-radius: 15;");

        Label text = new Label(label);
        text.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        text.setTextFill(Color.WHITE);
        box.getChildren().add(text);
        return box;
    }
}
