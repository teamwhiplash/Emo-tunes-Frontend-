module com.emo_tunes.javafxapp {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.emo_tunes.javafxapp to javafx.fxml;
    exports com.emo_tunes.javafxapp;
}
