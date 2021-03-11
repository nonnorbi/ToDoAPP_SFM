package hu.kisno;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label closeLabel;

    @FXML
    private TextField sgnUpFisrtName;

    @FXML
    private TextField signUpLastName;

    @FXML
    private TextField signUpUsername;

    @FXML
    private PasswordField signUpPasswird;

    @FXML
    private Button signUpButton;

    @FXML
    void close(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void initialize() {
    }
}
