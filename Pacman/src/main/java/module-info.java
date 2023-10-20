module com.example.pacman {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.pacman to javafx.fxml;
    exports com.example.pacman;
}