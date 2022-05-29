module com.example.chessgui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.net.http;
    requires org.json;
    requires json.simple;

    exports chesslogic to javafx.graphics;
}