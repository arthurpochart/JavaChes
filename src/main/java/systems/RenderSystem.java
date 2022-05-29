package systems;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class RenderSystem implements Initializable {
    @FXML
    public GridPane gridPane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized");
    }

    public void onHelloButtonClick(ActionEvent actionEvent) {
        System.out.println("Clicked");
    }


}
