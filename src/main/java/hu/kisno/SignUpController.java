package hu.kisno;

import hu.kisno.animations.Shaker;
import hu.kisno.database.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class SignUpController {

    @FXML
    private Label registrationMassageLabel;
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
    private Button loginButton;
    @FXML
    private Label errorSignUpMassageLabel;

    public void signUpButtonOnAction(ActionEvent event) {

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String firstname = sgnUpFisrtName.getText();
        String lastname = signUpLastName.getText();
        String username = signUpUsername.getText();
        String password = signUpPasswird.getText();

        String insertFields = "INSERT INTO users(firstname, lastname, username, password) VALUES ('";
        String insertValues = firstname + "','" + lastname + "','" + username + "','" + password + "')";
        String insertRegister = insertFields + insertValues;

        try {

            errorSignUpMassageLabel.setText("");

            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertRegister);

            registrationMassageLabel.setText("User hase been registreted successfully!");
            signUpButton.getScene().getWindow().hide();
            /*try {

                signUpButton.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("login.fxml"));
                loader.load();
                Parent root = loader.getRoot();
                Stage loginStage = new Stage();
                loginStage.initStyle(StageStyle.UNDECORATED);
                loginStage.setScene(new Scene(root, 700, 400));
                loginStage.showAndWait();


            } catch ( IOException e ) {
                e.printStackTrace();
            }*/


        } catch ( SQLException throwables ) {

            Shaker firstName = new Shaker(sgnUpFisrtName);
            Shaker lastName = new Shaker(signUpLastName);
            Shaker userName = new Shaker(signUpUsername);
            Shaker pwd = new Shaker(signUpPasswird);

            firstName.shake();
            lastName.shake();
            userName.shake();
            pwd.shake();

            errorSignUpMassageLabel.setText("Username is not available!");

            throwables.printStackTrace();
            throwables.getCause();
        }

    }

    @FXML
    public void close (MouseEvent event){ System.exit(0); }
}
