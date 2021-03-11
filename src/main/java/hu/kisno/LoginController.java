package hu.kisno;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController {

    public void close(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label labelClose;

    @FXML
    private TextField loginUsername;

    @FXML
    private PasswordField loginPassword;

    @FXML
    private Button loginButton;

    @FXML
    private Button loginSignUpButton;

    @FXML
    void initialize() {
        loginButton.setOnAction(event -> {
            System.out.println("Clicked Login Button!");
        });
        loginSignUpButton.setOnAction(event -> {
            System.out.println("Clicked Sign Up Button!");
        });

    }
}