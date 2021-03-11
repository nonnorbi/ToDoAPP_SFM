package hu.kisno;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class AddItemController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    void initialize() {
        addButton.setOnAction(event -> {
            System.out.println("Clicked addButton!");
        });
    }

    public void close(MouseEvent event) {
        System.exit(0);
    }
}