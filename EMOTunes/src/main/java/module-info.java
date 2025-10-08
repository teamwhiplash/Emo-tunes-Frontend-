module com.emo_tunes.javafxapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;

    opens com.emo_tunes.javafxapp to javafx.fxml;
    exports com.emo_tunes.javafxapp;
}
