module com.example.snakegame2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.snakegame2 to javafx.fxml;
    exports com.example.snakegame2;
}